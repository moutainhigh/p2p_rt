package com.rbao.east.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.UserEvaluateDao;
import com.rbao.east.entity.UserEvaluate;
import com.rbao.east.service.UserEvaluateService;

@Service
@Transactional
public class UserEvaluateServiceImpl implements UserEvaluateService{
	
	private static Logger logger = LoggerFactory.getLogger(UserEvaluateServiceImpl.class);
	
	@Autowired
	private UserEvaluateDao userEvaluateDao;

	@Override
	public UserEvaluate getUserEvaluateByUserId(Integer userId) {
		return userEvaluateDao.selectEntity("selectUserEvaluateByUserId", userId);
	}

	@Override
	public boolean save(UserEvaluate userEvaluate) {
		return userEvaluateDao.saveOrUpdate(userEvaluate);
	}

	@Override
	public boolean deleteByUserId(Integer userId) {
		return userEvaluateDao.deletes("deleteByUserId", userId);
	}
}
