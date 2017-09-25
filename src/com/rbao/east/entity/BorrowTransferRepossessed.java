package com.rbao.east.entity;

public class BorrowTransferRepossessed extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6774374154057813052L;

	private Integer id;

    private Integer transferId;

    private Integer repossessedId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public Integer getRepossessedId() {
        return repossessedId;
    }

    public void setRepossessedId(Integer repossessedId) {
        this.repossessedId = repossessedId;
    }
}