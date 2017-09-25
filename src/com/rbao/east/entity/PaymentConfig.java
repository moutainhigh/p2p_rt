package com.rbao.east.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;

public class PaymentConfig extends BaseEntity{
	
	private static final long serialVersionUID = -8901945612123332268L;
	public static final Map<Short,String> ALL_PAYMENT_STATUS = new HashMap<Short,String>();//支付状态
	public static final Map<Integer,String> ALL_PAYMENT_TYPE = new HashMap<Integer,String>();//支付类型
	public static final Map<Integer,String> ALL_FEE_TYPE = new HashMap<Integer,String>();//费用类型
	//停用、启用
	public static final Short STATUS_STOP = 0;
	public static final Short STATUS_START = 1;
	
	//支付类型
	public static final Integer PAYMENT_TYPE_OOF=2;
	public static final Integer PAYMENT_TYPE_ON=1;
	
	//不收费、按比例收费、按固定金额收费
	public static final Integer FEE_TYPE_NONE=0;
	public static final Integer FEE_TYPE_PROPORTION=1;
	public static final Integer FEE_TYPE_FIXATION=2;
	
	static {
		ALL_PAYMENT_STATUS.put(STATUS_START, "启用");
		ALL_PAYMENT_STATUS.put(STATUS_STOP, "停用");
		
		ALL_PAYMENT_TYPE.put(PAYMENT_TYPE_OOF, "线下支付");
		ALL_PAYMENT_TYPE.put(PAYMENT_TYPE_ON, "线上支付");
		
		ALL_FEE_TYPE.put(FEE_TYPE_NONE, "不收费");
		ALL_FEE_TYPE.put(FEE_TYPE_PROPORTION, "比例收费");
		ALL_FEE_TYPE.put(FEE_TYPE_FIXATION, "固定费用");
	}
	private  String gatewaUrl;
	
	private String clientId ;//客户号、商户编号
	
	private String encryptKey ; //加密 秘钥

	private String account ;//账号、终端号
	
	private String viewPaymentStatus;
	
	private String viewPaymentType;
	
	private String viewFeeType;
	
    private Integer id;

    private String paymentName;

    private String paymentCode;

    private Short paymentStatus;

    private Integer paymentType;

    private String paymentConfig;

    private Integer paymentFeeType;

    private Float paymentMaxMoney;

    private Float paymentMaxFee;

    private String paymentDescription;

    private Short paymentSequence;
    private String paymentIco;

	public String getPaymentIco() {
		return paymentIco;
	}

	public void setPaymentIco(String paymentIco) {
		this.paymentIco = paymentIco;
	}

	public String getViewPaymentStatus() {
		return viewPaymentStatus;
	}

	public void setViewPaymentStatus(String viewPaymentStatus) {
		this.viewPaymentStatus = viewPaymentStatus;
	}

	public String getViewPaymentType() {
		return viewPaymentType;
	}

	public void setViewPaymentType(String viewPaymentType) {
		this.viewPaymentType = viewPaymentType;
	}

	public String getViewFeeType() {
		return viewFeeType;
	}

	public void setViewFeeType(String viewFeeType) {
		this.viewFeeType = viewFeeType;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName == null ? null : paymentName.trim();
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode == null ? null : paymentCode.trim();
    }

    public Short getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Short paymentStatus) {
        this.paymentStatus = paymentStatus;
        setViewPaymentStatus(ALL_PAYMENT_STATUS.get(paymentStatus));
    }

    public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
		setViewPaymentType(ALL_PAYMENT_TYPE.get(paymentType));
	}

	public String getPaymentConfig() {
		buildPaymentConfig();
        return paymentConfig;
    }
	public void buildPaymentConfig(){
		//str = "[{\"id\":1,\"sex\":0,\"name\":\"test\",\"age\":100,\"viewSex\":\"\"}]";
		this.paymentConfig = "";

		if(!StringUtils.isEmpty(getClientId())){
			this.paymentConfig += "\"clientId\":\""+getClientId()+"\",";
		}
		if(!StringUtils.isEmpty(getEncryptKey())){
			this.paymentConfig += "\"encryptKey\":\""+getEncryptKey()+"\",";
		}
		if(!StringUtils.isEmpty(getAccount())){
			this.paymentConfig += "\"account\":\""+getAccount()+"\",";
		}
		if(!StringUtils.isEmpty(getGatewaUrl())){
			this.paymentConfig += "\"gatewaUrl\":\""+getGatewaUrl()+"\",";;
		}
		
		if(!StringUtils.isEmpty(this.paymentConfig))
			this.paymentConfig = "[{" + StringUtil.removeLastStr(this.paymentConfig, ",") + "}]";
		
	}

    public void setPaymentConfig(String paymentConfig) {
        this.paymentConfig = paymentConfig == null ? null : paymentConfig.trim();
        if(!StringUtils.isEmpty(paymentConfig)){
        	PaymentConfig cfg = (PaymentConfig) SpringUtils.toObject(paymentConfig, PaymentConfig.class);
            setClientId(cfg.getClientId());
            setEncryptKey(cfg.getEncryptKey());
            setAccount(cfg.getAccount());
            setGatewaUrl(cfg.getGatewaUrl());
        }
        
    }

    public Integer getPaymentFeeType() {
        return paymentFeeType;
    }

    public void setPaymentFeeType(Integer paymentFeeType) {
        this.paymentFeeType = paymentFeeType;
        setViewFeeType(ALL_FEE_TYPE.get(paymentFeeType));
    }

    public Float getPaymentMaxMoney() {
        return paymentMaxMoney;
    }

    public void setPaymentMaxMoney(Float paymentMaxMoney) {
        this.paymentMaxMoney = paymentMaxMoney;
    }

    public Float getPaymentMaxFee() {
        return paymentMaxFee;
    }

    public void setPaymentMaxFee(Float paymentMaxFee) {
        this.paymentMaxFee = paymentMaxFee;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription == null ? null : paymentDescription.trim();
    }

    public Short getPaymentSequence() {
        return paymentSequence;
    }

    public void setPaymentSequence(Short paymentSequence) {
        this.paymentSequence = paymentSequence;
    }

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getGatewaUrl() {
		return gatewaUrl;
	}

	public void setGatewaUrl(String gatewaUrl) {
		this.gatewaUrl = gatewaUrl;
	}
	
}