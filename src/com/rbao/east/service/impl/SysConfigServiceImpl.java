package com.rbao.east.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.SysConfigDao;
import com.rbao.east.entity.SysConfig;
import com.rbao.east.service.SysConfigService;
/**
 * 
 * @author xiangxiaoyan
 *
 */
@Service
@Transactional
public class SysConfigServiceImpl implements SysConfigService {

	@SuppressWarnings("unused")
	@Autowired
	private SysConfigDao sysConfigDao;
	@Override
	public List<SysConfig> getAll() {
		return this.sysConfigDao.selectAll();
	}

	@Override
	public boolean saveOrUpdate(SysConfig entity) {
		return this.sysConfigDao.saveOrUpdate(entity);
	}

}
