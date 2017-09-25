package com.rbao.east.controller.front;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baofoo.sdk.api.BaoFooApi;
import com.hnapay.gateway.client.enums.CharsetTypeEnum;
import com.hnapay.gateway.client.java.ClientSignature;
import com.mypay.merchantutil.Md5Encrypt;
import com.mypay.merchantutil.UrlHelper;
import com.mypay.merchantutil.timestamp.TimestampUtils;
import com.rbao.east.common.Constants;
import com.rbao.east.common.GenerateNo;
import com.rbao.east.common.SequenceUtils;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.AccountCash;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.AllBank;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.ExperienceGold;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.PaymentConfig;
import com.rbao.east.entity.RedenvelopesProbability;
import com.rbao.east.entity.RedenvelopesRecord;
import com.rbao.east.entity.RelaseCard;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.entity.UserBank;
import com.rbao.east.entity.UserCredit;
import com.rbao.east.entity.UserEvaluate;
import com.rbao.east.entity.VipUser;
import com.rbao.east.lianliancashpay.Md5Algorithm;
import com.rbao.east.lianlianpayutil.LLPayUtil;
import com.rbao.east.lianlianpayutil.TraderRSAUtil;
import com.rbao.east.pay.DateUtil;
import com.rbao.east.pay.EpayMD5;
import com.rbao.east.pay.PayUtils;
import com.rbao.east.pay.PaymentForOnlineService;
import com.rbao.east.pay.entity.OnlineRechargeBF;
import com.rbao.east.pay.entity.OnlineRechargeBeiFu;
import com.rbao.east.pay.entity.OnlineRechargeChinaPay;
import com.rbao.east.pay.entity.OnlineRechargeEBank;
import com.rbao.east.pay.entity.OnlineRechargeFengFu;
import com.rbao.east.pay.entity.OnlineRechargeGFB;
import com.rbao.east.pay.entity.OnlineRechargeHC;
import com.rbao.east.pay.entity.OnlineRechargeHFB;
import com.rbao.east.pay.entity.OnlineRechargeLianLian;
import com.rbao.east.pay.entity.OnlineRechargePOSPT;
import com.rbao.east.pay.entity.OnlineRechargeSQ;
import com.rbao.east.pay.entity.OnlineRechargeSina;
import com.rbao.east.pay.entity.OnlineRechargeXinSheng;
import com.rbao.east.pay.entity.OnlineRechargeYeePay;
import com.rbao.east.service.AccountCashService;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.AccountRechargeService;
import com.rbao.east.service.AllBankService;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.CashStageService;
import com.rbao.east.service.ExperienceGoldService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.PaymentConfigService;
import com.rbao.east.service.RechargeCashService;
import com.rbao.east.service.RecommendRewardService;
import com.rbao.east.service.RedenvelopesService;
import com.rbao.east.service.RelaseBankService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserBankService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserEvaluateService;
import com.rbao.east.service.UserService;
import com.rbao.east.service.VipUserService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DecimalUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.GopayUtils;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.PropertiesUtil;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.SysCacheUtils;
import com.rbao.east.utils.UploadUtils;
import com.sumavision.sumapay.util.SignatureUtil;

/**
 * 我的账户
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("account/")
public class AccountController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private AllBankService allBankService;
	@Autowired
	private UserBankService userBankService;
	@Autowired
	private UserService userService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private AccountCashService accountCashService;
	@Autowired
	private PaymentConfigService paymentConfigService;
	@Autowired
	private AccountRechargeService accountRechargeService;
	@Autowired
	private BorrowTransferService borrowTransferService;
	@Autowired
	private UserEvaluateService userEvaluateService;
	@Autowired
	private BorrowService borrowQueryService;
	@Autowired
	private BorrowRepossessedService borrowRepossessedService;
	@Autowired
	private BorrowRepaymentService borrowRepaymentService;
	@Autowired
	private VipUserService vipUserService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private UserCreditService  userCreditService;
	@Autowired
	private RechargeCashService rechargeCashService;
	@Autowired
	private CashStageService cashStageService;
	@Autowired
	private RedenvelopesService redenvelopesService;
	@Autowired
	private ExperienceGoldService experienceGoldService;
	@Autowired
	private RecommendRewardService  recommendRewardService;
	@Autowired
	private RelaseBankService relaseBankService;
	@RequestMapping("accountIndexToJson.do")
	public void accountIndexToJson(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramMap = getParameters(request);
		if(paramMap == null){
			paramMap = new HashMap<String, String>();
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		User user = this.userService.getById(this.loginFrontUser(request).getId());
		param.put("user",user);
		
		UserAccount userAccount = this.userAccountService.getByUserId(this
				.loginFrontUser(request).getId());
		param.put("userAccount", userAccount);
		BigDecimal sumGet = (userAccount.getGetInterest().add(userAccount
				.getGetReward())).subtract(userAccount.getPayInterest().add(
				userAccount.getPayReward()));
		// 判断净收益是否大于0,-1小于，0等于，1大于
		if (sumGet.compareTo(BigDecimal.valueOf(0)) == -1) {
			sumGet = BigDecimal.valueOf(0);
		}
		param.put("invest", sumGet);
		//最近待收
		List<BorrowRepossessed> borrowRepossesseds = borrowRepossessedService.getBorrowRepWaitByUserId(this.loginFrontUser(request).getId());
		if(borrowRepossesseds.size()>0){
			param.put("borrowRepossessed", borrowRepossesseds.get(0));
		}
		//最近待还
		List<BorrowRepayment> borrowRepayments = borrowRepaymentService.getWaitRepaymentByUserId(this.loginFrontUser(request).getId());
		if(borrowRepayments.size()>0){
			param.put("repayment",borrowRepayments.get(0));
		}
		UserEvaluate evaluete = userEvaluateService.getUserEvaluateByUserId(this.loginFrontUser(request).getId());
		if(evaluete!=null){
			param.put("evaluete", evaluete);
		}
		param.put("userId", this.loginFrontUser(request).getId().toString());
		VipUser vipUser = vipUserService.selectByUserId(paramMap);
		if(vipUser != null && vipUser.getVipStatus().equals(VipUser.VIP_USER)){
			param.put("vipUser", vipUser);
		}
		
		Integer msgCount = messageCenterService.countUnReadMsg(this.loginFrontUser(request).getId());
		param.put("msgCount", msgCount);
		
		SpringUtils.renderJson(response, param);
	}
	
	/**
	 * 我的账户
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("accountIndex.do")
	public String acccountIndex(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		@SuppressWarnings("unused")
		User user = this.loginFrontUser(request);
		Map<String, String> param = getParameters(request);
		if(param == null){
			param = new HashMap<String, String>();
		}
		// 昨日收益：活期昨日收益+定期或标昨日还款收益
		Map<String, Object> queryMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		queryMap.put("userId", user.getId());
		queryMap.put("now", new Date());
		queryMap.put("yesterDay", DateUtils.addDay(new Date(), -1));
		// 活期昨日收益
		resultMap = borrowRepossessedService.selectSumYesterday(queryMap);
		BigDecimal sumYesPro = new BigDecimal(resultMap.get("sumInterest")
				.toString());
		resultMap = borrowRepossessedService.selectSumYesterday(queryMap);
		BigDecimal sumInterest = new BigDecimal(resultMap
				.get("sumInterest").toString());
		//红包昨日收益
		//红包收益添加到累计收益里面
		Map redMap = new HashMap();
		Integer[] status = new Integer[]{RedenvelopesRecord.STATUS_HAS_OPEN};
		Date redNow = new Date();
		redNow = DateUtils.addDay(redNow, -1);
		redMap.put("userId", user.getId());
		redMap.put("inStatus", status);
		redMap.put("openTime", redNow);
		BigDecimal redMoney = redenvelopesService.getRedSumMoneyByUsrIdAndStatusTime(redMap);
		model.addAttribute("yesterdayInterest", sumYesPro.add(sumInterest).add(redMoney).setScale(2, RoundingMode.HALF_UP));
		
		User users = this.userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user",users);
		
		UserAccount userAccount = this.userAccountService.getByUserId(this
				.loginFrontUser(request).getId());
		model.addAttribute("userAccount", userAccount);
		BigDecimal sumGet = (userAccount.getGetInterest().add(userAccount
				.getGetReward())).subtract(userAccount.getPayInterest().add(
				userAccount.getPayReward()));
		// 判断净收益是否大于0,-1小于，0等于，1大于
		if (sumGet.compareTo(BigDecimal.valueOf(0)) == -1) {
			sumGet = BigDecimal.valueOf(0);
		}
		model.addAttribute("invest", sumGet.setScale(2, BigDecimal.ROUND_DOWN));
		//最近待收
		List<BorrowRepossessed> borrowRepossesseds = borrowRepossessedService.getBorrowRepWaitByUserId(this.loginFrontUser(request).getId());
		if(borrowRepossesseds.size()>0){
			model.addAttribute("borrowRepossessed", borrowRepossesseds.get(0));
		}
		//最近待还
		List<BorrowRepayment> borrowRepayments = borrowRepaymentService.getWaitRepaymentByUserId(this.loginFrontUser(request).getId());
		if(borrowRepayments.size()>0){
			model.addAttribute("repayment",borrowRepayments.get(0));
		}
		UserEvaluate evaluete = userEvaluateService.getUserEvaluateByUserId(this.loginFrontUser(request).getId());
		if(evaluete!=null){
			model.addAttribute("evaluete", evaluete);
		}
		param.put("userId", this.loginFrontUser(request).getId().toString());
		VipUser vipUser = vipUserService.selectByUserId(param);
		if(vipUser != null && vipUser.getVipStatus().equals(VipUser.VIP_USER)){
			model.addAttribute("vipUser", vipUser);
		}
		UserCredit userCredit = userCreditService.getByUserId(user.getId());
		if(userCredit != null ){
			model.addAttribute("creditValue", userCredit.getCreditValue());
		}
		Integer msgCount = messageCenterService.countUnReadMsg(this.loginFrontUser(request).getId());
		model.addAttribute("msgCount", msgCount);
		
		//用户累计收益
		Map<String,String> paramsMap = new HashMap<String, String>();
		BigDecimal totalIncome = new BigDecimal(0);
		totalIncome = totalIncome.add(userAccount.getGetInterest());
		//体验金
		paramsMap.put("userId", user.getId().toString());
		paramsMap.put("experienceStatus", "1");
		ExperienceGold experienceGold = experienceGoldService.getExperienceGoldByParam(paramsMap);
		if(experienceGold != null){
			totalIncome = totalIncome.add(experienceGold.getExperienceGoldInterest());
		}
		//分销收益
		paramsMap.clear();
		paramsMap.put("recommendUserid", user.getId().toString());
		//为空情况
		PageModel pageModel = recommendRewardService.getRewardPage(paramsMap);
		if(pageModel != null){
			List<Map> dataList = new LinkedList<Map>();
			for (Iterator<Map> i = pageModel.getList().iterator(); i.hasNext();) {
				Map<String, Object> curMap = i.next();
				String recommendMoney = curMap.get("recommendMoney").toString();
				totalIncome = totalIncome.add(new BigDecimal(recommendMoney));
			}
		}
		//红包收益添加到累计收益里面
		Integer[] status2 = new Integer[]{RedenvelopesRecord.STATUS_HAS_OPEN};
		BigDecimal redMoney2 = redenvelopesService.getRedSumMoneyByUsrIdAndStatus(user.getId(), status2);
		totalIncome = totalIncome.add(redMoney2);
		model.addAttribute("totalIncome", totalIncome.setScale(2, RoundingMode.HALF_UP));
		//待还本息
		model.addAttribute("repayH",userAccount.getWaitRepayCapital().add(userAccount.getWaitRepayInterest()).setScale(2, RoundingMode.HALF_UP));
		//代收本息
		model.addAttribute("totalH",userAccount.getWaitRepossessedCapital().add(userAccount.getWaitRepossessedInterest()).setScale(2, RoundingMode.HALF_UP));
		
		
		//获取个人体验金状态
		paramsMap.clear();
		paramsMap.put("userId", user.getId().toString());
		ExperienceGold experienceGoldUser = experienceGoldService.getExperienceGoldByParam(paramsMap);
		
		if(experienceGoldUser!=null){
			Date experienceGoldRepaytime = experienceGoldUser.getExperienceGoldRepaytime();
			boolean flag = experienceGoldRepaytime.after(new Date());
			if(flag==false){
					model.addAttribute("isOverTime", 1);
			}
			model.addAttribute("experienceGoldInt", experienceGoldUser.getExperienceGoldInterest().longValue());
		}
		
		model.addAttribute("experienceGoldUser", experienceGoldUser);
		return "userinfo/account/accountIndex";
	}

	/**
	 * 资金交易记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("accountLog.do")
	public String accountLog(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		/*
		 * Map<String,String> paramsMap = getParameters(request);
		 * 
		 * // 获得当前登录用户的rightId下的子权限
		 */
		Map<String, String> paramsMap = getParameters(request);
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		PageModel pageModel = accountLogService.getAccountLog(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		return "userinfo/account/transcationRecord";
	}

	/**
	 * 交易记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("transcationRecord.do")
	public String transcationRecord(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/account/transcationRecord";
	}

	@RequestMapping("transcationRecordPage.do")
	public void transcationRecordPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		String tradeCodeStr = "";
		if(paramsMap.containsKey("tradeCode")){
			tradeCodeStr = paramsMap.get("tradeCode");
		}
		
		StringBuffer sql = new StringBuffer();
		if(!"".equals(tradeCodeStr)){
			String tradeCode[] = {};
			sql.append("(");
			if(tradeCodeStr.indexOf(",")>0){
				tradeCode = tradeCodeStr.split(",");
			}else{
				sql.append("'").append(tradeCodeStr).append("'").append(",");
			}
			for(int i= 0;i<tradeCode.length;i++){
				sql.append("'").append(tradeCode[i] ).append("'").append(",");
			}
			sql.deleteCharAt(sql.toString().length()-1);
			sql.append(")");
		}
		paramsMap.remove("tradeCode");
		paramsMap.put("tradeCode", sql.toString());
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		PageModel pageModel = accountLogService.getAccountLog(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);

	}
	
	/**
	 * 用户竞拍记录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toShowBorrowTransfer.do")
	public String toShowBorrowTransfer(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/borrowTransfer/toShowBorrowTransfer";
	}
	
	
	
	@RequestMapping("getBorrowTransferByUserId.do")
	public void getBorrowTransferByUserId(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		User user=this.loginFrontUser(request);
		param.put("userId", user.getId().toString());
		PageModel page = borrowTransferService
					.selectBorrowTransferByUserId(param);
		SpringUtils.renderJson(response, page);
	}
	
	
	
	/**
	 * 充值提现
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("cashValue.do")
	public String cashValue(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		UserAccount userAccount = this.userAccountService.getByUserId(this
				.loginFrontUser(request).getId());
		model.addAttribute("userAccount", userAccount);
		return "userinfo/account/cashValue";
	}
	
	
	/**
	 * 充值记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("rechargeLog.do")
	public String rechargeLog(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/account/rechargeLog";
	}
	
	/**
	 * 提现记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("withdrawLog.do")
	public String withdrawLog(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/account/withdrawLog";
	}
	
	

	@RequestMapping("cashValuePage.do")
	public void cashValuePage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		if (paramsMap.get("cxType") == null) {
			paramsMap.put("cxType", "0");
		}
		PageModel pageModel = accountCashService.selectCashAndRecharge(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);
	}
	/**
	 * 撤销提现
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("qxCashValue.do")
	public String qxCashValue(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		boolean flag=false;
		flag=this.accountCashService.cancelCash(this.loginFrontUser(request).getId(), Integer.parseInt(paramsMap.get("cid")),new BigDecimal(paramsMap.get("toMoney")));
		return "userinfo/account/withdrawLog";
	}
	
	/**
	 * 充值
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("recharge.do")
	public String recharge(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> params = new HashMap<String, String>();
		User user = this.loginFrontUser(request);
		params.put("userId", user.getId().toString());
		UserBank userBank = userBankService.getByUserId(params);
		if(userBank==null){
			model.addAttribute("bankCheck", "ok");
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
		param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
		//客服热线
		model.addAttribute("websitetel", SysCacheUtils.getSysConfig().getSysWebsitetel());
		
		//个人网银借充值方式查询
		Map<String,String> paramsMap = new HashMap<String, String>();
		paramsMap.put("paymentStatus", PaymentConfig.STATUS_START.toString());
		paramsMap.put("paymentType", PaymentConfig.PAYMENT_TYPE_OOF.toString());
		paramsMap.put("paymentCodePreffix", "30");
		List<PaymentConfig> paymentConfigList=paymentConfigService.getOnlineList(paramsMap);
		model.addAttribute("paymentConfigList",paymentConfigList);
		//企业网银充值方式查询
		paramsMap.put("paymentCodePreffix", "60");
		paymentConfigList=paymentConfigService.getOnlineList(paramsMap);
		model.addAttribute("paymentConfigListERP",paymentConfigList);
		// 线上支付
		List<PaymentConfig> list = paymentConfigService.getOnlineList(param);
		model.addAttribute("list", list);
		System.out.println(list);
		// 用户账户
		// UserAccount userAccount=this.userAccountService.getByUserId(13);
		UserAccount userAccount = this.userAccountService.getByUserId(this
				.loginFrontUser(request).getId());
		model.addAttribute("userAccount", userAccount);
		// 获取在线充值手续费
		SysFeesRate sys = SysCacheUtils.getSysFeesRate();
		model.addAttribute("sysOnline", sys.getSysOnlinePoundage());
		String succ = request.getParameter("succ");
		if(succ!=null){
			if(succ.equals("true")){
				request.setAttribute("message", "充值成功！");
			}else{
				request.setAttribute("message", "充值失败！");
			}
		}
		return "userinfo/account/recharge";
	}

	
	/**
	 * 提现
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("withdraw.do")
	public String withdraw(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = new HashMap<String, String>();
		User user=this.userService.getById(this.loginFrontUser(request).getId());
		param.put("userId", user.getId().toString());
		String result="";
		//判断用户是否实名认证
		if(user.getRealnameStatus()!=User.REALNAME_PASS){
			result="redirect:/basics/personalData.do?type=1";
		}else if(user.getPhoneStatus()!=User.PHONE_PASS){
			result="redirect:/basics/personalData.do?type=3";
		}else{
			// 用户银行卡
			UserBank userBank = this.userBankService.getByUserId(param);
			// 判断该用户是否已添加银行卡
			if (userBank != null) {
				// 用户账户
				UserAccount userAccount = this.userAccountService.getByUserId(this.loginFrontUser(request).getId());
				// 费率
				SysFeesRate sFRate = SysCacheUtils.getSysFeesRate();
				model.addAttribute("sFRate", sFRate);
				model.addAttribute("userAccount", userAccount);
				model.addAttribute("userBank", userBank);
				AccountCash accountCash = this.accountCashService.selectAccountCashByUserIdStatus(param);
				Integer accountCashid;
				if (accountCash != null) {
					accountCashid = 1;// 显示提现审核中
				} else {
					accountCashid = 2;// 显示提现申请
				}
				model.addAttribute("accountCashid", accountCashid);
				model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
				model.addAttribute("cashTypeFee", AccountCash.cashTypeFee);
				/*BigDecimal freeCashMoney=this.accountCashService.freeCashMoneys(user.getId());
				model.addAttribute("freeCashMoney", freeCashMoney);*/
				result= "userinfo/account/withdraw";
			} else {
				model.addAttribute("bankCheck", "ok");
				//result= "redirect:/account/bankCard.do";
				result= "userinfo/account/withdraw";
			}
		}
		return result;

	}

	/**
	 * ajax计算提现手续费
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("calcCashFee.do")
	public void calcCashFee(HttpServletRequest request,
			HttpServletResponse response, Model model){
		User user = this.loginFrontUser(request);
		BigDecimal cashMoney = new BigDecimal(request.getParameter("cashTotal"));
		BigDecimal fee = this.accountCashService.getCashFee(user.getId()
						, cashMoney, null);
		SpringUtils.renderText(response, DecimalUtils.getTwoDecimal(fee));
	}
	
	/**
	 * ajax计算提现手续费
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("calcCashFees.do")
	public void calsCashFees(HttpServletRequest request,
			HttpServletResponse response, Model model){
		User user = this.loginFrontUser(request);
		BigDecimal cashMoney = new BigDecimal(request.getParameter("cashTotal"));
		BigDecimal fee = this.cashStageService.getCashFee(cashMoney);
		SpringUtils.renderText(response, DecimalUtils.getTwoDecimal(fee));
	}
	/**
	 * 提交提现申请
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("applyWithDraw.do")
	public void applyWithDraw(HttpServletRequest request,HttpServletResponse response, Model model, AccountCash accountCash) {
		JsonResult json=new JsonResult();
		User user = this.loginFrontUser(request);
		String noOrder = GenerateNo.payRecordNo(user.getId());
		
		try{
			
			Map<String, String> param = this.getParameters(request);
			param.put("userId", this.loginFrontUser(request).getId().toString());
			param.put("noOrder", noOrder);
			accountCash.setCashAddip(this.getIpAddr(request));
			Map<String ,String> map=new HashMap<String, String>();
			map.put("userId", this.loginFrontUser(request).getId().toString());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			map.put("thisTime", sdf.format(new Date()));
			Map m=new HashMap();
			m.putAll(map);
			m.put("cashStatus",  new Integer[]{AccountCash.cashApply,AccountCash.cashSuccess});
			//今日已经提现或申请的
			BigDecimal todayTotal=this.accountCashService.selectWithDrawTotal(m);
			if(todayTotal==null){
				todayTotal=new BigDecimal(0);
			}
			DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
			DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
			if ("".equals(user.getUserPaypassword())
					|| user.getUserPaypassword() == null) {
				json.setCode("212");
				json.setMsg("你的交易密码为初始密码，请先修改交易密码！");
				logger.error("你的交易密码为初始密码，请先修改交易密码！");
			}else{
				if(param!=null){
					String payPassword = desEncrpt.decrypt(param.get("payPassword").toString());
					if(user.getUserPaypassword().equals(MD5Utils.stringToMD5(aesEncrypt.encrypt(payPassword)))){
						//账户
						UserAccount userAccount = this.userAccountService.selectByUserIdForUpdate(this.loginFrontUser(request).getId());
						// 费率
						SysFeesRate sFRate = SysCacheUtils.getSysFeesRate();
						//大于0
						if(CompareUtils.greaterThanZero(accountCash.getCashTotal())){
							//最小提现金额
							if(CompareUtils.greaterEquals(accountCash.getCashTotal(), sFRate.getSysMinWithdrawal())){
								//今日提现总额
								BigDecimal total=todayTotal.add(accountCash.getCashTotal());
								//最大提现金额
								if(CompareUtils.greaterEquals(sFRate.getSysMaxWithdrawal(), accountCash.getCashTotal())){
									if(CompareUtils.greaterEquals(sFRate.getSysMaxWithdrawal(), total)){
										//用户可用余额
										if(CompareUtils.greaterEquals( userAccount.getAvailableMoney(),accountCash.getCashTotal())){
											BigDecimal delaySumMoney=new BigDecimal(0);
											param.put("code", Borrow.BORROW_TYPE_JING);
											//只查初审通过
											List<Borrow> borrowList=borrowQueryService.getBorrowListByUserIdANDBorrowTypeCode(param);
											
											for (Borrow borrow : borrowList) {
												BigDecimal	delayMoney= borrow.getBorrowSum().subtract(borrow.getPaidAmount()).subtract(borrow.getPaidInterest());
												delaySumMoney=delaySumMoney.add(delayMoney);
											}
											delaySumMoney = delaySumMoney.multiply(new BigDecimal(1.1)); //10%
											
											if(CompareUtils.greaterThanAndEqualsZero(userAccount.getAvailableMoney().subtract(delaySumMoney))){
												accountCash.setUserId(user.getId());
												boolean flag = this.accountCashService.saveAccountCash(accountCash,param);
												
												/*CashBean reqBean = new CashBean();
										        reqBean.setApi_version(PropertiesUtil.get("cash.VERSION"));
										        reqBean.setOid_partner(PropertiesUtil.get("cash.OID_PARTNER"));
										        reqBean.setSign_type(PropertiesUtil.get("cash.SIGN_TYPE"));
										        reqBean.setNo_order(noOrder);
										        reqBean.setUser_id(user.getId().toString());
										        reqBean.setAcct_name(user.getUserRealname());
										        reqBean.setCard_no("6222081202007688888");
										        reqBean.setBank_code("03050001");
										        reqBean.setNo_order(ApiUtils.getCurrentDateTimeStr());
										        reqBean.setDt_order(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
										        reqBean.setCity_code("110001");
										        // reqBean.setPrcptcd("12345678901");
										        reqBean.setBrabank_name("运城车站支行");
										        reqBean.setMoney_order(param.get("cashTotal"));
										        reqBean.setFlag_card("0");
										        reqBean.setInfo_order("测试");
										        reqBean.setNotify_url("www.sina.com");
										        reqBean.setSign(genSign(JSON.parseObject(JSON.toJSONString(reqBean))));
										        String reqJson = JSON.toJSONString(reqBean);

										        HttpRequestSimple httpclent = new HttpRequestSimple();
										        String resJson = httpclent.postSendHttp(PropertiesUtil.get("cash.URL"),
										                reqJson);

										        System.out.println("结果报文为:" + resJson);*/
												/**
												 * 记录日志
												 */
												User loginUser=this.loginFrontUser(request);
												OperatorLog operatorLog = new OperatorLog();
												operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
												operatorLog.setOperatorTitle("提现申请");
												operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
												operatorLog.setOperatorCategory(OperatorLog.CATEGORY_DRAW);
												operatorLog.setOperatorParams(accountCash.toString());
												operatorLog.setOperatorReturn(flag?"添加提现申请成功":"添加提现申请失败");
												operatorLog.setOperatorStatus(flag?200:300);
												operatorLog.setLinkUrl(RequestUtils.getIpAddr());
												operatorLog.setOperatorIp(this.getIpAddr(request));
												operatorLogService.addFrontLog(operatorLog);
												if(flag){
													json.setCode("204");
													json.setMsg("提现申请正在处理，请耐心等待");
													logger.info("提现申请成功！");
												}else{
													json.setCode("205");
													json.setMsg("提现申请失败！");
													logger.error("提现申请失败！");
												}
											}else{
												json.setCode("206");
												json.setMsg("账户可用净资产不足！您当前可提现"+delaySumMoney.setScale(2, BigDecimal.ROUND_DOWN)+"元。");
												logger.error("账户可用净资产不足！");
											}
										}else{
											json.setCode("206");
											json.setMsg("账户可用余额不足！");
											logger.error("账户可用余额不足！");
										}
									}else{
										json.setCode("210");
										json.setMsg("每日最大提现金额为"+sFRate.getSysMaxWithdrawal()+"元");
										logger.error("每日最大提现金额为"+sFRate.getSysMaxWithdrawal()+"元");
									}
								}else{
									json.setCode("202");
									json.setMsg("最大提现金额为"+sFRate.getSysMaxWithdrawal()+"元");
									logger.error("最大提现金额为"+sFRate.getSysMaxWithdrawal()+"元");
								}
								
							}else{
								json.setCode("201");
								json.setMsg("最小提现金额为"+sFRate.getSysMinWithdrawal()+"元");
								logger.error("最小提现金额为"+sFRate.getSysMinWithdrawal()+"元");
							}
							
						}else{
							json.setCode("200");
							json.setMsg("提现金额必须大于"+sFRate.getSysMinWithdrawal()+"元");
							logger.error("提现金额必须大于"+sFRate.getSysMinWithdrawal()+"元");
						}
					}else{
						json.setCode("211");
						json.setMsg("交易密码错误");
						logger.error("交易密码错误");
					}
				}
			}
			
			
			
			
		}catch(Exception e){
			
		}
		SpringUtils.renderJson(response, json);
	}
	 private static String genSign(JSONObject reqObj)
	    {
	        String sign = reqObj.getString("sign");
	        String sign_type = reqObj.getString("sign_type");
	        // // 生成待签名串
	        String sign_src = genSignData(reqObj);
	        System.out.println("商户[" + reqObj.getString("oid_partner") + "]待签名原串"
	                + sign_src);
	        if ("MD5".equals(sign_type))
	        {
	            sign_src += "&key=" + PropertiesUtil.get("cash.TRADER_MD5_KEY");
	            return signMD5(sign_src);
	        }
	        if ("RSA".equals(sign_type))
	        {
	            return getSignRSA(sign_src);
	        }
	        return null;
	    }

	    private static String signMD5(String signSrc)
	    {

	        try
	        {
	            return Md5Algorithm.getInstance().md5Digest(
	                    signSrc.getBytes("utf-8"));
	        } catch (UnsupportedEncodingException e)
	        {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    /**
	     * RSA签名验证
	     * 
	     * @param reqObj
	     * @return
	     */
	    public static String getSignRSA(String sign_src)
	    {
	        return TraderRSAUtil.sign(PropertiesUtil.get("cash.prikeyvalue"), sign_src);

	    }

	    /**
	     * 生成待签名串
	     * 
	     * @param paramMap
	     * @return
	     */
	    public static String genSignData(JSONObject jsonObject)
	    {
	        StringBuffer content = new StringBuffer();

	        // 按照key做首字母升序排列
	        List<String> keys = new ArrayList<String>(jsonObject.keySet());
	        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
	        for (int i = 0; i < keys.size(); i++)
	        {
	            String key = (String) keys.get(i);
	            // sign 和ip_client 不参与签名
	            if ("sign".equals(key))
	            {
	                continue;
	            }
	            String value = (String) jsonObject.getString(key);
	            // 空串不参与签名
	            if (null == value)
	            {
	                continue;
	            }
	            content.append((i == 0 ? "" : "&") + key + "=" + value);

	        }
	        String signSrc = content.toString();
	        if (signSrc.startsWith("&"))
	        {
	            signSrc = signSrc.replaceFirst("&", "");
	        }
	        return signSrc;
	    }
	/**
	 * 银行卡
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("bankCard.do")
	public String bankCard(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = new HashMap<String, String>();
		User user=this.userService.getById(this.loginFrontUser(request).getId());
		param.put("userId", user.getId().toString());
		String result="";
		//判断用户是否实名认证
		if(user.getRealnameStatus()!=User.REALNAME_PASS){
			result="redirect:/basics/personalData.do";
		}else if(user.getPhoneStatus()!=User.PHONE_PASS){
			result="redirect:/basics/personalData.do";
		}else{
			param.put("userId", this.loginFrontUser(request).getId().toString());
			UserBank userBank = this.userBankService.getByUserId(param);
			// 判断该用户是否已添加银行卡
			if (userBank != null) {
				String [] strs = userBank.getBankCity().split("\\|");
				
				userBank.setBankCity(strs[0]+strs[1].toString());
				model.addAttribute("userBank", userBank);
				
				Map<String,String> paramsMap = new HashMap<String,String>();
				StringBuffer sql = new StringBuffer();
				sql.append("('-1')");//查询处理该申请是否在解绑中
				paramsMap.put("result", sql.toString());
				paramsMap.put("bankAccount", userBank.getBankAccount());
				RelaseCard relaseCard = relaseBankService.getByParam(paramsMap);
				boolean bl = false;
				if(relaseCard!=null&&!"".equals(relaseCard.getId())){
					bl = true;
				}
				model.addAttribute("isRelease", bl);//是否申请中
			} 
			List<AllBank> list = this.allBankService.getAllList();
			model.addAttribute("user", user);
			model.addAttribute("bank", list);
			result= "userinfo/account/bankCard";
		}
		return result;
		
	}

	
	/**
	 * 跳转到解绑银行卡
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toRelaseCard.do")
	public String toRelaseCard(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = new HashMap<String, String>();
		User user=this.userService.getById(this.loginFrontUser(request).getId());
		param.put("userId", user.getId().toString());
		String result="";
		//判断用户是否实名认证
		if(user.getRealnameStatus()!=User.REALNAME_PASS){
			result="redirect:/basics/personalData.do";
		}else if(user.getPhoneStatus()!=User.PHONE_PASS){
			result="redirect:/basics/personalData.do";
		}else{
			result= "userinfo/account/relaseBankCard";
		}
		return result;
	}
	
	/** 
	 * 新增 - 提交 – 只保存文件到服务器上 
	 */  
	@RequestMapping(value = "relaseCard.do", method = RequestMethod.POST)  
	public void relaseCard(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value = "cardPic", required = false) MultipartFile filedata, Model model) {
		Map<String, String> param = new HashMap<String, String>();
		User user=this.userService.getById(this.loginFrontUser(request).getId());
		String relaseReason = request.getParameter("relaseReason");
		param.put("userId", user.getId().toString());
		//判断用户是否实名认证
		if(user.getRealnameStatus()!=User.REALNAME_PASS){
			try {
				response.sendRedirect("/basics/personalData.do");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(user.getPhoneStatus()!=User.PHONE_PASS){
			try {
				response.sendRedirect("/basics/personalData.do");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			param.put("userId", this.loginFrontUser(request).getId().toString());
			UserBank userBank = this.userBankService.getByUserId(param);
			// 判断该用户是否已添加银行卡
			if (userBank != null) {
				// 保存相对路径到数据库 图片写入服务器
		        if (filedata != null && !filedata.isEmpty()) {
		            // 获取图片的文件名
		            String fileName = filedata.getOriginalFilename();
		            // 获取图片的扩展名
		            String extensionName = fileName
		                    .substring(fileName.lastIndexOf(".") + 1);
		            // 新的图片文件名 = 获取时间戳+"."图片扩展名
		            String newFileName = String.valueOf(System.currentTimeMillis())
		                    + "." + extensionName;
		            String patch = saveFile(newFileName, filedata);
		            RelaseCard relaseCard = new RelaseCard();
		            relaseCard.setUserId(user.getId());
		            relaseCard.setUserBankId(userBank.getId());
		            relaseCard.setCardPath(patch);
		            relaseCard.setReReason(relaseReason);
		            relaseCard.setBankAccount(userBank.getBankAccount());
		            relaseCard.setBankBranch(userBank.getBankBranch());
		            relaseCard.setBankCity("");
		            relaseCard.setBankName(userBank.getAllBank().getBankName());
		            relaseCard.setBankUserId(userBank.getUser().getId());
		            relaseCard.setResult(relaseCard.RESULT_APPLY);
		            relaseCard.setRelasetime(new Date());
		            
		            boolean bl = relaseBankService.relaseBank(relaseCard);
		            if(bl){
		            	try {
							response.sendRedirect(request.getContextPath()+"/account/bankCard.html");
						} catch (IOException e) {
							e.printStackTrace();
						}
		            }
		            User loginUser=this.loginFrontUser(request);
					OperatorLog operatorLog = new OperatorLog();
					operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
					operatorLog.setOperatorTitle("解绑银行卡申请");
					operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
					operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BANKCARD);
					operatorLog.setOperatorParams(userBank.toString());
					operatorLog.setOperatorReturn(bl?"解绑银行卡申请成功":"解绑银行卡申请失败");
					operatorLog.setOperatorStatus(bl?200:300);
					operatorLog.setLinkUrl(RequestUtils.getIpAddr());
					operatorLog.setOperatorIp(this.getIpAddr(request));
					operatorLogService.addFrontLog(operatorLog);
		        }
			} 
		}
	}
	
    private String saveFile(String newFileName, MultipartFile filedata) {
        String saveFilePath = UploadUtils.getRealPath();
 
        /* 构建文件目录 */
        File fileDir = new File(saveFilePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
 
        try {
            FileOutputStream out = new FileOutputStream(saveFilePath + File.separator 
                    + newFileName);
            // 写入文件
            out.write(filedata.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String resultFilePath = UploadUtils.getRelatedPath() + "/" + newFileName;
        if (StringUtils.isNotBlank(resultFilePath)) {
			resultFilePath = resultFilePath.replaceAll("\\\\", "/");
		}
        return resultFilePath;
    }
	
	/**
	 * 添加银行卡
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userBank
	 */
	@RequestMapping("addBankCard.do")
	public void addBankCard(HttpServletRequest request,
			HttpServletResponse response, Model model, UserBank userBank) {
		@SuppressWarnings("unused")
		Map<String, String> param = this.getParameters(request);
		param.put("userId", this.loginFrontUser(request).getId().toString());
		boolean flag = false;
		String ip;
		JsonResult json=new JsonResult();
		try {
			ip = this.getIpAddr(request);
			userBank.setBankAddip(ip);
			userBank.setBankAddtime(new Date());
			UserBank bank = this.userBankService.getByUserId(param);
			if(bank==null){
				if(userBank.getBankId()!=null && userBank.getBankCity()!=null && userBank.getBankAccount()!=null){
					flag = this.userBankService.saveOrUpdate(userBank);
					if(flag){
						json.setCode("100");
						json.setMsg("银行卡添加成功");
					}else{
						json.setCode("101");
						json.setMsg("银行卡添加失败");
					}
				}else{
					flag=false;
					json.setCode("102");
					json.setMsg("银行卡信息填写不完整");
				}
			}else{
				flag=false;
				json.setCode("103");
				json.setMsg("该用户已添加银行卡");
			}
			/**
			 * 记录日志
			 */
			User loginUser=this.loginFrontUser(request);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
			operatorLog.setOperatorTitle("添加银行卡");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BANKCARD);
			operatorLog.setOperatorParams(userBank.toString());
			operatorLog.setOperatorReturn(flag?"添加银行卡成功":"添加银行卡失败");
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
		} catch (Exception e) {
			flag = false;
		}
		SpringUtils.renderJson(response, json);

		/*@SuppressWarnings("unused")
		Map<String, String> param = this.getParameters(request);
		param.put("userId", this.loginFrontUser(request).getId().toString());
		boolean flag = false;
		String ip;
		JsonResult json=new JsonResult();
		try {
			ip = this.getIpAddr(request);
			userBank.setBankAddip(ip);
			userBank.setBankAddtime(new Date());
			UserBank bank = this.userBankService.getByUserId(param);
			if(bank==null){
				if(userBank.getBankBranch()!=null && userBank.getBankAccount()!=null){
					flag = this.userBankService.saveOrUpdate(userBank);
					if(flag){
						json.setCode("100");
						json.setMsg("银行卡添加成功");
					}else{
						json.setCode("101");
						json.setMsg("银行卡添加失败");
					}
				}else{
					flag=false;
					json.setCode("102");
					json.setMsg("银行卡信息填写不完整");
				}
			}else{
				flag=false;
				json.setCode("103");
				json.setMsg("该用户已添加银行卡");
			}
			*//**
			 * 记录日志
			 *//*
			User loginUser=this.loginFrontUser(request);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
			operatorLog.setOperatorTitle("添加银行卡");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BANKCARD);
			operatorLog.setOperatorParams(userBank.toString());
			operatorLog.setOperatorReturn(flag?"添加银行卡成功":"添加银行卡失败");
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
		} catch (Exception e) {
			flag = false;
		}
		SpringUtils.renderJson(response, json);*/
	}

	// 账户
	@RequestMapping("userLeft.do")
	public String userLeft(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		UserAccount userAccount = this.userAccountService.getByUserId(this
				.loginFrontUser(request).getId());
		model.addAttribute("userAccount", userAccount);
		model.addAttribute("user",userService.getById(this.loginFrontUser(request).getId()));
		return "userinfo/userLeft";
	}

	// 验证
	@RequestMapping("userTop.do")
	public String attestationLeft(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/userTop";
	}
	/**
	 * 线下充值
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param accountRecharge
	 * @return
	 */
	@RequestMapping(value = "xianxiaRecharge.do", method = RequestMethod.POST)
	public void xianxiaRecharge(HttpServletRequest request,
			HttpServletResponse response, Model model,
			AccountRecharge accountRecharge) {
		Map<String, String> param = this.getParameters(request);
		User user = this.loginFrontUser(request);
		//线下充值
		if(param.get("rechargeType").equals(AccountRecharge.RECHARGE_TYPE_OFF)){
			boolean flag=false;
			AccountRecharge accoun = new AccountRecharge();
			accoun.setUserId(this.loginFrontUser(request).getId());
			accoun.setRechargeType(AccountRecharge.RECHARGE_TYPE_OFF);//线下充值
			
			String rechargeTradeNo=SequenceUtils.payRecordNo(this.loginFrontUser(request).getId());
			accoun.setRechargeTradeNo(rechargeTradeNo);
			BigDecimal big = new BigDecimal(param.get("rechargeMoney"));
			accoun.setRechargeMoney(big);
			accoun.setRechargePayment(param.get("rechargePayment"));
			accoun.setRechargeStatus(AccountRecharge.RECHAREGE_STATUS_WAIT_CHAECK);
			accoun.setRechargeType(AccountRecharge.RECHARGE_TYPE_OFF);
			accoun.setRechargeRemark(accountRecharge.getRechargeRemark()+".线下充值：充值金额："+big);
			accoun.setRechargeAddip(this.getIpAddr(request));
			accoun.setRechargeAddtime(new Date());
			
			flag=accountRechargeService.saveAccountRecharge(accoun,true);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid(user.getUserAccount());
			operatorLog.setOperatorTitle("添加线下充值");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_RECHARGE);
			operatorLog.setOperatorParams(user.toString());
			operatorLog.setOperatorReturn(flag?"添加线下充值成功":"添加线下充值失败");
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
			if(flag){
				JsonResult jsonResult=new JsonResult();
				jsonResult.setCode("200");
				jsonResult.setMsg("线下充值成功，等待后台审核！");
				SpringUtils.renderJson(response, jsonResult);
				
				
			}
		}
	}
	/**
	 * 在线充值
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param accountRecharge
	 * @return
	 */
	@RequestMapping(value = "onlineRecharge.do", method = RequestMethod.POST)
	public String onlineRecharge(HttpServletRequest request,
			HttpServletResponse response, Model model,
			AccountRecharge accountRecharge) {
		Map<String, String> param = this.getParameters(request);
		User user = this.loginFrontUser(request);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", user.getId().toString());
		UserBank userBank = this.userBankService.getByUserId(params);
		//线下充值
		if(param.get("rechargeType").equals(AccountRecharge.RECHARGE_TYPE_OFF)){
			boolean flag=false;
			AccountRecharge accoun = new AccountRecharge();
			accoun.setUserId(this.loginFrontUser(request).getId());
			accoun.setRechargeType(AccountRecharge.RECHARGE_TYPE_OFF);//线下充值
			
			String rechargeTradeNo=SequenceUtils.payRecordNo(this.loginFrontUser(request).getId());
			accoun.setRechargeTradeNo(rechargeTradeNo);
			BigDecimal big = new BigDecimal(param.get("rechargeMoney"));
			accoun.setRechargeMoney(big);
			accoun.setRechargePayment(param.get("rechargePayment"));
			accoun.setRechargeStatus(AccountRecharge.RECHAREGE_STATUS_WAIT_CHAECK);
			accoun.setRechargeType(AccountRecharge.RECHARGE_TYPE_OFF);
			accoun.setRechargeRemark(accountRecharge.getRechargeRemark()+".线下充值：充值金额："+big);
			accoun.setRechargeAddip(this.getIpAddr(request));
			accoun.setRechargeAddtime(new Date());
			
			flag=accountRechargeService.saveAccountRecharge(accoun,true);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid(user.getUserAccount());
			operatorLog.setOperatorTitle("添加线下充值");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_RECHARGE);
			operatorLog.setOperatorParams(user.toString());
			operatorLog.setOperatorReturn(flag?"添加线下充值成功":"添加线下充值失败");
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
			if(flag){
				JsonResult jsonResult=new JsonResult();
				jsonResult.setCode("200");
				jsonResult.setMsg("线下充值成功，等待后台审核！");
				SpringUtils.renderJson(response, jsonResult);
				return null;
				
			}
		}else{
			String clientIP =this.getIpAddr(request);
			if (clientIP.equals("0:0:0:0:0:0:0:1")) { //localhost取到的IP需转换一下
			}
			clientIP = "127_0_0_1";
			String payment = request.getParameter("rechargeId");
			String payID = request.getParameter("rechargePayment");
			// String serialNumber = GenerateNo.payRecordNo(13);
			String serialNumber=null;
			if(!payment.equals("5")){
				 serialNumber= GenerateNo.payRecordNo(user.getId());
				}else if(payment.equals("5")){
				  serialNumber = GenerateNo.payRecordNo();
				}
			User u = this.loginFrontUser(request);
			Date curDate = new Date();
			accountRecharge.setRechargeTradeNo(serialNumber);
			accountRecharge.setUserId(user.getId());
			accountRecharge.setRechargeAddtime(curDate);
			accountRecharge.setRechargeAddip(clientIP);
			param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
			param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
			String remark = "";
			PaymentConfig paymentConfig = this.paymentConfigService.getById(Integer.parseInt(payment));
			//String path = request.getContextPath();
			//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
			String basePath=SysCacheUtils.getSysConfig().getSysWebsitedomain();
			if (payment.equals("1")) { // 网银在线支付
				OnlineRechargeEBank eBank = buildEBank(accountRecharge,paymentConfig,basePath);
				model.addAttribute("params", eBank);
				remark = remark + "网银在线支付";
				
			} else if (payment.equals("2")) {// 宝付支付
				OnlineRechargeBF baofu = baoFu(accountRecharge, paymentConfig,basePath, payID);
				model.addAttribute("params", baofu);
				remark = remark + "宝付支付";
			} else if (payment.equals("3")) { // 宝付支付PC
				String ret = null;
				try {
					ret = new BaoFooApi().PcFrom(request, response, serialNumber, userBank, user, accountRecharge, basePath);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				model.addAttribute("params", ret);
				remark = remark + "宝付支付PC";
				accountRecharge.setRechargeRemark(remark);
				accountRecharge.setRechargePayment(remark);
				boolean flag = accountRechargeService.saveAccountRecharge(accountRecharge,true);
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("添加线上充值");
				operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_RECHARGE);
				operatorLog.setOperatorParams(user.toString());
				operatorLog.setOperatorReturn(flag?"添加线上充值成功":"添加线上充值失败");
				operatorLog.setOperatorStatus(flag?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addFrontLog(operatorLog);
				if (flag) {
					model.addAttribute("payType", payment);
					return "userinfo/account/gotoOnlineRechargeBFPC";
				}
			} else if (payment.equals("4")) {// 双乾支付
				OnlineRechargeSQ dMoney = buildDoubleMoney(accountRecharge,
						paymentConfig,basePath);
				model.addAttribute("params", dMoney);
				remark = remark + "双乾支付";
			} else if (payment.equals("5")) {// 银联网上支付
				OnlineRechargeChinaPay chinaPay = buildChinaPay(accountRecharge,paymentConfig,basePath);
				model.addAttribute("params", chinaPay);
				remark = remark + "银联网上支付";
			}else if (payment.equals("6")) {// 汇潮支付
				OnlineRechargeHC hc = huiChao(accountRecharge,paymentConfig,basePath);
				model.addAttribute("params", hc);
				remark = remark + "汇潮网上支付";
			}else if (payment.equals("7")) {// 贝付支付
				OnlineRechargeBeiFu beifu= beifu(accountRecharge,paymentConfig,basePath,clientIP,payment);
				model.addAttribute("params", beifu);
				remark = remark + "贝付网上支付";
			}else if (payment.equals("8")) {// 汇付宝
				OnlineRechargeHFB huifubao = huifubao(accountRecharge, clientIP,
						paymentConfig, basePath);
				model.addAttribute("params", huifubao);
				remark = remark + "汇付宝网上支付";
			}else if(payment.equals("9")){//铂金pos平台支付
				OnlineRechargePOSPT pospt = pospt(accountRecharge,paymentConfig,basePath);
				model.addAttribute("params", pospt);
				remark = remark + "财付通网上支付";
			}else if (payment.equals("11")) {// 新浪支付
				OnlineRechargeSina sina= sina(accountRecharge,paymentConfig,basePath,clientIP);
				model.addAttribute("params", sina);
				remark = remark + " 新浪网上支付";
			}else if(payment.equals("12")){//易宝支付
				OnlineRechargeYeePay yeepay = yeepay(accountRecharge,paymentConfig,basePath);
				model.addAttribute("params", yeepay);
				remark = remark + "易宝网上支付";
			}else if(payment.equals("17")){//易宝支付
				OnlineRechargeFengFu fengfu = fengfu(accountRecharge,paymentConfig,basePath);
				model.addAttribute("params", fengfu);
				remark = remark + "丰付网上支付";
			}else if(payment.equals("19")){//易宝支付
				OnlineRechargeXinSheng xinsheng = xinsheng(accountRecharge,paymentConfig,basePath);
				model.addAttribute("params", xinsheng);
				remark = remark + "新生网上支付";
			}
			accountRecharge.setRechargeRemark(remark);
			accountRecharge.setRechargePayment(remark);
			boolean flag = accountRechargeService.saveAccountRecharge(accountRecharge,true);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid(user.getUserAccount());
			operatorLog.setOperatorTitle("添加线上充值");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_RECHARGE);
			operatorLog.setOperatorParams(user.toString());
			operatorLog.setOperatorReturn(flag?"添加线上充值成功":"添加线上充值失败");
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (flag) {
				model.addAttribute("payType", payment);
				return "userinfo/account/gotoOnlineRecharge";
			}
		}
		return null;
	}
	 private String createRiskItem()
	    {
	        JSONObject riskItemObj = new JSONObject();
	        riskItemObj.put("user_info_full_name", "你好");
	        riskItemObj.put("frms_ware_category", "1999");
	        return riskItemObj.toString();
	    }
	@SuppressWarnings("unused")
	private OnlineRechargeLianLian getLianLian( User user,AccountRecharge accountRecharge,
			PaymentConfig paymentConfig, String basePath){
		// 构造支付请求对象
		OnlineRechargeLianLian lianlian  = new OnlineRechargeLianLian();
		/*lianlian.setGatewayUrl(paymentConfig.getGatewaUrl());*/
		lianlian.setBusi_partner(PropertiesUtil.get("BUSI_PARTNER"));
		lianlian.setDt_order(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accountRecharge.getRechargeAddtime()).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
		lianlian.setMoney_order(accountRecharge.getRechargeMoney().toString());
		lianlian.setName_goods("线上充值");
		lianlian.setNo_order(accountRecharge.getRechargeTradeNo());
		lianlian.setNotify_url(basePath+PropertiesUtil.get("NOTIFY_URL"));
		lianlian.setOid_partner(paymentConfig.getClientId());
		lianlian.setRisk_item(createRiskItem());
		lianlian.setSign_type(PropertiesUtil.get("SIGN_TYPE"));
		lianlian.setTimestamp(LLPayUtil.getCurrentDateTimeStr());
		lianlian.setUrl_return(basePath+PropertiesUtil.get("URL_RETURN"));
		lianlian.setUser_id(user.getId().toString());
		lianlian.setUserreq_ip("127_0_0_1");
		lianlian.setValid_order("10080");// 单位分钟，可以为空，默认7天
		lianlian.setVersion("1.0");
		lianlian.setInfo_order(accountRecharge.getRechargeRemark());
		
		
		
		/*lianlian.setCard_no("6227002490150680092");*/
		/* lianlian.setUrl_order("");*/
        /*if (!LLPayUtil.isnull(req.getParameter("no_agree")))
        {
            lianlian.setNo_agree(req.getParameter("no_agree"));
            lianlian.setBack_url("http://www.lianlianpay.com/");
        } else
        {*/
            // 从系统中获取用户身份信息
           /* lianlian.setId_type("0");
            lianlian.setId_no(user.getCardNumber());
            lianlian.setAcct_name("连连");
            lianlian.setFlag_modify("1");
            lianlian.setBack_url("http://www.lianlianpay.com/");*/
        // 加签名
        String sign = LLPayUtil.addSign(JSON.parseObject(JSON
                .toJSONString(lianlian)), PropertiesUtil.get("TRADER_PRI_KEY"),
                paymentConfig.getEncryptKey());
        lianlian.setSign(sign);
		return lianlian;
		
	}
	//财付通--铂金
		private OnlineRechargePOSPT pospt(AccountRecharge accountRecharge,
				PaymentConfig paymentConfig, String basePath) {
			
			OnlineRechargePOSPT pospt = new OnlineRechargePOSPT();
			pospt.setGatewayUrl(paymentConfig.getGatewaUrl());
			pospt.setVersion("10");
			pospt.setReqType("OrderPay");
			pospt.setMerId(paymentConfig.getClientId());
			
			Date date = new Date();
			SimpleDateFormat formatterDate = new SimpleDateFormat("yyyyMMdd");
		    String reqDate = formatterDate.format(date);
		    
		    SimpleDateFormat formatterTime = new SimpleDateFormat("HHmmss");
		    String reqTime = formatterTime.format(date);
			pospt.setReqTime(reqTime);
			pospt.setReqDate(reqDate);
			
			
			pospt.setOrdId(accountRecharge.getRechargeTradeNo());
			pospt.setOrdAmt(accountRecharge.getRechargeMoney().toString());
			pospt.setBgRetUrl(basePath+PropertiesUtil.get("POSPT.callback.url"));
			pospt.setRetUrl(basePath+PropertiesUtil.get("POSPT.pageRetUrl"));
			pospt.setGateId("tenpay");
			pospt.setMerPriv(accountRecharge.getUserId().toString());
			pospt.setMd5Str(paymentConfig.getEncryptKey());
			String chkValue=MD5Utils.encode2hex(pospt.getSignStr());
			pospt.setChkValue(chkValue);
			
		
			return pospt;
		}
	// 新浪支付
		private OnlineRechargeSina sina(AccountRecharge accountRecharge, PaymentConfig paymentConfig,String basePath,String clientIP) {
			OnlineRechargeSina s=new OnlineRechargeSina();
			s.setGatewayUrl(paymentConfig.getGatewaUrl());
			s.setInputCharset("1");
			s.setBgUrl(basePath+PropertiesUtil.get("Sina.notifyURL"));
			s.setVersion("v2.3");
			s.setLanguage("1");
			s.setSignType("1");
			s.setMerchantAcctId(paymentConfig.getAccount());//200100100220000373358400001 测试用
			s.setOrderId(accountRecharge.getRechargeTradeNo());
			BigDecimal money=new BigDecimal(accountRecharge.getRechargeMoney().toString());
			money=money.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN);
			s.setOrderAmount(money.toString());
			s.setOrderTime(DateUtils.formatDate(DateUtils.timePattern2,accountRecharge.getRechargeAddtime()));
			s.setPid(paymentConfig.getClientId());
			String key=paymentConfig.getEncryptKey();
			//String signStr=md5(Origin+key={key});
			//s.setSignMsg(md5(Origin+key={key}));
			//s.setSignMsg(key);
			String value=s.getSignStr();
			String signType=s.getSignType();
			String charset="utf-8";
			//String privakey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ8BI+qYudsDAW7FM/Bt75kWDD8Uq85Ne9Ci9Sj9YLRGV0sefKcLw/JdQZFSyncBwPveew7xlOj9kcCouSgH4TvgX1CmzHG+GpgXrS45IAjkLjwh9L6oeaYSEZI26jRjhOSrLOIFfCIn08J3urdjLyqtgT10bTpNiLwlfnhSt85LAgMBAAECgYBTujf9erqzdxu0BqfY/Srjw9ZjCTGHCwodzRGnqfTYOQoShlVBaVKfNzglnCD9FbFXb8oBDnlJTu0HNFF7oNqa5nCQKCnjywp3ijfGmvbPh3KQuAsWd6xX8w5ST9yMyBUxtgQ+KwTAlNVWWXlfz0RQ/LUrayG6psRRRV7il7W7kQJBANAT5dY+yaggWQrKN/EpRqcpBB9RqX0amtWhqengKjOfeGUtsbdPg21l732KlHWO/yGYkrkhYBwcHIUdez+YoWUCQQDDn+uWkqRTSz+BU6b9lEHMfIu2Sn3Roquis5wstF7O/FyCIOzOR1ukQ6LEqSlJyaaET7+aohUW482Xcj5R+w3vAkEAqObd9FE3RAAuBzIqwtmsflgu/nU9TU3hTXRTBoNt/kV+ZzL2RUSH/K93/cIVzG9s7gp5X0mWSHsRM4TA3Ni6eQJARthFg1Q7+oZHy1g2I6NraNi56Bx2EFNvAQ82weHYa/FstxuhZtrkz0pHSh1Q/I3wpf4Tj7afGJkLPYt8EkhvNQJBAMrxRQpFLbuO1ZWjr4paykNeIyqFlqQgzblyErRMVpRCmK0MCtMJqfqekxnofX9CkckobDGNhyrtD+HOleDe/RI=";
			String signMsg=EpayMD5.getSign(value,signType,key,charset).toLowerCase();
			s.setSignMsg(signMsg);
			return s;
		}
	//新生支付
		private OnlineRechargeXinSheng xinsheng(AccountRecharge accountRecharge,PaymentConfig paymentConfig, String basePath) {
			OnlineRechargeXinSheng xinsheng = new OnlineRechargeXinSheng();
			String clientIP = "192.168.21.1";
			xinsheng.setVersion("2.6");
			xinsheng.setSerialID(accountRecharge.getRechargeTradeNo());
			String userId = accountRecharge.getUserId()+"";
			System.out.println(userId);
			xinsheng.setSubmitTime(xinsheng.getSerialID().substring(userId.length()+1, userId.length()+15));
			System.out.println(xinsheng.getSubmitTime());
			xinsheng.setPayType("ALL");
			xinsheng.setType("1000");
			xinsheng.setCustomerIP("www.tzjr8888.com["+clientIP+"]");
			xinsheng.setReturnUrl(basePath+PropertiesUtil.get("xinsheng.returnUrl"));
			xinsheng.setNoticeUrl(basePath+PropertiesUtil.get("xinsheng.noticeUrl"));
			xinsheng.setGatewayUrl(paymentConfig.getGatewaUrl());
			xinsheng.setPartnerID(paymentConfig.getClientId());
			xinsheng.setRemark(userId);
			xinsheng.setCharset("1");
			xinsheng.setOrderID(xinsheng.getSerialID());

			String OrderMoney = accountRecharge.getRechargeMoney().toString();
			if (!"".equals(OrderMoney)) {
				BigDecimal a;
				a = new BigDecimal(OrderMoney).multiply(BigDecimal.valueOf(100)); // 使用分进行提交
				OrderMoney = String.valueOf(a.setScale(0));
			} else {
				OrderMoney = ("0");
			}		
			xinsheng.setTotalAmount(OrderMoney);
			xinsheng.setOrderAmount(OrderMoney);
			xinsheng.setDisplayName("台州聚融");
			xinsheng.setGoodsName("p2p");
			xinsheng.setGoodsCount("1");
			xinsheng.setOrders(xinsheng.getOrderID()+","+xinsheng.getOrderAmount()+","+
								xinsheng.getDisplayName()+","+xinsheng.getGoodsName()+","+xinsheng.getGoodsCount());
			xinsheng.setOrderDetails(xinsheng.getOrders());
			xinsheng.setSignType("1");
			String signMsg = xinsheng.getSignMsg();
			try {
				xinsheng.setSignMsg(ClientSignature.genSignByRSA(signMsg, CharsetTypeEnum.UTF8));
				return xinsheng;
			} catch (Exception e) {
				
				return null;
			}
		}	
	
	//丰付支付
	private OnlineRechargeFengFu fengfu(AccountRecharge accountRecharge,PaymentConfig paymentConfig, String basePath) {
		OnlineRechargeFengFu fengfu = new OnlineRechargeFengFu();
		fengfu.setGatewayUrl(paymentConfig.getGatewaUrl());	
		fengfu.setTradeProcess(paymentConfig.getClientId());
		fengfu.setMd5key(paymentConfig.getEncryptKey());
		fengfu.setTotalBizType(PropertiesUtil.get("fengfu.totalBizType"));
		fengfu.setBackurl(basePath+PropertiesUtil.get("fengfu.backUrl"));
		fengfu.setReturnurl(basePath+PropertiesUtil.get("fengfu.returnUrl"));
		fengfu.setNoticeurl(basePath+PropertiesUtil.get("fengfu.noticeUrl"));
		
		String totalPrice = accountRecharge.getRechargeMoney()+"";
		if ("".equals(totalPrice)) {
			totalPrice = ("0");
		} 
		fengfu.setTotalPrice(totalPrice);
		
		fengfu.setFund(fengfu.getTotalPrice());
		fengfu.setMerAcct(fengfu.getTradeProcess());
		fengfu.setBizType(fengfu.getTotalBizType());
		fengfu.setProductId("1234567890");
		fengfu.setProductName("投资理财");
		fengfu.setProductNumber("1");
		
		String aKey = fengfu.getMd5key();
		fengfu.setDescription(accountRecharge.getUserId()+","+aKey);
		fengfu.setUserIdIdentity(accountRecharge.getUserId()+"");
		fengfu.setRequestId(accountRecharge.getRechargeTradeNo());
		String aValue = fengfu.getMd5Sign();
		fengfu.setMersignature(SignatureUtil.hmacSign(aValue, aKey));
		return fengfu;
	}
	//易宝支付
		private OnlineRechargeYeePay yeepay(AccountRecharge accountRecharge
				,PaymentConfig paymentConfig, String basePath) {
			OnlineRechargeYeePay yp = new OnlineRechargeYeePay();
			yp.setGatewayUrl(paymentConfig.getGatewaUrl());
			yp.setP0_Cmd("Buy");
			yp.setP1_MerId(paymentConfig.getClientId());
			yp.setP2_Order(accountRecharge.getRechargeTradeNo());
			yp.setP3_Amt(accountRecharge.getRechargeMoney().toString());
			yp.setP4_Cur(PropertiesUtil.get("EBank.moneytype"));
			yp.setP8_Url(basePath + PropertiesUtil.get("YeePay.ReturnURL"));
			yp.setPr_NeedResponse("1");// 默认为"1"，需要应答机制
			yp.setPa_MP(accountRecharge.getUserId().toString()); 
			String keyValue = paymentConfig.getEncryptKey(); 
			String hmac = "";// 交易签名串
			// 获得MD5-HMAC签名
			hmac = PaymentForOnlineService.getReqMd5HmacForOnlinePayment(yp.getP0_Cmd(),
					yp.getP1_MerId(),yp.getP2_Order(),yp.getP3_Amt(),yp.getP4_Cur(),yp.getP5_Pid(),yp.getP6_Pcat(),yp.getP7_Pdesc(),
					yp.getP8_Url(),yp.getP9_SAF(),yp.getPa_MP(),yp.getPd_FrpId(),yp.getPr_NeedResponse(),keyValue);
			yp.setHmac(hmac);
			return yp;
		}
	
	// 汇付宝支付
		private OnlineRechargeHFB huifubao(AccountRecharge accountRecharge,
				String clientIP, PaymentConfig paymentConfig, String basePath) {
			OnlineRechargeHFB hfb=new OnlineRechargeHFB();
			hfb.setVersion("1");
			hfb.setPayType("20");
			hfb.setPayCode("0");
			hfb.setAgentId(paymentConfig.getClientId());
			hfb.setAgentBillId(accountRecharge.getRechargeTradeNo());
			hfb.setPayAmt(accountRecharge.getRechargeMoney().toString());
			hfb.setNotifyUrl(basePath+PropertiesUtil.get("HFB.NotifyUrl"));
			hfb.setReturnUrl(basePath+PropertiesUtil.get("HFB.ReturnURL"));
			hfb.setGatewayUrl(paymentConfig.getGatewaUrl());
			hfb.setIsTest("1");
			String ip=clientIP.replace(".", "_");
			
			hfb.setUserIp(ip);
			hfb.setAgentBillTime(DateUtils.formatDate(DateUtils.timePattern2,
					accountRecharge.getRechargeAddtime()));
			try {
				hfb.setGoodsName(URLEncoder.encode(SysCacheUtils.getSysConfig().getSysWebsitesignature()+"网上充值", "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				
			}
			hfb.setRemark(accountRecharge.getUserId().toString());
			String sign=hfb.getSignStr()+paymentConfig.getEncryptKey();
			sign = EpayMD5.MD5HFB(sign);// MD5检验结果
			hfb.setSign(sign);
			return hfb;
			
		}
	// 汇潮支付
	private OnlineRechargeHC huiChao(AccountRecharge accountRecharge, PaymentConfig paymentConfig,String basePath) {
		OnlineRechargeHC hc=new OnlineRechargeHC();
		hc.setMerNo(paymentConfig.getClientId());
		hc.setBillNo(accountRecharge.getRechargeTradeNo());
		hc.setOrderTime(DateUtils.formatDate(DateUtils.timePattern2,accountRecharge.getRechargeAddtime()));
		hc.setGatewayUrl(paymentConfig.getGatewaUrl());
		
		hc.setReturnURL(basePath+PropertiesUtil.get("HC.ReturnURL"));
		hc.setAdviceURL(basePath+PropertiesUtil.get("HC.AdviceURL"));
		hc.setAmount(accountRecharge.getRechargeMoney().toString());
		
		String md5src=hc.getMd5src()+"&"+paymentConfig.getEncryptKey();
		EpayMD5 md5=new EpayMD5();
	    String signInfo; //MD5加密后的字符串
	    signInfo = md5.getMD5ofStr(md5src);//MD5检验结果
		hc.setSignInfo(signInfo);
		return hc;
	}
	// 贝付支付
	private OnlineRechargeBeiFu beifu(AccountRecharge accountRecharge, PaymentConfig paymentConfig,String basePath,String clientIP,String payment) {
		OnlineRechargeBeiFu beif = new OnlineRechargeBeiFu();
		beif.setPartner(paymentConfig.getClientId());
		beif.setInputCharset(PropertiesUtil.get("beifu.inputCharset"));
		beif.setSignType(PropertiesUtil.get("beifu.signType"));
		beif.setNotifyUrl(basePath+PropertiesUtil.get("beifu.notifyUrl"));
		beif.setReturnUrl(basePath+PropertiesUtil.get("beifu.returnUrl"));
		//beif.setGatewayUrl(request.getContextPath()+"/sign.do");
		beif.setService(PropertiesUtil.get("beifu.service"));
		beif.setOutTradeNo(accountRecharge.getRechargeTradeNo());
		beif.setSubject(SysCacheUtils.getSysConfig().getSysWebsitesignature()+"线上充值");
		beif.setExterInvokeIp(clientIP);
		beif.setSellerId(paymentConfig.getClientId());
		//beif.setBuyerId(accountRecharge.getUserId().toString());
		beif.setExtraCommonParam(accountRecharge.getUserId().toString());
		try {
			beif.setAntiPhishingKey(new DateUtil().timestamp(payment));
		} catch (Exception e) {
			e.printStackTrace();
		}
		beif.setPaymentType("1");
		beif.setTotalFee(accountRecharge.getRechargeMoney().toString());
		// //签名加密S
		          	    
			// 签名方式
			/*
			 * 将请求参数名排序，拼接成字符串，如：a=v1&b=v2 然后对该字符串签名，DigitalSigner.sign() : String
			 * 得到的即为签名.
			 */
	        Map<String,String[]> reqMap=new HashMap<String ,String[]>();
	        reqMap.put("partner",new String[]{beif.getPartner()});
	        reqMap.put("sign_type", new String[]{beif.getSignType()});
	        reqMap.put("service", new String[]{beif.getService()});
	        reqMap.put("input_charset", new String[]{beif.getInputCharset()});
	        reqMap.put("notify_url", new String[]{beif.getNotifyUrl()});
	        reqMap.put("return_url", new String[]{beif.getReturnUrl()});
	        reqMap.put("out_trade_no", new String[]{beif.getOutTradeNo()});
	        reqMap.put("subject", new String[]{beif.getSubject()});
	        reqMap.put("exter_invoke_ip", new String[]{beif.getExterInvokeIp()});
	        reqMap.put("anti_phishing_key", new String[]{beif.getAntiPhishingKey()});
	        reqMap.put("payment_type", new String[]{beif.getPaymentType()});
	        reqMap.put("seller_id", new String[]{beif.getSellerId()});
	        reqMap.put("buyer_id", new String[]{beif.getBuyerId()});
	        reqMap.put("total_fee", new String[]{beif.getTotalFee()});
	        reqMap.put("extra_common_param", new String[]{beif.getExtraCommonParam()});
	        
	       // reqMap.put("anti_phishing_key",new String[]{anti_phishing_key});
	        reqMap.put("anti_phishing_key",new String[]{beif.getAntiPhishingKey()});

		
		// 签名方式
		/*
		 * 将请求参数名排序，拼接成字符串，如：a=v1&b=v2 然后对该字符串签名，DigitalSigner.sign() : String
		 * 得到的即为签名
		 */
		String key=paymentConfig.getEncryptKey();
        String paramStr = UrlHelper.sortParamers(reqMap);
        
        String plaintext = TimestampUtils.mergePlainTextWithMerKey(paramStr, key);
        
        // 加密(MD5加签)，默认已取UTF-8字符集，如果需要更改，可调用Md5Encrypt.setCharset(inputCharset)
        String sign = Md5Encrypt.encrypt(plaintext); 
        
		try {
			String gateway=paymentConfig.getGatewaUrl();
	       // //签名加密E
	        beif.setSign(URLEncoder.encode(sign, beif.getInputCharset()));
	        beif.setGatewayUrl(gateway);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		return beif;
	}
	// 国付宝支付
	private OnlineRechargeGFB buildGFB(AccountRecharge accountRecharge,
			String clientIP, PaymentConfig paymentConfig,String basePath) {
		OnlineRechargeGFB gfb = new OnlineRechargeGFB();
		gfb.setMerchantID(paymentConfig.getClientId());
		gfb.setVirCardNoIn(paymentConfig.getAccount());
		/*gfb.setGatewayUrl(PropertiesUtil.get("GFB.Gateway.url"));*/
		gfb.setGatewayUrl(paymentConfig.getGatewaUrl());
		gfb.setMerOrderNum(accountRecharge.getRechargeTradeNo());
		gfb.setTranAmt(accountRecharge.getRechargeMoney().toString());
		gfb.setTranDateTime(DateUtils.formatDate(DateUtils.timePattern2,
				accountRecharge.getRechargeAddtime()));
		gfb.setTranIP(clientIP);
		gfb.setGopayServerTime(GopayUtils.getGopayServerTime());

		gfb.setBackgroundMerUrl(basePath+PropertiesUtil.get("GFB.callback.url"));
		gfb.setVerficationCode(paymentConfig.getEncryptKey());
		gfb.setMerRemark1(accountRecharge.getUserId().toString());
		gfb.setSignValue(MD5Utils.stringToMD5(gfb.getOrgSignValue())); // 加密串
		return gfb;
	}

	// 网银在线支付
	private OnlineRechargeEBank buildEBank(AccountRecharge accountRecharge,
			PaymentConfig paymentConfig,String basePath) {
		OnlineRechargeEBank eBank = new OnlineRechargeEBank();
		eBank.setKey(paymentConfig.getEncryptKey());// MD5密钥
		// eBank.setKey(PropertiesUtil.get("EBank.key"));//MD5密钥
		eBank.setMid(paymentConfig.getClientId());
		// eBank.setMid(PropertiesUtil.get("EBank.mid"));
		eBank.setOid(accountRecharge.getRechargeTradeNo());
		eBank.setAmount(accountRecharge.getRechargeMoney().toString());
		eBank.setMoneytype(PropertiesUtil.get("EBank.moneytype"));
		eBank.setUrl(basePath+PropertiesUtil.get("EBank.recharge.success"));//
		eBank.setRemark2("[url:=" + basePath+PropertiesUtil.get("EBank.callback.url")+ "]");

	//	eBank.setGatewayUrl(PropertiesUtil.get("EBank.gateway.url"));// 支付平台接口地址
		eBank.setGatewayUrl(paymentConfig.getGatewaUrl());
	    eBank.setRemark1(accountRecharge.getUserId().toString());
		eBank.setMd5info(GopayUtils.md5(eBank.getOrgSignValue()).toUpperCase());// 加密串
		return eBank;

	}
	// 银联网上支付
	private OnlineRechargeChinaPay buildChinaPay(AccountRecharge accountRecharge,PaymentConfig paymentConfig,String basePath) {
		//String merId=PropertiesUtil.get("cpay.merId");
		String merId=paymentConfig.getClientId();
		String ordId=accountRecharge.getRechargeTradeNo();
		
		BigDecimal transA=accountRecharge.getRechargeMoney();//金額
		BigDecimal transAm=transA.multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_DOWN);
		DecimalFormat df=new DecimalFormat("000000000000");
		String transAmt=df.format(Integer.parseInt(transAm.toString()));
	     
		Date transDate=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    String transDateString = formatter.format(transDate);
	    
		OnlineRechargeChinaPay cpay=new OnlineRechargeChinaPay();
		cpay.setMerId(merId);
		cpay.setOrdId(ordId);
		cpay.setTransAmt(transAmt);
		cpay.setCuryId("156");
		cpay.setTransDate(transDateString);
		cpay.setTransType("0001");
		cpay.setVersion(PropertiesUtil.get("cpay.version"));
		cpay.setBgRetUrl(basePath+PropertiesUtil.get("cpay.bgRetUrl"));//basePath+
		cpay.setPageRetUrl(PropertiesUtil.get("cpay.pageRetUrl"));
		//cpay.setGatewayUrl(PropertiesUtil.get("cpay.gatewayUrl"));
		cpay.setGatewayUrl(paymentConfig.getGatewaUrl());
		cpay.setPriv1(accountRecharge.getUserId().toString());
		cpay.setExtFlag("00");
		String merkey=PropertiesUtil.get("chinapay.merkey.filepath");
		merkey = ContextLoader.getCurrentWebApplicationContext()
				.getServletContext().getRealPath(merkey); 
		List errorList = new ArrayList();
		boolean buildOK = false;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		try {
			buildOK = key.buildKey(merId, KeyUsage,merkey);
		} catch (Exception e) {
			// 
		}
		if (!buildOK) {
			System.out.println("build error!");
			errorList.add("build_key_error!");
		}
		SecureLink t=new SecureLink (key); 
		String chkValue="";
		// 对订单的签名2007版本
		 if(cpay.getVersion().equals("20070129")){
			// String money=cpay.getTransAmt();
			 chkValue= t.Sign(merId+cpay.getOrdId()+cpay.getTransAmt()+cpay.getCuryId()+transDateString+cpay.getTransType()+cpay.getPriv1()); 
		     System.out.println(merId+cpay.getOrdId()+cpay.getTransAmt()+cpay.getCuryId()+transDateString+cpay.getTransType()+cpay.getPriv1());
		 }else{
			 chkValue = t.signOrder(merId, cpay.getOrdId(), transAmt, cpay.getCuryId(),
					 transDateString, cpay.getTransType());
		 }
		
		cpay.setChkValue(chkValue);
		return cpay;
	}

	// 双乾支付
	private OnlineRechargeSQ buildDoubleMoney(AccountRecharge accountRecharge,
			PaymentConfig paymentConfig,String basePath) {
		OnlineRechargeSQ dMoney = new OnlineRechargeSQ();
		dMoney.setMerNo(paymentConfig.getClientId());
		// dMoney.setMerNo(PropertiesUtil.get("DMoney.MerNo"));
		dMoney.setBillNo(accountRecharge.getRechargeTradeNo());
		dMoney.setAmount(accountRecharge.getRechargeMoney().toString());
		dMoney.setPaymentType("");
		dMoney.setPayType("CSPAY");
		/*dMoney.setGatewayUrl(PropertiesUtil.get("DMoney.gatewayUrl"));*/
		dMoney.setGatewayUrl(paymentConfig.getGatewaUrl());
		
		dMoney.setMd5key(paymentConfig.getEncryptKey());
		// dMoney.setMd5key(PropertiesUtil.get("DMoney.MD5key"));
		dMoney.setMerRemark(String.valueOf(accountRecharge.getUserId()));
		dMoney.setNotifyURL(basePath+PropertiesUtil.get("DMoney.notifyURL"));
		dMoney.setReturnURL(basePath+PropertiesUtil.get("DMoney.returnURL"));
		dMoney.setMd5info(PayUtils.signMap(dMoney.getOrgSignValue(),
				dMoney.getMd5key(), "REQ"));
		return dMoney;
		
	}

	// 宝付支付
	@SuppressWarnings("unused")
	private OnlineRechargeBF baoFu(AccountRecharge accountRecharge,
			PaymentConfig paymentConfig,String basePath, String payID) {
		OnlineRechargeBF bf = new OnlineRechargeBF();
		bf.setAdditionalInfo(String.valueOf(accountRecharge.getUserId()));
		bf.setMemberID(paymentConfig.getClientId());
		// bf.setMemberID(PropertiesUtil.get("BF.memberID"));
		/*bf.setGatewayUrl(PropertiesUtil.get(("BF.payUrl")));*/
		bf.setGatewayUrl(paymentConfig.getGatewaUrl());
		bf.setPayID(payID);
		
		bf.setReturnUrl(basePath+PropertiesUtil.get("BF.returnUrl"));
		bf.setTerminalID(paymentConfig.getAccount());
		bf.setInterfaceVersion(PropertiesUtil.get("BF.InterfaceVersion"));
		bf.setKeyType(PropertiesUtil.get("BF.KeyType"));
		/* bf.setOrderMoney(accountRecharge.getPayMoney().toString()); */
		// 订单金额
		String OrderMoney = accountRecharge.getRechargeMoney().toString();
		if (!"".equals(OrderMoney)) {
			BigDecimal a;
			a = new BigDecimal(OrderMoney).multiply(BigDecimal.valueOf(100)); // 使用分进行提交
			OrderMoney = String.valueOf(a.setScale(0));
		} else {
			OrderMoney = ("0");
		}
		bf.setOrderMoney(OrderMoney);
		bf.setNoticeType(PropertiesUtil.get("BF.noticeType"));
		bf.setPageUrl(basePath+PropertiesUtil.get("BF.pageUrl"));
		// 时间格式
		Date currTime = new Date();
		// 时间以yyyy-MM-dd HH:mm:ss的方式表示
		SimpleDateFormat formatter1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		// 时间以yyyyMMDDHHmmss的方式表示
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.CHINA);
		String TradeDate = new String(formatter2.format(currTime));
		bf.setTradeDate(TradeDate);

		bf.setTransID(accountRecharge.getRechargeTradeNo());
		// 加密
		bf.setMd5key(paymentConfig.getEncryptKey());// md5密钥（KEY）
		// bf.setMd5key(PropertiesUtil.get("BF.Md5key"));//md5密钥（KEY）
		String md5 = bf.getMd5Sign();// 加密串
		bf.setMd5info(PayUtils.getMD5ofStr(md5));// 加密
		return bf;

	}

	/**
	 * 充值成功
	 */
	
	/**
	 * 充值成功
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("rechargeResult.do")
	public String rechargeResult(HttpServletRequest request, HttpServletResponse response, Model model){
		System.out.println(request);
		boolean succ = false;
		String amount = "";
		//贝付支付
		String status=request.getParameter("trade_status");
		if(status!=null){
			succ=status.equals("TRADE_FINISHED");
			amount=request.getParameter("total_fee");
		}
		
		//网银在线
		status = request.getParameter("v_pstatus");
		if(status!=null){ 
			succ = status.equals("20"); 
			amount = request.getParameter("v_amount");
		}
		//双乾
		status = request.getParameter("Succeed");
		if(status!=null){ 
			succ = status.equals("88"); 
			amount = request.getParameter("Amount");
		}
		//汇付宝
		status = request.getParameter("result");
		if(status!=null){
			succ = status.equals("1");
			amount = request.getParameter("payAmt");
		}
		//财付通---铂金
		status = request.getParameter("resultCode");
		if(status!=null){ 
			succ = status.equals("000000"); 
			amount = request.getParameter("ordAmt");
		}
		status = request.getParameter("status");
		if(status!=null){
			succ = status.equals("2");
			amount = request.getParameter("tradeAmount");
		}
		//连连支付
		status = request.getParameter("result_pay");
		if(status!= null && status.equals("SUCCESS")){
			succ = true;
			amount = request.getParameter("money_order");
		}
		//宝付支付
		status = request.getParameter("Result");
		if(status.equals("1")){
			succ = true;
			amount = request.getParameter("FactMoney");
		}
		
		//测试代码,测试完以后删掉
				Enumeration enu = request.getParameterNames();
				String params = "";
				while (enu.hasMoreElements()) {  
				    String paramName = (String) enu.nextElement(); 
				    String value = request.getParameter(paramName );
				    params += "&"+paramName+"="+value;
				    System.out.println(paramName+"="+value);
				}
				System.out.println("params from SQ->"+params);
				logger.debug("params from SQ->"+params);
		model.addAttribute("status",succ); 
		model.addAttribute("amount",amount);
		return "redirect:recharge.do?succ="+succ;
		
	}
	
	@RequestMapping("redenvelopesRecord.do")
	public String redenvelopesLog(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/account/redenvelopesLog";
	}
	
	@RequestMapping("redenvelopesLogPage.do")
	public void redenvelopesLogPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		PageModel pageModel = redenvelopesService.getFrontListPageRed(paramsMap);
		List<RedenvelopesProbability> list = pageModel.getList();
		
		SpringUtils.renderJson(response, pageModel);
	}
	
	//打开红包
	@RequestMapping("openRedEncelope.do")
	public void openRedEncelope(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JsonResult JsonResult = null;
		String id = request.getParameter("id");
		try {
			JsonResult = redenvelopesService.openRedenvelopes(Integer.parseInt(id), false);
		} catch (Exception e) {
			JsonResult=new JsonResult("301","打开红包失败！！！");
			e.printStackTrace();
		}finally{
			SpringUtils.renderJson(response, JsonResult);
		}
	}
}
