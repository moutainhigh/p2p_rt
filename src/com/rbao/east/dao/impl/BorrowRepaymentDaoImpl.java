package com.rbao.east.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.BorrowRepaymentDao;
import com.rbao.east.entity.BorrowRepayment;

@Repository
public class BorrowRepaymentDaoImpl extends BaseDaoImpl<BorrowRepayment> implements
			BorrowRepaymentDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BigDecimal selectSumByKindAndStatus(Integer userId, String[] kind,
			Integer[] status) {
		Map m = new HashMap();
		m.put("userId", userId);
		m.put("borrowKindsCode", kind);
		m.put("inStatus", status);
		BigDecimal money = (BigDecimal)super.selects("selectSumByKindAndStatus", m).get(0);
		if(money == null){
			return new BigDecimal(0);
		}else{
			return money;
		}
	}
	
	/**
	 * 垫付还款统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public PageModel reparmentSummary(Map<String, String> map) {
		return super.getPage("reparmentSummary", "getRepaymentTotalCount", map);
	}
	
	/**
	 * 垫付还款统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public List<Map<String, Object>> reparmentAllSummary(Map<String, String> map) {
		return super.selects("reparmentSummary", map);
	}

}
