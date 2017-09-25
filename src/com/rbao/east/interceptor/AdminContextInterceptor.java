package com.rbao.east.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.rbao.east.common.Constants;
import com.rbao.east.entity.User;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserService;

/**
 * 管理平台拦截器
 * 
 * 包括登录信息、权限信息
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory
			.getLogger(AdminContextInterceptor.class);
	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		User user = null;
		if (adminId != null) {
			// 指定管理员（开发状态）
			user = userService.getById(adminId);
			if (user == null) {
				throw new IllegalStateException("User ID=" + adminId
						+ " not found!");
			}
		} else {
			// 正常状态
			user = (User) request.getSession().getAttribute(
					Constants.ADMIN_USER_SESSION);
		}
		String uri = getURI(request);
		System.out.println("uri:" + uri);
		// 不在验证的范围内
		if (exclude(uri)) {
			return true;
		}

		// 用户为null跳转到登陆页面
		if (user == null) {
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；

			{
				response.setHeader("statusCode", "301");// 在响应头设置session状态
				return false;
			}
			response.sendRedirect(getLoginUrl(request));
			return false;
		}
		// 用户不是管理员，提示无权限。
		if (!(user.getIsSystem() == 1)) {
			request.setAttribute("message", "用户不是管理员");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		/*
		 * List<TreeModel> treeModels = moduleService.getTreeModelByUserId(user
		 * .getId()); Set<String> treeModelSet = new HashSet<String>(); for (int
		 * i = 0; i < treeModels.size(); i++) { if
		 * (StringUtils.isNotBlank(treeModels.get(i).getUrl())) {
		 * treeModelSet.add(treeModels.get(i).getUrl()); } }
		 * 
		 * // 没有访问权限，提示无权限。 if (!(auth && user.getIsSystem() == 1 &&
		 * permistionPass(uri, treeModelSet, true))) {
		 * request.setAttribute("message", "用户无权限");
		 * response.sendError(HttpServletResponse.SC_FORBIDDEN); return false; }
		 */
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	private String getLoginUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(loginUrl).append("?");
		buff.append("returnUrl").append("=").append(returnUrl);
		if (!StringUtils.isBlank(processUrl)) {
			buff.append("&").append("processUrl").append("=")
					.append(getProcessUrl(request));
		}
		return buff.toString();
	}

	private String getProcessUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(processUrl);
		return buff.toString();
	}

	private boolean exclude(String uri) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean permistionPass(String uri, Set<String> perms,
			boolean viewOnly) {
		String u = null;
		int i;
		i = uri.indexOf("/");
		if (i == -1) {
			throw new RuntimeException("uri must start width '/':" + uri);
		}
		u = uri.substring(i + 1);
		for (String perm : perms) {
			if (u.startsWith(perm)) {
				System.out.println(u.startsWith(perm));
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得第三个路径分隔符的位置
	 * 
	 * @param request
	 * @throws IllegalStateException
	 *             访问路径错误，没有三(四)个'/'
	 */
	private static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		int start = 0, i = 0, count = 2;
		if (!StringUtils.isBlank(ctxPath)) {
			count++;
		}
		while (i < count && start != -1) {
			start = uri.indexOf('/', start + 1);
			i++;
		}
		if (start <= 0) {
			throw new IllegalStateException(
					"admin access path not like '/admin/p2p/...' pattern: "
							+ uri);
		}
		return uri.substring(start);
	}

	private Integer adminId;
	private boolean auth = true;
	private String[] excludeUrls;

	private String loginUrl;
	private String processUrl;
	private String returnUrl;

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public String[] getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getProcessUrl() {
		return processUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	@Autowired
	UserService userService;
	@Autowired
	SysModuleService moduleService;

}