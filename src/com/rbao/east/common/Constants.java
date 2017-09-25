package com.rbao.east.common;

import com.rbao.east.utils.PropertiesUtil;

/**
 * 系统全局常量类
 * @author Administrator
 *
 */
public class Constants {
	
	/**
	 * 查询路径前缀V
	 */
	public static final String PRE_PATH_VIEW = "v_";
	/**
	 * 更新路径前缀o
	 */
	public static final String PRE_PATH_EDIT = "o_";
	
	public static final String PAGED_CURPAGE = "pageNum"; //当前第几页
	public static final String PAGED_NUM_PERPAGE = "numPerPage";//每页显示多少条
	public static final String TOTAL_PAGE = "totalPage";//总的页数
	
	public final static String ADMIN_USER_SESSION="ADMIN_USER_SESSION"; //管理员session
	public final static String FRONT_USER_SESSION="FRONT_USER_SESSION";//前台会员session
	public final static String APP_USER_SESSION="APP_USER_SESSION";//手机端会员session
	public final static String SESSION_COUNT = "SESSION_COUNT";//统计session数量
	
	public final static String ADMIN_URL = "ADMIN_URL";
	
	public final static String CACHE_SYS_FEES_RATE = "CACHE_SYS_FEES_RATE"; //缓存取费率
	public final static String CACHE_SYS_SMTP_CONFIG = "CACHE_SYS_SMTP_CONFIG"; //SMTP 配置
	public final static String CACHE_SYS_SMS_CONFIG="CACHE_SYS_SMS_CONFIG";//短信配置
	public final static String CACHE_SYS_LETTER_TEMPLATES = "CACHE_SYS_LETTER_TEMPLATES";//模板配置
	public final static String CACHE_SYS_CONFIG = "CACHE_SYS_CONFIG";//模板配置
	public final static String CUSTOMER_SERVICE = "CUSTOMER_SERVICE";//客服
	
	public final static String VAR_BORROW_USER = "borrowUser"; //借款人
	public final static String VAR_AGREE_NO = "agreementNo"; //协议号
	public final static String VAR_DATE = "DATE"; //签订时间
	
	public final static String VAR_TENDER_USER = "tenderuser";//投标人
	public final static String VAR_BORROW_TITLE= "borrowTitle";//标名
	public final static String VAR_BORROW_APR= "apr";//利率
	public final static String VAR_BORROW_PERIOD= "period";//期限
	public final static String VAR_TENDER_AMOUNT= "tenderamount";//投标金额
	public final static String VAR_TENDER_AMOUNT_DAXIE= "daxie";//大写金额
	public final static String VAR_BORROW_ENDTIME= "endtime";//还款时间
	
	public final static String VAR_TENDER_USER_CARDNUMBER= "cardnumber";
	
	/**
	 * app 端的公钥
	 */
	public static final String DES_PUBLIC_KEY_IOS_ANDROID = "20150727";
	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";
	
	public final static String PHONE_CODE="phonevalcode";
	/**
	 * 提示信息
	 */
	public static final String MESSAGE = "message";
	/**
	 * cookie中的JSESSIONID名称
	 */
	public static final String JSESSION_COOKIE = "JSESSIONID";
	/**
	 * url中的jsessionid名称
	 */
	public static final String JSESSION_URL = "jsessionid";
	/**
	 * 附件路径
	 */
	public static final String FILEPATH = "files";
	public static final String FILEPATH_REAL = "/home/datas/";
	/**
	 * HTTP POST请求
	 */
	public static final String POST = "POST";
	/**
	 * HTTP GET请求
	 */
	public static final String GET = "GET";
	
	public static final String DES_PUBLIC_ENCRYPT_KEY = "6Ta4OaHZdpA="; //DES加密key 
	public static final String DES_PRIVATE_ENCRYPT_KEY ="o0al4OaEWBzA1";
	/**
	 * #系统帐号userID，关联一个系统账户，平台的收益通过这个账户提现出来
	 */
//	@Value("#{config['SYSTEM.ADMIN.USERID']}")
	public static final Integer ADMIN_USER_ID = Integer.valueOf(PropertiesUtil.get("SYSTEM.ADMIN.USERID"));
	/**
	 * #平台的担保帐号，平台垫付还款时，用此帐号
	 */
//	@Value("#{config['SYSTEM.PLATFORM.VOUCH.ACCOUNT.USERID']}")
	public static final Integer PLATFORM_VOUCH_USER_ID = Integer.valueOf(PropertiesUtil.get("SYSTEM.PLATFORM.VOUCH.ACCOUNT.USERID"));;
	
	/**
	 * 提前几天通知用户VIP到期
	 */
	public static final int NOTIFY_DAY_NUM = 3;
}
