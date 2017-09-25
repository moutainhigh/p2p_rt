package com.rbao.east.entity;

public class OtherMessage extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4027975176513951372L;

	private Integer id;

    private Integer userId;

    private String personalAbility;

    private String personalHobbies;

    private String otherState;

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

    public String getPersonalAbility() {
        return personalAbility;
    }

    public void setPersonalAbility(String personalAbility) {
        this.personalAbility = personalAbility == null ? null : personalAbility.trim();
    }

    public String getPersonalHobbies() {
        return personalHobbies;
    }

    public void setPersonalHobbies(String personalHobbies) {
        this.personalHobbies = personalHobbies == null ? null : personalHobbies.trim();
    }

    public String getOtherState() {
        return otherState;
    }

    public void setOtherState(String otherState) {
        this.otherState = otherState == null ? null : otherState.trim();
    }
}