package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.rbao.east.entity.AutotenderRules;



/**
 * 自动投标
 * @author xiangxiaoyan
 *
 */
public interface AutotenderRulesService {
	
	/**
	 * 删除自动投保记录
	 * @param userId
	 * @return
	 */
	public boolean deleteByUserId(Integer userId);
	

	//添加自动投资
	public boolean addAuto(AutotenderRules auto)throws DataAccessException;
	
	public List<AutotenderRules> getAutoQueue() ;
   //通过useid查找
	public AutotenderRules getByUserId(Integer userId);
    //更新
	public boolean updateAuto(AutotenderRules auto);
	
	/**
	 * 投标成功，重新排队
	 * @param userId
	 */
	public void tenderSuccess(Integer userId) ;

	
	/**
	 * 自动投标
	 */
	public void autoTenderRules(AutotenderRules autotenderRules);
	public Map getRank(Map m);
	//自动投标设置额度合计
	public BigDecimal getAllTenderMoney();
	
	//自动投标有效余额合计
	public BigDecimal getEffAutoMoney();
}
