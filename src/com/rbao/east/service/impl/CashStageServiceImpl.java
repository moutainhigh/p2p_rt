package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.CashStageDao;
import com.rbao.east.entity.CashStage;
import com.rbao.east.service.CashStageService;

@Service
@Transactional
public class CashStageServiceImpl implements CashStageService{
	
	@Autowired
	private CashStageDao cashStageDao;

	@Override
	public PageModel getPage(Map<String, String> params) {
		return this.cashStageDao.getPage("selectByEntity", params);
	}

	@Override
	public boolean saveOrUpdate(CashStage cashStage) {
		return this.cashStageDao.saveOrUpdate(cashStage);
	}

	@Override
	public CashStage selectById(Integer id) {
		return this.cashStageDao.selectByPrimaryKey(id);
	}

	@Override
	public BigDecimal getCashFee(BigDecimal cashMoney) {
		Map<String,BigDecimal> map=new HashMap<String, BigDecimal>();
		map.put("cashMoney", cashMoney);
		return (BigDecimal) this.cashStageDao.getObject("getCashFee", map);
	}
}
