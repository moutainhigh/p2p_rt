package com.rbao.east.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.NationDao;
import com.rbao.east.entity.Nation;
import com.rbao.east.service.NationService;

@Service
@Transactional
public class NationServiceImpl implements NationService {
	private static Logger logger = LoggerFactory
			.getLogger(NationServiceImpl.class);

	@Autowired
	private NationDao nationDao;

	@Override
	public List<Nation> getNationList(Map<String, String> maps) {
		return nationDao.select("getNationList", maps);
	}

	@Override
	public Nation selectByPrimaryKey(Integer id) {
		return nationDao.selectByPrimaryKey(id);
	}

	@Override
	public int getCount(Map<String, String> maps) {
		return nationDao.getTotalCount("getCount", maps);
	}

	@Override
	public PageModel getNationPage(Map<String, String> maps) {
		return nationDao.getPage("getNationList", "getCount", maps);
	}

	@Override
	public void saveOrUpdate(Nation nation) {
		nationDao.saveOrUpdate(nation);
	}

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return nationDao.deleteByPrimaryKey(id);
	}

	@Override
	public int checkExist(Map<String, String> maps) {
		return nationDao.getTotalCount("checkExist", maps);
	}

}
