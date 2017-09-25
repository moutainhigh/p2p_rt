package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.QuickBorrowDao;
import com.rbao.east.entity.QuickBorrow;
import com.rbao.east.service.QuickBorrowService;

@Service
@Transactional
public class QuickBorrowServiceImpl implements QuickBorrowService {

	@Autowired
	private QuickBorrowDao quickBorrowDao ;
	
	@Override
	public void saveOrUpdate(QuickBorrow qBorrow) {
		quickBorrowDao.saveOrUpdate(qBorrow);
	}

	@Override
	public PageModel getPagedList(Map<String, String> params) {
		return quickBorrowDao.getPage("selectPage", "selectCount", params);
	}

	@Override
	public QuickBorrow getById(Integer id) {
		return this.quickBorrowDao.selectByPrimaryKey(id);
	}
	
	/**
	 * 待处理的个人借款数量
	 * @return Integer
	 */
	@Override
	public Integer summaryDisposeCount() {
		return this.quickBorrowDao.summaryDisposeCount(QuickBorrow.STATUS_NEW);
	}

}
