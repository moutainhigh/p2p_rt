package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Report;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.ReportService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;

/**
 * 统计报告
 * 
 * @author daicheng
 * 
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("report")
public class ReportController extends BaseController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private SysModuleService moduleService;

	private String beginTime;
	private String endTime;
	
	/**
	 * 所有统计信息分页显示
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"getReportList")
	public String getReportList(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		Map<String, String> requests = this.getParameters(request);

		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.loginAdminUser(request);
		requests.put("userId", user.getId().toString());
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(requests);
		result.put("righSubtList", righSubtList);
		model.addAttribute("righSubtList", righSubtList);

		PageModel pageModel = reportService.getPageReports(requests);
		model.addAttribute("pm", pageModel);
		model.addAttribute("params", requests);
		return "report/showReportList";
	}

	/**
	 * 导出Excel
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"toExcel")
	public void toExcel(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		Map<String, String> params = this.getParameters(request);

		// System.out.println("-->>>>"+request.getParameter("beginTime"));
		// System.out.println("-->>>>"+request.getParameter("endTime"));
		// Set<String> keys = params.keySet();
		// for (String key : keys) {
		// System.out.println("request-key-->" + key + " ,value-->"
		// + params.get(key));
		// }
		// String bt = request.getParameter("beginTime");
		// String et = request.getParameter("endTime");

		try {
			List<Report> reports = reportService.getAllReports(params);
			System.out.println("---------export--reports-number--->"
					+ reports.size());
			String[] titles = { "统计日期", "注册人数", "VIP认证数", "实名认证数", "手机认证数",
					"借款手续费", "利息管理费", "提现手续费", "vip管理费", "充值手续费", "已还款数量",
					"已还款金额", "新增逾期数", "新增逾期金额", "线上充值", "线下充值", "提现", "充值-提现",
					"利息", "投标奖励", "线下充值奖励", "续投奖励", "推荐奖励", "注册奖励", "发布数量",
					"发布总额", "还款数", "还款总额", "投标数", "投标总额", "有效投标数", "有效投标总额" };
			List<String[]> contents = new ArrayList<String[]>();
			for (Report report : reports) {
				String[] conList = new String[32];
				conList[0] = DateUtils.formatDate("yyyy-MM-dd",
						report.getReportDate());
				conList[1] = report.getRegisterCount().toString();
				conList[2] = report.getVipCount().toString();
				conList[3] = report.getAttestationRealnameCount().toString();
				conList[4] = report.getAttestationTelCount().toString();
				conList[5] = report.getBorrowFee().toString();
				conList[6] = report.getInterestFee().toString();
				conList[7] = report.getCashFee().toString();
				conList[8] = report.getVipFee().toString();
				conList[9] = report.getRechargeFee().toString();
				conList[10] = report.getOverdueRepayedCount().toString();
				conList[11] = report.getOverdueRepayedAmount().toString();
				conList[12] = report.getNewIncreasedCount().toString();
				conList[13] = report.getNewIncreasedAmount().toString();
				conList[14] = report.getRechargeOnline().toString();
				conList[15] = report.getRechargeOffline().toString();
				conList[16] = report.getCash().toString();
				conList[17] = report.getGetIn().toString();
				conList[18] = report.getInterest().toString();
				conList[19] = report.getTenderReward().toString();
				conList[20] = report.getRechargeOfflineReward().toString();
				conList[21] = report.getContinueReward().toString();
				conList[22] = report.getRecommendReward().toString();
				conList[23] = report.getRegisterReward().toString();
				conList[24] = report.getPublishCount().toString();
				conList[25] = report.getPublishAmount().toString();
				conList[26] = report.getRepayedCount().toString();
				conList[27] = report.getRepayedAmount().toString();
				conList[28] = report.getTenderCount().toString();
				conList[29] = report.getTenderAmount().toString();
				conList[30] = report.getEffeTenderCount().toString();
				conList[31] = report.getEffeTenderAmount().toString();

				contents.add(conList);
			}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(("report-data"+".xls").getBytes("UTF-8"),
							"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "全部统计数据", titles, contents);
		} catch (Exception e) {
			logger.error("导出excel失败", e);
			
		}
	}

	@RequestMapping("test2")
	public String test2(HttpServletResponse response,
			HttpServletRequest request, Model model, String type) {
		System.out.println("------type----->" + type);

		Map<String, String> requests = this.getParameters(request);
		Set<String> keys = requests.keySet();
		for (String key : keys) {
			System.out.println("test-key-->" + key + " ,value-->"
					+ requests.get(key));
		}
		return "report/test2";
	}

	/**
	 * 根据type跳转到对应的视图
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"goWhichChart")
	public String chartsDispatcher(HttpServletResponse response,
			HttpServletRequest request, Model model, String type) throws Exception {
		int dayNum = 0;
		beginTime = request.getParameter("beginTime");
		endTime = request.getParameter("endTime");

		if (StringUtils.isEmpty(beginTime) && StringUtils.isEmpty(endTime)) {
			endTime = DateUtils.formatDate("yyyy-MM-dd", new Date());
			beginTime = DateUtils.formatDate("yyyy-MM-dd",
					DateUtils.addDay(new Date(), -6));//初始显示7天
			dayNum = 7;
		}
		
		if (StringUtils.isNotEmpty(beginTime) && StringUtils.isEmpty(endTime)) {
			Date bt = DateUtils.convertStringToDate(beginTime);
			int days = DateUtils.daysBetween(bt, new Date());
			dayNum = days+1;
			if (days > 30) {// 页面最多显示多少
				days = 29;
				dayNum = 29+1;
			}
			endTime = DateUtils.convertDateToString(DateUtils.addDay(bt, days));
		}
		if (StringUtils.isEmpty(beginTime) && StringUtils.isNotEmpty(endTime)) {
			Date et = DateUtils.convertStringToDate(endTime);
			beginTime = DateUtils
					.convertDateToString(DateUtils.addDay(et, -29));
			dayNum = 29+1;
		}
		if (StringUtils.isNotEmpty(beginTime)
				&& StringUtils.isNotEmpty(endTime)) {
			Date bt = DateUtils.convertStringToDate(beginTime);
			Date et = DateUtils.convertStringToDate(endTime);
			int days = DateUtils.daysBetween(bt, et);
			dayNum = days+1;
			if (days > 30) {
				days = 29;
				dayNum = 29+1;
			}
			beginTime = DateUtils
					.convertDateToString(DateUtils.addDay(et, -days));
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		model.addAttribute("params", params);
		
		model.addAttribute("type", type);
		model.addAttribute("days", dayNum);
		
		System.out.println("------type----->" + type);
		if (type.equalsIgnoreCase("user")) {
			return "report/userChart";
		}else if (type.equalsIgnoreCase("overdue")) {
			return "report/overdueChart";
		}else if (type.equalsIgnoreCase("money")) {
			return "report/money";
		}else if (type.equalsIgnoreCase("investincome")) {
			return "report/investincome";
		}else if (type.equalsIgnoreCase("borrow")) {
			return "report/borrow";
		}else if (type.equalsIgnoreCase("platincome")) {
			return "report/platincome";
		}
		
		return null;
		
	}

	/**
	 * 转到用户趋势图
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"gotoUserChart")
	public String gotoUserChart(HttpServletResponse response,
			HttpServletRequest request, Model model) throws ParseException {

		beginTime = request.getParameter("beginTime");
		endTime = request.getParameter("endTime");

//		Map<String, String> requests = this.getParameters(request);
//		Set<String> keys = requests.keySet();
//		for (String key : keys) {
//			System.out.println("test-key-->" + key + " ,value-->"
//					+ requests.get(key));
//		}
		
		//控制页面最多显示数目
		if (StringUtils.isNotEmpty(beginTime) && StringUtils.isEmpty(endTime)) {
			Date bt = DateUtils.convertStringToDate(beginTime);
			int days = DateUtils.daysBetween(bt, new Date());
			if (days > 30) {// 页面最多显示多少
				days = 29;
			}
			endTime = DateUtils.convertDateToString(DateUtils.addDay(bt, days));
		}
		if (StringUtils.isEmpty(beginTime) && StringUtils.isNotEmpty(endTime)) {
			Date et = DateUtils.convertStringToDate(endTime);
			beginTime = DateUtils
					.convertDateToString(DateUtils.addDay(et, -29));
		}
		if (StringUtils.isNotEmpty(beginTime)
				&& StringUtils.isNotEmpty(endTime)) {
			Date bt = DateUtils.convertStringToDate(beginTime);
			Date et = DateUtils.convertStringToDate(endTime);
			int days = DateUtils.daysBetween(bt, et);
			if (days > 30) {
				days = 29;
			}
			beginTime = DateUtils
					.convertDateToString(DateUtils.addDay(et, -days));
		}
		

		Map<String, String> params = new HashMap<String, String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		model.addAttribute("params", params);
		return "report/userChart";
	}

	/**
	 * 得到用户趋势数据
	 */
	@RequestMapping("getUserData")
	public String getUserData(HttpServletResponse response,
			HttpServletRequest request, Model model) {

		// Map<String, String> requests = this.getParameters(request);
		// Set<String> keys = requests.keySet();
		// for (String key : keys) {
		// System.out.println("key-->"+key+" ,value-->"+requests.get(key));
		// }

		System.out.println("beginTime:" + beginTime + "endTime:" + endTime);

		List<Integer> countVip = new ArrayList<Integer>();
		List<Integer> countReg = new ArrayList<Integer>();
		List<Integer> countName = new ArrayList<Integer>();
		List<Integer> countTel = new ArrayList<Integer>();
		List<String> dates = new ArrayList<String>();


		Map<String, String> params = new HashMap<String, String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		model.addAttribute("params", params);

		List<Report> reports = reportService.getAllReports(params);
		for (Report re : reports) {
			countVip.add(re.getVipCount());
			countReg.add(re.getRegisterCount());
			countName.add(re.getAttestationRealnameCount());
			countTel.add(re.getAttestationTelCount()/2);
			dates.add(DateUtils.formatDate("MM-dd", re.getReportDate()));
		}

		Map<String, List> map = new HashMap<String, List>();
		map.put("countTel", countTel);
		map.put("countName", countName);
		map.put("countReg", countReg);
		map.put("countVip", countVip);
		map.put("dates", dates);

		String countData = SpringUtils.fromObject(map);
		SpringUtils.renderText(response, countData);

		return null;
	}

	/**
	 * 转到overdue趋势图
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"gotoOverdueChart")
	public String gotoOverDueChart(HttpServletResponse response,
			HttpServletRequest request, Model model) throws ParseException {

		beginTime = request.getParameter("beginTime");
		endTime = request.getParameter("endTime");

		Map<String, String> params = new HashMap<String, String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		model.addAttribute("params", params);
		return "report/overdueChart";
	}

	/**
	 * 得到Overdue趋势数据
	 */
	@RequestMapping("getOverdueData")
	public String getOverdueData(HttpServletResponse response,
			HttpServletRequest request, Model model) {

		List<Integer> repayedCount = new ArrayList<Integer>();
		List repayedAmount = new ArrayList();
		List<Integer> newIncreasedCount = new ArrayList<Integer>();
		List newIncreasedAmount = new ArrayList();
		List<String> dates = new ArrayList<String>();

		if (StringUtils.isEmpty(beginTime) & StringUtils.isEmpty(endTime)) {
			endTime = DateUtils.formatDate("yyyy-MM-dd", new Date());
			beginTime = DateUtils.formatDate("yyyy-MM-dd",
					DateUtils.addDay(new Date(), -6));
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		model.addAttribute("params", params);

		List<Report> reports = reportService.getAllReports(params);
		for (Report re : reports) {
			repayedCount.add(re.getRepayedCount());
			repayedAmount.add(re.getRepayedAmount());
			newIncreasedCount.add(re.getNewIncreasedCount());
			newIncreasedAmount.add(re.getNewIncreasedAmount());
			dates.add(DateUtils.formatDate("MM-dd", re.getReportDate()));
		}

		Map<String, List> map = new HashMap<String, List>();
		map.put("repayedCount", repayedCount);
		map.put("repayedAmount", repayedAmount);
		map.put("newIncreasedCount", newIncreasedCount);
		map.put("newIncreasedAmount", newIncreasedAmount);
		map.put("dates", dates);

		String countData = SpringUtils.fromObject(map);
		SpringUtils.renderText(response, countData);

		return null;
	}

	/**
	 * 得到Money趋势数据
	 */
	@RequestMapping("getMoneyData")
	public String getMoneyData(HttpServletResponse response,
			HttpServletRequest request, Model model) {

		List rechargeOnlines = new ArrayList();// 线上充值
		List rechargeOfflines = new ArrayList();// 线下充值
		List cashs = new ArrayList();// 提现
		List getIns = new ArrayList();// 充值-提现
		List<String> dates = new ArrayList<String>();

		if (StringUtils.isEmpty(beginTime) & StringUtils.isEmpty(endTime)) {
			endTime = DateUtils.formatDate("yyyy-MM-dd", new Date());
			beginTime = DateUtils.formatDate("yyyy-MM-dd",
					DateUtils.addDay(new Date(), -6));
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		model.addAttribute("params", params);

		List<Report> reports = reportService.getAllReports(params);
		for (Report re : reports) {
			rechargeOnlines.add(re.getRechargeOnline());
			rechargeOfflines.add(re.getRechargeOffline());
			cashs.add(re.getCash());
			getIns.add(re.getGetIn());
			dates.add(DateUtils.formatDate("MM-dd", re.getReportDate()));
		}

		Map<String, List> map = new HashMap<String, List>();
		map.put("rechargeOnlines", rechargeOnlines);
		map.put("rechargeOfflines", rechargeOfflines);
		map.put("cashs", cashs);
		map.put("getIns", getIns);
		map.put("dates", dates);

		String countData = SpringUtils.fromObject(map);
		SpringUtils.renderText(response, countData);
		return null;
	}

	/**
	 * 得到PlatIncome趋势数据
	 */
	@RequestMapping("getPlatIncomeData")
	public String getPlatIncomeData(HttpServletResponse response,
			HttpServletRequest request, Model model) {

		List borrowFees = new ArrayList();
		List interestFees = new ArrayList();
		List cashFees = new ArrayList();
		List vipFees = new ArrayList();
		List rechargeFees = new ArrayList();
		List<String> dates = new ArrayList<String>();

		if (StringUtils.isEmpty(beginTime) & StringUtils.isEmpty(endTime)) {
			endTime = DateUtils.formatDate("yyyy-MM-dd", new Date());
			beginTime = DateUtils.formatDate("yyyy-MM-dd",
					DateUtils.addDay(new Date(), -6));
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		model.addAttribute("params", params);

		List<Report> reports = reportService.getAllReports(params);
		for (Report re : reports) {
			borrowFees.add(re.getBorrowFee());
			interestFees.add(re.getInterestFee());
			cashFees.add(re.getCashFee());
			vipFees.add(re.getVipFee());
			rechargeFees.add(re.getRechargeFee());
			dates.add(DateUtils.formatDate("MM-dd", re.getReportDate()));
		}

		Map<String, List> map = new HashMap<String, List>();
		map.put("borrowFees", borrowFees);
		map.put("interestFees", interestFees);
		map.put("cashFees", cashFees);
		map.put("vipFees", vipFees);
		map.put("rechargeFees", rechargeFees);
		map.put("dates", dates);

		String countData = SpringUtils.fromObject(map);
		SpringUtils.renderText(response, countData);
		return null;
	}

	/**
	 * 得到Borrow趋势数据
	 */
	@RequestMapping("getBorrowData")
	public String getBorrowData(HttpServletResponse response,
			HttpServletRequest request, Model model) {

		List publishCounts = new ArrayList();// 发布数量
		List publishAmounts = new ArrayList();// 发布总额
		List repayedCounts = new ArrayList();// 还款数
		List repayedAmounts = new ArrayList();// 还款总额
		List tenderCounts = new ArrayList();// 投标数
		List tenderAmounts = new ArrayList();// 投标总额
		List effeTenderCounts = new ArrayList();// 有效投标数
		List effeTenderAmounts = new ArrayList();// 有效投标总额
		List<String> dates = new ArrayList<String>();

		if (StringUtils.isEmpty(beginTime) & StringUtils.isEmpty(endTime)) {
			endTime = DateUtils.formatDate("yyyy-MM-dd", new Date());
			beginTime = DateUtils.formatDate("yyyy-MM-dd",
					DateUtils.addDay(new Date(), -6));
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		model.addAttribute("params", params);

		List<Report> reports = reportService.getAllReports(params);
		for (Report re : reports) {
			publishCounts.add(re.getPublishCount());
			publishAmounts.add(re.getPublishAmount());
			repayedCounts.add(re.getRepayedCount());
			repayedAmounts.add(re.getRepayedAmount());
			tenderCounts.add(re.getTenderCount());
			tenderAmounts.add(re.getTenderAmount());
			effeTenderCounts.add(re.getEffeTenderCount());
			effeTenderAmounts.add(re.getEffeTenderAmount());
			dates.add(DateUtils.formatDate("MM-dd", re.getReportDate()));
		}

		Map<String, List> map = new HashMap<String, List>();
		map.put("publishCounts", publishCounts);
		map.put("publishAmounts", publishAmounts);
		map.put("repayedCounts", repayedCounts);
		map.put("repayedAmounts", repayedAmounts);
		map.put("tenderCounts", tenderCounts);
		map.put("tenderAmounts", tenderAmounts);
		map.put("effeTenderCounts", effeTenderCounts);
		map.put("effeTenderAmounts", effeTenderAmounts);
		map.put("dates", dates);

		String countData = SpringUtils.fromObject(map);
		SpringUtils.renderText(response, countData);
		return null;
	}

	/**
	 * 得到InvestIncome趋势数据
	 */
	@RequestMapping("getInvestIncomeData")
	public String getInvestIncomeData(HttpServletResponse response,
			HttpServletRequest request, Model model) {

		List interests = new ArrayList();// 利息
		List tenderRewards = new ArrayList();// 投标奖励
		List rechargeOfflineRewards = new ArrayList();//
		List continueRewards = new ArrayList();// 续投奖励
		List recommendRewards = new ArrayList();// 推荐奖励
		List registerRewards = new ArrayList();// 注册奖励
		List<String> dates = new ArrayList<String>();

		if (StringUtils.isEmpty(beginTime) & StringUtils.isEmpty(endTime)) {
			endTime = DateUtils.formatDate("yyyy-MM-dd", new Date());
			beginTime = DateUtils.formatDate("yyyy-MM-dd",
					DateUtils.addDay(new Date(), -6));
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		model.addAttribute("params", params);

		List<Report> reports = reportService.getAllReports(params);
		for (Report re : reports) {
			interests.add(re.getInterest());
			tenderRewards.add(re.getTenderReward());
			rechargeOfflineRewards.add(re.getRechargeOfflineReward());
			continueRewards.add(re.getContinueReward());
			recommendRewards.add(re.getRecommendReward());
			registerRewards.add(re.getRegisterReward());
			dates.add(DateUtils.formatDate("MM-dd", re.getReportDate()));
		}

		Map<String, List> map = new HashMap<String, List>();
		map.put("interests", interests);
		map.put("tenderRewards", tenderRewards);
		map.put("rechargeOfflineRewards", rechargeOfflineRewards);
		map.put("continueRewards", continueRewards);
		map.put("recommendRewards", recommendRewards);
		map.put("registerRewards", registerRewards);
		map.put("dates", dates);

		String countData = SpringUtils.fromObject(map);
		SpringUtils.renderText(response, countData);
		return null;
	}
}
