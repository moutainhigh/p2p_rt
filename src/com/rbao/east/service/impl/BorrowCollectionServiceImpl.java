package com.rbao.east.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.BorrowCollectionDao;
import com.rbao.east.entity.BorrowCollection;
import com.rbao.east.service.BorrowCollectionService;


@Service
@Transactional
public class BorrowCollectionServiceImpl implements BorrowCollectionService {
	@Autowired
	private BorrowCollectionDao borrowCollectionDao;
	
	@Override
	public PageModel getBorrowCollectionList(Map<String, String> params) {
		return borrowCollectionDao.getPage("findBorrowCollection", params);
	}

	@Override
	public List<Map<String, Object>> findBorrowCollectionById(
			Map<String, String> params) {
		return borrowCollectionDao.selects("findBorrowCollectionById", params);
	}

	@Override
	public void addBorrowCollection(BorrowCollection bc) {
		borrowCollectionDao.insert("insert", bc);
	}

	@Override
	public List<Map<String, Object>> toRepayExcel(Map<String, String> params) {

		return borrowCollectionDao.selects("findBorrowCollection", params);
	}	

}
