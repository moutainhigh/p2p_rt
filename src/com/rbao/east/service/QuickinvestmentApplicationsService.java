package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.QuickinvestmentApplications;

/**
 * 预约理财产品
 */
public interface QuickinvestmentApplicationsService {

	/**
	 * 分页显示预约信息
	 * @param paramsMap
	 * @return
	 */
	public PageModel getQuickinvestmentApplicationsList(Map<String, String> paramsMap);
	
	/**
	 * 根据Id查看
	 * @param id
	 * @return
	 */
	public QuickinvestmentApplications getById(Integer id);
	
	/**
	 * 保存
	 * @param quickinvestmentApplications
	 * @return
	 */
	public boolean saveQuickinvestmentApplications(QuickinvestmentApplications quickinvestmentApplications);
	
	/**
	 * 修改
	 * @param quickinvestmentApplications
	 * @return
	 */
	public boolean updateQuickinvestmentApplications(QuickinvestmentApplications quickinvestmentApplications);
}
