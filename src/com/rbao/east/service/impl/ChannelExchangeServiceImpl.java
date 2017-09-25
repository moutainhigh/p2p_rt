package com.rbao.east.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.rbao.east.dao.ChannelExchangeDao;
import com.rbao.east.service.ChannelExchangeService;

/**
 * 渠道数据交互
 * @author hjy
 * 2016-01-19 10:09:34
 *
 */
@Service
@Transactional
public class ChannelExchangeServiceImpl implements ChannelExchangeService{
	
	@Autowired
	private ChannelExchangeDao channelExchangeDao;
	
	public ChannelExchangeDao getChannelExchangeDao() {
		return channelExchangeDao;
	}

	public void setChannelExchangeDao(ChannelExchangeDao channelExchangeDao) {
		this.channelExchangeDao = channelExchangeDao;
	}

	@Override
	public List<Map<String, Object>> tenderUserCount() {
		return channelExchangeDao.tenderUserCount();
	}

}
