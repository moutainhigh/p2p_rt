package com.rbao.east.entity;

import java.util.Date;

public class AttestationApply extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1462885506174777496L;
	
	//状态
	public static final Integer ATTESTATIAONAPPLY_APPLY = 0;//申请
	public static final Integer ATTESTATIAONAPPLY_PASS = 1;//通过
	public static final Integer ATTESTATIAONAPPLY_FAILED= -1;//不通过

	private Integer id;

    private Integer userId;

    private Integer attestationTypeid;

    private String attestationName;

    private Integer attestationStatus;

    private String attestationImg;

    private Integer attestationScore;

    private String attestationRemarks;

    private Date attestationVerifyDatetime;

    private Integer attestationVerifyUserid;

    private String attestationVerifyReview;

    private String attestationApplyIp;

    private Date attestationApplyDatetime;
    
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

    public Integer getAttestationTypeid() {
        return attestationTypeid;
    }

    public void setAttestationTypeid(Integer attestationTypeid) {
        this.attestationTypeid = attestationTypeid;
    }

    public String getAttestationName() {
        return attestationName;
    }

    public void setAttestationName(String attestationName) {
        this.attestationName = attestationName == null ? null : attestationName.trim();
    }

    public Integer getAttestationStatus() {
        return attestationStatus;
    }

    public void setAttestationStatus(Integer attestationStatus) {
        this.attestationStatus = attestationStatus;
    }

    public String getAttestationImg() {
        return attestationImg;
    }

    public void setAttestationImg(String attestationImg) {
        this.attestationImg = attestationImg == null ? null : attestationImg.trim();
    }

    public Integer getAttestationScore() {
        return attestationScore;
    }

    public void setAttestationScore(Integer attestationScore) {
        this.attestationScore = attestationScore;
    }

    public String getAttestationRemarks() {
        return attestationRemarks;
    }

    public void setAttestationRemarks(String attestationRemarks) {
        this.attestationRemarks = attestationRemarks == null ? null : attestationRemarks.trim();
    }

    public Date getAttestationVerifyDatetime() {
        return attestationVerifyDatetime;
    }

    public void setAttestationVerifyDatetime(Date attestationVerifyDatetime) {
        this.attestationVerifyDatetime = attestationVerifyDatetime;
    }

    public Integer getAttestationVerifyUserid() {
        return attestationVerifyUserid;
    }

    public void setAttestationVerifyUserid(Integer attestationVerifyUserid) {
        this.attestationVerifyUserid = attestationVerifyUserid;
    }

    public String getAttestationVerifyReview() {
        return attestationVerifyReview;
    }

    public void setAttestationVerifyReview(String attestationVerifyReview) {
        this.attestationVerifyReview = attestationVerifyReview == null ? null : attestationVerifyReview.trim();
    }

    public String getAttestationApplyIp() {
        return attestationApplyIp;
    }

    public void setAttestationApplyIp(String attestationApplyIp) {
        this.attestationApplyIp = attestationApplyIp == null ? null : attestationApplyIp.trim();
    }

    public Date getAttestationApplyDatetime() {
        return attestationApplyDatetime;
    }

    public void setAttestationApplyDatetime(Date attestationApplyDatetime) {
        this.attestationApplyDatetime = attestationApplyDatetime;
    }
}