package com.rbao.east.service;

import com.rbao.east.entity.UserEvaluate;

/**
 * 用户额度
 */
public interface UserEvaluateService {
	/**
	 * 根据userId删除
	 * @param userId
	 * @return
	 */
	public boolean deleteByUserId(Integer userId);

	/**
	 * 根据用户Id查询用户额度信息
	 * @param userId
	 * @return
	 */
	public UserEvaluate getUserEvaluateByUserId(Integer userId);
	
	/**
	 * 保存用户额度信息
	 * @param userEvaluate
	 * @return
	 */
	public boolean save(UserEvaluate userEvaluate);
}
