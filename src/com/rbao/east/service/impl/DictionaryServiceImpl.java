package com.rbao.east.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.DictionaryDao;
import com.rbao.east.entity.Dictionary;
import com.rbao.east.service.DictionaryService;

@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService{
	
	@Autowired
	private DictionaryDao dictionaryDao;

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return this.dictionaryDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean insert(Dictionary record) {
		return this.dictionaryDao.insertSelective(record);
	}

	@Override
	public boolean insertSelective(Dictionary record) {
		return this.dictionaryDao.insertSelective(record);
	}

	@Override
	public Dictionary selectByPrimaryKey(Integer id) {
		return this.dictionaryDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(Dictionary record) {
		return this.updateByPrimaryKeySelective(record);
	}

	@Override
	public boolean updateByPrimaryKey(Dictionary record) {
		return this.dictionaryDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Dictionary> selectByParentId(Map<String, String> params) {
		return this.dictionaryDao.select("selectByParentId", params);
	}

	@Override
	public List<Dictionary> selectByCon(Map<String, String> params) {
		return this.dictionaryDao.select("selectByCon", params);
	}

	@Override
	public boolean checkCodeExist(Map<String, String> params) {
		boolean flag = true;
		int num = this.dictionaryDao.getTotalCount("checkCodeExist", params);
		if (num<1) {
			flag = false;
		}
		return flag;
	}

	@Override
	public PageModel getPageDic(Map<String, String> params) {
		return this.dictionaryDao.getPage("selectByCon", "getCountByCon", params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getTreeList() {
		return this.dictionaryDao.selectAll();
	}

	@Override
	public int getCountByParentId(Map<String, String> params) {
		return this.dictionaryDao.getTotalCount("getCountByParentId", params);
	}

	@Override
	public void saveOrUpdate(Dictionary dictionary) {
		this.dictionaryDao.saveOrUpdate(dictionary);
	}

	@Override
	public Dictionary getDicByCode(String dicCode) {
		return this.dictionaryDao.selectEntity("getDicByCode", dicCode);
	}

}
