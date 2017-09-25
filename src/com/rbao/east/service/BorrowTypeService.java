package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.BorrowType;

/**
 * 标种
 */

public interface BorrowTypeService {

	/**
	 * 保存标种
	 * @param borrowType
	 * @return
	 */
	public boolean saveBorrowType(BorrowType borrowType);
	
	/**
	 * 通过code数组取
	 * @param codes
	 * @return
	 */
	public List<BorrowType> getByTypeCodes(String[] codes);
	/**
	 * 根据Id查询标种信息
	 * @param id
	 * @return
	 */
	public BorrowType getBorrowTypeById(Integer id);
	
	/**
	 * 根据参数查询标种信息
	 * @param frontPublish 允许前台发标参数值
	 * @param adminPublish 允许后台发标参数值
	 * @param status 是否可用
	 * @return
	 */
	public List<BorrowType> getBorrowsTypeByParam(Integer frontPublish,Integer adminPublish,Integer status);
	
	/**
	 * 删除标种
	 * @param id
	 * @return
	 */
	public boolean deleteById(Integer id);
	
	/**
	 * 查询所有标种
	 * @return
	 */
	public List<BorrowType> getAllBorrowTypes();
/**
 * 查询所有标种ListPage
 * @param paramsMap
 * @return
 */
	public PageModel getAllBorrowType(Map<String, String> paramsMap);
	/**
	 * 
	* @Title: getByBorrowTypeCode
	* @Description: 借款类型code
	* @return    Integer返回类型
	* @throws
	 */
public Integer getByBorrowTypeCode(String id, String code);
/**
 * 
* @Title: getBorrowTypeByCode
* @Description: 得到借款类型code
* @return    BorrowType 返回类型
* @throws
 */

	public BorrowType getBorrowTypeByCode(String code);
	/**
	 * 
	* @Title: getBorrowTypeByName
	* @Description: 借款类型名称
	* @return    BorrowType 返回类型
	* @throws
	 */
	public BorrowType getBorrowTypeByName(String name);
	/**
	 * 
	* @Title: getBorrowTypesNotDing
	* @Description: 借款类型
	* @return   List<BorrowType> 返回类型 
	* @throws
	 */
	public List<BorrowType> getBorrowTypesNotDing(String dingCode);

}
