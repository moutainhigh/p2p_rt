package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.dao.UserAccountDao;
import com.rbao.east.dao.VipUserDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.entity.VipUser;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.service.VipUserService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SysCacheUtils;

@Service
@Transactional
public class VipUserServiceImpl implements VipUserService {
	@Autowired
	private VipUserDao vipUserDao;
	@Autowired
	private UserService userService;
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	private MessageCenterService messageCenterService;

	@SuppressWarnings("unused")
	@Override
	public boolean saveOrUpdateVipUser(VipUser vipUser, String creditCode,
			Integer loginUserId, Integer status) {
		DwzResult dwzResult = null;
		// 获取vip申请费用
		BigDecimal sysVipRate = SysCacheUtils.getSysFeesRate().getSysVipRate();
		// 获取账户信息
		UserAccount userAccount = this.userAccountDao.selectEntity(
				"selectByUserId", vipUser.getUserId());
		boolean flag = false;
		try {
			// vip申请是否需要费用
			if (sysVipRate.compareTo(BigDecimal.valueOf(0)) == 1) {
				if( !VipUser.VIP_FAIL.equals(vipUser.getVipStatus())){
					// 申请用户的账户可用金额是否大于vip所需费用
					/*if (userAccount.getAvailableMoney().compareTo(sysVipRate) == 1) {*/
						// 总金额减少
						userAccount.setAllMoney(userAccount.getAllMoney().subtract(sysVipRate));
						if(userAccount.getAllMoney().compareTo(new BigDecimal(0)) < 1){
							userAccount.setAllMoney(new BigDecimal(0));
						}
						//冻结金额减少
						userAccount.setUnavailableMoney(userAccount.getUnavailableMoney().subtract(sysVipRate));
						if(userAccount.getUnavailableMoney().compareTo(new BigDecimal(0)) < 1){
							userAccount.setUnavailableMoney(new BigDecimal(0));
						}
						// 获取平台账户
						UserAccount adminAccount = this.userAccountDao.selectAdminAccount();
						adminAccount.setAllMoney(adminAccount.getAllMoney().add(sysVipRate));
						adminAccount.setAvailableMoney(adminAccount.getAvailableMoney().add(sysVipRate));
						// 扣除vip费用
						flag = this.userAccountDao.updateByPrimaryKeySelective(userAccount);
						accountLogService.add(userAccount,AccountLog.TRADE_CODE_VIP, sysVipRate,BigDecimal.valueOf(0), adminAccount.getUserId(),
								"用户vip扣除费用" + sysVipRate + "元",RequestUtils.getIpAddr());
						if (flag) {
							// 将vip费用给平台
							flag = this.userAccountDao.updateByPrimaryKeySelective(adminAccount);
							accountLogService.add(adminAccount,AccountLog.TRADE_CODE_VIP, sysVipRate,BigDecimal.valueOf(0), vipUser.getId(),"vip扣除费用所得" + sysVipRate + "元",
									RequestUtils.getIpAddr());
						}
						vipUser.setVipUpdateDatetime(new Date());
						if (flag) {
							flag = this.vipUserDao.saveOrUpdate(vipUser);
						}
				}else{
					userAccount.setAvailableMoney(userAccount.getAvailableMoney().add(sysVipRate));
					userAccount.setUnavailableMoney(userAccount.getUnavailableMoney().subtract(sysVipRate));
					flag = this.userAccountDao.updateByPrimaryKeySelective(userAccount);
					accountLogService.add(userAccount,AccountLog.TRADE_CODE_VIP, sysVipRate,BigDecimal.valueOf(0), vipUser.getUserId(),
							"用户vip扣除费用解冻" + sysVipRate + "元",RequestUtils.getIpAddr());
					
					vipUser.setVipStatus(VipUser.VIP_FAIL);
					vipUser.setVipUpdateDatetime(new Date());
					this.saveOrUpdate(vipUser);
					flag = false;
				}
			
			} else {
				flag = this.vipUserDao.saveOrUpdate(vipUser);
			}
			// 1状态为通过
			if (flag && status == VipUser.VIP_USER) {
			this.creditLogService.addCreditLog(creditCode,vipUser.getUserId(), loginUserId);
			this.userCreditService.addUserCredit(creditCode,vipUser.getUserId(), loginUserId);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}

		return flag;
	}

	@Override
	public PageModel VipUserPageList(Map<String, String> param,
			Map<String, String> paramCustomer) {
		// 是否是超级管理员
		boolean flag = this.userService.isAdminByUserId(Integer.parseInt(param
				.get("userId")));
		if (flag) {
			// 用户vip用户
			return this.vipUserDao.getPage("selectVipUserByCustomerIdList",
					"selectVipUserByCustomerIdCount", param);
		} else {
			// 根据客服id查看
			return this.vipUserDao.getPage("selectVipUserByCustomerIdList",
					"selectVipUserByCustomerIdCount", paramCustomer);
		}
	}

	@Override
	public VipUser selectVipUserById(Integer id) {
		return this.vipUserDao.selectByPrimaryKey(id);
	}

	@Override
	public VipUser selectByUserId(Map<String, String> param) {
		return this.vipUserDao.selectEntity("selectByUserId", param);
	}

	@Override
	public boolean saveOrUpdate(VipUser vipUser) {
		return this.vipUserDao.saveOrUpdate(vipUser);
	}

	@Override
	public PageModel vipUserPageList(Map<String, String> param) {
		return this.vipUserDao.getPage("selectVipUserByCustomerIdList",
				"selectVipUserByCustomerIdCount", param);
	}

	@Override
	public void updateVipStatusByEndDate(VipUser vip) {
		vip.setVipStatus(VipUser.VIP_STOP);
		vip.setVipEndDate(new Date());
		try {
			this.vipUserDao.update("updateVipStatusByEndDate", vip);
		} catch (Exception e) {
			
		}
	}

	@Override
	public List<VipUser> selectVipEndUser(Map<String, String> params) {
		return this.vipUserDao.select("selectVipEndUser", params);
	}

	@Override
	public void vipNotify() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("vipStatus", VipUser.VIP_USER + "");
		List<VipUser> vipUsers = this.selectVipEndUser(params);
		for (VipUser vipUser : vipUsers) {
			Date endDate = vipUser.getVipEndDate();
			try {
				int notifyNum = DateUtils.daysBetween(new Date(),endDate);
				if (notifyNum == Constants.NOTIFY_DAY_NUM) {
					User user = userService.getById(vipUser.getUserId());
					MessageCenter mc = new MessageCenter();
					mc.setSendUserId(Constants.ADMIN_USER_ID);
					mc.setReceiveUserId(vipUser.getUserId());
					mc.setMessageTitle("【"+SysCacheUtils.getSysConfig().getSysWebsitename()+"】vip用户[" + user.getUserAccount()
							+ "]vip到期提醒");
					mc.setMessageSendDatetime(new Date());
					mc.setMessageContent("您的VIP帐户["
							+ user.getUserAccount()
							+ "]将于["
							+ DateUtils.formatDate("yyyy-MM-dd HH:mm:ss",
									vipUser.getVipEndDate())
							+ "]到期,如需继续使用,请尽快续费,谢谢!");

					messageCenterService.sendMessage(mc, null);
				}
			} catch (Exception e) {
				
			}
		}
	}

	@Override
	public boolean deleteByUserId(Integer userId) {
		return this.vipUserDao.deletes("deleteByUserId", userId);
	}

}
