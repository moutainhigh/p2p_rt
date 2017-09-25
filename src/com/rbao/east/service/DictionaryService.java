package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Dictionary;

/**
 * 字典
 * */
public interface DictionaryService {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @Description:删除 
	* @return    boolean返回类型
	* @throws
	 */
	boolean deleteByPrimaryKey(Integer id);
	/**
	 * 
	* @Title: insert
	* @Description: 增加
	* @return    boolean返回类型
	* @throws
	 */
	boolean insert(Dictionary record);
	/**
	 * 
	* @Title: insert
	* @Description: 增加
	* @return    boolean返回类型
	* @throws
	 */
	boolean insertSelective(Dictionary record);
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description: 查询信息
	* @return    Dictionary返回类型
	* @throws
	 */
	Dictionary selectByPrimaryKey(Integer id);
	/**
	 * 
	* @Title: updateByPrimaryKey
	* @Description: 更新
	* @return   boolean返回类型
	* @throws
	 */
	boolean updateByPrimaryKeySelective(Dictionary record);
	/**
	 * 
	* @Title: updateByPrimaryKey
	* @Description: 更新
	* @return   boolean返回类型
	* @throws
	 */
	boolean updateByPrimaryKey(Dictionary record);

	/**
	 * 根据父菜单id获取子菜单
	 */
	public List<Dictionary> selectByParentId(Map<String, String> params);

	/**
	 * 根据条件查询
	 */
	public List<Dictionary> selectByCon(Map<String, String> params);

	/**
	 * 根据父菜单id获取记录数
	 */
	int getCountByParentId(Map<String, String> params);
	/**
	 * 
	* @Title: checkCodeExist
	* @Description: 检查是否存在
	* @return    boolean返回类型
	* @throws
	 */
	public boolean checkCodeExist(Map<String, String> params);
	/**
	 * 
	* @Title: getPageDic
	* @Description: 分页
	* @return PageModel返回类型   
	* @throws
	 */
	PageModel getPageDic(Map<String, String> params);

	public void saveOrUpdate(Dictionary dictionary);
	
	@SuppressWarnings("rawtypes")
	public List getTreeList();
	
	/**
	 * 根据字典代码查询字典
	 */
	Dictionary getDicByCode(String dicCode);
}
