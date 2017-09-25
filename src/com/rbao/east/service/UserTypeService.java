package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.UserType;

/**
 * 用户类型
 * */	
public interface UserTypeService {
	/**
	 * 分页
	 * @param paramsMap
	 * @return
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return   UserType返回类型 
	* @throws
	 */
	public UserType getById(Integer id);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param userType
	 * @return
	 */
	public boolean save(UserType userType) ;
	/**
	 * 添加
	 * @param userType
	 * @return
	 */
	public boolean add(UserType userType) ;
	/**
	 * 
	* @Title: getUserTypeList
	* @Description: 用户类型列表信息
	* @return    List<UserType> 
	* @throws
	 */
	public List<UserType> getUserTypeList();
	
	//根据type查询
	public List<UserType> selectByType(Integer type);
	
	//根据那么查询
	public UserType selectByName(String name);
	
}
