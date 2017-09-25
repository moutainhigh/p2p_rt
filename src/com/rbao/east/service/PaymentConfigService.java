package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.PaymentConfig;

/**
 * 支付接口配置
 * */
public interface PaymentConfigService {
	/**
	 * 分页
	 * @param entity 实体
	 * @param curPage 当前页
	 * @return
	 */
	public PageModel getList(PaymentConfig entity,Integer curPage);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return    PaymentConfig 返回类型
	* @throws
	 */
	public PaymentConfig getById(Integer id);
	/**
	 * 删除
	 * @param id 参数
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param paymentConfig 参数
	 * @return
	 */
	public boolean save(PaymentConfig paymentConfig) ;
/**
 * 分页
 * @param paramsMap
 * @return
 */
	public PageModel getpagedList(Map<String, String> paramsMap);
	/**
	 * 
	* @Title: 
	* @Description: 
	* @return    
	* @throws
	 */
	public List<PaymentConfig> getOnlineList(Map<String, String> paramsMap) ;
	/**
	 * 
	* @Title: getAll
	* @Description: 查询所有
	* @return    List<PaymentConfig>  返回类型
	* @throws
	 */
	public List<PaymentConfig> getAll();
/**
 * 更新
 * @param paymentConfig 参数
 * @return
 */
	public boolean update(PaymentConfig paymentConfig);
	/**
	 * 总数
	 * @param paramsMap 参数
	 * @return
	 */
	public int selectTotalCount(Map<String, String> paramsMap);
	
	/**
	 * 查询paymentConfig
	 * @return
	 */
	public PaymentConfig getByParam(Map<String,String> params);
	
}
