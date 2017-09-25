package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.RelationMessage;

/**
 * 配偶关系
 * */
public interface RelationMessageService {
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
	* @return    RelationMessage 返回类型
	* @throws
	 */
	public RelationMessage getById(Integer id);
	/**
	 * 
	* @Title: getByUserId
	* @Description: 根据用户id查询
	* @return    RelationMessage返回类型
	* @throws
	 */
	public RelationMessage getByUserId(Integer userId);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param relationMessage
	 * @return
	 */
	public boolean save(RelationMessage relationMessage) ;
	/**
	 * 添加
	 * @param relationMessage
	 * @return
	 */
	public boolean add(RelationMessage relationMessage) ;
	
}
