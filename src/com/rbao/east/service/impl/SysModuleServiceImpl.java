package com.rbao.east.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.SysModuleDao;
import com.rbao.east.dao.TreeModelDao;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.SysRole;
import com.rbao.east.entity.TreeModel;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.SysRoleModuleService;
import com.rbao.east.service.SysRoleService;

@Service
@Transactional
public class SysModuleServiceImpl implements SysModuleService{

	@Autowired
	private SysModuleDao moduleDao;
	
	
	@Autowired
	private SysRoleModuleService roleModuleService;
	
	@Autowired
	private SysRoleService roleService;
	
	@Autowired
	private TreeModelDao treeModelDao;
	
	@Override
	
	public List<SysModule> getMenuModuleList(int userId) {
		// TODO Auto-generated method stub
		return moduleDao.select("getMenuList", userId);
	}

	@Override
	
	public List<SysModule> getRightGroupList(Map<String, String> param) {
		// TODO Auto-generated method stub
		return moduleDao.select("getRightGroupList", param);
	}

	@Override
	
	public List<TreeModel> getTreeModelByUserId(int userId) {
		// TODO Auto-generated method stub
		return treeModelDao.select("getTreeModelByUserId", userId);
	}

	@Override
	
	public PageModel getTreeModelListByRightId(Map<String, String> param,
			Integer curPage) {
		
		PageModel pageModel=new  PageModel(curPage);  //设置当前页
		if(param.containsKey("numPerPage")){
			pageModel.setPageSize(Integer.parseInt(param.get("numPerPage").toString()));
		}
		
		return treeModelDao.getPage("getTreeModelListByRightId","getTreeModelListCountByRightId", param, pageModel);
	}

	@Override
	
	public boolean insert(Map<String, Object> param) throws DataAccessException  {
		boolean isbool=false;
		SysModule module=(SysModule)param.get("module");
	    boolean bool=moduleDao.insert("insert",module);
	    if(bool){
	    	List<SysRole> list=roleService.getSysRoleIdByUserId(param);
	    	if(list.size()>0){
	    		param.put("list", list);
	    		if(module.getId()!=0){
	    			param.put("moduleId", module.getId());
	    			isbool=roleModuleService.save(param);
	    		}else{
	    			throw new RuntimeException("添加用户关联角色错误！！！");
	    		}
	    	}else{
	    		throw new RuntimeException("用户角色为空！！！");
	    	}
	    }
		return isbool;
	}

	@Override
	
	public boolean update(SysModule module) {
		// TODO Auto-generated method stub
		return moduleDao.update("updateByPrimaryKeySelective",module);
	}

	@Override
	
	public SysModule selectByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return moduleDao.selectByPrimaryKey(id);
	}

	@Override
	
	public boolean deleteByPrimaryKey(int id) {
		boolean bool=false;
		bool=moduleDao.deleteByPrimaryKey(id);
		if(bool==true){
			bool=roleModuleService.deleteByPrimaryKey(id);
			if(bool==false){
				throw new RuntimeException("删除权限关联错误！！！");
			}
		}
		return bool;
	}

	@Override
	
	public List<TreeModel> getTreeModelByRoleId(int roleId) {
		// TODO Auto-generated method stub
		return treeModelDao.select("getTreeModelByRoleId", roleId);
	}



	

}
