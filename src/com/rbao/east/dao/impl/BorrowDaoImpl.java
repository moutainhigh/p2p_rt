package com.rbao.east.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.BorrowDao;
import com.rbao.east.entity.Borrow;

@Repository
public class BorrowDaoImpl extends BaseDaoImpl<Borrow> implements BorrowDao {

	private static Logger logger = LoggerFactory
			.getLogger(BorrowDaoImpl.class);

	

	/**
	 * 借款待办事项
	 * @param status
	 * 				状态：1.待审核
	 * 					2.待发布
	 * 					3.待放款
	 * @param flag
	 * @return
	 */
	public Integer summaryBacklogCount(Integer status, Boolean flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("flag", flag);
		return (Integer) super.getObject("summaryBacklogCount", map);
	}
	
	/**
	 * 借款数据统计-按产品类型
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByType(String date) {
		return super.selects("borrowSummaryByType", date);
	}
	
	/**
	 * 借款数据统计-按期限
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByDate(String date) {
		return super.selects("borrowSummaryByDate", date);
	}
	
	/**
	 * 借款数据统计-按省份
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByProvince(String date) {
		return super.selects("borrowSummaryByProvince", date);
	}
}
