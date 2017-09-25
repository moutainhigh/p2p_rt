package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.RecommendAccountDao;
import com.rbao.east.entity.RecommendAccount;
@Repository
public class RecommendAccountDaoImpl extends BaseDaoImpl<RecommendAccount> implements RecommendAccountDao{
	private static Logger logger = LoggerFactory.getLogger(RecommendAccountDaoImpl.class);

}
