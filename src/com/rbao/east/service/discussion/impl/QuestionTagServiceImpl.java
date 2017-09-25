package com.rbao.east.service.discussion.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.QuestionTagDao;
import com.rbao.east.entity.QuestionTag;
import com.rbao.east.service.QuestionTagService;

@Service
@Transactional
public class QuestionTagServiceImpl implements QuestionTagService{
	
	@Autowired
	private QuestionTagDao questionTagDao;

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return questionTagDao.deleteByPrimaryKey(id);
	}

	@Override
	public QuestionTag selectByPrimaryKey(Integer id) {
		return questionTagDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveOrUpdate(QuestionTag questionTag) {
		return questionTagDao.saveOrUpdate(questionTag);
	}

	@Override
	public List<QuestionTag> getQuestionTags(Map<String, String> params) {
		return questionTagDao.select("getQuestionTags", params);
	}

	@Override
	public Integer getQuestionTagsCount(Map<String, String> params) {
		return questionTagDao.getTotalCount("getQuestionTagsCount", params);
	}

	@Override
	public List<Map> getTagCount() {
		return questionTagDao.selects("getTagCount", null);
	}

	@Override
	public Integer getTagCountByTagId(Integer tagId) {
		return questionTagDao.getTotalCount("getTagCountByTagId", tagId);
	}

	@Override
	public List getTagsByQid(Integer questionId) {
		return questionTagDao.select("getTagsByQid", questionId);
	}

	@Override
	public boolean deleteByQuestionId(Integer questionId) {
		return questionTagDao.deletes("deleteByQuestionId", questionId);
	}

}
