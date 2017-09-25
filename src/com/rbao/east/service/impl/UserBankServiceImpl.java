package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.UserBankDao;
import com.rbao.east.entity.UserBank;
import com.rbao.east.service.UserBankService;

@Service
@Transactional
public class UserBankServiceImpl implements UserBankService {

	@Autowired
	private UserBankDao userBankDao;

	@Override
	public PageModel getUserBankList(Map<String,String> paramsMap) {
		return userBankDao.getPage("selectByEntity", "selectTotalCount",paramsMap);
	}

	@Override
	public boolean saveOrUpdate(UserBank userBank) {
		return userBankDao.saveOrUpdate(userBank);
	}

	@Override
	public UserBank getById(Integer id) {
		return userBankDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer userBankId) {
		return userBankDao.deleteByPrimaryKey(userBankId);
	}


	@Override
	public UserBank getByUserId(Map<String, String> paramsMap) {
		return this.userBankDao.selectEntity("selectByUserId",paramsMap );
	}
	
}
