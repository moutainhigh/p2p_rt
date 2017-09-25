package com.rbao.east.entity;

public class RelationMessage extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1942713052210440356L;

	private Integer id;

    private Integer userId;

    private String addressTel;

    private String mobilePhone;

    private String province;

    private String city;

    private String area;

    private String postcode;

    private String newAddress;

    private String linkman2Name;

    private Integer linkman2Relation;

    private String linkman2Phone;

    private String linkman2Tel;

    private String linkman3Name;

    private Integer linkman3Relation;

    private String linkman3Phone;

    private String linkman3Tel;

    private String linkman4Name;

    private Integer linkman4Relation;

    private String linkman4Phone;

    private String linkman4Tel;

    private String msn;

    private String qq;

    private String ww;

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

    public String getAddressTel() {
        return addressTel;
    }

    public void setAddressTel(String addressTel) {
        this.addressTel = addressTel == null ? null : addressTel.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress == null ? null : newAddress.trim();
    }

    public String getLinkman2Name() {
        return linkman2Name;
    }

    public void setLinkman2Name(String linkman2Name) {
        this.linkman2Name = linkman2Name == null ? null : linkman2Name.trim();
    }

    public Integer getLinkman2Relation() {
        return linkman2Relation;
    }

    public void setLinkman2Relation(Integer linkman2Relation) {
        this.linkman2Relation = linkman2Relation;
    }

    public String getLinkman2Phone() {
        return linkman2Phone;
    }

    public void setLinkman2Phone(String linkman2Phone) {
        this.linkman2Phone = linkman2Phone == null ? null : linkman2Phone.trim();
    }

    public String getLinkman2Tel() {
        return linkman2Tel;
    }

    public void setLinkman2Tel(String linkman2Tel) {
        this.linkman2Tel = linkman2Tel == null ? null : linkman2Tel.trim();
    }

    public String getLinkman3Name() {
        return linkman3Name;
    }

    public void setLinkman3Name(String linkman3Name) {
        this.linkman3Name = linkman3Name == null ? null : linkman3Name.trim();
    }

    public Integer getLinkman3Relation() {
        return linkman3Relation;
    }

    public void setLinkman3Relation(Integer linkman3Relation) {
        this.linkman3Relation = linkman3Relation;
    }

    public String getLinkman3Phone() {
        return linkman3Phone;
    }

    public void setLinkman3Phone(String linkman3Phone) {
        this.linkman3Phone = linkman3Phone == null ? null : linkman3Phone.trim();
    }

    public String getLinkman3Tel() {
        return linkman3Tel;
    }

    public void setLinkman3Tel(String linkman3Tel) {
        this.linkman3Tel = linkman3Tel == null ? null : linkman3Tel.trim();
    }

    public String getLinkman4Name() {
        return linkman4Name;
    }

    public void setLinkman4Name(String linkman4Name) {
        this.linkman4Name = linkman4Name == null ? null : linkman4Name.trim();
    }

    public Integer getLinkman4Relation() {
        return linkman4Relation;
    }

    public void setLinkman4Relation(Integer linkman4Relation) {
        this.linkman4Relation = linkman4Relation;
    }

    public String getLinkman4Phone() {
        return linkman4Phone;
    }

    public void setLinkman4Phone(String linkman4Phone) {
        this.linkman4Phone = linkman4Phone == null ? null : linkman4Phone.trim();
    }

    public String getLinkman4Tel() {
        return linkman4Tel;
    }

    public void setLinkman4Tel(String linkman4Tel) {
        this.linkman4Tel = linkman4Tel == null ? null : linkman4Tel.trim();
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn == null ? null : msn.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWw() {
        return ww;
    }

    public void setWw(String ww) {
        this.ww = ww == null ? null : ww.trim();
    }
}