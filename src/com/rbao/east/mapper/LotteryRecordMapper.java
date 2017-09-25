package com.rbao.east.mapper;

import com.rbao.east.entity.LotteryRecord;

public interface LotteryRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LotteryRecord record);

    int insertSelective(LotteryRecord record);

    LotteryRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryRecord record);

    int updateByPrimaryKey(LotteryRecord record);
}