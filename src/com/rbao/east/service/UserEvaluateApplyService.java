package com.rbao.east.service;

import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.UserEvaluateApply;
/**
 * 额度申请
 * */
public interface UserEvaluateApplyService {
	
	/**
	 * 根据userId删除
	 * @param userId
	 * @return
	 */
	public boolean deleteByUserId(Integer userId);

	/**
	 * 额度申请列表
	 * @param paramsMap
	 * @return
	 */
	public PageModel getUserEvaluateApplyList(Map<String, String> paramsMap);
	
	/**
	 * 根据Id查询额度申请信息
	 * @param id
	 * @return
	 */
	public UserEvaluateApply getUserEvaluateApplyById(Integer id);
	
	/**
	 * 修改
	 * @param apply
	 * @return
	 */
	public boolean updateUserEvaluateApply(UserEvaluateApply apply);
	
	/**
	 * 额度申请
	 * @param userEvaluateApply
	 * @return
	 */
	public boolean toEvaluateApply(UserEvaluateApply userEvaluateApply);
	
	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	public UserEvaluateApply selectByUserId(Integer userId);
	
	/**
	 * 前台用户额度申请列表
	 * @param paramsMap
	 * @return
	 */
	public PageModel getApplyByUserId(Map<String, String> paramsMap);
}
