package com.rbao.east.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.FinancialProductsDao;
import com.rbao.east.entity.FinancialProducts;
import com.rbao.east.service.FinancialProductsService;

@Service
@Transactional
public class FinancialProductsServiceImpl implements FinancialProductsService {
	
	private static Logger logger = LoggerFactory.getLogger(FinancialProductsServiceImpl.class);
	
	@Autowired
	private FinancialProductsDao financialProductsDao;

	@Override
	public PageModel getFinancialProductsList(Map<String, String> prams) {
		return financialProductsDao.getPage("selectProductsList", "selectProductsCount", prams);
	}

	@Override
	public FinancialProducts getById(Integer id) {
		return financialProductsDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveFinancialProducts(FinancialProducts financialProducts) {
		return financialProductsDao.saveOrUpdate(financialProducts);
	}

	@Override
	public boolean updateFinancialProducts(FinancialProducts financialProducts) {
		return financialProductsDao.updateByPrimaryKeySelective(financialProducts);
	}

	@Override
	public FinancialProducts getProduct() {
		Map<String, String> params = new HashMap<String,String>();
		return financialProductsDao.select("selectProduct", params).get(0);
	}
}
