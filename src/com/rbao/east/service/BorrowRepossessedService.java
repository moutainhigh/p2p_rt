package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.BorrowRepossessed;


/**
 * @author admin
 *	待收
 */
	
public interface BorrowRepossessedService {
	/**
	 * 
	* @Title: updateBorrowRepossessed
	* @Description: 
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean updateBorrowRepossessed(BorrowRepossessed borrowRepossessed);
	/**
	 * 
	* @Title: selectSum
	* @Description: 查询收回本金，收回利息
	* @return    List 返回类型
	* @throws
	 */
	
	public List selectSum(Map<String ,String> paramMap);
	
	/**
	 * 
	* @Title: selectBorrowRepossessedByTenderId
	* @Description: 查询投资赎回
	* @return    List<BorrowRepossessed> 返回类型
	* @throws
	 */
	public List<BorrowRepossessed> selectBorrowRepossessedByTenderId(Map<String, Object> param);
	/**
	 * 
	* @Title: selectWaitRepossessByTender
	* @Description: 统计待收根据投标id
	* @return    	List<BorrowRepossessed> 返回类型
	* @throws
	 */
	List<BorrowRepossessed> selectWaitRepossessByTender(Integer tenderId);
	/**
	 * 
	* @Title: getBorrowRepossessedListByPkList
	* @Description: 借款待收
	* @return    List 返回类型
	* @throws
	 */
	public List getBorrowRepossessedListByPkList(Map<String, Object> param);
	/**
	 * 
	* @Title: getBorrowRepossessedListByRepossessedPkList
	* @Description: 待收
	* @return    List<BorrowRepossessed> 返回类型
	* @throws
	 */
	public List<BorrowRepossessed> getBorrowRepossessedListByRepossessedPkList(Map<String, Object> param);
	/**
	 * 
	* @Title: getBorrowRepossessedByPk
	* @Description: 根据主键查询待收
	* @return    BorrowRepossessed 返回类型
	* @throws
	 */
	public BorrowRepossessed getBorrowRepossessedByPk(Integer pk);
	/**
	 * 
	* @Title: selectBorrowRepossessedByRepaymentId
	* @Description: 根据还款id查询待收
	* @return    PageModel 返回类型
	* @throws
	 */
	public PageModel selectBorrowRepossessedByRepaymentId(
			Map<String, String> param);
	/**
	 * 
	* @Title: saveBorrowRepossessed
	* @Description: 保存操作
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean saveBorrowRepossessed(BorrowRepossessed borrowRepossessed);
	
	/**
	 * 
	* @Title: getborrowRepossessedByTransferId
	* @Description:  统计债权总额
	* @return   BorrowRepossessed 返回类型 
	* @throws
	 */
	public BorrowRepossessed getborrowRepossessedByTransferId(Integer transferId);
	
	/**
	 * 账户中心最近待收
	 * @param userId
	 * @return
	 */
	public List<BorrowRepossessed> getBorrowRepWaitByUserId(Integer userId);
	/**
	 * 
	* @Title: 
	* @Description: 
	* @return    
	* @throws
	 */
	public Object getPrepareAmountOrRepossessedInterestByUserId(Integer userId);
	
	/**
	 * 我是投资者-收款明细账
	 * @param paramsMap
	 * @return
	 */
	public PageModel getRepByStatusPage(Map<String, String> paramsMap);
	
	//待收、已收总额
	public BigDecimal getCountMoney(Integer status);
   /**
    * 统计昨日收益
    * @param map
    * @return
    */
	Map<String, Object> selectSumYesterday(Map<String, Object> map);
	
}
