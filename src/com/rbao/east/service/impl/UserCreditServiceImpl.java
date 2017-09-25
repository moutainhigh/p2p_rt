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
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.dao.UserCreditDao;
import com.rbao.east.entity.CreditLog;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.UserCredit;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.CreditTypeService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.RequestUtils;

@Service
@Transactional
public class UserCreditServiceImpl implements UserCreditService {

	private static Logger logger = LoggerFactory.getLogger(UserCreditServiceImpl.class);
	@Autowired
	private UserCreditDao userCreditDao;
	@Autowired
	private CreditTypeService creditTypeService;
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	UserService usrSvc;
	
	@Override
	public PageModel getList(Map<String,String> paramsMap) {
		
		return userCreditDao.getPage("selectByEntity", paramsMap);
		
	}

	@Override
	public UserCredit getById(Integer id) {
		return userCreditDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteById(Integer id) {
		return userCreditDao.deleteByPrimaryKey(id);
	}

	@Override
	public boolean save(UserCredit userCredit) {
		return userCreditDao.updateByPrimaryKeySelective(userCredit); 
	}

	@Override
	public boolean add(UserCredit userCredit) {
		return userCreditDao.insertSelective(userCredit); 
	}
	
	@Override
	public List<UserCredit> seletByUserId(Integer userId) {
		return userCreditDao.select("selectByUserId", userId);
	}

	@Override
	public UserCredit getByUserId(Integer userId) {
		return this.userCreditDao.selectEntity("selectByUserId", userId);
	}
	public UserCredit selectByUserIdForUpdate(Integer userId){
		return this.userCreditDao.selectEntity("selectByUserIdForUpdate", userId);
	}
	@Override
	public JsonResult subtractCreditValueUsable(Integer userId,int castCredit,String remark){
		CreditType credit=this.creditTypeService.getCreditType(null, CreditType.credit_exchange);
		UserCredit ucredit=this.selectByUserIdForUpdate(userId);
		if(ucredit.getCreditValueUsable().intValue() < castCredit){
			throw new RuntimeException("subtractCreditValueUsable error:user creditValueUsable not enough,userId("+userId+");"
					+" current is "+ucredit.getCreditValueUsable()
					+" cast is "+castCredit);
		}
		ucredit.setCreditValueUsable(ucredit.getCreditValueUsable().intValue()-castCredit);
		ucredit.setCreditUpdateTime(new Date());
		ucredit.setCreditUpdateIp(RequestUtils.getIpAddr());
		userCreditDao.updateByPrimaryKeySelective(ucredit);
		
		//添加积分日志
		CreditLog creditLog=new CreditLog();
		
		creditLog.setUserId(userId);
		creditLog.setCreditTypeId(credit.getId());
		if(castCredit > 0)
			creditLog.setCreditOperateType(CreditLog.CREDIT_RELEASE);//积分减少
		else
			creditLog.setCreditOperateType(CreditLog.CREDIT_ADD);//积分增加
		creditLog.setCreditOperateValue(castCredit);
		creditLog.setCreditOperateRemark(remark);
		creditLog.setCreditOperateDatetime(new Date());
		creditLog.setCreditOperateIp(RequestUtils.getIpAddr());
		creditLog.setCreditOperater(Constants.ADMIN_USER_ID);

		creditLogService.add(creditLog);
		return new JsonResult(JsonResult.SUCCESS);
	}
	/**
	 * 
	 */
	@Override
	public boolean addUserCredit(String creditCode, Integer userId,Integer loginUserId,Map<String,Object>... params){
		boolean flag=false;
		try {
			//根据用户ID查询用户
			UserCredit ucredit=this.selectByUserIdForUpdate(userId);
			//获取积分信息
			CreditType credit=this.creditTypeService.getCreditType(null, creditCode);
			for(Map<String,Object> param : params){
				credit.rebuild(param);
			}
			//添加或修改用户积分
			if(ucredit==null){
				UserCredit uCredit=new UserCredit();
				uCredit.setCreditAddTime(new Date());
				uCredit.setCreditAddIp(RequestUtils.getIpAddr());
				uCredit.setCreditValue(credit.getCreditScore());
				uCredit.setCreditValueUsable(credit.getCreditScore());
				uCredit.setUserId(userId);
				uCredit.setCreditOperator(loginUserId);
				flag=this.add(uCredit);
				if(flag){
					logger.info("添加用户积分成功");
				}
			}else{
				ucredit.setId(ucredit.getId());
				ucredit.setCreditUpdateIp(RequestUtils.getIpAddr());
				ucredit.setCreditUpdateTime(new Date());
				ucredit.setCreditOperator(loginUserId);
				ucredit.setCreditValue(ucredit.getCreditValue()+credit.getCreditScore());
				ucredit.setCreditValueUsable(ucredit.getCreditValueUsable()+credit.getCreditScore());
				flag=this.save(ucredit);
				if(flag){
					logger.info("修改用户积分成功");
				}else{
					logger.error("修改用户积分失败");
				}
			}
		} catch (Exception e) {
			flag=false;
			logger.error("添加或修改用户积分，记录出错");
			e.printStackTrace();
			return false;
		} 
		return flag;
	}

	@Override
	public boolean addUserCredit(String creditCode, Integer userId,Map<String,Object>... params) {
		return this.addUserCredit(creditCode, userId, Constants.ADMIN_USER_ID,params);
	}

	@Override
	public boolean addUserCreditAndLog(String creditCode, Integer userId,Map<String,Object>... params) {
		try {
			addUserCredit(creditCode,userId,params);
			this.creditLogService.addCreditLog(creditCode, userId, params);
		} catch (Exception e) {
			logger.error("add credit error:"+creditCode+";"+userId,e);
			return false;
		}
		return true;
	}

	@Override
	public List getCreditByUserId(Map<String, String> param) {
		
		return this.userCreditDao.selects("getCreditByUserId", param);
	}
	
	
}
