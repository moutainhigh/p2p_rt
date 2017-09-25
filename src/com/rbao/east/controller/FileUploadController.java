package com.rbao.east.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rbao.east.utils.ImageUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.UploadUtils;

/**
 * @author ltq 批量上传图片和附件
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping("upload/")
public class FileUploadController {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(FileUploadController.class);
	private static long maxSize=1000000;
	private static HashMap<String, String> extMap = new HashMap<String, String>();
	static {
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,pptx,pdf,htm,html,txt,zip,rar,gz,bz2");
	}

	/**
	 * 错误信息参数
	 */

	@RequestMapping(value = "/editorImg")
	public void attachUpload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ret = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("filedata");
		if(file.getSize() > maxSize){
			ret = "{\"err\":\"上传文件大小超过限制\",\"msg\":\"上传文件大小超过限制\"}";
			SpringUtils.renderJson(response, ret);
			return;
		}
		String realFileName = file.getOriginalFilename();
		/** 构建图片保存的目录 **/
		String filePathDir = UploadUtils.getRelatedPath();
		/** 得到图片保存目录的真实路径 **/
		String fileRealPathDir = UploadUtils.getRealPath();

		/** 获取文件的后缀 **/
		String suffix = realFileName.substring(realFileName.lastIndexOf("."));
		if (Arrays.<String> asList(extMap.get("image").split(",")).contains(
				realFileName.substring(realFileName.lastIndexOf(".") + 1)
						.toLowerCase())) {
		// /**使用UUID生成文件名称**/
		String fileImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
		// String fileImageName = multipartFile.getOriginalFilename();
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = fileRealPathDir + File.separator + fileImageName;

		String resultFilePath = filePathDir + "/" + fileImageName;
		File newFile = new File(fileName);
		try {
			FileCopyUtils.copy(file.getBytes(), newFile);
		} catch (IllegalStateException e) {
			
		} catch (IOException e) {
			
		}
		ImageUtils.Thumbnail(fileName, fileRealPathDir + File.separator
				+ "mid_" + fileImageName, 320, 240, 100);
		if (StringUtils.isNotBlank(resultFilePath)) {
			resultFilePath = resultFilePath.replaceAll("\\\\", "/");
		}
		String reqPath = request.getContextPath() + resultFilePath;
		// 返回路径给页面上传
		 ret = "{\"err\":\"\",\"msg\":\"" + reqPath + "\"}";
		}else
		{
			ret = "{\"err\":\"\",\"msg\":\"上传文件格式错误\"}";
		}
		SpringUtils.renderText(response, ret);

	}

	@RequestMapping(value = "/editorImgForKindEditor")
	public void editorImgForKindEditor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8"); //转码
		PrintWriter out = response.getWriter();
		// 定义允许上传的文件扩展名
		JSONObject obj = new JSONObject();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("imgFile");
		String realFileName = file.getOriginalFilename();
		if(file.getSize() > maxSize){
			obj.put("message", "上传文件大小超过限制");
			obj.put("error", "上传文件大小超过限制");
			out.println(obj.toString());
			//SpringUtils.renderJson(response, param);
			return;
		}
		/** 构建图片保存的目录 **/
		String filePathDir = UploadUtils.getRelatedPath();
		/** 得到图片保存目录的真实路径 **/
		String fileRealPathDir = UploadUtils.getRealPath();

		/** 获取文件的后缀 **/
		String suffix = realFileName.substring(realFileName.lastIndexOf("."));
		if (Arrays.<String> asList(extMap.get("image").split(",")).contains(
				realFileName.substring(realFileName.lastIndexOf(".") + 1)
						.toLowerCase())) {
			// /**使用UUID生成文件名称**/
			String fileImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
			// String fileImageName = multipartFile.getOriginalFilename();
			/** 拼成完整的文件保存路径加文件 **/
			String fileName = fileRealPathDir + File.separator + fileImageName;

			String resultFilePath = filePathDir + "/" + fileImageName;
			File newFile = new File(fileName);
			try {
				FileCopyUtils.copy(file.getBytes(), newFile);
			} catch (IllegalStateException e) {
				
			} catch (IOException e) {
				
			}
			ImageUtils.Thumbnail(fileName, fileRealPathDir + File.separator
					+ "mid_" + fileImageName, 320, 240, 100);
			if (StringUtils.isNotBlank(resultFilePath)) {
				resultFilePath = resultFilePath.replaceAll("\\\\", "/");
			}
			String reqPath = resultFilePath;
			// 返回路径给页面上传
			obj.put("error", 0);
			obj.put("url", reqPath);
		} else {
			obj.put("url", "");
			obj.put("message", "上传文件扩展名是不允许的扩展名");
			obj.put("error", 1);
		}
	    out.println(obj.toString());
	}

	/*
	 * 多文件上传
	 */
	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
	public void attachsUpload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartRequest.getFiles("Filedata");

		String reqPath = null;
		String realFileName = null;
		String suffix = null;
		for (MultipartFile file : files) {
			realFileName = file.getOriginalFilename();
			System.out.println("文件名：" + realFileName);
			/** 构建图片保存的目录 **/
			String filePathDir = UploadUtils.getRelatedPath();
			/** 得到图片保存目录的真实路径 **/
			String fileRealPathDir = UploadUtils.getRealPath();
			/** 获取文件的后缀 **/
			suffix = realFileName.substring(realFileName.lastIndexOf("."));
			// /**使用UUID生成文件名称**/
			String fileImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
			// String fileImageName = multipartFile.getOriginalFilename();
			/** 拼成完整的文件保存路径加文件 **/
			String fileName = fileRealPathDir + File.separator + fileImageName;

			String resultFilePath = filePathDir + "/" + fileImageName;
			File newFile = new File(fileName);
			try {
				FileCopyUtils.copy(file.getBytes(), newFile);
			} catch (IllegalStateException e) {
				
			} catch (IOException e) {
				
			}
			if (StringUtils.isNotBlank(resultFilePath)) {
				resultFilePath = resultFilePath.replaceAll("\\\\", "/");
			}
			reqPath = resultFilePath;
			// 返回路径给页面上传

		}
		// 返回路径给页面上传
		String ret = "[{\"filepath\":\"" + reqPath + "\",\"filename\":\""
				+ realFileName + "\",\"suffix\":\"" + suffix + "\"}]";
		System.out.println(ret);
		SpringUtils.renderJson(response, ret);

	}

	/**
	 * 删除物理文件 2013-12-12 cjx
	 */
	@RequestMapping(value = "/common/removeImg", method = RequestMethod.POST)
	public void removeImg(HttpServletRequest request,
			HttpServletResponse response) {
		String msg = "0";
		try {
			String imgUrlString = request.getParameter("imgUrl");
			if (StringUtils.isNotBlank(imgUrlString)) {
				String fileRealPathDir = request.getSession()
						.getServletContext().getRealPath(imgUrlString);
				File file = new File(fileRealPathDir);
				if (file.exists()) {
					file.delete();
				}
				msg = "1";
			}
		} catch (Exception e) {
			System.out.println("删除文件出错====" + e.toString());
		}
		SpringUtils.renderJson(response, msg);
	}

}
