package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.SysSmtpConfigDao;
import com.rbao.east.entity.SysSmtpConfig;
@Repository
public class SysSmtpConfigDaoImpl extends BaseDaoImpl<SysSmtpConfig> implements SysSmtpConfigDao {

	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(SysSmtpConfigDaoImpl.class);
}
