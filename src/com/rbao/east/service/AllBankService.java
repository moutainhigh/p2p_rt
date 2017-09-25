package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.AllBank;
/**
 * 银行
 * */
public interface AllBankService {

	/**
	 * 
	* @Title: getAllList
	* @Description: 银行卡列表
	* @return List<AllBank>    返回类型
	* @throws
	 */
	public List<AllBank> getAllList();

	/**
	 * 
	* @Title: getAllBankList
	* @Description: 银行卡数据集
	* @return PageModel    返回类型
	* @throws
	 */
	public PageModel getAllBankList(Map<String, String> paramsMap);

	/**
	 * 
	* @Title: saveOrUpdate
	* @Description: 更新银行
	* @return boolean    返回类型
	* @throws
	 */
	public boolean saveOrUpdate(AllBank allBank);

	/**
	 * 
	* @Title: getById
	* @Description: 得到单个银行卡
	* @return AllBank    返回类型
	* @throws
	 */
	public AllBank getById(Integer id);

	/**
	 * 
	* @Title: deleteById
	* @Description: 删除银行卡
	* @return boolean    返回类型
	* @throws
	 */
	public boolean deleteById(Integer id);

	/**
	 * 
	* @Title: getByBankCode
	* @Description: 银行卡数目
	* @return Integer    返回类型
	* @throws
	 */
	public Integer getByBankCode(String allBankId, String bankCode);


}
