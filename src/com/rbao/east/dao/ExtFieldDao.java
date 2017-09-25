package com.rbao.east.dao;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.ExtField;
/**
 * 扩展字段
 * */
public interface ExtFieldDao extends BaseDao<ExtField> {
	
	List getAllExtInfo(Map<String, Object> params);

}
