package com.rbao.east.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rbao.east.common.Constants;

/**
 * CMS上下文信息拦截器
 * 
 * 包括登录信息、权限信息、站点信息
 */
public class WebContextInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(WebContextInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException {

		if (null == request.getSession().getAttribute(
				Constants.FRONT_USER_SESSION)) {
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest")){// 如果是ajax请求响应头会有，x-requested-with；
				response.setHeader("statusCode", "301");// 在响应头设置session状态
				return false;
			}
			request.setAttribute(Constants.MESSAGE, "未登录！");
			try {
				response.sendRedirect(request.getContextPath() + "/web/toLogin.html");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			return false;
		}
		return true;
	}
}