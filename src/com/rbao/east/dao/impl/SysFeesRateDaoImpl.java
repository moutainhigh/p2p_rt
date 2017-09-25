package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.SysFeesRateDao;
import com.rbao.east.entity.SysFeesRate;

@Repository
public class SysFeesRateDaoImpl extends BaseDaoImpl<SysFeesRate> implements SysFeesRateDao {

	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(SysFeesRateDaoImpl.class);
}
