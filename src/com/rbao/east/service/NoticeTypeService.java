package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.NoticeType;

/**
 * 提醒类型
 * */
public interface NoticeTypeService {
	/**
	 * 分页
	 * @param paramsMap 参数
	 * @return
	 */
	public PageModel getpagedList(Map<String, String> paramsMap);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询
	* @return    NoticeType 返回类型
	* @throws
	 */
	public NoticeType getById(Integer id);
	/**
	 * 删除
	 * @param id 参数
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param noticeType 参数
	 * @return
	 */
	public boolean save(NoticeType noticeType) ;
	/**
	 * 
	* @Title: getAll
	* @Description: 查询所有
	* @return    List<NoticeType> 返回类型
	* @throws
	 */
	public List<NoticeType> getAll();
	/**
	 * 
	* @Title: selectTotalCount
	* @Description: 总数量
	* @return    int 返回类型
	* @throws
	 */
	public int selectTotalCount(Map<String, String> paramsMap);
}
