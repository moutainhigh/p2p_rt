package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.FriendsRequest;

public interface FriendsRequestService {

	/**
	 * 分页查看好友请求信息
	 * @param param
	 * @return
	 */
	public PageModel getFriendsRequestList(Map<String, String> param);
	
	/**
	 * 根据Id查看好友请求
	 * @param id
	 * @return
	 */
	public FriendsRequest getFriendsRequestById(int id);
	
	/**
	 * 保存好友请求信息
	 * @param friendsRequest
	 * @return
	 */
	public boolean saveFriendsRequest(FriendsRequest friendsRequest);
	
}
