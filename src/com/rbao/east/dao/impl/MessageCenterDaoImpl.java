package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.MessageCenterDao;
import com.rbao.east.entity.MessageCenter;

@Repository
public class MessageCenterDaoImpl extends BaseDaoImpl<MessageCenter> implements MessageCenterDao{
	private static Logger logger = LoggerFactory.getLogger(MessageCenterDaoImpl.class);

}
