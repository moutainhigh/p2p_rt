package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.BorrowRedeemDao;
import com.rbao.east.dao.UserAccountDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRedeem;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.BorrowRedeemService;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.RequestUtils;

@Service
@Transactional
public class BorrowRedeemServiceImpl implements BorrowRedeemService {

	@Autowired
	private BorrowRedeemDao borrowRedeemDao;
	
	@Autowired
	private UserService userService;

	@Autowired
	private BorrowRepossessedService borrowRepossessedService;

	@Autowired
	private BorrowTenderService borrowTenderService;

	@Autowired
	private UserAccountService accountService;

	@Autowired
	private UserAccountDao accountDao;

	@Autowired
	private BorrowService borrowQueryService;

	@Autowired
	private BorrowRepaymentService borrowRepaymentService;
	
	@Autowired
	private AccountLogService accountLogService;

	@Autowired
	private MessageCenterService messageCenterService;
	
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
	
	@Override
	public boolean saveBorrowRedeem(Map<String, Object> param) {
		boolean bool = false;
		try {
			param.put("repossessedStatus",
					BorrowRepossessed.STATUS_REPOSSESSED_SUCC);
			Integer tenderId = Integer.parseInt(param.get("tenderId")
					.toString());
			BorrowRedeem isHaveBorrowRedeem=borrowRedeemDao.selectEntity("selectBorrowRedeemByTenderId",tenderId);
			if(null != isHaveBorrowRedeem ){
					throw new RuntimeException("不能重复申请！！！");
			}
			BorrowTender borrowTender = borrowTenderService
					.selectBorrowTenderByBorrowTenderId(tenderId);
			List<BorrowRepossessed> list = borrowRepossessedService
					.selectBorrowRepossessedByTenderId(param);
			if (borrowTender == null) {
				throw new RuntimeException("尚无该投标详细记录");
			}
			if (list.size() <= 0) {
				throw new RuntimeException("尚无该待收款详细记录");
			}
			
			BorrowRedeem borrowRedeem = new BorrowRedeem();
			borrowRedeem.setTenderId(tenderId);
			borrowRedeem.setRedeemStatus(BorrowRedeem.STATUS_AUDITING);
			borrowRedeem.setRedeemMoney(borrowTender.getEffectiveAmount());
			// 计算赎回手续费
			BigDecimal redeemFee = CalculateProcess.getRedeemFee(
					borrowTender.getEffectiveAmount(), list.size());
			// 计算赎回到账金额
			BigDecimal redeemBackmoney = borrowTender.getEffectiveAmount()
					.subtract(redeemFee);
			borrowRedeem.setRedeemFee(redeemFee);
			borrowRedeem.setRedeemBackmoney(redeemBackmoney);
			borrowRedeem.setCreateIp(param.get("IpAddr").toString());
			borrowRedeem.setCreateTime(new Date());
			bool = borrowRedeemDao.insertSelective(borrowRedeem);
			if (bool) {
				param.clear();
				param.put("redeemId", borrowRedeem.getId());
				param.put("list", list);
				bool = borrowRedeemDao.insertObj(
						"insertborrowRedeemRepossessed", param);
				if (!bool) {
					throw new RuntimeException("添加赎回记录失败！！！请稍后再试！！！！");
				}
			} else {
				throw new RuntimeException("申请赎回失败！！！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return bool;
	}

	@Override
	public PageModel getBorrowByStatusList(Map<String, String> param) {
		// TODO Auto-generated method stub
		return borrowRedeemDao.getPage("getBorrowRedeemList",
				"getBorrowRedeemCount", param);
	}

	@Override
	public BorrowRedeem getBorrowRedeemById(Integer pk) {
		// TODO Auto-generated method stub
		return borrowRedeemDao.selectEntity("getBorrowRedeemById", pk);
	}

	@Override
	public List<Integer> getRepossessedIdListByPk(Integer pk) {
		// TODO Auto-generated method stub
		return borrowRedeemDao.selects("getRepossessedIdListByPk", pk);
	}

	@Override
	public boolean updateBorrowRedeem(Map<String, Object> param) {
		boolean bool = false;
		try {
			Integer userId = Integer.valueOf( param.get("userId").toString());
			BorrowRedeem borrowRedeem = this.getBorrowRedeemById(Integer.parseInt(param.get("id").toString()));
			User frontUser=userService.getById(borrowRedeem.getUserId());
			if(null==borrowRedeem){
				throw new RuntimeException("没有该赎回信息！！！");
			}
			if (Integer.parseInt(param.get("redeemStatus").toString()) == BorrowRedeem.STATUS_FAIL) {
				// 审核失败
				borrowRedeem.setRedeemStatus(Integer.parseInt(param.get(
						"redeemStatus").toString()));
				borrowRedeem.setAuditUser(userId);
				borrowRedeem.setAuditTime(new Date());
				borrowRedeem.setAuditIp(param.get("ipAddress").toString());
				if (param.containsKey("redeemRemark")) {
					borrowRedeem.setRedeemRemark(param.get("redeemRemark")
							.toString());
				}
				bool = borrowRedeemDao
						.updateByPrimaryKeySelective(borrowRedeem);
				
				//发送消息
				MessageCenter center = new MessageCenter();
				center.setMessageContent("标【"+borrowRedeem.getBorrowTitle()+"】赎回请求失败，失败原因为【"+borrowRedeem.getRedeemRemark()+"】.");
				center.setMessageSendIp(RequestUtils.getIpAddr());
				center.setReceiveUserId(frontUser.getId());
				center.setMessageTitle("赎回申请失败");
				center.setSendUserId(userId);
				center.setMessageAddress(frontUser.getUserEmail());
				messageCenterService.send(center, Notice.REDEEM_NO);
				
			} else if (Integer.parseInt(param.get("redeemStatus").toString()) == BorrowRedeem.STATUS_SUCCESS) {
				// 审核成功
				borrowRedeem.setRedeemStatus(Integer.parseInt(param.get(
						"redeemStatus").toString()));
				borrowRedeem.setAuditUser(userId);
				borrowRedeem.setAuditTime(new Date());
				borrowRedeem.setAuditIp(param.get("ipAddress").toString());
				if (param.containsKey("redeemRemark")) {
					borrowRedeem.setRedeemRemark(param.get("redeemRemark")
							.toString());
				}
				bool = borrowRedeemDao
						.updateByPrimaryKeySelective(borrowRedeem);
				if (bool) {
					param.clear();
					param.put("borrowRedeemId", borrowRedeem.getId());
					param.put("receiveUserId",frontUser.getId());
					param.put("sendUserId",userId);
					bool = this.redeemReturnedMoney(param);
					if (bool) {
						MessageCenter center = new MessageCenter();
						center.setMessageContent("标【"+borrowRedeem.getBorrowTitle()+"】赎回申请成功，申请金额为【"+borrowRedeem.getRedeemMoney()+"】元，实到账金额为【"+borrowRedeem.getRedeemBackmoney()+"】元，扣除手续费【"+borrowRedeem.getRedeemFee()+"】元.");
						center.setMessageSendIp(RequestUtils.getIpAddr());
						center.setReceiveUserId(frontUser.getId());
						center.setMessageTitle("赎回申请成功");
						center.setSendUserId(userId);
						center.setMessageAddress(frontUser.getUserEmail());
						messageCenterService.send(center, Notice.REDEEM_YES);
						
					
						
					}else{
						throw new RuntimeException("借款赎回回款失败，请稍后再试");
					}
				} else {
					throw new RuntimeException("借款赎回审核失败");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return bool;
	}

	public boolean redeemReturnedMoney(Map<String, Object> param) {
		boolean bool = false;
		try {
			Integer borrowRedeemId=Integer.parseInt(param.get("borrowRedeemId").toString());
			Integer receiveUserId=Integer.parseInt(param.get("receiveUserId").toString());
			Integer sendUserId=Integer.parseInt(param.get("sendUserId").toString());
			
			BorrowRedeem borrowRedeem = this
					.getBorrowRedeemById(borrowRedeemId);
			BorrowTender borrowTender = borrowTenderService
					.selectBorrowTenderByBorrowTenderId(borrowRedeem
							.getTenderId());
			borrowTender.setTenderStatus(BorrowTender.STATUS_REDEEM);
			bool = borrowTenderService.updateByBorrowTender(borrowTender);
			if (bool) {
				Borrow borrow = borrowQueryService.getBorrowById(borrowTender
						.getBorrowId());
				borrow.setTenderSum(borrow.getTenderSum().subtract(
						borrowRedeem.getRedeemMoney()));
				if(borrow.getBorrowStatus().equals(Borrow.STATUS_FULL)||borrow.getBorrowStatus().equals(Borrow.STATUS_REPLYING)){
					borrow.setBorrowStatus(Borrow.STATUS_FIRSTAUDIT_YES);
				}
				bool = borrowQueryService.updateBorrow(borrow);
				if (bool) {
					BigDecimal repossessedInterest=new BigDecimal(0);
					List<Integer> repossessedIds = this
							.getRepossessedIdListByPk(borrowRedeemId);
					BigDecimal stayingCapital=new BigDecimal(0);
					Integer lastDay=null;
					for (int i = 0; i < repossessedIds.size(); i++) {
						BorrowRepossessed borrowRepossessed = borrowRepossessedService
								.getBorrowRepossessedByPk(repossessedIds
										.get(i));
						stayingCapital=stayingCapital.add(borrowRepossessed.getRepossessedCapital());
						
						repossessedInterest=repossessedInterest.add(borrowRepossessed.getRepossessedInterest());
						borrowRepossessed
								.setRepossessedStatus(BorrowRepossessed.STATUS_RETIRED_BILL);
						borrowRepossessedService
								.updateBorrowRepossessed(borrowRepossessed);
						BorrowRepayment borrowRepayment = borrowRepaymentService
								.getBorrowRepaymentById(borrowRepossessed
										.getRepaymentId());
						borrowRepayment
								.setRepaymentAmount(borrowRepayment
										.getRepaymentAmount()
										.subtract(borrowRepossessed.getPrepareAmount()));
						borrowRepayment
								.setRepaymentPrincipal(borrowRepayment
										.getRepaymentPrincipal()
										.subtract(borrowRepossessed.getRepossessedCapital()));
						borrowRepayment.setRepaymentInterest(borrowRepayment.getRepaymentInterest().subtract(borrowRepossessed.getRepossessedInterest()));
						borrowRepayment.setLateInterest(borrowRepayment.getLateInterest().subtract(borrowRepossessed.getLateInterest()));
						if(!CompareUtils.greaterThanZero(borrowRepayment.getRepaymentAmount())){
							borrowRepayment.setRepaymentStatus(BorrowRepayment.REPAYMENT_STATUS_REDEEMED);
						}
						borrowRepaymentService
								.updateBorrowRepayment(borrowRepayment);
						
						if(borrowRepossessed.getCurrentPeriod().equals(borrowRepossessed.getTotalPeriod())){ 
							lastDay=DateUtils.daysBetween(new Date(),borrowRepossessed.getPrepareDatetime());
						}
						
					}
					
					UserAccount userAccount = accountService
							.getByUserId(borrowTender.getUserId());
					
					userAccount.setAvailableMoney(userAccount
							.getAvailableMoney().add(
									borrowRedeem.getRedeemMoney()));
					
					
					userAccount.setAllMoney(userAccount.getAllMoney().subtract(repossessedInterest));
					userAccount.setWaitRepossessedCapital(userAccount.getWaitRepossessedCapital().subtract(stayingCapital));
					userAccount.setWaitRepossessedInterest(userAccount.getWaitRepossessedInterest().subtract(repossessedInterest));
					
					bool = accountService
							.updateByPrimaryKeySelective(userAccount);
					
					
					
					if (bool) {
					
						accountLogService.add(userAccount, AccountLog.TRADE_CODE_BORROW_REDEEM, borrowRedeem.getRedeemMoney()
								,new BigDecimal(0), borrowTender.getUserId(), 
								"用户["+userAccount.getUser().getUserAccount()+"]赎回成功,"+
								"到账金额为["+borrowRedeem.getRedeemBackmoney()+"]元,"+
								"赎回金额为["+borrowRedeem.getRedeemMoney()+"]元,"+
								"手续费为["+borrowRedeem.getRedeemFee()+"]元"
								,RequestUtils.getIpAddr());

						userAccount.setAvailableMoney(userAccount
								.getAvailableMoney().subtract(
										borrowRedeem.getRedeemFee()));
						
						userAccount.setAllMoney(userAccount.getAllMoney().subtract(borrowRedeem.getRedeemFee()));

						
						bool = accountService
								.updateByPrimaryKeySelective(userAccount);
					
					if (bool) {
						//用户赎回信息添加到日志表
						accountLogService.add(userAccount, AccountLog.TRADE_CODE_BORROW_REDEEM_FEE, borrowRedeem.getRedeemFee()
								,new BigDecimal(0), borrowTender.getUserId(), 
								"用户["+userAccount.getUser().getUserAccount()+"]赎回成功,"+
								"扣除手续费["+borrowRedeem.getRedeemFee()+"]元"
								,RequestUtils.getIpAddr());
						
						
						UserAccount adminUserAccount = accountDao
								.selectAdminAccount();
						adminUserAccount
								.setAllMoney(adminUserAccount.getAllMoney()
										.add(borrowRedeem.getRedeemFee()));
						adminUserAccount.setAvailableMoney(adminUserAccount
								.getAvailableMoney().add(
										borrowRedeem.getRedeemFee()));
						bool = accountService
								.updateByPrimaryKeySelective(adminUserAccount);
						adminUserAccount.setWaitRepayCapital(adminUserAccount.getWaitRepayCapital().subtract(stayingCapital));
						adminUserAccount.setWaitRepayInterest(adminUserAccount.getWaitRepayInterest().subtract(repossessedInterest));
						if (bool) {
							//添加管理员账户信息到日志表
							
							accountLogService.add(adminUserAccount, AccountLog.TRADE_CODE_BORROW_REDEEM_FEE, borrowRedeem.getRedeemFee()
									,new BigDecimal(0), borrowTender.getUserId(), 
									"向用户["+userAccount.getUser().getUserAccount()+"]收取赎回手续费["+borrowRedeem.getRedeemFee()+"]元"
									,RequestUtils.getIpAddr());
							
							param.put("stayingCapital", stayingCapital);
							param.put("lastDay", lastDay);
							
							//添加积分
							userCreditService.addUserCredit(CreditType.REDEEM_YES, receiveUserId, sendUserId);
							creditLogService.addCreditLog(CreditType.REDEEM_YES,receiveUserId, sendUserId);
							
							
						} else {
							throw new RuntimeException("修改管理员资金数据错误！！！");
						}
					} else {
						throw new RuntimeException("回款失败，请稍后再试！！！");
					}
				}else{
					throw new RuntimeException("扣除手续费失败，请稍后再试！！！");
				}
				} else {
					throw new RuntimeException("修改标信息失败！！！");
				}
			} else {
				throw new RuntimeException("修改投标详细状态失败！！！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return bool;
	}

	@Override
	public List<BorrowRedeem> getByTenderId(Integer tenderId) {
		return this.borrowRedeemDao.select("getByTenderId", tenderId);
	}

}
