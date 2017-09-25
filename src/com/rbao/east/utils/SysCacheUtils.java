package com.rbao.east.utils;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;

import com.rbao.east.common.Constants;
import com.rbao.east.entity.SysConfig;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.SysLetterTemplates;
import com.rbao.east.entity.SysSmsConfig;
import com.rbao.east.entity.SysSmtpConfig;

/**
 * 提供读取缓存的方法
 * @author Liutq
 *
 */
public class SysCacheUtils {

	private static ServletContext servletContext = null;
	
	/**
	 * 获取费率
	 * @return
	 */
	public static SysFeesRate getSysFeesRate(){
		return (SysFeesRate)getServletContext().getAttribute(Constants.CACHE_SYS_FEES_RATE);
	}

	/**
	 * 获取Smtp配置
	 * @return
	 */
	public static SysSmtpConfig getSysSmtpConfig(){
		return (SysSmtpConfig)getServletContext().getAttribute(Constants.CACHE_SYS_SMTP_CONFIG);
	}
	
	/**
	 * 获取短信配置
	 * @return
	 */
	public static SysSmsConfig getSysSmsConfig(){
		return (SysSmsConfig)getServletContext().getAttribute(Constants.CACHE_SYS_SMS_CONFIG);
	}
	/**
	 * 获取系统配置
	 * 对应sys_config_params表
	 * @return
	 */
	public static Map<String,String> getConfigParams(){
		return (Map<String,String>) getServletContext().getAttribute("SysConfigParams");
	}
	/**
	 * 获取模板配置
	 * @return
	 */
	public static SysLetterTemplates getSysLetterTemplates(){
		return (SysLetterTemplates)getServletContext().getAttribute(Constants.CACHE_SYS_LETTER_TEMPLATES);
	}
	
	/**
	 * 获取系统参数
	 * @return
	 */
	public static SysConfig getSysConfig(){
		return (SysConfig) getServletContext().getAttribute(Constants.CACHE_SYS_CONFIG);
	}

	public static ServletContext getServletContext() {
		if(servletContext==null){
			servletContext = ContextLoader.getCurrentWebApplicationContext()
					.getServletContext();
		}
		return servletContext;
	}

}
