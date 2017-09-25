package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.ExperienceGold;

/**
 * 体验金
 */

public interface ExperienceGoldService {
	/**
	 * 
	* @Title: getExperienceGoldByUserId
	* @Description: 查询
	* @return ExperienceGold   返回类型
	* @throws
	 */
	ExperienceGold getExperienceGoldByUserId(Integer id);
	/**
	 * 
	* @Title: saveOrUpdate
	* @Description: 更新操作
	* @return    boolean返回类型
	* @throws
	 */
	boolean saveOrUpdate(ExperienceGold experienceGold2);
	/**
	 * 
	* @Title: getExperienceGoldListByStatus
	* @Description: 查询数据集
	* @return    List<ExperienceGold>返回类型
	* @throws
	 */
	List<ExperienceGold> getExperienceGoldListByStatus();
	/**
	 * 
	* @Title: addExperienceGold
	* @Description: 添加
	* @return   void返回类型 
	* @throws
	 */

	void addExperienceGold(ExperienceGold experienceGold);
	/**
	 * 
	* @Title: getExperienceGoldByParam
	* @Description: 根据参数得到体验金的记录
	* @return ExperienceGold    返回类型
	* @throws
	 */
	ExperienceGold getExperienceGoldByParam(Map<String,String> paramsMap);

}
