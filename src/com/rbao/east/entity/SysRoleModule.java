package com.rbao.east.entity;

public class SysRoleModule extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer sysModuleId;

    private Integer sysRoleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysModuleId() {
        return sysModuleId;
    }

    public void setSysModuleId(Integer sysModuleId) {
        this.sysModuleId = sysModuleId;
    }

    public Integer getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }
}