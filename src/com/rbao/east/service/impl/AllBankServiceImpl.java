package com.rbao.east.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AllBankDao;
import com.rbao.east.entity.AllBank;
import com.rbao.east.service.AllBankService;



@Service
@Transactional
public class AllBankServiceImpl implements AllBankService {

	@Autowired 
	private AllBankDao allBankDao;
	@Override
	
	public List<AllBank> getAllList() {
		
		return this.allBankDao.selectAll();
	}
	@Override
	
	public PageModel getAllBankList(Map<String, String> paramsMap) {
		return this.allBankDao.getPage("selectByEntity", "selectTotalCount", paramsMap);
	}
	@Override
	
	public boolean saveOrUpdate(AllBank allBank) {
		return this.allBankDao.saveOrUpdate(allBank);
	}
	@Override
	
	public AllBank getById(Integer id) {
		return this.allBankDao.selectByPrimaryKey(id);
	}
	@Override
	
	public boolean deleteById(Integer id) {
		return this.allBankDao.deleteByPrimaryKey(id);
	}
	@Override
	
	public Integer getByBankCode(String allBankId, String bankCode) {
		Map map = new HashMap();
		map.put("BankId", allBankId);
		map.put("bankCode", bankCode);
		
		return this.allBankDao.getTotalCount("selectTotalCount", map);
	}

	

	

	
	
}
