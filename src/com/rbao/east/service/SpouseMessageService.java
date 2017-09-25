package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.SpouseMessage;

/**
 * 配偶信息
 * */	
public interface SpouseMessageService {
	/**
	 * 
	* @Title: getList
	* @Description: 分页
	* @return    PageModel 返回类型
	* @throws
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return    SpouseMessage 返回类型
	* @throws
	 */
	public SpouseMessage getById(Integer id);
	/**
	 * 
	* @Title: getByUserId
	* @Description: 根据用户id查询
	* @return    SpouseMessage 返回类型
	* @throws
	 */
	public SpouseMessage getByUserId(Integer userId);
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
	* @Description: 保存 
	* @return    boolean返回类型
	* @throws
	 */
	public boolean save(SpouseMessage spouseMessage) ;
	/**
	 * 
	* @Title: add
	* @Description: 添加
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean add(SpouseMessage spouseMessage) ;
	
}
