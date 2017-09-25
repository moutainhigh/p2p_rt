package com.rbao.east.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baofoo.sdk.api.BaoFooApi;
import com.baofoo.sdk.demo.base.TransContent;
import com.baofoo.sdk.demo.base.response.TransRespBF0040002;
import com.rbao.east.dao.AccountCashDao;
import com.rbao.east.dao.BorrowDao;
import com.rbao.east.dao.BorrowRepaymentDao;
import com.rbao.east.dao.BorrowTransferDao;
import com.rbao.east.dao.RedenvelopesRecordDao;
import com.rbao.east.entity.AccountCash;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowTransfer;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.ExperienceGold;
import com.rbao.east.entity.RedenvelopesRecord;
import com.rbao.east.entity.VipUser;
import com.rbao.east.service.AccountCashService;
import com.rbao.east.service.AccountStatisticsService;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.ExperienceGoldService;
import com.rbao.east.service.OperatorLogService;
import com.rbao.east.service.RedenvelopesService;
import com.rbao.east.service.ReportService;
import com.rbao.east.service.VipUserService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;

@Component("taskJobImpl")
public class TaskJobImpl implements TaskJob {
	private static Logger logger = LoggerFactory.getLogger(TaskJobImpl.class);

	@Autowired
	private BorrowRepaymentService borrowRepaymentService;
	@Autowired
	private BorrowService borrowQueryService;

	@Autowired
	private BorrowTransferService borrowTransferService;

	@Autowired
	private AccountStatisticsService accountStatisticsService;
	@Autowired
	BorrowTenderService tenderService;
	@Autowired
	OperatorLogService operLogService;
	@Autowired
	private BorrowTypeService borrowTypeService;
	@Autowired
	private ReportService reportService;
	@Autowired
	RedenvelopesRecordDao redRecdDao ;
	@Autowired
	RedenvelopesService redRecdService;
	@Autowired
	private BorrowRepaymentDao borrowRepaymentDao;
	@Autowired
	private BorrowDao borrowDao;
	@Autowired
	private BorrowTransferDao transferDao;
	@Autowired
	private VipUserService vipService;
	
	@Autowired
	private ExperienceGoldService experienceGoldService;
	@Autowired
	private AccountCashDao  accountCashDao;
	@Autowired
	private  AccountCashService accountCashService;

	/**
	 * 标逾期处理
	 */
	@Override
	public void borrowOverdueDispose() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("repaymentStatus", BorrowRepayment.REPAYMENT_STATUS_SUCCESS);
		param.put("repaymentStatusForOverdue",
				BorrowRepayment.REPAYMENT_STATUS_OVERDUE);
		List<BorrowRepayment> borrowRepayments = borrowRepaymentDao.select(
				"selectAll", param);
		if (borrowRepayments.size() > 0) {
			for (BorrowRepayment borrowRepayment : borrowRepayments) {
				borrowRepaymentService.borrowOverdueDispose(borrowRepayment);
			}
		}
	}

	/**
	 * 流标解冻
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void returnedMoneyForFlowStandardByBorrowTenderList() {
		Map m = new HashMap();
		m.put("status", Borrow.STATUS_FIRSTAUDIT_YES);
		m.put("notInType",
				borrowTypeService.getByTypeCodes(new String[] {
						Borrow.BORROW_TYPE_ZHUAN, Borrow.BORROW_TYPE_DING
						,Borrow.BORROW_TYPE_ZHUAN_DF }));
		List<Borrow> borrowList = borrowDao.select("flowStandardList", m);
		if (borrowList.size() > 0) {
			for (Borrow borrow : borrowList) {
				borrowQueryService
						.returnedMoneyForFlowStandardByBorrowTenderList(borrow);
			}
		}
	}

	/**
	 * 自动投标
	 */
	@Override
	public void autoTenderRules() {
		borrowQueryService.autoTenderRules();
	}

	/**
	 * 债券转让处理
	 */
	@Override
	public void borrowTransferOverdueDispose() {
		List<BorrowTransfer> borrowTransfers = transferDao.select(
				"selectBorrowTransferPastDues", null);
		if (borrowTransfers.size() > 0) {
			for (BorrowTransfer borrowTransfer : borrowTransfers) {
				borrowTransferService
						.borrowTransferOverdueDispose(borrowTransfer);
			}
		}
	}

	/**
	 * 秒标自动审核
	 */
	@Override
	public void autoBorrowAuditForImmediateRepay() {
		Map<String, Object> param = new HashMap<String, Object>();
		BorrowType borrowType = borrowTypeService
				.getBorrowTypeByCode(Borrow.BORROW_TYPE_MIAO);
		param.put("borrowStatus", Borrow.STATUS_FULL);
		param.put("bidKind", borrowType.getId());
		List<Borrow> borrows = borrowQueryService
				.findAutoBorrowAuditForImmediate(param);
		for (Borrow borrow : borrows) {
			borrowQueryService.AutoBorrowAuditForImmediate(borrow, borrowType);
		}
	}

	/**
	 * 自动还款
	 */
	@Override
	public void autoBorrowRepaymentReturnMoney() {
		List<BorrowRepayment> borrowRepayments = borrowRepaymentService
				.findAutoBorrowRepayment();
		for (BorrowRepayment borrowRepayment : borrowRepayments) {
			borrowRepaymentService
					.autoBorrowRepaymentReturnMoney(borrowRepayment);
		}
	}
	
	
	

	@Override
	public void autoPayExperienceGold() {
		
		List<ExperienceGold> ExperienceGoldList =experienceGoldService.getExperienceGoldListByStatus();

		for (ExperienceGold experienceGold : ExperienceGoldList) {
			experienceGoldService.addExperienceGold(experienceGold);
		}
		
	}

	@Override
	public void autoCopyDataByDay() {
		try {
			accountStatisticsService.autoCopyDataByDay();
		} catch (Exception e) {
			logger.error("拷贝数据异常!", e);
		}
	}

	/**
	 * 每日统计
	 */
	@Override
	public void saveReport() {
		reportService.saveReport();
	}

	@Override
	public void vipNotify() {
		/**
		 * 将到期VIP用户状态改为停卡状态
		 */
		try {
			vipService.updateVipStatusByEndDate(new VipUser());
		} catch (Exception e1) {
			logger.error("更改用户VIP状态异常", e1);
		}

		/**
		 * 提前告知用户VIP即将到期
		 */
		try {
			vipService.vipNotify();
		} catch (Exception e2) {
			logger.error("提前告知用户VIP即将到期异常", e2);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void redEnvJob() {
		
		Map m = new HashMap();
//		m.put("userId", userId);
		m.put("inStatus", new Integer[]{RedenvelopesRecord.STATUS_CANNOT_OPEN
										,RedenvelopesRecord.STATUS_NOT_OPEN});
		List<RedenvelopesRecord> recds = this.redRecdDao.select("selectFrontByParam", m);
		
		for(RedenvelopesRecord recd : recds){			
			try {
				
				//已过期
				if(CompareUtils.greaterThan(DateUtils.preciseToDay(new Date())
						,DateUtils.preciseToDay(recd.getPeriodEndTime()))){
					recd.setStatus(RedenvelopesRecord.STATUS_OVERDUE);
					recd.setUpdateTime(new Date());
					this.redRecdDao.updateByPrimaryKeySelective(recd);
					continue;
				}
				//可打开
				if(recd.getStatus().equals(RedenvelopesRecord.STATUS_NOT_OPEN)){ 
					redRecdService.openRedenvelopesIfSettingAuto(recd);
					continue;
				}
				//未满足条件
				if(recd.getStatus().equals(RedenvelopesRecord.STATUS_CANNOT_OPEN)){
					
					Date beginTime = recd.getPeriodBeginTime();//开始计算时间
					//查询参数
					Map param = new HashMap();
					param.put("beginTimeStr", DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", beginTime));
					param.put("userId", recd.getUserId());
					
					//查询当前用户以满足的条件
					BigDecimal fillin = new BigDecimal(0);
					if(recd.getOpenLimitType().equals(RedenvelopesRecord.OPEN_LIMIT_TYPE_TENDER)){
						//投标
						fillin = this.tenderService.selectSumByMap(param);
						
					}else if(recd.getOpenLimitType().equals(RedenvelopesRecord.OPEN_LIMIT_TYPE_LOGIN_DAYS)){
						//累计登录天数
						fillin = new BigDecimal(this.operLogService.getLoginDays(param).size());
						
					}else if(recd.getOpenLimitType().equals(RedenvelopesRecord.OPEN_LIMIT_TYPE_LOGIN_CONTINUE)){					
						//连续登录天数
						fillin = new BigDecimal(DateUtils.calContinueDays(this.operLogService.getLoginDays(param)));
						
					}else if(recd.getOpenLimitType().equals(RedenvelopesRecord.OPEN_LIMIT_TYPE_CONTINUE_TENDER)){
						// 续投红包
						fillin = new BigDecimal(this.tenderService.selectCountByMap(param));
					}
					//没有变动，不做处理
					if(CompareUtils.equals(fillin, recd.getFillLimitParam())){
						continue;
					}
					//更新当前红包
					recd.setFillLimitParam(fillin);
					if(CompareUtils.greaterEquals(recd.getFillLimitParam(), recd.getOpenLimitParam())){
						recd.setStatus(RedenvelopesRecord.STATUS_NOT_OPEN);
					}
					recd.setUpdateTime(new Date());
					redRecdDao.updateByPrimaryKeySelective(recd);
					
					if(recd.getStatus().equals(RedenvelopesRecord.STATUS_NOT_OPEN)){ //可打开
						//赠送登录红包
						if(recd.getOpenLimitType().equals(RedenvelopesRecord.OPEN_LIMIT_TYPE_LOGIN_DAYS)){
							redRecdService.sendLoginRedRecord(recd.getUserId(),false);
						}
						//如果满足条件并用户开启了自动打开则打开当前红包
						redRecdService.openRedenvelopesIfSettingAuto(recd);
					}
				}
			} catch (Exception e) {
				logger.error("redEnvJob("+recd.getId()+") error:"+e.toString());
			}
		}
		
	}

	@Override
	public void autoCheckCashNoOrder() {
		List<AccountCash> list = accountCashDao.select("selectAccountCashingList", null);
		boolean flag = false;
		Map<String,Object> params =new HashMap<String,Object>();
		for(int i=0;i<list.size();i++){
//			/* 连连提现  start */
//			String result = WebClient.appadd(list.get(i).getNoOrder());
//			JSONObject json = JSONObject.fromObject(result);
//			if(json!=null&&json.containsKey("result_pay")){
//				if(json.get("result_pay").equals("SUCCESS")){
//					params.put("noOrder", list.get(i).getNoOrder());
//					params.put("cashMoney", list.get(i).getCashAccount());
//					params.put("info_order", "连连提现");
//					params.put("cashStatuss", 1);
//					params.put("ipAddress", list.get(i).getCashAddip());
//					accountCashService.updateH5AccountCashById(params);
//				}else if(json.get("result_pay").equals("FAILURE")){
//				  	 String  verifyRemark = "连连打款失败";
//					flag = accountCashService.exeCashFailure(list.get(i).getNoOrder(),"连连","",verifyRemark);
//					System.out.println(flag);
//
//				}
//			}
//			/* 连连提现  end */
			
			/* 宝付提现 start */
			try {
				TransContent<TransRespBF0040002> result = BaoFooApi.BF004002(list.get(i).getNoOrder());
				if(result !=null && "0000".equals(result.getTrans_head().getReturn_code())){
					int state = Integer.parseInt(result.getTrans_reqDatas().get(0).getState());
					if(state == 1){
						params.put("noOrder", list.get(i).getNoOrder());
						params.put("cashMoney", list.get(i).getCashAccount());
						params.put("info_order", "宝付提现");
						params.put("cashStatuss", 1);
						params.put("ipAddress", list.get(i).getCashAddip());
						accountCashService.updateH5AccountCashById(params);
					} else if (state == -1 || state == 2){
					  	 String  verifyRemark = "宝付打款失败";
						flag = accountCashService.exeCashFailure(list.get(i).getNoOrder(),"宝付","",verifyRemark);
						System.out.println(flag);
	
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			/* 宝付提现 end */
		}
		
	}


}
