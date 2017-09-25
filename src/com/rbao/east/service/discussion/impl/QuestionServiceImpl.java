package com.rbao.east.service.discussion.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.QuestionDao;
import com.rbao.east.entity.Question;
import com.rbao.east.service.QuestionService;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionDao questionDao;

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return questionDao.deleteByPrimaryKey(id);
	}

	@Override
	public Question selectByPrimaryKey(Integer id) {
		return questionDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveOrUpdate(Question question) {
		return questionDao.saveOrUpdate(question);
	}

	@Override
	public List<Question> getQuestions(Map<String, String> params) {
		return questionDao.select("getQuestions", params);
	}

	@Override
	public Integer getQuestionsCount(Map<String, String> params) {
		return questionDao.getTotalCount("getQuestionsCount", params);
	}

	@Override
	public List<Question> getUnreadQuestions(Map<String, Object> params) {
		return questionDao.select("getUnreadQuestions", params);
	}

	@Override
	public PageModel getPage(Map<String, String> params) {
		return questionDao.getPage("getQuestions", "getQuestionsCount", params);
	}

	@Override
	public Integer getQuestionUsersCount() {
		return questionDao.getTotalCount("getQuestionUsersCount", null);
	}

	@Override
	public PageModel getQuestionByTagPage(Map<String, String> params) {
		return questionDao.getPage("getQuestionsByTagId", "getQuestionsCountByTagId", params);
	}

	@Override
	public PageModel getUnreadQuestionPage(Map<String, Object> params) {
		return questionDao.getPage("getUnreadQuestions", "getUnreadQuestionsCount", params);
	}

	@Override
	public Integer getUnreadQuestionsCount(Map<String, Object> params) {
		return questionDao.getTotalCount("getUnreadQuestionsCount", params);
	}

	@Override
	public PageModel getUserAnswerQuestionPage(Map<String, String> params) {
		return questionDao.getPage("getUserAnswerQuestions", "getUserAnswerQuestionsCount", params);
	}

}
