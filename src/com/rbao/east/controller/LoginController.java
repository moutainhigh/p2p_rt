package com.rbao.east.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.rbao.east.common.Constants;
import com.rbao.east.controller.admin.UserManagerController;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.User;
import com.rbao.east.service.OperatorLogService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.RequestUtils;

/**
 * 统一认证中心Action
 * 
 * 统一认证中心由两个方面组成。
 * <ul>
 * <li>
 * 用户信息来源。 可以有多种来源，最合适的来源是LDAP，也可以是数据库。本认证中心为实现更实用于互联网的、更轻量级的单点登录，使用数据库作为用户信息来源。
 * 只要在同一数据库实例下，即可访问到用户信息。各系统可以自行实现注册、修改密码、禁用等功能。如果是用一体系下的应用，这些功能都提供了统一接口。
 * <li>
 * 用户认证信息。用户登录成功后，需要保存登录信息，最合适的保存技术是memcached，也可以是其他集群缓存或数据库。本认证中心使用数据库保存登录信息。
 * 只要在同一数据库实例下，即可访问。各系统可以自行实现退出登录。
 * </ul>
 */
@Controller
public class LoginController {
	public static final String PROCESS_URL = "processUrl";
	public static final String RETURN_URL = "returnUrl";
	public static final String MESSAGE = "message";

	public static final String LOGIN_INPUT = "login";
	public static final String LOGIN_SUCCESS = "index";
	@Autowired
	private ImageCaptchaService captchaService;
	@Autowired
	private UserService userService;
	@Autowired
	protected OperatorLogService operatorLogService;
	private static Logger logger = LoggerFactory
			.getLogger(UserManagerController.class);

	/**
	 * 统一登录入口
	 * 
	 * 
	 * @param processUrl
	 *            登录成功后的处理地址。登录成功后即重定向到该页面，并将returnUrl、auth_key作为参数。
	 * @param returnUrl
	 *            登录成功，并处理后，返回到该地址。
	 * @param message
	 *            登录是提示的信息，比如：“您需要登录后才能继续刚才的操作”，该信息必须用UTF-8编码进行URLEncode。
	 * @param request
	 * @param model
	 * @return 重定向至processUrl，如prosessUrl不存在，则返回登录成功界面。
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String input(HttpServletRequest request, ModelMap model) {
		String processUrl = RequestUtils.getQueryParam(request, PROCESS_URL);
		String returnUrl = RequestUtils.getQueryParam(request, RETURN_URL);
		String message = RequestUtils.getQueryParam(request, MESSAGE);
		User user = (User) request.getSession().getAttribute(
				Constants.ADMIN_USER_SESSION);
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		// 存在认证信息，且未过期
		if (user != null) {
			String view = getView(processUrl, returnUrl);
			if (view != null) {
				return view;
			} else {

				return LOGIN_SUCCESS;
			}
		}
		if (!StringUtils.isBlank(processUrl)) {
			model.addAttribute(PROCESS_URL, processUrl);
		}
		if (!StringUtils.isBlank(returnUrl)) {
			model.addAttribute(RETURN_URL, returnUrl);
		}
		if (!StringUtils.isBlank(message)) {
			model.addAttribute(MESSAGE, message);
		}
		return LOGIN_INPUT;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submit(String username, String password, String processUrl,
			String returnUrl, String message, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		if (validateSubmit(request, response)) {
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid(StringUtils.trim(request
					.getParameter("useraccount")));
			operatorLog.setOperatorTitle("管理员登录日志");
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
			operatorLog.setOperatorParams(StringUtils.trim(request
					.getParameter("useraccount")));
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorIp(RequestUtils.getIpAddr(request));
			User user = userService.selectByUserUid(StringUtils.trim(request
					.getParameter("useraccount")));
			DesEncrypt desEncrpt = new DesEncrypt(
					Constants.DES_PUBLIC_ENCRYPT_KEY);
			DesEncrypt aesEncrypt = new DesEncrypt(
					Constants.DES_PRIVATE_ENCRYPT_KEY);
			System.out.println(desEncrpt.decrypt(StringUtils
					.trimToEmpty(request.getParameter("password"))));
			if (user != null
					&& StringUtils
							.trimToEmpty(user.getUserPassword())
							.toLowerCase()
							.equals(MD5Utils.stringToMD5(aesEncrypt.encrypt(desEncrpt
									.decrypt(StringUtils.trimToEmpty(request
											.getParameter("password"))))))) {
				if (user.getUserIslock() == 0) {
					request.getSession(true).setAttribute(
							Constants.ADMIN_USER_SESSION, user);
					String view = getView(processUrl, returnUrl);

					boolean flag = false;
					try {
						// 获取登陆IP
						String ip = RequestUtils.getIpAddr(request);
						// 获取当期日期
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date date = sdf.parse(sdf.format(new Date()));
						// 修改登陆IP和时间
						user.setUserLoginip(ip);
						user.setUserLogintime(date);
						flag = this.userService.saveOrUpdate(user);
					} catch (Exception e) {
						
						logger.error("更新登录信息出错");
					}
					// 添加日志

					operatorLog.setOperatorReturn("登录成功");
					operatorLog.setOperatorStatus(200);
					operatorLogService.addAdminLog(operatorLog);
					if (view != null) {
						return view;
					} else {
						return "redirect:" + LOGIN_SUCCESS;
					}
				} else {
					operatorLog.setOperatorReturn("用户已经锁定，请联系超级管理员");
					operatorLog.setOperatorStatus(300);
					operatorLogService.addAdminLog(operatorLog);
					model.addAttribute(MESSAGE, "用户已经锁定，请联系超级管理员");
				}

			} else {
				// 添加日志
				operatorLog.setOperatorReturn("登录失败,用户名或密码错误");
				operatorLog.setOperatorStatus(300);
				operatorLogService.addAdminLog(operatorLog);
				model.addAttribute(MESSAGE, "用户名或密码错误");
			}

		} else {
			model.addAttribute(MESSAGE, "验证码错误");
		}
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		if (!StringUtils.isBlank(processUrl)) {
			model.addAttribute(PROCESS_URL, processUrl);
		}
		if (!StringUtils.isBlank(returnUrl)) {
			model.addAttribute(RETURN_URL, returnUrl);
		}
		if (!StringUtils.isBlank(message)) {
			model.addAttribute(MESSAGE, message);
		}
		return LOGIN_INPUT;

	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		// 添加日志
		User user = (User) request.getSession().getAttribute(
				Constants.ADMIN_USER_SESSION);
		if (user != null) {
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setOperatorTitle("管理员退出日志");
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
			operatorLog.setLogUserid(user.getUserAccount());
			operatorLog.setOperatorReturn("后台管理员退出");
			operatorLog.setOperatorStatus(200);
			operatorLog.setOperatorParams(user.getUserAccount());
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorIp(RequestUtils.getIpAddr(request));
			operatorLogService.addAdminLog(operatorLog);
		}
		for (Enumeration attribute = request.getAttributeNames(); attribute
				.hasMoreElements();) {
			request.getSession().removeAttribute(
					(String) attribute.nextElement());
		}
		String processUrl = RequestUtils.getQueryParam(request, PROCESS_URL);
		String returnUrl = RequestUtils.getQueryParam(request, RETURN_URL);
		String view = getView(processUrl, returnUrl);
		request.getSession(false).invalidate();
		if (view != null) {
			return view;
		} else {
			return "redirect:login";
		}
	}

	/**
	 * 获得地址
	 * 
	 * @param processUrl
	 * @param returnUrl
	 * @param authId
	 * @return
	 */
	private String getView(String processUrl, String returnUrl) {
		if (!StringUtils.isBlank(processUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(processUrl);
			if (!StringUtils.isBlank(returnUrl)) {
				sb.append("?").append(RETURN_URL).append("=").append(returnUrl);
			}
			return sb.toString();
		} else if (!StringUtils.isBlank(returnUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(returnUrl);

			return sb.toString();
		} else {
			return null;
		}
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

}
