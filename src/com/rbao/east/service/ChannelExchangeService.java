package com.rbao.east.service;

import java.util.List;
import java.util.Map;

/**
 * 渠道数据交互
 * @author hjy
 * 2016-01-19 10:09:00
 *
 */
public interface ChannelExchangeService {

	/**
	 * 投资用户统计
	 * @return
	 */
	public List<Map<String, Object>> tenderUserCount();
}
