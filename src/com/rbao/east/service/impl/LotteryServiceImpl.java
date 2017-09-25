package com.rbao.east.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.dao.LotteryRecordDao;
import com.rbao.east.entity.LotteryPrize;
import com.rbao.east.entity.LotteryRecord;
import com.rbao.east.entity.UserCredit;
import com.rbao.east.mapper.LotteryPrizeMapper;
import com.rbao.east.mapper.LotteryRecordMapper;
import com.rbao.east.service.ILotteryService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.utils.SysCacheUtils;

@Service("lotteryService")
@Transactional
public class LotteryServiceImpl implements ILotteryService {

	@Autowired
	LotteryPrizeMapper LotteryPrzMapper ;
	@Autowired
	LotteryRecordMapper LotteryRecdMapper ;
	@Autowired
	LotteryRecordDao lotteryRecdDao;
	@Autowired
	UserCreditService userCreditService;

	@SuppressWarnings("unchecked")
	@Override
	public List<LotteryPrize> getAvbPrizes(Integer prizeType) {
		Map m = new HashMap();
		m.put("prizeType", prizeType);
		return LotteryPrzMapper.getAvbPrizes(m);
	}
	@Override
	public List<LotteryPrize> getAllPrizes() {
		return LotteryPrzMapper.getAll();
	}
	@Override
	public LotteryPrize getPrizeById(Integer id) {
		return LotteryPrzMapper.selectByPrimaryKey(id);
	}
	@Override
	public void addRecord(LotteryRecord recd) {
		LotteryRecdMapper.insert(recd);
	}
	@SuppressWarnings({ "rawtypes" })
	public ServiceResult startLottery(Integer usrId,Integer type){
		
		Map<String,String> cfg = SysCacheUtils.getConfigParams();
		int cost = Integer.parseInt(cfg.get("CREDIT_LOTTERY_CAST_"+type));//取出对应奖区所需的积分
		
		UserCredit usrCredit = userCreditService.getByUserId(usrId);
		if(usrCredit.getCreditValueUsable().intValue() < cost){ //积分不足
			return new ServiceResult("104","您当前的可用积分不足"); 
		}
		//随机获取奖品
		LotteryPrize prize = getRandomPrize(type); 
		if(prize == null){
			return new ServiceResult("105","未能查到剩余奖品"); 
		}
		//保存中奖记录
		rcordWinPrize(usrId,prize);
		//扣除积分
		userCreditService.subtractCreditValueUsable(usrId, cost, "积分抽奖，扣掉积分:"+cost);
		return new ServiceResult(ServiceResult.SUCCESS,"",prize); 
	}
	@Override
	public LotteryPrize getRandomPrize(Integer prizeType) {
		List<LotteryPrize> przs = getAvbPrizes(prizeType);
		if(przs.size() == 0){
			return null;
		}
		LotteryPrize prz = getRandomPrize(przs);
		
		return prz;
	}
	public LotteryPrize getRandomPrize(List<LotteryPrize> przs){
		int  sum = 0;//总概率精度
		for(LotteryPrize prz : przs){
			sum += prz.getProbability();
		}
		for(LotteryPrize prz : przs){//概率数组循环 
			int randomNum = new Random().nextInt(sum);//随机生成1到sum的整数
			if(randomNum<=prz.getProbability()){//中奖
				return prz;
			}else{
				sum -=prz.getProbability();
			}
		}
		return null;
		
	}
	@Override
	public void rcordWinPrize(Integer usrId,LotteryPrize prize) {
		LotteryRecord recd = new LotteryRecord();
		recd.setCreateTime(new Date());
		recd.setPrizeId(prize.getId());
		recd.setUsrId(usrId);
		recd.setPrizeDisc(prize.getDisc());
		
		this.LotteryRecdMapper.insertSelective(recd);
		
		prize.setWinAmount(prize.getWinAmount()+1);
		int rows = this.LotteryPrzMapper.updateByPrimaryKeySelective(prize);
		if(rows == 0){
			throw new OptimisticLockingFailureException("update lottery prize concurrent error");
		}
	}
	@Override
	public PageModel getLotteryRecordPaged(Map m) {

			return lotteryRecdDao.getPage("selectRecord", m);
	}
	@Override
	public void updateRecord(LotteryRecord recd) {
		this.LotteryRecdMapper.updateByPrimaryKeySelective(recd);
		
	}
	@Override
	public LotteryRecord getRecordById(Integer id){
		return this.LotteryRecdMapper.selectByPrimaryKey(id);
	}
		
}
