package com.rbao.east.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShopGoods extends BaseEntity{
	
	public static final Map<Integer,String> ALL_isEnable = new HashMap<Integer,String>();
	
	public static final Integer isEnable_yes = 1;
	public static final Integer isEnable_no = 0;
	
	static {
		ALL_isEnable.put(isEnable_yes, "显示");
		ALL_isEnable.put(isEnable_no, "隐藏");
	}
    /**
	 * 
	 */
	private static final long serialVersionUID = 68478207451186939L;

	private Integer id;

    private String goodsName;//

    private String goodsCode;//

    private String categoryCode;//

    private Integer totalCount;//

    private Integer sellCount;

    private Integer requireCredit;//

    private String goodsSummary;//

    private String logPath;

    private Integer orderBy;//

    private Integer isEnable;//

    private Date publishTime;//

    private Date createTime;

    private Date updateTime;

    private Integer vsion;

    private Integer flag2;

    private String misc1;

    private String misc2;

    private String goodsDetail;//

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode == null ? null : categoryCode.trim();
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getRequireCredit() {
        return requireCredit;
    }

    public void setRequireCredit(Integer requireCredit) {
        this.requireCredit = requireCredit;
    }

    public String getGoodsSummary() {
        return goodsSummary;
    }

    public void setGoodsSummary(String goodsSummary) {
        this.goodsSummary = goodsSummary == null ? null : goodsSummary.trim();
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath == null ? null : logPath.trim();
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFlag2() {
        return flag2;
    }

    public void setFlag2(Integer flag2) {
        this.flag2 = flag2;
    }

    public String getMisc1() {
        return misc1;
    }

    public void setMisc1(String misc1) {
        this.misc1 = misc1 == null ? null : misc1.trim();
    }

    public String getMisc2() {
        return misc2;
    }

    public void setMisc2(String misc2) {
        this.misc2 = misc2 == null ? null : misc2.trim();
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail == null ? null : goodsDetail.trim();
    }

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	public Integer getVsion() {
		return vsion;
	}

	public void setVsion(Integer vsion) {
		this.vsion = vsion;
	}
}