package com.rbao.east.entity;

import java.util.Date;

public class AttestationType extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4394659060074359889L;

	private Integer id;

    private String attestationName;

    private String attestationSequence;

    private Integer attestationStatus;

    private Integer attestationScore;

    private String attestationSummary;

    private String attestationRemark;

    private Date attestationDatetime;

    private String attestationIp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttestationName() {
        return attestationName;
    }

    public void setAttestationName(String attestationName) {
        this.attestationName = attestationName == null ? null : attestationName.trim();
    }

    public String getAttestationSequence() {
        return attestationSequence;
    }

    public void setAttestationSequence(String attestationSequence) {
        this.attestationSequence = attestationSequence == null ? null : attestationSequence.trim();
    }

    public Integer getAttestationStatus() {
        return attestationStatus;
    }

    public void setAttestationStatus(Integer attestationStatus) {
        this.attestationStatus = attestationStatus;
    }

    public Integer getAttestationScore() {
        return attestationScore;
    }

    public void setAttestationScore(Integer attestationScore) {
        this.attestationScore = attestationScore;
    }

    public String getAttestationSummary() {
        return attestationSummary;
    }

    public void setAttestationSummary(String attestationSummary) {
        this.attestationSummary = attestationSummary == null ? null : attestationSummary.trim();
    }

    public String getAttestationRemark() {
        return attestationRemark;
    }

    public void setAttestationRemark(String attestationRemark) {
        this.attestationRemark = attestationRemark == null ? null : attestationRemark.trim();
    }

    public Date getAttestationDatetime() {
        return attestationDatetime;
    }

    public void setAttestationDatetime(Date attestationDatetime) {
        this.attestationDatetime = attestationDatetime;
    }

    public String getAttestationIp() {
        return attestationIp;
    }

    public void setAttestationIp(String attestationIp) {
        this.attestationIp = attestationIp == null ? null : attestationIp.trim();
    }
}