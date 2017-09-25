package com.rbao.east.controller.channelexchange;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.rbao.east.common.Constants;
import com.rbao.east.controller.BaseController;
import com.rbao.east.controller.admin.AccountCashController;
import com.rbao.east.service.ChannelExchangeService;
import com.rbao.east.utils.SpringUtils;


/**
 * 渠道数据交互
 * @author hjy
 * 2016-01-19 10:03:13
 *
 */
@Controller
@RequestMapping("/channelExchange/")
public class ChannelExchangeController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(AccountCashController.class);
	
	@Autowired
	private ChannelExchangeService channelExchangeService;

	public ChannelExchangeService getChannelExchangeService() {
		return channelExchangeService;
	}

	public void setChannelExchangeService(
			ChannelExchangeService channelExchangeService) {
		this.channelExchangeService = channelExchangeService;
	}

	/**
	 * 投资用户统计
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=Constants.PRE_PATH_VIEW + "getTenderUserCount",produces = "application/json,charset=utf-8")	
	public @ResponseBody String getTenderUserCount(HttpServletResponse response, HttpServletRequest request, Model model) {
		Map<String, Object> chart_map = new HashMap<String, Object>();
		List<Map<String, Object>> tenderUser = channelExchangeService.tenderUserCount();

		List<Map<String, Object>> tenderUserList = new ArrayList<Map<String,Object>>();
		Map<String, Object> datas = new HashMap<String, Object>();
		
		for(int i = 0; i < tenderUser.size(); i++) {
			
			datas = new HashMap<String, Object>();
			datas.put("name", "手机号码");
			datas.put("data",  tenderUser.get(i).get("user_phone").toString());
			tenderUserList.add(datas);
			
			datas = new HashMap<String, Object>();
			datas.put("name", "注册时间");
			datas.put("data", tenderUser.get(i).get("user_addtime").toString());
			tenderUserList.add(datas);
			
			datas = new HashMap<String, Object>();
			datas.put("name","投资金额" );
			datas.put("data", tenderUser.get(i).get("tender_amount").toString());
			tenderUserList.add(datas);
			
			datas = new HashMap<String, Object>();
			datas.put("name", "首次投资时间");
			datas.put("data",  tenderUser.get(i).get("tender_addtime").toString());
			tenderUserList.add(datas);
		}
			
		chart_map.put("series", tenderUserList);
		return SpringUtils.fromObject(chart_map);		
		
	}
	

}