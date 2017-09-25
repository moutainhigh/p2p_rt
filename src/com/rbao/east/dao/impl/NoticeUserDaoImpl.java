package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.NoticeUserDao;
import com.rbao.east.entity.NoticeUser;

@Repository
public class NoticeUserDaoImpl extends BaseDaoImpl<NoticeUser> implements NoticeUserDao{
	private static Logger logger = LoggerFactory.getLogger(NoticeUserDaoImpl.class);
}
