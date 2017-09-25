package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Nation;

/**
 * 民族信息
 * */	
public interface NationService {
	/**
	 * 
	* @Title: getNationList
	* @Description: 得到数据集
	* @return    	List<Nation>返回类型
	* @throws
	 */
	List<Nation> getNationList(Map<String, String> maps);
	/**
	 * 
	* @Title: 
	* @Description: 
	* @return    
	* @throws
	 */
	int getCount(Map<String, String> maps);
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description: 查询信息
	* @return    Nation返回类型
	* @throws
	 */
	Nation selectByPrimaryKey(Integer id);
	/**
	 * 分页
	 * @param maps 参数
	 * @return
	 */
	PageModel getNationPage(Map<String, String> maps);
	/**
	 * 更新操作
	 * @param nation 参数
	 */
	public void saveOrUpdate(Nation nation);
	/**
	 * 删除
	 * @param id 参数
	 * @return
	 */
	boolean deleteByPrimaryKey(Integer id);
	/**
	 * 
	* @Title: checkExist
	* @Description: 检查是否存在
	* @return    int 返回类型
	* @throws
	 */
	int checkExist(Map<String, String> maps);

}
