package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;

import com.rbao.east.entity.RedenvelopesProbability;

/**
 * 红包设置
 * */
public interface RedenvelopesProbabilityService {
/**
 * 保存
 * @param probability
 * @return
 */
	
	public boolean save(List<RedenvelopesProbability> probability);

	/**
	 * 
	* @Title: getAll
	* @Description: 查询所有
	* @return    List<RedenvelopesProbability>返回类型
	* @throws
	 */
	public List<RedenvelopesProbability> getAll();
	/**
	 * 
	* @Title: getRedenvelopesProbability
	* @Description: 查询红包概率
	* @return    RedenvelopesProbability返回类型
	* @throws
	 */
	public RedenvelopesProbability getRedenvelopesProbability();

	/**
	 * 计算翻倍后的金额
	 * @param money
	 * @return
	 */
	BigDecimal calProbaMultiMoney(BigDecimal money);
	
}
