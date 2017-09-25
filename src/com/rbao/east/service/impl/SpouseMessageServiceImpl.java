package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.SpouseMessageDao;
import com.rbao.east.entity.SpouseMessage;
import com.rbao.east.service.SpouseMessageService;

@Service
@Transactional
public class SpouseMessageServiceImpl implements SpouseMessageService {

	@Autowired
	private SpouseMessageDao spouseMessageDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return spouseMessageDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public SpouseMessage getById(Integer id) {
		return spouseMessageDao.selectByPrimaryKey(id);
	}
	
	@Override
	public SpouseMessage getByUserId(Integer userId) {
		return spouseMessageDao.selectEntity("selectByUserId", userId);
	}

	@Override
	public boolean deleteById(Integer id) {
		return spouseMessageDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(SpouseMessage spouseMessage) {
		return spouseMessageDao.updateByPrimaryKeySelective(spouseMessage); 
	}
	
	@Override
	public boolean add(SpouseMessage spouseMessage) {
		return spouseMessageDao.insertSelective(spouseMessage); 
	}
	
}
