package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.PrivateBusinessMessageDao;
import com.rbao.east.entity.PrivateBusinessMessage;
import com.rbao.east.service.PrivateBusinessMessageService;

@Service
@Transactional
public class PrivateBusinessMessageServiceImpl implements PrivateBusinessMessageService {

	@Autowired
	private PrivateBusinessMessageDao privateBusinessMessageDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return privateBusinessMessageDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public PrivateBusinessMessage getById(Integer id) {
		return privateBusinessMessageDao.selectByPrimaryKey(id);
	}
	
	@Override
	public PrivateBusinessMessage getByUserId(Integer userId) {
		return privateBusinessMessageDao.selectEntity("selectByUserId", userId);
	}

	@Override
	public boolean deleteById(Integer id) {
		return privateBusinessMessageDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(PrivateBusinessMessage privateBusinessMessage) {
		return privateBusinessMessageDao.updateByPrimaryKeySelective(privateBusinessMessage); 
	}
	
	@Override
	public boolean add(PrivateBusinessMessage privateBusinessMessage) {
		return privateBusinessMessageDao.insertSelective(privateBusinessMessage); 
	}
	
}
