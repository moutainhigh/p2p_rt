package com.rbao.east.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.SysSmtpConfig;
import com.rbao.east.entity.User;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.SysCacheUtils;



public class MailSendTool {

//	 //发件人邮箱服务器
//	private String emailHost;
//	//发件人邮箱
//	private String emailFrom;
//	// 发件人用户名
//	private String emailUserName;
//	 //发件人密码
//	private String emailPassword;
//	 //收件人邮箱，多个邮箱以“;”分隔
//	private String toEmails;
//	//邮件主题
//	private String subject;
//	// 邮件内容
//	private String content;
//	//邮件中的图片，为空时无图片。map中的key为图片ID，value为图片地址
	private Map<String, String> pictures;
//	//邮件中的附件，为空时无附件。map中的key为附件ID，value为附件地址
	private Map<String, String> attachments;

	
	/**
	 * 发送邮件
	 * 
	 * 
	 * 
	 * @throws Exception
	 */
	public boolean  sendEmail(MessageCenter message) throws Exception {
		boolean flag=false;
		SysSmtpConfig smtp=SysCacheUtils.getSysSmtpConfig();
		/*SysSmtpConfig smtp=new SysSmtpConfig();
		smtp.setSysSmtpServer("smtp.exmail.qq.com");
		smtp.setSysMailAddress("hello@360xjs.com");
		smtp.setSysMailName("hello@360xjs.com");
		smtp.setSysMailPassword("123qaz@");
		smtp.setSysSmtpNeedauth(0);*/
		if (StringUtils.isBlank(smtp.getSysSmtpServer())
				|| StringUtils.isBlank(smtp.getSysMailAddress())
				|| StringUtils.isBlank(smtp.getSysMailName())
				|| StringUtils.isBlank(smtp.getSysMailPassword())) {
			throw new RuntimeException("发件人信息不完全，请确认发件人信息！");
		}

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		// 设定mail server
		senderImpl.setHost(smtp.getSysSmtpServer());

		// 建立邮件消息
		MimeMessage mailMessage = senderImpl.createMimeMessage();

		MimeMessageHelper messageHelper = null;
		messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
		// 设置发件人邮箱
		System.out.print("设置发件人邮箱:"+smtp.getSysMailAddress());
		messageHelper.setFrom(new InternetAddress (smtp.getSysMailAddress(),javax.mail.internet.MimeUtility.encodeText(smtp.getSysMailSender())));
		// 设置收件人邮箱
		String[] toEmailArray =message.getMessageAddress().split(";");
		List<String> toEmailList = new ArrayList<String>();
		if (null == toEmailArray || toEmailArray.length <= 0) {
			throw new RuntimeException("收件人邮箱不得为空！");
		} else {
			for (String s : toEmailArray) {
				if (StringUtils.isNotBlank(s)) {
					toEmailList.add(s);
				}
			}
			if (null == toEmailList || toEmailList.size() <= 0) {
				throw new RuntimeException("收件人邮箱不得为空！");
			} else {
				toEmailArray = new String[toEmailList.size()];
				for (int i = 0; i < toEmailList.size(); i++) {
					toEmailArray[i] = toEmailList.get(i);
				}
			}
		}
		messageHelper.setTo(toEmailArray);

		// 邮件主题
		messageHelper.setSubject(message.getMessageTitle());

		// true 表示启动HTML格式的邮件
		messageHelper.setText(message.getMessageContent(), true);
		
		// 添加图片
		if (null != pictures) {
			for (Iterator<Map.Entry<String, String>> it = pictures.entrySet()
					.iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				String cid = entry.getKey();
				String filePath = entry.getValue();
				if (null == cid || null == filePath) {
					throw new RuntimeException("请确认每张图片的ID和图片地址是否齐全！");
				}

				File file = new File(filePath);
				if (!file.exists()) {
					throw new RuntimeException("图片" + filePath + "不存在！");
				}

				FileSystemResource img = new FileSystemResource(file);
				messageHelper.addInline(cid, img);
			}
		}

		// 添加附件
		if (null != attachments) {
			for (Iterator<Map.Entry<String, String>> it = attachments
					.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				String cid = entry.getKey();
				String filePath = entry.getValue();
				if (null == cid || null == filePath) {
					throw new RuntimeException("请确认每个附件的ID和地址是否齐全！");
				}

				File file = new File(filePath);
				if (!file.exists()) {
					throw new RuntimeException("附件" + filePath + "不存在！");
				}

				FileSystemResource fileResource = new FileSystemResource(file);
				messageHelper.addAttachment(cid, fileResource);
			}
		}

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", smtp.getSysSmtpNeedauth()==0?"true":"false"); // 是否让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		//是否ssl认证
		if(smtp.getSysSslAuthentication()== SysSmtpConfig.sysSslAuthentication_yes){
			prop.put("mail.smtp.starttls.enable","true");
			prop.setProperty("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			prop.setProperty("mail.smtp.socketFactory.fallback", "false");
			prop.setProperty("mail.smtp.socketFactory.port", smtp.getSysSmtpPort().toString());
		}
		prop.setProperty("mail.smtp.port", smtp.getSysSmtpPort().toString());
		
		// 添加验证
		EmailAutherticator auth = new EmailAutherticator(smtp.getSysMailName(),
				smtp.getSysMailPassword());
		Session session = Session.getDefaultInstance(prop, auth);
		senderImpl.setSession(session);

		// 发送邮件
		try{
			senderImpl.send(mailMessage);
			flag=true;
		}catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	public static void main(String[] args) throws Exception {
		MailSendTool mu = new MailSendTool();
		// test1(mu);
		test2(mu);
		// test3(mu);
		// test4(mu);
		// test5(mu);
		// test6(mu);
	}

	public static void test1(MailSendTool mu) throws Exception {
		String toEmails = "28099868@qq.com";
		String subject = "第一封，简单文本邮件";
		StringBuilder builder = new StringBuilder();
		builder.append("我相信天上不会掉馅饼");
		String content = builder.toString();
		MessageCenter message=new MessageCenter();
		message.setMessageAddress(toEmails);
		message.setMessageContent(content);
		message.setMessageTitle(subject);
		mu.sendEmail(message);
	}

	public static void test2(MailSendTool mu) throws Exception {
		String toEmails = "1053636324@qq.com";
		String subject = "第二封，HTML邮件";
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body><br />我是你的吗？<br />是的，是很久了。<br /></body></html>");
		String content = builder.toString();
		MessageCenter message=new MessageCenter();
		message.setMessageAddress(toEmails);
		message.setMessageContent(content);
		message.setMessageTitle(subject);
		mu.sendEmail(message);
	}

	public static void test3(MailSendTool mu) throws Exception {
		String toEmails = "774599724@qq.com";
		String subject = "第三封，图片邮件";

		Map<String, String> pictures = new HashMap<String, String>();
		pictures.put("d1", "D:/work/download/d1.jpg");
		pictures.put("d2", "D:/work/download/测试图片2.jpg");
		pictures.put("d3", "D:/work/download/d3.jpg");

		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>看看下面的图，你会知道花儿为什么是这样红的：<br />");
		builder.append("<img src=\"cid:d1\" /><br />");
		builder.append("<img src=\"cid:d2\" /><br />");
		builder.append("<img src=\"cid:d3\" /><br />");
		builder.append("</body></html>");
		String content = builder.toString();
		MessageCenter message=new MessageCenter();
		message.setMessageAddress(toEmails);
		message.setMessageContent(content);
		message.setMessageTitle(subject);
		mu.sendEmail(message);

	}

	public static void test4(MailSendTool mu) throws Exception {
		String toEmails = "774599724@qq.com";
		String subject = "第四封，附件邮件";
		Map<String, String> attachments = new HashMap<String, String>();
		attachments.put("d1.jar", "D:/work/download/activation.jar");
		attachments.put("d2.doc",
				"C:/Documents and Settings/Administrator/桌面/Java设计模式.doc");
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>看看附件中的资料，你会知道世界为什么是平的。</body></html>");
		String content = builder.toString();
		MessageCenter message=new MessageCenter();
		message.setMessageAddress(toEmails);
		message.setMessageContent(content);
		message.setMessageTitle(subject);
		mu.sendEmail(message);
	}

	public static void test5(MailSendTool mu) throws Exception {
		String toEmails = "774599724@qq.com";
		String subject = "第五封，综合邮件";

		Map<String, String> attachments = new HashMap<String, String>();
		attachments.put("d1.jar", "D:/work/download/activation.jar");
		attachments.put("d2.doc",
				"C:/Documents and Settings/Administrator/桌面/Java设计模式.doc");

		Map<String, String> pictures = new HashMap<String, String>();
		pictures.put("d1", "D:/work/download/d1.jpg");
		pictures.put("d2", "D:/work/download/测试图片2.jpg");
		pictures.put("d3", "D:/work/download/d3.jpg");

		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>看看附件中的资料，你会知道世界为什么是平的。<br />");
		builder.append("看看下面的图，你会知道花儿为什么是这样红的：<br />");
		builder.append("<img src=\"cid:d1\" /><br />");
		builder.append("<img src=\"cid:d2\" /><br />");
		builder.append("<img src=\"cid:d3\" /><br />");
		builder.append("</body></html>");
		String content = builder.toString();
		MessageCenter message=new MessageCenter();
		message.setMessageAddress(toEmails);
		message.setMessageContent(content);
		message.setMessageTitle(subject);
		mu.sendEmail(message);
	}

	public static void test6(MailSendTool mu) throws Exception {
		String toEmails = "774599724@qq.com;geloin@163.com";
		String subject = "第五封，群发邮件";

		Map<String, String> attachments = new HashMap<String, String>();
		attachments.put("d1.jar", "D:/work/download/activation.jar");
		attachments.put("d2.doc",
				"C:/Documents and Settings/Administrator/桌面/Java设计模式.doc");

		Map<String, String> pictures = new HashMap<String, String>();
		pictures.put("d1", "D:/work/download/d1.jpg");
		pictures.put("d2", "D:/work/download/测试图片2.jpg");
		pictures.put("d3", "D:/work/download/d3.jpg");

		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>看看附件中的资料，你会知道世界为什么是平的。<br />");
		builder.append("看看下面的图，你会知道花儿为什么是这样红的：<br />");
		builder.append("<img src=\"cid:d1\" /><br />");
		builder.append("<img src=\"cid:d2\" /><br />");
		builder.append("<img src=\"cid:d3\" /><br />");
		builder.append("</body></html>");
		String content = builder.toString();
		MessageCenter message=new MessageCenter();
		message.setMessageAddress(toEmails);
		message.setMessageContent(content);
		message.setMessageTitle(subject);
		mu.sendEmail(message);
	}
	
	/**
	 * 激活邮箱，链接加密
	 * @param user
	 * @return
	 */
	public String getdecodeIdStr(User user){
		String chars="0123456789qwertyuiopasdfghjklmnbvcxz-=~!@#$%^*+-._/*<>|";
		int length=chars.length();
		StringBuffer url=new StringBuffer();
		StringBuffer rancode=new StringBuffer();
		String timeStr=System.currentTimeMillis()/1000+"";
		String userIDAddtime = user.getId()+ DateUtils.convertDateToString(user.getUserAddtime());
		MD5Utils md5=new MD5Utils();
		userIDAddtime =  md5.stringToMD5(userIDAddtime);
		url.append(user.getId()).append(",").append(userIDAddtime).append(",").append(timeStr).append(",");
		for(int i=0;i<10;i++){
			int num=(int)(Math.random()*(length-2))+1;
			rancode.append(chars.charAt(num));
		}
		url.append(rancode);
		String idurl=url.toString();
		BASE64Encoder encoder=new BASE64Encoder();
		String s=encoder.encode(idurl.getBytes());
		return s;
	}

	public Map<String, String> getPictures() {
		return pictures;
	}

	public void setPictures(Map<String, String> pictures) {
		this.pictures = pictures;
	}

	public Map<String, String> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}
	
}
