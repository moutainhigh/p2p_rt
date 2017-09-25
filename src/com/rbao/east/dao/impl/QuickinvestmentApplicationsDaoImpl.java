package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.QuickinvestmentApplicationsDao;
import com.rbao.east.entity.QuickinvestmentApplications;

@Repository
public class QuickinvestmentApplicationsDaoImpl extends BaseDaoImpl<QuickinvestmentApplications> implements QuickinvestmentApplicationsDao{
	private static Logger logger = LoggerFactory.getLogger(QuickinvestmentApplicationsDaoImpl.class);
}
