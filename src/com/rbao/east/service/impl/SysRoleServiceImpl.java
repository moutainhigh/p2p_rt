package com.rbao.east.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.SysRoleDao;
import com.rbao.east.dao.SysRoleModuleDao;
import com.rbao.east.dao.SysUserRoleDao;
import com.rbao.east.dao.TreeModelDao;
import com.rbao.east.entity.SysRole;
import com.rbao.east.entity.TreeModel;
import com.rbao.east.service.SysRoleService;
import com.rbao.east.utils.PropertiesUtil;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService{

	
	@Autowired
	private SysRoleDao roleDao;
	
	@Autowired
	private TreeModelDao treeModelDao;
	
	@Autowired
	private SysUserRoleDao userRoleDao;
	
	@Autowired
	private SysRoleModuleDao roleModuleDao;
	
	@Override
	
	public List<SysRole> getSysRoleIdByUserId(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return roleDao.select("getSysRoleIdByUserId", param);
	}

	@Override
	
	public PageModel getSysRolePage(Map<String, String> param,
			Integer curPage) {
		
		PageModel pageModel=new  PageModel(curPage);  //设置当前页
		if(param.containsKey("numPerPage")){
			pageModel.setPageSize(Integer.parseInt(param.get("numPerPage").toString()));
		}
		return roleDao.getPage("getSysRolePage","getSysRolePageCount", param, pageModel);
	}

	@Override
	
	public List<TreeModel> getSysRoleTreeByUserId(int userId) {
		int beginUserId = 0;
		beginUserId = Integer.parseInt(PropertiesUtil.get("SYSTEM.USER.BEGIN.ID"));
		if(userId >= beginUserId){
			return treeModelDao.select("getSysRoleTreeByUserId", userId);
		}else{
			return treeModelDao.select("getAllSysRoleTree", userId);
		}
		
	}

	@Override
	
	public boolean saveSysRole(Map<String, Object> param) {
		boolean bool=false;
		SysRole role=(SysRole) param.get("role");
		boolean isbool=roleDao.insert("insert", role);
		if(isbool){
			param.put("roleId", role.getId());
			List<String> roleIds=roleDao.selects("showPerRoleListByRoleId", param);
			param.put("roleIds", roleIds);
			List<String> userIds=roleDao.selects("getUserIdsByRoleIds", param);
			userIds.add("1");
			param.put("userIds", userIds);
			bool=roleDao.insertObj("insertUserRole", param);
			if(!bool){
				throw new RuntimeException("添加角色关联失败");
			}
		}else{
			throw new RuntimeException("添加角色失败");
		}
		return bool;
	}

	@Override
	
	public SysRole selectSysRoleById(int id) {
		// TODO Auto-generated method stub
		return roleDao.selectByPrimaryKey(id);
	}

	@Override
	
	public boolean updateSysRole(SysRole role) {
		// TODO Auto-generated method stub
		return roleDao.updateByPrimaryKeySelective(role);
	}

	@Override
	
	public boolean deleteSysRoleById(int id) {
		
		Map<String, Object> param=new HashMap<String, Object>();
		boolean isbool= roleDao.deleteByPrimaryKey(id);
		if(isbool){
			//删除用户角色关联
			userRoleDao.deleteByPrimaryKey(id);
			//删除角色模块关联
			param.put("role_id", id);
			roleDao.deletes("deleteRoleModule",param);
		}
		return isbool;
	}


	@Override
	
	public boolean addRoleModule(Map<String, Object> param) {
		boolean bool=true;
		roleDao.deletes("deleteRoleModule",param);
		String[] rightIds=(String[]) param.get("rightIds");
		if(null!=rightIds&&rightIds.length>0){
			List<String> moduleIds=roleDao.selects("getModuleIdList", param);
			if(moduleIds.size()>0){
				param.put("moduleIds",moduleIds);
				roleModuleDao.insertObj("insertModuleRole", param);
			}
		}
		return bool;
	}

	@Override
	
	public List<TreeModel> showUserListByRoleId(Map<String, Object> param) {
		List<TreeModel> treeList=null;
		param.put("userBeginId",PropertiesUtil.get("SYSTEM.USER.BEGIN.ID"));
		List<String> roleChildIds=roleDao.selects("showChildRoleListByRoleId", param);
		param.put("roleChildIds", roleChildIds);
		treeList =treeModelDao.select("showUserListByRoleId", param);
		return treeList;
	}

	

}
