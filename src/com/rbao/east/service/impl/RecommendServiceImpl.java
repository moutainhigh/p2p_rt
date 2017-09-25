package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.RecommendDao;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.Recommend;
import com.rbao.east.entity.RecommendAccount;
import com.rbao.east.entity.RecommendReward;
import com.rbao.east.entity.User;
import com.rbao.east.service.RecommendAccountService;
import com.rbao.east.service.RecommendRewardService;
import com.rbao.east.service.RecommendService;
import com.rbao.east.service.SysFeesRateService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.GetDatabaseConn;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SysCacheUtils;

@Service
@Transactional
public class RecommendServiceImpl implements RecommendService{

	private static Logger logger = LoggerFactory.getLogger(RecommendServiceImpl.class);
	
	@Autowired
	private RecommendDao recommendDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private SysFeesRateService sysFeesRateService;
	@Autowired
	private RecommendAccountService recommendAccountService;
	@Autowired
	private RecommendRewardService recommendRewardService;
	@Autowired
	private UserService userService;
	
	@Override
	public PageModel getRecommends(Map<String, String> paramsMap) {
		return recommendDao.getPage("selectByEntity", "selectTotalCount", paramsMap);
	}

	@Override
	public boolean saveRecommend(Recommend recommend,BigDecimal reward) {
		if(CompareUtils.greaterThanZero(reward) &&
				(recommend.getRecommendStatus().equals(Recommend.RECOMMEND_STATUS_SUCC)||
						recommend.getRecommendStatus().equals(Recommend.ADD_MONEY_BY_RATE))){
			//推荐人加奖励
			userAccountService.addRecommendReward(recommend.getRecommendUserid(), reward);
			if(recommend.getRecommendStatus().equals(Recommend.ADD_MONEY_BY_RATE)){
				recommend.setRecommendStatus(Recommend.RECOMMEND_STATUS_SUCC);
			}
		}
		
		if(!recommend.getRecommendStatus().equals(Recommend.ADD_MONEY_BY_RATE)){
			return recommendDao.saveOrUpdate(recommend);
		}else{
			return true;
		}
		
	}

	@Override
	public boolean deleteRecommendById(Integer id) {
		return recommendDao.deleteByPrimaryKey(id);
	}

	@Override
	public List getRecommendByUserId(Integer userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return recommendDao.selects("selectRecommendByUserId", params);
	}
	public Recommend getByUserId(Integer userId){
		return this.recommendDao.selectEntity("selectByUserId", userId);
	}

	@Override
	public Recommend getRecommendById(Integer id) {
		return recommendDao.selectByPrimaryKey(id);
	}

	@Override
	public BigDecimal setInviteMoney(BigDecimal money) {
		Map<String,String> map= SysCacheUtils.getConfigParams();
		BigDecimal inviteRewardRate =new BigDecimal(map.get("recommend_InviteRewardRate"));
		BigDecimal inviteMoney = money.subtract(inviteRewardRate).divide(new BigDecimal(100));
		return inviteMoney;
	}

	@Override
	public PageModel getRecommendByRecommendUser(Map<String, String> paramsMap) {
		return recommendDao.getPage("selectByRecommendUser", "selectByRecommendUserCount", paramsMap);
	}

	@Override
	public Recommend selectIsRecommend(Integer userId) {
		return recommendDao.selectEntity("selectIsRecommend", userId);
	}
	
	public void allRecommendUser(Integer userId,Integer index,BigDecimal interestFee,BigDecimal tenderMoney,Integer status,Integer secId,Integer thridId){
		Recommend r=this.getByUserId(userId);
		if(r == null){
			return ;
		}
		//会员级数是否大于等于2
		if(Recommend.RECOMMEND_LEVEL_TIME.intValue() >= 2 && index<=Recommend.RECOMMEND_LEVEL_TIME.intValue()){
			Map<String,String> map= SysCacheUtils.getConfigParams();
			Recommend recommend =new Recommend();
			recommend.setRecommendStatus(Recommend.RECOMMEND_STATUS_NEW);
			recommend.setRecommendUserid(r.getRecommendUserid());
			recommend.setUserId(r.getUserId());
			recommend.setId(r.getId());
			BigDecimal recommendMoney=new BigDecimal(0);
			BigDecimal reward=new BigDecimal(0);
			if(CompareUtils.greaterThanZero(interestFee) || CompareUtils.greaterThanZero(tenderMoney)){
				recommendMoney=CalculateProcess.getRecommondMoneyByRate(interestFee).add(CalculateProcess.getRecommondMoneyByTenderMoney(tenderMoney));
				//会员佣金比例
				BigDecimal userFee=new BigDecimal(0);
				if(CompareUtils.greaterThanZero(interestFee)){
					userFee=new BigDecimal(map.get("reward_recommend_level_"+index));
				}
				if( CompareUtils.greaterThanZero(tenderMoney)){
					userFee=new BigDecimal(map.get("tender_recommend_level_"+index));
				}
				reward=recommendMoney.multiply(userFee).divide(new BigDecimal(100));
				if(CompareUtils.greaterThanZero(reward)){ //按比例
					recommend.setRecommendStatus(Recommend.ADD_MONEY_BY_RATE);//标注不保存数据库
					this.saveRecommend(recommend,reward);
				}
			}
			if(index ==1 ){
				this.saveOrUpdateData(r, reward, interestFee,tenderMoney, index,status,null,null);
			}else if(index==2){
				this.saveOrUpdateData(r, reward, interestFee,tenderMoney, index,status,secId,null);
			}else{
				this.saveOrUpdateData(r, reward, interestFee,tenderMoney, index,status,secId,thridId);
			}
			index++;
			this.allRecommendUser(r.getRecommendUserid(),index,interestFee,tenderMoney,status,r.getUserId(),secId);
		}
	}
	
	public void allRecommendUser(Integer userId,Integer index,BigDecimal interestFee,BigDecimal tenderMoney,Integer status,Integer secId,Integer thridId,Borrow borrow){
		Recommend r=this.getByUserId(userId);
		if(r == null){
			return ;
		}
		//会员级数是否大于等于2
		if(Recommend.RECOMMEND_LEVEL_TIME.intValue() >= 2 && index<=Recommend.RECOMMEND_LEVEL_TIME.intValue()){
			Map<String,String> map= SysCacheUtils.getConfigParams();
			Recommend recommend =new Recommend();
			recommend.setRecommendStatus(Recommend.RECOMMEND_STATUS_NEW);
			recommend.setRecommendUserid(r.getRecommendUserid());
			recommend.setUserId(r.getUserId());
			recommend.setId(r.getId());
			BigDecimal recommendMoney=new BigDecimal(0);
			BigDecimal reward=new BigDecimal(0);
			if(CompareUtils.greaterThanZero(interestFee) || CompareUtils.greaterThanZero(tenderMoney)){
				recommendMoney=CalculateProcess.getRecommondMoneyByRate(interestFee).add(CalculateProcess.getRecommondMoneyByTenderMoney(tenderMoney,borrow));
				//会员佣金比例
				BigDecimal userFee=new BigDecimal(0);
				if(CompareUtils.greaterThanZero(interestFee)){
					userFee=new BigDecimal(map.get("reward_recommend_level_"+index));
				}
				if( CompareUtils.greaterThanZero(tenderMoney)){
					userFee=new BigDecimal(map.get("tender_recommend_level_"+index));
				}
				reward=recommendMoney.multiply(userFee).divide(new BigDecimal(100));
				
				
				reward=reward.setScale(2, BigDecimal.ROUND_HALF_UP);
				
				
				if(CompareUtils.greaterThanZero(reward)){ //按比例
					recommend.setRecommendStatus(Recommend.ADD_MONEY_BY_RATE);//标注不保存数据库
					this.saveRecommend(recommend,reward);
				}
			}
			if(index ==1 ){
				this.saveOrUpdateData(r, reward, interestFee,tenderMoney, index,status,null,null);
			}else if(index==2){
				this.saveOrUpdateData(r, reward, interestFee,tenderMoney, index,status,secId,null);
			}else{
				this.saveOrUpdateData(r, reward, interestFee,tenderMoney, index,status,secId,thridId);
			}
			index++;
			this.allRecommendUser(r.getRecommendUserid(),index,interestFee,tenderMoney,status,r.getUserId(),secId,borrow);
		}
	}
	
	
	
	
	
	public void saveOrUpdateData(Recommend r,BigDecimal reward,BigDecimal interestFee,BigDecimal tenderMoney,Integer index,Integer status,Integer secId,Integer thridId){
		//奖励账户信息
		RecommendAccount ranew=new RecommendAccount();
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", r.getRecommendUserid());
		params.put("level", index);
		RecommendAccount ra=this.recommendAccountService.selectByUserId(params);
		if(ra!=null){
			ranew.setId(ra.getId());
			ranew.setRewards(ra.getRewards().add(reward));
		}else{
			ranew.setLevel(index);
			ranew.setUserId(r.getRecommendUserid());
			ranew.setRewards(reward);
		}
		this.recommendAccountService.saveOrUpdate(ranew);
		if(CompareUtils.greaterThanZero(reward)){
			//奖励添加记录信息
			RecommendReward rr=new RecommendReward();
			rr.setAddTime(new Date());
			rr.setAddIp(RequestUtils.getIpAddr());
			Map<String,Object> obj=new HashMap<String, Object>();
			obj.put("userId", r.getUserId());
			obj.put("level", index);
			
			//是否有上限用户
			if(secId!=null){
				List<Recommend> re=this.selectByRecommendUserIdList(secId);
				//解决三级推广信息，查询三级推广人
				if(index==3 && re.size()>1){
					obj.clear();
					obj.put("userId", thridId);
					obj.put("recommendUserIds", secId);
					obj.put("level", index);
					List<Recommend> rec=getRbRecommendbyUserId(obj);
					if(rec.size()>0){
						rr.setLevelUserId(rec.get(0).getUserId());
					}
				}else{
					if(re.size()>1){
						obj.put("userIds", secId);
						//解决二级推广信息
					}else if(index==3 && re.size()==1){
						obj.put("recommendUserIds", secId);
					}else{
						obj.put("userIds", secId);
					}
					List<Recommend> rec=this.getRbRecommendbyUserId(obj);
					if(rec.size()>0){
						rr.setLevelUserId(rec.get(rec.size()-1).getUserId());
					}
				}
				
			}else{
				List<Recommend> rec=this.getRbRecommendbyUserId(obj);
				if(rec.size()>0){
					rr.setLevelUserId(rec.get(rec.size()-1).getUserId());
				}
			}
			rr.setRecommendUserId(r.getRecommendUserid());
			rr.setLevelUserReward(interestFee);
			rr.setTenderMoney(tenderMoney);
			rr.setRecommondLevel(index);
			rr.setRecommendUserReward(reward);
			rr.setStatus(status);
			this.recommendRewardService.saveOrUpdate(rr);
		}
		
	}


	@Override
	public PageModel selectCountRecommend(Map<String, String> params) {
		PageModel page=this.recommendDao.getPage("selectCountRecommend", params);
		/*List<Map> list=page.getList();
		for(int i=0;i<list.size();i++){
			 Map map = list.get(i);
			// map.putAll(this.selectByRecommendUserId(Integer.valueOf(map.get("id").toString())));
			 map.putAll(this.selectCountByRecommendUserId(Integer.valueOf(map.get("id").toString())));
		}*/
		return page;
	}

	
	@Override
	public PageModel selectCountRecommend4Admin(Map<String, String> params) {
		PageModel page=this.recommendDao.getPage("selectCountRecommend", params);
		List<Map> list=page.getList();
		for(int i=0;i<list.size();i++){
			 Map map = list.get(i);
			 BigDecimal l= recommendAccountService.getRewardsByUserId(Integer.valueOf(map.get("id").toString()));
			 map.put("rewards", l);
		}
		return page;
	}

	
	
	public Map<String,Object> selectCountByRecommendUserId(Integer recommendUserId) {
		Map<String,Object> levl=new HashMap<String, Object>();
		
		if(Recommend.RECOMMEND_LEVEL_TIME.intValue() >= 2){
			for(int i=1;i<=Recommend.RECOMMEND_LEVEL_TIME.intValue();i++){
				String[] recommendSubLevl = GetDatabaseConn.getRecommendSubLevl(recommendUserId+"", (i+1)+"");
				levl.put("levl"+i, recommendSubLevl.length);
			}
		}
		return levl;
	}

	
	
	@Override
	public Map<String,Object> selectByRecommendUserId(Integer recommendUserId) {
		Map<String,Object> user=new HashMap<String, Object>();
		//会员级数是否大于等于2
		if(Recommend.RECOMMEND_LEVEL_TIME.intValue() >= 2){
			Map<String,Object> obj=new HashMap<String, Object>();
			Map<String,Object> objs=new HashMap<String, Object>();
			for(int i=1;i<=Recommend.RECOMMEND_LEVEL_TIME.intValue();i++){
				obj.put("recommendUserId", recommendUserId);
				obj.put("level", i);
				List<Recommend> rec=this.getRbRecommend(obj);
				if(rec.size()>0){
					objs.put("level"+i, rec.size());
				}else{
					objs.put("level"+i, 0);
				}
			}
			Map<String,String> params=new HashMap<String, String>();
			params.put("id", recommendUserId.toString());
			user=this.selectCountRecommendParams(params).get(0);
			for(int i=1;i<=Recommend.RECOMMEND_LEVEL_TIME.intValue();i++){
				if(i>1){
					user.put("level"+i, Integer.valueOf(objs.get("level"+i).toString())- Integer.valueOf(objs.get("level"+(i-1)).toString()));
				}else{
					user.put("level"+i, objs.get("level"+i));
				}
			}
		}
		return user;
	}
	
	@Override
	public List<Map> selectCountRecommendParams(Map<String, String> params) {
		return this.recommendDao.selects("selectCountRecommend", params);
	}

	@Override
	public List<Recommend> getRbRecommend(Map<String, Object> params) {
		return this.recommendDao.select("getRbRecommend", params);
	}
	
	
	@Override
	public List<Recommend> getRbRecommendbyUserId(Map<String, Object> params) {
		return this.recommendDao.select("getRbRecommendbyUserId", params);

	}

	@Override
	public List<Recommend> selectByRecommendUserIdList(Integer id) {
		return this.recommendDao.select("selectByRecommendUserId", id);
	}

	/*@Override
	public List<Recommend> getSubRecommendLists(String recommendUserId) {
		//查询一级用户
		Map<String,Object> obj1=new HashMap<String, Object>();
		obj1.put("recommendUserId", recommendUserId);
		obj1.put("level", 1);
		List<Recommend> rbRecommend1 = this.getRbRecommend(obj1);
		
		Map<String,Object> Levl1=new HashMap<String, Object>();
		Levl1.put("recommendUserId", recommendUserId);
		Levl1.put("level", 1);
		
		List<Recommend> rbRecommendLevl1 =getRbRecommendLevl(Levl1);
		
		
		Map<String,Object> Levl2=new HashMap<String, Object>();
		Levl2.put("recommendUserId", recommendUserId);
		Levl2.put("level", 2);
		
		List<Recommend> rbRecommendLevl2 =getRbRecommendLevl(Levl2);
		
		
		Map<String,Object> Levl3=new HashMap<String, Object>();
		Levl3.put("recommendUserId", recommendUserId);
		Levl3.put("level", 3);
		
		List<Recommend> rbRecommendLevl3 =getRbRecommendLevl(Levl3);
		
		
		List<Integer> list=new ArrayList<Integer>();	//存储1级用户id,用于查询二级之后剔除一级
		List<Integer> listTemp= new ArrayList<Integer>();  //存储2级用户id,用于查询三级级之后剔除一,二级
		
		Iterator<Recommend> it=rbRecommend1.iterator(); 
		while(it.hasNext()){  
			 Recommend a=it.next(); 
			 a.setLevl(1);	//标识一级用户
			 list.add(a.getUserId());
		}
		
		
		//查询一二级用户
		Map<String,Object> obj2=new HashMap<String, Object>();
		obj2.put("recommendUserId", recommendUserId);
		obj2.put("level", 2);
		List<Recommend> rbRecommend2 = this.getRbRecommend(obj2);
		
		
		Iterator<Recommend> iterator = rbRecommend2.iterator();
		while(iterator.hasNext()){  
			  Recommend a=iterator.next(); 
			  listTemp.add(a.getUserId());
			  if(list.contains(a.getUserId())){  
				  iterator.remove();  
			  }else{
				a.setLevl(2);//标识二级用户
			  } 
		}
		
		
		//查询一二三级用户
		Map<String,Object> obj3=new HashMap<String, Object>();
		obj3.put("recommendUserId", recommendUserId);
		obj3.put("level", 3);
		List<Recommend> rbRecommend3 = this.getRbRecommend(obj3);
		
		Iterator<Recommend> iterator3 = rbRecommend3.iterator();
		while(iterator3.hasNext()){  
			  Recommend a=iterator3.next(); 
			  if(listTemp.contains(a.getUserId())){  
				  iterator3.remove();  
			  }else{
				a.setLevl(3);//标识三级用户
			  } 
		}
		
		
		
	
		//重新拼装list,加了一二三级用户标识
		rbRecommend1.addAll(rbRecommend2);
		rbRecommend1.addAll(rbRecommend3);
		
		return rbRecommend1;
	}*/

	
	
	@Override
	public List<Recommend> getSubRecommendLists(String recommendUserId) {
		//查询一级用户
	
		Map<String,Object> Levl1=new HashMap<String, Object>();
		Levl1.put("recommendUserId", recommendUserId);
		Levl1.put("level", 1);
		
		List<Recommend> rbRecommendLevl1 =getRbRecommendLevl(Levl1);
		
		Map<String,Object> Levl2=new HashMap<String, Object>();
		Levl2.put("recommendUserId", recommendUserId);
		Levl2.put("level", 2);
		
		List<Recommend> rbRecommendLevl2 =getRbRecommendLevl(Levl2);
		
		
		Map<String,Object> Levl3=new HashMap<String, Object>();
		Levl3.put("recommendUserId", recommendUserId);
		Levl3.put("level", 3);
		
		List<Recommend> rbRecommendLevl3 =getRbRecommendLevl(Levl3);
		
		Iterator<Recommend> iterator1 = rbRecommendLevl1.iterator();
		while(iterator1.hasNext()){  
			  Recommend a=iterator1.next(); 
			  a.setLevl(1);//标识一级用户
			 
		}
		Iterator<Recommend> iterator2 = rbRecommendLevl2.iterator();
		while(iterator2.hasNext()){  
			  Recommend a=iterator2.next(); 
			  a.setLevl(2);//标识二级用户
			 
		}
		Iterator<Recommend> iterator3 = rbRecommendLevl3.iterator();
		while(iterator3.hasNext()){  
			  Recommend a=iterator3.next(); 
			  a.setLevl(3);//标识三级用户
			 
		}
		//重新拼装list,加了一二三级用户标识
		rbRecommendLevl1.addAll(rbRecommendLevl2);
		rbRecommendLevl1.addAll(rbRecommendLevl3);
		
		return rbRecommendLevl1;
	}
	
	
	
	@Override
	public List<Recommend> getRbRecommendLevl(Map<String, Object> params) {
		return this.recommendDao.select("getRbRecommendtest", params);
	}

	@Override
	public List<String> getRecommendstest(Map<String, Object> params) {
		return this.recommendDao.selects("getRbRecommendtest1", params);
	}

	@Override
	public List getRecommendLevl(Map<String, Object> levl) {
		 
				List selects = this.recommendDao.selects("getRecommendLevl", levl);
				
				return selects;
	}

	
	

	
	
	/*@Override
	public List<Recommend> getRbRecommendUser(Map<String, Object> params) {
		return this.recommendDao.select("getRbRecommendUser", params);
	}*/

}
