package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.CreditLog;

/**
 * 积分日志
 * */	
public interface CreditLogService {
	/**
	 * 
	* @Title: getPagedList
	* @Description: 分页	
	* @return  PageModel返回类型  
	* @throws
	 */
	public PageModel getPagedList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: id查询
	* @return    CreditLog返回类型
	* @throws
	 */
	public CreditLog getById(Integer id);
	/**
	 * 
	* @Title: deleteById
	* @Description: 删除
	* @return boolean返回类型   
	* @throws
	 */
	public boolean deleteById(Integer id);
	/**
	 * 
	* @Title: save
	* @Description: 保存	 
	* @return    Boolean 返回类型
	* @throws
	 */
	public boolean save(CreditLog creditLog) ;
	/**
	 * 
	* @Title: add	
	* @Description: 添加	
	* @return    boolean返回类型
	* @throws
	 */
	public boolean add(CreditLog creditLog) ;
	//通过userId查询
	public List<CreditLog> getByUserId(Integer userId);
	/**
	 * 添加积分
	 * @param creditCode积分code
	 * @param userId用户Id
	 * @param loginUserId当前登录用户
	 * @return
	 */
	public boolean addCreditLog(String creditCode, Integer userId,Integer loginUserId,Map<String,Object>... params);
	
	/**
	 * 添加积分
	 * @param creditCode积分code
	 * @param userId用户Id
	 * @return
	 */
	public boolean addCreditLog(String creditCode, Integer userId,Map<String,Object>... params);
	/**
	 * 
	* @Title: getPagedListNew
	* @Description: 分页 
	* @return    PageModel 返回类型
	* @throws
	 */
	public PageModel getPagedListNew(Map<String, String> paramsMap);
}
