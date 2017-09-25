package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.FriendsRequestDao;
import com.rbao.east.entity.FriendsRequest;

@Repository
public class FriendsRequestDaoImpl extends BaseDaoImpl<FriendsRequest> implements FriendsRequestDao{

	private static Logger logger = LoggerFactory.getLogger(FriendsRequestDaoImpl.class);
}
