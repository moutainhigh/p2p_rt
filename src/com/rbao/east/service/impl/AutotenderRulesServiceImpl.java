package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.dao.AutotenderRulesDao;
import com.rbao.east.dao.BorrowDao;
import com.rbao.east.entity.AutotenderRules;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AutotenderRulesService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTypeHandleService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.OperatorLogService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;

@Service
public class AutotenderRulesServiceImpl implements AutotenderRulesService {

	@Autowired
	private AutotenderRulesDao autotenderRulesDao;
	@Autowired
	BorrowTenderService tenderService;
	@Autowired
	private BorrowDao borrowDao;

	@Autowired
	private UserAccountService accountService;

	@Autowired
	private BorrowTypeService borrowTypeService;

	@Autowired
	private OperatorLogService operatorLogService;

	@Override
	public boolean addAuto(AutotenderRules auto) throws DataAccessException {
		// TODO Auto-generated method stub
		auto.setQueueTime(new Date());
		return autotenderRulesDao.insertSelective(auto);
	}

	public List<AutotenderRules> getAutoQueue() {
		return autotenderRulesDao.select("getAutoQueue", null);
	}

	public void tenderSuccess(Integer userId) {
		AutotenderRules aTender = getByUserId(userId);

		aTender.setTenderTimes(aTender.getTenderTimes() + 1); // 投标次数+1
		aTender.setLastTenderTime(new Date());
		aTender.setQueueTime(new Date());

		this.autotenderRulesDao.updateByPrimaryKeySelective(aTender);
	}

	@Override
	public AutotenderRules getByUserId(Integer userId) {
		return autotenderRulesDao.selectEntity("getByUserId", userId);
	}

	@Override
	public boolean updateAuto(AutotenderRules auto) {
		auto.setQueueTime(new Date());
		return autotenderRulesDao.updateByPrimaryKeySelective(auto);
	}

	/**
	 * 自动投标
	 */
	@Override
	public synchronized void autoTenderRules(AutotenderRules autotenderRules) {
		try {
				ServiceResult result = new ServiceResult();
					List<Borrow> allowBorrow = borrowDao.select(
							"findAllowToBorrowListByBorrowStatus",
							autotenderRules);
					if(allowBorrow.size()>0){
						for (Borrow borrows : allowBorrow) {
							if(!StringUtils.isEmpty(borrows.getTenderPassword())){ //过滤掉定向标
								continue;
							}
							BigDecimal tendedMoney = tenderService.selectSumByBorrowIdAndUserId(borrows.getId(), autotenderRules.getUserId());
							if(CompareUtils.greaterThanZero(tendedMoney)){ //限定只能投一次
								continue;
							}
							UserAccount userAccount=accountService.getByUserId(autotenderRules.getUserId());
							
							//自动投标限制待收
//							if(null!=userAccount){
//								BigDecimal totalWaitRepossessed = userAccount.getWaitRepossessedInterest().add(userAccount.getWaitRepossessedCapital());
//								BigDecimal minWaitRepossess = borrows.getMinWaitRepossess();
//								if(minWaitRepossess.subtract(totalWaitRepossessed).longValue()>0){
//									continue;
//								}
//							}
							BigDecimal totalWaitRepossessed = userAccount.getWaitRepossessedInterest().add(userAccount.getWaitRepossessedCapital());
							BigDecimal minWaitRepossess = borrows.getMinWaitRepossess();
							if(minWaitRepossess.longValue()>0){
								if(totalWaitRepossessed.subtract(minWaitRepossess).longValue()<0){
									continue;
								}
							}
							
							BorrowTender tender = new BorrowTender();
							tender.setTenderAmount(autotenderRules.getTenderMoney().setScale(0, BigDecimal.ROUND_DOWN));
							if(CompareUtils.greaterThan(tender.getTenderAmount(),userAccount.getAvailableMoney())){ 
								//超过可用金额
								tender.setTenderAmount(userAccount.getAvailableMoney().setScale(0, BigDecimal.ROUND_DOWN));
							}
								tender.setBorrowId(borrows.getId());
								tender.setUserId(autotenderRules.getUserId());
								tender.setTenderAddip("127.0.0.1");
								tender.setTenderType(BorrowTender.TENDER_TYPE_AUTO);
								tender.setDeductionMoney(new BigDecimal(0));
								BorrowType borrowType = borrowTypeService.getBorrowTypeById(borrows.getBidKind());
								// 获取标种对应的service实现类
								BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class,
										borrowType.getDealService());
								if(borrowType.getCode().equals(Borrow.BORROW_TYPE_ZHUAN)){//流转标
									tender.setTenderCopies(tender.getTenderAmount().divide(borrows.getAmountPerCopies()).intValue());
								}
							//	ServiceResult sResult = borrowService.allowToTender(tender,null);
//								if(!sResult.isSuccessed()){
//									//throw new RuntimeException(sResult.getMsg());
//									continue;
//								}
								result = borrowService.saveTender(tender,null); // 保存投标
								if(result.isSuccessed()){
									autotenderRules.setQueueTime(new Date());
									autotenderRules.setTenderTimes(autotenderRules.getTenderTimes()+1);
									autotenderRules.setLastTenderTime(new Date());
									autotenderRulesDao.updateByPrimaryKeySelective(autotenderRules);
									// 添加日志
									OperatorLog operatorLog = new OperatorLog();
									operatorLog.setLogUserid(userAccount.getUser().getUserAccount());
									operatorLog.setOperatorTitle("投标保存");
									operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
									operatorLog.setOperatorParams(tender.toString());
									operatorLog.setOperatorReturn(result.getMsg());
									operatorLog.setOperatorStatus(Integer.parseInt(result.getCode()));
									operatorLog.setLinkUrl(RequestUtils.getIpAddr());
									operatorLogService.addFrontLog(operatorLog);
									break;
								}
							
						}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public BigDecimal getAllTenderMoney() {
		return (BigDecimal) autotenderRulesDao.selects("selectAllTenderMoney", null).get(0);
	}

	@Override
	public BigDecimal getEffAutoMoney() {
		return (BigDecimal) autotenderRulesDao.selects("selectEffAutoMoney", null).get(0);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Map getRank(Map m) {
		return (Map) this.autotenderRulesDao.selects("getRank", m).get(0);
	}

	@Override
	public boolean deleteByUserId(Integer userId) {
		return this.autotenderRulesDao.deletes("deleteByUserId", userId);
	}

}
