package com.rbao.east.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FinancialProducts extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//产品状态
	public static final Map<Integer,String> ALL_PRODUCTS_STATUS = new HashMap<Integer,String>();
	public static final Integer PRODUCTS_STATUS_CONFIGURABLE = 1; //configurable可配置
	public static final Integer PRODUCTS_STATUS_ESTABLISH = 2;//已成立establish
	public static final Integer PRODUCTS_STATUS_CASH = 3;//已兑付cash
	
	static{
		ALL_PRODUCTS_STATUS.put(PRODUCTS_STATUS_CONFIGURABLE, "可配置");
		ALL_PRODUCTS_STATUS.put(PRODUCTS_STATUS_ESTABLISH, "已成立");
		ALL_PRODUCTS_STATUS.put(PRODUCTS_STATUS_CASH, "已兑付");
	}

	private Integer id;

    private String productsTitle;

    private Integer productsStatus;

    private Integer productsType;

    private Long productsMinProfit;

    private Long productsMaxProfit;

    private Integer productsTimeLimit;

    private Date productsPublishDate;

    private Integer productsScale;

    private Integer productsInvestBegin;

    private String productsIncomeDistribution;

    private Integer productsAdduser;

    private Date productsAddtime;

    private Integer productsUpdateuser;

    private Date productsUpdatetime;

    private String productsContent;
    
    private String viewProductsStatus;
    
    private User addUser;
    
    private User updateUser;
    
    private Dictionary dictionaryProductsType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductsTitle() {
        return productsTitle;
    }

    public void setProductsTitle(String productsTitle) {
        this.productsTitle = productsTitle == null ? null : productsTitle.trim();
    }

    public Integer getProductsStatus() {
        return productsStatus;
    }

    public void setProductsStatus(Integer productsStatus) {
        this.productsStatus = productsStatus;
        setViewProductsStatus(ALL_PRODUCTS_STATUS.get(productsStatus));
    }

    public Integer getProductsType() {
        return productsType;
    }

    public void setProductsType(Integer productsType) {
        this.productsType = productsType;
    }

    public Long getProductsMinProfit() {
        return productsMinProfit;
    }

    public void setProductsMinProfit(Long productsMinProfit) {
        this.productsMinProfit = productsMinProfit;
    }

    public Long getProductsMaxProfit() {
        return productsMaxProfit;
    }

    public void setProductsMaxProfit(Long productsMaxProfit) {
        this.productsMaxProfit = productsMaxProfit;
    }

    public Integer getProductsTimeLimit() {
        return productsTimeLimit;
    }

    public void setProductsTimeLimit(Integer productsTimeLimit) {
        this.productsTimeLimit = productsTimeLimit;
    }

    public Date getProductsPublishDate() {
        return productsPublishDate;
    }

    public void setProductsPublishDate(Date productsPublishDate) {
        this.productsPublishDate = productsPublishDate;
    }

    public Integer getProductsScale() {
        return productsScale;
    }

    public void setProductsScale(Integer productsScale) {
        this.productsScale = productsScale;
    }

    public Integer getProductsInvestBegin() {
        return productsInvestBegin;
    }

    public void setProductsInvestBegin(Integer productsInvestBegin) {
        this.productsInvestBegin = productsInvestBegin;
    }

    public String getProductsIncomeDistribution() {
        return productsIncomeDistribution;
    }

    public void setProductsIncomeDistribution(String productsIncomeDistribution) {
        this.productsIncomeDistribution = productsIncomeDistribution == null ? null : productsIncomeDistribution.trim();
    }

    public Integer getProductsAdduser() {
        return productsAdduser;
    }

    public void setProductsAdduser(Integer productsAdduser) {
        this.productsAdduser = productsAdduser;
    }

    public Date getProductsAddtime() {
        return productsAddtime;
    }

    public void setProductsAddtime(Date productsAddtime) {
        this.productsAddtime = productsAddtime;
    }

    public Integer getProductsUpdateuser() {
        return productsUpdateuser;
    }

    public void setProductsUpdateuser(Integer productsUpdateuser) {
        this.productsUpdateuser = productsUpdateuser;
    }

    public Date getProductsUpdatetime() {
        return productsUpdatetime;
    }

    public void setProductsUpdatetime(Date productsUpdatetime) {
        this.productsUpdatetime = productsUpdatetime;
    }

    public String getProductsContent() {
        return productsContent;
    }

    public void setProductsContent(String productsContent) {
        this.productsContent = productsContent == null ? null : productsContent.trim();
    }

	public User getAddUser() {
		return addUser;
	}

	public void setAddUser(User addUser) {
		this.addUser = addUser;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public String getViewProductsStatus() {
		return viewProductsStatus;
	}

	public void setViewProductsStatus(String viewProductsStatus) {
		this.viewProductsStatus = viewProductsStatus;
	}

	public Dictionary getDictionaryProductsType() {
		return dictionaryProductsType;
	}

	public void setDictionaryProductsType(Dictionary dictionaryProductsType) {
		this.dictionaryProductsType = dictionaryProductsType;
	}
}