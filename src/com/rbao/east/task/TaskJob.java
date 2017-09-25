package com.rbao.east.task;

public interface TaskJob {

	/**
	 * 标逾期处理
	 */
	public void borrowOverdueDispose();

	/**
	 * 流标解冻
	 */
	public void returnedMoneyForFlowStandardByBorrowTenderList();

	/**
	 * 自动发标
	 */
	public void autoTenderRules();

	/**
	 * 债券转让处理
	 */
	public void borrowTransferOverdueDispose();

	/**
	 * 秒标自动审核
	 */
	public void autoBorrowAuditForImmediateRepay();

	/**
	 * 自动还款
	 */
	public void autoBorrowRepaymentReturnMoney();

	/**
	 * 每日帐户统计<br>
	 * 每天自动从rb_user_account表中拷贝数据到rb_account_statistics表中，每日统计
	 */
	public void autoCopyDataByDay();

	/**
	 * 每日平台全部统计数据
	 */
	public void saveReport();
	
	
	/**
	 * 提前3天通知即将vip到期用户
	 * vip到期后更改用户状态
	 */
	public void vipNotify();
	
	
	/**
	 * 自动发放体验金利息
	 * */
	public void autoPayExperienceGold();
	/**
	 * 自动核对提现账单
	 */
	public void autoCheckCashNoOrder();
}
