package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.BorrowRelatedDao;
import com.rbao.east.entity.BorrowRelated;
import com.rbao.east.service.BorrowRelatedService;
import com.rbao.east.utils.DesEncrypt;

@Service
@Transactional
public class BorrowRelatedServiceImpl implements BorrowRelatedService{

	
	@Autowired
	private BorrowRelatedDao borrowRelatedDao;
	
	
	
	public boolean saveOrUpdate(BorrowRelated borrowRelated){
		return borrowRelatedDao.saveOrUpdate(borrowRelated);
	}



	@Override
	public BorrowRelated findRecentlyEntity() {
		// TODO Auto-generated method stub
		return borrowRelatedDao.selectEntity("findRecentlyEntity",null);
	}
	
	@Override
	public BorrowRelated selectByPrimaryKey(Integer pk){
		return borrowRelatedDao.selectByPrimaryKey(pk);
	}



	@Override
	public PageModel getDHTPage(Map<String, String> paramsMap) {
		PageModel pm = borrowRelatedDao.getPage("getDHTPage",
				"getDHTPageCount", paramsMap);
		new DesEncrypt().encryptInList(pm.getList(),new String[]{"id","relatedId"}); //id加密
		return pm;
	}
	
}
