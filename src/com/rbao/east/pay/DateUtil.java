package com.rbao.east.pay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mypay.merchantutil.Md5Encrypt;
import com.mypay.merchantutil.UrlHelper;
import com.mypay.merchantutil.timestamp.TimestampResponseParser;
import com.mypay.merchantutil.timestamp.TimestampResponseResult;
import com.mypay.merchantutil.timestamp.TimestampUtils;
import com.rbao.east.common.WebClient;
import com.rbao.east.entity.PaymentConfig;
import com.rbao.east.service.PaymentConfigService;
import com.rbao.east.utils.PropertiesUtil;
import com.rbao.east.utils.SpringUtils;
public class DateUtil {
@Autowired
private PaymentConfigService paymentConfigService = SpringUtils.getBean(PaymentConfigService.class, "paymentConfigServiceImpl");
	/**
	 * 贝付请求时间戳 
	 * @return
	 * @throws Exception 
	 * @author css
	 */
	public String timestamp(String id) throws Exception {
		PaymentConfig paymentConfig = this.paymentConfigService.getById(Integer.parseInt(id));
		//String key = "9UCKYZ6Q804CO5O43TGHLMDO4YTU10hggixe"; // 商户加密字符串
		String key=paymentConfig.getEncryptKey();
		
		String ask_for_time_stamp_gateway = "http://www.ebatong.com/gateway.htm"; // ebatong商户网关
		//String ask_for_time_stamp_gateway=paymentConfig.getGatewaUrl();
		String service = "query_timestamp"; // 服务名称：请求时间戳
		//String partner = "201204201739476361"; // 合作者商户ID
		String partner =paymentConfig.getClientId();
		//String input_charset = "UTF-8"; // 字符集
		String input_charset=PropertiesUtil.get("beifu.inputCharset");
		//String sign_type = "MD5"; // 摘要签名算法
		String sign_type=PropertiesUtil.get("beifu.signType");
		
		// 请求参数排序
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("service",new String[]{service});
		params.put("partner",new String[]{partner});
		params.put("input_charset",new String[]{input_charset});
		params.put("sign_type",new String[]{sign_type});
		String paramStr = UrlHelper.sortParamers(params);
		
		String plaintext = TimestampUtils.mergePlainTextWithMerKey(paramStr, key);
		
		// 加密(MD5加签)，默认已取UTF-8字符集，如果需要更改，可调用Md5Encrypt.setCharset(inputCharset)
		String sign = Md5Encrypt.encrypt(plaintext); 
		
		// 拼接请求
		String url = ask_for_time_stamp_gateway + "?" + paramStr + "&sign=" + sign;
		
		String askForTimestampResponseString = new WebClient().doGet(url);
		// 验证时间戳有效性
		TimestampResponseResult result = TimestampResponseParser.parse(askForTimestampResponseString);
		
		String timestamp=null;
		
		if (result.isSuccess()) {
			timestamp = result.getTimestamp();
			String resultMd5 = result.getResultMd5();
			String timestampMergeWithMerKey = TimestampUtils.mergePlainTextWithMerKey(timestamp, key);
			System.out.println("时间戳：" + timestamp);
			System.out.println("有效性：" + resultMd5.equals(Md5Encrypt.encrypt(timestampMergeWithMerKey)));
		}
		
		return timestamp;
	}
}
