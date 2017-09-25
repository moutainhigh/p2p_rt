package com.rbao.east.service;

import java.util.List;

import com.rbao.east.entity.Attach;

/**
 * 附件
 * */
public interface AttachService {

	/**
	 * 
	* @Title: add
	* @Description: 增加附件
	* @return void    返回类型
	* @throws
	 */
	public void add(Attach attach);
	/**
	 * 
	* @Title: save
	* @Description: 保存附件
	* @return void    返回类型
	* @throws
	 */
	public void save(List<Attach> attachs,Integer relateId,String tableName);
	
	
	public List<Attach> selectByAttach(Attach attach);

}
