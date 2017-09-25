package com.rbao.east.entity;

import java.util.Date;

public class SysRole extends BaseEntity{
    private Integer id;

    private String roleName;

    private String roleSequence;

    private Integer roleStatus;

    private Integer roleType;

    private String roleSummary;

    private Integer roleSuper;

    private String roleRemark;

    private Date roleAddtime;

    private String roleAddip;

    private String rolePurview;
    
    private String roleSuperName;
    
    

    public String getRoleSuperName() {
		return roleSuperName;
	}

	public void setRoleSuperName(String roleSuperName) {
		this.roleSuperName = roleSuperName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleSequence() {
        return roleSequence;
    }

    public void setRoleSequence(String roleSequence) {
        this.roleSequence = roleSequence == null ? null : roleSequence.trim();
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getRoleSummary() {
        return roleSummary;
    }

    public void setRoleSummary(String roleSummary) {
        this.roleSummary = roleSummary == null ? null : roleSummary.trim();
    }

    public Integer getRoleSuper() {
        return roleSuper;
    }

    public void setRoleSuper(Integer roleSuper) {
        this.roleSuper = roleSuper;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark == null ? null : roleRemark.trim();
    }

    public Date getRoleAddtime() {
        return roleAddtime;
    }

    public void setRoleAddtime(Date roleAddtime) {
        this.roleAddtime = roleAddtime;
    }

    public String getRoleAddip() {
        return roleAddip;
    }

    public void setRoleAddip(String roleAddip) {
        this.roleAddip = roleAddip == null ? null : roleAddip.trim();
    }

    public String getRolePurview() {
        return rolePurview;
    }

    public void setRolePurview(String rolePurview) {
        this.rolePurview = rolePurview == null ? null : rolePurview.trim();
    }
}