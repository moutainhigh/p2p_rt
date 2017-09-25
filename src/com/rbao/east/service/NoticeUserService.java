package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.NoticeUser;

/**
 * 用户提醒设置
 */
public interface NoticeUserService {

	/**
	 * 根据用户Id和提醒Id查询
	 * @param userId 用户Id
	 * @param noticeId 提醒Id
	 * @return
	 */
	public NoticeUser getNoticeUserByUserIdAndNoticeId(Integer userId,Integer noticeId);
	
	/**
	 * 保存用户提醒设置
	 * @param paramsMap
	 * @param userId 
	 * @return
	 */
	public boolean saveNoticeUser(Map<String, NoticeUser> paramsMap,Integer userId);
	
	/**
	 * 根据用户Id查询用户提醒设置
	 * @param userId
	 * @return
	 */
	public List getNoticeUsersByUserId(Integer userId);
	
	/**
	 * 根据用户Id删除用户提醒设置
	 * @param userId
	 * @return
	 */
	public boolean deleteNoticeUsersByUserId(Integer userId);
}
