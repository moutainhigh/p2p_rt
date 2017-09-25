package com.rbao.east.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
/**
 * 访问url方法工具类
 * */
public class WebClient {

	/**
	 * 通过get来访问url
	 * 
	 * @param src
	 *            需要访问的url
	 * @return
	 * @throws Exception
	 */
	public String doGet(String src) throws Exception {
		return doGet(src, "utf-8");
	}

	/**
	 * 通过get来访问url
	 * 
	 * @param src
	 *            需要访问的地址
	 * @param outputencode
	 *            获取输出时的编码
	 * @return
	 * @throws Exception
	 */
	public String doGet(String src, String outputencode)
			throws Exception {
		return doGet(src, outputencode, null);
	}

	/**
	 * 通过get来访问url
	 * 
	 * @param src
	 *            需要访问的地址
	 * @param outputencode
	 *            获取输出时的编码
	 * @param headers
	 *            需要添加的访问头信息
	 * @return
	 * @throws Exception
	 */
	public String doGet(String src, String outputencode,
			HashMap<String, String> headers) throws Exception {
		StringBuffer result = new StringBuffer();
		URL url = new URL(src);
		URLConnection connection = url.openConnection();
		BufferedReader in = null;
		try {
			if (headers != null) {
				Iterator<String> iterators = headers.keySet().iterator();
				while (iterators.hasNext()) {
					String key = iterators.next();
					connection.setRequestProperty(key, headers.get(key));
				}
			}
			connection.connect();
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), outputencode));
			String line;
			while ((line = in.readLine()) != null) {
				result.append("\n" + line);
			}
			return result.toString();
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (in != null) {
				in.close();
			}
		}

	}
	/**
	 * 通过post来访问url
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doPost(String url,Map<String, String> params) throws ClientProtocolException, IOException {
        String result = null;
        List<NameValuePair> nvps = HttpClientHandler.buildNameValuePair(params);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        EntityBuilder builder = EntityBuilder.create();
        try {
            HttpPost httpPost = new HttpPost(url);
            builder.setParameters(nvps);
            httpPost.setEntity(builder.build());
            CloseableHttpResponse response = httpclient.execute(httpPost);

            try {
                HttpEntity entity = response.getEntity();
                if (response.getStatusLine().getReasonPhrase().equals("OK")
                    && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }
	/**
	 * 访问url 
	 * @param params json对象
	 * @return
	 */
	public static String appadd(String noOrder) { 
		StringBuffer sb=null;
        try { 
            //创建连接 
            URL url = new URL("https://yintong.com.cn/traderapi/orderquery.htm"); 
            HttpURLConnection connection = (HttpURLConnection) url 
                    .openConnection(); 
            connection.setDoOutput(true); 
            connection.setDoInput(true); 
            connection.setRequestMethod("POST"); 
            connection.setUseCaches(false); 
            connection.setInstanceFollowRedirects(true); 
            connection.setRequestProperty("Content-Type", 
                    "application/x-www-form-urlencoded"); 

            connection.connect(); 

            //POST请求 
            DataOutputStream out = new DataOutputStream( 
                    connection.getOutputStream()); 
           JSONObject obj = new JSONObject(); 
            obj.put("oid_partner", com.rbao.east.web.llpay.config.PartnerConfig.OID_PARTNER); 
            obj.put("no_order", noOrder); 
            obj.put("sign_type", "RSA"); 
            obj.put("type_dc", "1"); 
            String sign = com.rbao.east.lianlianpayutil.LLPayUtil.addSign(obj, com.rbao.east.web.llpay.config.PartnerConfig.TRADER_PRI_KEY,
                    com.rbao.east.web.llpay.config.PartnerConfig.MD5_KEY);
            obj.put("sign", sign);
            out.writeBytes(obj.toString()); 
            out.flush(); 
            out.close(); 

            //读取响应 
            BufferedReader reader = new BufferedReader(new InputStreamReader( 
                    connection.getInputStream())); 
            String lines; 
             sb = new StringBuffer(""); 
            while ((lines = reader.readLine()) != null) { 
                lines = new String(lines.getBytes(), "utf-8"); 
                sb.append(lines); 
            } 
             
            System.out.println(sb); 
            reader.close(); 
            // 断开连接 
            connection.disconnect(); 
        } catch (MalformedURLException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } catch (UnsupportedEncodingException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        }
		return sb.toString(); 

    } 
	public static void main(String[] args) {
		appadd(null);
	}
}
