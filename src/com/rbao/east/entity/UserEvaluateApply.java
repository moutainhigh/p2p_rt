package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserEvaluateApply extends BaseEntity{
	
	private static final long serialVersionUID = 5658700765009362721L;
	
	public static final Integer APPLY_STATUS_WAIT_CHECK = 1;//待审核
	public static final Integer APPLY_STATUS_CHECK_SUCCESS = 2;//通过
	public static final Integer APPLY_STATUS_CHECK_FAIL = 3;//未通过
	
	public static final Map<Integer,String> ALL_APPLY_STATUS = new HashMap<Integer,String>();
	
	static {
		ALL_APPLY_STATUS.put(APPLY_STATUS_WAIT_CHECK, "待审核");
		ALL_APPLY_STATUS.put(APPLY_STATUS_CHECK_SUCCESS, "审核通过");
		ALL_APPLY_STATUS.put(APPLY_STATUS_CHECK_FAIL, "审核未通过");
	}
	
    private Integer id;

    private Integer userId;

    private String evaluateapplyType;

    private BigDecimal amount;

    private BigDecimal amountApply;

    private BigDecimal amountBefore;

    private Integer evaluateapplyStatus;

    private Date evaluateapplyAddtime;

    private String evaluateapplyContent;

    private String evaluateapplyRemark;

    private String verifyRemark;

    private Date verifyDatetime;

    private Integer verifyUser;

    private String evaluateapplyAddip;
    
    private User user;
    
    private String viewApplyStatus;
    
    private User verifyUserAccount;

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

    public String getEvaluateapplyType() {
        return evaluateapplyType;
    }

    public void setEvaluateapplyType(String evaluateapplyType) {
        this.evaluateapplyType = evaluateapplyType == null ? null : evaluateapplyType.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountApply() {
        return amountApply;
    }

    public void setAmountApply(BigDecimal amountApply) {
        this.amountApply = amountApply;
    }

    public BigDecimal getAmountBefore() {
        return amountBefore;
    }

    public void setAmountBefore(BigDecimal amountBefore) {
        this.amountBefore = amountBefore;
    }

    public Integer getEvaluateapplyStatus() {
        return evaluateapplyStatus;
    }

    public void setEvaluateapplyStatus(Integer evaluateapplyStatus) {
        this.evaluateapplyStatus = evaluateapplyStatus;
        setViewApplyStatus(ALL_APPLY_STATUS.get(evaluateapplyStatus));
    }

    public Date getEvaluateapplyAddtime() {
        return evaluateapplyAddtime;
    }

    public void setEvaluateapplyAddtime(Date evaluateapplyAddtime) {
        this.evaluateapplyAddtime = evaluateapplyAddtime;
    }

    public String getEvaluateapplyContent() {
        return evaluateapplyContent;
    }

    public void setEvaluateapplyContent(String evaluateapplyContent) {
        this.evaluateapplyContent = evaluateapplyContent == null ? null : evaluateapplyContent.trim();
    }

    public String getEvaluateapplyRemark() {
        return evaluateapplyRemark;
    }

    public void setEvaluateapplyRemark(String evaluateapplyRemark) {
        this.evaluateapplyRemark = evaluateapplyRemark == null ? null : evaluateapplyRemark.trim();
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark == null ? null : verifyRemark.trim();
    }

    public Date getVerifyDatetime() {
        return verifyDatetime;
    }

    public void setVerifyDatetime(Date verifyDatetime) {
        this.verifyDatetime = verifyDatetime;
    }

    public Integer getVerifyUser() {
        return verifyUser;
    }

    public void setVerifyUser(Integer verifyUser) {
        this.verifyUser = verifyUser;
    }

    public String getEvaluateapplyAddip() {
        return evaluateapplyAddip;
    }

    public void setEvaluateapplyAddip(String evaluateapplyAddip) {
        this.evaluateapplyAddip = evaluateapplyAddip == null ? null : evaluateapplyAddip.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getViewApplyStatus() {
		return viewApplyStatus;
	}

	public void setViewApplyStatus(String viewApplyStatus) {
		this.viewApplyStatus = viewApplyStatus;
	}

	public User getVerifyUserAccount() {
		return verifyUserAccount;
	}

	public void setVerifyUserAccount(User verifyUserAccount) {
		this.verifyUserAccount = verifyUserAccount;
	}

}