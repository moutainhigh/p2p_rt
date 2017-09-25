package com.rbao.east.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Sandy
 *
 */
public class Content extends BaseEntity {

	private Integer id;

	private String channelIds;

	private String contentTitle;

	private Integer contentStatus;

	private String contentSummary;

	private Date contentAddDatetime;

	private String contentAuthor;

	private Date contentUpdateDatetime;

	private String contentCreateUser;

	private String contentUpdateUser;

	private String contentSource;

	private String externalLinkTitle;

	private String contentSourceLink;
   
	private Integer contentClick;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date contentPublishTime;

	private String contentTxt;
	//
	private Integer channelId;
	
	private List<Attach> attachs;
	private List<ContentChannel> contentChannels;
	private Integer contentSequence;
	private String updateTimeStr;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds == null ? null : channelIds.trim();
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle == null ? null : contentTitle.trim();
	}

	public Integer getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(Integer contentStatus) {
		this.contentStatus = contentStatus;
	}

	public String getContentSummary() {
		return contentSummary;
	}

	public void setContentSummary(String contentSummary) {
		this.contentSummary = contentSummary == null ? null : contentSummary
				.trim();
	}

	public Date getContentAddDatetime() {
		return contentAddDatetime;
	}

	public void setContentAddDatetime(Date contentAddDatetime) {
		this.contentAddDatetime = contentAddDatetime;
	}

	public String getContentAuthor() {
		return contentAuthor;
	}

	public void setContentAuthor(String contentAuthor) {
		this.contentAuthor = contentAuthor == null ? null : contentAuthor
				.trim();
	}

	public Date getContentUpdateDatetime() {
		return contentUpdateDatetime;
	}

	public void setContentUpdateDatetime(Date contentUpdateDatetime) {
		this.contentUpdateDatetime = contentUpdateDatetime;
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		String dateStr=sdf.format(contentUpdateDatetime);
		setUpdateTimeStr(dateStr);
	}

	public String getContentCreateUser() {
		return contentCreateUser;
	}

	public void setContentCreateUser(String contentCreateUser) {
		this.contentCreateUser = contentCreateUser == null ? null
				: contentCreateUser.trim();
	}

	public String getContentUpdateUser() {
		return contentUpdateUser;
	}

	public void setContentUpdateUser(String contentUpdateUser) {
		this.contentUpdateUser = contentUpdateUser == null ? null
				: contentUpdateUser.trim();
	}

	public String getContentSource() {
		return contentSource;
	}

	public void setContentSource(String contentSource) {
		this.contentSource = contentSource == null ? null : contentSource
				.trim();
	}

	public String getExternalLinkTitle() {
		return externalLinkTitle;
	}

	public void setExternalLinkTitle(String externalLinkTitle) {
		this.externalLinkTitle = externalLinkTitle == null ? null
				: externalLinkTitle.trim();
	}

	public String getContentSourceLink() {
		return contentSourceLink;
	}

	public void setContentSourceLink(String contentSourceLink) {
		this.contentSourceLink = contentSourceLink == null ? null
				: contentSourceLink.trim();
	}

	public Date getContentPublishTime() {
		return contentPublishTime;
	}

	public void setContentPublishTime(Date contentPublishTime) {
		this.contentPublishTime = contentPublishTime;
	}

	public String getContentTxt() {
		return contentTxt;
	}

	public void setContentTxt(String contentTxt) {
		this.contentTxt = contentTxt == null ? null : contentTxt.trim();
	}

	public List<Attach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<Attach> attachs) {
		this.attachs = attachs;
	}

	public List<ContentChannel> getContentChannels() {
		return contentChannels;
	}

	public void setContentChannels(List<ContentChannel> contentChannels) {
		this.contentChannels = contentChannels;
	}


	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getContentSequence() {
		return contentSequence;
	}

	public void setContentSequence(Integer contentSequence) {
		this.contentSequence = contentSequence;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public Integer getContentClick() {
		return contentClick;
	}

	public void setContentClick(Integer contentClick) {
		this.contentClick = contentClick;
	}

}