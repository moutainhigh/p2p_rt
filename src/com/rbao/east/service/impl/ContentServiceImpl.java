package com.rbao.east.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.AttachDao;
import com.rbao.east.dao.ContentChannelDao;
import com.rbao.east.dao.ContentDao;
import com.rbao.east.entity.Attach;
import com.rbao.east.entity.Content;
import com.rbao.east.entity.ContentChannel;
import com.rbao.east.service.AttachService;
import com.rbao.east.service.ContentService;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {
	private static Logger logger = LoggerFactory
			.getLogger(ContentServiceImpl.class);

	@Autowired
	private ContentDao contentDao;
	@Autowired
	private AttachDao attachDao;
	@Autowired
	private  ContentChannelDao  contentChannelDao;
	@Autowired
	private AttachService  attachService;
	@Override
	
	public boolean saveOrUpdate(Content content, String[] attachTitle, String[] attachPath,
			String[] attachRealTitle, String[] attachFileType,
			Integer[] attachSequence){
		try{
			this.contentDao.saveOrUpdate(content);
			 Integer contentId=content.getId();
			// Integer channeId=content.getChannelId();
			//删除ContentChannel原来的
			ContentChannel contentChannelDe=new ContentChannel();
			contentChannelDe.setContentId(contentId);
			//contentChannelDe.setChannelId(channeId);
			this.contentChannelDao.delete("deleteByContentId", contentChannelDe);
			
			String channelId []=StringUtils.split(content.getChannelIds(),",");
			for(int i=0;i<channelId.length;i++){
				ContentChannel contentChannel=new ContentChannel();
				
				contentChannel.setContentId(contentId);
				contentChannel.setChannelId(Integer.parseInt(channelId[i]));
				contentChannel.setContentSequence(content.getContentSequence());
				/*contentChannelDao.update("updateByContentIdAndChannelId", contentChannel);*/
				this.contentChannelDao.saveOrUpdate(contentChannel);
			}
			//保存附件
				
				List<Attach>  attachs=new ArrayList<Attach>();
				if(attachRealTitle!=null){
					 for (int i = 0; i < attachRealTitle.length; i++) {
					    Attach newAttach=new Attach();
						newAttach.setAttachSequence(attachSequence[i]);
						newAttach.setAttachTitle(attachTitle[i]);
						newAttach.setAttachRealTitle(attachRealTitle[i]);
						newAttach.setAttachPath(attachPath[i]);
						newAttach.setAttachFileType(attachFileType[i]);
						attachs.add(newAttach);
						
					}
					attachService.save(attachs, contentId, Attach.TABLE_NAME_CONTENT);
				}else{
					attachService.save(attachs, contentId, Attach.TABLE_NAME_CONTENT);
				}
				
	
			return true;
		}catch (Exception e) {
			logger.info("发布或者修改信息内容出错====="+e.toString());
			throw new RuntimeException("运行时出错！");   
		}
	}
	
	@Override
	
	public PageModel getListById(Map<String, String> paramsMap) {
		return contentDao.getPage("selectByEntity","selectTotalCount",paramsMap);
	}

	@Override
	
	public boolean deleteById(Integer id) {
		boolean flag=false;
		try{
			flag=this.contentDao.deleteByPrimaryKey(id);
			if(flag){
				ContentChannel  contentChannel=new ContentChannel();
				Integer contentId=id;
				contentChannel.setContentId(contentId);
				contentChannelDao.delete("deleteByContentId", contentChannel);
				
				Attach attach=new Attach();
				Integer attachRelateId=id;
				attach.setAttachRelateId(attachRelateId);
				attachDao.delete("deleteByContentIdAndTableName", attach);
				
			}
			return flag;
		}catch (Exception e) {
			
			logger.info("删除信息内容出错====="+e.toString());
			throw new RuntimeException("运行时出错！"); 
		}
	}

	@Override
	
	public Content getById(Integer contentId,Integer channelId) {
		Content content=contentDao.selectByPrimaryKey(contentId);
		//搜索attach表中有无附加文件
		Attach attach = new Attach();
		attach.setAttachTablename(Attach.TABLE_NAME_CONTENT);
		attach.setAttachRelateId(contentId);
		List<Attach> attachList=attachDao.select("selectByAttach", attach);
		if(attachList.size()>0){
			content.setAttachs(attachList);
		}
	    //排序
		ContentChannel contentChannel=new ContentChannel();
		contentChannel.setContentId(contentId);
		contentChannel.setChannelId(channelId);
		List<ContentChannel> contentChannelList= contentChannelDao.select("selectByContentChannel", contentChannel);
		if(contentChannelList.size()>0){
			content.setContentChannels(contentChannelList);
		}
		return content;
	}

	@Override
	
	public List<Content> getListBychannelIds(Map<String, String> param) {
		Content content=new Content();
		content.setChannelIds(param.get("channelId"));
		return contentDao.selectByEntity(content);
	}
	/**
	 * 前台页面内容查询跳转
	 */
	@Override
	
	public List<Content> getListByChannelId(Content content) {
		return contentDao.select("selectByChannelId", content);
	}
	/**
	 * 前台页面内容分页
	 */
	@Override
	
	public PageModel showContentByPage(Map<String, String> paramsMap) {
		  //搜索attach表中有无附加文件
		/*Attach attach = new Attach();
		attach.setAttachTablename(Attach.TABLE_NAME_CONTENT);*/
		PageModel pageModel=new  PageModel(Integer.parseInt(paramsMap.get(Constants.PAGED_CURPAGE).toString()));  //设置当前页
	    pageModel.setPageSize(5);
		paramsMap.put("attachTablename", Attach.TABLE_NAME_CONTENT);
		PageModel contentPageModel=contentDao.getPage("showContentByPage", "showContentByPageTotalCount", paramsMap,pageModel);
     
		return  contentPageModel;
	}
/**
 * 首页 网络公告
 */
	@Override
	public List<Content> getContentTitle(Map<String, String> param) {
		return contentDao.select("selectByChannelIdsStatus", param);
	}
   /**
    * 首页图片
    */
	@Override
	public List<Content> getContentPicFri(String channelId) {
		return contentDao.select("selectBychannelIdPicFri", channelId);
	}

@Override
public void updateClick(Content content) {
	contentDao.saveOrUpdate(content);
}

@Override
public boolean updateByPrimaryKeyForNumber(Integer id) {
	Content content=new Content();
	content.setId(id);
	return contentDao.update("updateByPrimaryKeyForNumber", content);
}
   


	
	
}
