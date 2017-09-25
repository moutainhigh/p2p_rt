package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.AttachDao;
import com.rbao.east.entity.Attach;

@Repository
public class AttachDaoImpl extends BaseDaoImpl<Attach> implements AttachDao {

	private static Logger logger = LoggerFactory.getLogger(AttachDaoImpl.class);

}
