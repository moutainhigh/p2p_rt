package com.rbao.east.entity;

import java.util.Date;

public class ShopCategory extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3113423843510338367L;

	private Integer id;

    private String categoryName;

    private String categoryCode;//编码

    private Integer isEnable;//状态：0隐藏、1显示

    private String remark;

    private Integer orderBy;

    private Date createTime;

    private Date updateTime;

    private Integer flag1;

    private Integer flag2;

    private String misc1;

    private String misc2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode == null ? null : categoryCode.trim();
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
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

    public Integer getFlag1() {
        return flag1;
    }

    public void setFlag1(Integer flag1) {
        this.flag1 = flag1;
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
}