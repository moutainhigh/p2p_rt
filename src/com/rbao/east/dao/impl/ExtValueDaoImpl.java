package com.rbao.east.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.ExtValueDao;
import com.rbao.east.entity.ExtValue;

@Repository
public class ExtValueDaoImpl extends BaseDaoImpl<ExtValue> implements ExtValueDao{
	
	private static Logger logger = LoggerFactory.getLogger(FinaceMessageDaoImpl.class);

	@Override
	public boolean deleteByBorrowId(int borrowId, String fldTable) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("borrowId", String.valueOf(borrowId)); 
		param.put("fldTable", fldTable);
		return deletes("deleteByBorrowId", param); 
	}


	/*@Override
	public boolean deleteByBorrowId(int borrowId, ExtValue extValue) {
		Map param = new HashMap();
		param.put("extVal", extValue);
		return deletes(String.valueOf(borrowId), param);
	}*/
	
}
