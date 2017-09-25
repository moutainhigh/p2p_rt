package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BorrowTransfer extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9079386385028387251L;

	public static final Integer STATUS_AUCTIONING = 1;//招标中
	public static final Integer STATUS_SUCCESS = 2;//转让成功
	public static final Integer STATUS_FAILD = 3;//转让失败
	
	public static final Map<Integer,String> ALL_STATUS=new HashMap<Integer, String>();
	static{
		ALL_STATUS.put(STATUS_AUCTIONING, "招标中");
		ALL_STATUS.put(STATUS_SUCCESS, "转让成功");
		ALL_STATUS.put(STATUS_FAILD, "转让失败");
	}
	
	private Integer id;

    private Integer tenderId;
    
    private BigDecimal repossessedCapital ;

    private BigDecimal transferMoney; //转让基准价格
    
    private BigDecimal lastAuctionMoney ;//最新竞拍价格

    private Integer transferStatus;

    private BigDecimal transferInterestRate; //承接人获得的利率

    private Date createTime;

    private Date endTime;

    private BigDecimal transferFee; //转让手续费
    
    private String createIp;
    
    private String borrowTitle;
    
    private Integer residualMaturity; //剩余期限
    
    private Date succTime ;//成交时间
    private Integer userId ;//转让人
    private Integer acceptUserId;//承接人
    private BigDecimal repossessedInterest;//代收利息
    

    public String getBorrowTitle() {
		return borrowTitle;
	}

	public void setBorrowTitle(String borrowTitle) {
		this.borrowTitle = borrowTitle;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public BigDecimal getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(BigDecimal transferMoney) {
        this.transferMoney = transferMoney;
    }

    public Integer getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(Integer transferStatus) {
        this.transferStatus = transferStatus;
    }

    public BigDecimal getTransferInterestRate() {
        return transferInterestRate;
    }

    public void setTransferInterestRate(BigDecimal transferInterestRate) {
        this.transferInterestRate = transferInterestRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public BigDecimal getRepossessedCapital() {
		return repossessedCapital;
	}

	public void setRepossessedCapital(BigDecimal repossessedCapital) {
		this.repossessedCapital = repossessedCapital;
	}

	public BigDecimal getLastAuctionMoney() {
		return lastAuctionMoney;
	}

	public void setLastAuctionMoney(BigDecimal lastAuctionMoney) {
		this.lastAuctionMoney = lastAuctionMoney;
	}

	public Integer getResidualMaturity() {
		return residualMaturity;
	}

	public void setResidualMaturity(Integer residualMaturity) {
		this.residualMaturity = residualMaturity;
	}

	public Date getSuccTime() {
		return succTime;
	}

	public void setSuccTime(Date succTime) {
		this.succTime = succTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAcceptUserId() {
		return acceptUserId;
	}

	public void setAcceptUserId(Integer acceptUserId) {
		this.acceptUserId = acceptUserId;
	}

	public BigDecimal getRepossessedInterest() {
		return repossessedInterest;
	}

	public void setRepossessedInterest(BigDecimal repossessedInterest) {
		this.repossessedInterest = repossessedInterest;
	}
	
	
	
}