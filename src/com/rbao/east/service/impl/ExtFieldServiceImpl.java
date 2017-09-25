package com.rbao.east.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.ExtFieldDao;
import com.rbao.east.dao.ExtValueDao;
import com.rbao.east.entity.ExtField;
import com.rbao.east.entity.ExtValue;
import com.rbao.east.service.ExtFieldService;

@Service
@Transactional
public class ExtFieldServiceImpl implements ExtFieldService {

	@Autowired
	private ExtFieldDao extFieldDao;
	@Autowired
	private ExtValueDao extValueDao;

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return false;
	}

	@Override
	public boolean insert(ExtField extField) {
		return extFieldDao.insertSelective(extField);
	}

	@Override
	public boolean insertSelective(ExtField extField) {
		return extFieldDao.insertSelective(extField);
	}

	@Override
	public ExtField selectByPrimaryKey(Integer id) {
		return extFieldDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(ExtField extField) {
		return extFieldDao.updateByPrimaryKeySelective(extField);
	}

	@Override
	public boolean updateByPrimaryKey(ExtField extField) {
		return extFieldDao.updateByPrimaryKeySelective(extField);
	}

	/**  
	* @Name: getAllExtInfo
	* @Description: 根据borrowId查询信息
	* @Author: 肖世杰 
	* @Version: V1.00 
	* @Create Date: 2015-03-06 
	* @Parameters: 
		 *borrowId 表rb_ext_field表borrowId
	* @Return: List
	*/
	@Override
	public List getAllExtInfo(String fldInTable, Integer borrowId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fldInTable", fldInTable);    // 相当于rbborrow表Id 
		params.put("borrowId", borrowId);   // 借款标Id 
		List list = this.extFieldDao.getAllExtInfo(params); 
		return list;
	}

	@Override
	public boolean updateValue(List<ExtValue> list, Map<String, String> params) {
		
		String vBorrowId = params.get("borrowId");
		if(StringUtils.isNotBlank(vBorrowId)) {
			String fldTable = params.get("fFldInTable");   // table名称
			// 删除
			this.extValueDao.deleteByBorrowId(Integer.parseInt(vBorrowId),fldTable);
		}
		
		if(null != list && list.size() > 0) {
			Iterator<ExtValue> iter = list.iterator();
			while(iter.hasNext()) {
				ExtValue extValue = iter.next();
				this.extValueDao.insertSelective(extValue);   // 添加一条记录
			}
			return true;
		}else {
			return false;
		}
	}

}
