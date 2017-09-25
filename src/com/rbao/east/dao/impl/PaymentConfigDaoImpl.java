package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.PaymentConfigDao;
import com.rbao.east.entity.PaymentConfig;

@Repository
public class PaymentConfigDaoImpl extends BaseDaoImpl<PaymentConfig> implements PaymentConfigDao{
	private static Logger logger = LoggerFactory.getLogger(PaymentConfigDaoImpl.class);

}
