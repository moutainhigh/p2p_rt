package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.PrivateBusinessMessage;
/**
 * 个人企业信息
 * */
public interface PrivateBusinessMessageService {
	/**
	 * 分页
	 * @param paramsMap 参数
	 * @return
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return    PrivateBusinessMessage 返回类型
	* @throws
	 */
	public PrivateBusinessMessage getById(Integer id);
	/**
	 * 
	* @Title: getByUserId
	* @Description: 根据用户id查询
	* @return    PrivateBusinessMessage 返回类型
	* @throws
	 */
	public PrivateBusinessMessage getByUserId(Integer userId);
	/**
	 * 删除
	 * @param id 参数
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param personalMessage 参数
	 * @return
	 */
	public boolean save(PrivateBusinessMessage privateBusinessMessage) ;
	/**
	 *  添加
	 * @param personalMessage 参数
	 * @return
	 */
	public boolean add(PrivateBusinessMessage privateBusinessMessage) ;
	
}
