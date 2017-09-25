package com.rbao.east.service;

import java.util.List;

import com.rbao.east.entity.SysLetterTemplates;

/**
 * 模板设置
 */

public interface SysLetterTemplatesService {
	/**
	 * 获取模板信息
	 * @return
	 */
	public List<SysLetterTemplates> getSysLetterTemplates();
	
	/**
	 * 保存模板信息
	 * @param letterTemplates
	 * @return
	 */
	public boolean saveSysLetterTemplates(SysLetterTemplates letterTemplates);
	
	/**
	 * 获取模板对象
	 * @return
	 */
	public SysLetterTemplates getSysLetterTemplate();
}
