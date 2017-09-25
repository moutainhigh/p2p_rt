package com.rbao.east.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.entity.MessageCenter;
/**
 * 
 * @author xiangxiaoyan
 *	信息中心
 */
public interface MessageCenterService {
	/**
	 * 分页查询
	 * @param paramsMap
	 * @return
	 */
	public PageModel getPagedList(Map<String,String> paramsMap);
	
	public PageModel getUserMessagePage(Map<String,String> paramsMap);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteMsgCenter(Integer id);
	
	public void send(Integer userid,String title,String content,String noticeType);
	/**
	 * 发送站内信、短信、邮件总的调度方法
	 * @param center
	 * @param noticeCode
	 * @return
	 */
	public boolean send(MessageCenter center,String noticeCode) ;
	
	/**
	 * 站内信
	 */
	public boolean sendMessage(MessageCenter center,String noticeCode);
	
	/***
	 * 短信
	 * @param center
	 * @return
	 */
	public boolean sendSms(MessageCenter center,String noticeCode);
	
	/**
	 * 邮件
	 * @param center
	 * @return
	 */
	public boolean sendEmail(MessageCenter center,String noticeCode);
	
	/**
	 * 邮件带附件
	 * @param center
	 * @return
	 */
	public boolean sendEmail(MessageCenter center,String noticeCode,Map<String, String> param);
	
	
	
	/**
	 * 保存，判断
	 * @param center
	 * @return
	 */
	public boolean toSaveMsgCenter(MessageCenter center,String receiveUserId,String  noticeTypeId);
	
	/**
	 * 根据Id查看
	 * @param id
	 * @return
	 */
	public MessageCenter getMsgById(Integer id);
	
	/**
	 * 修改消息
	 * @param messageCenter
	 * @return
	 */
	public boolean updateMsgCenter(MessageCenter messageCenter);
	
	/**
	 * 保存消息
	 * @param center
	 * @return
	 */
	public boolean addMsg(MessageCenter center);
	
	/**
	 * 根据接收者用户Id查询未读站内信条数
	 * @param userId
	 * @return
	 */
	public Integer countUnReadMsg(Integer userId);
	
	
	public boolean getFlagWithPhoneAndTime(Date date, String tel); 
	/**
	 * 获取最新的记录
	 * 
	 * @param address
	 * 			地址
	 * @return
	 */
	public List<MessageCenter> getMessage(Map<String, String> paramsMap);
}
