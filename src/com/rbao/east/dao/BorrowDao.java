package com.rbao.east.dao;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.Borrow;

/**
 * 标
 * @author Liutq
 *
 */
public interface BorrowDao extends BaseDao<Borrow>{
	
	/**
	 * 借款待办事项
	 * @param status
	 * 				状态：1.待审核
	 * 					2.待发布
	 * 					3.待放款
	 * @param flag
	 * @return
	 */
	public Integer summaryBacklogCount(Integer status, Boolean flag);
	
	/**
	 * 借款数据统计-按产品类型
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByType(String date);
	
	/**
	 * 借款数据统计-按期限
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByDate(String date);
	
	/**
	 * 借款数据统计-按省份
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByProvince(String date);
	
}
