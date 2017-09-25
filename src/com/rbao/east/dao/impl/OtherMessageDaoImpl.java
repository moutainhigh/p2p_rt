package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.OtherMessageDao;
import com.rbao.east.entity.OtherMessage;

@Repository
public class OtherMessageDaoImpl extends BaseDaoImpl<OtherMessage> implements OtherMessageDao{
	private static Logger logger = LoggerFactory.getLogger(OtherMessageDaoImpl.class);

}
