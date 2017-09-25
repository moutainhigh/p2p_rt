package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Area;
/**
 * 地区
 * */
@SuppressWarnings("rawtypes")
public interface AreaService {

	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @Description: id 删除记录
	* @return boolean    返回类型
	* @throws
	 */
	boolean deleteByPrimaryKey(Integer id);

	/**
	 * 
	* @Title: insert
	* @Description: 插入 数据
	* @return boolean    返回类型
	* @throws
	 */
	boolean insert(Area record);

	/**
	 * 
	* @Title: insertSelective
	* @Description: 插入选择字段
	* @return boolean    返回类型
	* @throws
	 */
	boolean insertSelective(Area record);

	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description: id 得到实体
	* @return Area    返回类型
	* @throws
	 */
	Area selectByPrimaryKey(Integer id);

	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @Description: 更新记录
	* @return boolean    返回类型
	* @throws
	 */
	boolean updateByPrimaryKeySelective(Area record);

	/**
	 * 
	* @Title: updateByPrimaryKey
	* @Description: 主键 更新记录
	* @return boolean    返回类型
	* @throws
	 */
	boolean updateByPrimaryKey(Area record);
	/**
	 * 
	* @Title: getPageAreas
	* @Description: 数据列表
	* @return PageModel    返回类型
	* @throws
	 */
	PageModel getPageAreas(Map<String, String> map);
	/**
	 * 
	* @Title: saveOrUpdate
	* @Description: 更新数据
	* @return void    返回类型
	* @throws
	 */
	public void saveOrUpdate(Area area);
	/**
	 * 
	* @Title: getTreeList
	* @Description: 得到树列表
	* @return List    返回类型
	* @throws
	 */
	List getTreeList();
	
	/**
	 * 根据父菜单id获取子菜单
	 */
	List<Area> selectByParentId(Integer parentId);
	/**
	 * 根据条件查询
	 */
	List<Area> selectByCon(Map<String, String> map);
	/**
	 * 根据条件获取记录数
	 */
	int getCountByCon(Map<String, String> map);
	/**
	 * 根据父菜单id获取记录数
	 */
	int getCountByParentId(Integer parentId);
	/**
	 * 
	* @Title: checkAreaExist
	* @Description: 得到地区 数量
	* @return int    返回类型
	* @throws
	 */
	public int checkAreaExist(Map<String, String> maps);

}
