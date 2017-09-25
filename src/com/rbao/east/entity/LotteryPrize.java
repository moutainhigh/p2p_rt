package com.rbao.east.entity;

import java.util.HashMap;
import java.util.Map;

public class LotteryPrize extends BaseEntity{
	
	public static final Map<Integer,String> ALL_stats = new HashMap<Integer,String>();
	public static final Map<Integer,String> ALL_type = new HashMap<Integer,String>();
	
	public static final Integer stats_yes = 1;
	public static final Integer stats_no = 0;
	
	public static final Integer type_l = 1;//小奖
	public static final Integer stats_m = 2;//中奖
	public static final Integer stats_h = 3;//大奖
	
	static {
		ALL_stats.put(stats_yes, "可用");
		ALL_stats.put(stats_no, "不可用");
		
		ALL_type.put(type_l, "小奖区");
		ALL_type.put(stats_m, "中奖区");
		ALL_type.put(stats_h, "大奖区");
	}
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1815809094560292524L;
	
	
	

	private Integer id;

    private String disc;

    private Integer pointStart;

    private Integer pointEnd;

    private Integer probability;

    private Integer prizeType;

    private Integer prizeValue;

    private Integer totalAmount;

    private Integer winAmount;

    private Integer vsion;

    private Integer status;

    private Integer flag1;

    private String remk;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc == null ? null : disc.trim();
    }

    public Integer getPointStart() {
        return pointStart;
    }

    public void setPointStart(Integer pointStart) {
        this.pointStart = pointStart;
    }

    public Integer getPointEnd() {
        return pointEnd;
    }

    public void setPointEnd(Integer pointEnd) {
        this.pointEnd = pointEnd;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Integer getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(Integer prizeType) {
        this.prizeType = prizeType;
    }

    public Integer getPrizeValue() {
        return prizeValue;
    }

    public void setPrizeValue(Integer prizeValue) {
        this.prizeValue = prizeValue;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(Integer winAmount) {
        this.winAmount = winAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlag1() {
        return flag1;
    }

    public void setFlag1(Integer flag1) {
        this.flag1 = flag1;
    }

    public String getRemk() {
        return remk;
    }

    public void setRemk(String remk) {
        this.remk = remk == null ? null : remk.trim();
    }

	public Integer getVsion() {
		return vsion;
	}

	public void setVsion(Integer vsion) {
		this.vsion = vsion;
	}
}