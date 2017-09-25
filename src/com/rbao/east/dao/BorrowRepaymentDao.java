package com.rbao.east.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.BorrowRepayment;
/**
 * 还款
 * */
public interface BorrowRepaymentDao extends BaseDao<BorrowRepayment>{

	public BigDecimal selectSumByKindAndStatus(Integer userId,String[] kind,Integer[] status);
	
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
