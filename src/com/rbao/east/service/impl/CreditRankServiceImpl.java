package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.CreditRankDao;
import com.rbao.east.entity.CreditRank;
import com.rbao.east.service.CreditRankService;

@Service
@Transactional
public class CreditRankServiceImpl implements CreditRankService {

	@Autowired
	private CreditRankDao creditRankDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		
		return creditRankDao.getPage("selectByEntity","selectTotalCount",paramsMap);
		
	}

	@Override
	public CreditRank getById(Integer id) {
		return creditRankDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer id) {
		return creditRankDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(CreditRank creditRank) {
		return creditRankDao.updateByPrimaryKeySelective(creditRank); 
	}
	
	@Override
	public boolean add(CreditRank creditRank) {
		return creditRankDao.insertSelective(creditRank); 
	}
	
}
