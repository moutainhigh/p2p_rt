package com.rbao.east.dao;

import java.math.BigDecimal;

import com.rbao.east.entity.AccountCash;
/**
 * 用户提现
 * */
public interface AccountCashDao extends BaseDao<AccountCash>{

	/**
	 * 统计用户总提现
	 * @param status
	 * 				审核状态
	 * @param flag
	 * 			是否为当天
	 * @return BigDecimal
	 */
	public BigDecimal summaryCash(Integer status, Boolean flag);
	
	/**
	 * 统计提现待审
	 * @param status
	 * @return
	 */
	public Integer summaryCashNoCheck(Integer status);
	
}
