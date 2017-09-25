package com.rbao.east.controller.front;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.service.AutotenderRulesService;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.ReportService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.SpringUtils;

/**
 * 实时财务
 */

@Controller
@RequestMapping("currentTimeFinance/")
public class CurrentTimeFinanceController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(CurrentTimeFinanceController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private BorrowTenderService borrowTenderService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private BorrowRepossessedService borrowRepossessedService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private BorrowRepaymentService borrowRepaymentService;
	@Autowired
	private AutotenderRulesService autotenderRulesService;
	
	@RequestMapping("getCurrentTimeFinanceToJson.do")
	public void getCurrentTimeFinanceToJson(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			//上线天数
			int countDays = DateUtils.daysBetween(DateUtils.convertStringToDate("2014-12-01"),new Date());
			param.put("countDays",countDays);
		} catch (ParseException e) {
			
		}
		//平台总人数
		Integer allUsers = userService.getAllUsers();
		param.put("allUsers",allUsers);
		
		//投资人数
		Integer tenderUsers = borrowTenderService.selectTenders();
		param.put("tenderUsers",tenderUsers);
		
		//借款人数
		Integer borrowUsers = borrowService.getBorrowUsers();
		param.put("borrowUsers",borrowUsers);
		
		//待收总额
		BigDecimal waitMoney = borrowRepossessedService.getCountMoney(BorrowRepossessed.STATUS_REPOSSESSING);
		
		if(waitMoney != null){
			param.put("waitMoney", waitMoney);
		}else{
			param.put("waitMoney", new BigDecimal(0));
		}
		
		//已回款总额
		BigDecimal returnMoney = borrowRepossessedService.getCountMoney(BorrowRepossessed.STATUS_REPOSSESSED_SUCC);
		if(returnMoney != null){
			param.put("returnMoney", returnMoney);
		}else{
			param.put("returnMoney", new BigDecimal(0));
		}
		
		//已创造财富
		BigDecimal wealth = new BigDecimal(0);
		Map map = reportService.getWealth();
		if(map != null){
			BigDecimal interest =(BigDecimal)map.get("interest");
			BigDecimal tenderReward =(BigDecimal)map.get("tenderReward");
			BigDecimal rechargeOfflineReward =(BigDecimal)map.get("rechargeOfflineReward");
			BigDecimal continueReward =(BigDecimal)map.get("continueReward");
			BigDecimal recommendReward =(BigDecimal)map.get("recommendReward");
			BigDecimal registerReward =(BigDecimal)map.get("registerReward");
			wealth = interest.add(tenderReward).add(rechargeOfflineReward).add(continueReward).add(recommendReward).add(registerReward);
			if(wealth != null){
				param.put("wealth", wealth);
			}
			//逾期总数
			BigDecimal newIncreasedCount = (BigDecimal) map.get("newIncreasedCount");
			if(newIncreasedCount != null){
				param.put("newIncreasedCount", newIncreasedCount);
			}
			//逾期总额
			BigDecimal newIncreasedAmount = (BigDecimal) map.get("newIncreasedAmount");
			if(newIncreasedAmount != null){
				param.put("newIncreasedAmount", newIncreasedAmount);
			}
			//还款总数
			BigDecimal repayedCount = (BigDecimal) map.get("repayedCount");
			if(repayedCount != null){
				param.put("repayedCount", repayedCount);
			}
			//还款总额
			BigDecimal repayedAmount = (BigDecimal) map.get("repayedAmount");
			if(repayedAmount != null){
				param.put("repayedAmount", repayedAmount);
			}
		}
		
		//提前还款次数
		Integer forwardPayment = borrowRepaymentService.getForwardRepayment();
		if(forwardPayment != null){
			param.put("forwardPayment", forwardPayment);
		}else{
			param.put("forwardPayment", new BigDecimal(0));
		}
		
		//今日满标额
		Calendar calendar = Calendar.getInstance();
		Map<String, String> paramsMap = new HashMap<String, String>();
		String nowDate = DateUtils.getDate(calendar.getTime());
		paramsMap.put("startDate", nowDate + " 00:00:00");
		paramsMap.put("endDate", nowDate + " 23:59:59");
		BigDecimal todayFullMoney = borrowService.getFullMoney(paramsMap);
		if(todayFullMoney != null){
			param.put("todayFullMoney", todayFullMoney);
		}
		
		//昨日满标额
		calendar.add(Calendar.DATE, -1);
		Map<String, String> paramsMap1 = new HashMap<String, String>();
		String firstDate = DateUtils.getDate(calendar.getTime());
		paramsMap1.put("startDate", firstDate );
		paramsMap1.put("endDate", nowDate);
		BigDecimal yesterdayFullMoney = reportService.selectFullMoney(paramsMap1);
		if(yesterdayFullMoney != null){
			param.put("yesterdayFullMoney", yesterdayFullMoney);
		}
		
		
		//自动投标设置额度合计
		BigDecimal allTenderMoney = autotenderRulesService.getAllTenderMoney();
		if(allTenderMoney != null){
			param.put("allTenderMoney", allTenderMoney);
		}else{
			param.put("allTenderMoney", new BigDecimal(0));
		}
		
		//自动投标有效余额合计
		BigDecimal allEffAutoMoney = autotenderRulesService.getEffAutoMoney();
		if(allEffAutoMoney != null){
			param.put("allEffAutoMoney", allEffAutoMoney);
		}else{
			param.put("allEffAutoMoney", new BigDecimal(0));
		}
		
		//本周满标额
		String[] week=DateUtils.getWeekDay();
		paramsMap.put("startDate", week[0]);
		paramsMap.put("endDate", week[1]);
		BigDecimal weekFullMoney = reportService.selectFullMoney(paramsMap);
		if(weekFullMoney != null){
			param.put("weekFullMoney", weekFullMoney);
		}
		
		//上周满标额
		String[] forwardWeek=DateUtils.getNewLastWeekDay(new Date());
		paramsMap.put("startDate", forwardWeek[0] + " 00:00:00");
		paramsMap.put("endDate", forwardWeek[1] + " 23:59:59");
		BigDecimal forwardWeekFullMoney = reportService.selectFullMoney(paramsMap);
		if(forwardWeekFullMoney != null){
			param.put("forwardWeekFullMoney", forwardWeekFullMoney);
		}
		
		//本月满标额
		String[] monthDate = DateUtils.findMonthDate();
		paramsMap.put("startDate", monthDate[0] + " 00:00:00");
		paramsMap.put("endDate", monthDate[1] + " 23:59:59");
		BigDecimal monthFullMoney = reportService.selectFullMoney(paramsMap);
		if(monthFullMoney != null){
			param.put("monthFullMoney", monthFullMoney);
		}
		
		//上月满标额
		String[] date = DateUtils.findLastMonth();
		paramsMap = new HashMap<String, String>();
		paramsMap.put("startDate", date[0]);
		paramsMap.put("endDate", date[1]);
		BigDecimal forwardMonthFullMoney = reportService.selectFullMoney(paramsMap);
		if(forwardMonthFullMoney != null){
			param.put("forwardMonthFullMoney", forwardMonthFullMoney);
		}
		SpringUtils.renderJson(response, param);
	}
	
	@RequestMapping("getCurrentTimeFinance.do")
	public String getCurrentTimeFinance(HttpServletRequest request,HttpServletResponse response,Model model){
		/*try {
			//上线天数
			int countDays = DateUtils.daysBetween(DateUtils.convertStringToDate("2013-12-02"),new Date());
			model.addAttribute("countDays", countDays);
		} catch (ParseException e) {
			
		}
		
		//平台总人数
		Integer allUsers = userService.getAllUsers();
		model.addAttribute("allUsers", allUsers);
		//投资人数
		Integer tenderUsers = borrowTenderService.getTenderUsers();
		model.addAttribute("tenderUsers", tenderUsers);
		
		//借款人数
		Integer borrowUsers = borrowService.getBorrowUsers();
		model.addAttribute("borrowUsers", borrowUsers);
		//待收总额
		BigDecimal waitMoney = borrowRepossessedService.getCountMoney(BorrowRepossessed.STATUS_REPOSSESSING);
		
		if(waitMoney != null){
			model.addAttribute("waitMoney", waitMoney);
		}else{
			model.addAttribute("waitMoney", new BigDecimal(0));
		}
		
		//已回款总额
		BigDecimal returnMoney = borrowRepossessedService.getCountMoney(BorrowRepossessed.STATUS_REPOSSESSED_SUCC);
		if(returnMoney != null){
			model.addAttribute("returnMoney", returnMoney);
		}else{
			model.addAttribute("returnMoney", new BigDecimal(0));
		}
		
		//已创造财富
		BigDecimal wealth = new BigDecimal(0);
		Map map = reportService.getWealth();
		if(map != null){
			BigDecimal interest =(BigDecimal)map.get("interest");
			BigDecimal tenderReward =(BigDecimal)map.get("tenderReward");
			BigDecimal rechargeOfflineReward =(BigDecimal)map.get("rechargeOfflineReward");
			BigDecimal continueReward =(BigDecimal)map.get("continueReward");
			BigDecimal recommendReward =(BigDecimal)map.get("recommendReward");
			BigDecimal registerReward =(BigDecimal)map.get("registerReward");
			wealth = interest.add(tenderReward).add(rechargeOfflineReward).add(continueReward).add(recommendReward).add(registerReward);
			if(wealth != null){
				model.addAttribute("wealth", wealth);
			}else{
				model.addAttribute("wealth", new BigDecimal(0));
			}
			//逾期总数
			BigDecimal newIncreasedCount = (BigDecimal) map.get("newIncreasedCount");
			if(newIncreasedCount != null){
				model.addAttribute("newIncreasedCount", newIncreasedCount);
			}else{
				model.addAttribute("newIncreasedCount", new BigDecimal(0));
			}
			//逾期总额
			BigDecimal newIncreasedAmount = (BigDecimal) map.get("newIncreasedAmount");
			if(newIncreasedAmount != null){
				model.addAttribute("newIncreasedAmount", newIncreasedAmount);
			}else{
				model.addAttribute("newIncreasedAmount", new BigDecimal(0));
			}
			//还款总数
			BigDecimal repayedCount = (BigDecimal) map.get("repayedCount");
			if(repayedCount != null){
				model.addAttribute("repayedCount", repayedCount);
			}else{
				model.addAttribute("repayedCount", new BigDecimal(0));
			}
			//还款总额
			BigDecimal repayedAmount = (BigDecimal) map.get("repayedAmount");
			if(repayedAmount != null){
				model.addAttribute("repayedAmount", repayedAmount);
			}else{
				model.addAttribute("repayedAmount", new BigDecimal(0));
			}
		}else{
			model.addAttribute("wealth", new BigDecimal(0));
			model.addAttribute("newIncreasedCount", new BigDecimal(0));
			model.addAttribute("newIncreasedAmount", new BigDecimal(0));
			model.addAttribute("repayedCount", new BigDecimal(0));
			model.addAttribute("repayedAmount", new BigDecimal(0));
		}
		
		//提前还款次数
		Integer forwardPayment = borrowRepaymentService.getForwardRepayment();
		if(forwardPayment != null){
			model.addAttribute("forwardPayment", forwardPayment);
		}else{
			model.addAttribute("forwardPayment", new BigDecimal(0));
		}
		
		//今日满标额
		Calendar calendar = Calendar.getInstance();
		Map<String, String> paramsMap = new HashMap<String, String>();
		String nowDate = DateUtils.getDate(calendar.getTime());
		paramsMap.put("startDate", nowDate + " 00:00:00");
		paramsMap.put("endDate", nowDate + " 23:59:59");
		BigDecimal todayFullMoney = borrowService.getFullMoney(paramsMap);
		if(todayFullMoney != null){
			model.addAttribute("todayFullMoney", todayFullMoney);
		}else{
			model.addAttribute("todayFullMoney", new BigDecimal(0));
		}
		
		//昨日满标额
		calendar.add(Calendar.DATE, -1);
		Map<String, String> paramsMap1 = new HashMap<String, String>();
		paramsMap1.put("startDate", nowDate + " 00:00:00");
		paramsMap1.put("endDate", nowDate + " 23:59:59");
		BigDecimal yesterdayFullMoney = borrowService.getFullMoney(paramsMap1);
		if(yesterdayFullMoney != null){
			model.addAttribute("yesterdayFullMoney", yesterdayFullMoney);
		}else{
			model.addAttribute("yesterdayFullMoney", new BigDecimal(0));
		}
		
		
		//自动投标设置额度合计
		BigDecimal allTenderMoney = autotenderRulesService.getAllTenderMoney();
		if(allTenderMoney != null){
			model.addAttribute("allTenderMoney", allTenderMoney);
		}else{
			model.addAttribute("allTenderMoney", new BigDecimal(0));
		}
		
		//自动投标有效余额合计
		BigDecimal allEffAutoMoney = autotenderRulesService.getEffAutoMoney();
		if(allEffAutoMoney != null){
			model.addAttribute("allEffAutoMoney", allEffAutoMoney);
		}else{
			model.addAttribute("allEffAutoMoney", new BigDecimal(0));
		}
		
		//本周满标额
		String[] week=DateUtils.getWeekDay();
		paramsMap.put("startDate", week[0] + " 00:00:00");
		paramsMap.put("endDate", week[1] + " 23:59:59");
		BigDecimal weekFullMoney = borrowService.getFullMoney(paramsMap);
		if(weekFullMoney != null){
			model.addAttribute("weekFullMoney", weekFullMoney);
		}else{
			model.addAttribute("weekFullMoney", new BigDecimal(0));
		}
		
		//上周满标额
		String[] forwardWeek=DateUtils.getLastWeekDay();
		paramsMap.put("startDate", forwardWeek[0] + " 00:00:00");
		paramsMap.put("endDate", forwardWeek[1] + " 23:59:59");
		BigDecimal forwardWeekFullMoney = borrowService.getFullMoney(paramsMap);
		if(forwardWeekFullMoney != null){
			model.addAttribute("forwardWeekFullMoney", forwardWeekFullMoney);
		}else{
			model.addAttribute("forwardWeekFullMoney", new BigDecimal(0));
		}
		
		//本月满标额
		String[] monthDate = DateUtils.findMonthDate();
		paramsMap.put("startDate", monthDate[0] + " 00:00:00");
		paramsMap.put("endDate", monthDate[1] + " 23:59:59");
		BigDecimal monthFullMoney = borrowService.getFullMoney(paramsMap);
		if(monthFullMoney != null){
			model.addAttribute("monthFullMoney", monthFullMoney);
		}else{
			model.addAttribute("monthFullMoney", new BigDecimal(0));
		}
		
		//上月满标额
		String[] date = DateUtils.findLastMonth();
		paramsMap = new HashMap<String, String>();
		paramsMap.put("startDate", date[0]);
		paramsMap.put("endDate", date[1]);
		BigDecimal forwardMonthFullMoney = borrowService.getFullMoney(paramsMap);
		if(forwardMonthFullMoney != null){
			model.addAttribute("forwardMonthFullMoney", forwardMonthFullMoney);
		}else{
			model.addAttribute("forwardMonthFullMoney", new BigDecimal(0));
		}*/
		return "currentTimeFinance/finance";
	}
	
	/**
	 * 已回款列表
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("getRepaymentsPage.do")
	public void getRepaymentsPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("repaymentStatus",BorrowRepayment.REPAYMENT_STATUS_SUCCESS.toString());
		PageModel pageModel = borrowRepaymentService.getRepayments(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);
	}
	
	
	@RequestMapping("getRepayingsPage.do")
	public void getRepayingsPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		//涓ゅ懆鍐�
		if(paramsMap.get("type").equals("2")){
			String date1 = DateUtils.formatDate(new Date());
			String date2 = DateUtils.formatDate(DateUtils.addDay(new Date(),14));
			paramsMap.put("beginTime", date1 + " 00:00:00");
			paramsMap.put("endTime", date2 + " 23:59:59");
		}
		//涓や釜鏈堝唴搴旇繕
		if(paramsMap.get("type").equals("3")){
			String nowDate1 = DateUtils.formatDate(new Date());
			String nowDate2 = DateUtils.formatDate(DateUtils.addMonth(new Date(),2));
			paramsMap.put("beginTime", nowDate1 + " 00:00:00");
			paramsMap.put("endTime", nowDate2 + " 23:59:59");
		}
		
		//涓変釜鏈堝唴搴旇繕
		if(paramsMap.get("type").equals("4")){
			String nowDate1 = DateUtils.formatDate(new Date());
			String nowDate2 = DateUtils.formatDate(DateUtils.addMonth(new Date(),3));
			paramsMap.put("beginTime", nowDate1 + " 00:00:00");
			paramsMap.put("endTime", nowDate2 + " 23:59:59");
		}
		paramsMap.put("repaymentStatus",BorrowRepayment.REPAYMENT_STATUS_REPAYING.toString());
		PageModel pageModel = borrowRepaymentService.getRepayments(paramsMap);
		SpringUtils.renderJson(response, pageModel);
	}
	
	@RequestMapping("getCountByMonth.do")
	public void getCountByMonth(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		PageModel pageModel = reportService.getReportByMonth(paramsMap);
		SpringUtils.renderJson(response, pageModel);
	}

}
