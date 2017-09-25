package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.SysRole;
import com.rbao.east.entity.TreeModel;

	
public interface SysRoleService {

	/**
	 * 
	* @Title: getSysRoleIdByUserId
	* @Description: 根据用户id查询角色信息
	* @return    List<SysRole> 返回类型
	* @throws
	 */
	public List<SysRole> getSysRoleIdByUserId(Map<String, Object> param);
	/**
	 * 分页
	 * @param param
	 * @param curPage
	 * @return
	 */
	public PageModel getSysRolePage(Map<String, String> param,Integer curPage) ;
	/**
	 * 
	* @Title: getSysRoleTreeByUserId
	* @Description: 根据用户id查询角色所持操作
	* @return    List<TreeModel> 返回类型
	* @throws
	 */
	public List<TreeModel> getSysRoleTreeByUserId(int userId);
	/**
	 * 保存角色信息
	 * @param param
	 * @return
	 */
	public boolean saveSysRole(Map<String, Object> param);
	/**
	 * 
	* @Title: selectSysRoleById
	* @Description: 根据id查询角色
	* @return    SysRole
	* @throws
	 */
	public SysRole selectSysRoleById(int id);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public boolean updateSysRole(SysRole role);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteSysRoleById(int id);
	/**
	 * 添加
	 * @param param
	 * @return
	 */
	public boolean addRoleModule(Map<String, Object> param);
	/**
	 * 
	* @Title: showUserListByRoleId
	* @Description: 根据角色id显示用户信息
	* @return   List<TreeModel> 返回类型 
	* @throws
	 */
	public List<TreeModel> showUserListByRoleId(Map<String, Object> param);
}
