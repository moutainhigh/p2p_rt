package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.FinancialProductsDao;
import com.rbao.east.entity.FinancialProducts;

@Repository
public class FinancialProductsDaoImpl extends BaseDaoImpl<FinancialProducts> implements FinancialProductsDao{
	private static Logger logger = LoggerFactory.getLogger(FinancialProductsDaoImpl.class);
}
