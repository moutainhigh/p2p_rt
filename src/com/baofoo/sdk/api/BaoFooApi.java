package com.baofoo.sdk.api;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.baofoo.sdk.demo.base.TransContent;
import com.baofoo.sdk.demo.base.request.TransReqBF0040001;
import com.baofoo.sdk.demo.base.request.TransReqBF0040002;
import com.baofoo.sdk.demo.base.response.TransRespBF0040001;
import com.baofoo.sdk.demo.base.response.TransRespBF0040002;
import com.baofoo.sdk.domain.RequestParams;
import com.baofoo.sdk.http.HttpFormParameter;
import com.baofoo.sdk.http.HttpSendModel;
import com.baofoo.sdk.http.SimpleHttpResponse;
import com.baofoo.sdk.rsa.RsaCodingUtil;
import com.baofoo.sdk.util.BaofooClient;
import com.baofoo.sdk.util.TransConstant;
import com.google.common.collect.Lists;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserBank;
import com.rbao.east.pay.PayUtils;
import com.rbao.east.utils.PropertiesUtil;
import com.rbao.east.web.bf.util.HttpUtil;
import com.rbao.east.web.bf.util.JXMConvertUtil;
import com.rbao.east.web.bf.util.MapToXMLString;
import com.rbao.east.web.bf.util.SecurityUtil;
import com.rbao.east.web.bf.vo.PaymentInfoBF;
import com.thoughtworks.xstream.XStream;

import net.sf.json.JSONObject;

public class BaoFooApi {
	
	/**
	 * 查询充值订单状态
	 * @param TransID 商户订单号
	 */
	public Map<String, String> CERQuery(String TransID) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
		Map<String, String> result = new HashMap<>();
		
		//先查询互联网订单
		if (query(TransID)) {
			System.out.println("互联网支付成功");
			result.put("resp_code", "0000");
			result.put("retMsg", "成功！");
			return result;
		}
		
		String  pfxpath = BaoFooApi.class.getResource("/").getPath() + PropertiesUtil.get("BF.app.keyStorePath");;//商户私钥
		File pfxfile=new File(pfxpath);
		if(!pfxfile.exists()){
			System.out.println("私钥文件不存在！");
			result.put("resp_code", "999");
			result.put("retMsg", "私钥文件不存在！");
			return result;
		}

		String cerpath = BaoFooApi.class.getResource("/").getPath() + PropertiesUtil.get("BF.app.pubKey");;//宝付公钥
		File cerfile=new File(cerpath);
		if(!cerfile.exists()){//判断宝付公钥是否为空
			System.out.println("宝付公钥文件不存在！");
			log("=====公钥文件不存在！======");
			result.put("resp_code", "999");
			result.put("resp_msg", "公钥文件不存在！");
			return result;
		}

		String trans_id="TSN"+System.currentTimeMillis();//商户订单号
		//=================================================
			String version = "4.0.0.1";//版本号
			String terminal_id = PropertiesUtil.get("BF.app.terminalId");	//商户号
			String member_id = PropertiesUtil.get("BF.app.merchantId");	//终端号
			String pfxpwd  = PropertiesUtil.get("BF.app.keyStorePassword");//商户私钥证书密码		
		//======================FORM===========================
			String input_charset = "1";
			String language = "1";
			String data_type=PropertiesUtil.get("BF.app.dataType"); //加密报文的数据类型（xml/json）
		//=====================================================
			String request_url = PropertiesUtil.get("BF.app.queryUrl");//请求地址
		
		Map<String,String> ArrayData = new HashMap<String,String>();
		 ArrayData.put("orig_trans_id", TransID);
		 ArrayData.put("trans_serial_no", trans_id);
		 ArrayData.put("terminal_id", terminal_id);
		 ArrayData.put("member_id", member_id);
		 ArrayData.put("additional_info", "附加信息");
		 ArrayData.put("req_reserved", "保留");
		 
		 Map<Object,Object> ArrayData1 = new HashMap<Object,Object>();
		 		 
		 String XmlOrJson = "";
		 if(data_type.equals("xml")){
			 ArrayData1.putAll(ArrayData); 			 
			 XmlOrJson = MapToXMLString.converter(ArrayData1,"data_content");
		 }else{
			 JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
			 XmlOrJson = jsonObjectFromMap.toString();
		 }
		 log("====请求明文:"+XmlOrJson);
		 String base64str = SecurityUtil.Base64Encode(XmlOrJson);
		 String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str,pfxpath,pfxpwd);		 
		 
		 Map<String,String> Post= new HashMap<String,String>();
		 
		 Post.put("version",version);
		 Post.put("input_charset", input_charset);
		 Post.put("language", language);
		 Post.put("terminal_id", terminal_id);
		 Post.put("member_id", member_id);
		 Post.put("data_type", data_type);
		 Post.put("data_content", data_content);
		 
		 log("====请求表单:"+Post.toString());		 
		 String PostString  = HttpUtil.RequestForm(request_url, Post);

		 log("====同步返回报文："+PostString);		 
		 
		 PostString = RsaCodingUtil.decryptByPubCerFile(PostString,cerpath);
		 
		 if(PostString.isEmpty()){
			result.put("resp_code", "999");
			result.put("resp_msg", "返回空报文！");
			return result;
		 }		 
		 PostString = SecurityUtil.Base64Decode(PostString);
		 log("=====返回查询数据解密结果:"+PostString);
		
		if(data_type.equals("xml")){
					PostString = JXMConvertUtil.XmlConvertJson(PostString);		    
					log("=====返回结果转JSON:"+PostString);
		}
			
		Map<String,String> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);//将JSON转化为Map对象。
		log("转换为MAP对象："+ArrayDataString);
		String retCode = ArrayDataString.get("resp_code").toString();
		
		//根据返回参数 商户记录自已的订单
		
		if(retCode.equals("0000")){
			return ArrayDataString;
		}		 
		return ArrayDataString;
	}
	
	/**
	 * 查询充值订单状态（互联网支付）
	 * @param TransID 商户订单号
	 */
	public boolean query(String TransID){
		try {
			String queryUrl = PropertiesUtil.get("BF.queryUrl");
			String memberId = PropertiesUtil.get("BF.merchantId");
			String terminalId = PropertiesUtil.get("BF.terminalId");
			String md5Sign = PayUtils.getMD5ofStr(memberId + "|" + terminalId + "|" + TransID + "|" + PropertiesUtil.get("BF.encryptKey"));
			
			HttpSendModel httpSendModel = new HttpSendModel(queryUrl);
			httpSendModel.setParams(Lists.newArrayList(new HttpFormParameter("MemberID", memberId)
					, new HttpFormParameter("TerminalID", terminalId)
					, new HttpFormParameter("TransID", TransID)
					, new HttpFormParameter("MD5Sign", md5Sign)));
			SimpleHttpResponse response = com.baofoo.sdk.util.HttpUtil.doRequest(httpSendModel, "UTF-8");	
			
			String retCode = response.getEntityString().split("\\|")[3];
			
			return "Y".equalsIgnoreCase(retCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 代付交易接口(BF0040001)
	 * @param transReqData
	 * @return
	 * @throws Exception
	 */
	public static TransContent<TransRespBF0040001> BF004001(TransReqBF0040001 transReqData) throws Exception {
		String dataType = TransConstant.data_type_xml; // 数据类型 xml/json

		TransContent<TransReqBF0040001> transContent = new TransContent<TransReqBF0040001>(
				dataType);

		List<TransReqBF0040001> trans_reqDatas = new ArrayList<TransReqBF0040001>();

		trans_reqDatas.add(transReqData);

		transContent.setTrans_reqDatas(trans_reqDatas);

		String bean2XmlString = transContent.obj2Str(transContent);
		System.out.println("报文：" + bean2XmlString);

		String keyStorePath = BaoFooApi.class.getResource("/").getPath() + PropertiesUtil.get("BF.cash.keyStorePath");
		String keyStorePassword = PropertiesUtil.get("BF.cash.keyStorePassword");
		String pubKey = BaoFooApi.class.getResource("/").getPath() + PropertiesUtil.get("BF.cash.pubKey");
		String origData = bean2XmlString;
		origData =  new String(Base64.encodeBase64(origData.getBytes()));//Base64.encode(origData);
		String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
				keyStorePath, keyStorePassword);

		System.out.println("----------->【私钥加密-结果】" + encryptData);

		// 发送请求
		RequestParams requestParams = new RequestParams();
		requestParams.setMemberId(Integer.parseInt(PropertiesUtil.get("BF.cash.memberId")));
		requestParams.setTerminalId(Integer.parseInt(PropertiesUtil.get("BF.cash.terminalId")));
		requestParams.setDataType(dataType);
		requestParams.setDataContent(encryptData);// 加密后数据
		requestParams.setVersion(PropertiesUtil.get("BF.cash.version"));
		requestParams.setRequestUrl(PropertiesUtil.get("BF.cash.URL"));
		SimpleHttpResponse smpleHttpResponse = BaofooClient.doRequest(requestParams);

		System.out.println("宝付请求返回结果：" + smpleHttpResponse.getEntityString());

		TransContent<TransRespBF0040001> str2Obj = new TransContent<TransRespBF0040001>(
				dataType);
		XStream xStream = str2Obj.getxStream();

		String reslut = smpleHttpResponse.getEntityString();
		if (reslut.contains("trans_content")) {
			//明文返回
			str2Obj = (TransContent<TransRespBF0040001>) str2Obj.str2Obj(
					reslut, TransRespBF0040001.class);
			// 业务逻辑判断
		} else {
			//密文返回
			//第一步：公钥解密
			reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pubKey);
			//第二步BASE64解密
			reslut = new String(new Base64().decode(reslut));
			System.out.println(reslut);
			str2Obj = (TransContent<TransRespBF0040001>) str2Obj.str2Obj(
					reslut, TransRespBF0040001.class);
			// 业务逻辑判断
		}
		str2Obj.setxStream(xStream);
		
		return str2Obj;
	}

	/**
	 * 代付交易状态查证接口(BF0040002)
	 * @param transReqData
	 * @return
	 * @throws Exception
	 */
	public static TransContent<TransRespBF0040002> BF004002(String noOrder) throws Exception {
		TransContent<TransReqBF0040002> transContent = new TransContent<TransReqBF0040002>(
				TransConstant.data_type_xml);

		List<TransReqBF0040002> trans_reqDatas = new ArrayList<TransReqBF0040002>();

		TransReqBF0040002 transReqData = new TransReqBF0040002();
		transReqData.setTrans_batchid("20218703");
		transReqData.setTrans_no("1ABCDEF34");

		trans_reqDatas.add(transReqData);

		TransReqBF0040002 transReqData2 = new TransReqBF0040002();

		transReqData2.setTrans_batchid("");
		transReqData2.setTrans_no(noOrder);
		trans_reqDatas.add(transReqData2);

		transContent.setTrans_reqDatas(trans_reqDatas);

		String bean2XmlString = transContent.obj2Str(transContent);
		System.out.println("报文：" + bean2XmlString);

		String keyStorePath = BaoFooApi.class.getResource("/").getPath() + PropertiesUtil.get("BF.cash.keyStorePath");
		String keyStorePassword = PropertiesUtil.get("BF.cash.keyStorePassword");
		String pubKey = BaoFooApi.class.getResource("/").getPath() + PropertiesUtil.get("BF.cash.pubKey");
		String origData = bean2XmlString;
		//origData = Base64.encode(origData);
		/**
		 * 加密规则：项目编码UTF-8 
		 * 第一步：BASE64 加密
		 * 第二步：商户私钥加密
		 */
		origData =  new String(Base64.encodeBase64(origData.getBytes()));//Base64.encode(origData);
		String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
				keyStorePath, keyStorePassword);

		System.out.println("----------->【私钥加密-结果】" + encryptData);

		// 发送请求
		String requestUrl = PropertiesUtil.get("BF.cash.URL2");
		String version = PropertiesUtil.get("BF.cash.version");
		String memberId = PropertiesUtil.get("BF.cash.memberId"); // 商户号
		String terminalId = PropertiesUtil.get("BF.cash.terminalId"); // 终端号
		String dataType = TransConstant.data_type_xml; // 数据类型 xml/json

		RequestParams params = new RequestParams();
		params.setMemberId(Integer.parseInt(memberId));
		params.setTerminalId(Integer.parseInt(terminalId));
		params.setDataType(dataType);
		params.setDataContent(encryptData);// 加密后数据
		params.setVersion(version);
		params.setRequestUrl(requestUrl);
		SimpleHttpResponse response = BaofooClient.doRequest(params);

		System.out.println("宝付请求返回结果：" + response.getEntityString());

		TransContent<TransRespBF0040002> str2Obj = new TransContent<TransRespBF0040002>(dataType);

		String reslut = response.getEntityString();
		
		/**
		 * 在商户终端正常的情况下宝付同步返回会以密文形式返回,如下：
		 * 
		 * 此时要先宝付提供的公钥解密：RsaCodingUtil.decryptByPubCerFile(reslut, pub_key)
		 * 
		 * 再次通过BASE64解密：new String(new Base64().decode(reslut))
		 * 
		 * 在商户终端不正常或宝付代付系统异常的情况下宝付同步返回会以明文形式返回
		 */
		System.out.println(reslut);
		if (reslut.contains("trans_content")) {
			// 我报文错误处理
			str2Obj = (TransContent<TransRespBF0040002>) str2Obj
					.str2Obj(reslut,TransRespBF0040002.class);
			//业务逻辑判断
		} else {
			reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pubKey);
			reslut = new String(new Base64().decode(reslut));
			str2Obj = (TransContent<TransRespBF0040002>) str2Obj
					.str2Obj(reslut,TransRespBF0040002.class);
			//业务逻辑判断
		}
		
		return str2Obj;
	}
	

	
	//PC请求方法
	public String PcFrom(HttpServletRequest request, HttpServletResponse response, String trans_id, UserBank userBank, User user
			, AccountRecharge accountRecharge, String basePath) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
		
		String  acc_no = userBank.getBankAccount();//银行卡号
		String  pay_code = userBank.getAllBank().getBankCode();//银行编码
		String  id_holder = user.getUserRealname();//姓名
		String  id_card = user.getCardNumber();//身份证号		
		if(accountRecharge.getRechargeMoney() == null){
			System.out.println("金额不能为空！");
			return "金额不能为空！";
		}
		BigDecimal  txn_amt_num = accountRecharge.getRechargeMoney().multiply(BigDecimal.valueOf(100));//金额转换成分		
		String  txn_amt = String.valueOf(txn_amt_num.setScale(0));//支付金额
		
		String  pfxpath = BaoFooApi.class.getResource("/").getPath() + PropertiesUtil.get("BF.pc.keyStorePath");;//商户私钥
		File pfxfile=new File(pfxpath);
		if(!pfxfile.exists()){
			System.out.println("私钥文件不存在！");
			return "私钥文件不存在！";
		}
		
		String trade_date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//交易日期
//		String trans_id="TID"+System.currentTimeMillis();//商户订单号
		//=================================================
			String version = "4.0.0.0";//版本号
			String terminal_id = PropertiesUtil.get("BF.pc.terminalId");	//商户号
			String member_id = PropertiesUtil.get("BF.pc.merchantId");	//终端号
			String pfxpwd  = PropertiesUtil.get("BF.pc.keyStorePassword");//商户私钥证书密码			
			String txn_type = "03311";
			
		//======================FORM===========================
			String input_charset = "1";
			String language = "1";
			String txn_sub_type ="03"; //交易子类
			String data_type=PropertiesUtil.get("BF.pc.dataType"); //加密报文的数据类型（xml/json）
		//=====================================================
		String back_url = basePath + PropertiesUtil.get("BF.pc.backUrl");//商户改成自已的地址。返回方法在backurl内请参考	
		String return_url = basePath + PropertiesUtil.get("BF.pc.returnUrl");//商户改成自已的地址。返回方法在returnurl内请参考	
		String request_url = PropertiesUtil.get("BF.pc.requestUrl");//请求地址
		
		Map<String,String> ArrayData = new HashMap<String,String>();

		 ArrayData.put("txn_sub_type", txn_sub_type);
		 ArrayData.put("biz_type", "0000");
		 ArrayData.put("terminal_id", terminal_id);
		 ArrayData.put("member_id", member_id);
		 ArrayData.put("pay_code", pay_code);
		 ArrayData.put("acc_no", acc_no);
		 ArrayData.put("id_card_type", "01");
		 ArrayData.put("id_card", id_card);
		 ArrayData.put("id_holder", id_holder);
		 ArrayData.put("valid_date", "");//暂不支持信用卡（传空）
		 ArrayData.put("valid_no", "");//暂不支持信用卡（传空）
		 ArrayData.put("trans_id", trans_id);
		 ArrayData.put("txn_amt", txn_amt);
		 ArrayData.put("trade_date", trade_date);
		 ArrayData.put("commodity_name", "商品名称");
		 ArrayData.put("commodity_amount", "1");//商品数量（默认为1）
		 ArrayData.put("user_name", "用户名称");
		 ArrayData.put("page_url", back_url);
		 ArrayData.put("return_url", return_url);
		 ArrayData.put("additional_info", user.getId() + "");
		 ArrayData.put("req_reserved", "保留");
		 Map<Object,Object> ArrayData1 = new HashMap<Object,Object>();
		 		 
		 String XmlOrJson = "";
		 if(data_type.equals("xml")){
			 ArrayData1.putAll(ArrayData); 			 
			 XmlOrJson = MapToXMLString.converter(ArrayData1,"data_content");
		 }else{
			 JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
			 XmlOrJson = jsonObjectFromMap.toString();
		 }
		 System.out.println("====请求明文:"+XmlOrJson);
		 String base64str = SecurityUtil.Base64Encode(XmlOrJson);
		 String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str,pfxpath,pfxpwd);	
		 
		String FormString ="正在跳转到支付界面..."
				 +"<body onload=\"document.pay.submit()\"><form id=\"pay\" name=\"pay\" action=\"" + request_url + "\" method=\"post\">"
				 +"<input name=\"version\" type=\"hidden\" id=\"version\" value=\""+version+"\" />"
				 +"<input name=\"input_charset\" type=\"hidden\" id=\"input_charset\" value=\""+input_charset+"\" />"
				 +"<input name=\"language\" type=\"hidden\" id=\"language\" value=\""+language+"\" />"
				 +"<input name=\"terminal_id\" type=\"hidden\" id=\"terminal_id\" value=\""+terminal_id+"\" />"
				 +"<input name=\"txn_type\" type=\"hidden\" id=\"txn_type\" value=\""+txn_type+"\" />"
				 +"<input name=\"txn_sub_type\" type=\"hidden\" id=\"txn_sub_type\" value=\""+txn_sub_type+"\" />"
				 +"<input name=\"member_id\" type=\"hidden\" id=\"member_id\" value=\""+member_id+"\" />"
				 +"<input name=\"data_type\" type=\"hidden\" id=\"data_type\" value=\""+data_type+"\" />"
				 +"<textarea name=\"data_content\" style=\"display:none;\" id=\"data_content\">"+data_content+"</textarea>"
				 +"<input name=\"back_url\" type=\"hidden\" id=\"back_url\" value=\"" + back_url + "\" />"
				 +"</form></body>";
		log("====请求表单:" + XmlOrJson);
		
		return FormString;
	}
	
	//SDK请求方法
	public static Map<String, String> SdkFrom(HttpServletRequest request,HttpServletResponse response, String trans_id, UserBank userBank, User user
			, PaymentInfoBF paymentInfo, String basePath) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
		Map<String, String> result = new HashMap<>();
		result.put("retCode", "999");
		
		String  acc_no = userBank.getBankAccount();//银行卡号
		String  pay_code = userBank.getAllBank().getBankCode();//银行编码
		String  id_holder = user.getUserRealname();//姓名
		String  mobile = paymentInfo.getMobile();//手机号
		String  id_card = user.getCardNumber();//身份证号		
		if(paymentInfo.getMoney_order().isEmpty()){
			result.put("retMsg", "金额不能为空！");
			return result;
		}
		BigDecimal  txn_amt_num = new BigDecimal(paymentInfo.getMoney_order()).multiply(BigDecimal.valueOf(100));//金额转换成分		
		String  txn_amt = String.valueOf(txn_amt_num.setScale(0));//支付金额
		
		String  pfxpath = BaoFooApi.class.getResource("/").getPath() + PropertiesUtil.get("BF.app.keyStorePath");;//商户私钥
		File pfxfile=new File(pfxpath);
		if(!pfxfile.exists()){
			System.out.println("私钥文件不存在！");
			result.put("retMsg", "私钥文件不存在！");
			return result;
		}
		
		String trade_date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//交易日期
//		String trans_id="TID"+System.currentTimeMillis();//商户订单号
		//=================================================
			String version = "4.0.0.0";//版本号
			String terminal_id = PropertiesUtil.get("BF.app.terminalId");	//商户号
			String member_id = PropertiesUtil.get("BF.app.merchantId");	//终端号
			String pfxpwd  = PropertiesUtil.get("BF.app.keyStorePassword");//商户私钥证书密码			
			String txn_type = "03311";
			
		//======================FORM===========================
			String input_charset = "1";
			String language = "1";
			String txn_sub_type ="02"; //交易子类
			String data_type=PropertiesUtil.get("BF.app.dataType"); //加密报文的数据类型（xml/json）
		//=====================================================
		String return_url = basePath + PropertiesUtil.get("BF.app.returnUrl");//商户改成自已的地址。返回方法在returnurl内请参考	
		String request_url = PropertiesUtil.get("BF.app.requestUrl");//请求地址
		
		Map<String,String> ArrayData = new HashMap<String,String>();

		 ArrayData.put("txn_sub_type", txn_sub_type);
		 ArrayData.put("biz_type", "0000");
		 ArrayData.put("terminal_id", terminal_id);
		 ArrayData.put("member_id", member_id);
		 ArrayData.put("pay_code", pay_code);
		 ArrayData.put("acc_no", acc_no);
		 ArrayData.put("id_card_type", "01");
		 ArrayData.put("id_card", id_card);
		 ArrayData.put("id_holder", id_holder);
		 if (!StringUtils.isEmpty(mobile)) {
			 ArrayData.put("mobile", mobile);
		 }
		 ArrayData.put("valid_date", "");//暂不支持信用卡（传空）
		 ArrayData.put("valid_no", "");//暂不支持信用卡（传空）
		 ArrayData.put("trans_id", trans_id);
		 ArrayData.put("txn_amt", txn_amt);
		 ArrayData.put("trade_date", trade_date);
		 ArrayData.put("commodity_name", "商品名称");
		 ArrayData.put("commodity_amount", "1");//商品数量（默认为1）
		 ArrayData.put("user_name", "用户名称");
		 ArrayData.put("return_url", return_url);
		 ArrayData.put("additional_info", user.getId() + "");
		 ArrayData.put("req_reserved", "保留");
		 Map<Object,Object> ArrayData1 = new HashMap<Object,Object>();
		 		 
		 String XmlOrJson = "";
		 if(data_type.equals("xml")){
			 ArrayData1.putAll(ArrayData); 			 
			 XmlOrJson = MapToXMLString.converter(ArrayData1,"data_content");
		 }else{
			 JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
			 XmlOrJson = jsonObjectFromMap.toString();
		 }
		 System.out.println("====请求明文:"+XmlOrJson);
		 String base64str = SecurityUtil.Base64Encode(XmlOrJson);
		 String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str,pfxpath,pfxpwd);		 
		 
		 Map<String,String> Post= new HashMap<String,String>();
		 
		 Post.put("version",version);
		 Post.put("input_charset", input_charset);
		 Post.put("language", language);
		 Post.put("terminal_id", terminal_id);
		 Post.put("txn_type", txn_type);
		 Post.put("txn_sub_type", txn_sub_type);
		 Post.put("member_id", member_id);
		 Post.put("data_type", data_type);
		 Post.put("data_content", data_content);
		 
		 System.out.println("====请求表单:"+Post.toString());		 
		 String PostString  = HttpUtil.RequestForm(request_url, Post);		
		 System.out.println("====同步返回报文："+PostString);
		 if(PostString.isEmpty()){
			result.put("retMsg", "私钥文件不存在！返回空报文！");
			return result;
		 }
		 
		 System.out.println("====请求结果：" + PostString);
		 Map<String,String> ArrayDataString = JXMConvertUtil.JsonConvertHashMap((Object)PostString);//将JSON转化为Map对象。
		 String retCode = ArrayDataString.get("retCode");
		
		//根据返回参数 商户记录自已的订单
		
		if(retCode.equals("0000")){
			return ArrayDataString;
		}
		 
		return ArrayDataString;
	}
	
	//SDK回调方法
	public Map<String, String> SdkReturnurl(HttpServletRequest request,HttpServletResponse response)throws IOException{
		Map<String, String> result = new HashMap<>();
		result.put("resp_code", "999");
		
		String data_content = request.getParameter("data_content");//回调参数
		if(data_content.isEmpty()){//判断参数是否为空
			log("=====返回数据为空");
			result.put("resp_code", "999");
			result.put("message", "返回数据为空");
			return result;
		}
		log("=====返回数据:"+data_content);
		String cerpath = BaoFooApi.class.getResource("/").getPath() + PropertiesUtil.get("BF.app.pubKey");;//宝付公钥
		String data_type=PropertiesUtil.get("BF.app.dataType"); //加密报文的数据类型（xml/json）
		
		File cerfile=new File(cerpath);
		if(!cerfile.exists()){//判断宝付公钥是否为空
			System.out.println("宝付公钥文件不存在！");
			log("=====公钥文件不存在！======");
			result.put("resp_code", "999");
			result.put("resp_msg", "公钥文件不存在！");
			return result;
		}
		
		 data_content = RsaCodingUtil.decryptByPubCerFile(data_content,cerpath);
		 if(data_content.isEmpty()){//判断解密是否正确。如果为空则宝付公钥不正确
				log("=====检查解密公钥是否正确！");
				result.put("resp_code", "999");
				result.put("resp_msg", "检查解密公钥是否正确！");
				return result;
		 }
		 data_content = SecurityUtil.Base64Decode(data_content);		 
		 log("=====返回数据解密结果:"+data_content);
		 
		if(data_type.equals("xml")){
			data_content = JXMConvertUtil.XmlConvertJson(data_content);		    
			log("=====返回结果转JSON:"+data_content);
		}
		
		Map<String,String> ArrayData = JXMConvertUtil.JsonConvertHashMap((Object)data_content);//将JSON转化为Map对象。
		String resp_code = ArrayData.get("resp_code");
		
		//这里根据ArrayData 对象里的值去写本地服务器端数据
		//商户自行写入自已的数据。。。。。。。。
		if(resp_code.equals("0000")){
			return ArrayData;
		}

		return ArrayData;
	}
	
	private void log(String msg) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\t: " + msg);
	}
	
	public static void main(String[] argv) {
		try {
			new BaoFooApi().CERQuery("E1714820160402124341755443");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
