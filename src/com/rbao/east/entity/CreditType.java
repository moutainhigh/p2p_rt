package com.rbao.east.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class CreditType extends BaseEntity implements Serializable{
	
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final String REDEEM_YES="REDEEM_YES";//赎回审核成功
	
	public static final String WITHDRAW_YES = "WITHDRAW_YES";//提现审核通过
	
	public static final String BORROW_OVERDUE = "BORROW_OVERDUE";//标逾期
	
	public static final String BORROW_END = "BORROW_END";//借款标流标borrow_end
	
	public static final String BORROW_TRANSFER_SUCCESS_AUCTION = "BORROW_TRANSFER_SUCCESS_AUCTION";//债券转让成功——竞拍人
	
	public static final String BORROW_TRANSFER_SUCCESS_TRANSFER = "BORROW_TRANSFER_SUCCESS_TRANSFER";//债券转让成功——转让人
	
	public static final String BORROW_YES = "BORROW_YES";//借款标复审通过borrow_yes
	
	public static final String REALNAME="realname";//实名认证code
	public static final String PHONE="phone";//手机认证code
	public static final String EMAIL="email";//邮箱认证code
	public static final String VIDEO="video";//视频认证code
	public static final String SCENE="scene";//现场认证code
	public static final String VIP="vip";//vip认证code
	
	public static final String INVEST_SUCCESS ="invest_success";//借款标有人投标时borrow_join
	
	public static final String credit_exchange = "credit_exchange";//积分兑换
	
	
	

	private Integer id;

    private String creditName;

    private String creditCode;

    private Integer creditScore;

    private Integer creditCycle;

    private Integer creditAwardTimes;

    private Integer creditInterval;

    private String creditRemark;

    private Integer creditOptUserid;

    private Date creditOptDatetime;

    private String creditOptIp;

    private Date creditOptUpdatetime;

    private String creditOptUpdateip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName == null ? null : creditName.trim();
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Integer getCreditCycle() {
        return creditCycle;
    }

    public void setCreditCycle(Integer creditCycle) {
        this.creditCycle = creditCycle;
    }

    public Integer getCreditAwardTimes() {
        return creditAwardTimes;
    }

    public void setCreditAwardTimes(Integer creditAwardTimes) {
        this.creditAwardTimes = creditAwardTimes;
    }

    public Integer getCreditInterval() {
        return creditInterval;
    }

    public void setCreditInterval(Integer creditInterval) {
        this.creditInterval = creditInterval;
    }

    public String getCreditRemark() {
        return creditRemark;
    }

    public void setCreditRemark(String creditRemark) {
        this.creditRemark = creditRemark == null ? null : creditRemark.trim();
    }

    public Integer getCreditOptUserid() {
        return creditOptUserid;
    }

    public void setCreditOptUserid(Integer creditOptUserid) {
        this.creditOptUserid = creditOptUserid;
    }

    public Date getCreditOptDatetime() {
        return creditOptDatetime;
    }

    public void setCreditOptDatetime(Date creditOptDatetime) {
        this.creditOptDatetime = creditOptDatetime;
    }

    public String getCreditOptIp() {
        return creditOptIp;
    }

    public void setCreditOptIp(String creditOptIp) {
        this.creditOptIp = creditOptIp == null ? null : creditOptIp.trim();
    }

    public Date getCreditOptUpdatetime() {
        return creditOptUpdatetime;
    }

    public void setCreditOptUpdatetime(Date creditOptUpdatetime) {
        this.creditOptUpdatetime = creditOptUpdatetime;
    }

    public String getCreditOptUpdateip() {
        return creditOptUpdateip;
    }

    public void setCreditOptUpdateip(String creditOptUpdateip) {
        this.creditOptUpdateip = creditOptUpdateip == null ? null : creditOptUpdateip.trim();
    }
    /**
     * 自定义积分规则
     * @param params 参数
     */
    public void rebuild(Map<String,Object> params){
    	//投标成功
    	if(this.getCreditCode().equals(INVEST_SUCCESS)){ 
    		BigDecimal tenderMoney = (BigDecimal) params.get("tenderMoney");
    		int period = Integer.parseInt(params.get("period").toString());
    		int isDay  = Integer.parseInt(params.get("isDay").toString());
    		BigDecimal month = new BigDecimal(period);
    		if(isDay == Borrow.IS_DAY_Y.intValue()){ //天标
    			month = month.divide(new BigDecimal(30),4,BigDecimal.ROUND_DOWN);
    		}
    		BigDecimal score = tenderMoney.divide(new BigDecimal(10000),4,BigDecimal.ROUND_DOWN)
    					.multiply(new BigDecimal(this.getCreditScore()))
    					.multiply(month).setScale(0,BigDecimal.ROUND_DOWN);
    		this.setCreditScore(score.intValue()); //
    	}else if(this.getCreditCode().equals(BORROW_TRANSFER_SUCCESS_AUCTION)||this.getCreditCode().equals(BORROW_TRANSFER_SUCCESS_TRANSFER)||this.getCreditCode().equals(REDEEM_YES)){
    		    BigDecimal oneDayCreditScore=new BigDecimal(this.getCreditScore())
    		    					.divide(new BigDecimal(30),16,BigDecimal.ROUND_DOWN)
    		    					.divide(new BigDecimal(10000),16,BigDecimal.ROUND_DOWN);
    		    BigDecimal stayingCapital= new BigDecimal(params.get("stayingCapital").toString());
    		    BigDecimal lastDay=new BigDecimal(params.get("lastDay").toString());
    		    
    		    BigDecimal score=oneDayCreditScore.multiply(stayingCapital).multiply(lastDay).setScale(0, BigDecimal.ROUND_DOWN);
    		    this.setCreditScore(score.intValue());   
    	}
    }
    
}