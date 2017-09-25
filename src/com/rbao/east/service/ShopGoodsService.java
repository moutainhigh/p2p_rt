package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.entity.ShopGoods;

/**
 * 积分商品
 * */	
public interface ShopGoodsService {
	
	/**
	 * 后台分页查询
	 * @param params
	 * @return
	 */
	PageModel getAdminPage(Map<String, String> params);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return  	ShopGoods 返回类型  
	* @throws
	 */
	ShopGoods getById(Integer id);
	/**
	 * 
	* @Title: getByCode
	* @Description: 根据code查询
	* @return ShopGoods 返回类型   
	* @throws
	 */
	ShopGoods getByCode(String code);
	/**
	 * 保存操作
	 * @param goods
	 * @return
	 */
	JsonResult save(ShopGoods goods) ; 
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	JsonResult deleteById(Integer id);
 /**
  * 检测是否相同
  * @param code
  * @param id
  * @return
  */
	boolean checkHasSameCode(String code, Integer id); 
/**
 *根据CateGoryCode查询数量
 * @param code
 * @return
 */
	int getCountByCateGoryCode(String code);
/**
 * 更新
 * @param goods
 * @param cellCount
 */
	void updateGoodsSellCount(ShopGoods goods, int cellCount);
	
	/**
	 * 获取商品分类关联信息
	 * @param param
	 * @return
	 */
	Map<String, String> getShopCategoryByParam(Map<String, String> param);
}
