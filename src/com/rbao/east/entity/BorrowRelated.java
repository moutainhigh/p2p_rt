package com.rbao.east.entity;

import java.util.Date;

public class BorrowRelated extends BaseEntity{
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Integer STATUS_NEW=1; //新建
	public static final Integer STATUS_FIRSTAUDIT_YES= 2;//初审通过

	private Integer id;

    private Integer relatedPeriodNum;

    private Date relatedPublishTime;

    private Date relatedAllowTenderTime;

    private Date addTime;

    private Integer borrowoneId;
    
    private Borrow borrowone;

    private Integer borrowtwoId;

    private Borrow borrowtwo;
    
    private Integer borrowthreeId;

    private Borrow borrowthree;
    
    private Long borrowOneCount;
    
    private Long borrowTwoCount;
    
    private Long borrowThreeCount;
    
    
    
    
    public Long getBorrowOneCount() {
		return borrowOneCount;
	}

	public void setBorrowOneCount(Long borrowOneCount) {
		this.borrowOneCount = borrowOneCount;
	}

	public Long getBorrowTwoCount() {
		return borrowTwoCount;
	}

	public void setBorrowTwoCount(Long borrowTwoCount) {
		this.borrowTwoCount = borrowTwoCount;
	}

	public Long getBorrowThreeCount() {
		return borrowThreeCount;
	}

	public void setBorrowThreeCount(Long borrowThreeCount) {
		this.borrowThreeCount = borrowThreeCount;
	}

	private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRelatedPeriodNum() {
        return relatedPeriodNum;
    }

    public void setRelatedPeriodNum(Integer relatedPeriodNum) {
        this.relatedPeriodNum = relatedPeriodNum;
    }

    public Date getRelatedPublishTime() {
        return relatedPublishTime;
    }

    public void setRelatedPublishTime(Date relatedPublishTime) {
        this.relatedPublishTime = relatedPublishTime;
    }

    public Date getRelatedAllowTenderTime() {
        return relatedAllowTenderTime;
    }

    public void setRelatedAllowTenderTime(Date relatedAllowTenderTime) {
        this.relatedAllowTenderTime = relatedAllowTenderTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getBorrowoneId() {
        return borrowoneId;
    }

    public void setBorrowoneId(Integer borrowoneId) {
        this.borrowoneId = borrowoneId;
    }

    public Integer getBorrowtwoId() {
        return borrowtwoId;
    }

    public void setBorrowtwoId(Integer borrowtwoId) {
        this.borrowtwoId = borrowtwoId;
    }

    public Integer getBorrowthreeId() {
        return borrowthreeId;
    }

    public void setBorrowthreeId(Integer borrowthreeId) {
        this.borrowthreeId = borrowthreeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Borrow getBorrowone() {
		return borrowone;
	}

	public void setBorrowone(Borrow borrowone) {
		this.borrowone = borrowone;
	}

	public Borrow getBorrowtwo() {
		return borrowtwo;
	}

	public void setBorrowtwo(Borrow borrowtwo) {
		this.borrowtwo = borrowtwo;
	}

	public Borrow getBorrowthree() {
		return borrowthree;
	}

	public void setBorrowthree(Borrow borrowthree) {
		this.borrowthree = borrowthree;
	}
    
    
    
}