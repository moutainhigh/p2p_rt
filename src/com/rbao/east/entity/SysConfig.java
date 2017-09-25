package com.rbao.east.entity;


public class SysConfig extends BaseEntity {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 8666363882115705479L;
	
	public static final Integer STATUS_YES = 1;
	public static final Integer STATUS_NO = 2;

	private Integer id;

    private String sysWebsitename;

    private String sysWebsitedomain;

    private String sysWebsitekeyword;

    private String sysWebsitedescribe;

    private String sysWebsiteicp;

    private String sysWebsitefax;

    private String sysWebsitetel;

    private String sysWebsiteaddress;

    private String sysWebsitesignature;

    private String sysRegisterProtocol;
    
    private Integer autoTenderStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysWebsitename() {
        return sysWebsitename;
    }

    public void setSysWebsitename(String sysWebsitename) {
        this.sysWebsitename = sysWebsitename == null ? null : sysWebsitename.trim();
    }

    public String getSysWebsitedomain() {
        return sysWebsitedomain;
    }

    public void setSysWebsitedomain(String sysWebsitedomain) {
        this.sysWebsitedomain = sysWebsitedomain == null ? null : sysWebsitedomain.trim();
    }

    public String getSysWebsitekeyword() {
        return sysWebsitekeyword;
    }

    public void setSysWebsitekeyword(String sysWebsitekeyword) {
        this.sysWebsitekeyword = sysWebsitekeyword == null ? null : sysWebsitekeyword.trim();
    }

    public String getSysWebsitedescribe() {
        return sysWebsitedescribe;
    }

    public void setSysWebsitedescribe(String sysWebsitedescribe) {
        this.sysWebsitedescribe = sysWebsitedescribe == null ? null : sysWebsitedescribe.trim();
    }

    public String getSysWebsiteicp() {
        return sysWebsiteicp;
    }

    public void setSysWebsiteicp(String sysWebsiteicp) {
        this.sysWebsiteicp = sysWebsiteicp == null ? null : sysWebsiteicp.trim();
    }

    public String getSysWebsitefax() {
        return sysWebsitefax;
    }

    public void setSysWebsitefax(String sysWebsitefax) {
        this.sysWebsitefax = sysWebsitefax == null ? null : sysWebsitefax.trim();
    }

    public String getSysWebsitetel() {
        return sysWebsitetel;
    }

    public void setSysWebsitetel(String sysWebsitetel) {
        this.sysWebsitetel = sysWebsitetel == null ? null : sysWebsitetel.trim();
    }

    public String getSysWebsiteaddress() {
        return sysWebsiteaddress;
    }

    public void setSysWebsiteaddress(String sysWebsiteaddress) {
        this.sysWebsiteaddress = sysWebsiteaddress == null ? null : sysWebsiteaddress.trim();
    }

    public String getSysWebsitesignature() {
        return sysWebsitesignature;
    }

    public void setSysWebsitesignature(String sysWebsitesignature) {
        this.sysWebsitesignature = sysWebsitesignature == null ? null : sysWebsitesignature.trim();
    }

    public String getSysRegisterProtocol() {
        return sysRegisterProtocol;
    }

    public void setSysRegisterProtocol(String sysRegisterProtocol) {
        this.sysRegisterProtocol = sysRegisterProtocol == null ? null : sysRegisterProtocol.trim();
    }

	public Integer getAutoTenderStatus() {
		return autoTenderStatus;
	}

	public void setAutoTenderStatus(Integer autoTenderStatus) {
		this.autoTenderStatus = autoTenderStatus;
	}
}