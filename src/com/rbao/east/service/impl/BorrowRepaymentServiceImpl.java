package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.dao.BorrowRepaymentDao;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTypeHandleService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;

@Service
@Transactional
public class BorrowRepaymentServiceImpl implements BorrowRepaymentService{

	private static Logger logger = LoggerFactory.getLogger(BorrowRepaymentServiceImpl.class);
	
	@Autowired
	private BorrowRepaymentDao borrowRepaymentDao;
	
	@Autowired
	private BorrowRepossessedService borrowRepossessedService;
	
	@Autowired
	private BorrowService borrowQueryService; 
	
	@Autowired
	private BorrowTypeService borrowTypeService;
	
	@Autowired
	private MessageCenterService messageCenterService;
	
	@Autowired
	private UserCreditService userCreditService;
	
	@Autowired
	private CreditLogService creditLogService;
	
	
	@Override
	public boolean saveBorrowRepayment(BorrowRepayment repayment) {
		return borrowRepaymentDao.saveOrUpdate(repayment);
	}

	@Override
	public BorrowRepayment getBorrowRepaymentById(Integer id) {
		return borrowRepaymentDao.selectByPrimaryKey(id);
	}

	@Override
	public void borrowOverdueDispose(BorrowRepayment borrowRepayment) {

		try {
			int lateDays = DateUtils.daysBetween(
					borrowRepayment.getRepaymentTime(), new Date());
			// 逾期天数
			BigDecimal lateInterest = null;// 滞纳金
			BigDecimal allLateInterest = new BigDecimal(0);
			List<BorrowRepossessed> borrowRepossessedList=borrowRepayment.getBorrowOverdueRepossessedlist();

			for (BorrowRepossessed borrowRepossessed : borrowRepossessedList) {

				lateInterest = CalculateProcess.getLateInterest(
						borrowRepossessed, lateDays);
				allLateInterest = allLateInterest.add(lateInterest);
				borrowRepossessed.setLateDays(lateDays);
				borrowRepossessed.setLateInterest(lateInterest);
				borrowRepossessed.setPrepareAmount(borrowRepossessed
						.getRepossessedInterest()
						.add(borrowRepossessed.getRepossessedCapital())
						.add(borrowRepossessed.getLateInterest()));
				borrowRepossessed
						.setRepossessedStatus(BorrowRepossessed.STATUS_OVERDUE);
				borrowRepossessedService
						.updateBorrowRepossessed(borrowRepossessed);

			}
			borrowRepayment.setLateInterest(allLateInterest);
			borrowRepayment.setLateDays(lateDays);
			borrowRepayment.setRepaymentAmount(borrowRepayment
					.getRepaymentInterest().add(
							borrowRepayment.getRepaymentPrincipal().add(
									borrowRepayment.getLateInterest())));
			borrowRepayment
					.setRepaymentStatus(BorrowRepayment.REPAYMENT_STATUS_OVERDUE);
			borrowRepaymentDao.updateByPrimaryKeySelective(borrowRepayment);

			Borrow borrow = borrowQueryService.getBorrowById(borrowRepayment
					.getBorrowId());
			borrow.setBorrowStatus(Borrow.STATUS_OVERDUE);
			borrowQueryService.updateBorrow(borrow);

			// 向用户发送消息
			MessageCenter center = new MessageCenter();
			center.setMessageContent("标【" + borrow.getBorrowTitle() + "】已逾期。");
			center.setMessageSendIp(RequestUtils.getIpAddr());
			center.setReceiveUserId(borrow.getUserId());
			center.setMessageTitle("标【" + borrow.getBorrowTitle() + "】已逾期。");
			center.setSendUserId(Constants.ADMIN_USER_ID);
			messageCenterService.send(center, Notice.BORROW_OVERDUE);

			// 添加积分
			userCreditService.addUserCredit(CreditType.BORROW_OVERDUE,
					borrow.getUserId(), Constants.ADMIN_USER_ID);
			creditLogService.addCreditLog(CreditType.BORROW_OVERDUE,
					borrow.getUserId(), Constants.ADMIN_USER_ID);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("标逾期处理失败", e);
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	@Override
	public boolean updateBorrowRepayment(BorrowRepayment repayment) {
		// TODO Auto-generated method stub
		return borrowRepaymentDao.updateByPrimaryKeySelective(repayment);
	}

	@Override
	public PageModel selectBorrowRepayment(Map<String, String> params) {
		PageModel model =  borrowRepaymentDao.getPage("selectBorrowRepayment", "countBorrowRepayment", params);
		new DesEncrypt().encryptInList(model.getList(),new String[]{"repayId"}); //repayId加密
		new DesEncrypt().encryptInList(model.getList(),new String[]{"bId"}); //repayId加密
		return model;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BorrowRepayment> findAutoBorrowRepayment() {
		Map m = new HashMap();
		m.put("onlySelfType", BorrowType.REPAY_TYPE_SELF);
		m.put("inStatus", new Integer[]{BorrowRepayment.REPAYMENT_STATUS_FAILD,
							BorrowRepayment.REPAYMENT_STATUS_OVERDUE,
							BorrowRepayment.REPAYMENT_STATUS_REPAYING});
		return borrowRepaymentDao.select("findAutoBorrowRepayment", m);
	}

	@Override
	public boolean autoBorrowRepaymentReturnMoney(
			BorrowRepayment borrowRepayment) {
		boolean bool=false;
		try {
			Borrow borrow = this.borrowQueryService.getBorrowById(borrowRepayment.getBorrowId());
			BorrowType borrowType = borrowTypeService.getBorrowTypeById(borrow.getBidKind());
			//获取标种对应的service实现类
			BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class, borrowType.getDealService());
			ServiceResult rest = null;
			borrowRepayment.setRepayedUserId(borrow.getUserId());
			rest = borrowService.repay(borrowRepayment);
			if(rest.isSuccessed()){
				bool=true;
			}else{
				throw new RuntimeException(rest.getMsg());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return bool;
	}

	@Override
	public List<BorrowRepayment> getWaitRepaymentByUserId(Integer userId) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("userId", userId.toString());
		return borrowRepaymentDao.select("selectWaitRepayment", params);
	}

	@Override
	public List<BorrowRepayment> getRepaymentByBorrowId(Integer BorrowId) {
		// TODO Auto-generated method stub
		return borrowRepaymentDao.select("getRepaymentByBorrowId", BorrowId);
	}

	@Override
	public Integer getRepayingCount(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return (Integer) borrowRepaymentDao.getObject("getRepayingCount", param);
	}

	@Override
	public Integer getForwardRepayment() {
		return borrowRepaymentDao.getTotalCount("selectForwardRepayment", null);
	}

	@Override
	public PageModel getRepayments(Map<String, String> paramsMap) {
		PageModel page=borrowRepaymentDao.getPage("selectRepayments", "countRepayments", paramsMap);
		new DesEncrypt().encryptInList(page.getList(),new String[]{"id"}); //id加密
		return page;
	}
	
	/**
	 * 垫付还款统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public PageModel reparmentSummary(Map<String, String> map) {
		return borrowRepaymentDao.reparmentSummary(map);
	}
	
	/**
	 * 垫付还款统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public List<Map<String, Object>> reparmentAllSummary(Map<String, String> map) {
		return borrowRepaymentDao.reparmentAllSummary(map);
	}
	
}
