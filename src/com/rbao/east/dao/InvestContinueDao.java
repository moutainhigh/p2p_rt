package com.rbao.east.dao;

import com.rbao.east.entity.InvestContinue;
/**
 * 续投奖励
 * */
public interface InvestContinueDao extends BaseDao<InvestContinue> {

	public InvestContinue getByUserId(Integer userId);
}
