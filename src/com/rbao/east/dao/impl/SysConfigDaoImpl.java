package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.SysConfigDao;
import com.rbao.east.entity.SysConfig;
@Repository
public class SysConfigDaoImpl extends BaseDaoImpl<SysConfig> implements SysConfigDao{
	
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(SysConfigDaoImpl.class);
}
