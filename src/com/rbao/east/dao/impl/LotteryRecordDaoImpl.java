package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.LotteryRecordDao;
import com.rbao.east.entity.LotteryRecord;

@Repository
public class LotteryRecordDaoImpl extends BaseDaoImpl<LotteryRecord> implements LotteryRecordDao{

	private static Logger logger = LoggerFactory.getLogger(LotteryRecordDaoImpl.class);
}
