package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.AccountDeduct;
/**
 * 费用扣除
 * */
public interface AccountDeductService {

	/**
	 * 
	* @Title: save
	* @Description: 保存记录
	* @return boolean    返回类型
	* @throws
	 */
	public boolean save(AccountDeduct accountDeduct);

	/**
	 * 
	* @Title: getAccountDeductList
	* @Description: 得到列表
	* @return PageModel    返回类型
	* @throws
	 */
	public PageModel getAccountDeductList(Map<String, String> paramsMap);

	/**
	 * 
	* @Title: getById
	* @Description: 得到单个信息
	* @return AccountDeduct    返回类型
	* @throws
	 */
	public AccountDeduct getById(Integer accountDeductId);

}
