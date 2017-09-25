package com.rbao.east.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.SysLetterTemplatesDao;
import com.rbao.east.entity.SysLetterTemplates;
import com.rbao.east.service.SysLetterTemplatesService;

@Service
@Transactional
public class SysLetterTemplatesServiceImpl implements SysLetterTemplatesService{

	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SysLetterTemplatesDao sysLetterTemplatesDao;
	
	@Override
	public List<SysLetterTemplates> getSysLetterTemplates() {
		return sysLetterTemplatesDao.selectAll();
	}

	@Override
	public boolean saveSysLetterTemplates(SysLetterTemplates letterTemplates) {
		return sysLetterTemplatesDao.saveOrUpdate(letterTemplates);
	}

	@Override
	public SysLetterTemplates getSysLetterTemplate() {
		List<SysLetterTemplates> list=this.getSysLetterTemplates();
		if(list.size()==0){
			return new SysLetterTemplates();
		}else{
			return list.get(0);
		}
	}
	
}
