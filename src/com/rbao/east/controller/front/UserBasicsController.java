package com.rbao.east.controller.front;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.EducationMessage;
import com.rbao.east.entity.FinanceMessage;
import com.rbao.east.entity.Friends;
import com.rbao.east.entity.FriendsRequest;
import com.rbao.east.entity.HouseMessage;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.NoticeType;
import com.rbao.east.entity.NoticeUser;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.OtherMessage;
import com.rbao.east.entity.PersonalMessage;
import com.rbao.east.entity.PrivateBusinessMessage;
import com.rbao.east.entity.RelationMessage;
import com.rbao.east.entity.SpouseMessage;
import com.rbao.east.entity.UnitMessage;
import com.rbao.east.entity.User;
import com.rbao.east.service.EducationMessageService;
import com.rbao.east.service.FinanceMessageService;
import com.rbao.east.service.FriendsRequestService;
import com.rbao.east.service.FriendsService;
import com.rbao.east.service.HouseMessageService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.NoticeService;
import com.rbao.east.service.NoticeTypeService;
import com.rbao.east.service.NoticeUserService;
import com.rbao.east.service.OtherMessageService;
import com.rbao.east.service.PersonalMessageService;
import com.rbao.east.service.PrivateBusinessMessageService;
import com.rbao.east.service.RecommendRewardService;
import com.rbao.east.service.RecommendService;
import com.rbao.east.service.RelationMessageService;
import com.rbao.east.service.SpouseMessageService;
import com.rbao.east.service.UnitMessageService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.Base64Utils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;
import com.rbao.east.utils.SysCacheUtils;
/**
 * 用户基本信息操作
 * */
@Controller
@RequestMapping("basics/")
public class UserBasicsController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(UserBasicsController.class);

	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageCaptchaService captchaService;
	@Autowired
	private HouseMessageService houseMessageService;
	@Autowired
	private EducationMessageService educationMessageService;
	@Autowired
	private OtherMessageService otherMessageService;
	@Autowired
	private PersonalMessageService personalMessageService;
	@Autowired
	private PrivateBusinessMessageService privateBusinessMessageService;
	@Autowired
	private FinanceMessageService financeMessageService;
	@Autowired
	private RelationMessageService relationMessageService;
	@Autowired
	private SpouseMessageService spouseMessageService;
	@Autowired
	private UnitMessageService unitMessageService;
	@Autowired
	private FriendsRequestService friendsRequestService;
	@Autowired
	private FriendsService friendsService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeUserService noticeUserService;
	@Autowired
	private NoticeTypeService noticeTypeService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private RecommendRewardService  recommendRewardService;

	/**
	 * 站内信
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("userMessage.do")
	public String userMessage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> params = getParameters(request);
		@SuppressWarnings("unchecked")
		List<MessageCenter> list =  messageCenterService.getUserMessagePage(params).getList();
		model.addAttribute("message",list);
		return "userinfo/basics/userMessage";
	}

	/**
	 * 收件箱
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("userReceiveMessagePage.do")
	public void userReceiveMessagePage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		Integer userId = this.loginFrontUser(request).getId();
		if (userId != null) {
			paramsMap.put("receiveUserId", String.valueOf(userId));
			PageModel pageModel = messageCenterService
					.getUserMessagePage(paramsMap);
			model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
			model.addAttribute("rel", request.getParameter("rel"));
			SpringUtils.renderJson(response, pageModel);
		}
	}
	/**
	 * 已读信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("userReceiveedMessagePage.do")
	public void userReceiveedMessagePage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("messageStatus", "1");
		Integer userId = this.loginFrontUser(request).getId();
		if (userId != null) {
			paramsMap.put("receiveUserId", String.valueOf(userId));
			PageModel pageModel = messageCenterService
					.getUserMessagePage(paramsMap);
			model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
			model.addAttribute("rel", request.getParameter("rel"));
			SpringUtils.renderJson(response, pageModel);
		}
	}
	/**
	 * 未读信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("userReceiveingMessagePage.do")
	public void userReceiveingMessagePage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("messageStatus", "0");
		Integer userId = this.loginFrontUser(request).getId();
		if (userId != null) {
			paramsMap.put("receiveUserId", String.valueOf(userId));
			PageModel pageModel = messageCenterService
					.getUserMessagePage(paramsMap);
			model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
			model.addAttribute("rel", request.getParameter("rel"));
			SpringUtils.renderJson(response, pageModel);
		}
	}

	/**
	 * 已发送
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("userSendMessagePage.do")
	public void userSendMessagePage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		if (paramsMap == null) {
			paramsMap = new HashMap<String, String>();
		}
		Integer userId = this.loginFrontUser(request).getId();
		if (userId != null) {
			paramsMap.put("sendUserId", userId.toString());
			PageModel pageModel = messageCenterService
					.getUserMessagePage(paramsMap);
			model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
			model.addAttribute("rel", request.getParameter("rel"));
			SpringUtils.renderJson(response, pageModel);
		}
	}

	/**
	 * 发信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("sendMessage.do")
	public void userSendMessage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (validateSubmit(request, response)) {
			Map<String, String> paramsMap = getParameters(request);
			MessageCenter messageCenter = new MessageCenter();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Integer sendId = this.loginFrontUser(request).getId();
			User receiveUser = userService.selectByUserUid(paramsMap
					.get("userAccount"));
			if (receiveUser != null) {
				messageCenter.setSendUserId(sendId);
				messageCenter.setReceiveUserId(receiveUser.getId());
				messageCenter
						.setMessageContent(paramsMap.get("messageContent"));
				messageCenter.setMessageTitle(paramsMap.get("messageTitle"));
				messageCenter.setNoticeTypeId(1);
				try {
					messageCenter.setMessageSendDatetime(sdf.parse(sdf
							.format(new Date())));
					messageCenter.setMessageSendIp(this.getIpAddr(request));
					messageCenterService.sendMessage(messageCenter, null);
					SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "发送成功");
				} catch (Exception e) {
					logger.info("发送站内信失败。");
					
					SpringUtils.renderJsonResult(response,"301", "发送失败");
				}
			} else {
				SpringUtils.renderJsonResult(response,"301", "发送失败");
			}
		}
	}

	/**
	 * 验证用户是否存在
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkUserAccount.do")
	public void checkUserAccount(HttpServletRequest request,
			HttpServletResponse response) {
		String userAccount1 = request.getParameter("friendsUserAccount");
		String userAccount2 = request.getParameter("userAccount");
		Map<String, String> param = new HashMap<String, String>();
		if (userAccount1 != null) {
			param.put("userAccount", userAccount1);
		}
		if (userAccount2 != null) {
			param.put("userAccount", userAccount2);
		}
		// 根据用户名称查询用户是否存在
		User Account = this.userService.selectUserByParam(param);
		if (Account == null) {
			SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "成功");
		} else {
			SpringUtils.renderJsonResult(response,"301", "已经存在");
		}
	}

	/**
	 * 删除消息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("deleteMessage.do")
	public void deleteMessage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		String messageId = paramsMap.get("messageId");
		if (!messageId.equals("")) {
			if (messageCenterService.deleteMsgCenter(Integer
					.parseInt(messageId))) {
				SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "删除成功");
			} else {
				SpringUtils.renderJsonResult(response,"301", "删除失败");
			}
		} else {
			SpringUtils.renderJsonResult(response,"301", "删除失败");
		}

	}

	/**
	 * 根据Id查询查看
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("getMessageById.do")
	public String getMessageById(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String messageId = request.getParameter("messageId");
		if (!messageId.equals("")) {
			MessageCenter messageCenter = messageCenterService
					.getMsgById(Integer.parseInt(messageId));
			model.addAttribute("message", messageCenter);
			messageCenter.setMessageStatus(MessageCenter.STATUS_READED);
			messageCenterService.updateMsgCenter(messageCenter);
			return "userinfo/basics/messageDetail";
		} else {
			return "userinfo/basics/userMessage";
		}
	}

	/**
	 * 好友管理
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("userFriend.do")
	public String userFriend(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Integer userId = this.loginFrontUser(request).getId();
		if (userId != null) {
			request.setAttribute("signUserId",Base64Utils.encodeStr(userId.toString()));
			request.setAttribute("signUserIdNew", userId.toString());
			String path = request.getContextPath();
			String basePath =SysCacheUtils.getSysConfig().getSysWebsitedomain()+"/register?u=";
			request.setAttribute("recommendPth",basePath );
		}
		return "userinfo/basics/userFriend";
	}
	//好友奖励汇总
		@RequestMapping("friendSumReward.do")
		public void friendSumReward(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			Map<String, String> paramsMap = getParameters(request);
			if (paramsMap == null) {
				paramsMap = new HashMap<String, String>();
			}
			Integer userId = this.loginFrontUser(request).getId();
			if (userId != null) {
				paramsMap.put("recommendUserid", String.valueOf(userId));
				PageModel pageModel = recommendRewardService.getRewardPage(paramsMap);
				model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
				model.addAttribute("rel", request.getParameter("rel"));
				SpringUtils.renderJson(response, pageModel);
			}
		}
	
	/**
	 * 添加好友
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("addFriend.do")
	public void addFriend(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		String friendsUserAccount = paramsMap.get("friendsUserAccount");
		String friendsContent = paramsMap.get("friendsContent");
		User friendsUser = userService.selectByUserUid(friendsUserAccount);
		if (friendsUser != null) {
			FriendsRequest friendsRequest = new FriendsRequest();
			friendsRequest.setUserId(this.loginFrontUser(request).getId());
			friendsRequest.setFriendsUserid(friendsUser.getId());
			friendsRequest.setFriendsContent(friendsContent);
			try {
				friendsRequest.setFriendsAddip(this.getIpAddr(request));
				if (friendsRequestService.saveFriendsRequest(friendsRequest)) {
					SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "添加好友成功");
				} else {
					SpringUtils.renderJsonResult(response,"301", "添加好友失败");
				}
			} catch (Exception e) {
				logger.info("添加好友请求失败");
				
				SpringUtils.renderJsonResult(response,"301", "添加好友失败");
			}
		} else {
			SpringUtils.renderJsonResult(response,"301", "添加好友失败");
		}
	}

	/**
	 * 下限用户
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("inviteFriend.do")
	public void inviteFriend(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		if (paramsMap == null) {
			paramsMap = new HashMap<String, String>();
		}
		Integer userId = this.loginFrontUser(request).getId();
		if (userId != null) {
			paramsMap.put("inviteUserid", userId.toString());
			PageModel pageModel = userService.getUserByInvitePage(paramsMap);
			model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
			model.addAttribute("rel", request.getParameter("rel"));
			SpringUtils.renderJson(response, pageModel);
		}
	}

	/**
	 * 好友请求
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("friendRequestPage.do")
	public void friendRequestPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		if (paramsMap == null) {
			paramsMap = new HashMap<String, String>();
		}
		Integer userId = this.loginFrontUser(request).getId();
		if (userId != null) {
			paramsMap.put("friendsUserid", String.valueOf(userId));
			PageModel pageModel = friendsRequestService
					.getFriendsRequestList(paramsMap);
			model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
			model.addAttribute("rel", request.getParameter("rel"));
			SpringUtils.renderJson(response, pageModel);
		}
	}

	/**
	 * 处理好友请求
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("checkFriendsRequest.do")
	public void checkFriendsRequest(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		String requestId = paramsMap.get("requestId");
		String status = paramsMap.get("requestStatus");
		if (!requestId.equals("") && !status.equals("")) {
			FriendsRequest friendsRequest = friendsRequestService
					.getFriendsRequestById(Integer.parseInt(requestId));
			friendsRequest.setFriendsStatus(Integer.parseInt(status));
			if (friendsRequestService.saveFriendsRequest(friendsRequest)) {
				SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "处理好友请求成功");
			} else {
				SpringUtils.renderJsonResult(response,"301", "处理好友请求失败");
			}
		} else {
			SpringUtils.renderJsonResult(response,"301", "处理好友请求失败");
		}
	}

	/**
	 * 我的好友
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("myFriendsPage.do")
	public void myFriendsPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		if (paramsMap == null) {
			paramsMap = new HashMap<String, String>();
		}
		Integer userId = this.loginFrontUser(request).getId();
		if (userId != null) {
			paramsMap.put("userId", String.valueOf(userId));
			paramsMap.put("friendsType", String.valueOf(Friends.TYPE_FRIEND));
			PageModel pageModel = friendsService.getFriendsList(paramsMap);
			model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
			model.addAttribute("rel", request.getParameter("rel"));
			SpringUtils.renderJson(response, pageModel);
		}
	}

	/**
	 * 解除关系
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("friendEnd.do")
	public void friendEnd(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		String friendId = paramsMap.get("friendId");
		if (!friendId.equals("")) {
			if (friendsService.deleteFriend(Integer.parseInt(friendId))) {
				SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "解除关系成功");
			} else {
				SpringUtils.renderJsonResult(response,"301", "解除关系失败");
			}
		} else {
			SpringUtils.renderJsonResult(response,"301", "解除关系失败");
		}

	}

	/**
	 * 黑名单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("blacklistPage.do")
	public void blacklistPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		if (paramsMap == null) {
			paramsMap = new HashMap<String, String>();
		}
		Integer userId = this.loginFrontUser(request).getId();
		if (userId != null) {
			paramsMap.put("userId", String.valueOf(userId));
			paramsMap
					.put("friendsType", String.valueOf(Friends.TYPE_BLACKLIST));
			PageModel pageModel = friendsService.getFriendsList(paramsMap);
			model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
			model.addAttribute("rel", request.getParameter("rel"));
			SpringUtils.renderJson(response, pageModel);
		}
	}

	@RequestMapping("friendPushMoney.do")
	public void friendPushMoney(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		if (paramsMap == null) {
			paramsMap = new HashMap<String, String>();
		}
		Integer userId = this.loginFrontUser(request).getId();
		if (userId != null) {
			paramsMap.put("recommendUserid", String.valueOf(userId));
			PageModel pageModel = recommendService
					.getRecommendByRecommendUser(paramsMap);
			model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
			model.addAttribute("rel", request.getParameter("rel"));
			SpringUtils.renderJson(response, pageModel);
		}
	}

	/**
	 * 提醒设置
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("userNotice.do")
	public String userNotice(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		Integer userId = this.loginFrontUser(request).getId();
		List noticeUsers = noticeUserService.getNoticeUsersByUserId(userId);
		List noticeList = noticeService.getNotices(paramsMap);
		List<NoticeType> noticeTypes = noticeTypeService.getAll();
		model.addAttribute("noticeUsers", noticeUsers);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("noticeTypes", noticeTypes);
		return "userinfo/basics/userNotice";
	}

	/**
	 * 保存用户提醒设置
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("setNoitce.do")
	public void setNoitce(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		Integer userId = this.loginFrontUser(request).getId();
		Iterator<String> keys = paramsMap.keySet().iterator();
		Map<String, NoticeUser> map = new HashMap<String, NoticeUser>();
		while (keys.hasNext()) {
			String key = keys.next();
			String[] checked = key.split("_");
			if (checked.length != 2) {
				continue;
			}
			if (checked[0].equals("cookie")) {
				continue;
			}
			NoticeUser noticeUser = null;
			if (map.containsKey(checked[1])) {
				noticeUser = map.get(checked[1]);
			} else {
				noticeUser = new NoticeUser();
				noticeUser.setNoticeId(Short.valueOf(checked[1]));
			}
			if (checked[0].equals("messagge")) {
				noticeUser.setMessage(NoticeUser.NOTICE_SEND_YES);
			} else if (checked[0].equals("email")) {
				noticeUser.setEmail(NoticeUser.NOTICE_SEND_YES);
			} else if (checked[0].equals("phone")) {
				noticeUser.setPhone(NoticeUser.NOTICE_SEND_YES);
			}
			noticeUser.setUserId(Short.parseShort(userId.toString()));
			map.put(checked[1], noticeUser);
		}
		noticeUserService.saveNoticeUser(map, userId);
		SpringUtils.renderJsonResult(response,JsonResult.SUCCESS, "提醒设置成功");
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
	 * 头像上传
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("avatar.do")
	public String avatar(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		request.setAttribute("httpUrlPath", request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath());
		return "userinfo/basics/avatar";
	}

	/**
	 * 头像上传
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "avatar.do", params = "a=uploadavatar")
	public @ResponseBody
	String uploadavatarUserPic(
			@RequestParam(value = "Filedata", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String filePathDir = Constants.FILEPATH + "/"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String fileRealPathDir = request.getSession().getServletContext()
				.getRealPath(filePathDir); // 获取本地存储路径
		File fileSaveFile = new File(fileRealPathDir);
		if (!fileSaveFile.exists())
			fileSaveFile.mkdirs();
		OutputStream outputStream = null;
		String str = "";
		if (!file.isEmpty()) {
			str = "/" + filePathDir + "/" + UUID.randomUUID().toString() + "."
					+ file.getOriginalFilename().split("\\.")[1];
//			File files = new File(request.getSession().getServletContext()
//					.getRealPath("/")
//					+ str); // 新建一个文件
			File files = new File(Constants.FILEPATH_REAL
					+ str); // 新建一个文件
			try {
				outputStream = new FileOutputStream(files);
				outputStream.write(file.getBytes());
				outputStream.flush();
			} catch (Exception e) {
				
			} finally {
				try {
					outputStream.close();
				} catch (IOException ie) {
					
				}
			}
			PrintWriter out = response.getWriter();
			out.print(request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()
					+ str);
			out.flush();
			out.close();

		}
		return null;
	}

	/**
	 * 头像裁剪
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "avatar.do", params = "a=rectavatar")
	public @ResponseBody
	String rectavatarUserPic(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		User user = this.loginFrontUser(request);
		String avatar1 = request.getParameter("avatar1");// 大
		String avatar2 = request.getParameter("avatar2");// 中
		String avatar3 = request.getParameter("avatar3");// 小
//		String imgfilepath = request
//				.getSession()
//				.getServletContext()
//				.getRealPath(
//						Constants.FILEPATH
//								+ "/"
//								+ new SimpleDateFormat("yyyy-MM-dd")
//										.format(new Date()) + "/");
		String imgfilepath = Constants.FILEPATH_REAL + Constants.FILEPATH
								+ "/"
								+ new SimpleDateFormat("yyyy-MM-dd")
										.format(new Date()) + "/";
		File fileSaveFile = new File(imgfilepath);
		if (!fileSaveFile.exists())
			fileSaveFile.mkdirs();
		String imagepath1 = "";
		String imagepath2 = "";
		String imagepath3 = "";
		imagepath1 = imgfilepath + "/" + user.getId() + "_big.jpg";
		imagepath2 = imgfilepath + "/" + user.getId() + "_middle.jpg";
		imagepath3 = imgfilepath + "/" + user.getId() + "_small.jpg";
		boolean a1 = saveFile(imagepath1, getFlashDataDecode(avatar1));
		boolean a2 = saveFile(imagepath2, getFlashDataDecode(avatar2));
		boolean a3 = saveFile(imagepath3, getFlashDataDecode(avatar3));
		PrintWriter out = response.getWriter();

		if (a1 && a2 && a3) {
			out.print("<?xml version=\"1.0\" ?><root><face success=\"0\"/></root>");
		} else {

			out.print("<?xml version=\"1.0\" ?><root><face success=\"1\"/></root>");
		}
		out.flush();
		out.close();
		String url = "/" + Constants.FILEPATH + "/"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/"
				+ user.getId() + "_big.jpg";
		/*System.out.println(url);
		user.setAvatarImg(url);
		boolean bool = userService.saveOrUpdate(user);*/
		User u=new User();
		u.setAvatarImg(url);
		u.setId(user.getId());
		user.setAvatarImg(u.getAvatarImg());
		userService.updateByPrimaryKeySelective(u);
		return null;
	}

	private byte[] getFlashDataDecode(String src) {
		char[] s = src.toCharArray();
		int len = s.length;
		byte[] r = new byte[len / 2];
		for (int i = 0; i < len; i = i + 2) {
			int k1 = s[i] - 48;
			k1 -= k1 > 9 ? 7 : 0;
			int k2 = s[i + 1] - 48;
			k2 -= k2 > 9 ? 7 : 0;
			r[i / 2] = (byte) (k1 << 4 | k2);
		}
		return r;
	}

	public boolean saveFile(String path, byte[] b) {
		try {
			FileOutputStream fs = new FileOutputStream(path);
			fs.write(b, 0, b.length);
			fs.close();
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * 个人资料填写
	 */
	@RequestMapping("personalData.do")
	public String personalData(HttpServletRequest request,HttpServletResponse response, Model model) {
		User user = userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user", user);
		PersonalMessage personal = personalMessageService.getByUserId(user
				.getId());
		model.addAttribute("personal", personal);
		model.addAttribute("registerProtocol", SysCacheUtils.getSysConfig().getSysWebsitedomain());
		
		RelationMessage relationMessage=this.relationMessageService.getByUserId(user.getId());
		model.addAttribute("relationMessage", relationMessage);
		
		
		
		return "userinfo/userdata/personalData";
	}  
	
	/**
	 * 添加收货地址
	 * @param request
	 * @param response
	 * @param message
	 */
	@RequestMapping("saveRelationMessage.do")
	public void saveRelationMessage(HttpServletRequest request,HttpServletResponse response,RelationMessage message){
		try {
			
			User user=this.loginFrontUser(request);
			RelationMessage rm=this.relationMessageService.getByUserId(user.getId());
			boolean flag=true;
			OperatorLog log=new OperatorLog();
			if(rm==null){
				message.setUserId(user.getId());
				flag=this.relationMessageService.add(message);
	
				log.setOperatorTitle("添加收货方式地址");	
			}else{
				message.setId(rm.getId());
				flag=this.relationMessageService.save(message);
				
				log.setOperatorTitle("修改收货地址");
			}
			
			log.setLogUserid(StringUtil.toString(user.getId()));
			log.setOperatorType(OperatorLog.TYPE_FRONT);
			log.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			log.setOperatorParams(message.toString());
			log.setOperatorStatus(flag ? 200:400);
			log.setLinkUrl(RequestUtils.getIpAddr());
			log.setOperatorReturn(flag ? "操作成功" : "操作失败");
			operatorLogService.add(log);
			
			SpringUtils.renderJsonResult(response, flag ? "200" :" 400", flag ? "操作成功!" : "操作失败!");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作失败");
		}
		
		
		
	}
	
	@RequestMapping("delRelationMessage.do")
	public void delRelationMessage(HttpServletRequest request,HttpServletResponse response,RelationMessage message){	
		try {
			User user=this.loginFrontUser(request);
			boolean flag=this.relationMessageService.deleteById(message.getId());
			
			OperatorLog log=new OperatorLog();
			log.setLogUserid(StringUtil.toString(user.getId()));
			log.setOperatorType(OperatorLog.TYPE_FRONT);
			log.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			log.setOperatorParams(message.toString());
			log.setOperatorStatus(flag ? 200:400);
			log.setLinkUrl(RequestUtils.getIpAddr());
			log.setOperatorReturn(flag ? "操作成功" : "操作失败");
			operatorLogService.add(log);
			SpringUtils.renderJsonResult(response, flag ? "200" :" 400", flag ? "操作成功!" : "操作失败!");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作失败");
		}
		
		
	}
	
	/**
	 * 房产资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("buliding.do")
	public String buliding(HttpServletRequest request,HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		HouseMessage house = houseMessageService.getByUserId(user.getId());
		model.addAttribute("house", house);
		return "userinfo/userdata/buliding";
	}
	/**
	 * 单位资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("unit.do")
	public String unit(HttpServletRequest request,HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		UnitMessage unit = unitMessageService.getByUserId(user.getId());
		model.addAttribute("unit", unit);
		return "userinfo/userdata/unit";
	}
	
	/**
	 * 私营业主
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("privatebusiness.do")
	public String privatebusiness(HttpServletRequest request,HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		PrivateBusinessMessage privateBusiness = privateBusinessMessageService
				.getByUserId(user.getId());
		model.addAttribute("privatebusiness", privateBusiness);
		return "userinfo/userdata/privatebusiness";
	}
	
	/**
	 * 财务状况
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("finance.do")
	public String finance(HttpServletRequest request,HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		FinanceMessage finance = financeMessageService
				.getByUserId(user.getId());
		model.addAttribute("finance", finance);
		return "userinfo/userdata/finance";
	}
	
	/**
	 * 联系方式
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("relation.do")
	public String relation(HttpServletRequest request,HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		RelationMessage relationMessage = relationMessageService
				.getByUserId(user.getId());
		model.addAttribute("relation", relationMessage);
		return "userinfo/userdata/relation";
	}
	
	/**
	 * 配偶资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("spouse.do")
	public String spouse(HttpServletRequest request,HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		SpouseMessage spouse = spouseMessageService.getByUserId(user.getId());
		model.addAttribute("spouse", spouse);
		return "userinfo/userdata/spouse";
	}
	
	/**
	 * 教育背景
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("education.do")
	public String education(HttpServletRequest request,HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		EducationMessage education = educationMessageService.getByUserId(user
				.getId());
		model.addAttribute("education", education);
		return "userinfo/userdata/education";
	}
	
	/**
	 * 其他信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("other.do")
	public String other(HttpServletRequest request,HttpServletResponse response, Model model){
		User user = userService.getById(this.loginFrontUser(request).getId());
		OtherMessage other = otherMessageService.getByUserId(user.getId());
		model.addAttribute("other", other);
		return "userinfo/userdata/other";
	}

	/**
	 * 房产信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param houseMessage
	 */
	@RequestMapping("savaHouse.do")
	public void savaHouse(HttpServletRequest request,
			HttpServletResponse response, Model model, HouseMessage houseMessage) {
		try{
			Map<String,String> map=this.getParameters(request);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			houseMessage.setYear(sdf.parse(map.get("yearString")));
			OperatorLog operatorLog=new OperatorLog();
			houseMessage.setUserId(this.loginFrontUser(request).getId());
			boolean bool = false;
			JsonResult json = new JsonResult();
			HouseMessage house=this.houseMessageService.getByUserId(this.loginFrontUser(request).getId());
			if (house != null) {
				bool = houseMessageService.save(houseMessage);
				operatorLog.setOperatorTitle("添加房产信息");
				operatorLog.setOperatorReturn(bool?"添加房产信息成功":"添加房产信息失败");
			} else {
				bool = houseMessageService.add(houseMessage);
				operatorLog.setOperatorTitle("修改房产信息");
				operatorLog.setOperatorReturn(bool?"修改房产成功":"修改房产失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("用户名："+this.loginFrontUser(request).getUserAccount()+"用户Id："+this.loginFrontUser(request).getId());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			operatorLog.setOperatorParams(houseMessage.toString());
			operatorLog.setOperatorStatus(bool?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("100");
			} else {
				json.setCode("101");
			}
			SpringUtils.renderJson(response, json);
		}catch(Exception e){
			
			logger.error("添加或修改房产信息失败！");
		}
	}

	/**
	 * 其他
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param otherMessage
	 */
	@RequestMapping("savaOther.do")
	public void savaOther(HttpServletRequest request,
			HttpServletResponse response, Model model, OtherMessage otherMessage) {
		try{
			OperatorLog operatorLog=new OperatorLog();
			otherMessage.setUserId(this.loginFrontUser(request).getId());
			boolean bool = false;
			JsonResult json = new JsonResult();
			OtherMessage other=this.otherMessageService.getByUserId(this.loginFrontUser(request).getId());
			if (other!= null) {
				bool = otherMessageService.save(otherMessage);
				operatorLog.setOperatorTitle("添加其他信息");
				operatorLog.setOperatorReturn(bool?"添加其他信息成功":"添加其他信息失败");
			} else {
				bool = otherMessageService.add(otherMessage);
				operatorLog.setOperatorTitle("修改其他信息");
				operatorLog.setOperatorReturn(bool?"修改其他信息成功":"修改其他信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("用户名："+this.loginFrontUser(request).getUserAccount()+"用户Id："+this.loginFrontUser(request).getId());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			operatorLog.setOperatorParams(otherMessage.toString());
			operatorLog.setOperatorStatus(bool?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("100");
			} else {
				json.setCode("101");
			}
			SpringUtils.renderJson(response, json);
		}catch(Exception e){
			
			logger.error("添加或修改其他信息失败！");
		}
		
	}

	/**
	 * 单位信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param unitMessage
	 * @throws ParseException
	 */
	@RequestMapping("savaUnit.do")
	public void savaOther(HttpServletRequest request,
			HttpServletResponse response, Model model, UnitMessage unitMessage)
			throws ParseException {
		try{
			OperatorLog operatorLog=new OperatorLog();
			Map<String, String> paramsMap = getParameters(request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			unitMessage.setServeTime(sdf.parse(paramsMap.get("yearString")));
			unitMessage.setToServeTime(sdf.parse(paramsMap.get("yearString1")));
			unitMessage.setUserId(this.loginFrontUser(request).getId());
			boolean bool = false;
			JsonResult json = new JsonResult();
			UnitMessage unit=this.unitMessageService.getByUserId(this.loginFrontUser(request).getId());
			if (unit!= null) {
				bool = unitMessageService.save(unitMessage);
				operatorLog.setOperatorTitle("添加单位信息");
				operatorLog.setOperatorReturn(bool?"添加单位信息成功":"添加单位信息失败");
			} else {
				bool = unitMessageService.add(unitMessage);
				operatorLog.setOperatorTitle("修改单位信息");
				operatorLog.setOperatorReturn(bool?"修改单位信息成功":"修改单位信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("用户名："+this.loginFrontUser(request).getUserAccount()+"用户Id："+this.loginFrontUser(request).getId());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			operatorLog.setOperatorParams(unitMessage.toString());
			operatorLog.setOperatorStatus(bool?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("100");
			} else {
				json.setCode("101");
			}
			SpringUtils.renderJson(response, json);
		}catch (Exception e) {
			
			logger.error("添加或修改单位信息失败");
		}
		
	}

	/**
	 * 财务信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param financeMessage
	 */
	@RequestMapping("savaFinance.do")
	public void savaFinance(HttpServletRequest request,
			HttpServletResponse response, Model model,
			FinanceMessage financeMessage) {
		try{
			OperatorLog operatorLog=new OperatorLog();
			financeMessage.setUserId(this.loginFrontUser(request).getId());
			boolean bool = false;
			JsonResult json = new JsonResult();
			FinanceMessage finance=this.financeMessageService.getByUserId(this.loginFrontUser(request).getId());
			if (finance != null) {
				bool = financeMessageService.save(financeMessage);
				operatorLog.setOperatorTitle("添加财务信息");
				operatorLog.setOperatorReturn(bool?"添加财务信息成功":"添加财务信息失败");
			} else {
				bool = financeMessageService.add(financeMessage);
				operatorLog.setOperatorTitle("修改财务信息");
				operatorLog.setOperatorReturn(bool?"修改财务信息成功":"修改财务信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("用户名："+this.loginFrontUser(request).getUserAccount()+"用户Id："+this.loginFrontUser(request).getId());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			operatorLog.setOperatorParams(financeMessage.toString());
			operatorLog.setOperatorStatus(bool?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("100");
			} else {
				json.setCode("101");
			}
			SpringUtils.renderJson(response, json);
		}catch(Exception e){
			
			logger.error("添加或修改财务信息失败");
		}
		
	}

	/**
	 * 教育背景
	 */
	@RequestMapping("savaEducation.do")
	public void savaEducation(HttpServletRequest request,
			HttpServletResponse response, Model model,
			EducationMessage educationMessage) throws ParseException {
		try{
			OperatorLog operatorLog=new OperatorLog();
			Map<String, String> paramsMap = getParameters(request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			educationMessage.setUserId(this.loginFrontUser(request).getId());
			educationMessage.setDateFrom(sdf.parse(paramsMap.get("yearString")));
			educationMessage.setDateTo(sdf.parse(paramsMap.get("yearString1")));
			boolean bool = false;
			JsonResult json = new JsonResult();
			EducationMessage edu=this.educationMessageService.getByUserId(this.loginFrontUser(request).getId());
			if (edu != null) {
				bool = educationMessageService.save(educationMessage);
				operatorLog.setOperatorTitle("添加教育背景信息");
				operatorLog.setOperatorReturn(bool?"添加教育背景信息成功":"添加教育背景信息失败");
			} else {
				bool = educationMessageService.add(educationMessage);
				operatorLog.setOperatorTitle("修改教育背景信息");
				operatorLog.setOperatorReturn(bool?"修改教育背景信息成功":"修改教育背景信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("用户名："+this.loginFrontUser(request).getUserAccount()+"用户Id："+this.loginFrontUser(request).getId());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			operatorLog.setOperatorParams(educationMessage.toString());
			operatorLog.setOperatorStatus(bool?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("100");
			} else {
				json.setCode("101");
			}
			SpringUtils.renderJson(response, json);
		}catch(Exception e){
			
			logger.error("添加或修改教育背景失败");
		}
	}

	/**
	 * 配偶资料
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param spouseMessage
	 */
	@RequestMapping("savaSpouse.do")
	public void savaSpouse(HttpServletRequest request,
			HttpServletResponse response, Model model,
			SpouseMessage spouseMessage) {
		try{
			OperatorLog operatorLog=new OperatorLog();
			spouseMessage.setUserId(this.loginFrontUser(request).getId());
			boolean bool = false;
			JsonResult json = new JsonResult();
			SpouseMessage spouse=this.spouseMessageService.getByUserId(this.loginFrontUser(request).getId());
			if (spouse!= null) {
				bool = spouseMessageService.save(spouseMessage);
				operatorLog.setOperatorTitle("添加配偶资料信息");
				operatorLog.setOperatorReturn(bool?"添加配偶资料信息成功":"添加配偶资料信息失败");
			} else {
				bool = spouseMessageService.add(spouseMessage);
				operatorLog.setOperatorTitle("修改配偶资料信息");
				operatorLog.setOperatorReturn(bool?"修改配偶资料信息成功":"修改配偶资料信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("用户名："+this.loginFrontUser(request).getUserAccount()+"用户Id："+this.loginFrontUser(request).getId());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			operatorLog.setOperatorParams(spouseMessage.toString());
			operatorLog.setOperatorStatus(bool?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("100");
			} else {
				json.setCode("101");
			}
			SpringUtils.renderJson(response, json);
		}catch(Exception e){
			
			logger.error("添加或修改配偶资料出错");
		}
		
	}

	/**
	 * 私营信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param privateBusinessMessage
	 * @throws ParseException
	 */
	@RequestMapping("savaPrivateBusiness.do")
	public void savaPrivateBusiness(HttpServletRequest request,
			HttpServletResponse response, Model model,
			PrivateBusinessMessage privateBusinessMessage)
			throws ParseException {
		try{
			OperatorLog operatorLog=new OperatorLog();
			Map<String, String> paramsMap = getParameters(request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			privateBusinessMessage.setUserId(this.loginFrontUser(request).getId());
			privateBusinessMessage.setEstablishTime(sdf.parse(paramsMap.get("yearString")));
			boolean bool = false;
			JsonResult json = new JsonResult();
			PrivateBusinessMessage privateB=this.privateBusinessMessageService.getByUserId(this.loginFrontUser(request).getId());
			if (privateB != null) {
				bool = privateBusinessMessageService.save(privateBusinessMessage);
				operatorLog.setOperatorTitle("添加私营信息");
				operatorLog.setOperatorReturn(bool?"添加私营信息成功":"添加私营信息失败");
			} else {
				bool = privateBusinessMessageService.add(privateBusinessMessage);
				operatorLog.setOperatorTitle("修改私营信息");
				operatorLog.setOperatorReturn(bool?"修改私营信息成功":"修改私营信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("用户名："+this.loginFrontUser(request).getUserAccount()+"用户Id："+this.loginFrontUser(request).getId());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			operatorLog.setOperatorParams(privateBusinessMessage.toString());
			operatorLog.setOperatorStatus(bool?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("100");
			} else {
				json.setCode("101");
			}
			SpringUtils.renderJson(response, json);
		}catch(Exception e){
			
			logger.error("添加或修改私营信息出错");
		}
		
	}

	/**
	 * 个人资料
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param personalMessage
	 */
	@RequestMapping("savaPersonal.do")
	public void savaPersonal(HttpServletRequest request,
			HttpServletResponse response, Model model,
			PersonalMessage personalMessage) {
		try{
			OperatorLog operatorLog=new OperatorLog();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			personalMessage.setUserId(this.loginFrontUser(request).getId());
			boolean bool = false;
			JsonResult json = new JsonResult();
			PersonalMessage personal=this.personalMessageService.getByUserId(this.loginFrontUser(request).getId());
			String socialId = request.getParameter("socialId");
			Integer social = Integer.parseInt(request.getParameter("social").toString());
			if(social == 1){
				personalMessage.setSocial(social);
				personalMessage.setSocialId(socialId);
			}else{
				personalMessage.setSocial(social);
			}
			if (personal != null) {
				bool = personalMessageService.save(personalMessage);
				operatorLog.setOperatorTitle("添加个人资料信息");
				operatorLog.setOperatorReturn(bool?"添加个人资料息成功":"添加个人资料信息失败");
			} else {
				bool = personalMessageService.add(personalMessage);
				operatorLog.setOperatorTitle("修改个人资料信息");
				operatorLog.setOperatorReturn(bool?"修改个人资料信息成功":"修改个人资料信息失败");
			}
			User user = new User();
			user.setId(this.loginFrontUser(request).getId());
			user.setUserArea(request.getParameter("userArea"));
			user.setUserCity(request.getParameter("userCity"));
			user.setUserProvince(request.getParameter("userProvince"));
			user.setUserNation(request.getParameter("userNation"));
			String data=request.getParameter("userBirthday");
			if(null!=data&&!"".equals(data)){
				user.setUserBirthday(sdf.parse(data));
			}
			bool = userService.updateByPrimaryKeySelective(user);
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("用户名："+this.loginFrontUser(request).getUserAccount()+"用户Id："+this.loginFrontUser(request).getId());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			operatorLog.setOperatorParams(personalMessage.toString());
			operatorLog.setOperatorStatus(bool?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("100");
			} else {
				json.setCode("101");
			}
			SpringUtils.renderJson(response, json);
		}catch(Exception e){
			
			logger.error("添加或修改个人资料出错");
		}
		
	}

	/**
	 * 联系方式
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param relationMessage
	 */
	@RequestMapping("savaRelation.do")
	public void savaRelation(HttpServletRequest request,
			HttpServletResponse response, Model model,
			RelationMessage relationMessage) {
		try{
			OperatorLog operatorLog=new OperatorLog();
			relationMessage.setUserId(this.loginFrontUser(request).getId());
			boolean bool = false;
			JsonResult json = new JsonResult();
			RelationMessage relation=this.relationMessageService.getByUserId(this.loginFrontUser(request).getId());
			if (relation != null) {
				bool = relationMessageService.save(relationMessage);
				operatorLog.setOperatorTitle("添加联系方式信息");
				operatorLog.setOperatorReturn(bool?"添加联系方式信息成功":"添加联系方式信息失败");
			} else {
				bool = relationMessageService.add(relationMessage);
				operatorLog.setOperatorTitle("修改联系方式信息");
				operatorLog.setOperatorReturn(bool?"修改联系方式信息成功":"修改联系方式信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("用户名："+this.loginFrontUser(request).getUserAccount()+"用户Id："+this.loginFrontUser(request).getId());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PERSONAL_MESSAGE);
			operatorLog.setOperatorParams(relationMessage.toString());
			operatorLog.setOperatorStatus(bool?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (bool) {
				json.setCode("100");
			} else {
				json.setCode("101");
			}
			SpringUtils.renderJson(response, json);
		}catch(Exception e){
			
			logger.error("添加或修改联系方式出错");
		}
		
	}
}
