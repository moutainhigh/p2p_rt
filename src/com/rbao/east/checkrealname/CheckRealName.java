package com.rbao.east.checkrealname;

import com.rbao.east.checkrealname.IdentifierServiceStub;
import com.rbao.east.checkrealname.IdentifierServiceStub.CheckRequest;
import com.rbao.east.checkrealname.IdentifierServiceStub.CheckResponse;
import com.rbao.east.checkrealname.IdentifierServiceStub.Credential;
import com.rbao.east.checkrealname.IdentifierServiceStub.IdentifierData;
import com.rbao.east.checkrealname.IdentifierServiceStub.SimpleCheck;
import com.rbao.east.checkrealname.IdentifierServiceStub.SimpleCheckResponse;
import com.rbao.east.utils.PropertiesUtil;

/**
 * 实名认证
 * @author cdw
 * @date 2015年11月2日
 */
public class CheckRealName {
	public static boolean checkRealName(String realName,String IDNumber ){
		String user = PropertiesUtil.get("user");
		String pwd = PropertiesUtil.get("pwd");
		boolean flag = false;
		try {
			CheckRequest req = new CheckRequest();
			req.setIDNumber(IDNumber);
			req.setName(realName);
			
			Credential cred = new Credential();
			cred.setUserName(user);
			cred.setPassword(pwd);
			
			IdentifierServiceStub service = new IdentifierServiceStub();
			SimpleCheck ck = new SimpleCheck();
			ck.setRequest(req);
			ck.setCred(cred);
			
			SimpleCheckResponse repe = service.simpleCheck(ck);
			CheckResponse rep = repe.getSimpleCheckResult();
			switch (rep.getResponseCode()){
				case 100: //调用成功
					IdentifierData identifier = rep.getIdentifier();
					String result = identifier.getResult();
					if (result.equals("一致")){
						flag=true;
						System.out.println("身份认证成功");
						System.out.println("生日:" + identifier.getBirthday());
						//其他信息略。。。
					}
					else{
						flag = false;
						System.out.println("身份认证失败:" + result);
					}
					break;
				default: //调用失败
					System.out.println("接口调用失败:" + rep.getResponseText());
					break;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return flag;
		
		
	}
	public static void main(String[] args) {
		checkRealName("陈定伟","411528199102017113");
	}
}
