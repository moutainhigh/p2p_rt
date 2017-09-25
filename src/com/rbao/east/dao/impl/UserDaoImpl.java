package com.rbao.east.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.UserDao;
import com.rbao.east.entity.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(UserDaoImpl.class);



	/**
	 * 统计用户注册数量
	 * @param date
	 * 			查询条件注册时间
	 * @return object
	 */
	public Integer summaryRegisterCount(Date date) {
		return (Integer) super.getObject("summaryRegisterCount", date);
	}
	
	/**
	 * 统计待审核的认证信息数量
	 * @param map
	 * @return
	 */
	public Integer summaryNoCheckCount(Map<String, Integer> map) {
		return (Integer) super.getObject("summaryNoCheckCount", map);
	}
	
	/**
	 * 用户注册统计
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> registerSummary() {
		return super.selects("registerSummary", null);
	}
	
	public List<User> selectUserListToExcel(Map<String, String> map) {
		return super.selects("selectUserListToExcel", map);
	}
	
}
