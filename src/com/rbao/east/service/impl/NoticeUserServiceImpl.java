package com.rbao.east.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.NoticeUserDao;
import com.rbao.east.entity.NoticeUser;
import com.rbao.east.service.NoticeUserService;

@Service
@Transactional
public class NoticeUserServiceImpl implements NoticeUserService {

	Logger logger = LoggerFactory.getLogger(NoticeUserServiceImpl.class);
	
	@Autowired
	private NoticeUserDao noticeUserDao;

	@Override
	public NoticeUser getNoticeUserByUserIdAndNoticeId(Integer userId, Integer noticeId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("noticeId", noticeId);
		return noticeUserDao.selectEntity("selectByUserIdAndNoticeId", params);
	}

	@Override
	public boolean saveNoticeUser(Map<String, NoticeUser> paramsMap,Integer userId) {
		Set<String> key = paramsMap.keySet();
		NoticeUser noticeUser;
		if(this.getNoticeUsersByUserId(userId).size()>0){
			this.deleteNoticeUsersByUserId(userId);
		}
		try{
			
			for (Iterator it = key.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            noticeUser = paramsMap.get(s);
	            noticeUserDao.saveOrUpdate(noticeUser);
	        }
			return true;
		}catch (Exception e) {
			
			return false;
		}
        
	}

	@Override
	public List getNoticeUsersByUserId(Integer userId) {
		return noticeUserDao.select("selectByUserId", userId);
	}

	@Override
	public boolean deleteNoticeUsersByUserId(Integer userId) {
		return noticeUserDao.deletes("deleteByUserId", userId);
	}
}
