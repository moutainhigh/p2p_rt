package com.rbao.east.controller.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.rbao.east.controller.BaseController;
import com.rbao.east.dao.ExperienceGoldDao;
import com.rbao.east.entity.AccountCash;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.AccountCashService;
import com.rbao.east.service.AccountRechargeService;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.OperatorLogService;
import com.rbao.east.service.QuickBorrowService;
import com.rbao.east.service.ReportService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DecimalUtils;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;

/**
 * 统计
 * @author syl
 *
 */
@Controller
@RequestMapping("summary/")
public class SummaryController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(UserAccountController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private AccountCashService accountCashService;
	
	@Autowired
	private AccountRechargeService accountRechargeService;
	
	@Autowired
	private BorrowService borrowService;
	
	@Autowired
	private BorrowTenderService borrowTenderService;
	
	@Autowired
	private BorrowTransferService borrowTransferService;
	
	@Autowired
	private BorrowRepaymentService borrowRepaymentService;
	
	@Autowired
	private QuickBorrowService quickBorrowService;
	
	@Autowired
	private SysModuleService moduleService;
	
	@Autowired
	private OperatorLogService operatorLogService;
	@Autowired
	private ExperienceGoldDao experienceGoldDao;
	
	
	private String dateYear;
	
	/**
	 * 信息统计
	 * @param response
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "message")
	public void message(HttpServletResponse response) {
		
		Integer loginCount = operatorLogService.summaryLoginCount();					//获取今日登陆用户数
		/* List<Map<String, Object>> registercounts = userService.registerSummary();		
		Integer	registerAllCount =0;                                                    //累计注册用户数
		if(registercounts!=null){
			registerAllCount=Integer.parseInt(registercounts.get(0).get("total_add").toString());
		}*/
		Integer	registerAllCount =0;
		registerAllCount = userService.getAllUsers();
		Integer registerCount = userService.summaryRegisterCount(new Date());			//今日注册用户数
		
		BigDecimal rechargeAll = reportService.summaryRecharge();						//累计用户总充值
		BigDecimal cashAll = reportService.summaryCash();								//累计用户总提现
		BigDecimal platformEarnings = reportService.summaryPlatformEarnings();			//累计平台总收益
		BigDecimal userEarnings = reportService.summaryUserEarnings();					//累计用户总收益
		BigDecimal allExperienceGlod = (BigDecimal) experienceGoldDao.getObject("getAllExperienceGoldByParam", null);
		BigDecimal cash = accountCashService.summaryCash(AccountCash.cashSuccess, true);//今日用户总提现
		BigDecimal recharge = accountRechargeService.summaryRecharge();					//今日用户总充值
		
		//有体验金利息时
		if (allExperienceGlod != null) {
			userEarnings = userEarnings.add(allExperienceGlod);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginCount", loginCount);
		map.put("registerCount", registerCount);
		map.put("registerAllCount", registerAllCount);
		map.put("rechargeAll", rechargeAll);
		map.put("cashAll", cashAll);
		map.put("platformEarnings", platformEarnings);
		map.put("userEarnings", userEarnings);
		map.put("cash", cash);
		map.put("recharge", recharge);
		
		
		SpringUtils.renderJson(response, map);
	}
	
	/**
	 * 待办事项
	 * @param response
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "backlog")
	public void backlog(HttpServletResponse response) {
		
		Integer check = borrowService.summaryBacklogCount(Borrow.STATUS_NEW, false);				//待审核的借款项目
		Integer publish = borrowService.summaryBacklogCount(Borrow.STATUS_NEW, true);				//待发布的借款项目
		Integer credit = borrowService.summaryBacklogCount(Borrow.STATUS_FULL, false);				//待放款的借款项目
		Integer dispose = quickBorrowService.summaryDisposeCount();									//待处理的个人借款
		Integer userCheck = userService.summaryNoCheckCount();										//待审核的认证信息
		Integer cashCheck = accountCashService.summaryCashNoCheck();								//提现待审
		BigDecimal cashChechMoney = accountCashService.summaryCash(AccountCash.cashApply, false);	//提现待审金额
		Integer rechargeCheck = accountRechargeService.summaryRechargeNoCheck();					//线下充值待审
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("check", check);
		map.put("publish", publish);
		map.put("credit", credit);
		map.put("dispose", dispose);
		map.put("userCheck", userCheck);
		map.put("cashCheck", cashCheck);
		map.put("cashChechMoney", cashChechMoney);
		map.put("rechargeCheck", rechargeCheck);
		
		SpringUtils.renderJson(response, map);
	}
	
	/**
	 * 平台资金统计
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getPlatformSummary")
	public String getPlatformSummary(HttpServletResponse response, HttpServletRequest request, Model model) {
		
		Map<String, String> requests = this.getParameters(request);
		
		dateYear = requests.get("dateYear");
		
		User user = this.loginAdminUser(request);
		requests.put("userId", user.getId().toString());
		
		if (StringUtils.isEmpty(dateYear)) {
			dateYear = DateUtils.formatDate("yyyy", new Date());
			requests.put("dateYear", dateYear);
		}
		
		List<SysModule> righSubtList = moduleService.getRightGroupList(requests);
		model.addAttribute("righSubtList", righSubtList);
		
		List<Map<String, Object>> platform = reportService.platformSummary(dateYear+"-01-01");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> money_type_array = new ArrayList<String>();
		money_type_array.add("收入");
		money_type_array.add("支出");
		money_type_array.add("盈亏");
		List<String> key_array = new ArrayList<String>();
		key_array.add("in_money");
		key_array.add("out_money");
		key_array.add("profit_loss");
		
		for(int i = 0; i < 3; i++) {
			Map<String, Object> line_map = new HashMap<String, Object>();
			line_map.put("money_type", money_type_array.get(i));
			line_map.put("spring", platform.get(0).get(key_array.get(i)));
			line_map.put("summer", platform.get(1).get(key_array.get(i)));
			line_map.put("autumn", platform.get(2).get(key_array.get(i)));
			line_map.put("winter", platform.get(3).get(key_array.get(i)));
			BigDecimal total = (BigDecimal)platform.get(0).get(key_array.get(i));
			total = total.add((BigDecimal)platform.get(1).get(key_array.get(i)));
			total = total.add((BigDecimal)platform.get(2).get(key_array.get(i)));
			total = total.add((BigDecimal)platform.get(3).get(key_array.get(i)));
			line_map.put("total", DecimalUtils.getTwoDecimal(total));
			list.add(line_map);
		}
		model.addAttribute("list", list);
		
		model.addAttribute("params", requests);
		return "report/platformSummary";
	}
	
	/**
	 * 平台资金统计-导出Excel
	 * @param response
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"toPlatformExcel")
	public void toPlatformExcel(HttpServletResponse response, HttpServletRequest request) {
		try {
			String[] titles = {"类别", "一个度", "二季度", "三季度", "四季度", "合计"};
			
			dateYear = request.getParameter("dateYear");
			List<Map<String, Object>> platform = reportService.platformSummary(dateYear+"-01-01");
			List<String[]> contents = new ArrayList<String[]>();
			
			List<String> money_type_array = new ArrayList<String>();
			money_type_array.add("收入");
			money_type_array.add("支出");
			money_type_array.add("盈亏");
			
			List<String> key_array = new ArrayList<String>();
			key_array.add("in_money");
			key_array.add("out_money");
			key_array.add("profit_loss");
			
			for(int i = 0; i < 3; i++) {
				String[] line_coutents = new String[6];
				line_coutents[0] = money_type_array.get(i);
				line_coutents[1] = platform.get(0).get(key_array.get(i)).toString();
				line_coutents[2] = platform.get(1).get(key_array.get(i)).toString();
				line_coutents[3] = platform.get(2).get(key_array.get(i)).toString();
				line_coutents[4] = platform.get(3).get(key_array.get(i)).toString();
				
				BigDecimal total = (BigDecimal)platform.get(0).get(key_array.get(i));
				total = total.add((BigDecimal)platform.get(1).get(key_array.get(i)));
				total = total.add((BigDecimal)platform.get(2).get(key_array.get(i)));
				total = total.add((BigDecimal)platform.get(3).get(key_array.get(i)));
				line_coutents[5] = DecimalUtils.getTwoDecimal(total);
				
				contents.add(line_coutents);
			}
			
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename=" + new String(("report-platform-data"+".xls").getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "平台资金统计", titles, contents);
		} catch (Exception e) {
			logger.error("平台资金统计-导出Excel", e);
		}
	}
	
	/**
	 * 用户注册统计
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getRegisterSummary")
	public String getRegisterSummary(HttpServletResponse response, HttpServletRequest request, Model model) {
		Map<String, String> requests = this.getParameters(request);
		
		User user = this.loginAdminUser(request);
		requests.put("userId", user.getId().toString());
		
		List<SysModule> righSubtList = moduleService.getRightGroupList(requests);
		model.addAttribute("righSubtList", righSubtList);
		
		model.addAttribute("list", userService.registerSummary());
		
		model.addAttribute("params", requests);
		return "report/registerSummary";
	}
	
	/**
	 * 用户注册统计折线图
	 * @param response
	 */
	@RequestMapping("getRegisterChartData")
	public void getRegisterChartData(HttpServletResponse response) {
		Map<String, Object> chart_map = new HashMap<String, Object>();
		
		List<Map<String, Object>> register = userService.registerSummary();
		
		String[] xaxis = new String[register.size()];
		
		BigDecimal[] day_add = new BigDecimal[register.size()];
		
		BigDecimal[] total_add = new BigDecimal[register.size()];
		
		for(int i = 0; i < register.size(); i++) {
			xaxis[i] = register.get(i).get("province").toString();
			
			day_add[i] = (BigDecimal) register.get(i).get("day_add");
			
			total_add[i] = (BigDecimal) register.get(i).get("total_add");
		}
		
		List<Map<String, Object>> series = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("name", "新增（人）");
		datas.put("data", day_add);
		series.add(datas);
		datas = new HashMap<String, Object>();
		datas.put("name", "累计（人）");
		datas.put("data", total_add);
		series.add(datas);
		
		chart_map.put("xaxis", xaxis);
		
		chart_map.put("series", series);
		
		SpringUtils.renderJson(response, chart_map);
	}
	
	/**
	 * 用户注册统计-导出Excel
	 * @param response
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"toRegisterExcel")
	public void toRegisterExcel(HttpServletResponse response) {
		try {
			String[] titles = {"省份", "新增（人）", "累计（人）"};
			
			List<Map<String, Object>> register = userService.registerSummary();
			
			List<String[]> contents = new ArrayList<String[]>();
			
			for(Map<String, Object> map : register) {
				String[] str = new String[titles.length];
				
				str[0] = map.get("province").toString();
				str[1] = map.get("day_add").toString();
				str[2] = map.get("total_add").toString();
				
				contents.add(str);
			}
			
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename=" + new String(("register-data"+".xls").getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "用户注册统计", titles, contents);
		} catch (Exception e) {
			logger.error("用户注册统计-导出Excel", e);
		}
	}
	
	/**
	 * 投资统计
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getInvestSummary")
	public String getInvestSummary(HttpServletResponse response, HttpServletRequest request, Model model) {
		
		Map<String, String> requests = this.getParameters(request);
		
		User user = this.loginAdminUser(request);
		requests.put("userId", user.getId().toString());
		
		List<SysModule> righSubtList = moduleService.getRightGroupList(requests);
		model.addAttribute("righSubtList", righSubtList);
		
		model.addAttribute("list", borrowTenderService.investSummary(requests));
		
		model.addAttribute("params", requests);
		
		request.getSession(true).setAttribute("queryParams", requests);  
		
		return "borrow/investSummary";
	}
	
	/**
	 * 投资统计-导出Excel
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(Constants.PRE_PATH_EDIT + "toInvestExcel")
	public void toInvestExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
			
			List<Map<String, Object>> invest = borrowTenderService.investAllSummary(params);
			
			String[] titles = {"投资人账号", "投资人姓名", "投资人手机号","投资总额", "利息总额", "待还本金", "待还利息", "投标方式", "投资时间", "投资状态", "借款人账号", "借款标题", "借款金额","预期年化收益率"};
			
			List<String[]> contents = new ArrayList<String[]>();
			
			for(Map<String, Object> map : invest) {
				String[] str = new String[titles.length];
				
				str[0] = map.get("user_accounts") == null ? "" : map.get("user_accounts").toString();
				str[1] = map.get("user_realname") == null ? "" : map.get("user_realname").toString();
				str[2] = map.get("user_phone") == null ? "" : map.get("user_phone").toString();
				str[3] = map.get("tender_amount") == null ? "" : map.get("tender_amount").toString();
				str[4] = map.get("interest_amount") == null ? "" : map.get("interest_amount").toString() ;
				str[5] = map.get("staying_amount") == null ? "" : map.get("staying_amount").toString();
				str[6] = map.get("staying_interest") == null ? "" : map.get("staying_interest").toString();
				str[7] = map.get("tender_type") == null ? "" : BorrowTender.ALL_TENDER_TYPE.get((Integer)map.get("tender_type"));
				str[8] = map.get("tender_addtime") == null ? "" : DateUtils.parseToDateTimeStr((Date)map.get("tender_addtime"));
				str[9] = map.get("tender_status") == null ? "" : BorrowTender.ALL_STATUS.get((Integer)map.get("tender_status"));
				str[10] = map.get("user_account") == null ? "" : map.get("user_account").toString();
				str[11] = map.get("borrow_title") == null ? "" : map.get("borrow_title").toString();
				str[12] = map.get("borrow_sum") == null ? "" : map.get("borrow_sum").toString();
				str[13] = map.get("annual_interest_rate") == null ? "" : map.get("annual_interest_rate").toString()+"%";
				contents.add(str);
			}
		
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流     
			response.setHeader("Content-disposition", "attachment; filename=" + new String("inverst-data.xls".getBytes("UTF-8"),"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型   
			ExcelUtil.buildExcel(os,"投资统计",titles,contents);
		} catch (IOException e) {
			logger.error("投资统计-导出Excel失败",e);
		}
	}
	
	/**
	 * 项目投资统计
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getProjectSummary")
	public String getProjectSummary(HttpServletResponse response, HttpServletRequest request, Model model) {
		
		Map<String, String> requests = this.getParameters(request);
		
		User user = this.loginAdminUser(request);
		requests.put("userId", user.getId().toString());
		requests.put("borrow_status", "(2, 4, 5, 6, 10, 11)");
		
		List<SysModule> righSubtList = moduleService.getRightGroupList(requests);
		model.addAttribute("righSubtList", righSubtList);
		
		model.addAttribute("list", borrowService.getProjectSummary(requests));
		
		model.addAttribute("params", requests);
		
		request.getSession(true).setAttribute("queryParams", requests);  
		
		return "borrow/projectSummary";
	}
	
	/**
	 * 项目投资统计 - 导出Excel
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(Constants.PRE_PATH_EDIT + "toProjectExcel")
	public void toProjectExcel(HttpServletRequest request, HttpServletResponse response) {
		try {

			String borrowId = request.getParameter("borrow_id");
			Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
			params.put("borrow_id", borrowId);
			
			List<Map<String, Object>> invest = borrowTenderService.investAllSummary(params);
			List<Map<String, Object>> projectSummary = borrowService.getAllProjectSummary(params);
			
			List<Map<Integer, String>> subTitles = null;
			if (StringUtils.isNotEmpty(borrowId)) {
				Borrow borrow = borrowService.getBorrowById(Integer.parseInt(borrowId));
				
				//副标题
				subTitles = new ArrayList<>();
				Map<Integer, String> subTitle = new HashMap<Integer, String>();
				subTitle.put(0, "借款标题");
				subTitle.put(1, borrow.getBorrowTitle());
				subTitle.put(3, "借款人帐号");
				subTitle.put(4, borrow.getUser().getUserAccount());
				subTitle.put(6, "借款金额");
				subTitle.put(7, borrow.getBorrowSum() + "");
				subTitle.put(8, "预期年化收益率");
				subTitle.put(9, borrow.getAnnualInterestRate() + "%");
				subTitles.add(subTitle);
				subTitle = new HashMap<Integer, String>();
				subTitle.put(0, "借款期限");
				subTitle.put(1, borrow.getBorrowTimeLimit() + (borrow.getIsDay() == Borrow.IS_DAY_Y ? "天" : "个月"));
				subTitle.put(3, "募集齐时间");
				subTitle.put(4, DateUtils.convertDateToString(borrow.getVerifyReviewTime()));
				subTitle.put(6, "到期还款日");
				subTitle.put(7, DateUtils.convertDateToString(borrow.getRepaymentTime()));
				subTitles.add(subTitle);
			}
			
			String[] titles = {"投资人账号", "投资人姓名", "投资人手机号","投资总额", "利息总额", "待还本金", "待还利息", "投标方式", "投资时间", "投资状态", "借款人账号", "借款标题", "借款金额","预期年化收益率","借款期限","满标日期","到期还款日"};
			
			List<String[]> contents = new ArrayList<String[]>();
			
			for(Map<String, Object> map : invest) {
				String[] str = new String[titles.length];
				
				str[0] = map.get("user_accounts") == null ? "" : map.get("user_accounts").toString();
				str[1] = map.get("user_realname") == null ? "" : map.get("user_realname").toString();
				str[2] = map.get("user_phone") == null ? "" : map.get("user_phone").toString();
				str[3] = map.get("tender_amount") == null ? "" : map.get("tender_amount").toString();
				str[4] = map.get("interest_amount") == null ? "" : map.get("interest_amount").toString() ;
				str[5] = map.get("staying_amount") == null ? "" : map.get("staying_amount").toString();
				str[6] = map.get("staying_interest") == null ? "" : map.get("staying_interest").toString();
				str[7] = map.get("tender_type") == null ? "" : BorrowTender.ALL_TENDER_TYPE.get((Integer)map.get("tender_type"));
				str[8] = map.get("tender_addtime") == null ? "" : DateUtils.parseToDateTimeStr((Date)map.get("tender_addtime"));
				str[9] = map.get("tender_status") == null ? "" : BorrowTender.ALL_STATUS.get((Integer)map.get("tender_status"));
				str[10] = map.get("user_account") == null ? "" : map.get("user_account").toString();
				str[11] = map.get("borrow_title") == null ? "" : map.get("borrow_title").toString();
				str[12] = map.get("borrow_sum") == null ? "" : map.get("borrow_sum").toString();
				str[13] = map.get("annual_interest_rate") == null ? "" : map.get("annual_interest_rate").toString()+"%";
				for(Map<String, Object> project : projectSummary) {
					if((project.get("id").toString()).equals(map.get("borrow_id").toString())){
						str[14] = project.get("borrow_time_limit") == null ? "" : project.get("borrow_time_limit").toString()+project.get("day_type").toString();
						str[15] = project.get("verify_review_time") == null ? "" : DateUtils.parseToDateTimeStr((Date)project.get("verify_review_time"));
						str[16] = project.get("repayment_time") == null ? "" : DateUtils.parseToDateTimeStr((Date)project.get("repayment_time"));
						contents.add(str);
					}
				}
			}
		
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流     
			response.setHeader("Content-disposition", "attachment; filename=" + new String("project-data.xls".getBytes("UTF-8"),"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型   
			ExcelUtil.buildExcel(os,"投资统计", subTitles, titles,contents);
		} catch (IOException e) {
			logger.error("投资统计-导出Excel失败",e);
		}
	}
	
	/**
	 * 债权转让统计
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getTransferSummary")
	public String getTransferSummary(HttpServletResponse response, HttpServletRequest request, Model model) {
		
		Map<String, String> requests = this.getParameters(request);
		
		User user = this.loginAdminUser(request);
		requests.put("userId", user.getId().toString());
		
		List<SysModule> righSubtList = moduleService.getRightGroupList(requests);
		model.addAttribute("righSubtList", righSubtList);
		
		model.addAttribute("list", borrowTransferService.transferSummary(requests));
		
		model.addAttribute("params", requests);
		
		request.getSession(true).setAttribute("queryParams", requests);  
		
		return "borrowTransfer/transferSummary";
	}
	
	/**
	 * 债权转让统计-导出Excel
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(Constants.PRE_PATH_EDIT + "toTransferExcel")
	public void toTransferExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
			
			List<Map<String, Object>> transfer = borrowTransferService.transferAllSummary(params);
			
			String[] titles = {"投资人账号", "投资人姓名", "投资总额", "利息总额", "待还本金", "待还利息", "投标方式", "投资时间", "投资状态", "借款人账号", "借款标题", "借款金额"};
			
			List<String[]> contents = new ArrayList<String[]>();
			
			for(Map<String, Object> map : transfer) {
				String[] str = new String[titles.length];
				
				str[0] = map.get("borrow_title") == null ? "" : map.get("borrow_title").toString();
				str[1] = map.get("surplus_count") == null ? "" : map.get("surplus_count").toString();
				str[2] = map.get("borrow_user_account") == null ? "" : map.get("borrow_user_account").toString();
				str[3] = map.get("borrow_user_realname") == null ? "" : map.get("borrow_user_realname").toString();
				str[4] = map.get("create_time") == null ? "" : DateUtils.parseToDateTimeStr((Date)map.get("create_time"));
				str[5] = map.get("repossessed_interest") == null ? "" : map.get("repossessed_interest").toString();
				str[6] = map.get("worth") == null ? "" : map.get("worth").toString();
				str[7] = map.get("last_auction_money") == null ? "" : map.get("last_auction_money").toString();
				str[8] = map.get("transfer_fee") == null ? "" : map.get("transfer_fee").toString();
				str[9] = map.get("profit_loss") == null ? "" : map.get("profit_loss").toString();
				str[10] = map.get("user_account") == null ? "" : map.get("user_account").toString();
				str[11] = map.get("user_realname") == null ? "" : map.get("user_realname").toString();
				str[12] = map.get("end_time") == null ? "" : DateUtils.parseToDateTimeStr((Date)map.get("end_time"));
				
				contents.add(str);
			}
		
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流     
			response.setHeader("Content-disposition", "attachment; filename=" + new String("transfer-data.xls".getBytes("UTF-8"),"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型   
			ExcelUtil.buildExcel(os,"债权转让统计",titles,contents);
		} catch (Exception e) {
			logger.error("债权转让统计-导出Excel失败",e);
		}
	}
	
	/**
	 * 垫付还款统计
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getRepaymentSummary")
	public String getRepaymentSummary(HttpServletResponse response, HttpServletRequest request, Model model) {
		
		Map<String, String> requests = this.getParameters(request);
		
		User user = this.loginAdminUser(request);
		requests.put("userId", user.getId().toString());
		
		List<SysModule> righSubtList = moduleService.getRightGroupList(requests);
		model.addAttribute("righSubtList", righSubtList);
		
		model.addAttribute("list", borrowRepaymentService.reparmentSummary(requests));
		
		model.addAttribute("params", requests);
		
		request.getSession(true).setAttribute("queryParams", requests);  
		
		return "borrow/repaymentSummary";
	}
	
	/**
	 * 垫付还款统计-导出Excel
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(Constants.PRE_PATH_EDIT + "toRepaymentExcel")
	public void toRepaymentExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
			
			List<Map<String, Object>> transfer = borrowRepaymentService.reparmentAllSummary(params);
			
			String[] titles = {"借款人账号", "借款标题", "垫付人账号", "垫付金额", "期数", "预计垫付时间", "实际垫付时间"};
			
			List<String[]> contents = new ArrayList<String[]>();
			
			for(Map<String, Object> map : transfer) {
				String[] str = new String[titles.length];
				
				str[0] = map.get("user_account") == null ? "" : map.get("user_account").toString();
				str[1] = map.get("borrow_title") == null ? "" : map.get("borrow_title").toString();
				str[2] = map.get("users") == null ? "" : map.get("users").toString();
				str[3] = map.get("repayment_realamount") == null ? "" : map.get("repayment_realamount").toString();
				str[4] = map.get("period") == null ? "" : map.get("period").toString();
				str[5] = map.get("repayment_time") == null ? "" : DateUtils.parseToDateTimeStr((Date)map.get("repayment_time"));
				str[6] = map.get("repayment_paidtime") == null ? "" : DateUtils.parseToDateTimeStr((Date)map.get("repayment_paidtime"));
				
				contents.add(str);
			}
		
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流     
			response.setHeader("Content-disposition", "attachment; filename=" + new String("repayment-data.xls".getBytes("UTF-8"),"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型   
			ExcelUtil.buildExcel(os,"垫付还款统计",titles,contents);
		} catch (Exception e) {
			logger.error("垫付还款统计-导出Excel失败",e);
		}
	}
	
	/**
	 * 充值提现数据统计
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getRechargeCashSummary")
	public String getRechargeCashSummary(HttpServletResponse response, HttpServletRequest request, Model model) {
		
		Map<String, String> requests = this.getParameters(request);
		
		dateYear = requests.get("dateYear");
		
		User user = this.loginAdminUser(request);
		requests.put("userId", user.getId().toString());
		
		if (StringUtils.isEmpty(dateYear)) {
			dateYear = DateUtils.formatDate("yyyy", new Date());
			requests.put("dateYear", dateYear);
		}
		
		List<SysModule> righSubtList = moduleService.getRightGroupList(requests);
		model.addAttribute("righSubtList", righSubtList);
		
//		List<Map<String, Object>> recharge_cash = reportService.rechargeCashSummary(dateYear + "-01-01");
		model.addAttribute("list", reportService.rechargeCashSummary(dateYear + "-01-01"));
		
		model.addAttribute("params", requests);
		return "report/rechargeCashSummary";
	}
	
	/**
	 * 充值提现数据统计-导出Excel
	 * @param response
	 * @param request
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"toRechargeCashExcel")
	public void toRechargeCashExcel(HttpServletResponse response, HttpServletRequest request) {
		try {
			String[] titles = {"月份", "充值（元）", "提现（元）", "充值笔数", "提现笔数"};
			
			dateYear = request.getParameter("dateYear");
			
			List<Map<String, Object>> recharge_cash = reportService.rechargeCashSummary(dateYear + "-01-01");
			
			List<String[]> contents = new ArrayList<String[]>();
			for(Map<String, Object> map : recharge_cash) {
				String[] line_contents = new String[5];
				
				line_contents[0] = map.get("month") == null ? "" : map.get("month").toString();
				line_contents[1] = map.get("recharge") == null ? "" : map.get("recharge").toString();
				line_contents[2] = map.get("cash") == null ? "" : map.get("cash").toString();
				line_contents[3] = map.get("recharge_count") == null ? "" : map.get("recharge_count").toString();
				line_contents[4] = map.get("cash_count") == null ? "" : map.get("cash_count").toString();
				
				contents.add(line_contents);
			}
			
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename=" + new String(("report-RechargeCash-data"+".xls").getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "充值提现数据统计", titles, contents);
		} catch (Exception e) {
			logger.error("充值提现数据统计-导出Excel", e);
		}
	}
	
	/**
	 * 充值提现金额数据折线图
	 * @param response
	 * @param request
	 */
	@RequestMapping("getRechargeCashMoneyData")
	public void getRechargeCashMoneyData(HttpServletResponse response, HttpServletRequest request) {
		
		Map<String, Object> chart_map = new HashMap<String, Object>();
		String[] xaxis = new String[12];
		
		Map<String, String> recharge_cash_map = new HashMap<String, String>();
		recharge_cash_map.put("recharge", "充值");
		recharge_cash_map.put("cash", "提现");
		
		dateYear = request.getParameter("dateYear");
		
		List<Map<String, Object>> recharge_cash = reportService.rechargeCashSummary(dateYear + "-01-01");
		
		List<Map<String, Object>> series_list = new ArrayList<Map<String,Object>>();
		for(String key : recharge_cash_map.keySet()) {
			Map<String, Object> series_map = new HashMap<String, Object>();
			series_map.put("name", recharge_cash_map.get(key));
			
			BigDecimal[] datas = new BigDecimal[12];
			for(int i = 0; i < recharge_cash.size(); i++) {
				String month = recharge_cash.get(i).get("month").toString();
				xaxis[i] = month.substring(0, month.length() - 1);
				
				datas[i] = (BigDecimal) recharge_cash.get(i).get(key);
			}
			series_map.put("data", datas);
			series_list.add(series_map);
		}
		
		chart_map.put("xaxis", xaxis);
		chart_map.put("series", series_list);
		
		SpringUtils.renderJson(response, chart_map);
	}
	
	/**
	 * 充值提现笔数数据折线图
	 * @param response
	 * @param request
	 */
	@RequestMapping("getRechargeCashCountData")
	public void getRechargeCashCountData(HttpServletResponse response, HttpServletRequest request) {
		
		Map<String, Object> chart_map = new HashMap<String, Object>();
		String[] xaxis = new String[12];
		
		Map<String, String> recharge_cash_map = new HashMap<String, String>();
		recharge_cash_map.put("recharge_count", "充值笔数");
		recharge_cash_map.put("cash_count", "提现笔数");
		
		dateYear = request.getParameter("dateYear");
		
		List<Map<String, Object>> recharge_cash = reportService.rechargeCashSummary(dateYear + "-01-01");
		
		List<Map<String, Object>> series_list = new ArrayList<Map<String,Object>>();
		for(String key : recharge_cash_map.keySet()) {
			Map<String, Object> series_map = new HashMap<String, Object>();
			series_map.put("name", recharge_cash_map.get(key));
			
			BigDecimal[] datas = new BigDecimal[12];
			for(int i = 0; i < recharge_cash.size(); i++) {
				String month = recharge_cash.get(i).get("month").toString();
				xaxis[i] = month.substring(0, month.length() - 1);
				
				datas[i] = (BigDecimal) recharge_cash.get(i).get(key);
			}
			series_map.put("data", datas);
			series_list.add(series_map);
		}
		
		chart_map.put("xaxis", xaxis);
		chart_map.put("series", series_list);
		
		SpringUtils.renderJson(response, chart_map);
	}
	
	/**
	 * 平台资金折线统计图
	 * @param response
	 * @param request
	 */
	@RequestMapping("getPlatformChartData")
	public void getPlatformChartData(HttpServletResponse response, HttpServletRequest request) {
		
		Map<String, Object> chart_map = new HashMap<String, Object>();
		String[] xaxis = {"一季度", "二季度", "三季度", "四季度"};
		chart_map.put("xaxis", xaxis);
		
		dateYear = request.getParameter("dateYear");
		
		List<Map<String, Object>> platform = reportService.platformSummary(dateYear+"-01-01");
		
		List<String> money_type_array = new ArrayList<String>();
		money_type_array.add("收入");
		money_type_array.add("支出");
		money_type_array.add("盈亏");
		
		List<String> key_array = new ArrayList<String>();
		key_array.add("in_money");
		key_array.add("out_money");
		key_array.add("profit_loss");
		
		List<Map<String, Object>> series_list = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < 3; i++) {
			Map<String, Object> series_map = new HashMap<String, Object>();
			series_map.put("name", money_type_array.get(i));
			
			BigDecimal[] datas = new BigDecimal[4];
			datas[0] = (BigDecimal) platform.get(0).get(key_array.get(i));
			datas[1] = (BigDecimal) platform.get(1).get(key_array.get(i));
			datas[2] = (BigDecimal) platform.get(2).get(key_array.get(i));
			datas[3] = (BigDecimal) platform.get(3).get(key_array.get(i));
			series_map.put("data", datas);
			
			series_list.add(series_map);
		}
		
		chart_map.put("series", series_list);
		
		SpringUtils.renderJson(response, chart_map);
	}
	
	/**
	 * 平台资金折线柱状图
	 * @param response
	 * @param request
	 */
	@RequestMapping("getPlatformHistData")
	public void getPlatformHistData(HttpServletResponse response, HttpServletRequest request) {
		
		String[] xaxis = {"借款手续费", "利息管理费", "提现手续费", "vip管理费", "充值手续费", "投标奖励", "线下充值奖励", "续投奖励", "推荐奖励", "注册奖励"};
		String[] platform_key = {"borrow_fee", "interest_fee", "cash_fee", "vip_fee", "recharge_fee", "tender_reward", "recharge_offline_reward", "continue_reward", "recommend_reward", "register_reward"};
		
		dateYear = request.getParameter("dateYear");
		
		Map<String, Object> platform = reportService.platformHistSummary(dateYear+"-01-01");
		
		List<Map<String, Object>> series_list = new ArrayList<Map<String,Object>>();
		
		for(int i = 0; i < xaxis.length; i++) {
			BigDecimal[] datas = new BigDecimal[1];
			Map<String, Object> series = new HashMap<String, Object>();
			series.put("name", xaxis[i]);
			datas[0] = (BigDecimal) platform.get(platform_key[i]);
			series.put("data", datas);
			
			series_list.add(series);
		}
		
		SpringUtils.renderJson(response, series_list);
		
	}
	
	/**
	 * 成交金额同比增长柱状图
	 * @param response
	 * @param request
	 */
	@RequestMapping("getBargainMoneyData")
	public void getBargainMoneyData(HttpServletResponse response, HttpServletRequest request) {
		
		Map<String, Object> hist_map = new HashMap<String, Object>();
		String[] xaxis = new String[12];
		
		dateYear = request.getParameter("dateYear");
		
		List<Map<String, Object>> bargain = borrowTenderService.bargainSummary(dateYear + "-01-01");
		
		Integer pre_year = Integer.valueOf(dateYear) - 1;
		List<Map<String, Object>> bargain_pre = borrowTenderService.bargainSummary(pre_year.toString() + "-01-01");
		
		List<Map<String, Object>> series_list = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> series_map = new HashMap<String, Object>();
		BigDecimal[] datas = new BigDecimal[12];
		for(int i = 0; i < bargain.size(); i++) {
			String month = bargain.get(i).get("month").toString();
			xaxis[i] = month.substring(0, month.length() - 1);
			
			datas[i] = (BigDecimal) bargain.get(i).get("money");
		}
		hist_map.put("xaxis", xaxis);
		
		series_map.put("name", dateYear + "年度");
		series_map.put("data", datas);
		series_list.add(series_map);
		
		datas = new BigDecimal[12];
		series_map = new HashMap<String, Object>();
		for(int i = 0; i < bargain_pre.size(); i++) {
			
			datas[i] = (BigDecimal) bargain_pre.get(i).get("money");
		}
		series_map.put("name", pre_year.toString() + "年度");
		series_map.put("data", datas);
		series_list.add(series_map);
		
		hist_map.put("series", series_list);
		
		SpringUtils.renderJson(response, hist_map);
	}
	
	/**
	 * 成交笔数同比增长柱状图
	 * @param response
	 * @param request
	 */
	@RequestMapping("getBargainCountData")
	public void getBargainCountData(HttpServletResponse response, HttpServletRequest request) {
		
		Map<String, Object> hist_map = new HashMap<String, Object>();
		String[] xaxis = new String[12];
		
		dateYear = request.getParameter("dateYear");
		
		List<Map<String, Object>> bargain = borrowTenderService.bargainSummary(dateYear + "-01-01");
		
		Integer pre_year = Integer.valueOf(dateYear) - 1;
		List<Map<String, Object>> bargain_pre = borrowTenderService.bargainSummary(pre_year.toString() + "-01-01");
		
		List<Map<String, Object>> series_list = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> series_map = new HashMap<String, Object>();
		BigDecimal[] datas = new BigDecimal[12];
		for(int i = 0; i < bargain.size(); i++) {
			String month = bargain.get(i).get("month").toString();
			xaxis[i] = month.substring(0, month.length() - 1);
			
			datas[i] = (BigDecimal) bargain.get(i).get("count");
		}
		hist_map.put("xaxis", xaxis);
		
		series_map.put("name", dateYear + "年度");
		series_map.put("data", datas);
		series_list.add(series_map);
		
		datas = new BigDecimal[12];
		series_map = new HashMap<String, Object>();
		for(int i = 0; i < bargain_pre.size(); i++) {
			datas[i] = (BigDecimal) bargain_pre.get(i).get("count");
		}
		series_map.put("name", pre_year.toString() + "年度");
		series_map.put("data", datas);
		series_list.add(series_map);
		
		hist_map.put("series", series_list);
		
		SpringUtils.renderJson(response, hist_map);
	}
	
	/**
	 * 借款数据统计（按type分类）
	 * @param response
	 * @param request
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowSummaryByWhat")
	public String getBorrowSummaryByWhat(HttpServletResponse response, HttpServletRequest request, Model model, String type) {
		
		Map<String, String> requests = this.getParameters(request);
		
		dateYear = requests.get("dateYear");
		
		User user = this.loginAdminUser(request);
		requests.put("userId", user.getId().toString());
		
		if (StringUtils.isEmpty(dateYear)) {
			dateYear = DateUtils.formatDate("yyyy", new Date());
			requests.put("dateYear", dateYear);
		}
		
		List<SysModule> righSubtList = moduleService.getRightGroupList(requests);
		model.addAttribute("righSubtList", righSubtList);
		
		List<Map<String, Object>> borrow = null;
		List<Map<String,Object>> borrows=null;//存新手标
		if(type.equalsIgnoreCase("province")) {
			borrow = borrowService.borrowSummaryByProvince(dateYear + "-01-01");
		} else if(type.equalsIgnoreCase("date")) {
			borrow = borrowService.borrowSummaryByDate(dateYear + "-01-01");
			borrows = borrowService.borrowSummaryByType(dateYear + "-01-01");
			model.addAttribute("news", borrows.get(0));
		} else if(type.equalsIgnoreCase("type")) {
			borrow = borrowService.borrowSummaryByType(dateYear + "-01-01");
		}
		
		model.addAttribute("list", borrow);
		model.addAttribute("params", requests);
		
		if(type.equalsIgnoreCase("province")) {
			return "borrow/borrowByProvinceSummary";
		} else if(type.equalsIgnoreCase("date")) {
			return "borrow/borrowByDateSummary";
		} else if(type.equalsIgnoreCase("type")) {
			return "borrow/borrowByTypeSummary";
		} else {
			return null;
		}
	}
	
	/**
	 * 借款数据统计（按type分类）-导出Excel
	 * @param response
	 * @param request
	 * @param model
	 * @param type
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"toBorrowExcel")
	public void toBorrowExcel(HttpServletResponse response, HttpServletRequest request, Model model, String type) {
		try {
			
			Map<String, String[]> type_map = new HashMap<String, String[]>();
			type_map.put("type", new String[]{"类型", "笔数", "金额（元）"});
			type_map.put("date", new String[]{"期限（月）", "笔数", "金额（元）"});
			type_map.put("province", new String[]{"区域", "笔数", "金额（元）"});
			
			Map<String, String[]> key_map = new HashMap<String, String[]>();
			key_map.put("province", new String[]{"province", "count", "money"});
			key_map.put("date", new String[]{"borrow_limit", "count", "money"});
			key_map.put("type", new String[]{"name", "count", "money"});
			
			dateYear = request.getParameter("dateYear");
			
			List<Map<String, Object>> borrow = null;
			
			Map<String,Object> news=null;
			if(type.equalsIgnoreCase("province")) {
				borrow = borrowService.borrowSummaryByProvince(dateYear + "-01-01");
			} else if(type.equalsIgnoreCase("date")) {
				borrow = borrowService.borrowSummaryByDate(dateYear + "-01-01");
				
				news = borrowService.borrowSummaryByType(dateYear + "-01-01").get(0);
				
			} else if(type.equalsIgnoreCase("type")) {
				borrow = borrowService.borrowSummaryByType(dateYear + "-01-01");
			}
			
			List<String[]> contents = new ArrayList<String[]>();
			String[] key = key_map.get(type);
			int index=0;
			for(Map<String, Object> map : borrow) {
				String[] content = new String[key.length];
				
				for(int i = 0; i < key.length; i++) {
					content[i] = map.get(key[i]) == null ? "" : map.get(key[i]).toString();
				}
				
				if(index==0&&news!=null){
					Map<String, Object> map2=borrow.get(7);
					content[1]=(Integer.parseInt(content[1])+Integer.parseInt(map2.get("count").toString())+Integer.parseInt(news.get("count").toString()))+"";
					
					BigDecimal totalMoney=new BigDecimal(map2.get("money").toString());
					BigDecimal newMoney=new BigDecimal(news.get("money").toString());
					BigDecimal oneThreeMoney=new BigDecimal(content[2]);
					
					content[2]=oneThreeMoney.add(newMoney).add(totalMoney).toString();
				}
				
				index++;
				contents.add(content);
			}
			
			
			
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename=" + new String(("borrow-" + type + "-data"+".xls").getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "借款数据统计-按" + type_map.get(type)[0], type_map.get(type), contents);
		} catch (Exception e) {
			logger.error("借款数据统计（按type分类）-导出Excel失败", e);
		}
		
	}
	/**
	 * 借款数据统计饼状图-（按类型）
	 * @param response
	 * @param request
	 */
	@RequestMapping("getBorrowByWhatData")
	public void getBorrowByWhatData(HttpServletResponse response, HttpServletRequest request) {
		
		dateYear = request.getParameter("dateYear");
		
		String type = request.getParameter("type");
		
		Map<String, Object> pie_map = new HashMap<String, Object>();
		pie_map.put("name", "笔数");
		
		List<Object[]> series_list = new ArrayList<Object[]>();
		Object[] series_array = null;
		
		List<Map<String, Object>> borrow = null;
		List<Map<String, Object>> borrows = null;
		if(type.equalsIgnoreCase("province")) {
			borrow = borrowService.borrowSummaryByProvince(dateYear + "-01-01");
			
			for(Map<String, Object> map : borrow) {
				series_array = new Object[2];
				series_array[0] = map.get("province");
				series_array[1] = map.get("count");
				series_list.add(series_array);
			}
		} else if(type.equalsIgnoreCase("date")) {
			int totalCountDuo=0;
			int totalCountNew=0;
			borrow = borrowService.borrowSummaryByDate(dateYear + "-01-01");
			borrows = borrowService.borrowSummaryByType(dateYear + "-01-01");
			for(int i=0;i<borrows.size();i++){
				if(borrows.get(i).get("name").equals("争分夺秒标")){
					totalCountDuo=Integer.parseInt(borrows.get(i).get("count").toString());
				}else if(borrows.get(i).get("name").equals("新手专享标")){
					totalCountNew=Integer.parseInt( borrows.get(i).get("count").toString());
				}
			}
			for(int i=0;i<borrow.size();i++){
				series_array = new Object[2];
				series_array[0] = borrow.get(i).get("borrow_limit");
				if(i==0){
					 int aa = Integer.parseInt(borrow.get(i).get("count").toString());
					series_array[1]=aa+totalCountDuo+totalCountNew;
				}else{
					if(borrow.get(i).get("borrow_limit").equals("25-36")){
						series_array[1]=0;
					}else{
						
						series_array[1]=borrow.get(i).get("count");
					}
				}
				
				series_list.add(series_array);
			}
			/*for(Map<String, Object> map : borrow) {
				series_array = new Object[2];
				series_array[0] = map.get("borrow_limit");
				series_array[1] = map.get("count");
				series_list.add(series_array);
			}*/
		} else if(type.equalsIgnoreCase("type")) {
			borrow = borrowService.borrowSummaryByType(dateYear + "-01-01");
			
			for(Map<String, Object> map : borrow) {
				series_array = new Object[2];
				series_array[0] = map.get("name");
				series_array[1] = map.get("count");
				series_list.add(series_array);
			}
		}
		
		pie_map.put("data", series_list);
		SpringUtils.renderJson(response, pie_map);
	}
	
	/**
	 * 根据type判断其跳转的页面
	 * @param request
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"getWithGoToWhat")
	public String getWithGoToWhat(HttpServletRequest request, Model model, String type) {
		
		dateYear = request.getParameter("dateYear");
		
		if (StringUtils.isEmpty(dateYear)) {
			dateYear = DateUtils.formatDate("yyyy", new Date());
		}
		
		model.addAttribute("dateYear", dateYear);
		model.addAttribute("type", type);
		
		if(type.equalsIgnoreCase("platformChart")) {
			return "report/platformChart";
		} else if(type.equalsIgnoreCase("platformHist")) {
			return "report/platformHist";
		} else if(type.equalsIgnoreCase("rechargeCashMoney")) {
			return "report/rechargeCashMoneyChart";
		} else if(type.equalsIgnoreCase("rechargeCashCount")) {
			return "report/rechargeCashCountChart";
		} else if(type.equalsIgnoreCase("bargainMoney")) {
			return "borrow/bargainMoneyChart";
		} else if (type.equalsIgnoreCase("bargainCount")) {
			return "borrow/bargainCountChart";
		} else if(type.equalsIgnoreCase("borrowByDate")) {
			return "borrow/borrowByDateChart";
		} else if(type.equalsIgnoreCase("borrowByType")) {
			return "borrow/borrowByTypeChart";
		} else if(type.equalsIgnoreCase("borrowByProvince")) {
			return "borrow/borrowByProvinceChart";
		} else {
			return null;
		}
	}
	
}
