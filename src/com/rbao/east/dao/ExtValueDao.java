package com.rbao.east.dao;

import com.rbao.east.entity.ExtValue;
/**
 * 扩展值
 * */
public interface ExtValueDao extends BaseDao<ExtValue>{

	public boolean deleteByBorrowId(int borrowId, String fldTable);

}
