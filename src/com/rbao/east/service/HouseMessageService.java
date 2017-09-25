package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.HouseMessage;

/**
 * 房屋信息
 * */
public interface HouseMessageService {
	/**
	 * 分页
	 * @param paramsMap 参数
	 * @return
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return    HouseMessage 返回类型
	* @throws
	 */
	public HouseMessage getById(Integer id);
	/**
	 * 
	* @Title: getByUserId
	* @Description: 根据用户id查询
	* @return    HouseMessage 返回类型
	* @throws
	 */
	public HouseMessage getByUserId(Integer userId);
	/**
	 * 删除
	 * @param id 参数
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param houseMessage 参数
	 * @return
	 */
	public boolean save(HouseMessage houseMessage) ;
	/**
	 * 添加
	 * @param houseMessage 参数
	 * @return
	 */
	public boolean add(HouseMessage houseMessage) ;
	
}
