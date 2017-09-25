package com.rbao.east.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbao.east.dao.SysConfigParamDao;
import com.rbao.east.entity.SysConfigParams;
import com.rbao.east.service.SysConfigParamService;

@Service
public class SysConfigParamServiceImpl implements SysConfigParamService{

	@Autowired
	SysConfigParamDao cfgDao ;
	
	@Override
	public List<SysConfigParams> getAll() {
		return cfgDao.selectAll();
	}
	@Override
	public Map<String,String> getAllValueToMap(){
		Map<String,String> map = new HashMap<String,String>();
		for(SysConfigParams cfg : getAll()){
			map.put(cfg.getSysKey(), cfg.getSysValueAuto());
		}
		return map;
	}
	@Override
	public List<SysConfigParams> getByType(String type) {
		
		return cfgDao.select("selectByType", type);
	}

	@Override
	public boolean updateValue(Integer id, String value) {
		
		SysConfigParams dbCfg = cfgDao.selectByPrimaryKey(id);
		if(dbCfg == null){
			return false;
		}
		SysConfigParams cfg = new SysConfigParams();
		cfg.setId(id); 
		cfg.setInputType(dbCfg.getInputType());
		cfg.setSysValueAuto(value);
		
		return cfgDao.updateByPrimaryKeySelective(cfg);
		
	}

}
