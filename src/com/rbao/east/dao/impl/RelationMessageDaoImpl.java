package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.RelationMessageDao;
import com.rbao.east.entity.RelationMessage;

@Repository
public class RelationMessageDaoImpl extends BaseDaoImpl<RelationMessage> implements RelationMessageDao{

	private static Logger logger = LoggerFactory.getLogger(RelationMessageDaoImpl.class);
}
