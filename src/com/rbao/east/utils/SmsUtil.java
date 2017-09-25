
package com.rbao.east.utils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.xml.rpc.ServiceException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.SysSmsConfig;

public class SmsUtil {
	private static Logger logger = LoggerFactory .getLogger(SmsUtil.class);	
	
	private static String baseurl = ""; // sms url
	
	private static String password = "";// sms password
	
	private static String sn = "";
	
	static {
		baseurl = PropertiesUtil.get("sms.baseurl");
		password = PropertiesUtil.get("sms.password");
		sn = PropertiesUtil.get("sms.userName");
		System.out.println(baseurl+password+sn);
	}

	public static Integer send(MessageCenter message){
		String phone=message.getMessageAddress();
		String content=message.getMessageContent();
		SysSmsConfig sms=SysCacheUtils.getSysSmsConfig();
		Integer ret=new Integer(-1);
		try {
			if(ret !=0){
			
				System.err.println("register sms failed["+ret+"]-->"+phone+"-->"+content);
				logger.error("register sms failed["+ret+"]-->"+phone+"-->"+content);
			}
			ret = sendSmsEtonenet(sms.getSysSmsAccount(), sms.getSysSmsPassword(), phone, content);
			if(ret != 0){
				System.err.println("send sms failed["+ret+"]-->"+phone+"-->"+content);
				logger.error("send sms failed["+ret+"]-->"+phone+"-->"+content);
			}
			
			System.out.println("content--------------"+content);
		} catch (Exception e) {
			logger.error("send sms -->"+phone+"-->"+content,e);
			e.printStackTrace();
			
		} 
		System.out.println(ret);
		return ret;
	}
	
	public static int sendSms(String phone, String content) {
		int value=-1;
		try {
	        logger.error("before send sms: " + phone + " " + content);
			value = sendSmsEtonenet(sn, password, phone , content);
			System.out.println(value);
			logger.error("send sms [" + value + "]-->" + phone + "-->"
					+ content);
		} catch (Exception e) {
			logger.error("send sms -->" + phone + "-->" + content, e);
			e.printStackTrace();
		}
		return value;
	}

    /**
     * 单条下行实例
     * @throws Exception
     */
    public static int sendSmsEtonenet(String spid, String sppassword, String phone, String content) throws Exception {
        //操作命令、SP编号、SP密码，必填参数
        String command = "MT_REQUEST";
        //sp服务代码，可选参数，默认为 00
        String spsc = "00";
        //源号码，可选参数
        String sa = "";
        //目标号码，必填参数
        String da = phone;
        //下行内容以及编码格式，必填参数
        int dc = 15;
        String ecodeform = "GBK";
        String sm = new String(Hex.encodeHex(content.getBytes(ecodeform)));
        
        if (!da.startsWith("86")) {
        	da = "86" + da;
        }

        //组成url字符串
        StringBuilder smsUrl = new StringBuilder();
        smsUrl.append(baseurl);
        smsUrl.append("?command=" + command);
        smsUrl.append("&spid=" + spid);
        smsUrl.append("&sppassword=" + sppassword);
        smsUrl.append("&spsc=" + spsc);
        smsUrl.append("&sa=" + sa);
        smsUrl.append("&da=" + da);
        smsUrl.append("&sm=" + sm);
        smsUrl.append("&dc=" + dc);

        //发送http请求，并接收http响应
        String resStr = doGetRequest(smsUrl.toString());
        logger.error("send sms response: " + resStr);
        
        //解析响应字符串
        HashMap<String,String> pp = parseResStr(resStr);
        System.out.println(pp.get("command"));
        System.out.println(pp.get("spid"));
        System.out.println(pp.get("mtmsgid"));
        System.out.println(pp.get("mtstat"));
        System.out.println(pp.get("mterrcode"));
        
        return Integer.parseInt(pp.get("mterrcode"));
    }

    /**
     * 发送http GET请求，并返回http响应字符串
     * 
     * @param urlstr 完整的请求url字符串
     * @return
     */
    public static String doGetRequest(String urlstr) {
        String res = null;
        HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
        client.getParams().setIntParameter("http.socket.timeout", 10000);
        client.getParams().setIntParameter("http.connection.timeout", 5000);

        HttpMethod httpmethod = new GetMethod(urlstr);
        try {
            int statusCode = client.executeMethod(httpmethod);
            if (statusCode == HttpStatus.SC_OK) {
                res = httpmethod.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpmethod.releaseConnection();
        }
        return res;
    }
    
    /**
     * 将 短信下行 请求响应字符串解析到一个HashMap中
     * @param resStr
     * @return
     */
    public static HashMap<String,String> parseResStr(String resStr) {
        HashMap<String,String> pp = new HashMap<String,String>();
        try {
            String[] ps = resStr.split("&");
            for(int i=0;i<ps.length;i++){
                int ix = ps[i].indexOf("=");
                if(ix!=-1){
                   pp.put(ps[i].substring(0,ix),ps[i].substring(ix+1)); 
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return pp;
    }
	
	public static void main(String[] args) throws ServiceException, RemoteException {
		sendSms("15900636884","ff");	
	}
}
