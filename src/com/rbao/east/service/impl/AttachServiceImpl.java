package com.rbao.east.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.AttachDao;
import com.rbao.east.entity.Attach;
import com.rbao.east.service.AttachService;
@Service
@Transactional
public class AttachServiceImpl implements AttachService {

	@Autowired
	private AttachDao attachDao;
	
	@Override
	public void add(Attach attach) {
		attachDao.insertSelective(attach);
	}

	/*
	 * 多个附件上传
	 * 先删后加
	 */
	@Override
	public void save(List<Attach> attachs, Integer relateId, String tableName) {
		Attach delAtt = new Attach();
		delAtt.setAttachRelateId(relateId);
		delAtt.setAttachTablename(tableName);
		attachDao.delete("deleteByContentIdAndTableName", delAtt);
		
		for(Attach att : attachs){
			att.setAttachTablename(tableName);
			att.setAttachRelateId(relateId);
			add(att);
		}
		
	}


	@Override
	public List<Attach> selectByAttach(Attach attach) {
		return this.attachDao.select("selectByAttach", attach);
	}



	
	
}
