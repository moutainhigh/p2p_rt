package com.rbao.east.service.borrow.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.SysCacheUtils;

/**
 * 净值标
 * @author Liutq
 *
 */
@Service("netAssetsBorrow")
@Transactional
public class NetAssetsBorrow extends BorrowTypeHandleServiceImpl {

	/**
	 * 判断用户的可用净资产是否大于等于当前
	 */
	@Override
	public ServiceResult withinAllowBorrowMoney(UserAccount borrowAcc,Borrow borrow) {
		SysFeesRate rate = SysCacheUtils.getSysFeesRate();
		BigDecimal borrowRate = rate.getSysWorthRate().divide(new BigDecimal(100)); //发布净值标比例
		Integer[] unFullStatus = {Borrow.STATUS_NEW ,Borrow.STATUS_FIRSTAUDIT_YES};
		if(borrowService.getBorrowCount(borrow.getUserId(), unFullStatus)>0){ //查询是否有未完成的标
			return new ServiceResult("101","还有未完成的标，不能继续发");
		}
		//计算利息
		BigDecimal interest = calculateInterest(borrow.getIsDay().equals(Borrow.IS_DAY_Y),
												borrow.getBorrowSum(), borrow.getAnnualInterestRate(),
												borrow.getBorrowTimeLimit(), borrow.getRepaymentStyle());
						
		String[] kinds = {Borrow.BORROW_TYPE_JING}; //净值标
		Integer[] inStatus = {BorrowRepayment.REPAYMENT_STATUS_REPAYING,BorrowRepayment.REPAYMENT_STATUS_OVERDUE,BorrowRepayment.REPAYMENT_STATUS_FAILD};
		BigDecimal noRepayCopiesMoney = this.repayDao.selectSumByKindAndStatus(borrowAcc.getUserId(),kinds , inStatus);	
		
		BigDecimal netAssetsMoney = borrowAcc.getAllMoney() //可用净资产 = 可用金额-代还利息-代还本金-未还的净值标金额
								.subtract(borrowAcc.getWaitRepayInterest())
								.subtract(borrowAcc.getWaitRepayCapital())
								.subtract(noRepayCopiesMoney);
		netAssetsMoney = netAssetsMoney.multiply(borrowRate);
		if(CompareUtils.greaterThan(borrow.getBorrowSum().add(interest), netAssetsMoney)){
			return new ServiceResult("140","可用净值不足，您当前的可用净值为["+netAssetsMoney+"]元");
		}
		
		return new ServiceResult(ServiceResult.SUCCESS);
	}

}
