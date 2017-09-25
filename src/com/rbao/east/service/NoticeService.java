package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Notice;
/**
 * 提醒
 * */
public interface NoticeService {
	/**
	 * 根据id查询
	 * @param id 参数
	 * @return
	 */
	public Notice getById(Integer id);
	/**
	 * 删除
	 * @param id 参数
	 * @return
	 */
	public boolean deleteById(Integer id);
	/**
	 * 保存
	 * @param notice 参数
	 * @return
	 */
	public boolean save(Notice notice) ;
/**
 * 分页
 * @param paramsMap 参数
 * @return
 */
	public PageModel getpagedList(Map<String, String> paramsMap);
	/**
	 * 查询总行数
	 * @param paramsMap 参数
	 * @return
	 */
	public int selectTotalCount(Map<String, String> paramsMap);
	
	/**
	 * 根据提醒编码查询提醒
	 * @param noticeCode
	 * @return
	 */
	public Notice getByNoticeCode(String noticeCode);
	
	/**
	 * 查询所有
	 * @param paramsMap
	 * @return
	 */
	public List getNotices(Map<String, String> paramsMap);
}
