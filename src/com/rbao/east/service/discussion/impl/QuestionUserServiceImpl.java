package com.rbao.east.service.discussion.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.QuestionUserDao;
import com.rbao.east.entity.QuestionUser;
import com.rbao.east.service.QuestionUserService;

@Service
@Transactional
public class QuestionUserServiceImpl implements QuestionUserService {

	@Autowired
	private QuestionUserDao questionUserDao;

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return questionUserDao.deleteByPrimaryKey(id);
	}

	@Override
	public QuestionUser selectByPrimaryKey(Integer id) {
		return questionUserDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveOrUpdate(QuestionUser questionUser) {
		return questionUserDao.saveOrUpdate(questionUser);
	}

	@Override
	public List<QuestionUser> getQuestionUsers(Map<String, String> params) {
		return questionUserDao.select("getQuestionUsers", params);
	}

	@Override
	public Integer getQuestionUsersCount(Map<String, String> params) {
		return questionUserDao.getTotalCount("getQuestionUsersCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getUserhaveSeeQuestionIds(Integer userId) {
		return questionUserDao.selects("getUserhaveSeeQuestionIds", userId);
	}

	@Override
	public boolean deleteByQuestionId(Integer questionId) {
		return questionUserDao.deletes("deleteByQuestionId", questionId);
	}

}
