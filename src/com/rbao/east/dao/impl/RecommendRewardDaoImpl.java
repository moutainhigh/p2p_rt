package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.RecommendRewardDao;
import com.rbao.east.entity.RecommendReward;

@Repository
public class RecommendRewardDaoImpl extends BaseDaoImpl<RecommendReward> implements
		RecommendRewardDao {
	private static Logger logger = LoggerFactory.getLogger(RecommendRewardDaoImpl.class);
	
}
