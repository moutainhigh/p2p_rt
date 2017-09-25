package com.rbao.east.entity;

import java.util.Date;

public class OperatorLog extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3739748327001022972L;

	public static final Integer TYPE_ADMIN = 1; // 后台日志
	public static final Integer TYPE_FRONT = 2;// 前台日志

	public static final Integer CATEGORY_LOGIN = 1; // 登录等处类别
	public static final Integer CATEGORY_BORROW = 2; // 借款管理
	public static final Integer CATEGORY_DEDUCT = 3; // 扣费
	public static final Integer CATEGORY_AUTHORITY = 7;// 角色权限
	public static final Integer CATEGORY_ATTESTATION=8;//认证审核
	public static final Integer CATEGORY_RECHARGE=9;//充值
	public static final Integer CATEGORY_BANKCARD=10;//银行卡
	public static final Integer CATEGORY_DRAW=11;//提现
	public static final Integer CATEGORY_ATTESTATION_APPLY=12;//认证申请
	public static final Integer CATEGORY_PERSONAL_MESSAGE=13;//个人资料
	public static final Integer CATEGORY_BORROW_REDEEM=14;//投资赎回
	public static final Integer CATEGORY_PRODUCTS=15;//理财产品
	public static final Integer CATEGORY_SHOP_GOODS = 16;// 积分商城--商品维护
	public static final Integer CATEGORY_SHOP_CATEGORY = 17;// 积分商城--商品维护
	public static final Integer CATEGORY_SHOP_ORDER = 18;// 积分商城--订单维护
	
	public static final Integer CATEGORY_EXPERIENCE_GOLD = 19;//体验金
	
	private Integer id;

	private String logUserid;

	private String operatorReturn;

	private String operatorTitle;

	private Date createTime;

	private String operatorIp;

	private Integer operatorCategory;

	private String linkUrl;

	private Integer operatorStatus;

	private String operatorParams;

	private Integer operatorType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogUserid() {
		return logUserid;
	}

	public void setLogUserid(String logUserid) {
		this.logUserid = logUserid;
	}

	public String getOperatorReturn() {
		return operatorReturn;
	}

	public void setOperatorReturn(String operatorReturn) {
		this.operatorReturn = operatorReturn == null ? null : operatorReturn
				.trim();
	}

	public String getOperatorTitle() {
		return operatorTitle;
	}

	public void setOperatorTitle(String operatorTitle) {
		this.operatorTitle = operatorTitle == null ? null : operatorTitle
				.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp == null ? null : operatorIp.trim();
	}

	public Integer getOperatorCategory() {
		return operatorCategory;
	}

	public void setOperatorCategory(Integer operatorCategory) {
		this.operatorCategory = operatorCategory;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl == null ? null : linkUrl.trim();
	}

	public Integer getOperatorStatus() {
		return operatorStatus;
	}

	public void setOperatorStatus(Integer operatorStatus) {
		this.operatorStatus = operatorStatus;
	}

	public String getOperatorParams() {
		return operatorParams;
	}

	public void setOperatorParams(String operatorParams) {
		this.operatorParams = operatorParams == null ? null : operatorParams
				.trim();
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType == null ? null : operatorType;
	}
}