package com.rbao.east.service.discussion.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.TagDao;
import com.rbao.east.entity.Tag;
import com.rbao.east.service.TagService;

@Service
@Transactional
public class TagServiceImpl implements TagService {

	@Autowired
	private TagDao tagDao;

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return this.tagDao.deleteByPrimaryKey(id);
	}

	@Override
	public Tag selectByPrimaryKey(Integer id) {
		return this.tagDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveOrUpdate(Tag tag) {
		return this.tagDao.saveOrUpdate(tag);
	}

	@Override
	public List<Tag> getTags(Map<String, String> params) {
		return this.tagDao.select("getTags", params);
	}

	@Override
	public Integer getTagsCount(Map<String, String> params) {
		return this.tagDao.getTotalCount("getTagsCount", params);
	}

	@Override
	public PageModel getPage(Map<String, String> params) {
		return this.tagDao.getPage("getTags", "getTagsCount", params);
	}

	@Override
	public List<Tag> checkCode(Map<String, String> params) {
		return this.tagDao.select("checkCode", params);
	}

}
