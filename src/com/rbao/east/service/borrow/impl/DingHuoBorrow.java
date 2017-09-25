package com.rbao.east.service.borrow.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.StringUtil;

/**
 * 信用标
 * @author Liutq
 *
 */
@Service("dingHuoTong")
@Transactional
public class DingHuoBorrow extends BorrowTypeHandleServiceImpl {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 判断可用信用额度
	 */
	@Override
	public ServiceResult withinAllowBorrowMoney(UserAccount borrowAcc,Borrow borrow) {
		
		return new ServiceResult(ServiceResult.SUCCESS);
	}
	/**
	 * 保存发标之前，填充borrow
	 * @param borrow
	 * @throws  
	 */
	protected void fullInBorrowOfSaveBorrow(Borrow borrow) {
		borrow.setBorrowStatus(Borrow.STATUS_FIRSTAUDIT_YES);
		super.fullInBorrowOfSaveBorrow(borrow);
	}
	/**
	 * 投标时填充对象
	 * @param tender
	 * @param borrow
	 */
	protected void fullInTenderAndBorrowOfSaveTender(BorrowTender tender,Borrow borrow){
		super.fullInTenderAndBorrowOfSaveTender(tender, borrow);
		if(CompareUtils.equals(borrow.getTenderSum(), borrow.getBorrowSum())){ 
			borrow.setBorrowStatus(Borrow.STATUS_REPLYING);//投满即改为收益中
		}
	}
	/**
	 * 投标
	 */
	public ServiceResult saveTender(BorrowTender tender,Map<String, String> params) {
		try {
			ServiceResult res = super.saveTender(tender,params);
			if(!res.isSuccessed()){
				return res;
			}
			
			Borrow borrow = borrowDao.selectEntity("selectByPrimaryKeyForUpdate",tender.getBorrowId());	
			UserAccount borrowAcc = accountDao.selectByUserIdForUpdate(borrow.getUserId()); //发标人账户
			UserAccount tenderAcc = accountDao.selectByUserId(tender.getUserId()); //投标人账户
			User tenderUser = super.userDao.selectByPrimaryKey(tender.getUserId());
			List<BorrowTender> tenderList = new ArrayList<BorrowTender>(); //投标人列表，在这里只有一条
			tenderList.add(tender);
			//创建代收待还
			createRepayAndRepossessed(borrow,tenderList); 
			//重新设置代收利息以免出现误差
			updateTenderInterestByRepossessed(tender);
			//处理投标奖励
			tenderRewardToTenderAccount(borrow,tender,tenderAcc,borrowAcc); 
			//处理续投奖励
			continueRewardToTenderAccount(tender,tenderAcc,borrow); 
			//投标人的钱转入发标人账户
			tenderToBorrowAccount(tenderList,borrowAcc,borrow);
			//扣除借款手续费
			deductLoanFee(borrowAcc,borrow,tender.getEffectiveAmount()); 
			//修改标状态
	//		borrowDao.updateByPrimaryKeySelective(borrow); 
			//生成借款协议
			createBorrowAgreement(borrow,tender.getId()); 
			//投标人发送消息，添加积分
			sendMsg(tender.getUserId(),"扣除冻结款","标["+borrow.getBorrowTitle()+"]投标成功，扣除冻结款["+tender.getEffectiveAmount()+"]元。",Notice.LOAN_YES_ACCOUNT);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tenderMoney", tender.getEffectiveAmount());
			map.put("period", borrow.getBorrowTimeLimit());
			map.put("isDay", borrow.getIsDay());
			userCreditService.addUserCreditAndLog(CreditType.INVEST_SUCCESS, tender.getUserId(),map);
			//发标人发送消息，添加积分
			sendMsg(borrow.getUserId(),"["+borrow.getBorrowTitle()+"]投标成功","["+tenderUser.getUserAccount()+"]投标成功，投标金额["+tender.getEffectiveAmount()+"]元",Notice.BORROW_REVIEW_YES);
			if(borrow.getTenderTimes().intValue() == 1){
				userCreditService.addUserCreditAndLog(CreditType.BORROW_YES, borrow.getUserId());
			}			
		} catch (Exception e) {
			
			log.error("save tender error:"+tender,e);
			throw new RuntimeException("save tender error:"+tender);
		}
		return new ServiceResult(ServiceResult.SUCCESS,"投标成功");
	}
	/**
	 * 借款协议，构建投标人列表
	 * @param borrow
	 * @param bType
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public List<String[]> buildAgreementTenderList(Borrow borrow,Integer tenderUserId){
		Map m = new HashMap();
		m.put("borrowId", borrow.getId());
		m.put("inStatus", new Integer[]{BorrowTender.STATUS_OVERDUE,BorrowTender.STATUS_REPAYING});
		List<Map> tenderList = this.tenderDao.selects("selectAgreePdfListOfZhuan", m);  
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{ "出借人(id)", "借款金额", "借款期限","年利率", "借款开始日", "借款到期日", "截止还款日", "还款本息" });
		for(Map tenderMap : tenderList){
			Integer userId=(Integer) tenderMap.get("userId");
			String tenderUser = (String) tenderMap.get("usrName");
			if(!userId.toString().equals(tenderUserId.toString())){
				tenderUser=tenderUser.substring(0,3)+"***";
			}
			BigDecimal capital = (BigDecimal) tenderMap.get("amount");
			Date beginTime = (Date) tenderMap.get("beginTime");
			Date endTime = (Date) tenderMap.get("endTime");
			BigDecimal totalAmount = (BigDecimal) tenderMap.get("totalAmount");
			//期限
			String periodStr = borrow.getBorrowTimeLimit()+"个月";
			if(borrow.getIsDay().equals(Borrow.IS_DAY_Y)){
				periodStr = borrow.getBorrowTimeLimit()+"天";
			}
			//开始日
			list.add(new String[]{tenderUser
								 ,StringUtil.toString(capital)
								 ,periodStr
								 ,borrow.getAnnualInterestRate()+"%"
								 ,DateUtils.formatDate(beginTime)
								 ,DateUtils.formatDate(endTime)
								 ,DateUtils.formatDate(endTime)
								 ,StringUtil.toString(totalAmount)});
		}
		
		
		return list;
	}

}
