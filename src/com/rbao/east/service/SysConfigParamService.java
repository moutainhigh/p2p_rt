package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.SysConfigParams;

/**
 * 配置
 * */
public interface SysConfigParamService {
	/**
	 * 
	 * @Title: getAll
	 * @Description: 查询所有
	 * @return  List<SysConfigParams> 返回类型  
	 * @throws
	 */
	public List<SysConfigParams> getAll();
	/**
	 * 
	 * @Title: updateValue
	 * @Description: 更新
	 * @return   boolean返回类型 
	 * @throws
	 */
	public boolean updateValue(Integer id,String value);
	/**
	 * 
	 * @Title: getByType
	 * @Description: 根据类型查询
	 * @return 	List<SysConfigParams> 返回类型   
	 * @throws
	 */
	List<SysConfigParams> getByType(String type);
	/**
	 * 
	 * @Title: getAllValueToMap
	 * @Description: 得到数据
	 * @return  Map<String, String> 返回类型  
	 * @throws
	 */
	Map<String, String> getAllValueToMap(); 
}
