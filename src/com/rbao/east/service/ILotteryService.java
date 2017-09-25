package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.entity.LotteryPrize;
import com.rbao.east.entity.LotteryRecord;

/**
 * 抽奖
 * */	
public interface ILotteryService {

	/**
	 * 前台抽奖操作
	 * @param usrId 
	 * @param type 奖区类型(小奖区、中奖区、大奖区)
	 * @return
	 */
	public ServiceResult startLottery(Integer usrId,Integer type) ;
	
	public PageModel getLotteryRecordPaged(Map m);
	
	public void updateRecord(LotteryRecord recd) ;
	
	/**
	 * 获取可用的奖品列表
	 * @return
	 */
	public List<LotteryPrize> getAvbPrizes(Integer prizeType) ;
	/**
	 * 
	* @Title: 
	* @Description: 
	* @return    
	* @throws
	 */
	public LotteryPrize getPrizeById(Integer id);
	
	public void addRecord(LotteryRecord recd);
	/**
	 * 
	* @Title: getRandomPrize
	* @Description: 随机奖励
	* @return    LotteryPrize 返回类型
	* @throws
	 */
	public LotteryPrize getRandomPrize(Integer prizeType) ;
	/**
	 * 所获奖励
	 * @param usrId 参数
	 * @param prize 参数
	 */
	public void rcordWinPrize(Integer usrId,LotteryPrize prize);
	/**
	 * 
	* @Title: getRecordById
	* @Description: 根据id得到记录
	* @return    LotteryRecord返回类型
	* @throws
	 */
	LotteryRecord getRecordById(Integer id);
	/**
	 * 
	* @Title: getAllPrizes
	* @Description:查询所有 
	* @return    List<LotteryPrize>返回类型
	* @throws
	 */
	List<LotteryPrize> getAllPrizes(); 
}
