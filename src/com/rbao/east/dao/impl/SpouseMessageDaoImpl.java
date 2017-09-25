package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.SpouseMessageDao;
import com.rbao.east.entity.SpouseMessage;

@Repository
public class SpouseMessageDaoImpl extends BaseDaoImpl<SpouseMessage> implements SpouseMessageDao{

	private static Logger logger = LoggerFactory.getLogger(SpouseMessageDaoImpl.class);
}
