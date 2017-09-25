package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.FinanceMessageDao;
import com.rbao.east.entity.FinanceMessage;

@Repository
public class FinaceMessageDaoImpl extends BaseDaoImpl<FinanceMessage> implements FinanceMessageDao{

	private static Logger logger = LoggerFactory.getLogger(FinaceMessageDaoImpl.class);
}
