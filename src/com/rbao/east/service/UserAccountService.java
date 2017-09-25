package com.rbao.east.service;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.UserAccount;

/**
 * 用户账户
 * */

public interface UserAccountService {
	
	/**
	 * 添加注册奖励
	 * @param userId
	 */
	public void addRegisterAward(Integer userId);
	
	public UserAccount selectByUserIdForUpdate(Integer userId);
	
	public List<UserAccount> getUserAccountList();
	/**
	 * 
	 * @author Sandy
	 * @return
	 */
	public PageModel getUserAccountList(Map<String, String> paramsMap);

	public UserAccount getById(Integer userAccountId);
	
	public boolean updateByPrimaryKeySelective(UserAccount userAccount);
	
	public List<UserAccount> selectUserAccountList(Map<String, String> paramsMap);
	/**
	 * 推广邀请审核通过为推荐人添加奖励
	 * @param recommendUserId 推荐人Id 
	 * @param reward 奖励金额
	 */
	public void addRecommendReward(Integer recommendUserId,BigDecimal reward);
	/**
	 * 向某人付款
	 * @param payUserId
	 * @param recvUserId
	 * @param fee
	 * @param remark
	 */
	public void payToPlat(Integer payUserId,BigDecimal fee,String payType,String remark);
	
	public DwzResult updateUserAccountForAccountCash(Map<String, Object> param);

	/**
	 * 充值记录审核通过为用户添加金额
	 * @param userId 用户Id
	 * @param rechargeMoney 充值金额
	 * @param fee 费用
	 * @param rechargeType 充值类型
	 */
	public void addAccountRecharge(Integer userId, BigDecimal rechargeMoney,BigDecimal fee,String rechargeType,String remark);
	
	public UserAccount getByUserId(Integer userId);
	
	/**
	 * 统计用户账户分页
	 * @param paramsMap
	 * @return
	 */
	public PageModel getUserAccountStatisticsList(Map<String, String> paramsMap);
	
	/**
	 * 线上充值后执行方法，处理用户总金额
	 * @param accountRecharge
	 * @param mode充值 方式
	 * @return
	 * @throws DataAccessException
	 */
	public boolean rechargeToAccount(AccountRecharge accountRecharge,Integer mode)throws DataAccessException;
	
	public boolean deleteByUserId(Integer userId);
	
	public List<Map<String, Object>> getQQ(Map<String,Object> params);
	
	/**
	 * 统计总获得利息
	 * @return BigDecimal
	 */
	public BigDecimal getAllInterestMoney();

	void addMoney(Integer usrId, BigDecimal money, String tradeCode);
	
}
