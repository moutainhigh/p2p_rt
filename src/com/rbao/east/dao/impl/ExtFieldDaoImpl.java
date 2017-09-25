package com.rbao.east.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.ExtFieldDao;
import com.rbao.east.entity.ExtField;

@Repository
public class ExtFieldDaoImpl extends BaseDaoImpl<ExtField> implements ExtFieldDao {
	
	private static Logger logger = LoggerFactory.getLogger(ExtFieldDaoImpl.class);

	@Override
	public List getAllExtInfo(Map<String, Object> params) {
		return select("getAllExtInfo",params);   // 获取所有的信息
	}
	
}
