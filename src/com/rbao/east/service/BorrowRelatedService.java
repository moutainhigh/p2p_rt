package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.BorrowRelated;

/**
 * 定活通
 * */
public interface BorrowRelatedService {

	
	/**
	 * 
	* @Title: saveOrUpdate
	* @Description: 更新	
	* @return   boolean 返回类型 
	* @throws
	 */
	public boolean saveOrUpdate(BorrowRelated borrowRelated);
	/**
	 * 
	* @Title: getDHTPage
	* @Description: 定活通
	* @return    PageModel 返回类型
	* @throws
	 */
	public PageModel getDHTPage(Map<String, String> paramsMap);
	/**
	 * 
	* @Title: findRecentlyEntity
	* @Description: 查询实体
	* @return    BorrowRelated 返回类型
	* @throws
	 */
	public BorrowRelated findRecentlyEntity();
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description: 主键查询
	* @return    BorrowRelated 返回类型
	* @throws
	 */
	public BorrowRelated selectByPrimaryKey(Integer pk);
	
}
