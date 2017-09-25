package com.rbao.east.service.impl;



import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AccountDeductDao;
import com.rbao.east.dao.UserAccountDao;
import com.rbao.east.entity.AccountDeduct;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AccountDeductService;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.utils.CompareUtils;

@Service
@Transactional
public class AccountDeductServiceImpl implements AccountDeductService {
	private static Logger logger = LoggerFactory
			.getLogger(AccountDeductServiceImpl.class);
	@Autowired
	private AccountDeductDao accountDeductDao;
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private AccountLogService accountLogService;
	@Override
	public boolean save(AccountDeduct accountDeduct) {
		
		boolean flag=false;
		try{
			if(accountDeduct.getCheckState()==null){
				accountDeduct.setCheckState(0);
			}
			flag=this.accountDeductDao.saveOrUpdate(accountDeduct);
			if(accountDeduct.getCheckState()==1){//
				    //扣费金额
					BigDecimal deductAmount=accountDeduct.getDeductAmount();
					
					Integer type=accountDeduct.getDeductType();
					//扣除用户账号总额 和可用余额
					Integer userId=accountDeduct.getUserId();
					UserAccount userAccount=userAccountDao.selectByUserId(userId);
					BigDecimal allMoney=userAccount.getAllMoney().subtract(deductAmount);
					BigDecimal availableMoney=userAccount.getAvailableMoney().subtract(deductAmount); 
					if(CompareUtils.greaterThanAndEqualsZero(availableMoney)){
						userAccount.setAllMoney(allMoney);
						userAccount.setAvailableMoney(availableMoney);
						flag=userAccountDao.saveOrUpdate(userAccount);
					}else{
						throw new RuntimeException("用户可用金额不足,不能成功扣除！！！");
					}
					
					if(flag){
						
						//添加用户账号log
						accountLogService.add(userAccount, AccountLog.TRADE_CODE_Account_Deduct_FEE, deductAmount
											,new BigDecimal(0), Constants.ADMIN_USER_ID, 
											"用户["+accountDeduct.getUserAccount()+"]成功扣除"+AccountDeduct.ALL_TYPE.get(type)+deductAmount+"元给管理员.【"+accountDeduct.getRemark()+"】"
											,accountDeduct.getCheckIp());
		
						//向管理员增加  总额 和可用余额
						UserAccount adminAccount=userAccountDao.selectAdminAccount();
						BigDecimal adminAllMoney=adminAccount.getAllMoney().add(deductAmount);
						BigDecimal adminAvailableMoney=adminAccount.getAvailableMoney().add(deductAmount);
						adminAccount.setAllMoney(adminAllMoney);
						adminAccount.setAvailableMoney(adminAvailableMoney);
						flag=userAccountDao.updateByPrimaryKeySelective(adminAccount);
						if(flag){
							//添加管理员log
							accountLogService.add(adminAccount, AccountLog.TRADE_CODE_Account_Deduct_FEE_Admin, deductAmount
												,new BigDecimal(0), accountDeduct.getUserId(), 
												"管理员成功添加来自用户["+accountDeduct.getUserAccount()+"]"+AccountDeduct.ALL_TYPE.get(type)+deductAmount+"元.【"+accountDeduct.getRemark()+"】"
												,accountDeduct.getCheckIp());
							
						}
					}
				}
			
		}catch (Exception e) {
			
			logger.info("用户费用扣除出错====="+e.toString());
			throw new RuntimeException(e.getLocalizedMessage());
			
		}
		return flag;
	}

	@Override
	public PageModel getAccountDeductList(Map<String, String> paramsMap) {
		return this.accountDeductDao.getPage("selectByEntity", "selectTotalCount", paramsMap);
	}

	@Override
	public AccountDeduct getById(Integer accountDeductId) {
		return this.accountDeductDao.selectByPrimaryKey(accountDeductId);
	}

	

	
	
}
