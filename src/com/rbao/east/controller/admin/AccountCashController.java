package com.rbao.east.controller.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.baofoo.sdk.api.BaoFooApi;
import com.baofoo.sdk.demo.base.TransContent;
import com.baofoo.sdk.demo.base.request.TransReqBF0040001;
import com.baofoo.sdk.demo.base.response.TransRespBF0040001;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.dao.AccountCashDao;
import com.rbao.east.entity.AccountCash;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserBank;
import com.rbao.east.lianlianpayutil.TraderRSAUtil;
import com.rbao.east.service.AccountCashService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserBankService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.PropertiesUtil;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.SysCacheUtils;
import com.rbao.east.web.llpay.security.Md5Algorithm;
/**
 * 提现操作
 * */
@Controller
@RequestMapping("accountCash/")
public class AccountCashController extends BaseController{
	
	private static Logger logger = LoggerFactory
			.getLogger(AccountCashController.class);
	
	
	@Autowired
	private AccountCashService accountCashService;
	
	
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserBankService userBankService;
	@Autowired
	private AccountCashDao accountCashDao;
	
	
	
	/**
	 * 获取提现列表
	 * */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAccountCashList")
	public String getAccountCashList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, String> param=this.getParameters(request);
		String target="";
		User user=this.loginAdminUser(request);
		try {
			param.put("userId", user.getId().toString());
			if(param.get("cashStatus").equals("4")){
				param.put("cashType", "1");
			}else{
				param.put("cashType", "0");
			}
			if(param.get("cashStatus").equals("5")){
				param.put("cashType", "2");
			}
			
			//提现成功或失败的，取所有类型
			if (param.get("cashStatus").equals("1") || param.get("cashStatus").equals("2")) {
				param.remove("cashType");
			}
			
			List<SysModule> righSubtList=moduleService.getRightGroupList(param);
			result.put("righSubtList", righSubtList);
		
			if(param.containsKey("cashStatus")){
				param.put("cashStatus",param.get("cashStatus"));
			}
			PageModel pageModel = accountCashService.getAccountCashList(param);
			result.put("pm", pageModel);
			target="accountCash/showAccountCashList";
			result.putAll(param);
			result.put("code", 1);
			logger.info("显示提现列表成功！");
		} catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("显示提现列表失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		request.getSession(true).setAttribute("queryParams", param);
		return target;
	}
	//H5提现待审核
	@RequestMapping(Constants.PRE_PATH_VIEW + "getH5AccountCashList")
	public String getH5AccountCashList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, String> param=this.getParameters(request);
		String target="";
		User user=this.loginAdminUser(request);
		try {
			param.put("userId", user.getId().toString());
			param.put("cashType", "1");
			List<SysModule> righSubtList=moduleService.getRightGroupList(param);
			result.put("righSubtList", righSubtList);
		
			if(param.containsKey("rbCashStatus")){
				param.put("cashStatus",param.get("rbCashStatus"));
			}
			PageModel pageModel = accountCashService.getAccountCashList(param);
			result.put("pm", pageModel);
			model.addAttribute("cashtype", 1);
			target="accountCash/showAccountCashList";
			result.putAll(param);
			result.put("code", 1);
			logger.info("显示提现列表成功！");
		} catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("显示提现列表失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		request.getSession(true).setAttribute("queryParams", param);
		return target;
	}
	//H5提现待审核
		@RequestMapping(Constants.PRE_PATH_VIEW + "getAppAccountCashList")
		public String getAppAccountCashList(HttpServletRequest request, HttpServletResponse response,
				Model model) {
			Map<String, Object> result=new HashMap<String, Object>();
			Map<String, String> param=this.getParameters(request);
			String target="";
			User user=this.loginAdminUser(request);
			try {
				param.put("userId", user.getId().toString());
				param.put("cashType", "2");
				List<SysModule> righSubtList=moduleService.getRightGroupList(param);
				result.put("righSubtList", righSubtList);
			
				if(param.containsKey("rbCashStatus")){
					param.put("cashStatus",param.get("rbCashStatus"));
				}
				PageModel pageModel = accountCashService.getAccountCashList(param);
				result.put("pm", pageModel);
				model.addAttribute("cashtype", 2);
				target="accountCash/showAccountCashList";
				result.putAll(param);
				result.put("code", 1);
				logger.info("显示提现列表成功！");
			} catch (Exception e) {
				
				result.put("code", 0);
				result.put("message", "数据操作出现异常，请稍后再试！");
				logger.error("显示提现列表失败，异常信息:" + e);
			}
			this.setParameters(result, request);
			request.getSession(true).setAttribute("queryParams", param);
			return target;
		}
	/**
	 * 更新提现操作
	 * */
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateAccountCash")
	public String updateAccountCash(HttpServletRequest request, HttpServletResponse response,
			Model model,AccountCash accountCashs) {
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, String> param=this.getParameters(request);
		User user=this.loginAdminUser(request);
		DwzResult res = null;
		boolean flag=false;
		String ipAddress = null;
		String target="";
		try {
			if("toJsp".equals(param.get("type").toString())){
				int id=Integer.parseInt(param.get("supportID").toString());
				AccountCash accountCash=accountCashService.getAccountCashById(id);
				model.addAttribute("accountCash",accountCash);
				result.put("right_id", param.get("right_id"));
				result.put("cashStatus", param.get("cashStatus"));
				target="accountCash/updateAccountCash";
				//恢复到app线上打款
			}else if("update".equals(param.get("type").toString())&&accountCashs.getCashType()==0){
				//统一为线下打款，不用分别处理h5及app提现
//			}else if("update".equals(param.get("type").toString())){
				result.putAll(param);
				 ipAddress=this.getIpAddr(request);
				result.put("ipAddress", ipAddress);
				result.put("user", user);
				result.put("verifyRemark", accountCashs.getVerifyRemark());
				result.put("noOrder", accountCashs.getNoOrder());
				 res=accountCashService.updateAccountCashById(result);
				 if(res!=null&&res.getStatusCode().equals("200")){
					 flag=true;
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
					
					SpringUtils.renderJson(response, new DwzResult(flag, flag?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()));
					target=null;
			}
			//统一为线下打款，不用分别处理h5及app提现（已恢复）
			else if("update".equals(param.get("type").toString())&&accountCashs.getCashType()==1){
				AccountCash accountCash=accountCashService.getAccountCashById(accountCashs.getId());
				if(param.get("cashStatuss").equals("2")){
					flag=accountCashService.exeCashFailure(accountCash.getNoOrder(),user.getUserAccount(),this.getIpAddr(request),param.get("verifyRemark"));
					SpringUtils.renderJson(response, new DwzResult(flag, flag?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()));
					target=null;
				}else if(accountCash.getCashStatus()==4&&param.get("cashStatuss").equals("1")){
					//处理H5(提现中)成功的
					Map<String,Object> params =new HashMap<String,Object>();
					params.put("noOrder", accountCash.getNoOrder());
					params.put("cashMoney", accountCash.getCashAccount());
					params.put("info_order", "宝付提现");
					params.put("cashStatuss", 1);
					params.put("ipAddress", accountCash.getCashAddip());
					res = accountCashService.updateH5AccountCashById(params);
					if(res!=null&&res.getStatusCode().equals("200")){
						 flag=true;
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
						
						SpringUtils.renderJson(response, new DwzResult(flag, flag?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()));
						target=null;
				} else {
					//审核H5提现
					User frontUser = userService.selectByPrimaryKey(accountCashs.getUserId());
					Map<String, String> params = new HashMap<String, String>();
					params.put("userId", accountCashs.getUserId().toString());
					UserBank userBank = this.userBankService.getByUserId(params);
					String basePath=SysCacheUtils.getSysConfig().getSysWebsitedomain();
					String [] strs = userBank.getBankCity().split("\\|");
						
					/* 宝付提现 start */					
					TransReqBF0040001 transReqData = new TransReqBF0040001();
					transReqData.setTrans_no(accountCashs.getNoOrder());
					transReqData.setTrans_money(accountCashs.getCashAccount().toString());
					transReqData.setTo_acc_name(frontUser.getUserRealname());
					transReqData.setTo_acc_no(accountCashs.getCashBankAccount());
					transReqData.setTo_bank_name(accountCashs.getCashBank());
					transReqData.setTo_pro_name(strs[0]);
					transReqData.setTo_city_name(strs[1]);
					transReqData.setTo_acc_dept(accountCashs.getCashBranch());
					transReqData.setTrans_summary("");					
					TransContent<TransRespBF0040001> str2Obj = BaoFooApi.BF004001(transReqData);
					
					if ("0000".equals(str2Obj.getTrans_head().getReturn_code())) {
			        	accountCash.setCashStatus(4);
						accountCash.setVerifyName(user.getUserAccount());
						accountCash.setCashAddip(ipAddress);
						flag=accountCashDao.update("updateByPrimaryKeySelective", accountCash);
					}
					
					if ("0000".equals(str2Obj.getTrans_head().getReturn_code()) && flag){
                    	  flag=true;
					} else {
						OperatorLog operatorLog = new OperatorLog();
  						operatorLog.setLogUserid(user.getUserAccount());
  						operatorLog.setOperatorTitle("H5提现失败");
  						operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
  						operatorLog.setOperatorParams(str2Obj.obj2Str(str2Obj));
  						operatorLog.setOperatorReturn("H5提现申请失败");
  						operatorLog.setOperatorStatus(300);
  						operatorLog.setLinkUrl(getURI(request));
  						operatorLogService.addAdminLog(operatorLog);
					}
					/* 宝付提现 end */
					
					SpringUtils.renderJson(response, new DwzResult(flag, flag?"提现审核操作成功":"提现审核操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()));
				}
				
				target=null;
			}else if("update".equals(param.get("type").toString())&&accountCashs.getCashType()==2){
				AccountCash accountCash=accountCashService.getAccountCashById(accountCashs.getId());
				if(param.get("cashStatuss").equals("2")){
					flag=accountCashService.exeCashFailure(accountCash.getNoOrder(),user.getUserAccount(),this.getIpAddr(request),param.get("verifyRemark"));
					SpringUtils.renderJson(response, new DwzResult(flag, flag?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()));
					target=null;
				}else if(accountCash.getCashStatus()==5&&param.get("cashStatuss").equals("1")){
					//处理App(提现中)成功的
					Map<String,Object> params =new HashMap<String,Object>();
					params.put("noOrder", accountCash.getNoOrder());
					params.put("cashMoney", accountCash.getCashAccount());
					params.put("info_order", "宝付提现");
					params.put("cashStatuss", 1);
					params.put("ipAddress", accountCash.getCashAddip());
					res = accountCashService.updateH5AccountCashById(params);
					if(res!=null&&res.getStatusCode().equals("200")){
						 flag=true;
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
						
						SpringUtils.renderJson(response, new DwzResult(flag, flag?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()));
						target=null;
				} else {
					//审核App提现
					User frontUser = userService.selectByPrimaryKey(accountCashs.getUserId());
					Map<String, String> params = new HashMap<String, String>();
					params.put("userId", accountCashs.getUserId().toString());
					UserBank userBank = this.userBankService.getByUserId(params);
					String basePath=SysCacheUtils.getSysConfig().getSysWebsitedomain();
					String [] strs = userBank.getBankCity().split("\\|");
						
					/* 宝付提现 start */
					TransReqBF0040001 transReqData = new TransReqBF0040001();
					transReqData.setTrans_no(accountCashs.getNoOrder());
					transReqData.setTrans_money(accountCashs.getCashAccount().toString());
					transReqData.setTo_acc_name(frontUser.getUserRealname());
					transReqData.setTo_acc_no(accountCashs.getCashBankAccount());
					transReqData.setTo_bank_name(accountCashs.getCashBank());
					transReqData.setTo_pro_name(strs[0]);
					transReqData.setTo_city_name(strs[1]);
					transReqData.setTo_acc_dept(accountCashs.getCashBranch());
					transReqData.setTrans_summary("");					
					TransContent<TransRespBF0040001> str2Obj = BaoFooApi.BF004001(transReqData);
					
					if ("0000".equals(str2Obj.getTrans_head().getReturn_code())) {
			        	accountCash.setCashStatus(5);
						accountCash.setVerifyName(user.getUserAccount());
						accountCash.setCashAddip(ipAddress);
						flag=accountCashDao.update("updateByPrimaryKeySelective", accountCash);
					}
					
					if ("0000".equals(str2Obj.getTrans_head().getReturn_code()) && flag){
                    	  flag=true;
					} else {
						OperatorLog operatorLog = new OperatorLog();
  						operatorLog.setLogUserid(user.getUserAccount());
  						operatorLog.setOperatorTitle("App提现失败");
  						operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
  						operatorLog.setOperatorParams(str2Obj.obj2Str(str2Obj));
  						operatorLog.setOperatorReturn("App提现申请失败");
  						operatorLog.setOperatorStatus(300);
  						operatorLog.setLinkUrl(getURI(request));
  						operatorLogService.addAdminLog(operatorLog);
					}
					/* 宝付提现 end */
					
					SpringUtils.renderJson(response, new DwzResult(flag, flag?"提现审核操作成功":"提现审核操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()));
				}
				
				target=null;
			}

			result.put("code", 1);
		} catch (Exception e) {
			SpringUtils.renderDwzResult(response, false,e.getLocalizedMessage()
				,"",param.get("right_id").toString()); 
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			e.printStackTrace();
			logger.error("修改提现列表失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return target;
	}
	
	private static String genSign(JSONObject reqObj)
    {
        String sign = reqObj.getString("sign");
        String sign_type = reqObj.getString("sign_type");
        // // 生成待签名串
        String sign_src = genSignData(reqObj);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]待签名原串"
                + sign_src);
        if ("MD5".equals(sign_type))
        {
            sign_src += "&key=" + PropertiesUtil.get("cash.TRADER_MD5_KEY");
            return signMD5(sign_src);
        }
        if ("RSA".equals(sign_type))
        {
            return getSignRSA(sign_src);
        }
        return null;
    }

    private static String signMD5(String signSrc)
    {

        try
        {
            return Md5Algorithm.getInstance().md5Digest(
                    signSrc.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * RSA签名验证
     * 
     * @param reqObj
     * @return
     */
    public static String getSignRSA(String sign_src)
    {
        return TraderRSAUtil.sign(PropertiesUtil.get("cash.prikeyvalue"), sign_src);

    }

    /**
     * 生成待签名串
     * 
     * @param paramMap
     * @return
     */
    public static String genSignData(JSONObject jsonObject)
    {
        StringBuffer content = new StringBuffer();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(jsonObject.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++)
        {
            String key = (String) keys.get(i);
            // sign 和ip_client 不参与签名
            if ("sign".equals(key))
            {
                continue;
            }
            String value = (String) jsonObject.getString(key);
            // 空串不参与签名
            if (null == value)
            {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&"))
        {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "shwoAccountLogList")
	public String shwoAccountLogList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		AccountCash accountCash=accountCashService.getAccountCashById(Integer.parseInt(param.get("supportID").toString()));
		return "redirect:../accountLog/v_getAccountLogList?right_id=329&userId="+accountCash.getUserId();
	}
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		try {
			Map<String, String> param=(Map)request.getSession().getAttribute("queryParams");
		param.put("numPerPage", "500000");
		PageModel page=accountCashService.getAccountCashList(param);
		List<AccountCash> accountCashs=page.getList();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//String[] titles={"用户名称","真实姓名","提现账号","提现银行","提现支行","提现总额","到账金额","手续费","申请时间","提现状态","申请IP","审核人","审核备注","审核时间"};
		String[] titles={"序号","用户名称","真实姓名","手机号码","订单号","提现账号","提现银行","提现支行","银行地址","提现总额","到账金额","手续费","申请时间","提现状态"};
		List<String[]> contents=new ArrayList<String[]>();
		int index=1;
		for (AccountCash accountCash : accountCashs) {
			String status="";
			String [] strList=new String[14];
			int cashStatus=accountCash.getCashStatus();
			switch (cashStatus) {
			case 0:
				status="待审";
				break;
			case 1:
				status="成功";
				break;
			case 2:
				status="失败";
				break;
			case 4:
				status="提现中";
				break;
			default:
				status="失败";
				break;
			}

			strList[0]=(page.getPageSize()*(page.getCurrentPage()-1)+index)+"";
			index++;
			
			strList[1]=accountCash.getUserAccount();
			strList[2]=accountCash.getUserRealname();
			strList[3]=accountCash.getUserPhone();
			strList[4]=accountCash.getNoOrder();
			if(accountCash.getCashBankAccount() != null ){
				strList[5]=accountCash.getCashBankAccount();
			}else{
				strList[5]="";
			}
			strList[6]=accountCash.getCashBank();
			strList[7]=accountCash.getCashBranch();
			strList[8]=accountCash.getBankCity().split("\\|")[0]+"|"+accountCash.getBankCity().split("\\|")[1];
			strList[9]=accountCash.getCashTotal().toString();
			strList[10]=accountCash.getCashAccount().toString();
			strList[11]=accountCash.getCashFee().toString();
			strList[12]=dateFormat.format(accountCash.getCashAddtime());
			strList[13]=status;
			/*if(accountCash.getCashAddip() != null ){
				strList[10]=accountCash.getCashAddip();
			}else{
				strList[10]="";
			}
			if(accountCash.getVerifyName() != null ){
				strList[11]=accountCash.getVerifyName();
			}else{
				strList[11]="";
			}
			if(accountCash.getVerifyRemark() != null ){
				strList[12]=accountCash.getVerifyRemark();
			}else{
				strList[12]="";
			}
			if( null != accountCash.getVerifyTime() ){
				strList[13]=dateFormat.format(accountCash.getVerifyTime());
			}else{
				strList[13]="";
			}*/
			contents.add(strList);
		}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流     
			response.setHeader("Content-disposition", "attachment; filename="+ 
							new String("accountCashList.xls".getBytes("UTF-8"),"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型   
			ExcelUtil.buildExcel(os,"用户提现记录",titles,contents);
		} catch (IOException e) {
			logger.error("导出excel失败",e);
			
		}
	}
	
	
	
}

