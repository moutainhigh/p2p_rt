package com.rbao.east.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.TreeModel;
import com.rbao.east.entity.User;
/**
 * 用户
 * */
public interface UserService {
	/**
	 * 用户找回密码
	 * @return
	 */
	public User getRestPassword(Map<String, String> map);
	/**
	 * yan
	 * 注册奖励 投资达到一定金额
	 * @param userId
	 */
	public void registerAward(Integer userId);
	/**
	 * 认证通过给注册奖励
	 * @param userId
	 */
	public void registerAwardAttestation(Integer userId);
	
	public PageModel getPagedList(Map<String,String> paramsMap);
	/*查看资料完整度*/
	public PageModel listUserMessage(Map<String,String> paramsMap);
	
	public User getByIdParam(Map<String, String> map);
	
	public List<User> getlist(Map<String,String> params);

	public User getUserByName(Map<String,String> params);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public User getById(Integer id);
	/**
	 * 删除
	 * @param entity
	 * @return
	 */
	public boolean deleteById(User entity);
	/**
	 * 通过id删除前台用户
	 * @param entity
	 * @return
	 */
	public boolean deleteFrontUserById(User entity);
	/**
	 * 更新操作
	 * @param user
	 * @return
	 */
	public boolean saveOrUpdate(User user) ;
	/**
	 * 通过用户id查询
	 * @param params
	 * @return
	 */
	public User selectByUserUid(Object params);
	public User selectByUserUid2(Object params);
	/**
	 * 修改用户，添加用户积分，积分记录
	 * @param user用户信息
	 * @param creditCode积分code
	 * @param userId用户Id
	 * @param loginUserId当前登录Id
	 * @param status认证状态
	 * @return
	 */
	public boolean updateUser(User user, String creditCode, Integer userId,Integer loginUserId,Integer status);

	public PageModel getPageModel(Map<String, Object> param,Integer curPage);
	

	public List<TreeModel> getUserRoleListByUserId(int userId);
	
	public boolean addUserRole(Map<String, Object> param);
	
	public List<TreeModel> allFrontUserList();
	
	/**
	 * 查询用户名，邮箱，手机，证件号码是否存在
	 * @param param
	 * @return
	 */
	public User selectUserByParam(Map<String, String> param);
	
	public boolean isAdminByUserId(int userId);
	
	//查询用户是否认证过
	public boolean selectByStatus(Map<String, Object> param);
	
	/**
	 * 根据角色名称查询用户信息
	 * @param roleName
	 * @return
	 */
	public List<User> selectUserByRoleName(Map<String, String> param);
	
	/**
	 * 根据邀请人查询
	 * @param param
	 * @return
	 */
	public PageModel getUserByInvitePage(Map<String, String> param);
	
	
	public boolean updateByPrimaryKeySelective(User user);
	public boolean updateUserRate(User user, String realname, Integer id,
			Integer id2, int parseInt);
	
	//统计前台用户
	public Integer getAllUsers();
	
	User selectByPrimaryKey(Integer id);
	
	public User checkUserByEmail(Map<String, String> param);
	public User checkUserByEmail2(Map<String, String> param);
	public User getUserByUid(Map<String, String> param);
	public User findUserByPhone(String phone);
	PageModel selectForSelectedList(Map<String, String> m);
	
	/**
	 * 统计用户注册数量
	 * @param date
	 * 			查询条件注册时间
	 * @return Integer
	 */
	public Integer summaryRegisterCount(Date date);
	
	/**
	 * 统计待审核的认证信息数量
	 * @return
	 */
	public Integer summaryNoCheckCount();
	
	/**
	 * 用户注册统计
	 * @return
	 */
	public List<Map<String, Object>> registerSummary();
	
	/**
	 * 导出用户信息
	 * @param map
	 * @return
	 */
	public List<User> selectUserListToExcel(Map<String, String> map);
	
}
