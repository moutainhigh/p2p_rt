package com.rbao.east.thread.weixin;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeChatUtil {

	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用管理器初始化
			TrustManager[] tm = { new TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			System.out.println("微信服务器连接超时！");
		} catch (Exception e) {
			System.out.println("HTTPS请求错误，错误信息：\n" + e.getMessage());
		}
		return jsonObject;
	}

	// 获取access_token的接口地址（GET）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				System.out.println("获取TOKEN失败("+jsonObject.getInt("errcode")+")："+WeChatErrorCode.ERRORCODE.get(jsonObject.getInt("errcode")));
			}
		}
		return accessToken;
	}

	// 菜单创建（POST）

	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *          
	 * @param accessToken
	 *  有效的access_token
	 * @return 0表示成功
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		
	    // 将菜单对象转换成json字符串  
	    String jsonMenu = JSONObject.fromObject(menu).toString();  
	    
	    System.out.println("这个是组合好的 *******************************"+jsonMenu);
		// 调用接口创建菜单
		System.out.println("url++++"+url);
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				System.out.println("创建菜单失败("+result+")："+WeChatErrorCode.ERRORCODE.get(result));
			}
		}
		return result;
	}
	
	/**
     * 文件上传到微信服务器
     * @param fileType 文件类型
     * @param filePath 文件路径
     * @return JSONObject
     * @throws Exception
     */
    public static String send(String fileType, String filePath,String token) throws Exception {  
        String result = null;  
        File file = new File(filePath);  
        if (!file.exists() || !file.isFile()) {  
            throw new IOException("文件不存在");  
        }  
        /** 
        * 第一部分 
        */  
        URL urlObj = new URL("http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+ token + "&type="+fileType+"");  
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();  
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式  
        con.setDoInput(true);  
        con.setDoOutput(true);  
        con.setUseCaches(false); // post方式不能使用缓存  
        // 设置请求头信息  
        con.setRequestProperty("Connection", "Keep-Alive");  
        con.setRequestProperty("Charset", "UTF-8");  
        // 设置边界  
        String BOUNDARY = "----------" + System.currentTimeMillis();  
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);  
        // 请求正文信息  
        // 第一部分：  
        StringBuilder sb = new StringBuilder();  
        sb.append("--"); // 必须多两道线  
        sb.append(BOUNDARY);  
        sb.append("\r\n");  
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");  
        sb.append("Content-Type:application/octet-stream\r\n\r\n");  
        byte[] head = sb.toString().getBytes("utf-8");  
        // 获得输出流  
        OutputStream out = new DataOutputStream(con.getOutputStream());  
        // 输出表头  
        out.write(head);  
        // 文件正文部分  
        // 把文件已流文件的方式 推入到url中  
        DataInputStream in = new DataInputStream(new FileInputStream(file));  
        int bytes = 0;  
        byte[] bufferOut = new byte[1024];  
        while ((bytes = in.read(bufferOut)) != -1) {  
        out.write(bufferOut, 0, bytes);  
        }  
        in.close();  
        // 结尾部分  
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线  
        out.write(foot);  
        out.flush();  
        out.close();  
        StringBuffer buffer = new StringBuffer();  
        BufferedReader reader = null;  
        try {  
        // 定义BufferedReader输入流来读取URL的响应  
        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));  
        String line = null;  
        while ((line = reader.readLine()) != null) {  
        //System.out.println(line);  
        buffer.append(line);  
        }  
        if(result==null){  
        result = buffer.toString();  
        }  
        } catch (IOException e) {  
        System.out.println("发送POST请求出现异常！" + e);  
        e.printStackTrace();  
        throw new IOException("数据读取异常");  
        } finally {  
        if(reader!=null){  
        reader.close();  
        }  
        }  
//        JSONObject jsonObj =new JSONObject(result);  
        JSONObject jsonObj =JSONObject.fromObject(result);
        System.out.println("上传媒体文件返回的json：*****"+jsonObj);
        String meid=jsonObj.getString("media_id");
        return meid;  
    }
    	
    	//上传图文素材接口地址
    	public final static String  tuwen_sucai= "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
    	/**
    	 *上传图文素材  返回Meid 
    	 * @return
    	 */
    	public static String tuPianSuCai(String token,String result){
    		String requestUrl = tuwen_sucai.replace("ACCESS_TOKEN", token);
    		JSONObject jsonObject = httpRequest(requestUrl, "POST", result);
    		System.out.println("上传图文素材jsonObject反馈的json：********"+jsonObject);
    		String result1="";
			if (jsonObject.containsKey("type")) {
	    		String a=jsonObject.getString("media_id");
	    		return a;
			}else {
				result1="";
			}
			return result1;
    	}
    
    	
    	//获取用户组
    	public final static String yonghu_zu="https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
    	
    	//分组群发url接口
    	public final static String fenzu_url="https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
    	     /**
    	       * 根据分组发送消息
    	       * 
    	       * @param uploadurl
    	       *            apiurl
    	       * @param access_token
    	       * @param data
    	       * @return
    	       */
    	     public static int sendMsg(String token, String data)
    	     {	
    	    	 int result=0;
    	    	 String requestUrl = fenzu_url.replace("ACCESS_TOKEN", token);
    	 	    // 将菜单对象转换成json字符串  
    	 	    String jsonMenu = JSONObject.fromObject(data).toString();  
    	 	    
    	 	    JSONObject jsonObject = httpRequest(requestUrl, "POST", jsonMenu);
    	 	    System.out.println("分组发送图文组装的json：*******"+jsonMenu);
    	 	    System.out.println("分组发送消息返回的json："+jsonObject);
    	 	    
    	 	    
    			if (null != jsonObject) {
    				if (0 != jsonObject.getInt("errcode")) {
    					result = jsonObject.getInt("errcode");
    					System.out.println("发送失败("+result+")："+WeChatErrorCode.ERRORCODE.get(result));
    				}
    			}
    	 	    
    	    	 return result;
    	     }	
    	     
    	          /**
    	           * 获取用户组信息
    	           * 
    	           * @param url
    	           *       
    	           * @param token
    	           *            access_token
    	           * @return id字符串，每个id以,分割
    	           */
    	          public static String yonghu(String token)
    	          {
    	              String groupurl =yonghu_zu.replace("ACCESS_TOKEN", token);
    	              System.out.println("*******组装的用户组URL：******"+groupurl);
    	              HttpClient client = new DefaultHttpClient();
    	              HttpGet get = new HttpGet(groupurl);
    	              String result = null;
    	              try
    	              {
    	                  HttpResponse res = client.execute(get);
    	                  String responseContent = null; // 响应内容
    	                  HttpEntity entity = res.getEntity();
    	                  responseContent = EntityUtils.toString(entity, "UTF-8");
    	                  JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
    	                  JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();// 将json字符串转换为json对象
    	                  System.out.println("******获取用户组得到的json数据：******"+json);
    	                  if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)// 成功返回消息
    	                  {
    	                      if (json.get("errcode") == null)// 不存在错误消息，成功返回
    	                      {
    	                          JsonArray groups = json.getAsJsonArray("groups"); // 返回对象数组
    	                          StringBuffer buffer = new StringBuffer();
    	                          for (int i = 0; i < groups.size(); i++)
    	                          {
    	                              buffer.append(groups.get(i).getAsJsonObject().get("id")
    	                                      .getAsString()
    	                                      + ",");
    	                          }
    	                          result = buffer.toString();
    	                      }
    	                  }
    	              }
    	              catch (Exception e)
    	              {
    	                  e.printStackTrace();
    	              }
    	              finally
    	              { // 关闭连接 ,释放资源
    	                  client.getConnectionManager().shutdown();
    	                  return result;
    	              }
    	          }
	     
}
