package com.rbao.east.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import com.rbao.east.common.Constants;

public class Base64Utils {

	/**
	 * 
	 * 创建日期2014-4-24上午10:12:38 修改日期 作者： TODO 使用Base64加密算法加密字符串 return
	 */
	public static String encodeStr(String plainText) {
		try {
			return new String(Base64.encodeBase64(
					plainText.getBytes(Constants.UTF8), true));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			
		}
		return "";
	}

	/**
	 * 
	 * 创建日期2014-4-24上午10:15:11 修改日期 作者： TODO 使用Base64加密 return
	 */
	public static String decodeStr(String encodeStr) {
		try {
			return new String(Base64.decodeBase64(encodeStr
					.getBytes(Constants.UTF8)));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			
		}
		return "";
	}
}
