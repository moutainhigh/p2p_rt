package com.rbao.east.service;

import java.util.List;

import com.rbao.east.entity.SysSmtpConfig;
/**
 * 
 * @author xiangxiaoyan
 *
 */
	
public interface SysSmtpConfigService {
/**
 * 查询所有
 * @return
 */
	public List<SysSmtpConfig> getAll();
	/**
	 * 更新操作
	 * @param entity
	 * @return
	 */
	public boolean saveOrUpdate(SysSmtpConfig entity);
	/**
	 * 
	* @Title: getSysSmtpConfig
	* @Description: 得到smtp配置信息
	* @return    SysSmtpConfig 返回类型
	* @throws
	 */
	public SysSmtpConfig getSysSmtpConfig();
}
