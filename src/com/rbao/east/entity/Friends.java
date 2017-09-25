package com.rbao.east.entity;

import java.util.Date;

public class Friends extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	public static final Integer TYPE_FRIEND = 1;//好友
	public static final Integer TYPE_BLACKLIST = 2;//黑名单blacklist
	
    private Integer id;

    private Integer userId;

    private Integer friendsUserid;

    private String friendsUsername;

    private Integer friendsStatus;

    private Integer friendsType;

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

    public String getFriendsUsername() {
        return friendsUsername;
    }

    public void setFriendsUsername(String friendsUsername) {
        this.friendsUsername = friendsUsername == null ? null : friendsUsername.trim();
    }

    public Integer getFriendsStatus() {
        return friendsStatus;
    }

    public void setFriendsStatus(Integer friendsStatus) {
        this.friendsStatus = friendsStatus;
    }

    public Integer getFriendsType() {
        return friendsType;
    }

    public void setFriendsType(Integer friendsType) {
        this.friendsType = friendsType;
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