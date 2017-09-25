package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.PersonalMessageDao;
import com.rbao.east.entity.PersonalMessage;
import com.rbao.east.service.PersonalMessageService;

@Service
@Transactional
public class PersonalMessageServiceImpl implements PersonalMessageService {

	@Autowired
	private PersonalMessageDao personalMessageDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return personalMessageDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public PersonalMessage getById(Integer id) {
		return personalMessageDao.selectByPrimaryKey(id);
	}
	
	@Override
	public PersonalMessage getByUserId(Integer userId) {
		return personalMessageDao.selectEntity("selectByUserId", userId);
	}

	@Override
	public boolean deleteById(Integer id) {
		return personalMessageDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(PersonalMessage personalMessage) {
		return personalMessageDao.updateByPrimaryKeySelective(personalMessage); 
	}
	
	@Override
	public boolean add(PersonalMessage personalMessage) {
		return personalMessageDao.insertSelective(personalMessage); 
	}
	
}
