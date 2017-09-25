package com.rbao.east.dao;

import java.util.List;

import com.rbao.east.entity.BorrowRepossessed;
/**
 * 待收
 * */
public interface BorrowRepossessedDao extends BaseDao<BorrowRepossessed>{

	public List<BorrowRepossessed> selectUnRepayedByRepayId(Integer repayId);
	
	public List<BorrowRepossessed> selectByTenderId(Integer tenderId);

	public List selectListByRepayId(Integer repayId);
}
