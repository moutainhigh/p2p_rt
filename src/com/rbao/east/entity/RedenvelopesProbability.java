package com.rbao.east.entity;

import java.math.BigDecimal;

public class RedenvelopesProbability extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5287982263986236028L;

	private Integer id;

    private BigDecimal probability;

    private BigDecimal multi;
    
    private BigDecimal min;
    
    private BigDecimal max;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }

    public BigDecimal getMulti() {
        return multi;
    }

    public void setMulti(BigDecimal multi) {
        this.multi = multi;
    }

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}
    
    
}