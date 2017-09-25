package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Question;

/**
 * 问答
 * */	
public interface QuestionService {
/**
 * 删除
 * @param id
 * @return
 */
	boolean deleteByPrimaryKey(Integer id);

	/**
	 * 
	* @Title: selectByPrimaryKey
	* @Description:查询
	* @return    Question返回类型
	* @throws
	 */
	Question selectByPrimaryKey(Integer id);
	/**
	 * 更新操作
	 * @param question
	 * @return
	 */
	boolean saveOrUpdate(Question question);
	
	/**
	 * 根据条件查询得到list
	 */
	List<Question> getQuestions(Map<String, String> params);
	
	/**
	 * 根据条件查询列表总记录数
	 */
	Integer getQuestionsCount(Map<String, String> params);

	/**
	 * 
	* @Title: getUnreadQuestions
	* @Description:未读
	* @return   List<Question> 返回类型
	* @throws
	 */
	List<Question> getUnreadQuestions(Map<String, Object> params);
	/**
	 * 分页
	 * @param params
	 * @return
	 */
	PageModel getPage(Map<String, String> params);
	
	/**
	 * 获取问问题的人数量
	 */
	Integer getQuestionUsersCount();
	/**
	 * 
	* @Title: getUnreadQuestionsCount
	* @Description: 未读数量
	* @return   Integer返回类型 
	* @throws
	 */
	Integer getUnreadQuestionsCount(Map<String, Object> params);
	/**
	 * 
	* @Title: getQuestionByTagPage
	* @Description: 分页
	* @return    PageModel返回类型
	* @throws
	 */
	PageModel getQuestionByTagPage(Map<String, String> params);
	/**
	 * 
	* @Title: getUnreadQuestionPage
	* @Description: 未读数量分页
	* @return    PageModel
	* @throws
	 */
	PageModel getUnreadQuestionPage(Map<String, Object> params);
	/**
	 * 
	* @Title: getUserAnswerQuestionPage
	* @Description: 回答分页
	* @return  PageModel 返回类型  
	* @throws
	 */
	PageModel getUserAnswerQuestionPage(Map<String, String> params);
	
}
