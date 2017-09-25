package com.rbao.east.entity;

import java.math.BigDecimal;

public class RecommendAccount  extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8343298667861267411L;

	private Integer id;

    private Integer userId;

    private Integer level;

    private BigDecimal rewards;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BigDecimal getRewards() {
        return rewards;
    }

    public void setRewards(BigDecimal rewards) {
        this.rewards = rewards;
    }
}