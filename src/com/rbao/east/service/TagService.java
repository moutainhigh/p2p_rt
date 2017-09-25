package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.Tag;
/**
 * 标签
 * */
public interface TagService {

	boolean deleteByPrimaryKey(Integer id);

	Tag selectByPrimaryKey(Integer id);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	boolean saveOrUpdate(Tag tag);
	
	/**
	 * 根据条件查询得到list
	 */
	List<Tag> getTags(Map<String, String> params);
	
	/**
	 * 根据条件查询列表总记录数
	 */
	Integer getTagsCount(Map<String, String> params);
	
	PageModel getPage(Map<String, String> params);
	
	List<Tag> checkCode(Map<String, String> params);
}
