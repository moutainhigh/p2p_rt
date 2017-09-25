package com.rbao.east.service.borrow.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.entity.UserEvaluate;
import com.rbao.east.service.UserEvaluateService;
import com.rbao.east.utils.CompareUtils;

/**
 * 信用标
 * @author Liutq
 *
 */
@Service("creditBorrow")
@Transactional
public class CreditBorrow extends BorrowTypeHandleServiceImpl {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserEvaluateService userEvaluateService;
	/**
	 * 判断可用信用额度
	 */
	@Override
	public ServiceResult withinAllowBorrowMoney(UserAccount borrowAcc,Borrow borrow) {
		UserEvaluate userEvaluate = userEvaluateService.getUserEvaluateByUserId(borrow.getUserId());
		if(userEvaluate == null){
			return new ServiceResult("142","信用额度不足");
		}
		if(CompareUtils.greaterThan(borrow.getBorrowSum(), userEvaluate.getCreditAvailable())){
			return new ServiceResult("142","信用额度不足，您的信用额度为："+userEvaluate.getCreditAvailable());
		}
		return new ServiceResult(ServiceResult.SUCCESS);
	}
	/**
	 * 复审通过
	 * @param borrow
	 * @return
	 * @throws Exception
	 */
	public ServiceResult reviewSuccess(Borrow borrow){
		try {
			ServiceResult res = super.reviewSuccess(borrow);
			if(!res.isSuccessed())
				return res;
			//减掉可用信用
			UserEvaluate userEvaluate = userEvaluateService.getUserEvaluateByUserId(borrow.getUserId());
			userEvaluate.setCreditAvailable(userEvaluate.getCreditAvailable().subtract(borrow.getTenderSum()));
			userEvaluate.setCreditUnavailable(userEvaluate.getCreditUnavailable().add(borrow.getTenderSum()));
			userEvaluateService.save(userEvaluate);
			
			return new ServiceResult(ServiceResult.SUCCESS,"审核成功");
		} catch (Exception e) {
			
			log.error("creditborrow reviewSuccess faild:"+borrow,e);
			throw new RuntimeException("creditborrow reviewSuccess faild:"+borrow,e);
		}
	}
	/**
	 * 还款
	 */
	public ServiceResult repay(BorrowRepayment repay){
		try {
			ServiceResult res = super.repay(repay);
			if(!res.isSuccessed())
				return res;
			Borrow borrow = this.borrowDao.selectByPrimaryKey(repay.getBorrowId());
			//加上可用信用
			UserEvaluate userEvaluate = userEvaluateService.getUserEvaluateByUserId(borrow.getUserId());
			userEvaluate.setCreditAvailable(userEvaluate.getCreditAvailable().add(repay.getRepaymentPrincipal()));
			userEvaluate.setCreditUnavailable(userEvaluate.getCreditUnavailable().subtract(repay.getRepaymentPrincipal()));
			userEvaluateService.save(userEvaluate);
			
			return new ServiceResult(ServiceResult.SUCCESS,"还款成功");
		} catch (Exception e) {
			
			log.error("creditborrow repay faild:"+repay,e);
			throw new RuntimeException("creditborrow repay faild:"+repay,e);
		}
	}

}
