package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.AttestationApply;
/**
 * 
* @ClassName: AttestationApplyService
* @Description: 证件接口
 */
public interface AttestationApplyService {
	/**
	 * 
	* @Title: getList
	* @Description: 得到列表
	* @return PageModel    返回类型
	* @throws
	 */
	public PageModel getList(Map<String,String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: id 得到单个数据
	* @return AttestationApply    返回类型
	* @throws
	 */
	public AttestationApply getById(Integer id);
	/**
	 * 
	* @Title: deleteById
	* @Description: id 删除数据
	* @return boolean    返回类型
	* @throws
	 */
	public boolean deleteById(Integer id);
	/**
	 * 
	* @Title: save
	* @Description: 保存证件
	* @return boolean    返回类型
	* @throws
	 */
	public boolean save(AttestationApply attestationApply) ;
	/**
	 * 
	* @Title: add
	* @Description: 增加证件
	* @return boolean    返回类型
	* @throws
	 */
	public boolean add(AttestationApply attestationApply) ;
	/**
	 * 
	* @Title: listAttestationApply
	* @Description: 得到列表
	* @return List<AttestationApply>    返回类型
	* @throws
	 */
	public List<AttestationApply> listAttestationApply(AttestationApply attestationApply);
	
	/**
	 * 根据用户Id和证件类型查询改资料是否已上传
	 * @param map
	 * @return
	 */
	public AttestationApply selectByuserIdAndType(AttestationApply attestationApply);
	
	
}
