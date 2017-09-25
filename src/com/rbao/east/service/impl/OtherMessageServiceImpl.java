package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.OtherMessageDao;
import com.rbao.east.entity.OtherMessage;
import com.rbao.east.service.OtherMessageService;

@Service
@Transactional
public class OtherMessageServiceImpl implements OtherMessageService {

	@Autowired
	private OtherMessageDao otherMessageDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return otherMessageDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public OtherMessage getById(Integer id) {
		return otherMessageDao.selectByPrimaryKey(id);
	}
	
	@Override
	public OtherMessage getByUserId(Integer userId) {
		return otherMessageDao.selectEntity("selectByUserId", userId);
	}

	@Override
	public boolean deleteById(Integer id) {
		return otherMessageDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(OtherMessage otherMessage) {
		return otherMessageDao.updateByPrimaryKeySelective(otherMessage); 
	}
	
	@Override
	public boolean add(OtherMessage otherMessage) {
		return otherMessageDao.insertSelective(otherMessage); 
	}
	
}
