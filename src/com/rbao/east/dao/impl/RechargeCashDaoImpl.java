package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.RechargeCashDao;
import com.rbao.east.entity.RechargeCash;
@Repository
public class RechargeCashDaoImpl extends BaseDaoImpl<RechargeCash> implements RechargeCashDao{
	private static Logger logger = LoggerFactory.getLogger(RechargeCashDaoImpl.class);

}
