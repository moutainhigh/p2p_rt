package com.rbao.east.entity;

import java.util.Date;

/**
 * 
 * @author 肖世杰
 * 基本配置value表
 */
public class ExtValue extends BaseEntity {
	
    private Integer id;    // 编号
 
    private Integer tbRelatedId;    // 表关联Id

    private Integer fldId;    // 与主表rb_ext_field表关联

    private String fValue;    // 存储小数据值

    private Date uptTime;    // 更新时间

    private String fValuew;    // 存储大数据值

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTbRelatedId() {
        return tbRelatedId;
    }

    public void setTbRelatedId(Integer tbRelatedId) {
        this.tbRelatedId = tbRelatedId;
    }

    public Integer getFldId() {
        return fldId;
    }

    public void setFldId(Integer fldId) {
        this.fldId = fldId;
    }

    public String getfValue() {
        return fValue;
    }

    public void setfValue(String fValue) {
        this.fValue = fValue == null ? null : fValue.trim();
    }

    public Date getUptTime() {
        return uptTime;
    }

    public void setUptTime(Date uptTime) {
        this.uptTime = uptTime;
    }

    public String getfValuew() {
        return fValuew;
    }

    public void setfValuew(String fValuew) {
        this.fValuew = fValuew == null ? null : fValuew.trim();
    }
    
}