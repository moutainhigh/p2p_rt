package com.rbao.east.entity;


/**
 * 
 * @author 肖世杰
 * 基本配置主表
 */
public class ExtField extends BaseEntity {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2954104655431526287L;
	
	public static final String table_borrow = "rb_borrow";

	private Integer id;    // 主键Id

    private String fldCode;    // code编码

    private String fldName;    // 名称

    private String fldDesc;    // 描述

    private String fldInTable;    // 对应的表名

    private String fldType;   // 类型

    private String styleCode;    // html代码
    
    private Integer sort;    // 排序字段

    private String remark;    // 备注说明

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFldCode() {
        return fldCode;
    }

    public void setFldCode(String fldCode) {
        this.fldCode = fldCode == null ? null : fldCode.trim();
    }

    public String getFldName() {
        return fldName;
    }

    public void setFldName(String fldName) {
        this.fldName = fldName == null ? null : fldName.trim();
    }

    public String getFldDesc() {
        return fldDesc;
    }

    public void setFldDesc(String fldDesc) {
        this.fldDesc = fldDesc == null ? null : fldDesc.trim();
    }

    public String getFldInTable() {
        return fldInTable;
    }

    public void setFldInTable(String fldInTable) {
        this.fldInTable = fldInTable == null ? null : fldInTable.trim();
    }

    public String getFldType() {
        return fldType;
    }

    public void setFldType(String fldType) {
        this.fldType = fldType == null ? null : fldType.trim();
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode == null ? null : styleCode.trim();
    }

    public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

}