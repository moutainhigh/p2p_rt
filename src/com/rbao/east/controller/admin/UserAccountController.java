package com.rbao.east.controller.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.StringUtil;

/**
 * 资金账号总览
 * @author Sandy
 *
 */
@Controller
@RequestMapping("userAccount/")
public class UserAccountController extends BaseController{
	
	private static Logger logger = LoggerFactory
			.getLogger(UserAccountController.class);
	
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private SysModuleService moduleService;
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getUserAccountList")
	public String getUserAccountList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);		
		PageModel  pageModel=userAccountService.getUserAccountList(paramsMap);
		model.addAttribute("pm",pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		
		paramsMap.put("userId", String.valueOf(user.getId()));
		
		model.addAttribute("right_id", paramsMap.get("right_id"));//刷新
    	// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		return "fundAccount/userAccountList";
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> paramsMap=this.getParameters(request);
		try {
		List<UserAccount> userAccounts=userAccountService.selectUserAccountList(paramsMap);
		if (userAccounts.size()>500000) {
			userAccounts = userAccounts.subList(0, 500000);
		}
		//String[] titles={"用户名称","真实姓名","总资金","可用资金","冻结金额","待收金额","待还金额","待收利息","待还利息","净资产","总支出奖励","总支出利息","总获得奖励","总获得利息"};
		String[] titles={"序号","用户名称","真实姓名","总资金","可用资金","冻结金额","待收本金","待收利息","待还金额","净资产"};
		List<String[]> contents=new ArrayList<String[]>();
		for (UserAccount userAccount : userAccounts) {
			String [] strList=new String[titles.length];
			
			strList[0]=StringUtil.toString(userAccount.getId());
			strList[1]=StringUtil.toString(userAccount.getUserAccount());
			strList[2]=StringUtil.toString(userAccount.getUserRealname());
			strList[3]=StringUtil.toString(userAccount.getAllMoney());
			strList[4]=StringUtil.toString(userAccount.getAvailableMoney());
			strList[5]=StringUtil.toString(userAccount.getUnavailableMoney());
			strList[6]=StringUtil.toString(userAccount.getWaitRepossessedCapital());
			strList[7]=StringUtil.toString(userAccount.getWaitRepossessedInterest());
			strList[8]=StringUtil.toString(userAccount.getWaitRepayCapital());
			strList[9]=StringUtil.toString(userAccount.getAllMoney().subtract(userAccount.getWaitRepayCapital()));
			
			
			contents.add(strList);
		}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流     
			response.setHeader("Content-disposition", "attachment; filename="+ 
							new String("userAccountList.xls".getBytes("UTF-8"),"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型   
			ExcelUtil.buildExcel(os,"用户账户记录",titles,contents);
		} catch (IOException e) {
			logger.error("导出userAccountListExcel失败",e);
			
		}
	}
	
}
	
	


