package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.entity.RechargeCash;

/**
 * 充值提现记录
 * @author yan
 *
 */

public interface RechargeCashService {
	/**
	 * 删除
	 * @param id 参数
	 * @return
	 */
	public Boolean deleteByPrimaryKey(Integer id);
	/**
	 * 添加
	 * @param record 参数
	 * @return
	 */
	public Boolean insert(RechargeCash record);
	/**
	 * 添加
	 * @param record
	 * @return
	 */
	public Boolean insertSelective(RechargeCash record);
	/**
	 * 
	 * @Title: selectByPrimaryKey
	 * @Description: 根据主键查询
	 * @return    RechargeCash 返回类型
	 * @throws
	 */
	public RechargeCash selectByPrimaryKey(Integer id);
	/**
	 * 更新
	 * @param record
	 * @return
	 */
	public Boolean updateByPrimaryKeySelective(RechargeCash record);
	/**
	 * 更新
	 * @param record
	 * @return
	 */
	public Boolean updateByPrimaryKey(RechargeCash record);

	/**如下是自定义****/
	public List<RechargeCash> selectByRechargeId(Integer id);
	/**
	 * 
	 * @Title: selectByCashId
	 * @Description: 根据id查询
	 * @return    List<RechargeCash>返回类型 
	 * @throws
	 */
	public List<RechargeCash> selectByCashId(Integer id);
	/**
	 * 
	 * @Title: selectSumByRechargeId
	 * @Description: 查询总额
	 * @return  BigDecimal 返回类型  
	 * @throws
	 */
	public BigDecimal selectSumByRechargeId(Integer id);
	/**
	 * 删除
	 * @param id
	 */
	public void deleteByCashId(Integer id);
	/**
	 * 添加
	 * @param rechgCashMap
	 * @param cashId
	 * @param userId
	 */
	public void addRechgCash(Map<Integer,BigDecimal> rechgCashMap,Integer cashId,Integer userId);



}
