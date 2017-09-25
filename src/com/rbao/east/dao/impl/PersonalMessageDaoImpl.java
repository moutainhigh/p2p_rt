package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.PersonalMessageDao;
import com.rbao.east.entity.PersonalMessage;

@Repository
public class PersonalMessageDaoImpl extends BaseDaoImpl<PersonalMessage> implements PersonalMessageDao{

	private static Logger logger = LoggerFactory.getLogger(PersonalMessageDaoImpl.class);
}
