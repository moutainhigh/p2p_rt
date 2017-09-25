package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.entity.ShopCategory;

/**
 * 商品分类
 * */
public interface ShopCategoryService {
/**
 * 分页
 * @param paramsMap
 * @return
 */
	public PageModel getShopCategoryList(Map<String, String> paramsMap);
/**
 * 更新
 * @param ShopCategory
 * @return
 */
	public JsonResult saveOrUpdate(ShopCategory ShopCategory);
	/**
	 * 
	* @Title: getById
	* @Description:根据id查询 
	* @return   ShopCategory返回类型 
	* @throws
	 */
	public ShopCategory getById(Integer id);
/**
 * 删除
 * @param id
 * @return
 */
	public JsonResult deleteById(Integer id);
	/**
	 * 
	* @Title: checkSameCode
	* @Description: 
	* @return   Integer 返回类型 
	* @throws
	 */
	public Integer checkSameCode(String shopCategoryId, String shopCategoryCode);

	/**
	 * 查询所有可用的分类
	 * @return
	 */
	List<ShopCategory> selectEnableList();

}
