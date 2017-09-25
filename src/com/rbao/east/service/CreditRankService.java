package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.CreditRank;

/**
 * 积分排名
 * */	
public interface CreditRankService {
	/**
	 * 
	* @Title: getList
	* @Description: 分页
	* @return    PageModel返回类型
	* @throws
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return  CreditRank返回类型  
	* @throws
	 */
	public CreditRank getById(Integer id);
	/**
	 * 
	* @Title: deleteById
	* @Description: 删除
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean deleteById(Integer id);
	/**
	 * 
	* @Title: save
	* @Description:保存 
	* @return  boolean返回类型  
	* @throws
	 */
	public boolean save(CreditRank creditRank) ;
	/**
	 * 
	* @Title: add
	* @Description: 添加	
	* @return   boolean返回类型 
	* @throws
	 */
	public boolean add(CreditRank creditRank) ;
	
}
