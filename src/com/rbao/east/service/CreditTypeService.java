package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.CreditType;

/**
 * 积分类型
 * */
public interface CreditTypeService {
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
	* @return  CreditType 返回类型  
	* @throws
	 */
	public CreditType getById(Integer id);
	/**
	 * 
	* @Title: deleteById
	* @Description: 根据id删除	
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean deleteById(Integer id);
	/**
	 * 
	* @Title: save	
	* @Description:  保存	
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean save(CreditType creditType) ;
	/**
	 * 
	* @Title: add
	* @Description: 添加
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean add(CreditType creditType) ;
	
	//根据积分类型名称或code查询积分
	public CreditType getCreditType(String  creditName,String creditCode);
    //编码唯一
	public Integer getbyCode(CreditType creditType);
	
}
