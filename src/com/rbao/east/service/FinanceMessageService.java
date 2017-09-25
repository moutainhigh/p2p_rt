package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.FinanceMessage;

/**
 * 理财信息
 * */	
public interface FinanceMessageService {
	/**
	 * 分页
	 * @param paramsMap
	 * @return
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById 
	* @Description: 根据id查询
	* @return   FinanceMessage 返回类型 
	* @throws
	 */
	public FinanceMessage getById(Integer id);
	/**
	 * 
	* @Title: getByUserId
	* @Description: 根据用户id查询
	* @return FinanceMessage 返回类型   
	* @throws
	 */
	public FinanceMessage getByUserId(Integer userId);
	/**
	 * 
	* @Title: deleteById
	* @Description: 删除
	* @return    boolean返回类型
	* @throws
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param financeMessage 参数
	 * @return
	 */
	public boolean save(FinanceMessage financeMessage) ;
/**
 * 添加
 * @param financeMessage 参数
 * @return
 */
	public boolean add(FinanceMessage financeMessage) ;
	
}
