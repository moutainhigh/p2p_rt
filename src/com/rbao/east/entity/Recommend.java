package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Recommend extends BaseEntity{
	
	private static final long serialVersionUID = -2157735432188426931L;
	
	public static final Integer RECOMMEND_STATUS_NEW = 1;//新建
	public static final Integer RECOMMEND_STATUS_SUCC = 2;//通过
	public static final Integer RECOMMEND_STATUS_FAIL = 3;//未通过
	
	public static final Integer ADD_MONEY_BY_RATE = 1111; //标注是按利息给奖励
	
	public static final Integer RECOMMEND_LEVEL_TIME=3;//推荐奖励会员级数，一级会员，二级会员。。。
	
	public static final Integer initLevel=new Integer(1);//默认会员等级	
	public static final Map<Integer, String> ALL_RECOMMEND_STATUS =  new HashMap<Integer, String>();
	
	static {
		ALL_RECOMMEND_STATUS.put(RECOMMEND_STATUS_NEW, "待审核");
		ALL_RECOMMEND_STATUS.put(RECOMMEND_STATUS_SUCC, "审核通过");
		ALL_RECOMMEND_STATUS.put(RECOMMEND_STATUS_FAIL, "审核未通过");
		
	}
	
    private Integer id;

    private Integer userId;

    private Integer recommendStatus;

    private Integer recommendSequence;

    private BigDecimal recommendMoney;

    private Integer recommendUserid;

    private Date recommendAddtime;

    private Integer verifyUserid;

    private String verifyRemark;

    private Date verifyAddtime;

    private String verifyAddip;
    
    private User user;
    
    private User recommendUser;
    
    private User verifyUser;
    

    private int levl;
    
    public int getLevl() {
		return levl;
	}

	public void setLevl(int levl) {
		this.levl = levl;
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

    public Integer getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(Integer recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public Integer getRecommendSequence() {
        return recommendSequence;
    }

    public void setRecommendSequence(Integer recommendSequence) {
        this.recommendSequence = recommendSequence;
    }

    public BigDecimal getRecommendMoney() {
        return recommendMoney;
    }

    public void setRecommendMoney(BigDecimal recommendMoney) {
        this.recommendMoney = recommendMoney;
    }

    public Integer getRecommendUserid() {
        return recommendUserid;
    }

    public void setRecommendUserid(Integer recommendUserid) {
        this.recommendUserid = recommendUserid;
    }

    public Date getRecommendAddtime() {
        return recommendAddtime;
    }

    public void setRecommendAddtime(Date recommendAddtime) {
        this.recommendAddtime = recommendAddtime;
    }

    public Integer getVerifyUserid() {
        return verifyUserid;
    }

    public void setVerifyUserid(Integer verifyUserid) {
        this.verifyUserid = verifyUserid;
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark == null ? null : verifyRemark.trim();
    }

    public Date getVerifyAddtime() {
        return verifyAddtime;
    }

    public void setVerifyAddtime(Date verifyAddtime) {
        this.verifyAddtime = verifyAddtime;
    }

    public String getVerifyAddip() {
        return verifyAddip;
    }

    public void setVerifyAddip(String verifyAddip) {
        this.verifyAddip = verifyAddip == null ? null : verifyAddip.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getRecommendUser() {
		return recommendUser;
	}

	public void setRecommendUser(User recommendUser) {
		this.recommendUser = recommendUser;
	}

	public User getVerifyUser() {
		return verifyUser;
	}

	public void setVerifyUser(User verifyUser) {
		this.verifyUser = verifyUser;
	}
	
	
}