package com.rbao.east.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.FriendsDao;
import com.rbao.east.entity.Friends;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.User;
import com.rbao.east.service.FriendsService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.RequestUtils;

@Service
@Transactional
public class FriendsServiceImpl implements FriendsService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FriendsDao friendsDao;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private UserService userService;

	@Override
	public PageModel getFriendsList(Map<String, String> param) {
		return friendsDao.getPage("selectFriendsList", "selectFriendsCount", param);
	}

	@Override
	public Friends getFriendById(int id) {
		return friendsDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveFriend(Friends friends) {
		return friendsDao.saveOrUpdate(friends);
	}

	@Override
	public boolean deleteFriend(Integer id) {
		Friends friend = this.getFriendById(id);
		User user = userService.getById(friend.getUserId());
		try{
			if(friend.getFriendsType().equals(Friends.TYPE_FRIEND)){
				if(friendsDao.deleteByPrimaryKey(id)){
					MessageCenter center = new MessageCenter();
					center.setMessageContent("成功解除与用户"+friend.getFriendsUsername()+"的好友关系。");
					center.setMessageTitle("解除好友关系");
					center.setMessageSendIp(RequestUtils.getIpAddr());
					center.setReceiveUserId(friend.getUserId());
					center.setSendUserId(Constants.ADMIN_USER_ID);
					messageCenterService.send(center, Notice.FRIEND_END);
				}
			}
			if(friend.getFriendsType().equals(Friends.TYPE_BLACKLIST)){
				if(friendsDao.deleteByPrimaryKey(id)){
					MessageCenter center = new MessageCenter();
					center.setMessageAddress(user.getUserEmail());
					center.setMessageContent("成功将用户"+friend.getFriendsUsername()+"从黑名单列表删除。");
					center.setMessageTitle("移除黑名单");
					center.setMessageSendIp(RequestUtils.getIpAddr());
					center.setReceiveUserId(friend.getUserId());
					center.setSendUserId(Constants.ADMIN_USER_ID);
					messageCenterService.send(center, Notice.FRIEND_END);
				}
			}
			return true;
		}catch (Exception e) {
			
			return false;
		}
	}
	
}
