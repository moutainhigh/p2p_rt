package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.dao.BorrowDao;
import com.rbao.east.dao.BorrowTenderDao;
import com.rbao.east.dao.BorrowTransferAuctionDao;
import com.rbao.east.dao.BorrowTransferDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowTransfer;
import com.rbao.east.entity.BorrowTransferAuction;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.AgreementUtil;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DecimalUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.StringUtil;
import com.rbao.east.utils.SysCacheUtils;


@Service
@Transactional
public class BorrowTransferServiceImpl implements BorrowTransferService{
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BorrowTransferDao transferDao;
	@Autowired
	private BorrowRepossessedService borrowRepossessedService;
	@Autowired
	private BorrowTenderService borrowTenderService;
	@Autowired
	private BorrowTenderDao tenderDao;
	@Autowired
	private BorrowDao borrowDao;
	@Autowired
	private BorrowTransferAuctionDao borrowTransferAuctionDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	private UserService userService;
	
	public BigDecimal getTransferMoney(Integer tenderId) {
		try {
			List<BorrowRepossessed> waitRepoList = borrowRepossessedService.selectWaitRepossessByTender(tenderId);
			BigDecimal[] money = getRepoCapitalAndInterest(waitRepoList);
			BorrowRepossessed repo = waitRepoList.get(0); //最近一次代收
			Date beginTime = repo.getRepossessedAddtime();
			if(repo.getCurrentPeriod().intValue() > 1){ //不是第一期
				beginTime = DateUtils.addMonth(repo.getPrepareDatetime(), -1);//上期回款日
			}
			BigDecimal transferMoney = CalculateProcess.getTransferMoney(money[0], beginTime,repo.getPrepareDatetime(), repo.getRepossessedInterest().add(repo.getLateInterest()));
		//	BigDecimal curInterest = CalculateProcess.getUnRepayInterest(beginTime,repo.getRepossessedInterest());
			return transferMoney;
			//查询未到账的利息
		} catch (Exception e) {
			
			return null;
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void save(BorrowTransfer transfer) {
		Map<String, String> cfg = SysCacheUtils.getConfigParams();
		try {
			BorrowTender tender = tenderDao.selectByPrimaryKey(transfer.getTenderId());
			Borrow borrow = borrowDao.selectByPrimaryKey(tender.getBorrowId());
			
			//转让手续费
			BigDecimal transferFee=new BigDecimal(0);
			
			if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_MONTHLY)){
				List<BorrowRepossessed> waitRepoList = borrowRepossessedService.selectWaitRepossessByTender(transfer.getTenderId());
				transferFee=CalculateProcess.getDayInterest(waitRepoList.get(0).getRepossessedInterest(), 15);
			}else{
				transferFee=CalculateProcess.getTransferFee(tender.getEffectiveAmount(), borrow.getAnnualInterestRate());
			}
			Date now = new Date();
			List<BorrowRepossessed> waitRepoList = borrowRepossessedService.selectWaitRepossessByTender(transfer.getTenderId());
			BorrowRepossessed repo = waitRepoList.get(0);
			BigDecimal[] money = getRepoCapitalAndInterest(waitRepoList);
			
			transfer.setTransferStatus(BorrowTransfer.STATUS_AUCTIONING);
			transfer.setCreateTime(now);
			int periodDay = Integer.parseInt(cfg.get("tsf_period"));
			transfer.setEndTime(DateUtils.addHour(now, 24 * periodDay));//转让期限
			transfer.setTransferFee(transferFee);
			transfer.setRepossessedCapital(money[0]); //代收本金
			transfer.setRepossessedInterest(money[1]);
			transfer.setUserId(tender.getUserId());
			
			/*if(borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_ONETIME)){
				int dayPeriod = 0;
				if(borrow.getIsDay().equals(Borrow.IS_DAY_Y)){ //天标
					dayPeriod = DateUtils.daysBetween(repo.getRepossessedAddtime(), repo.getPrepareDatetime());
				}else{
					dayPeriod = DateUtils.getMonthSpace(repo.getRepossessedAddtime(), repo.getPrepareDatetime());
					dayPeriod = dayPeriod*30;
				}
				transfer.setTransferInterestRate(CalculateProcess.getYearRateOfOneTimeRepay(money[1], transfer.getTransferMoney(), dayPeriod));
			}else{ //分期
				transfer.setTransferInterestRate(CalculateProcess.getYearRate(money[1], transfer.getTransferMoney(), waitRepoList.size()));
			}*/
			
			if (borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_ONETIME)) {
				int dayPeriod = 0;
				if (borrow.getIsDay().equals(Borrow.IS_DAY_Y)) { // 天标
					dayPeriod = DateUtils.daysBetween( repo.getRepossessedAddtime(), repo.getPrepareDatetime());
				} else {
					Integer borrowTimeLimit = borrow.getBorrowTimeLimit();
					dayPeriod=borrowTimeLimit*30;
				}
				// 债权转让后重新计划年化利率
				BigDecimal interest = money[1]; //待收利息
				BigDecimal capital = money[0];	//待收本金
				BigDecimal transferInterest = interest.add(capital).subtract(transfer.getTransferMoney());//接收人所获得利息收益
				transfer.setTransferInterestRate(CalculateProcess.getYearRateOfOneTimeRepay(transferInterest, transfer.getTransferMoney(), dayPeriod));
			} else { // 分期
				// 债权转让后重新计划年化利率
				BigDecimal interest = money[1];  //待收利息
				BigDecimal capital = money[0];	//待收本金
				BigDecimal transferInterest = interest.add(capital).subtract(transfer.getTransferMoney());//接收人所获得利息收益
				transfer.setTransferInterestRate(CalculateProcess.getYearRate(transferInterest, transfer.getTransferMoney(), waitRepoList.size()));
			}
			transferDao.insertSelective(transfer);

			Map param = new HashMap();
			param.put("transferId", transfer.getId());
			param.put("waitRepoList", waitRepoList);
			transferDao.insertObj("insertborrowTransferRepossess", param);
			
			tender.setTransferCount(tender.getTransferCount().intValue()+1);
			tenderDao.updateByPrimaryKeySelective(tender);
		} catch (Exception e) {
			
			log.error("add borrowTransfer error:"+transfer,e);
			throw new RuntimeException();
		}
	}
	private BigDecimal[] getRepoCapitalAndInterest(List<BorrowRepossessed> waitRepoList){
		BigDecimal capital = new BigDecimal(0);
		BigDecimal interest = new BigDecimal(0);
		for(BorrowRepossessed repo : waitRepoList){
			capital = capital.add(repo.getRepossessedCapital());
			interest = interest.add(repo.getRepossessedInterest()).add(repo.getLateInterest());
		}
		return new BigDecimal[]{capital,interest};
	}

	@Override
	public ServiceResult allowToTransfer(BorrowTransfer transfer) {
		Map<String, String> cfg = SysCacheUtils.getConfigParams();
		//查询历史转让记录
		Date lastEndTime = null;
		for(BorrowTransfer lastTransfer : getByTenderId(transfer.getTenderId())){
			if(lastTransfer.getTransferStatus().equals(BorrowTransfer.STATUS_SUCCESS)){
				return new ServiceResult("107","当前记录已经转让成功，不能重复操作");
			}else if(lastTransfer.getTransferStatus().equals(BorrowTransfer.STATUS_AUCTIONING)){
				return new ServiceResult("108","已经有正在进行中的转让，不能重复操作");
			}else if(lastTransfer.getTransferStatus().equals(BorrowTransfer.STATUS_FAILD)){
				if(lastEndTime == null){
					lastEndTime = lastTransfer.getEndTime();
				}else{
					if(CompareUtils.greaterThan(lastTransfer.getEndTime(), lastEndTime)){
						lastEndTime = lastTransfer.getEndTime();
					}
				}
			}
		}
	//	System.out.println("lastEndTime == "+lastEndTime);
		//距离上次转让需满24小时
		if(lastEndTime != null){
			Date allowTime = DateUtils.addHour(lastEndTime,Integer.parseInt(cfg.get("tsf_Interval_hours"))); 
			if(CompareUtils.greaterThan(allowTime, new Date())){
				return new ServiceResult("109","请在["+DateUtils.formatDate(DateUtils.dateTimePattern,allowTime)+"]过后再对此投资进行转让操作");
			}
		}
		BorrowTender tender = tenderDao.selectByPrimaryKey(transfer.getTenderId());
		if(tender == null){
			return new ServiceResult("103","未能找到当前记录，请刷新页面重试");
		}
		if(!tender.getTenderStatus().equals(BorrowTender.STATUS_REPAYING)
			&&!tender.getTenderStatus().equals(BorrowTender.STATUS_OVERDUE)){
			return new ServiceResult("115","当前投资记录不允许转让");
		}
		//同一个投资转让不能超过3次
		int tsfCountMax = Integer.parseInt(cfg.get("tsf_count_max"));
		if(tender.getTransferCount().intValue()>=tsfCountMax){
			return new ServiceResult("104","当前记录已经提交转让"+tsfCountMax+"次，不能再转让");
		}
		if(tender.getTenderType().equals(BorrowTender.TENDER_TYPE_TRANSFER)){
			return new ServiceResult("116","当前投资为债权转让，不允许再次转让操作");
		}
		//15天后才能转让
		List<BorrowRepossessed> borrowRepossessed = borrowRepossessedService.selectWaitRepossessByTender(tender.getId());
		if(borrowRepossessed.size()>0){
			Date effTime = borrowRepossessed.get(0).getRepossessedAddtime();			
			Date allowTransferTime = DateUtils.preciseToDay(DateUtils.addDay(effTime, Integer.valueOf(cfg.get("tsf_tender_interval_min"))));//15天后才能转让  
			if(CompareUtils.greaterThan(allowTransferTime, DateUtils.preciseToDay(new Date()))){
				return new ServiceResult("118","请在["+DateUtils.formatDate(DateUtils.DEFAULT_FORMAT,allowTransferTime)+"]过后再对此投资进行转让操作");
			}
		}
		//还款日前三天不能转让
		if(borrowRepossessed.size()>0){
			Date prepareDate = borrowRepossessed.get(0).getPrepareDatetime();			
			int daysBetween = DateUtils.daysBetween(new Date(), prepareDate);
			int repayDayMin = Integer.parseInt(cfg.get("tsf_repay_interval_min"));
			if(daysBetween <= repayDayMin){
				return new ServiceResult("119","当前投资距离还款时间不足"+repayDayMin+"天，不允许转让");
			}
		}
		return new ServiceResult(ServiceResult.SUCCESS);
	}
	public List<BorrowTransfer> getByTenderId(Integer tenderId){
		return getByTenderIdAndStatus(tenderId,null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BorrowTransfer> getByTenderIdAndStatus(Integer tenderId,Integer[] status){
		Map param = new HashMap();
		param.put("tenderId", tenderId);
		param.put("inStatus", status);
		return this.transferDao.select("getByTenderIdAndStatus", param);
	}
	@Override
	public PageModel selectBorrowTransList(Map<String, String> param) {
		// TODO Auto-generated method stub
		PageModel model=transferDao.getPage("selectBorrowTransList", "selectBorrowCountTransList", param);
		new DesEncrypt().encryptInList(model.getList(),new String[]{"id"}); //id加密
		return model;
	}
	@Override
	public List<BorrowTransfer> getBorrowTransferListOnIndex() {
		// 
		return  transferDao.select("getBorrowTransferListOnIndex", null);
	}
	
	@Override
	public BorrowTransfer showBorrowTransferById(Integer transferId) {
		// TODO Auto-generated method stub
		return transferDao.selectByPrimaryKey(transferId);
	}
	@Override
	public PageModel selectBorrowRepossessedByTransferId(
			Map<String, String> param) {
		// TODO Auto-generated method stub
		
		
		return transferDao.getPage("selectBorrowRepossessedByTransferId", "selectBorrowRepossessedCountByTransferId", param);
	}
	@Override
	public PageModel selectBorrowTransferAuction(Map<String, String> param) {
		// TODO Auto-generated method stub
		
		
		return transferDao.getPage("selectBorrowTransferAuction", "selectBorrowTransferAuctionCount", param);
	}
	
	/**
	 * 竞拍
	 */
	@Override
	public boolean saveTransfer(Map<String, Object> param) {
		User user=(User) param.get("user");
		boolean bool=false;
		try {
			if(StringUtils.isEmpty(user.getUserPaypassword())){
					throw new RuntimeException("请先设置支付密码！！！");
			}
			//
			
			DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
			DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
			
			String payPassword = desEncrpt.decrypt(param.get("payPassword").toString());
			
			if(!user.getUserPaypassword().equals(MD5Utils.stringToMD5(aesEncrypt.encrypt(payPassword)))){
					throw new RuntimeException("支付密码不正确！！！");
			}
			
			Float auctionMoney=Float.valueOf(param.get("auctionMoney").toString());
			if(auctionMoney%10!=0){
				throw new RuntimeException("竞标金额必须为10的整数倍！！！");
			}
			
			BorrowTransfer borrowTransfer=transferDao.selectByPrimaryKey(Integer.parseInt(param.get("transferId").toString()));
			if(null==borrowTransfer){
					throw new RuntimeException("尚无该标转让信息！！！");
			}else{
				if(borrowTransfer.getTransferStatus()!=BorrowTransfer.STATUS_AUCTIONING){
					throw new RuntimeException("该标已转让完成！！！");
				}
			}
			
			BorrowTender borrowTender=borrowTenderService.selectBorrowTenderByBorrowTenderId(borrowTransfer.getTenderId());
			
			if(null==borrowTender){
				throw new RuntimeException("尚无该标投标详细记录！！！");
			}else{
				if(borrowTender.getUserId()==user.getId()||borrowTender.getUserId().equals(user.getId())){
					throw new RuntimeException("不能竞拍您转让的标！！！");	
				}
			}
			
			if(!CompareUtils.greaterEquals(borrowTransfer.getEndTime(), new Date())){
				throw new RuntimeException("该标竞拍时间已过！！！");
			}
						
			if(param.containsKey("auctionMoney")){
				if(borrowTransfer.getLastAuctionMoney()!=null){
					if(!CompareUtils.greaterEquals(new BigDecimal(param.get("auctionMoney").toString()),borrowTransfer.getLastAuctionMoney().add(new BigDecimal(10)))){
						throw new RuntimeException("竞拍金额必须大于或等于￥"+borrowTransfer.getLastAuctionMoney().add(new BigDecimal(10)));
					}
				}else{
					if(!CompareUtils.greaterEquals(new BigDecimal(param.get("auctionMoney").toString()),borrowTransfer.getTransferMoney().add(new BigDecimal(10)))){
						throw new RuntimeException("竞拍金额必须大于或等于￥"+borrowTransfer.getTransferMoney().add(new BigDecimal(10)));
					}
				}
			}else{
				throw new RuntimeException("请输入竞拍金额！！！");
			}
			Borrow borrow = borrowDao.selectByPrimaryKey(borrowTender.getBorrowId());
			borrowTransfer.setLastAuctionMoney(new BigDecimal(param.get("auctionMoney").toString()));
			
		
			UserAccount userAccount=userAccountService.getByUserId(user.getId());
			if(!CompareUtils.greaterEquals(userAccount.getAvailableMoney(),borrowTransfer.getLastAuctionMoney())){
				throw new RuntimeException("您可用余额不足，请您先充值！！！");
			}
			
			List<BorrowRepossessed> waitRepoList = borrowRepossessedService.selectWaitRepossessByTender(borrowTransfer.getTenderId());
			BigDecimal[] money = getRepoCapitalAndInterest(waitRepoList);
			BorrowRepossessed repo = waitRepoList.get(0);
			BigDecimal yearRate = new BigDecimal(0);
			
			if(repo.getTotalPeriod().intValue() == 1){ //一次性还款
				int dayPeriod = 0;
				if(borrow.getIsDay().equals(Borrow.IS_DAY_Y)){ //天标
					dayPeriod = DateUtils.daysBetween(repo.getRepossessedAddtime(), repo.getPrepareDatetime());
				}else{
					dayPeriod = DateUtils.getMonthSpace(repo.getRepossessedAddtime(), repo.getPrepareDatetime());
					dayPeriod = dayPeriod*30;
				}
				yearRate = CalculateProcess.getYearRateOfOneTimeRepay(money[1], borrowTransfer.getLastAuctionMoney(), dayPeriod);
			}else{ //分期
				yearRate = CalculateProcess.getYearRate(money[1], borrowTransfer.getLastAuctionMoney(), waitRepoList.size());
			}
			
			borrowTransfer.setTransferInterestRate(yearRate);
			bool=transferDao.updateByPrimaryKeySelective(borrowTransfer);
			if(!bool){
				throw new RuntimeException("修改竞拍失败，请稍后再试！！！");
			}
			BorrowTransferAuction borrowTransferAuction=new BorrowTransferAuction();
			borrowTransferAuction.setCreateTime(new Date());
			borrowTransferAuction.setAuctionMoney(new BigDecimal(param.get("auctionMoney").toString()));
			borrowTransferAuction.setCurInterestRate(yearRate);
			borrowTransferAuction.setStatus(BorrowTransferAuction.STATUS_VALID);
			borrowTransferAuction.setTransferId(borrowTransfer.getId());
			borrowTransferAuction.setUserId(user.getId());
			bool=borrowTransferAuctionDao.insertSelective(borrowTransferAuction);
			if(bool){
				
				//判断是否重复投标
				List<BorrowTransferAuction> BorrowTransferAuctionList=borrowTransferAuctionDao.select("selectBorrowTransferAuctionListByTransferIds",borrowTransfer.getId());
				if(BorrowTransferAuctionList.size()>1){
					BorrowTransferAuction transferAuction=BorrowTransferAuctionList.get(1);
					if(transferAuction.getUserId().equals(user.getId())){
						throw new RuntimeException("不能重复竞拍！！！");
					}
				}
				
				
				
				//冻结用户竞拍金额
				userAccount.setAvailableMoney(userAccount.getAvailableMoney().subtract(borrowTransferAuction.getAuctionMoney()));
				userAccount.setUnavailableMoney(userAccount.getUnavailableMoney().add(borrowTransferAuction.getAuctionMoney()));
				userAccountService.updateByPrimaryKeySelective(userAccount);
				
				accountLogService.add(userAccount, AccountLog.TRADE_CODE_MONEY_UNAVAILABLE, borrowTransferAuction.getAuctionMoney(), new BigDecimal(0), Constants.ADMIN_USER_ID
									, "冻结竞拍人竞拍金额:"+borrowTransferAuction.getAuctionMoney()+"元."
									, null);
				
				//解冻上一位竞拍人
				cancelLastAuction(borrowTransfer.getId());
			}else{
				throw new RuntimeException("竞拍失败，请稍后再试！！！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return bool;
	}
	@Override
	public PageModel selectBorrowTransferByUserId(Map<String, String> param) {
		// TODO Auto-generated method stub
		return transferDao.getPage("selectBorrowTransferByUserId", "selectBorrowTransferCountByUserId", param);
	}
	
	
	
	/**
	 * 定时器债券转让处理
	 */
	@Override
	public void borrowTransferOverdueDispose(BorrowTransfer borrowTransfer) {
		try {
				Map<String, Object> param=new HashMap<String, Object>();
				List<BorrowTransferAuction> BorrowTransferAuctionList=borrowTransferAuctionDao.select("selectBorrowTransferAuctionListByTransferIds",borrowTransfer.getId());
				BorrowTender borrowTender=borrowTenderService.selectBorrowTenderByBorrowTenderId(borrowTransfer.getTenderId());
				User transferUser = this.userService.getById(borrowTender.getUserId());
				if(BorrowTransferAuctionList.size()>0){
					if(null!=borrowTender){
						List borrowTransferRepossessedIds=transferDao.selects("selectBorrowTransferRepossessedIds", borrowTransfer.getId());
							if(borrowTransferRepossessedIds.size()>0){
								param.put("list", borrowTransferRepossessedIds);
								List<BorrowRepossessed> borrowRepossesseds=borrowRepossessedService.getBorrowRepossessedListByRepossessedPkList(param);
								if(borrowRepossesseds.size()>0){
									BigDecimal tenderAmount=BorrowTransferAuctionList.get(0).getAuctionMoney();
									BigDecimal trasferStayingInterest=new BigDecimal(0); // 转让人的待收利息
									BigDecimal stayingCapital=new BigDecimal(0);
									BigDecimal auctionStayingInterest=new BigDecimal(0);
									BigDecimal transferFee=borrowTransfer.getTransferFee(); //总支出手续费
									BigDecimal plantFee=transferFee.divide(new BigDecimal(2),2,BigDecimal.ROUND_UP);//平台得的费
									BigDecimal auctionFee=transferFee.subtract(plantFee);//竞拍人的的费
									BigDecimal FeeOfDiff = new BigDecimal(0); //承接人应扣除的费用(当前时间-转让时间这期间的利息)
									int effectDateDiff = 0;
									Integer lastDay=null;
									for (int i=0;i<borrowRepossesseds.size();i++) {
										BorrowRepossessed borrowRepossessed = borrowRepossesseds.get(i);
										Integer repossessedStatus=borrowRepossessed.getRepossessedStatus();
										trasferStayingInterest=trasferStayingInterest.add(borrowRepossessed.getRepossessedInterest());
										stayingCapital=stayingCapital.add(borrowRepossessed.getRepossessedCapital());//
										borrowRepossessed.setRepossessedStatus(BorrowRepossessed.STATUS_TRANSFER);
										borrowRepossessedService.updateBorrowRepossessed(borrowRepossessed);
										borrowRepossessed.setRepossessedStatus(repossessedStatus);
										if(i == 0){
											
											BigDecimal dateInterest=new BigDecimal(0);
											if(borrowRepossessed.getTotalPeriod().intValue()==1){
												//一次性还款
												int dayBetween=DateUtils.daysBetween(borrowRepossessed.getRepossessedAddtime(),borrowRepossessed.getPrepareDatetime());
												dateInterest=borrowRepossessed.getRepossessedInterest().divide(new BigDecimal(dayBetween),4, BigDecimal.ROUND_UP);//一天的利息
											}else{
												dateInterest=borrowRepossessed.getRepossessedInterest().divide(new BigDecimal(30),4, BigDecimal.ROUND_UP);//一天的利息
											}
											effectDateDiff=DateUtils.daysBetween(borrowTransfer.getCreateTime(),new Date());
											if(effectDateDiff<0){
												effectDateDiff=0;		
											}
											FeeOfDiff=dateInterest.multiply(new BigDecimal(effectDateDiff)).setScale(2,BigDecimal.ROUND_DOWN);
										}
										auctionStayingInterest=auctionStayingInterest.add(borrowRepossessed.getRepossessedInterest());
										
										if(borrowRepossessed.getCurrentPeriod().equals(borrowRepossessed.getTotalPeriod())){ 
											lastDay=DateUtils.daysBetween(new Date(),borrowRepossessed.getPrepareDatetime());
										}
									}
									BorrowTender borrowTenderTransfer = new BorrowTender();
									BeanUtils.copyProperties(borrowTender,borrowTenderTransfer);
									borrowTenderTransfer.setId(null);
									borrowTenderTransfer.setTenderAddip(null);
									borrowTenderTransfer.setTenderType(BorrowTender.TENDER_TYPE_TRANSFER);
									borrowTenderTransfer.setTransferCount(null);
									borrowTenderTransfer.setTenderAddtime(new Date());
									borrowTenderTransfer.setUserId(BorrowTransferAuctionList.get(0).getUserId());
									borrowTenderTransfer.setTenderAmount(tenderAmount);
									borrowTenderTransfer.setEffectiveAmount(tenderAmount);
									borrowTenderTransfer.setStayingInterest(auctionStayingInterest); //auctionStayingInterest
									borrowTenderTransfer.setRepaymentAmount(stayingCapital.add(auctionStayingInterest)); //auctionStayingInterest
									borrowTender.setTenderStatus(BorrowTender.STATUS_TRANSFER);
									borrowTenderService.updateByBorrowTender(borrowTender);
									borrowTenderService.saveBorrowTender(borrowTenderTransfer);
									
									for (BorrowRepossessed borrowRepossessed : borrowRepossesseds) {
										borrowRepossessed.setId(null);
										borrowRepossessed.setUserId(BorrowTransferAuctionList.get(0).getUserId());
										borrowRepossessed.setTenderId(borrowTenderTransfer.getId());
										borrowRepossessed.setRepossessedAddip(null);
										borrowRepossessed.setRepossessedAddtime(new Date());
										borrowRepossessed.setPrepareAmount(borrowRepossessed.getRepossessedCapital()
																			.add(borrowRepossessed.getRepossessedInterest()
																			.add(borrowRepossessed.getLateInterest())));
										borrowRepossessedService.saveBorrowRepossessed(borrowRepossessed);
									}
									borrowTransfer.setAcceptUserId(borrowTenderTransfer.getUserId());
									borrowTransfer.setSuccTime(new Date());
									borrowTransfer.setTransferStatus(BorrowTransfer.STATUS_SUCCESS);
									transferDao.updateByPrimaryKeySelective(borrowTransfer);
									
									
									//竞拍价转给申请转让的人
									UserAccount account=userAccountService.getByUserId(borrowTender.getUserId());
									account.setAllMoney(account.getAllMoney().add(BorrowTransferAuctionList.get(0).getAuctionMoney().subtract(trasferStayingInterest)).subtract(stayingCapital));
									account.setAvailableMoney(account.getAvailableMoney().add(BorrowTransferAuctionList.get(0).getAuctionMoney()));
									account.setWaitRepossessedCapital(account.getWaitRepossessedCapital().subtract(stayingCapital)); 
									account.setWaitRepossessedInterest(account.getWaitRepossessedInterest().subtract(trasferStayingInterest));
									accountLogService.add(account, AccountLog.TRADE_CODE_TRANSFER_RETURN_MONEY, BorrowTransferAuctionList.get(0).getAuctionMoney(), new BigDecimal(0), BorrowTransferAuctionList.get(0).getUserId()
														, "债券转让成功,向用户增加竞拍价:"+BorrowTransferAuctionList.get(0).getAuctionMoney()+"元。总金额扣除待收利息: "+trasferStayingInterest+"元,总金额扣除待收本金"+stayingCapital+"元"
														, "127.0.0.1");
									
									//扣除转让手续费
									account.setAllMoney(account.getAllMoney().subtract(transferFee));
									account.setAvailableMoney(account.getAvailableMoney().subtract(transferFee));
									userAccountService.updateByPrimaryKeySelective(account);
									
									accountLogService.add(account, AccountLog.TRADE_CODE_TRANSFER_FEE, transferFee, new BigDecimal(0), Constants.ADMIN_USER_ID
														, "扣除债权转让手续费"+transferFee+"元."
														, "127.0.0.1");
									
									//扣除竞拍人竞拍金额
									UserAccount userAccount=userAccountService.getByUserId(BorrowTransferAuctionList.get(0).getUserId());
									userAccount.setAllMoney(userAccount.getAllMoney().add(auctionStayingInterest).add(stayingCapital).subtract(BorrowTransferAuctionList.get(0).getAuctionMoney()));
									userAccount.setUnavailableMoney(userAccount.getUnavailableMoney().subtract(BorrowTransferAuctionList.get(0).getAuctionMoney()));
									userAccount.setWaitRepossessedCapital(userAccount.getWaitRepossessedCapital().add(stayingCapital)); 
									userAccount.setWaitRepossessedInterest(userAccount.getWaitRepossessedInterest().add(auctionStayingInterest));
									userAccountService.updateByPrimaryKeySelective(userAccount);
									accountLogService.add(userAccount, AccountLog.TRADE_CODE_TRANSFER_SUBMONEY, BorrowTransferAuctionList.get(0).getAuctionMoney(), new BigDecimal(0),borrowTender.getUserId()
														, "债券转让竞拍成功,扣除债权转让竞拍金额"+BorrowTransferAuctionList.get(0).getAuctionMoney()+"元.总金额加上待收利息:"+auctionStayingInterest+"元,总金额加上待收本金:"+stayingCapital+"元"
														, "127.0.0.1");
									
									//转让人支付的费用，一半给竞拍人
									userAccount.setAllMoney(userAccount.getAllMoney().add(auctionFee));
									userAccount.setAvailableMoney(userAccount.getAvailableMoney().add(auctionFee));
									userAccountService.updateByPrimaryKeySelective(userAccount);
									accountLogService.add(userAccount, AccountLog.TRADE_CODE_TRANSFER_FEE,  auctionFee, new BigDecimal(0),borrowTender.getUserId()
														, "向用户["+transferUser.getUserAccount()+"]收取债权转让所得款"+auctionFee+"元。"
														, "127.0.0.1");
									//转让人支付的费用，一半给平台
									UserAccount adminAccount=userAccountService.getByUserId(Constants.ADMIN_USER_ID);
									adminAccount.setAllMoney(adminAccount.getAllMoney().add(plantFee));
									adminAccount.setAvailableMoney(adminAccount.getAvailableMoney().add(plantFee));
									userAccountService.updateByPrimaryKeySelective(adminAccount);
									accountLogService.add(adminAccount, AccountLog.TRADE_CODE_TRANSFER_FEE,  plantFee, new BigDecimal(0),borrowTender.getUserId()
											, "向用户["+transferUser.getUserAccount()+"]收取债权转让手续费"+plantFee+"元。"
											, "127.0.0.1");
									
									if(CompareUtils.greaterThanZero(FeeOfDiff)){
										this.userAccountService.payToPlat(BorrowTransferAuctionList.get(0).getUserId(), FeeOfDiff
													, AccountLog.TRADE_CODE_TRANSFER_FEE, "扣除"+effectDateDiff+"天的利息");
									}
																		
									//推送消息
									MessageCenter center = new MessageCenter();
									center.setMessageContent("标【"+borrowTransfer.getBorrowTitle()+"】转让成功，转让金额为【"+BorrowTransferAuctionList.get(0).getAuctionMoney()+"】元，扣除债权转让手续费"+borrowTransfer.getTransferFee()+"元.");
									center.setMessageSendIp("127.0.0.1");
									center.setReceiveUserId(account.getUserId());
									center.setMessageTitle("债权转让成功");
									center.setSendUserId(Constants.ADMIN_USER_ID);
									messageCenterService.send(center, Notice.BORROW_TRANSFER_SUCCESS);
									
									
									MessageCenter userCenter = new MessageCenter();
									userCenter.setMessageContent("标【"+borrowTransfer.getBorrowTitle()+"】竞拍成功，扣除竞拍金额为【"+BorrowTransferAuctionList.get(0).getAuctionMoney()+"】元。");
									userCenter.setMessageSendIp("127.0.0.1");
									userCenter.setReceiveUserId(account.getUserId());
									userCenter.setMessageTitle("债权转让竞拍成功");
									userCenter.setSendUserId(Constants.ADMIN_USER_ID);
									messageCenterService.send(userCenter, Notice.BORROW_TRANSFER_SUCCESS);
									
									param.clear();
									param.put("stayingCapital", stayingCapital);
									param.put("lastDay",lastDay);
									
									//添加竞拍人积分
									userCreditService.addUserCredit(CreditType.BORROW_TRANSFER_SUCCESS_AUCTION,userAccount.getUserId(), Constants.ADMIN_USER_ID,param);
									creditLogService.addCreditLog(CreditType.BORROW_TRANSFER_SUCCESS_AUCTION,userAccount.getUserId(), Constants.ADMIN_USER_ID,param);
									
									//减去转让人积分
									userCreditService.addUserCredit(CreditType.BORROW_TRANSFER_SUCCESS_TRANSFER,account.getUserId(), Constants.ADMIN_USER_ID,param);
									creditLogService.addCreditLog(CreditType.BORROW_TRANSFER_SUCCESS_TRANSFER,account.getUserId(), Constants.ADMIN_USER_ID,param);
									
									User acceptUser = this.userService.getById(borrowTenderTransfer.getUserId());
									Borrow borrow=borrowDao.selectByPrimaryKey(borrowTenderTransfer.getBorrowId());
									
									//生成债权转让协议 
									Map<String, String> cfg = SysCacheUtils.getConfigParams();
									Map<String, Object> replaceMap = buildReplaceAgreementMap(transferUser,acceptUser,stayingCapital,auctionStayingInterest,borrow);
									String pdfContent = StringUtil.replaceString(cfg.get("tsf_agreement_content"),replaceMap);
									String mailContent = StringUtil.replaceString(cfg.get("tsf_agreement_mailContent"),replaceMap);
									String mailTitle = StringUtil.replaceString(cfg.get("tsf_agreement_mailTitle"),replaceMap);
									Map m = new HashMap();//扩展域
									String pdfPath = AgreementUtil.createAgreementThenMailToTransfer(pdfContent
											,null,acceptUser.getId(),mailContent,mailTitle, m);
									//协议更新到数据库
									if(org.apache.commons.lang3.StringUtils.isNotEmpty(pdfPath)){
										BorrowTender tmpTender = new BorrowTender();
										tmpTender.setId(borrowTenderTransfer.getId());
										tmpTender.setAgreementPath(pdfPath);
										tmpTender.setAgreementTime(new Date());
										this.tenderDao.updateByPrimaryKeySelective(tmpTender);
									}
									
								}
							}
					}
				}else{
					borrowTransfer.setTransferStatus(BorrowTransfer.STATUS_FAILD);
					transferDao.updateByPrimaryKeySelective(borrowTransfer);
					MessageCenter center = new MessageCenter();
					center.setMessageContent("标【"+borrowTransfer.getBorrowTitle()+"】转让失败。");
					center.setMessageSendIp("127.0.0.1");
					center.setReceiveUserId(borrowTender.getUserId());
					center.setMessageTitle("债权转让失败，原因：无人承接");
					center.setSendUserId(Constants.ADMIN_USER_ID);
					messageCenterService.send(center, Notice.BORROW_TRANSFER_FAILD);
				}	
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	public void cancelTransfer(BorrowTransfer transfer){
		BorrowTender borrowTender=borrowTenderService.selectBorrowTenderByBorrowTenderId(transfer.getTenderId());
		transfer.setTransferStatus(BorrowTransfer.STATUS_FAILD);
		transferDao.updateByPrimaryKeySelective(transfer);
		MessageCenter center = new MessageCenter();
		center.setMessageContent("标【"+transfer.getBorrowTitle()+"】转让失败,原因：回款");
		center.setMessageSendIp("127.0.0.1");
		center.setReceiveUserId(borrowTender.getUserId());
		center.setMessageTitle("债权转让失败");
		center.setSendUserId(Constants.ADMIN_USER_ID);
		messageCenterService.send(center, Notice.BORROW_TRANSFER_FAILD);
		
		cancelLastAuctionMoney(transfer.getId());
	}
	public void cancelLastAuctionMoney(Integer transferId){
		//还款解冻最后一位资金记录
		List<BorrowTransferAuction> BorrowTransferAuctionList=borrowTransferAuctionDao.select("selectBorrowTransferAuctionListByTransferIds",transferId);
		if(BorrowTransferAuctionList.size() >= 1){
			BorrowTransferAuction transferAuction=BorrowTransferAuctionList.get(0);
					
			transferAuction.setStatus(BorrowTransferAuction.STATUS_NULLITY);
			borrowTransferAuctionDao.updateByPrimaryKeySelective(transferAuction);
			
			UserAccount oldAuction=userAccountService.getByUserId(transferAuction.getUserId());
			oldAuction.setAvailableMoney(oldAuction.getAvailableMoney().add(transferAuction.getAuctionMoney()));
			oldAuction.setUnavailableMoney(oldAuction.getUnavailableMoney().subtract(transferAuction.getAuctionMoney()));
			userAccountService.updateByPrimaryKeySelective(oldAuction);
					
			accountLogService.add(oldAuction, AccountLog.TRADE_CODE_MONEY_RETURN, transferAuction.getAuctionMoney(), new BigDecimal(0), Constants.ADMIN_USER_ID
								, "竞拍无效,用户回款,解冻竞拍人竞拍金额:"+transferAuction.getAuctionMoney()+"元."
								, null);
		}
	}
	
	public void cancelLastAuction(Integer transferId){
		//解冻上一位竞拍人
		List<BorrowTransferAuction> BorrowTransferAuctionList=borrowTransferAuctionDao.select("selectBorrowTransferAuctionListByTransferIds",transferId);
		if(BorrowTransferAuctionList.size()>1){
			BorrowTransferAuction transferAuction=BorrowTransferAuctionList.get(1);
					
			transferAuction.setStatus(BorrowTransferAuction.STATUS_NULLITY);
			borrowTransferAuctionDao.updateByPrimaryKeySelective(transferAuction);
			
			UserAccount oldAuction=userAccountService.getByUserId(transferAuction.getUserId());
			oldAuction.setAvailableMoney(oldAuction.getAvailableMoney().add(transferAuction.getAuctionMoney()));
			oldAuction.setUnavailableMoney(oldAuction.getUnavailableMoney().subtract(transferAuction.getAuctionMoney()));
			userAccountService.updateByPrimaryKeySelective(oldAuction);
					
			accountLogService.add(oldAuction, AccountLog.TRADE_CODE_MONEY_RETURN, transferAuction.getAuctionMoney(), new BigDecimal(0), Constants.ADMIN_USER_ID
								, "竞拍无效,解冻竞拍人竞拍金额:"+transferAuction.getAuctionMoney()+"元."
								, null);
		}
	}
	
	private Map<String,Object> buildReplaceAgreementMap(User tsfUser,User acceptUser,BigDecimal capital,BigDecimal interest,Borrow borrow){
		User borUsr = this.userService.getById(borrow.getUserId());
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("transfer_user", tsfUser.getUserAccount());//转让人
		map.put("accept_user", acceptUser.getUserAccount());//受让人
		map.put("transfer_capital", DecimalUtils.getTwoDecimal(capital));//转让本金
		map.put("dueIn_interest", DecimalUtils.getTwoDecimal(interest));//转让利息
		map.put("totalMoney", DecimalUtils.getTwoDecimal(capital.add(interest)));//转让利息
		map.put("transfer_date", DateUtils.formatDate(new Date()));
		map.put("borrow_name", borrow.getBorrowTitle());
		map.put("borrow_user", borUsr.getUserAccount());
		return map;
	}
	
	@Override
	public PageModel findBorrowTransferDeal(Map<String, String> param) {
		return transferDao.getPage("findBorrowTransferDeal", param);
	}
	
	/**
	 * 债权转让统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public PageModel transferSummary(Map<String, String> map) {
		return transferDao.transferSummary(map);
	}
	
	/**
	 * 债权转让统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public List<Map<String, Object>> transferAllSummary(Map<String, String> map) {
		return transferDao.transferAllSummary(map);
	}
	
	@Override
	public PageModel selectBorrowTransListData(Map<String, Object> param) {
		PageModel pageModel=new  PageModel(Integer.parseInt(param.get(Constants.PAGED_CURPAGE).toString()));  //设置当前页
		if(param.containsKey(Constants.PAGED_NUM_PERPAGE)){
			pageModel.setPageSize(Integer.parseInt(param.get(Constants.PAGED_NUM_PERPAGE).toString()));
		}
		PageModel model=transferDao.getPage("selectBorrowTransListData", "selectBorrowCountTransListData", param,pageModel);
		new DesEncrypt().encryptInList(model.getList(),new String[]{"id"}); //id加密
		return model;
	}
	@Override
	public List<BorrowTransfer> getByTransferUserid(Map<String, Object> param) {
		return this.transferDao.select("getByTransferUserid", param);
	}
	
}

