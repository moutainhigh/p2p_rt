package com.rbao.east.entity;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class User extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2606930512596991493L;
	
	public static final String DEFAULT_PWD="rb@1234";
	
	public static final Integer IS_BLACK_LIST_YES=new Integer(1);//是，
	public static final Integer IS_BLACK_LIST_NO=new Integer(2);//不是
	
	/**
	 * 担保
	 */
	public static final Map<Integer,String> VOUCH=new HashMap<Integer, String>();
	public static final Integer VOUCHN=0;
	public static final Integer VOUCHY=1;
	
	/**
	 * lock
	 */
	public static final Map<Integer,String> LOCK=new HashMap<Integer, String>();
	public static final Integer LOCKN=0;
	public static final Integer LOCKY=1;
	
	/**
	 * 实名认证状态
	 */
	public static final Map<Integer,String> REALNAME=new HashMap<Integer, String>();
	public static final Integer REALNAME_FAIL=1;
	public static final Integer REALNAME_PASS=2;
	public static final Integer REALNAME_APPLY=-1;
	public static final Integer REALNAME_DEFAULT=-2;
	
	/**
	 * 手机认证状态
	 */
	public static final Map<Integer,String> PHONE=new HashMap<Integer, String>();
	public static final Integer PHONE_FAIL=1;
	public static final Integer PHONE_PASS=2;
	public static final Integer PHONE_APPLY=-1;
	public static final Integer PHONE_DEFAULT=-2;
	
	/**
	 * 邮箱认证状态
	 */
	public static final Map<Integer,String> EMAIL=new HashMap<Integer, String>();
	public static final Integer EMAIL_FAIL=1;
	public static final Integer EMAIL_PASS=2;
	public static final Integer EMAIL_APPLY=-1;
	public static final Integer EMAIL_DEFAULT=-2;
	
	/**
	 * 现场认证状态
	 */
	public static final Map<Integer,String> SCENE=new HashMap<Integer, String>();
	public static final Integer SCENE_FAIL=1;
	public static final Integer SCENE_PASS=2;
	public static final Integer SCENE_APPLY=-1;
	public static final Integer SCENE_DEFAULT=-2;
	
	/**
	 * 视频认证状态
	 */
	public static final Map<Integer,String> VIDEO=new HashMap<Integer, String>();
	public static final Integer VIDEO_FAIL=1;
	public static final Integer VIDEO_PASS=2;
	public static final Integer VIDEO_APPLY=-1;
	public static final Integer VIDEO_DEFAULT=-2;
	
	
	/***
	 * 系统管理员
	 */
	public static final Map<Integer,String> SYSTEM=new HashMap<Integer, String>();
	public static final Integer SYSTEMN=2;
	public static final Integer SYSTEMY=1;
	
	/**
	 * 证件类型
	 */
	public static final Map<Integer,String> ALL_CARD=new HashMap<Integer, String>();
	public static final Integer IDCARD=1;
	public static final Integer COCARD=2;
	public static final Integer TWCARD=3;
	static {
		VOUCH.put(VOUCHN, "没有");
		VOUCH.put(VOUCHY, "有");
		LOCK.put(LOCKN, "否");
		LOCK.put(LOCKY, "是");
		REALNAME.put(REALNAME_FAIL, "未通过");
		REALNAME.put(REALNAME_PASS, "通过");
		REALNAME.put(REALNAME_APPLY, "申请中");
		REALNAME.put(REALNAME_DEFAULT, "未申请");
		PHONE.put(PHONE_FAIL, "未通过");
		PHONE.put(PHONE_PASS, "通过");
		PHONE.put(PHONE_APPLY, "申请中");
		PHONE.put(PHONE_DEFAULT, "未申请");
		EMAIL.put(EMAIL_FAIL, "未通过");
		EMAIL.put(EMAIL_PASS, "通过");
		EMAIL.put(EMAIL_APPLY, "申请中");
		EMAIL.put(EMAIL_DEFAULT, "未申请");
		SCENE.put(SCENE_FAIL, "未通过");
		SCENE.put(SCENE_PASS, "通过");
		SCENE.put(SCENE_APPLY, "申请中");
		SCENE.put(SCENE_DEFAULT, "未申请");
		VIDEO.put(VIDEO_FAIL, "未通过");
		VIDEO.put(VIDEO_PASS, "通过");
		VIDEO.put(VIDEO_APPLY, "申请中");
		VIDEO.put(VIDEO_DEFAULT, "未申请");
		SYSTEM.put(SYSTEMN, "否");
		SYSTEM.put(SYSTEMY, "是");
		ALL_CARD.put(IDCARD, "身份证");
		ALL_CARD.put(COCARD, "军官证");
		ALL_CARD.put(TWCARD, "台胞证");
	}
	
	private Integer id;

    private Integer typeId;

    private String userAccount;

    private String userPassword;

    private String userPaypassword;

    private Integer userVouch;

    private Integer userIslock;

    private Integer inviteUserid;

    private String inviteMoney;

    private Integer cardType;

    private String cardNumber;

    private String cardFrontImg;

    private String cardBackImg;

    private String userNation;

    private String userRealname;

    private String userIntegral;

    private Integer avatarStatus;

    private Integer realnameStatus;

    private Integer emailStatus;

    private Integer phoneStatus;

    private Integer videoStatus;

    private Integer sceneStatus;

    private String userEmail;

    private String userSex;

    private String avatarImg;

    private String userTel;

    private String userPhone;

    private String userQq;

    private String userQuestion;

    private String userAnswer;

    private Date userBirthday;
    
    private Date addBlackTime;//拉黑时间

    private String userProvince;

    private String userCity;

    private String userArea;

    private String userAddress;

    private String userRemind;

    private String userPrivacy;

    private Date userAddtime;

    private String userAddip;

    private Date userLogintime;

    private String userLoginip;

    private Integer isSystem;
    
    private String viewType;
    
    private String viewVouch;
    
    private String viewLock;
    
    private String viewRealName;
    
    private String viewPhone;
    
    private String viewEmail;
    
    private String viewScene;
    
    private String viewVideo;
    
    private String viewSystem;
    
    private String viewCard;
    
    private UserType type;
    
    private Integer isThirdparty;
    
    private Integer autoRedFlag;
    
    private Integer isBlackList;//是否已加入黑名单
    
    
    private String rtUserFlag; //标识融腾内部员工 rt_user_flag
    
    
    private Integer levl;
    
   
    private String bankAccount;
    
    

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getLevl() {
		return levl;
	}

	public void setLevl(Integer levl) {
		this.levl = levl;
	}

	public String getRtUserFlag() {
		return rtUserFlag;
	}

	public void setRtUserFlag(String rtUserFlag) {
		this.rtUserFlag = rtUserFlag;
	}

	public Integer getIsThirdparty() {
		return isThirdparty;
	}

	public void setIsThirdparty(Integer isThirdparty) {
		this.isThirdparty = isThirdparty;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	private String uid;
    
	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getViewCard() {
		return viewCard;
	}

	public void setViewCard(String viewCard) {
		this.viewCard = viewCard;
	}

	public String getViewSystem() {
		return viewSystem;
	}

	public void setViewSystem(String viewSystem) {
		this.viewSystem = viewSystem;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getViewVouch() {
		return viewVouch;
	}

	public void setViewVouch(String viewVouch) {
		this.viewVouch = viewVouch;
	}

	public String getViewLock() {
		return viewLock;
	}

	public void setViewLock(String viewLock) {
		this.viewLock = viewLock;
	}

	public String getViewRealName() {
		return viewRealName;
	}

	public void setViewRealName(String viewRealName) {
		this.viewRealName = viewRealName;
	}

	public String getViewPhone() {
		return viewPhone;
	}

	public void setViewPhone(String viewPhone) {
		this.viewPhone = viewPhone;
	}

	public String getViewEmail() {
		return viewEmail;
	}

	public void setViewEmail(String viewEmail) {
		this.viewEmail = viewEmail;
	}

	public String getViewScene() {
		return viewScene;
	}

	public void setViewScene(String viewScene) {
		this.viewScene = viewScene;
	}

	public String getViewVideo() {
		return viewVideo;
	}

	public void setViewVideo(String viewVideo) {
		this.viewVideo = viewVideo;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }


    public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserPaypassword() {
        return userPaypassword;
    }

    public void setUserPaypassword(String userPaypassword) {
        this.userPaypassword = userPaypassword == null ? null : userPaypassword.trim();
    }

    public Integer getUserVouch() {
        return userVouch;
    }

    public void setUserVouch(Integer userVouch) {
        this.userVouch = userVouch;
        setViewVouch(VOUCH.get(userVouch));
    }

    public Integer getUserIslock() {
        return userIslock;
    }

    public void setUserIslock(Integer userIslock) {
        this.userIslock = userIslock;
        setViewLock(LOCK.get(userIslock));
    }

    public Integer getInviteUserid() {
        return inviteUserid;
    }

    public void setInviteUserid(Integer inviteUserid) {
        this.inviteUserid = inviteUserid;
    }

    public String getInviteMoney() {
        return inviteMoney;
    }

    public void setInviteMoney(String inviteMoney) {
        this.inviteMoney = inviteMoney == null ? null : inviteMoney.trim();
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
        setViewCard(ALL_CARD.get(cardType));
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    public String getCardFrontImg() {
        return cardFrontImg;
    }

    public void setCardFrontImg(String cardFrontImg) {
        this.cardFrontImg = cardFrontImg == null ? null : cardFrontImg.trim();
    }

    public String getCardBackImg() {
        return cardBackImg;
    }

    public void setCardBackImg(String cardBackImg) {
        this.cardBackImg = cardBackImg == null ? null : cardBackImg.trim();
    }

    public String getUserNation() {
        return userNation;
    }

    public void setUserNation(String userNation) {
        this.userNation = userNation == null ? null : userNation.trim();
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname == null ? null : userRealname.trim();
    }

    public String getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(String userIntegral) {
        this.userIntegral = userIntegral == null ? null : userIntegral.trim();
    }

    public Integer getAvatarStatus() {
        return avatarStatus;
    }

    public void setAvatarStatus(Integer avatarStatus) {
        this.avatarStatus = avatarStatus;
    }

    public Integer getRealnameStatus() {
        return realnameStatus;
    }

    public void setRealnameStatus(Integer realnameStatus) {
        this.realnameStatus = realnameStatus;
        setViewRealName(REALNAME.get(realnameStatus));
    }

    public Integer getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(Integer emailStatus) {
        this.emailStatus = emailStatus;
        setViewEmail(EMAIL.get(emailStatus));
    }

    public Integer getPhoneStatus() {
        return phoneStatus;
    }

    public void setPhoneStatus(Integer phoneStatus) {
        this.phoneStatus = phoneStatus;
        setViewPhone(PHONE.get(phoneStatus));
    }

    public Integer getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(Integer videoStatus) {
        this.videoStatus = videoStatus;
        setViewVideo(VIDEO.get(videoStatus));
    }

    public Integer getSceneStatus() {
        return sceneStatus;
    }

    public void setSceneStatus(Integer sceneStatus) {
        this.sceneStatus = sceneStatus;
        setViewScene(SCENE.get(sceneStatus));
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg == null ? null : avatarImg.trim();
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel == null ? null : userTel.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq == null ? null : userQq.trim();
    }

    public String getUserQuestion() {
        return userQuestion;
    }

    public void setUserQuestion(String userQuestion) {
        this.userQuestion = userQuestion == null ? null : userQuestion.trim();
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer == null ? null : userAnswer.trim();
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince == null ? null : userProvince.trim();
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity == null ? null : userCity.trim();
    }

    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea == null ? null : userArea.trim();
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress == null ? null : userAddress.trim();
    }

    public String getUserRemind() {
        return userRemind;
    }

    public void setUserRemind(String userRemind) {
        this.userRemind = userRemind == null ? null : userRemind.trim();
    }

    public String getUserPrivacy() {
        return userPrivacy;
    }

    public void setUserPrivacy(String userPrivacy) {
        this.userPrivacy = userPrivacy == null ? null : userPrivacy.trim();
    }

    public Date getUserAddtime() {
        return userAddtime;
    }

    public void setUserAddtime(Date userAddtime) throws ParseException {
    	
    		this.userAddtime = userAddtime;
    }

    public String getUserAddip() {
        return userAddip;
    }

    public void setUserAddip(String userAddip) {
        this.userAddip = userAddip == null ? null : userAddip.trim();
    }

    public Date getUserLogintime() {
        return userLogintime;
    }

    public void setUserLogintime(Date userLogintime) {
        this.userLogintime = userLogintime;
    }

    public String getUserLoginip() {
        return userLoginip;
    }

    public void setUserLoginip(String userLoginip) {
        this.userLoginip = userLoginip == null ? null : userLoginip.trim();
    }

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
        setViewSystem(SYSTEM.get(isSystem));
    }

	public Integer getAutoRedFlag() {
		return autoRedFlag;
	}

	public void setAutoRedFlag(Integer autoRedFlag) {
		this.autoRedFlag = autoRedFlag;
	}

	public Integer getIsBlackList() {
		return isBlackList;
	}

	public void setIsBlackList(Integer isBlackList) {
		this.isBlackList = isBlackList;
	}

	public Date getAddBlackTime() {
		return addBlackTime;
	}

	public void setAddBlackTime(Date addBlackTime) {
		this.addBlackTime = addBlackTime;
	}
	
	
}