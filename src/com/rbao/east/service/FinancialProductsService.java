package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.FinancialProducts;

/**
 * 理财产品
 */

public interface FinancialProductsService {

	/**
	 * 分页查看理财产品信息
	 * @param prams
	 * @return
	 */
	public PageModel getFinancialProductsList(Map<String, String> prams);
	
	/**
	 * 根据Id查看理财产品信息
	 * @param id
	 * @return
	 */
	public FinancialProducts getById(Integer id);
	
	/**
	 * 保存理财产品信息
	 * @param financialProducts
	 * @return
	 */
	public boolean saveFinancialProducts(FinancialProducts financialProducts);
	
	/**
	 * 修改理财产品信息
	 * @param financialProducts
	 * @return
	 */
	public boolean updateFinancialProducts(FinancialProducts financialProducts);
	
	
	/**
	 * 查询可配置最新理财产品
	 * @return
	 */
	public FinancialProducts getProduct();
	
}
