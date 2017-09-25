package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.SysRoleModuleDao;
import com.rbao.east.service.SysRoleModuleService;


@Service
@Transactional
public class SysRoleModuleServiceImpl implements SysRoleModuleService{

	@Autowired
	private SysRoleModuleDao roleModuleDao;
	
	
	@Override
	
	public boolean save(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return roleModuleDao.insertObj("insertSysRoleModule", param);
	}


	@Override
	
	public boolean deleteByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return roleModuleDao.deleteByPrimaryKey(id);
	}

}
