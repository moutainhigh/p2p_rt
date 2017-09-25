package com.rbao.east.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.AccountCashDao;
import com.rbao.east.dao.ChannelExchangeDao;
import com.rbao.east.entity.AccountCash;
import com.rbao.east.entity.ChannelExchange;

/**
 * 渠道数据交互
 * 2016-01-19 10:01:10
 * @author hjy
 *
 */
@Repository
public class ChannelExchangeDaoImpl extends BaseDaoImpl<ChannelExchange> implements ChannelExchangeDao{

	private static Logger logger = LoggerFactory.getLogger(ChannelExchangeDaoImpl.class);

	/**
	 * 已投资用户信息
	 * @return
	 */
	@Override
	public List<Map<String, Object>> tenderUserCount() {
		return super.selects("tenderUserCount", null);
	}

}
