package com.rbao.east.service.borrow.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.UserAccount;

/**
 * 给力标
 * @author Liutq
 *
 */
@Service("generalBorrow")
@Transactional
public class GeneralBorrow extends BorrowTypeHandleServiceImpl {

	@Override
	public ServiceResult withinAllowBorrowMoney(UserAccount borrowAcc,Borrow borrow) {
		return new ServiceResult(ServiceResult.SUCCESS);
	}

}
