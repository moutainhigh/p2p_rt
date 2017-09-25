package com.rbao.east.service.borrow.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.util.StringUtils;

import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.Constants;
import com.rbao.east.common.SequenceUtils;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.dao.BorrowDao;
import com.rbao.east.dao.BorrowRepaymentDao;
import com.rbao.east.dao.BorrowRepossessedDao;
import com.rbao.east.dao.BorrowTenderDao;
import com.rbao.east.dao.InvestContinueDao;
import com.rbao.east.dao.UserAccountDao;
import com.rbao.east.dao.UserDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.AccountPayment;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRedeem;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowTransfer;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.InvestContinue;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.Recommend;
import com.rbao.east.entity.RecommendReward;
import com.rbao.east.entity.RedenvelopesRecord;
import com.rbao.east.entity.SysConfigParams;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.BorrowRedeemService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.BorrowTypeHandleService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.RecommendService;
import com.rbao.east.service.RedenvelopesService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.AgreementUtil;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DecimalUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.PdfUtils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.StringUtil;
import com.rbao.east.utils.SysCacheUtils;

/**
 * 定义标种
 * @author Liutq
 *
 */
@Service
@Transactional
public abstract class BorrowTypeHandleServiceImpl implements BorrowTypeHandleService {

	private static Logger log = LoggerFactory
			.getLogger(BorrowTypeHandleServiceImpl.class);
	@Autowired
	BorrowService borrowService ;
	@Autowired
	BorrowDao borrowDao;
	@Autowired
	UserAccountDao accountDao;
	@Autowired
	BorrowTenderDao tenderDao;
	@Autowired
	BorrowTenderService tenderService;
	@Autowired
	AccountLogService accountLogService;
	@Autowired
	BorrowRepossessedDao repossessedDao;
	@Autowired
	BorrowRepaymentDao repayDao;
	@Autowired
	UserDao userDao;
	@Autowired
	InvestContinueDao continueDao;
	@Autowired
	BorrowTypeService borrowTypeService;
	@Autowired
	RecommendService recommendService;
	@Autowired
	private MessageCenterService msgService;
	@Autowired
	UserCreditService userCreditService;
	@Autowired
	BorrowTransferService transferService;
	@Autowired
	BorrowRedeemService redeemService;
	@Autowired
	MessageCenterService messageCenterService;
	@Autowired
	UserService userService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private SysConfigParamService sysConfigParamService;
	@Autowired
	private RedenvelopesService redenvelopesService;
	/**
	 * 发标保存前做判断
	 * @return
	 */
	@Override
	public ServiceResult allowToPublishBorrow(Borrow borrow){
		//判断标题是否存在
		/*if(borrowService.getByBorrowName(borrow.getBorrowTitle()).size()>0){
			return new ServiceResult("192","["+borrow.getBorrowTitle()+"]已经存在，请重新输入标题");
		}*/
		//判断利率是否超出最大利率
		SysFeesRate fee = SysCacheUtils.getSysFeesRate();
		if(CompareUtils.greaterThanZero(fee.getSysLoanRate())
				&& CompareUtils.greaterThan(borrow.getAnnualInterestRate(), fee.getSysLoanRate())){
			return new ServiceResult("102","年利率不能超过"+DecimalUtils.getTwoDecimal(fee.getSysLoanRate())+"%");
		}
		if(CompareUtils.greaterThan(borrow.getMaxAmount(), borrow.getBorrowSum())){
			return new ServiceResult("103","最大投标金额不能超过总金额");
		}
		if(CompareUtils.greaterThan(borrow.getBorrowSum(), fee.getSysMaxLoan().multiply(new BigDecimal(10000)))){
			return new ServiceResult("109","借款总额不能超过"+fee.getSysMaxLoan()+"万元");
		}
		if(CompareUtils.greaterThanZero(borrow.getMaxAmount()) &&
				CompareUtils.greaterThan(borrow.getMinAmount(),borrow.getMaxAmount())){
			return new ServiceResult("104","最小投标金额不能超过最大投标金额");
		}
		if(CompareUtils.greaterThan(borrow.getMinAmount(),borrow.getBorrowSum())){
			return new ServiceResult("105","最小投标金额不能超过借款总额");
		}
		UserAccount borrowAcc = this.accountDao.selectByUserId(borrow.getUserId());
		return withinAllowBorrowMoney(borrowAcc,borrow);
	}
	/**
	 * 发标保存
	 */
	@Override
	public ServiceResult saveBorrow(Borrow borrow) {
		fullInBorrowOfSaveBorrow(borrow);
		boolean succ = borrowDao.insertSelective(borrow);
		if(borrow.getUserId().equals(Constants.ADMIN_USER_ID)){
			checkAndCreateAdminAccount();
		}
		//sendMsg(borrow.getUserId(),"标创建成功","标["+borrow.getBorrowTitle()+"]创建成功，等待初审",Notice.BORROW_CREATED);
		if(succ){
			return new ServiceResult(ServiceResult.SUCCESS,"发标保存成功");
		}else{
			return new ServiceResult("110","发标保存失败");
		}
	}
	/**
	 * 发送消息
	 * @param userid
	 * @param title
	 * @param content
	 * @param noticeType
	 */
	public void sendMsg(Integer userid,String title,String content,String noticeType){

		msgService.send(userid, title, content, noticeType);
	}
	/**
	 * 创建平台帐号
	 */
	public boolean checkAndCreateAdminAccount(){
		return accountDao.selectAdminAccount()!=null 
				&& accountDao.selectPlatformVouchAccount()!=null;
	}
	/**
	 * 保存发标之前，填充borrow
	 * @param borrow
	 * @throws  
	 */
	protected void fullInBorrowOfSaveBorrow(Borrow borrow) {
		Date now = new Date();
		if(borrow.getBorrowStatus() == null){
			borrow.setBorrowStatus(Borrow.STATUS_NEW);
		}
		if(borrow.getIsDay() == null){
			borrow.setIsDay(Borrow.IS_DAY_N); //默认是月标
		}
		if(borrow.getIsDay().equals(Borrow.IS_DAY_Y)){
			borrow.setRepaymentStyle(Borrow.REPAYMENT_STYLE_ONETIME); //天标都是一次性还款
			borrow.setEndTime(DateUtils.addDay(now, borrow.getBorrowTimeLimit()));
		}else{
			borrow.setEndTime(DateUtils.addMonth(now, borrow.getBorrowTimeLimit()));
		}
		borrow.setBorrowAddtime(now);
		borrow.setBorrowNo(SequenceUtils.getBorrowNo(borrow.getBidKind()+"", borrow.getUserId()));
		if(!StringUtils.isEmpty(borrow.getTenderPassword())){
			borrow.setTenderPassword(MD5Utils.stringToMD5(borrow.getTenderPassword())); //对投标密码加密
		}
	}

	
	/**
	 * 判断金额是否在可借款额度之内
	 * @param borrowAcc
	 * @param borrow
	 * @return
	 */
	public abstract ServiceResult withinAllowBorrowMoney(UserAccount borrowAcc,Borrow borrow) ;
	/**
	 * 计算利息（非复利计算）
	 * @param isDay 天标
	 * @param tenderMoney 投标金额
	 * @param apr 年利率
	 * @param borrowTimeLimit 借款期限
	 * @param repayStyle 还款方式
	 * @return
	 * @throws Exception
	 */
	public BigDecimal calculateInterest(boolean isDay,BigDecimal tenderMoney,BigDecimal apr,Integer borrowTimeLimit,Integer repayStyle ) {
		BigDecimal interest = new BigDecimal(0);
		if(isDay){ //天标
			interest = CalculateProcess.interestOfDay360(tenderMoney, apr, borrowTimeLimit);
		}else{
			if(repayStyle.equals(Borrow.REPAYMENT_STYLE_MONTHLY)){ //按月分期
				interest = CalculateProcess.interestOfMonthlyRepay(tenderMoney, apr, borrowTimeLimit);
			}else{
				//一次性还和每月付息到期还本计算是一样的
				interest =  CalculateProcess.interestOfMonth(tenderMoney, apr, borrowTimeLimit);
			}
		}
		return interest.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * 计算利息(收益复投)
	 * @param capital 本金
	 * @param yearRate 年利率
	 * @param period 期限
	 * @return
	 */
	public BigDecimal calculateInterestByInterestInverst(BigDecimal capital,BigDecimal yearRate,Integer period){
		BigDecimal total = CalculateProcess.getTotalByInterestInverst(capital,yearRate, period);
		return total.subtract(capital);
	}
	/**
	 * 判断是否可投标
	 */
	public ServiceResult allowToTender(BorrowTender tender,Map<String, String> params){
		
		Borrow borrow = borrowDao.selectByPrimaryKey(tender.getBorrowId());		
		User tenderUser = userDao.selectByPrimaryKey(tender.getUserId());
		SysFeesRate feeRate = SysCacheUtils.getSysFeesRate();
		//判断投标金额是否大于0
		BigDecimal tenderAmount=tender.getTenderAmount();
		
		//判断可用金额
		UserAccount acc = accountDao.selectByUserId(tender.getUserId());
		//判断标状态
		if(!Borrow.STATUS_FIRSTAUDIT_YES.equals(borrow.getBorrowStatus())){
			return new ServiceResult("201","当前标的状态为["+borrow.getViewStatus()+"],不允许投标");
		}
		//判断支付密码
		if(StringUtils.isEmpty(tenderUser.getUserPaypassword())){
			return new ServiceResult("255","请先设置支付密码");
		}
		DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
		DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
		
		if(params!=null){
		String payPassword = desEncrpt.decrypt(params.get("payPassword").toString());
		/*String tenderPassword=null;
		if(!StringUtils.isEmpty(borrow.getTenderPassword())){
			 tenderPassword = desEncrpt.decrypt(params.get("tenderPassword").toString());
		}*/
		//判断抵扣金，默认为0
		Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
		String deductionMoneyPercent = sysConfigParamMap.get("deductionMoney_percent");
		BigDecimal deductionMoney = tender.getDeductionMoney();
		if(-1 == tender.getTenderAmount().multiply(new BigDecimal(deductionMoneyPercent)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP).compareTo(deductionMoney)){
			return new ServiceResult("117", "抵扣金最多只能为投资金额的"+deductionMoneyPercent+"%，请重新输入！");
		}
		if(1 == tender.getDeductionMoney().compareTo(acc.getDeductionMoney())){
			return new ServiceResult("118", "抵扣金可用余额不足，请重新输入！");
		}
		//适用于短期标，开关
		//活动开关
		String activitySwitch = sysConfigParamMap.get("activity_switch");
		if(activitySwitch.equals("1")){
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String activityBeginStr = sysConfigParamMap.get("activity_begin");
			String activityEndStr = sysConfigParamMap.get("activity_end");
			//判断时间
			Date activityNow = new Date();
			Date activityBegin = null;
			Date activityEnd = null ;
			try {
				activityBegin = sdf.parse(activityBeginStr);
				activityEnd  = sdf.parse(activityEndStr);
				//活动时间范围内
				if(activityNow.before(activityEnd) && activityNow.after(activityBegin)){
					if(tender.getDeductionMoney().intValue() != 0){
						if(borrow.getBidKind() == 9 ||borrow.getBidKind() == 11 ||borrow.getBidKind() == 12 ||borrow.getBidKind() == 13 ){
							return new ServiceResult("119", "该标为长期标，不能使用抵扣金！");
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		/*}*/
		if(!tenderUser.getUserPaypassword().equals(MD5Utils.stringToMD5(aesEncrypt.encrypt(payPassword)))){
			return new ServiceResult("256","支付密码不正确");
		}
		//判断定向密码
		if(!StringUtils.isEmpty(borrow.getTenderPassword())&&
				!borrow.getTenderPassword().equals(MD5Utils.stringToMD5(params.get("tenderPassword")))){
			return new ServiceResult("257","投标密码输入不正确");
		}
		}
		//判断有效期
		if(CompareUtils.greaterThan(new Date(), borrow.getAllowTenderTime())){
			return new ServiceResult("202","此标已失效，不能继续投标");
		}
		//判断是否投自己的标
		if(tender.getUserId().intValue() == borrow.getUserId().intValue()){
			return new ServiceResult("203","不允许投自己发的标");
		}
		//如果投过，就不能再投新手标
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userId", tenderUser.getId());
		int count = tenderService.selectFirstBuy(result);
		if(count != 0 && borrow.getBidKind() == 8){
			return new ServiceResult("259","投资过的用户不能再次投资新手标");
		}
		
		if(CompareUtils.greaterThanZero(borrow.getMaxAmount())){
			//判断最大投标金额
			if(CompareUtils.greaterThan(tender.getTenderAmount(), borrow.getMaxAmount())){
				return new ServiceResult("205","投标金额不能大于["+DecimalUtils.getTwoDecimal(borrow.getMaxAmount())+"]元");
			}
			//判断累计投标金额
			BigDecimal tendedMoney = tenderService.selectSumByBorrowIdAndUserId(borrow.getId(), tender.getUserId());
			if(CompareUtils.greaterThan(tender.getTenderAmount().add(tendedMoney), borrow.getMaxAmount())){
				return new ServiceResult("206","累计投标金额不能大于["+borrow.getMaxAmount()+"]元");
			}
		}

		//判断是否超出总金额
		BigDecimal remainMoney = borrow.getBorrowSum().subtract(borrow.getTenderSum());
		if(CompareUtils.greaterThan(tender.getTenderAmount(), remainMoney)){
			/*tender.setTenderAmount(remainMoney);*/
			return new ServiceResult("206","投标金额超出此标还剩余金额["+DecimalUtils.getTwoDecimal(remainMoney)+"]元");
		}
		//判断最小投标金额
		if(CompareUtils.greaterThan(remainMoney, borrow.getMinAmount())){
			BigDecimal min = borrow.getMinAmount();
			if(CompareUtils.greaterThan(feeRate.getSysInvestMinmoney(), min)){
				min = feeRate.getSysInvestMinmoney();
			}
			if(CompareUtils.greaterThan(min, tender.getTenderAmount())){
				return new ServiceResult("204","投标金额不能小于["+min+"]元");
			}			
		}
		
		if(CompareUtils.greaterThan(tender.getTenderAmount().subtract(tender.getDeductionMoney()), acc.getAvailableMoney())){
			return new ServiceResult("207","可用余额不足，您的可用余额为["+DecimalUtils.getTwoDecimal(acc.getAvailableMoney())+"]元");
		}
		if(CompareUtils.greaterThan(borrow.getMinWaitRepossess(), acc.getWaitRepossessedCapital())){
			return new ServiceResult("208","投标失败，当前标限制投标最小代收款为["+borrow.getMinWaitRepossess()+"]元");
		}
		if(!CompareUtils.greaterThanZero(tender.getTenderAmount())){
			return new ServiceResult("258","投标金额不能小于0！！！");
		}
		return new ServiceResult(ServiceResult.SUCCESS);
		
	}
	
	/**
	 * 投标时填充对象
	 * @param tender
	 * @param borrow
	 */
	protected void fullInTenderAndBorrowOfSaveTender(BorrowTender tender,Borrow borrow){
		
		
		if(tender.getEffectiveAmount()==null){
			tender.setEffectiveAmount(tender.getTenderAmount());
		}			
		if(tender.getTenderStatus() == null){
			tender.setTenderStatus(BorrowTender.STATUS_NEW);
		}		
		if(tender.getCalInterestType() == null){
			tender.setCalInterestType(BorrowTender.CAL_INTEREST_TYPE_BACK);
		}	
		
		//计算利息
		BigDecimal interest = new BigDecimal(0);
		if(tender.getCalInterestType().equals(BorrowTender.CAL_INTEREST_TYPE_BACK)){//利息返还
			interest = calculateInterest(borrow.getIsDay().equals(Borrow.IS_DAY_Y),
									tender.getTenderAmount(),
									borrow.getAnnualInterestRate(),
									borrow.getBorrowTimeLimit(),
									borrow.getRepaymentStyle());
			
		}else if(tender.getCalInterestType().equals(BorrowTender.CAL_INTEREST_TYPE_INVERST)){ //复利
			interest = calculateInterestByInterestInverst(tender.getEffectiveAmount(),borrow.getMonthlyInterestRate(),borrow.getBorrowTimeLimit());
		}
		tender.setInterestAmount(interest);
		
		borrow.setTenderSum(borrow.getTenderSum().add(tender.getEffectiveAmount()));
		borrow.setTenderTimes(borrow.getTenderTimes()+1);
		if(CompareUtils.equals(borrow.getTenderSum(), borrow.getBorrowSum())){ 
			borrow.setBorrowStatus(Borrow.STATUS_FULL);//满标
		}
	}
	/**
	 * 冻结投标金额
	 * @param tender
	 * @param borrow
	 */
	protected void frozenTenderMoney(BorrowTender tender,Borrow borrow,UserAccount tenderAcc){
		//冻结金额:有效 - （amount - dedction）
		tenderAcc.setAvailableMoney(tenderAcc.getAvailableMoney().subtract(tender.getEffectiveAmount()).add(tender.getDeductionMoney()));
		tenderAcc.setUnavailableMoney(tenderAcc.getUnavailableMoney().add(tender.getEffectiveAmount()).subtract(tender.getDeductionMoney()));
		tenderAcc.setDeductionMoney(tenderAcc.getDeductionMoney().subtract(tender.getDeductionMoney()));
		accountDao.updateByPrimaryKeySelective(tenderAcc);
		//添加log
		if(tenderAcc.getDeductionMoney().intValue() == 0){
		accountLogService.add(tenderAcc, AccountLog.TRADE_CODE_MONEY_UNAVAILABLE, tender.getEffectiveAmount()
							,new BigDecimal(0), borrow.getUserId(), "投标成功，投标金额为["+tender.getEffectiveAmount()+"]元."
							,tender.getTenderAddip());
		}else{
		accountLogService.add(tenderAcc, AccountLog.TRADE_CODE_MONEY_UNAVAILABLE, tender.getEffectiveAmount()
				,new BigDecimal(0), borrow.getUserId(), "投标成功，投标金额为["+tender.getEffectiveAmount()+"]元,其中使用了抵扣金金额为["+tender.getDeductionMoney()+"]元."
				,tender.getTenderAddip());
		}
	}
	
	/**
	 * 保存投标
	 * @param tender
	 * @return
	 */
	public ServiceResult saveTender(BorrowTender tender,Map<String, String> params) {
		try {
			
			Borrow borrow = borrowDao.selectEntity("selectByPrimaryKeyForUpdate",tender.getBorrowId());	
			UserAccount tenderAcc = accountDao.selectByUserIdForUpdate(tender.getUserId());
			User tenderUser = userDao.selectByPrimaryKey(tender.getUserId());
			
			ServiceResult sResult = allowToTender(tender,params);
			if (!sResult.isSuccessed()) {
				return sResult;
			}
			
			fullInTenderAndBorrowOfSaveTender(tender,borrow); //根据标的不同，填充tender和borrow对象			
			tenderDao.insertSelective(tender);
			boolean succ = borrowDao.update("updateByPrimaryKeySelectiveByTender",borrow);
			if(!succ){
				throw new RuntimeException("updateByPrimaryKeySelectiveByTender error:"+borrow);
			}
			
			frozenTenderMoney(tender,borrow,tenderAcc);
			//投标成功发送消息
			String msg = "此标还差["+borrow.getBorrowSum().subtract(borrow.getTenderSum())+"]满标";
			if(borrow.getBorrowStatus().equals(Borrow.STATUS_FULL)){
				msg = "此标已满标";
			}
			Map<String,String> map= SysCacheUtils.getConfigParams();
			if(map.get("recommend_status").equals(SysConfigParams.recommend_open)){
				//推荐奖励，奖励固定金额
				if(this.tenderService.selectRepayedCountByUserId(tender.getUserId()) == 1){ //第一次投资，给介绍人加钱
					addRecommendMoney(tender.getUserId());  //推荐奖励
				}
			}
			if(tender.getDeductionMoney().intValue() == 0){
				sendMsg(tender.getUserId(),"投标成功","标["+borrow.getBorrowTitle()+"]投标成功，投标金额为："+tender.getEffectiveAmount()+"元.",Notice.LOAN_CREATED);
			}else{
				sendMsg(tender.getUserId(),"投标成功","标["+borrow.getBorrowTitle()+"]投标成功，投标金额为："+tender.getEffectiveAmount()+"元,其中抵扣金为："+tender.getDeductionMoney()+"元",Notice.LOAN_CREATED);
			}
			sendMsg(borrow.getUserId(),"["+borrow.getBorrowTitle()+"]投标成功","["+tenderUser.getUserAccount()+"]成功投标["+tender.getEffectiveAmount()+"]元。"+msg+"。",CreditType.INVEST_SUCCESS);
		} catch (DataAccessException e) {
			log.error("save tender error:"+tender,e);
			throw new RuntimeException("save tender error:"+tender);
		}
		return new ServiceResult(ServiceResult.SUCCESS,"投标成功");
	}
	/**
	 * 推荐奖励 
	 * @param userId
	 * @param money 投资金额
	 */
	public void recommonedAward(Integer userId,BigDecimal money){
		//推荐奖励
		Map<String,String> map= SysCacheUtils.getConfigParams();
		BigDecimal inviteRewardMoney =new BigDecimal(map.get("recommend_InviteRewardMoney"));
		if(inviteRewardMoney!=null){
			if(CompareUtils.greaterThanZero(inviteRewardMoney)){
				//推荐奖励，奖励投资人的千几
				//if(this.tenderService.selectRepayedCountByUserId(tender.getUserId()) == 1){ //第一次投资，给介绍人加钱
					//addRecommendMoney(userId, new BigDecimal(0), money);  //推荐奖励
					if(map.get("recommend_status").equals(SysConfigParams.recommend_open)){
						recommendService.allRecommendUser(userId,Recommend.initLevel ,new BigDecimal(0),money ,RecommendReward.StatusStaied,null,null);
					}
				//}
			}
		}
	}
	
	private void recommonedAward(Integer userId, BigDecimal money,
			Borrow borrow) {
		//推荐奖励
				Map<String,String> map= SysCacheUtils.getConfigParams();
				BigDecimal inviteRewardMoney =new BigDecimal(map.get("recommend_InviteRewardMoney"));
				if(inviteRewardMoney!=null){
					if(CompareUtils.greaterThanZero(inviteRewardMoney)){
						//推荐奖励，奖励投资人的千几
						//if(this.tenderService.selectRepayedCountByUserId(tender.getUserId()) == 1){ //第一次投资，给介绍人加钱
							//addRecommendMoney(userId, new BigDecimal(0), money);  //推荐奖励
							if(map.get("recommend_status").equals(SysConfigParams.recommend_open)){
								recommendService.allRecommendUser(userId,Recommend.initLevel ,new BigDecimal(0),money ,RecommendReward.StatusStaied,null,null,borrow);
							}
						//}
					}
				}
		
	}
	
	/**
	 * 借款协议书
	 */
	public void createAgreeMent(Borrow borrow){
		List<BorrowTender> tenderList = tenderService.selectByBorrowId(borrow.getId());
		try{
			for(BorrowTender tender : tenderList){
				createBorrowAgreement(borrow,tender.getId()); //借款协议
				//添加积分
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("tenderMoney", tender.getEffectiveAmount());
				params.put("period", borrow.getBorrowTimeLimit());
				params.put("isDay", borrow.getIsDay());
				userCreditService.addUserCreditAndLog(CreditType.INVEST_SUCCESS, tender.getUserId(),params);
				Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
				//活动内容
				/*getActivityValid(tender, borrow);*/
				recommonedAward(tender.getUserId(),tender.getEffectiveAmount(),borrow);
				//注册奖励
				userService.registerAward(tender.getUserId());
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 复审通过
	 * @param borrow
	 * @return
	 * @throws Exception
	 */
	public ServiceResult reviewSuccess(Borrow oborrow){
		Borrow borrow = borrowDao.selectEntity("selectByPrimaryKeyForUpdate",oborrow.getId());
		borrow.setVerifyReviewUser(oborrow.getVerifyReviewUser());
		borrow.setVerifyReviewRemark(oborrow.getVerifyReviewRemark());
		borrow.setVerifyReviewTime(oborrow.getVerifyReviewTime());
		List<BorrowTender> tenderList = tenderService.selectByBorrowId(borrow.getId());
		UserAccount borrowAcc = accountDao.selectByUserIdForUpdate(borrow.getUserId());
		
		try {
			//创建代收待还
			createRepayAndRepossessed(borrow,tenderList); 
						
			for(BorrowTender tender : tenderList){
				//处理红包
				getActivityValid(tender,borrow);
				UserAccount tenderAcc = accountDao.selectByUserIdForUpdate(tender.getUserId());
				//处理投标奖励
				//tenderRewardToTenderAccount(borrow,tender,tenderAcc,borrowAcc); 
				continueRewardToTenderAccount(tender,tenderAcc,borrow);
				//重新设置代收利息以免出现误差
				updateTenderInterestByRepossessed(tender);
				if(tender.getDeductionMoney().intValue() == 0){
					sendMsg(tender.getUserId(),"扣除冻结款","标["+borrow.getBorrowTitle()+"]复审通过，解除冻结款["+tender.getEffectiveAmount().subtract(tender.getDeductionMoney())+"]元。",Notice.LOAN_YES_ACCOUNT);
				}else{
					sendMsg(tender.getUserId(),"扣除冻结款","标["+borrow.getBorrowTitle()+"]复审通过，解除冻结款["+tender.getEffectiveAmount().subtract(tender.getDeductionMoney())+"]元,使用了抵扣金["+tender.getDeductionMoney()+"]元。",Notice.LOAN_YES_ACCOUNT);	
				}
				
			}
			//投标人的钱转入发标人账户
			tenderToBorrowAccount(tenderList,borrowAcc,borrow);
			for(BorrowTender tender : tenderList){
				 UserAccount tenderAcc = accountDao.selectByUserIdForUpdate(tender.getUserId());
				//处理投标奖励
				tenderRewardToTenderAccount(borrow,tender,tenderAcc,borrowAcc); 
			}
			deductLoanFee(borrowAcc,borrow,borrow.getTenderSum()); //扣除借款手续费
			borrow.setBorrowStatus(Borrow.STATUS_REPLYING);
			//修改endtime
			if(borrow.getIsDay().equals(Borrow.IS_DAY_Y)){
				borrow.setEndTime(DateUtils.addDay(new Date(), borrow.getBorrowTimeLimit()));
			}else{
				borrow.setEndTime(DateUtils.addMonth(new Date(), borrow.getBorrowTimeLimit()));
			}
			borrowDao.updateByPrimaryKeySelective(borrow); //修改标状态
			//发消息
			sendMsg(borrow.getUserId(),"标复审通过","标["+borrow.getBorrowTitle()+"]复审通过",Notice.BORROW_REVIEW_YES);
			userCreditService.addUserCreditAndLog(CreditType.BORROW_YES, borrow.getUserId());
			
		} catch (Exception e) {
			
			log.error("deal reviewSuccess error:"+borrow,e);
			throw new RuntimeException("deal reviewSuccess error:"+borrow);
		}
		
		
		return new ServiceResult(ServiceResult.SUCCESS,"复审成功");
	}
	/**
	 * 更新代收利息和代收本金，防止四舍五入出现误差
	 * @param tender
	 */
	public void updateTenderInterestByRepossessed(BorrowTender tender){
		List<BorrowRepossessed> repossesseList = this.repossessedDao.selectByTenderId(tender.getId());
		BigDecimal allInterest = new BigDecimal(0);
		BigDecimal allCapital = new BigDecimal(0);
		for(BorrowRepossessed repossess : repossesseList){
			allInterest = allInterest.add(repossess.getRepossessedInterest());
			allCapital = allCapital.add(repossess.getRepossessedCapital());
		}
		tender.setStayingInterest(allInterest);
		tender.setStayingAmount(allCapital);
		tender.setInterestAmount(allInterest);
		tender.setTenderStatus(BorrowTender.STATUS_REPAYING);
		this.tenderDao.updateByPrimaryKeySelective(tender);
	}
	/**
	 * 判断是否可还款
	 * @param repay
	 * @param repayAcc
	 * @return
	 */
	public ServiceResult allowToRepay(BorrowRepayment repay,UserAccount repayAcc){
		if(repay.getRepaymentStatus().equals(BorrowRepayment.REPAYMENT_STATUS_SUCCESS)){
			return new ServiceResult("315","当前记录已经还款成功，不能重复还款");
		}
		if(CompareUtils.greaterThan(repay.getRepaymentAmount(), repayAcc.getAvailableMoney())){
			return new ServiceResult("301","账户可用余额为"+DecimalUtils.getTwoDecimal(repayAcc.getAvailableMoney())+"元,不足以支付还款");
		}
		return new ServiceResult(ServiceResult.SUCCESS);
	}
	/**
	 * 还款
	 */
	public ServiceResult repay(BorrowRepayment repay){
		Borrow borrow = borrowDao.selectByPrimaryKey(repay.getBorrowId());
		
		UserAccount borAcc = accountDao.selectByUserId(borrow.getUserId());
		UserAccount repayAcc = accountDao.selectByUserId(repay.getRepayedUserId());
		
		ServiceResult result = allowToRepay(repay,repayAcc); //判断是否允许还款
		if(!result.getCode().equals(ServiceResult.SUCCESS)){
			return result;
		}
		
		try {
			List<BorrowRepossessed> repossessedList = repossessedDao.selectUnRepayedByRepayId(repay.getId());
			if(repossessedList.size()==0){
				return new ServiceResult("315","未能查到待收款列表，还款失败");
			}
			for(BorrowRepossessed repossessed : repossessedList){
				UserAccount repoAcc = accountDao.selectByUserId(repossessed.getUserId());
				//处理回款
				repayToRepossessedAccount(repoAcc,repayAcc,borAcc,repossessed,borrow); 
				//利息管理费
				repossessed.setDeductInterestFee(deductInterestFee(repoAcc,repossessed.getRepossessedInterest(),repay));
				//滞纳金管理费
				repossessed.setDeductLateInterestFee(deductPlatformLateInterestFee(repoAcc,repossessed.getLateInterest(),repay));
				//修改tender记录
				updateTenderByRepossessed(repossessed);
				//修改repossessed
				updateRepossessedOfRepayed(repossessed);
				sendMsg(repossessed.getUserId(),"回款",
					"标["+borrow.getBorrowTitle()+"]还款成功，回款本金["+repossessed.getRepossessedCapital()+"]元,利息["+repossessed.getRepossessedInterest()+"]元。"
					+"当前期数["+repossessed.getCurrentPeriod().intValue()+"/"+repossessed.getTotalPeriod().intValue()+"]",Notice.LOAN_PAY);
				//更新回款,总额-利息费-滞纳金管理费
				updateInvstContinueByRepossessed(repossessed.getUserId(),repossessed.getPrepareAmount()
																		.subtract(repossessed.getDeductInterestFee())
																		.subtract(repossessed.getDeductLateInterestFee()));
				//取消正在进行中的转让
				cancelTransfer(repossessed.getTenderId());
				//取消正在进行中的赎回
				cancelRedeem(repossessed.getTenderId());
			}
			//修改还款人账户资金
			this.accountDao.updateByPrimaryKeySelective(repayAcc);
			this.accountDao.updateNoneAssetByPk(borAcc);
			//修改borrow
			updateBorrowOfRepay(borrow,repay);
			
			repay.setRemark("还款成功");
			repay.setRepaymentPaidtime(new Date());
			repay.setRepaymentRealamount(repay.getRepaymentAmount());
			repay.setRepaymentStatus(BorrowRepayment.REPAYMENT_STATUS_SUCCESS);
			
			if(CompareUtils.greaterThan(repay.getRepaymentTime(), repay.getRepaymentPaidtime())){
				//按时还款添加积分
				userCreditService.addUserCreditAndLog("borrow_paymengt", borrow.getUserId());
			}
			sendMsg(repay.getRepayedUserId(),"还款",
					"标["+borrow.getBorrowTitle()+"]还款成功，还款本金["+repay.getRepaymentPrincipal()+"]元,利息["+repay.getRepaymentInterest()+"]元。"
					+"当前期数["+repay.getCurrentPeriod().intValue()+"/"+repay.getTotalPeriod().intValue()+"]",Notice.BORROW_REPAY);
		} catch (Exception e) {
			
			log.error("-----repay error:"+repay,e);
			repay.setRemark(e.toString());
			repay.setRepaymentStatus(BorrowRepayment.REPAYMENT_STATUS_FAILD);
			
			throw new RuntimeException("还款操作失败");
		}finally{
			this.repayDao.updateByPrimaryKeySelective(repay);
		}
		
		return new ServiceResult(ServiceResult.SUCCESS,"还款成功");
	}
	/**
	 * 取消正在进行中的转让
	 * @param tenderId
	 */
	public void cancelTransfer(Integer tenderId){
		List<BorrowTransfer> transfers = transferService.getByTenderId(tenderId);
		for(BorrowTransfer transfer : transfers){
			//正在进行中的转让
			if(transfer.getTransferStatus().equals(BorrowTransfer.STATUS_AUCTIONING)){
				transferService.cancelTransfer(transfer);
			}
		}
	}
	/**
	 * 取消赎回操作
	 * @param tenderId
	 */
	public void cancelRedeem(Integer tenderId){
		List<BorrowRedeem> redeemList = this.redeemService.getByTenderId(tenderId);
		for(BorrowRedeem redeem : redeemList){
			if(redeem.getRedeemStatus().equals(BorrowRedeem.STATUS_AUDITING)){
				Map<String, Object> param=new HashMap<String, Object>();
				param.put("redeemStatus", BorrowRedeem.STATUS_FAIL);
				param.put("redeemRemark", "投资回款，当前赎回自动处理为失败");
				param.put("id", redeem.getId());
				param.put("userId", Constants.ADMIN_USER_ID);
				param.put("ipAddress", RequestUtils.getIpAddr());
				redeemService.updateBorrowRedeem(param);
			}
		}
		
	}
	/**
	 * 还款成功修改Repossessed标
	 * @param repossess
	 */
	public void updateRepossessedOfRepayed(BorrowRepossessed repossess){
		repossess.setPaidAmount(repossess.getPaidAmount().add(repossess.getPrepareAmount()));
		repossess.setPaidDatetime(new Date());
		repossess.setRepossessedStatus(BorrowRepossessed.STATUS_REPOSSESSED_SUCC);
		this.repossessedDao.updateByPrimaryKeySelective(repossess);
	}
	/**
	 * 还款后修改borrow
	 * @param borrow
	 * @param repay
	 */
	public void updateBorrowOfRepay(Borrow borrow,BorrowRepayment repay){
		
		borrow.setRepaymentUser(repay.getRepayedUserId());
		borrow.setRepaymentAmount(borrow.getRepaymentAmount().add(repay.getRepaymentAmount()));
		borrow.setPaidAmount(borrow.getPaidAmount().add(repay.getRepaymentPrincipal()));
		borrow.setPaidInterest(borrow.getPaidInterest().add(repay.getRepaymentInterest()));
		borrow.setRepaymentTime(new Date());
		borrow.setPaymentAmount(repay.getRepaymentAmount());
		
		Integer[] inStatus = {BorrowTender.STATUS_NEW,BorrowTender.STATUS_OVERDUE,BorrowTender.STATUS_REPAYING };
		List<BorrowTender> unRepayedList = tenderDao.selectByInStatusAndBorrow(borrow.getId(), inStatus);
		if(unRepayedList.size() == 0){ //已还完
			borrow.setBorrowStatus(Borrow.STATUS_REPLY_SUCCESS);
		}
		this.borrowDao.updateByPrimaryKeySelective(borrow);
	}
	/**
	 * 更新还款帐号资金
	 * @param acc
	 * @param repay
	 * @param borrow
	 */
	public void updateRepayedAccount(UserAccount acc,BorrowRepayment repay,Borrow borrow){
		acc.setAllMoney(acc.getAllMoney().subtract(repay.getRepaymentAmount()));
		acc.setAvailableMoney(acc.getAvailableMoney().subtract(repay.getRepaymentAmount()));
		acc.setPayInterest(acc.getPayInterest().add(repay.getRepaymentInterest()).add(repay.getLateInterest()));
		acc.setWaitRepayCapital(acc.getWaitRepayCapital().subtract(repay.getRepaymentPrincipal()));
		acc.setWaitRepayInterest(acc.getWaitRepayInterest().subtract(repay.getRepaymentInterest()));
		this.accountDao.updateByPrimaryKeySelective(acc);
		//添加log
		accountLogService.add(acc, AccountLog.TRADE_CODE_REPAY, repay.getRepaymentAmount()
							,new BigDecimal(0), null, 
							"标["+borrow.getBorrowTitle()+"]还款成功" +
							"，支付本金["+repay.getRepaymentPrincipal()+"]元" +
							"，利息["+repay.getRepaymentInterest()+"]元" +
							(CompareUtils.greaterThanZero(repay.getLateInterest())?
							"，滞纳金["+repay.getLateInterest()+"]元":"")
							,repay.getRepaymentAddip(),AccountPayment.PAYSTATUS_SUBTRACT);		
	}
	
	
	/**
	 * 创建标书的借款协议
	 * @param borrow
	 */
	public void createBorrowAgreement(Borrow borrow,Integer tenderId){
		BorrowType borrowType = this.borrowTypeService.getBorrowTypeById(borrow.getBidKind());
		if(StringUtils.isEmpty(borrowType.getAgreementContent())){ //没有协议内容，直接返回
			return;
		}
		BorrowTender borrowTender=tenderService.selectBorrowTenderByBorrowTenderId(tenderId);
		User user=userDao.selectByPrimaryKey(borrowTender.getUserId());
		if(null==user){
			return;
		}
		String filePath = createBorrowAgreementPDF(borrowType.getAgreementContent(),borrow,user.getUserEmail(),borrowTender.getUserId(),borrowTender);
		//协议路径保存到borrow
		BorrowTender tmpTender = new BorrowTender();
		tmpTender.setId(tenderId);
		tmpTender.setAgreementPath(filePath);
		tmpTender.setAgreementTime(new Date());
		this.tenderDao.updateByPrimaryKeySelective(tmpTender);
	}
	
	@SuppressWarnings("rawtypes")
	private String createBorrowAgreementPDF(String content,Borrow borrow,String userEmail,Integer tenderUser) {
		
		String mailContent = SysCacheUtils.getSysConfig().getSysWebsitesignature()
				+ "--标【"+ borrow.getBorrowTitle()+ "】借款协议书,密码为您的邮箱账号！！！";
		String mailTitle = SysCacheUtils.getSysConfig().getSysWebsitesignature()
				+ "--标【"+ borrow.getBorrowTitle()+ "】借款协议书";
		Map m = new HashMap();//扩展域
		return AgreementUtil.createAgreementThenMailTo(StringUtil.replaceString(content,buildReplaceAgreeContentMap(borrow)),
				buildAgreementTenderList(borrow,tenderUser), tenderUser,mailContent,mailTitle, m);
	}
	
	
	@SuppressWarnings("rawtypes")
	private String createBorrowAgreementPDF(String content,Borrow borrow,String userEmail,Integer tenderUser,BorrowTender borrowTender) {
		
		String mailContent = SysCacheUtils.getSysConfig().getSysWebsitesignature()
				+ "--标【"+ borrow.getBorrowTitle()+ "】借款协议书,密码为您的邮箱账号！！！";
		String mailTitle = SysCacheUtils.getSysConfig().getSysWebsitesignature()
				+ "--标【"+ borrow.getBorrowTitle()+ "】借款协议书";
		Map m = new HashMap();//扩展域
		return AgreementUtil.createAgreementThenMailTo(StringUtil.replaceString(content,buildReplaceAgreeContentMap(borrow,tenderUser,borrowTender)),
				buildAgreementTenderList(borrow,tenderUser), tenderUser,mailContent,mailTitle, m);
	}
	
	public Map<String,Object> buildReplaceAgreeContentMap(Borrow borrow){
		Map<String,Object> map = new HashMap<String,Object>();
		if(null != borrow.getUser() && !"".equals(borrow.getUser())){
			map.put(Constants.VAR_BORROW_USER, borrow.getUser().getUserAccount());
		}
		map.put(Constants.VAR_AGREE_NO, borrow.getBorrowNo());
		map.put(Constants.VAR_DATE, DateUtils.formatDate(new Date()));
		return map;
	}
	
	
	public Map<String,Object> buildReplaceAgreeContentMap(Borrow borrow,Integer tenderUser,BorrowTender borrowTender){
		Map<String,Object> map = new HashMap<String,Object>();
		if(null != borrow.getUser() && !"".equals(borrow.getUser())){
			
			if(null != borrow.getUser().getUserRealname() && !"".equals(borrow.getUser().getUserRealname())){
				map.put(Constants.VAR_BORROW_USER, borrow.getUser().getUserRealname());
			}else{
				map.put(Constants.VAR_BORROW_USER, borrow.getUser().getUserAccount());
			}
			
		}
		map.put(Constants.VAR_AGREE_NO, borrow.getBorrowNo());
		map.put(Constants.VAR_DATE, DateUtils.formatDate("yyyy年MM月dd日 ",new Date()));
		
		User tenderUserEnity = userService.getById(tenderUser);
		
		
		if(null != tenderUserEnity.getUserRealname() && !"".equals(tenderUserEnity.getUserRealname())){
			map.put(Constants.VAR_TENDER_USER, tenderUserEnity.getUserRealname());
		}else{
			map.put(Constants.VAR_TENDER_USER, tenderUserEnity.getUserAccount());
		}
		if(null != tenderUserEnity.getCardNumber() && !"".equals(tenderUserEnity.getCardNumber())){
			map.put(Constants.VAR_TENDER_USER_CARDNUMBER, tenderUserEnity.getCardNumber());
		}else{
			map.put(Constants.VAR_TENDER_USER_CARDNUMBER, "暂无实名认证");
		}
		
		
		map.put(Constants.VAR_BORROW_TITLE, borrow.getBorrowTitle());
		map.put(Constants.VAR_BORROW_APR, borrow.getAnnualInterestRate()+"%");
		String periodStr = borrow.getBorrowTimeLimit()+"个月";
		if(borrow.getIsDay().equals(Borrow.IS_DAY_Y)){
			periodStr = borrow.getBorrowTimeLimit()+"天";
		}
		
		map.put(Constants.VAR_BORROW_PERIOD, periodStr);
		map.put(Constants.VAR_TENDER_AMOUNT, borrowTender.getTenderAmount());
		map.put(Constants.VAR_TENDER_AMOUNT_DAXIE, PdfUtils.number2CNMontrayUnit(borrowTender.getTenderAmount()));
		
		
		map.put(Constants.VAR_BORROW_ENDTIME, DateUtils.formatDate("yyyy年MM月dd日 ",borrow.getEndTime()));
		
		
		
		return map;
	}
	
	/**
	 * 借款协议，构建投标人列表
	 * @param borrow
	 * @param bType
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String[]> buildAgreementTenderList(Borrow borrow,Integer tenderUserId){
		Map m = new HashMap();
		m.put("borrowId", borrow.getId());
		m.put("inStatus", new Integer[]{BorrowTender.STATUS_OVERDUE,BorrowTender.STATUS_REPAYING});
		List<Map> tenderList = this.tenderDao.selects("selectAgreePdfList", m);  
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{ "出借人(id)", "借款金额", "借款期限","年利率", "借款开始日", "借款到期日", "截止还款日", "还款本息" });
		for(Map tenderMap : tenderList){
			Integer userId=(Integer) tenderMap.get("userId");
			String tenderUser = (String) tenderMap.get("usrName");
			if(!userId.toString().equals(tenderUserId.toString())){
				tenderUser=tenderUser.substring(0,3)+"***";
			}
			BigDecimal capital = (BigDecimal) tenderMap.get("amount");
			BigDecimal interest = (BigDecimal) tenderMap.get("interest");
		//	Date tenderTime = (Date) tenderMap.get("tenderTime");
			BigDecimal tenderAmount = (BigDecimal) tenderMap.get("effeAmount");
			//期限
			String periodStr = borrow.getBorrowTimeLimit()+"个月";
			if(borrow.getIsDay().equals(Borrow.IS_DAY_Y)){
				periodStr = borrow.getBorrowTimeLimit()+"天";
			}
			//截止还款日
			String repayDate = "";
			if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_ONETIME)){
				repayDate = DateUtils.formatDate(borrow.getEndTime());
			}else{
				repayDate = "每月"+DateUtils.getDay(borrow.getEndTime())+"日";
			}
			//开始日
			list.add(new String[]{tenderUser
								 ,StringUtil.toString(tenderAmount)
								 ,periodStr
								 ,borrow.getAnnualInterestRate()+"%"
								 ,DateUtils.formatDate(borrow.getVerifyReviewTime())
								 ,DateUtils.formatDate(borrow.getEndTime())
								 ,repayDate
								 ,StringUtil.toString(capital.add(interest))});
		}
		
		
		return list;
	}
	/**
	 * 扣除借款手续费
	 */
	public void deductLoanFee(UserAccount borrowAcc,Borrow borrow,BigDecimal borrowAmount){
		BigDecimal fee = getLoanFee(borrow.getIsDay().equals(Borrow.IS_DAY_Y),borrow.getBorrowTimeLimit()
									,borrowAmount);
		if(!CompareUtils.greaterThanZero(fee)){
			return ; //没有平台管理费，直接返回
		}
		
		//从系统账户中将钱转入投资者账户
		borrowAcc.setAllMoney(borrowAcc.getAllMoney().subtract(fee));
		borrowAcc.setAvailableMoney(borrowAcc.getAvailableMoney().subtract(fee));
		accountDao.updateByPrimaryKeySelective(borrowAcc);
		//添加log
		accountLogService.add(borrowAcc, AccountLog.TRADE_CODE_LOAN_FEE, fee
							,new BigDecimal(0), Constants.ADMIN_USER_ID, 
							"标["+borrow.getBorrowTitle()+"]已生效，支付借款手续费["+DecimalUtils.getTwoDecimal(fee)+"]元"
							,null,AccountPayment.PAYSTATUS_SUBTRACT);
		
		UserAccount adminAcc = accountDao.selectAdminAccount();
		adminAcc.setAllMoney(adminAcc.getAllMoney().add(fee));
		adminAcc.setAvailableMoney(adminAcc.getAvailableMoney().add(fee)); 
		accountDao.updateByPrimaryKeySelective(adminAcc);
		//添加log
		accountLogService.add(adminAcc, AccountLog.TRADE_CODE_LOAN_FEE, fee
							,new BigDecimal(0), borrowAcc.getUserId(), 
							"收取借款手续费["+DecimalUtils.getTwoDecimal(fee)+"]元"
							,null);
		
	}
	/**
	 * 获取借款手续费
	 * @param isDay
	 * @param borrowTimeLimit
	 * @param amount
	 * @return
	 */
	public BigDecimal getLoanFee(boolean isDay,int borrowTimeLimit,BigDecimal amount){
		if(isDay){
			return CalculateProcess.getLoanFeeByDay(borrowTimeLimit, amount);
		}else{
			return CalculateProcess.getLoanFeeByMonth(borrowTimeLimit, amount);
		}
	}
	/**
	 * 修改tender记录
	 * @param repossessed
	 */
	public void updateTenderByRepossessed(BorrowRepossessed repossessed){
		BorrowTender tender = this.tenderDao.selectByPrimaryKey(repossessed.getTenderId());
		//已还利息
		tender.setInterestPaid(tender.getInterestPaid().add(repossessed.getRepossessedInterest()));
		//已还本金
		tender.setPaidAmount(tender.getPaidAmount().add(repossessed.getRepossessedCapital()));
		//待还利息
		tender.setStayingInterest(tender.getStayingInterest().subtract(repossessed.getRepossessedInterest()));
		//待还本金
		tender.setStayingAmount(tender.getStayingAmount().subtract(repossessed.getRepossessedCapital()));
		
		if(CompareUtils.greaterThanZero(tender.getStayingAmount())){ 
			tender.setTenderStatus(BorrowTender.STATUS_REPAYING); 
		}else{
			tender.setTenderStatus(BorrowTender.STATUS_REPAYED); //已还清
		}
		tenderDao.updateByPrimaryKeySelective(tender);
	}
	/**
	 * 利息管理费
	 * @param tender
	 * @param tenderAcc
	 */
	public BigDecimal deductInterestFee(UserAccount acc,BigDecimal interest,BorrowRepayment repay){
		try {
			BigDecimal fee = CalculateProcess.getInterestFee(interest);
			
			if(!CompareUtils.greaterThanZero(fee))
				return new BigDecimal(0);
			//从系统账户中将钱转入投资者账户
			acc.setAllMoney(acc.getAllMoney().subtract(fee));
			acc.setAvailableMoney(acc.getAvailableMoney().subtract(fee));
			acc.setGetInterest(acc.getGetInterest().subtract(fee));
			accountDao.updateByPrimaryKeySelective(acc);
			//添加log
			accountLogService.add(acc, AccountLog.TRADE_CODE_INTEREST_FEE, fee
								,new BigDecimal(0), Constants.ADMIN_USER_ID, 
								"支付利息管理费["+DecimalUtils.getTwoDecimal(fee)+"]元"
								,repay.getRepaymentAddip());
			
			UserAccount adminAcc = accountDao.selectAdminAccount();
			adminAcc.setAllMoney(adminAcc.getAllMoney().add(fee));
			adminAcc.setAvailableMoney(adminAcc.getAvailableMoney().add(fee)); 
			adminAcc.setGetInterest(adminAcc.getGetInterest().add(fee));
			accountDao.updateByPrimaryKeySelective(adminAcc);
			//添加log
			accountLogService.add(adminAcc, AccountLog.TRADE_CODE_INTEREST_FEE, fee
								,new BigDecimal(0), acc.getUserId(), 
								"获得利息管理费["+DecimalUtils.getTwoDecimal(fee)+"]元"
								,repay.getRepaymentAddip());
			//
			//addRecommendMoney(acc.getUserId(),fee,new BigDecimal(0));  //按比例给推荐人奖励
			Map<String,String> map= SysCacheUtils.getConfigParams();
			if(map.get("recommend_status").equals(SysConfigParams.recommend_open)){
				recommendService.allRecommendUser(acc.getUserId(),Recommend.initLevel ,fee,new BigDecimal(0),RecommendReward.StatusStaied,null,null);
			}
		
			return fee;
		} catch (Exception e) {
			
			log.error("continueRewardToTenderAccount error:"+repay,e);
			throw new RuntimeException("continueRewardToTenderAccount error:"+repay);
		}
	}
	/**
	 * 给当前投标人的上线添加推荐奖励
	 * 从利息管理费中扣除
	 */
	public void addRecommendMoney(Integer userId){
		try {
			Recommend re = recommendService.getByUserId(userId);
			if(re == null){ //没有推荐人
				return;
			}
			re.setRecommendStatus(Recommend.RECOMMEND_STATUS_SUCC);
			re.setVerifyRemark("投标自动审核通过");
			re.setVerifyAddtime(new Date()); 
			recommendService.saveRecommend(re,re.getRecommendMoney());
			//推荐奖励固定金额
			this.recommendService.saveOrUpdateData(re, re.getRecommendMoney(), new BigDecimal(0),new BigDecimal(0), RecommendReward.initLevel,RecommendReward.StatusStaied,null,null);
		} catch (Exception e) {
			
		}
	}
	/**
	 * 滞纳金管理费
	 * @param acc
	 * @param interest
	 * @param repay
	 */
	public BigDecimal deductPlatformLateInterestFee(UserAccount acc,BigDecimal lateInterest,BorrowRepayment repay){
		try {
			BigDecimal fee = CalculateProcess.getLateInterestFee(lateInterest);
			if(!CompareUtils.greaterThanZero(fee))
				return new BigDecimal(0);
			//从系统账户中将钱转入投资者账户
			acc.setAllMoney(acc.getAllMoney().subtract(fee));
			acc.setAvailableMoney(acc.getAvailableMoney().subtract(fee));
			acc.setGetInterest(acc.getGetInterest().subtract(fee)); 
			accountDao.updateByPrimaryKeySelective(acc);
			//添加log
			accountLogService.add(acc, AccountLog.TRADE_CODE_PLATFORM_LATE_INTEREST_FEE, fee
								,new BigDecimal(0), Constants.ADMIN_USER_ID, 
								"支付滞纳金管理费["+DecimalUtils.getTwoDecimal(fee)+"]元"
								,repay.getRepaymentAddip());
			
			UserAccount adminAcc = accountDao.selectAdminAccount();
			adminAcc.setAllMoney(adminAcc.getAllMoney().add(fee));
			adminAcc.setAvailableMoney(adminAcc.getAvailableMoney().add(fee)); 
			adminAcc.setGetInterest(adminAcc.getGetInterest().add(fee)); 
			accountDao.updateByPrimaryKeySelective(adminAcc);
			//添加log
			accountLogService.add(adminAcc, AccountLog.TRADE_CODE_PLATFORM_LATE_INTEREST_FEE, fee
								,new BigDecimal(0), acc.getUserId(), 
								"支付滞纳金管理费["+DecimalUtils.getTwoDecimal(fee)+"]元"
								,repay.getRepaymentAddip());
			return fee;
		} catch (Exception e) {
			
			log.error("continueRewardToTenderAccount error:"+repay,e);
			throw new RuntimeException("continueRewardToTenderAccount error:"+repay);
		}
	}
	/**
	 * 续投奖励
	 * @throws Exception 
	 */
	public void continueRewardToTenderAccount(BorrowTender tender,UserAccount tenderAcc,Borrow borrow){
		try {
			InvestContinue invContinue = continueDao.getByUserId(tenderAcc.getUserId());
			User user = userDao.selectByPrimaryKey(tenderAcc.getUserId());
			if(invContinue == null){
				return;//没有续投直接返回
			}
			BigDecimal continueMoney = getContinueMoney(invContinue,tender.getEffectiveAmount());//续投金额
			
			BigDecimal continueReward = CalculateProcess.getContinueReward(continueMoney); //计算续投奖励

			continueDao.updateByPrimaryKeySelective(invContinue); //更新InvestContinue
			
			if(!CompareUtils.greaterThanZero(continueReward))
				return; //没有续投，直接返回
			
			//从系统账户中将钱转入投资者账户
			tenderAcc.setAllMoney(tenderAcc.getAllMoney().add(continueReward));
			tenderAcc.setAvailableMoney(tenderAcc.getAvailableMoney().add(continueReward));
			tenderAcc.setGetReward(tenderAcc.getGetReward().add(continueReward));
			accountDao.updateByPrimaryKeySelective(tenderAcc);
			//添加log
			accountLogService.add(tenderAcc, AccountLog.TRADE_CODE_CONTINUE_REWARD, continueReward
								,new BigDecimal(0), Constants.ADMIN_USER_ID, 
								"标["+borrow.getBorrowTitle()+"]已生效,获得续投奖励["+DecimalUtils.getTwoDecimal(continueReward)+"]元"
								+";当前续投信息如下["+invContinue.getUserInvestRepayment()+"-"+invContinue.getUserRecharge()+"]"
								,tender.getTenderAddip());
			
			UserAccount adminAcc = accountDao.selectAdminAccount();
			adminAcc.setAllMoney(adminAcc.getAllMoney().subtract(continueReward));
			adminAcc.setAvailableMoney(adminAcc.getAvailableMoney().subtract(continueReward)); 
			adminAcc.setPayReward(adminAcc.getPayReward().add(continueReward));
			accountDao.updateByPrimaryKeySelective(adminAcc);
			//添加log
			accountLogService.add(adminAcc, AccountLog.TRADE_CODE_CONTINUE_REWARD, continueReward
								,new BigDecimal(0), tender.getUserId(), 
								"标["+borrow.getBorrowTitle()+"]已生效,向用户["+user.getUserAccount()+"]支付续投奖励["+DecimalUtils.getTwoDecimal(continueReward)+"]元"
								,tender.getTenderAddip(),AccountPayment.PAYSTATUS_SUBTRACT);
		} catch (Exception e) {
			
			log.error("continueRewardToTenderAccount error:"+tender,e);
			throw new RuntimeException("continueRewardToTenderAccount error:"+tender);
		}
	}
	/**
	 * 获取续投金额
	 * 续投规则：
	 * 		1.充值，InvestRepayment 不变，UserRecharge + 充值的金额
	 * 		2.取现，先减掉InvestRepayment   UserRecharge，(UserRecharge < 取现金额)剩余部分再从减
	 * 		3.回款，InvestRepayment += 回款金额，UserRecharge 不变
	 * 		4.投标 如下。
	 * @param invContinue
	 * @param tenderMoney
	 * @return
	 */
	public BigDecimal getContinueMoney(InvestContinue invContinue,BigDecimal tenderMoney){
		//add by 20140202
		//先用续投表中的充值金额和投标金额比较，抵掉所有的充值金额的才算回款以后的续投金额
		BigDecimal backMoney = tenderMoney.subtract(invContinue.getUserRecharge());
		
		//如果backMoney如果大于零，那么投资金额-充值金额剩下的余额就为续投金额
		if(CompareUtils.greaterThanZero(backMoney)){
			invContinue.setUserRecharge(new BigDecimal(0));
			
			if(!CompareUtils.greaterThan(invContinue.getUserInvestRepayment(), backMoney)){ 
				BigDecimal xutouMoney = invContinue.getUserInvestRepayment();
				invContinue.setUserInvestRepayment(new BigDecimal(0));//更新回款金额
				return xutouMoney;
			}else{
				invContinue.setUserInvestRepayment(invContinue.getUserInvestRepayment().subtract(backMoney));
				return backMoney;
			}
		}else{
			//如果投资金额-充值金额<0.则没有续投金额，充值金额=充值金额-投资金额，续投金额=0
			invContinue.setUserRecharge(invContinue.getUserRecharge().subtract(tenderMoney));
			return new BigDecimal(0);
		}
	}

	/**
	 * 投标奖励
	 * @param borrow
	 * @param tender
	 * @param tenderAcc
	 * @param borrowAcc
	 */
	public void tenderRewardToTenderAccount(Borrow borrow,BorrowTender tender,UserAccount tenderAcc,UserAccount borrowAcc){
		
		BigDecimal reward = null;
		if(CompareUtils.greaterThanZero(borrow.getPartAmount())){
			reward = CalculateProcess.getTenderRewardPart(borrow.getBorrowSum(), tender.getEffectiveAmount()
								, borrow.getPartAmount());
		}else if(CompareUtils.greaterThanZero(borrow.getProportionRate())){
			reward = CalculateProcess.getTenderRewardByTenderRate(borrow.getProportionRate()
											, tender.getEffectiveAmount());
		}
		if(!CompareUtils.greaterThanZero(reward))
			return; //没有奖励则直接返回
		
		tenderAcc.setAllMoney(tenderAcc.getAllMoney().add(reward));
		tenderAcc.setAvailableMoney(tenderAcc.getAvailableMoney().add(reward));
		tenderAcc.setGetReward(tenderAcc.getGetReward().add(reward));
		accountDao.updateByPrimaryKeySelective(tenderAcc);
		//添加log
		accountLogService.add(tenderAcc, AccountLog.TRADE_CODE_TENDER_REWARD, reward
							,new BigDecimal(0), borrowAcc.getUserId(), 
							"标["+borrow.getBorrowTitle()+"]已生效，获得投标奖励["+reward+"]元"
							,tender.getTenderAddip());
		
		borrowAcc.setAllMoney(borrowAcc.getAllMoney().subtract(reward));
		borrowAcc.setAvailableMoney(borrowAcc.getAvailableMoney().subtract(reward));
		borrowAcc.setPayReward(borrowAcc.getPayReward().add(reward));
		accountDao.updateByPrimaryKeySelective(borrowAcc);
		//添加log
		accountLogService.add(borrowAcc, AccountLog.TRADE_CODE_TENDER_REWARD, reward
							,new BigDecimal(0), tenderAcc.getUserId(), 
							"标["+borrow.getBorrowTitle()+"]已生效，向用户["+tenderAcc.getUser().getUserAccount()+"]支付投标奖励["+DecimalUtils.getTwoDecimal(reward)+"]元"
							,tender.getTenderAddip());
	}
	/**
	 * 投标人的钱转入发标人账户
	 * @param tenderList
	 * @param borrowAcc
	 */
	public void tenderToBorrowAccount(List<BorrowTender> tenderList,UserAccount borrowAcc,Borrow borrow){

		for(BorrowTender tender : tenderList){
			 UserAccount tenderAcc = accountDao.selectByUserIdForUpdate(tender.getUserId()); 
			User tenderUser = userDao.selectByPrimaryKey(tender.getUserId());
			//投标人
			tenderAcc.setAllMoney(tenderAcc.getAllMoney().add(tender.getStayingInterest()));
			tenderAcc.setUnavailableMoney(tenderAcc.getUnavailableMoney().add(tender.getDeductionMoney()).subtract(tender.getEffectiveAmount()));
			tenderAcc.setWaitRepossessedCapital(tenderAcc.getWaitRepossessedCapital().add(tender.getStayingAmount()));
			tenderAcc.setWaitRepossessedInterest(tenderAcc.getWaitRepossessedInterest().add(tender.getStayingInterest()));
			
			accountDao.updateByPrimaryKeySelective(tenderAcc);
			//添加log
			if(tender.getDeductionMoney().intValue() == 0 ){
			accountLogService.add(tenderAcc, AccountLog.TRADE_CODE_TENDER_SUCC, tender.getEffectiveAmount()
								,new BigDecimal(0), borrowAcc.getUserId(), 
								"投标["+borrow.getBorrowTitle()+"]已生效，投标金额为["+tender.getEffectiveAmount()+"]元，待收利息["+tender.getStayingInterest()+"]元"
								,tender.getTenderAddip());
			}else{
			accountLogService.add(tenderAcc, AccountLog.TRADE_CODE_TENDER_SUCC, tender.getEffectiveAmount()
					,new BigDecimal(0), borrowAcc.getUserId(), 
					"投标["+borrow.getBorrowTitle()+"]已生效，投标金额为["+tender.getEffectiveAmount()+"]元，使用抵扣金为["+tender.getDeductionMoney()+"]元，待收利息["+tender.getStayingInterest()+"]元"
					,tender.getTenderAddip());
			}
			
			//发标人
			borrowAcc.setAllMoney(borrowAcc.getAllMoney().add(tender.getEffectiveAmount()));
			borrowAcc.setAvailableMoney(borrowAcc.getAvailableMoney().add(tender.getEffectiveAmount()));
			borrowAcc.setWaitRepayCapital(borrowAcc.getWaitRepayCapital().add(tender.getEffectiveAmount()));
			borrowAcc.setWaitRepayInterest(borrowAcc.getWaitRepayInterest().add(tender.getStayingInterest()));
			
			accountLogService.add(borrowAcc, AccountLog.TRADE_CODE_BORROW_SUCC, tender.getEffectiveAmount()
					,new BigDecimal(0), tenderUser.getId(), 
					"标["+borrow.getBorrowTitle()+"]已生效，向用户["+tenderUser.getUserAccount()
								+"]成功借款["+tender.getEffectiveAmount()+"]元，" +
					"生成待还利息["+tender.getStayingInterest()+"]元"
					,null);
		}		
		accountDao.updateByPrimaryKeySelective(borrowAcc);
	}
	/**
	 * 回款操作，包括滞纳金保存到投标人账户
	 * @param repoAcc 
	 * @param tenderList
	 * @param borrowAcc
	 */
	public void repayToRepossessedAccount(UserAccount repoAcc,UserAccount repayAcc,UserAccount borAcc,BorrowRepossessed repossessed,Borrow borrow){
		//投标人
		User repoUser = this.userDao.selectByPrimaryKey(repoAcc.getUserId());
		repoAcc.setAllMoney(repoAcc.getAllMoney().add(repossessed.getLateInterest()));
		repoAcc.setAvailableMoney(repoAcc.getAvailableMoney().add(repossessed.getPrepareAmount()));
		repoAcc.setWaitRepossessedCapital(repoAcc.getWaitRepossessedCapital().subtract(repossessed.getRepossessedCapital())); 
		repoAcc.setWaitRepossessedInterest(repoAcc.getWaitRepossessedInterest().subtract(repossessed.getRepossessedInterest()));
		repoAcc.setGetInterest(repoAcc.getGetInterest().add(repossessed.getRepossessedInterest())
									.add(repossessed.getLateInterest()));
		accountDao.updateByPrimaryKeySelective(repoAcc);
		//添加log
		accountLogService.add(repoAcc, AccountLog.TRADE_CODE_REPOSSESSED, repossessed.getPrepareAmount()
				,repossessed.getRepossessedCapital(), borrow.getUserId(), 
				"标["+borrow.getBorrowTitle()+"]回款成功，" +
				"本金["+repossessed.getRepossessedCapital()+"]元," +
				"利息["+repossessed.getRepossessedInterest()+"]元"+
				(CompareUtils.greaterThanZero(repossessed.getLateInterest())?
							",滞纳金["+repossessed.getLateInterest()+"]元":"")
				,null);
		
		//还款人
		repayAcc.setAllMoney(repayAcc.getAllMoney().subtract(repossessed.getPrepareAmount()));
		repayAcc.setAvailableMoney(repayAcc.getAvailableMoney().subtract(repossessed.getPrepareAmount()));
		
		borAcc.setPayInterest(borAcc.getPayInterest().add(repossessed.getRepossessedInterest()).add(repossessed.getLateInterest()));
		borAcc.setWaitRepayCapital(borAcc.getWaitRepayCapital().subtract(repossessed.getRepossessedCapital()));
		borAcc.setWaitRepayInterest(borAcc.getWaitRepayInterest().subtract(repossessed.getRepossessedInterest()));
		
		//添加log
		accountLogService.add(repayAcc, AccountLog.TRADE_CODE_REPAY, repossessed.getPrepareAmount()
							,new BigDecimal(0), repoAcc.getUserId(), 
							"标["+borrow.getBorrowTitle()+"]还款成功" +
							"。向用户["+repoUser.getUserAccount()+"]支付本金["+repossessed.getRepossessedCapital()+"]元" +
							"，利息["+repossessed.getRepossessedInterest()+"]元" +
							(CompareUtils.greaterThanZero(repossessed.getLateInterest())?
							"，滞纳金["+repossessed.getLateInterest()+"]元":"")
							,null,AccountPayment.PAYSTATUS_SUBTRACT);		
		
	}
	/**
	 * 更新续投表的回款金额
	 * @param userId
	 * @param money
	 */
	public void updateInvstContinueByRepossessed(Integer userId,BigDecimal money){
		InvestContinue invContinue = continueDao.getByUserId(userId);
		if(invContinue == null){
			invContinue = new InvestContinue();
			invContinue.setUserId(userId);
			invContinue.setUserInvestRepayment(money);
		}else{
			invContinue.setUserInvestRepayment(invContinue.getUserInvestRepayment().add(money));
		}
		continueDao.saveOrUpdate(invContinue);
	}
	/**
	 * 创建代收待还记录
	 * @param borrow
	 * @throws Exception 
	 */
	protected void createRepayAndRepossessed(Borrow borrow,List<BorrowTender> tenderList){
		borrow.setMonthlyInterestRate(CalculateProcess.getMonthlyRate(borrow.getAnnualInterestRate())); //月利率
		
		if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_ONETIME)){ //一次性还
			createRepayAndRepossessed(borrow,tenderList,1,null);
		}else if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_MONTHLY)){//每月分期
			createRepayAndRepossessedOfRepaymentMonthly(borrow,tenderList);
		}else if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_MONTHLY_INTEREST_DUE_ALL)){//按月付息，到期还本
			createRepayAndRepossessedOfRepaymentMonthlyInterestDueAll(borrow,tenderList);
		}
	}
	/**
	 * 创建代收待还对象总的逻辑类，包含一次性、每月分期、
	 * 每月付息到期还本（利息复投与利息每期返还逻辑）
	 * @param borrow
	 * @param tenderList
	 * @throws Exception 
	 */
	protected BorrowRepayment buildRepaymentAndRepossessed(Borrow borrow,List<BorrowTender> tenderList,Integer seq,Map<String,BigDecimal> monthlyMap){

		Date now = new Date();
		Date lastRepayTime = null;
				
		BigDecimal allTenderInterest = new BigDecimal(0);//所有投标人的利息总和
		BigDecimal allTenderCapital = new BigDecimal(0);//所有投标人的本金总和
		Integer totalPeriod = borrow.getBorrowTimeLimit(); //总还款期数
		List<BorrowRepossessed> repossessList = new ArrayList<BorrowRepossessed>();
		for(BorrowTender tender : tenderList){
			
			BigDecimal capital 	= null, //本金
					   interest = null; //利息
			BigDecimal capitalOld = null; //用于每月分期，还原本金
			if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_ONETIME)){ //一次性还
				totalPeriod = 1; 
				//一次性还款，只有一期,
				capital = tender.getEffectiveAmount().subtract(tender.getDeductionMoney());
				interest = tender.getInterestAmount();
				if(lastRepayTime == null){
					lastRepayTime = DateUtils.addMonth(now, borrow.getBorrowTimeLimit()); //
					if(borrow.getIsDay().equals(Borrow.IS_DAY_Y)){ //天标
						lastRepayTime = DateUtils.addDay(now, borrow.getBorrowTimeLimit()); //
					}
				}
			}else if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_MONTHLY)){//每月分期
				MathContext mc = new MathContext(12,RoundingMode.HALF_DOWN);
				BigDecimal pastCapital = new BigDecimal(0); //历史本金总和
				String mapKey = "PAST_CAPITAL_"+tender.getId();
				if(monthlyMap.get(mapKey) != null){
					pastCapital = monthlyMap.get(mapKey);
				}
				//利息+本金
				BigDecimal totalMoney = CalculateProcess.getPaymentMoneyPerMonth(tender.getEffectiveAmount(), borrow.getBorrowTimeLimit(), borrow.getAnnualInterestRate());
				interest  = tender.getEffectiveAmount().subtract(pastCapital).multiply(borrow.getMonthlyInterestRate())
									.setScale(2,BigDecimal.ROUND_HALF_UP); //保留2位
				if(tender.getDeductionMoney().intValue() != 0){
					BigDecimal subMoney = tender.getDeductionMoney().divide(new BigDecimal(borrow.getBorrowTimeLimit()),mc);
					capital  = totalMoney.subtract(interest).subtract(subMoney);
				}else{
					capital  = totalMoney.subtract(interest);
				}
				capitalOld = totalMoney.subtract(interest);
				/*if(seq.intValue() == borrow.getBorrowTimeLimit().intValue()){ //最后一期,修正代收本金
					//投标有效本金-代收本金的差值，如果不为0就需要修正
					BigDecimal totalCapital = pastCapital.add(capital);
					BigDecimal diffCapital = tender.getEffectiveAmount().subtract(totalCapital);
					if(CompareUtils.notEqualsZero(diffCapital)){
						capital = capital.add(diffCapital);
						interest = interest.subtract(diffCapital);
					}
				}*/
				if (seq.intValue() == borrow.getBorrowTimeLimit().intValue()) { // 最后一期,修正代收本金
					// 投标有效本金-代收本金的差值，如果不为0就需要修正
					BigDecimal totalCapital = pastCapital.add(totalMoney.subtract(interest));
					BigDecimal diffCapital = tender.getEffectiveAmount()
							.subtract(totalCapital);
					if (CompareUtils.notEqualsZero(diffCapital)) {
						//不加此判断会出现利息为负数的情况,比如投标金额1000,24个月,年化11,投四笔300,210,400,80,此时最后一笔待还、代收会为负数，但是本金+利息仍然是正常的和其他期数相同
						if (CompareUtils.greaterThan(interest, diffCapital)) {
							if(tender.getDeductionMoney().intValue() != 0){
							    //	BigDecimal subMoney = tender.getDeductionMoney().divide(new BigDecimal(borrow.getBorrowTimeLimit()),BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_DOWN);
								capitalOld = totalMoney.subtract(interest).add(diffCapital);
								capital = capital.add(diffCapital);
							}else{
								capitalOld = capital.add(diffCapital);
								capital = capital.add(diffCapital);
							}
						/*	capital = capital.add(diffCapital);*/
							interest = interest.subtract(diffCapital);
						}
					}
				}
				
				monthlyMap.put(mapKey, pastCapital.add(capitalOld)); //加到历史本金
				if(lastRepayTime == null){
					lastRepayTime = DateUtils.addMonth(now,seq); //获取最后还款时间
				}
				
			}else if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_MONTHLY_INTEREST_DUE_ALL)){//每月付息，到期还本
				
				if(tender.getCalInterestType().equals(BorrowTender.CAL_INTEREST_TYPE_BACK)){//利息返还
					if(seq.intValue() == borrow.getBorrowTimeLimit().intValue()){ //最后一期
						capital = tender.getEffectiveAmount().subtract(tender.getDeductionMoney());
					}else{
						capital	= new BigDecimal(0); //本金
					}
					interest = tender.getEffectiveAmount().multiply(borrow.getMonthlyInterestRate())//利息
											.setScale(2,BigDecimal.ROUND_HALF_UP); //保留2位;
					
				}else if(tender.getCalInterestType().equals(BorrowTender.CAL_INTEREST_TYPE_INVERST)){//利息复投
					if(seq.intValue() == borrow.getBorrowTimeLimit().intValue()){ //最后一期
						capital = tender.getEffectiveAmount();
						interest = calculateInterestByInterestInverst(tender.getEffectiveAmount(),borrow.getMonthlyInterestRate(),borrow.getBorrowTimeLimit());
					}else{
						capital	= new BigDecimal(0); //本金
						interest= new BigDecimal(0);//利息
					}
				}
				
				if(lastRepayTime == null){
					lastRepayTime = DateUtils.addMonth(now,seq); //
				}
			}
			
			BorrowRepossessed repossess = new BorrowRepossessed();
			
			repossess.setRepossessedStatus(BorrowRepossessed.STATUS_REPOSSESSING);
			repossess.setCurrentPeriod(seq);
			repossess.setTotalPeriod(totalPeriod);
			repossess.setRepossessedSequence(seq); 
			repossess.setUserId(tender.getUserId());
			repossess.setTenderId(tender.getId());
			repossess.setPrepareDatetime(lastRepayTime); //最后还款时间
			
			repossess.setRepossessedCapital(capital);//本金
			/*repossess.setRepossessedCapital(capital.subtract(tender.getDeductionMoney()));*/
			repossess.setRepossessedInterest(interest);//利息
			repossess.setPrepareAmount(repossess.getRepossessedCapital().
										add(repossess.getRepossessedInterest()));//总额
			repossess.setRepossessedAddtime(now);
			repossessList.add(repossess);
			
			allTenderInterest = allTenderInterest.add(repossess.getRepossessedInterest());
			//一次性还款
			if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_ONETIME)){
				allTenderCapital = allTenderCapital.add(repossess.getRepossessedCapital().add(tender.getDeductionMoney()));
			}else if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_MONTHLY)){ //每月还款
				MathContext mc = new MathContext(12,RoundingMode.HALF_DOWN);
				BigDecimal subMoney = tender.getDeductionMoney().divide(new BigDecimal(borrow.getBorrowTimeLimit()),mc);
				allTenderCapital = allTenderCapital.add(repossess.getRepossessedCapital().add(subMoney));
			}else{ //按月还息，到期还本
				if(seq.intValue() == borrow.getBorrowTimeLimit().intValue()){ //最后一期
					allTenderCapital = allTenderCapital.add(repossess.getRepossessedCapital().add(tender.getDeductionMoney()));
				}else{
					allTenderCapital = allTenderCapital.add(repossess.getRepossessedCapital());
				}
			}
		}
		//插入待还
		BorrowRepayment repay = new BorrowRepayment();
		repay.setBorrowId(borrow.getId());
		repay.setRepaymentTime(lastRepayTime);
		repay.setRepaymentStatus(BorrowRepayment.REPAYMENT_STATUS_REPAYING);
		repay.setRepaymentInterest(allTenderInterest);
		repay.setRepaymentPrincipal(allTenderCapital);
		repay.setRepaymentSequence(seq);
		repay.setCurrentPeriod(seq);
		repay.setTotalPeriod(totalPeriod);
		repay.setRepaymentAmount(repay.getRepaymentPrincipal()
							.add(repay.getRepaymentInterest()));
		
		repay.setBorrowRepossessedlist(repossessList);
		return repay;
		
	}
	/**
	 * 插入代收待还
	 * 
	 * @param borrow
	 * @param tenderList
	 * @throws Exception 
	 */
	protected void createRepayAndRepossessed(Borrow borrow,List<BorrowTender> tenderList,Integer seq,Map<String,BigDecimal> monthlyMap){
		BorrowRepayment repay = buildRepaymentAndRepossessed(borrow, tenderList, seq, monthlyMap);
		repayDao.saveOrUpdate(repay);
		//插入代收
		for(BorrowRepossessed repossess : repay.getBorrowRepossessedlist()){
			repossess.setRepaymentId(repay.getId());
			this.repossessedDao.insertSelective(repossess);
		}
		
	}
	/**
	 * 按月分期插入代收待还记录
	 * @param borrow
	 * @param tenderList
	 */
	protected void createRepayAndRepossessedOfRepaymentMonthly(Borrow borrow,List<BorrowTender> tenderList){
		Map<String,BigDecimal> monthlyMap = new HashMap<String, BigDecimal>(); //用来记录已经保存的本金
		 for(int i=1;i<=borrow.getBorrowTimeLimit().intValue();i++){
			 createRepayAndRepossessed(borrow,tenderList,i,monthlyMap);
		 }
	}
	/**
	 * 每月付息到期还款插入代收待还记录
	 * @param borrow
	 * @param tenderList
	 * @throws Exception
	 */
	protected void createRepayAndRepossessedOfRepaymentMonthlyInterestDueAll(Borrow borrow,List<BorrowTender> tenderList){
		 for(int i=1;i<=borrow.getBorrowTimeLimit();i++){
			 createRepayAndRepossessed(borrow,tenderList,i,null);
		 }
	}
	/**
	 * 
	* @Title: getActivityValid
	* @Description: 判断是否处于红包发放有效内
	* @return void    返回类型
	* @throws
	 */
	public boolean getActivityValid(BorrowTender tender,Borrow borrow){
		boolean flag = false;
		Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
		//活动开关
		String activitySwitch = sysConfigParamMap.get("activity_switch");
		if(activitySwitch.equals("1")){
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String activityBeginStr = sysConfigParamMap.get("activity_begin");
			String activityEndStr = sysConfigParamMap.get("activity_end");
			
			//判断时间
			Date activityNow = new Date();
			Date activityBegin = null;
			Date activityEnd = null ;
			try {
				activityBegin = sdf.parse(activityBeginStr);
				activityEnd  = sdf.parse(activityEndStr);
				//活动时间范围内
				if(activityNow.before(activityEnd) && activityNow.after(activityBegin)){
					//未使用抵扣金
					if(tender.getDeductionMoney().intValue() == 0){
						//判断该用户是否达到1000红包的上线
						Map<String,Object> map=new HashMap<String, Object>();
						map.put("userId", tender.getUserId());
						map.put("tenderAddtime", activityBegin);
						//查询一定时间内投资成功的总金额
						map.put("tenderStatus", new Integer [] {BorrowTender.STATUS_REPAYING,BorrowTender.STATUS_REPAYED,BorrowTender.STATUS_OVERDUE,BorrowTender.STATUS_TRANSFER});
						map.put("borrowKinds", new Integer [] {BorrowType.TYPE_MINGXING,BorrowType.TYPE_LIUYUE,BorrowType.TYPE_JIUYUE,BorrowType.TYPE_SHEERYUE});
						//已投资金额
						BigDecimal tenderMoney=this.tenderService.queryTenderMoneyByKind(map);
						/*tenderMoney = tenderMoney.subtract(tender.getEffectiveAmount()).setScale(2, RoundingMode.HALF_UP);*/
						//投资金额，小于100000
						if(tenderMoney.compareTo(new BigDecimal(100000)) == -1){
							//长期标
							if(borrow.getBidKind() == 9 ||borrow.getBidKind() == 11 ||borrow.getBidKind() == 12 ||borrow.getBidKind() == 13 ){
								getActivityRecommend(tender,borrow,tenderMoney);
								flag = true;
							}
						}
						
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return flag;
	}
	
	/**
	 * 
	* @Title: getActivityRecommend
	* @Description: 该方法，用于双12活动
	* @return void    返回类型
	* @throws
	 */
	public void getActivityRecommend(BorrowTender tender,Borrow borrow,BigDecimal tenderAllMoney){
		//第一步，发放红包。第二步，发送消息内容。第三步，对用户的累计收益进行处理
		RedenvelopesRecord env = new RedenvelopesRecord();
		Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
		String activityName = sysConfigParamMap.get("activity_name");
		String activityTime = sysConfigParamMap.get("activity_limit_time");
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		env.setName(activityName);
		env.setSendUserId(Constants.ADMIN_USER_ID);
		Date now = new Date();
		env.setSendTime(new Date());
		env.setPeriodBeginTime(now);
		try {
			env.setPeriodEndTime(sdf.parse(activityTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	MathContext mc = new MathContext(4,RoundingMode.HALF_UP);
		//赠送红包金额，如果投资金额的1%小于剩余，赠送全部，如果大于剩余，赠送余下
		BigDecimal envMoney = tender.getEffectiveAmount().divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal alreadyMoney = tenderAllMoney.divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal unUserMoney = new BigDecimal(1000).subtract(alreadyMoney).setScale(2, RoundingMode.HALF_UP);
		if(envMoney.compareTo(unUserMoney) == 1){
			envMoney = unUserMoney;
		}
		env.setAmount(envMoney);
		env.setRedType(RedenvelopesRecord.TYPE_CASH);
		env.setStatus(RedenvelopesRecord.STATUS_NOT_OPEN);
		env.setRemark("活动期间内，投资"+tender.getEffectiveAmount()+"元，发放"+envMoney+"元现金红包");
		env.setUserId(tender.getUserId());
		boolean flag = redenvelopesService.addRedenvelopesRecord(env);
		//如果发放成功，添加日志
		if(flag == true){
			sendMsg(tender.getUserId(),"投资红包","标["+borrow.getBorrowTitle()+"]投标成功，投标金额为："+tender.getEffectiveAmount()+"元。处于平台活动期间，收到现金红包为："+envMoney+"元。",Notice.LOAN_CREATED);
		}
	} 
	
	/**
	 * 重新发标
	 */
	public ServiceResult updateBorrow(Borrow borrow){
		ServiceResult result = new ServiceResult("110","重新发标失败");
		boolean bl = false;
		bl = borrowDao.updateByPrimaryKeySelective(borrow);
		if(bl){
			result = new ServiceResult(ServiceResult.SUCCESS,"重新发标保存成功");
		}
		return result;
	}
	
}
