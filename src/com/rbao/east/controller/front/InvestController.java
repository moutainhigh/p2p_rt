package com.rbao.east.controller.front;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.dao.BorrowRepossessedDao;
import com.rbao.east.entity.AutotenderRules;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowTransfer;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysConfig;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.entity.UserEvaluate;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.AutotenderRulesService;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.SysConfigService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserEvaluateService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;

/**
 * 我的投资
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("invest/")
public class InvestController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(InvestController.class);

	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private BorrowRepossessedService borrowRepossessedService;
	@Autowired
	private BorrowTenderService borrowTenderService;
	@Autowired
	private BorrowService borrowQueryService;
	@Autowired
	private AutotenderRulesService autotenderRulesService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private BorrowTypeService borrowTypeService;
	@Autowired
	private UserService userService;
	@Autowired
	private BorrowTransferService borrowTransferService;
	@Autowired
	private UserEvaluateService userEvaluateService;
	@Autowired
	private BorrowRepossessedDao borrowRepossessedDao;

	
	@RequestMapping("/toInvest.do")
	public String toInvest(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String [] codes={"LI","ZHUAN","MIAO","XING","JING",Borrow.BORROW_TYPE_ZHUAN_DF};
		List<BorrowType> borrowTypeList=borrowTypeService.getByTypeCodes(codes);
		model.addAttribute("borrowTypeList",borrowTypeList);
		return "borrow/invest";
	}

	/**
	 * 自动投资
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unused")
	// 自动投资
	@RequestMapping("autoInvestment.do")
	public String auto(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = this.loginFrontUser(request);
		AutotenderRules autoTenderRules = autotenderRulesService.getByUserId(user.getId());
		model.addAttribute("user", user);
		String msg = "0";
		try {
			//标种信息
			List<BorrowType> bType = borrowTypeService.getBorrowsTypeByParam(null,null, BorrowType.STATUS_ABLE);
			request.setAttribute("bType", bType);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", user.getId().toString());
			param.put("realnameStatus", User.REALNAME_PASS);
			param.put("phoneStatus", User.PHONE_PASS);
		/*	param.put("emailStatus", User.EMAIL_PASS);*/
			if (this.userService.selectByStatus(param)) {
				msg = "80";
				request.setAttribute("error", "80");
			}
			List<SysConfig> cfg = sysConfigService.getAll();
			if(cfg.size()>0){
				if(cfg.get(0).getAutoTenderStatus().equals(SysConfig.STATUS_NO)){
					request.setAttribute("closed", "true");
				}
			}
			//排名
			Map<String, Object> m = new HashMap<String, Object>();
			if(autoTenderRules != null){
				m.put("queueTime", autoTenderRules.getQueueTime());
			}
			request.setAttribute("totalRank", this.autotenderRulesService.getRank(m));
			m.put("minMoney", new BigDecimal(50));
			request.setAttribute("avbRank", this.autotenderRulesService.getRank(m));
		} catch (Exception e) {
			
		}

		//if (autoTenderRules != null) {
			request.setAttribute("autoTenderRules", autoTenderRules);
		//}

		return "userinfo/invest/autoInvestment";
	}

	/**
	 * 添加自动投资
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param auto
	 */
	@RequestMapping("saveAuto.do")
	public void getAuto(HttpServletRequest request,
			HttpServletResponse response, Model model, AutotenderRules auto) {
		User user = this.loginFrontUser(request);
		auto.setUserId(user.getId());
		AutotenderRules pms = autotenderRulesService.getByUserId(user.getId());

		JsonResult json = new JsonResult();
		// 如果不存在
		try {
			OperatorLog operatorLog = new OperatorLog();
			auto.setUpdateTime(new Date());
			if (pms == null) {// 第一次添加
				auto.setAprStatus(1);
				boolean retmsg = autotenderRulesService.addAuto(auto);
				operatorLog.setOperatorTitle("添加自动投资");
				operatorLog.setOperatorReturn(retmsg ? "添加自动投资成功" : "添加自动投资失败");
				operatorLog.setOperatorStatus(retmsg ? 200 : 300);
				if (retmsg) {
					json.setCode("201");
					json.setMsg("添加成功");
				}else{
					json.setCode("202");
					json.setMsg("添加失败");
				}
			} else {// 修改
				auto.setId(pms.getId());
				boolean flag = autotenderRulesService.updateAuto(auto);
				operatorLog.setOperatorTitle("修改自动投资");
				operatorLog.setOperatorReturn(flag ? "修改自动投资成功" : "修改自动投资失败");
				operatorLog.setOperatorStatus(flag ? 200 : 300);
				if(flag){
					json.setCode("203");
					json.setMsg("修改成功");
				}else{
					json.setCode("204");
					json.setMsg("修改失败");
				}
				
			}
			/**
			 * 记录日志
			 */
			User loginUser = this.loginFrontUser(request);
			operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："+ loginUser.getUserAccount());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
			operatorLog.setOperatorParams(user.toString());
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
		} catch (Exception e) {
			
		}
		SpringUtils.renderJson(response, json);
	}
	
	//我是投资者
	
	/**
	 * 正在投标的项目
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("inBid.do")
	public String inBid(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Integer userId = this.loginFrontUser(request).getId();
		List<BorrowTender> list = borrowTenderService.getBorrowDetailByUserId(userId);
		UserAccount userAccount = userAccountService.getByUserId(userId);
		model.addAttribute("unavailableMoney", userAccount.getUnavailableMoney());
		Integer tendeing = 0;
		Integer backing = 0;
		Integer transfing=0;
		Map<String,String> params = new HashMap<String,String>();
		params.put("userId", userId.toString());
		backing = borrowRepossessedDao.getTotalCount("selectTotalCounts", params);
		BigDecimal effAmount= new BigDecimal("0.00");
		for(int i=0;i<list.size();i++){
			if(list.get(i).getEffectiveAmount()!=null){
				
				effAmount=effAmount.add(list.get(i).getEffectiveAmount());
			}
			if(list.get(i).getTenderStatus()!=null&&list.get(i).getTenderStatus()==1){
				tendeing+=1;
			}/*else if(list.get(i).getTenderStatus()!=null&&list.get(i).getTenderStatus()==3){
				backing+=1;
				
			}*//*else if(list.get(i).getTenderStatus()!=null&&list.get(i).getTenderStatus()==7){
				transfing+=1;
			}*/
			
		}
		//查看转让中的笔数，查询transfer表
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userAccount.getUserId());
		param.put("inStatus",  new Integer[]{BorrowTransfer.STATUS_AUCTIONING});
		transfing = borrowTransferService.getByTransferUserid(param).size();
		model.addAttribute("tendeing", tendeing);
		model.addAttribute("backing", backing);
		model.addAttribute("transfing", transfing);
		model.addAttribute("effAmount", effAmount);
		return "userinfo/invest/inBid";
	}
	@RequestMapping("inBidPage.do")
	public void inBidPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginFrontUser(request).getId().toString());
		PageModel pageModel = borrowQueryService.getBorrowInBidPage(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);
	}
	/**
	 * 正在收款的项目
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("inRepaying.do")
	public String inRepaying(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/invest/inRepaying";
	}
	@RequestMapping("inRepayingPage.do")
	public void inRepayingPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginFrontUser(request).getId().toString());
		PageModel pageModel = borrowTenderService.getBorrowRepayingPage(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);
	}

	/**
	 * 已还清的项目
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("inRepayed.do")
	public String inRepayed(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/invest/inRepayed";
	}
	
	/**
	 * 借出明细账
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("getRecord.do")
	public String getRecord(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/invest/investRecord";
	}
	
	/**
	 * 收款明细账
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("getRepByStatus.do")
	public String getRepByStatus(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/invest/getRepByStatus";
	}
	@RequestMapping("getRepByStatusPage.do")
	public void getRepByStatusPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginFrontUser(request).getId().toString());
		PageModel pageModel = borrowRepossessedService.getRepByStatusPage(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);
	}
	@RequestMapping("getRepByStatus1.do")
	public String getRepByStatus1(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/invest/getRepByStatus1";
	}
	
	/**
	 * 投资记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("investRecord.do")
	public String investRecord(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("bidKind", paramsMap.get("bidKind"));
		return "userinfo/invest/investRecord";
	}

	@RequestMapping("investRecordPage.do")
	public void investRecordPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginFrontUser(request).getId().toString());
		PageModel pageModel = borrowTenderService.selectInvestByUserId(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);
	}

	@RequestMapping("showInvestPlanList.do")
	public String showInvestPlanList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("bidKindNo", paramsMap.get("bidKindNo"));
		return "userinfo/invest/investRecord1";
	}
	/**
	 * 定活通投资赎回分页
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("selectInvestRedeem.do")
	public void selectInvestRedeem(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> params = getParameters(request);
		params.put("userId", this.loginFrontUser(request).getId().toString());
		params.put("BidCode", BorrowType.BIDCODE);
		PageModel pageModel = borrowTenderService.selectInvestRedeem(params);
		SpringUtils.renderJson(response, pageModel);
	}
	
	/**
	 * 直投区投资赎回分页 债券转让
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("queryInvestRedeem.do")
	public void queryInvestRedeem(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> params = getParameters(request);
		params.put("userId", this.loginFrontUser(request).getId().toString());
		params.put("BidCode", BorrowType.BIDCODE);
		//查询六个月的债券转让
		params.put("borrowLimit6", "6");
		
		PageModel pageModel = borrowTenderService.queryInvestRedeem(params);
		SpringUtils.renderJson(response, pageModel);
	}
	
	/**
	 * 债券转让页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("redeemTransfer/{tenderId}.do")
	public String redeemTransfer(HttpServletRequest request,
			HttpServletResponse response, Model model,@PathVariable("tenderId") Integer tenderId){
		BorrowTender borrowTender=this.borrowTenderService.selectBorrowTenderByBorrowTenderId(tenderId);
		Borrow borrow = this.borrowQueryService.getBorrowById(borrowTender.getBorrowId());
		BigDecimal transferFee = new BigDecimal(0);
		if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_MONTHLY)){
			List<BorrowRepossessed> waitRepoList = borrowRepossessedService.selectWaitRepossessByTender(tenderId);
			transferFee=CalculateProcess.getDayInterest(waitRepoList.get(0).getRepossessedInterest(), 15);
		}else{
			transferFee=CalculateProcess.getTransferFee(borrowTender.getEffectiveAmount(), borrow.getAnnualInterestRate());
		}
		model.addAttribute("tenderId", tenderId);
		model.addAttribute("transferFee", transferFee);
		model.addAttribute("transferMoney",borrowTransferService.getTransferMoney(tenderId));
		return "userinfo/invest/redeemTransfer";
	}
	/**
	 * 投资赎回
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("investRedeem.do")
	public String investRedeem(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/invest/investRedeem";
	}
	
	@RequestMapping("transferRedeem.do")
	public String transferRedeem(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/invest/investTransfer";
	}

	/**
	 * 借款协议
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loanAgreement.do")
	public String loanAgreement(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("bidKind", paramsMap.get("bidKind"));
		return "userinfo/invest/loanAgreement";
	}

	@RequestMapping("loanAgreementPage.do")
	public void loanAgreementPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginFrontUser(request).getId().toString());
		PageModel pageModel = borrowQueryService.selectAgreementPath(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);
	}

	@RequestMapping("showLoanAgreement.do")
	public String showLoanAgreement(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("bidKindNo", paramsMap.get("bidKindNo"));
		return "userinfo/invest/loanAgreement1";
	}

	/**
	 * 资产统计
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping("propertyStatistics.do")
	public String propertyStatistics(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		UserEvaluate evaluete = userEvaluateService.getUserEvaluateByUserId(this.loginFrontUser(request).getId());
		if(evaluete!=null){
			model.addAttribute("evaluete", evaluete);
		}
		UserAccount userAccount = this.userAccountService.getByUserId(this.loginFrontUser(request).getId());
		// 累计静收益
		BigDecimal sumGet = (userAccount.getGetInterest().add(userAccount
				.getGetReward())).subtract(userAccount.getPayInterest().add(
				userAccount.getPayReward()));
		model.addAttribute("userAccount", userAccount);
		// 判断净收益是否大于0,-1小于，0等于，1大于
		if (sumGet.compareTo(BigDecimal.valueOf(0)) == -1) {
			sumGet = BigDecimal.valueOf(0);
		}
		model.addAttribute("sumGet", sumGet);
		Map<String, String> param = new HashMap<String, String>();
		// 提现总金额
		param.put("tradeCode", "13");
		param.put("userId", this.loginFrontUser(request).getId().toString());
		Integer sumMoney = accountLogService.selectByTradeCode(param);
		model.addAttribute("sumMoney", sumMoney);
		// 充值总金额
		param.put("tradeCode", "11");
		param.put("userId", this.loginFrontUser(request).getId().toString());
		Integer txsumMoney = accountLogService.selectByTradeCode(param);
		model.addAttribute("txsumMoney", txsumMoney);
		// 回收本金，利息。。。。
		param.put("repossessedStatus", "2");
		List list = borrowRepossessedService.selectSum(param);
		model.addAttribute("repossessedSum", list.get(0));
		param.put("bidKind", "6");
		List list1 = borrowRepossessedService.selectSum(param);
		model.addAttribute("repossessedBao", list1.get(0));
		// 以投资金额
		param.put("tenderStatus2", "2");
		param.put("tenderStatus6", "6");
		List eff = borrowTenderService.selectEff(param);
		model.addAttribute("eff", eff.get(0));
		param.put("bidKideEff", "6");
		List effBoa = borrowTenderService.selectEff(param);
		model.addAttribute("effBao", effBoa.get(0));
		return "userinfo/invest/propertyStatistics";
	}

}
