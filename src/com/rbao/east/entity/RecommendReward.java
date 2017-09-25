package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RecommendReward  extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5789544386802520972L;
	
	public static final Integer StatusStaying=new Integer(0);//未发放
	public static final Integer StatusStaied=new Integer(1);//已发放
	
	public static final Integer initLevel=new Integer(1);//默认会员等级

	private Integer id;

    private Integer recommendUserId;

    private BigDecimal recommendUserReward;
    
    private BigDecimal tenderMoney;

    private Integer levelUserId;

    private BigDecimal levelUserReward;

    private Integer recommondLevel;

    private Integer status;

    private Date addTime;

    private String addIp;

    private String remark;
    
    private User recommendUser;
    
    private User levelUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecommendUserId() {
        return recommendUserId;
    }

    public void setRecommendUserId(Integer recommendUserId) {
        this.recommendUserId = recommendUserId;
    }

    public BigDecimal getRecommendUserReward() {
        return recommendUserReward;
    }

    public void setRecommendUserReward(BigDecimal recommendUserReward) {
        this.recommendUserReward = recommendUserReward;
    }

    public Integer getLevelUserId() {
        return levelUserId;
    }

    public void setLevelUserId(Integer levelUserId) {
        this.levelUserId = levelUserId;
    }

    public BigDecimal getLevelUserReward() {
        return levelUserReward;
    }

    public void setLevelUserReward(BigDecimal levelUserReward) {
        this.levelUserReward = levelUserReward;
    }

    public Integer getRecommondLevel() {
        return recommondLevel;
    }

    public void setRecommondLevel(Integer recommondLevel) {
        this.recommondLevel = recommondLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public BigDecimal getTenderMoney() {
		return tenderMoney;
	}

	public void setTenderMoney(BigDecimal tenderMoney) {
		this.tenderMoney = tenderMoney;
	}

	public User getRecommendUser() {
		return recommendUser;
	}

	public void setRecommendUser(User recommendUser) {
		this.recommendUser = recommendUser;
	}

	public User getLevelUser() {
		return levelUser;
	}

	public void setLevelUser(User levelUser) {
		this.levelUser = levelUser;
	}
    
    
}