package com.rbao.east.mapper;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.LotteryPrize;

public interface LotteryPrizeMapper {
	
	List<LotteryPrize> getAll() ;
	
	List<LotteryPrize> getAvbPrizes(Map m) ;
	
    int deleteByPrimaryKey(Integer id);

    int insert(LotteryPrize record);

    int insertSelective(LotteryPrize record);

    LotteryPrize selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryPrize record);

    int updateByPrimaryKey(LotteryPrize record);
}