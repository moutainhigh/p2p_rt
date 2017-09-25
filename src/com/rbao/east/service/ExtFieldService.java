package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.ExtField;
import com.rbao.east.entity.ExtValue;

/**
 * 扩展字段
 * */
public interface ExtFieldService {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @Description: 删除
	* @return    boolean 返回类型
	* @throws
	 */
	boolean deleteByPrimaryKey(Integer id);
	/**
	 * 
	* @Title: insert
	* @Description: 添加
	* @return    boolean 返回类型
	* @throws
	 */
	boolean insert(ExtField extField);
	/**
	 * 
	* @Title: insertSelective
	* @Description: 添加
	* @return    boolean 返回类型
	* @throws
	 */
	boolean insertSelective(ExtField extField);
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description: 查询
	* @return    ExtField返回类型
	* @throws
	 */
	ExtField selectByPrimaryKey(Integer id);
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @Description: 更新
	* @return   boolean 返回类型 
	* @throws
	 */
	boolean updateByPrimaryKeySelective(ExtField extField);
	/**
	 * 
	* @Title: updateByPrimaryKey
	* @Description: 更新
	* @return   boolean 返回类型 
	* @throws
	 */
	boolean updateByPrimaryKey(ExtField extField);

	/**  
	* @Name: getAllExtInfo
	* @Description: 根据extId查询信息
	* @Author: 肖世杰 
	* @Version: V1.00 
	* @Create Date: 2015-03-06 
	* @Parameters: 
		 *extId 表rb_ext_field表id
	* @Return: List
	*/
	List getAllExtInfo(String extType, Integer borrowId);

	/**
	 * 
	* @Title: updateValue
	* @Description: 更新
	* @return   boolean 返回类型 
	* @throws
	 */
	public boolean updateValue(List<ExtValue> list, Map<String, String> params);
	
}
