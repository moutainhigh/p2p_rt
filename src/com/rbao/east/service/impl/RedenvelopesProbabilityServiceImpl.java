package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.RedenvelopesProbabilityDao;
import com.rbao.east.entity.RedenvelopesProbability;
import com.rbao.east.service.RedenvelopesProbabilityService;
import com.rbao.east.utils.CompareUtils;



@Service
@Transactional
public class RedenvelopesProbabilityServiceImpl implements RedenvelopesProbabilityService{

	@Autowired
	private RedenvelopesProbabilityDao redenvelopesProbabilityDao;
	
	@Override
	public boolean save(List<RedenvelopesProbability> probability) {
		//删除
		this.redenvelopesProbabilityDao.deletes("deleteAll", null);
		//增加
		for(RedenvelopesProbability pro : probability){
			this.redenvelopesProbabilityDao.insertSelective(pro);
		}
		return true;
		
	//	return redenvelopesProbabilityDao.saveOrUpdate(probability);
	}

	@Override
	public List<RedenvelopesProbability> getAll() {
		return redenvelopesProbabilityDao.selectAll();
	}

	/**
	 * 获取RedenvelopesProbability对象
	 */
	@Override
	public RedenvelopesProbability getRedenvelopesProbability() {
		List<RedenvelopesProbability> list=this.getAll();
		if(list.size()==0){
			return new RedenvelopesProbability();
		}else{
			return list.get(0);
		}
	}
	@Override
	public BigDecimal calProbaMultiMoney(BigDecimal money){
		
		return money.multiply(getRandomMulti(money))
				.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 红包倍率
	 * @return
	 */
	public BigDecimal getRandomMulti(){
		List<RedenvelopesProbability> proList = getAll();
		
		int  sum = 0;//总概率精度
		for(RedenvelopesProbability pro : proList){
			sum += pro.getProbability().intValue();
		}
		//随机通过概率获取倍率
		for(RedenvelopesProbability pro : proList){//概率数组循环 
			int randomNum = new Random().nextInt(sum);//随机生成1到sum的整数
			if(randomNum <= pro.getProbability().intValue() ){//中奖
				return pro.getMulti();
			}else{
				sum -=pro.getProbability().intValue();
			}
		}
		return new BigDecimal(1); 
	}
	/**
	 * 新红包倍率
	 * @param money
	 * @return
	 */
	public BigDecimal getRandomMulti(BigDecimal money){
		List<RedenvelopesProbability> proList = getAll();
		int randomNum = new Random().nextInt(100);
		BigDecimal result = new BigDecimal(1);
		for(RedenvelopesProbability pro : proList){
			if(pro.getMax()==null&&pro.getMin()==null){
				if(pro.getProbability().intValue()>=randomNum){
					result = pro.getMulti();
				}
			}else{
//				max=null min<money
				if(pro.getMax()==null&&
					CompareUtils.greaterEquals(money,pro.getMin())){
					if(pro.getProbability().intValue()>=randomNum){
						result = pro.getMulti();
					}
				}else
//					min<money<max
					if(CompareUtils.greaterEquals(money,pro.getMin())
							&&CompareUtils.greaterEquals(pro.getMax(),money)){
						if(pro.getProbability().intValue()>=randomNum){
							result = pro.getMulti();
						}
					}
//				min=null max>money
				if(pro.getMin()==null&&
					CompareUtils.greaterEquals(pro.getMax(),money)){
					if(pro.getProbability().intValue()>=randomNum){
						result = pro.getMulti();
					}
				}
			}
			result = new BigDecimal(1);
		}
		return result;
	}
}
