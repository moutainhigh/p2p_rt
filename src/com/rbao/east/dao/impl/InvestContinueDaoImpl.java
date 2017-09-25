package com.rbao.east.dao.impl;

import org.springframework.stereotype.Repository;

import com.rbao.east.dao.InvestContinueDao;
import com.rbao.east.entity.InvestContinue;

@Repository
public class InvestContinueDaoImpl extends BaseDaoImpl<InvestContinue> implements
		InvestContinueDao {

	@Override
	public InvestContinue getByUserId(Integer userId) {
		return selectEntity("selectByUserId", userId);
	}


}
