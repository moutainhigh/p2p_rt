package com.rbao.east.entity;

public class FinanceMessage extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8471340970940053490L;

	private Integer id;

    private Integer userId;

    private String refundMoney;

    private Integer houseFreedom;

    private String jiejinMoney;

    private Integer carFreedom;

    private String carJiejin;

    private String refundXinyong;

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

    public String getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(String refundMoney) {
        this.refundMoney = refundMoney == null ? null : refundMoney.trim();
    }

    public Integer getHouseFreedom() {
        return houseFreedom;
    }

    public void setHouseFreedom(Integer houseFreedom) {
        this.houseFreedom = houseFreedom;
    }

    public String getJiejinMoney() {
        return jiejinMoney;
    }

    public void setJiejinMoney(String jiejinMoney) {
        this.jiejinMoney = jiejinMoney == null ? null : jiejinMoney.trim();
    }

    public Integer getCarFreedom() {
        return carFreedom;
    }

    public void setCarFreedom(Integer carFreedom) {
        this.carFreedom = carFreedom;
    }

    public String getCarJiejin() {
        return carJiejin;
    }

    public void setCarJiejin(String carJiejin) {
        this.carJiejin = carJiejin == null ? null : carJiejin.trim();
    }

    public String getRefundXinyong() {
        return refundXinyong;
    }

    public void setRefundXinyong(String refundXinyong) {
        this.refundXinyong = refundXinyong == null ? null : refundXinyong.trim();
    }
}