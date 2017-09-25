package com.rbao.east.dao.impl;

import org.springframework.stereotype.Repository;

import com.rbao.east.dao.QuickBorrowDao;
import com.rbao.east.entity.QuickBorrow;
@Repository
public class QuickBorrowDaoImpl extends BaseDaoImpl<QuickBorrow> implements QuickBorrowDao {

	/**
	 * 待处理的个人借款数量
	 * @param status
	 * 				状态
	 * @return Integer
	 */
	public Integer summaryDisposeCount(Integer status) {
		return (Integer) super.getObject("summaryDisposeCount", status);
	}
}
