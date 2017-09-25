package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.entity.ShopOrder;
/**
 * 积分订单
 * */
public interface ShopOrderService {
	/**
	 * 后台分页
	 * @param paramsMap
	 * @return
	 */
	public PageModel getShopOrderList(Map<String,String> paramsMap);
	
	public ShopOrder getById(Integer id);
	
	public JsonResult updateShopOrder(ShopOrder shopOrder);
	
	/**
	 * 用户下单
	 * @param order
	 * @return
	 */
	public JsonResult addUserOrder(ShopOrder order);
	/**
	 * 取消订单
	 * @param orderId
	 * @return
	 */
	public JsonResult cancelUserOrder(Integer orderId, Integer cancelType);
	
	
	public List<Map<String, String>> getAllShopOrder(Map<String,Object> paramsMap);
	
}
