package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.EducationMessageDao;
import com.rbao.east.entity.EducationMessage;
import com.rbao.east.service.EducationMessageService;

@Service
@Transactional
public class EducationMessageServiceImpl implements EducationMessageService {

	@Autowired
	private EducationMessageDao educationMessageDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return educationMessageDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public EducationMessage getById(Integer id) {
		return educationMessageDao.selectByPrimaryKey(id);
	}
	
	@Override
	public EducationMessage getByUserId(Integer userId) {
		return educationMessageDao.selectEntity("selectEduByUserId", userId);
	}

	@Override
	public boolean deleteById(Integer id) {
		return educationMessageDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(EducationMessage educationMessage) {
		return educationMessageDao.updateByPrimaryKeySelective(educationMessage); 
	}
	
	@Override
	public boolean add(EducationMessage educationMessage) {
		return educationMessageDao.insertSelective(educationMessage); 
	}
	
}
