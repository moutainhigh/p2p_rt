package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.entity.BorrowTransfer;

/**
 * 债权转让
 * */	
public interface BorrowTransferService {
	
	public ServiceResult allowToTransfer(BorrowTransfer transfer);
	/**
	 * 提交申请
	 * @param transfer
	 */
	public void save(BorrowTransfer transfer);
	
	/**
	 * 
	* @Title: showBorrowTransferById
	* @Description: 债权转让信息
	* @return  BorrowTransfer 返回类型  
	* @throws
	 */
	public BorrowTransfer showBorrowTransferById(Integer transferId);
	
	/**
	 * 
	* @Title: selectBorrowTransList
	* @Description: 债权转让信息列表
	* @return    PageModel 返回类型
	* @throws
	 */
	public PageModel selectBorrowTransList(Map<String, String> param);
	/**
	 * 
	* @Title: getBorrowTransferListOnIndex
	* @Description: 债权转让
	* @return    List<BorrowTransfer> 返回类型
	* @throws
	 */
	public List<BorrowTransfer> getBorrowTransferListOnIndex(); 
	
	/**
	 * 
	* @Title: selectBorrowRepossessedByTransferId
	* @Description: 分页显示信息
	* @return    PageModel 返回类型
	* @throws
	 */
	public PageModel selectBorrowRepossessedByTransferId(Map<String, String> param);
	/**
	 * 
	* @Title: selectBorrowTransferAuction
	* @Description: 分页
	* @return    PageModel 返回类型
	* @throws
	 */
	public PageModel selectBorrowTransferAuction(Map<String, String> param);
	/**
	 * 
	* @Title: selectBorrowTransferByUserId
	* @Description: 用户id查询债权转让
	* @return    PageModel 返回类型
	* @throws
	 */
	public PageModel selectBorrowTransferByUserId(Map<String, String> param);
	/**
	 * 
	* @Title: saveTransfer
	* @Description: 保存操作
	* @return    boolean 返回类型
	* @throws
	 */
	public boolean saveTransfer(Map<String, Object> param);
	/**
	 * 
	* @Title: getByTenderId
	* @Description:投标id
	* @return     List<BorrowTransfer>返回类型
	* @throws
	 */
	public List<BorrowTransfer> getByTenderId(Integer tenderId);
	
	/**
	 * 获得转让金额
	 * @param tenderId
	 * @return
	 */
	public BigDecimal getTransferMoney(Integer tenderId) ;
	
	public void cancelTransfer(BorrowTransfer transfer);
	
	/**
	 * 定时器债券转让处理
	 */
	public void borrowTransferOverdueDispose(BorrowTransfer borrowTransfer);
	
	/**
	 * 债权转让协议
	 */
	public PageModel findBorrowTransferDeal(Map<String, String> param);
	
	/**
	 * 债权转让统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public PageModel transferSummary(Map<String, String> map);
	
	/**
	 * 债权转让统计
	 * @param map
	 * 			条件
	 * @return
	 */
	public List<Map<String, Object>> transferAllSummary(Map<String, String> map);
	/**
	 * 
	* @Title: selectBorrowTransListData
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param param
	* @param @return    参数
	* @return PageModel    返回类型
	* @throws
	 */
	public PageModel selectBorrowTransListData(Map<String, Object> param);
	
	/**
	 * 
	* @Title: getByTransferUserid
	* @Description:投标id
	* @return     List<BorrowTransfer>返回类型
	* @throws
	 */
	public List<BorrowTransfer> getByTransferUserid(Map<String, Object> param);
}
