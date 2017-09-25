package com.rbao.east.service.discussion.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.DiscussionConfigDao;
import com.rbao.east.entity.DiscussionConfig;
import com.rbao.east.service.DiscussionConfigService;

@Service
@Transactional
public class DiscussionConfigServiceImpl implements DiscussionConfigService {

	@Autowired
	private DiscussionConfigDao configDao;

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return configDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean saveOrUpdate(DiscussionConfig record) {
		return configDao.saveOrUpdate(record);
	}

	@Override
	public DiscussionConfig selectByPrimaryKey(Integer id) {
		return configDao.selectByPrimaryKey(id);
	}

	@Override
	public PageModel getPage(Map<String, String> params) {
		return configDao.getPage("getConfigs", "getConfigsCount", params);
	}

	@Override
	public Integer getConfigsCount(Map<String, String> params) {
		return configDao.getTotalCount("getConfigsCount", params);
	}

	@Override
	public DiscussionConfig getValue(String disKey) {
		return configDao.selectEntity("getValue", disKey);
	}

}
