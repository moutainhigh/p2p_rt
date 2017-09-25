package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.RecommendAccountDao;
import com.rbao.east.entity.RecommendAccount;
import com.rbao.east.service.RecommendAccountService;

/**
 * 
 * @author yan
 *
 */
@Service
public class RecommendAccountServiceImpl implements RecommendAccountService {
	
	@Autowired
	private RecommendAccountDao recommendAccountDao;

	@Override
	public boolean saveOrUpdate(RecommendAccount recommendAccount) {
		return this.recommendAccountDao.saveOrUpdate(recommendAccount);
	}

	@Override
	public RecommendAccount selectByUserId(Map<String,Object> params) {
		return this.recommendAccountDao.selectEntity("selectByUserId", params);
	}

	@Override
	public PageModel getPage(Map<String, String> params) {
		return this.recommendAccountDao.getPage("selectByEntity", params);
	}

	@Override
	public BigDecimal getRewardsByUserId(Integer id) {
		return (BigDecimal) this.recommendAccountDao.selects("getRewardsByUserId", id).get(0);
	}

}
