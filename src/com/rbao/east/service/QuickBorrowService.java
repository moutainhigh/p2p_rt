package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.QuickBorrow;
/**
 * 快速借款
 * */
public interface QuickBorrowService {
/**
 * 更新操作
 * @param qBorrow 参数
 */
	public void saveOrUpdate(QuickBorrow qBorrow);
	/**
	 * 分页
	 * @param params 参数
	 * @return
	 */
	public PageModel getPagedList(Map<String,String> params);
	/**
	 * 查询
	 * @param id 参数
	 * @return
	 */
	public QuickBorrow getById(Integer id);
	
	/**
	 * 待处理的个人借款数量
	 * @return Integer
	 */
	public Integer summaryDisposeCount();

}
