package com.rbao.east.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.AccountCashDao;
import com.rbao.east.entity.AccountCash;

@Repository
public class AccountCashDaoImpl extends BaseDaoImpl<AccountCash> implements AccountCashDao{

	private static Logger logger = LoggerFactory.getLogger(AccountCashDaoImpl.class);
	
	/**
	 * 统计用户总提现
	 * @param status
	 * 				审核状态
	 * @return BigDecimal
	 */
	public BigDecimal summaryCash(Integer status, Boolean flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("flag", flag);
		return (BigDecimal) super.getObject("summaryCash", map);
	}
	
	/**
	 * 统计提现待审
	 * @param status
	 * @return
	 */
	public Integer summaryCashNoCheck(Integer status) {
		return (Integer) super.getObject("summaryCashNoCheck", status);
	}
}
