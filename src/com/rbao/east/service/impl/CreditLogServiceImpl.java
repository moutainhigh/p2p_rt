package com.rbao.east.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.CreditLogDao;
import com.rbao.east.entity.CreditLog;
import com.rbao.east.entity.CreditType;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.CreditTypeService;
import com.rbao.east.utils.RequestUtils;

@Service
@Transactional
public class CreditLogServiceImpl implements CreditLogService {
	private static Logger logger = LoggerFactory.getLogger(CreditLogServiceImpl.class);
	@Autowired
	private CreditLogDao creditLogDao;
	
	@Autowired
	private CreditTypeService creditTypeService;
	@Override
	public PageModel getPagedList(Map<String,String> paramsMap) {
		
		return creditLogDao.getPage("selectCreditLog","selectTotalCount",paramsMap);
		
	}

	@Override
	public CreditLog getById(Integer id) {
		return creditLogDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer id) {
		return creditLogDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(CreditLog creditLog) {
		return creditLogDao.updateByPrimaryKeySelective(creditLog); 
	}
	
	@Override
	public boolean add(CreditLog creditLog) {
		return creditLogDao.insertSelective(creditLog); 
	}

	@Override
	public List<CreditLog> getByUserId(Integer userId) {
		return creditLogDao.select("selectByUserId", userId);
	}
	public boolean addCreditLog(String creditCode, Integer userId,Map<String,Object>... params){
		return addCreditLog(creditCode,userId,Constants.ADMIN_USER_ID,params);
	}
	@Override
	public boolean addCreditLog(String creditCode, Integer userId,Integer loginUserId,Map<String,Object>... params){
		boolean flag=false;
		try {
			CreditType credit=this.creditTypeService.getCreditType(null, creditCode);
			for(Map<String,Object> param : params){
				credit.rebuild(param);
			}
			CreditLog creditLog=new CreditLog();
			//添加积分记录
			creditLog.setUserId(userId);
			creditLog.setCreditTypeId(credit.getId());
			//判断添加积分是否大于0，大于0则操作类型为添加积分，小于0，则为减少积分
			if(credit.getCreditScore()!=0){
				if(credit.getCreditScore()>0){
					creditLog.setCreditOperateType(CreditLog.CREDIT_ADD);
				}else{
					creditLog.setCreditOperateType(CreditLog.CREDIT_RELEASE);
				}
				creditLog.setCreditOperateValue(credit.getCreditScore());
				creditLog.setCreditOperateRemark(credit.getCreditName()+","+credit.getCreditScore());
				creditLog.setCreditOperateDatetime(new Date());
				creditLog.setCreditOperateIp(RequestUtils.getIpAddr());
				creditLog.setCreditOperater(loginUserId);
				flag=this.add(creditLog);
				if(flag){
					logger.info("添加积分记录成功");
				}
			}
			
		} catch (Exception e) {
			flag=false;
			logger.error("添加或修改用户积分，记录出错");
			
		} 
		return flag;
	}

	@Override
	public PageModel getPagedListNew(Map<String, String> paramsMap) {
		return creditLogDao.getPage("selectCreditLogNew","selectTotalCountNew",paramsMap);
	}
	
	
	
}
