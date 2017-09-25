/**
 * 
 */
package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.RelaseBankDao;
import com.rbao.east.entity.RelaseCard;
import com.rbao.east.service.RelaseBankService;

/**
 * @author zhuguoqiang
 *
 */
@Service
@Transactional
public class RelaseBankServiceImpl implements RelaseBankService {

	@Autowired
	private RelaseBankDao relaseBankDao;
	
	@Override
	public boolean relaseBank(RelaseCard relaseCard) {
		return relaseBankDao.saveOrUpdate(relaseCard);
	}

	@Override
	public PageModel getRelaseBankList(Map<String, String> paramsMap) {
		
		return relaseBankDao.getPage("selectRelaseBankList", "selectTotalCount",paramsMap);
	}

	@Override
	public RelaseCard getById(Integer id){
		return relaseBankDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveOrUpdate(RelaseCard relaseCard) {
		return relaseBankDao.saveOrUpdate(relaseCard);
	}

	@Override
	public RelaseCard getByParam(Map<String, String> paramsMap) {
		return (RelaseCard) relaseBankDao.getObject("selectByParam", paramsMap);
	}
	
}
