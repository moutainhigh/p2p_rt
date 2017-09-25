package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.BorrowRepossessedDao;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.service.BorrowRepossessedService;

@Service
@Transactional
public class BorrowRepossessedServiceImpl implements BorrowRepossessedService{

	@Autowired
	private BorrowRepossessedDao borrowRepossessedDao;
	
	
	@Override
	public boolean updateBorrowRepossessed(BorrowRepossessed borrowRepossessed) {
		// TODO Auto-generated method stub
		return borrowRepossessedDao.updateByPrimaryKeySelective(borrowRepossessed);
	}


	@Override
	public List selectSum(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return borrowRepossessedDao.selects("selectSum", paramMap);
	}


	@Override
	public List<BorrowRepossessed> selectBorrowRepossessedByTenderId(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return borrowRepossessedDao.select("selectBorrowRepossessedByTenderId",param);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BorrowRepossessed> selectWaitRepossessByTender(Integer tenderId) {
		Map param = new HashMap();
		param.put("repossessedStatus", BorrowRepossessed.STATUS_REPOSSESSED_SUCC);
		param.put("tenderId", tenderId);
		return selectBorrowRepossessedByTenderId(param);
	} 


	@Override
	public List getBorrowRepossessedListByPkList(Map<String, Object> param) {
		
		return borrowRepossessedDao.selects("getBorrowRepossessedListByPkList", param);
	}


	@Override
	public BorrowRepossessed getBorrowRepossessedByPk(Integer pk) {
		// TODO Auto-generated method stub
		return borrowRepossessedDao.selectByPrimaryKey(pk);
	}

	@Override
	public List<BorrowRepossessed> getBorrowRepossessedListByRepossessedPkList(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return borrowRepossessedDao.select("getBorrowRepossessedListByRepossessedPkList", param);
	}


	@Override
	public boolean saveBorrowRepossessed(BorrowRepossessed borrowRepossessed) {
		// TODO Auto-generated method stub
		return borrowRepossessedDao.insertSelective(borrowRepossessed);
	}


	@Override
	public PageModel selectBorrowRepossessedByRepaymentId(
			Map<String, String> param) {
		// TODO Auto-generated method stub
		return borrowRepossessedDao.getPage("selectByEntity", "selectTotalCount", param);
	}


	@Override
	public BorrowRepossessed getborrowRepossessedByTransferId(Integer transferId) {
		// TODO Auto-generated method stub
		return borrowRepossessedDao.selectEntity("getborrowRepossessedByTransferId", transferId);
	}


	@Override
	public List<BorrowRepossessed> getBorrowRepWaitByUserId(Integer userId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId.toString());
		return borrowRepossessedDao.select("selectWait", params);
	}


	@Override
	public Object getPrepareAmountOrRepossessedInterestByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return borrowRepossessedDao.getObject("getPrepareAmountOrRepossessedInterestByUserId", userId);
	}


	@Override
	public PageModel getRepByStatusPage(Map<String, String> paramsMap) {
		 PageModel model =borrowRepossessedDao.getPage("selectRepByStatus", "countRepByStatus", paramsMap);
		 
//		new DesEncrypt().encryptInList(model.getList(),new String[]{"bId"}); //repayId加密
		for (Map map : (List<Map>)model.getList()) {
			map.put("ebId", map.get("bId"));
		}
		
		return model;
	}


	@Override
	public BigDecimal getCountMoney(Integer status) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("repossessedStatus", status);
		return (BigDecimal) borrowRepossessedDao.selects("selectCountMoney", params).get(0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> selectSumYesterday(Map<String,Object> map) {
		return (Map<String, Object>) borrowRepossessedDao.getObject("selectSumYesterday", map);
	}

}
