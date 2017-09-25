package com.rbao.east.thread;

import com.rbao.east.service.BorrowService;

/**
 * 自动投标线程
 * @author LTQ
 *
 */
public class AutoTenderThread implements Runnable {

	private BorrowService borService;
	
	public AutoTenderThread(BorrowService borService){
		this.borService = borService;
	}
	
	@Override
	public void run() {
		borService.autoTenderRules();
	}

}
