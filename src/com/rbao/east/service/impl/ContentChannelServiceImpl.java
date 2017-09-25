package com.rbao.east.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.dao.ContentChannelDao;
import com.rbao.east.entity.ContentChannel;
import com.rbao.east.service.ContentChannelService;

@Service
@Transactional
public class ContentChannelServiceImpl implements ContentChannelService {

	@Autowired
	private ContentChannelDao contentChannelDao;

	
	@Override
	
	public boolean saveOrUpdate(ContentChannel contentChannel) {
	//	contentChannelDao.delete("deleteByContentId", contentChannel);
		
		return contentChannelDao.update("updateByContentIdAndChannelId", contentChannel);
	}





	
	
}
