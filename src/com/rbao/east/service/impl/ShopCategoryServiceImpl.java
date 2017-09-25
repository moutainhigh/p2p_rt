package com.rbao.east.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.dao.ShopCategoryDao;
import com.rbao.east.entity.ShopCategory;
import com.rbao.east.service.ShopCategoryService;
import com.rbao.east.service.ShopGoodsService;
import com.rbao.east.utils.StringUtil;



@Service
@Transactional
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Autowired 
	
	private ShopCategoryDao shopCategoryDao;
	@Autowired 
	ShopGoodsService goodsService;
	
	@Override
	public List<ShopCategory> selectEnableList(){
		return this.shopCategoryDao.select("selectEnableList", null);
	}
	
	@Override	
	public PageModel getShopCategoryList(Map<String, String> paramsMap) {
		return this.shopCategoryDao.getPage("selectByEntity", paramsMap);
	}
	@Override	
	public JsonResult saveOrUpdate(ShopCategory ct) {
		if(ct.getId()!= null && ct.getId().intValue() > 0){ //修改
			ShopCategory dbCt = getById(ct.getId());
			if(dbCt == null){
				return new JsonResult("102","没有找到当前记录，可能已经被删除");
			}
			if(goodsService.getCountByCateGoryCode(dbCt.getCategoryCode()) > 0){
				if(!dbCt.getCategoryCode().equals(ct.getCategoryCode())){
					return new JsonResult("103","当前分类下有商品，不能修改原编码["+dbCt.getCategoryCode()+"]");
				}
				
			}
			
		}
		Integer totalCount=checkSameCode(StringUtil.toString(ct.getId()), ct.getCategoryCode());
		if(totalCount.intValue() > 0){
			return new JsonResult("105","编码已经存在，请重新输入");
		}
		this.shopCategoryDao.saveOrUpdate(ct);
		return new JsonResult(JsonResult.SUCCESS,"保存成功");
	}
	@Override
	
	public ShopCategory getById(Integer id) {
		return this.shopCategoryDao.selectByPrimaryKey(id);
	}
	@Override
	
	public JsonResult deleteById(Integer id) {
		ShopCategory ct = getById(id);
		if(ct == null){
			return new JsonResult("102","没有找到当前记录，可能已经被删除");
		}
		if(goodsService.getCountByCateGoryCode(ct.getCategoryCode()) > 0){
			return new JsonResult("103","当前分类下有商品，必须先删除对应商品才能删除栏目");
		}
		this.shopCategoryDao.deleteByPrimaryKey(id);
		return new JsonResult(JsonResult.SUCCESS,"删除成功");
	}
	@Override
	
	public Integer checkSameCode(String shopCategoryId, String shopCategoryCode) {
		Map map = new HashMap();
		map.put("shopCategoryId", shopCategoryId);
		map.put("categoryCode", shopCategoryCode);
		
		return this.shopCategoryDao.getTotalCount("selectTotalCount", map);
	}

	

	

	
	
}
