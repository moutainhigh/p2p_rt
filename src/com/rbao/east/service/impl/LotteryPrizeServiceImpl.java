package com.rbao.east.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.LotteryPrizeDao;
import com.rbao.east.entity.LotteryPrize;
import com.rbao.east.service.LotteryPrizeService;

@Service
@Transactional
public class LotteryPrizeServiceImpl implements LotteryPrizeService {
	
	@Autowired 
	private LotteryPrizeDao lotteryPrizeDao;
	
	@Override
	public PageModel getLotteryPrizeList(Map<String,String> paramsMap ){
		
		return this.lotteryPrizeDao.getPage("selectByEntity", paramsMap);
	}
	@Override
	public LotteryPrize getById(Integer id){
		
		return this.lotteryPrizeDao.selectByPrimaryKey(id);
	}
	
	@Override
	public boolean saveOrUpdate(LotteryPrize lotteryPrize){
		if(lotteryPrize.getId()!=null && lotteryPrize.getId().intValue() > 0){
			LotteryPrize dbLottery = this.lotteryPrizeDao.selectByPrimaryKey(lotteryPrize.getId());
			lotteryPrize.setVsion(dbLottery.getVsion());
		}
		return this.lotteryPrizeDao.saveOrUpdate(lotteryPrize);
	}
	
	@Override
	public boolean deleteById(Integer id){
		
		return this.lotteryPrizeDao.deleteByPrimaryKey(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
