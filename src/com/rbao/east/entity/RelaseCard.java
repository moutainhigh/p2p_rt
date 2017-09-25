package com.rbao.east.entity;

import java.util.Date;

public class RelaseCard extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 457713964694718809L;
	
	private Integer id;
	// 操作人ID
	private Integer userId;
	// 操作人
	private User operateUser;
	// 身份证图片URL地址
	private String cardPath;
	// 解绑原因
	private String reReason;
	// 要解绑用户银行卡ID
	private Integer UserBankId;
	// 要解绑用户银行卡
	private String bankAccount;
	
	private String bankBranch;

    private String bankCity;
    
    private String bankName;
    
    private Integer bankUserId;
    
    private User bankUser;
	/**
	 * 解绑申请状态
	 */
	private String result;
	
	private Date relasetime;
	
	public static final String RESULT_FAIL="1";//未通过申请
	public static final String RESULT_PASS="2";//申请通过
	public static final String RESULT_APPLY="-1";//申请中
	public static final String RESULT_DEFAULT="-2";//未申请
	
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
	public User getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(User operateUser) {
		this.operateUser = operateUser;
	}
	public String getCardPath() {
		return cardPath;
	}
	public void setCardPath(String cardPath) {
		this.cardPath = cardPath;
	}
	public String getReReason() {
		return reReason;
	}
	public void setReReason(String reReason) {
		this.reReason = reReason;
	}
	public Integer getUserBankId() {
		return UserBankId;
	}
	public void setUserBankId(Integer userBankId) {
		UserBankId = userBankId;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getBankCity() {
		return bankCity;
	}
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getRelasetime() {
		return relasetime;
	}
	public void setRelasetime(Date relasetime) {
		this.relasetime = relasetime;
	}
	public Integer getBankUserId() {
		return bankUserId;
	}
	public void setBankUserId(Integer bankUserId) {
		this.bankUserId = bankUserId;
	}
	public User getBankUser() {
		return bankUser;
	}
	public void setBankUser(User bankUser) {
		this.bankUser = bankUser;
	}
}
