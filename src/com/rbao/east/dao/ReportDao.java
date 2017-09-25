package com.rbao.east.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.entity.Report;

/**
 * 统计报告
 * @author daicheng
 *
 */
public interface ReportDao extends BaseDao<Report>{

	/**
	 * 统计用户总充值
	 * @return BigDecimal
	 */
	public BigDecimal summaryRecharge();
	
	/**
	 * 统计用户总提现
	 * @return BigDecimal
	 */
	public BigDecimal summaryCash();
	
	/**
	 * 平台总收益
	 * @return BigDecimal
	 */
	public BigDecimal summaryPlatformEarnings();
	
	/**
	 * 用户总收益
	 * @return BigDecimal
	 */
	public BigDecimal summaryUserEarnings();
	
	/**
	 * 平台资金统计
	 * @date
	 * 		年份
	 * @return
	 */
	public List<Map<String, Object>> platformSummary(String date);
	
	/**
	 * 平台资金统计柱状图
	 * @param date
	 * 			年份
	 * @return
	 */
	public Map<String, Object> platformHistSummary(String date);
	
	/**
	 * 充值提现数据统计
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> rechargeCashSummary(String date);
}
