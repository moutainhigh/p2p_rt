package com.rbao.east.controller.front;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.search.expression.Criteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Channel;
import com.rbao.east.entity.ShopOrder;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserCredit;
import com.rbao.east.service.ChannelService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.ShopOrderService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.SpringUtils;
/**
 * 积分操作
 * */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
	@Autowired
	private ChannelService channelService;
	@Autowired
	private UserService userService;
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	private UserCreditService  userCreditService;
	
	@Autowired
	private ShopOrderService shopOrderService;
	//会员规则
	@RequestMapping("memberRule.do")
	public String memberRule(HttpServletRequest request,
			HttpServletResponse response, Model model){
		//获取用户
				Map<String, String> param = getParameters(request);
				if(param == null){
					param = new HashMap<String, String>();
				}
				param.put("userId", this.loginFrontUser(request).getId().toString());
				User user = this.userService.getById(this.loginFrontUser(request).getId());
				model.addAttribute("user",user);
				
				//用户积分
				UserCredit userCredit = userCreditService.getByUserId(user.getId());
				if(userCredit != null ){
					model.addAttribute("creditValue", userCredit.getCreditValue());
					model.addAttribute("creditValueUsable", userCredit.getCreditValueUsable());
				}
				
				
				Map<String, Object> paramsMap=new HashMap<String, Object>();
				paramsMap.put("status",ShopOrder.status_yes_send);
				List<Map<String, String>> list=shopOrderService.getAllShopOrder(paramsMap);
				
				model.addAttribute("pm", list);
		
		
		return "member/memberRule";
	}
	
	//会员积分
	@RequestMapping("memberIntegral.do")
	public String recLeft(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Channel channel=channelService.selectChannelByCode("hyjf");
		model.addAttribute("channel", channel);
		//获取用户
		Map<String, String> param = getParameters(request);
		if(param == null){
			param = new HashMap<String, String>();
		}
		param.put("userId", this.loginFrontUser(request).getId().toString());
		User user = this.userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user",user);
		
		//用户积分
		UserCredit userCredit = userCreditService.getByUserId(user.getId());
		if(userCredit != null ){
			model.addAttribute("creditValue", userCredit.getCreditValue());
			model.addAttribute("creditValueUsable", userCredit.getCreditValueUsable());
		}
		
		
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("status",ShopOrder.status_yes_send);
		List<Map<String, String>> list=shopOrderService.getAllShopOrder(paramsMap);
		
		model.addAttribute("pm", list);

		return "member/memberIntegral";
	}
	
	//积分转换-领取红包
	@RequestMapping("getRedPacket.do")
	public String getRedPacket(Model model ){
		Channel channel=channelService.selectChannelByCode("redInfo");
		model.addAttribute("channel", channel);
		
		return "member/getRedPacket";
	}
		
	
	@RequestMapping("creditLog.do")
	public String creditLog(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//获取用户
		Map<String, String> param = getParameters(request);
		if(param == null){
			param = new HashMap<String, String>();
		}
		param.put("userId", this.loginFrontUser(request).getId().toString());
		User user = this.userService.getById(this.loginFrontUser(request).getId());
		model.addAttribute("user",user);
		
		//用户积分
		UserCredit userCredit = userCreditService.getByUserId(user.getId());
		if(userCredit != null ){
			model.addAttribute("creditValue", userCredit.getCreditValue());
			model.addAttribute("creditValueUsable", userCredit.getCreditValueUsable());
		}
		
		
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("status",ShopOrder.status_yes_send);
		List<Map<String, String>> list=shopOrderService.getAllShopOrder(paramsMap);
		
		model.addAttribute("pm", list);
		return "member/creditLog";
	}
	
	@RequestMapping("creditLogPage.do")
	public void creditLogPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		
		
		PageModel pageModel = creditLogService.getPagedListNew(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);
	}
	
	
	

}