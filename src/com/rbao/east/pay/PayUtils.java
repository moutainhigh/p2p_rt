package com.rbao.east.pay;

import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;
public class PayUtils {

	/**
	 * 宝付支付加密
	 * @param args
	 */
	public static String getMD5ofStr(String str){
		return getMD5ofStr(str,"utf-8");
	}
	public  static String getMD5ofStr(String str, String encode) {
		try{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(str.getBytes(encode));
		byte[] digest = md5.digest();

		StringBuffer hexString = new StringBuffer();
		String strTemp;
		for (int i = 0; i < digest.length; i++) {
			// byteVar &
			// 0x000000FF的作用是，如果digest[i]是负数，则会清除前面24个零，正的byte整型不受影响。
			// (...) | 0xFFFFFF00的作用是，如果digest[i]是正数，则置前24位为一，
			// 这样toHexString输出一个小于等于15的byte整型的十六进制时，倒数第二位为零且不会被丢弃，这样可以通过substring方法进行截取最后两位即可。
			strTemp = Integer.toHexString(
					(digest[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
			hexString.append(strTemp);
		}
			return hexString.toString();
		}catch(Exception e){
			
			return "";
		}

	}
	
	public static String signMap(String[] md5Map, String securityKey, String type)
	{
		String[] md5ReqMap = new String[]
		{ "MerNo", "BillNo", "Amount", "ReturnURL" };
		String[] md5ResMap = new String[]
		{ "MerNo", "BillNo", "Amount", "Succeed" };
		Map<String, String> map = new TreeMap<String, String>();
		if (type.equals("REQ"))
		{
			for (int i = 0; i < md5ReqMap.length; i++)
			{
				map.put(md5ReqMap[i], md5Map[i]);
			}
		}
		else if (type.equals("RES"))
		{
			for (int i = 0; i < md5ResMap.length; i++)
			{
				map.put(md5ResMap[i], md5Map[i]);
			}
		}
		
		EpayMD5 md5=new EpayMD5();
		String strBeforeMd5 = joinMapValue(map, '&') + md5.getMD5ofStr(securityKey);
		System.out.println(strBeforeMd5);
		
		return md5.getMD5ofStr(strBeforeMd5);
		
	}
	public static String joinMapValue(Map<String, String> map, char connector)
	{
		StringBuffer b = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet())
		{
			b.append(entry.getKey());
			b.append('=');
			if (entry.getValue() != null)
			{
				b.append(entry.getValue());
			}
			b.append(connector);
		}
		return b.toString();
	}
}
