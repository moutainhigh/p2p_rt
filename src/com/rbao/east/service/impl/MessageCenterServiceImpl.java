package com.rbao.east.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.rbao.east.controller.admin.UserManagerController;
import com.rbao.east.dao.MessageCenterDao;
import com.rbao.east.dao.UserDao;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.NoticeUser;
import com.rbao.east.entity.User;
import com.rbao.east.mail.MailSendTool;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.NoticeService;
import com.rbao.east.service.NoticeUserService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SmsUtil;

/**
 * 
 * @author xiangxiaoyan
 * 
 */
@Service
@Transactional
public class MessageCenterServiceImpl implements MessageCenterService {

	private static Logger logger = LoggerFactory
			.getLogger(UserManagerController.class);
	@Autowired
	private MessageCenterDao messageCenterDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeUserService noticeUserService;

	public PageModel getPagedList(Map<String, String> paramsMap) {
		return this.messageCenterDao.getPage("selectMsgCenterList",
				"selectMsgCenterTotalCount", paramsMap);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteMsgCenter(Integer id) {
		return this.messageCenterDao.deleteByPrimaryKey(id);
	}

	/**
	 * 站内信
	 */
	@Override
	public boolean sendMessage(MessageCenter center, String noticeCode) {
		boolean flag = false;
		try {
			center.setMessageStatus(MessageCenter.STATUS_UNREAD);// 默认未读
			center.setNoticeTypeId(MessageCenter.MESSAGE);
			flag = this.messageCenterDao.saveOrUpdate(center);
		} catch (Exception e) {

			logger.error("发送站内信失败");
			flag = false;
			throw new RuntimeException("运行时出错！");
		}
		return flag;
	}

	/***
	 * 短信
	 * 
	 * @param center
	 * @return
	 */
	@Override
	public boolean sendSms(MessageCenter center, String noticeCode) {
		User user = userDao.selectByPrimaryKey(center.getReceiveUserId());
		if (StringUtils.isEmpty(center.getMessageAddress())) {
			center.setMessageAddress(user.getUserPhone());
		}
		boolean flag = false;
		try {
			if (StringUtils.isNotBlank(center.getMessageAddress())) {
				int ret = SmsUtil.sendSms(center.getMessageAddress(),
						center.getMessageContent());// 发送短信
				// 发送成功为0，其他都为失败
				if (ret == 0) {
					center.setMessageStatus(MessageCenter.STATUS_SUCCESS);
				} else {
					center.setMessageStatus(MessageCenter.STATUS_FAILD);
				}
				center.setNoticeTypeId(MessageCenter.SMS);
				flag = this.messageCenterDao.saveOrUpdate(center);
			}
		}  catch (Exception e) {

			logger.error("发送短信失败");
			flag = false;
			throw new RuntimeException("运行时出错！");
		}
		return flag;
	}

	/**
	 * 邮件
	 * 
	 * @param center
	 * @return
	 */
	@Override
	public boolean sendEmail(MessageCenter center, String noticeCode) {
		User user = userDao.selectByPrimaryKey(center.getReceiveUserId());
		if (StringUtils.isEmpty(center.getMessageAddress())) {
			center.setMessageAddress(user.getUserEmail());
		}
		boolean flag = false;
		try {
			if (StringUtils.isNotBlank(center.getMessageAddress())) {
				MailSendTool mail = new MailSendTool();
				// 发送邮件
				if (mail.sendEmail(center)) {
					center.setMessageStatus(MessageCenter.STATUS_SUCCESS);
				} else {
					center.setMessageStatus(MessageCenter.STATUS_FAILD);
				}
				center.setNoticeTypeId(MessageCenter.EMAIL);
				flag = this.messageCenterDao.saveOrUpdate(center);
			}
		} catch (Exception e) {
			logger.error("发送邮件失败");
			flag = false;
			throw new RuntimeException("运行时出错！");
		}
		return flag;
	}

	/**
	 * 保存，判断
	 * 
	 * @param center
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean toSaveMsgCenter(MessageCenter center, String receiveUserId,
			String noticeTypeId) {
		boolean flag = false;
		String[] noticeTypes = StringUtils.split(noticeTypeId, ",");
		String[] receiveUsers = StringUtils.split(receiveUserId, ",");
		try {
			if (center.getId() == null) {
				if (noticeTypeId != null) {
					for (int i = 0; i < noticeTypes.length; i++) {
						for (int j = 0; j < receiveUsers.length; j++) {
							center.setReceiveUserId(Integer
									.parseInt(receiveUsers[j].toString()));
							// 1,站内信 2，邮件 3，手机短信
							if (Integer.parseInt(noticeTypes[i]) == MessageCenter.MESSAGE) {
								flag = this.sendMessage(center,
										Notice.NOTICE_CODE_ADMIN_SEND);
							}
							if (Integer.parseInt(noticeTypes[i]) == MessageCenter.EMAIL) {

								User user = this.userDao
										.selectByPrimaryKey(Integer
												.parseInt(receiveUsers[j]));
								// 邮件地址
								if (user.getUserEmail() != null
										&& !"".equals(user.getUserEmail())) {
									center.setMessageAddress(user
											.getUserEmail());
									flag = this.sendEmail(center,
											Notice.NOTICE_CODE_ADMIN_SEND);
								}
							}
							if (Integer.parseInt(noticeTypes[i]) == MessageCenter.SMS) {
								User user = this.userDao
										.selectByPrimaryKey(Integer
												.parseInt(receiveUsers[j]));
								// 手机号
								if (user.getUserPhone() != null
										&& !"".equals(user.getUserPhone())) {
									center.setMessageAddress(user
											.getUserPhone());
									flag = this.sendSms(center,
											Notice.NOTICE_CODE_ADMIN_SEND);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {

			logger.error("添加或者修改信息出错");
			flag = false;
			throw new RuntimeException("运行时出错！");
		}
		return flag;
	}

	public void send(Integer userid, String title, String content,
			String noticeType) {
		MessageCenter center = new MessageCenter();
		center.setMessageContent(content);
		center.setMessageTitle(title);
		center.setReceiveUserId(userid);
		try {
			if (StringUtils.isEmpty(center.getMessageSendIp())) {
				center.setMessageSendIp(RequestUtils.getIpAddr());
			}
		} catch (Exception e1) {
		}
		try {
			send(center, noticeType);
		} catch (Exception e) {
			logger.error("send error:" + noticeType + ";" + userid + ";"
					+ title + ";" + content, e);
		}
	}

	@Override
	public boolean send(MessageCenter center, String noticeCode) {
		try {
			if (center.getSendUserId() == null) {
				center.setSendUserId(Constants.ADMIN_USER_ID);
			}
			if (StringUtils.isEmpty(center.getReceiveUserId().toString())) {
				return false;
			}
			User user = userDao.selectByPrimaryKey(center.getReceiveUserId());
			if (user == null) {
				return false;
			}

			String code = noticeCode.toLowerCase();
			Notice notice = noticeService.getByNoticeCode(code);
			NoticeUser noticeUser = noticeUserService
					.getNoticeUserByUserIdAndNoticeId(
							center.getReceiveUserId(), notice.getId());

			if (noticeUser != null) {
				if (notice.getEmail().equals(Notice.EMAIL_REQUIRED_SELECTED)) {
					try {
						if (!StringUtils.isEmpty(user.getUserEmail())
								&& user.getEmailStatus()
										.equals(User.EMAIL_PASS)) {
							center.setMessageAddress(user.getUserEmail());
							this.sendEmail(center, code);
						}
					} catch (Exception e) {

					}
				}
				if (notice.getMessage()
						.equals(Notice.MESSAGE_REQUIRED_SELECTED)) {
					try {
						center.setMessageAddress("");
						this.sendMessage(center, code);
					} catch (Exception e) {

					}
				}
				if (notice.getPhone().equals(Notice.PHONE_REQUIRED_SELECTED)) {
					try {
						if (!StringUtils.isEmpty(user.getUserPhone())
								&& user.getPhoneStatus()
										.equals(User.PHONE_PASS)) {
							center.setMessageAddress(user.getUserPhone());
							this.sendSms(center, code);
						}
					} catch (Exception e) {

					}
				}
				if (noticeUser.getEmail().equals(NoticeUser.NOTICE_SEND_YES)) {
					try {
						if (!StringUtils.isEmpty(user.getUserEmail())
								&& user.getEmailStatus()
										.equals(User.EMAIL_PASS)) {
							center.setMessageAddress(user.getUserEmail());
							this.sendEmail(center, code);
						}
					} catch (Exception e) {

					}
				}
				if (noticeUser.getMessage().equals(NoticeUser.NOTICE_SEND_YES)) {
					try {
						center.setMessageAddress("");
						this.sendMessage(center, code);
					} catch (Exception e) {

					}
				}
				if (noticeUser.getPhone().equals(NoticeUser.NOTICE_SEND_YES)) {
					try {
						if (!StringUtils.isEmpty(user.getUserPhone())
								&& user.getPhoneStatus()
										.equals(User.PHONE_PASS)) {
							center.setMessageAddress(user.getUserPhone());
							this.sendSms(center, code);
						}
					} catch (Exception e) {

					}
				}
				return true;
			} else {
				// 用户未设置时按照系统规则
				if (notice.getEmail().equals(Notice.EMAIL_REQUIRED_SELECTED)) {
					try {
						if (!StringUtils.isEmpty(user.getUserEmail())
								&& user.getEmailStatus()
										.equals(User.EMAIL_PASS)) {
							center.setMessageAddress(user.getUserEmail());
							this.sendEmail(center, code);
						}
					} catch (Exception e) {

					}
				}
				if (notice.getMessage()
						.equals(Notice.MESSAGE_REQUIRED_SELECTED)) {
					try {
						center.setMessageAddress("");
						this.sendMessage(center, code);
					} catch (Exception e) {

					}
				}
				if (notice.getPhone().equals(Notice.PHONE_REQUIRED_SELECTED)) {
					try {
						if (!StringUtils.isEmpty(user.getUserPhone())
								&& user.getPhoneStatus()
										.equals(User.PHONE_PASS)) {
							center.setMessageAddress(user.getUserPhone());
							this.sendSms(center, code);
						}
					} catch (Exception e) {

					}
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("send error:" + noticeCode + ";" + center, e);
			return false;
		}
	}

	@Override
	public PageModel getUserMessagePage(Map<String, String> paramsMap) {
		return this.messageCenterDao.getPage("selectMsgCenter",
				"selectMsgCenterCount", paramsMap);
	}

	@Override
	public MessageCenter getMsgById(Integer id) {
		return messageCenterDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateMsgCenter(MessageCenter messageCenter) {
		return messageCenterDao.updateByPrimaryKeySelective(messageCenter);
	}

	@Override
	public boolean addMsg(MessageCenter center) {
		return messageCenterDao.saveOrUpdate(center);
	}

	/**
	 * 邮件带附件
	 * 
	 * @param center
	 * @return
	 */
	@Override
	public boolean sendEmail(MessageCenter center, String noticeCode,
			Map<String, String> param) {
		User user = userDao.selectByPrimaryKey(center.getReceiveUserId());
		if (StringUtils.isEmpty(center.getMessageAddress())) {
			center.setMessageAddress(user.getUserEmail());
		}
		boolean flag = false;
		try {
			MailSendTool mail = new MailSendTool();
			mail.setAttachments(param);

			// 发送邮件
			if (mail.sendEmail(center)) {
				center.setMessageStatus(MessageCenter.STATUS_SUCCESS);
			} else {
				center.setMessageStatus(MessageCenter.STATUS_FAILD);
			}
			center.setNoticeTypeId(MessageCenter.EMAIL);
			flag = this.messageCenterDao.saveOrUpdate(center);
		} catch (Exception e) {

			logger.error("发送邮件失败");
			flag = false;
			throw new RuntimeException("运行时出错！");
		}
		return flag;
	}

	@Override
	public Integer countUnReadMsg(Integer userId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("receiveUserId", userId);
		return messageCenterDao.getTotalCount("countUnReadMsg", map);
	}

	@Override
	public boolean getFlagWithPhoneAndTime(Date date, String tel) {
		boolean falg = true;
		Map<String, String> map = new HashMap<String, String>();
		map.put("tel", tel);
		List selects = messageCenterDao.selects("getFlagWithPhoneAndTime", map);
		if (selects.size() > 0) {
			SimpleDateFormat inputFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");

			MessageCenter messageCenter = (MessageCenter) selects.get(selects
					.size() - 1);
			String messageCenterFormat = inputFormat.format(messageCenter
					.getMessageSendDatetime());
			long startT = fromDateStringToLong(messageCenterFormat); // 数据库中最后一条技术时间
			long endT = fromDateStringToLong(inputFormat.format(date)); // 当前时间

			long ss = (endT - startT) / (1000); // 共计秒数
			if (ss < 60) {// 两分钟之内不发送了
				return falg = false;
			}
			return falg;
		} else {
			return falg;
		}
	}

	public long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
		Date date = null; // 定义时间类型
		SimpleDateFormat inputFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			date = inputFormat.parse(inVal); // 将字符型转换成日期型
		} catch (Exception e) {

		}
		return date.getTime(); // 返回毫秒数
	}
	/**
	 *获取最新记录
	 */
	@Override
	public List<MessageCenter> getMessage(Map<String, String> paramsMap) {
		return  messageCenterDao.select("getMessage", paramsMap);
	}

}
