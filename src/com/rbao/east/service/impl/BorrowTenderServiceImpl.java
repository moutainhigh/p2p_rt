package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.BorrowTenderDao;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.utils.DesEncrypt;

@Service
@Transactional
public class BorrowTenderServiceImpl implements BorrowTenderService {

	@Autowired
	BorrowTenderDao tenderDao;
	
	@Override
	public List<BorrowTender> selectByBorrowId(Integer borrowId) {
		
		return tenderDao.select("selectByBorrowId", borrowId);
	}

	@Override
	public boolean updateByBorrowTender(BorrowTender borrowTender) {
		return tenderDao.updateByPrimaryKeySelective(borrowTender);
	}

	@Override
	public PageModel shwoBorrowTenderInfoByPage(Map<String, String> param) {
		return tenderDao.getPage("shwoBorrowTenderInfoByPage", "shwoBorrowTenderInfoCountByPage", param);
	}

	@Override
	public List selectEff(Map<String, String> param) {
		return tenderDao.selects("selectEff", param);
	}

	@Override
	public PageModel selectInvestByUserId(Map<String, String> params) {
		 
			PageModel model=tenderDao.getPage("selectInvestByUserId", "selectInvestByUserIdCount", params);
			
			//new DesEncrypt().encryptInList(model.getList(),new String[]{"userId"}); //repayId加密
				return model;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int selectRepayedCountByUserId(Integer userId){
		Map m = new HashMap();
		m.put("userId", userId);
	//	m.put("status", BorrowTender.STATUS_REPAYED);
		return tenderDao.getTotalCount("queryCountByUserStatus", m);
	}

	@Override
	public BorrowTender selectBorrowTenderByBorrowTenderId(Integer pk) {
		return tenderDao.selectByPrimaryKey(pk);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageModel selectInvestRedeem(Map<String, String> params) {
		Map m = new HashMap();
		m.putAll(params);
		if(params.containsKey("redeemStatus")){
			String queryStatus = params.get("redeemStatus");
			if(queryStatus.equals("1")){ //可赎回
				m.put("tenderStatus", new Integer[]{BorrowTender.STATUS_REPAYING,BorrowTender.STATUS_OVERDUE});
				m.put("redeemStatus", null);//将redeemStatus清空
			}else if(queryStatus.equals("2") || queryStatus.equals("3")){
				m.put("tmpRedeemStatus", queryStatus);
			}
		}
		 
			PageModel model=tenderDao.getPage("selectInvestRedeem", "selectInvestRedeemCount", m);
			new DesEncrypt().encryptInList(model.getList(),new String[]{"bId"}); //repayId加密
				return model;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageModel queryInvestRedeem(Map<String, String> params) {
		Map m = new HashMap();
		m.putAll(params);
		if(params.containsKey("transferStatus")){
			String queryStatus = params.get("transferStatus");
			if(queryStatus.equals("1")){ //可转让
				m.put("tenderStatus", new Integer[]{BorrowTender.STATUS_REPAYING,BorrowTender.STATUS_OVERDUE});
			}else if(queryStatus.equals("2") || queryStatus.equals("3")){
				m.put("tmpTransferStatus", queryStatus);
			}
		}
		PageModel model=tenderDao.getPage("queryInvestRedeem", "queryInvestRedeemCount", m);
		
//		new DesEncrypt().encryptInList(model.getList(),new String[]{"bId"}); //repayId加密
		for (Map map : (List<Map>)model.getList()) {
			m.put("ebId", map.get("bId"));
		}
		
		return model;
	}
	@Override
	public boolean saveBorrowTender(BorrowTender borrowTender) {
		return tenderDao.insertSelective(borrowTender);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BigDecimal selectSumByBorrowIdAndUserId(Integer borrowId,
			Integer userId) {
		Map m = new HashMap();
		m.put("borrowId", borrowId); 
		m.put("userId", userId);
		List<BorrowTender> list = tenderDao.select("selectByBorrowIdAndUserId", m);
		BigDecimal money = new BigDecimal(0);
		for(BorrowTender tender : list){
			money = money.add(tender.getEffectiveAmount());
		}
		return money;
	}

	@Override
	public BigDecimal getEffectiveAmountByDay(Map<String, String> params) {
		String reusult = (String) tenderDao.getObject("getEffectiveAmountByDay", params);
		if (reusult == null) {
			return new BigDecimal(0.00);
		}
		return new BigDecimal(reusult);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getTenderUserIds(Map<String, String> params) {
		return tenderDao.selects("getTenderUserIds", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getEffectiveAmount(
			Map<String, String> params) {
		return tenderDao.selects("getEffectiveAmount", params);
	}

	@Override
	public List getbyUserId(Integer userId) {
		return tenderDao.selects("getTenderAllMoneyAndTenderNum",userId);
	}

	@Override
	public Object getTenderSumByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return tenderDao.getObject("getTenderSumByUserId", userId);
	}

	@Override
	public PageModel getBorrowRepayingPage(Map<String, String> paramsMap) {
		PageModel model =tenderDao.getPage("selectRepaying", "countRepaying", paramsMap);
		new DesEncrypt().encryptInList(model.getList(),new String[]{"id"}); //repayId加密
		 	return	model;
				
	}

	@Override
	public Integer getTenderUsers(Integer borrowId) {
		return tenderDao.getTotalCount("selectTenderUsers", borrowId);
	}

	@Override
	public String getAgreementPathByBorrowIdAndUserId(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return (String) tenderDao.getObject("getAgreementPathByBorrowIdAndUserId", param);
	}

	@Override
	public BigDecimal queryTenderMoney(Map<String, Object> params) {
		return (BigDecimal) tenderDao.selects("queryTenderMoney", params).get(0);
	}
	@Override
	public BigDecimal queryTenderMoneyByKind(Map<String, Object> params) {
		return (BigDecimal) tenderDao.selects("queryTenderMoneyByKind", params).get(0);
	}

	@Override
	public Integer selectTenders() {
		return tenderDao.getTotalCount("selectTenders", null);
	}

	@Override
	public List<Map<String, Object>> toTenderRecordExcel(
			Map<String, String> params) {
		return tenderDao.selects("shwoBorrowTenderInfoByPage", params);
	}

	@Override
	public BigDecimal sumTenderMoney(Map<String, Object> params) {
		return (BigDecimal) tenderDao.selects("sumTenderMoney", params).get(0);
	}
	
	/**
	 * 成交数据统计
	 * @param date
	 * 			年份
	 * @return
	 */
	@Override
	public List<Map<String, Object>> bargainSummary(String date) {
		return tenderDao.bargainSummary(date);
	}
	
	/**
	 * 投资统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public PageModel investSummary(Map<String, String> map) {
		return tenderDao.investSummary(map);
	}
	
	/**
	 * 投资统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public List<Map<String, Object>> investAllSummary(Map<String, String> map) {
		return tenderDao.investAllSummary(map);
	}
	
	@Override
	public BigDecimal selectSumByMap(Map m){
		return (BigDecimal) tenderDao.getObject("selectSumByMap", m);
	}
	@Override
	public Integer selectCountByMap(Map m){
		return (Integer) tenderDao.getObject("selectCountByMap", m);
	}

	@Override
	public List<Map> getUserTenderRank(Map<String, Object> param) {
		return this.tenderDao.selects("getUserTenderRank", param);
	}

	@Override
	public List getBorrowDetailByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return tenderDao.selects("getBorrowDetailByUserId",userId);
	}

	@Override
	public Integer selectFirstBuy(Map map) {
		Integer count = this.tenderDao.getTotalCount("selectFirstBuy", map);
		return count;
	}

	@Override
	public Integer getEffectiveTenderCount(Integer userid) {
		Integer count = this.tenderDao.getTotalCount("selectEffectiveTenderCount", userid);
		return count;
	}

	@Override
	public List<BorrowTender> queryTenderByBorrowidAndUserId(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tenderDao.select("selectByBorrowIdAndUserId", params);
	}
}
