package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.AttestationType;
/**
 * 证件类型
 * */
public interface AttestationTypeService {
	/**
	 * 
	* @Title: getList
	* @Description: 参数得到数据集
	* @return PageModel    返回类型
	* @throws
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description:单个数据
	* @return AttestationType    返回类型
	* @throws
	 */
	public AttestationType getById(Integer id);
	/**
	 * 
	* @Title: deleteById
	* @Description: 删除
	* @return boolean    返回类型
	* @throws
	 */
	public boolean deleteById(Integer id);
	/**
	 * 
	* @Title: save
	* @Description: 保存
	* @return boolean    返回类型
	* @throws
	 */
	public boolean save(AttestationType attestationType) ;
	/**
	 * 
	* @Title: add
	* @Description: 增加
	* @return boolean    返回类型
	* @throws
	 */
	public boolean add(AttestationType attestationType) ;
	/**
	 * 
	* @Title: listAttestationType
	* @Description: 数据集
	* @return List<AttestationType>    返回类型
	* @throws
	 */
	public List<AttestationType> listAttestationType(AttestationType attestationType);
	/**
	 * 根据userId查询用户没有上传资料的证件类型
	 * @param userId
	 * @return
	 */
	public List<AttestationType> selectTypeByUserId(Integer userId);
	
}
