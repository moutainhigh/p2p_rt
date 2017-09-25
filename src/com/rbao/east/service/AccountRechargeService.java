package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.AccountRecharge;

/**
 * 充值记录审核
 */
public interface AccountRechargeService {
	
	public AccountRecharge selectByPrimaryKeyForUpdate(Integer id);
	
	/**
	 *  统计一段时间内充值总额
	 *  yan
	 * @param params
	 * @return
	 */
	public BigDecimal  queryRechargeMoney(Map<String,Object> params);

	/**
	 * 分页显示
	 * @param paramsMap
	 * @return
	 */
	public PageModel getAccountRecharge(Map<String, String> paramsMap);
	
	/**
	 * 保存充值记录
	 * @param accountRecharge
	 * @return
	 */
	public boolean saveAccountRecharge(AccountRecharge re,boolean isAudit);
	
	/**
	 * 根据Id查询充值记录
	 * @param id
	 * @return
	 */
	public AccountRecharge getAccountRechargeById(Integer id);
	
	
	
	public AccountRecharge getAccountRecharge(AccountRecharge accountRecharge);
	
	/**
	 * 查询一段时间内充值的充值记录
	 * @param params
	 * @return
	 */
	public List<AccountRecharge> queryAccountRecharge(Map<String,Object> params);
	
	/**
	 * 统计用户总充值
	 * @return BigDecimal
	 */
	public BigDecimal summaryRecharge();
	
	/**
	 * 统计线下充值待审
	 * @return
	 */
	public Integer summaryRechargeNoCheck();
}
