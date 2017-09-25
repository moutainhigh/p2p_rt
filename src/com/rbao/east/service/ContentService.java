package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Content;

/**
 * 内容
 * */
public interface ContentService {
	/**
	 * 
	* @Title: saveOrUpdate
	* @Description: 更新操作	
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean saveOrUpdate(Content content, String[] attachTitle, String[] attachPath,
			String[] attachRealTitle, String[] attachFileType,
			Integer[] attachSequence) throws Exception;
	/**
	 * 
	* @Title: getListById
	* @Description: 分页
	* @return    PageModel返回类型
	* @throws
	 */
	public PageModel getListById(Map<String, String> paramsMap);
	/**
	 * 
	* @Title: deleteById
	* @Description: 根据id删除	
	* @return    boolean返回类型
	* @throws
	 */
	public boolean deleteById(Integer id);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询	
	* @return    Content 返回类型
	* @throws
	 */
	public Content getById(Integer contentId,Integer channelId);
	/**
	 * 
	* @Title: getListBychannelIds
	* @Description: 得到数据集
	* @return    List返回类型
	* @throws
	 */
	public List getListBychannelIds(Map<String, String> param);
	/**
	 * 
	* @Title: getListByChannelId
	* @Description: 根据channelid查询
	* @return  List<Content>返回类型   
	* @throws
	 */
	public List<Content> getListByChannelId(Content content);
	/**
	 * 
	* @Title: showContentByPage
	* @Description: 分页	
	* @return    PageModel返回类型
	* @throws
	 */
	public PageModel showContentByPage(Map<String, String> param);
	/**
	 * 
	* @Title: getContentTitle
	* @Description:内容标题 
	* @return    List<Content> 返回类型
	* @throws
	 */
	public List<Content> getContentTitle(Map<String, String> param);
	/**
	 * 
	* @Title: getContentPicFri
	* @Description: 内容
	* @return    List<Content>返回类型
	* @throws
	 */
	public List<Content> getContentPicFri(String id);
	/**
	 * 
	* @Title: updateClick
	* @Description: 更新操作
	* @return   void 返回类型 
	* @throws
	 */
	public void updateClick(Content content);
	/**
	 * 
	* @Title: updateByPrimaryKeyForNumber
	* @Description: 更新操作
	* @return  boolean 返回类型  
	* @throws
	 */
	public boolean updateByPrimaryKeyForNumber(Integer id);



}
