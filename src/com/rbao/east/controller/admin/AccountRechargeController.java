package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.math.BigDecimal;
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

import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.Constants;
import com.rbao.east.common.SequenceUtils;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.PaymentConfig;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.AccountRechargeService;
import com.rbao.east.service.PaymentConfigService;
import com.rbao.east.service.SysFeesRateService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;
/**
 * 充值操作
 * */
@Controller
@RequestMapping("accountRecharge/")
public class AccountRechargeController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(AccountRechargeController.class);

	@Autowired
	private AccountRechargeService accountRechargeService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private SysFeesRateService sysFeesRateService;
	@Autowired
	private UserService userService;
	@Autowired
	private PaymentConfigService paymentConfigService;

	/**
	 * 充值记录列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAccountRechargeList")
	public String getAccountRechargeList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = accountRechargeService
				.getAccountRecharge(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		return "record/accountrechargelist";
	}

	/**
	 * 根据Id查询充值记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAccountRechargeById")
	public String getAccountRechargeById(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		model.addAttribute("right_id", paramsMap.get("right_id").toString());
		AccountRecharge accountRecharge = accountRechargeService
				.getAccountRechargeById(Integer.parseInt(paramsMap.get(
						"supportID").toString()));
		model.addAttribute("accountRecharge", accountRecharge);
		SysFeesRate sysFeesRate = sysFeesRateService.getSysFeesRate();
		// 线下充值奖励
		if (accountRecharge.getRechargeType().equals(
				AccountRecharge.RECHARGE_TYPE_OFF) &&
				CompareUtils.greaterEquals(accountRecharge.getRechargeMoney(), sysFeesRate.getSysOfflineRewardMinmoney())) {
			BigDecimal rechargeAward = CalculateProcess.awardOfRecharge(
					accountRecharge.getRechargeMoney(), sysFeesRate.getSysOfflineReward());
			model.addAttribute("rechargeAward", rechargeAward);
		}else{
			model.addAttribute("rechargeAward", "0.00");
		}
		
		return "record/checkaccountrecharge";

	}

	/**
	 * 审核充值记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param accountRecharge
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "checkAccountRecharge")
	public void checkAccountRecharge(HttpServletRequest request,
			HttpServletResponse response, Model model,
			AccountRecharge accountRecharge) {
		boolean succ = false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id").toString());
		accountRecharge.setVerifyUserid(this.loginAdminUser(request).getId());
		accountRecharge.setVerifyTime(new Date());
		try {
			succ = accountRechargeService.saveAccountRecharge(accountRecharge,true);
		} catch (Exception e) {
			
		}
		SpringUtils.renderDwzResult(response, succ, succ ? "操作成功" : "操作失败",
				DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id")
						.toString());
	}

	/**
	 * 添加线下充值
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @author Sandy 5/17
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "addAccountRecharge")
	public String addUserBank(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		// paramsMap.put("paymentType", "0");
		paramsMap.put("paymentStatus", PaymentConfig.STATUS_START.toString());
		paramsMap.put("paymentType", PaymentConfig.PAYMENT_TYPE_OOF.toString());
		List<PaymentConfig> paymentConfigList = paymentConfigService
				.getOnlineList(paramsMap);
		model.addAttribute("paymentConfigList", paymentConfigList);

		return "record/addAccountRecharge";

	}

	@RequestMapping(Constants.PRE_PATH_EDIT + "save")
	public void saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, AccountRecharge accountRecharge) {
		Map<String, String> paramsMap = getParameters(request);

		if (!validateCaptcha(request)) {
			SpringUtils.renderDwzResult(response, false, "验证码输入错误");
			return;
		}

		boolean flag = false;
		User user = userService.selectByUserUid(paramsMap.get("userAccount"));
		if (user == null) {
			SpringUtils.renderDwzResult(response, false, "用户名不存在！",
					DwzResult.CALLBACK_CLOSECURRENT, paramsMap.get("right_id")
							.toString());

		} else {
			try {
				Integer userId = user.getId();
				accountRecharge.setUserId(userId);
				accountRecharge
						.setRechargeType(AccountRecharge.RECHARGE_TYPE_back);// 线下充值

				String rechargeTradeNo = SequenceUtils.payRecordNo(userId);
				accountRecharge.setRechargeTradeNo(rechargeTradeNo);

				accountRecharge.setRechargeAddip(this.getIpAddr(request));
				accountRecharge.setRechargeAddtime(new Date());
				flag = accountRechargeService
						.saveAccountRecharge(accountRecharge,true);
			} catch (Exception e) {
				
				logger.error("添加线下充值失败！", e);
				flag = false;
			}
			SpringUtils.renderDwzResult(response, flag, flag ? "添加线下充值成功！"
					: "添加线下充值失败！", DwzResult.CALLBACK_CLOSECURRENTDIALOG, paramsMap
					.get("right_id").toString());
		}
	}
	
	/**
	 * 导出Excel
	 * @author adc
	 */
	@RequestMapping("toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		
		params.put("numPerPage", "500000");
		System.out.println("req"+params);
		try {
			PageModel pm = accountRechargeService.getAccountRecharge(params);
			List<Map<String, String>> ars = pm.getList();

			System.out.println("---------export-- accountStatistics-number--->"
					+ ars.size());

			String[] titles = { "序号", "订单号", "用户名称", "真实姓名", "手机号", "类型", "所属银行",
					"充值金额", "费用", "到帐金额", "充值时间", "状态", "银行返回" };

			List<String[]> contents = new ArrayList<String[]>();
			for (Map<String, String> map : ars) {
				String[] conList = new String[13];
				conList[0] = StringUtil.toString(map.get("id"));
				conList[1] = StringUtil.toString(map.get("rechargeTradeNo"));
				conList[2] = StringUtil.toString(map.get("userAccount"));
				conList[3] = StringUtil.toString(map.get("userRealname"));
				conList[4] = StringUtil.toString(map.get("userPhone"));
				conList[5] = StringUtil
						.toString(AccountRecharge.ALL_RECHAREGE_TYPE.get(map
								.get("rechargeType")));
				conList[6] = StringUtil.toString(map.get("rechargePayment"));
				conList[7] = StringUtil.toString(map.get("rechargeMoney"));
				conList[8] = StringUtil.toString(map.get("rechargeFee"));
				BigDecimal money = new BigDecimal(StringUtil.toString(map
						.get("rechargeMoney")));
				BigDecimal fee = new BigDecimal(StringUtil.toString(map
						.get("rechargeFee")));
				conList[9] = StringUtil.toString(money.subtract(fee));
				conList[10] = StringUtil.toString(map.get("rechargeAddtime"));
				conList[11] = StringUtil
						.toString(AccountRecharge.ALL_RECHAREGE_STATUS.get(map
								.get("rechargeStatus")));
				conList[12] = StringUtil.toString(map.get("rechargeReturn"));

				contents.add(conList);

			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("accountRecharge-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "每日统计数据", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}

	}
	

}
