package com.rbao.east.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Notice extends BaseEntity{
	
	private static final long serialVersionUID = 7142721288364713307L;
	public static final Map<Short,String> ALL_MESSAGE = new HashMap<Short,String>();//短消息
	public static final Map<Short,String> ALL_EMAIL = new HashMap<Short,String>();//邮箱
	public static final Map<Short,String> ALL_PHONE = new HashMap<Short,String>();//手机短信
	public static final Map<Short,String> ALL_NOTICE_STATUS = new HashMap<Short,String>();
	
	//短消息
	public static final Short MESSAGE_REQUIRED_SELECTED = 1; //必选已选
	public static final Short MESSAGE_REQUIRED_UNSELECTED = 2;//必选未选
	public static final Short MESSAGE_CHOOSABLE_SELECTED = 3;//可选已选
	public static final Short MESSAGE_CHOOSABLE_UNSELECTED = 4;//可选未选
	
	//邮箱
	public static final Short EMAIL_REQUIRED_SELECTED = 1; //必选已选
	public static final Short EMAIL_REQUIRED_UNSELECTED = 2;//必选未选
	public static final Short EMAIL_CHOOSABLE_SELECTED = 3;//可选已选
	public static final Short EMAIL_CHOOSABLE_UNSELECTED = 4;//可选未选
	
	//手机短信
	public static final Short PHONE_REQUIRED_SELECTED = 1; //必选已选
	public static final Short PHONE_REQUIRED_UNSELECTED = 2;//必选未选
	public static final Short PHONE_CHOOSABLE_SELECTED = 3;//可选已选
	public static final Short PHONE_CHOOSABLE_UNSELECTED = 4;//可选未选
	
	//状态
	public static final Short NOTICE_STATUS_STOP = 0;//停用
	public static final Short NOTICE_STATUS_START = 1;//启用
	
	public static final String NOTICE_CODE_ADMIN_SEND = "ADMIN_SEND"; //后台管理员发送
	
	public static final String RECOMMEND_AWARD ="RECOMMEND_AWARD";//推广邀请奖励发送消息
	public static final String NOTICE_CODE_RECHARGE="RECHARGE";//充值发送消息
	public static final String NOTICE_CODE_EVALUATE_APPLAY_YES ="EVALUATE_APPLAY_YES"; //额度申请通过
	public static final String NOTICE_CODE_EVALUATE_APPLAY_NO ="EVALUATE_APPLAY_NO";//额度申请不通过
	public static final String VIP_YES="VIP_YES";
	public static final String VIP_NO="VIP_NO";
	public static final String REALNAME_YES="REALNAME_YES";
	public static final String REALNAME_NO="REALNAME_NO";
	public static final String PHONE_YES="PHONE_YES";
	public static final String PHONE_NO="PHONE_NO";
	public static final String EMAIL_YES="EMAIL_YES";
	public static final String EMAIL_NO="EMAIL_NO";
	public static final String VIDEO_YES="VIDEO_YES";
	public static final String VIDEO_NO="VIDEO_NO";
	public static final String SCENE_YES="SCENE_YES";
	public static final String SCENE_NO="SCENE_NO";
	public static final String ATTESTATIONAPPLY_YES="ATTESTATIONAPPLY_YES";//用户证件审核状态
	public static final String ATTESTATIONAPPLY_NO="ATTESTATIONAPPLY_NO";
	
	
	public static final String ExperienceGoid ="EXPERIENCEGOID";//体验金发送消息
	
	public static final String deductionMoney ="DEDUCTIONMONEY";//抵扣金发送消息
	//提醒设置时用到的code
	//1.资金站内信设置
	
	//新加
	public static final String BORROW_OVERDUE = "BORROW_OVERDUE";//标逾期
	public static final String BORROW_TRANSFER_SUCCESS = "BORROW_TRANSFER_SUCCESS";//债券转让成功
	public static final String BORROW_TRANSFER_FAILD = "BORROW_TRANSFER_FAILD";//债券转让失败
	
	public static final String WITHDRAW_YES = "WITHDRAW_YES";//提现审核通过
	public static final String WITHDRAW_NO = "WITHDRAW_NO";//提现审核不通过
	public static final String REDEEM_YES="REDEEM_YES";//赎回审核成功
	public static final String REDEEM_NO="REDEEM_NO";//赎回审核不成功
	public static final String RECHARGE = "RECHARGE";//网站充值
	public static final String MARGIN_THAW = "MARGIN_THAW";//保证金解冻margin_thaw
	public static final String RECHARGE_DOWN = "RECHARGE_DOWN";//线下充值recharge_down
//	public static final String VIP_YES = "VIP_YES";//VIP审核通过
//	public static final String VIP_NO = "VIP_NO";//VIP审核不通过
	//2.贷款者站内信设置
	public static final String BORROW_CREATED = "BORROW_CREATED";//借款标留言borrow_msg
	public static final String BORROW_END = "BORROW_END";//借款标流标borrow_end
	public static final String BORROW_YES = "BORROW_YES";//借款标初审通过borrow_yes
	public static final String BORROW_NO = "BORROW_NO";//借款标初审不通过borrow_no
	public static final String BORROW_JOIN = "BORROW_JOIN";//借款标有人投标时borrow_join
	public static final String BORROW_REVIEW_YES = "BORROW_REVIEW_YES";//借款标复审通过borrow_review_yes
	public static final String BORROW_REVIEW_NO = "BORROW_REVIEW_NO";//借款标复审不通过borrow_review_no
	public static final String BORROW_REPAY = "BORROW_REPAY";
	//3.投资者站内信设置
	public static final String LOAN_CREATED = "LOAN_CREATED";//投标的借款标修改内容loan_update
	public static final String LOAN_END = "LOAN_END";//投标的借款标流标loan_end
	public static final String LOAN_YES_ACCOUNT = "LOAN_YES_ACCOUNT";//借出成功，扣除冻结款loan_yes_account
	public static final String LOAN_NO_ACCOUNT = "LOAN_NO_ACCOUNT";//借出失败，解冻冻结款loan_no_account
	public static final String LOAN_PAY = "LOAN_PAY";//收到借款loan_pay
	public static final String LOAN_ADVANCED = "LOAN_ADVANCED";//网站垫付loan_advanced
	public static final String LOAN_ASSESS = "LOAN_ASSESS";//借款标评价loan_assess
	
	
	public static final String EVALUATE_APPLAY_NO = "EVALUATE_APPLAY_NO"; //额度申请不通过evaluate_applay_No
	public static final String EVALUATE_APPLAY_YES = "EVALUATE_APPLAY_YES"; //额度申请通过evaluate_applay_yes
	public static final String SYSTEM = "SYSTEM"; //系统提示system
	public static final String INFO_NO = "INFO_NO"; //资料审核不通过info_no
	public static final String INFO_YES = "INFO_YES"; //资料审核通过info_yes
	public static final String FRIEND_END = "FRIEND_END"; //解除好友关系friend_end
	public static final String FRIEND_PASS = "FRIEND_PASS"; //拒绝好友请求friend_pass
	public static final String FRIEND_YES = "FRIEND_YES"; //成为好友关系friend_yes
	public static final String FRIEND_REQUEST = "FRIEND_REQUEST"; //请求加为好友friend_request
	
	static{
		ALL_MESSAGE.put(MESSAGE_REQUIRED_SELECTED,"必选已选");
		ALL_MESSAGE.put(MESSAGE_REQUIRED_UNSELECTED,"必选未选");
		ALL_MESSAGE.put(MESSAGE_CHOOSABLE_SELECTED,"可选已选");
		ALL_MESSAGE.put(MESSAGE_CHOOSABLE_UNSELECTED,"可选未选");
		
		ALL_EMAIL.put(EMAIL_REQUIRED_SELECTED,"必选已选");
		ALL_EMAIL.put(EMAIL_REQUIRED_UNSELECTED,"必选未选");
		ALL_EMAIL.put(EMAIL_CHOOSABLE_SELECTED,"可选已选");
		ALL_EMAIL.put(EMAIL_CHOOSABLE_UNSELECTED,"可选未选");
		
		ALL_PHONE.put(PHONE_REQUIRED_SELECTED,"必选已选");
		ALL_PHONE.put(PHONE_REQUIRED_UNSELECTED,"必选未选");
		ALL_PHONE.put(PHONE_CHOOSABLE_SELECTED,"可选已选");
		ALL_PHONE.put(PHONE_CHOOSABLE_UNSELECTED,"可选未选");
		
		ALL_NOTICE_STATUS.put(NOTICE_STATUS_STOP, "停用");
		ALL_NOTICE_STATUS.put(NOTICE_STATUS_START, "启用");
	}
	
	private String viewMessage;
	
	private String viewEmail;
	
	private String viewPhone;
	
	private String viewNoticeStatus;
	
    private Integer id;

    private String noticeTitle;

    private String noticeCode;

    private Short noticeStatus;

    private Short noticeSequence;

    private Short noticeTypeId;

    private Short message;

    private Short email;

    private Short phone;

    private Date noticeAddtime;

	private String noticeAddip;
    
    private NoticeType noticeType ;

	public String getViewMessage() {
		return viewMessage;
	}

	public void setViewMessage(String viewMessage) {
		this.viewMessage = viewMessage;
	}

	public String getViewEmail() {
		return viewEmail;
	}

	public void setViewEmail(String viewEmail) {
		this.viewEmail = viewEmail;
	}

	public String getViewPhone() {
		return viewPhone;
	}

    public NoticeType getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(NoticeType noticeType) {
		this.noticeType = noticeType;
	}

	public void setViewPhone(String viewPhone) {
		this.viewPhone = viewPhone;
	}
	
    public String getViewNoticeStatus() {
		return viewNoticeStatus;
	}

	public void setViewNoticeStatus(String viewNoticeStatus) {
		this.viewNoticeStatus = viewNoticeStatus;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle == null ? null : noticeTitle.trim();
    }

    public String getNoticeCode() {
        return noticeCode;
    }

    public void setNoticeCode(String noticeCode) {
        this.noticeCode = noticeCode == null ? null : noticeCode.trim();
    }

    public Short getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Short noticeStatus) {
        this.noticeStatus = noticeStatus;
        setViewNoticeStatus(ALL_NOTICE_STATUS.get(noticeStatus));
    }

    public Short getNoticeSequence() {
        return noticeSequence;
    }

    public void setNoticeSequence(Short noticeSequence) {
        this.noticeSequence = noticeSequence;
    }

    public Short getNoticeTypeId() {
        return noticeTypeId;
    }

    public void setNoticeTypeId(Short noticeTypeId) {
        this.noticeTypeId = noticeTypeId;
    }

    public Short getMessage() {
        return message;
    }

    public void setMessage(Short message) {
        this.message = message;
        setViewMessage(ALL_MESSAGE.get(message));
    }

    public Short getEmail() {
        return email;
    }

    public void setEmail(Short email) {
        this.email = email;
        setViewEmail(ALL_EMAIL.get(email));
    }

    public Short getPhone() {
        return phone;
    }

    public void setPhone(Short phone) {
        this.phone = phone;
        setViewPhone(ALL_PHONE.get(phone));
    }

    public Date getNoticeAddtime() {
        return noticeAddtime;
    }

    public void setNoticeAddtime(Date noticeAddtime) {
        this.noticeAddtime = noticeAddtime;
    }

    public String getNoticeAddip() {
        return noticeAddip;
    }

    public void setNoticeAddip(String noticeAddip) {
        this.noticeAddip = noticeAddip == null ? null : noticeAddip.trim();
    }

}