package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.ContentDao;
import com.rbao.east.entity.Content;

@Repository
public class ContentDaoImpl extends BaseDaoImpl<Content> implements ContentDao {

	private static Logger logger = LoggerFactory
			.getLogger(ContentDaoImpl.class);
	
}
