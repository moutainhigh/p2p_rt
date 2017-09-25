package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.OtherMessage;

/**
 * 其他信息
 * */
public interface OtherMessageService {
	/**
	 * 分页
	 * @param paramsMap参数
	 * @return
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return    OtherMessage 返回类型
	* @throws
	 */
	public OtherMessage getById(Integer id);
	/**
	 * 
	* @Title: getByUserId
	* @Description: 根据用户id查询
	* @return    OtherMessage返回类型
	* @throws
	 */
	public OtherMessage getByUserId(Integer userId);
	/**
	 * 删除
	 * @param id 参数
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param otherMessage 参数
	 * @return
	 */
	public boolean save(OtherMessage otherMessage) ;
	/**
	 * 添加
	 * @param otherMessage参数
	 * @return
	 */
	public boolean add(OtherMessage otherMessage) ;
	
}
