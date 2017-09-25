package com.rbao.east.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.SysFeesRateDao;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.service.SysFeesRateService;
/**
 * 
 * @author xiangxiaoyan
 *
 */
@Service
@Transactional
public class SysFeesRateServiceImpl implements SysFeesRateService{
	
	@Autowired
	private SysFeesRateDao sfrDao;

	@Override
	public List<SysFeesRate> getAll() {
		return this.sfrDao.selectAll();
	}

	@Override
	public boolean saveOrUpdate(SysFeesRate entity) {
		return this.sfrDao.saveOrUpdate(entity);
	}

	@Override
	public SysFeesRate getSysFeesRate() {
		List<SysFeesRate> feesList = getAll();
		if(feesList.size()==0){
			return new SysFeesRate();
		}else{
			return feesList.get(0);
		}
	}

}
