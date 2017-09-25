package com.rbao.east.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.Recommend;

/**
 * 推广邀请记录
 */
public interface RecommendService {
	
	/**
	 * 查询用户id查询推广上线用户
	 * @param params
	 * @return
	 */
	public List<Recommend> getRbRecommendbyUserId(Map<String,Object> params);
	
	/**
	 * 查询当前用户的推广上级用户 
	 * @param params
	 * @return
	 */
	//public List<Recommend> getRbRecommendUser(Map<String,Object> params);
	
	/**
	 * 查询单个推广列表
	 * @param id 邀请人id
	 * @return
	 */
	public List<Recommend> selectByRecommendUserIdList(Integer id);
	
	/**
	 * 查询推广信息
	 * @param params 参数
	 * @return
	 */
	public List<Recommend> getRbRecommend(Map<String,Object> params);
	/**
	 * 查询推广等级
	 * @param params 参数
	 * @return
	 */
	public List<Recommend> getRbRecommendLevl(Map<String,Object> params);
	
	/**
	 * 统计推广信息
	 * @param params
	 * @return
	 */
	public PageModel selectCountRecommend(Map<String,String> params);
	
	
	public List<Map> selectCountRecommendParams(Map<String,String> params);
	
	/**
	 * 根据推广人查看邀请人
	 * @param recommendUserId
	 * @return
	 */
	public Map<String,Object> selectByRecommendUserId(Integer recommendUserId);
	
	/**
	 * 
	 * @param r
	 * @param reward 奖金金额
	 * @param interestFee利息
	 * @param tenderMoney投资本金
	 * @param index
	 * @param status 奖励发放状态
	 * * @param id 上个用户id(二级)
	 * * @param thridId 上上个用户id(三级)
	 */
	public void saveOrUpdateData(Recommend r,BigDecimal reward,BigDecimal interestFee,BigDecimal tenderMoney,Integer index,Integer status,Integer secId,Integer thridId);
	/**
	 * 
	 * @param userId当前用户id
	 * @param index 累计
	 * @param interestFee 利息管理费
	 * @param tenderMoney 投标本金
	 * @param status 奖励发放状态
	 * * @param id 上个用户id(二级)
	 * * @param thridId 上上个用户id(三级)
	 * @return 
	 */
	public void allRecommendUser(Integer userId,Integer index,BigDecimal interestFee,BigDecimal tenderMoney,Integer status,Integer secId,Integer thridId);
	
	public void allRecommendUser(Integer userId,Integer index,BigDecimal interestFee,BigDecimal tenderMoney,Integer status,Integer secId,Integer thridId, Borrow borrow);
	/**
	 * 查询该用户的推荐人是否有推荐人
	 * @param userId
	 * @return
	 */
	public Recommend selectIsRecommend(Integer userId);

	/**
	 * 分页显示
	 * @param paramsMap
	 * @return
	 */
	public PageModel getRecommends(Map<String, String> paramsMap);
	
	public Recommend getByUserId(Integer userId);
	
	/**
	 * 保存
	 * @param recommend
	 * @return
	 */
	public boolean saveRecommend(Recommend recommend,BigDecimal reward);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteRecommendById(Integer id);
	
	/**
	 * 根据用户Id查询
	 * @param userId
	 * @return
	 */
	public List<Recommend> getRecommendByUserId(Integer userId);
	
	/**
	 * 根据Id查询推广邀请信息
	 * @param id
	 * @return
	 */
	public Recommend getRecommendById(Integer id);
	
	
	/**
	 * 好友提成（按比例计算）
	 * @param money 平台利息管理费
	 * @return
	 */
	public BigDecimal setInviteMoney(BigDecimal money);
	
	/**
	 * 好友提成查看
	 * @param paramsMap
	 * @return
	 */
	public PageModel getRecommendByRecommendUser(Map<String, String> paramsMap);

	
	//查询某人下面的被推荐人
	public List<Recommend> getSubRecommendLists(String recommendUserId);

	public List<String> getRecommendstest(Map<String, Object> levl);

	public List getRecommendLevl(Map<String, Object> levl);

	public PageModel selectCountRecommend4Admin(Map<String, String> paramsMap);

	
}
