package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.entity.UserCredit;

/**
 * 用户积分
 * */	
public interface UserCreditService {
	/**
	 * 分页
	 * @param paramsMap
	 * @return
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public UserCredit getById(Integer id);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param userCredit
	 * @return
	 */
	public boolean save(UserCredit userCredit) ;
	/**
	 * 添加
	 * @param userCredit
	 * @return
	 */
	public boolean add(UserCredit userCredit) ;
	/**
	 * 
	* @Title: seletByUserId
	* @Description: 根据用户id查询
	* @return    List<UserCredit> 返回类型
	* @throws
	 */
	public List<UserCredit> seletByUserId(Integer userId);
	/**
	 * 
	* @Title: getByUserId
	* @Description: 根据用户id查询
	* @return    UserCredit 返回类型
	* @throws
	 */
	public UserCredit getByUserId(Integer userId);
	
	/**
	 * 添加或修改用户积分
	 * @param creditCode积分code
	 * @param userId用户Id
	 * @param loginUserId当前登录用户Id
	 * @return
	 */
	public boolean addUserCredit(String creditCode, Integer userId,Integer loginUserId,Map<String,Object>... params);
	
	/**
	 * 添加或修改用户积分
	 * @param creditCode积分code
	 * @param userId用户Id
	 * @param loginUserId当前登录用户Id
	 * @return
	 */
	public boolean addUserCredit(String creditCode, Integer userId,Map<String,Object>... params);
	
	public boolean addUserCreditAndLog(String creditCode, Integer userId,Map<String,Object>... params) ;

	public List getCreditByUserId(Map<String, String> param);

	/**
	 * 减掉可用积分
	 * 当积分不够时会抛异常
	 * @param userId
	 * @param castCredit
	 * @param remark
	 * @return
	 */
	JsonResult subtractCreditValueUsable(Integer userId, int castCredit,String remark);

}
