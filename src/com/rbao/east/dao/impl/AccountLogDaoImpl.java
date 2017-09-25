package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.AccountLogDao;
import com.rbao.east.entity.AccountLog;

@Repository
public class AccountLogDaoImpl extends BaseDaoImpl<AccountLog> implements AccountLogDao {
	private static Logger logger = LoggerFactory.getLogger(AccountLogDaoImpl.class);

}
