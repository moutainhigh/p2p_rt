package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Report;
/**
 * 报表统计
 * */
public interface ReportService {
	
	/**
	 * 统计满标额
	 * yan
	 * @param params
	 * @return
	 */
	public BigDecimal selectFullMoney(Map<String,String> params);
	

	Report getReportById(Integer id);

	List<Report> getAllReports();

	/**
	 * 分页
	 */
	PageModel getPageReports(Map<String, String> params);

	List<Report> getAllReports(Map<String, String> params);

	/**
	 * 统计每日数据
	 * 
	 * @return
	 */
	public boolean saveReport();
	
	
	public String getSumByUserId(Map<String, Object> param);
	
	public String getRepossessedSumByUserId(Map<String, Object> param);
	
	//已创造财富
	public Map getWealth();

	//月度统计
	public PageModel getReportByMonth(Map<String, String> paramsMap);
	
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
