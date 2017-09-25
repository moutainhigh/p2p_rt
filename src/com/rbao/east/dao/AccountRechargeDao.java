package com.rbao.east.dao;

import java.math.BigDecimal;

import com.rbao.east.entity.AccountRecharge;

/**
 * 充值记录审核
 */
public interface AccountRechargeDao extends BaseDao<AccountRecharge>{
	
	/**
	 * 统计用户总充值
	 * @param status
	 * 				审核状态
	 * @return BigDecimal
	 */
	public BigDecimal summaryRecharge(Integer status);
	
	/**
	 * 统计线下充值待审
	 * @param status 审核状态
	 * @param type 类型
	 * @return
	 */
	public Integer summaryRechargeNoCheck(Integer status, String type);

}
