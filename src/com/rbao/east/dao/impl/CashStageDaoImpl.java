package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.CashStageDao;
import com.rbao.east.entity.CashStage;
@Repository
public class CashStageDaoImpl extends BaseDaoImpl<CashStage> implements CashStageDao{

	private static Logger logger = LoggerFactory.getLogger(CashStageDaoImpl.class);
	
}
