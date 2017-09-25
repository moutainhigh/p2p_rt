package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.OperatorLogDao;
import com.rbao.east.entity.OperatorLog;

@Repository
public class OperatorLogDaoImpl extends BaseDaoImpl<OperatorLog> implements OperatorLogDao {
	private static Logger logger = LoggerFactory.getLogger(OperatorLogDaoImpl.class);

	/**
	 * 统计用户登陆数量
	 * @return Object
	 */
	public Integer summaryLoginCount() {
		return (Integer) super.getObject("summaryLoginCount", null);
	}
}
