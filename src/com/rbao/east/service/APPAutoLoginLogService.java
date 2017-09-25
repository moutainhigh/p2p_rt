package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.entity.APPAutologinLog;

/**
 *@类说明：app自动登陆日志service
 *@类 名：APPAutoLoginLogService.java
 *@作 者：李江华
 *@创建日期：2015年7月30日 下午6:25:30
 */
public interface APPAutoLoginLogService {
	
	/**
	 *@方法说明：根据参数获取
	 *@方法作者：李江华
	 *@创建日期：2015年7月30日 下午6:33:21
	 *@返回类型：APPAutologinLog
	 */
	public APPAutologinLog getByParam(Map<String, String> params);
	
	//更新
	public void saveOrUpdate(APPAutologinLog log);
	
	//插入
	public boolean add(APPAutologinLog log);
	//删除 根据userid
	public void deleteByUserId(Integer userId);
	//删除 根据token
	public void deleteByTokenId(String token);
	//根据user_id更新数据
	public boolean updateByUser(APPAutologinLog log);
	
	
}
