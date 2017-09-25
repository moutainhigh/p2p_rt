package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.RelaseCard;

public interface RelaseBankService {
	//申请解绑银行卡
	public boolean relaseBank(RelaseCard relaseCard);
	
	public PageModel getRelaseBankList(Map<String,String> paramsMap);
	
	public RelaseCard getById(Integer id);
	
	public boolean saveOrUpdate(RelaseCard relaseCard);
	
	public RelaseCard getByParam(Map<String,String> paramsMap);
}
