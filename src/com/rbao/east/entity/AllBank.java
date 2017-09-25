package com.rbao.east.entity;

import java.util.Date;
/**
 * 银行
 * */
public class AllBank extends BaseEntity{
    private Integer id;

    private String bankName;//银行名称

    private Integer bankSequence;//银行排序

    private Integer bankStatus;//银行状态

    private Date bankUpdatetime;//更新时间

    private String bankCode;//银行编码
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public Integer getBankSequence() {
        return bankSequence;
    }

    public void setBankSequence(Integer bankSequence) {
        this.bankSequence = bankSequence;
    }

    public Integer getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(Integer bankStatus) {
        this.bankStatus = bankStatus;
    }

    public Date getBankUpdatetime() {
        return bankUpdatetime;
    }

    public void setBankUpdatetime(Date bankUpdatetime) {
        this.bankUpdatetime = bankUpdatetime;
    }

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
    
}