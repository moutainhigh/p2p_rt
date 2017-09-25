package com.rbao.east.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AttestationTypeDao;
import com.rbao.east.entity.AttestationType;
import com.rbao.east.service.AttestationTypeService;

@Service
@Transactional
public class AttestationTypeServiceImpl implements AttestationTypeService {

	@Autowired
	private AttestationTypeDao attestationTypeDao;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return attestationTypeDao.getPage("selectByEntity","selectTotalCount", paramsMap);
		
	}

	@Override
	public AttestationType getById(Integer id) {
		return attestationTypeDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer id) {
		return attestationTypeDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(AttestationType attestationType) {
		return attestationTypeDao.updateByPrimaryKeySelective(attestationType); 
	}

	@Override
	public boolean add(AttestationType attestationType) {
		return attestationTypeDao.insertSelective(attestationType); 
	}

	@Override
	public List<AttestationType> listAttestationType(
			AttestationType attestationType) {
		return attestationTypeDao.select("selectList", attestationType);
	}

	@Override
	public List<AttestationType> selectTypeByUserId(Integer userId) {
		return this.attestationTypeDao.select("selectTypeByUserId", userId);
	}
	
	
}
