package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.LotteryPrize;
/**
 * 奖品
 * */
public interface LotteryPrizeService {

	/**
	 * 
	* @Title: getLotteryPrizeList
	* @Description: 奖励列表分页
	* @return    PageModel 返回类型
	* @throws
	 */
	public  PageModel getLotteryPrizeList(Map<String,String> parmasMap);

	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return   LotteryPrize 返回类型 
	* @throws
	 */
	public LotteryPrize getById(Integer id);
	/**
	 * 更新操作
	 * @param lotteryPrize 参数
	 * @return
	 */
	public boolean saveOrUpdate(LotteryPrize lotteryPrize);
	/**
	 * 删除
	 * @param id 参数
	 * @return
	 */
	public boolean deleteById(Integer id);
	
	
	
	
}
