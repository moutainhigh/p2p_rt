package com.rbao.east.service;

import java.util.List;

import com.rbao.east.entity.SysSmsConfig;

	
public interface SysSmsConfigService {
/**
 * 保存
 * @param config
 * @return
 */
	public boolean save(SysSmsConfig config);
	/**
	 * 查询所有
	 * @return
	 */
	public List<SysSmsConfig> getAll();
	/**
	 * 
	* @Title: getSysSmsConfig
	* @Description: 查询短信配置信息
	* @return    SysSmsConfig 返回类型
	* @throws
	 */
	public SysSmsConfig getSysSmsConfig();
}
