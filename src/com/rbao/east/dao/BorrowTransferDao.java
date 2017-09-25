package com.rbao.east.dao;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.BorrowTransfer;
/**
 * 转让
 * */
public interface BorrowTransferDao extends BaseDao<BorrowTransfer> {

	/**
	 * 债权转让统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public PageModel transferSummary(Map<String, String> map);
	
	/**
	 * 债权转让统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public List<Map<String, Object>> transferAllSummary(Map<String, String> map);
}
