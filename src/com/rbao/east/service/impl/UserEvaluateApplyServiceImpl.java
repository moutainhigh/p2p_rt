package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.UserEvaluateApplyDao;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserEvaluate;
import com.rbao.east.entity.UserEvaluateApply;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.UserEvaluateApplyService;
import com.rbao.east.service.UserEvaluateService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.RequestUtils;

@Service
@Transactional
public class UserEvaluateApplyServiceImpl implements UserEvaluateApplyService{
	
	@Autowired
	private UserEvaluateApplyDao userEvaluateApplyDao;//用户额度申请
	@Autowired
	private UserEvaluateService userEvaluateService;//用户额度
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private UserService userService;

	@Override
	public PageModel getUserEvaluateApplyList(Map<String, String> paramsMap) {
		return userEvaluateApplyDao.getPage("selectByEntity", "selectTotalCount", paramsMap);
	}

	@Override
	public UserEvaluateApply getUserEvaluateApplyById(Integer id) {
		return userEvaluateApplyDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateUserEvaluateApply(UserEvaluateApply apply) {
		User user = userService.getById(apply.getUserId());
		//用户额度
		UserEvaluate userEvaluate = userEvaluateService.getUserEvaluateByUserId(apply.getUserId());
		if(apply.getEvaluateapplyStatus().equals(UserEvaluateApply.APPLY_STATUS_CHECK_SUCCESS)){
			if(apply.getAmountApply().compareTo(apply.getAmount())==1 ||apply.getAmountApply().compareTo(apply.getAmount())==0){//申请额度大于或等于通过额度
				if(userEvaluate == null){
					userEvaluate = new UserEvaluate();
					userEvaluate.setUserId(apply.getUserId());
					userEvaluate.setCredit(apply.getAmount());
					userEvaluate.setCreditAvailable(apply.getAmount());
					userEvaluateService.save(userEvaluate);
				}else{
					userEvaluate.setCredit(userEvaluate.getCredit().add(apply.getAmount()));
					userEvaluate.setCreditAvailable(userEvaluate.getCredit().add(apply.getAmount()));
					userEvaluateService.save(userEvaluate);
				}
				MessageCenter center = new MessageCenter();
				center.setMessageContent("额度申请通过，额度为"+apply.getAmount());
				center.setMessageSendIp(RequestUtils.getIpAddr());
				center.setReceiveUserId(apply.getUserId());
				center.setMessageTitle("额度申请通过");
				center.setSendUserId(apply.getVerifyUser());
				messageCenterService.send(center, Notice.EVALUATE_APPLAY_YES);
			}else{
				MessageCenter center = new MessageCenter();
				center.setMessageContent("额度申请未通过");
				center.setMessageSendIp(RequestUtils.getIpAddr());
				center.setReceiveUserId(apply.getUserId());
				center.setMessageTitle("额度申请未通过");
				center.setMessageAddress(user.getUserEmail());
				center.setSendUserId(apply.getVerifyUser());
				messageCenterService.send(center, Notice.EVALUATE_APPLAY_NO);
			}
		}else{
			if(userEvaluate != null){
				apply.setAmount(userEvaluate.getCredit());
			}else{
				apply.setAmount(new BigDecimal(0));
			}
			MessageCenter center = new MessageCenter();
			center.setMessageContent("额度申请未通过");
			center.setMessageSendIp(RequestUtils.getIpAddr());
			center.setReceiveUserId(apply.getUserId());
			center.setMessageTitle("额度申请未通过");
			center.setMessageAddress(user.getUserEmail());
			center.setSendUserId(apply.getVerifyUser());
			messageCenterService.send(center, Notice.EVALUATE_APPLAY_NO);
		}
		return userEvaluateApplyDao.updateByPrimaryKeySelective(apply);
	}

	@Override
	public boolean toEvaluateApply(UserEvaluateApply userEvaluateApply) {
		boolean flag=false;
		try{
			UserEvaluateApply evaluateApply =this.selectByUserId(userEvaluateApply.getUserId());
			userEvaluateApply.setEvaluateapplyStatus(UserEvaluateApply.APPLY_STATUS_WAIT_CHECK);
			userEvaluateApply.setEvaluateapplyAddtime(new Date());
			userEvaluateApply.setEvaluateapplyType("1");//信用额度申请
			userEvaluateApply.setEvaluateapplyRemark(userEvaluateApply.getEvaluateapplyContent());
			if(CompareUtils.greaterThanZero(userEvaluateApply.getAmountApply())){
				if(evaluateApply != null){
					if(DateUtils.daysBetween(evaluateApply.getEvaluateapplyAddtime(), new Date())>=30){
						userEvaluateApply.setAmountBefore(evaluateApply.getAmount());
						userEvaluateApply.setAmount(userEvaluateApply.getAmountApply());
						flag=this.userEvaluateApplyDao.saveOrUpdate(userEvaluateApply);
					}else{
						flag=false;
						throw new RuntimeException("额度申请一个月只能申请一次");
					}
				}else{
					userEvaluateApply.setAmountBefore(BigDecimal.valueOf(new Double(0)));
					userEvaluateApply.setAmount(userEvaluateApply.getAmountApply());
					flag=this.userEvaluateApplyDao.saveOrUpdate(userEvaluateApply);
				}
					
			}else{
				flag=false;
				throw new RuntimeException("额度申请必须大于0");
			}
		}catch(Exception e){
			flag=false;
			
			throw new RuntimeException("额度申请添加出错！");
		}
		return flag;
	}
	@Override
	public UserEvaluateApply selectByUserId(Integer userId) {
		return this.userEvaluateApplyDao.selectEntity("selectByUserId", userId);
	}

	@Override
	public PageModel getApplyByUserId(Map<String, String> paramsMap) {
		return this.userEvaluateApplyDao.getPage("selectApplyByUserId", "countApplyByUserId", paramsMap);
	}

	@Override
	public boolean deleteByUserId(Integer userId) {
		return this.userEvaluateApplyDao.deletes("deleteByUserId", userId);
	}
	
}
