package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.NoticeTypeDao;
import com.rbao.east.entity.NoticeType;

@Repository
public class NoticeTypeDaoImpl extends BaseDaoImpl<NoticeType> implements NoticeTypeDao{
	private static Logger logger = LoggerFactory.getLogger(NoticeTypeDaoImpl.class);
}
