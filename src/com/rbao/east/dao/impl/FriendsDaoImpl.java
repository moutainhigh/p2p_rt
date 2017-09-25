package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.FriendsDao;
import com.rbao.east.entity.Friends;

@Repository
public class FriendsDaoImpl extends BaseDaoImpl<Friends> implements FriendsDao{

	private static Logger logger = LoggerFactory.getLogger(FriendsDaoImpl.class);
}
