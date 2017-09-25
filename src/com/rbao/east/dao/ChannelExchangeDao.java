package com.rbao.east.dao;

import java.util.List;
import java.util.Map;

import com.rbao.east.entity.ChannelExchange;

/**
 * 渠道数据交互
 * @author hjy
 * 2016-01-19 10:06:32
 *
 */
public  interface ChannelExchangeDao extends BaseDao<ChannelExchange>{  

	/**
	 * 已投资用户信息
	 * @return
	 */
	public List<Map<String, Object>> tenderUserCount();
}
