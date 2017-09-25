package com.rbao.east.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.BorrowTenderDao;
import com.rbao.east.entity.BorrowTender;

@Repository
public class BorrowTenderDaoImpl extends BaseDaoImpl<BorrowTender> implements
		BorrowTenderDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BorrowTender> selectByInStatusAndBorrow(Integer borrowId,Integer[] inStatus){
		Map m = new HashMap();
		m.put("inStatus", inStatus);
		m.put("borrowId", borrowId);
		return select("selectByInStatusAndBorrow", m);
	}
	
	/**
	 * 成交数据统计
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> bargainSummary(String date) {
		return super.selects("bargainSummary", date);
	}
	
	/**
	 * 投资统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public PageModel investSummary(Map<String, String> map) {
		return super.getPage("investSummary", "getInvestTotalCount", map);
	}
	
	/**
	 * 投资统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public List<Map<String, Object>> investAllSummary(Map<String, String> map) {
		return super.selects("investSummary", map);
	}
}
