package com.rbao.east.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AreaDao;
import com.rbao.east.entity.Area;
import com.rbao.east.service.AreaService;

@Service
@Transactional
public class AreaServiceImpl implements AreaService{

	@Autowired
	private AreaDao areaDao;
	
	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return areaDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean insert(Area record) {
		 return areaDao.insert("", record);
	}

	@Override
	public boolean insertSelective(Area record) {
		return areaDao.insertSelective(record);
	}

	@Override
	public Area selectByPrimaryKey(Integer id) {
		return areaDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(Area record) {
		return areaDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public boolean updateByPrimaryKey(Area record) {
		return false;
	}

	@Override
	public List<Area> selectByParentId(Integer parentId) {
		return areaDao.select("selectByParentId", parentId);
	}

	@Override
	public List<Area> selectByCon(Map<String, String> map) {
		return areaDao.select("selectByCon", map);
	}

	@Override
	public int getCountByCon(Map<String, String> map) {
		return areaDao.getTotalCount("getCountByCon", map);
	}

	@Override
	public int getCountByParentId(Integer parentId) {
		return areaDao.getTotalCount("getCountByParentId", parentId);
	}

	@Override
	public PageModel getPageAreas(Map<String, String> map) {
		return areaDao.getPage("selectByCon", "getCountByCon", map);
	}

	@Override
	public List getTreeList() {
		return areaDao.selectAll();
	}

	@Override
	public void saveOrUpdate(Area area) {
		areaDao.saveOrUpdate(area);
	}

	@Override
	public int checkAreaExist(Map<String, String> maps) {
		return this.areaDao.getTotalCount("checkAreaExist", maps);
	}

}
