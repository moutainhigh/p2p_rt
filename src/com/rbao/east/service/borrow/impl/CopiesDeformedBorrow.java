package com.rbao.east.service.borrow.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.CalculateProcess;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.utils.DateUtils;

/**
 * 第二种流转标，
 * 跟普通流转标的区别是还款日期都是一样的
 * @author Liutq
 *
 */
@Service("CopiesDeformedBorrow")
@Transactional
public class CopiesDeformedBorrow extends CopiesBorrow {
	@Autowired
	BorrowRepaymentService repaySvc;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 扩展CopiesBorrow类的方法
	 * ，按照剩余天数来计算利息
	 * 这个方法在投标的时候会被调用
	 * @param tender
	 * @param borrow
	 */
	protected void fullInTenderAndBorrowOfSaveTender(BorrowTender tender,Borrow borrow){
		
		super.fullInTenderAndBorrowOfSaveTender(tender, borrow);
		
		//最后还款时间
		Date repayTime = DateUtils.addMonth(borrow.getPublishDatetime(), borrow.getBorrowTimeLimit());
		//当前时间距离还款时间实际的天数
		int betweenDays = DateUtils.daysBetween(new Date(), repayTime);
		
		BigDecimal interest = CalculateProcess.interestOfDay365(tender.getTenderAmount(), borrow.getAnnualInterestRate(), betweenDays);
		
		tender.setInterestAmount(interest);
	}
	
	/**
	 * 扩展BorrowTypeHandleServiceImpl类的方法
	 * 投标的时候会调用，一条待还记录对应多条代收记录，
	 * 每次有人投标都追加代收记录，更新待还金额
	 */
	protected BorrowRepayment buildRepaymentAndRepossessed(Borrow borrow,List<BorrowTender> tenderList,Integer seq,Map<String,BigDecimal> monthlyMap){
		
		BorrowRepayment repay = super.buildRepaymentAndRepossessed(borrow, tenderList, seq, monthlyMap);
		
		List<BorrowRepayment> dbRepayList = repaySvc.getRepaymentByBorrowId(borrow.getId());
		if(dbRepayList.size() > 0){
			BorrowRepayment dbRepay = dbRepayList.get(0);
			
			dbRepay.setRepaymentInterest(dbRepay.getRepaymentInterest().add(repay.getRepaymentInterest()));
			dbRepay.setRepaymentPrincipal(dbRepay.getRepaymentPrincipal().add(repay.getRepaymentPrincipal()));
			dbRepay.setRepaymentAmount(dbRepay.getRepaymentPrincipal().add(dbRepay.getRepaymentInterest()));
			
			dbRepay.setBorrowRepossessedlist(repay.getBorrowRepossessedlist());
			
			for(BorrowRepossessed repo : dbRepay.getBorrowRepossessedlist()){
				repo.setPrepareDatetime(dbRepay.getRepaymentTime());
			}
			
			return dbRepay;
		}else{
			//还款时间，从发布时间开始结算
			repay.setRepaymentTime(DateUtils.addMonth(borrow.getPublishDatetime(), borrow.getBorrowTimeLimit()));
			for(BorrowRepossessed repossess : repay.getBorrowRepossessedlist()){
				repossess.setPrepareDatetime(repay.getRepaymentTime());
			}
			return repay;
		}
		
	}
	

}
