package com.rbao.east.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.NoticeDao;
import com.rbao.east.entity.Notice;
import com.rbao.east.service.NoticeService;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDao noticeDao;
	
	@Override
	public Notice getById(Integer id) {
		return noticeDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer id) {
		return noticeDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(Notice notice) {
		notice.setNoticeCode(notice.getNoticeCode().toLowerCase());
		return noticeDao.saveOrUpdate(notice);
	}

	@Override
	public PageModel getpagedList(Map<String, String> paramsMap) {
		return noticeDao.getPage("selectByEntity","selectTotalCount", paramsMap);
	}

	@Override
	public int selectTotalCount(Map<String, String> paramsMap) {
		return noticeDao.getTotalCount("selectTotalCount", paramsMap);
	}

	@Override
	public Notice getByNoticeCode(String noticeCode) {
		Notice notice = new Notice();
		notice.setNoticeCode(noticeCode.toLowerCase());
		return noticeDao.selectEntity("selectByNoticeCode", notice);
	}

	@Override
	public List getNotices(Map<String, String> paramsMap) {
		return noticeDao.select("selectNotices", paramsMap);
	}
	
}
