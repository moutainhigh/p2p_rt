package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.RechargeCashDao;
import com.rbao.east.entity.RechargeCash;
import com.rbao.east.service.RechargeCashService;
@Service
@Transactional
public class RechargeCashServiceImpl implements RechargeCashService{
	
	@Autowired
	private RechargeCashDao rechargeCashDao;

	@Override
	public Boolean deleteByPrimaryKey(Integer id) {
		return this.rechargeCashDao.deleteByPrimaryKey(id);
	}

	@Override
	public Boolean insert(RechargeCash record) {
		return this.rechargeCashDao.insert("insert", record);
	}

	@Override
	public Boolean insertSelective(RechargeCash record) {
		return this.rechargeCashDao.insertSelective(record);
	}

	@Override
	public RechargeCash selectByPrimaryKey(Integer id) {
		return this.rechargeCashDao.selectByPrimaryKey(id);
	}

	@Override
	public Boolean updateByPrimaryKeySelective(RechargeCash record) {
		return this.rechargeCashDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Boolean updateByPrimaryKey(RechargeCash record) {
		return this.rechargeCashDao.update("updateByPrimaryKey", record);
	}

	@Override
	public List<RechargeCash> selectByRechargeId(Integer id) {
		return this.rechargeCashDao.select("selectByRechargeId", id);
	}

	@Override
	public List<RechargeCash> selectByCashId(Integer id) {
		return this.rechargeCashDao.select("selectByCashId", id);
	}

	@Override
	public BigDecimal selectSumByRechargeId(Integer id) {
		return (BigDecimal) this.rechargeCashDao.selects("selectSumByRechargeId",id).get(0);
	}

	@Override
	public void deleteByCashId(Integer id) {
		this.rechargeCashDao.deletes("deleteByCashId", id);
	}

	@Override
	public void addRechgCash(Map<Integer, BigDecimal> rechgCashMap,
			Integer cashId, Integer userId) {
		Iterator<Entry<Integer,BigDecimal>> it = rechgCashMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<Integer,BigDecimal> entry = it.next();
			
			RechargeCash rc = new RechargeCash();
			rc.setCashId(cashId);
			rc.setRechargeId(entry.getKey());
			rc.setCashFeeAmount(entry.getValue());
			rc.setCreateTime(new Date());
			rc.setUserId(userId);
			
			this.insertSelective(rc);
		}
	}

}
