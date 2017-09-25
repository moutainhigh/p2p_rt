package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.entity.AccountCash;
/**
 * 提现
 * */
public interface AccountCashService {
	
	
	/**
	 * 取消提现
	 * @param userId
	 * @param cashId
	 * @param cashMoney 提现金额
	 * @return
	 */
	public boolean cancelCash(Integer userId,Integer cashId,BigDecimal cashMoney);
	/**
	 * 
	* @Title: selectCashForUpdate
	* @Description: id 更新记录
	* @return AccountCash    返回类型
	* @throws
	 */
	public AccountCash selectCashForUpdate(Integer id);
	/**
	 * 
	* @Title: selectCashByNoOrderForUpdate
	* @Description:编号 更新记录
	* @return AccountCash    返回类型
	* @throws
	 */
	public AccountCash selectCashByNoOrderForUpdate(String noOrder);

	/**
	 * 
	* @Title: freeCashMoneys
	* @Description: id 得到现金
	* @return BigDecimal    返回类型
	* @throws
	 */
	public BigDecimal freeCashMoneys(Integer userId);
	/**
	 * 
	* @Title: getAccountCashList
	* @Description: 得到现金流
	* @return PageModel    返回类型
	* @throws
	 */
	public PageModel getAccountCashList(Map<String, String> param);
	/**
	 * 
	* @Title: getAccountCashById
	* @Description: 得到用户资金
	* @return AccountCash    返回类型
	* @throws
	 */
	public AccountCash getAccountCashById(int id);
	/**
	 * 
	* @Title: updateAccountCashById
	* @Description: 资金列表
	* @return DwzResult    返回类型
	* @throws
	 */
	public DwzResult updateAccountCashById(Map<String, Object> param) throws DataAccessException;
	/**
	 * 
	* @Title: selectAccountCashByCashStatus
	* @Description: T得到资金列表
	* @return List<AccountCash>    返回类型
	* @throws
	 */
	public List<AccountCash> selectAccountCashByCashStatus(Map<String, String> param);
	
	//提现申请
	public boolean saveAccountCash(AccountCash accountCash,Map<String,String> param);
	//userId，status查询
	public AccountCash selectAccountCashByUserIdStatus(Map<String,String> param);
	
	//查询充值和提现记录
	public PageModel selectCashAndRecharge(Map<String, String> param);
	/*修改状态*/
	public boolean updateCashStatus(AccountCash accountCash);
	/**
	 * 
	* @Title: selectWithDrawTotal
	* @Description: 得到充值金额
	* @return BigDecimal    返回类型
	* @throws
	 */
	public BigDecimal selectWithDrawTotal(Map<String,String> map);
	
	/**
	 * 计算提现手续费
	 * @param usrId
	 * @param cashTotal
	 * @param rechgCashMap
	 * @return
	 */
	public BigDecimal getCashFee(Integer usrId,BigDecimal cashTotal,Map<Integer,BigDecimal> rechgCashMap) ;
	
	/**
	 * 免费提现金额
	 * yan
	 * @param UserId
	 * @return
	 */
	public BigDecimal freeCashMoney(Integer userId);
	
	/**
	 * 统计用户总提现
	 * @return BigDecimal
	 */
	public BigDecimal summaryCash(Integer status, Boolean flag);
	
	/**
	 * 统计提现待审
	 * @return
	 */
	public Integer summaryCashNoCheck();
	/**
	 * 
	* @Title: selectByNoOrder
	* @Description: 得到资金
	* @return AccountCash    返回类型
	* @throws
	 */
	public AccountCash selectByNoOrder(String noOrder);
	/**
	 * 处理H5提现失败
	 * @param noOrder
	 * @param verifyName
	 * @param ipAddress
	 * @return
	 */
	public  boolean exeCashFailure(String noOrder,String verifyName,String ipAddress,String verifyRemark);
	/**
	 * 处理H5提现
	 * @param param
	 * @return
	 */
	public  DwzResult updateH5AccountCashById(Map<String, Object> param);
}
