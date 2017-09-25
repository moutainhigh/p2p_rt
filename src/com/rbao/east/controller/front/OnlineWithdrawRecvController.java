package com.rbao.east.controller.front;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.log.SysoCounter;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.dao.AccountCashDao;
import com.rbao.east.entity.AccountCash;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.lianlianpayutil.LLPayUtil;
import com.rbao.east.pay.entity.RetBean;
import com.rbao.east.service.AccountCashService;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.AccountRechargeService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.PaymentConfigService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;

/**
 * 在线提现回调类
 * @author cdw
 * @date 2015年11月11日
 */
@Controller
@RequestMapping("/user/onlineWithdraw/")
public class OnlineWithdrawRecvController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(OnlineWithdrawRecvController.class);
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private AccountCashDao accountCashDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private PaymentConfigService paymentConfigService;
	@Autowired
	private AccountRechargeService accountRechargeService;
	@Autowired
	private AccountCashService accountCashService;
	@RequestMapping("withDraw.do")
    public void withDraw(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, Object> result=new HashMap<String, Object>();
 		result.put("noOrder", "E40420141128103600599759");
 		result.put("cashMoney", "100");
 		result.put("info_order", "失败");
 		result.put("cashStatuss", 1);
 		String ipAddress="127.0.0.1";
 		result.put("ipAddress", ipAddress);
 		/*User user=this.loginAdminUser(request);
 		result.put("user", user);*/
 		DwzResult res=this.updateAccountCashById(result);
		/*String reqStr = LLPayUtil.readReqStr(request);
        JSONObject json = JSONObject.fromObject(reqStr);
        if(json.get("result_pay ").equals("SUCCESS")){
        	 Map<String, Object> result=new HashMap<String, Object>();
     		result.put("noOrder", json.get("no_order"));
     		result.put("cashMoney", json.get("money_order"));
     		result.put("info_order", json.get("info_order"));
     		result.put("cashStatuss", 1);
     		String ipAddress=this.getIpAddr(request);
     		result.put("ipAddress", ipAddress);
     		User user=this.loginAdminUser(request);
     		result.put("user", user);
     		DwzResult res=this.updateAccountCashById(result);
     		RetBean ret = new RetBean();
     		ret.setRet_code("0000");
     		ret.setRet_msg("交易成功");
     		try {
				response.getWriter().write(JSON.toJSONString(ret));
			 System.out.println("提现异步通知成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     		OperatorLog operatorLog = new OperatorLog();
     		operatorLog.setLogUserid(user.getUserAccount());
     		operatorLog.setOperatorTitle("提现审核操作");
     		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_DRAW);
     		operatorLog.setOperatorParams(result.toString());
     		operatorLog.setOperatorReturn(res.getMessage());
     		operatorLog.setOperatorStatus(Integer.parseInt(res.getStatusCode()));
     		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
     		operatorLogService.addAdminLog(operatorLog);
        }
       */
		
		
		/*SpringUtils.renderJson(response, res);*/
    }
	
	public DwzResult updateAccountCashById(Map<String, Object> param) {
		DwzResult dwzResult=null;
		boolean bool=false;
		AccountCash accountCashF=accountCashService.selectByNoOrder(param.get("noOrder").toString());
		try {
			if(accountCashF==null){
				System.out.println("not find "+param.get("noOrder")+param.get(param.get("cashMoney")));
				
			}else if(param.containsKey("cashStatuss")){
				int cashStatus =Integer.parseInt(param.get("cashStatuss").toString());
				AccountCash accountCashOld=accountCashService.selectCashByNoOrderForUpdate(param.get("noOrder").toString());
				AccountCash accountCash=new AccountCash();
				User user=(User) param.get("user");
				if(cashStatus==1 && accountCashOld.getCashStatus() == AccountCash.cashApply){
					accountCash.setNoOrder(param.get("noOrder").toString());
					
					if(accountCashOld.getCashStatus().intValue()!=0){
						if(accountCashOld.getCashStatus().intValue()==1){
							throw new RuntimeException("已提现成功！不能重复提现！！");
						}
						if(accountCashOld.getCashStatus().intValue()==3){
							throw new RuntimeException("已撤销提现！提现失败！");
						}
					}
					accountCash.setVerifyUserid(1001);//连连提现
					if(param.containsKey("info_order")){
						accountCash.setVerifyRemark(param.get("info_order").toString());
					}
					accountCash.setCashStatus(1);
					bool=accountCashDao.update("updateByPrimaryKeySelective", accountCash);
					if(bool){
						dwzResult=userAccountService.updateUserAccountForAccountCash(param);
					}else{
						throw new RuntimeException("修改提现状态错误！！！");
					}
				}else if(cashStatus==2 && accountCashOld.getCashStatus() == AccountCash.cashApply){
					
					accountCash.setId(Integer.parseInt(param.get("id").toString()));
					//AccountCash accountCashInfo=this.selectCashForUpdate(accountCash.getId());
					//AccountCash accountCashInfo=accountCashDao.selectByPrimaryKey(Integer.parseInt(param.get("id").toString()));
					accountCash.setVerifyUserid(user.getId());
					if(param.containsKey("info_order")){
						accountCash.setVerifyRemark(param.get("info_order").toString());
					}
					if(accountCashOld.getCashStatus()==AccountCash.cashApply){
						accountCash.setCashStatus(2);
						
						bool=accountCashDao.update("updateByPrimaryKeySelective", accountCash);
					}
					
					UserAccount userAccount=userAccountService.selectByUserIdForUpdate(accountCashOld.getUserId());
					BigDecimal unavailableMoney=userAccount.getUnavailableMoney().subtract(accountCashOld.getCashTotal());
					BigDecimal availableMoney=userAccount.getAvailableMoney().add(accountCashOld.getCashTotal()); 
					userAccount.setUnavailableMoney(unavailableMoney);
					userAccount.setAvailableMoney(availableMoney);
					
					userAccountService.updateByPrimaryKeySelective(userAccount);
					
					accountLogService.add(userAccount, AccountLog.WITHDRAW_APPLY_NO,accountCashOld.getCashTotal()
							,new BigDecimal(0), accountCashOld.getUserId(), 
							"用户["+accountCashOld.getUserAccount()+"]提现失败,解冻资金"+
							"["+accountCashOld.getCashTotal()+"]元"
							,param.get("ipAddress").toString());
					
					MessageCenter center = new MessageCenter();
					center.setMessageContent("提现失败，失败原因【"+accountCash.getVerifyRemark()+"】");
					center.setMessageSendIp(RequestUtils.getIpAddr());
					//center.setReceiveUserId(accountCash.getUserId());
					center.setReceiveUserId(accountCashOld.getUserId());
					center.setMessageTitle("提现失败");
					center.setSendUserId(accountCash.getVerifyUserid());
					messageCenterService.send(center, Notice.WITHDRAW_NO);
					
					
					dwzResult=new DwzResult(bool, bool?"提现审核操作成功":"提现审核操作失败",DwzResult.CALLBACK_CLOSECURRENT,String.valueOf("1001"));
				}
			}else{
				throw new RuntimeException("提现审核错误！！！");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return dwzResult;
	}
	
}
