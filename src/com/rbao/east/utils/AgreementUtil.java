package com.rbao.east.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rbao.east.common.Constants;
import com.rbao.east.common.SequenceUtils;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.User;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.UserService;

public class AgreementUtil {

	/**
	 * 创建协议并发邮件， 包括借款协议；债权转让协议
	 * 
	 * @param pdfContent
	 * @param tableDatas
	 * @param pdfMailTitle
	 * @param recvUsrId
	 * @param extendsValueMap
	 * @return
	 */
	public static String createAgreementThenMailTo(String pdfContent,
			List<String[]> tableDatas, Integer recvUsrId,String mailContent,String mailTitle,
			Map extendsValueMap) {
		User usr = usrSvc.getById(recvUsrId);
		String fileName = SequenceUtils.getUUID() + ".pdf";
		String relatedPath = UploadUtils.getRelatedPath();
		String fullPath = UploadUtils.getRealPath(relatedPath) + "/" + fileName;
		try {
			File file = File.createTempFile("tempFile", ".pdf"); // 创建临时文件
			// 生成PDF
			if (PdfUtils.createPDFFile(file, tableDatas, pdfContent)) {
				String watermark = UploadUtils.getRealPath(PropertiesUtil
						.get("AGREEMENT.PDF.WATERMARK.FILEPATH"));
				//第二个水印
				String watermark2 = UploadUtils.getRealPath(PropertiesUtil
						.get("AGREEMENT.PDF.WATERMARK2.FILEPATH"));
				PdfUtils.waterMarkTwo(file.getPath(), watermark,watermark2, fullPath); // 添加水印
			}
			String relatedflPath = relatedPath + "/" + fileName;

			if (StringUtils.isEmpty(usr.getUserEmail())) {
				log.error("not send agreement pdf to "+usr.getUserAccount()+";user's email is null");
				return relatedflPath;
			}
			// 发邮件
			Map<String, String> attachments = new HashMap<String, String>();
			attachments.put(fileName, fullPath);

			MessageCenter center = new MessageCenter();
			center.setSendUserId(Constants.ADMIN_USER_ID);
			center.setReceiveUserId(usr.getId());
			center.setMessageAddress(usr.getUserEmail());
			center.setMessageContent("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
					+ "</head><body>"
					+ mailContent
					+ "</body></html>");
			center.setMessageTitle(mailTitle);
			center.setMessageSendIp(RequestUtils.getIpAddr());
			msgCterSvc.sendEmail(center, null, attachments);

			return relatedflPath;
		} catch (Exception e) {
			log.error("createAgreementThenMailTo error:"+recvUsrId,e);
			return null;
		}
	}
	
	/**
	 * 创建协议并发邮件， 专用于债券转让
	 * 
	 * @param pdfContent
	 * @param tableDatas
	 * @param pdfMailTitle
	 * @param recvUsrId
	 * @param extendsValueMap
	 * @return
	 */
	public static String createAgreementThenMailToTransfer(String pdfContent,
			List<String[]> tableDatas, Integer recvUsrId,String mailContent,String mailTitle,
			Map extendsValueMap) {
		User usr = usrSvc.getById(recvUsrId);
		String fileName = SequenceUtils.getUUID() + ".pdf";
		String relatedPath = UploadUtils.getRelatedPath();
		String fullPath = UploadUtils.getRealPath(relatedPath) + "/" + fileName;
		try {
			File file = File.createTempFile("tempFile", ".pdf"); // 创建临时文件
			// 生成PDF
			if (PdfUtils.createPDFFile(file, tableDatas, pdfContent)) {
				String watermark = UploadUtils.getRealPath(PropertiesUtil
						.get("AGREEMENT.PDF.WATERMARK.FILEPATH"));
				/*//第二个水印
				String watermark2 = UploadUtils.getRealPath(PropertiesUtil
						.get("AGREEMENT.PDF.WATERMARK2.FILEPATH"));*/
				PdfUtils.waterMark(file.getPath(), watermark, fullPath); // 添加水印
			}
			String relatedflPath = relatedPath + "/" + fileName;

			if (StringUtils.isEmpty(usr.getUserEmail())) {
				log.error("not send agreement pdf to "+usr.getUserAccount()+";user's email is null");
				return relatedflPath;
			}
			// 发邮件
			Map<String, String> attachments = new HashMap<String, String>();
			attachments.put(fileName, fullPath);

			MessageCenter center = new MessageCenter();
			center.setSendUserId(Constants.ADMIN_USER_ID);
			center.setReceiveUserId(usr.getId());
			center.setMessageAddress(usr.getUserEmail());
			center.setMessageContent("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
					+ "</head><body>"
					+ mailContent
					+ "</body></html>");
			center.setMessageTitle(mailTitle);
			center.setMessageSendIp(RequestUtils.getIpAddr());
			msgCterSvc.sendEmail(center, null, attachments);

			return relatedflPath;
		} catch (Exception e) {
			log.error("createAgreementThenMailTo error:"+recvUsrId,e);
			return null;
		}
	}

	private static final Logger log = LoggerFactory
			.getLogger(AgreementUtil.class);
	private static UserService usrSvc = SpringUtils.getBean(UserService.class,
			"userServiceImpl");
	private static MessageCenterService msgCterSvc = SpringUtils.getBean(
			MessageCenterService.class, "messageCenterServiceImpl");

}
