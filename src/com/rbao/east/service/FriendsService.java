package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Friends;
/**
 * 好友
 * */
public interface FriendsService {

	/**
	 * 分页查看好友信息
	 * @param param
	 * @return
	 */
	public PageModel getFriendsList(Map<String, String> param);
	
	/**
	 * 根据Id查看
	 * @param id
	 * @return
	 */
	public Friends getFriendById(int id);
	
	/**
	 * 保存好友
	 * @param friends
	 * @return
	 */
	public boolean saveFriend(Friends friends);
	
	/**
	 * 删除好友
	 * @param id
	 * @return
	 */
	public boolean deleteFriend(Integer id);
	
}
