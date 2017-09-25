package com.rbao.east.entity;
import java.math.BigDecimal;

public class SysFeesRate extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8033435386617995411L;
	
	public static final BigDecimal cashMinFee=new BigDecimal(2);//最小提现手续费
	
	public static final BigDecimal cashMaxFee=new BigDecimal(50);//最大提现手续费
	
	public static final Integer typeRegister=new Integer(1);//注册就送
	public static final Integer typeAttestation=new Integer(2);//三个认证通过就送
	public static final Integer typeTender=new Integer(3);//投标到一定金额就送钱

	private Integer id;

    private BigDecimal sysLoanRate;

    private BigDecimal sysLoanPoundage;

    private BigDecimal sysMaxLoan;

    private BigDecimal sysWithdrawalPoundage;

    private BigDecimal sysOnlinePoundage;

    private BigDecimal sysMaxWithdrawal;

    private BigDecimal sysMinWithdrawal;

    private BigDecimal sysContinueReward;

    private BigDecimal sysRegisteredReward;

    private BigDecimal sysOverdueRate;

    private BigDecimal sysOfflineReward;

    private BigDecimal sysOfflineMinmoney;
    
    private BigDecimal sysOfflineRewardMinmoney;

    private BigDecimal sysInterestRate;

    private BigDecimal sysAuthRate;

    private BigDecimal sysVideoRate;

    private BigDecimal sysVipRate;


    private BigDecimal sysInvestMinmoney;

    private BigDecimal sysWorthRate;
    
    private BigDecimal sysPlatformOverdueRate;
    
    private Integer sysWithdrawalDay;
    
    private BigDecimal sysWithdrawalPoundageOnline;
    
    private Integer sysRegisteredType;
    
    private BigDecimal sysRegisteredTenderMoney;
    //按每笔收费字段
    private BigDecimal sysWithdrawalOne;
    
    public BigDecimal getSysWithdrawalOne() {
		return sysWithdrawalOne;
	}

	public void setSysWithdrawalOne(BigDecimal sysWithdrawalOne) {
		this.sysWithdrawalOne = sysWithdrawalOne;
	}

	public Integer getSysRegisteredType() {
		return sysRegisteredType;
	}

	public void setSysRegisteredType(Integer sysRegisteredType) {
		this.sysRegisteredType = sysRegisteredType;
	}

	public BigDecimal getSysRegisteredTenderMoney() {
		return sysRegisteredTenderMoney;
	}

	public void setSysRegisteredTenderMoney(BigDecimal sysRegisteredTenderMoney) {
		this.sysRegisteredTenderMoney = sysRegisteredTenderMoney;
	}

	public BigDecimal getSysWithdrawalPoundageOnline() {
		return sysWithdrawalPoundageOnline;
	}

	public void setSysWithdrawalPoundageOnline(
			BigDecimal sysWithdrawalPoundageOnline) {
		this.sysWithdrawalPoundageOnline = sysWithdrawalPoundageOnline;
	}

	public Integer getSysWithdrawalDay() {
		return sysWithdrawalDay;
	}

	public void setSysWithdrawalDay(Integer sysWithdrawalDay) {
		this.sysWithdrawalDay = sysWithdrawalDay;
	}


	public BigDecimal getSysPlatformOverdueRate() {
		return sysPlatformOverdueRate;
	}

	public void setSysPlatformOverdueRate(BigDecimal sysPlatformOverdueRate) {
		this.sysPlatformOverdueRate = sysPlatformOverdueRate;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getSysLoanRate() {
        return sysLoanRate;
    }

    public void setSysLoanRate(BigDecimal sysLoanRate) {
        this.sysLoanRate = sysLoanRate;
    }

    public BigDecimal getSysLoanPoundage() {
        return sysLoanPoundage;
    }

    public void setSysLoanPoundage(BigDecimal sysLoanPoundage) {
        this.sysLoanPoundage = sysLoanPoundage;
    }

    public BigDecimal getSysMaxLoan() {
        return sysMaxLoan;
    }

    public void setSysMaxLoan(BigDecimal sysMaxLoan) {
        this.sysMaxLoan = sysMaxLoan;
    }

    public BigDecimal getSysWithdrawalPoundage() {
        return sysWithdrawalPoundage;
    }

    public void setSysWithdrawalPoundage(BigDecimal sysWithdrawalPoundage) {
        this.sysWithdrawalPoundage = sysWithdrawalPoundage;
    }

    public BigDecimal getSysOnlinePoundage() {
        return sysOnlinePoundage;
    }

    public void setSysOnlinePoundage(BigDecimal sysOnlinePoundage) {
        this.sysOnlinePoundage = sysOnlinePoundage;
    }

    public BigDecimal getSysMaxWithdrawal() {
        return sysMaxWithdrawal;
    }

    public void setSysMaxWithdrawal(BigDecimal sysMaxWithdrawal) {
        this.sysMaxWithdrawal = sysMaxWithdrawal;
    }

    public BigDecimal getSysMinWithdrawal() {
        return sysMinWithdrawal;
    }

    public void setSysMinWithdrawal(BigDecimal sysMinWithdrawal) {
        this.sysMinWithdrawal = sysMinWithdrawal;
    }

    public BigDecimal getSysContinueReward() {
        return sysContinueReward;
    }

    public void setSysContinueReward(BigDecimal sysContinueReward) {
        this.sysContinueReward = sysContinueReward;
    }

    public BigDecimal getSysRegisteredReward() {
        return sysRegisteredReward;
    }

    public void setSysRegisteredReward(BigDecimal sysRegisteredReward) {
        this.sysRegisteredReward = sysRegisteredReward;
    }

    public BigDecimal getSysOverdueRate() {
        return sysOverdueRate;
    }

    public void setSysOverdueRate(BigDecimal sysOverdueRate) {
        this.sysOverdueRate = sysOverdueRate;
    }

    public BigDecimal getSysOfflineReward() {
        return sysOfflineReward;
    }

    public void setSysOfflineReward(BigDecimal sysOfflineReward) {
        this.sysOfflineReward = sysOfflineReward;
    }

    public BigDecimal getSysOfflineMinmoney() {
        return sysOfflineMinmoney;
    }

    public void setSysOfflineMinmoney(BigDecimal sysOfflineMinmoney) {
        this.sysOfflineMinmoney = sysOfflineMinmoney;
    }
    
    public BigDecimal getSysOfflineRewardMinmoney() {
        return sysOfflineRewardMinmoney;
    }

    public void setSysOfflineRewardMinmoney(BigDecimal sysOfflineRewardMinmoney) {
        this.sysOfflineRewardMinmoney = sysOfflineRewardMinmoney;
    }

    public BigDecimal getSysInterestRate() {
        return sysInterestRate;
    }

    public void setSysInterestRate(BigDecimal sysInterestRate) {
        this.sysInterestRate = sysInterestRate;
    }

    public BigDecimal getSysAuthRate() {
        return sysAuthRate;
    }

    public void setSysAuthRate(BigDecimal sysAuthRate) {
        this.sysAuthRate = sysAuthRate;
    }

    public BigDecimal getSysVideoRate() {
        return sysVideoRate;
    }

    public void setSysVideoRate(BigDecimal sysVideoRate) {
        this.sysVideoRate = sysVideoRate;
    }

    public BigDecimal getSysVipRate() {
        return sysVipRate;
    }

    public void setSysVipRate(BigDecimal sysVipRate) {
        this.sysVipRate = sysVipRate;
    }
   

    public BigDecimal getSysInvestMinmoney() {
        return sysInvestMinmoney;
    }

    public void setSysInvestMinmoney(BigDecimal sysInvestMinmoney) {
        this.sysInvestMinmoney = sysInvestMinmoney;
    }

    public BigDecimal getSysWorthRate() {
        return sysWorthRate;
    }

    public void setSysWorthRate(BigDecimal sysWorthRate) {
        this.sysWorthRate = sysWorthRate;
    }
    
    //这三个参数已移至sys_config_params表中
	private BigDecimal sysInviteReward;
    
    private BigDecimal sysInviteRewardRate;
    
    private BigDecimal sysInviteRewardMoney;
    public BigDecimal getSysInviteReward() {
        return sysInviteReward;
    }

    public void setSysInviteReward(BigDecimal sysInviteReward) {
        this.sysInviteReward = sysInviteReward;
    }
    public BigDecimal getSysInviteRewardMoney() {
		return sysInviteRewardMoney;
	}

	public void setSysInviteRewardMoney(BigDecimal sysInviteRewardMoney) {
		this.sysInviteRewardMoney = sysInviteRewardMoney;
	}

	public BigDecimal getSysInviteRewardRate() {
		return sysInviteRewardRate;
	}

	public void setSysInviteRewardRate(BigDecimal sysInviteRewardRate) {
		this.sysInviteRewardRate = sysInviteRewardRate;
	}
}