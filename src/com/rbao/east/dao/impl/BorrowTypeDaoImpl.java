package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.BorrowTypeDao;
import com.rbao.east.entity.BorrowType;

@Repository
public class BorrowTypeDaoImpl extends BaseDaoImpl<BorrowType> implements BorrowTypeDao{

	private static Logger logger = LoggerFactory.getLogger(BorrowTypeDaoImpl.class);
	
}
