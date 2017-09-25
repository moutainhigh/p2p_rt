package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.UserBank;

/**
 * 用户银行
 * */	
public interface UserBankService {
/**
 * 分页
 * @param paramsMap
 * @return
 */
	public PageModel getUserBankList(Map<String,String> paramsMap);
/**
 * 更新操作
 * @param userBank
 * @return
 */
	public boolean saveOrUpdate(UserBank userBank);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return  UserBank 返回类型  
	* @throws
	 */
	public UserBank getById(Integer id);
/**
 * 删除
 * @param userBankId
 * @return
 */
	public boolean deleteById(Integer userBankId);
	/**
	 * 
	* @Title: getByUserId
	* @Description: 根据用户id查询
	* @return  UserBank 返回类型  
	* @throws
	 */
	public UserBank getByUserId(Map<String, String> paramsMap);
}
