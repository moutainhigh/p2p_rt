package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.QuestionTag;

/**
 * 问答标签
 * */	
public interface QuestionTagService {
/**
 * 删除
 * @param id
 * @return
 */
	boolean deleteByPrimaryKey(Integer id);
/**
 * 查询
 * @param id
 * @return
 */
	QuestionTag selectByPrimaryKey(Integer id);
	/**
	 * 更新操作
	 * @param questionTag
	 * @return
	 */
	boolean saveOrUpdate(QuestionTag questionTag);
	
	/**
	 * 根据条件查询得到list
	 */
	List<QuestionTag> getQuestionTags(Map<String, String> params);
	
	/**
	 * 根据条件查询列表总记录数
	 */
	Integer getQuestionTagsCount(Map<String, String> params);
	/**
	 * 
	* @Title: getTagCountByTagId
	* @Description: 得到tag数量
	* @return    List<Map> 返回类型
	* @throws
	 */
	List<Map> getTagCount();
	/**
	 * 
	* @Title: getTagCountByTagId
	* @Description: 得到tag数量
	* @return    Integer 返回类型
	* @throws
	 */
	Integer getTagCountByTagId(Integer tagId);
	/**
	 * 
	* @Title: getTagsByQid
	* @Description: 
	* @return    List 返回类型
	* @throws
	 */
	List getTagsByQid(Integer questionId);
	/**
	 * 
	* @Title: deleteByQuestionId
	* @Description: 删除
	* @return    boolean 返回类型
	* @throws
	 */
	boolean deleteByQuestionId(Integer questionId);
}
