package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Channel;

/**
 * 栏目
 * */
public interface ChannelService {

public PageModel getList(Channel entity,Integer curPage);

/**
 * 
* @Title: getById
* @Description: 根据id查询
* @return    Channel返回类型
* @throws
 */
	public Channel getById(Integer id);

	/**
	 * 
	* @Title: deleteById
	* @Description: 根据id删除
	* @return   boolean返回类型 
	* @throws
	 */
	public boolean deleteById(Integer id);

	/**
	 * 
	* @Title: saveOrUpdate
	* @Description: 更新操作
	* @return    boolean
	* @throws
	 */
	public boolean saveOrUpdate(Channel channel) ;

	/**
	 * 
	* @Title: getTreeList
	* @Description:菜单
	* @return    List 返回类型
	* @throws
	 */
	public List getTreeList(Channel channel);

	/**
	 * 
	* @Title: getListByParentId
	* @Description: 分页
	* @return   PageModel返回类型 
	* @throws
	 */
	public PageModel getListByParentId(Map<String,String> paramsMap);

	/**
	 * 
	* @Title: getByChannelCode
	* @Description: 栏目code
	* @return    Integer
	* @throws
	 */
	public Integer getByChannelCode(String channelId, String channelCode);

	/**
	 * 前台页面
	 * @param channel
	 * @return
	 */
		public List<Channel> getListByDisplay(Channel channel);
/**
 * 前台foot栏目
 * @param channelId
 * @return
 */
	public List<Channel> getByParentId(Map<String,String> param);
/**
 * 前台 栏目无内容显示自身内容
 * @param channelId
 * @return
 */
public PageModel getByIdPage(Map<String,String> param);
/**
 * 根据id查code
 * @param channelCode
 * @return
 */
public List<Channel> getIdByChannelCode(String channelCode);
/**
 * 根据code查内容
 * @param param
 * @return
 */
public List<Map<String, Object>> getContentByChannelCode(Map<String, Object> param);
/**
 * 根据code查内容
 * @param param
 * @return
 */
public List<Map<String, Object>> getContentByChannelCodes(Map<String, Object> param);
/**
 * 分页
 * @param param
 * @return
 */
public PageModel getContentPageByChannelId(Map<String, String> param);

/**
 * 栏目code
 * @param param
 * @return
 */
Channel selectChannelByCode(String code);
	
	/**
	 * 查询channel下的文章列表(不分页)
	 * @return
	 */
	public List<Map<String,Object>> getContentByChannelId(Map param);
	
	/**
	 * 热门专题
	 * @param param
	 * @return
	 */
	public List<Channel> getHot(Map<String,String> param);
}
