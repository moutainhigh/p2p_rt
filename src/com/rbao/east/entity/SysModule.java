package com.rbao.east.entity;

import java.util.List;

public class SysModule extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String moduleName;

    private String moduleUrl;

    private String moduleUnicode;

    private String moduleDescribe;

    private Integer moduleSequence;

    private Integer moduleView;

    private Integer moduleParentId;
    
    private String moduleParentName;
    
    private List<SysModule> sysModule;
    

    
    
    public String getModuleParentName() {
		return moduleParentName;
	}

	public void setModuleParentName(String moduleParentName) {
		this.moduleParentName = moduleParentName;
	}

	public List<SysModule> getSysModule() {
		return sysModule;
	}

	public void setSysModule(List<SysModule> sysModule) {
		this.sysModule = sysModule;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl == null ? null : moduleUrl.trim();
    }

    public String getModuleUnicode() {
        return moduleUnicode;
    }

    public void setModuleUnicode(String moduleUnicode) {
        this.moduleUnicode = moduleUnicode == null ? null : moduleUnicode.trim();
    }

    public String getModuleDescribe() {
        return moduleDescribe;
    }

    public void setModuleDescribe(String moduleDescribe) {
        this.moduleDescribe = moduleDescribe == null ? null : moduleDescribe.trim();
    }

    public Integer getModuleSequence() {
        return moduleSequence;
    }

    public void setModuleSequence(Integer moduleSequence) {
        this.moduleSequence = moduleSequence;
    }

    public Integer getModuleView() {
        return moduleView;
    }

    public void setModuleView(Integer moduleView) {
        this.moduleView = moduleView;
    }

    public Integer getModuleParentId() {
        return moduleParentId;
    }

    public void setModuleParentId(Integer moduleParentId) {
        this.moduleParentId = moduleParentId;
    }
}