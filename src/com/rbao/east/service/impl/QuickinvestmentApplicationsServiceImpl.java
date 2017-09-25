package com.rbao.east.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.QuickinvestmentApplicationsDao;
import com.rbao.east.entity.QuickinvestmentApplications;
import com.rbao.east.service.FinancialProductsService;
import com.rbao.east.service.QuickinvestmentApplicationsService;

@Service
@Transactional
public class QuickinvestmentApplicationsServiceImpl implements
		QuickinvestmentApplicationsService {

	private static Logger logger = LoggerFactory.getLogger(QuickinvestmentApplicationsServiceImpl.class);
	
	@Autowired
	private QuickinvestmentApplicationsDao quickinvestmentApplicationsDao;
	@Autowired
	private FinancialProductsService financialProductsService;
	
	@Override
	public PageModel getQuickinvestmentApplicationsList(
			Map<String, String> paramsMap) {
		return quickinvestmentApplicationsDao.getPage("selectQuickInvestmentList", "selectQuickInvestmentCount", paramsMap);
	}

	@Override
	public QuickinvestmentApplications getById(Integer id) {
		return quickinvestmentApplicationsDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveQuickinvestmentApplications(
			QuickinvestmentApplications quickinvestmentApplications) {
		if(quickinvestmentApplications.getInvestProductsId() == null || quickinvestmentApplications.getInvestProductsId().equals("")){
			Integer investProductsId = financialProductsService.getProduct().getId();
			quickinvestmentApplications.setInvestProductsId(investProductsId);
		}
		return quickinvestmentApplicationsDao.saveOrUpdate(quickinvestmentApplications);
	}

	@Override
	public boolean updateQuickinvestmentApplications(
			QuickinvestmentApplications quickinvestmentApplications) {
		return quickinvestmentApplicationsDao.updateByPrimaryKeySelective(quickinvestmentApplications);
	}
}
