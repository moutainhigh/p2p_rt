package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AccountLogDao;
import com.rbao.east.dao.AccountPaymentDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.AccountPayment;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.utils.RequestUtils;

@Service
@Transactional
public class AccountLogServiceImpl implements AccountLogService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AccountLogDao logDao ;
	@Autowired
	AccountPaymentDao accPaymentDao;
	@Autowired
	private UserAccountService userAccountService;
	
	@Override
	public boolean add(UserAccount acc,String tradeType,BigDecimal dealMoney,
						Integer tradeUserId,String remark) {
		return add(acc, tradeType, dealMoney, new BigDecimal(0), tradeUserId, remark, RequestUtils.getIpAddr(), AccountPayment.PAYSTATUS_ADD);
	}
	@Override
	public boolean add(UserAccount acc,String tradeType,BigDecimal dealMoney,BigDecimal repossessedMoney,
						Integer tradeUserId,String remark,String ip) {
		return add(acc, tradeType, dealMoney, repossessedMoney, tradeUserId, remark, ip, AccountPayment.PAYSTATUS_ADD);
	}
	@Override
	public boolean add(UserAccount acc,String tradeType,BigDecimal dealMoney,BigDecimal repossessedMoney,
						Integer tradeUserId,String remark,String ip,Integer payStatus) {
		AccountLog log = new AccountLog();
		log.setUserId(acc.getUserId());
		log.setAllMoney(acc.getAllMoney());
		log.setAvailableMoney(acc.getAvailableMoney());
		log.setUnavailableMoney(acc.getUnavailableMoney());
		log.setRepossessedMoney(repossessedMoney);
		log.setDealMoney(dealMoney);
		log.setTradeCode(tradeType);
		log.setTradeUserId(tradeUserId);
		log.setLogRemark(remark);
		log.setLogAddip(ip);
		log.setLogAddtime(new Date());
		
		try {
			if(StringUtils.isEmpty(log.getLogAddip()))
				log.setLogAddip(RequestUtils.getIpAddr());
		} catch (Exception e1) {
			//
		}
		
		try {
			
			if(acc.getUserId().equals(Constants.ADMIN_USER_ID)){ //管理员需记录account payment表
				AccountPayment payment = new AccountPayment();
				payment.setPayDescription(log.getLogRemark());
				payment.setPayFee(log.getDealMoney());
				payment.setPayStatus(payStatus);
				payment.setPayType(Integer.parseInt(log.getTradeCode()));
				this.accPaymentDao.insertSelective(payment);
			}
			
			return logDao.insertSelective(log);
			
			
		} catch (DataAccessException e) {
			
			logger.error("add log error:"+acc,e);
			throw new RuntimeException();
		}
	}
	
	@Override
	public PageModel getAccountLog(Map<String, String> paramsMap) {
		return logDao.getPage("selectByEntity", "selectTotalCount", paramsMap);
	}

	@Override
	public PageModel getLogStatisticsPage(Map<String, String> paramsMap) {
		PageModel pm =  userAccountService.getUserAccountStatisticsList(paramsMap);
		List<Map> usrAccountList = pm.getList();
		List<Map> logStatisticsList = logDao.selects("selectLog",paramsMap);
		for(Map usrAcc : usrAccountList){
			usrAcc.putAll(getLogMap(logStatisticsList,Integer.parseInt(usrAcc.get("userId").toString())));
		}
		return pm;
	}
	
	@SuppressWarnings("unchecked")
	public Map getLogMap(List<Map> checkingList,int userId){
		Map retMap = new HashMap();
		for(int i=0;i<checkingList.size();i++){
			Map checkingMap = checkingList.get(i);
			
			if(((Integer)checkingMap.get("userId")).intValue() == userId){
				retMap.put("log_"+checkingMap.get("tradeCode"), checkingMap.get("sumMoney"));		
			}
		}
		return retMap;
	}

	@Override
	public Integer selectByTradeCode(Map<String, String> paramsMap) {
		return logDao.getTotalCount("selectByTradeCode", paramsMap);
	}
	@Override
	public BigDecimal getTotleMoneyByTradeCode(Map<String, Object> param) {
		 Object object=logDao.getObject("getTotleMoneyByTradeCode", param);
		 if(null!=object){
			 return new BigDecimal(object.toString());
		 }else{
			 return new BigDecimal("0.00");
		 }
	}
	@Override
	public List<AccountLog> getRankingList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return logDao.select("getRankingList", param);
	}
	@Override
	public List<AccountLog> getNewTenderList(String tradeCode) {
		// TODO Auto-generated method stub
		return logDao.select("getNewTenderList", tradeCode);
	}
	@Override
	public BigDecimal queryContinueAwardMoney(Map<String, Object> params) {
		return (BigDecimal) this.logDao.selects("queryContinueAwardMoney", params).get(0);
	}
	@Override
	public Integer queryRegister(Map<String, Object> params) {
		return (Integer) this.logDao.selects("queryRegister", params).get(0);
	}
	
}
