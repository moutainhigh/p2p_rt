package com.rbao.east.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.SysCacheUtils;

/**
 * 计算利息和本金、奖励处理
 * @author Liutq
 *
 */
public class CalculateProcess{
	private static Logger logger = LoggerFactory.getLogger(CalculateProcess.class);
	
	
	/**
	  * 取现手续费，分线上线下
	  * @param offline
	  * @param online
	  * @return
	  */
	 public static BigDecimal calcCashFee(BigDecimal offline,BigDecimal online){
		 SysFeesRate fee = SysCacheUtils.getSysFeesRate();
		 double offline_fee = Double.parseDouble(fee.getSysWithdrawalPoundage().toString()) / 1000;
		 double online_fee  = Double.parseDouble(fee.getSysWithdrawalPoundageOnline().toString()) / 1000;
		 BigDecimal feeOff = offline.multiply(new BigDecimal(offline_fee));
		 BigDecimal feeOn = online.multiply(new BigDecimal(online_fee));
		 return feeOff.add(feeOn).setScale(2,BigDecimal.ROUND_HALF_UP);
	 }
	
	/**
	 * 线下充值奖励
	 * @param rechargeMoney 充值金额
	 * @param offlineRate 线下充值奖励比例
	 * @return
	 */
	public static BigDecimal awardOfRecharge(BigDecimal rechargeMoney,BigDecimal offlineRate){
		return rechargeMoney.multiply(offlineRate.divide(new BigDecimal(1000))).setScale(2, BigDecimal.ROUND_UP);
	}
	
	 /**
	  * 按月计算利息
	  * @param totalTenderMoney  投标总金额
	  * @param apr  年利率
	  * @return
	  * @throws Exception
	  */
	 public static BigDecimal interestOfMonth(BigDecimal totalTenderMoney,BigDecimal apr,Integer loanPeriodCount){
		    try {
				Double dapr=apr.doubleValue();
				
				BigDecimal dlv = apr.divide(new BigDecimal(12),16,BigDecimal.ROUND_UP)
									.divide(new BigDecimal(100));  
				
				BigDecimal perMonth= totalTenderMoney.multiply(dlv);  //一个月的利息
				return	perMonth.multiply(new BigDecimal(loanPeriodCount));
				
			} catch (Exception e) {
				
				logger.info("interestOfMonth error:"+totalTenderMoney+","+apr+","+loanPeriodCount,e);
				throw new RuntimeException();
			}
	 }
	 /**
	  * 按天计算利息，一年按照360天算
	  * @param totalTenderMoney
	  * @param apr
	  * @param loanPeriodCount
	  * @return
	  * @throws Exception
	  */
	 public static BigDecimal interestOfDay360(BigDecimal totalTenderMoney,BigDecimal apr,Integer loanPeriodCount) {
		    try {
					//每天的利率
					BigDecimal dlv = apr.divide(new BigDecimal(12),16,BigDecimal.ROUND_HALF_UP)
										.divide(new BigDecimal(30),16,BigDecimal.ROUND_HALF_UP)
										.divide(new BigDecimal(100));  
					
					//每一天的利息
					BigDecimal perDay= totalTenderMoney.multiply(dlv); 
					
					BigDecimal interest = perDay.multiply(new BigDecimal(loanPeriodCount));
					
					return interest.setScale(2,BigDecimal.ROUND_HALF_UP); 
				
			} catch (Exception e) {
				
				logger.info("interestOfDay error:"+totalTenderMoney+","+apr+","+loanPeriodCount,e);
				throw new RuntimeException();
			}
	 }
	 /**
	  * 按天计算利息，一年按照365天算
	  * @param totalTenderMoney
	  * @param apr
	  * @param loanPeriodCount
	  * @return
	  * @throws Exception
	  */
	 public static BigDecimal interestOfDay365(BigDecimal totalTenderMoney,BigDecimal apr,int loanPeriodCount) {
		    try {
					//每天的利率
					BigDecimal dlv = apr.divide(new BigDecimal(365),16,BigDecimal.ROUND_HALF_UP)
										.divide(new BigDecimal(100));  
					
					//每一天的利息
					BigDecimal perDay= totalTenderMoney.multiply(dlv); 
					
					BigDecimal interest = perDay.multiply(new BigDecimal(loanPeriodCount));
					
					return interest.setScale(2,BigDecimal.ROUND_HALF_UP); 
				
			} catch (Exception e) {
				
				logger.info("interestOfDay error:"+totalTenderMoney+","+apr+","+loanPeriodCount,e);
				throw new RuntimeException();
			}
	 }
	 /**
	  * 按月分期，计算总利息
	  * @param tenderMoney
	  * @param apr
	  * @param loanPeriodCount
	  * @return
	  * @throws Exception
	  */
	 public static BigDecimal interestOfMonthlyRepay(BigDecimal tenderMoney,BigDecimal apr,Integer loanPeriodCount) {
		 try {
			BigDecimal totalMoney=  getPaymentMoneyPerMonth(tenderMoney,loanPeriodCount, apr);
			BigDecimal t = totalMoney.multiply(new BigDecimal(loanPeriodCount));
			return t.subtract(tenderMoney);
		} catch (Exception e) {
			
			logger.info("interestOfMonthlyRepay error:"+tenderMoney+","+apr+","+loanPeriodCount,e);
			throw new RuntimeException();
		}
	 }
	 
	 /**
	  * 计算月利率，12个月
	  * @return 
	  * @throws Exception
	  */
	 public static BigDecimal getMonthlyRate(BigDecimal apr){
		try {
			 BigDecimal bc=new BigDecimal(12);
			 BigDecimal av=apr.divide(bc,16, BigDecimal.ROUND_DOWN);
			 
			 BigDecimal y= av.divide(new BigDecimal(100),18,BigDecimal.ROUND_DOWN);
			 return y;
		} catch (Exception e) {
			
			logger.error("getMonthlyRate error:"+apr);
		}
		return null;
	 }
	
	 /**
	  * 计算日利息，12个月  30天计算
	  * @return
	  * @throws Exception
	  * 2014-1-17
	  * cjx
	  */
	 public static BigDecimal getMonthlyDate(BigDecimal apr) throws Exception{
		try {
			 BigDecimal bc=new BigDecimal(12);
			 BigDecimal av=apr.divide(bc,6, BigDecimal.ROUND_DOWN);
			 System.out.print("计算月利息==================="+av);  
			 
			 BigDecimal d=new BigDecimal(30);
			 BigDecimal ddv=av.divide(d,6, BigDecimal.ROUND_DOWN);
			 
			 
			 BigDecimal da=ddv.divide(new BigDecimal(100),6,BigDecimal.ROUND_DOWN);
			 System.out.print("计算日利息==================="+da);  
			 return da;
		} catch (Exception e) {
			logger.info("计算日利息"+e.toString());
			System.out.println("计算日利息"+e.toString());
		}
		return null;
	 }
	 
	 /**
	  * 平台管理费，
	  * @param money总金额 
	  * @param lv  比率
	  * @return
	  * @throws Exception
	  * 2014-2-12
	  * cjx
	  */
	 public static BigDecimal getPlatformFee(BigDecimal money,String lv) throws Exception{
			try {
				 BigDecimal bc=new BigDecimal(10000);
				 BigDecimal b=new BigDecimal(lv);
				 
				 BigDecimal av=b.divide(bc,6, BigDecimal.ROUND_DOWN);
				 BigDecimal m=money.multiply(av);
				 System.out.print("平台管理费==================="+m);  
				 return m;
			} catch (Exception e) {
				logger.info("平台管理费出错"+e.toString());
				System.out.println("平台管理费出错"+e.toString());
			}
			return null;
    }
	 
	 
	 /**
	  * 罚息(万分之几) 费，
	  * @param money总金额 
	  * @param lv  比率
	  * @return
	  * @throws Exception
	  * 2014-2-12
	  * cjx
	  */
	 public static BigDecimal getPenaltyFee(BigDecimal money,String lv,Integer date) throws Exception{
			try {
				if(date <= 0){
					return new BigDecimal(0);
				}
				 BigDecimal bc=new BigDecimal(10000);
				 BigDecimal b=new BigDecimal(lv);
				 
				 BigDecimal bs=new BigDecimal(date);
				 BigDecimal av=b.divide(bc,6, BigDecimal.ROUND_DOWN);
				 
				 BigDecimal m=money.multiply(av).multiply(bs);
				 System.out.print("罚息(万分之几) 费==================="+m);  
				 return m;
			} catch (Exception e) {
				logger.info("罚息(万分之几) 费出错"+e.toString());
				System.out.println("罚息(万分之几) 费出错"+e.toString());
			}
			return null;
    }
	 
	 
	 /**
	  * 滞纳金(万分之几) ，
	  * @param money总金额 
	  * @param lv  比率
	  * @return
	  * @throws Exception
	  * 2014-2-12
	  * cjx
	  */
	 public static BigDecimal getForfeitFee(BigDecimal money,String lv,Integer date) throws Exception{
			try {
				 BigDecimal bc=new BigDecimal(10000);
				 BigDecimal b=new BigDecimal(lv);
				 
				 BigDecimal bs=new BigDecimal(date);
				 BigDecimal av=b.divide(bc,6, BigDecimal.ROUND_DOWN);
				 
				 BigDecimal m=money.multiply(av).multiply(bs);
				 System.out.print("滞纳金(万分之几) 费==================="+m);  
				 return m;
			} catch (Exception e) {
				logger.info("滞纳金(万分之几) 费出错"+e.toString());
				System.out.println("滞纳金(万分之几) 费出错"+e.toString());
			}
			return null;
   }
	 /**
	  * 续投奖励
	  * @param continueMoney
	  * @return
	  */
	 public static BigDecimal getContinueReward(BigDecimal continueMoney){
		 SysFeesRate fee = SysCacheUtils.getSysFeesRate();
		 return fee.getSysContinueReward().divide(new BigDecimal(10000))
				 .multiply(continueMoney);
	 }
	 /**
	  * 获取利息费
	  * @param money
	  * @return
	  */
	 public static BigDecimal getInterestFee(BigDecimal interest){
		 SysFeesRate fee = SysCacheUtils.getSysFeesRate();
		 return fee.getSysInterestRate().divide(new BigDecimal(100))
				 	.multiply(interest).setScale(2,BigDecimal.ROUND_UP);
	 }
	 /**
	  * 获取滞纳金管理费
	  * @param money
	  * @return
	  */
	 public static BigDecimal getLateInterestFee(BigDecimal interest){
		 SysFeesRate fee = SysCacheUtils.getSysFeesRate();
		 return fee.getSysOverdueRate().divide(new BigDecimal(100))
				 	.multiply(interest).setScale(2,BigDecimal.ROUND_UP);
	 }
	 /**
	  * 推荐奖励，按比例
	  * @param interest
	  * @return
	  */
	 public static BigDecimal getRecommondMoneyByRate(BigDecimal interest) {
		    Map<String,String> map= SysCacheUtils.getConfigParams();
			BigDecimal inviteRewardRate = new BigDecimal(map.get("recommend_InviteRewardRate"));
			inviteRewardRate = inviteRewardRate.divide(new BigDecimal(100));
			return interest.multiply(inviteRewardRate).setScale(2, BigDecimal.ROUND_DOWN);
	 }
	 
	 /**
	  * 推荐奖励,按投资金额比例
	  * @param tenderMoney
	  * @return
	  */
	 public static BigDecimal getRecommondMoneyByTenderMoney(BigDecimal tenderMoney){
		 Map<String,String> map= SysCacheUtils.getConfigParams();
		 BigDecimal inviteRewardMoney=new BigDecimal(map.get("recommend_InviteRewardMoney")).divide(new BigDecimal(1000));
		 return tenderMoney.multiply(inviteRewardMoney).setScale(2,BigDecimal.ROUND_DOWN);
		 
	 }
	 
	 public static BigDecimal getRecommondMoneyByTenderMoney(
				BigDecimal tenderMoney, Borrow borrow) {
		 Map<String,String> map= SysCacheUtils.getConfigParams();
		 BigDecimal inviteRewardMoney=new BigDecimal(map.get("recommend_InviteRewardMoney")).divide(new BigDecimal(1000));
		 
		 Integer isDay = borrow.getIsDay();//是否天标
		 Integer borrowTimeLimit = borrow.getBorrowTimeLimit(); //天数/月份数
		 
		 if(isDay.equals(1)){  //天标
			 inviteRewardMoney=new BigDecimal(borrowTimeLimit).divide(new BigDecimal(360),12,BigDecimal.ROUND_HALF_DOWN);
		 }else{
			 inviteRewardMoney=new BigDecimal(borrowTimeLimit).divide(new BigDecimal(12),12,BigDecimal.ROUND_HALF_DOWN);
		 }
		 
		 
		 return tenderMoney.multiply(inviteRewardMoney).setScale(2,BigDecimal.ROUND_DOWN);
		}
	 
	 /**
	  * 按天计算借款手续费,一月按30天计算
	  * @param period
	  * @param amount
	  * @return
	  */
	 public static BigDecimal getLoanFeeByDay(Integer period,BigDecimal amount){
		 SysFeesRate fee = SysCacheUtils.getSysFeesRate();
		 MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN);
		 BigDecimal t=new BigDecimal(30);
		 BigDecimal l=new BigDecimal(period);
		 return (fee.getSysLoanPoundage().divide(t, mc)) //一天的费率
				 	.multiply(l).multiply(amount).divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_UP);
	 }
	 /**
	  * 按月计算借款手续费
	  * @param period
	  * @param amount
	  * @return
	  */
	 public static BigDecimal getLoanFeeByMonth(Integer period,BigDecimal amount){
		 SysFeesRate fee = SysCacheUtils.getSysFeesRate();
		 return fee.getSysLoanPoundage().multiply(new BigDecimal(period)).
				 multiply(amount).divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_UP);
	}
	 /**
	  * 计算本息（复利）
	  * @return
	  */
	 public static BigDecimal getTotalByInterestInverst(BigDecimal capital,BigDecimal yearRate,Integer period){
		BigDecimal monthRate =  getMonthlyRate(yearRate);//月利率
		
		BigDecimal r = monthRate.add(new BigDecimal(1)); //1+月利率
		
		double m = Math.pow(r.doubleValue(), period.doubleValue()); //(1+月利率)的period次方
	 
		BigDecimal totalMoney = capital.multiply(new BigDecimal(m));
		
		return totalMoney.setScale(2, BigDecimal.ROUND_DOWN);//保留2位小数
	 }
	 /**
	  * 通过利息与本金计算年利率，
	  * @param interest
	  * @param capital
	  * @param period 借款月数
	  * @return
	  */
	 public static BigDecimal getYearRate(BigDecimal interest,BigDecimal capital,int period){
		 BigDecimal rate = interest.divide(capital,9,BigDecimal.ROUND_HALF_UP)
				 			.divide(new BigDecimal(period),9,BigDecimal.ROUND_HALF_UP); //一个月的利率
		 return rate.multiply(new BigDecimal(12)).
				 multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);//12个月利率
	 }
	 /**
	  * 一次性还款计算年利率
	  * @param interest 利息
	  * @param capital  本金
	  * @param period 天数
	  * @return
	  */
	 public static BigDecimal getYearRateOfOneTimeRepay(BigDecimal interest,BigDecimal capital,int period){
		 BigDecimal rate = interest.divide(capital,9,BigDecimal.ROUND_HALF_UP)
				 			.divide(new BigDecimal(period),9,BigDecimal.ROUND_HALF_UP); //一天的利率
		 return rate.multiply(new BigDecimal(30)).multiply(new BigDecimal(12)).
				 multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);//12个月利率
	 }
	 
	 /**
	  * 每月分期还款，计算每月还多少
	  * @param money总金额 
	  * @param lv  比率
	  * @return
	  * @throws Exception
	  */
	 public static BigDecimal getPaymentMoneyPerMonth(BigDecimal money,Integer period,BigDecimal yearRate){
			 try {
				 BigDecimal r=new BigDecimal(1);
				 BigDecimal totalR=new BigDecimal(0);
				 //月利率 
			     BigDecimal mr=yearRate.divide(new BigDecimal(12),12,BigDecimal.ROUND_UP)
			    		 	.divide(new BigDecimal(100));  //月利率
//			     System.out.println(mr +"  R====月利率");
			     
				 BigDecimal rr=r.add(mr);  //1+R
			//	 System.out.println(rr +"  ====1+R ");
				 
				 Double k =  Math.pow(rr.doubleValue(), period);  //n 次方
				 
				 BigDecimal bd = new BigDecimal(k);
				 BigDecimal  bd2 = bd.setScale(12,BigDecimal.ROUND_HALF_DOWN);
				 
			//	 System.out.println("N次方"+bd2);
				 BigDecimal v=  mr.multiply(bd2);  //月利率 X n次方
				// System.out.println("月利率 X n次方" +v );
				 BigDecimal muj= bd2.subtract(r);    //被除
				 
			//	 System.out.println(" n次方  -  1" +muj );
				 BigDecimal zhi= v.divide(muj, 12,BigDecimal.ROUND_DOWN);
			//	 System.out.println(" 相除  -  1" +zhi );
				 
				 BigDecimal payMoney = money.multiply(zhi);
				 return payMoney.setScale(2,BigDecimal.ROUND_DOWN);
			} catch (Exception e) {
				
				logger.error("getPaymentMoneyPerMonth error:"+money+","+period+","+yearRate,e);
			}
			return null;
   }
	 //按月分期付款本金方法
	 public static BigDecimal getBjMoney(BigDecimal totalMoney,BigDecimal bxMoney, Integer period,
				BigDecimal yearRate) {
			BigDecimal monthRate=yearRate.divide(new BigDecimal(12),
					6, BigDecimal.ROUND_UP).divide(new BigDecimal(100), 6,
					BigDecimal.ROUND_DOWN);
			BigDecimal Bj = totalMoney;
			for (int i = 0; i < period; i++) {
				System.out.println("还利息："+Bj.multiply(monthRate));
				System.out.println("还本金："+bxMoney.subtract(Bj.multiply(monthRate)));
				Bj =Bj.subtract(bxMoney.subtract(Bj.multiply(monthRate)));
				System.out.println("剩余本金："+Bj);
			}
			return null;
	}
	 /**
	  * 分摊奖励
	  * @return
	  */
	public static BigDecimal getTenderRewardPart(BigDecimal totalMoney,BigDecimal tenderMoney,BigDecimal partMoney){

			BigDecimal bl = tenderMoney.divide(totalMoney, 
							6,BigDecimal.ROUND_DOWN);
			
			return partMoney.multiply(bl).setScale(2,BigDecimal.ROUND_DOWN);
			
		
	}
	 /**
	  * 按比例计算奖励
	  * @return
	  */
	public static BigDecimal getTenderRewardByTenderRate(BigDecimal rate,BigDecimal tenderMoney){
		BigDecimal dk= rate.divide(new BigDecimal(100));
		return tenderMoney.multiply(dk).setScale(2,BigDecimal.ROUND_DOWN);
	}
	public static void main(String[] args) {
		 try {
			 BigDecimal d = new BigDecimal(12.33333);
			 BigDecimal a = d.setScale(2,BigDecimal.ROUND_DOWN);
			 System.out.println("fffffffffff--->"+a);
			} catch (Exception e) {
				System.out.println("--==="+e.toString());
			}
	}
	 
	/**
	 * 计算标逾期滞纳金
	 * @return
	 */
	public static BigDecimal getLateInterest(BorrowRepossessed borrowRepossessed,int lateDays){
		BigDecimal lateInterest=null;
		SysFeesRate fee = SysCacheUtils.getSysFeesRate();
		BigDecimal OverdueMoney=borrowRepossessed.getRepossessedInterest().add(borrowRepossessed.getRepossessedCapital());
		lateInterest=OverdueMoney.divide(new BigDecimal(10000)).multiply(fee.getSysOverdueRate()).multiply(new BigDecimal(lateDays));
		return  lateInterest.setScale(2, BigDecimal.ROUND_UP);
	}
	/**
	 * 计算债权转让费
	 * @return
	 */
	public static BigDecimal getTransferFee(BigDecimal capital,BigDecimal yearRate){
		BigDecimal rate = getMonthlyRate(yearRate); //月利率 
		return  capital.multiply(rate).divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_UP);//半个月利息
	}
	/**
	 * 转让价格
	 * @param waitCapital
	 * @param effTime
	 * @param curInterest
	 * @return
	 */
	public static BigDecimal getTransferMoney(BigDecimal waitCapital,Date effTime,Date repayDate,BigDecimal curInterest){
		
		try {
			int curDay = DateUtils.daysBetween(effTime,new Date());
			int period = DateUtils.daysBetween(effTime,repayDate); //当期的期限
			BigDecimal i = curInterest.divide(new BigDecimal(period),16,BigDecimal.ROUND_HALF_UP) //一天的利息
							.multiply(new BigDecimal(curDay));
			return i.add(waitCapital).setScale(2,BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			
			return null;
		}
	}
	/**
	 * 获取当期距离今天应得的利息
	 * @param curInterest 月利息
	 * @param day 天数
	 * @return
	 */
	public static BigDecimal getDayInterest(BigDecimal monthInterest,Integer day){
	
			BigDecimal i = monthInterest.divide(new BigDecimal(30),4,BigDecimal.ROUND_HALF_UP) //一天的利息
							.multiply(new BigDecimal(day)).setScale(2,BigDecimal.ROUND_UP);//扣除15天的利息
			return i;
	}
	
	/**
	 *  计算赎回手续费
	 */
	public static BigDecimal getRedeemFee(BigDecimal effectiveAmount,Integer lateDays){
		return effectiveAmount.multiply(new BigDecimal(lateDays)).multiply(new BigDecimal(0.001));
	}

	
	
	
}
