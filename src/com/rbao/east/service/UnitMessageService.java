package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.UnitMessage;
/**
 * 单位信息
 * */
public interface UnitMessageService {
	/**
	 * 分页
	 * @param paramsMap
	 * @return
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询角色
	* @return    UnitMessage 返回类型
	* @throws
	 */
	public UnitMessage getById(Integer id);
	/**
	 * 
	* @Title: getByUserId
	* @Description: 根据用户id查询角色
	* @return    UnitMessage 返回类型
	* @throws
	 */
	public UnitMessage getByUserId(Integer userId);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param unitMessage
	 * @return
	 */
	public boolean save(UnitMessage unitMessage) ;
	/**
	 * 添加
	 * @param unitMessage
	 * @return
	 */
	public boolean add(UnitMessage unitMessage) ;
	
}
