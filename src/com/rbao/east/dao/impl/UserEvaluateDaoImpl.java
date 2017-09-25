package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.UserEvaluateDao;
import com.rbao.east.entity.UserEvaluate;

@Repository
public class UserEvaluateDaoImpl extends BaseDaoImpl<UserEvaluate> implements UserEvaluateDao{

	private static Logger logger = LoggerFactory.getLogger(UserEvaluateDaoImpl.class);
}
