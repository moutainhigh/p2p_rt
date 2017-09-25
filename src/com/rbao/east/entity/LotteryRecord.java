package com.rbao.east.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LotteryRecord  extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -681217641800087984L;
	
	public static final Integer status_new = 1;
	public static final Integer status_hasPrized = 2;
	public static final Integer status_PrizedErr = 3;
	
	public static final Map<Integer,String> ALL_LotteryRecord_STATUS = new HashMap<Integer,String>();
	
	static {
		ALL_LotteryRecord_STATUS.put(status_new, "未兑奖");
		ALL_LotteryRecord_STATUS.put(status_hasPrized, "已兑奖");
	}
	
    private Integer id;

    private Integer usrId;

    private Integer prizeId;

    private String prizeDisc;

    private Date createTime;

    private Integer status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public Integer getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Integer prizeId) {
        this.prizeId = prizeId;
    }

    public String getPrizeDisc() {
        return prizeDisc;
    }

    public void setPrizeDisc(String prizeDisc) {
        this.prizeDisc = prizeDisc == null ? null : prizeDisc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}