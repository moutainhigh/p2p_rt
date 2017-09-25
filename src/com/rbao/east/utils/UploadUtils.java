package com.rbao.east.utils;

import java.io.File;
import java.util.Date;

import org.springframework.web.context.ContextLoader;

/**
 * 上传文件工具类
 * @author Liutq
 *
 */
public class UploadUtils {

	/**
	 * 附件路径
	 */
	public static final String FILE_PATH = "files";
	public static final String FILE_PATH_REAL = "/home/datas/files";
	
	/**
	 * 获得上传目录的相对地址
	 * @return
	 */
	public static String getRelatedPath(){
		String filePathDir = "/"+FILE_PATH +  "/"+DateUtils.formatDate(new Date());
		
		return filePathDir;
	}
	/**
	 * 获得上传目录的完整路径
	 * @return
	 */
	public static String getRealPath(){
//		return getRealPath(getRelatedPath());
		String fileRealPathDir = FILE_PATH_REAL + "/"+DateUtils.formatDate(new Date());
		File pathFile = new File(fileRealPathDir);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		
		return fileRealPathDir;
	}
	/**
	 * 获得上传目录的完整路径
	 * @return
	 */
	public static String getRealPath(String path){
		String fileRealPathDir = ContextLoader.getCurrentWebApplicationContext()
				.getServletContext().getRealPath(path);
		File pathFile = new File(fileRealPathDir);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		return fileRealPathDir;
	}
}
