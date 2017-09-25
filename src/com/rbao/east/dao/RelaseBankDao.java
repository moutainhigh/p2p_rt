package com.rbao.east.dao;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.RelaseCard;

public interface RelaseBankDao extends BaseDao<RelaseCard>{
	
	public List<RelaseCard> selectRelaseBankList(Map<String,String> map);
}
