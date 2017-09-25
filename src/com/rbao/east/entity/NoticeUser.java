package com.rbao.east.entity;

public class NoticeUser extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Short NOTICE_SEND_YES = 1;//发送消息
	public static final Short NOTICE_SEND_NO = 2;//不发送消息

	private Short id;

    private Short userId;

    private Short noticeId;

    private Short message;

    private Short email;

    private Short phone;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Short getUserId() {
        return userId;
    }

    public void setUserId(Short userId) {
        this.userId = userId;
    }

    public Short getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Short noticeId) {
        this.noticeId = noticeId;
    }

    public Short getMessage() {
        return message;
    }

    public void setMessage(Short message) {
        this.message = message;
    }

    public Short getEmail() {
        return email;
    }

    public void setEmail(Short email) {
        this.email = email;
    }

    public Short getPhone() {
        return phone;
    }

    public void setPhone(Short phone) {
        this.phone = phone;
    }
}