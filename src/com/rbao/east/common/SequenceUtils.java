package com.rbao.east.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
/**
 * 
 * 生成订单工具类
 * */
public class SequenceUtils {

	/**
	 * 生成UUID的方法
	 * 
	 * @return
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// System.out.println(s);
		// 去掉“-”符号
		String str = s.substring(0, 8) + s.substring(9, 13)
				+ s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
		return str;
	}
	
	//md5加密
	public static  String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	} 
	/**
	   * 生成充值流水号，是E开始+ 用户ID+当前的年月日时分秒+6位不重复的随机数
	   * @return
	   */
    public static String payRecordNo(Integer userId){
  	 try{
        String pre="E";
  	  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
  	  String dateString=sdf.format(new Date());
  	  String randomString= String.valueOf(Math.random()).substring(2).substring(0, 6);
  	  
  	  return pre+userId+dateString+randomString;
  	 }catch (Exception e) {
			System.out.println("生成充值流水号出错===="+e.toString());
		 }
  	 return "";
    }
    /**
     * 生成标编号 生成规则，标种编号+发标人ID+年月日时分少+6位随机数
     * 
     */
    public static String getBorrowNo(String borrowTypeNo,Integer userId){
   	 try{
   	   SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
   	   String dateString=sdf.format(new Date());
   	   String randomString= String.valueOf(Math.random()).substring(2).substring(0, 6);
   	  
   	   return borrowTypeNo+userId+dateString+randomString;
   	 }catch (Exception e) {
			System.out.println("生成标编号出错===="+e.toString());
		 }
   	 return "";
     }
	
}
