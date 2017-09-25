
package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.UserEvaluateApplyDao;
import com.rbao.east.entity.UserEvaluateApply;

/**
 * 额度申请
 */

@Repository
public class UserEvaluateApplyDaoImpl extends BaseDaoImpl<UserEvaluateApply> implements UserEvaluateApplyDao{
	private static Logger logger = LoggerFactory
	.getLogger(UserEvaluateApplyDaoImpl.class);
}
