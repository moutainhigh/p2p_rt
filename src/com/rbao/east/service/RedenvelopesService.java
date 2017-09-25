package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.entity.RedenvelopesRecord;
/**
 * 红包
 * */
public interface RedenvelopesService {
	
	public boolean addRedenvelopesRecord(RedenvelopesRecord record) ;

	BigDecimal getRedSumMoneyByUsrIdAndStatus(Integer usrId, Integer[] status);
 
	/**
	 * 给多人发送红包
	 * @param record
	 * @param recvUserIds
	 * @return
	 */
	boolean sendRedRecord(RedenvelopesRecord record, List<Integer> recvUserIds);

	/**
	 * 用户中心--红包列表 分页
	 * @param map
	 * @return
	 */
	PageModel getFrontListPaged(Map map);
	
	//后台红包列表分页
	PageModel getAdminListPaged(Map map);
	
	
	/**
	 * 打开红包
	 * @param id
	 * @param isAuto 是否自动打开，自动打开不会有加倍
	 * @return
	 */
	JsonResult openRedenvelopes(Integer id,boolean isAuto);

	/**
	 * 赠送投资满额红包 5、10、100
	 * @param userId
	 * @param money
	 * @return
	 */
	boolean sendTenderRedRecord(Integer userId, BigDecimal money);

	/**
	 * 赠送登录红包
	 */
	public BigDecimal sendLoginRedRecord(Integer userId,boolean isRegister);

	/**
	 * 设置自动打开红包
	 * @param userId
	 * @param opened
	 * @return
	 */
	boolean setAutoFlag(Integer userId, boolean opened);

	/**
	 * 打开所有可以打开的红包
	 * @param userId
	 * @return
	 */
	int openAllRed(Integer userId);

	/**
	 * 如果用户设置自动打开红包，则打开当前红包
	 * @param red
	 * @return
	 */
	JsonResult openRedenvelopesIfSettingAuto(RedenvelopesRecord red);

	boolean sendTenderRewardRedRecord(Integer userId, BigDecimal money);

	boolean sendInvitedRedRecord(Integer userId, BigDecimal money);    
	//获取红包
	PageModel getFrontListPageRed(Map map);
	/**
	 * 
	* @Title: getRedSumMoneyByUsrIdAndStatusTime
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return BigDecimal    返回类型
	* @throws
	 */
	BigDecimal getRedSumMoneyByUsrIdAndStatusTime(Map map);
}
