package com.rbao.east.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.UserTypeDao;
import com.rbao.east.entity.UserType;
import com.rbao.east.service.UserTypeService;
/**
 * 
 * @author zhushuai
 *
 */
@Service
@Transactional
public class UserTypeServiceImpl implements UserTypeService {

	@Autowired
	private UserTypeDao userTypeDao;
	
	/**@author
	 * 分页查询
	 */

	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		
		return userTypeDao.getPage("selectByEntity","selectTotalCount",paramsMap);
		
	}

	@Override
	public UserType getById(Integer id) {
		return userTypeDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer id) {
		return userTypeDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(UserType userType) {
		return userTypeDao.updateByPrimaryKeySelective(userType); 
	}
	
	@Override
	public boolean add(UserType userType) {
		return userTypeDao.insertSelective(userType); 
	}

	@Override
	public List<UserType> getUserTypeList() {
		// TODO Auto-generated method stub
		return userTypeDao.selectAll();
	}

	@Override
	public List<UserType> selectByType(Integer type) {
		return this.userTypeDao.select("selectByType", type);
	}

	@Override
	public UserType selectByName(String name) {
		return this.userTypeDao.selectEntity("selectByName", name);
	}

}
