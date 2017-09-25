package com.rbao.east.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.NoticeTypeDao;
import com.rbao.east.entity.NoticeType;
import com.rbao.east.service.NoticeTypeService;

@Service
@Transactional
public class NoticeTypeServiceImpl implements NoticeTypeService{

	@Autowired
	private NoticeTypeDao noticeTypeDao;
	
	@Override
	public PageModel getpagedList(Map<String, String> paramsMap) {
		return noticeTypeDao.getPage("selectByEntity","selectTotalCount", paramsMap);
	}

	@Override
	public NoticeType getById(Integer id) {
		return noticeTypeDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer id) {
		return noticeTypeDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(NoticeType noticeType) {
		return noticeTypeDao.saveOrUpdate(noticeType);
	}

	@Override
	public List<NoticeType> getAll() {
		return noticeTypeDao.selectAll();
	}

	@Override
	public int selectTotalCount(Map<String, String> paramsMap) {
		return noticeTypeDao.getTotalCount("selectTotalCount", paramsMap);
	}
}
