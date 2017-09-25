package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.AccountStatistics;

/**
 * 每日统计
 */
public interface AccountStatisticsService {

	/**
	 * 保存信息
	 */
	@Deprecated
	public boolean saveAccountStatistics(AccountStatistics statistics);
	
	/**
	 * 分页显示
	 */
	@Deprecated
	public PageModel getAccountStatistics(Map<String, String> paramsMap);
	/**
	 * 
	* @Title: getPageAccountStatistics
	* @Description: 列表数据
	* @return PageModel    返回类型
	* @throws
	 */
	public PageModel getPageAccountStatistics(Map<String, String> params);
	/**
	 * 
	* @Title: getAllAccountStatistics
	* @Description: 列表数据
	* @return List<AccountStatistics>    返回类型
	* @throws
	 */
	public List<AccountStatistics> getAllAccountStatistics(Map<String, String> params);
	
	/**
	 * 根据Id查询
	 */
	@Deprecated
	public AccountStatistics getStatisticsById(Integer id);
	
	/**
	 * 删除
	 */
	@Deprecated
	public boolean deleteStatisticsById(Integer id);
	
	/**
	 * 每天自动从rb_user_account表中拷贝数据到rb_account_statistics中
	 */
	public boolean autoCopyDataByDay();
	
	/**
	 * 根据userId查询
	 */
	public AccountStatistics selectByUserId(Map<String, String> params);
	
	
	/**
	 *得到本站全部的预期收益
	 */
	public BigDecimal getAllInterestAndReward(Map<String, Object> param);
	
	
}
