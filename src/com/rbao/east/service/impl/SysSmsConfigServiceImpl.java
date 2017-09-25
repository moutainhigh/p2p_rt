package com.rbao.east.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.SysSmsConfigDao;
import com.rbao.east.entity.SysSmsConfig;
import com.rbao.east.service.SysSmsConfigService;

@Service
@Transactional
public class SysSmsConfigServiceImpl implements SysSmsConfigService{

	@Autowired
	private SysSmsConfigDao sysSmsConfigDao;
	
	@Override
	public boolean save(SysSmsConfig config) {
		return sysSmsConfigDao.saveOrUpdate(config);
	}

	@Override
	public List<SysSmsConfig> getAll() {
		return sysSmsConfigDao.selectAll();
	}

	/**
	 * 获取SysSmsConfig对象
	 */
	@Override
	public SysSmsConfig getSysSmsConfig() {
		List<SysSmsConfig> list=this.getAll();
		if(list.size()==0){
			return new SysSmsConfig();
		}else{
			return list.get(0);
		}
		
	}
	
}
