package com.rbao.east.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rbao.east.dao.BorrowRepossessedDao;
import com.rbao.east.entity.BorrowRepossessed;

@Repository
public class BorrowRepossessedDaoImpl extends BaseDaoImpl<BorrowRepossessed> implements
		BorrowRepossessedDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BorrowRepossessed> selectUnRepayedByRepayId(Integer repayId) {
		Map m = new HashMap();
		m.put("repayId", repayId);
		m.put("inStatus", new Integer[]{BorrowRepossessed.STATUS_OVERDUE
									,BorrowRepossessed.STATUS_REPOSSESSING});
		return super.select("selectByRepayIdAndStatus", m);
	}

	@Override
	public List<BorrowRepossessed> selectByTenderId(Integer tenderId) {
		return super.select("selectByTenderId", tenderId);
	}

	@Override
	public List selectListByRepayId(Integer repayId) {
		// TODO Auto-generated method stub
		return super.select("selectListByRepayId", repayId);
	}

}
