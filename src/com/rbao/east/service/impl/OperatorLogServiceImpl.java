package com.rbao.east.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.OperatorLogDao;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.service.OperatorLogService;
import com.rbao.east.utils.RequestUtils;

@Service
public class OperatorLogServiceImpl implements OperatorLogService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OperatorLogDao logDao ;
	@Override
	public void add(OperatorLog log) {
		log.setCreateTime(new Date());
		try {
			if(StringUtils.isEmpty(log.getOperatorIp()))
				log.setOperatorIp(RequestUtils.getIpAddr());
			
			logDao.insertSelective(log);
		} catch (DataAccessException e) {
			
			logger.error("insertSelective(log) error:"+log,e);
		}
	}
	@Override
	public void addAdminLog(OperatorLog log) {
		log.setOperatorType(OperatorLog.TYPE_ADMIN);
		add(log);
	}
	@Override
	public void addFrontLog(OperatorLog log) {
		log.setOperatorType(OperatorLog.TYPE_FRONT);
		add(log);
	}
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Date> getLoginDays(Map m){
		return logDao.selects("selectLoginDays", m);
	}
	
	@Override
	public PageModel getPagedList(Map<String, String> paramsMap) {
		// TODO Auto-generated method stub
		return logDao.getPage("selectOperatorLog","selectTotalCount",paramsMap);
	}
	
	/**
	 * 统计用户登陆数量
	 * @return Object
	 */
	public Integer summaryLoginCount() {
		return logDao.summaryLoginCount();
	}

}
