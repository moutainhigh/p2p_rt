package com.rbao.east.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VipUser extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 959361032914840683L;
	
	public static final Map<Integer,String> VIP_STATUS=new HashMap<Integer, String>();
	public static final Integer VIP_APPLY=0;
	public static final Integer VIP_USER=1;
	public static final Integer VIP_FREEZE=2;
	public static final Integer VIP_STOP=3;
	public static final Integer VIP_END=4;
	public static final Integer VIP_FAIL=5;
	public static final Integer VIP_MONTH_NUM=12;//vip月数
	static{
		VIP_STATUS.put(VIP_APPLY, "申请中");
		VIP_STATUS.put(VIP_USER, "VIP用户");
		VIP_STATUS.put(VIP_FREEZE, "冻结");
		VIP_STATUS.put(VIP_STOP, "停卡");
		VIP_STATUS.put(VIP_END, "封号");
		VIP_STATUS.put(VIP_FAIL, "未通过");
	}
	
	private Integer id;

    private Integer userId;

    private String vipCard;

    private Integer vipMonthNum;

    private Date vipStartDate;

    private Date vipEndDate;

    private Integer vipStatus;

    private Integer vipFreezeTime;

    private Integer vipFreezeTimes;

    private Date vipAddDatetime;

    private String vipAddIp;

    private Date vipUpdateDatetime;

    private String vipUpdateIp;

    private Integer vipOperatorId;

    private String vipRemarks;

    private Integer vipCustomer;
    
    private User user;
    
    private User userCustomer;
    
    private String vipView;
    
    public String getVipView() {
		return vipView;
	}

	public void setVipView(String vipView) {
		this.vipView = vipView;
	}

	public User getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(User userCustomer) {
		this.userCustomer = userCustomer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

    public String getVipCard() {
        return vipCard;
    }

    public void setVipCard(String vipCard) {
        this.vipCard = vipCard == null ? null : vipCard.trim();
    }

    public Integer getVipMonthNum() {
        return vipMonthNum;
    }

    public void setVipMonthNum(Integer vipMonthNum) {
        this.vipMonthNum = vipMonthNum;
    }

    public Date getVipStartDate() {
        return vipStartDate;
    }

    public void setVipStartDate(Date vipStartDate) {
        this.vipStartDate = vipStartDate;
    }

    public Date getVipEndDate() {
        return vipEndDate;
    }

    public void setVipEndDate(Date vipEndDate) {
        this.vipEndDate = vipEndDate;
    }

    public Integer getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(Integer vipStatus) {
        this.vipStatus = vipStatus;
        setVipView(VIP_STATUS.get(vipStatus));
    }

    public Integer getVipFreezeTime() {
        return vipFreezeTime;
    }

    public void setVipFreezeTime(Integer vipFreezeTime) {
        this.vipFreezeTime = vipFreezeTime;
    }

    public Integer getVipFreezeTimes() {
        return vipFreezeTimes;
    }

    public void setVipFreezeTimes(Integer vipFreezeTimes) {
        this.vipFreezeTimes = vipFreezeTimes;
    }

    public Date getVipAddDatetime() {
        return vipAddDatetime;
    }

    public void setVipAddDatetime(Date vipAddDatetime) {
        this.vipAddDatetime = vipAddDatetime;
    }

    public String getVipAddIp() {
        return vipAddIp;
    }

    public void setVipAddIp(String vipAddIp) {
        this.vipAddIp = vipAddIp == null ? null : vipAddIp.trim();
    }

    public Date getVipUpdateDatetime() {
        return vipUpdateDatetime;
    }

    public void setVipUpdateDatetime(Date vipUpdateDatetime) {
        this.vipUpdateDatetime = vipUpdateDatetime;
    }

    public String getVipUpdateIp() {
        return vipUpdateIp;
    }

    public void setVipUpdateIp(String vipUpdateIp) {
        this.vipUpdateIp = vipUpdateIp == null ? null : vipUpdateIp.trim();
    }

    public Integer getVipOperatorId() {
        return vipOperatorId;
    }

    public void setVipOperatorId(Integer vipOperatorId) {
        this.vipOperatorId = vipOperatorId;
    }

    public String getVipRemarks() {
        return vipRemarks;
    }

    public void setVipRemarks(String vipRemarks) {
        this.vipRemarks = vipRemarks == null ? null : vipRemarks.trim();
    }

    public Integer getVipCustomer() {
        return vipCustomer;
    }

    public void setVipCustomer(Integer vipCustomer) {
        this.vipCustomer = vipCustomer;
    }
}