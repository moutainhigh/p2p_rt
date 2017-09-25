package com.rbao.east.controller.admin;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.AttestationApply;
import com.rbao.east.entity.AttestationType;
import com.rbao.east.entity.CreditLog;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserCredit;
import com.rbao.east.entity.VipUser;
import com.rbao.east.service.AttestationApplyService;
import com.rbao.east.service.AttestationTypeService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.CreditTypeService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.service.VipUserService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.SysCacheUtils;


/**
 * 认证管理
 * */
@Controller
@RequestMapping("attestation/")
public class UserAttestationController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(UserAttestationController.class);
	@Autowired
	private AttestationTypeService attestationTypeService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private AttestationApplyService attestationApplyService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserCreditService userCreditService;//用户积分
	@Autowired
	private CreditLogService creditLogService;//积分日志
	@Autowired
	private CreditTypeService creditTypeService;//积分类型
	@Autowired
	private VipUserService vipUserService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private UserAccountService userAccountService;
	/**
	 * 实名认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"realnameAttestation")
	public String realnameAttestation(HttpServletRequest request,HttpServletResponse response, Model model){
		attestationUtils(request, response, model);
		return "usermanage/userAttestation/realnameAttestation";
	}
	
	/**
	 * 跳转到审核页面
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"toSaveRealnameAttestation")
	public String toUpdateRealnameAttestation(HttpServletResponse response,
			HttpServletRequest request, Model model){
		toAttestationUtils(response, request, model,"realname");
		return "usermanage/userAttestation/toSaveRealnameAttestation";
	}
	/**
	 * 实名认证审核
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveRealnameAttestation")
	public void saveRealnameAttestation(HttpServletRequest request,HttpServletResponse response, Model model,User user){
		boolean flag = false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		User loginUser=this.loginAdminUser(request);
		try{
			if (this.validateCaptcha(request)) {
				if(user.getUserRealname()!=null){
					user.setRealnameStatus(Integer.parseInt(param.get("status")));
					flag=this.userService.updateUserRate(user, CreditType.REALNAME,user.getId(), loginUser.getId(), Integer.parseInt(param.get("status")));
					//注册奖励
					this.userService.registerAwardAttestation(user.getId());
					//资料完整度
					if(flag==true && Integer.parseInt(param.get("status")) == User.REALNAME_PASS){
						User integralUser = this.userService.getById(user.getId());
						CreditType credit=this.creditTypeService.getCreditType(null, CreditType.REALNAME);
						String integral = integralUser.getUserIntegral();
						if(integral != null){
							Integer integral1 = Integer.parseInt(integral);
							Integer userIntegral = integral1+credit.getCreditScore();
							integralUser.setUserIntegral(userIntegral.toString());
						}else{
							integralUser.setUserIntegral(credit.getCreditScore().toString());
						}
						userService.updateByPrimaryKeySelective(integralUser);
					}
					/**
					 * 发送站内信
					 */
					MessageCenter center=new MessageCenter();
					center.setMessageSendDatetime(new Date());
					center.setMessageSendIp(this.getIpAddr(request));
					center.setMessageStatus(MessageCenter.STATUS_UNREAD);
					center.setReceiveUserId(user.getId());
					center.setSendUserId(Constants.ADMIN_USER_ID);
					center.setMessageContent(flag&&user.getRealnameStatus()==User.REALNAME_PASS?"用户"+user.getUserAccount()+",你的实名认证通过，添加积分，"+param.get("creditScore"):"用户"+user.getUserAccount()+"，你的实名认证不通过。审核意见："+param.get("messageContent"));
					center.setMessageTitle("实名认证审核");
					this.messageCenterService.send(center, flag&&user.getRealnameStatus()==User.REALNAME_PASS?Notice.REALNAME_YES:Notice.REALNAME_NO);
					
					/**
					 * 记录日志
					 */
					OperatorLog operatorLog = new OperatorLog();
					operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
					operatorLog.setOperatorTitle("实名认证审核");
					operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
					operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
					operatorLog.setOperatorParams(user.toString());
					operatorLog.setOperatorReturn(flag&&user.getRealnameStatus()==User.REALNAME_PASS?"用户"+user.getUserAccount()+",实名认证成功，添加积分"+param.get("creditScore"):"用户"+user.getUserAccount()+"，你的实名认证失败");
					operatorLog.setOperatorStatus(flag&&user.getRealnameStatus()==User.REALNAME_PASS?200:300);
					operatorLog.setLinkUrl(RequestUtils.getIpAddr());
					operatorLog.setOperatorIp(this.getIpAddr(request));
					operatorLogService.addAdminLog(operatorLog);
				}
			}else{
				boolean result=false;
				SpringUtils.renderDwzResult(response, result, result ? "" : "验证码错误！",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
			}
		}catch(Exception e){
			flag=false;
			
			logger.error("实名认证出错");
		}
		if(flag){
			SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
		}
	}
	
	
	/**
	 * 手机认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"phoneAttestation")
	public String phoneAttestation(HttpServletRequest request,HttpServletResponse response, Model model){
		attestationUtils(request, response, model);
		return "usermanage/userAttestation/phoneAttestation";
	}
	
	/**
	 * 跳转到审核页面
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"toSavePhoneAttestation")
	public String toUpdatePhoneAttestation(HttpServletResponse response,
			HttpServletRequest request, Model model){
		toAttestationUtils(response, request, model,"phone");
		return "usermanage/userAttestation/toSavePhoneAttestation";
	}
	/**
	 * 手机认证审核
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"savePhoneAttestation")
	public void savePhoneAttestation(HttpServletRequest request,HttpServletResponse response, Model model,User user){
		boolean flag = false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		User loginUser=this.loginAdminUser(request);
		try{   
				if (this.validateCaptcha(request)) {
					if(user.getUserPhone()!=null){
						user.setPhoneStatus(Integer.parseInt(param.get("status")));
						flag=this.userService.updateUser(user, CreditType.PHONE,user.getId(), loginUser.getId(), Integer.parseInt(param.get("status")));
						//注册奖励
						this.userService.registerAwardAttestation(user.getId());
						//资料完整度
						if(flag==true && Integer.parseInt(param.get("status"))==User.PHONE_PASS){
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
						}
						
						/**
						 * 发送站内信
						 */
						MessageCenter center=new MessageCenter();
						center.setMessageSendDatetime(new Date());
						center.setMessageSendIp(this.getIpAddr(request));
						center.setMessageStatus(MessageCenter.STATUS_UNREAD);
						center.setReceiveUserId(user.getId());
						center.setSendUserId(Constants.ADMIN_USER_ID);
						center.setMessageContent(flag&&user.getPhoneStatus()==User.PHONE_PASS?"用户"+user.getUserAccount()+"，你的手机认证通过，添加积分"+param.get("creditScore"):"用户"+user.getUserAccount()+"，你的手机认证不通过。审核意见："+param.get("messageContent"));
						center.setMessageTitle("手机认证审核");
						this.messageCenterService.send(center, flag&&user.getPhoneStatus()==User.PHONE_PASS?Notice.PHONE_YES:Notice.PHONE_NO);
						/**
						 * 记录日志
						 */
						OperatorLog operatorLog = new OperatorLog();
						operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
						operatorLog.setOperatorTitle("手机认证审核");
						operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
						operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
						operatorLog.setOperatorParams(user.toString());
						operatorLog.setOperatorReturn(flag&&user.getPhoneStatus()==User.PHONE_PASS?"用户"+user.getUserAccount()+",你的手机认证成功，添加积分"+param.get("creditScore"):"用户"+user.getUserAccount()+"，你的手机认证失败");
						operatorLog.setOperatorStatus(flag&&user.getPhoneStatus()==User.PHONE_PASS?200:300);
						operatorLog.setLinkUrl(RequestUtils.getIpAddr());
						operatorLog.setOperatorIp(this.getIpAddr(request));
						operatorLogService.addAdminLog(operatorLog);
					}
				}else{
					boolean result=false;
					SpringUtils.renderDwzResult(response, result, result ? "" : "验证码错误！",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
				}
		}catch(Exception e){
			flag=false;
			
			logger.error("手机认证出错");
		}
		if(flag){
			SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
		}
	}
	
	/**
	 * 邮箱认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"emailAttestation")
	public String emailAttestation(HttpServletRequest request,HttpServletResponse response, Model model){
		attestationUtils(request, response, model);
		return "usermanage/userAttestation/emailAttestation";
	}
	
	/**
	 * 跳转到审核页面
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"toSaveEmailAttestation")
	public String toSaveEmailAttestation(HttpServletResponse response,
			HttpServletRequest request, Model model){
		toAttestationUtils(response, request, model,"email");
		return "usermanage/userAttestation/toSaveEmailAttestation";
	}
	/**
	 * 邮箱认证审核
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveEmailAttestation")
	public void saveEmailAttestation(HttpServletRequest request,HttpServletResponse response, Model model,User user){
		boolean flag = false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		User loginUser=this.loginAdminUser(request);
		try{
			if (this.validateCaptcha(request)) {
				if(user.getUserEmail()!=null){
						user.setEmailStatus(Integer.parseInt(param.get("status")));
						flag=this.userService.updateUser(user, CreditType.EMAIL,user.getId(), loginUser.getId(), Integer.parseInt(param.get("status")));
						//注册奖励
						this.userService.registerAwardAttestation(user.getId());
						//资料完整度
						if(flag==true && Integer.parseInt(param.get("status")) == User.EMAIL_PASS){
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
						}
						
						/**
						 * 发送站内信
						 */
						MessageCenter center=new MessageCenter();
						center.setMessageSendDatetime(new Date());
						center.setMessageSendIp(this.getIpAddr(request));
						center.setMessageStatus(MessageCenter.STATUS_UNREAD);
						center.setReceiveUserId(user.getId());
						center.setSendUserId(Constants.ADMIN_USER_ID);
						center.setMessageContent(flag&&user.getEmailStatus()==User.EMAIL_PASS?"用户"+user.getUserAccount()+"，你的邮箱认证通过，添加积分"+param.get("creditScore"):"用户"+user.getUserAccount()+"，你的邮箱认证不通过。审核意见："+param.get("messageContent"));
						center.setMessageTitle("邮箱认证审核");
						center.setNoticeTypeId(MessageCenter.MESSAGE);
						this.messageCenterService.send(center, flag&&user.getEmailStatus()==User.EMAIL_PASS?Notice.EMAIL_YES:Notice.EMAIL_NO);
						/**
						 * 记录日志
						 */
						OperatorLog operatorLog = new OperatorLog();
						operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
						operatorLog.setOperatorTitle("邮箱认证审核");
						operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
						operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
						operatorLog.setOperatorParams(user.toString());
						operatorLog.setOperatorReturn(flag&&user.getEmailStatus()==User.EMAIL_PASS?"用户"+user.getUserAccount()+",你的邮箱认证成功，添加积分"+param.get("creditScore"):"用户"+user.getUserAccount()+"，你的邮箱认证失败");
						operatorLog.setOperatorStatus(flag&&user.getEmailStatus()==User.EMAIL_PASS?200:300);
						operatorLog.setLinkUrl(RequestUtils.getIpAddr());
						operatorLog.setOperatorIp(this.getIpAddr(request));
						operatorLogService.addAdminLog(operatorLog);
				}
			}else{
				boolean result=false;
				SpringUtils.renderDwzResult(response, result, result ? "" : "验证码错误！",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
			}
		}catch(Exception e){
			flag=false;
			
			logger.error("邮箱认证出错");
		}
		if(flag){
			SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
		}
	}
	
	/**
	 * 视频认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"videoAttestation")
	public String videoAttestation(HttpServletRequest request,HttpServletResponse response, Model model){
		attestationUtils(request, response, model);
		return "usermanage/userAttestation/videoAttestation";
	}
	
	/**
	 * 跳转到审核页面
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"toSaveVideoAttestation")
	public String toSaveVideoAttestation(HttpServletResponse response,
			HttpServletRequest request, Model model){
		toAttestationUtils(response, request, model,"video");
		return "usermanage/userAttestation/toSaveVideoAttestation";
	}
	/**
	 * 视频认证审核
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveVideoAttestation")
	public void saveVideoAttestation(HttpServletRequest request,HttpServletResponse response, Model model,User user){
		boolean flag = false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		User loginUser=this.loginAdminUser(request);
		try{
			if (this.validateCaptcha(request)) {
				user.setVideoStatus(Integer.parseInt(param.get("status")));
				flag=this.userService.updateUser(user, CreditType.VIDEO,user.getId(), loginUser.getId(), Integer.parseInt(param.get("status")));
				
				//资料完整度
				if(flag==true && Integer.parseInt(param.get("status")) == User.VIDEO_PASS){
					User integralUser = this.userService.getById(user.getId());
					CreditType credit=this.creditTypeService.getCreditType(null, CreditType.VIDEO);
					String integral = integralUser.getUserIntegral();
					if(integral != null){
						Integer integral1 = Integer.parseInt(integral);
						Integer userIntegral = integral1+credit.getCreditScore();
						integralUser.setUserIntegral(userIntegral.toString());
					}else{
						integralUser.setUserIntegral(credit.getCreditScore().toString());
					}
					userService.updateByPrimaryKeySelective(integralUser);
				}
				
				/**
				 * 发送站内信
				 */
				MessageCenter center=new MessageCenter();
				center.setMessageSendDatetime(new Date());
				center.setMessageSendIp(this.getIpAddr(request));
				center.setMessageStatus(MessageCenter.STATUS_UNREAD);
				center.setReceiveUserId(user.getId());
				center.setSendUserId(Constants.ADMIN_USER_ID);
				center.setMessageContent(flag&&user.getVideoStatus()==User.VIDEO_PASS?"用户"+user.getUserAccount()+"，你的视频认证通过，添加积分"+param.get("creditScore"):"用户"+user.getUserAccount()+"，你的视频认证不通过。审核意见："+param.get("messageContent"));
				center.setMessageTitle("视频认证审核");
				this.messageCenterService.send(center, flag?Notice.VIDEO_YES:Notice.VIDEO_NO);
				/**
				 * 记录日志
				 */
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
				operatorLog.setOperatorTitle("视频认证审核");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
				operatorLog.setOperatorParams(user.toString());
				operatorLog.setOperatorReturn(flag&&user.getVideoStatus()==User.VIDEO_PASS?"用户"+user.getUserAccount()+",你的视频认证成功，添加积分"+param.get("creditScore"):"用户"+user.getUserAccount()+"，你的视频认证失败");
				operatorLog.setOperatorStatus(flag&&user.getVideoStatus()==User.VIDEO_PASS?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLog.setOperatorIp(this.getIpAddr(request));
				operatorLogService.addAdminLog(operatorLog);
			}else{
				boolean result=false;
				SpringUtils.renderDwzResult(response, result, result ? "" : "验证码错误！",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
			}
		}catch(Exception e){
			flag=false;
			
			logger.error("视频认证出错");
		}
		if(flag){
			SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
		}
	}
	
	/**
	 * 现场认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"sceneAttestation")
	public String sceneAttestation(HttpServletRequest request,HttpServletResponse response, Model model){
		attestationUtils(request, response, model);
		return "usermanage/userAttestation/sceneAttestation";
	}
	
	/**
	 * 跳转到审核页面
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"toSaveSceneAttestation")
	public String toSaveSceneAttestation(HttpServletResponse response,
			HttpServletRequest request, Model model){
		toAttestationUtils(response, request, model,"scene");
		return "usermanage/userAttestation/toSaveSceneAttestation";
	}
	/**
	 * 现场认证审核
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveSceneAttestation")
	public void saveSceneAttestation(HttpServletRequest request,HttpServletResponse response, Model model,User user){
		boolean flag = false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		try{
			User loginUser=this.loginAdminUser(request);
			if (this.validateCaptcha(request)) {
				user.setSceneStatus(Integer.parseInt(param.get("status")));
				flag=this.userService.updateUser(user, CreditType.SCENE,user.getId(), loginUser.getId(), Integer.parseInt(param.get("status")));
				
				//资料完整度
				if(flag==true && Integer.parseInt(param.get("status")) == User.SCENE_PASS){
					User integralUser = this.userService.getById(user.getId());
					CreditType credit=this.creditTypeService.getCreditType(null, CreditType.SCENE);
					String integral = integralUser.getUserIntegral();
					if(integral != null){
						Integer integral1 = Integer.parseInt(integral);
						Integer userIntegral = integral1+credit.getCreditScore();
						integralUser.setUserIntegral(userIntegral.toString());
					}else{
						integralUser.setUserIntegral(credit.getCreditScore().toString());
					}
					userService.updateByPrimaryKeySelective(integralUser);
				}
				
				/**
				 * 发送站内信
				 */
				MessageCenter center=new MessageCenter();
				center.setMessageSendDatetime(new Date());
				center.setMessageStatus(MessageCenter.STATUS_UNREAD);
				center.setReceiveUserId(user.getId());
				center.setSendUserId(loginUser.getId());
				center.setMessageContent(flag&&user.getSceneStatus()==User.SCENE_PASS?"用户"+user.getUserAccount()+"，你的现场认证通过，添加积分"+param.get("creditScore"):"用户"+user.getUserAccount()+"，你的现场认证不通过。审核意见："+param.get("messageContent"));
				center.setMessageTitle("【"+SysCacheUtils.getSysConfig().getSysWebsitesignature()+"】用户"+user.getUserAccount()+"现场认证审核");
				center.setMessageSendIp(this.getIpAddr(request));
				this.messageCenterService.send(center, flag?Notice.SCENE_YES:Notice.SCENE_NO);
				
				/**
				 * 记录日志
				 */
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
				operatorLog.setOperatorTitle("现场认证审核");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
				operatorLog.setOperatorParams(user.toString());
				operatorLog.setOperatorReturn(flag&&user.getSceneStatus()==User.SCENE_PASS?"用户"+user.getUserAccount()+",你的现场认证成功，添加积分"+param.get("creditScore"):"用户"+user.getUserAccount()+"，你的现场认证失败");
				operatorLog.setOperatorStatus(flag&&user.getSceneStatus()==User.SCENE_PASS?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLog.setOperatorIp(this.getIpAddr(request));
				operatorLogService.addAdminLog(operatorLog);
			}else{
				boolean result=false;
				SpringUtils.renderDwzResult(response, result, result ? "" : "验证码错误！",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
			}
		}catch(Exception e){
			flag=false;
			
			logger.error("现场认证出错");
		}
		if(flag){
			SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
		}
	}
	
	/**
	 * Vip认证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"vipAttestation")
	public String vipAttestation(HttpServletRequest request,HttpServletResponse response, Model model){
		Map<String, String> param=getParameters(request);//超级管理员
		Map<String, String> paramCustomer=getParameters(request);//其他角色
		paramCustomer.put("vipCustomer", this.loginAdminUser(request).getId().toString());
		param.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService.getRightGroupList(param);
		model.addAttribute("righSubtList", righSubtList);
		PageModel list=this.vipUserService.VipUserPageList(param,paramCustomer);
		model.addAttribute("pm", list);
		model.addAttribute("right_id", param.get("right_id"));
		model.addAttribute("searchParams", param);// 用于搜索框保留值
		return "usermanage/userAttestation/vipAttestation";
	}
	
	/**
	 * 跳转到审核页面
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(Constants.PRE_PATH_VIEW+"toSaveVipAttestation")
	public String toSaveVipAttestation(HttpServletResponse response,
			HttpServletRequest request, Model model){
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		Map<String, String> params = this.getParameters(request);
		VipUser user = this.vipUserService.selectVipUserById(Integer.parseInt(param.get("supportID")));
		//查询积分类型
		CreditType creditType=this.creditTypeService.getCreditType(null, "vip");
		model.addAttribute("creditType", creditType);
		model.addAttribute("vipUser", user);
		//获取费率
		SysFeesRate feeRate=SysCacheUtils.getSysFeesRate();
		model.addAttribute("feeRate", feeRate);
		return "usermanage/userAttestation/toSaveVipAttestation";
	}
	/**
	 * Vip认证审核
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveVipAttestation")
	public void saveVipAttestation(HttpServletRequest request,HttpServletResponse response, Model model,VipUser vipUser){
		boolean flag = false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		User loginUser=this.loginAdminUser(request);
		try{
			if (this.validateCaptcha(request)) {
					vipUser.setVipStatus(Integer.parseInt(param.get("status")));
					vipUser.setVipOperatorId(loginUser.getId());
					vipUser.setVipUpdateIp(this.getIpAddr(request));
					flag=this.vipUserService.saveOrUpdateVipUser(vipUser, CreditType.VIP, loginUser.getId(), Integer.parseInt(param.get("status")));
					
					//资料完整度
					if(flag==true && Integer.parseInt(param.get("status")) == VipUser.VIP_USER){
						User integralUser = this.userService.getById(vipUser.getUserId());
						CreditType credit=this.creditTypeService.getCreditType(null, CreditType.VIP);
						String integral = integralUser.getUserIntegral();
						if(integral != null){
							Integer integral1 = Integer.parseInt(integral);
							Integer userIntegral = integral1+credit.getCreditScore();
							integralUser.setUserIntegral(userIntegral.toString());
						}else{
							integralUser.setUserIntegral(credit.getCreditScore().toString());
						}
						userService.updateByPrimaryKeySelective(integralUser);
					}
					
					/**
					 * 发送站内信
					 */
					MessageCenter center=new MessageCenter();
					center.setMessageSendDatetime(new Date());
					center.setMessageSendIp(this.getIpAddr(request));
					center.setMessageStatus(MessageCenter.STATUS_UNREAD);
					center.setReceiveUserId(vipUser.getUserId());
					center.setSendUserId(Constants.ADMIN_USER_ID);
					center.setMessageContent(flag&&vipUser.getVipStatus()==VipUser.VIP_USER?"你的VIP申请通过，添加积分"+param.get("creditScore"):"你的VIP申请失败。审核意见："+vipUser.getVipRemarks());
					center.setMessageTitle("vip申请审核");
					this.messageCenterService.send(center, flag?Notice.VIP_YES:Notice.VIP_NO);
					/**
					 * 记录日志
					 */
					OperatorLog operatorLog = new OperatorLog();
					operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
					operatorLog.setOperatorTitle("vip申请审核");
					operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
					operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
					operatorLog.setOperatorParams(vipUser.toString());
					operatorLog.setOperatorReturn(flag&&vipUser.getVipStatus()==VipUser.VIP_USER?"你的VIP申请通过，添加积分"+param.get("creditScore"):"你的VIP申请失败");
					operatorLog.setOperatorStatus(flag&&vipUser.getVipStatus()==VipUser.VIP_USER?200:300);
					operatorLog.setLinkUrl(RequestUtils.getIpAddr());
					operatorLog.setOperatorIp(this.getIpAddr(request));
					operatorLogService.addAdminLog(operatorLog);
			}else{
				boolean result=false;
				SpringUtils.renderDwzResult(response, result, result ? "" : "验证码错误！",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
			}
		}catch(Exception e){
			flag=false;
			SpringUtils.renderDwzResult(response, false,e.getLocalizedMessage(),"", param.get("right_id").toString());
			
			logger.error("vip认证出错");
		}
		SpringUtils.renderDwzResult(response, true, flag ? "审核通过" : "审核未通过",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
	}
	
	/**
	 * 认证查询公共方法
	 * @param request
	 * @param response
	 * @param model
	 */
	public void attestationUtils(HttpServletRequest request,HttpServletResponse response, Model model ){
		Map<String, String> param=getParameters(request);
		param.put("userId", this.loginAdminUser(request).getId().toString());
		param.put("isSystem", Integer.toString(User.SYSTEMN));// 不是管理员
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService.getRightGroupList(param);
		model.addAttribute("righSubtList", righSubtList);
		PageModel list=userService.getPagedList(param);
		model.addAttribute("pm", list);
		model.addAttribute("right_id", param.get("right_id"));
		model.addAttribute("searchParams", param);// 用于搜索框保留值
	}
	/**
	 * 跳转到审核页面的公共方法
	 * @param response
	 * @param request
	 * @param model
	 * @param credit 积分类型
	 */
	public void toAttestationUtils(HttpServletResponse response,
			HttpServletRequest request, Model model,String creditCode){
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		Map<String, String> params = this.getParameters(request);
		params.put("id", param.get("supportID"));
		params.put("isSystem", Integer.toString(User.SYSTEMN));
		User user = this.userService.getByIdParam(params);
		//查询积分类型
		CreditType creditType=this.creditTypeService.getCreditType(null, creditCode);
		model.addAttribute("creditType", creditType);
		model.addAttribute("user", user);
	}
	
	/**
	 * 证件类型管理
	 * @param request
	 * @param response
	 * @param model
	 * @param attestationType
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getListAttestationType")
	public String getListAttestationType(HttpServletRequest request, HttpServletResponse response,
			Model model,AttestationType attestationType) {
		Map<String,String> paramsMap = getParameters(request);
		Map<String, Object> result=new HashMap<String, Object>();
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		result.put("righSubtList", righSubtList);
		result.put("right_id", paramsMap.get("right_id"));
		PageModel pageModel = attestationTypeService.getList(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",attestationType);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		this.setParameters(result, request);
		return "usermanage/userAttestation/listAttestationType";
	}
	/**
	 * 删除
	 * @param request
	 * @param response
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "deleteAttestationType")
	public void deleteCreditType(HttpServletRequest request, HttpServletResponse response
			) {
		Map<String,String> paramsMap = getParameters(request);
		boolean succ = false;
		try {
			User loginUser=this.loginAdminUser(request);
			succ = attestationTypeService.deleteById(Integer.parseInt(paramsMap.get("supportID")));
			/**
			 * 记录日志
			 */
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
			operatorLog.setOperatorTitle("删除证件类型");
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			operatorLog.setOperatorParams(paramsMap.get("supportID"));
			operatorLog.setOperatorReturn(succ?"删除证件类型成功":"删除证件类型失败");
			operatorLog.setOperatorStatus(succ?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addAdminLog(operatorLog);
		} catch (Exception e) {
			
			logger.error("删除出错："+paramsMap.get("supportID"),e);
		}
		
		SpringUtils.renderDwzResult(response, succ, succ?"删除成功":"删除失败");
	}
	/**
	 * 添加跳转
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "get1AttestationType")
	public String get1CreditType(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		request.setAttribute("right_id", paramsMap.get("right_id"));
		return "usermanage/userAttestation/updateAttestationType";
	}
	/**
	 * 修改查询数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAttestationType")
	public String getCreditType(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		//request.setAttribute("right_id", request.getAttribute("right_id"));
		AttestationType attestationType = attestationTypeService.getById(Integer.parseInt(paramsMap.get("supportID")));
		request.setAttribute("right_id", request.getAttribute("right_id"));
		model.addAttribute("attestationType", attestationType);
		this.setParameters(paramsMap, request);
		return "usermanage/userAttestation/updateAttestationType";
	}
	/**
	 * 添加or修改
	 * @param request
	 * @param response
	 * @param attestationType
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveAttestationType")
	public void saveAttestationType(HttpServletRequest request, HttpServletResponse response,
			AttestationType attestationType) {
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		User loginUser = loginAdminUser(request);
		try {
			OperatorLog operatorLog = new OperatorLog();
			if(attestationType.getId()==null){
				String ip = this.getIpAddr(request); 
				attestationType.setAttestationIp(ip);
				attestationType.setAttestationDatetime(new Date());
				operatorLog.setOperatorTitle("添加证件类型");
				operatorLog.setOperatorReturn(succ?"添加证件类型"+attestationType.getAttestationName()+"成功":"添加证件类型"+attestationType.getAttestationName()+"失败");
				succ = attestationTypeService.add(attestationType);
			}else{
				operatorLog.setOperatorTitle("修改证件类型");
				operatorLog.setOperatorReturn(succ?"修改证件类型"+attestationType.getAttestationName()+"成功":"修改证件类型"+attestationType.getAttestationName()+"失败");
				succ = attestationTypeService.save(attestationType);
			}
			/**
			 * 记录日志
			 */
			
			operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			operatorLog.setOperatorParams(attestationType.toString());
			operatorLog.setOperatorStatus(succ?200:300);
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addAdminLog(operatorLog);
		} catch (Exception e) {
			
			/*logger.error("添加用户出错："+creditType.getName(),e);*/
		}
		SpringUtils.renderDwzResult(response, succ, succ?"成功":"失败"
			,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
	}
	
	
	/**
	 * 添加用户证件跳转添加查询所有证件类型
	 * @param request
	 * @param response
	 * @param model
	 * @param attestationType
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "addAttestationApply")
	public String addAttestationApply(HttpServletRequest request, HttpServletResponse response,
			Model model,AttestationType attestationType) {
		List<AttestationType> attestationList = attestationTypeService.listAttestationType(attestationType);
		if(attestationList!=null){
			model.addAttribute("attestationList", attestationList);
		}
		/*AttestationApply attestationApply = new AttestationApply();
		attestationApply.setUserId(userId)
		List<AttestationApply> attestationApplyList = attestationApplyService.listAttestationApply(attestationApply);*/
		Map<String,String> paramsMap = getParameters(request);
		request.setAttribute("right_id", paramsMap.get("right_id"));
		return "usermanage/userAttestation/addAttestationApply";
	}
	/*@RequestMapping(Constants.PRE_PATH_VIEW + "attType")
	public void attType(HttpServletRequest request, HttpServletResponse response,
			Model model,AttestationType attestationType) {
		Map<String, String> paramsMap = getParameters(request);
		AttestationApply attestationApply = new AttestationApply();
		attestationApply.setUserId(Integer.parseInt(paramsMap.get("param")));
		List<AttestationApply> attestationApplyList = attestationApplyService.listAttestationApply(attestationApply);
		SpringUtils.renderJson(response, attestationApplyList);
	}*/
	/**
	 * 添加用户证件
	 * @param request
	 * @param response
	 * @param model
	 * @param attestationApply
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "addUserAttestationApply")
	public void addAttestationApply(HttpServletRequest request, HttpServletResponse response,
			Model model,AttestationApply attestationApply) {
		boolean bool = false;
		Map<String,String> paramsMap = getParameters(request);
		try {
			AttestationType attType = attestationTypeService.getById(Integer.parseInt(paramsMap.get("attestationTypeid")));
			Map<String,String> params = new HashMap<String, String>();
			params.put("userAccount", request.getParameter("userAccount"));
			User user=userService.getUserByName(params);
			if(user==null){
				throw new RuntimeException("失败,该用户不存在请重新添加！"); 
			}
			attestationApply.setUserId(user.getId());
			List<AttestationApply> attlist = attestationApplyService.listAttestationApply(attestationApply);
			if(attlist.size()>0){
				if(attlist.get(0).getAttestationStatus()==0 || attlist.get(0).getAttestationStatus()==1){
					throw new RuntimeException("失败,证件类型已经添加，请重新选择！"); 
				}
			}
			User loginUser = loginAdminUser(request);
			attestationApply.setUserId(user.getId());
			attestationApply.setAttestationName(attType.getAttestationName());
			attestationApply.setAttestationApplyDatetime(new Date());
			String ip =this.getIpAddr(request);
			
			attestationApply.setAttestationApplyIp(ip);
			attestationApply.setAttestationScore(attType.getAttestationScore());
			attestationApply.setAttestationImg(request.getParameter("url"));
			bool = attestationApplyService.add(attestationApply);
				
				/**
				 * 记录日志
				 */
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
				operatorLog.setOperatorTitle("添加用户证件");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
				operatorLog.setOperatorParams(attestationApply.toString());
				operatorLog.setOperatorReturn(bool?"用户"+user.getUserAccount()+"添加"+attType.getAttestationName()+"证件成功":"用户"+user.getUserAccount()+"审核"+attType.getAttestationName()+"证件失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLog.setOperatorIp(this.getIpAddr(request));
				operatorLogService.addAdminLog(operatorLog);
			SpringUtils.renderDwzResult(response, bool, bool?"成功":"失败"
				,DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id").toString()); 
		} catch (Exception e) {
			SpringUtils.renderDwzResult(response, false, e.getMessage()
				,DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id").toString()); 
		}
	}
	/**
	 * 用户证件列表
	 * @param request
	 * @param response
	 * @param model
	 * @param attestationType
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getListAttestationApply")
	public String getListAttestationApply(HttpServletRequest request, HttpServletResponse response,
			Model model,AttestationApply attestationApply) {
		Map<String,String> paramsMap = getParameters(request);
		/*if(attestationApply.getAttestationStatus()==null){
			paramsMap.put("attestationStatus", "0");
		}*/
		Map<String, Object> result=new HashMap<String, Object>();
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		Map<String,String> params = getParameters(request);
		List<AttestationType> att = attestationTypeService.listAttestationType(new AttestationType());
		if(att.size()>0){
			model.addAttribute("attList", att);
		}
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		result.put("righSubtList", righSubtList);
		result.put("right_id", paramsMap.get("right_id"));
		PageModel pageModel = attestationApplyService.getList(params);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",params);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		this.setParameters(result, request);
		return "usermanage/userAttestation/listAttestationApply";
	}
	/**
	 * 查询信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAttestationApply")
	public String getAttestationApply(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		//request.setAttribute("right_id", request.getAttribute("right_id"));
		AttestationApply attestationApply = attestationApplyService.getById(Integer.parseInt(paramsMap.get("supportID")));
		request.setAttribute("right_id", request.getAttribute("right_id"));
		model.addAttribute("attestationApply", attestationApply);
		this.setParameters(paramsMap, request);
		return "usermanage/userAttestation/updateAttestationApply";
	}
	
	/**
	 * 审核用户证件
	 * @param request
	 * @param response
	 * @param attestationType
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveAttestationApply")
	public void saveAttestationApply(HttpServletRequest request, HttpServletResponse response,
			AttestationApply attestationApply) {
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		User loginUser = loginAdminUser(request);
		try {
			if(attestationApply.getAttestationStatus().equals(AttestationApply.ATTESTATIAONAPPLY_PASS)){//审核通过
				UserCredit userCredit = new UserCredit();
				List<UserCredit> userCre = userCreditService.seletByUserId(attestationApply.getUserId());
				if(userCre.size()>0){
					userCredit.setId(userCre.get(0).getId());
					userCredit.setCreditValue(userCre.get(0).getCreditValue()+attestationApply.getAttestationScore());
					userCredit.setCreditUpdateTime(new Date());
					userCredit.setCreditUpdateIp(this.getIpAddr(request));
					userCreditService.save(userCredit);
				}else{
					userCredit.setCreditValue(attestationApply.getAttestationScore());
					userCreditService.add(userCredit);
				}
				CreditLog creditLog = new CreditLog();
				creditLog.setUserId(attestationApply.getUserId());
				creditLog.setCreditTypeId(6);
				creditLog.setCreditOperateType(1);
				creditLog.setCreditOperateValue(attestationApply.getAttestationScore());
				creditLog.setCreditOperateDatetime(new Date());
				String ip = this.getIpAddr(request);
				creditLog.setCreditOperateIp(ip);
				creditLog.setCreditOperater(loginUser.getId());
				creditLog.setCreditOperateRemark(attestationApply.getAttestationName()+"证件认证，积分"+attestationApply.getAttestationScore());
				creditLogService.add(creditLog);
			}
			attestationApply.setAttestationVerifyDatetime(new Date());
			attestationApply.setAttestationVerifyUserid(loginUser.getId());
			succ = attestationApplyService.save(attestationApply);
			
			//资料完整度
			if(succ==true && attestationApply.getAttestationStatus()==1){
				User integralUser = this.userService.getById(attestationApply.getUserId());
				String integral = integralUser.getUserIntegral();
				if(integral != null){
					Integer integral1 = Integer.parseInt(integral);
					Integer userIntegral = integral1+attestationApply.getAttestationScore();
					integralUser.setUserIntegral(userIntegral.toString());
				}else{
					integralUser.setUserIntegral(attestationApply.getAttestationScore().toString());
				}
				userService.updateByPrimaryKeySelective(integralUser);
			}
			
			/**
			 * 发送站内信
			 */
			MessageCenter center=new MessageCenter();
			center.setMessageSendDatetime(new Date());
			center.setMessageSendIp(this.getIpAddr(request));
			center.setReceiveUserId(attestationApply.getUserId());
			center.setSendUserId(Constants.ADMIN_USER_ID);
			center.setMessageContent(succ&&attestationApply.getAttestationStatus()==AttestationApply.ATTESTATIAONAPPLY_PASS?"你的"+attestationApply.getAttestationName()+"审核通过，添加积分"+attestationApply.getAttestationScore():"你的用户证件审核不通过,审核意见："+attestationApply.getAttestationRemarks());
			center.setMessageTitle("用户证件审核");
			this.messageCenterService.send(center, succ?Notice.ATTESTATIONAPPLY_YES:Notice.ATTESTATIONAPPLY_NO);
			/**
			 * 记录日志
			 */
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id"+loginUser.getId().toString()+",用户名"+loginUser.getUserAccount());
			operatorLog.setOperatorTitle("用户证件审核");
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			operatorLog.setOperatorParams(attestationApply.toString());
			operatorLog.setOperatorReturn(succ&&attestationApply.getAttestationStatus()==AttestationApply.ATTESTATIAONAPPLY_PASS?"用户证件"+attestationApply.getAttestationName()+"审核通过，添加积分"+attestationApply.getAttestationScore():"用户证件"+attestationApply.getAttestationName()+"审核不通过,审核意见："+attestationApply.getAttestationRemarks());
			operatorLog.setOperatorStatus(succ?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addAdminLog(operatorLog);
		} catch (Exception e) {
			
			/*logger.error("添加用户出错："+creditType.getName(),e);*/
		}
		SpringUtils.renderDwzResult(response, succ, succ?"成功":"失败"
			,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
	}
	
}
