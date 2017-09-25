package com.rbao.east.entity;

public class BorrowType extends BaseEntity{
	
	private static final long serialVersionUID = -2157735432188426931L;
	
	public static final Integer REPAY_TYPE_SELF = 1;//人工还
	public static final Integer REPAY_TYPE_AUTO = 2;//自动还
	public static final String BIDCODE="DING";
	
	public static final Integer STATUS_ABLE = 1;//可用
	public static final Integer STATUS_UNABLE = 2;//不可用
	public static final Integer TYPE_LI=1;//力标
	public static final Integer TYPE_LIU=2;//流转标
	public static final Integer TYPE_MIAO=3;//秒标
	public static final Integer TYPE_JING=4;//净值标
	public static final Integer TYPE_XING=5;//信用标
	public static final Integer TYPE_DING=6;//定活通
	public static final Integer TYPE_XINSHOU=8;//新手标
	public static final Integer TYPE_MINGXING=9;//明星标
	public static final Integer TYPE_HUODONG=10;//活动标
	public static final Integer TYPE_LIUYUE=11;//六月标
	public static final Integer TYPE_JIUYUE=12;//九月标
	public static final Integer TYPE_SHEERYUE=13;//十二月标
	public static final Integer TYPE_ZHENGFEN=14;//争分夺秒
	
    private Integer id;

    private String name;

    private String code;

    private Integer frontPublish;

    private Integer adminPublish;

    private String dealService;

    private Integer status;

    private String logPath;
    
    private String agreementContent;
    
    private String remark;
    
    private Integer repayType; 
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getFrontPublish() {
        return frontPublish;
    }

    public void setFrontPublish(Integer frontPublish) {
        this.frontPublish = frontPublish;
    }

    public Integer getAdminPublish() {
        return adminPublish;
    }

    public void setAdminPublish(Integer adminPublish) {
        this.adminPublish = adminPublish;
    }

    public String getDealService() {
        return dealService;
    }

    public void setDealService(String dealService) {
        this.dealService = dealService == null ? null : dealService.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getAgreementContent() {
		return agreementContent;
	}

	public void setAgreementContent(String agreementContent) {
		this.agreementContent = agreementContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRepayType() {
		return repayType;
	}

	public void setRepayType(Integer repayType) {
		this.repayType = repayType;
	}
    
}