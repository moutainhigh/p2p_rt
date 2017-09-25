package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.PrivateBusinessMessageDao;
import com.rbao.east.entity.PrivateBusinessMessage;

@Repository
public class PrivateBusinessMessageDaoImpl extends BaseDaoImpl<PrivateBusinessMessage> implements PrivateBusinessMessageDao{

	private static Logger logger = LoggerFactory.getLogger(PrivateBusinessMessageDaoImpl.class);
}
