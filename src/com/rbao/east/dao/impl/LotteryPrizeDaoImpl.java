package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.LotteryPrizeDao;
import com.rbao.east.entity.LotteryPrize;

@Repository
public class LotteryPrizeDaoImpl extends BaseDaoImpl<LotteryPrize> implements LotteryPrizeDao{

	private static Logger logger = LoggerFactory.getLogger(LotteryPrizeDaoImpl.class);
}
