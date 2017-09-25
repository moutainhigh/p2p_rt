package com.rbao.east.dao;

import com.rbao.east.entity.QuickBorrow;

public interface QuickBorrowDao extends BaseDao<QuickBorrow>{

	/**
	 * 待处理的个人借款数量
	 * @param status
	 * 				状态
	 * @return Integer
	 */
	public Integer summaryDisposeCount(Integer status);
}
