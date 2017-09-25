package com.rbao.east.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BorrowCollection extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Map<Integer, String> BC_STATUS=new HashMap<Integer, String>();
	public static final Integer STATUS_LAW = 1;//法律
	public static final Integer STATUS_SITE = 2;//现场
	public static final Integer STATUS_PHONE = 3;//电话
	
	static{
		BC_STATUS.put(STATUS_LAW, "法律");
		BC_STATUS.put(STATUS_SITE, "现场");
		BC_STATUS.put(STATUS_PHONE, "电话");
	}
	
    private Integer id;

    private Integer borrowId;

    private Integer collectionType;

    private String collectionUser;

    private Date collectionTime;

    private String collectionResult;

    private String collectionRemark;

    private Integer addUserid;

    private Date addTime;

    private String addIp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public Integer getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Integer collectionType) {
        this.collectionType = collectionType;
    }

    public String getCollectionUser() {
        return collectionUser;
    }

    public void setCollectionUser(String collectionUser) {
        this.collectionUser = collectionUser == null ? null : collectionUser.trim();
    }

    public Date getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getCollectionResult() {
        return collectionResult;
    }

    public void setCollectionResult(String collectionResult) {
        this.collectionResult = collectionResult == null ? null : collectionResult.trim();
    }

    public String getCollectionRemark() {
        return collectionRemark;
    }

    public void setCollectionRemark(String collectionRemark) {
        this.collectionRemark = collectionRemark == null ? null : collectionRemark.trim();
    }

    public Integer getAddUserid() {
        return addUserid;
    }

    public void setAddUserid(Integer addUserid) {
        this.addUserid = addUserid;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }
}