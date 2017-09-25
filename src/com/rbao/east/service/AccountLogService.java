package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.UserAccount;
/**
 * 用户日志
 * */
public interface AccountLogService {
	
	/**
	 * yan
	 * 查询该用户是否有注册奖励 
	 * @param params
	 * @return
	 */
	public Integer queryRegister(Map<String,Object> params);
	
	/**
	 * yan
	 * 查询一段时间内回款续投奖励总额
	 * @param params
	 * @return
	 */
	public BigDecimal queryContinueAwardMoney(Map<String,Object> params);
	
	/**
	 * 添加log
	 * @param acc 
	 * @param tradeType 交易类型
	 * @param dealMoney 交易金额
	 * @param repossessedMoney 收回金额
	 * @param tradeUserId 交易对方
	 * @param remark 说明
	 * @param ip
	 * @return
	 */
	public boolean add(UserAccount acc,String tradeType,BigDecimal dealMoney,BigDecimal repossessedMoney,
			Integer tradeUserId,String remark,String ip) ;
	
	/**
	 * 给平台账户添加资金log必须调用这一个
	 * @param acc 
	 * @param tradeType 交易类型
	 * @param dealMoney 交易金额
	 * @param repossessedMoney 收回金额
	 * @param tradeUserId 交易对方
	 * @param remark 说明
	 * @param ip
	 * @param payStatus (ADD/SUBTRACT)
	 * @return
	 */
	public boolean add(UserAccount acc, String tradeType, BigDecimal dealMoney,
			BigDecimal repossessedMoney, Integer tradeUserId, String remark,
			String ip, Integer payStatus); 
	
	public boolean add(UserAccount acc, String tradeType, BigDecimal dealMoney,
			Integer tradeUserId, String remark); 
	/**
	 * 分页显示
	 * @param paramsMap
	 * @return
	 */
	public PageModel getAccountLog(Map<String, String> paramsMap);
	
	/**
	 * 统计
	 * @return
	 */
	public PageModel getLogStatisticsPage(Map<String, String> paramsMap);
	
	public Map getLogMap(List<Map> checkingList,int userId);
	  /* 查询用户的总充值金额*/
	public Integer selectByTradeCode(Map<String, String> paramsMap);


	/**
	 * 根据类型得到总金额
	 * @return BigDecimal
	 */
	public BigDecimal getTotleMoneyByTradeCode(Map<String, Object> param);
	
	/**
	 * 得到首页投资金额排行榜
	 */
	public List<AccountLog> getRankingList(Map<String, Object> param);
	
	/**
	 * 
	* @Title: getNewTenderList
	* @Description: 得到新记录表
	* @return List<AccountLog>    返回类型
	* @throws
	 */
	public List<AccountLog> getNewTenderList(String tradeCode);
	
	
}
