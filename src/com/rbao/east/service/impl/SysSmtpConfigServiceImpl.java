package com.rbao.east.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.SysSmtpConfigDao;
import com.rbao.east.entity.SysSmtpConfig;
import com.rbao.east.service.SysSmtpConfigService;
/**
 * 
 * @author xiangxiaoyan
 *
 */
@Service
@Transactional
public class SysSmtpConfigServiceImpl implements SysSmtpConfigService{

	@Autowired
	private SysSmtpConfigDao sysSmtpConfigDao;
	@Override
	public List<SysSmtpConfig> getAll() {
		return this.sysSmtpConfigDao.selectAll();
	}

	@Override
	public boolean saveOrUpdate(SysSmtpConfig entity) {
		return this.sysSmtpConfigDao.saveOrUpdate(entity);
	}

	/**
	 * 获取SysSmtpConfig对象
	 */
	@Override
	public SysSmtpConfig getSysSmtpConfig() {
		List<SysSmtpConfig> list=this.getAll();
		if(list.size()==0){
			return new SysSmtpConfig();
		}else
		return list.get(0);
	}

}
