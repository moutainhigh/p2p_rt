package com.rbao.east.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.BorrowTypeDao;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.service.BorrowTypeService;

@Service
@Transactional
public class BorrowTypeServiceImpl implements BorrowTypeService{
	
	@Autowired
	private BorrowTypeDao borrowTypeDao;

	@Override
	public boolean saveBorrowType(BorrowType borrowType) {
		return borrowTypeDao.saveOrUpdate(borrowType);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BorrowType> getByTypeCodes(String[] codes){
		Map m = new HashMap();
		m.put("inCodes", codes);
		return borrowTypeDao.select("selectByCodes", m);
	}

	@Override
	public BorrowType getBorrowTypeById(Integer id) {
		return borrowTypeDao.selectByPrimaryKey(id);
	}

	@Override
	public List<BorrowType> getBorrowsTypeByParam(Integer frontPublish,Integer adminPublish,Integer status) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("frontPublish", frontPublish);
		paramsMap.put("adminPublish", adminPublish);
		paramsMap.put("status", status);
		return borrowTypeDao.select("selectBorrowTypeByParams", paramsMap);
	}

	@Override
	public boolean deleteById(Integer id) {
		return borrowTypeDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<BorrowType> getAllBorrowTypes() {
		return borrowTypeDao.selectAll();
	}

	@Override
	public PageModel getAllBorrowType(Map<String, String> paramsMap) {
		return borrowTypeDao.getPage("selectByEntity", "selectTotalCount", paramsMap);
	}

	@Override
	public Integer getByBorrowTypeCode(String id, String code) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("code", code);
		return this.borrowTypeDao.getTotalCount("selectTotalCount", map);
	}

	@Override
	public BorrowType getBorrowTypeByCode(String code) {
		// TODO Auto-generated method stub
		return borrowTypeDao.selectEntity("getBorrowTypeByCode", code);
	}

	@Override
	public List<BorrowType> getBorrowTypesNotDing(String dingCode) {
		// TODO Auto-generated method stub
		Map<String,String> params=new HashMap<String, String>();
		params.put("dingCode", dingCode);
		return borrowTypeDao.select("selectBorrowTypeNotDing", params);
	}
	@Override
	public BorrowType getBorrowTypeByName(String name) {
		
		return borrowTypeDao.selectEntity("getBorrowTypeByName", name);
	}
	
}
