package com.rbao.east.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.dao.ShopOrderDao;
import com.rbao.east.entity.ShopGoods;
import com.rbao.east.entity.ShopOrder;
import com.rbao.east.entity.UserCredit;
import com.rbao.east.service.ShopGoodsService;
import com.rbao.east.service.ShopOrderService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.utils.CompareUtils;

@Service
@Transactional
public class ShopOrderServiceImpl implements ShopOrderService {
	
	@Autowired
	private ShopOrderDao shopOrderDao;
	@Autowired
	ShopGoodsService goodsSvc;
	@Autowired
	UserCreditService uCredtSvc;
	
	@Override
	public PageModel getShopOrderList(Map<String,String> paramsMap){
		
		return this.shopOrderDao.getPage("selectByEntity",paramsMap);
	}
	
	@Override
	public ShopOrder getById(Integer id){
		
		return this.shopOrderDao.selectByPrimaryKey(id);
	}
	public void insertShopOrder(ShopOrder order){
		order.setCreateTime(new Date());
		order.setUpdateTime(order.getCreateTime());
		order.setStatus(ShopOrder.status_no_send);
		boolean succ = this.shopOrderDao.insertSelective(order);
		if(!succ){
			throw new RuntimeException("shopOrder insertSelective return false :"+order);
		}
	}

	@Override
	public JsonResult  updateShopOrder(ShopOrder shopOrder) {
		ShopOrder dbRecord = getById(shopOrder.getId());
		if(!dbRecord.getStatus().equals(ShopOrder.status_no_send)){
			return new JsonResult("101","当前订单状态为["+ShopOrder.ALL_ShopOrder_STATUS.get(dbRecord.getStatus())+"]，不允许修改");
		}
		//shopOrder.set
		dbRecord.setMisc1(shopOrder.getMisc1());
		dbRecord.setMisc2(shopOrder.getMisc2());
		dbRecord.setRemark(shopOrder.getRemark());
		dbRecord.setStatus(shopOrder.getStatus());
		this.shopOrderDao.updateByPrimaryKeySelective(dbRecord);
		//return new JsonResult("101","操作成功");
		return new JsonResult(JsonResult.SUCCESS,"操作成功");
	}

	
	public JsonResult allowToAddOrder(ShopOrder order,ShopGoods goods){
		if(CompareUtils.greaterThan(goods.getPublishTime(), new Date())){
			return new JsonResult("101","当前商品尚未开始兑换");
		}
		if(goods.getIsEnable().equals(ShopGoods.isEnable_no)){
			return new JsonResult("102","当前商品不可兑换");
		}
		if(order.getGoodsCount().intValue() <= 0){
			return new JsonResult("103","商品数量必须大于0");
		}
		int remainCount = goods.getTotalCount().intValue() - goods.getSellCount().intValue();
		if(order.getGoodsCount().intValue() > remainCount){
			return new JsonResult("103","当前商品剩余数量不足");
		}
		UserCredit ucredt = uCredtSvc.getByUserId(order.getUserId());
		if(ucredt.getCreditValueUsable().intValue() < order.getCastCredit()){
			return new JsonResult("104","可用积分不足，当前积分为["+ucredt.getCreditValueUsable()+"]");
		}
		int castSingleCredit = order.getCastCredit().intValue()/order.getGoodsCount().intValue();
		if(castSingleCredit < goods.getRequireCredit().intValue()){
			return new JsonResult("105","花费积分不足购买此商品");
		}
		return new JsonResult(JsonResult.SUCCESS);
		
	}
	@Override
	public JsonResult addUserOrder(ShopOrder order) {
		ShopGoods goods = this.goodsSvc.getById(order.getGoodsId());
		//设置花费总积分
		order.setCastCredit(goods.getRequireCredit().intValue() * order.getGoodsCount().intValue());//兑换所需总积分
		
		//判断是否可以兑换
		JsonResult rst = allowToAddOrder(order, goods);
		if(!rst.isSuccessed()){
			return rst;
		}
		//添加订单
		insertShopOrder(order);
		//修改商品已购买数量，此处会检查版本号抛出OptimisticLockingFailureException，避免并发
		goodsSvc.updateGoodsSellCount(goods,order.getGoodsCount());
		//扣除积分
		uCredtSvc.subtractCreditValueUsable(order.getUserId(), order.getCastCredit(), "积分商城兑换"+order.getGoodsCount()+"份["+goods.getGoodsName()+"]");
		return new JsonResult(JsonResult.SUCCESS);
	}
	@Override
	public JsonResult cancelUserOrder(Integer orderId,Integer cancelType) {
		
		ShopOrder order = getById(orderId);
		ShopGoods goods = this.goodsSvc.getById(order.getGoodsId());
		JsonResult rst = allowToCancelOrder(order); 
		if(!rst.isSuccessed()){
			return rst;
		}
		//修改订单
		order.setStatus(cancelType);
		order.setUpdateTime(new Date());
		//此处会检查版本号抛出OptimisticLockingFailureException，避免并发
		shopOrderDao.updateByPrimaryKeySelective(order);
		//更新已售数量,此处会检查版本号抛出OptimisticLockingFailureException，避免并发
		goodsSvc.updateGoodsSellCount(goods,-order.getGoodsCount());
		//退回积分
		uCredtSvc.subtractCreditValueUsable(order.getUserId(), -order.getCastCredit(), "积分商城兑换"+order.getGoodsCount()+"份["+goods.getGoodsName()+"]");
		return new JsonResult(JsonResult.SUCCESS,"操作成功");
	}
	public JsonResult allowToCancelOrder(ShopOrder order){
		if(!order.getStatus().equals(ShopOrder.status_no_send)){
			return new JsonResult("101","当前订单不允许取消");
		}
		return new JsonResult(JsonResult.SUCCESS);
	}
	
	
	
	@Override
	public List<Map<String, String>> getAllShopOrder(Map<String, Object> paramsMap) {
		return shopOrderDao.selects("selectAll", paramsMap);
	}
	
	
}
