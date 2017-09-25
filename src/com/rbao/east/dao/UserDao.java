package com.rbao.east.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rbao.east.entity.User;


public interface UserDao extends BaseDao<User>{

	/**
	 * 统计用户注册数量
	 * @param date
	 * 			查询条件注册时间
	 * @return Integer
	 */
	public Integer summaryRegisterCount(Date date);
	
	/**
	 * 统计待审核的认证信息数量
	 * @param map
	 * @return
	 */
	public Integer summaryNoCheckCount(Map<String, Integer> map);
	
	/**
	 * 用户注册统计
	 * @return
	 */
	public List<Map<String, Object>> registerSummary();
	
	
	public List<User> selectUserListToExcel(Map<String, String> map);
}
