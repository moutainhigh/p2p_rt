package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.dao.AccountCashDao;
import com.rbao.east.dao.InvestContinueDao;
import com.rbao.east.dao.UserAccountDao;
import com.rbao.east.entity.AccountCash;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.InvestContinue;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.PaymentConfig;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AccountCashService;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.AccountRechargeService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.PaymentConfigService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DecimalUtils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SysCacheUtils;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {
	
	private static Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);
	
	@Autowired
	private AccountCashDao accountCashDao;
	@Autowired
	private AccountCashService accountCashService;
	
	@Autowired
	private UserAccountDao userAccountDao;
	
	@Autowired
	private InvestContinueDao investContinueDao;
	
	@Autowired
	private AccountLogService accountLogService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageCenterService messageCenterService;
	
	@Autowired
	private AccountRechargeService accountRechargeService;
	
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	private PaymentConfigService paymentConfigService;
	@Override
	public PageModel getUserAccountList(Map<String, String> paramsMap) {
		return this.userAccountDao.getPage("selectByEntity", "selectTotalCount", paramsMap);
	}
	@Override
	public void addMoney(Integer usrId,BigDecimal money,String tradeCode){
		UserAccount acc = userAccountDao.selectByUserId(usrId);
		
		acc.setAllMoney(acc.getAllMoney().add(money));
		acc.setAvailableMoney(acc.getAvailableMoney().add(money));
		
		userAccountDao.updateByPrimaryKeySelective(acc);
		
		String type = AccountLog.ALL_TRADE_CODE.get(tradeCode);
		accountLogService.add(acc, tradeCode, money, Constants.ADMIN_USER_ID
				, "增加金额："+money+"元。金额来源："+type);
	}
	@Override
	public UserAccount getById(Integer userAccountId) {
		return this.userAccountDao.selectByPrimaryKey(userAccountId);
	}
	@Override
	public List<UserAccount> selectUserAccountList(Map<String, String> paramsMap) {
		return this.userAccountDao.select("selectByEntity", paramsMap);
	}
	@Override
	public void addRegisterAward(Integer userId){
		UserAccount userAccount=userAccountDao.selectByUserIdForUpdate(userId);
		if(userAccount==null){
			return;
		}
		
		
		//如果有注册奖励了,就不加了,防止加两次注册奖励.(为了防止客户在系统参数中之前设置的是注册奖励1,后来改成2,这样会加两次注册奖励)
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("tradeCode", AccountLog.TRADE_CODE_REGISTER_FEE);
		Integer count= accountLogService.queryRegister(params);
		if(count>0){
			return;
		}
		
		User user=userService.getById(userId);
		SysFeesRate fee = SysCacheUtils.getSysFeesRate();
		BigDecimal registerReward=fee.getSysRegisteredReward();
		try{
			if(CompareUtils.greaterThanZero(registerReward)){
				userAccount.setAllMoney(userAccount.getAllMoney().add(registerReward));
				userAccount.setAvailableMoney(userAccount.getAvailableMoney().add(registerReward));
				userAccount.setGetReward(userAccount.getGetReward().add(registerReward));
				//平台账户减去奖励金额
				UserAccount adminAccount = userAccountDao.selectAdminAccount();
				adminAccount.setAllMoney(adminAccount.getAllMoney().subtract(registerReward)) ;
				adminAccount.setAvailableMoney(adminAccount.getAvailableMoney().subtract(registerReward));
				adminAccount.setPayReward(adminAccount.getPayReward().add(registerReward));
				//推荐人添加log
				userAccountDao.saveOrUpdate(userAccount);
				accountLogService.add(userAccount, AccountLog.TRADE_CODE_REGISTER_FEE, registerReward
									, new BigDecimal(0), Constants.ADMIN_USER_ID
									, "获得注册奖励"+registerReward+"元"
									, RequestUtils.getIpAddr());
				//平台用户添加log
				userAccountDao.saveOrUpdate(adminAccount);
				accountLogService.add(adminAccount, AccountLog.TRADE_CODE_REGISTER_FEE
						,registerReward, new BigDecimal(0), user.getId()
						, "向用户["+user.getUserAccount()+"]支付注册奖励"+registerReward+"元"
						, RequestUtils.getIpAddr());
				
				//发送消息
				MessageCenter center = new MessageCenter();
				center.setMessageContent("获得注册奖励"+registerReward+"元");
				center.setMessageSendIp(RequestUtils.getIpAddr());
				center.setReceiveUserId(user.getId());
				center.setMessageTitle("注册奖励");
				center.setSendUserId(Constants.ADMIN_USER_ID);
				messageCenterService.send(center, Notice.RECOMMEND_AWARD);
			}
		}catch(Exception e){
			
		}
		
	}
	@Override
	public void addRecommendReward(Integer recommendUserId, BigDecimal reward) {
		//推荐人添加邀请奖励
		UserAccount userAccount = userAccountDao.selectByUserIdForUpdate(recommendUserId);
		if(userAccount==null){//推荐人不存在
			return;
		}
		userAccount.setAllMoney(userAccount.getAllMoney().add(reward));
		userAccount.setAvailableMoney(userAccount.getAvailableMoney().add(reward));
		userAccount.setGetReward(userAccount.getGetReward().add(reward));
		//平台账户减去奖励金额
		UserAccount adminAccount = userAccountDao.selectAdminAccount();
		adminAccount.setAllMoney(adminAccount.getAllMoney().subtract(reward)) ;
		adminAccount.setAvailableMoney(adminAccount.getAvailableMoney().subtract(reward));
		adminAccount.setPayReward(adminAccount.getPayReward().add(reward));
		
		
		User recommendUser = userService.getById(recommendUserId);//添加log时获取用户名称
		//添加log
		try {
			//推荐人添加log
			userAccountDao.saveOrUpdate(userAccount);
			accountLogService.add(userAccount, AccountLog.TRADE_CODE_RECOMMEND_AWARD_FEE, reward
								, new BigDecimal(0), Constants.ADMIN_USER_ID
								, "获得邀请奖励"+reward+"元"
								, RequestUtils.getIpAddr());
			//平台用户添加log
			userAccountDao.saveOrUpdate(adminAccount);
			accountLogService.add(adminAccount, AccountLog.TRADE_CODE_RECOMMEND_AWARD_FEE
					,reward, new BigDecimal(0), recommendUserId
					, "向用户["+recommendUser.getUserAccount()+"]支付推广邀请奖励"+reward+"元"
					, RequestUtils.getIpAddr());
			
			//发送消息
			MessageCenter center = new MessageCenter();
			center.setMessageContent("推广邀请奖励"+reward+"元");
			center.setMessageSendIp(RequestUtils.getIpAddr());
			center.setReceiveUserId(recommendUser.getId());
			center.setMessageTitle("推广邀请奖励");
			center.setSendUserId(Constants.ADMIN_USER_ID);
			messageCenterService.send(center, Notice.RECOMMEND_AWARD);
		} catch (Exception e) {
			
		}
	}
	
	
	/**
	 * AccountCash提现审核修改金额
	 */
	@Override
	public DwzResult updateUserAccountForAccountCash(Map<String, Object> param) {
		DwzResult dwzResult=null;
		boolean bool=false;
		try {
			AccountCash accountCashInfo=accountCashService.selectByNoOrder(param.get("noOrder").toString());
			UserAccount userAccount=userAccountDao.selectByUserId(accountCashInfo.getUserId());
			BigDecimal allMoney=userAccount.getAllMoney().subtract(accountCashInfo.getCashAccount());
			BigDecimal unavailableMoney=userAccount.getUnavailableMoney().subtract(accountCashInfo.getCashAccount()); 
		
			userAccount.setAllMoney(allMoney);
			userAccount.setUnavailableMoney(unavailableMoney);
			//提现成功设置类型为0
			accountCashInfo.setCashType(0);
			bool=userAccountDao.updateByPrimaryKeySelective(userAccount);
			if(bool){
				accountCashDao.update("updateByPrimaryKeySelective", accountCashInfo);
				//用户提现信息添加到日志表
				accountLogService.add(userAccount, AccountLog.TRADE_CODE_ACCOUNT_CASH, accountCashInfo.getCashAccount()
						,new BigDecimal(0), accountCashInfo.getUserId(), 
						"用户["+accountCashInfo.getUserAccount()+"]提现成功,"+
						"到账金额为["+accountCashInfo.getCashAccount()+"]元,"+
						"提现金额为["+accountCashInfo.getCashTotal()+"]元"
						,param.get("ipAddress").toString());
				
	
				userAccount.setAllMoney(userAccount.getAllMoney().subtract(accountCashInfo.getCashFee()));
				userAccount.setUnavailableMoney(userAccount.getUnavailableMoney().subtract(accountCashInfo.getCashFee()));
				
				bool=userAccountDao.updateByPrimaryKeySelective(userAccount);
				
				//添加用户提现手续费日志
				if(CompareUtils.greaterThanZero(accountCashInfo.getCashFee())){
					accountLogService.add(userAccount, AccountLog.TRADE_CODE_ACCOUNT_CASH_FEE, accountCashInfo.getCashFee()
							,new BigDecimal(0), accountCashInfo.getUserId(), 
							"向用户["+accountCashInfo.getUserAccount()+"]收取提现"+
							"手续费["+accountCashInfo.getCashFee()+"]元"
							,param.get("ipAddress").toString());
				}
				if(bool){
					//向管理员用户加入手续费
					UserAccount adminAccount=userAccountDao.selectAdminAccount();
					BigDecimal adminAllMoney=adminAccount.getAllMoney().add(accountCashInfo.getCashFee());
					BigDecimal adminAvailableMoney=adminAccount.getAvailableMoney().add(accountCashInfo.getCashFee());
					adminAccount.setAllMoney(adminAllMoney);
					adminAccount.setAvailableMoney(adminAvailableMoney);
					bool=userAccountDao.updateByPrimaryKeySelective(adminAccount);
					if(bool){
						//添加管理员账户信息到日志表
						if(CompareUtils.greaterThanZero(accountCashInfo.getCashFee())){
							bool=accountLogService.add(adminAccount, AccountLog.TRADE_CODE_ACCOUNT_CASH, accountCashInfo.getCashFee()
									,new BigDecimal(0), accountCashInfo.getUserId(), 
									"向用户["+accountCashInfo.getUserAccount()+"]收取提现手续费["+accountCashInfo.getCashFee()+"]元"
									,param.get("ipAddress").toString());
						}
						if(bool){
							InvestContinue investContinue= investContinueDao.getByUserId(userAccount.getUserId());
							if(null!=investContinue){
								BigDecimal userInvestRepayment=investContinue.getUserInvestRepayment().subtract(accountCashInfo.getCashTotal());
								if(CompareUtils.greaterThanAndEqualsZero(userInvestRepayment)){
									investContinue.setUserInvestRepayment(userInvestRepayment);
								}else{
									investContinue.setUserInvestRepayment(new BigDecimal(0));
									BigDecimal cashTotal=accountCashInfo.getCashTotal().subtract(investContinue.getUserInvestRepayment());
									BigDecimal userRecharge=investContinue.getUserRecharge().subtract(cashTotal);
									if(CompareUtils.greaterThanAndEqualsZero(userRecharge)){
										investContinue.setUserRecharge(userRecharge);
									}else{
										investContinue.setUserRecharge(new BigDecimal(0));
									}
								}
								bool=investContinueDao.updateByPrimaryKeySelective(investContinue);
								if(bool){
									MessageCenter center = new MessageCenter();
									center.setMessageContent("提现成功，申请金额为【"+accountCashInfo.getCashTotal()+"】元，实到账金额为【"+accountCashInfo.getCashAccount()+"】元，扣除手续费【"+accountCashInfo.getCashFee()+"】元.");
									center.setMessageSendIp(RequestUtils.getIpAddr());
									center.setReceiveUserId(userAccount.getUserId());
									center.setMessageTitle("提现成功");
									center.setSendUserId(adminAccount.getUserId());
									center.setMessageAddress(userAccount.getUser().getUserEmail());
									messageCenterService.send(center, Notice.WITHDRAW_YES);
									
									//添加积分
									userCreditService.addUserCredit(CreditType.WITHDRAW_YES, userAccount.getUserId(), adminAccount.getUserId());
									creditLogService.addCreditLog(CreditType.WITHDRAW_YES,userAccount.getUserId(), adminAccount.getUserId());
									
									dwzResult=new DwzResult(true,"提现审核操作成功",DwzResult.CALLBACK_CLOSECURRENT,String.valueOf("1001"));
								}else{
									throw new RuntimeException("修改用户续投奖励表错误！！！");
								}
							}else{
								dwzResult=new DwzResult(true,"提现审核操作成功",DwzResult.CALLBACK_CLOSECURRENT,String.valueOf("1001"));
							}
						}else{
							throw new RuntimeException("提现信息加入日志表错误！！！");
						}
					}else{
						throw new RuntimeException("修改管理员资金数据错误！！！");
					}
				}else{
					throw new RuntimeException("提现信息加入日志表错误！！！");
				}
			}else{
				throw new RuntimeException("修改用户资金数据错误！！！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return dwzResult;
	}
	
	
	@Override
	public void addAccountRecharge(Integer userId, BigDecimal rechargeMoney,BigDecimal fee,String rechargeType,String remark) {
		//充值到账金额
		UserAccount userAccount = userAccountDao.selectByUserIdForUpdate(userId);
		BigDecimal dealMoney = rechargeMoney.subtract(fee);
		userAccount.setAllMoney(userAccount.getAllMoney().add(dealMoney));
		userAccount.setAvailableMoney(userAccount.getAvailableMoney().add(dealMoney));
		//续投表中添加用户充值
		InvestContinue investContinue = investContinueDao.getByUserId(userId);
		if(investContinue != null){
			investContinue.setUserRecharge(investContinue.getUserRecharge().add(dealMoney));
			investContinueDao.saveOrUpdate(investContinue);
		}else if(investContinue==null){
			investContinue = new InvestContinue();
			investContinue.setUserId(userId);
			investContinue.setUserRecharge(dealMoney);
			investContinueDao.saveOrUpdate(investContinue);
		}
		
		User user = userService.getById(userId);
		if(rechargeType.equals(AccountRecharge.RECHARGE_TYPE_back)){ //后台充值
			userAccountDao.updateByPrimaryKeySelective(userAccount);
			accountLogService.add(userAccount, AccountLog.TRADE_CODE_RECHARGE_BACK, rechargeMoney, Constants.ADMIN_USER_ID
								, "后台充值"+rechargeMoney+"元。备注："+remark);
			
			
			//平台扣除
			UserAccount adminAccount = userAccountDao.selectAdminAccount();
			adminAccount.setAllMoney(adminAccount.getAllMoney().subtract(rechargeMoney));
			adminAccount.setAvailableMoney(adminAccount.getAvailableMoney().subtract(rechargeMoney));
			userAccountDao.updateByPrimaryKeySelective(adminAccount);
			accountLogService.add(adminAccount, AccountLog.TRADE_CODE_RECHARGE_BACK, rechargeMoney, userAccount.getUserId()
								, "为用户["+user.getUserAccount()+"]充值"+rechargeMoney+"元。备注："+remark);
		}
		//线上充值
		if(rechargeType.equals(AccountRecharge.RECHARGE_TYPE_ON)){
			//平台收取费用
			UserAccount adminAccount = userAccountDao.selectAdminAccount();
			adminAccount.setAllMoney(adminAccount.getAllMoney().add(fee));
			adminAccount.setAvailableMoney(adminAccount.getAvailableMoney().add(fee));
			try {
				//用户添加log
				userAccountDao.updateByPrimaryKeySelective(userAccount);
				accountLogService.add(userAccount, AccountLog.TRADE_CODE_RECHARGE_ONLINE, rechargeMoney, new BigDecimal(0), Constants.ADMIN_USER_ID
									, "线上充值成功，充值"+rechargeMoney+"元，扣除手续费"+fee+"元，到账"+dealMoney+"元。"
									, RequestUtils.getIpAddr());
				
				//平台添加log
				userAccountDao.updateByPrimaryKeySelective(adminAccount);
				accountLogService.add(adminAccount, AccountLog.TRADE_CODE_RECHARGE_ONLINE, fee, new BigDecimal(0), userId
									, "用户["+user.getUserAccount()+"]线上充值成功，收取手续费"+fee+"元。"
									, RequestUtils.getIpAddr());
				//发送消息
				MessageCenter center = new MessageCenter();
				center.setMessageContent("网站充值"+dealMoney+"元，充值成功。");
				center.setReceiveUserId(user.getId());
				center.setMessageTitle("线上充值");
				center.setSendUserId(Constants.ADMIN_USER_ID);
				center.setMessageSendIp(RequestUtils.getIpAddr());
				messageCenterService.send(center, Notice.RECHARGE);
				
			} catch (Exception e) {
				
			}
		}
		//线下充值
		if(rechargeType.equals(AccountRecharge.RECHARGE_TYPE_OFF)){
			
			//用户添加log
			userAccountDao.updateByPrimaryKeySelective(userAccount);
			try {
				accountLogService.add(userAccount, AccountLog.TRADE_CODE_RECHARGE_OFFLINE, rechargeMoney, new BigDecimal(0), Constants.ADMIN_USER_ID
									, "线下充值成功，充值"+rechargeMoney+"元，到账"+rechargeMoney+"元。"
									, RequestUtils.getIpAddr());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//用户添加线下充值奖励
			BigDecimal minRewardMoney = SysCacheUtils.getSysFeesRate().getSysOfflineRewardMinmoney();//线下充值获得奖励最小金额
			BigDecimal rechargeAward = new BigDecimal(0);
			if(dealMoney.compareTo(minRewardMoney) == 1 || dealMoney.compareTo(minRewardMoney)==0){
				rechargeAward = CalculateProcess.awardOfRecharge(rechargeMoney, SysCacheUtils.getSysFeesRate().getSysOfflineReward());
				UserAccount account = userAccountDao.selectByUserId(userId);
				account.setAllMoney(account.getAllMoney().add(rechargeAward));
				account.setAvailableMoney(account.getAvailableMoney().add(rechargeAward));
				account.setGetReward(account.getGetReward().add(rechargeAward));
				//平台扣除充值奖励
				UserAccount adminAccount = userAccountDao.selectAdminAccount();
				adminAccount.setAllMoney(adminAccount.getAllMoney().subtract(rechargeAward));
				adminAccount.setAvailableMoney(adminAccount.getAvailableMoney().subtract(rechargeAward));
				adminAccount.setPayReward(adminAccount.getPayReward().add(rechargeAward));
				try {
					
					//添加线下充值奖励log
					userAccountDao.updateByPrimaryKeySelective(account);
					accountLogService.add(account, AccountLog.TRADE_CODE_RECHARGE_OFF_AWARD_FEE, rechargeAward, new BigDecimal(0), Constants.ADMIN_USER_ID
										, "获得线下充值奖励"+rechargeAward+"元。"
										, RequestUtils.getIpAddr());
					
					userAccountDao.updateByPrimaryKeySelective(adminAccount);
					accountLogService.add(adminAccount, AccountLog.TRADE_CODE_RECHARGE_OFF_AWARD_FEE, rechargeAward, new BigDecimal(0), userId
										, "向用户["+user.getUserAccount()+"]支付线下充值奖励"+rechargeAward+"元"
										, RequestUtils.getIpAddr());
					
					
				} catch (Exception e) {
					
				}
			}
			//发送消息
			MessageCenter center = new MessageCenter();
			center.setMessageContent("线下充值"+dealMoney+"元，获得线下充值奖励"+rechargeAward+"元，充值成功。");
			center.setReceiveUserId(user.getId());
			center.setMessageAddress(user.getUserEmail());
			center.setMessageTitle("线下充值");
			center.setSendUserId(Constants.ADMIN_USER_ID);
			center.setMessageSendIp(RequestUtils.getIpAddr());
			messageCenterService.send(center, Notice.RECHARGE_DOWN);
		}
	}
	@Override
	public UserAccount getByUserId(Integer userId) {
		return this.userAccountDao.selectEntity("selectByUserId", userId);
	}
	@Override
	public boolean updateByPrimaryKeySelective(UserAccount userAccount) {
		return userAccountDao.updateByPrimaryKeySelective(userAccount);
	}
	@Override
	public PageModel getUserAccountStatisticsList(Map<String, String> paramsMap) {
		return userAccountDao.getPage("selectUserAccount", "selectUserAccountCount", paramsMap);
	}
	@Override
	public boolean rechargeToAccount(AccountRecharge accountRecharge, Integer mode) throws DataAccessException {
		boolean operResult=false;
		try{
			AccountRecharge recharge =accountRechargeService.getAccountRecharge(accountRecharge);
			if(recharge == null){ //没有找到记录	
				logger.error("online recharge error:not found pay record--->"+accountRecharge.getRechargeTradeNo()+"-->"+accountRecharge.getRechargeMoney());
				return false;
			}
			
			recharge=accountRechargeService.selectByPrimaryKeyForUpdate(recharge.getId()); 
			if(!recharge.getRechargeStatus().equals(AccountRecharge.RECHAREGE_STATUS_WAIT_CHAECK)){ //状态发生改变就不做处理
				logger.error("online recharge error:pay record status has changed to ("+recharge.getViewRechargeStatus()+")--->"+recharge.getUserId()+
						"-->"+accountRecharge.getRechargeTradeNo()+"-->"+accountRecharge.getRechargeMoney());
				return false;
			}
			if(accountRecharge.getRechargeMoney().compareTo(recharge.getRechargeMoney()) != 0){ //金额不一致，不做处理
				logger.error("online recharge error:toMoney is not equals--->"+recharge.getUserId()+
						"-->"+accountRecharge.getRechargeTradeNo()+"-->"+accountRecharge.getRechargeMoney()+"-->"+recharge.getRechargeMoney());
				return false;
			}
			String remark = "成功充值"+DecimalUtils.getTwoDecimal(recharge.getRechargeMoney())+"元";
			recharge.setRechargeStatus(AccountRecharge.RECHAREGE_STATUS_CHECK_SUCC);
			//recharge.setRechargeAddtime(new Date());
			recharge.setVerifyTime(new Date());
			Map<String,String> param=new HashMap<String, String>();
			/*param.put("paymentName", "国付宝");*/
			param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
			param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
			PaymentConfig paymentConfig =null;
			if(mode == 1){
				remark = "通过网银在线"+remark;
			}else if(mode == 2){
				remark = "通过宝付支付"+remark;
			}else if(mode == 3){
				remark = "通过国付宝"+remark;
				param.put("paymentName", "国付宝");
				paymentConfig=this.paymentConfigService.getByParam(param);
			}else if(mode == 4){
				remark = "通过双乾在线"+remark;
			}else if(mode == 5){
				remark = "通过银联在线"+remark;
			}else if(mode == 6){
				remark = "通过汇潮在线"+remark;
			}else if(mode == 7){
				remark = "通过贝付在线"+remark;
			}else if(mode == 8){
				remark = "通过汇付宝在线"+remark;
			}else if(mode == 12){
				remark = "通过易宝在线"+remark;
			}else if(mode == 17){
				remark = "通过丰付在线"+remark;
			}else if(mode == 19){
				remark = "通过新生在线"+remark;
			}else if (mode==122){
				remark = "连连支付在线"+remark;
			}
			//第三方支付扣除费用
			BigDecimal fee=new BigDecimal(0);
			//按固定金额收费
			if(paymentConfig!=null){
				if(paymentConfig.getPaymentFeeType().intValue()==PaymentConfig.FEE_TYPE_FIXATION.intValue()){
					fee=BigDecimal.valueOf(paymentConfig.getPaymentMaxMoney());
				//按比例收费 除以1000
				}else if(paymentConfig.getPaymentFeeType().intValue()==PaymentConfig.FEE_TYPE_PROPORTION.intValue()){
					fee=accountRecharge.getRechargeMoney().multiply(BigDecimal.valueOf(paymentConfig.getPaymentMaxFee())).divide(new BigDecimal(1000));
				//不收费
				}else if(paymentConfig.getPaymentFeeType().intValue()==PaymentConfig.FEE_TYPE_NONE.intValue()){
					fee=new BigDecimal(0);
				}
			}
			recharge.setRechargeRemark(remark);
			recharge.setRechargeFee(fee);
			recharge.setRechargeMoney(accountRecharge.getRechargeMoney().subtract(fee));
			operResult= accountRechargeService.saveAccountRecharge(recharge,false);
			/*if(operResult){
				this.addAccountRecharge(accountRecharge.getUserId(), accountRecharge.getRechargeMoney(), fee, AccountRecharge.RECHARGE_TYPE_ON);
			}*/
		}catch(Exception e){
			
			operResult=false;
			throw new RuntimeException("线上充值处理错误！！！");
		}
		
		return operResult;
	}
	
	@Override
	public List<UserAccount> getUserAccountList() {
		return this.userAccountDao.select("getUserAccountList", null);
	}
	@Override
	public boolean deleteByUserId(Integer userId) {
		UserAccount entity=new UserAccount();
		entity.setUserId(userId);
		return this.userAccountDao.delete("deleteByUserId", entity);
	}
	@Override
	public void payToPlat(Integer payUserId, BigDecimal fee,
			String payType, String remark) {
		UserAccount payAcc = this.userAccountDao.selectByUserId(payUserId);
		payAcc.setAllMoney(payAcc.getAllMoney().subtract(fee));
		payAcc.setAvailableMoney(payAcc.getAvailableMoney().subtract(fee));
		
		this.userAccountDao.updateByPrimaryKeySelective(payAcc);
		accountLogService.add(payAcc, payType, fee, Constants.ADMIN_USER_ID
				, "支付费用"+fee+"元，费用说明："+remark);
		
		
		UserAccount adminAcc = this.userAccountDao.selectAdminAccount();
		adminAcc.setAllMoney(adminAcc.getAllMoney().add(fee));
		adminAcc.setAvailableMoney(adminAcc.getAvailableMoney().add(fee));
		
		this.userAccountDao.updateByPrimaryKeySelective(payAcc);
		accountLogService.add(adminAcc, payType, fee, payUserId
				, "收取费用"+fee+"元，费用说明："+remark);
	}
	@Override
	public List<Map<String, Object>> getQQ(Map<String,Object> params) {
		// TODO Auto-generated method stub
		return userAccountDao.selects("getQQ", params);
	}
	@Override
	public BigDecimal getAllInterestMoney() {
		 Object object=userAccountDao.getObject("getAllInterestMoney",null );
		 if(null!=object){
			 return new BigDecimal(object.toString());
		 }else{
			 return new BigDecimal("0.00");
		 }
	}
	@Override
	public UserAccount selectByUserIdForUpdate(Integer userId) {
		return userAccountDao.selectByUserIdForUpdate(userId);
	}
	
}
