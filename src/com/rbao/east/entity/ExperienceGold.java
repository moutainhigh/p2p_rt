package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ExperienceGold extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer userid;

    private BigDecimal experienceGoldInterest;

    private Date experienceGoldAddtime;

    private Date experienceGoldRepaytime;

    private Integer experienceGoldStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public BigDecimal getExperienceGoldInterest() {
        return experienceGoldInterest;
    }

    public void setExperienceGoldInterest(BigDecimal experienceGoldInterest) {
        this.experienceGoldInterest = experienceGoldInterest;
    }

    public Date getExperienceGoldAddtime() {
        return experienceGoldAddtime;
    }

    public void setExperienceGoldAddtime(Date experienceGoldAddtime) {
        this.experienceGoldAddtime = experienceGoldAddtime;
    }

    public Date getExperienceGoldRepaytime() {
        return experienceGoldRepaytime;
    }

    public void setExperienceGoldRepaytime(Date experienceGoldRepaytime) {
        this.experienceGoldRepaytime = experienceGoldRepaytime;
    }

    public Integer getExperienceGoldStatus() {
        return experienceGoldStatus;
    }

    public void setExperienceGoldStatus(Integer experienceGoldStatus) {
        this.experienceGoldStatus = experienceGoldStatus;
    }
}