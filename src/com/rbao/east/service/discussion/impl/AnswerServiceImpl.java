package com.rbao.east.service.discussion.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AnswerDao;
import com.rbao.east.entity.Answer;
import com.rbao.east.service.AnswerService;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService{
	
	@Autowired
	private AnswerDao answerDao;

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return answerDao.deleteByPrimaryKey(id);
	}

	@Override
	public Answer selectByPrimaryKey(Integer id) {
		return answerDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveOrUpdate(Answer answer) {
		return answerDao.saveOrUpdate(answer);
	}

	@Override
	public List<Answer> getAnswers(Map<String, String> params) {
		return answerDao.select("getAnswers", params);
	}

	@Override
	public Integer getAnswersCount(Map<String, String> params) {
		return answerDao.getTotalCount("getAnswersCount", params);
	}

	@Override
	public List<Map> getTopUser(Map<String, String> params) {
		return answerDao.selects("getTopUser", params);
	}

	@Override
	public PageModel getPage(Map<String, String> params) {
		return answerDao.getPage("getAnswers", "getAnswersCount", params);
	}

	@Override
	public Integer getAnswerUserCount() {
		return answerDao.getTotalCount("getAnswerUserCount", null);
	}

	@Override
	public boolean deleteByQuestionId(Integer questionId) {
		return answerDao.deletes("deleteByQuestionId", questionId);
	}

}
