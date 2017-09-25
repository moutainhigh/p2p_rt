package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.BorrowCollection;
/**
 * 催收
 * */
public interface BorrowCollectionService {
	/**
	 * 获取催收列表
	 * @param params
	 * @return 分页数据
	 */
	public PageModel getBorrowCollectionList(Map<String,String> params);
	/**
	 * 
	* @Title: findBorrowCollectionById
	* @Description: 返回列表
	* @return List<Map<String,Object>>    返回类型
	* @throws
	 */
	public List<Map<String, Object>> findBorrowCollectionById(Map<String,String> params);
	/**
	 * 
	* @Title: addBorrowCollection
	* @Description: 增加催收
	* @return void    返回类型
	* @throws
	 */
	public void addBorrowCollection(BorrowCollection bc);
	/**
	 * 
	* @Title: toRepayExcel
	* @Description: 导出
	* @return List<Map<String,Object>>    返回类型
	* @throws
	 */
	public List<Map<String, Object>> toRepayExcel(Map<String, String> params);
}
