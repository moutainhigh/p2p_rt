package com.rbao.east.service;

import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.VipUser;

public interface VipUserService {
	
	public boolean deleteByUserId(Integer userId);
	/**
	 * 认证审核调用方法，save
	 * @param vipUser vip用户
	 * @param creditCode积分code
	 * @param loginUserId当前登录Id
	 * @param status认证状态
	 * @return
	 */
	public boolean saveOrUpdateVipUser(VipUser vipUser,String creditCode,Integer loginUserId,Integer status);
	
	/**
	 * 根据userId 和客服Id查询vip用户 分权限查询
	 * @param param  userId
	 * @param paramCustomer 客服ID
	 * @return
	 */
	public PageModel VipUserPageList(Map<String, String> param,Map<String, String> paramCustomer);
	
	/**
	 * 所有VIp用户
	 * @param param
	 * @return
	 */
	public PageModel vipUserPageList(Map<String ,String> param);
	
	//主键查询
	public VipUser selectVipUserById(Integer id);
	//根据UserID查询
	public VipUser selectByUserId(Map<String ,String> param);
	
	//修改vip用户
	public boolean saveOrUpdate(VipUser vipUser);
	
	public void updateVipStatusByEndDate(VipUser vip);
	
	public List<VipUser> selectVipEndUser(Map<String ,String> params);
	
	public void vipNotify();
}
