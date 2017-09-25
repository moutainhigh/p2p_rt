package com.rbao.east.controller.front;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.rbao.east.common.Constants;
import com.rbao.east.controller.BaseController;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.SysCacheUtils;

/**
 * 第三方登陆
 * 
 * @author user
 *
 */

@Controller
@RequestMapping("thirdpartyLogin/")
public class thirdpartyLoginController extends BaseController {

	@Autowired
	private UserService userService;
	

	@RequestMapping("toThirdparty.do")
	public String toThirdparty(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		
		if (param.containsKey("typeCode")) {
			if (param.get("typeCode").equals("sina")) {
				// 新浪登陆
				try {
					Oauth oauth = new Oauth();
					response.sendRedirect(oauth.authorize("code", null, "all"));
				} catch (IOException e) {
					
				} catch (WeiboException e) {
					
				} // 重定向到新浪授权页面
			} else if (param.get("typeCode").equals("QQ")) {
				// QQ登陆
				com.qq.connect.oauth.Oauth oauth = new com.qq.connect.oauth.Oauth();
				try {
					String url = oauth.getAuthorizeURL(request);
					response.sendRedirect(url);
				} catch (QQConnectException e) {
					
				} catch (IOException e) {
					
				}
			}
		}
		return "index";
	}
	
	
	
	

	@RequestMapping("sinaLogin.do")
	public String sinaLogin(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		String accesstoken = null;
		String code = request.getParameter("code");
		if (code != null) {
			try {
				Oauth oauth = new Oauth();
				AccessToken accessToken = oauth.getAccessTokenByCode(code);
				accesstoken = accessToken.getAccessToken();
				Account am = new Account(accesstoken);
				String uid = am.getUid().get("uid").toString();
				param.put("uid", uid);
				param.put("isThirdparty", "1");
				com.rbao.east.entity.User user = userService.getUserByUid(param);
				if (null == user) {
					if (accessToken != null) {
						Users users = new Users(accesstoken);
						User weiboUser = users.showUserById(uid);
						System.out.println(weiboUser);

						/**
						 * 用于注入数据库 Client client = FbbUtil.getClient(); FbbUser
						 * fbbUser= client.doOauth(1, weiboUser.getId());
						 */
						model.addAttribute("registerProtocol", SysCacheUtils.getSysConfig().getSysRegisterProtocol());
						model.addAttribute("accessToken", accessToken);
						model.addAttribute("uid",uid);
						model.addAttribute("isThirdparty",1);
						model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
						return "user/thirdpartyLogin";
					}
				}else{
					request.getSession().setAttribute(
							Constants.FRONT_USER_SESSION, user);
					return "index";
				}
			} catch (Exception e) {
				
			}
		}
		model.addAttribute("message1", "微博登入失败，稍后再试！！！");
		return "user/login";
	}

	
	
	@RequestMapping("QQLogin.do")
	public String QQLogin(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		try {
			com.qq.connect.javabeans.AccessToken qqtoken = new com.qq.connect.oauth.Oauth().getAccessTokenByRequest(request);
			if(null != qqtoken){
				String accessToken = qqtoken.getAccessToken();
				System.out.println(accessToken);
				OpenID oId = new OpenID(accessToken);
				String openid = oId.getUserOpenID();
				param.put("uid", openid);
				param.put("isThirdparty", "2");
				com.rbao.east.entity.User user = userService.getUserByUid(param);
				if (null == user) {
						UserInfo userinfo = new UserInfo(accessToken, openid);
						UserInfoBean ub = userinfo.getUserInfo();
						System.out.println(ub.getNickname());
						System.out.println(ub.getLevel());
						com.qq.connect.api.weibo.UserInfo weiboUserInfo = new com.qq.connect.api.weibo.UserInfo(accessToken, openid);
						System.out.println(weiboUserInfo.getUserInfo().getName());
						System.out.println(weiboUserInfo.getUserInfo().getEmail());
						System.out.println(weiboUserInfo.getUserInfo().getLevel());
						
						model.addAttribute("registerProtocol", SysCacheUtils.getSysConfig().getSysRegisterProtocol());
						model.addAttribute("accessToken", accessToken);
						model.addAttribute("uid",openid);
						model.addAttribute("isThirdparty",2);
						model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
						return "user/thirdpartyLogin";
			
				}else{
					request.getSession().setAttribute(
							Constants.FRONT_USER_SESSION, user);
					return "index";
				}
			}
		} catch (QQConnectException e) {
			
		}
		model.addAttribute("message1", "QQ登入失败，稍后再试！！！");
		return "user/login";
	}
	
	
	
	
	
	
	
}
