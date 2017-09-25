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

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.dao.AccountLogDao;
import com.rbao.east.dao.AccountPaymentDao;
import com.rbao.east.dao.RedenvelopesRecordDao;
import com.rbao.east.dao.UserAccountDao;
import com.rbao.east.dao.UserDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.RedenvelopesRecord;
import com.rbao.east.entity.User;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.RedenvelopesProbabilityService;
import com.rbao.east.service.RedenvelopesService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.SysCacheUtils;

@Service
@Transactional
public class RedenvelopesServiceImpl implements RedenvelopesService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AccountLogDao logDao ;
	@Autowired
	AccountPaymentDao accPaymentDao;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	RedenvelopesRecordDao redRecdDao ;
	@Autowired
	private UserAccountDao accDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	RedenvelopesProbabilityService redProService;
	@Autowired
	private UserService userService;
	@Autowired
	MessageCenterService messageCenterService;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BigDecimal getRedSumMoneyByUsrIdAndStatus(Integer usrId,Integer[] status){
		Map m = new HashMap();
		m.put("userId", usrId);
		m.put("inStatus", status);
		return (BigDecimal) redRecdDao.selects("selectSumMoneyByMap", m).get(0);
	}
	
	@Override
	public boolean addRedenvelopesRecord(RedenvelopesRecord record) {
		record.setRealAmount(record.getAmount());
		redRecdDao.insertSelective(record);
				
		return true;
	}
	/**
	 * 被邀请人投资奖励红包
	 * @param userId
	 * @param money
	 * @return
	 */
	@Override
	public boolean sendInvitedRedRecord(Integer userId,BigDecimal money){
		
		try {
			Map<String,String> cfgMap = SysCacheUtils.getConfigParams();
			
			RedenvelopesRecord recd = new RedenvelopesRecord();
			recd.setName(RedenvelopesRecord.RED_NAME_INVITED);
			recd.setUserId(userId);
			recd.setAmount(money);
		//	recd.setOpenLimitType(RedenvelopesRecord.OPEN_LIMIT_TYPE_TENDER);
		//	recd.setOpenLimitParam(new BigDecimal(cfgMap.get("register"+money.intValue())));
			recd.setSendTime(new Date());
			recd.setPeriodBeginTime(recd.getSendTime());
			recd.setPeriodEndTime(DateUtils.addMonth(recd.getPeriodBeginTime(), 
										Integer.parseInt(cfgMap.get("period_month_red"))));
			recd.setRemark("无条件"); 
			recd.setSendUserId(Constants.ADMIN_USER_ID);
			recd.setStatus(RedenvelopesRecord.STATUS_NOT_OPEN);
			recd.setRedType(RedenvelopesRecord.TYPE_CASH);
			
			return addRedenvelopesRecord(recd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean sendTenderRewardRedRecord(Integer userId,BigDecimal money){
		
		try {
			Map<String,String> cfgMap = SysCacheUtils.getConfigParams();
			
			RedenvelopesRecord recd = new RedenvelopesRecord();
			recd.setName("投标奖励");
			recd.setUserId(userId);
			recd.setAmount(money);
		//	recd.setOpenLimitType(RedenvelopesRecord.OPEN_LIMIT_TYPE_TENDER);
		//	recd.setOpenLimitParam(new BigDecimal(cfgMap.get("register"+money.intValue())));
			recd.setSendTime(new Date());
			recd.setPeriodBeginTime(recd.getSendTime());
			recd.setPeriodEndTime(DateUtils.addMonth(recd.getPeriodBeginTime(), 
										Integer.parseInt(cfgMap.get("period_month_red"))));
			recd.setRemark("无条件"); 
			recd.setSendUserId(Constants.ADMIN_USER_ID);
			recd.setStatus(RedenvelopesRecord.STATUS_NOT_OPEN);
			recd.setRedType(RedenvelopesRecord.TYPE_CASH);
			
			return addRedenvelopesRecord(recd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean sendTenderRedRecord(Integer userId,BigDecimal money){
		
		try {
			Map<String,String> cfgMap = SysCacheUtils.getConfigParams();
			
			if(cfgMap.get("open_tender_red").equals("0")){
				return true;//关闭
			}
			
			RedenvelopesRecord recd = new RedenvelopesRecord();
			recd.setName("新手红包");
			recd.setUserId(userId);
			recd.setAmount(money);
			recd.setOpenLimitType(RedenvelopesRecord.OPEN_LIMIT_TYPE_TENDER);
			recd.setOpenLimitParam(new BigDecimal(cfgMap.get("register"+money.intValue())));
			recd.setSendTime(new Date());
			recd.setPeriodBeginTime(recd.getSendTime());
			recd.setPeriodEndTime(DateUtils.addMonth(recd.getPeriodBeginTime(), 
										Integer.parseInt(cfgMap.get("period_month_red"))));
			recd.setRemark("投资满"+recd.getOpenLimitParam()+"元"); 
			recd.setSendUserId(Constants.ADMIN_USER_ID);
			recd.setStatus(RedenvelopesRecord.STATUS_CANNOT_OPEN);
			recd.setRedType(RedenvelopesRecord.TYPE_TENDER);
			
			return addRedenvelopesRecord(recd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public BigDecimal sendLoginRedRecord(Integer userId,boolean isRegister){
		
		try {
			Map<String,String> cfgMap = SysCacheUtils.getConfigParams();
			
			if(cfgMap.get("open_register_red").equals("0")){
				return new BigDecimal(0);//关闭
			}
			
			RedenvelopesRecord recd = new RedenvelopesRecord();
			recd.setUserId(userId);
			if(isRegister){
				recd.setName("新手红包");
			}else{
				recd.setName("登录红包");
			}
			
			recd.setAmount(new BigDecimal(cfgMap.get("login_money")));
			recd.setOpenLimitType(RedenvelopesRecord.OPEN_LIMIT_TYPE_LOGIN_DAYS);
			recd.setOpenLimitParam(new BigDecimal(cfgMap.get("login_day")));
			recd.setSendTime(new Date());
			recd.setPeriodBeginTime(recd.getSendTime());
			recd.setPeriodEndTime(DateUtils.addMonth(recd.getPeriodBeginTime(), 
										Integer.parseInt(cfgMap.get("period_month_red"))));
			recd.setRemark("登录满"+recd.getOpenLimitParam()+"天"); 
			recd.setSendUserId(Constants.ADMIN_USER_ID);
			recd.setStatus(RedenvelopesRecord.STATUS_CANNOT_OPEN);
			recd.setRedType(RedenvelopesRecord.TYPE_LOGIN);
			
			addRedenvelopesRecord(recd);
			return recd.getAmount();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return new BigDecimal(0);
	}
	@Override
	public boolean sendRedRecord(RedenvelopesRecord record,List<Integer> recvUserIds) {
		for(Integer userId : recvUserIds){
			
			record.setUserId(userId);
			addRedenvelopesRecord(record);
		}
		return true;
	}
	@Override
	public JsonResult openRedenvelopesIfSettingAuto(RedenvelopesRecord red){
		User dbUser = this.userService.getById(red.getUserId());
		if(dbUser == null){
			return new JsonResult("933","没有找到用户"); 
		}
		if(dbUser.getAutoRedFlag().intValue() == 2){
			return openRedenvelopes(red.getId(),true);
		}
		return new JsonResult("932","当前用户并没有开启自动打开红包"); 
	}
	@Override
	public JsonResult openRedenvelopes(Integer id,boolean isAuto){
		RedenvelopesRecord red = this.redRecdDao.selectByPrimaryKey(id);
		JsonResult rest = validateOpenLimit(red);
		if(!rest.isSuccessed()){
			return rest;
		}
		BigDecimal money = red.getAmount();
		if(isAuto == false){
			//资金加倍
			money = redProService.calProbaMultiMoney(money);
		}
		userAccountService.addMoney(red.getUserId(), money,AccountLog.TRADE_CODE_OPEN_RED);
		
		red.setStatus(RedenvelopesRecord.STATUS_HAS_OPEN);
		red.setRealAmount(money);
		red.setUpdateTime(new Date());
		redRecdDao.updateByPrimaryKeySelective(red);
		
		messageCenterService.send(red.getUserId(), "打开红包", "红包["+red.getName()+"]打开成功，得到金额"+red.getRealAmount()+"元。", Notice.LOAN_CREATED);
		/*if(red.getOpenLimitType().equals(RedenvelopesRecord.OPEN_LIMIT_TYPE_LOGIN_DAYS)){
			sendLoginRedRecord(red.getUserId());
		}*/
		return new JsonResult(JsonResult.SUCCESS,red.getRealAmount().toString());
	}
	public JsonResult validateOpenLimit(RedenvelopesRecord record){
		if(record == null){
			return new JsonResult("104","没有找到当前记录");
		}
		if(record.getStatus().equals(RedenvelopesRecord.STATUS_CANNOT_OPEN)){
			return new JsonResult("101","未满足条件");
		}else if(record.getStatus().equals(RedenvelopesRecord.STATUS_HAS_OPEN)){
			return new JsonResult("102","该红包已经打开");
		}else if(record.getStatus().equals(RedenvelopesRecord.STATUS_OVERDUE)){
			return new JsonResult("103","该红包已经逾期");
		}
		return new JsonResult(JsonResult.SUCCESS);
	}
	@Override
	public PageModel getFrontListPaged(Map map){
		return this.redRecdDao.getPage("selectFrontByParam", "selectFrontCountByParam", map);
	}
	
	@Override
	public PageModel getAdminListPaged(Map map){
		return this.redRecdDao.getPage("selectAdminByParam", "selectAdminCountByParam", map);
	}
	
	
	@Override
	public boolean setAutoFlag(Integer userId,boolean opened) {
		User user = new User();
		user.setId(userId);
		user.setAutoRedFlag(opened?2:1);
		userDao.updateByPrimaryKeySelective(user);
		if(opened){
		//	openAllRed(userId);
		}
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int openAllRed(Integer userId){
		Map m = new HashMap();
		m.put("userId", userId);
		m.put("inStatus", new Integer[]{RedenvelopesRecord.STATUS_NOT_OPEN});
		List<RedenvelopesRecord> recds = this.redRecdDao.select("selectFrontByParam", m);
		
		int openCount = 0;
		for(RedenvelopesRecord recd : recds){
			if(this.openRedenvelopes(recd.getId(),true).isSuccessed()){
				openCount ++;
			}
		}
		return openCount;
	}
	@Override
	public PageModel getFrontListPageRed(Map map){
		return this.redRecdDao.getPage("selectFrontByParamRed", "selectFrontCountByParam", map);
	}

	@Override
	public BigDecimal getRedSumMoneyByUsrIdAndStatusTime(Map map) {
		return (BigDecimal) redRecdDao.selects("selectSumMoneyByMapTime", map).get(0);
	}
}
