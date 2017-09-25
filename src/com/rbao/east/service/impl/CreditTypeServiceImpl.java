package com.rbao.east.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.CreditTypeDao;
import com.rbao.east.entity.CreditType;
import com.rbao.east.service.CreditTypeService;

@Service
@Transactional
public class CreditTypeServiceImpl implements CreditTypeService {

	@Autowired
	private CreditTypeDao creditTypeDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return creditTypeDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public CreditType getById(Integer id) {
		return creditTypeDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer id) {
		return creditTypeDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(CreditType creditType) {
		return creditTypeDao.updateByPrimaryKeySelective(creditType); 
	}
	
	@Override
	public boolean add(CreditType creditType) {
		return creditTypeDao.insertSelective(creditType); 
	}

	//根据实体查询
	@Override
	public CreditType getCreditType(String  creditName,String creditCode) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("creditName", creditName);
		map.put("creditCode", creditCode);
		return this.creditTypeDao.selectEntity("selectEntity", map);
	}

	@Override
	public Integer getbyCode(CreditType creditType) {
		List<CreditType> list= creditTypeDao.selectByEntity(creditType);
		return list.size();
	}
	
}
