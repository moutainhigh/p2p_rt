package com.rbao.east.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rbao.east.common.Constants;
import com.rbao.east.entity.APPAutologinLog;
import com.rbao.east.entity.User;
import com.rbao.east.service.APPAutoLoginLogService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.TokenUtils;

/**
 * CMS上下文信息拦截器
 * 
 * 包括登录信息、权限信息、站点信息
 */
public class MobileContextInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(MobileContextInterceptor.class);
	@Autowired
	private APPAutoLoginLogService appLogService;
	
	@Autowired
	private UserService userService;
	@SuppressWarnings("finally")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
			HttpSession session = request.getSession();
		
		System.out.println("sessionId:===>"+session.getId());
		try{
		
 		/*if (null == session.getAttribute(Constants.APP_USER_SESSION)) {*/
			
			String token = request.getHeader("token");
			if(token == null || "".equals(token)){
				token = request.getParameter("token");
			}
			
			System.out.println(token != null && StringUtils.isNotEmpty(token));
			if(token != null && StringUtils.isNotEmpty(token)){
				//从数据库中根据token获取当前用户信息
				Map<String, String> params = new HashMap<String, String>();
				params.put("token", token);
				APPAutologinLog loginLog = this.appLogService.getByParam(params);
				if(loginLog != null){
					//解密token密文
					Map<String, Object> tokenMap = TokenUtils.decryptToken(token);
					//没有被篡改
					if(tokenMap.get("userId").equals(loginLog.getUserId().toString())){
						Date now = new Date();//当前时间
						Date effTime = loginLog.getEffTime();						
						if(now.before(effTime)){
							//当前token有效							
							//更新有效时间
							loginLog.setEffTime(TokenUtils.getEffTime(effTime));
							this.appLogService.saveOrUpdate(loginLog);
							
							User user = this.userService.getById(loginLog.getUserId());
							session.setMaxInactiveInterval(TokenUtils.effSecond);
							session.setAttribute(Constants.APP_USER_SESSION, user);
							return true;
						}
						
					}
				}else{
					request.setAttribute("msg", "未登录！");
					response.setStatus(401);
					logger.info("未登陆");
					return false;
				}
			}else{
				request.setAttribute("msg", "未登录！");
				response.setStatus(401);
				logger.info("未登陆");
				return false;
			}
			
		/*}*/
			
		}catch(Exception e){
			 System.out.println("in procedure, catch ArithmeticException: " + e);
			 response.setStatus(401);
			 logger.info("未登陆");
		}
		return true;
		
	}
}