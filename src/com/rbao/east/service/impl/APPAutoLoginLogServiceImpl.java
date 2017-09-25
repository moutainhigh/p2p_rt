package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.APPAutoLoginLogDao;
import com.rbao.east.entity.APPAutologinLog;
import com.rbao.east.service.APPAutoLoginLogService;
@Service
@Transactional
public class APPAutoLoginLogServiceImpl implements APPAutoLoginLogService {

	@Autowired
	private APPAutoLoginLogDao logDao;

	@Override
	public APPAutologinLog getByParam(Map<String, String> params) {
		
		return this.logDao.selectEntity("selectByParams", params);
	}

	@Override
	public void saveOrUpdate(APPAutologinLog log) {
		
		this.logDao.saveOrUpdate(log);
		
	}
	
	

	@Override
	public void deleteByUserId(Integer userId) {
		APPAutologinLog log = new APPAutologinLog();
		log.setUserId(userId);
		this.logDao.delete("deleteByEntity", log);
	}

	@Override
	public void deleteByTokenId(String token) {
		APPAutologinLog log = new APPAutologinLog();
		log.setToken(token);
		this.logDao.delete("deleteByEntityByToken", log);
	}
	
	@Override
	public boolean add(APPAutologinLog log) {
		// TODO Auto-generated method stub
		return logDao.insertSelective(log); 
	}

	@Override
	public boolean updateByUser(APPAutologinLog log) {
		return this.logDao.updateByUserId(log);
	}
}
