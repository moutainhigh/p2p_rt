package com.rbao.east.service.borrow.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.utils.CompareUtils;

/**
 * 秒标
 * @author Liutq
 *
 */
@Service("immediateRepayBorrow")
@Transactional
public class ImmediateRepayBorrow extends BorrowTypeHandleServiceImpl {

	/**
	 * 可用金额不能小于本金+利息 
	 * @param borrowAcc
	 * @param borrow
	 * @return
	 */
	@Override
	public ServiceResult withinAllowBorrowMoney(UserAccount borrowAcc,Borrow borrow) {
		BigDecimal interest = super.calculateInterest(false,borrow.getBorrowSum(),
									borrow.getAnnualInterestRate(),1,Borrow.REPAYMENT_STYLE_ONETIME);
		if(CompareUtils.greaterThan(borrow.getBorrowSum().add(interest),
						borrowAcc.getAvailableMoney())){
			return new ServiceResult("125","账户可用金额不足");
		}
		return new ServiceResult(ServiceResult.SUCCESS);
	}
	/**
	 * 保存发标之前，填充borrow
	 * @param borrow
	 */
	protected void fullInBorrowOfSaveBorrow(Borrow borrow){
		borrow.setIsDay(Borrow.IS_DAY_N);
		borrow.setBorrowTimeLimit(1);
		borrow.setRepaymentStyle(Borrow.REPAYMENT_STYLE_ONETIME);
		super.fullInBorrowOfSaveBorrow(borrow); 		
	}
	protected BorrowRepayment buildRepaymentAndRepossessed(Borrow borrow,List<BorrowTender> tenderList,Integer seq,Map<String,BigDecimal> monthlyMap){
		Date now = new Date();
		BorrowRepayment repay = super.buildRepaymentAndRepossessed(borrow, tenderList, seq, monthlyMap);
		repay.setRepaymentTime(now);
		for(BorrowRepossessed repossess : repay.getBorrowRepossessedlist()){
			repossess.setPrepareDatetime(now);
		}
		return repay;
	}
	/*public void continueRewardToTenderAccount(BorrowTender tender,UserAccount tenderAcc,Borrow borrow){
		//秒标没有续投奖励，因此重写这个方法，什么事也不干
	}*/
	/*public void updateInvstContinueByRepossessed(Integer userId,BigDecimal money){
		//秒标没有续投奖励，因此重写这个方法，什么事也不干
	}*/
}
