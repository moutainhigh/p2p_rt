package com.rbao.east.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.BorrowTransferDao;
import com.rbao.east.entity.BorrowTransfer;

@Repository
public class BorrowTransferDaoImpl extends BaseDaoImpl<BorrowTransfer> implements BorrowTransferDao {
	private static Logger logger = LoggerFactory.getLogger(BorrowTransferDaoImpl.class);

	/**
	 * 债权转让统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public PageModel transferSummary(Map<String, String> map) {
		return super.getPage("transferSummary", "getTransferTotalCount", map);
	}
	
	/**
	 * 债权转让统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public List<Map<String, Object>> transferAllSummary(Map<String, String> map) {
		return super.selects("transferSummary", map);
	}
}
