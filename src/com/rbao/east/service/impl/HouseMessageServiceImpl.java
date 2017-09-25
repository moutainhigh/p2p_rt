package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.HouseMessageDao;
import com.rbao.east.entity.HouseMessage;
import com.rbao.east.service.HouseMessageService;

@Service
@Transactional
public class HouseMessageServiceImpl implements HouseMessageService {

	@Autowired
	private HouseMessageDao houseMessageDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return houseMessageDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public HouseMessage getById(Integer id) {
		return houseMessageDao.selectByPrimaryKey(id);
	}
	
	@Override
	public HouseMessage getByUserId(Integer userId) {
		return houseMessageDao.selectEntity("selectByUserId", userId);
	}

	@Override
	public boolean deleteById(Integer id) {
		return houseMessageDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(HouseMessage houseMessage) {
		return houseMessageDao.updateByPrimaryKeySelective(houseMessage); 
	}
	
	@Override
	public boolean add(HouseMessage houseMessage) {
		return houseMessageDao.insertSelective(houseMessage); 
	}
	
}
