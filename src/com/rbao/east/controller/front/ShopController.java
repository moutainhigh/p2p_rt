package com.rbao.east.controller.front;

import java.util.Calendar;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.reflect.generics.tree.Tree;

import com.rbao.east.common.Constants;
import com.rbao.east.common.GenerateNo;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.CreditLog;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.RelationMessage;
import com.rbao.east.entity.ShopOrder;
import com.rbao.east.entity.SysConfigParams;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserCredit;
import com.rbao.east.entity.VipUser;
import com.rbao.east.service.ChannelService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.CreditTypeService;
import com.rbao.east.service.RelationMessageService;
import com.rbao.east.service.ShopCategoryService;
import com.rbao.east.service.ShopGoodsService;
import com.rbao.east.service.ShopOrderService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.service.VipUserService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.SysCacheUtils;
import com.sun.org.apache.xpath.internal.operations.And;

/**
 * 积分商城订单管理
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("shop/")
public class ShopController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ShopController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VipUserService vipUserService;
	
	@Autowired
	private UserCreditService  userCreditService;
	
	@Autowired
	private CreditTypeService creditTypeService;//积分类型
	
	@Autowired
	private ShopGoodsService shopGoodsService;
	
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@Autowired
	private SysConfigParamService sysConfigParamService;
	
	@Autowired
	private ShopOrderService shopOrderService;
	
	@Autowired
	private RelationMessageService relationMessageService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private CreditLogService creditLogService;
	
	
	
	
	
	/**
	 * 跳转商品商城
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toShopStore.do")
	public String toShopStore(HttpServletRequest request, Model model) {
		
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
		
		//
		String shopType = param.get("type");
		if(shopType != null && !shopType.equals("")) {
			model.addAttribute("type", shopType);
		}
		
		//获取商品分类
		model.addAttribute("categorys", shopCategoryService.selectEnableList());
		
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("status",ShopOrder.status_yes_send);
		List<Map<String, String>> list=shopOrderService.getAllShopOrder(paramsMap);
		model.addAttribute("pm", list);
		
		return "integral/shop/shopStore";
	}
	
	/**
	 * 跳转商品详细
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toShopDetail/{gid}.do")
	public String toShopDetail(HttpServletRequest request, Model model, @PathVariable("gid")String gid) {
		
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
		
		//获取商品详细信息
		param.put("gId", gid);
		model.addAttribute("goods", shopGoodsService.getShopCategoryByParam(param));
		
		//获取商品分类
		model.addAttribute("categorys", shopCategoryService.selectEnableList());
		//获取积分规则
		
		
		
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("status",ShopOrder.status_yes_send);
		List<Map<String, String>> list=shopOrderService.getAllShopOrder(paramsMap);
		model.addAttribute("pm", list);
		
		return "integral/shop/shopDetail";
	}
	
	/**
	 * 加载商品列表
	 * @param response
	 * @param model
	 */
	@RequestMapping("getShopStore.do")
	public void getShopStore(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Map<String, String> params = super.getParameters(request);
		
		
		if(params.get("ctCodeArrays").equals("all")){
			params.put("ctCode", null);
		}else{
			params.put("ctCode", params.get("ctCodeArrays"));
		}
		
		if(params.get("reqCreditArrays").equals("all")){
			params.put("reqCredit", null);
		}else{
			params.put("reqCredit", params.get("reqCreditArrays"));
		}
		
		
		
		params.put(Constants.PAGED_NUM_PERPAGE, 12 + "");
		params.put("isEnable", "1");
		
		PageModel shopStoreModel = shopGoodsService.getAdminPage(params);
		SpringUtils.renderJson(response,shopStoreModel );
	}
	
	/**
	 * 兑换商品
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("cashGoods.do")
	public void cashGoods(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String> params = super.getParameters(request);
		String goodsId=params.get("goodsId");
		String number=params.get("number");
		RelationMessage relation=relationMessageService.getByUserId(loginFrontUser(request).getId());
		User user=userService.getById(loginFrontUser(request).getId());
		JsonResult json = new JsonResult();
		if(relation!=null&&relation.getNewAddress()!=null){
			if(user.getUserRealname()!=null){
				//用户下单
				ShopOrder order=new ShopOrder();
				order.setUserId(loginFrontUser(request).getId());
				order.setGoodsCount(Integer.parseInt(number));
				order.setGoodsId(Integer.parseInt(goodsId));
				order.setRecvAddress(relation.getProvince()+relation.getCity()+relation.getArea()+relation.getNewAddress());
				order.setOrderNo(GenerateNo.nextOrdId());//订单号
				order.setRecvUser(relation.getLinkman2Name());
				order.setRecvTel(relation.getLinkman2Phone());
				order.setAddIp(RequestUtils.getIpAddr(request));
				try {
					json=shopOrderService.addUserOrder(order);
				} catch (Exception e) {
					logger.error("addOrder error:" + params, e);
					json = new JsonResult("108", "系统繁忙，请重试");
				}
				if(json.getCode().equals("200")){
					json.setMsg("兑换成功！");
				}
			}else{
				json.setCode("106");
				json.setMsg("兑换失败！请先进行实名认证！");
			}
		}else{
			json.setCode("107");
			json.setMsg("兑换失败！请先完善您个人资料中的联系方式！");
		}
		SpringUtils.renderJson(response, json);
	}
}