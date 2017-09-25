package com.rbao.east.service;

import java.util.List;

import com.rbao.east.entity.SysFeesRate;
/**
 * 
 * @author xiangxiaoyan
 *	资金费率
 */

public interface SysFeesRateService {
	/**
	 * 更新操作
	 * @param entity
	 * @return
	 */
	public boolean saveOrUpdate(SysFeesRate entity);
	/**
	 * 
	* @Title: getAll 
	* @Description: 查询所有
	* @return   List<SysFeesRate>返回类型 
	* @throws
	 */
	public List<SysFeesRate> getAll();
	/**
	 * 
	* @Title: getSysFeesRate
	* @Description: 查询所有费率
	* @return   SysFeesRate 返回类型 
	* @throws
	 */
	public SysFeesRate getSysFeesRate();
	
}
