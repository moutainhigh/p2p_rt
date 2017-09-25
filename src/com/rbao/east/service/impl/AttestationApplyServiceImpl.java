package com.rbao.east.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AttestationApplyDao;
import com.rbao.east.entity.AttestationApply;
import com.rbao.east.entity.AttestationType;
import com.rbao.east.service.AttestationApplyService;
import com.rbao.east.service.AttestationTypeService;

@Service
@Transactional
public class AttestationApplyServiceImpl implements AttestationApplyService {

	@Autowired
	private AttestationApplyDao attestationApplyDao;
	@Autowired 
	private AttestationTypeService attestationTypeService;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		return attestationApplyDao.getPage("selectByEntity","selectTotalCount", paramsMap);
	}

	@Override
	public AttestationApply getById(Integer id) {
		return attestationApplyDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer id) {
		return attestationApplyDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(AttestationApply attestationApply) {
		return attestationApplyDao.updateByPrimaryKeySelective(attestationApply); 
	}

	@Override
	public boolean add(AttestationApply attestationApply) {
		boolean flag=false;
		try{
			//AttestationType type=new AttestationType();
			//type.setId(attestationApply.getAttestationTypeid());
			//证件类型
			AttestationType attestationType=this.attestationTypeService.getById(attestationApply.getAttestationTypeid());
			attestationApply.setAttestationApplyDatetime(new Date());
			attestationApply.setAttestationName(attestationType.getAttestationName());
			attestationApply.setAttestationScore(attestationType.getAttestationScore());
			attestationApply.setAttestationStatus(0);
			flag=attestationApplyDao.insertSelective(attestationApply); 
		}catch (Exception e) {
			flag=false;
			
			throw new RuntimeException("资料上传申请失败");
		}
		return flag;
	}

	@Override
	public List<AttestationApply> listAttestationApply(
			AttestationApply attestationApply) {
		return attestationApplyDao.select("selectList", attestationApply);
	}

	@Override
	public AttestationApply selectByuserIdAndType(AttestationApply attestationApply) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", attestationApply.getUserId());
		map.put("attestationTypeid", attestationApply.getAttestationTypeid());
		return this.attestationApplyDao.selectEntity("selectByEntity", map);
	}

	
	
}
