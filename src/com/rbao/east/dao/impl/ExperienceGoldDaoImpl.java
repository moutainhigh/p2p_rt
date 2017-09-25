package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.ExperienceGoldDao;
import com.rbao.east.entity.ExperienceGold;

/**
 * 
 */
@Repository
public class ExperienceGoldDaoImpl extends BaseDaoImpl<ExperienceGold> implements ExperienceGoldDao {

	private static Logger logger = LoggerFactory
			.getLogger(ExperienceGoldDaoImpl.class);
}
