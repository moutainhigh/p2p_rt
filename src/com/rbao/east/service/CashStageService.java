package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.CashStage;

/**
 * 提现等级
 * @author yan
 *
 */
public interface CashStageService {
	/**
	 * 分页
	 * @param params
	 * @return
	 * yan
	 */
	public PageModel getPage(Map<String,String> params);
	
	public boolean saveOrUpdate(CashStage cashStage);
	
	public CashStage selectById(Integer id);
	
	/**
	 * 提现手续费
	 * @param cashMoney 提现总金额
	 * @return 提现手续费
	 */
	public BigDecimal getCashFee(BigDecimal cashMoney);

}
