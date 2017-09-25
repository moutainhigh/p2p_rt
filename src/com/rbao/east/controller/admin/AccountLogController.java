package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.StringUtil;

/**
 * 资金使用记录
 * 
 */

@Controller
@RequestMapping("accountLog/")
public class AccountLogController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(AccountLogController.class);

	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private UserService userService;

	/**
	 * 资金使用记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAccountLogList")
	public String getAccountLogList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		Integer userId=null;
		if(paramsMap.containsKey("userId")){
			userId=Integer.parseInt(paramsMap.get("userId"));
		}
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		if(null!=userId){
			paramsMap.put("userId", userId.toString());
		}else{
			paramsMap.put("userId", null);
		}

		String tradeCode = paramsMap.get("tradeCode");
		if (StringUtils.isNotEmpty(tradeCode)) {
			paramsMap.put("tradeCode", "(" + tradeCode + ")");
		}
		
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = accountLogService.getAccountLog(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		
		paramsMap.put("tradeCode", tradeCode);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		return "record/accountloglist";
	}

	/**
	 * 导出Excel
	 */
	@RequestMapping("logToExcel")
	public void logToExcel(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		params.put("numPerPage", "500000");
		try {
			PageModel pm = accountLogService.getAccountLog(params);
			List<Map<String, String>> ars = pm.getList();

			String[] titles = { "序号", "用户名称", "真实姓名" ,"类型", "总金额", "操作金额", "可用金额",
					"冻结金额", "收回金额", "交易对方", "添加时间", "添加IP", "备注" };

			List<String[]> contents = new ArrayList<String[]>();
			for (Map<String, String> map : ars) {
				String[] conList = new String[titles.length];
				conList[0] = StringUtil.toString(map.get("id"));
				conList[1] = StringUtil.toString(map.get("userAccount"));
				conList[2] = StringUtil.toString(map.get("userRealname"));
				conList[3] = StringUtil.toString(AccountLog.ALL_TRADE_CODE
						.get(map.get("tradeCode")));
				conList[4] = StringUtil.toString(map.get("allMoney"));
				conList[5] = StringUtil.toString(map.get("dealMoney"));
				conList[6] = StringUtil.toString(map.get("availableMoney"));
				conList[7] = StringUtil.toString(map.get("unavailableMoney"));
				conList[8] = StringUtil.toString(map.get("repossessedMoney"));
				conList[9] = StringUtil.toString(map.get("tradeCodeUser"));
				conList[10] = StringUtil.toString(map.get("logAddtime"));
				conList[11] = StringUtil.toString(map.get("logAddip"));
				conList[12] = StringUtil.toString(map.get("logRemark"));

				contents.add(conList);

			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("accountlog-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "资金使用记录统计数据", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}

	}

	/**
	 * 统计分页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getLogStatisticsPageList")
	public String getLogStatisticsPageList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = accountLogService.getLogStatisticsPage(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		request.getSession(true).setAttribute("queryParams", paramsMap);
		return "accountStatistics/logstatisticslist";
	}

	/**
	 * 根据用户名查看资金记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAccountLogListByUserAccount")
	public String getAccountLogListByUserAccount(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		User user = userService.getById(Integer.parseInt(param.get("supportID")
				.toString()));
		return "redirect:v_getAccountLogList?right_id=329&userAccount="
				+ user.getUserAccount();
	}

	// logToExcel
	/**
	 * 导出Excel
	 */
	@RequestMapping("toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response) {
		//Map<String, String> params = this.getParameters(request);
		Map<String, String> params=(Map<String, String>)request.getSession().getAttribute("queryParams");
		
		params.put("numPerPage", "10000");

		PageModel pm = accountLogService.getLogStatisticsPage(params);
		List<Map<String, String>> ars = pm.getList();
		System.out.println("---------export-- logStatistics-number--->"
				+ ars.size());

		try {
			String[] titles = { "用户名称","真实姓名","手机号", "资金总额", "可用金额", "投标奖励", "续投奖励",
					"借款手续费", "利息管理费", "滞纳金管理费", "推广邀请奖励", "提现成功", "充值",
					"线下充值奖励", "用户费用扣款" };

			List<String[]> contents = new ArrayList<String[]>();
			for (Map<String, String> map : ars) {
				String[] conList = new String[titles.length];
				conList[0] = StringUtil.toString(map.get("userAccount"));
				conList[1] = StringUtil.toString(map.get("userRealname"));
				conList[2] = StringUtil.toString(map.get("userPhone"));
				conList[3] = StringUtil.toString(map.get("allMoney"));
				conList[4] = StringUtil.toString(map.get("availableMoney"));
				conList[5] = toString(map.get("log_4"));
				conList[6] = toString(map.get("log_5"));
				conList[7] = toString(map.get("log_6"));
				conList[8] = toString(map.get("log_8"));
				conList[9] = toString(map.get("log_9"));
				conList[10] = toString(map.get("log_10"));
				conList[11] = toString(map.get("log_11"));
				conList[12] = toString(map.get("log_13"));
				conList[13] = toString(map.get("log_14"));
				conList[14] = toString(map.get("log_15"));

				contents.add(conList);

			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("logStatistics-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "帐户统计数据", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}
	}

	private String toString(Object obj) {
		if (obj == null) {
			return "0.00";
		} else {
			return obj.toString();
		}
	}
}
