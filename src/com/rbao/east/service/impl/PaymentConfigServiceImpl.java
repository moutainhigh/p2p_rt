package com.rbao.east.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.PaymentConfigDao;
import com.rbao.east.entity.PaymentConfig;
import com.rbao.east.service.PaymentConfigService;

@Service
@Transactional
public class PaymentConfigServiceImpl implements PaymentConfigService{
	
	@Autowired
	private PaymentConfigDao paymentConfigDao;

	@Override
	public PageModel getList(PaymentConfig entity, Integer curPage) {
		PageModel pageModel=new  PageModel(curPage);  
		return paymentConfigDao.getPage("selectByEntity","selectTotalCount", entity, pageModel);
	}

	@Override
	public PaymentConfig getById(Integer id) {
		return paymentConfigDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer id) {
		return paymentConfigDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(PaymentConfig paymentConfig) {
					
		return paymentConfigDao.saveOrUpdate(paymentConfig);
	}

	@Override
	public PageModel getpagedList(Map<String, String> paramsMap) {
		return paymentConfigDao.getPage("selectByEntity","selectTotalCount",paramsMap);
	}
	@Override
	public List<PaymentConfig> getOnlineList(Map<String, String> paramsMap) {
		return paymentConfigDao.select("selectByEntity", paramsMap);
	}
	
	@Override
	public List<PaymentConfig> getAll() {
		return paymentConfigDao.selectAll();
	}
	
	@Override
	public boolean update(PaymentConfig paymentConfig){
		return paymentConfigDao.updateByPrimaryKeySelective(paymentConfig);
	}

	@Override
	public int selectTotalCount(Map<String, String> paramsMap) {
		
		return paymentConfigDao.getTotalCount("selectTotalCount", paramsMap);
	}

	@Override
	public PaymentConfig getByParam(Map<String, String> params) {
		return this.paymentConfigDao.selectEntity("selectByEntity", params);
	}
	
}
