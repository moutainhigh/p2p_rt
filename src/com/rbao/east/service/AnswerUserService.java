package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.AnswerUser;
/**
 * 回答信息人
 * */
public interface AnswerUserService {

	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @Description: 删除
	* @return boolean    返回类型
	* @throws
	 */
	boolean deleteByPrimaryKey(Integer id);

	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description: 查询记录
	* @return AnswerUser    返回类型
	* @throws
	 */
	AnswerUser selectByPrimaryKey(Integer id);

	/**
	 * 
	* @Title: saveOrUpdate
	* @Description: 更新记录
	* @return boolean    返回类型
	* @throws
	 */
	boolean saveOrUpdate(AnswerUser answerUser);
	
	/**
	 * 根据条件查询得到list
	 */
	List<AnswerUser> getAnswerUsers(Map<String, String> params);
	
	/**
	 * 根据条件查询列表总记录数
	 */
	Integer getAnswerUsersCount(Map<String, String> params);
	/**
	 * 
	* @Title: deleteByQuestionId
	* @Description:根据id 删除记录
	* @return boolean    返回类型
	* @throws
	 */
	boolean deleteByQuestionId(Integer questionId);
	
	/**
	 * 
	* @Title: deleteByAnswerId
	* @Description: 根据主键 删除id
	* @return boolean    返回类型
	* @throws
	 */
	boolean deleteByAnswerId(Integer answerId);
}
