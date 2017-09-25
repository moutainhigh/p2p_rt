package com.rbao.east.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.FriendsRequestDao;
import com.rbao.east.entity.Friends;
import com.rbao.east.entity.FriendsRequest;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.User;
import com.rbao.east.service.FriendsRequestService;
import com.rbao.east.service.FriendsService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.RequestUtils;

@Service
@Transactional
public class FriendsRequestServiceImpl implements FriendsRequestService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FriendsRequestDao friendsRequestDao;
	@Autowired
	private FriendsService friendsService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageCenterService messageCenterService;

	@Override
	public PageModel getFriendsRequestList(Map<String, String> param) {
		return friendsRequestDao.getPage("selectFriendsRequestList", "selectFriendsRequestCount", param);
	}

	@Override
	public FriendsRequest getFriendsRequestById(int id) {
		return friendsRequestDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean saveFriendsRequest(FriendsRequest friendsRequest) {
		//同意添加为好友
		if(friendsRequest.getFriendsStatus() == FriendsRequest.STATUS_SUCCESS){
			Friends friends = new Friends();
			friends.setFriendsType(Friends.TYPE_FRIEND);
			friends.setUserId(friendsRequest.getUserId());
			friends.setFriendsUserid(friendsRequest.getFriendsUserid());
			User friendsUser = userService.getById(friendsRequest.getFriendsUserid());
			friends.setFriendsUsername(friendsUser.getUserAccount());
			User user = userService.getById(friendsRequest.getUserId());
			friends.setFriendsContent("用户"+friendsUser.getUserAccount()+"同意了"+user.getUserAccount()+"的好友请求，"+user.getUserAccount()+"添加好友成功。");
			try {
				friends.setFriendsAddip(RequestUtils.getIpAddr());
				friendsService.saveFriend(friends);
				MessageCenter center = new MessageCenter();
				center.setMessageContent("用户"+friendsUser.getUserAccount()+"同意好友请求。");
				center.setMessageSendIp(RequestUtils.getIpAddr());
				center.setReceiveUserId(user.getId());
				center.setSendUserId(Constants.ADMIN_USER_ID);
				center.setMessageTitle("添加好友"+friendsUser.getUserAccount());
				messageCenterService.send(center, Notice.FRIEND_YES);
			} catch (Exception e) {
				logger.info("添加好友失败。");
				
			}
		}
		//添加到黑名单
		if(friendsRequest.getFriendsStatus()== FriendsRequest.STATUS_BLACKLIST){
			Friends friends = new Friends();
			friends.setFriendsType(Friends.TYPE_BLACKLIST);
			friends.setUserId(friendsRequest.getFriendsUserid());
			friends.setFriendsUserid(friendsRequest.getUserId());
			User friendsUser = userService.getById(friendsRequest.getUserId());
			friends.setFriendsUsername(friendsUser.getUserAccount());
			User user = userService.getById(friendsRequest.getFriendsUserid());
			friends.setFriendsContent("用户"+friendsUser.getUserAccount()+"添加"+user.getUserAccount()+"到黑名单。");
			try {
				friends.setFriendsAddip(RequestUtils.getIpAddr());
				friendsService.saveFriend(friends);
			} catch (Exception e) {
				logger.info("添加为黑名单失败。");
				
			}
		}
		//发送好友请求
		if(friendsRequest.getFriendsStatus()== FriendsRequest.STATUS_DEFAULT){
			User user = userService.getById(friendsRequest.getUserId());
			User friendsUser = userService.getById(friendsRequest.getFriendsUserid());
			MessageCenter center = new MessageCenter();
			center.setMessageContent("向用户"+friendsUser.getUserAccount()+"发送好友请求。");
			center.setMessageSendIp(RequestUtils.getIpAddr());
			center.setReceiveUserId(user.getId());
			center.setSendUserId(Constants.ADMIN_USER_ID);
			center.setMessageAddress(user.getUserEmail());
			center.setMessageTitle("发送好友请求");
			messageCenterService.send(center, Notice.FRIEND_REQUEST);
		}
		//拒绝添加为好友
		if(friendsRequest.getFriendsStatus() == FriendsRequest.STATUS_FAIL){
			User user = userService.getById(friendsRequest.getUserId());
			User friendsUser = userService.getById(friendsRequest.getFriendsUserid());
			MessageCenter center = new MessageCenter();
			center.setMessageContent("用户"+friendsUser.getUserAccount()+"拒绝了您的好友请求。");
			center.setMessageSendIp(RequestUtils.getIpAddr());
			center.setReceiveUserId(user.getId());
			center.setSendUserId(Constants.ADMIN_USER_ID);
			center.setMessageAddress(user.getUserEmail());
			center.setMessageTitle("拒绝好友请求");
			messageCenterService.send(center, Notice.FRIEND_PASS);
		}
		return friendsRequestDao.saveOrUpdate(friendsRequest);
	}
	
}
