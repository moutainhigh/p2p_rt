package com.rbao.east.appapi.common;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * API工具类
 * @author  syl
 *
 */
public class AppUtils {
	
	/**
	 * 把对象转换成MAP对象
	 * @param targets
	 * 			目标数组
	 * @param resource
	 * 			资源对象
	 * @param id
	 * 			处理ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> toObjectMap(String[] targets, Object resource, String id) {
		Map<String, String> map = new HashMap<String, String>();
		if(targets != null && targets.length > 0) {
			for(String key : targets) {
				Boolean flag = false;
				Object value = null;//获取的值
				if(resource instanceof Map) {
					Map<Object, Object> line = (Map<Object, Object>) resource;
					if(line != null && line.containsKey(key)) {
						value = line.get(key);
					}
				} else {
					String get = "get" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
					try {
						Method method = resource.getClass().getDeclaredMethod(get);
						method.setAccessible(true);
						value = method.invoke(resource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(value instanceof Date || value instanceof Timestamp) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					if(key.equals("id")) {
						map.put(id, format.format(value));
					} else {
						map.put(key, format.format(value));
					}
					flag = true;
				} else if(value instanceof Double || value instanceof BigDecimal) {
					DecimalFormat format = new DecimalFormat("#0.00");
					if(key.equals("id")) {
						map.put(id, format.format(value));
					} else {
						map.put(key, format.format(value));
					}
					flag = true;
				} else {
					if(value != null) {
						if(key.equals("id")) {
							map.put(id, value.toString());
						} else {
							map.put(key, value.toString());
						}
						flag = true;
					}
					
				}
				
				if(!flag) {
					if(key.equals("id")) {
						map.put(id, "");
					} else {
						map.put(key, "");
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 
	* @Title: setPercentStard
	* @Description: 格式化百分比
	* @return String    返回类型
	* @throws
	 */
	public static  String setPercentStard(String percent){
		BigDecimal bigPercent = new BigDecimal(percent);
		if(bigPercent.compareTo(new BigDecimal(100)) == 0 || bigPercent.compareTo(new BigDecimal(0)) == 0){
			return bigPercent.intValue()+"";
		}else{
			percent = bigPercent.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"";
			return percent;
		}
		
	}
	
}
