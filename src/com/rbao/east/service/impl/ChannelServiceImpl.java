package com.rbao.east.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.ChannelDao;
import com.rbao.east.dao.TreeModelDao;
import com.rbao.east.entity.Channel;
import com.rbao.east.service.ChannelService;

@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private TreeModelDao treeModelDao;
	@Override
	public PageModel getList(Channel entity, Integer curPage) {
		PageModel pageModel=new PageModel(curPage);
		return channelDao.getPage("selectByEntity","selectTotalCount", entity, pageModel);
	}

	@Override
	public Channel getById(Integer id) {
		return channelDao.selectByPrimaryKey(id);
	}

	@Override
	
	public boolean deleteById(Integer id) {
		return channelDao.deleteByPrimaryKey(id);
	}

	@Override
	
	public boolean saveOrUpdate(Channel channel) {
		return channelDao.saveOrUpdate(channel);
	}

	@Override
	public List getTreeList(Channel channel) {
		return channelDao.selectAll();
	}

	@Override
	public PageModel getListByParentId(Map<String,String> paramsMap) {
		return channelDao.getPage("selectByEntity","selectTotalCount",paramsMap);
	}

	@Override
	public Integer getByChannelCode(String channelId,String channelCode) {
		Map map = new HashMap();
		map.put("id", channelId);
		map.put("channelCode", channelCode);
		
		return channelDao.getTotalCount("selectTotalCount", map);
	}

	@Override
	public List<Channel> getListByDisplay(Channel channel) {
		
		return channelDao.select("selectByDisplay",channel);
	}
/**
 * foot栏目
 */
	@Override
	public List<Channel> getByParentId(Map<String,String> param) {
		return channelDao.select("selectByParentId", param);
	}

@Override
public PageModel getByIdPage(Map<String,String> param) {
	return channelDao.getPage("showChannelByPage", "showChannelByPageTotalCount", param);
}

@Override
public List<Channel> getIdByChannelCode(String channelCode) {
	return channelDao.select("getIdByChannelCode", channelCode);
}

@Override
public List<Map<String, Object>> getContentByChannelCode(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return channelDao.selects("getContentByChannelCode", param);
}

@Override
public List<Map<String, Object>> getContentByChannelCodes(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return channelDao.selects("getContentByChannelCodes", param);
}

@Override
public PageModel getContentPageByChannelId(Map<String, String> param) {
	// TODO Auto-generated method stub
	return channelDao.getPage("getContentPageByChannelId", "getContentPageCountByChannelId", param);
}



@Override
public Channel selectChannelByCode(String code){
	
	return this.channelDao.selectEntity("selectChannelByCode",code);
}

	@Override
	public List<Map<String, Object>> getContentByChannelId(Map param) {
		// TODO Auto-generated method stub
		return channelDao.selects("getContentPageByChannelId", param);
	}
	
	@Override
	public List<Channel> getHot(Map<String, String> param) {
		return channelDao.select("selectHot", param);
	}
	
	
}
