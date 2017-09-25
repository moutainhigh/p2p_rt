package com.rbao.east.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.dao.ShopGoodsDao;
import com.rbao.east.entity.ShopGoods;
import com.rbao.east.service.ShopGoodsService;
@Service
@Transactional
public class ShopGoodsServiceImpl implements ShopGoodsService {

	@Autowired
	ShopGoodsDao goodsDao ;
	@Override
	public PageModel getAdminPage(Map<String, String> params) {
		return goodsDao.getPage("selectAdminPage", params);
	}

	@Override
	public ShopGoods getById(Integer id) {
		return goodsDao.selectByPrimaryKey(id);
	}
	@Override
	public int getCountByCateGoryCode(String code){
		return (Integer) goodsDao.selects("selectcountByCategoryCode", code).get(0); 
	}

	@Override
	public JsonResult save(ShopGoods goods) {
		if(this.checkHasSameCode(goods.getGoodsCode(), goods.getId())){
			return new JsonResult("102","商品编号重复，保存失败");
		}
		boolean succ = false;
		if(goods.getId() == null){ //新增
			goods.setCreateTime(new Date());
			goods.setUpdateTime(goods.getCreateTime());
			succ = goodsDao.insertSelective(goods);
		}else{ //修改
			ShopGoods dbGoods = goodsDao.selectByPrimaryKey(goods.getId());
			
	//		BeanUtils.copyProperties(goods, dbGoods);
			if(goods.getLogPath()==null){
				goods.setLogPath("");//数据库设置为空
			}
			goods.setUpdateTime(new Date());
			if(goods.getTotalCount().intValue() < dbGoods.getSellCount().intValue()){
				return new JsonResult("101","修改后的总量不能少于已售出数量["+dbGoods.getSellCount()+"]");
			}
			goods.setVsion(dbGoods.getVsion());
			succ = goodsDao.updateByPrimaryKeySelective(goods);
		}
		if(succ){
			return new JsonResult(JsonResult.SUCCESS,"操作成功");
		}else{
			return new JsonResult("108","操作失败，请重试");
		}
		
	}

	@Override
	public JsonResult deleteById(Integer id) {
		ShopGoods goods = getById(id);
		if(goods == null){
			return new JsonResult("201","当前记录已经被删除");
		}
		if(goods.getSellCount().intValue() > 0){
			return new JsonResult("202","已经有人换购当前商品，不允许删除");
		}
		goodsDao.deleteByPrimaryKey(id);
		return new JsonResult(JsonResult.SUCCESS,"删除成功");
	}
	@Override
	public ShopGoods getByCode(String code){
		return goodsDao.selectEntity("selectByCode", code);
	}
	@Override
	public boolean checkHasSameCode(String code,Integer id){
		ShopGoods goods = getByCode(code);
	       
	       if(goods != null){
	    	   if(id ==null || !goods.getId().equals(id)){
	    		   return true;
	    	   }
	       }
	       return false;
	}
	@Override
	public void updateGoodsSellCount(ShopGoods goods,int cellCount){
		goods.setSellCount(goods.getSellCount().intValue() + cellCount);
		goods.setUpdateTime(new Date());
		goodsDao.updateByPrimaryKeySelective(goods);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getShopCategoryByParam(Map<String, String> param) {
		return (Map<String, String>) goodsDao.getObject("selectShopCategory", param);
	}
}
