package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.Map;

import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowTender;
/**
 * 标的操作
 * */
public interface BorrowTypeHandleService {
	/**
	 * 推荐奖励 
	 * yan
	 * @param userId
	 * @param money 投资金额
	 */
	public void recommonedAward(Integer userId,BigDecimal money);
	
	/**
	 * yan
	 * 生成pdf
	 * @param borrow
	 */
	public void createAgreeMent(Borrow borrow);

	/**
	 * 发标保存前做判断
	 * @return
	 */
	public ServiceResult allowToPublishBorrow(Borrow borrow);
	
	/**
	 * 保存发标
	 * @param borrow
	 * @return
	 */
	public ServiceResult saveBorrow(Borrow borrow);
	
	/**
	 * 判断是否可以投标
	 * @param tender
	 * @return
	 */
	public ServiceResult allowToTender(BorrowTender tender,Map<String, String> params);
	
	/**
	 * 保存投标
	 * @param tender
	 * @return
	 */
	public ServiceResult saveTender(BorrowTender tender,Map<String, String> params) ;
	
	/**
	 * 复审通过
	 * @param borrow
	 * @return
	 * @throws Exception
	 */
	public ServiceResult reviewSuccess(Borrow borrow) ; 
	
	/**
	 * 还款
	 * @param repay
	 * @return
	 */
	public ServiceResult repay(BorrowRepayment repay) ;
	
	/**
	 * 
	* @Title: getActivityRecommend
	* @Description: 该方法，用于双12活动
	* @return void    返回类型
	* @throws
	 */
	public void getActivityRecommend(BorrowTender tender,Borrow borrow,BigDecimal tenderAllMoney) ;
	/**
	 * 
	* @Title: getActivityValid
	* @Description: 判断是否处于红包发放有效内
	* @return void    返回类型
	* @throws
	 */
	public boolean getActivityValid(BorrowTender tender,Borrow borrow) ;
	
	/**
	 * 再次发标
	 * @param borrow
	 * @return
	 */
	public ServiceResult updateBorrow(Borrow borrow);

}
