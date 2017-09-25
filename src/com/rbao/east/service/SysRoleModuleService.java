package com.rbao.east.service;

import java.util.Map;

public interface SysRoleModuleService {
	/**
	 * 保存
	 * @param param
	 * @return
	 */
	public boolean save(Map<String, Object> param);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteByPrimaryKey(int id);

}
