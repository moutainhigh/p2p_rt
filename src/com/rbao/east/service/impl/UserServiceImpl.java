package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.SysRoleDao;
import com.rbao.east.dao.TreeModelDao;
import com.rbao.east.dao.UserAccountDao;
import com.rbao.east.dao.UserDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.SysRole;
import com.rbao.east.entity.TreeModel;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.AutotenderRulesService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserEvaluateApplyService;
import com.rbao.east.service.UserEvaluateService;
import com.rbao.east.service.UserService;
import com.rbao.east.service.VipUserService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SysCacheUtils;
/**
 * 
 * @author xiangxiaoyan
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TreeModelDao treeModelDao;
	
	@Autowired
	private SysRoleDao roleDao;
	@Autowired
	private UserAccountDao accountDao;
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired 
    private UserAccountDao userAccountDao;
    @Autowired 
    private UserAccountService userService;
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private AutotenderRulesService autotenderRulesService;
    @Autowired
    private VipUserService vipUserService;
    @Autowired
    private UserEvaluateService userEvaluateService;
    @Autowired 
    private UserEvaluateApplyService userEvaluateApplyService;
    @Autowired
	private AccountLogService logService;
    @Autowired
    private BorrowTenderService tenderService;
    /**
	 * 用户找回密码
	 * @return
	 */
	public User getRestPassword(Map<String, String> map) {
		return (User) userDao.getObject("selectResetPassword", map);
	}
	
    
	/**@author xiangxiaoyan
	 * 分页查询
	 */

	@Override
	
	public PageModel getPagedList(Map<String,String> paramsMap) {

		 return userDao.getPage("selectUserList","selectUserTotalCount",paramsMap);
	}
	@Override
	
	public PageModel listUserMessage(Map<String,String> paramsMap) {

		 return userDao.getPage("selectUserMessage","selectUserMessageTotalCount",paramsMap);
	}
	
	public PageModel getPageModel(Map<String, Object> param,Integer curPage){
		 PageModel pageModel=null;
		 PageModel pageModel1=new  PageModel(curPage);  //设置当前页
		 if(param.containsKey("numPerPage")){
			 pageModel1.setPageSize(Integer.parseInt(param.get("numPerPage").toString()));
		 }
		 Map<String, Object> result=new HashMap<String, Object>();
		 List<SysRole> roles =roleDao.selects("getSysRoleIdByUserId", param);
		 if(roles.size()>0){
			 result.put("roles", roles);
			 List<String> roleChildIds=roleDao.selects("getChildRoleIds", result);
			 param.put("roleChildIds", roleChildIds);
			 pageModel=userDao.getPage("shwoUserList","shwoUserListCount",param,pageModel1);
		 }
		 return pageModel;
	}
	
	
	public List<User> getlist(Map<String,String> params){
		return userDao.select("selectUserList", params);
	}

	/**@author xiangxiaoyan
	 * 删除
	 */

	@Override
	
	public boolean deleteById(User entity) {
		boolean bool=false;
		boolean flag=this.isAdminByUserId(entity.getId());
		try{
			if(!flag){
				bool=userDao.delete("deleteByPrimaryKeyandparam", entity);
				if(bool){
					bool=userDao.deletes("deleteUserRoleByUserIds",entity.getId());
				}else{
					throw new RuntimeException("删除用户信息错误！");
				}
			}else{
				throw new RuntimeException("该用户为超级管理员，不能删除");
			}
		}catch (Exception e) {
			
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return bool;
	}

	/**@author xiangxiaoyan
	 * 更新
	 */
	@Override
	
	public boolean saveOrUpdate(User user) {
		if(user.getId() == null){
			String avatarImg="/common/views/images/default-avatar.jpg";
			user.setAvatarImg(avatarImg);
		}
		this.userDao.saveOrUpdate(user);
		try {
			if(accountDao.selectByUserId(user.getId()) == null){ 
				accountDao.createUserAccount(user.getId()); //创建用户帐号
			}
			
		} catch (DataAccessException e) {
			
			log.error("saveOrUpdate user error:"+user,e);
			throw new RuntimeException("saveOrUpdate user error:"+user);
		}
		return true;
	}

	/**
	 * 查询
	 */
	@Override
	
	public User selectByUserUid(Object params) {
		// TODO Auto-generated method stub
		return this.userDao.selectEntity("selectByUserUid", params);
	}
	public User selectByUserUid2(Object params) {
		// TODO Auto-generated method stub
		return this.userDao.selectEntity("selectByUserUid2", params);
	}
	/**
	 * 查询
	 */
	@Override
	
	public User getById(Integer id) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(id);
	}

	/**xiangxiaoyan
	 * 查询
	 */
	@Override
	
	public User getByIdParam(Map<String, String> map) {
		return this.userDao.selectEntity("selectByPrimaryKeyandparam", map);
	}


	@Override
	
	public List<TreeModel> getUserRoleListByUserId(int userId) {
		// TODO Auto-generated method stub
		return treeModelDao.select("getUserRoleListByUserId", userId);
	}


	@Override
	
	public boolean addUserRole(Map<String, Object> param) {
		boolean bool=true;
		userDao.deletes("deleteUserRoleByUserId",param);
		String[] roleIds=(String[]) param.get("roles");
		if(null!=roleIds&&roleIds.length>0){
			List<String> roleList=roleDao.selects("getChildRoleIdss", param);
			param.put("roleList", roleList);
			userDao.insertObj("inserUserRoleList", param);
		}
		return bool;
	}


	/**
	 * 查询所有用户
	 */
	@Override
	
	public List<TreeModel> allFrontUserList() {
		
		return this.treeModelDao.select("selectAllFrontUser",null);
	}
	/**
	 * 查询用户名，邮箱，手机，证件号码是否存在
	 */
	@Override
	
	public User selectUserByParam(Map<String, String> param) {
		return this.userDao.selectEntity("selectUserByParam", param);
	}
	@Override
	
	public User getUserByName(Map<String, String> params) {
		return this.userDao.selectEntity("selectUserByName", params);
	}
	@Override
	
	public boolean isAdminByUserId(int userId) {
		boolean bool=false;
		User user=userDao.selectEntity("isAdminByUserId", userId);
		if(null!=user){
			bool=true;
		}
		return bool;
	}
	@Override
	
	public boolean updateUser(User user, String creditCode, Integer userId,Integer loginUserId,Integer status) {
		boolean flag=false;
		try{
			flag=this.userDao.saveOrUpdate(user);
			System.out.println(status==2);
			//2状态为通过
			if(flag&&status==2){
				this.creditLogService.addCreditLog(creditCode, userId, loginUserId);
				this.userCreditService.addUserCredit(creditCode, userId, loginUserId);
				
			}
		}catch(Exception e){
			flag=false;
			
			throw new RuntimeException("认证信息出错");
		}
		return flag;
	}
	@Override
	
	public boolean deleteFrontUserById(User entity) {
		boolean flag=false;
		try{
			flag=this.userAccountService.deleteByUserId(entity.getId());
			this.autotenderRulesService.deleteByUserId(entity.getId());
			/*this.vipUserService.deleteByUserId(entity.getId());
			this.userEvaluateApplyService.deleteByUserId(entity.getId());
			this.userEvaluateService.deleteByUserId(entity.getId());*/
			
			flag=userDao.delete("deleteByPrimaryKeyandparam", entity);
		}catch (Exception e) {
			
		}
		return flag;
	}
	@Override
	
	public boolean selectByStatus(Map<String, Object> param) {
		User user=this.userDao.selectEntity("selectUserByStatus", param);
		if (user==null) {
			return true;
		}else{
			return false;
		}
	}
	@Override
	
	public List<User> selectUserByRoleName(Map<String, String> param) {
		return this.userDao.select("selectByRoleName", param);
	}
	@Override
	
	public PageModel getUserByInvitePage(Map<String, String> param) {
		return userDao.getPage("selectByInviteUserId", "selectInviteUserIdCount", param);
	}
	@Override
	
	public boolean updateByPrimaryKeySelective(User user) {
		return userDao.updateByPrimaryKeySelective(user);
	}
	@Override
	
	public boolean updateUserRate(User user, String creditCode, Integer userId,
			Integer loginUserId, int status) {
		boolean b=this.updateUser(user, creditCode, userId, loginUserId, status);
		if(b && status == User.REALNAME_PASS){
			//实名认证扣费
			UserAccount userAccount=userAccountService.getByUserId(user.getId());
			BigDecimal availableMoney=userAccount.getAvailableMoney();
			BigDecimal sysAuthRate=SysCacheUtils.getSysFeesRate().getSysAuthRate();
			//可用余额-扣费>=0
			if(CompareUtils.greaterThanAndEqualsZero(availableMoney.subtract(sysAuthRate))){
				//实名认证费用是否大于0
				if(CompareUtils.greaterThanZero(sysAuthRate)){
					userAccount.setAllMoney(userAccount.getAllMoney().subtract(sysAuthRate));
					userAccount.setAvailableMoney(userAccount.getAvailableMoney().subtract(sysAuthRate));
					b=userAccountService.updateByPrimaryKeySelective(userAccount);
					if(b){//管理员添加费用
						UserAccount adminAccount=userAccountDao.selectAdminAccount();
						adminAccount.setAllMoney(adminAccount.getAllMoney().add(sysAuthRate));
						adminAccount.setAvailableMoney(adminAccount.getAvailableMoney().add(sysAuthRate));
						b=userAccountDao.updateByPrimaryKeySelective(adminAccount);
					}
					if(b){//添加资金记录
						b= accountLogService.add(userAccount, AccountLog.TRADE_CODE_REALNAME, sysAuthRate, new BigDecimal(0), Constants.ADMIN_USER_ID
									, "用户["+user.getUserAccount()+"]申请实名认证，扣费"+sysAuthRate+"元。"
									, RequestUtils.getIpAddr());
					}
				}
			}else {
				return false;
			}
		}
		return b;
	}
	@Override
	public Integer getAllUsers() {
		return userDao.getTotalCount("countAllUsers", null);
	}
	@Override
	public User selectByPrimaryKey(Integer id) {
		return userDao.selectByPrimaryKey(id);
	}
	@Override
	public User checkUserByEmail(Map<String, String> param) {
		// TODO Auto-generated method stub
		return userDao.selectEntity("checkUserByEmail", param);
	}
	
	@Override
	public User checkUserByEmail2(Map<String, String> param) {
		// TODO Auto-generated method stub
		return userDao.selectEntity("checkUserByEmail2", param);
	}
	
	@Override
	public void registerAwardAttestation(Integer userId){
		//注册奖励
		User user=this.getById(userId);
		if(user.getRealnameStatus()==User.REALNAME_PASS&&user.getEmailStatus()==User.EMAIL_PASS&&user.getPhoneStatus()==User.PHONE_PASS){
			//添加注册奖励
			SysFeesRate fee = SysCacheUtils.getSysFeesRate();
			Integer registerAwardType=fee.getSysRegisteredType();
			if(registerAwardType.equals(SysFeesRate.typeAttestation)){
				userAccountService.addRegisterAward(userId);
			}
		}
	}
	@Override
	public PageModel selectForSelectedList(Map<String,String> m){
		return this.userDao.getPage("selectForSelectedList", m);
	}
	
	
	
	/**
	 * 注册奖励
	 * @param userId
	 */
	@Override
	public void registerAward(Integer userId){
		//判断该用户是否有过注册奖励
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("tradeCode", AccountLog.TRADE_CODE_REGISTER_FEE);
	    Integer count=logService.queryRegister(params);
	    if(count==0){
	    	//查询该用户累计投资金额
	    	params.clear();
	    	params.put("userId", userId);
	    	params.put("tenderStatus", new Integer [] {BorrowTender.STATUS_OVERDUE,BorrowTender.STATUS_REPAYING,BorrowTender.STATUS_REPAYED});
	    	BigDecimal tenderMoney=tenderService.sumTenderMoney(params);
	    	//添加注册奖励
			SysFeesRate fee = SysCacheUtils.getSysFeesRate();
			Integer registerAwardType=fee.getSysRegisteredType();
			if(registerAwardType.equals(SysFeesRate.typeTender)){
				if(CompareUtils.greaterEquals(tenderMoney, fee.getSysRegisteredTenderMoney())){
					userAccountService.addRegisterAward(userId);
				}
			}
	    }
	}
	@Override
	public User getUserByUid(Map<String, String> param) {
		// TODO Auto-generated method stub
		return userDao.selectEntity("getUserByUid", param);
	}
	@Override
	public User findUserByPhone(String phone) {
		// TODO Auto-generated method stub
		return userDao.selectEntity("findUserByPhone", phone);
	}
	
	/**
	 * 统计用户注册数量
	 * @param date
	 * 			查询条件注册时间
	 * @return Integer
	 */
	public Integer summaryRegisterCount(Date date) {
		return userDao.summaryRegisterCount(date);
	}
	
	/**
	 * 统计待审核的认证信息数量
	 * @param status
	 * @return
	 */
	public Integer summaryNoCheckCount() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("realname_status", User.REALNAME_APPLY);
		map.put("email_status", User.EMAIL_APPLY);
		map.put("phone_status", User.PHONE_APPLY);
		map.put("video_status", User.VIDEO_APPLY);
		map.put("scene_status", User.SCENE_APPLY);
		return userDao.summaryNoCheckCount(map);
	}
	
	/**
	 * 用户注册统计
	 * @return
	 */
	public List<Map<String, Object>> registerSummary() {
		return userDao.registerSummary();
	}
	
	

	public List<User> selectUserListToExcel(Map<String, String> map){
		return userDao.selectUserListToExcel(map);
	}
}
