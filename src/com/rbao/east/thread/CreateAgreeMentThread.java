package com.rbao.east.thread;

import com.rbao.east.entity.Borrow;
import com.rbao.east.service.BorrowTypeHandleService;


/**
 * 生成pdf文档
 * @author yan
 *
 */
public class CreateAgreeMentThread implements Runnable {

	@SuppressWarnings("rawtypes")
	private static BorrowTypeHandleService  borrowTypeHandleService= null;
	private static Borrow borrow=null;
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	public CreateAgreeMentThread(BorrowTypeHandleService borrowTypeHandleService,Borrow borrow){
		this.borrowTypeHandleService = borrowTypeHandleService;
		this.borrow=borrow;
	}
	
	@Override
	public void run() {
		borrowTypeHandleService.createAgreeMent(borrow);
	}

}
