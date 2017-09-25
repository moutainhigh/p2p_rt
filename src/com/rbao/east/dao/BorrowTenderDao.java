package com.rbao.east.dao;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.BorrowTender;
/**
 * 投标
 * */
public interface BorrowTenderDao extends BaseDao<BorrowTender> {

	public List<BorrowTender> selectByInStatusAndBorrow(Integer borrowId,Integer[] inStatus);
	
	/**
	 * 成交数据统计
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> bargainSummary(String date);
	
	/**
	 * 投资统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public PageModel investSummary(Map<String, String> map);
	
	/**
	 * 投资统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public List<Map<String, Object>> investAllSummary(Map<String, String> map);
	
}
