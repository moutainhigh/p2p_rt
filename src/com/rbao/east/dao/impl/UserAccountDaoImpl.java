package com.rbao.east.dao.impl;

import org.springframework.stereotype.Repository;

import com.rbao.east.common.Constants;
import com.rbao.east.dao.UserAccountDao;
import com.rbao.east.entity.UserAccount;

@Repository
public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount> implements UserAccountDao {

	@Override
	public UserAccount selectByUserId(Integer userId) {
		if(userId == null || userId < 1){
			return null;
		}
		return super.selectEntity("selectByUserId", userId);
		
	}
	
	@Override
	public UserAccount selectByUserIdForUpdate(Integer userId) {
		if(userId == null || userId < 1){
			return null;
		}
		return super.selectEntity("selectByUserIdForUpdate", userId);
		
	}

	@Override
	public UserAccount selectAdminAccount() {
		UserAccount acc = selectByUserId(Constants.ADMIN_USER_ID);
		if(acc == null){ //没有就重新创建一个
			createUserAccount(Constants.ADMIN_USER_ID);
			acc = selectByUserId(Constants.ADMIN_USER_ID);
		}
		return acc;
	}

	@Override
	public boolean updateNoneAssetByPk(UserAccount acc) {
		return this.update("updateNoneAssetByPk", acc);
	}
	@Override
	public boolean createUserAccount(Integer userId) {
		UserAccount acc = new UserAccount();
		acc.setUserId(userId);
		return insertSelective(acc);
	}

	@Override
	public UserAccount selectPlatformVouchAccount() {
		UserAccount acc = selectByUserId(Constants.PLATFORM_VOUCH_USER_ID);
		if(acc == null){ //没有就重新创建一个
			createUserAccount(Constants.PLATFORM_VOUCH_USER_ID);
			acc = selectByUserId(Constants.PLATFORM_VOUCH_USER_ID);
		}
		return acc;
	}

}
