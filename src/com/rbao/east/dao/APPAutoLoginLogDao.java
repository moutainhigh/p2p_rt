package com.rbao.east.dao;

import com.rbao.east.entity.APPAutologinLog;
/**
 * app登陆
 * */
public interface APPAutoLoginLogDao extends BaseDao<APPAutologinLog> {

	public boolean updateByUserId(APPAutologinLog log);
}
