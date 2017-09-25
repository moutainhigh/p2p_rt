package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.BorrowRepayment;
/**
 * 还款
 * */
public interface BorrowRepaymentService {

	/**
	 * 保存还款信息
	 * @param repayment
	 * @return
	 */
	public boolean saveBorrowRepayment(BorrowRepayment repayment);
	
	/**
	 * 根据id查询还款信息
	 * @param id
	 * @return
	 */
	public BorrowRepayment getBorrowRepaymentById(Integer id);
	
	/**
	 * 标逾期处理
	 */
	public void borrowOverdueDispose(BorrowRepayment borrowRepayment);
	/**
	 * 还款明细
	 * @param params
	 * @return
	 */
	public PageModel selectBorrowRepayment(Map<String,String> params);
	
	
	
	public boolean updateBorrowRepayment(BorrowRepayment repayment);
	
	
	/**
	 * 查询需要自动还款的信息
	 */
	public List<BorrowRepayment> findAutoBorrowRepayment();
	
	/**
	 * 自动还款
	 */
	public boolean  autoBorrowRepaymentReturnMoney(BorrowRepayment borrowRepayment);
	
	/**
	 * 最近待还
	 * @param userId
	 * @return
	 */
	public List<BorrowRepayment> getWaitRepaymentByUserId(Integer userId);
	
	/**
	 * 根据borrowId查询待还款信息
	 */
	public List<BorrowRepayment> getRepaymentByBorrowId(Integer BorrowId);
	
	
	/**
	 * 根据状态统计用户还款数量
	 */
	public Integer getRepayingCount(Map<String, Object> param); 
	

	//提前还款次数
	public Integer getForwardRepayment();
	
	//实时财务列表
	public PageModel getRepayments(Map<String, String> paramsMap);
	
	/**
	 * 垫付还款统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public PageModel reparmentSummary(Map<String, String> map);
	
	/**
	 * 垫付还款统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public List<Map<String, Object>> reparmentAllSummary(Map<String, String> map);
	
}
