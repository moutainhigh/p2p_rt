package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.AccountDeduct;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.AccountDeductService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;

/**
 * 账号费用管理--》用户费用扣除
 * 
 * @author Sandy
 * 
 */
@Controller
@RequestMapping("accountDeduct/")
public class AccountDeductController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(AccountDeductController.class);
	@Autowired
	private AccountDeductService accountDeductService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysModuleService moduleService;

	
	/**
	 * 添加扣除费用
	 * */
	@RequestMapping(Constants.PRE_PATH_EDIT + "addAccountDeduct")
	public String addUserBank(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));

		/*
		 * Map deductType=new HashMap(); deductType.put(1, "现场认证扣费");
		 * deductType.put(2, "担保垫付扣费"); deductType.put(3, "其他");
		 * model.addAttribute("deductTypeMap",deductType);
		 */
		return "fundAccount/addAccountDeduct";
	}

	/**
	 * 保存扣除费用
	 * */
	@RequestMapping(Constants.PRE_PATH_EDIT + "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			AccountDeduct accountDeduct) {
		User userAdmin = loginAdminUser(request);
		Map<String, String> paramsMap = getParameters(request);
		boolean flag = false;
		User user = userService.selectByUserUid(paramsMap.get("userAccount"));
		if (user == null) {
			SpringUtils.renderDwzResult(response, false, "用户名不存在！",
					DwzResult.CALLBACK_CLOSECURRENT, paramsMap.get("right_id")
							.toString());

		} else {
			try {
				accountDeduct.setUserId(user.getId());
				accountDeduct.setAddTime(new Date());
				accountDeduct.setAddIp(this.getIpAddr(request));
				accountDeduct.setAddUserId(userAdmin.getId());
				if (!validateCaptcha(request)) {
					SpringUtils.renderDwzResult(response, false, "验证码输入错误");
					return;
				}
				flag = accountDeductService.save(accountDeduct);
			} catch (Exception e) {
				
				logger.error("添加用户费用失败！", e);
				flag = false;
			}
			SpringUtils.renderDwzResult(response, flag, flag ? "添加用户费用成功"
					: "添加用户费用失败", DwzResult.CALLBACK_CLOSECURRENTDIALOG, paramsMap
					.get("right_id").toString());
		}

	}
	/**
	 * 获取列表
	 * */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAccountDeductList")
	public String getAccountDeductList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = loginAdminUser(request);
		Map<String, String> paramsMap = getParameters(request);
		PageModel pageModel = accountDeductService
				.getAccountDeductList(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值

		paramsMap.put("userId", String.valueOf(user.getId()));

		model.addAttribute("right_id", paramsMap.get("right_id"));// 刷新
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		return "fundAccount/accountDeductList";

	}
	/**
	 * 审核扣除费用
	 * */
	@RequestMapping(Constants.PRE_PATH_EDIT + "checkAccountDeduct")
	public String checkAccountDeduct(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));

		Integer accountDeductId = Integer.parseInt(paramsMap.get("id"));
		AccountDeduct accountDeduct = accountDeductService
				.getById(accountDeductId);
		model.addAttribute("accountDeduct", accountDeduct);

		return "fundAccount/checkAccountDeduct";
	}
	/**
	 * 保存扣除费用
	 * */
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveCheck")
	public void saveCheck(HttpServletRequest request,
			HttpServletResponse response, AccountDeduct accountDeduct) {
		User userAdmin = loginAdminUser(request);
		Map<String, String> paramsMap = getParameters(request);
		boolean flag = false;
		if (!validateCaptcha(request)) {
			SpringUtils.renderDwzResult(response, false, "验证码输入错误");
			return;
		}
		try {
			accountDeduct.setCheckUserId(userAdmin.getId());
			accountDeduct.setCheckIp(this.getIpAddr(request));
			accountDeduct.setCheckTime(new Date());
			flag = accountDeductService.save(accountDeduct);

		} catch (Exception e) {
			
			logger.error("审核用户费用失败，可能用户可用余额不足！", e);
			flag = false;
		}
		// 添加操作日志
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(userAdmin.getUserAccount());
		operatorLog.setOperatorTitle("审核扣费");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_DEDUCT);
		operatorLog.setOperatorParams(accountDeduct.toString());
		operatorLog.setOperatorReturn(flag ? "审核用户费用成功" : "审核用户费用失败");
		operatorLog.setOperatorStatus(flag ? Integer
				.parseInt(DwzResult.SUCCESS) : Integer
				.parseInt(DwzResult.FAILD));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addAdminLog(operatorLog);

		SpringUtils.renderDwzResult(response, flag, flag ? "审核用户费用成功"
				: "审核用户费用失败", DwzResult.CALLBACK_CLOSECURRENT,
				paramsMap.get("right_id").toString());

	}

	/**
	 * 导出Excel
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "toExcel")
	public void logToExcel(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		params.put("numPerPage", "500000");
		try {
			PageModel pm = accountDeductService.getAccountDeductList(params);
			List<AccountDeduct> ars = pm.getList();

			String[] titles = { "序号", "用户名", "扣费类型", "扣费金额", "备注", "创建时间",
					"创建人", "状态", "审核人", "审核时间" };

			List<String[]> contents = new ArrayList<String[]>();
			for (AccountDeduct ad : ars) {
				String[] conList = new String[10];
				conList[0] = StringUtil.toString(ad.getId());
				conList[1] = StringUtil.toString(ad.getUserAccount());
				conList[2] = StringUtil.toString(AccountDeduct.ALL_TYPE.get(ad.getDeductType()));
				conList[3] = StringUtil.toString(ad.getDeductAmount());
				conList[4] = StringUtil.toString(ad.getRemark());
				conList[5] = StringUtil.toString(DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", ad.getAddTime()));
				conList[6] = StringUtil.toString(ad.getAddUserAccount());
				conList[7] = StringUtil.toString(ad.getCheckState());
				conList[8] = StringUtil.toString(ad.getCheckUserAccount());
				conList[9] = StringUtil.toString(DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", ad.getCheckTime()));

				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(
							("accountDeduct-data" + ".xls").getBytes("UTF-8"),
							"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "费用扣除审核记录统计数据", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}

	}

}
