package com.rbao.east.service;

import java.util.List;

import com.rbao.east.entity.SysConfig;
/**
 * 
 * @author xiangxiaoyan
 *
 */
	
public interface SysConfigService {
	/**
	 * 
	* @Title: getAll
	* @Description: 查询所有
	* @return     List<SysConfig>返回类型
	* @throws
	 */
	public List<SysConfig> getAll();
	/**
	 * 更新操作
	 * @param entity
	 * @return
	 */
	public boolean saveOrUpdate(SysConfig entity);
}
