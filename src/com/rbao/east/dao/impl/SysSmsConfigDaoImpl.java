package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.SysSmsConfigDao;
import com.rbao.east.entity.SysSmsConfig;

@Repository
public class SysSmsConfigDaoImpl extends BaseDaoImpl<SysSmsConfig> implements SysSmsConfigDao{
	private static Logger logger = LoggerFactory.getLogger(SysSmsConfigDaoImpl.class);
}
