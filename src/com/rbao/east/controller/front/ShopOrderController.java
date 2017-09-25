package com.rbao.east.controller.front;

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

import com.rbao.east.common.GenerateNo;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.ShopOrder;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserCredit;
import com.rbao.east.service.ShopCategoryService;
import com.rbao.east.service.ShopGoodsService;
import com.rbao.east.service.ShopOrderService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;

/**
 * 积分商城订单管理
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("usrOrder/")
public class ShopOrderController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(ShopOrderController.class);
	@Autowired
	ShopOrderService orderSvc;
	@Autowired
	private UserService userService;
	@Autowired
	private UserCreditService  userCreditService;
	
	
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@Autowired
	private ShopOrderService shopOrderService;
	/**
	 * 跳转我的订单列表
	 * */
	@RequestMapping("myOrderList")
	public String myOrderList(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		
		
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
		
		//获取商品分类
		model.addAttribute("categorys", shopCategoryService.selectEnableList());
		
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("status",ShopOrder.status_yes_send);
		List<Map<String, String>> list=shopOrderService.getAllShopOrder(paramsMap);
		model.addAttribute("pm", list);
		
		
		return "shop/shopOrder";
		
	}
	
	
	
	/**
	 * 用户下单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getMyOrderList")
	public void getMyOrderList(HttpServletRequest request,
			HttpServletResponse response) {
		User user = this.loginFrontUser(request);
		if (user != null) {
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("userName", user.getUserAccount());
			PageModel pageMode = orderSvc.getShopOrderList(paramsMap);
			SpringUtils.renderJson(response, pageMode);
		}

		
	}
	
	
	
	
	
	
	/**
	 * 用户下单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("addOrder")
	public void addOrder(HttpServletRequest request,
			HttpServletResponse response) {
		User user = this.loginFrontUser(request);
		if (user == null) {
			SpringUtils.renderJsonResult(response, "115", "获取用户信息失败，请重新登录");
			return;
		}

		Map<String, String> params = this.getParameters(request);

		Integer goodsId = Integer.valueOf(params.get("goodsId"));// 单个商品兑换积分
		ShopOrder order = new ShopOrder();
		order.setAddIp(RequestUtils.getIpAddr(request));
		order.setGoodsCount(Integer.valueOf(params.get("goodsCount")));// 兑换商品数量
		order.setGoodsId(goodsId);
		order.setOrderNo(GenerateNo.nextOrdId());// 订单号
		order.setRecvAddress(params.get("recvAddr"));
		order.setRecvTel(params.get("recvTel"));
		order.setRecvUser(params.get("recvUser"));
		order.setUserId(user.getId());

		JsonResult sResult = null;

		try {
			sResult = this.orderSvc.addUserOrder(order);
		} catch (Exception e) {
			logger.error("addOrder error:" + params, e);
			sResult = new JsonResult("103", "系统繁忙，请重试");
		}

		// 添加日志
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("积分商城下单");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_SHOP_ORDER);
		operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
		operatorLog.setOperatorParams(params.toString());
		operatorLog.setOperatorReturn(sResult.getMsg());
		operatorLog.setOperatorStatus(Integer.parseInt(sResult.getCode()));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addFrontLog(operatorLog);

		SpringUtils.renderJsonResult(response, sResult.getCode(),
				sResult.getMsg());
	}

	/**
	 * 取消订单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("cancelOrder")
	public void cancelOrder(HttpServletRequest request,
			HttpServletResponse response) {
		User user = this.loginFrontUser(request);
		if (user == null) {
			SpringUtils.renderJsonResult(response, "115", "获取用户信息失败，请重新登录");
			return;
		}

		Map<String, String> params = this.getParameters(request);

		Integer orderId = Integer.valueOf(params.get("orderId"));// 单个商品兑换积分
		JsonResult sResult = null;

		try {
			sResult = this.orderSvc.cancelUserOrder(orderId,
					ShopOrder.status_cancle_by_user);
		} catch (Exception e) {
			logger.error("addOrder error:" + params, e);
			sResult = new JsonResult("103", "系统繁忙，请重试");
		}

		// 添加日志
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("积分商城取消订单");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_SHOP_ORDER);
		operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
		operatorLog.setOperatorParams(params.toString());
		operatorLog.setOperatorReturn(sResult.getMsg());
		operatorLog.setOperatorStatus(Integer.parseInt(sResult.getCode()));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addFrontLog(operatorLog);

		SpringUtils.renderJsonResult(response, sResult.getCode(),
				sResult.getMsg());
	}

}