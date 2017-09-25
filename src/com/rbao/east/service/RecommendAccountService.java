package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.RecommendAccount;

/**
 * 
 * @author yan
 * 推荐
 */
public interface RecommendAccountService {
	
	/**
	 * 添加或修改
	 * @param recommendAccount
	 * @return
	 */
	public boolean saveOrUpdate(RecommendAccount recommendAccount);
	
	
	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	public RecommendAccount selectByUserId(Map<String,Object> params);
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public PageModel getPage(Map<String,String> params);


	public BigDecimal getRewardsByUserId(Integer id);

}
