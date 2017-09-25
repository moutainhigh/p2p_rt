package com.rbao.east.entity;

public class SysConfigParams extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3204699227036621536L;
	
	public static final String recommend_open="1";//推广奖励开启
	public static final String recommend_close="0";//推广奖励关闭
	
	
	public enum inputTypeEnum {
        text, textarea;
    }

	private Integer id;

    private String sysName;

    private String sysValue;

    private String sysKey;

    private String sysType;

    private String inputType;

    private String remark;

    private String limitCode;

    private String sysValueBig;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName == null ? null : sysName.trim();
    }

    public String getSysValue() {
        return sysValue;
    }

    public void setSysValue(String sysValue) {
        this.sysValue = sysValue == null ? null : sysValue;
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey == null ? null : sysKey.trim();
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType == null ? null : sysType.trim();
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType == null ? null : inputType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getLimitCode() {
        return limitCode;
    }

    public void setLimitCode(String limitCode) {
        this.limitCode = limitCode == null ? null : limitCode.trim();
    }

    public String getSysValueBig() {
        return sysValueBig;
    }

    public void setSysValueBig(String sysValueBig) {
        this.sysValueBig = sysValueBig == null ? null : sysValueBig;
    }
    
    /**
     * 自动匹配，不用关心类型
     * 但是InputType必须有值
     * @param value
     */
    public void setSysValueAuto(String value){

    	if(this.getInputType().equals(inputTypeEnum.textarea.name())){
    		this.setSysValueBig(value);
    		
    	}else if(this.getInputType().equals(inputTypeEnum.text.name())){
    		this.setSysValue(value);
    	}
    }
    /**
     * 自动匹配，不用关心类型
     * 但是InputType必须有值
     * @param value
     */
    public String getSysValueAuto(){
    	String sysValueAuto = null;
    	if(this.getInputType().equals(inputTypeEnum.textarea.name())){
    		sysValueAuto = this.getSysValueBig();
    	}else if(this.getInputType().equals(inputTypeEnum.text.name())){
    		sysValueAuto = this.getSysValue();
    	}
    	return sysValueAuto;
    }
}