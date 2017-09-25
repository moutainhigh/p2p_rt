package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.AreaDao;
import com.rbao.east.entity.Area;

@Repository
public class AreaDaoImpl extends BaseDaoImpl<Area> implements AreaDao {

	private static Logger logger = LoggerFactory.getLogger(AreaDaoImpl.class);
}
