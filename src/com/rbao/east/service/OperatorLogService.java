package com.rbao.east.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.OperatorLog;
/**
 * 操作日志
 * */
public interface OperatorLogService {
/**
 * 添加日志
 * @param log 参数
 */
	public void add(OperatorLog log);
	/**
	 * 添加后台日志
	 * @param log
	 */
	public void addAdminLog(OperatorLog log);
	/**
	 * 添加前台日志
	 * @param log
	 */
	public void addFrontLog(OperatorLog log);
	/**
	 * 分页
	 * 
	 */
	public PageModel getPagedList(Map<String,String> paramsMap);

	/**
	 * 
	* @Title: getLoginDays
	* @Description: 登录天数
	* @return    List<Date> 返回类型
	* @throws
	 */
	List<Date> getLoginDays(Map m);
	
	/**
	 * 统计用户登陆数量
	 * @return Object
	 */
	public Integer summaryLoginCount();
}
