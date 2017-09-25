package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.QuestionUser;

public interface QuestionUserService {
/**
 * 删除
 * @param id 参数
 * @return
 */
	boolean deleteByPrimaryKey(Integer id);

	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description: 根据主键查询
	* @return  QuestionUser 返回类型  
	* @throws
	 */
	QuestionUser selectByPrimaryKey(Integer id);
/**
 * 更新操作
 * @param questionUser 参数
 * @return
 */
	boolean saveOrUpdate(QuestionUser questionUser);

	/**
	 * 根据条件查询得到list
	 */
	List<QuestionUser> getQuestionUsers(Map<String, String> params);

	/**
	 * 根据条件查询列表总记录数
	 */
	Integer getQuestionUsersCount(Map<String, String> params);

	/**
	 * 
	* @Title: getUserhaveSeeQuestionIds
	* @Description: 已看问题数量
	* @return  List<Integer>  返回类型  
	* @throws
	 */
	List<Integer> getUserhaveSeeQuestionIds(Integer userId);
	/**
	 * 删除
	 * @param questionId 参数
	 * @return
	 */
	boolean deleteByQuestionId(Integer questionId);

}
