package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.UnitMessageDao;
import com.rbao.east.entity.UnitMessage;
import com.rbao.east.service.UnitMessageService;

@Service
@Transactional
public class UnitMessageServiceImpl implements UnitMessageService {

	@Autowired
	private UnitMessageDao unitMessageDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return unitMessageDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public UnitMessage getById(Integer id) {
		return unitMessageDao.selectByPrimaryKey(id);
	}
	
	@Override
	public UnitMessage getByUserId(Integer userId) {
		return unitMessageDao.selectEntity("selectByUserId", userId);
	}

	@Override
	public boolean deleteById(Integer id) {
		return unitMessageDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(UnitMessage unitMessage) {
		return unitMessageDao.updateByPrimaryKeySelective(unitMessage); 
	}
	
	@Override
	public boolean add(UnitMessage unitMessage) {
		return unitMessageDao.insertSelective(unitMessage); 
	}
	
}
