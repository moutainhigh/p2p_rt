package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowType;
/**
 * 标的
 * */
public interface BorrowService {
	
	/**
	 * 查询用户发的指定状态的标的数量
	 * @param UserId
	 * @param borrowStatus 状态，可多选 逗号隔开
	 * @return
	 */
	public int getBorrowCount(Integer UserId,Integer[] borrowStatus) ;
	
	/**
	 * 根据标的状态查询借款
	 * @param borrowStatus
	 * @return
	 * 2014-5-4
	 */
	public PageModel getBorrowsByStatus(Map<String, String> paramsMap);
	
	/**
	 * 分页
	 * @param param
	 * @return
	 */
	public PageModel showBorrowStatusInfoPageByParam(Map<String, Object> param);
	/**
	 * 得到借款名称
	 * @param param
	 * @return List<Borrow> 返回类型
	 */
	public List<Borrow> getByBorrowName(String name);
	/**
	 * 首次借款
	 * @param param
	 * @return Borrow 返回类型
	 */
	public Borrow borrowFirst(Map<String, String> paramsMap );
	
	/**
	 * 根据Id查询标
	 * @param id
	 * @return
	 * 2014-5-6
	 */
	public Borrow getBorrowById(Integer id);
	
	public ServiceResult cancelBorrow(Integer id);
	
	/**
	 * 还款管理
	 * @param paramsMap
	 * @return
	 */
	public PageModel getPaymentBorrows(Map<String, String> paramsMap);
	
	/**
	 * 导出还款
	 * @param paramsMap
	 * @return
	 */
	public List<Map<String, Object>> toRepayExcel(Map<String, String> paramsMap);
	
	/**
	 * 保存标信息
	 * @param repayment
	 * @return
	 */
	public boolean saveBorrow(Borrow borrow);
	
	
	/**
	 * 初审修改
	 */
	
	public boolean updateBorrow(Borrow borrow);
	public boolean updateBorrowForAttach(Borrow borrow ,String[] attachTitle, String[] attachPath,
			String[] attachRealTitle, String[] attachFileType,
			Integer[] attachSequence);
	
	/**
	 * 复审修改
	 */
	
	public boolean updateSecondCheckBorrow(Map<String, Object> param);
	
	/**
	 * 复审失败 用户回款
	 * @param BorrowTenderList
	 * @return bool
	 */
	public boolean returnedMoneyByBorrowTenderList(Borrow borrow);
	/**
	 * 查询所有标信息
	 * @param params
	 * @return
	 */
	public List<Borrow> getAllBorrowsByStatus(Map<String, String> params);
	/**
	 * 首页查询借款信息
	 * @param borrow
	 * @return
	 * @author Sandy
	 */
	public List<Borrow> getBorrowListByEntity(Borrow borrow);
	
	
	/**
	 * 流标回款
	 */
	
	public void returnedMoneyForFlowStandardByBorrowTenderList(Borrow borrow);
	
	/**
	 * 协议查询
	 */
	public PageModel selectAgreementPath(Map<String,String> params);
	
	/**
	 * 正在招标和尚未发布的标
	 */
	public PageModel selectBorrowsByStatusAndUserId(Map<String,Object> params);
	
	/**
	 * 定活通发标
	 */
	public boolean savefixedCurrentLinkAccount(Map<String, Object> param); 
	
	/**
	 * 查询借款信息
	 * @param borrow
	 * @return
	 * @author 
	 */
	public Borrow findBorrowByTransferId(Integer transferId);
	
	
	/**
	 * 得到需要审核的秒标
	 * @param param
	 * @return
	 */
	public List<Borrow> findAutoBorrowAuditForImmediate(Map<String, Object> param);
	
	
	/**
	 * 自动审核秒标
	 */
	
	public boolean AutoBorrowAuditForImmediate(Borrow borrow,BorrowType borrowType);
/**
 * 获得定活通
 * @param paramsMap
 * @return
 */
	public PageModel getBorrowDing(Map<String, String> paramsMap);
	
	
	/**
	 * 查询标集合
	 * @param userId borrowTypeCode
	 */
	public List<Borrow> getBorrowListByUserIdANDBorrowTypeCode(Map<String, String> param);
	/**
	 * 查询借款资金情况信用额度情况
	 * @param userId
	 * @return
	 */
		public List getborrowInfo(Integer userId);
	/**
	 * 查询借款资金情况
	 * @param userId
	 * @return
	 */
	public List getborrowAccount(Integer userId);
	
	/**
	 * 我是投资者-正在投标的项目
	 * @param paramsMap
	 * @return
	 */
	public PageModel getBorrowInBidPage(Map<String, String> paramsMap);
	
	//统计借款人数
	public Integer getBorrowUsers();
	
	//满标额
	public BigDecimal getFullMoney(Map<String, String> paramsMap);
	
	public PageModel getBorrowDeal(Map<String, String> paramsMap);

	/**
	 * 自动投标
	 */
	public void autoTenderRules();
	
	/**
	 * 借款待办事项
	 * @param status
	 * 				状态：1.待审核
	 * 					2.待发布
	 * 					3.待放款
	 * @param flag
	 * @return
	 */
	public Integer summaryBacklogCount(Integer status, Boolean flag);
	
	/**
	 * 借款数据统计-按产品类型
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByType(String date);
	
	/**
	 * 借款数据统计-按期限
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByDate(String date);
	
	/**
	 * 借款数据统计-按省份
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByProvince(String date);
	
	public PageModel getProjectSummary(Map<String, String> paramsMap);
	
	public List<Map<String, Object>> getAllProjectSummary(Map<String, String> paramsMap);
	
	// 投资总额
	public BigDecimal gettenderSum(Map<String, String> paramsMap);
}
