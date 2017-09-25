package com.rbao.east.dao;

import com.rbao.east.entity.OperatorLog;

public interface OperatorLogDao extends BaseDao<OperatorLog> {

	/**
	 * 统计用户登陆数量
	 * @return Object
	 */
	public Integer summaryLoginCount();
}
