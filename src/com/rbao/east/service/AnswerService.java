package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Answer;
/**
 * 问答
 * */
public interface AnswerService {

	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @Description: 删除答案
	* @return boolean    返回类型
	* @throws
	 */
	boolean deleteByPrimaryKey(Integer id);

	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description: 得到答案
	* @return Answer    返回类型
	* @throws
	 */
	Answer selectByPrimaryKey(Integer id);

	/**
	 * 
	* @Title: saveOrUpdate
	* @Description: 更新
	* @return boolean    返回类型
	* @throws
	 */
	boolean saveOrUpdate(Answer answer);
	
	/**
	 * 根据条件查询得到list
	 */
	List<Answer> getAnswers(Map<String, String> params);
	
	/**
	 * 根据条件查询列表总记录数
	 */
	Integer getAnswersCount(Map<String, String> params);
	/**
	 * 
	* @Title: getTopUser
	* @Description: 列表
	* @return List<Map>    返回类型
	* @throws
	 */
	List<Map> getTopUser(Map<String, String> params);
	/**
	 * 
	* @Title: getPage
	* @Description: 列表数据集
	* @return PageModel    返回类型
	* @throws
	 */
	PageModel getPage(Map<String, String> params);
	
	/**
	 * 获取回答问题的净用户数量
	 */
	Integer getAnswerUserCount();
	/**
	 * 
	* @Title: deleteByQuestionId
	* @Description: 删除答案
	* @return boolean    返回类型
	* @throws
	 */
	boolean deleteByQuestionId(Integer questionId);
}
