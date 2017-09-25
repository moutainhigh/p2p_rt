package com.rbao.east.dao.impl;

import org.springframework.stereotype.Repository;

import com.rbao.east.dao.APPAutoLoginLogDao;
import com.rbao.east.entity.APPAutologinLog;
import com.rbao.east.entity.UserAccount;

@Repository
public class APPAutologinLogDaoImpl extends BaseDaoImpl<APPAutologinLog>
		implements APPAutoLoginLogDao {

	@Override
	public boolean updateByUserId(APPAutologinLog log) {
			return this.update("updateByUserid", log);
	}
	
	
}
