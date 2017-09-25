package com.rbao.east.entity;

public class UserType extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 465660316301058739L;
	
	public static final Integer adminType=2;
	public static final Integer frontType=1;

	private Integer id;
	
	private Integer type;

	private String name;

    private Integer sort;

    private Integer status;

    private String briefly;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBriefly() {
        return briefly;
    }

    public void setBriefly(String briefly) {
        this.briefly = briefly == null ? null : briefly.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}