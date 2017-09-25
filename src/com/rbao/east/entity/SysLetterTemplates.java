package com.rbao.east.entity;

public class SysLetterTemplates extends BaseEntity{
	private static final long serialVersionUID = 4977072334245938839L;

	private Integer id;

    private String sysRegisteredMailinfo;

    private String sysVerifyMailinfo;

    private String sysPasswordMailinfo;

    private String sysUpdataphone;

    private String sysPaypwdMailinfo;

    private String sysSmsInfo;

    private String sysLetter;

    private Integer sysSmsIsopen;

    private Integer sysMailIsopen;

    private Integer sysLetterIsopen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysRegisteredMailinfo() {
        return sysRegisteredMailinfo;
    }

    public void setSysRegisteredMailinfo(String sysRegisteredMailinfo) {
        this.sysRegisteredMailinfo = sysRegisteredMailinfo == null ? null : sysRegisteredMailinfo.trim();
    }

    public String getSysVerifyMailinfo() {
        return sysVerifyMailinfo;
    }

    public void setSysVerifyMailinfo(String sysVerifyMailinfo) {
        this.sysVerifyMailinfo = sysVerifyMailinfo == null ? null : sysVerifyMailinfo.trim();
    }

    public String getSysPasswordMailinfo() {
        return sysPasswordMailinfo;
    }

    public void setSysPasswordMailinfo(String sysPasswordMailinfo) {
        this.sysPasswordMailinfo = sysPasswordMailinfo == null ? null : sysPasswordMailinfo.trim();
    }

    public String getSysUpdataphone() {
        return sysUpdataphone;
    }

    public void setSysUpdataphone(String sysUpdataphone) {
        this.sysUpdataphone = sysUpdataphone == null ? null : sysUpdataphone.trim();
    }

    public String getSysPaypwdMailinfo() {
        return sysPaypwdMailinfo;
    }

    public void setSysPaypwdMailinfo(String sysPaypwdMailinfo) {
        this.sysPaypwdMailinfo = sysPaypwdMailinfo == null ? null : sysPaypwdMailinfo.trim();
    }

    public String getSysSmsInfo() {
        return sysSmsInfo;
    }

    public void setSysSmsInfo(String sysSmsInfo) {
        this.sysSmsInfo = sysSmsInfo == null ? null : sysSmsInfo.trim();
    }

    public String getSysLetter() {
        return sysLetter;
    }

    public void setSysLetter(String sysLetter) {
        this.sysLetter = sysLetter == null ? null : sysLetter.trim();
    }

    public Integer getSysSmsIsopen() {
        return sysSmsIsopen;
    }

    public void setSysSmsIsopen(Integer sysSmsIsopen) {
        this.sysSmsIsopen = sysSmsIsopen;
    }

    public Integer getSysMailIsopen() {
        return sysMailIsopen;
    }

    public void setSysMailIsopen(Integer sysMailIsopen) {
        this.sysMailIsopen = sysMailIsopen;
    }

    public Integer getSysLetterIsopen() {
        return sysLetterIsopen;
    }

    public void setSysLetterIsopen(Integer sysLetterIsopen) {
        this.sysLetterIsopen = sysLetterIsopen;
    }
}