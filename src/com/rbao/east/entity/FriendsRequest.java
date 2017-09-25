package com.rbao.east.entity;

import java.util.Date;

public class FriendsRequest extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	public static final Integer STATUS_DEFAULT = 0;//发送添加好友请求默认为0
	public static final Integer STATUS_SUCCESS = 1;//同意添加为好友
	public static final Integer STATUS_FAIL = 2;//不同意添加为好友
	public static final Integer STATUS_BLACKLIST = 3;//添加为黑名单
	
    private Integer id;

    private Integer userId;

    private Integer friendsUserid;

    private Integer friendsStatus;

    private String friendsContent;

    private Date friendsAddtime;

    private String friendsAddip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendsUserid() {
        return friendsUserid;
    }

    public void setFriendsUserid(Integer friendsUserid) {
        this.friendsUserid = friendsUserid;
    }

    public Integer getFriendsStatus() {
        return friendsStatus;
    }

    public void setFriendsStatus(Integer friendsStatus) {
        this.friendsStatus = friendsStatus;
    }

    public String getFriendsContent() {
        return friendsContent;
    }

    public void setFriendsContent(String friendsContent) {
        this.friendsContent = friendsContent == null ? null : friendsContent.trim();
    }

    public Date getFriendsAddtime() {
        return friendsAddtime;
    }

    public void setFriendsAddtime(Date friendsAddtime) {
        this.friendsAddtime = friendsAddtime;
    }

    public String getFriendsAddip() {
        return friendsAddip;
    }

    public void setFriendsAddip(String friendsAddip) {
        this.friendsAddip = friendsAddip == null ? null : friendsAddip.trim();
    }
}