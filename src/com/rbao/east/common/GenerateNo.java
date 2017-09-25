package com.rbao.east.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 产生各种单号
 * @author zhushuai
 *
 */
public class GenerateNo {

	private static int indx = 10;

	public static synchronized int nextIndx() {
		if (indx > 999)
			indx = 10;
		return indx++;
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
	   * 生成充值流水号16位，是当前的年月日时分秒+2位不重复的随机数  
	   * @return
	   */
      public static String payRecordNo(){
    	 try{
          
    	  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    	  String dateString=sdf.format(new Date());
    	  String randomString= String.valueOf(Math.random()).substring(2).substring(0, 2);
    	  
    	  return dateString+randomString;
    	 }catch (Exception e) {
			System.out.println("生成充值流水号出错===="+e.toString());
		 }
    	 return "";
      }

  	/**
  	 * 获取纯数字唯一订单号
  	 * 
  	 * @return
  	 */
  	public static String nextOrdId() {
  		long time = new Date().getTime();
  		int end = (int) (Math.random() * 10);
  		return String.valueOf(nextIndx()).concat(String.valueOf(time))
  				.concat(String.valueOf(end));

  	}
      /**
	   * 生成VipCard，是VIP开始+ 用户ID+当前的年月日时分秒+6位不重复的随机数
	   * @return
	   */
      public static String vipCard(Integer userId){
    	 try{
          String pre="VIP";
    	  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    	  String dateString=sdf.format(new Date());
    	  String randomString= String.valueOf(Math.random()).substring(2).substring(0, 6);
    	  return pre+userId+dateString+randomString;
    	 }catch (Exception e) {
			System.out.println("生成VIPCard出错===="+e.toString());
		 }
    	 return "";
      }
      
      /**
       * 生成标编号 生成规则，标种编号+发标人ID+年月日时分少+6位随机数
       * 2014-1-9
       * cjx
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
