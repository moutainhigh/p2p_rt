package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baofoo.sdk.api.BaoFooApi;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AccountRechargeDao;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.service.AccountRechargeService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.utils.RequestUtils;

@Service
@Transactional
public class AccountRechargeServiceImpl implements AccountRechargeService{
	Logger logger = LoggerFactory.getLogger(AccountRechargeServiceImpl.class);
	
	@Autowired
	private AccountRechargeDao accountRechargeDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private MessageCenterService messageCenterService;

	@Override
	public PageModel getAccountRecharge(Map<String, String> paramsMap) {
		return accountRechargeDao.getPage("selectByEntity", "selectTotalCount", paramsMap);
	}

	/**
	 * 线上充值回调与后台充值审核
	 */
	@Override
	public boolean saveAccountRecharge(AccountRecharge re,boolean isAudit) {
		Integer  rechargeStatus=re.getRechargeStatus();
		AccountRecharge accountRecharge=null;
		if(re.getId()!=null){
			accountRecharge=this.selectByPrimaryKeyForUpdate(re.getId());			
			
			if(isAudit && !accountRecharge.getRechargeStatus().equals(AccountRecharge.RECHAREGE_STATUS_WAIT_CHAECK)){
				return false;
			}
			accountRecharge.setVerifyUserid(re.getVerifyUserid());
			accountRecharge.setVerifyRemark(re.getVerifyRemark());
			accountRecharge.setVerifyTime(re.getVerifyTime());
		}else{
			accountRecharge=re;
		}
		accountRecharge.setRechargeStatus(rechargeStatus);
		if(rechargeStatus==null){
			rechargeStatus=AccountRecharge.RECHAREGE_STATUS_WAIT_CHAECK;
			accountRecharge.setRechargeStatus(rechargeStatus);
		}
		//线上充值审核，需判断订单状态
		if (isAudit && rechargeStatus.equals(AccountRecharge.RECHAREGE_STATUS_CHECK_SUCC) && accountRecharge.getRechargeType().equals(AccountRecharge.RECHARGE_TYPE_ON)) {
			try {
				Map<String, String> result = new BaoFooApi().CERQuery(accountRecharge.getRechargeTradeNo());
				if (!"0000".equals(result.get("resp_code"))) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		if(rechargeStatus.equals(AccountRecharge.RECHAREGE_STATUS_CHECK_SUCC)){
			userAccountService.addAccountRecharge(accountRecharge.getUserId(), accountRecharge.getRechargeMoney(), accountRecharge.getRechargeFee(), accountRecharge.getRechargeType(),accountRecharge.getRechargeRemark());
		}
		boolean flag = accountRechargeDao.saveOrUpdate(accountRecharge);
		if(flag && rechargeStatus.equals(AccountRecharge.RECHAREGE_STATUS_CHECK_FAIL)){
			//发送消息
			MessageCenter center = new MessageCenter();
			String content = "";
			String code = "";
			if(accountRecharge.getRechargeType().equals(AccountRecharge.RECHARGE_TYPE_ON)){
				content = "线上充值";
				code = Notice.RECHARGE;
			}
			if(accountRecharge.getRechargeType().equals(AccountRecharge.RECHARGE_TYPE_OFF)){
				content = "线下充值";
				code = Notice.RECHARGE_DOWN;
			}
			if(accountRecharge.getRechargeType().equals(AccountRecharge.RECHARGE_TYPE_back)){
				content = "后台充值";
				code = Notice.RECHARGE_DOWN;
			}
			center.setMessageContent(content+"失败");
			center.setReceiveUserId(accountRecharge.getUserId());
			//center.setMessageAddress(user.getUserEmail());
			center.setMessageTitle(content);
			center.setSendUserId(Constants.ADMIN_USER_ID);
			center.setMessageSendIp(RequestUtils.getIpAddr());
			messageCenterService.send(center,code);
		}
		
		return flag;
	}

	@Override
	public AccountRecharge getAccountRechargeById(Integer id) {
		return accountRechargeDao.selectByPrimaryKey(id);
	}

	@Override
	public AccountRecharge getAccountRecharge(AccountRecharge accountRecharge) {
		return this.accountRechargeDao.selectEntity("selectEntity", accountRecharge);
	}

	@Override
	public BigDecimal queryRechargeMoney(Map<String, Object> params) {
		return (BigDecimal) this.accountRechargeDao.selects("queryRechargeMoney", params).get(0);
	}

	@Override
	public List<AccountRecharge> queryAccountRecharge(Map<String, Object> params) {
		return this.accountRechargeDao.select("queryAccountRecharge", params);
	}
	@Override
	public AccountRecharge selectByPrimaryKeyForUpdate(Integer id) {
		return this.accountRechargeDao.selectEntity("selectByPrimaryKeyForUpdate", id);
	}
	
	/**
	 * 统计用户总充值
	 * @return BigDecimal
	 */
	@Override
	public BigDecimal summaryRecharge() {
		return accountRechargeDao.summaryRecharge(AccountRecharge.RECHAREGE_STATUS_CHECK_SUCC);
	}
	
	/**
	 * 统计线下充值待审
	 * @return
	 */
	public Integer summaryRechargeNoCheck() {
		return accountRechargeDao.summaryRechargeNoCheck(AccountRecharge.RECHAREGE_STATUS_WAIT_CHAECK, AccountRecharge.RECHARGE_TYPE_OFF);
	}
	
}
