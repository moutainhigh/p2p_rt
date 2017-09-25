package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.RecommendRewardDao;
import com.rbao.east.entity.RecommendReward;
import com.rbao.east.service.RecommendRewardService;

/**
 * 
 * @author yan
 *
 */
@Service
public class RecommendRewardServiceImpl implements RecommendRewardService {

	@Autowired
	private RecommendRewardDao recommendRewardDao;
	
	
	@Override
	public Boolean saveOrUpdate(RecommendReward recommendReward) {
		return this.recommendRewardDao.saveOrUpdate(recommendReward);
	}
	@Override
	public RecommendReward selectByUserId(Integer userId) {
		return this.recommendRewardDao.selectEntity("selectByUserId", userId);
	}
	@Override
	public PageModel getPage(Map<String, String> params) {
		return this.recommendRewardDao.getPage("selectByEntity", params);
	}
	//新增奖励汇总
		@Override
		public PageModel getRewardPage(Map<String, String> params) {
			
			return this.recommendRewardDao.getPage("selectReward", "selectRewardCount", params);
		}
}
