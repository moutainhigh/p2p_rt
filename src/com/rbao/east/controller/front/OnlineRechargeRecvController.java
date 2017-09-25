package com.rbao.east.controller.front;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;








import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.alibaba.fastjson.JSON;
import com.baofoo.sdk.api.BaoFooApi;
import com.hnapay.gateway.client.enums.CharsetTypeEnum;
import com.hnapay.gateway.client.java.ClientSignature;
import com.mypay.merchantutil.Md5Encrypt;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.PaymentConfig;
import com.rbao.east.lianlianpayutil.LLPayUtil;
import com.rbao.east.pay.EpayMD5;
import com.rbao.east.pay.PayUtils;
import com.rbao.east.pay.PaymentForOnlineService;
import com.rbao.east.pay.entity.OnlineRechargeChinaPay;
import com.rbao.east.pay.entity.OnlineRechargeGFB;
import com.rbao.east.pay.entity.OnlineRechargeHC;
import com.rbao.east.pay.entity.RetBean;
import com.rbao.east.service.AccountRechargeService;
import com.rbao.east.service.PaymentConfigService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.PropertiesUtil;
import com.rbao.east.utils.ResponseUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.SysCacheUtils;
import com.sumavision.sumapay.util.SignatureUtil;

/**
 * 在线支付回调类
 * @author 
 *
 */
@Controller
@RequestMapping("/user/onlineRecharge/")
public class OnlineRechargeRecvController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(OnlineRechargeRecvController.class);
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private PaymentConfigService paymentConfigService;
	
	@Autowired
	private AccountRechargeService accountRechargeService;
	/**
	 * 连连支付充值回调接口
	 * @param request
	 * @param response
	 */
	public static void main(String[] args) {
		String aa = "{&quot;dt_order&quot;：&quot;20151109161947&quot;,&quot;money_order&quot;：&quot;0.01&quot;,&quot;no_order&quot;：&quot;E142920151109161945520531&quot;,&quot;oid_partner&quot;：&quot;201408071000001543&quot;,&quot;oid_paybill&quot;：&quot;2015110926224725&quot;,&quot;result_pay&quot;：&quot;SUCCESS&quot;,&quot;settle_date&quot;：&quot;20151109&quot;,&quot;sign&quot;：&quot;a2d391f558fb56689efec0655deb7c25&quot;,&quot;sign_type&quot;：&quot;MD5&quot;}";
		aa=aa.replaceAll("&quot;", "\"").replaceAll("：", ":");
		System.out.println(aa);
		JSONObject json = JSONObject.fromObject(aa);
		String no_order = json.getString("no_order");
		System.out.println(no_order);
	}
	@RequestMapping("recvFromLianLian.do")
	public void recvFromLianLian(HttpServletRequest request, HttpServletResponse response){
		
	        String reqStr = LLPayUtil.readReqStr(request);
	        JSONObject json = JSONObject.fromObject(reqStr);
			String no_order = json.getString("no_order");
			String money_order = json.getString("money_order");
			if(json.getString("result_pay").equals("SUCCESS")){
				RetBean retBean = new RetBean();
				AccountRecharge accountRecharge = new AccountRecharge();
				accountRecharge.setRechargeTradeNo(no_order);
				accountRecharge.setRechargeAddip(this.getIpAddr(request));
				accountRecharge.setRechargeMoney(new BigDecimal(money_order));
				userAccountService.rechargeToAccount(accountRecharge, 122);
			
					retBean.setRet_code("0000");
			        retBean.setRet_msg("交易成功");
			        try {
						response.getWriter().write(JSON.toJSONString(retBean));
						System.out.println(JSON.toJSONString(retBean));
						response.getWriter().flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        System.out.println("pc支付异步通知数据接收处理成功");
			
				
			}
		}
		
		
	/**
	 * 财付通---铂金 充值回调接口
	 * @param request
	 * @param response
	 * @param model
	 * @param accountRecharge
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("recvFromPOSPT.do")
	public void recvFromPOSPT(HttpServletRequest request, HttpServletResponse response){
		
		//测试代码,测试完以后删掉
		Enumeration enu = request.getParameterNames();
		String params = "";
		while (enu.hasMoreElements()) {                ///迭代枚举数组下一个元素
		    String paramName = (String) enu.nextElement(); 
		    String value = request.getParameter(paramName );
		    params += "&"+paramName+"="+value;
		    System.out.println(paramName+"="+value);
		}
		System.out.println("params from POSPT->"+params);
		logger.debug("params from POSPT->"+params);
		
		
		//测试代码end
		
		PaymentConfig paymentConfig = this.paymentConfigService.getById(9);
		String md5Str = paymentConfig.getEncryptKey();
		
		String  reqType 		= request.getParameter("reqType").trim();
		String  merId 			= request.getParameter("merId").trim();
		String  resultCode 		= request.getParameter("resultCode").trim();
		String  resultMsg 		= request.getParameter("resultMsg").trim();
		String  ordId 			= request.getParameter("ordId").trim();
		String  ordAmt 			= request.getParameter("ordAmt").trim();
		String  gateId 			= request.getParameter("gateId").trim();
		String  merPriv 		= request.getParameter("merPriv").trim();
		String  retType 		= request.getParameter("retType").trim();
		String  ordStat 		= request.getParameter("ordStat").trim();
		String  paySeqId 		= request.getParameter("paySeqId").trim();
		String  gateSeqId 		= request.getParameter("gateSeqId").trim();
		String  chkValue		= request.getParameter("chkValue").trim(); 
		
		String	signMsg = reqType + merId + resultCode + resultMsg +  ordAmt +ordId 
							 +  gateId + merPriv + retType + ordStat +  paySeqId +  gateSeqId + md5Str;
		System.out.println("signMsg["+signMsg+"]");	
		Boolean flag=MD5Utils.encode2hex(signMsg).toUpperCase().equals(chkValue);
		
		if(flag){
			if(resultCode.equals("000000")){ //交易成功
				AccountRecharge accountRecharge = new AccountRecharge();
				accountRecharge.setRechargeTradeNo(ordId);
				accountRecharge.setUserId(Integer.parseInt(merPriv));
				accountRecharge.setRechargeAddip(this.getIpAddr(request));
				accountRecharge.setRechargeMoney(new BigDecimal(ordAmt));
				userAccountService.rechargeToAccount(accountRecharge, 9);
				ResponseUtils.renderText(response, "RECV_"+ordId); 
				return;
			}
		}
	}
	/**
	 * 新浪充值回调接口
	 * @param request
	 * @param response
	 * @param model
	 * @param accountRecharge
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("recvFromSina.do")
	public void recvFromSina(HttpServletRequest request, HttpServletResponse response,OnlineRechargeGFB gfb){
				
		//测试代码,测试完以后删掉
		Enumeration enu = request.getParameterNames();
		String params = "";
		while (enu.hasMoreElements()) {                ///迭代枚举数组下一个元素
		    String paramName = (String) enu.nextElement(); 
		    String value = request.getParameter(paramName );
		    params += "&"+paramName+"="+value;
		    System.out.println(paramName+"="+value);
		}
		System.out.println("params from Sina->"+params);
		logger.debug("params from Sina->"+params);
		//测试代码end
				
		String merchantAcctId = request.getParameter("merchantAcctId");
		String version = request.getParameter("version");
		String language = request.getParameter("language");//
		String signType = request.getParameter("signType");//
		
		String orderId = request.getParameter("orderId");
		String orderTime = request.getParameter("orderTime");
		String orderAmount = request.getParameter("orderAmount");//
		String dealId = request.getParameter("dealId");//
		
		String dealTime = request.getParameter("dealTime");
		String payAmount = request.getParameter("payAmount");
		String fee = request.getParameter("fee");//整型数字新浪支付收取商户的手续费，单位为分。
		String payResult = request.getParameter("payResult");//10：支付成功11：支付失败
		String signMsg = request.getParameter("signMsg");
		String payIp =request.getParameter("payIp");
		String bankDealId = request.getParameter("bankDealId");
		String errCode = request.getParameter("errCode");
		
		String bankId=request.getParameter("bankId");
		
		//验签
		String value="merchantAcctId="+merchantAcctId+"&version="+version+"&language="+language
				+"&signType="+signType+"&bankId="+bankId+"&orderId="+orderId
				+"&orderTime="+orderTime+"&orderAmount="+orderAmount+"&dealId="+dealId+"&bankDealId="+bankDealId
				+"&dealTime="+dealTime+"&payAmount="+payAmount+"&fee="+fee+"&payResult="+payResult+"&payIp="+payIp;
		PaymentConfig paymentConfig = this.paymentConfigService.getById(11);
		String key=paymentConfig.getEncryptKey();
		String charset="utf-8";
		Boolean signMsgFlag=EpayMD5.checkMd5Sign(value,signMsg,key,"utf-8");
		
		if(signMsgFlag){
			if(payResult.equals("10")){ //交易成功
				AccountRecharge accountRecharge = new AccountRecharge();
				accountRecharge.setRechargeTradeNo(orderId);
				accountRecharge.setRechargeAddip(this.getIpAddr(request));
				accountRecharge.setRechargeMoney(new BigDecimal(orderAmount).divide(new BigDecimal(100)));
				userAccountService.rechargeToAccount(accountRecharge, 11);
				ResponseUtils.renderText(response, "<result>1</result><redirecturl></redirecturl>"); 
				return;
			}
		}
		//ResponseUtils.renderText(response, "<result>1</result><redirecturl></redirecturl>");
	}
	
	/**
	 * 新生支付回调接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("recvFromXinSheng.do")
	public void recvFromXinSheng(HttpServletRequest request, HttpServletResponse response){
		System.out.println(this.getParameters(request));
		String orderID = request.getParameter("orderID");
		String resultCode = request.getParameter("resultCode");
		String orderAmount = request.getParameter("orderAmount");
		String payAmount = request.getParameter("payAmount");
		String userId = request.getParameter("remark");
		String remark = request.getParameter("remark");
		String partnerID = request.getParameter("partnerID");
		String acquiringTime = request.getParameter("acquiringTime");
		String completeTime = request.getParameter("completeTime");
		String orderNo = request.getParameter("orderNo");
		String charset = request.getParameter("charset");
		String signType = request.getParameter("signType");
		String stateCode = request.getParameter("stateCode");
		String signMsg = request.getParameter("signMsg");
		logger.debug("receive from XinSheng-->userId:"+userId+";orderID:"+orderID+";payAmount:"+payAmount+
				";stateCode:"+stateCode+";completeTime:"+completeTime);
		String signStr=
				"orderID="+orderID
				+"&resultCode="+resultCode
				+"&stateCode="+stateCode
				+"&orderAmount="+orderAmount
				+"&payAmount="+payAmount
				+"&acquiringTime="+acquiringTime
				+"&completeTime="+completeTime
				+"&orderNo="+orderNo
				+"&partnerID="+partnerID
				+"&remark="+remark
				+"&charset="+charset
				+"&signType="+signType;
		if(signMsg != null && signMsg != ""){
			try {
				if(ClientSignature.verifySignatureByRSA(signStr, signMsg, CharsetTypeEnum.UTF8)){
					String amount = Double.parseDouble(payAmount)/100+"";
					if(stateCode != null && stateCode != "" && stateCode.equals("2")){
						AccountRecharge accountRecharge = new AccountRecharge();
						accountRecharge.setRechargeTradeNo(orderID);
						accountRecharge.setUserId(Integer.parseInt(userId));
						accountRecharge.setRechargeMoney(new BigDecimal(amount));
						userAccountService.rechargeToAccount(accountRecharge, 19);
						ResponseUtils.renderText(response, "200");
					}
				}
			} catch (Exception e) {
				
			}
		}
		
		
	}
	
	/**
	 * 易宝支付回调接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("recvFromYeePay.do")
	public void recvFromYeePay(HttpServletRequest request, HttpServletResponse response){
		
		//测试代码,测试完以后删掉
				Enumeration enu = request.getParameterNames();
				String params = "";
				while (enu.hasMoreElements()) {                ///迭代枚举数组下一个元素
				    String paramName = (String) enu.nextElement(); 
				    String value = request.getParameter(paramName );
				    params += "&"+paramName+"="+value;
				    System.out.println(paramName+"="+value);
				}
				System.out.println("params from GFB->"+params);
				logger.debug("params from GFB->"+params);
				//测试代码end
		
		
		Map<String,String> param=new HashMap<String, String>();
/*		param.put("paymentCode", "yeepay");
		param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
		param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
		PaymentConfig paymentConfig = this.paymentConfigService.getByParam(param);*/
		//获取支付
		PaymentConfig paymentConfig = this.paymentConfigService.getById(12);
		String keyValue = paymentConfig.getEncryptKey();   // 商家密钥
		String r0_Cmd = PaymentForOnlineService.formatString(request.getParameter("r0_Cmd")); // 业务类型
		String p1_MerId = paymentConfig.getClientId();// 商户编号
		String r1_Code = PaymentForOnlineService.formatString(request.getParameter("r1_Code"));// 支付结果
		String r2_TrxId = PaymentForOnlineService.formatString(request.getParameter("r2_TrxId"));// 易宝支付交易流水号
		String r3_Amt = PaymentForOnlineService.formatString(request.getParameter("r3_Amt"));// 支付金额
		String r4_Cur = PaymentForOnlineService.formatString(request.getParameter("r4_Cur"));// 交易币种
		String r5_Pid = "";
		String r8_MP = "";
		try {
			r5_Pid = new String(PaymentForOnlineService.formatString(request.getParameter("r5_Pid")).getBytes("iso-8859-1"),"gbk");// 商品名称
			r8_MP = new String(PaymentForOnlineService.formatString(request.getParameter("r8_MP")).getBytes("iso-8859-1"),"gbk");// 商户扩展信息
		} catch (UnsupportedEncodingException e) {
			
		}
		String r6_Order = PaymentForOnlineService.formatString(request.getParameter("r6_Order"));// 商户订单号
		String r7_Uid = PaymentForOnlineService.formatString(request.getParameter("r7_Uid"));// 易宝支付会员ID
		String r9_BType = PaymentForOnlineService.formatString(request.getParameter("r9_BType"));// 交易结果返回类型
		String hmac = PaymentForOnlineService.formatString(request.getParameter("hmac"));// 签名数据
		boolean isOK = false;
		// 校验返回数据包
		isOK = PaymentForOnlineService.verifyCallback(hmac,p1_MerId,r0_Cmd,r1_Code, 
				r2_TrxId,r3_Amt,r4_Cur,r5_Pid,r6_Order,r7_Uid,r8_MP,r9_BType,keyValue);
		if(isOK) {
			//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
			if(r1_Code.equals("1")) {
				AccountRecharge accountRecharge = new AccountRecharge();
				accountRecharge.setRechargeTradeNo(r6_Order);
				accountRecharge.setUserId(Integer.parseInt(r8_MP));
				accountRecharge.setRechargeAddip(this.getIpAddr(request));
				accountRecharge.setRechargeMoney(new BigDecimal(r3_Amt));
				userAccountService.rechargeToAccount(accountRecharge, paymentConfig.getId());
				// 产品通用接口支付成功返回-浏览器重定向
				if(r9_BType.equals("1")) {
					System.out.println("callback方式:产品通用接口支付成功返回-浏览器重定向");
					// 产品通用接口支付成功返回-服务器点对点通讯
					return ;//"redirect:../../account/cashValue.do?t='yeepay'";
				} else if(r9_BType.equals("2")) {
					// 如果在发起交易请求时	设置使用应答机制时，必须应答以"success"开头的字符串，大小写不敏感
					System.out.println("SUCCESS");
				  // 产品通用接口支付成功返回-电话支付返回		
					SpringUtils.renderText(response, "SUCCESS");
				}
				// 下面页面输出是测试时观察结果使用
				System.out.println("<br>交易成功!<br>商家订单号:" + r6_Order + "<br>支付金额:" + r3_Amt + "<br>易宝支付交易流水号:" + r2_TrxId);
			}
			return ;
		} else {
			System.out.println("交易签名被篡改!");
			return ;
		}
	}
	
	/**
	 * 汇付宝充值回调接口
	 * @param request
	 * @param response
	 * @param model
	 * @param accountRecharge
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("recvFromHFB.do")
	public void recvFromHFB(HttpServletRequest request, HttpServletResponse response){
		
		//测试代码,测试完以后删掉
		Enumeration enu = request.getParameterNames();
		String params = "";
		while (enu.hasMoreElements()) {                ///迭代枚举数组下一个元素
		    String paramName = (String) enu.nextElement(); 
		    String value = request.getParameter(paramName );
		    params += "&"+paramName+"="+value;
		    System.out.println(paramName+"="+value);
		}
		System.out.println("params from HFB->"+params);
		logger.debug("params from HFB->"+params);
		
		
		request.getRequestURL();
		request.getQueryString();
		//测试代码end
		/*Map<String,String> param=new HashMap<String, String>();
		param.put("paymentName", "汇付宝");
		param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
		param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
		PaymentConfig paymentConfig = this.paymentConfigService.getByParam(param);*/
		PaymentConfig paymentConfig = this.paymentConfigService.getById(8);
		String Md5key = paymentConfig.getEncryptKey();
		
		String agentId = request.getParameter("agent_id");
		String agentBillId = request.getParameter("agent_bill_id");//
		String jnetBillNo = request.getParameter("jnet_bill_no");//
		String payType = request.getParameter("pay_type");//
		String result = request.getParameter("result");//1为成功
		String payAmt = request.getParameter("pay_amt");//
		String payMessage = request.getParameter("pay_message");//
		String remark = request.getParameter("remark");//
		String sign = request.getParameter("sign");//
		String signstr="result="+result+"&agent_id="+agentId+"&jnet_bill_no="+jnetBillNo+"&agent_bill_id="+agentBillId+
				"&pay_type="+payType+"&pay_amt="+payAmt+
				"&remark="+remark+"&key="+Md5key;
		String signMd5=EpayMD5.MD5HFB(signstr);
		signMd5=signMd5.toLowerCase();
		if(sign.equals(signMd5)){
			if(result.equals("1")){ //交易成功
				AccountRecharge accountRecharge = new AccountRecharge();
				accountRecharge.setRechargeTradeNo(agentBillId);
				accountRecharge.setUserId(Integer.parseInt(remark));
				accountRecharge.setRechargeAddip(this.getIpAddr(request));
				accountRecharge.setRechargeMoney(new BigDecimal(payAmt));
				userAccountService.rechargeToAccount(accountRecharge, 8);
				return;
			}
		}
	}
	/**
	 * "丰付的"充值回调接口
	 * @param request
	 * @param response
	 * @param ff
	 */
	@RequestMapping("recvFromFengFu.do")
	public void recvFromFF(HttpServletRequest request, HttpServletResponse response){
		System.out.println(this.getParameters(request));
		String requestId=request.getParameter("requestId");//外部系统请求流水号
		String payId=request.getParameter("payId");//交易流水号
		String fiscalDate=request.getParameter("fiscalDate");//会计日期
		String description=request.getParameter("description");//透传信息
		String resultSignature=request.getParameter("resultSignature");//数字签名
		String payType=request.getParameter("payType");//支付类型
		String bankCode=request.getParameter("bankCode");//银行代码
		String totalPrice=request.getParameter("totalPrice");//订单金额
		String tradeAmount=request.getParameter("tradeAmount");//订单实际支付金额
		String tradeFee=request.getParameter("tradeFee");//手续费
		String status=request.getParameter("status");//交易状态
		String userIdIdentity=request.getParameter("userIdIdentity");//第三方用户ID
		String md5key = request.getParameter("md5key");
		System.out.println(md5key);
		String[] strarr = description.split(","); 
		String userId = strarr[0];
		String aKey = strarr[1];
		//商户流水号+支付系统交易流水号+支付系统会计日期+透传信息
		String aValue=requestId+payId+fiscalDate+description;
		String sign = SignatureUtil.hmacSign(aValue, aKey);
		logger.debug("receive from FengFu-->userIdIdentity:"+userId+";payId:"+payId+";tradeAmount:"+tradeAmount+
				";status:"+status);
		
		if( sign !=null && sign !="" && sign.equals(resultSignature) && "2".equals(status)){
			AccountRecharge accountRecharge = new AccountRecharge();
			accountRecharge.setRechargeTradeNo(requestId);
			accountRecharge.setUserId(Integer.parseInt(userId));
			accountRecharge.setRechargeMoney(new BigDecimal(tradeAmount));
			userAccountService.rechargeToAccount(accountRecharge, 17);
			ResponseUtils.renderText(response, "200");
		}	
		
	}
	/**
	 * 国付宝充值回调接口
	 * @param request
	 * @param response
	 * @param model
	 * @param accountRecharge
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("recvFromGFB.do")
	public void recvFromGFB(HttpServletRequest request, HttpServletResponse response,OnlineRechargeGFB gfb){
				
		//测试代码,测试完以后删掉
		Enumeration enu = request.getParameterNames();
		String params = "";
		while (enu.hasMoreElements()) {                ///迭代枚举数组下一个元素
		    String paramName = (String) enu.nextElement(); 
		    String value = request.getParameter(paramName );
		    params += "&"+paramName+"="+value;
		    System.out.println(paramName+"="+value);
		}
		System.out.println("params from GFB->"+params);
		logger.debug("params from GFB->"+params);
		//测试代码end
				
		String respCode = request.getParameter("respCode");
		String orderNum = request.getParameter("merOrderNum");//订单号
		String tranAmt = request.getParameter("tranAmt");//金额
		String userId = request.getParameter("merRemark1");//userId
		gfb.setGopayServerTime("");
		Map<String,String> param=new HashMap<String, String>();
/*		param.put("paymentCode", "guofubao");
		param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
		param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
		PaymentConfig paymentConfig = this.paymentConfigService.getByParam(param);*/
		PaymentConfig paymentConfig = this.paymentConfigService.getById(3);
		//获取支付
		gfb.setVerficationCode(paymentConfig.getEncryptKey());//用于MD5加密验证
		logger.debug("receive from GFB-->userId:"+userId+";orderNum:"+orderNum+";tranAmt:"+tranAmt+
									";RespCode:"+respCode);
		if(!MD5Utils.stringToMD5(gfb.getOrgSignValue()).equals(gfb.getSignValue())){ //加密校验未通过
			ResponseUtils.renderText(response, "RespCode="+respCode+"|JumpURL="+PropertiesUtil.get("GFB.recharge.success"));
			return;
			// 参数中feeAmt=0.01 ???
		}
		
		if(respCode.equals("0000")){ //交易成功
			AccountRecharge accountRecharge = new AccountRecharge();
			accountRecharge.setRechargeTradeNo(orderNum);
			accountRecharge.setUserId(Integer.parseInt(userId));
			accountRecharge.setRechargeAddip(this.getIpAddr(request));
			accountRecharge.setRechargeMoney(new BigDecimal(tranAmt));
			userAccountService.rechargeToAccount(accountRecharge, paymentConfig.getId());
			ResponseUtils.renderText(response, "RespCode="+respCode+"|JumpURL="+PropertiesUtil.get("GFB.recharge.success")); 
			return;
		}
		ResponseUtils.renderText(response, "RespCode="+respCode+"|JumpURL="+PropertiesUtil.get("GFB.recharge.success"));
	}
	/**
	 * 网银在线充值回调接口
	 * @param request
	 * @param response
	 * @param model
	 * @param accountRecharge
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping("recvFromChinaBank.do")
	public void recvFromChinaBank(HttpServletRequest request, HttpServletResponse response){
		
		//测试代码,测试完以后删掉
		Enumeration enu = request.getParameterNames();
		String params = "";
		while (enu.hasMoreElements()) {  
		    String paramName = (String) enu.nextElement(); 
		    String value = request.getParameter(paramName );
		    params += "&"+paramName+"="+value;
		    System.out.println(paramName+"="+value);
		}
		System.out.println("params from ChinaBank->"+params);
		logger.debug("params from ChinaBank->"+params);
		//测试代码end
		
		String v_oid = request.getParameter("v_oid");		// 订单号
		String v_pmode = request.getParameter("v_pmode");		// 支付方式中文说明，如"中行长城信用卡"
		String v_pstatus = request.getParameter("v_pstatus");	// 支付结果，20支付完成；30支付失败；
		String v_pstring = request.getParameter("v_pstring");	// 对支付结果的说明，成功时（v_pstatus=20）为"支付成功"，支付失败时（v_pstatus=30）为"支付失败"
		String v_amount = request.getParameter("v_amount");		// 订单实际支付金额
		String v_moneytype = request.getParameter("v_moneytype");	// 币种
		String v_md5str = request.getParameter("v_md5str");		// MD5校验码
		String userId = request.getParameter("remark1");		// 备注1

		logger.debug("receive from ChinaBank-->userId:"+userId+";orderNum:"+v_oid+";tranAmt:"+v_amount+
				";RespCode:"+v_pstatus);
		Map<String,String> param=new HashMap<String, String>();
/*		param.put("paymentCode", "chinabank");
		param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
		param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
		//获取支付
		PaymentConfig paymentConfig = this.paymentConfigService.getByParam(param);*/
		PaymentConfig paymentConfig = this.paymentConfigService.getById(1);
		String text = v_oid+v_pstatus+v_amount+v_moneytype+paymentConfig.getEncryptKey(); //拼凑加密串
		String v_md5text = MD5Utils.stringToMD5(text).toUpperCase();
		
		if (v_md5str==null || !v_md5str.equals(v_md5text)) {
			ResponseUtils.renderText(response, "error");
			return;
		}
		ResponseUtils.renderText(response, "ok");// 告诉服务器验证通过,停止发送
		if ("20".equals(v_pstatus)) {
			AccountRecharge accountRecharge = new AccountRecharge();
			accountRecharge.setRechargeTradeNo(v_oid);
			accountRecharge.setUserId(Integer.parseInt(userId));
			accountRecharge.setRechargeMoney(new BigDecimal(v_amount));
			userAccountService.rechargeToAccount(accountRecharge, paymentConfig.getId());
			
		}
	}
	/**
	 * 双乾在线充值回调接口
	 * @param request
	 * @param response
	 * @param gfb
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("recvFromSQ.do")
	public void recvFromSQ(HttpServletRequest request, HttpServletResponse response){
		
		//测试代码,测试完以后删掉
		Enumeration enu = request.getParameterNames();
		String params = "";
		while (enu.hasMoreElements()) {  
		    String paramName = (String) enu.nextElement(); 
		    String value = request.getParameter(paramName );
		    params += "&"+paramName+"="+value;
		    System.out.println(paramName+"="+value);
		}
		System.out.println("params from SQ->"+params);
		logger.debug("params from SQ->"+params);
		
		//测试代码end
		Map<String,String> param=new HashMap<String, String>();
/*		param.put("paymentCode", "shuangqian");
		param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
		param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
		//获取支付
		PaymentConfig paymentConfig = this.paymentConfigService.getByParam(param);*/
		PaymentConfig paymentConfig = this.paymentConfigService.getById(4);
		String MerNo = paymentConfig.getClientId();
	    String MD5key = paymentConfig.getEncryptKey();
	    String BillNo = request.getParameter("BillNo");	
	    String Amount = request.getParameter("Amount");	
	    String Succeed = request.getParameter("Succeed");
	    String Result  = request.getParameter("Result"); 
	    String MD5info = request.getParameter("MD5info");
	    String userId  = request.getParameter("MerRemark"); 
	   	String md5str = PayUtils.signMap(new String[]{MerNo,BillNo,Amount,Succeed}, MD5key, "RES");

		System.out.println(MD5info+"=="+Succeed+"=====succeed======="+md5str);
		System.out.println(Result);

		logger.debug("receive from SQ-->userId:"+userId+";orderNum:"+BillNo+";tranAmt:"+Amount+
				";RespCode:"+Succeed);
		
		if(MD5info==null){
			ResponseUtils.renderText(response, "效验码为空");
			return;
		}
		if(MD5info.equals(md5str)){
			if(Succeed.equals("88")){
				AccountRecharge accountRecharge = new AccountRecharge();
				accountRecharge.setRechargeTradeNo(BillNo);
				accountRecharge.setUserId(Integer.parseInt(userId));
				accountRecharge.setRechargeMoney(new BigDecimal(Amount));
				userAccountService.rechargeToAccount(accountRecharge, paymentConfig.getId());
			}else {
				ResponseUtils.renderText(response, Succeed+"->"+Result);
				return;
				
			}
		}else{
			ResponseUtils.renderText(response, Succeed+"->"+Result);
			return;
		}
		
	}
	/**
	 * 宝付在线充值回调接口
	 * @param request
	 * @param response
	 * @param gfb
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("recvFromBF.do")
	public void recvFromBF(HttpServletRequest request, HttpServletResponse response){
		
		//测试代码,测试完以后删掉
		Enumeration enu = request.getParameterNames();
		String params = "";
		while (enu.hasMoreElements()) {  
		    String paramName = (String) enu.nextElement(); 
		    String value = request.getParameter(paramName );
		    params += "&"+paramName+"="+value;
		    System.out.println(paramName+"="+value);
		}
		System.out.println("params from BF->"+params);
		logger.debug("params from BF->"+params);
		//测试代码end
		
		Map<String,String> param=new HashMap<String, String>();
/*		param.put("paymentCode", "baofoo");
		param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
		param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
		//获取支付
		PaymentConfig paymentConfig = this.paymentConfigService.getByParam(param);*/
		PaymentConfig paymentConfig = this.paymentConfigService.getById(2);
		String MemberID = paymentConfig.getClientId();//商户号
		String TerminalID = paymentConfig.getAccount();//商户终端号
		String Md5key = paymentConfig.getEncryptKey();
		String TransID = request.getParameter("TransID");//商户流水号
		String Result = request.getParameter("Result");//支付结果
		String ResultDesc = request.getParameter("ResultDesc");//支付结果描述
		String FactMoney = request.getParameter("FactMoney");//实际成功金额
		String userId = request.getParameter("AdditionalInfo");//订单附加消息	
		String SuccTime = request.getParameter("SuccTime");//支付完成时间
		String Md5Sign = request.getParameter("Md5Sign");//MD5签名
		
		logger.debug("receive from BF-->userId:"+userId+";orderNum:"+TransID+";tranAmt:"+FactMoney+
				";RespCode:"+Result);
		String MARK = "~|~";
		String md5 = "MemberID=" + MemberID + MARK + "TerminalID=" + TerminalID + MARK + "TransID=" + TransID + MARK + "Result=" + Result + MARK + "ResultDesc=" + ResultDesc + MARK
				+ "FactMoney=" + FactMoney + MARK + "AdditionalInfo=" + userId + MARK + "SuccTime=" + SuccTime
				+ MARK + "Md5Sign=" + Md5key;
		String WaitSign = PayUtils.getMD5ofStr(md5);	
		if(WaitSign.compareTo(Md5Sign)==0){
			if("1".equals(Result)){ //成功
				AccountRecharge accountRecharge = new AccountRecharge();
				accountRecharge.setRechargeTradeNo(TransID);
				accountRecharge.setUserId(Integer.parseInt(userId));
				accountRecharge.setRechargeMoney(new BigDecimal(FactMoney).divide(BigDecimal.valueOf(100)));
				userAccountService.rechargeToAccount(accountRecharge, paymentConfig.getId()); 
			}
			System.out.println("OK");
			//校验通过开始处理订单		
			ResponseUtils.renderText(response, "OK");
			return;//全部正确了输出OK		
		}else{
			System.out.println("Md5CheckFail");
			ResponseUtils.renderText(response, "Md5CheckFail'");

		}
	}
	
	/**
	 * 宝付SDK在线充值回调接口
	 * @param request
	 * @param response
	 * @param gfb
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("recvFromBFSDK.do")
	public void recvFromBFSDK(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String, String> result = new BaoFooApi().SdkReturnurl(request, response);
		
		PaymentConfig paymentConfig = this.paymentConfigService.getById(2);
		
		if("0000".equals(result.get("resp_code"))){

			String TransID = result.get("trans_id");//商户流水号
			String FactMoney = result.get("succ_amt");//实际成功金额
			String userId = result.get("additional_info");//订单附加消息	
			
			AccountRecharge accountRecharge = new AccountRecharge();
			accountRecharge.setRechargeTradeNo(TransID);
			accountRecharge.setUserId(Integer.parseInt(userId));
			accountRecharge.setRechargeMoney(new BigDecimal(FactMoney));
			userAccountService.rechargeToAccount(accountRecharge, paymentConfig.getId()); 

			System.out.println("OK");
			//校验通过开始处理订单		
			ResponseUtils.renderText(response, "OK");
			return;//全部正确了输出OK		
		}else{
			System.out.println("Md5CheckFail");
			ResponseUtils.renderText(response, "Md5CheckFail'");

		}
	}
	
	/**
	 * 汇潮在线充值回调接口
	 * @param request
	 * @param response
	 * @param hc
	 */
	@RequestMapping("recvFromHC.do")
	public void recvFromHC(HttpServletRequest request, HttpServletResponse response,OnlineRechargeHC hc){
		//测试代码,测试完以后删掉
		java.util.Enumeration enu = request.getParameterNames();
		String params = "";
		while (enu.hasMoreElements()) {  
		    String paramName = (String) enu.nextElement(); 
		    String value = request.getParameter(paramName );
		    params += "&"+paramName+"="+value;
		    System.out.println(paramName+"="+value);
		}
		System.out.println("params from HC->"+params);
		logger.debug("params from HC->"+params);
		//测试代码end
		String BillNo = request.getParameter("BillNo");
	    String Amount = request.getParameter("Amount");
	    String tradeOrder = request.getParameter("tradeOrder");
	    String Succeed = request.getParameter("Succeed");
	    String Result = request.getParameter("Result");
	    String SignMD5info = request.getParameter("SignMD5info");
	    
	    PaymentConfig paymentConfig = this.paymentConfigService.getById(6);
	    String MD5key = paymentConfig.getEncryptKey();
	    EpayMD5 md5 = new EpayMD5();
	    String md5src = BillNo+"&"+Amount+"&"+Succeed+"&"+MD5key;
	    String md5sign; //MD5加密后的字符串
	    md5sign = md5.getMD5ofStr(md5src);//MD5检验结果
	    if(md5sign.compareTo(SignMD5info)==0){
		    if(Succeed.equals("88")){//成功
		    	AccountRecharge accountRecharge = new AccountRecharge();
		    	accountRecharge.setRechargeTradeNo(BillNo);
				
				AccountRecharge pay=accountRechargeService.getAccountRecharge(accountRecharge);
				Integer userId=pay.getUserId();
				accountRecharge.setUserId(userId);
				accountRecharge.setRechargeMoney(new BigDecimal(Amount));
				userAccountService.rechargeToAccount(accountRecharge, 6); 
				
				
		    }
		    System.out.println("OK");
			//校验通过开始处理订单		
			ResponseUtils.renderText(response, "OK");
			return;//全部正确了输出OK
	    }else{
			System.out.println("Md5CheckFail");
			ResponseUtils.renderText(response, "Md5CheckFail'");

		}
		
	}
	/**
	 * 银联在线充值回调接口
	 * @param request
	 * @param response
	 * @param cp
	 */
	@RequestMapping("recvFromCP.do")
	public void recvFromCP(HttpServletRequest req, HttpServletResponse response,OnlineRechargeChinaPay cp){
		//测试代码,测试完以后删掉
		java.util.Enumeration enu = req.getParameterNames();
		String params = "";
		while (enu.hasMoreElements()) {  
		    String paramName = (String) enu.nextElement(); 
		    String value = req.getParameter(paramName );
		    params += "&"+paramName+"="+value;
		    System.out.println(paramName+"="+value);
		}
		System.out.println("params from CP->"+params);
		logger.debug("params from CP->"+params);
		//测试代码end
		String Version = req.getParameter("version");
		String MerId = req.getParameter("merid");
		String OrdId = req.getParameter("orderno");
		String TransAmt = req.getParameter("amount");// 12
		String CuryId = req.getParameter("currencycode");// 3
		String TransDate = req.getParameter("transdate");// 8
		String TransType = req.getParameter("transtype");// 4
		String Status = req.getParameter("status");//
		String BgRetUrl = req.getParameter("BgRetUrl");
		String PageRetUrl = req.getParameter("PageRetUrl");
		String GateId = req.getParameter("GateId");
		String Priv1 = req.getParameter("Priv1");
		
		String ChkValue = req.getParameter("checkvalue");
		System.out.println(MerId+OrdId+TransAmt+CuryId+TransDate+TransType+Status+ChkValue);
		
		boolean buildOK = false;
		boolean res = false;
		int KeyUsage = 0;
		String PubKeyPath=PropertiesUtil.get("chinapay.pubkey.filepath");
		PubKeyPath = ContextLoader.getCurrentWebApplicationContext()
				.getServletContext().getRealPath(PubKeyPath); 
		List errorList = new ArrayList();
		PrivateKey key = new PrivateKey();
		try {
			buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
		} catch (Exception e) {
			//
		}
		if (!buildOK) {
			System.out.println("build error!");
			errorList.add("build error!");
		}
		try {
			SecureLink sl = new SecureLink(key);
			res=sl.verifyTransResponse(MerId, OrdId, TransAmt, CuryId, TransDate, TransType, Status, ChkValue);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			errorList.add(e.getMessage());
		}
		if(Status.equals("1001")){//交易成功
			AccountRecharge accountRecharge = new AccountRecharge();
			accountRecharge.setRechargeTradeNo(OrdId);
			accountRecharge.setUserId(Integer.parseInt(Priv1));
			
			String newTransAmt = TransAmt.replaceFirst("^0*", "");
			TransAmt=String.valueOf(Double.parseDouble((newTransAmt))/100);
			accountRecharge.setRechargeMoney(new BigDecimal(TransAmt));
			
			userAccountService.rechargeToAccount(accountRecharge, 5); 
			System.out.println("OK");
			//校验通过开始处理订单		
			ResponseUtils.renderText(response, "OK");
			return;//全部正确了输出OK
		}
	}
	/**
	 * 贝付在线充值回调接口
	 * @param request
	 * @param response
	 * @param bf
	 */
	@RequestMapping("recvFromBeiFu.do")
	public void recvFromBeiFu(HttpServletRequest request, HttpServletResponse response){
		//测试代码,测试完以后删掉
		Enumeration enu = request.getParameterNames();
		String params = "";
		while (enu.hasMoreElements()) {  
		    String paramName = (String) enu.nextElement(); 
		    String value = request.getParameter(paramName );
		    params += "&"+paramName+"="+value;
		    System.out.println(paramName+"="+value);
		}
		System.out.println("params from BF->"+params);
		logger.debug("params from BF->"+params);
		//测试代码end
		PaymentConfig paymentConfig = this.paymentConfigService.getById(7);//为贝付页面value的值
		//原加密字段
        String sign = request.getParameter("sign");
        //得到回传参数，并重新构建，iso-8859-1解决乱码问题
         Map<String, String> paramMap = this.getParameterss(request);
         for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                Object value = entry.getValue();
                String paramValue;
                if (value == null) {
                    entry.setValue(null);
                } else if (value instanceof String) {
                    paramValue = (String) value;
                    entry.setValue(paramValue);
                } else if (value instanceof String[]) {
                    String[] values = (String[]) value;
                    paramValue = (String) values[0];
                    entry.setValue(paramValue);
                }
            }
          //String input_charset="UTF-8";
         String input_charset= PropertiesUtil.get("beifu.inputCharset");
          try {
          for(Entry<String,String> entry:paramMap.entrySet()){            
                  paramMap.put(entry.getKey(), (new String(entry.getValue().getBytes("iso-8859-1"),input_charset)));
              
          }
          paramMap.put("subject", SysCacheUtils.getSysConfig().getSysWebsitesignature()+"线上充值");
          paramMap.remove("sign");
        
          List<String> keyList = new ArrayList<String>(paramMap.keySet());
          Collections.sort(keyList);

          StringBuffer strBuf = new StringBuffer();
          for (String paramName : keyList) {
              String paramValue = paramMap.get(paramName);
              Boolean a =false ;
              if(paramValue != null || !paramValue.trim().equals("")){
                  a = true;
              }
              strBuf.append((strBuf.length() > 0 ? "&" : "") + paramName + "=" + (a ? paramValue : ""));
          }
        
        // 加密(MD5加签)，默认已取UTF-8字符集，如果需要更改，可调用Md5Encrypt.setCharset(inputCharset)
        String paramString = strBuf.toString();
        //String key = "9UCKYZ6Q804CO5O43TGHLMDO4YTU10hggixe";
        String key=paymentConfig.getEncryptKey();
        paramString = new StringBuilder(paramString).append(key).toString();
        String signl = Md5Encrypt.encrypt(paramString);
        //MD5验签
        if(sign.equals(signl)){
            //验签成功，返回成功页面给用户
        	String outTradeNo=request.getParameter("out_trade_no");
			String totalFee=request.getParameter("total_fee");
			String  userId=request.getParameter("extra_common_param");//
			
        	AccountRecharge accountRecharge = new AccountRecharge();
			accountRecharge.setRechargeTradeNo(outTradeNo);
			accountRecharge.setUserId(Integer.parseInt(userId));
			accountRecharge.setRechargeMoney(new BigDecimal(totalFee));
			
        	userAccountService.rechargeToAccount(accountRecharge, 7); 
			System.out.println("OK");
			//校验通过开始处理订单		
			ResponseUtils.renderText(response, "OK");
        }else{
        	System.out.println("signCheckFail");
			ResponseUtils.renderText(response, "signCheckFail'");
        }
      } catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
          
      }
	
	
}
