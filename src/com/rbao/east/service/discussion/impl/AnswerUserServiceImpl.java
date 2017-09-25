package com.rbao.east.service.discussion.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.AnswerUserDao;
import com.rbao.east.entity.AnswerUser;
import com.rbao.east.service.AnswerUserService;

@Service
@Transactional
public class AnswerUserServiceImpl implements AnswerUserService{
	
	@Autowired
	private AnswerUserDao answerUserDao;

	@Override
	public boolean deleteByPrimaryKey(Integer id) {
		return answerUserDao.deleteByPrimaryKey(id);
	}

	@Override
	public AnswerUser selectByPrimaryKey(Integer id) {
		return answerUserDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveOrUpdate(AnswerUser answerUser) {
		return answerUserDao.saveOrUpdate(answerUser);
	}

	@Override
	public List<AnswerUser> getAnswerUsers(Map<String, String> params) {
		return answerUserDao.select("getAnswerUsers", params);
	}

	@Override
	public Integer getAnswerUsersCount(Map<String, String> params) {
		return answerUserDao.getTotalCount("getAnswerUsersCount", params);
	}

	@Override
	public boolean deleteByQuestionId(Integer questionId) {
		return answerUserDao.deletes("deleteByQuestionId", questionId);
	}

	@Override
	public boolean deleteByAnswerId(Integer answerId) {
		return answerUserDao.deletes("deleteByAnswerId", answerId);
	}

}
