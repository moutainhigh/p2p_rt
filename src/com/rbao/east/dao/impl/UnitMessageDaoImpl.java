package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.UnitMessageDao;
import com.rbao.east.entity.UnitMessage;

@Repository
public class UnitMessageDaoImpl extends BaseDaoImpl<UnitMessage> implements UnitMessageDao {

	private static Logger logger = LoggerFactory.getLogger(UnitMessageDaoImpl.class);
}
