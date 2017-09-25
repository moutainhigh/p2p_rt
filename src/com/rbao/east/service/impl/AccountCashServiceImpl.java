package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.dao.AccountCashDao;
import com.rbao.east.dao.InvestContinueDao;
import com.rbao.east.entity.AccountCash;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.InvestContinue;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.RechargeCash;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.entity.UserBank;
import com.rbao.east.service.AccountCashService;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.AccountRechargeService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.CashStageService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.RechargeCashService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserBankService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SysCacheUtils;
@Service
@Transactional
public class AccountCashServiceImpl  implements AccountCashService{
	private static Logger logger = LoggerFactory.getLogger(AccountCashServiceImpl.class);
	
	@Autowired
	private AccountCashDao accountCashDao;

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private UserBankService userBankService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private RechargeCashService rechargeCashService;
	@Autowired
	private AccountRechargeService accountRechargeService;
	@Autowired
	private BorrowTenderService borrowTenderService;
	@Autowired
	private InvestContinueDao investContinueDao;
	@Autowired
	private CashStageService cashStageService;
	
	@Override
	public PageModel getAccountCashList(Map<String, String> param) {
		return accountCashDao.getPage("selectAccountCashList","selectAccountCashListCount", param);
	}

	@Override
	public AccountCash getAccountCashById(int id) {
		return accountCashDao.selectByPrimaryKey(id);
	}

	@Override
	public DwzResult updateAccountCashById(Map<String, Object> param) {
		DwzResult dwzResult=null;
		boolean bool=false;
		try {
			if(param.containsKey("cashStatuss")){
				int cashStatus =Integer.parseInt(param.get("cashStatuss").toString());
				AccountCash accountCashOld=this.selectCashForUpdate(Integer.parseInt(param.get("id").toString()));
				AccountCash accountCash=new AccountCash();
				User user=(User) param.get("user");
				if(cashStatus==1 && accountCashOld.getCashStatus() == AccountCash.cashApply){
					accountCashOld.setId(Integer.parseInt(param.get("id").toString()));
					
					if(accountCashOld.getCashStatus().intValue()!=0){
						if(accountCashOld.getCashStatus().intValue()==1){
							throw new RuntimeException("已提现成功！不能重复提现！！");
						}
						if(accountCashOld.getCashStatus().intValue()==3){
							throw new RuntimeException("已撤销提现！提现失败！");
						}
					}
					accountCashOld.setVerifyUserid(user.getId());
					if(param.containsKey("verifyRemark")){
						accountCashOld.setVerifyRemark(param.get("verifyRemark").toString());
					}
					accountCashOld.setCashStatus(1);
					bool=accountCashDao.update("updateByPrimaryKeySelective", accountCashOld);
					if(bool){
						dwzResult=userAccountService.updateUserAccountForAccountCash(param);
					}else{
						throw new RuntimeException("修改提现状态错误！！！");
					}
				}else if(cashStatus==2 && accountCashOld.getCashStatus() == AccountCash.cashApply){
					
					accountCashOld.setId(Integer.parseInt(param.get("id").toString()));
					//AccountCash accountCashInfo=this.selectCashForUpdate(accountCash.getId());
					//AccountCash accountCashInfo=accountCashDao.selectByPrimaryKey(Integer.parseInt(param.get("id").toString()));
					accountCashOld.setVerifyUserid(user.getId());
					if(param.containsKey("verifyRemark")){
						accountCashOld.setVerifyRemark(param.get("verifyRemark").toString());
					}
					if(accountCashOld.getCashStatus()==AccountCash.cashApply){
						accountCashOld.setCashStatus(2);
						
						bool=accountCashDao.update("updateByPrimaryKeySelective", accountCashOld);
					}
					
					UserAccount userAccount=userAccountService.selectByUserIdForUpdate(accountCashOld.getUserId());
					BigDecimal unavailableMoney=userAccount.getUnavailableMoney().subtract(accountCashOld.getCashTotal());
					BigDecimal availableMoney=userAccount.getAvailableMoney().add(accountCashOld.getCashTotal()); 
					userAccount.setUnavailableMoney(unavailableMoney);
					userAccount.setAvailableMoney(availableMoney);
					
					userAccountService.updateByPrimaryKeySelective(userAccount);
					
					accountLogService.add(userAccount, AccountLog.WITHDRAW_APPLY_NO,accountCashOld.getCashTotal()
							,new BigDecimal(0), accountCashOld.getUserId(), 
							"用户["+accountCashOld.getUserAccount()+"]提现失败,解冻资金"+
							"["+accountCashOld.getCashTotal()+"]元"
							,param.get("ipAddress").toString());
					
					
					
					
					MessageCenter center = new MessageCenter();
					center.setMessageContent("提现失败，失败原因【"+param.get("verifyRemark")+"】");
					center.setMessageSendIp(RequestUtils.getIpAddr());
					//center.setReceiveUserId(accountCash.getUserId());
					center.setReceiveUserId(accountCashOld.getUserId());
					center.setMessageTitle("提现失败");
					center.setSendUserId(user.getId());
					messageCenterService.send(center, Notice.WITHDRAW_NO);
					
					
					dwzResult=new DwzResult(bool, bool?"提现审核操作成功":"提现审核操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());
				}
			}else{
				throw new RuntimeException("提现审核错误！！！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return dwzResult;
	}

	@Override
	public List<AccountCash> selectAccountCashByCashStatus(
			Map<String, String> param) {
		return accountCashDao.select("selectAccountCashList", param);
	}
	@Override
	public boolean saveAccountCash(AccountCash accountCash, Map<String,String> param) {
		boolean flag=false;
		try{
			//银行卡
			UserBank userBank=this.userBankService.getByUserId(param);
			//费率
			SysFeesRate sFRate=SysCacheUtils.getSysFeesRate();
			//收取提现金额的千分之几
			Map<Integer,BigDecimal> rechgCashMap = new HashMap<Integer, BigDecimal>();
			Integer cashTypeFee=AccountCash.cashTypeFee;//提现手续费方式
			BigDecimal fee=new	BigDecimal(0);//手续费
			if(cashTypeFee==1){
				//1，按比例收费
				fee=sFRate.getSysWithdrawalPoundage().multiply(accountCash.getCashTotal()).divide(new BigDecimal(1000));
				//最小和最大手续费
				/*if(CompareUtils.greaterEquals(SysFeesRate.cashMinFee, fee)){
					fee=SysFeesRate.cashMinFee;
				}else if(CompareUtils.greaterEquals(fee, SysFeesRate.cashMaxFee)){
					fee=SysFeesRate.cashMaxFee;
				}*/
			}else if(cashTypeFee==2){
				//2，按区间收费
				fee=this.cashStageService.getCashFee(accountCash.getCashTotal());
			}else if(cashTypeFee==3){
				//按每笔收费
				fee=new BigDecimal(param.get("cashFee"));
			}
			//线上线下
			//BigDecimal fee=getCashFee(accountCash.getUserId(), accountCash.getCashTotal(), rechgCashMap);
			
			accountCash.setCashAccount(accountCash.getCashTotal().subtract(fee));
			accountCash.setCashAddtime(new Date());
			accountCash.setCashBank(userBank.getAllBank().getBankName());
			accountCash.setCashBankAccount(userBank.getBankAccount());
			accountCash.setCashBranch(userBank.getBankBranch());
			accountCash.setCashFee(fee);
			accountCash.setCashStatus(AccountCash.cashApply);
			accountCash.setUserId(Integer.parseInt(param.get("userId")));
			accountCash.setNoOrder(param.get("noOrder"));
			flag=this.accountCashDao.insertSelective(accountCash);
			//this.rechargeCashService.addRechgCash(rechgCashMap,accountCash.getId(),accountCash.getUserId());
			//修改账户信息
			UserAccount userAccount=this.userAccountService.selectByUserIdForUpdate(Integer.parseInt(param.get("userId")));
			userAccount.setAvailableMoney(userAccount.getAvailableMoney().subtract(accountCash.getCashTotal()));
			userAccount.setUnavailableMoney(userAccount.getUnavailableMoney().add(accountCash.getCashTotal()));
			this.userAccountService.updateByPrimaryKeySelective(userAccount);
			
			//用户添加log
			accountLogService.add(userAccount, AccountLog.WITHDRAW_APPLY, accountCash.getCashTotal(), new BigDecimal(0), Integer.parseInt(param.get("userId"))
								, "申请提现金额为"+accountCash.getCashTotal()+"元，手续费"+fee+"元。"
								, RequestUtils.getIpAddr());
		}catch(Exception e){
			
			flag=false;
			throw new RuntimeException("提现申请出错！");
		}
		return flag;
	}

	@Override
	
	public AccountCash selectAccountCashByUserIdStatus(Map<String, String> param) {
		return this.accountCashDao.selectEntity("selectAccountCashByStatus", param);
	}

	@Override
	public PageModel selectCashAndRecharge(Map<String, String> param) {
		if(param.get("cxType")=="0"){
			return accountCashDao.getPage("selectCashAndRecharge","selectCashAndRechargeCount", param);
		}
		if(param.get("cxType").equals("1")){
			return accountCashDao.getPage("selectRecharge","selectRechargeCount", param);
		}
		if(param.get("cxType").equals("2")){
			return accountCashDao.getPage("selectCash","selectCashCount", param);
		}
		return null;
	}

	@Override
	public boolean updateCashStatus(AccountCash accountCash) {
		return this.accountCashDao.updateByPrimaryKeySelective(accountCash);
	}

	@Override
	public BigDecimal selectWithDrawTotal(Map<String, String> map) {
		return (BigDecimal)this.accountCashDao.selects("selectTotalMoney", map).get(0);
	}


	@Override
	public BigDecimal getCashFee(Integer usrId, BigDecimal cashTotal,
			Map<Integer, BigDecimal> rechgCashMap) {
		//获取免费额度
				BigDecimal free = freeCashMoneys(usrId);
				if(CompareUtils.greaterEquals(free,cashTotal)){
					return new BigDecimal(0);
				}
				BigDecimal feeAmount = cashTotal.subtract(free);
				
				int days = SysCacheUtils.getSysFeesRate().getSysWithdrawalDay();
				
				//取15天内充值的记录
				Map<String,Object> recharge = new HashMap<String,Object>();
				if(days>0){
					recharge.put("verifyTime", DateUtils.formatDate( //15天前的日期
					DateUtils.addDay(new Date(), -days)));
				}
				recharge.put("verifyTime", DateUtils.formatDate( //15天前的日期
							DateUtils.addDay(new Date(), -days)));
				recharge.put("userId", usrId);
				recharge.put("rechargeType", new String [] {AccountRecharge.RECHARGE_TYPE_ON,AccountRecharge.RECHARGE_TYPE_OFF});
				
				List<AccountRecharge> rechargeList = accountRechargeService.queryAccountRecharge(recharge);
				
				//获取线下金额
				BigDecimal offline = calcCashMoneyByType(rechargeList,AccountRecharge.RECHARGE_TYPE_OFF,feeAmount,rechgCashMap);
				//获取线上金额
				BigDecimal online = calcCashMoneyByType(rechargeList, AccountRecharge.RECHARGE_TYPE_ON,feeAmount.subtract(offline)
														,rechgCashMap);
				
				logger.debug("cash total:"+cashTotal+",free:"+free+",offline:"+offline+",online:"+online); 
				
				return CalculateProcess.calcCashFee(offline, online);
	}

	public BigDecimal calcCashMoneyByType(List<AccountRecharge> rechargeList,String payRecordType,BigDecimal cashRemain,Map<Integer,BigDecimal> rechgCashMap){
		BigDecimal total = new BigDecimal(0);
		for(AccountRecharge rechg : rechargeList){
			if(rechg.getRechargeType().equals(payRecordType)){
				BigDecimal hasCashed = rechargeCashService.selectSumByRechargeId(rechg.getId());
				BigDecimal curCash = rechg.getRechargeMoney().subtract(hasCashed);//减去历史提现
				if(!CompareUtils.greaterThanZero(curCash)){
					continue;  //当笔已经提现
				}
				if(!CompareUtils.greaterThanZero(cashRemain)){
					return total; 
				}
				if(CompareUtils.greaterThan(curCash,cashRemain)){
					curCash = cashRemain ;
				}
				
				total = total.add(curCash);
				cashRemain =cashRemain.subtract(curCash); 
				if(rechgCashMap != null)
					rechgCashMap.put(rechg.getId(), curCash);
			}
		}
		return total;
	}
	
	@Override
	public BigDecimal freeCashMoney(Integer userId) {
		BigDecimal freeMoney=new BigDecimal(0);
		/*//查询账户信息
		UserAccount account = this.userAccountService.getByUserId(userId);
		BigDecimal usableMoney=account.getAvailableMoney();//可用
		BigDecimal totalMoney=account.getAllMoney();//总金额
		BigDecimal tenderMoney = new BigDecimal(0);//一段时间内投资总额
		BigDecimal freeCash=new BigDecimal(0);//免费提现的待审的
		BigDecimal waitMoney=new BigDecimal(0);//提现待审收费的
		BigDecimal result []=null;
		SysFeesRate sys=SysCacheUtils.getSysFeesRate();
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("userId", userId);
			//收取提现手续费时间
			Integer WDMtime=sys.getSysWithdrawalDay();
			Date date = new Date();// 取时间
			map.put("verifyTime", DateUtils.formatDate(DateUtils.addDay(date, -WDMtime)));
			map.put("rechargeType", new String [] {AccountRecharge.RECHARGE_TYPE_ON,AccountRecharge.RECHARGE_TYPE_OFF});
			//充值成功金额，十五天 ，wdMtime
			BigDecimal successMoney=this.accountRechargeService.queryRechargeMoney(map);
			BigDecimal rechargeMoney=successMoney;
			//取15天内充值的记录
			List<AccountRecharge> rechargeList=accountRechargeService.queryAccountRecharge(map);
			
			tenderMoney=tenderMoneyInTime(userId, successMoney, rechargeList);
			result=rechargeSuccessInTime(successMoney, rechargeList, userId);
			successMoney=result[0];
			waitMoney=result[1];
			freeCash=sumFreeCashMoneyApply(userId,WDMtime,waitMoney);
			//免费提现金额=15投标成功+可用+冻结-(15充值成功-15天充值成功提现待审的-15天充值成功提现的)-免费提现申请中的
			BigDecimal sumMoney=new BigDecimal(0);
			//如果15投标成功+可用+冻结大于总金额,则用总金额
			sumMoney=tenderMoney.add(usableMoney).add(account.getUnavailableMoney());
			if(CompareUtils.greaterThan(sumMoney, totalMoney)){
				sumMoney=totalMoney;
			}
			
			freeMoney=sumMoney.subtract(successMoney).subtract(freeCash);
			//免费提现金额是否大于可用金额
			if(CompareUtils.greaterThan(freeMoney, usableMoney)){
				freeMoney=usableMoney;
			}
		} catch (Exception e) {
			
		}
		if(!CompareUtils.greaterThanZero(freeMoney)){
			freeMoney=new BigDecimal(0);
		}
		logger.info("提现了:"+freeMoney);*/
		return freeMoney;
	}
	@Override
	public BigDecimal freeCashMoneys(Integer userId) {
		BigDecimal freeMoney=new BigDecimal(0);
		//查询账户信息
		UserAccount account = this.userAccountService.getByUserId(userId);
		BigDecimal usableMoney=account.getAvailableMoney();//可用
		SysFeesRate sys=SysCacheUtils.getSysFeesRate();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		//收取提现手续费时间
		Integer WDMtime=sys.getSysWithdrawalDay();
		Date date = new Date();// 取时间
		map.put("verifyTime", DateUtils.formatDate(DateUtils.addDay(date, -WDMtime)));
		map.put("rechargeType", new String [] {AccountRecharge.RECHARGE_TYPE_ON,AccountRecharge.RECHARGE_TYPE_OFF});
		//充值成功金额，十五天 ，wdMtime
		BigDecimal rechargeMoney=this.accountRechargeService.queryRechargeMoney(map);
		//获取用户提现收费的钱
		BigDecimal feeMoney=this.continueMoney(userId,rechargeMoney);
		freeMoney=usableMoney.subtract(feeMoney);
		if(!CompareUtils.greaterThanZero(freeMoney)){
			freeMoney=new BigDecimal(0);
		}
		return freeMoney;
	}
	/**
	 *	获取用户回款金额+免费充值提现的钱
	 * @param id 用户id
	 * @param rechargeMoney 一定时间内充值成功的钱
	 * @return
	 */
	public BigDecimal continueMoney(Integer id,BigDecimal rechargeMoney){
		//BigDecimal [] continueMoney =new BigDecimal[2];
		//BigDecimal continueMoney=new BigDecimal(0);
		BigDecimal userRecharge=new BigDecimal(0);
		BigDecimal feeMoney=new BigDecimal(0);//提现收费的钱
		try{
			InvestContinue investContinue=this.investContinueDao.selectEntity("selectByUserId", id);
			if(investContinue!=null){
				//continueMoney=investContinue.getUserInvestRepayment();//用户回款金额
				userRecharge=investContinue.getUserRecharge();//用户回款金额;
				//recharge=15000,userRecharge=15000;就是最近没有投保也没有提现操作，
				//recharge=0,userRcharge=100,
				if(CompareUtils.equals(rechargeMoney, userRecharge)||CompareUtils.greaterEquals(rechargeMoney, new BigDecimal(0))){
					feeMoney=userRecharge;
				}
				//recharge=5000,userRecharge=15000,就是有10000已经提现或者投保
				if(CompareUtils.greaterThan(rechargeMoney, userRecharge)){
					feeMoney=userRecharge;
				}
				//recharge=15000,userRecharge=1000,就是最近没有投标，没有提现，在一段时间内充值了1000
				if(CompareUtils.greaterThanZero(rechargeMoney)){
					if(CompareUtils.greaterThan(userRecharge, rechargeMoney)){
						feeMoney=userRecharge;
					}
				}
				if(!CompareUtils.greaterThanAndEqualsZero(feeMoney)){
					feeMoney=new BigDecimal(0);
				}
			}
		}catch(Exception e){
			
		}finally{
			return feeMoney;
		}
	}
	
	/**
	 * 一段时间内投标成功总金额
	 * @param userId
	 * @return
	 */
	public BigDecimal tenderMoneyInTime(Integer userId,BigDecimal successMoney,List<AccountRecharge> rechargeList){
		BigDecimal tenderMoney = new BigDecimal(0);//一段时间内投资总额
		Map<String,Object> map=new HashMap<String, Object>();
		try{
			if(CompareUtils.greaterThanZero(successMoney)){
				//查询date时间内最早充值成功记录
				AccountRecharge earlyRecharge=null;
				if(rechargeList.size()>0){
					earlyRecharge=rechargeList.get(0);
				}
				if(earlyRecharge!=null){
					Date FirstFinishTime=earlyRecharge.getVerifyTime();
					map.clear();
					map.put("userId", userId);
					map.put("tenderAddtime", FirstFinishTime);
					//查询一定时间内投资成功的总金额
					map.put("tenderStatus", new String [] {BorrowTender.STATUS_REPAYING.toString(),BorrowTender.STATUS_OVERDUE.toString()});
					tenderMoney=this.borrowTenderService.queryTenderMoney(map);
				}
			}
		}catch(Exception e){
			
		}finally{
			return tenderMoney;
		}
	}

	/****
	 * (15充值成功-15天充值成功提现待审的-15天充值成功提现的),提现待审收费的
	 * @param successMoney
	 * @param rechargeList
	 * @param userId
	 * @return
	 */
	public BigDecimal [] rechargeSuccessInTime(BigDecimal successMoney,List<AccountRecharge> rechargeList,Integer userId){
		BigDecimal waitMoney=new BigDecimal(0);//提现待审收费的
		Map<String,Object> map=new HashMap<String, Object>();
		BigDecimal [] moneys =new BigDecimal[2];
		moneys[0]=new BigDecimal(0);
		moneys[1]=new BigDecimal(0);
		try{
			if(rechargeList!=null){
				for(AccountRecharge recharge:rechargeList){
					if(recharge.getId()!=null){
						//查询15天内充值成功的是否有提现的(根据充值)
						List<RechargeCash> rechargeCashList=this.rechargeCashService.selectByRechargeId(recharge.getId());
						if(rechargeCashList!=null){
							for(RechargeCash rechargeCash:rechargeCashList){
								//根据提现id查询提现记录是否有待审核
								map.put("id", rechargeCash.getCashId());
								AccountCash money=this.accountCashDao.selectEntity("selectAccountCashByStatus", map);
								//有待审的记录，则减去待审的
								if(money!=null){
									BigDecimal moneyWith=money.getCashAccount();
									if(moneyWith==null){
										moneyWith=new BigDecimal(0);
									}
									waitMoney=waitMoney.add(moneyWith);
									successMoney=successMoney.subtract(rechargeCash.getCashFeeAmount().subtract(moneyWith));
								}else{
									successMoney=successMoney.subtract(rechargeCash.getCashFeeAmount());
								}
								
							}
						}
					}
				}
			}
			moneys[0]=successMoney;
			moneys[1]=waitMoney;
		}catch(Exception e){
			
		}finally{
			return moneys;
		}
	}
	
	/**
	 * 统计免费提现申请中的
	 * @param userId
	 * @param WDMtime 收费时间
	 * @param waitMoney 提现待审收费的
	 * @return
	 */
	public BigDecimal sumFreeCashMoneyApply(Integer userId,Integer WDMtime,BigDecimal waitMoney){
		Map<String,Object> map=new HashMap<String, Object>();
		BigDecimal freeCash=new BigDecimal(0);//免费提现待审
		try{
			//根据提现userid查询提现记录是否有待审核
			map.put("userId", userId);
			map.put("cashStatus",new Integer [] {AccountCash.cashApply});
			map.put("cashAddtime", DateUtils.formatDate(DateUtils.addDay(new Date(), -WDMtime)));
			BigDecimal money=(BigDecimal) this.accountCashDao.selects("selectTotalMoney", map).get(0);//所有提现待审的
			
			if(CompareUtils.greaterThan(money, waitMoney)){
				freeCash=money.subtract(waitMoney);//所有提现待审的-提现待审收费的=免费提现待审的
				//freeMoney=freeMoney.subtract(freeCash);
			}
		}catch(Exception e){
			
		}finally{
			return freeCash;
		}
	}

	@Override
	public AccountCash selectCashForUpdate(Integer id) {
		return this.accountCashDao.selectEntity("selectCashForUpdate", id);
	}

	@Override
	public boolean cancelCash(Integer userId, Integer cashId,BigDecimal cashMoney) {
		boolean flag=false;
		try{
			UserAccount userAccount = this.userAccountService.selectByUserIdForUpdate(userId);
			AccountCash accountCash = this.selectCashForUpdate(cashId);
			if(accountCash!=null){
				if(accountCash.getCashStatus()==AccountCash.cashApply){
					accountCash.setCashStatus(3);
					flag = this.updateCashStatus(accountCash);
				}
			}
			
			if(flag){
				BigDecimal big = cashMoney;
				userAccount.setUnavailableMoney(userAccount.getUnavailableMoney().subtract(big));
				userAccount.setAvailableMoney(userAccount.getAvailableMoney().add(big));
				flag = userAccountService.updateByPrimaryKeySelective(userAccount);
				if(flag){
					String remark="撤销提现，解除冻结金额"+cashMoney+"元";
					String type = AccountLog.TRADE_CODE_CANCEL;
					BigDecimal toMoney = cashMoney;
					BigDecimal size = new BigDecimal(0);
					flag =accountLogService.add(userAccount, type, toMoney, size, userId, remark, RequestUtils.getIpAddr());
				}
			}
		}catch(Exception e){
			logger.error("取消提现失败");
			
		}
		return flag;
	}
	
	/**
	 * 统计用户总提现
	 * @return BigDecimal
	 */
	public BigDecimal summaryCash(Integer status, Boolean flag) {
		return accountCashDao.summaryCash(status, flag);
	}
	
	/**
	 * 统计提现待审
	 * @return
	 */
	public Integer summaryCashNoCheck() {
		return accountCashDao.summaryCashNoCheck(AccountCash.cashApply);
	}

	@Override
	public AccountCash selectCashByNoOrderForUpdate(String noOrder) {
		// TODO Auto-generated method stub
		return this.accountCashDao.selectEntity("selectCashByNoOrderForUpdate", noOrder);
	}

	@Override
	public AccountCash selectByNoOrder(String noOrder) {
		// TODO Auto-generated method stub
		return this.accountCashDao.selectEntity("selectByNoOrder", noOrder);
	}
	
	/**
	 * 
	 * @param userId
	 * @param continueFee
	 * @param WDMtime
	 * @return
	 *//*
	public BigDecimal continueMoney(Integer userId,BigDecimal continueFee,Integer WDMtime){
		BigDecimal continueMoney=new BigDecimal(0);//回款续投的钱
		try{
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("logAddtime", DateUtils.formatDate(DateUtils.addDay(new Date(), -WDMtime)));
			BigDecimal queryContinueAwardMoney=this.accountLogService.queryContinueAwardMoney(params);
			if(CompareUtils.greaterThanZero(queryContinueAwardMoney)){
				continueMoney=queryContinueAwardMoney.divide(continueFee.divide(new BigDecimal(10000)));
			}
		}catch(Exception e){
			
		}
		return continueMoney;
	}
	*/
	public  DwzResult updateH5AccountCashById(Map<String, Object> param) {
 		DwzResult dwzResult=null;
 		boolean bool=false;
 		AccountCash accountCashF=this.selectByNoOrder(param.get("noOrder").toString());
 		try {
 			if(accountCashF==null){
 				System.out.println("not find "+param.get("noOrder")+param.get(param.get("cashMoney")));
 				
 			}else if(param.containsKey("cashStatuss")){
 				int cashStatus =Integer.parseInt(param.get("cashStatuss").toString());
 				AccountCash accountCashOld=this.selectCashByNoOrderForUpdate(param.get("noOrder").toString());
 				AccountCash accountCash=new AccountCash();
 				/*User user=(User) param.get("user");*/
 				if(cashStatus==1 && accountCashOld.getCashStatus() == AccountCash.cashing||accountCashOld.getCashStatus() == 5){
 					/*accountCash.setNoOrder(param.get("noOrder").toString());*/
 					
 					if(accountCashOld.getCashStatus().intValue()!=0){
 						if(accountCashOld.getCashStatus().intValue()==1){
 							throw new RuntimeException("已提现成功！不能重复提现！！");
 						}
 						if(accountCashOld.getCashStatus().intValue()==3){
 							throw new RuntimeException("已撤销提现！提现失败！");
 						}
 					}
 					accountCashOld.setVerifyUserid(1001);//连连提现
 					if(param.containsKey("info_order")){
 						accountCashOld.setVerifyRemark(param.get("info_order").toString());
 					}
 					accountCashOld.setCashStatus(1);
 					accountCashOld.setCashType(0);
 					bool=accountCashDao.update("updateByPrimaryKeySelective", accountCashOld);
 					if(bool){
 						dwzResult=userAccountService.updateUserAccountForAccountCash(param);
 					}else{
 						throw new RuntimeException("修改提现状态错误！！！");
 					}
 				}
 			}else{
 				throw new RuntimeException("提现审核错误！！！");
 			}
 		} catch (Exception e) {
 			throw new RuntimeException(e.getLocalizedMessage());
 		}
 		return dwzResult;
 	}
	/**
 	 * 处理提现失败
 	 * @param noOrder
 	 */
 	public  boolean exeCashFailure(String noOrder,String verifyName,String ipAddress,String verifyRemark){
 		boolean  flag=false;
 		AccountCash accountCashOld=this.selectCashByNoOrderForUpdate(noOrder);
     	
			AccountCash accountCash=new AccountCash();
			/*User user=(User) result.get("user");*/
     	   UserAccount userAccount=userAccountService.selectByUserIdForUpdate(accountCashOld.getUserId());
			BigDecimal unavailableMoney=userAccount.getUnavailableMoney().subtract(accountCashOld.getCashTotal());
			BigDecimal availableMoney=userAccount.getAvailableMoney().add(accountCashOld.getCashTotal()); 
			userAccount.setUnavailableMoney(unavailableMoney);
			userAccount.setAvailableMoney(availableMoney);
			//若提现失败改提现类型为0 
			accountCashOld.setCashType(0);
			accountCashOld.setCashStatus(2);
			accountCashOld.setCashAddip(ipAddress);
			accountCashOld.setVerifyName(verifyName);
			flag=accountCashDao.update("updateByPrimaryKeySelective", accountCashOld);
			userAccountService.updateByPrimaryKeySelective(userAccount);
			accountLogService.add(userAccount, AccountLog.WITHDRAW_APPLY_NO,accountCashOld.getCashTotal()
					,new BigDecimal(0), accountCashOld.getUserId(), 
					"用户["+accountCashOld.getUserAccount()+"]提现失败,解冻资金"+
					"["+accountCashOld.getCashTotal()+"]元"
					,ipAddress);
			
			MessageCenter center = new MessageCenter();
			center.setMessageContent("提现失败，失败原因【"+verifyRemark+"】");
			center.setMessageSendIp(RequestUtils.getIpAddr());
			//center.setReceiveUserId(accountCash.getUserId());
			center.setReceiveUserId(accountCashOld.getUserId());
			center.setMessageTitle("提现失败");
			center.setSendUserId(accountCash.getVerifyUserid());
		messageCenterService.send(center, Notice.WITHDRAW_NO);
		return flag;
			
 	}
}
