package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.NoticeDao;
import com.rbao.east.entity.Notice;

@Repository
public class NoticeDaoImpl extends BaseDaoImpl<Notice> implements NoticeDao{
	private static Logger logger = LoggerFactory.getLogger(NoticeDaoImpl.class);
}
