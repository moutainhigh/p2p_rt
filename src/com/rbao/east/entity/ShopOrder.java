package com.rbao.east.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShopOrder extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5403217528071132750L;
	
	public static final Integer status_no_send=1;
	public static final Integer status_yes_send=2;
	public static final Integer status_cancle_by_user=3;
	public static final Integer status_cancle_by_plat=4;
	
	public static final Map<Integer,String> ALL_ShopOrder_STATUS=new HashMap<Integer, String>();
	
	static{
		ALL_ShopOrder_STATUS.put(status_no_send,"未发货");
		ALL_ShopOrder_STATUS.put(status_yes_send, "已发货");
		ALL_ShopOrder_STATUS.put(status_cancle_by_user,"用户取消");
		ALL_ShopOrder_STATUS.put(status_cancle_by_plat,"平台取消");
		
	}
	
	
    private Integer id;

    private Integer userId;

    private String orderNo;//订单号

    private Integer goodsId;

    private Integer goodsCount;//订单商品数量

    private Integer castCredit;//花费积分

    private String recvAddress;//收件人地址

    private String recvUser;//收件人姓名

    private String recvTel;//收件人电话

    private Integer status;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer vsion;

    private Integer flag1;

    private String misc1;//快递名称

    private String misc2;//快递单号
    
    private String addIp;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getCastCredit() {
        return castCredit;
    }

    public void setCastCredit(Integer castCredit) {
        this.castCredit = castCredit;
    }

    public String getRecvAddress() {
        return recvAddress;
    }

    public void setRecvAddress(String recvAddress) {
        this.recvAddress = recvAddress == null ? null : recvAddress.trim();
    }

    public String getRecvUser() {
        return recvUser;
    }

    public void setRecvUser(String recvUser) {
        this.recvUser = recvUser == null ? null : recvUser.trim();
    }

    public String getRecvTel() {
        return recvTel;
    }

    public void setRecvTel(String recvTel) {
        this.recvTel = recvTel == null ? null : recvTel.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVsion() {
        return vsion;
    }

    public void setVsion(Integer vsion) {
        this.vsion = vsion;
    }

    public Integer getFlag1() {
        return flag1;
    }

    public void setFlag1(Integer flag1) {
        this.flag1 = flag1;
    }

    public String getMisc1() {
        return misc1;
    }

    public void setMisc1(String misc1) {
        this.misc1 = misc1 == null ? null : misc1.trim();
    }

    public String getMisc2() {
        return misc2;
    }

    public void setMisc2(String misc2) {
        this.misc2 = misc2 == null ? null : misc2.trim();
    }

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}
}