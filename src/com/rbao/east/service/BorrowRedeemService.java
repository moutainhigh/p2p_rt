package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.BorrowRedeem;


/**
 * 借款赎回
 * */
public interface BorrowRedeemService {

	/**
	 * 
	* @Title: saveBorrowRedeem
	* @Description: 借款赎回
	* @return    boolean 返回类型	
	* @throws
	 */
	public boolean saveBorrowRedeem(Map<String, Object> param);
	/**
	 * 
	* @Title: getBorrowByStatusList
	* @Description: 列表集	
	* @return    PageModel 返回类型	
	* @throws
	 */
	public PageModel getBorrowByStatusList(Map<String, String> param);
	/**
	 * 
	* @Title: getBorrowRedeemById
	* @Description: 通过ID查询借款赎回	
	* @return    BorrowRedeem 返回类型
	* @throws
	 */
	public BorrowRedeem getBorrowRedeemById(Integer pk);
	/**
	 * 
	* @Title: getByTenderId
	* @Description: 通过投标id得到数据集
	* @return    List<BorrowRedeem> 返回类型
	* @throws
	 */
	public List<BorrowRedeem> getByTenderId(Integer tenderId);
	/**
	 * 
	* @Title: getRepossessedIdListByPk
	* @Description: 得到数据集
	* @return    List<Integer> 返回类型
	* @throws
	 */
	public List<Integer> getRepossessedIdListByPk(Integer pk);
	/**
	 * 
	* @Title: updateBorrowRedeem
	* @Description: 更新投资赎回
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean updateBorrowRedeem(Map<String, Object> param);
}
