package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rbao.east.utils.DateUtils;

public class RedenvelopesRecord extends BaseEntity{
    /**
     * 被邀请奖励红包名称
     */
	public static final String RED_NAME_INVITED = "被邀请投资奖励";
	
    public static final Map<Integer,String> ALL_STATUS = new HashMap<Integer,String>();
    public static final Map<Integer,String> ALL_Type = new HashMap<Integer,String>();
    
    public static final Integer STATUS_NOT_OPEN    = 1;
	public static final Integer STATUS_CANNOT_OPEN = 2;
	public static final Integer STATUS_HAS_OPEN    = 3;
	public static final Integer STATUS_OVERDUE    = 4;
	
	public static final Integer TYPE_CASH    = 1; //现金红包
	public static final Integer TYPE_LOGIN = 2;   //登录红包
	public static final Integer TYPE_TENDER    = 3;//满额红包
	public static final Integer TYPE_CONTINUE_TENDER    = 4;//续投红包
	
	public static final Integer OPEN_LIMIT_TYPE_TENDER = 1;
	public static final Integer OPEN_LIMIT_TYPE_LOGIN_DAYS = 2;
	public static final Integer OPEN_LIMIT_TYPE_LOGIN_CONTINUE = 3;
	public static final Integer OPEN_LIMIT_TYPE_CONTINUE_TENDER = 4;
	
	static {
		ALL_STATUS.put(STATUS_CANNOT_OPEN, "未满足条件");
		ALL_STATUS.put(STATUS_NOT_OPEN, "未打开");
		ALL_STATUS.put(STATUS_HAS_OPEN, "已打开");
		ALL_STATUS.put(STATUS_OVERDUE, "已过期");
		
		ALL_Type.put(TYPE_CASH, 		   "现金红包");
		ALL_Type.put(TYPE_LOGIN, 		   "登录红包");
		ALL_Type.put(TYPE_TENDER, 		   "满额红包");
		ALL_Type.put(TYPE_CONTINUE_TENDER, "续投红包");
	}
	
	public String viewStatus ;
	public String viewPriodStartTime ;
	public String viewPriodEndTime ;
	public String viewUpdateDate;
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6096722145592647793L;

	private Integer id;

    private Integer userId;

    private BigDecimal amount;
    
    private BigDecimal realAmount;

    private String name;
    
    private Integer redType;

    private Date periodBeginTime;

    private Date periodEndTime;

    private Integer sendUserId;

    private Integer status;

    private Integer openLimitType;

    private BigDecimal openLimitParam;

    private BigDecimal fillLimitParam;

    private Date sendTime;

    private Date updateTime;

    private String remark;
    
    /**
     * 扩展字段，用于业务之间传值
     * 不存储数据库
     */
    private String extendField ;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getPeriodBeginTime() {
        return periodBeginTime;
    }

    public void setPeriodBeginTime(Date periodBeginTime) {
        this.periodBeginTime = periodBeginTime;
        setViewPriodStartTime(DateUtils.formatDate("yyyy.MM.dd", periodBeginTime));
    }

    public Date getPeriodEndTime() {
        return periodEndTime;
    }

    public void setPeriodEndTime(Date periodEndTime) {
        this.periodEndTime = periodEndTime;
        setViewPriodEndTime(DateUtils.formatDate(DateUtils.FORMAT_STR1, periodEndTime));
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        setViewStatus(ALL_STATUS.get(status));
    }

    public Integer getOpenLimitType() {
        return openLimitType;
    }

    public void setOpenLimitType(Integer openLimitType) {
        this.openLimitType = openLimitType;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
       /* setViewUpdateDate(DateUtils.formatDate(DateUtils.FORMAT_STR1, updateTime));*/
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	public String getViewPriodStartTime() {
		return viewPriodStartTime;
	}

	public void setViewPriodStartTime(String viewPriodStartTime) {
		this.viewPriodStartTime = viewPriodStartTime;
	}

	public String getViewPriodEndTime() {
		return viewPriodEndTime;
	}

	public void setViewPriodEndTime(String viewPriodEndTime) {
		this.viewPriodEndTime = viewPriodEndTime;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public BigDecimal getOpenLimitParam() {
		return openLimitParam;
	}

	public void setOpenLimitParam(BigDecimal openLimitParam) {
		this.openLimitParam = openLimitParam;
	}

	public BigDecimal getFillLimitParam() {
		return fillLimitParam;
	}

	public void setFillLimitParam(BigDecimal fillLimitParam) {
		this.fillLimitParam = fillLimitParam;
	}

	public Integer getRedType() {
		return redType;
	}

	public void setRedType(Integer redType) {
		this.redType = redType;
	}

	public String getViewUpdateDate() {
		return viewUpdateDate;
	}

	public void setViewUpdateDate(String viewUpdateDate) {
		this.viewUpdateDate = viewUpdateDate;
	}

	public String getExtendField() {
		return extendField;
	}

	public void setExtendField(String extendField) {
		this.extendField = extendField;
	}
}