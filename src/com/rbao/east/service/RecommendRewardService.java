package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.RecommendReward;

/**
 * 
 * @author yan
 * 推荐奖励
 */
public interface RecommendRewardService {
	
	/**
	 * 添加或修改
	 * @param recommendAccount
	 * @return
	 */
	public Boolean saveOrUpdate(RecommendReward recommendReward);
	
	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	public RecommendReward selectByUserId(Integer userId);
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public PageModel getPage(Map<String,String> params);
	/**
	 * 分页查询汇总奖励
	 * @param params
	 * @return
	 */
	public PageModel getRewardPage(Map<String,String> params);

}
