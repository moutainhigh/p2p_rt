package com.rbao.east.dao.impl;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.ReportDao;
import com.rbao.east.entity.Report;

@Repository
public class ReportDaoImpl extends BaseDaoImpl<Report> implements ReportDao {

	private static Logger logger = LoggerFactory.getLogger(ReportDaoImpl.class);
	
	/**
	 * 统计用户总充值
	 * @return BigDecimal
	 */
	public BigDecimal summaryRecharge() {
		return (BigDecimal) super.getObject("summaryRecharge", null);
	}
	
	/**
	 * 统计用户总提现
	 * @return BigDecimal
	 */
	public BigDecimal summaryCash() {
		return (BigDecimal) super.getObject("summaryCash", null);
	}
	
	/**
	 * 平台总收益
	 * @return BigDecimal
	 */
	public BigDecimal summaryPlatformEarnings() {
		return (BigDecimal) super.getObject("summaryPlatformEarnings", null);
	}
	
	/**
	 * 用户总收益
	 * @return BigDecimal
	 */
	public BigDecimal summaryUserEarnings() {
		return (BigDecimal) super.getObject("summaryUserEarnings", null);
	}
	
	/**
	 * 平台资金统计
	 * @date
	 * 		年份
	 * @return
	 */
	public List<Map<String, Object>> platformSummary(String date) {
		return super.selects("platformSummary", date);
	}
	
	/**
	 * 平台资金统计柱状图
	 * @param date
	 * 			年份
	 * @return
	 */
	public Map<String, Object> platformHistSummary(String date) {
		return (Map<String, Object>) super.getObject("platformHistSummary", date);
	}
	
	/**
	 * 充值提现数据统计
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> rechargeCashSummary(String date) {
		return super.selects("rechargeCashSummary", date);
	}
}
