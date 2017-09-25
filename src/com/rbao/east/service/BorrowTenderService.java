package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.BorrowTender;
/**
 * 投标
 * */
public interface BorrowTenderService {

	/**
	 * 导出投标记录
	 * 
	 * @param paramsMap
	 */
	public List<Map<String, Object>> toTenderRecordExcel(
			Map<String, String> params);

	/**
	 * yan 统计投资人数
	 */
	public Integer selectTenders();

	/**
	 * 统计一段时间内投标总额 yan
	 * 
	 * @param params
	 * @return
	 */
	public BigDecimal queryTenderMoney(Map<String, Object> params);

	/**
	 * 统计投资金额 yan
	 * 
	 * @param params
	 * @return
	 */
	public BigDecimal sumTenderMoney(Map<String, Object> params);

	/**
	 * 根据borrow id查询
	 * 
	 * @param borrowId
	 * @return
	 */
	public List<BorrowTender> selectByBorrowId(Integer borrowId);

	/**
	 * @param borrowId 参数
	 * @param userId 参数
	 * @return
	 */
	public BigDecimal selectSumByBorrowIdAndUserId(Integer borrowId,
			Integer userId);


	/**
	 * 
	* @Title: updateByBorrowTender
	* @Description: 更新操作
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean updateByBorrowTender(BorrowTender borrowTender);

	/**
	 * 
	* @Title: saveBorrowTender
	* @Description: 保存发标
	* @return   boolean 返回类型 
	* @throws
	 */
	public boolean saveBorrowTender(BorrowTender borrowTender);

	/**
	 * 
	* @Title: shwoBorrowTenderInfoByPage
	* @Description: 分页显示数据
	* @return    PageModel 返回类型
	* @throws
	 */
	public PageModel shwoBorrowTenderInfoByPage(Map<String, String> param);

	/* 查询以投资金额 */
	public List selectEff(Map<String, String> param);

	public int selectRepayedCountByUserId(Integer userId);

	/**
	 * 投资记录
	 * 
	 * @param params
	 * @return
	 */
	public PageModel selectInvestByUserId(Map<String, String> params);

	public BorrowTender selectBorrowTenderByBorrowTenderId(Integer pk);

	/**
	 * 定活通投资赎回
	 * 
	 * @param params
	 * @return
	 */
	public PageModel selectInvestRedeem(Map<String, String> params);

	/**
	 * 直投区投资赎回
	 * 
	 * @param params
	 * @return
	 */
	public PageModel queryInvestRedeem(Map<String, String> params);
	/**
	 * 
	* @Title: getEffectiveAmountByDay
	* @Description: 可用金额	
	* @return    BigDecimal 返回类型
	* @throws
	 */
	
	public BigDecimal getEffectiveAmountByDay(Map<String, String> params);

	/**
	 * 
	* @Title: getTenderUserIds
	* @Description: 得到投标用户id
	* @return    List<Integer>  返回类型
	* @throws
	 */
	
	public List<Integer> getTenderUserIds(Map<String, String> params);
	/**
	 * 
	* @Title: getEffectiveAmount
	* @Description: 可用总额
	* @return    List<Map<String, String>> 返回类型
	* @throws
	 */
	
	public List<Map<String, String>> getEffectiveAmount(
			Map<String, String> params);
	/**
	 * 
	* @Title: getbyUserId
	* @Description: 用户id
	* @return  List 返回类型  
	* @throws
	 */
	
	public List getbyUserId(Integer userId);
	/**
	 * 
	* @Title: getBorrowDetailByUserId
	* @Description: 借款详细
	* @return    List 返回类型
	* @throws
	 */
	
	public List getBorrowDetailByUserId(Integer userId);
	/**
	 * 
	* @Title: getTenderSumByUserId
	* @Description: 借款总额
	* @return Object 返回类型   
	* @throws
	 */
	
	public Object getTenderSumByUserId(Integer userId);

	/**
	 * 我是投资者
	 * 
	 * @param paramsMap
	 */
	public PageModel getBorrowRepayingPage(Map<String, String> paramsMap);

	// 统计投资人数
	public Integer getTenderUsers(Integer borrowId);

	/**
	 * 根据标id和用户id取协议路径
	 * 
	 * @param param
	 * @return
	 */
	public String getAgreementPathByBorrowIdAndUserId(Map<String, Object> param);

	/**
	 * 成交数据统计
	 * 
	 * @param date
	 *            年份
	 * @return
	 */
	public List<Map<String, Object>> bargainSummary(String date);

	/**
	 * 投资统计
	 * 
	 * @param map
	 *            条件
	 * @return
	 */
	public PageModel investSummary(Map<String, String> map);

	/**
	 * 投资统计
	 * 
	 * @param map
	 *            条件
	 * @return
	 */
	public List<Map<String, Object>> investAllSummary(Map<String, String> map);
	/**
	 * 
	* @Title: 
	* @Description: 
	* @return    
	* @throws
	 */
	
	BigDecimal selectSumByMap(Map m);
	/**
	 * 
	* @Title: 
	* @Description: 
	* @return    
	* @throws
	 */
	
	Integer selectCountByMap(Map m);

	/**
	 * 
	* @Title: getUserTenderRank
	* @Description: 用户投资排行
	* @param @param param
	* @param @return    参数
	* @return List<Map>    返回类型
	* @throws
	 */
	List<Map> getUserTenderRank(Map<String, Object> param);

	/**
	 * 根据用户ID查询该用户是否是首次投资<br>
	 * 投资表中有非体验金购买记录则不是首次购买
	 * 
	 * @param map
	 * @return 0是首次购买,非0则不是首次购买
	 */
	public Integer selectFirstBuy(Map map);

	public Integer getEffectiveTenderCount(Integer userid);

	/**
	 * 
	* @Title: queryTenderMoney
	* @Description: 统计一段时间内的投资总额，时间，标种，标状态
	* @return BigDecimal    返回类型
	* @throws
	 */
	public BigDecimal queryTenderMoneyByKind(Map<String, Object> params);

	/**
	 * 
	* @Title: queryTenderByBorrowidAndUserId
	* @Description: 根据用户id和borrowid得到投资记录
	* @return List<BorrowTender>    返回类型
	* @throws
	 */
	public List<BorrowTender> queryTenderByBorrowidAndUserId(Map<String, Object> params);
	
}
