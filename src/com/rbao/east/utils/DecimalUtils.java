package com.rbao.east.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

/**
 * 金额的转换工具类
 * @author LTQ
 *
 */
public class DecimalUtils {

	private static DecimalFormat twoDecimalFormat=new DecimalFormat("0.00");
	
	/**
	 * 保留2位小数
	 * @param money
	 * @return
	 */
	public static String getTwoDecimal(BigDecimal money){
		return twoDecimalFormat.format(money);
	}
	/**
	 * 减掉余数,保留整除部分
	 * @param org
	 * @param in
	 * @return
	 */
	public static BigDecimal subtractRemainder(BigDecimal org,BigDecimal in){
		BigDecimal rem = org.remainder(in,new MathContext(6));
		if(rem.compareTo(new BigDecimal(0)) > 0){
			return org.subtract(rem);
		}
		return org;
	}
	
	/**
	 * 四舍五入,保留两位小数
	 * @param org
	 * @return
	 */
	public static BigDecimal fourHomesFive(BigDecimal org){
		BigDecimal rem = org.setScale(2, BigDecimal.ROUND_HALF_UP);
		return rem;
	}
}
