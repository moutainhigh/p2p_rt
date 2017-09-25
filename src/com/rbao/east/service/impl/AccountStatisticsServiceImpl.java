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

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AccountStatisticsDao;
import com.rbao.east.entity.AccountStatistics;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AccountStatisticsService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.DateUtils;

@Service
@Transactional
public class AccountStatisticsServiceImpl implements AccountStatisticsService {

	private static Logger logger = LoggerFactory
			.getLogger(AccountStatisticsServiceImpl.class);

	@Autowired
	private AccountStatisticsDao accountStatisticsDao;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private UserService userService;

	@Autowired
	private BorrowTenderService tenderService;

	@Override
	public boolean saveAccountStatistics(AccountStatistics statistics) {
		return accountStatisticsDao.saveOrUpdate(statistics);
	}

	@Override
	public PageModel getAccountStatistics(Map<String, String> paramsMap) {
		return accountStatisticsDao.getPage("selectByEntity",
				"selectTotalCount", paramsMap);
	}

	@Override
	public AccountStatistics getStatisticsById(Integer id) {
		return accountStatisticsDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteStatisticsById(Integer id) {
		return accountStatisticsDao.deleteByPrimaryKey(id);
	}

	@Override
	public PageModel getPageAccountStatistics(Map<String, String> params) {
		return accountStatisticsDao.getPage("getPageAccountStatistics",
				"getTotalCount", params);
	}

	public void copyEffectiveAmount() {
		Date preDate = DateUtils.addDay(new Date(), -1);
		String preDateStr = DateUtils.formatDate("yyyy-MM-dd", preDate);
		Map<String, String> dateMap = new HashMap<String, String>();
		dateMap.put("countDate", preDateStr);
		List<Integer> userIds = tenderService.getTenderUserIds(dateMap);

		List<Map<String, String>> listMaps = tenderService
				.getEffectiveAmount(dateMap);
		Map<Object, Object> newMap = new HashMap<Object, Object>();
		for (Map<String, String> map : listMaps) {
			newMap.put(map.get("userId"), map.get("amount"));
		}

		for (Integer uid : userIds) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", "" + uid);
			map.put("countDate", preDateStr);
			AccountStatistics asp = this.selectByUserId(map);
			asp.setTenderAmount((BigDecimal) newMap.get(uid));
			accountStatisticsDao.saveOrUpdate(asp);
		}

	}

	@Override
	public boolean autoCopyDataByDay() {
		Date preDate = DateUtils.addDay(new Date(), -1);// 统计前一天数据
		List<UserAccount> userAccounts = userAccountService
				.getUserAccountList();

		try {
			for (UserAccount ua : userAccounts) {
				AccountStatistics as = new AccountStatistics();
				System.out.println("userid------>"+ua.getUserId());
				as.setStatisticsAddtime(preDate);
				User user = userService.getById(ua.getUserId());
				
				//过滤掉administrator
				if (user == null || user.getId() == 1) {//如果数据不一致,跳出本次循环
					logger.error("不存在此id对应的User对象!",ua.getUserId());
					continue;
				}
				
				as.setUserId(user.getId());
				as.setUserRealname(user.getUserRealname());
				as.setUserName(user.getUserAccount());

				as.setAllMoney(ua.getAllMoney());
				as.setAvailableMoney(ua.getAvailableMoney());
				as.setUnavailableMoney(ua.getUnavailableMoney());

				as.setWaitRepayCapital(ua.getWaitRepayCapital());
				as.setWaitRepayInterest(ua.getWaitRepayInterest());
				as.setWaitRepossessedCapital(ua.getWaitRepossessedCapital());
				as.setWaitRepossessedInterest(ua.getWaitRepossessedInterest());

				as.setPayReward(ua.getPayReward());
				as.setPayInterest(ua.getPayInterest());

				as.setGetReward(ua.getGetReward());
				as.setGetInterest(ua.getGetInterest());
				// TODO ? 净总额
				as.setWorthMoney(ua.getAllMoney()
						.subtract(ua.getWaitRepayCapital())
						.subtract(ua.getWaitRepayInterest()));

				Map<String, String> emap = new HashMap<String, String>();
				emap.put("userId", "" + ua.getUserId());
				emap.put("countDate",
						DateUtils.formatDate("yyyy-MM-dd", preDate));
				as.setTenderAmount(tenderService.getEffectiveAmountByDay(emap));

				accountStatisticsDao.insert("insertSelective", as);
			}

			// TODO
			// copyEffectiveAmount();//将tender表统计数据写入
			// System.out.println("---copy---success---");
			return true;
		} catch (Exception e) {
			
			logger.error("复制数据异常");
			return false;
		}

	}

	@Override
	public AccountStatistics selectByUserId(Map<String, String> params) {
		return accountStatisticsDao.selectEntity("selectByUserId", params);
	}

	@Override
	public List<AccountStatistics> getAllAccountStatistics(
			Map<String, String> params) {
		return accountStatisticsDao.select("getAllAccountStatistics", params);
	}

	@Override
	public BigDecimal getAllInterestAndReward(Map<String, Object> param) {
		Object object=accountStatisticsDao.getObject("getAllInterestAndReward", param);
		if(null!=object){
			return new BigDecimal(object.toString());
		}else{
			return new BigDecimal("0.00");
		}
	}

}
