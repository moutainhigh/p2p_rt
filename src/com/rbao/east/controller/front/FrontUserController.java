package com.rbao.east.controller.front;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.log.SysoCounter;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.Recommend;
import com.rbao.east.entity.RecommendReward;
import com.rbao.east.entity.RedenvelopesRecord;
import com.rbao.east.entity.SysConfigParams;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.SysLetterTemplates;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.CreditTypeService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.OperatorLogService;
import com.rbao.east.service.RecommendService;
import com.rbao.east.service.RedenvelopesProbabilityService;
import com.rbao.east.service.RedenvelopesService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.Base64Utils;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.JsonUtils;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SmsUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;
import com.rbao.east.utils.SysCacheUtils;
/**
 * 前台用户操作
 * */
@Controller
public class FrontUserController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(FrontUserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private ImageCaptchaService captchaService;
	@Autowired
	private OperatorLogService operatorLogService;
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private CreditTypeService creditTypeService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private RedenvelopesProbabilityService redenvelopesProbabilityService;
	@Autowired
	RedenvelopesService redService ;
	@Autowired
	private SysConfigParamService sysConfigParamService;
	
	@SuppressWarnings("unused")
	@RequestMapping("selectByRecommendUserId.do")
	public String selectByRecommendUserId(HttpServletRequest request,
			HttpServletResponse response){
		PageModel page=this.recommendService.selectCountRecommend(null);
		return "";
	}
	
	@RequestMapping("checkCode.do")
	public void checkCode(HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = false;
		flag = captchaService.validateResponseForID(
				request.getSession(false).getId(),
				request.getParameter("captcha").toLowerCase());
		if (flag == true) {
			SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "成功");
		} else {
			SpringUtils.renderJsonResult(response,"301", "已经重复");
		}
	}

	/**
	 * 检测用户名是否已存在
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkUserAccount.do")
	public void checkUserAccount(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> param = this.getParameters(request);
		User user = this.userService.selectByUserUid(param);
		if (user == null) {
			SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "成功");
		} else {
			SpringUtils.renderJsonResult(response,"301", "已经存在");
		}
	}

	@RequestMapping("checkUserAccount2.do")
	public void checkUserAccount(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JsonResult JsonResult=null;
		try {
			Map<String, Object> param=this.getParametersO(request);
			if (null == param) {
				JsonResult=new JsonResult("300","参数错误");
				return;
			}else{
				if(!param.containsKey("userAccount")){
					JsonResult=new JsonResult("300","参数错误");
					return;
				}
			}
			String userAccount=param.get("userAccount").toString();
			User user=userService.selectByUserUid(userAccount);
			if(null!=user){
				JsonResult=new JsonResult("300","用户名已存在");
				logger.info("ip:"+this.getIpAddr(request)+",用户名已存在！！！用户名:"+userAccount);
			}else{
				JsonResult=new JsonResult(JsonResult.SUCCESS,"该用户名可以注册");
				logger.info("ip:"+this.getIpAddr(request)+",该用户名可以注册！！！用户名:"+userAccount);
			}
		} catch (Exception e) {
			JsonResult=new JsonResult("300","验证用户名失败,请稍后再试！！！");
			logger.error("ip:"+this.getIpAddr(request)+",验证用户名失败！！！",e);
		} finally {
			JsonUtils.toJson(response, JsonResult);
		}
	}
	
	
	/**
	 * 检测邮箱是否已存在
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkUserEmail.do")
	public void checkUserEmail(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> param = this.getParameters(request);
		User user = this.userService.checkUserByEmail(param);
		if (user == null) {
			SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "成功");
		} else {
			SpringUtils.renderJsonResult(response,"301", "已经重复");
		}
	}

	@RequestMapping("toRegister.do")
	public String toRegister(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String invite_userid = request.getParameter("u");
		if(StringUtils.isNotBlank(invite_userid)) {
			model.addAttribute("inviteUserid", Base64Utils.decodeStr(invite_userid));
		}
		//判断推广状态是否关闭
		Map<String,String> map= SysCacheUtils.getConfigParams();
		model.addAttribute("recommendStatus", map.get("recommend_status"));
		model.addAttribute("registerProtocol", SysCacheUtils.getSysConfig().getSysRegisterProtocol());
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		return "user/register";
	}

	/**
	 * 前台用户注册
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("register.do")
	public String register(HttpServletRequest request,
			HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		String registerType = request.getParameter("registerType");
		String inviteUserId = request.getParameter("inviteUserid").trim();
		//String userAccount = request.getParameter("userAccount");
		//String userPassword = request.getParameter("userPassword");
		String userPhone = request.getParameter("userPhone");
		String accessToken = request.getParameter("accessToken");
		String isThirdparty = request.getParameter("isThirdparty");
		String uid = request.getParameter("uid");
		DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
		DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
		String userPassword  =desEncrpt.decrypt(StringUtils.trimToEmpty(request
				.getParameter("userPassword")));
		if (StringUtils.isNotBlank(registerType)) {
			String errormsg = "";
			errormsg = loginValidate(request, errormsg, userPhone,
					userPassword);
			if (StringUtils.isNotBlank(errormsg)) {
				model.addAttribute(Constants.MESSAGE, errormsg);
			}
			/*if (validateSubmit(request, response) == false) {
				errormsg = "验证码输入有误。";
			}*/
			User existUser = userService.selectByUserUid(userPhone);
			if (existUser != null) {
				errormsg = "用户名已经存在。";
			}
			//验证是否有中文
			Pattern p=Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m=p.matcher(userPhone);
			boolean result=m.find();
			if(result){
				errormsg = "用户名不能为中文,请输入6-16位字符.英文,数字";
			}
			/*String userEmail = request.getParameter("userEmail");
			if (isEmail(userEmail) == false) {
				errormsg = "邮箱输入有误哈哈哈哈。";
			}*/
			Map<String, String> param = new HashMap<String, String>();
			param.put("userEmail", request.getParameter("userEmail"));
			User emailUser = userService.checkUserByEmail(param);
			if (emailUser != null) {
				errormsg = "邮箱已存在。";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isEmpty(errormsg)) {
				User user = new User();
				user.setUserAccount(userPhone);
				user.setUserPhone(userPhone);
				user.setPhoneStatus(2);
				user.setUserPassword(MD5Utils.stringToMD5(aesEncrypt.encrypt(userPassword)));
			/*	user.setUserEmail(userEmail);*/
				user.setTypeId(1);
				
				if(isThirdparty!=null){
					user.setIsThirdparty(Integer.parseInt(isThirdparty));
					
				}
				if(uid!=null){
					user.setUid(uid);
				}
				Map<String,String> map= SysCacheUtils.getConfigParams();
				try {
					user.setUserAddtime(sdf.parse(sdf.format(new Date())));
					user.setUserAddip(this.getIpAddr(request));
					if(map.get("recommend_status").equals(SysConfigParams.recommend_open)){
						// 邀请注册时设置邀请人Id，邀请奖励
						if (StringUtils.isNotBlank(inviteUserId)) {
							/*String inviteId = Base64Utils.decodeStr(inviteUserId);*/
							user.setInviteUserid(Integer.parseInt(inviteUserId));
							String inviteReward =map.get("recommend_InviteReward");
							if (CompareUtils.greaterThanZero(new BigDecimal(inviteReward))) {
								user.setInviteMoney(inviteReward);
							}
						}
					}
					
				} catch (Exception e) {
					
				}
		
				boolean flag=userService.saveOrUpdate(user);
				if(flag){
					userCreditService.addUserCredit(CreditType.PHONE, user.getId());
					creditLogService.addCreditLog(CreditType.PHONE, user.getId());
					//自动登录
//					String account = request.getParameter("userAccount");
					DesEncrypt desEncrpt1 = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
					DesEncrypt aesEncrypt1 = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
					String pwd = desEncrpt1.decrypt(StringUtils.trimToEmpty(request.getParameter("userPassword")));
					System.out.println(pwd);
					
					if (!hasCookieValue(request)) {// 没有保存cookie进行登录校验，有直接读取cookie进行登录
						if (isSession(request)) {
							return "redirect:index.do";
						}
					}
					String loginType = request.getParameter("loginType");
					if (StringUtils.isNotBlank(loginType)) {
						String errormsg1 = "";
						// 验证用户名密码是否为空
						errormsg1 = loginValidate(request, errormsg1, userPhone, pwd);
						if (StringUtils.isEmpty(errormsg1)) {
							User user1 = userService.selectByUserUid(userPhone);
							if (user1 != null) {
								if (MD5Utils.stringToMD5(aesEncrypt1.encrypt(pwd))
										.equals(user1.getUserPassword())) {
									String flag1 = request.getParameter("flag");
									if (flag1 != null && flag1.equals("1")) {
										Cookie cookie = new Cookie("cookie_user", java.net.URLEncoder.encode(userPhone,Constants.UTF8) + "-" + pwd);
										cookie.setMaxAge(60 * 60 * 24 * 7); // cookie 保存7天
										response.addCookie(cookie);
									} else {
										Cookie cookie = new Cookie("cookie_user", java.net.URLEncoder.encode(userPhone,Constants.UTF8) + "-" + null);
										cookie.setMaxAge(60 * 60 * 24 * 7); // cookie 保存7天
										response.addCookie(cookie);
									}
									if (user1.getUserIslock() == 0) {
										request.getSession().setAttribute(
												Constants.FRONT_USER_SESSION, user1);
										SimpleDateFormat sdf1 = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");
										try {
											user1.setUserLogintime(sdf1.parse(sdf1
													.format(new Date())));
											user1.setUserLoginip(this.getIpAddr(request));
											userService.saveOrUpdate(user1);
										} catch (Exception e) {
											logger.error("修改用户信息信息出错");
											
										}
										// 添加日志
										try {
											OperatorLog log = new OperatorLog();
											log.setLogUserid(user1.getId().toString());
											log.setOperatorIp(this.getIpAddr(request));
											log.setOperatorTitle("前台用户"
													+ user1.getUserAccount() + "登录");
											log.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
											log.setOperatorType(OperatorLog.TYPE_FRONT);
											log.setOperatorReturn("登录成功");
											log.setOperatorStatus(200);
											log.setOperatorParams(user1.getUserAccount());
											operatorLogService.addFrontLog(log);
										} catch (Exception e) {
											logger.error("添加日志失败");
											
										}
										/*//添加登陆红包
										redService. sendLoginRedRecord(user.getId(),false);
										//添加投资满满额红包
										redService.sendTenderRedRecord(user.getId(), new BigDecimal("5"));
										redService.sendTenderRedRecord(user.getId(), new BigDecimal("10"));
										redService.sendTenderRedRecord(user.getId(), new BigDecimal("100"));*/
										if(map.get("recommend_status").equals(SysConfigParams.recommend_open)){
											
											User recommendUser = userService.selectByUserUid(userPhone);
											if (recommendUser.getInviteUserid() != null) {
												Recommend recommend = new Recommend();
												recommend
														.setRecommendStatus(Recommend.RECOMMEND_STATUS_NEW);
												recommend.setRecommendUserid(recommendUser
														.getInviteUserid());
												recommend.setUserId(recommendUser.getId());
												if (!recommendUser.getInviteMoney().equals("0")) {
													recommend.setRecommendMoney(BigDecimal.valueOf(Double.parseDouble(recommendUser.getInviteMoney())));
												} else {
													recommend.setRecommendMoney(new BigDecimal(0));
												}
												recommendService.saveRecommend(recommend,BigDecimal.valueOf(Double.parseDouble(recommendUser.getInviteMoney())));
												recommendService.allRecommendUser(recommendUser.getId(),Recommend.initLevel ,new BigDecimal(0),new BigDecimal(0),RecommendReward.StatusStaying,null,null);
											}
										}
										
										
										/*//添加注册奖励
										SysFeesRate fee = SysCacheUtils.getSysFeesRate();
										Integer registerAwardType=fee.getSysRegisteredType();
										if(registerAwardType.equals(SysFeesRate.typeRegister)){
											userAccountService.addRegisterAward(user.getId());
										}*/
										
										return "redirect:registernotice.do";
									} else {
										errormsg1 = "账户锁定，请联系系统管理员。";
									}
								} else {
									errormsg1 = "密码输入有误，请重新输入。";
								}
							} else {
								errormsg1 = "用户名不存在，请重新输入。";
							}
						} else {
							OperatorLog log = new OperatorLog();
							log.setLogUserid(StringUtils.trim(userPhone));
							log.setOperatorIp(this.getIpAddr(request));

							log.setOperatorTitle("前台用户" + userPhone + "登录");
							log.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
							log.setOperatorType(OperatorLog.TYPE_FRONT);
							log.setOperatorReturn("登录失败");
							log.setOperatorStatus(300);
							log.setOperatorParams(userPhone);
							operatorLogService.addFrontLog(log);
						}
						model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
						model.addAttribute(Constants.MESSAGE, errormsg1);
					}
					return "user/registernotice";
					
				}
				/*if(map.get("recommend_status").equals(SysConfigParams.recommend_open)){
					User recommendUser = userService.selectByUserUid(userAccount);
					if (recommendUser.getInviteUserid() != null) {
						Recommend recommend = new Recommend();
						recommend
								.setRecommendStatus(Recommend.RECOMMEND_STATUS_NEW);
						recommend.setRecommendUserid(recommendUser
								.getInviteUserid());
						recommend.setUserId(recommendUser.getId());
						if (!recommendUser.getInviteMoney().equals("0")) {
							recommend.setRecommendMoney(BigDecimal.valueOf(Double.parseDouble(recommendUser.getInviteMoney())));
						} else {
							recommend.setRecommendMoney(new BigDecimal(0));
						}
						recommendService.saveRecommend(recommend,BigDecimal.valueOf(Double.parseDouble(recommendUser.getInviteMoney())));
						recommendService.allRecommendUser(recommendUser.getId(),Recommend.initLevel ,new BigDecimal(0),new BigDecimal(0),RecommendReward.StatusStaying);
					}
				}
				
				
				//添加注册奖励
				SysFeesRate fee = SysCacheUtils.getSysFeesRate();
				Integer registerAwardType=fee.getSysRegisteredType();
				if(registerAwardType.equals(SysFeesRate.typeRegister)){
					userAccountService.addRegisterAward(user.getId());
				}*/
				// 添加日志
				try {
					OperatorLog log = new OperatorLog();
					log.setCreateTime(sdf.parse(sdf.format(new Date())));
					log.setOperatorIp(this.getIpAddr(request));
					log.setOperatorTitle("前台用户" + userPhone + "注册");
					log.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
					log.setOperatorType(OperatorLog.TYPE_FRONT);
					log.setOperatorReturn("注册成功");
					log.setOperatorStatus(200);
					log.setOperatorParams(userPhone);
					log.setOperatorType(OperatorLog.TYPE_FRONT);
					operatorLogService.addFrontLog(log);
				} catch (Exception e) {
					logger.error("添加日志失败");
					
				}

				/*Map<String, String> params = new HashMap<String, String>();
				params.put("userAccount", userAccount);
				User us = userService.getUserByName(params);

				MessageCenter center = new MessageCenter();
				center.setSendUserId(Constants.ADMIN_USER_ID);
				center.setReceiveUserId(us.getId());
				center.setMessageTitle("【"+SysCacheUtils.getSysConfig().getSysWebsitename()+"】用户" + us.getUserAccount()
						+ "注册激活邮件");
				String id = MD5Utils.stringToMD5(us.getId().toString());
				String sendEmailTime = System.currentTimeMillis()/ 1000 + "";
				String path = request.getContextPath();
				//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
				String basePath = SysCacheUtils.getSysConfig().getSysWebsitedomain();
				SysLetterTemplates sysLetterTemplates = SysCacheUtils.getSysLetterTemplates();
				String mailContent = sysLetterTemplates
						.getSysRegisteredMailinfo()
						.replace("#userAccount#", us.getUserAccount())
						.replace(
								"#url#",basePath
										+ "/activation.do?validateId="
										+ id
										+ "&uid="
										+ us.getId()
										+"&send="
										+Base64Utils.encodeStr(sendEmailTime));
				center.setMessageContent("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>"+mailContent+"</body></html>");
				center.setMessageAddress(us.getUserEmail());

				center.setMessageSendIp(this.getIpAddr(request));

				messageCenterService.sendEmail(center, null);*/
				/*model.addAttribute("email", us.getUserEmail());*/
				return "user/registernotice";
			}
			model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
			model.addAttribute(Constants.MESSAGE, errormsg);
		}
		if(accessToken != null){
			return "user/thirdpartyLogin";
		}else{
			return "user/register";
		}
	}
	
	
	/**
	 * 
	* @Title: registerQuick
	* @Description: 快速注册
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping("registerQuick.do")
	public String registerQuick(HttpServletRequest request,
			HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		String registerType = request.getParameter("registerType");
		//注册名即为手机名
		String userAccount = request.getParameter("userPhone");
		String userPhone = request.getParameter("userPhone");
		String accessToken = request.getParameter("accessToken");
		String userPasswordOld =request.getParameter("userPassword");
		DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
		DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
		System.out.println(StringUtils.trimToEmpty(userPasswordOld));
		
		String userPassword  =desEncrpt.decrypt(StringUtils.trimToEmpty(userPasswordOld));
		if (StringUtils.isNotBlank(registerType)) {
			String errormsg = "";
			errormsg = loginValidate(request, errormsg, userAccount,
					userPassword);
			if (StringUtils.isNotBlank(errormsg)) {
				model.addAttribute(Constants.MESSAGE, errormsg);
			}
			User existUser = userService.selectByUserUid(userAccount);
			if (existUser != null) {
				userAccount = userPhone +"_1";
			}
			//验证是否有中文
			Pattern p=Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m=p.matcher(userAccount);
			boolean result=m.find();
			if(result){
				errormsg = "用户名不能为中文,请输入6-16位字符.英文,数字";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isEmpty(errormsg)) {
				User user = new User();
				user.setUserAccount(userAccount);
				user.setUserPhone(userPhone);
				user.setPhoneStatus(2);
				user.setUserPassword(MD5Utils.stringToMD5(aesEncrypt.encrypt(userPassword)));
				user.setTypeId(1);
				Map<String,String> map= SysCacheUtils.getConfigParams();
				try {
					user.setUserAddtime(sdf.parse(sdf.format(new Date())));
					user.setUserAddip(this.getIpAddr(request));
				} catch (Exception e) {
					
				}
		
				boolean flag=userService.saveOrUpdate(user);
				if(flag){
					userCreditService.addUserCredit(CreditType.PHONE, user.getId());
					creditLogService.addCreditLog(CreditType.PHONE, user.getId());
					//自动登录
					String account = userAccount;
					String pwd = desEncrpt.decrypt(StringUtils.trimToEmpty(request.getParameter("userPassword")));
					
					if (!hasCookieValue(request)) {// 没有保存cookie进行登录校验，有直接读取cookie进行登录
						if (isSession(request)) {
							return "redirect:index.do";
						}
					}
					String loginType = request.getParameter("loginType");
					if (StringUtils.isNotBlank(loginType)) {
						String errormsg1 = "";
						// 验证用户名密码是否为空
						errormsg1 = loginValidate(request, errormsg1, account, pwd);
						if (StringUtils.isEmpty(errormsg1)) {
							User user1 = userService.selectByUserUid(account);
							if (user1 != null) {
								if (MD5Utils.stringToMD5(aesEncrypt.encrypt(pwd))
										.equals(user1.getUserPassword())) {
									String flag1 = request.getParameter("flag");
									if (flag1 != null && flag1.equals("1")) {
										Cookie cookie = new Cookie("cookie_user", java.net.URLEncoder.encode(account,Constants.UTF8) + "-" + pwd);
										cookie.setMaxAge(60 * 60 * 24 * 7); // cookie 保存7天
										response.addCookie(cookie);
									} else {
										Cookie cookie = new Cookie("cookie_user", java.net.URLEncoder.encode(account,Constants.UTF8) + "-" + null);
										cookie.setMaxAge(60 * 60 * 24 * 7); // cookie 保存7天
										response.addCookie(cookie);
									}
									if (user1.getUserIslock() == 0) {
										request.getSession().setAttribute(
												Constants.FRONT_USER_SESSION, user1);
										SimpleDateFormat sdf1 = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");
										try {
											user1.setUserLogintime(sdf1.parse(sdf1
													.format(new Date())));
											user1.setUserLoginip(this.getIpAddr(request));
											userService.saveOrUpdate(user1);
										} catch (Exception e) {
											logger.error("修改用户信息信息出错");
											
										}
										// 添加日志
										try {
											OperatorLog log = new OperatorLog();
											log.setLogUserid(user1.getId().toString());
											log.setOperatorIp(this.getIpAddr(request));
											log.setOperatorTitle("前台用户"
													+ user1.getUserAccount() + "登录");
											log.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
											log.setOperatorType(OperatorLog.TYPE_FRONT);
											log.setOperatorReturn("登录成功");
											log.setOperatorStatus(200);
											log.setOperatorParams(user1.getUserAccount());
											operatorLogService.addFrontLog(log);
										} catch (Exception e) {
											logger.error("添加日志失败");
											
										}
										//添加登陆红包
										redService. sendLoginRedRecord(user.getId(),false);
										//添加投资满满额红包
										redService.sendTenderRedRecord(user.getId(), new BigDecimal("5"));
										redService.sendTenderRedRecord(user.getId(), new BigDecimal("10"));
										redService.sendTenderRedRecord(user.getId(), new BigDecimal("100"));
										if(map.get("recommend_status").equals(SysConfigParams.recommend_open)){
											
											User recommendUser = userService.selectByUserUid(userAccount);
											if (recommendUser.getInviteUserid() != null) {
												Recommend recommend = new Recommend();
												recommend
														.setRecommendStatus(Recommend.RECOMMEND_STATUS_NEW);
												recommend.setRecommendUserid(recommendUser
														.getInviteUserid());
												recommend.setUserId(recommendUser.getId());
												if (!recommendUser.getInviteMoney().equals("0")) {
													recommend.setRecommendMoney(BigDecimal.valueOf(Double.parseDouble(recommendUser.getInviteMoney())));
												} else {
													recommend.setRecommendMoney(new BigDecimal(0));
												}
												recommendService.saveRecommend(recommend,BigDecimal.valueOf(Double.parseDouble(recommendUser.getInviteMoney())));
												recommendService.allRecommendUser(recommendUser.getId(),Recommend.initLevel ,new BigDecimal(0),new BigDecimal(0),RecommendReward.StatusStaying,null,null);
											}
										}
										//添加注册奖励
										SysFeesRate fee = SysCacheUtils.getSysFeesRate();
										Integer registerAwardType=fee.getSysRegisteredType();
										if(registerAwardType.equals(SysFeesRate.typeRegister)){
											userAccountService.addRegisterAward(user.getId());
										}
										return "redirect:account/accountIndex.do";
									} else {
										errormsg1 = "账户锁定，请联系系统管理员。";
									}
								} else {
									errormsg1 = "密码输入有误，请重新输入。";
								}
							} else {
								errormsg1 = "用户名不存在，请重新输入。";
							}
						} else {
							OperatorLog log = new OperatorLog();
							log.setLogUserid(StringUtils.trim(account));
							log.setOperatorIp(this.getIpAddr(request));

							log.setOperatorTitle("前台用户" + account + "登录");
							log.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
							log.setOperatorType(OperatorLog.TYPE_FRONT);
							log.setOperatorReturn("登录失败");
							log.setOperatorStatus(300);
							log.setOperatorParams(account);
							operatorLogService.addFrontLog(log);
						}
						model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
						model.addAttribute(Constants.MESSAGE, errormsg1);
					}
					return "index";
					
				}
				// 添加日志
				try {
					OperatorLog log = new OperatorLog();
					log.setCreateTime(sdf.parse(sdf.format(new Date())));
					log.setOperatorIp(this.getIpAddr(request));
					log.setOperatorTitle("前台用户" + userAccount + "注册");
					log.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
					log.setOperatorType(OperatorLog.TYPE_FRONT);
					log.setOperatorReturn("注册成功");
					log.setOperatorStatus(200);
					log.setOperatorParams(userAccount);
					log.setOperatorType(OperatorLog.TYPE_FRONT);
					operatorLogService.addFrontLog(log);
				} catch (Exception e) {
					logger.error("添加日志失败");
				}

				return "index";
			}
			model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
			model.addAttribute(Constants.MESSAGE, errormsg);
		}
		if(accessToken != null){
			return "index";
		}else{
			return "index";
		}
	}
	
	
	
	@RequestMapping("registernotice.do")
	public String registernotice(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "user/registernotice";
	}

	/**
	 * 	检查手机号码是否存在
	 * 
	 * */
	@RequestMapping("checkUserPhone.do")
	public void checkUserPhone(HttpServletRequest request,
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
	
	@RequestMapping("checkUserPhone2.do")
	public void checkUserPhone2(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		 String userPhone = request.getParameter("userPhone");
		 String userAccount = request.getParameter("userAccount");
		 //根据手机号得到用户
		User user=userService.selectByUserUid2(userPhone);
		if(user == null){
			SpringUtils.renderJsonResult(response,JsonResult.ERROR, "该手机号尚未注册过，请重新输入！");
		}else if(!user.getUserAccount().equals(userAccount)){
			SpringUtils.renderJsonResult(response,JsonResult.ERROR, "用户名与手机号不匹配，请重新输入！");
		}else{
			SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "用户名与手机号匹配。");
		}
		
	}
	
	
	/**
	 * 验证手机认证码
	 */
	@RequestMapping("checkSmsCode.do")
	public void checkSmsCode(HttpServletRequest request,
			HttpServletResponse response) {
		
		DesEncrypt desEnc = new DesEncrypt();
		String sendSmsCode = "";
		String inputSmsCode = request.getParameter("smsCode");
		String tel = request.getParameter("tel");
		Cookie[] cookie = request.getCookies();
		for (int i = 0; i < cookie.length; i++) {
			Cookie cook = cookie[i];
			if (cook.getName().equalsIgnoreCase("smsRand")) { // 获取键
				sendSmsCode = cook.getValue().toString(); // 获取值
				//DES解密
				sendSmsCode = desEnc.decrypt(sendSmsCode);
				System.out.println(sendSmsCode);
				break;
			}
		}
		if ("".equals(sendSmsCode)){
			SpringUtils.renderJsonResult(response, "101","验证码过期");
		}else if (sendSmsCode.equals(inputSmsCode)) {
			SpringUtils.renderJsonResult(response, JsonResult.SUCCESS,"校验成功");
		} else {
			SpringUtils.renderJsonResult(response,"302","验证码校验失败"); // 验证码校验失败
		}
		}
	
	
	/**
	 * 生成注册手机认证码
	 */
	@RequestMapping("sendSms.do")
	public void sendSms(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//User user = userService.getById(this.loginFrontUser(request).getId());
		//Integer userId = user.getId();
		String tel = request.getParameter("tel");
		int rand = new Random().nextInt(1000000);
		DesEncrypt desEnc = new DesEncrypt();
		String randEnc = desEnc.encrypt(rand+"");
		Cookie cookie = new Cookie("smsRand",randEnc + "");
		cookie.setMaxAge(5 * 60);
		response.addCookie(cookie);
		request.getSession().setAttribute("phonevalcode", rand + "");
		
		Boolean falg=messageCenterService.getFlagWithPhoneAndTime(new Date(),tel);
		if(falg==false){
			SpringUtils.renderJsonResult(response, "899","短信已发送,请在1分钟后在点击发送");
			return;
		}
		
		//String content = "尊敬的"+SysCacheUtils.getSysConfig().getSysWebsitesignature()+"用户，您好！感谢您进行手机短信认证，您的验证码为："+rand+"，请您在5分钟内输入该验证码完成手机认证。";
		String content="您的验证码是："+rand+"。请不要把验证码泄露给其他人。";
		Date sendTime = new Date();
		MessageCenter center = new MessageCenter();
		center.setMessageAddress(tel);
		center.setMessageSendDatetime(sendTime);
		center.setMessageContent(content);
		center.setMessageSendIp(this.getIpAddr(request));
		//center.setReceiveUserId(userId);
		center.setMessageTitle("手机短信认证");
		center.setNoticeTypeId(MessageCenter.SMS);
		center.setSendUserId(Constants.ADMIN_USER_ID);
	    int result = SmsUtil.sendSms(tel,content);
		OperatorLog log = new OperatorLog();
		 //log.setLogUserid(userId.toString());
		 log.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
		 log.setOperatorIp(this.getIpAddr(request));
		 log.setOperatorType(OperatorLog.TYPE_FRONT);
		 log.setOperatorTitle("找回密码短信手机认证");
		if(result==0){     //模拟成功发送信息
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
	 * 验证手机认证码
	 */
	@RequestMapping("checkSmsCode2.do")
	public void checkSmsCodeW(HttpServletRequest request,
			HttpServletResponse response) {
			DesEncrypt desEnc = new DesEncrypt();
			String sendSmsCode = "";
			String telphone = "";
			String inputSmsCode = request.getParameter("smsCode");
			String tel = request.getParameter("tel");
			if(StringUtils.isBlank(tel)){
				SpringUtils.renderJsonResult(response, "100", "请验证手机号码");
				return;
			}
			Cookie[] cookie = request.getCookies();
			for (int i = 0; i < cookie.length; i++) {
				Cookie cook = cookie[i];
				if (cook.getName().equalsIgnoreCase("smsRand")) { // 获取键
					sendSmsCode = cook.getValue().toString(); // 获取值
					//DES解密
					sendSmsCode = desEnc.decrypt(sendSmsCode);
					System.out.println(sendSmsCode);
				}
				//手机号
				if (cook.getName().equalsIgnoreCase("smsPhone")) { // 获取键
					telphone = cook.getValue().toString(); // 获取值
				}
				if(StringUtils.isNotBlank(sendSmsCode)&&StringUtils.isNotBlank(telphone)){
					break;
				}
			}
			//判断手机号
			User user  = this.userService.getById(this.loginFrontUser(request).getId());
			String userPhone = user.getUserPhone();
			if(!userPhone.equals(telphone)){
				SpringUtils.renderJsonResult(response, "104", "您的手机号和验证码不匹配，请重新发送");
			}else if ("".equals(sendSmsCode)||"".equals(telphone)){
				SpringUtils.renderJsonResult(response, "101", "请重新发送验证码");
			}else if(!tel.equals(telphone)){
				SpringUtils.renderJsonResult(response, "102", "两次手机号码输入不一致");
			}else if (inputSmsCode.equals(sendSmsCode)&&tel.equals(telphone)) {
				SpringUtils.renderJsonResult(response, "200", "手机验证成功");
			} else {
				SpringUtils.renderJsonResult(response, "103", "验证码输入有误");
			}
		}
	
	@RequestMapping("sendeEmailAgain.do")
	public void sendEmailAgain(HttpServletRequest request, Model model,HttpServletResponse response){
		JsonResult json = new JsonResult();
		Boolean flag=false;
		Map<String, String> params = new HashMap<String, String>();
		String userEmail=request.getParameter("email");
		params.put("userEmail", userEmail);
		User us = userService.getUserByName(params);
		MessageCenter center = new MessageCenter();
		center.setSendUserId(Constants.ADMIN_USER_ID);
		center.setReceiveUserId(us.getId());
		center.setMessageTitle("【"+SysCacheUtils.getSysConfig().getSysWebsitename()+"】用户" + us.getUserAccount()
				+ "注册激活邮件");
		String id = MD5Utils.stringToMD5(us.getId().toString());
		String sendEmailTime = System.currentTimeMillis()/ 1000 + "";
		String path = request.getContextPath();
		//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		String basePath = SysCacheUtils.getSysConfig().getSysWebsitedomain();
		SysLetterTemplates sysLetterTemplates = SysCacheUtils.getSysLetterTemplates();
		String mailContent = sysLetterTemplates
				.getSysRegisteredMailinfo()
				.replace("#userAccount#", us.getUserAccount())
				.replace(
						"#url#",basePath
								+ "/activation.do?validateId="
								+ id
								+ "&uid="
								+ us.getId()
								+"&send="
								+Base64Utils.encodeStr(sendEmailTime));
		center.setMessageContent("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>"+mailContent+"</body></html>");
		center.setMessageAddress(us.getUserEmail());

		center.setMessageSendIp(this.getIpAddr(request));
		
		flag=messageCenterService.sendEmail(center, null);
		if(flag){
			json.setCode("100");
			json.setMsg("发送成功,请查收");
		}
		SpringUtils.renderJson(response, json);
	}
	/**
	 * 邮箱激活
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("activation.do")
	public String activation(HttpServletRequest request, Model model)
			throws Exception {
		String uid = StringUtils.defaultIfEmpty(request.getParameter("uid"),"0");
		String validateId = StringUtils.defaultIfEmpty(request.getParameter("validateId"), "0");
		String send = Base64Utils.decodeStr(request.getParameter("send"));
		Long sendTime = Long.valueOf(send);
		User user = userService.getById(Integer.parseInt(uid));
		/*UserAccount userAccount = userAccountService.getByUserId(user.getId());*/
		if (System.currentTimeMillis() - sendTime * 1000 > 1 * 24 * 60 * 60 * 1000) {
			request.setAttribute(Constants.MESSAGE, "激活码已过期。");
			return "user/register";
		}
		if(user.getEmailStatus().equals(User.EMAIL_PASS)){
			request.setAttribute(Constants.MESSAGE, "邮箱已经激活，请勿重复操作。");
			return "redirect:index.do";
		}
		if (!validateId.equals(MD5Utils.stringToMD5(uid))) {
			request.setAttribute(Constants.MESSAGE, "邮箱激活失败。");
			return "redirect:index.do";
			
		}else{
			userCreditService.addUserCredit(CreditType.EMAIL, user.getId());
			creditLogService.addCreditLog(CreditType.EMAIL, user.getId());
			user.setEmailStatus(User.EMAIL_PASS);
			boolean flag=userService.saveOrUpdate(user);
			//资料完整度
			if(flag==true){
				User integralUser = this.userService.getById(user.getId());
				CreditType credit=this.creditTypeService.getCreditType(null, CreditType.EMAIL);
				String integral = integralUser.getUserIntegral();
				if(integral != null){
					Integer integral1 = Integer.parseInt(integral);
					Integer userIntegral = integral1+credit.getCreditScore();
					integralUser.setUserIntegral(userIntegral.toString());
				}else{
					integralUser.setUserIntegral(credit.getCreditScore().toString());
				}
				userService.updateByPrimaryKeySelective(integralUser);
				
				/*//插入抵扣金
				if(user.getRealnameStatus() == 2 && userAccount.getDeductionStatus() == 0){
					Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
					String deductionMoneySwitch = sysConfigParamMap.get("deductionMoney_switch");
					if(deductionMoneySwitch.equals("1")){
						String deductionMoneyAmount = sysConfigParamMap.get("deductionMoney_amount");
						userAccount.setDeductionMoney(new BigDecimal(deductionMoneyAmount));
						userAccount.setDeductionStatus(1);
						boolean addFlag = userAccountService.updateByPrimaryKeySelective(userAccount);
					//发送站内信
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
							this.messageCenterService.send(centerDeduction, addFlag&&user.getEmailStatus()==User.EMAIL_PASS?Notice.EMAIL_YES:Notice.EMAIL_NO);
						
						}
				}
				}*/
				/**
				 * 发送站内信
				 */
				MessageCenter center=new MessageCenter();
				center.setMessageSendDatetime(new Date());
				center.setMessageSendIp(this.getIpAddr(request));
				center.setMessageStatus(MessageCenter.STATUS_UNREAD);
				center.setReceiveUserId(user.getId());
				center.setSendUserId(Constants.ADMIN_USER_ID);
				center.setMessageContent(flag&&user.getEmailStatus()==User.EMAIL_PASS?"用户"+user.getUserAccount()+"，你的邮箱激活成功，添加积分"+credit.getCreditScore().toString()+"分。":"用户"+user.getUserAccount()+"，你的邮箱激活失败，请重新操作。");
				center.setMessageTitle("邮箱激活");
				center.setNoticeTypeId(MessageCenter.MESSAGE);
				this.messageCenterService.send(center, flag&&user.getEmailStatus()==User.EMAIL_PASS?Notice.EMAIL_YES:Notice.EMAIL_NO);
				/**
				 * 记录日志
				 */
				OperatorLog operatorLog = new OperatorLog();
				//operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
				operatorLog.setOperatorTitle("邮箱激活");
				operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
				operatorLog.setOperatorParams(user.toString());
				operatorLog.setOperatorReturn(flag&&user.getEmailStatus()==User.EMAIL_PASS?"用户"+user.getUserAccount()+",你的邮箱激活成功，添加积分"+credit.getCreditScore().toString()+"分。":"用户"+user.getUserAccount()+"，你的邮箱激活失败，请重新操作。");
				operatorLog.setOperatorStatus(flag&&user.getEmailStatus()==User.EMAIL_PASS?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLog.setOperatorIp(this.getIpAddr(request));
				operatorLogService.addAdminLog(operatorLog);
			}
			model.addAttribute("userName", user.getUserAccount());
			request.getSession().setAttribute(Constants.FRONT_USER_SESSION,
					user);
			return "user/registersuc";
		}
	}

	@RequestMapping("toLogin.do")
	public String toLogin(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		Cookie[] cookies = request.getCookies();
		for(int i = 0 ;i< cookies.length;i++){
			Cookie cookie = cookies[i];
			if(cookie.getName().equals("cookie_user")){
				model.addAttribute("flag", 1);
			}
		}
		
		return "user/login";
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("login.do")
	public String login(HttpServletRequest request,
			HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		String account = request.getParameter("userAccount");
		String flag = request.getParameter("flag");
		DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
		DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
		String pwd = desEncrpt.decrypt(StringUtils.trimToEmpty(request.getParameter("password")));
		System.out.println(pwd);
		String destination = (String) request.getSession().getAttribute("destination");
		
		if (!hasCookieValue(request)) {// 没有保存cookie进行登录校验，有直接读取cookie进行登录
			if (isSession(request)) {
				if(destination!=null && !"".equals(destination)){
					request.getSession().setAttribute("destination","");
					return "redirect:"+destination;
				}
				return "redirect:index.do";
			}
		}
		String loginType = request.getParameter("loginType");
		if (StringUtils.isNotBlank(loginType)) {
			String errormsg = "";
			// 验证用户名密码是否为空
			errormsg = loginValidate(request, errormsg, account, pwd);
			if (StringUtils.isEmpty(errormsg)) {
				User user = userService.selectByUserUid(account);
				if (user != null) {
					if (MD5Utils.stringToMD5(aesEncrypt.encrypt(pwd))
							.equals(user.getUserPassword())) {
						if (flag != null && flag.equals("1")) {
						Cookie cookie = new Cookie("cookie_user", java.net.URLEncoder.encode(account,Constants.UTF8) + "-" + account);
							cookie.setMaxAge(60 * 60 * 24 * 7); // cookie 保存7天
							response.addCookie(cookie);
						} else {
							Cookie cookie = new Cookie("cookie_user", java.net.URLEncoder.encode(account,Constants.UTF8) + "-" + null);
							cookie.setMaxAge(0); // 清除缓存
							response.addCookie(cookie);
						}
						if (user.getUserIslock() == 0) {
							request.getSession().setAttribute(
									Constants.FRONT_USER_SESSION, user);
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							try {
								user.setUserLogintime(sdf.parse(sdf
										.format(new Date())));
								user.setUserLoginip(this.getIpAddr(request));
								userService.saveOrUpdate(user);
							} catch (Exception e) {
								logger.error("修改用户信息信息出错");
								
							}
							// 添加日志
							try {
								OperatorLog log = new OperatorLog();
								log.setLogUserid(user.getId().toString());
								log.setOperatorIp(this.getIpAddr(request));
								log.setOperatorTitle("前台用户"
										+ user.getUserAccount() + "登录");
								log.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
								log.setOperatorType(OperatorLog.TYPE_FRONT);
								log.setOperatorReturn("登录成功");
								log.setOperatorStatus(200);
								log.setOperatorParams(user.getUserAccount());
								operatorLogService.addFrontLog(log);
							} catch (Exception e) {
								logger.error("添加日志失败");
							}
							if(destination!=null && !"".equals(destination)){
								request.getSession().setAttribute("destination","");
								return "redirect:"+destination;
							}
							return "redirect:index.do";
						} else {
							errormsg = "账户锁定，请联系系统管理员。";
						}
					} else {
						errormsg = "密码输入有误，请重新输入。";
					}
				} else {
					errormsg = "用户名不存在，请重新输入。";
				}
			} else {
				OperatorLog log = new OperatorLog();
				log.setLogUserid(StringUtils.trim(account));
				log.setOperatorIp(this.getIpAddr(request));

				log.setOperatorTitle("前台用户" + account + "登录");
				log.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
				log.setOperatorType(OperatorLog.TYPE_FRONT);
				log.setOperatorReturn("登录失败");
				log.setOperatorStatus(300);
				log.setOperatorParams(account);
				operatorLogService.addFrontLog(log);
			}
			model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
			model.addAttribute(Constants.MESSAGE, errormsg);
		}
		return "user/login";
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("layout.do")
	public String layout(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		request.getSession().removeAttribute(Constants.FRONT_USER_SESSION);
		return "redirect:index.do";
	}

	@RequestMapping("toResetPassword.do")
	public String toResetPassword(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "user/resetpassword";
	}
	

	@RequestMapping("resetPassw3.do")
	public String resetPassw3(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		String userId = request.getParameter("userId");
		String userPhone = request.getParameter("userPhone");
		User user = userService.getById(Integer.parseInt(userId));
		
		
		if(userPhone.equals(user.getUserPhone())){
			model.addAttribute("userId", user.getId());
			model.addAttribute("userPhone", userPhone);
			
			model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
			return "user/setpassword";
		} else {
			return "redirect:resetPassword.do";
		}
	}
	
	@RequestMapping("changePassword4.do")
	public String changePassword4(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		String setPassword = request.getParameter("setPasswordType");
		if (StringUtils.isNotBlank(setPassword)) {
			String userId = request.getParameter("userId");
			String password = request.getParameter("userPassword");
			String confirmPassword = request.getParameter("passwordr");
			
			if (isNull(password).equals("")) {
				model.addAttribute(Constants.MESSAGE, "新密码不能为空。");
				return "user/setpassword";
			}
			if (isNull(confirmPassword).equals("")) {
				model.addAttribute(Constants.MESSAGE, "请输入确认密码。");
				return "user/setpassword";
			}
			
			if (!MD5Utils.stringToMD5(password).equals(
					MD5Utils.stringToMD5(confirmPassword))) {
				model.addAttribute(Constants.MESSAGE, "密码不一致，请确认。");
				return "user/setpassword";
			}
			DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
			DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
			String userPassword = desEncrpt.decrypt(password);
			User user=null;
			if(userId==null || userId.equals("")){
				model.addAttribute(Constants.MESSAGE, "修改出错,请联系客服或者返回第一步重新修改");
				return "user/setpassword";
			}else{
				 user = userService.selectByPrimaryKey(Integer.parseInt(userId));
			}
			if (user != null &&user.getIsSystem()==2) {
				user.setUserPassword(MD5Utils.stringToMD5(aesEncrypt.encrypt(userPassword)));
				userService.saveOrUpdate(user);
				return "user/setpasswordsuc";
			}else{
				model.addAttribute(Constants.MESSAGE, "修改出错,请联系客服");
				return "user/setpassword";
			}
		}
		return "redirect:toResetPassword.do";
	}


	

	/**
	 * 忘记登陆密码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("resetPassword.do")
	public String resetPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
		String errPage = "user/resetpassword";
		String setPasswordType=request.getParameter("setPasswordType");
		String username = request.getParameter("userAccount");
		User user1=new User();
		if (StringUtils.isEmpty(username)) { 
			model.addAttribute(Constants.MESSAGE, "用户名不能为空");
			return errPage;
		}else if("email".equals("setPasswordType")){
			Map<String, String> param = new HashMap<String, String>();
			param.put("userEmail", username);
			user1 = userService.checkUserByEmail(param);
		}
		
		User user = userService.selectByUserUid(username);
		if (user == null) {
			model.addAttribute(Constants.MESSAGE, "用户名输入不正确");
			return errPage;
		}
		model.addAttribute("user", user);
		model.addAttribute("userId",user.getId());
		model.addAttribute("setPasswordType",setPasswordType);
		return "user/resetpasswordnotice";
		
	}
	
	
	@RequestMapping("toByEmail.do")
	public String toByEmail(HttpServletRequest request,
			HttpServletResponse response, Model model) {
	/*	String userId = request.getParameter("uid");
		User user = userService.getById(Integer.parseInt(userId));
		model.addAttribute("userId", user.getId());*/
		return "user/email";
	}
	
	   /**
		 * 发送邮件
	 * @return 
		 */
	@RequestMapping(value = "reSendEmais.do")
	public void reSendEmail(HttpServletRequest request, HttpServletResponse response,Model model){
		Map<String, String> param = this.getParameters(request);
		String userAccount=request.getParameter("userAccount");
		User user1 =new User();
		user1.setUserAccount(userAccount);
	    user1=userService.checkUserByEmail(param);
		String email = request.getParameter("userEmail").trim();
		if (!isEmail(email)) {
			SpringUtils.renderJsonResult(response, "400", "邮箱格式不正确!");
			return;
		}
		if (user1 != null && email.equals(user1.getUserEmail())) {
			
		try {
		
			if (!email.equals(user1.getUserEmail())) {//不是原来邮箱则更新
				user1.setUserEmail(email);
				userService.updateByPrimaryKeySelective(user1);
			}
			
			SpringUtils.renderJsonResult(response, "200", "操作成功,请登录您的邮箱查看邮件");
			
			//发送邮件
			MessageCenter center = new MessageCenter();
			center.setSendUserId(Constants.ADMIN_USER_ID);
			center.setReceiveUserId(user1.getId());
			center.setMessageTitle("【"+SysCacheUtils.getSysConfig().getSysWebsitename()+"】用户"
					+ user1.getUserAccount() + "找回登录密码邮件");
			String id = MD5Utils.stringToMD5(user1.getId().toString());
			String sendEmailTime = System.currentTimeMillis()/ 1000 + "";
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
			SysLetterTemplates sysLetterTemplates = SysCacheUtils.getSysLetterTemplates();
			String mailContent = sysLetterTemplates
					.getSysPasswordMailinfo()
					.replace("#userAccount#", user1.getUserAccount())
					.replace("#url#",basePath
							+ "/foundPassword.do?send="
							+ sendEmailTime
							+ "&uid="
							+ user1.getId()
							+ "&code="
							+ MD5Utils.stringToMD5(user1
							.getUserAccount()));
			center.setMessageContent("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>"+mailContent+"</body></html>");
			center.setMessageAddress(user1.getUserEmail());
			
			center.setMessageSendIp(this.getIpAddr(request));
			messageCenterService.sendEmail(center, null);
			
		} catch (Exception e) {
			SpringUtils.renderJsonResult(response, "404", "系统异常!");
			e.printStackTrace();
		}
		} else {
			SpringUtils.renderJsonResult(response,"301", "该用户与邮箱不匹配！");
		}
	}
	

	
	
	
	/**
	 * 发送找回密码邮件后处理
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("foundPassword.do")
	public String foundPassword(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		User user = null;
		String uid = StringUtils.defaultIfEmpty(request.getParameter("uid"),
				"0");
		Long sendTime = Long.valueOf(request.getParameter("send"));
		String code = StringUtils.defaultIfEmpty(request.getParameter("code"),
				"");
		user = userService.getById(Integer.parseInt(uid));
		if (user == null) {
			model.addAttribute(Constants.MESSAGE, "用户不存在，请联系客服。");
			return "user/resetpasswordnotice";
		}
		if (code.equals(MD5Utils.stringToMD5(user.getUserAccount()))) {
			if (uid.length() < 1) {
				model.addAttribute(Constants.MESSAGE, "链接无效，请重新找回密码，获取邮件链接。");
				return "user/resetpasswordnotice";
			}
			if (System.currentTimeMillis() - sendTime * 1000 > 1 * 24 * 60 * 60
					* 1000) {
				model.addAttribute(Constants.MESSAGE, "链接有效时间1天，已经失效，请重新找回密码。");
				return "user/resetpasswordnotice";
			}
			model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
			model.addAttribute("userId", uid);
			return "user/setpassword";
		}
		return "user/resetpasswordnotice";
	}

	/**
	 * 重置密码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("changePassword.do")
	public void changePassword(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		String setPassword = request.getParameter("setPasswordType");
		String userAccount = request.getParameter("userAccount");
		if(isNull(setPassword).equals("")){
			SpringUtils.renderJsonResult(response, "108", "非法请求");
		}else if (isNull(userAccount).equals("")) {
			SpringUtils.renderJsonResult(response, "107", "用户名不能为空");
		}else if(StringUtils.isNotBlank(setPassword) && StringUtils.isNotBlank(userAccount)) {
			User user = userService.selectByUserUid(userAccount);
			if(user==null){
				SpringUtils.renderJsonResult(response, "101", "用户为空");
			}else{
				try{
					String uid = request.getParameter("uid");
					if(isNull(uid).equals("") || user.getId() != Integer.parseInt(uid)){
						SpringUtils.renderJsonResult(response, "109", "请确认找回密码的用户");
					}else{
						String password = request.getParameter("userPassword");
						String confirmPassword = request.getParameter("passwordr");
						
						if (isNull(password).equals("")) {
							SpringUtils.renderJsonResult(response, "102", "新密码不能为空");
						}else if (isNull(confirmPassword).equals("")) {
							SpringUtils.renderJsonResult(response, "103", "请输入确认密码");
						}else if (!MD5Utils.stringToMD5(password).equals(
								MD5Utils.stringToMD5(confirmPassword))) {
							SpringUtils.renderJsonResult(response, "104", "密码不一致，请确认");
						}else{
							DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
							DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
							String userPassword = desEncrpt.decrypt(password);
							String md4Password = MD5Utils.stringToMD5(aesEncrypt.encrypt(userPassword));
							User u = userService.getById(Integer.parseInt(uid));
							u.setUserPassword(md4Password);
							boolean b = userService.saveOrUpdate(u);
							if(b){
								SpringUtils.renderJsonResult(response, "200", "密码重置成功");
							}else{
								SpringUtils.renderJsonResult(response, "106", "密码重置失败");
							}
							
						}
					}
				}catch (Exception e) {
					//
					//session失效返回错误页面
					SpringUtils.renderJsonResult(response, "106", "密码重置失败");
				}
			}
		}
	}
	
	/**
	 * 跳转到修改成功页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("resetSuc.do")
	public String resetSuc(HttpServletRequest request,HttpServletResponse response,Model model){
		return "user/setpasswordsuc";
	}

	/**
	 * 判断浏览器中是否有cookie保证用户密码
	 * 
	 * @return
	 */
	private boolean hasCookieValue(HttpServletRequest request) {
		String username = "";
		String password = "";
		User user = new User();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if ("cookie_username".equals(isNull(cookie.getName()))) {
					try {
						username = URLDecoder.decode(cookie.getValue(),Constants.UTF8);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						
					}
				}
				if ("cookie_password".equals(isNull(cookie.getName()))) {
					password = cookie.getValue();
				}
			}
			if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
				return false;
			} else {
				user.setUserAccount(username);
				user.setUserPassword(password);
				return true;
			}
		}
		return false;
	}

	public boolean isSession(HttpServletRequest request) {
		User sessionUser = (User) request.getSession().getAttribute(
				Constants.FRONT_USER_SESSION);
		if (sessionUser == null)
			return false;
		return true;
	}

	/**
	 * 如果str为null，返回“”,否则返回str
	 * 
	 * @param str
	 * @return
	 */
	public String isNull(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	/**
	 * 验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean validateSubmit(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return captchaService.validateResponseForID(
					request.getSession(false).getId(),
					request.getParameter("captcha").toLowerCase());
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 异步验证验证码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkVerificationCode.do")
	public void checkVerificationCode(HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = validateSubmit1(request);
		if (flag == true) {
			SpringUtils.renderJsonResult(response, JsonResult.SUCCESS, "验证成功");
		} else {
			SpringUtils.renderJsonResult(response, "301", "验证失败");
		}
	}

	public boolean validateSubmit1(HttpServletRequest request) {
		try {
			return captchaService.validateResponseForID(
					request.getSession(false).getId(),
					request.getParameter("captcha").toLowerCase());
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 登录验证
	 * 
	 * @param request
	 * @param useraccount
	 * @param password
	 * @return
	 */
	private String loginValidate(HttpServletRequest request, String errormsg,
			String userAccount, String password) {
		/*if (isNull(userAccount).equals("")) {
			errormsg = "用户名不能为空。";
		}*/
		if (isNull(password).equals("")) {
			errormsg = "密码不能为空。";
		}
		if (isNull(password).equals("")) {
			errormsg = "用户名和密码不能为空。";
		}
		return errormsg;
	}

	/**
	 * 检查email是否是邮箱格式，返回true表示是，反之为否
	 * 
	 * @param email
	 * @return
	 */
	public boolean isEmail(String email) {
		email = isNull(email);
		Pattern regex = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	/**
	 * 发放红包
	 * @param request
	 * @param response
	 * @param model
	 */
}
