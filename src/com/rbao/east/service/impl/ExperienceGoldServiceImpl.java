package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.ExperienceGoldDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.ExperienceGold;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.ExperienceGoldService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.UserAccountService;

/**
 */
@Service
@Transactional
public class ExperienceGoldServiceImpl implements ExperienceGoldService {

	
	@Autowired
	ExperienceGoldDao experienceGoldDao;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	MessageCenterService messageCenterService;
	@Autowired
	private SysConfigParamService sysConfigParamService;
	
	@Autowired
	private BorrowTenderService borrowTenderService;
	@Override
	public ExperienceGold getExperienceGoldByUserId(Integer userId) {
		ExperienceGold experienceGold = experienceGoldDao.selectEntity("getExperienceGoldByUserId", userId);
		return experienceGold;
	}
	@Override
	public boolean saveOrUpdate(ExperienceGold experienceGold2) {
		return experienceGoldDao.saveOrUpdate(experienceGold2);
		
	}
	@Override
	public List<ExperienceGold> getExperienceGoldListByStatus() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		List selects = experienceGoldDao.selects("getExperienceGoldListByStatus", map);
		return selects;
	}
	@Override
	public void addExperienceGold(ExperienceGold experienceGold) {
		
		Integer effectiveTenderCount= borrowTenderService.getEffectiveTenderCount(experienceGold.getUserid());
		/*Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", experienceGold.getUserid());
    	params.put("tenderStatus", new Integer [] {BorrowTender.STATUS_OVERDUE,BorrowTender.STATUS_REPAYING,BorrowTender.STATUS_REPAYED});
    	BigDecimal tenderMoney=borrowTenderService.sumTenderMoney(params);
		
    	int compareTo = tenderMoney.compareTo(new BigDecimal(1000));*/
    	
		//if(compareTo != -1){
		if(effectiveTenderCount!=0){
			userAccountService.addMoney(experienceGold.getUserid(), experienceGold.getExperienceGoldInterest(), AccountLog.TRADE_CODE_EXPERIENCE_GOLD);
			
			Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
			String deductionMoneyAmount = sysConfigParamMap.get("experienceGold_money");
			messageCenterService.send(experienceGold.getUserid(), "体验金利息", "已经发放"+deductionMoneyAmount+"体验金利息"+experienceGold.getExperienceGoldInterest()+"元.", Notice.ExperienceGoid);
			
			experienceGold.setExperienceGoldStatus(1);
			experienceGoldDao.saveOrUpdate(experienceGold);
		}
		
		
	}
	@Override
	public ExperienceGold getExperienceGoldByParam(Map<String, String> paramsMap) {
		ExperienceGold experienceGold = experienceGoldDao.selectEntity("getExperienceGoldByParam", paramsMap);
		return experienceGold;
	}
	
	
	
	
}
