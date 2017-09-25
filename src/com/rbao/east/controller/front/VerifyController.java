package com.rbao.east.controller.front;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.checkrealname.CheckRealName;
import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.Constants;
import com.rbao.east.common.GenerateNo;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.AttestationApply;
import com.rbao.east.entity.AttestationType;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.ExperienceGold;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysConfigParams;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.SysLetterTemplates;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.entity.UserEvaluateApply;
import com.rbao.east.entity.VipUser;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.AttestationApplyService;
import com.rbao.east.service.AttestationTypeService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.CreditTypeService;
import com.rbao.east.service.ExperienceGoldService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserEvaluateApplyService;
import com.rbao.east.service.UserService;
import com.rbao.east.service.VipUserService;
import com.rbao.east.utils.Base64Utils;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SmsUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.SysCacheUtils;

/**
 * 安全中心
 * 
 * 
 */

@Controller
@RequestMapping("verify/")
public class VerifyController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(VerifyController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private AttestationTypeService attestationTypeService;
	@Autowired
	public UserEvaluateApplyService userEvaluateApplyService;
	@Autowired
	public AttestationApplyService attestationApplyService;
	@Autowired
	public UserAccountService userAccountService;
	@Autowired
	public VipUserService vipUserService;
	@Autowired
	public MessageCenterService messageCenterService;
	@Autowired
	public CreditTypeService creditTypeService;
	@Autowired
	private AccountLogService accountLogService;
	
	@Autowired
	private ExperienceGoldService experienceGoldService;
	@Autowired
	private SysConfigParamService sysConfigParamService;
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
	/**
	 * 检测手机号是否存在
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkUserPhone.do")
	public void checkUserAccount(HttpServletRequest request,
			HttpServletResponse response) {
		String userPhone = request.getParameter("userPhone");
		if(StringUtils.isBlank(userPhone)){
			SpringUtils.renderJsonResult(response,"101", "手机号为空");
		}else{
			Map<String, String> param = new HashMap<String, String>();
			param.put("userPhone", userPhone);
			User user = this.userService.selectUserByParam(param);
			if (user == null) {
				SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "成功");
			} else {
				SpringUtils.renderJsonResult(response,"301", "已经存在");
			}
		}
	}
	
	@RequestMapping("accountTradeVerify.do")
	public String accountTradeVerify(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/verify/accountTradeVerify";
	}

	/**
	 * 添加邮箱
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("addEmail.do")
	public String addEmail(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user", user);
		return "userinfo/verify/addEmail";
	}
	/**
	 * 重新发送激活邮件
	 */
	@RequestMapping(value = "reSendEmai.do")
	public void reSendEmail(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> param = this.getParameters(request);
		User user1 = this.userService.checkUserByEmail(param);
		if (user1==null||user1.getEmailStatus()!=2) {
		
		try {
			User user =  this.userService.selectByPrimaryKey(this.loginFrontUser(request).getId());
			if (user == null) {
				SpringUtils.renderJsonResult(response, "400", "请登录后操作!");
				return;
			}
			String email = request.getParameter("userEmail").trim();
//			String email = user.getUserEmail();
			if (!isEmail(email)) {
				SpringUtils.renderJsonResult(response, "400", "邮箱格式不正确!");
				return;
			}
			if (!email.equals(user.getUserEmail())) {//不是原来邮箱则更新
				user.setUserEmail(email);
				userService.updateByPrimaryKeySelective(user);
			}
			
			SpringUtils.renderJsonResult(response, "200", "操作成功,请登录您的邮箱查看激活邮件");
			
			//发送邮件
			MessageCenter center = new MessageCenter();
			center.setSendUserId(Constants.ADMIN_USER_ID);
			center.setReceiveUserId(user.getId());
			center.setMessageTitle("【"+SysCacheUtils.getSysConfig().getSysWebsitename()+"】会员" + email + "激活邮件");
			String id = MD5Utils.stringToMD5(user.getId().toString());
			String sendEmailTime = System.currentTimeMillis()/ 1000 + "";
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
			SysLetterTemplates sysLetterTemplates = SysCacheUtils.getSysLetterTemplates();
			String mailContent = sysLetterTemplates
					.getSysRegisteredMailinfo()
					.replace("#userAccount#", user.getUserAccount())
					.replace("#url#",basePath
							+ "/activation.do?validateId="
							+ id
							+ "&uid="
							+ user.getId()
							+"&send="
							+Base64Utils.encodeStr(sendEmailTime));
			center.setMessageContent("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>"+mailContent+"</body></html>");
			center.setMessageAddress(user.getUserEmail());
			
			center.setMessageSendIp(this.getIpAddr(request));
			messageCenterService.sendEmail(center, null);
			
		} catch (Exception e) {
			SpringUtils.renderJsonResult(response, "404", "系统异常!");
			e.printStackTrace();
		}
		} else {
			SpringUtils.renderJsonResult(response,"301", "邮箱已存在");
		}
	}
	
	private boolean isEmail(String email) {
		email = isNull(email);
		Pattern regex = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	private String isNull(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}
	@SuppressWarnings("unused")
	@RequestMapping("requestAddEmail.do")
	public void requestAddEmail(HttpServletRequest request,
			HttpServletResponse response, Model model, User user) {
		JsonResult json = new JsonResult();
		user.setId(this.loginFrontUser(request).getId());
		user.setEmailStatus(User.EMAIL_APPLY);
		Map<String, String> param = getParameters(request);
		boolean b = userService.saveOrUpdate(user);
		/**
		 * 记录日志
		 */
		User loginUser = this.loginFrontUser(request);
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
				+ loginUser.getUserAccount());
		operatorLog.setOperatorTitle("申请邮箱认证");
		operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION_APPLY);
		operatorLog.setOperatorParams(user.toString());
		operatorLog.setOperatorReturn(b ? "申请邮箱认证成功" : "申请邮箱认证失败");
		operatorLog.setOperatorStatus(b ? 200 : 300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLog.setOperatorIp(this.getIpAddr(request));
		operatorLogService.addFrontLog(operatorLog);
		if (b) {
			json.setCode("102");
		} else {
			json.setCode("103");
		}
		SpringUtils.renderJson(response, json);
	}

	@RequestMapping("changePhone.do")
	public String changePhone(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/verify/changePhone";
	}

	@RequestMapping("passwordProtect.do")
	public String passwordProtect(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/verify/passwordProtect";
	}

	/**
	 * 实名认证
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("realnameAttestation.do")
	public String realnameAttestation(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user", user);
		return "userinfo/verify/userAttestation";
	}

	@RequestMapping("requestRealnameAttestation.do")
	public void requestRealnameAttestation(HttpServletRequest request,
			HttpServletResponse response, Model model, User user) {
		JsonResult json = new JsonResult();
		Map<String, String> param = getParameters(request);
		
		user.setId(this.loginFrontUser(request).getId());
		user.setCardNumber(param.get("cardNumber").toUpperCase());
		/*user.setCardFrontImg(param.get("productURLs"));
		user.setCardBackImg(param.get("productURLs1"));*/
		if (user.getCardNumber().length() == 18) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNumber", user.getCardNumber());
			User u = this.userService.selectUserByParam(map);
			if (u == null) {
				//判断可用余额
				UserAccount userAccount=userAccountService.getByUserId(user.getId());
				BigDecimal availableMoney=userAccount.getAvailableMoney();
				BigDecimal sysAuthRate=SysCacheUtils.getSysFeesRate().getSysAuthRate();
				boolean b=false;
				if(CompareUtils.greaterThanAndEqualsZero(availableMoney.subtract(sysAuthRate))){
					user.setCardType(User.IDCARD);
					//实名认证
					boolean flag = CheckRealName.checkRealName(param.get("userRealname"),param.get("cardNumber"));
//					boolean flag = true;
					if(flag){
						userCreditService.addUserCredit(CreditType.REALNAME, user.getId());
						creditLogService.addCreditLog(CreditType.REALNAME, user.getId());
						user.setUserRealname(param.get("userRealname"));
						user.setRealnameStatus(2);
						json.setCode("10001");
						json.setMsg("实名认证成功");
						b = userService.saveOrUpdate(user);
						user = userService.getById(user.getId());
						//实名且邮箱认证都通过后，插入抵扣金，修改抵扣金状态
						if(true == b ){
							Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
							String deductionMoneySwitch = sysConfigParamMap.get("deductionMoney_switch");
							
						if(deductionMoneySwitch.equals("1")){
							String deductionMoneyAmount = sysConfigParamMap.get("deductionMoney_amount");
							userAccount.setDeductionMoney(new BigDecimal(deductionMoneyAmount));
							userAccount.setDeductionStatus(1);
							boolean addFlag = userAccountService.updateByPrimaryKeySelective(userAccount);
							
							if(true == addFlag){
								MessageCenter centerDeduction=new MessageCenter();
								centerDeduction.setMessageSendDatetime(new Date());
								centerDeduction.setMessageSendIp(this.getIpAddr(request));
								centerDeduction.setMessageStatus(MessageCenter.STATUS_UNREAD);
								centerDeduction.setReceiveUserId(user.getId());
								centerDeduction.setSendUserId(Constants.ADMIN_USER_ID);
								centerDeduction.setMessageContent(addFlag&&user.getEmailStatus()==User.EMAIL_PASS?"用户"+user.getUserAccount()+"得到抵扣金"+deductionMoneyAmount+"元，可以正常使用抵扣金。":"得到抵扣金失败！");
								centerDeduction.setMessageTitle("发送抵扣金");
								centerDeduction.setNoticeTypeId(MessageCenter.MESSAGE);
							/*	this.messageCenterService.send(centerDeduction, addFlag&&user.getEmailStatus()==User.EMAIL_PASS?Notice.EMAIL_YES:Notice.EMAIL_NO);
							*/	
								//发送站内信
								this.messageCenterService.send(user.getId(), "抵扣金", "已经发放抵扣金"+deductionMoneyAmount+"元,可以正常使用抵扣金。", Notice.deductionMoney);
								
								
							}
							
						}
							
							
							
							
							String experienceGoldSwitchValue = sysConfigParamMap.get("experienceGold_switch");
							String experienceGoldAprValue = sysConfigParamMap.get("experienceGold_apr");
							String experienceGoldPeriodValue = sysConfigParamMap.get("experienceGold_period");
							String experienceGoldMoneyValue = sysConfigParamMap.get("experienceGold_money");
							
							//判断是否开启体验金开关
							if(experienceGoldSwitchValue.equals("1")){
								
								
								//插入体验金
								
									ExperienceGold experienceGold=experienceGoldService.getExperienceGoldByUserId(user.getId());
									
									if(experienceGold ==null){
										//获取l利率和金额,期限,
										BigDecimal apr=new BigDecimal(experienceGoldAprValue);
										BigDecimal totalExperienceGold=new BigDecimal(experienceGoldMoneyValue);
										int loanPeriodCount=Integer.parseInt(experienceGoldPeriodValue);
										
										//计算出体验金利息(按一次性还款)
										BigDecimal interestOfDay365 = CalculateProcess.interestOfDay365(totalExperienceGold, apr, loanPeriodCount);
										
										Date date = new Date();//添加时间
										
										Date repayDay = DateUtils.addDay(date, loanPeriodCount);//还款时间
										
										
										ExperienceGold experienceGold2 = new ExperienceGold();
										experienceGold2.setUserid(user.getId());
										experienceGold2.setExperienceGoldStatus(0);
										experienceGold2.setExperienceGoldInterest(interestOfDay365);
										experienceGold2.setExperienceGoldAddtime(date);
										experienceGold2.setExperienceGoldRepaytime(repayDay);
										
										boolean payFlag = experienceGoldService.saveOrUpdate(experienceGold2);
										
										
										if(payFlag==true){
											//发送站内信
											messageCenterService.send(user.getId(), "体验金", "已经发放体验金"+totalExperienceGold+",利率为"+apr+"%,时长为"+loanPeriodCount+"天,到期收益为"+interestOfDay365+"元(注：3天后"+totalExperienceGold+"元体验金的利息"+interestOfDay365+"元显示在“可用余额”中，您需在首投后，且首投的产品进入到“计息中”的状态中，"+interestOfDay365+"元方可提现。)", Notice.ExperienceGoid);
											
											//记录操作日志
											User loginUser = this.loginFrontUser(request);
											OperatorLog operatorLog = new OperatorLog();
											operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
													+ loginUser.getUserAccount());
											operatorLog.setOperatorTitle("体验金发放");
											operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
											operatorLog
													.setOperatorCategory(OperatorLog.CATEGORY_EXPERIENCE_GOLD);
											operatorLog.setOperatorParams(user.toString());
											operatorLog.setOperatorReturn(payFlag ? "体验金发放成功" : "体验金发放失败");
											operatorLog.setOperatorStatus(payFlag ? 200 : 300);
											operatorLog.setLinkUrl(RequestUtils.getIpAddr());
											operatorLog.setOperatorIp(this.getIpAddr(request));
											operatorLogService.addFrontLog(operatorLog);
										}
										
										
									}
								
								
							}
							
							
							
							
						}
						
						
						
						
						
						
					}else {
						json.setCode("10002");
						json.setMsg("实名认证失败");
						
					}
					
					/**
					 * 记录日志
					 */
					User loginUser = this.loginFrontUser(request);
					OperatorLog operatorLog = new OperatorLog();
					operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
							+ loginUser.getUserAccount());
					operatorLog.setOperatorTitle("申请实名认证");
					operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
					operatorLog
							.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION_APPLY);
					operatorLog.setOperatorParams(user.toString());
					operatorLog.setOperatorReturn(b ? "申请实名认证成功" : "申请实名认证失败");
					operatorLog.setOperatorStatus(b ? 200 : 300);
					operatorLog.setLinkUrl(RequestUtils.getIpAddr());
					operatorLog.setOperatorIp(this.getIpAddr(request));
					operatorLogService.addFrontLog(operatorLog);
					/*if (b) {
						json.setCode("100");
					} else {
						json.setCode("101");
					}*/
				}else{
					json.setCode("102");
					json.setMsg("可用余额不足！");
				}
			}else{
				
				json.setCode("10003");
				json.setMsg("此身份证号已存在");
			}
		} else {
			logger.error("实名认证信息不完整！");
		}
        
		SpringUtils.renderJson(response, json);
	}

	/**
	 * 修改登录密码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("saveUserLoginPass.do")
	public String saveUserLoginPass(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		model.addAttribute("type", request.getParameter("type"));
		return "userinfo/verify/saveUserLoginPass";
	}

	@RequestMapping("requestSaveUserLoginPass.do")
	public void requestSaveUserLoginPass(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = userService.getById(this.loginFrontUser(request).getId());
		Map<String, Object> result = new HashMap<String, Object>();
		User u = new User();
		u.setId(this.loginFrontUser(request).getId());
		Map<String, String> param = getParameters(request);
		
		DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
		DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
		
		String userPassword = desEncrpt.decrypt(StringUtils.trimToEmpty(request
				.getParameter("userPassword")));
		String newUserPassword = desEncrpt.decrypt(StringUtils.trimToEmpty(request
				.getParameter("newUserPassword")));
		
		String pass = MD5Utils.stringToMD5(aesEncrypt.encrypt(userPassword));
		if (pass.equals(user.getUserPassword())) {
			if (param.get("newUserPassword").equals(
					param.get("newUserPassword2"))) {
				u.setUserPassword(MD5Utils.stringToMD5(aesEncrypt.encrypt(newUserPassword)));
				boolean b = userService.saveOrUpdate(u);
				User updateUser = userService.getById(this.loginFrontUser(
						request).getId());
				request.getSession().setAttribute(Constants.FRONT_USER_SESSION,
						updateUser);
				/**
				 * 记录日志
				 */
				User loginUser = this.loginFrontUser(request);
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
						+ loginUser.getUserAccount());
				operatorLog.setOperatorTitle("修改登陆密码");
				operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
				operatorLog.setOperatorParams(user.toString());
				operatorLog.setOperatorReturn(b ? "修改登陆成功" : "修改登陆失败");
				operatorLog.setOperatorStatus(b ? 200 : 300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLog.setOperatorIp(this.getIpAddr(request));
				operatorLogService.addFrontLog(operatorLog);
				if (b) {
					result.put("message", "修改成功");
				}
			} else {
				result.put("message", "两次输入密码不一致请重新输入");
			}
		} else {
			result.put("message", "登录密码不正确请重新输入");
		}
		SpringUtils.renderJson(response, result);
	}

	/**
	 * 修改支付密码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("saveUserPayPass.do")
	public String saveUserPayPass(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		return "userinfo/verify/saveUserPayPass";
	}

//	@RequestMapping("requestSaveUserPayPass.do")
//	public void requestSaveUserPayPass(HttpServletRequest request,
//			HttpServletResponse response, Model model) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		User user = userService.getById(this.loginFrontUser(request).getId());
//		User u = new User();
//		u.setId(this.loginFrontUser(request).getId());
//		Map<String, String> param = getParameters(request);
//		
//		DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
//		DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
//		String userPaypassword = desEncrpt.decrypt(StringUtils.trimToEmpty(request
//				.getParameter("userPaypassword")));
//		String newUserPaypassword = desEncrpt.decrypt(StringUtils.trimToEmpty(request
//				.getParameter("newUserPaypassword")));
//		
//		String pass = MD5Utils.stringToMD5(aesEncrypt.encrypt(userPaypassword));
//		
//		//String pass = MD5Utils.stringToMD5(param.get("userPaypassword"));
//		if (user.getUserPaypassword() == null
//				|| "".equals(user.getUserPaypassword())) {
//			if (pass.equals(user.getUserPassword())) {
//				if (param.get("newUserPaypassword").equals(
//						param.get("newUserPaypassword2"))) {
//					u.setUserPaypassword(MD5Utils.stringToMD5(aesEncrypt.encrypt(newUserPaypassword)));
//					boolean b = userService.saveOrUpdate(u);
//					user.setUserPaypassword(u.getUserPaypassword());
//					User updateUser = userService.getById(this.loginFrontUser(
//							request).getId());
//					request.getSession().setAttribute(
//							Constants.FRONT_USER_SESSION, updateUser);
//					/**
//					 * 记录日志
//					 */
//					User loginUser = this.loginFrontUser(request);
//					OperatorLog operatorLog = new OperatorLog();
//					operatorLog.setLogUserid("用户Id：" + loginUser.getId()
//							+ ",用户名：" + loginUser.getUserAccount());
//					operatorLog.setOperatorTitle("修改支付密码");
//					operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
//					operatorLog.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
//					operatorLog.setOperatorParams(user.toString());
//					operatorLog.setOperatorReturn(b ? "修改支付密码成功" : "修改支付密码失败");
//					operatorLog.setOperatorStatus(b ? 200 : 300);
//					operatorLog.setLinkUrl(RequestUtils.getIpAddr());
//					operatorLog.setOperatorIp(this.getIpAddr(request));
//					operatorLogService.addFrontLog(operatorLog);
//					if (b) {
//						result.put("message", "修改成功");
//					}
//				} else {
//					result.put("message", "两次输入密码不一致请重新输入");
//				}
//			} else {
//				result.put("message", "支付密码不正确请重新输入");
//			}
//		} else {
//			if (pass.equals(user.getUserPaypassword())) {
//				if (param.get("newUserPaypassword").equals(
//						param.get("newUserPaypassword2"))) {
//					u.setUserPaypassword(MD5Utils.stringToMD5(aesEncrypt.encrypt(newUserPaypassword)));
//					boolean b = userService.saveOrUpdate(u);
//					if (b) {
//						result.put("message", "修改成功");
//					}
//				} else {
//					result.put("message", "两次输入密码不一致请重新输入");
//				}
//			} else {
//				result.put("message", "支付密码不正确请重新输入");
//			}
//		}
//
//		SpringUtils.renderJson(response, result);
//	}

	@RequestMapping("requestSaveUserPayPass.do")
	public void requestSaveUserPayPass(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userService.getById(this.loginFrontUser(request).getId());
		User u = new User();
		u.setId(this.loginFrontUser(request).getId());
		Map<String, String> param = getParameters(request);
		
		DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
		DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
	
		String newUserPaypassword = desEncrpt.decrypt(StringUtils.trimToEmpty(request.getParameter("newUserPaypassword")));
		
		if (param.get("newUserPaypassword").equals(param.get("newUserPaypassword2"))) {
			u.setUserPaypassword(MD5Utils.stringToMD5(aesEncrypt.encrypt(newUserPaypassword)));
			boolean b = userService.saveOrUpdate(u);
			user.setUserPaypassword(u.getUserPaypassword());
			User updateUser = userService.getById(this.loginFrontUser(request).getId());
			request.getSession().setAttribute(Constants.FRONT_USER_SESSION,updateUser);
			
			/**
			 * 记录日志
			 */
			User loginUser = this.loginFrontUser(request);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
					+ loginUser.getUserAccount());
			operatorLog.setOperatorTitle("修改支付密码");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
			operatorLog.setOperatorParams(user.toString());
			operatorLog.setOperatorReturn(b ? "修改支付密码成功" : "修改支付密码失败");
			operatorLog.setOperatorStatus(b ? 200 : 300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
			if (b) {
				result.put("message", "修改成功");
			}
		} else {
			result.put("message", "两次输入密码不一致请重新输入");
		}

		SpringUtils.renderJson(response, result);
	}
	
	/**
	 * 找回支付密码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("getUserPayPass.do")
	public String getUserPayPass(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/verify/getUserPayPass";
	}

	
	/**
	 * 修改手机号码
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("savePhone.do")
	public void savePhone(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = getParameters(request);
		User user = (User) request.getSession().getAttribute(
				Constants.FRONT_USER_SESSION);
		String email = user.getUserEmail();
		if (StringUtils.isBlank(email)) {
			SpringUtils.renderJsonResult(response,"301","对不起，您尚未填写邮箱！");
		}else{
			// 发送邮箱
			try {
				String phone = param.get("phoneNum");
				String path = request.getContextPath();
				String basePath = SysCacheUtils.getSysConfig().getSysWebsitedomain();
				SysLetterTemplates sysLetterTemplates = SysCacheUtils
						.getSysLetterTemplates();
				String mailContent = sysLetterTemplates
						.getSysUpdataphone()
						.replace("#userAccount#", user.getUserAccount())
						.replace(
								"#url#",basePath
										+ "/verify/updatePhone.do?code="
										+ MD5Utils.stringToMD5(user.getId().toString())
										+ "&send="
										+ Base64Utils.encodeStr(phone));
				
				MessageCenter center = new MessageCenter();
				center.setSendUserId(Constants.ADMIN_USER_ID);
				center.setReceiveUserId(user.getId());
				center.setMessageAddress(email);
				center.setMessageContent("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>"+mailContent+"</body></html>");
				center.setMessageTitle("【"+SysCacheUtils.getSysConfig().getSysWebsitename()+"】用户" + user.getUserAccount()
						+ "修改手机号码");
				center.setMessageSendIp(this.getIpAddr(request));
				messageCenterService.sendEmail(center, null);
				model.addAttribute("email", user.getUserEmail());
			} catch (Exception e) {
				SpringUtils.renderJsonResult(response,"301","很抱歉的通知您，修改手机号码邮件发送错误！");
				
			}
			SpringUtils.renderJsonResult(response,"200",
					"修改手机号码邮件已发送至您的****" + email.substring(4, email.length())
							+ "邮箱，请查收！");
		}
		
	}
	/**
	 * 修改手机号码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updatePhone.do")
	public String updatePhone(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		Map<String, String> param = getParameters(request);
		User user = this.loginFrontUser(request);
		String send = StringUtils.defaultString(request.getParameter("send"),"");
		Long sendTime = Long.valueOf(Base64Utils.decodeStr(send));
		String code = StringUtils.defaultIfEmpty(request.getParameter("code"),
				"");
		if (code.equals(MD5Utils.stringToMD5(user.getId().toString()))) {
			if (System.currentTimeMillis() - sendTime * 1000 > 1 * 24 * 60 * 60
					* 1000) {
				model.addAttribute(Constants.MESSAGE, "链接有效时间1天，已经失效，请重新找回密码。");
				return "userinfo/verify/userAttestation";
			}
			user.setUserPhone(sendTime.toString());
			userService.saveOrUpdate(user);
			User u = (User) request.getSession().getAttribute(
					Constants.FRONT_USER_SESSION);
			u.setUserPhone(sendTime.toString());
		}
		return "redirect:userAttestation.do";
	}
	
	/**
	 * 找回交易密码页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("getPayPwd.do")
	public void getjypaypwd(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = (User) request.getSession().getAttribute(
				Constants.FRONT_USER_SESSION);
		String email = user.getUserEmail();
		if (email.equals("") || email == null) {
			SpringUtils.renderJsonResult(response,"301","对不起，您尚未填写邮箱！");
		}else{
			// 发送邮箱
			try {
				String sendEmailTime = System.currentTimeMillis() / 1000 + "";
				String path = request.getContextPath();
				String basePath = SysCacheUtils.getSysConfig().getSysWebsitedomain();
				//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
				SysLetterTemplates sysLetterTemplates = SysCacheUtils
						.getSysLetterTemplates();
				String mailContent = sysLetterTemplates
						.getSysPaypwdMailinfo()
						.replace("#userAccount#", user.getUserAccount())
						.replace(
								"#url#",basePath
										+ "/verify/setPayPwd.do?send="
										+ sendEmailTime
										+ "&code="
										+ MD5Utils.stringToMD5(user.getId()
												.toString()));
				MessageCenter center = new MessageCenter();
				center.setSendUserId(Constants.ADMIN_USER_ID);
				center.setReceiveUserId(user.getId());
				center.setMessageAddress(email);
				center.setMessageContent("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>"+mailContent+"</body></html>");
				center.setMessageTitle("【"+SysCacheUtils.getSysConfig().getSysWebsitename()+"】用户" + user.getUserAccount()
						+ "找回支付密码邮件");
				center.setMessageSendIp(this.getIpAddr(request));
				messageCenterService.sendEmail(center, null);
				model.addAttribute("email", user.getUserEmail());
				SpringUtils.renderJsonResult(response,"200",
						"找回支付密码邮件已发送至您的****" + email.substring(4, email.length())
								+ "邮箱，请查收！");
			} catch (Exception e) {
				SpringUtils.renderJsonResult(response,"301","很抱歉的通知您，找回密码邮件发送错误！");
				
			}
		}
		
	}

	/**
	 * 忘记密码，发送邮箱，重置密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("setPayPwd.do")
	public String setjypaypwd(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		User user = this.loginFrontUser(request);
		Long sendTime = Long.valueOf(request.getParameter("send"));
		String code = StringUtils.defaultIfEmpty(request.getParameter("code"),
				"");
		if (code.equals(MD5Utils.stringToMD5(user.getId().toString()))) {
			if (System.currentTimeMillis() - sendTime * 1000 > 1 * 24 * 60 * 60
					* 1000) {
				model.addAttribute(Constants.MESSAGE, "链接有效时间1天，已经失效，请重新找回密码。");
				return "userinfo/verify/getUserPayPass";
			}
		}
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		return "userinfo/verify/setUserPayPwd";
	}

	@RequestMapping("resetPayPwd.do")
	public void resetPayPwd(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userService.getById(this.loginFrontUser(request).getId());
		User u = new User();
		u.setId(this.loginFrontUser(request).getId());
		Map<String, String> param = getParameters(request);
		//
		DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
		DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
		String newUserPaypassword = desEncrpt.decrypt(StringUtils.trimToEmpty(request.getParameter("newUserPaypassword")));
		if (param.get("newUserPaypassword").equals(
				param.get("newUserPaypassword2"))) {
			u.setUserPaypassword(MD5Utils.stringToMD5(aesEncrypt.encrypt(newUserPaypassword)));
			boolean b = userService.saveOrUpdate(u);
			User updateUser = userService.getById(this.loginFrontUser(request)
					.getId());
			request.getSession().setAttribute(Constants.FRONT_USER_SESSION,
					updateUser);
			/**
			 * 记录日志
			 */
			User loginUser = this.loginFrontUser(request);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
					+ loginUser.getUserAccount());
			operatorLog.setOperatorTitle("重置支付密码");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
			operatorLog.setOperatorParams(user.toString());
			operatorLog.setOperatorReturn(b ? "重置支付密码成功" : "重置支付密码失败");
			operatorLog.setOperatorStatus(b ? 200 : 300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
			if (b) {
				result.put("message", "重置成功");
			}
		} else {
			result.put("message", "两次输入密码不一致请重新输入");
		}
		SpringUtils.renderJson(response, result);
	}

	/**
	 * 账户认证
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("userAttestation.do")
	public String userAttestation(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user", user);
		// 账户信息
		UserAccount userAccount = this.userAccountService.getByUserId(this
				.loginFrontUser(request).getId());
		//实名认证收费
		BigDecimal availableMoney=userAccount.getAvailableMoney();
		BigDecimal sysAuthRate=SysCacheUtils.getSysFeesRate().getSysAuthRate();
		model.addAttribute("availableMoney",availableMoney);
		model.addAttribute("sysAuthRate",sysAuthRate);
		return "userinfo/identity/username";
	}
	
	/**
	 * 邮箱认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toEmail.do")
	public String toEmail(HttpServletRequest request,
			HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user", user);
		return "userinfo/identity/email";
	}
	
	/**
	 * 手机认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toPhone.do")
	public String toPhone(HttpServletRequest request,
			HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user", user);
		return "userinfo/identity/phone";
	}
	/**
	 * 视频认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toVideo.do")
	public String toVideo(HttpServletRequest request,
			HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user", user);
		return "userinfo/identity/video";
	}
	
	/**
	 * 现场认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toScene.do")
	public String toScene(HttpServletRequest request,
			HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user", user);
		return "userinfo/identity/scene";
	}
	/**
	 * 资料上传
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toUpload.do")
	public String toUpload(HttpServletRequest request,
			HttpServletResponse response, Model model){
		// 证件类型
		List<AttestationType> list = attestationTypeService
				.selectTypeByUserId(this.loginFrontUser(request).getId());
		model.addAttribute("attType", list);
		return "userinfo/identity/upload";
	}
	/**
	 * 证明材料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("list.do")
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model){
		return "userinfo/identity/list";
	}
	/**
	 * vip
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toVip.do")
	public String toVip(HttpServletRequest request,
			HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		// vip申请费用
		SysFeesRate sys = SysCacheUtils.getSysFeesRate();
		model.addAttribute("vipFee", sys.getSysVipRate());
		model.addAttribute("user", user);

		// 账户信息
		UserAccount userAccount = this.userAccountService.getByUserId(this
				.loginFrontUser(request).getId());
		model.addAttribute("userAccount", userAccount.getAvailableMoney());
		Map<String ,String> param=new HashMap<String, String>();
		param.put("userId", this.loginFrontUser(request).getId().toString());
	//	param.put("vipStatus", VipUser.VIP_USER.toString());
		// vip用户信息
		VipUser vip = this.vipUserService.selectByUserId(param);
		model.addAttribute("vip", vip);
		return "userinfo/identity/vip";
	}

	/**
	 * 证明材料分页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("dataPage.do")
	public void dataPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 证件类型
		List<AttestationType> list = attestationTypeService
				.selectTypeByUserId(this.loginFrontUser(request).getId());
		model.addAttribute("attType", list);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", this.loginFrontUser(request).getId().toString());
		// 证明材料列表
		PageModel pm = this.attestationApplyService.getList(map);
		SpringUtils.renderJson(response, pm);
	}

	/**
	 * 视频认证申请
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("videoAttestationApply.do")
	public void videoAttestationApply(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JsonResult json = new JsonResult();
		User user = userService.getById(this.loginFrontUser(request).getId());
		if(user.getRealnameStatus().equals(User.REALNAME_PASS) && user.getPhoneStatus().equals(User.PHONE_PASS) && user.getEmailStatus().equals(User.EMAIL_PASS)){
			user.setVideoStatus(User.VIDEO_APPLY);
			boolean bool = this.userService.saveOrUpdate(user);
			/**
			 * 记录日志
			 */
			User loginUser = this.loginFrontUser(request);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
					+ loginUser.getUserAccount());
			operatorLog.setOperatorTitle("申请视频认证");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION_APPLY);
			operatorLog.setOperatorParams(user.toString());
			operatorLog.setOperatorReturn(bool ? "申请视频认证成功" : "申请视频认证失败");
			operatorLog.setOperatorStatus(bool ? 200 : 300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("200");
			} else {
				json.setCode("300");
				json.setMsg("视频认证错误！！！");
			}
		}else{
			json.setCode("101");
		}
		SpringUtils.renderJson(response, json);
	}

	/**
	 * 手机认证
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("checkPhone.do")
	public void checkPhone(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = getParameters(request);
		JsonResult json = new JsonResult();
		/*User user = new User();
		user.setPhoneStatus(User.SCENE_APPLY);
		user.setUserPhone(param.get("phoneNum"));*/
		User u = this.loginFrontUser(request);
		/*user.setId(this.loginFrontUser(request).getId());*/
		u.setUserPhone(param.get("phoneNum"));
		u.setPhoneStatus(2);
		boolean bool = this.userService.saveOrUpdate(u);
		request.getSession().setAttribute(Constants.FRONT_USER_SESSION, u);
		/**
		 * 记录日志
		 */
		User loginUser = this.loginFrontUser(request);
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
				+ loginUser.getUserAccount());
		operatorLog.setOperatorTitle("申请手机认证");
		operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION_APPLY);
		operatorLog.setOperatorParams(u.toString());
		operatorLog.setOperatorReturn(bool ? "手机认证成功" : "手机认证失败");
		operatorLog.setOperatorStatus(bool ? 200 : 300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLog.setOperatorIp(this.getIpAddr(request));
		operatorLogService.addFrontLog(operatorLog);
		if (bool) {
			json.setCode("201");
		} else {
			json.setCode("300");
			json.setMsg("手机认证错误！！！");
		}
		SpringUtils.renderJson(response, json);
	}
	
	/**
	 * 生成手机认证码
	 */
	@RequestMapping("user/sendSms.do")
	public void sendSms(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = userService.getById(this.loginFrontUser(request).getId());
		Integer userId = user.getId();
		String tel = request.getParameter("tel");
		int rand = new Random().nextInt(1000000);
		Cookie cookie = new Cookie("smsRand", rand + "");
		cookie.setMaxAge(5 * 60);
		response.addCookie(cookie);
		request.getSession().setAttribute("phonevalcode", rand + "");
		//String content = "尊敬的"+SysCacheUtils.getSysConfig().getSysWebsitesignature()+"用户，您好！感谢您进行手机短信认证，您的验证码为："+rand+"，请您在5分钟内输入该验证码完成手机认证。";
		String content="您的验证码是："+rand+"。请不要把验证码泄露给其他人。";
		Date sendTime = new Date();
		MessageCenter center = new MessageCenter();
		center.setMessageAddress(tel);
		center.setMessageSendDatetime(sendTime);
		center.setMessageContent(content);
		center.setMessageSendIp(this.getIpAddr(request));
		center.setReceiveUserId(userId);
		center.setMessageTitle("手机短信认证");
		center.setNoticeTypeId(MessageCenter.SMS);
		center.setSendUserId(Constants.ADMIN_USER_ID);
		int result = SmsUtil.sendSms(tel,content);
		OperatorLog log = new OperatorLog();
		 log.setLogUserid(userId.toString());
		 log.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
		 log.setOperatorIp(this.getIpAddr(request));
		 log.setOperatorType(OperatorLog.TYPE_FRONT);
		 log.setOperatorTitle("前台用户短信手机认证");
		if(result==0){
			log.setOperatorReturn("手机认证验证码短信发送成功，返回字符串为："+result);
			log.setOperatorStatus(200);
			Cookie cookiePhone = new Cookie("smsPhone", tel + "");
			cookie.setMaxAge(5 * 60);
			response.addCookie(cookiePhone);
			SpringUtils.renderJsonResult(response, JsonResult.SUCCESS,"手机认证短信发送成功");
		}else{
			log.setOperatorReturn("手机认证验证码短信发送失败，错误码为："+result);
			log.setOperatorStatus(300);
			logger.error("短信发送失败，错误码："+result);
			SpringUtils.renderJsonResult(response,"301","手机认证短信发送失败");
		}
		operatorLogService.add(log);
		messageCenterService.addMsg(center);
		System.out.println("sendSms:" + tel + "-->" + rand);
	}
	
	/**
	 * 使用验证码修改手机号
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("user/updatePhoneForCode.do")
	public String updatePhoneForCode(HttpServletRequest request,HttpServletResponse response,Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		Integer userId = user.getId();
		String tel = request.getParameter("tel");
		int rand = new Random().nextInt(1000000);
		Cookie cookie = new Cookie("smsRand", rand + "");
		cookie.setMaxAge(5 * 60);
		response.addCookie(cookie);
		request.getSession().setAttribute("phonevalcode", rand + "");
		//手机认证已经通过、修改手机号
		if(user.getPhoneStatus().equals(User.REALNAME_PASS) && user.getUserPhone() != null){
			String content = "尊敬的"+SysCacheUtils.getSysConfig().getSysWebsitesignature()+"用户，您好！您正在进行修改手机号操作，您的验证码为："+rand+"，请您在5分钟内输入该验证码完成修改手机号操作。";
			Date sendTime = new Date();
			MessageCenter center = new MessageCenter();
			center.setMessageAddress(user.getUserPhone());
			center.setMessageSendDatetime(sendTime);
			center.setMessageContent(content);
			center.setMessageSendIp(this.getIpAddr(request));
			center.setReceiveUserId(userId);
			center.setMessageTitle("修改手机号");
			center.setNoticeTypeId(MessageCenter.SMS);
			center.setSendUserId(Constants.ADMIN_USER_ID);
			int result = SmsUtil.sendSms(user.getUserPhone().toString(), content);
			OperatorLog log = new OperatorLog();
			 log.setLogUserid(userId.toString());
			 log.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			 log.setOperatorIp(this.getIpAddr(request));
			 log.setOperatorType(OperatorLog.TYPE_FRONT);
			 log.setOperatorTitle("前台用户修改手机号");
			 if(result==0){
					log.setOperatorReturn("修改手机号验证码短信发送成功，返回字符串为："+result);
					log.setOperatorStatus(200);
				}else{
					log.setOperatorReturn("修改手机号验证码短信发送失败，错误码为："+result);
					log.setOperatorStatus(300);
					logger.error("短信发送失败，错误码："+result);
				}
				operatorLogService.add(log);
				messageCenterService.addMsg(center);
				System.out.println("sendSms:" + tel + "-->" + rand);
				//SpringUtils.renderJsonResult(response,JsonResult.SUCCESS,"修改手机号短信发送成功");
			}
			return "userinfo/verify/resetPhone";
	}

	/**
	 * 验证手机认证码
	 */
	@RequestMapping("user/checkSmsCode.do")
	public void checkSmsCode(HttpServletRequest request,
			HttpServletResponse response) {
		User user = userService.getById(this.loginFrontUser(request).getId());
		//手机未认证或认证失败
		if(user.getPhoneStatus() == User.PHONE_FAIL || (user.getUserPhone() == null && user.getPhoneStatus()==User.PHONE_DEFAULT)){
			String sendSmsCode = "";
			String phone = "";
			String inputSmsCode = request.getParameter("smsCode");
			String tel = request.getParameter("tel");
			Cookie[] cookie = request.getCookies();
			for (int i = 0; i < cookie.length; i++) {
				Cookie cook = cookie[i];
				if (cook.getName().equalsIgnoreCase("smsRand")) { // 获取键
					sendSmsCode = cook.getValue().toString(); // 获取值
					System.out.println(sendSmsCode);
				}
				if (cook.getName().equalsIgnoreCase("smsPhone")) { // 获取键
					phone = cook.getValue().toString(); // 获取值
					System.out.println(phone);
				}
				if (StringUtils.isNotBlank(phone)&&StringUtils.isNotBlank(sendSmsCode)) {
					break;
				}
			}
			if ("".equals(sendSmsCode)){
				SpringUtils.renderJsonResult(response, "101","验证码过期");
			}else if (inputSmsCode.equals(sendSmsCode)&&tel.equals(phone)) {
				user.setUserPhone(tel);
				user.setPhoneStatus(User.PHONE_PASS);
				userService.saveOrUpdate(user);
				boolean flag = false;
				flag=this.userService.updateUser(user, CreditType.PHONE,user.getId(), null,User.PHONE_PASS);
				//资料完整度
				if(flag==true){
					User integralUser = this.userService.getById(user.getId());
					CreditType credit=this.creditTypeService.getCreditType(null, CreditType.PHONE);
					String integral = integralUser.getUserIntegral();
					if(integral != null){
						Integer integral1 = Integer.parseInt(integral);
						Integer userIntegral = integral1+credit.getCreditScore();
						integralUser.setUserIntegral(userIntegral.toString());
					}else{
						integralUser.setUserIntegral(credit.getCreditScore().toString());
					}
					userService.updateByPrimaryKeySelective(integralUser);
					/**
					 * 发送站内信
					 */
					MessageCenter c=new MessageCenter();
					c.setMessageSendDatetime(new Date());
					c.setMessageSendIp(this.getIpAddr(request));
					c.setMessageStatus(MessageCenter.STATUS_UNREAD);
					c.setReceiveUserId(user.getId());
					c.setSendUserId(Constants.ADMIN_USER_ID);
					c.setMessageContent(flag&&user.getPhoneStatus()==User.PHONE_PASS?"用户"+user.getUserAccount()+"，你的手机认证通过，添加积分"+credit.getCreditScore().toString():"用户"+user.getUserAccount()+"，你的手机认证不通过。");
					c.setMessageTitle("手机认证");
					this.messageCenterService.send(c, flag&&user.getPhoneStatus()==User.PHONE_PASS?Notice.PHONE_YES:Notice.PHONE_NO);
				}
				
				SpringUtils.renderJsonResult(response, JsonResult.SUCCESS,"校验成功");
			} else {
				SpringUtils.renderJsonResult(response,"301","验证码或手机号校验失败"); // 验证码校验失败
			}
		}else{
			String sendSmsCode = "";
			String inputSmsCode = request.getParameter("smsCode");
			String tel = request.getParameter("tel");
			Cookie[] cookie = request.getCookies();
			for (int i = 0; i < cookie.length; i++) {
				Cookie cook = cookie[i];
				if (cook.getName().equalsIgnoreCase("smsRand")) { // 获取键
					sendSmsCode = cook.getValue().toString(); // 获取值
					System.out.println(sendSmsCode);
					break;
				}
			}
			if ("".equals(sendSmsCode)){
				SpringUtils.renderJsonResult(response, "101","验证码过期");
			}else if (inputSmsCode.equals(sendSmsCode)) {
				user.setUserPhone(tel);
				user.setPhoneStatus(User.PHONE_PASS);
				userService.saveOrUpdate(user);
				SpringUtils.renderJsonResult(response, JsonResult.SUCCESS,"校验成功");
			} else {
				SpringUtils.renderJsonResult(response,"302","验证码校验失败"); // 验证码校验失败
			}
		}
	}

	/**
	 * 现场认证申请
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("sceneAttestationApply.do")
	public void sceneAttestationApply(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JsonResult json = new JsonResult();
		User user = userService.getById(this.loginFrontUser(request).getId());
		if(user.getRealnameStatus().equals(User.REALNAME_PASS) && user.getPhoneStatus().equals(User.PHONE_PASS) && user.getEmailStatus().equals(User.EMAIL_PASS)){
			user.setSceneStatus(User.SCENE_APPLY);
			boolean bool = this.userService.saveOrUpdate(user);
			/**
			 * 记录日志
			 */
			User loginUser = this.loginFrontUser(request);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
					+ loginUser.getUserAccount());
			operatorLog.setOperatorTitle("申请现场认证");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION_APPLY);
			operatorLog.setOperatorParams(user.toString());
			operatorLog.setOperatorReturn(bool ? "申请现场认证成功" : "申请现场认证失败");
			operatorLog.setOperatorStatus(bool ? 200 : 300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("201");
			} else {
				json.setCode("300");
				json.setMsg("视频认证错误！！！");
			}
		}else{
			json.setCode("103");
		}
		SpringUtils.renderJson(response, json);
	}

	/***
	 * 额度申请查询
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("evaluateApply.do")
	public String evaluateApply(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		try {
			String msg = "";
			Map<String ,String> param=new HashMap<String, String>();
			param.put("userId", this.loginFrontUser(request).getId().toString());
			param.put("vipStatus", VipUser.VIP_USER.toString());
			VipUser user = this.vipUserService.selectByUserId(param);
			if (user==null) {
				msg = "80";
				request.setAttribute("error", "80");
			}
			UserEvaluateApply apply = userEvaluateApplyService.selectByUserId(this.loginFrontUser(request).getId());
			if(apply != null){
				model.addAttribute("apply", apply);
			}
		} catch (Exception e) {
			
		}
		return "userinfo/evaluateApply/evaluateApply";
	}

	/***
	 * 额度申请
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toEvaluateApply.do")
	public void toEvaluateApply(HttpServletRequest request,
			HttpServletResponse response, Model model,
			UserEvaluateApply userEvaluateApply) {
		try {
			boolean flag = false;
			User loginUser = this.loginFrontUser(request);
			userEvaluateApply.setUserId(loginUser.getId());
			JsonResult json = new JsonResult();
			userEvaluateApply.setEvaluateapplyAddip(this.getIpAddr(request));
			// 该用户是否申请过额度申请
			UserEvaluateApply apply = this.userEvaluateApplyService
					.selectByUserId(loginUser.getId());
			Map<String ,String> param=new HashMap<String, String>();
			param.put("userId", loginUser.getId().toString());
			param.put("vipStatus", VipUser.VIP_USER.toString());
			VipUser user = this.vipUserService.selectByUserId(param);
			if(user!=null){
				// 申请额度是否大于0
				if (CompareUtils
						.greaterThanZero(userEvaluateApply.getAmountApply())) {
					if (apply != null) {
						int day;
						day = DateUtils.daysBetween(
								apply.getEvaluateapplyAddtime(), new Date());
						if (day >= 30) {
							flag = this.userEvaluateApplyService
									.toEvaluateApply(userEvaluateApply);
							if (flag) {
								json.setCode("200");
							} else {
								json.setCode("300");

							}
						} else {
							json.setCode("201");
							json.setMsg("申请失败，额度申请一个月只能申请一次！！！");
						}
					} else {
						flag = this.userEvaluateApplyService
								.toEvaluateApply(userEvaluateApply);
						if (flag) {
							json.setCode("200");
						} else {
							json.setCode("300");
						}
					}
					/**
					 * 记录日志
					 */
					OperatorLog operatorLog = new OperatorLog();
					operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
							+ loginUser.getUserAccount());
					operatorLog.setOperatorTitle("额度申请");
					operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
					operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
					operatorLog.setOperatorParams(userEvaluateApply.toString());
					operatorLog.setOperatorReturn(flag ? "额度申请成功" : "额度申请失败");
					operatorLog.setOperatorStatus(flag ? 200 : 300);
					operatorLog.setLinkUrl(RequestUtils.getIpAddr());
					operatorLog.setOperatorIp(this.getIpAddr(request));
					operatorLogService.addFrontLog(operatorLog);
				} else {
					json.setCode("202");
					json.setMsg("申请额度必须大于0");
				}
			}else{
				json.setCode("203");
				json.setMsg("您还不是Vip用户，请先申请Vip用户");
			}

			SpringUtils.renderJson(response, json);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 额度申请列表
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("evaluateApplyPage.do")
	public void evaluateApplyPage(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, String> paramsMap = getParameters(request);
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		PageModel pageModel = userEvaluateApplyService.getApplyByUserId(paramsMap);
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);
	}

	/**
	 * 资料上传申请
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param attestationApply
	 */
	@RequestMapping("toAttestationApply.do")
	public void toAttestationApply(HttpServletRequest request,
			HttpServletResponse response, Model model,
			AttestationApply attestationApply) {
		try {
			JsonResult json = new JsonResult();
			if (this.validateCaptcha(request)) {
				attestationApply
						.setUserId(this.loginFrontUser(request).getId());
				AttestationApply apply = this.attestationApplyService
						.selectByuserIdAndType(attestationApply);
				if (apply == null) {
					attestationApply.setUserId(this.loginFrontUser(request)
							.getId());
					if (attestationApply.getAttestationImg() != null
							&& attestationApply.getAttestationTypeid() != null) {
						attestationApply.setAttestationApplyIp(this
								.getIpAddr(request));
						boolean flag = this.attestationApplyService
								.add(attestationApply);
						/**
						 * 记录日志
						 */
						User loginUser = this.loginFrontUser(request);
						OperatorLog operatorLog = new OperatorLog();
						operatorLog.setLogUserid("用户Id：" + loginUser.getId()
								+ ",用户名：" + loginUser.getUserAccount());
						operatorLog.setOperatorTitle("申请资料上传");
						operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
						operatorLog
								.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION_APPLY);
						operatorLog.setOperatorParams(attestationApply
								.toString());
						operatorLog.setOperatorReturn(flag ? "申请资料上传成功"
								: "申请资料上传失败");
						operatorLog.setOperatorStatus(flag ? 200 : 300);
						operatorLog.setLinkUrl(RequestUtils.getIpAddr());
						operatorLog.setOperatorIp(this.getIpAddr(request));
						operatorLogService.addFrontLog(operatorLog);
						if (flag) {
							json.setCode("200");
						} else {
							json.setCode("201");
							json.setMsg("资料上传失败");
						}
					} else {
						logger.error("资料上传图片必须上传");
					}
				} else {
					json.setCode("203");
					json.setMsg("证件类型的资料已上传，不能重复上传！！！");
				}
			} else {
				json.setCode("202");
				json.setMsg("验证码错误！");
			}
			SpringUtils.renderJson(response, json);
		} catch (Exception e) {
			
		}

	}

	/**
	 * Vip申请
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param attestationApply
	 */
	@RequestMapping("toVipApply.do")
	public void toVipApply(HttpServletRequest request,
			HttpServletResponse response, Model model, VipUser vipUser) {
		try {
			JsonResult json = new JsonResult();
			User luser=this.userService.getById(this.loginFrontUser(request).getId());
			UserAccount userAccount = this.userAccountService.selectByUserIdForUpdate(luser.getId());
			SysFeesRate sys = SysCacheUtils.getSysFeesRate();
			if (this.validateCaptcha(request)) {
				if(luser.getRealnameStatus()==User.REALNAME_PASS&&luser.getPhoneStatus()==User.PHONE_PASS&&luser.getEmailStatus()==User.PHONE_PASS){
					if (vipUser.getVipCustomer() == null) {
						json.setCode("210");
						json.setMsg("请选择客服");
					} else {
						
						if (CompareUtils.greaterEquals(userAccount.getAvailableMoney().subtract(sys.getSysVipRate()), new BigDecimal(0)) ) {
							Map<String ,String> param=new HashMap<String, String>();
							param.put("userId", luser.getId().toString());
							//param.put("vipStatus", VipUser.VIP_USER.toString());
							VipUser user = this.vipUserService.selectByUserId(param);
							if (user != null && user.getVipStatus()==VipUser.VIP_END) {
								vipUser.setUserId(user.getId());
								vipUser.setVipUpdateDatetime(new Date());
								//判断该用户是否封号
								json.setCode("215");
								json.setMsg("已封号，不能申请vip用户");
							} else {
								vipUser.setVipUpdateIp(this.getIpAddr(request));
								vipUser.setVipCard(GenerateNo.vipCard(this.loginFrontUser(request).getId()));
								vipUser.setVipAddDatetime(new Date());
								vipUser.setVipAddIp(this.getIpAddr(request));
								
								// 获取vip申请费用
								BigDecimal sysVipRate = SysCacheUtils.getSysFeesRate().getSysVipRate();
								// vip申请是否需要费用
								if (sysVipRate.compareTo(BigDecimal.valueOf(0)) == 1) {
									userAccount.setAvailableMoney(userAccount.getAvailableMoney().subtract(sysVipRate));
									userAccount.setUnavailableMoney(userAccount.getUnavailableMoney().add(sysVipRate));
									//更新用户资金账户记录信息
									userAccountService.updateByPrimaryKeySelective(userAccount);
									//添加一条用户vip申请冻结资金的资金明细信息
									accountLogService.add(userAccount, AccountLog.TRADE_CODE_VIP, sysVipRate, BigDecimal.valueOf(0), vipUser.getId(), "用户vip申请费用冻结" + sysVipRate + "元", RequestUtils.getIpAddr());
									
								}
							}
							vipUser.setUserId(this.loginFrontUser(request).getId());
							vipUser.setVipStartDate(new Date());
							vipUser.setVipEndDate(DateUtils.addMonth(new Date(),
									VipUser.VIP_MONTH_NUM));
							vipUser.setVipMonthNum(VipUser.VIP_MONTH_NUM);
							vipUser.setVipStatus(VipUser.VIP_APPLY);
							boolean flag = this.vipUserService.saveOrUpdate(vipUser);
							/**
							 * 记录日志
							 */
							User loginUser = this.loginFrontUser(request);
							OperatorLog operatorLog = new OperatorLog();
							operatorLog.setLogUserid("用户Id：" + loginUser.getId()
									+ ",用户名：" + loginUser.getUserAccount());
							operatorLog.setOperatorTitle("VIP申请");
							operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
							operatorLog
									.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION_APPLY);
							operatorLog.setOperatorParams(vipUser.toString());
							operatorLog.setOperatorReturn(flag ? "申请vip添加成功"
									: "申请vip添加失败");
							operatorLog.setOperatorStatus(flag ? 200 : 300);
							operatorLog.setLinkUrl(RequestUtils.getIpAddr());
							operatorLog.setOperatorIp(this.getIpAddr(request));
							operatorLogService.addFrontLog(operatorLog);
							if (flag) {
								json.setCode("212");
								json.setMsg("申请");
							} else {
								json.setCode("213");
								json.setMsg("申请失败！");
							}
						} else {
							json.setCode("211");
							json.setMsg("你的余额不足！");
						}
					}
				}else{
					json.setCode("216");
					json.setMsg("请先进行实名认证，邮箱认证，手机认证");
				}
			} else {
				json.setCode("202");
				json.setMsg("验证码错误！");
			}
			SpringUtils.renderJson(response, json);
		} catch (Exception e) {
			
		}
	}
}
