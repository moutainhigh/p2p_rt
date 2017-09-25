package com.rbao.east.entity;

public class SysSmtpConfig extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8239822052527496778L;

	private Integer id;

    private String sysSmtpServer;

    private Integer sysSmtpNeedauth;

    private String sysMailAddress;

    private String sysMailPassword;

    private String sysMailSender;

    private String sysMailName;

    private Integer sysSmtpPort;
    
    private Integer sysSslAuthentication;
    
    public static final Integer sysSslAuthentication_no =0;//ssl认证，不认证
    public static final Integer sysSslAuthentication_yes =1;//ssl认证，认证

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysSmtpServer() {
        return sysSmtpServer;
    }

    public void setSysSmtpServer(String sysSmtpServer) {
        this.sysSmtpServer = sysSmtpServer == null ? null : sysSmtpServer.trim();
    }

    public Integer getSysSmtpNeedauth() {
        return sysSmtpNeedauth;
    }

    public void setSysSmtpNeedauth(Integer sysSmtpNeedauth) {
        this.sysSmtpNeedauth = sysSmtpNeedauth;
    }

    public String getSysMailAddress() {
        return sysMailAddress;
    }

    public void setSysMailAddress(String sysMailAddress) {
        this.sysMailAddress = sysMailAddress == null ? null : sysMailAddress.trim();
    }

    public String getSysMailPassword() {
        return sysMailPassword;
    }

    public void setSysMailPassword(String sysMailPassword) {
        this.sysMailPassword = sysMailPassword == null ? null : sysMailPassword.trim();
    }

    public String getSysMailSender() {
        return sysMailSender;
    }

    public void setSysMailSender(String sysMailSender) {
        this.sysMailSender = sysMailSender == null ? null : sysMailSender.trim();
    }

    public String getSysMailName() {
        return sysMailName;
    }

    public void setSysMailName(String sysMailName) {
        this.sysMailName = sysMailName == null ? null : sysMailName.trim();
    }

    public Integer getSysSmtpPort() {
        return sysSmtpPort;
    }

    public void setSysSmtpPort(Integer sysSmtpPort) {
        this.sysSmtpPort = sysSmtpPort;
    }
    public Integer getSysSslAuthentication() {
		return sysSslAuthentication;
	}

	public void setSysSslAuthentication(Integer sysSslAuthentication) {
		this.sysSslAuthentication = sysSslAuthentication;
	}
}