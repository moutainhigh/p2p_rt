package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.AccountStatisticsDao;
import com.rbao.east.entity.AccountStatistics;


@Repository
public class AccountStatisticsDaoImpl extends BaseDaoImpl<AccountStatistics> implements AccountStatisticsDao{
	private static Logger logger = LoggerFactory.getLogger(AccountStatisticsDaoImpl.class);
}
