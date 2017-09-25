package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.DiscussionConfig;

/**
 * 问答配置
 * */	
public interface DiscussionConfigService {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @Description: 删除	
	* @return    boolean返回类型
	* @throws
	 */
	boolean deleteByPrimaryKey(Integer id);
	/**
	 * 
	* @Title: saveOrUpdate
	* @Description: 更新操作
	* @return    boolean返回类型
	* @throws
	 */
	boolean saveOrUpdate(DiscussionConfig record);
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description: 根据主键查询信息
	* @return   DiscussionConfig返回类型 
	* @throws
	 */
	DiscussionConfig selectByPrimaryKey(Integer id);
	/**
	 * 
	* @Title: getPage
	* @Description: 分页
	* @return    
	* @throws
	 */
	PageModel getPage(Map<String, String> params);
	/**
	 * 
	* @Title: getConfigsCount
	* @Description: 得到论坛数量
	* @return    Integer返回类型
	* @throws
	 */
	Integer getConfigsCount(Map<String, String> params);
	/**
	 * 
	* @Title: getValue
	* @Description: 值
	* @return   DiscussionConfig返回类型 
	* @throws
	 */
	DiscussionConfig getValue(String disKey);
}
