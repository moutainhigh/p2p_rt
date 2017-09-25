package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.FinanceMessageDao;
import com.rbao.east.entity.FinanceMessage;
import com.rbao.east.service.FinanceMessageService;

@Service
@Transactional
public class FinanceMessageServiceImpl implements FinanceMessageService {

	@Autowired
	private FinanceMessageDao financeMessageDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return financeMessageDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public FinanceMessage getById(Integer id) {
		return financeMessageDao.selectByPrimaryKey(id);
	}
	
	@Override
	public FinanceMessage getByUserId(Integer userId) {
		return financeMessageDao.selectEntity("selectByUserId", userId);
	}

	@Override
	public boolean deleteById(Integer id) {
		return financeMessageDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(FinanceMessage financeMessage) {
		return financeMessageDao.updateByPrimaryKeySelective(financeMessage); 
	}
	
	@Override
	public boolean add(FinanceMessage financeMessage) {
		return financeMessageDao.insertSelective(financeMessage); 
	}
	
}
