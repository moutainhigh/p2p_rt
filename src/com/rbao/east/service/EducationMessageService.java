package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.EducationMessage;

/**
 * 教育信息
 * */	
public interface EducationMessageService {
	
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return   EducationMessage返回类型 
	* @throws
	 */
	public EducationMessage getById(Integer id);
	/**
	 * 
	* @Title: getByUserId
	* @Description: 根据用户id查询
	* @return    EducationMessage返回类型
	* @throws
	 */
	public EducationMessage getByUserId(Integer userId);
	
	/**
	 * 删除
	 * @param id 参数
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param educationMessage 参数
	 * @return
	 */
	public boolean save(EducationMessage educationMessage) ;
	/**
	 * 添加
	 * @param educationMessage 参数
	 * @return
	 */
	public boolean add(EducationMessage educationMessage) ;
	
}
