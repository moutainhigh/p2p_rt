package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 费用扣除
 * */
public class AccountDeduct extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6809085596993856521L;

	public static final Integer TYPE_spot = 1;//
	public static final Integer TYPE_ensure = 2;//
	public static final Integer TYPE_other = 3;//
	
	public static final Map<Integer,String> ALL_TYPE = new HashMap<Integer,String>();
	
	static {
		ALL_TYPE.put(TYPE_spot, "现场认证扣费");
		ALL_TYPE.put(TYPE_ensure, "担保垫付扣费");
		ALL_TYPE.put(TYPE_other, "其他");
	}
	private Integer id;

    private Integer userId;

    private Integer deductType;//扣除类型

    private BigDecimal deductAmount;//扣除金额

    private String remark;

    private Date addTime;

    private String addIp;

    private Integer addUserId;

    private Integer checkUserId;

    private Date checkTime;

    private String checkIp;
    
    private Integer checkState;

    /**
     * 扣费用户名
     * @return
     */
    private String userAccount;
    /**
     * 审核人
     * @return
     */
    private String checkUserAccount;
    /**
     * 扣费创建人
     * @return
     */
    private String addUserAccount;
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

    public Integer getDeductType() {
        return deductType;
    }

    public void setDeductType(Integer deductType) {
        this.deductType = deductType;
    }

    public BigDecimal getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(BigDecimal deductAmount) {
        this.deductAmount = deductAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckIp() {
        return checkIp;
    }

    public void setCheckIp(String checkIp) {
        this.checkIp = checkIp == null ? null : checkIp.trim();
    }

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getCheckUserAccount() {
		return checkUserAccount;
	}

	public void setCheckUserAccount(String checkUserAccount) {
		this.checkUserAccount = checkUserAccount;
	}

	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	public String getAddUserAccount() {
		return addUserAccount;
	}

	public void setAddUserAccount(String addUserAccount) {
		this.addUserAccount = addUserAccount;
	}
    
}