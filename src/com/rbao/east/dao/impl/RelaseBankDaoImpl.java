package com.rbao.east.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.RelaseBankDao;
import com.rbao.east.entity.RelaseCard;
@Repository
public class RelaseBankDaoImpl extends BaseDaoImpl<RelaseCard> implements RelaseBankDao {
	private static Logger logger = LoggerFactory.getLogger(UserBankDaoImpl.class);

	@Override
	public List<RelaseCard> selectRelaseBankList(Map<String,String> map) {
		return super.selects("selectRelaseBankList", map);
	}

}
