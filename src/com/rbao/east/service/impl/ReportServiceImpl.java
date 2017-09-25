package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AccountCashDao;
import com.rbao.east.dao.AccountRechargeDao;
import com.rbao.east.dao.ReportDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.Report;
import com.rbao.east.service.ReportService;
import com.rbao.east.utils.DateUtils;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ReportDao reportDao;
	@Autowired
	private AccountCashDao accountCashDao;
	@Autowired
	private AccountRechargeDao accountRechargeDao;

	@Override
	public Report getReportById(Integer id) {
		return reportDao.selectEntity("getReportById", id);
	}

	@Override
	public List<Report> getAllReports() {
		return reportDao.select("getAllReports", null);
	}

	@Override
	public PageModel getPageReports(Map<String, String> params) {
		return reportDao.getPage("getPageReports", "getTotalCount", params);
	}

	@Override
	public List<Report> getAllReports(Map<String, String> params) {
		return reportDao.select("getReportsByDate", params);
	}

	@Override
	public boolean saveReport() {
		boolean bool = false;
			Map<String, Object> param = new HashMap<String, Object>();
			Report report = new Report();
			try {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -1);
				String nowDate = DateUtils.getDate(cal.getTime());
				param.put("startDate", nowDate + " 00:00:00");
				param.put("endDate", nowDate + " 23:59:59");
				// 得到当天注册数量
				Integer registerCount = reportDao.getTotalCount("getRegisterCount",
						param);
				report.setRegisterCount(registerCount);

				// 得到当天申请Vip数量
				Integer vipCount = reportDao.getTotalCount("getVipCount", param);
				report.setVipCount(vipCount);

				// 得到当天实名认证数量
				param.put("realnameCode", "realname");
				Integer attestationRealnameCount = reportDao.getTotalCount(
						"getAttestationCountByCreditCode", param);
				report.setAttestationRealnameCount(attestationRealnameCount);

				// 得到当天手机认证数量
				param.remove("realnameCode");
				param.put("phoneCode", "phone");
				Integer attestationTelCount = reportDao.getTotalCount(
						"getAttestationCountByCreditCode", param);
				report.setAttestationTelCount(attestationTelCount);
	            //得到当天的提现笔数
				param.remove("phoneCode");
				Integer dayCashCount= accountCashDao.getTotalCount("selectTimeCashCount", param);
				report.setCashCount(dayCashCount);
				//得到当天的充值笔数
				Integer dayRechargeCount = accountRechargeDao.getTotalCount("selectTimeTotalCount", param);
				report.setRechargeCount(dayRechargeCount);
				// 得到当天借款手续费
				param.put("tradeCode", AccountLog.TRADE_CODE_LOAN_FEE);
				
				Object borrowFeeObj=reportDao.getObject(
						"getAccountLogMoneyByTradeCode", param);
				if(null==borrowFeeObj){
					borrowFeeObj="0.00";
				}
				String borrowFee = borrowFeeObj.toString();
				
				report.setBorrowFee(new BigDecimal(borrowFee).divide(new BigDecimal(2),2,BigDecimal.ROUND_UP));

				// 得到当天利息管理费
				param.put("tradeCode", AccountLog.TRADE_CODE_INTEREST_FEE);
				Object interestFeeObj= reportDao.getObject(
						"getAccountLogMoneyByTradeCode", param);
				if(null==interestFeeObj){
					interestFeeObj="0.00";
				}
				String interestFee = interestFeeObj.toString();
				report.setInterestFee(new BigDecimal(interestFee).divide(new BigDecimal(2),2,BigDecimal.ROUND_UP));

				// 得到当天提现手续费
				Object cashFeeObj=reportDao.getObject("getCashFee", param);
				
				if(null==cashFeeObj){
					cashFeeObj="0.00";
				}
				
				String cashFee = cashFeeObj.toString();
				
				report.setCashFee(new BigDecimal(cashFee));

				// 得到当天vip管理费
				param.put("tradeCode", AccountLog.TRADE_CODE_VIP);
				Object vipFeeObj =reportDao.getObject(
						"getAccountLogMoneyByTradeCode", param);
				if(null==vipFeeObj){
					vipFeeObj="0.00";
				}
				String vipFee =vipFeeObj.toString();
				
				report.setVipFee(new BigDecimal(vipFee).divide(new BigDecimal(2)));

				// 得到当天充值手续费
				report.setRechargeFee(new BigDecimal(0));
				
				// 得到当天逾期已还款数量,金额
				param.put("repaymentStatus",
						BorrowRepayment.REPAYMENT_STATUS_SUCCESS);
				Map<String, Object> overdueRepayed = (Map<String, Object>) reportDao
						.getObject("getOverdueRepayedCountAndAmount", param);
				Object count=overdueRepayed.get("count");
				report.setOverdueRepayedCount(Integer.parseInt(count.toString()));
				String overdueRepayedAmount="0.00";
				if(overdueRepayed.containsKey("amount")){
					String str = (String) overdueRepayed.get("amount");
				   if(str==null){
					   str="0.00";
				   }
					overdueRepayedAmount = str.toString();
				}
				report.setOverdueRepayedAmount(new BigDecimal(overdueRepayedAmount));

				// 得到当天新增逾期数
				param.put("repaymentStatus",
						BorrowRepayment.REPAYMENT_STATUS_OVERDUE);
				Map<String, Object> newIncreased = (Map<String, Object>) reportDao
						.getObject("getNewIncreasedCountAndAmount", param);
				Object newIncreasedCountcount=newIncreased.get("count");
				report.setNewIncreasedCount(Integer.parseInt(newIncreasedCountcount.toString()));
				String newIncreasedAmount="0.00";
				if(newIncreased.containsKey("amount")){
					
				  newIncreasedAmount = newIncreased.get("amount").toString();
				}
				report.setNewIncreasedAmount(new BigDecimal(newIncreasedAmount));

				// 得到当天线上充值金额
				param.put("rechargeStatus",
						AccountRecharge.RECHAREGE_STATUS_CHECK_SUCC);
				param.put("rechargeType", AccountRecharge.RECHARGE_TYPE_ON);
				Object rechargeOnlineObj =reportDao.getObject("getRecharge", param);
				if(null==rechargeOnlineObj){
					rechargeOnlineObj="0.00";
				}
				String rechargeOnline = rechargeOnlineObj.toString();
				report.setRechargeOnline(new BigDecimal(rechargeOnline));

				// 得到当天线下充值金额
				param.put("rechargeStatus",
						AccountRecharge.RECHAREGE_STATUS_CHECK_SUCC);
				param.put("rechargeType", AccountRecharge.RECHARGE_TYPE_OFF);
				Object rechargeOfflineObj=reportDao.getObject("getRecharge", param);
				if(null==rechargeOfflineObj){
					rechargeOfflineObj="0.00";
				}
				String rechargeOffline = rechargeOfflineObj.toString();
				report.setRechargeOffline(new BigDecimal(rechargeOffline));

				// 得到当天提现金额
				Object cashObj=reportDao.getObject("getCash", param);
				if(null==cashObj){
					cashObj="0.00";
				}
				String cash = cashObj.toString();
				report.setCash(new BigDecimal(cash));

				// 得到当天充值-提现金额
				BigDecimal getIn = report.getRechargeOnline()
						.add(report.getRechargeOffline())
						.subtract(report.getCash());
				report.setGetIn(getIn);

				// 得到当天利息金额
				Object interestObj = reportDao.getObject("getInterest", param);
				if(null==interestObj){
					interestObj="0.00";
				}
				String interest = interestObj.toString();
			
				report.setInterest(new BigDecimal(interest));

				// 得到当天投标奖励金额
				param.put("tradeCode", AccountLog.TRADE_CODE_TENDER_REWARD);
				Object tenderRewardObj=reportDao.getObject("getAccountLogMoneyByTradeCode", param);
				if(null==tenderRewardObj){
					tenderRewardObj="0.00";
				}
				String tenderReward = tenderRewardObj.toString();
				
				report.setTenderReward(new BigDecimal(tenderReward).divide(new BigDecimal(2),2,BigDecimal.ROUND_UP));

				// 得到当天线下充值奖励金额

				param.put("tradeCode", AccountLog.TRADE_CODE_RECHARGE_OFF_AWARD_FEE);
				Object rechargeOfflineRewardObj=reportDao.getObject("getAccountLogMoneyByTradeCode", param);
				if(null==rechargeOfflineRewardObj){
					rechargeOfflineRewardObj="0.00";
				}
				String rechargeOfflineReward = rechargeOfflineRewardObj.toString();
				report.setRechargeOfflineReward(new BigDecimal(
						rechargeOfflineReward).divide(new BigDecimal(2),2,BigDecimal.ROUND_UP));

				// 得到当天续投奖励金额
				param.put("tradeCode", AccountLog.TRADE_CODE_CONTINUE_REWARD);
				Object continueRewardObj=reportDao.getObject("getAccountLogMoneyByTradeCode", param);
				if(null==continueRewardObj){
					continueRewardObj="0.00";
				}
				String continueReward =continueRewardObj.toString();
				report.setContinueReward(new BigDecimal(continueReward).divide(new BigDecimal(2),2,BigDecimal.ROUND_UP));
				// 得到当天推荐奖励金额
				param.put("tradeCode", AccountLog.TRADE_CODE_RECOMMEND_AWARD_FEE);
				
				Object recommendRewardObj=reportDao.getObject("getAccountLogMoneyByTradeCode", param);
				if(null==recommendRewardObj){
					recommendRewardObj="0.00";
				}
				String recommendReward = recommendRewardObj.toString();
				report.setRecommendReward(new BigDecimal(recommendReward).divide(new BigDecimal(2),2,BigDecimal.ROUND_UP));

				// 得到当天注册奖励金额，修改注册奖励查询问题
				param.put("tradeCode", AccountLog.TRADE_CODE_REGISTER_FEE);
				Object registerRewardObj=reportDao.getObject("getAccountLogMoneyByTradeCode", param);
				if(null==registerRewardObj){
					registerRewardObj="0.00";
				}
				String registerReward = registerRewardObj.toString();
				report.setRegisterReward(new BigDecimal(registerReward).divide(new BigDecimal(2),2,BigDecimal.ROUND_UP));

				// 得到当天发布数量,发布总额
				Map<String, Object> publish = (Map<String, Object>) reportDao
						.getObject("getPublish", param);
				Object publishCount=publish.get("count");
				report.setPublishCount(Integer.parseInt(publishCount.toString()));
				String publishAmount="0.00";
				if( publish.containsKey("amount")){
					publishAmount = publish.get("amount").toString();
				}
				report.setPublishAmount(new BigDecimal(publishAmount));

				// 得到当天还款数
				param.put("tradeCode", AccountLog.TRADE_CODE_REPAY);
				Integer repayedCount = reportDao.getTotalCount("getRepayedCount",
						param);
				report.setRepayedCount(repayedCount);

				// 得到当天还款总额
				param.put("tradeCode", AccountLog.TRADE_CODE_REPAY);
				Object repayedAmountObj=reportDao.getObject("getAccountLogMoneyByTradeCode", param);
						if(null==repayedAmountObj){
							repayedAmountObj="0.00";
						}
				String repayedAmount = repayedAmountObj.toString();
				report.setRepayedAmount(new BigDecimal(repayedAmount));

				// 得到当天投标数,投标总额

				Map<String, Object> tender = (Map<String, Object>) reportDao
						.getObject("getTender", param);
				Object tenderCount=tender.get("count");
				report.setTenderCount(Integer.parseInt(tenderCount.toString()));
				String tenderAmount="0.00";
				if(tender.containsKey("amount")){
					tenderAmount=tender.get("amount").toString();
				}
				report.setTenderAmount(new BigDecimal(tenderAmount));

				// 得到当天有效投标数
				report.setEffeTenderCount(Integer.parseInt(tenderCount.toString()));

				// 得到当天有效投标总额
				String effeTenderAmount = tenderAmount;
				if (effeTenderAmount == null || effeTenderAmount == "") {
					effeTenderAmount = "0.00";
				}
				report.setEffeTenderAmount(new BigDecimal(effeTenderAmount));

				report.setReportDate(cal.getTime());
				bool=reportDao.insertSelective(report);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		
		return bool;
	}

	@Override
	public String getSumByUserId(Map<String, Object> param) {

		Object sum=reportDao.getObject("getSumByUserId", param);
		if(null==sum){
			return "0.00";
		}else{
			return sum.toString();
		}
	}

	@Override
	public String getRepossessedSumByUserId(Map<String, Object> param) {
		Object sum=reportDao.getObject("getRepossessedSumByUserId", param);
		if(null==sum){
			return "0.00";
		}else{
			return sum.toString();
		}
	}

	@Override
	public Map getWealth() {
		return (Map) reportDao.getObject("selectWealth", null);
	}

	@Override
	public PageModel getReportByMonth(Map<String, String> paramsMap) {
		return reportDao.getPage("countByMonth", "countMonth", paramsMap);
	}

	@Override
	public BigDecimal selectFullMoney(Map<String, String> params) {
		return (BigDecimal) reportDao.selects("selectFullMoney", params).get(0);
	}
	
	/**
	 * 统计用户总充值
	 * @return BigDecimal
	 */
	@Override
	public BigDecimal summaryRecharge() {
		return reportDao.summaryRecharge();
	}
	
	/**
	 * 统计用户总提现
	 * @return BigDecimal
	 */
	@Override
	public BigDecimal summaryCash() {
		return reportDao.summaryCash();
	}
	
	/**
	 * 平台总收益
	 * @return BigDecimal
	 */
	public BigDecimal summaryPlatformEarnings() {
		return reportDao.summaryPlatformEarnings();
	}
	
	/**
	 * 用户总收益
	 * @return BigDecimal
	 */
	public BigDecimal summaryUserEarnings() {
		return reportDao.summaryUserEarnings();
	}
	
	/**
	 * 平台资金统计
	 * @date
	 * 		年份
	 * @return
	 */
	public List<Map<String, Object>> platformSummary(String date) {
		return reportDao.platformSummary(date);
	}
	
	/**
	 * 平台资金统计柱状图
	 * @param date
	 * 			年份
	 * @return
	 */
	public Map<String, Object> platformHistSummary(String date) {
		return reportDao.platformHistSummary(date);
	}
	
	/**
	 * 充值提现数据统计
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> rechargeCashSummary(String date) {
		return reportDao.rechargeCashSummary(date);
	}

}
