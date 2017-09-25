package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.RecommendDao;
import com.rbao.east.entity.Recommend;

@Repository
public class RecommendDaoImpl extends BaseDaoImpl<Recommend> implements RecommendDao{
	private static Logger logger = LoggerFactory.getLogger(RecommendDaoImpl.class);
}
