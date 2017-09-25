package com.baofoo.sdk.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.baofoo.sdk.demo.base.TransContent;
import com.baofoo.sdk.demo.base.request.TransReqBF0040001;
import com.baofoo.sdk.demo.base.response.TransRespBF0040001;
import com.baofoo.sdk.domain.RequestParams;
import com.baofoo.sdk.http.SimpleHttpResponse;
import com.baofoo.sdk.rsa.RsaCodingUtil;
import com.baofoo.sdk.util.BaofooClient;
import com.baofoo.sdk.util.TransConstant;
public class BF0040001SDKDemo {

	/**
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String dataType = TransConstant.data_type_xml; // 数据类型 xml/json

		TransContent<TransReqBF0040001> transContent = new TransContent<TransReqBF0040001>(
				dataType);

		List<TransReqBF0040001> trans_reqDatas = new ArrayList<TransReqBF0040001>();

		TransReqBF0040001 transReqData = new TransReqBF0040001();
		transReqData.setTrans_no("9ABCDEFG17F");
		transReqData.setTrans_money("1");
		transReqData.setTo_acc_name("测试账号");
		transReqData.setTo_acc_no("666666666");
		transReqData.setTo_bank_name("中国工商银行");
		transReqData.setTo_pro_name("上海市");
		transReqData.setTo_city_name("上海市");
		transReqData.setTo_acc_dept("支行");
		transReqData.setTrans_summary("备注1");
		trans_reqDatas.add(transReqData);

		TransReqBF0040001 transReqData2 = new TransReqBF0040001();
		transReqData2.setTrans_no("9ABCDEF28GG");
		transReqData2.setTrans_money("1");
		transReqData2.setTo_acc_name("测试账号");
		transReqData2.setTo_acc_no("666666666");
		transReqData2.setTo_bank_name("中国工商银行");
		transReqData2.setTo_pro_name("上海市");
		transReqData2.setTo_city_name("上海市");
		transReqData2.setTo_acc_dept("支行");
		transReqData2.setTrans_summary("备注2");
		trans_reqDatas.add(transReqData2);

		transContent.setTrans_reqDatas(trans_reqDatas);

		String bean2XmlString = transContent.obj2Str(transContent);
		System.out.println("报文：" + bean2XmlString);

		String keyStorePath = "d:\\m_pri.pfx";
		String keyStorePassword = "123456";
		String pub_key = "d:\\baofoo_pub.cer";
		String origData = bean2XmlString;
		
		/**
		 * 加密规则：项目编码UTF-8 
		 * 第一步：BASE64 加密
		 * 第二步：商户私钥加密
		 */
		origData =  new String(new Base64().encodeBase64(origData.getBytes()));//Base64.encode(origData);
		String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
				keyStorePath, keyStorePassword);

		System.out.println("----------->【私钥加密-结果】" + encryptData);

		// 发送请求
		String requestUrl = "http://paytest.baofoo.com/baofoo-fopay/pay/BF0040001.do";
		String memberId = "100000178"; // 商户号
		String terminalId = "100000859"; // 终端号

		RequestParams params = new RequestParams();
		params.setMemberId(Integer.parseInt(memberId));
		params.setTerminalId(Integer.parseInt(terminalId));
		params.setDataType(dataType);
		params.setDataContent(encryptData);// 加密后数据
		params.setVersion("4.0.0");
		params.setRequestUrl(requestUrl);
		SimpleHttpResponse response = BaofooClient.doRequest(params);

		System.out.println("宝付请求返回结果：" + response.getEntityString());

		TransContent<TransRespBF0040001> str2Obj = new TransContent<TransRespBF0040001>(
				dataType);
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
		//明文返回处理可能是报文头参数不正确、或其他的异常导致；
		if (reslut.contains("trans_content")) {
			//明文返回
			//我报文错误处理
			str2Obj = (TransContent<TransRespBF0040001>) str2Obj.str2Obj(
					reslut, TransRespBF0040001.class);
			// 业务逻辑判断
		} else {
			//密文返回
			//第一步：公钥解密
			reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pub_key);
			//第二步BASE64解密
			reslut = new String(new Base64().decode(reslut));
			System.out.println(reslut);
			str2Obj = (TransContent<TransRespBF0040001>) str2Obj.str2Obj(
					reslut, TransRespBF0040001.class);
			// 业务逻辑判断
		}
	}

}
