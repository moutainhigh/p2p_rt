package com.rbao.east.dao;

import com.rbao.east.entity.UserAccount;

public interface UserAccountDao extends BaseDao<UserAccount> {

	public UserAccount selectByUserId(Integer userId);
	
	/**
	 * 获取平台帐号
	 * @return
	 */
	public UserAccount selectAdminAccount() ;
	/**
	 * 获取平台担保帐号
	 * @return
	 */
	public UserAccount selectPlatformVouchAccount();
	
	/*
	 * 创建帐号
	 */
	public boolean createUserAccount(Integer userId);

	boolean updateNoneAssetByPk(UserAccount acc);

	UserAccount selectByUserIdForUpdate(Integer userId); 
}
