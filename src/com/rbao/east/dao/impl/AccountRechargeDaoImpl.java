package com.rbao.east.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.AccountRechargeDao;
import com.rbao.east.entity.AccountRecharge;

@Repository
public class AccountRechargeDaoImpl extends BaseDaoImpl<AccountRecharge> implements AccountRechargeDao{
	private static Logger logger = LoggerFactory.getLogger(AccountRechargeDaoImpl.class);
	
	/**
	 * 统计用户总充值
	 * @param status
	 * 				审核状态
	 * @return BigDecimal
	 */
	public BigDecimal summaryRecharge(Integer status) {
		return (BigDecimal) super.getObject("summaryRecharge", status);
	}
	
	/**
	 * 统计线下充值待审
	 * @param status 审核状态
	 * @param type 类型
	 * @return
	 */
	public Integer summaryRechargeNoCheck(Integer status, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("type", type);
		return (Integer) super.getObject("summaryRechargeNoCheck", map);
	}
	
}
