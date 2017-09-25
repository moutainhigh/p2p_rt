package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.RelationMessageDao;
import com.rbao.east.entity.RelationMessage;
import com.rbao.east.service.RelationMessageService;

@Service
@Transactional
public class RelationMessageServiceImpl implements RelationMessageService {

	@Autowired
	private RelationMessageDao relationMessageDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return relationMessageDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public RelationMessage getById(Integer id) {
		return relationMessageDao.selectByPrimaryKey(id);
	}
	
	@Override
	public RelationMessage getByUserId(Integer userId) {
		return relationMessageDao.selectEntity("selectByUserId", userId);
	}

	@Override
	public boolean deleteById(Integer id) {
		return relationMessageDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(RelationMessage relationMessage) {
		return relationMessageDao.updateByPrimaryKeySelective(relationMessage); 
	}
	
	@Override
	public boolean add(RelationMessage relationMessage) {
		return relationMessageDao.insertSelective(relationMessage); 
	}
	
}
