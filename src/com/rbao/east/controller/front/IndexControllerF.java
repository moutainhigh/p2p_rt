package com.rbao.east.controller.front;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.dao.ExperienceGoldDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.Channel;
import com.rbao.east.entity.Content;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.AccountStatisticsService;
import com.rbao.east.service.BorrowRelatedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.ChannelService;
import com.rbao.east.service.ContentChannelService;
import com.rbao.east.service.ContentService;
import com.rbao.east.service.ReportService;
import com.rbao.east.service.SysConfigService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DecimalUtils;
import com.rbao.east.utils.SpringUtils;

/**
 * 首页
 * 
 * @author Sandy
 * 
 */
@Controller
@RequestMapping("/")
public class IndexControllerF extends BaseController {
	@Autowired
	private BorrowService borrowQueryService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserService userService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private ContentChannelService contentChannelService;

	@Autowired
	private BorrowRelatedService borrowRelatedService;

	@Autowired
	private BorrowTransferService borrowTransferService;

	@Autowired
	private AccountStatisticsService accountStatisticsService;

	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private BorrowTenderService borrowTenderService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private ExperienceGoldDao experienceGoldDao;
	
	/**
	 * 主页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("index.do")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(Borrow.STATUS_FIRSTAUDIT_YES);
		list.add(Borrow.STATUS_REPLYING);
		list.add(Borrow.STATUS_FULL);
		list.add(Borrow.STATUS_REVIEW_YES);
		list.add(Borrow.STATUS_REPLY_SUCCESS);
		result.put("list", list);
		
		param.put(Constants.PAGED_CURPAGE, "1");// 当前页
		param.put(Constants.PAGED_NUM_PERPAGE, "1");// 页条数
		param.put("orderType", 4+"");
		result.putAll(param);
		
		String id = "";
		result.put("bidList", new Integer [] {BorrowType.TYPE_XINSHOU});
		PageModel XSBList = borrowQueryService.showBorrowStatusInfoPageByParam(result); 	// 新手标	
		this.dateformat(XSBList.getList());
		result.put("bidList", new Integer [] {BorrowType.TYPE_LI});
		PageModel LBlist = borrowQueryService.showBorrowStatusInfoPageByParam(result);		// 给力标
		this.dateformat(LBlist.getList());
		result.put("bidList", new Integer [] {BorrowType.TYPE_ZHENGFEN});
		PageModel HDBlist = borrowQueryService.showBorrowStatusInfoPageByParam(result);		// 活动标
		this.dateformat(HDBlist.getList());
		if(XSBList.getList().size()>0){
			Map<String, Object> map = (Map<String, Object>) XSBList.getList().get(0);
			id += map.get("id")+",";
		}
		if(LBlist.getList().size()>0){
			Map<String, Object> map = (Map<String, Object>) LBlist.getList().get(0);
			id += map.get("id")+",";
		}
		if(HDBlist.getList().size()>0){
			Map<String, Object> map = (Map<String, Object>) HDBlist.getList().get(0);
			id += map.get("id")+",";
		}
		// 理财项目
		result.remove("bidList");
		result.put(Constants.PAGED_NUM_PERPAGE, "10");// 页条数
		PageModel borrows = borrowQueryService.showBorrowStatusInfoPageByParam(result);
		List<Map<String, Object>> borrowList = borrows.getList();
		List<Map<String, Object>> newBorrowList = new ArrayList<Map<String, Object>>();
		String mapId;
		for(Map<String, Object> map:borrowList){
			if(newBorrowList.size()>=4)break;
			mapId = map.get("id")+"";
			if(id.indexOf(mapId)<=0){
				newBorrowList.add(map);
			}
		}
		this.dateformat(newBorrowList);
		
		// 用户账户余额
		if (this.loginFrontUser(request) == null) {
		} else {
			UserAccount userAccount = this.userAccountService.getByUserId(this
					.loginFrontUser(request).getId());
			model.addAttribute("userAccount", userAccount);
		}
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		param.clear();
		param.put("borrowStatus", Borrow.STATUS_FIRSTAUDIT_YES.toString());
		Borrow borrow=borrowQueryService.borrowFirst(param);
		model.addAttribute("nowDate", new Date().getTime() + "");
		if(borrow!=null){
			model.addAttribute("allowTenderTime", borrow.getAllowTenderTime()
					.getTime() + "");
			model.addAttribute("publishTime", borrow.getPublishDatetime()
					.getTime() + "");
			model.addAttribute("Published",CompareUtils.greaterThan(new Date(),borrow.getPublishDatetime()));
			model.addAttribute("borrow", borrow);
		}else{
			model.addAttribute("Published","a");
			model.addAttribute("allowTenderTime", "");
			model.addAttribute("publishTime", "");
			model.addAttribute("borrow", null);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> channelCodes = new ArrayList<String>();
		channelCodes.add("video"); //媒体报道
		channelCodes.add("ImportantNotice");// 浩茗公告
		params.put("limit", 6);
		params.put("channelCodes", channelCodes);
		List<Map<String, Object>> contents = channelService.getContentByChannelCodes(params);
		
		params.clear();
		params.put("limit", 6);
		params.put("channelCode", "ZXZX");//资讯中心
		List<Map<String, Object>> zxzxs = channelService.getContentByChannelCode(params);
		
		model.addAttribute("XSBList",XSBList.getList());// 新手标
		model.addAttribute("LBlist",LBlist.getList());// 给力标
		model.addAttribute("HDBlist",HDBlist.getList());// 活动标
		model.addAttribute("borrows",newBorrowList);// 理财项目
		model.addAttribute("zxzxs",zxzxs);//资讯中心
		model.addAttribute("videoImportantNotice",contents);//浩茗公告

		return "index";
	}
	
	public void dateformat(List<Map<String, Object>> datas){
		long nowDateTime = new Date().getTime();
		long publishDatetime,end_time;
		Integer is_day,repayment_style,bid_rank,credit_type; 
		String isDayStr= "",maxAmount ="不限投";
		BigDecimal annual_interest_rate,tenderProgressRate,percent,max_amount;
		
		for(Map<String, Object> map: datas){
			publishDatetime = ((java.sql.Timestamp)map.get("publish_datetime")).getTime();
			end_time = ((java.sql.Timestamp)map.get("end_time")).getTime();
			if(publishDatetime <= nowDateTime && nowDateTime < end_time){
				map.put("butn_status", "allow");//允许投标
			}else if(end_time <= nowDateTime){
				map.put("butn_status", "over");//投标已结束
			}else if(nowDateTime < publishDatetime){
				map.put("butn_status", "wait");//待售
			}
			
			is_day = (Integer) map.get("is_day");
			if(is_day == 2){
				isDayStr = "个月";
			}else{
				isDayStr = "天";
			}
			map.put("isDayStr", isDayStr);
			
			repayment_style = (Integer) map.get("repayment_style");
			if(repayment_style ==  1){
				map.put("reypaymentStr", "到期还本付息");
			}else if(repayment_style ==  2){
				map.put("reypaymentStr", "按月分期");
			}else if(repayment_style ==  3){
				map.put("reypaymentStr", "先息后本");
			}
			
			annual_interest_rate = (BigDecimal) map.get("annual_interest_rate");
			BigDecimal annualInterestRate = DecimalUtils.fourHomesFive(annual_interest_rate);
			map.put("annualInterestRate", annualInterestRate);// 预期年化利率
			
			tenderProgressRate = map.get("tenderProgressRate")==null?new BigDecimal(0):(BigDecimal) map.get("tenderProgressRate");
			percent = DecimalUtils.fourHomesFive(tenderProgressRate);
			map.put("percent", percent);// 预期年化利率
			if(percent.compareTo(new BigDecimal(100.00))==0){
				map.put("isFull", "full");
			}
			
			max_amount = (BigDecimal) map.get("max_amount");
			if(max_amount.compareTo(new BigDecimal(2000.00))==0){
				map.put("maxAmount", "限投两千");
			}else if(max_amount.compareTo(new BigDecimal(5000.00))==0){
				map.put("maxAmount", "限投五千");
			}else if(max_amount.compareTo(new BigDecimal(10000.00))==0){
				map.put("maxAmount", "限投一万");
			}else if(max_amount.compareTo(new BigDecimal(15000.00))==0){
				map.put("maxAmount", "限投一万五");
			}else if(max_amount.compareTo(new BigDecimal(20000.00))==0){
				map.put("maxAmount", "限投两万");
			}else if(max_amount.compareTo(new BigDecimal(50000.00))==0){
				map.put("maxAmount", "限投五万");
			}else if(max_amount.compareTo(new BigDecimal(100000.00))==0){
				map.put("maxAmount", "限投十万");
			}else{
				map.put("maxAmount", maxAmount);
			}
			
			bid_rank = (Integer) map.get("bid_rank");
			switch(bid_rank){
				case 1 : map.put("bidRankStr", "A");
				case 2 : map.put("bidRankStr", "A+");
				case 3 : map.put("bidRankStr", "AA");
				case 4 : map.put("bidRankStr", "AA+");
				case 5 : map.put("bidRankStr", "AAA");
				case 6 : map.put("bidRankStr", "AAA+");
		        default : map.put("bidRankStr", "A");
			}
			
			credit_type = (Integer) map.get("credit_type");
			switch(credit_type){
				case 1 : map.put("creditStr", "信用");
				case 2 : map.put("creditStr", "质押");
				case 3 : map.put("creditStr", "抵押");
				case 4 : map.put("creditStr", "担保");
		        default : map.put("creditStr", "信用");
			}
		}
	}

	/**
	 * 主页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("indexSeek.do")
	public String indexSeek(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		return "indexSeek";
	}
	
	/**
	 * 得到首页项目推荐
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("getBorrowRecommend.do")
	public void getBorrowRecommend(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(Borrow.STATUS_FIRSTAUDIT_YES);
		list.add(Borrow.STATUS_REPLYING);
		list.add(Borrow.STATUS_FULL);
		list.add(Borrow.STATUS_REVIEW_YES);
		list.add(Borrow.STATUS_REPLY_SUCCESS);
		result.put("list", list);
		
		//result.put("isIndex", "1");
		param.put(Constants.PAGED_CURPAGE, "1");// 当前页
		param.put(Constants.PAGED_NUM_PERPAGE, "1");// 页条数
		param.put("orderType", 4+"");
		result.putAll(param);
		

		List<Integer> bidList = new ArrayList<Integer>();
		//新手标
		result.put("bidList", new Integer [] {BorrowType.TYPE_XINSHOU});
		PageModel XSBList = borrowQueryService
				.showBorrowStatusInfoPageByParam(result);		//新手标
		bidList.clear();
		
		result.put("bidList", new Integer [] {BorrowType.TYPE_MINGXING});
		PageModel MXBlist = borrowQueryService
				.showBorrowStatusInfoPageByParam(result);		//明星标
		bidList.clear();

		
		result.put("bidList", new Integer [] {BorrowType.TYPE_ZHENGFEN});
		PageModel HDBlist = borrowQueryService
				.showBorrowStatusInfoPageByParam(result);		//活动标
		bidList.clear();
		System.out.println(HDBlist.getList().size());
		result.put("bidList", new Integer [] {BorrowType.TYPE_LIUYUE});
		PageModel LYBlist = borrowQueryService
				.showBorrowStatusInfoPageByParam(result);		//六月标
		bidList.clear();
//		System.out.println(LYBlist.getList().get(0));
		
		result.put("bidList", new Integer [] {BorrowType.TYPE_JIUYUE});
		PageModel JYBlist = borrowQueryService
				.showBorrowStatusInfoPageByParam(result);		//九月标
		bidList.clear();
		
		result.put("bidList", new Integer [] {BorrowType.TYPE_SHEERYUE});
		PageModel SEYBlist = borrowQueryService
				.showBorrowStatusInfoPageByParam(result);		//十二标
		bidList.clear();
		
		result.put("bidList", new Integer [] {BorrowType.TYPE_LI});
		PageModel LBlist = borrowQueryService
				.showBorrowStatusInfoPageByParam(result);		//给力标
		bidList.clear();
		
		
		PageModel borrowList = borrowQueryService
				.showBorrowStatusInfoPageByParam(result);
		/*PageModel transList = borrowTransferService.selectBorrowTransList(param);
		PageModel DHTList=borrowRelatedService.getDHTPage(param);*/
		result.clear();
		
		System.out.println(XSBList.getList().size());
		result.put("XSBList", XSBList);
		result.put("MXBlist", MXBlist);
		result.put("HDBlist", HDBlist);
		result.put("LYBlist", LYBlist);
		result.put("JYBlist", JYBlist);
		result.put("SEYBlist", SEYBlist);
		result.put("borrowList", borrowList);
		result.put("LBlist", LBlist);
		result.put("nowDate", new Date().getTime() + "");
		SpringUtils.renderJson(response, result);
	}

	/**
	 * 首页统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getStatistics.do")
	public void getStatistics(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tradeCode", AccountLog.TRADE_CODE_TENDER_SUCC);
		//用户人数
		Integer userNum = userService.getAllUsers();
		//投资金额
		BigDecimal totleMoney = accountLogService
				.getTotleMoneyByTradeCode(param);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String nowDate = DateUtils.getDate(calendar.getTime());
		param.put("tradeCode", AccountLog.TRADE_CODE_REPOSSESSED);
		param.put("endDate", nowDate + " 23:59:59");
		param.put("startDate", nowDate + " 00:00:00");
		BigDecimal repossessed = accountLogService
				.getTotleMoneyByTradeCode(param);
		param.clear();
		
		BigDecimal allInterestAndReward = accountStatisticsService
				.getAllInterestAndReward(param);
		
		//累计总收益
		/*BigDecimal getAllInterestMoney=userAccountService.getAllInterestMoney();
		
		param.put("getAllInterestMoney", getAllInterestMoney);*/
		BigDecimal userEarnings = reportService.summaryUserEarnings();					//累计用户总收益
		BigDecimal allExperienceGlod = (BigDecimal) experienceGoldDao.getObject("getAllExperienceGoldByParam", null);
		userEarnings = userEarnings.add(allExperienceGlod);
		param.put("getAllInterestMoney", userEarnings);
		param.put("allInterestAndRewardStr", allInterestAndReward);
		param.put("repossessed", repossessed);
		param.put("totleMoney", totleMoney);
		param.put("userNum", userNum);
		//投资人数
		Integer tenderUsers = borrowTenderService.selectTenders();
		param.put("tenderUsers",tenderUsers);
		SpringUtils.renderJson(response, param);
	}

	/**
	 * 首页公告
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getContentList.do")
	public void getContentList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("limit", 6);
		param.put("channelCode", "webNotice");
		List<Map<String, Object>> channels = channelService
				.getContentByChannelCode(param);
		param.put("limit", 6);
		param.put("channelCode", "ImportantNotice");//安全保障
		List<Map<String, Object>> zygg = channelService
				.getContentByChannelCode(param);
		//媒体报道
		param.put("limit", 6);
		param.put("channelCode", "video");
		List<Map<String, Object>> vodeo = channelService

				.getContentByChannelCode(param);
		param.put("channelCode", "OperationBriefing");
		List<Map<String, Object>> briefings = channelService
				.getContentByChannelCode(param);
		param.put("limit", 6);
		param.put("channelCode", "borrowNews");
		List<Map<String, Object>> borrowNews = channelService
				.getContentByChannelCode(param);

		
		param.put("channelCode", "hyqy");
		List<Map<String, Object>> hyqy = channelService
				.getContentByChannelCode(param);
		
		//底部图片
		param.put("limit", 6);
		param.put("channelCode", "bottomPicture");
		List<Map<String, Object>> bottomPicture = channelService.getContentByChannelCode(param);
		
		//合作伙伴
		param.clear();
		param.put("channelCode", "collFriend");
		List<Map<String, Object>> collFriend = channelService
				.getContentByChannelCode(param);
		
		
		param.put("briefings", briefings);
		param.put("borrowNews",borrowNews);
		param.put("collFriend", collFriend);
		param.put("hyqy", hyqy);
		param.put("vodeo", vodeo);
		param.put("channels", channels);
		param.put("zygg", zygg);
		param.put("bottomPicture",bottomPicture);
		SpringUtils.renderJson(response, param);
	}

	/**
	 * 投资排行
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("getTenderInfoList.do")
	public void getTenderInfoList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("limit", 6);
		param.put("tradeCode", AccountLog.TRADE_CODE_TENDER_SUCC);
		List<AccountLog> userTenderListAll = accountLogService
				.getRankingList(param);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		String startDate = format.format(cal.getTime());

		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		String endDate = format.format(cal.getTime());

		param.put("endDate", endDate + " 23:59:59");
		param.put("startDate", startDate + " 00:00:00");
		List<AccountLog> userTenderListByMonth = accountLogService
				.getRankingList(param);
		// 周
		Calendar c = Calendar.getInstance();
		int weekday = c.get(7) - 2;
		c.add(5, -weekday);
		param.put("startDate", format.format(c.getTime()) + " 00:00:00");
		c.add(5, 6);
		param.put("endDate", format.format(c.getTime()) + " 23:59:59");
		List<AccountLog> userTenderListByWeek = accountLogService
				.getRankingList(param);

		Calendar calendar = Calendar.getInstance();
		String nowDate = DateUtils.getDate(calendar.getTime());
		param.put("startDate", nowDate + " 00:00:00");
		param.put("endDate", nowDate + " 23:59:59");
		List<AccountLog> userTenderListByDay = accountLogService
				.getRankingList(param);

		param.clear();
		
		
		//总投资排行榜
		param.put("userTenderListAll", userTenderListAll);
		//周排行榜
		param.put("userTenderListByWeek", userTenderListByWeek);
		//月排行榜
		param.put("userTenderListByMonth", userTenderListByMonth);
		//日排行榜
		param.put("userTenderListByDay", userTenderListByDay);
		SpringUtils.renderJson(response, param);
	}

	/**
	 * 用户投标排行
	 */
	@RequestMapping("getUserTenderData.do")
	public void getUserTenderData(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		//总榜
		List<Map> zong = borrowTenderService.getUserTenderRank(param);
		params.put("zong", zong);
		
		//月榜
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		String startDate = format.format(cal.getTime());

		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		String endDate = format.format(cal.getTime());
		
		param.put("endDate", endDate + " 23:59:59");
		param.put("startDate", startDate + " 00:00:00");
		List<Map> yue = borrowTenderService.getUserTenderRank(param);
		params.put("yue", yue);
		
		param.clear();
		// 周
		Calendar c = Calendar.getInstance();
		int weekday = c.get(7) - 2;
		c.add(5, -weekday);
		param.put("startDate", format.format(c.getTime()) + " 00:00:00");
		System.out.println(format.format(c.getTime()) + " 00:00:00");
		c.add(5, 6);
		param.put("endDate", format.format(c.getTime()) + " 23:59:59");
		System.out.println(format.format(c.getTime()) + " 00:00:00");
		
		List<Map> zhou = borrowTenderService.getUserTenderRank(param);
		params.put("zhou", zhou);
		
		param.clear();
		Calendar calendar = Calendar.getInstance();
		String nowDate = DateUtils.getDate(calendar.getTime());
		param.put("startDate", nowDate + " 00:00:00");
		param.put("endDate", nowDate + " 23:59:59");
		List<Map> ri = borrowTenderService.getUserTenderRank(param);
		params.put("ri", ri);
		
		param.clear();
//		List<Map> toubiao =borrowTenderService.getTenderRecords();
//		params.put("toubiao", toubiao);
//		
//		List<Map> repayment =borrowTenderService.getBorrowRepaymentRecords();
//		params.put("repayment", repayment);
		
		SpringUtils.renderJson(response, params);
	}
	
	
	
	/**
	 * 首页图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getContentListPic.do")
	public void getContentListPic(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		String channelCode = "advertisement";
		List<Content> contentListPic = contentService
				.getContentPicFri(channelCode);
		param.put("contentListPic", contentListPic);
		SpringUtils.renderJson(response, param);
	}

	/**
	 * top
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("top.do")
	public String top(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channelCode", "bzzx");
		List<Map<String, Object>> bzzxChannels = channelService
				.getContentByChannelCode(param);
		model.addAttribute("bzzxChannels", bzzxChannels);
		param.put("channelCode", "aboutUs");
		List<Map<String, Object>> aboutUsChannels = channelService
				.getContentByChannelCode(param);
		model.addAttribute("aboutUsChannels", aboutUsChannels);
		return "top";
	}

	/**
	 * foot
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("foot.do")
	public String foot(HttpServletRequest request,
			HttpServletResponse response, Model model, HttpSession session) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, String> params=new HashMap<String, String>();
		
		param.put("channelCode", "blogroll");
		param.put("limit", 11);
		List<Map<String, Object>> blogrollList = channelService
				.getContentByChannelCode(param);
		model.addAttribute("blogrollList", blogrollList);
		
		params.put("channelDisplay", "1");
		params.put("channelParentId", "0");
		params.put("limit", "8");
		List<Channel> channelLists=channelService.getByParentId(params);
		model.addAttribute("channelList",channelLists);
		
		
		        //安全保障
				param.clear();
				param.put("channelCode", "aqbz");
				param.put("limit", 3);
				List<Map<String, Object>> safetyList = channelService
						.getContentByChannelCode(param);
				model.addAttribute("safetyList", safetyList);	
				// 关于我们
				param.clear();
				param.put("channelCode", "gywm");
				param.put("limit", 3);
				List<Map<String, Object>> aboutusList = channelService
						.getContentByChannelCode(param);
				model.addAttribute("aboutusList", aboutusList);	
				
				// 帮助中心
				param.clear();
				param.put("channelCode", "bzzx");
				param.put("limit", 5);
				List<Map<String, Object>> helpCenterList = channelService
						.getContentByChannelCode(param);
				model.addAttribute("helpCenterList", helpCenterList);	
				
				// 帮助中心
				param.clear();
				param.put("channelCode", "bottomPicture");
				param.put("limit",4);
				List<Map<String, Object>> bottomPictureList = channelService
						.getContentByChannelCode(param);
				model.addAttribute("bottomPictureList", bottomPictureList);
				
		return "foot";
	}
	@RequestMapping("commons/guide.do")
	public String guide(HttpServletRequest request,
			HttpServletResponse response, Model model, HttpSession session) {

		return "guide";
	}
	
	@RequestMapping("commons/safeGuarantee.do")
	public String safeGuarantee(HttpServletRequest request,
			HttpServletResponse response, Model model, HttpSession session) {
		
		return "safeGuarantee";
	}
}