package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.TreeModel;


public interface SysModuleService {

	/**
	 * 
	* @Title:getMenuModuleList 
	* @Description: 菜单集合
	* @return   List<SysModule> 返回类型  
	* @throws
	 */
	public List<SysModule> getMenuModuleList(int userId);
	/**
	 * 
	* @Title: getRightGroupList
	* @Description: 菜单
	* @return    List<SysModule> 返回类型
	* @throws
	 */
	public List<SysModule> getRightGroupList(Map<String, String> param);
	/**
	 * 
	* @Title: getTreeModelByUserId
	* @Description: 根据用户id查询菜单
	* @return  List<TreeModel>返回类型  
	* @throws
	 */
	public List<TreeModel> getTreeModelByUserId(int userId);
	/**
	 * 分页
	 * @param param
	 * @param curPage
	 * @return
	 */
	public PageModel getTreeModelListByRightId(Map<String, String> param,Integer curPage) ;
	/**
	 * 添加
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	public boolean insert(Map<String, Object> param) throws DataAccessException ; 
	/**
	 * 更新
	 * @param module
	 * @return
	 */
	public boolean update(SysModule module);
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description: 主键查询
	* @return    SysModule 返回类型
	* @throws
	 */
	public SysModule selectByPrimaryKey(int id);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteByPrimaryKey(int id);
	/**
	 * 
	* @Title: getTreeModelByRoleId
	* @Description: 角色id查询菜单
	* @return    List<TreeModel> 返回类型
	* @throws
	 */
	public List<TreeModel> getTreeModelByRoleId(int roleId);
	
}
