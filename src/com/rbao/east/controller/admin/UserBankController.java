package com.rbao.east.controller.admin;

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
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.AllBank;
import com.rbao.east.entity.RelaseCard;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserBank;
import com.rbao.east.service.AllBankService;
import com.rbao.east.service.RelaseBankService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserBankService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;

/**
 * 银行账号管理
 * @author Sandy
 *
 */
@Controller
@RequestMapping("userBank/")
public class UserBankController extends BaseController{
	
	private static Logger logger = LoggerFactory
			.getLogger(UserBankController.class);
	@Autowired
	private UserBankService userBankService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private UserService userService;
	@Autowired
	private AllBankService allBankService;
	@Autowired
	private RelaseBankService relaseBankService;
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getUserBankList")
	public String getUserBankList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);		
		PageModel  pageModel=userBankService.getUserBankList(paramsMap);
		model.addAttribute("pm",pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		
		paramsMap.put("userId", String.valueOf(user.getId()));
		
		model.addAttribute("right_id", paramsMap.get("right_id"));//刷新
    	// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		
		return "fundAccount/userBankList";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "addUserBank")
	public String addUserBank(HttpServletRequest request, HttpServletResponse response,
			Model model) {
			Map<String,String> paramsMap = getParameters(request);
			model.addAttribute("right_id",paramsMap.get("right_id"));
			
			List<AllBank> allBankList=allBankService.getAllList();
			model.addAttribute("allBankList",allBankList);
			return "fundAccount/addUpdateUserBank";
		
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateUserBank")
	public String updateUserBank(HttpServletRequest request, HttpServletResponse response,
			Model model) {
			Map<String,String> paramsMap = getParameters(request);
			model.addAttribute("right_id",paramsMap.get("right_id"));
			
			List<AllBank> allBankList=allBankService.getAllList();
			model.addAttribute("allBankList",allBankList);
			
			Integer userBankId=Integer.parseInt(paramsMap.get("id"));
			UserBank userBank= userBankService.getById(userBankId);
			model.addAttribute("userBank",userBank);
			
			Integer userId=userBank.getUserId();
			User user=userService.getById(userId);
			model.addAttribute("userAccount",user.getUserAccount());
			
			
			return "fundAccount/addUpdateUserBank";
		
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveOrUpdate")
	public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response,
			UserBank userBank) {
		Map<String,String> paramsMap = getParameters(request);
		boolean flag=false;
		User user=userService.selectByUserUid(paramsMap.get("userAccount"));
		if(user==null){
			SpringUtils.renderDwzResult(response, false, "用户名不存在！",DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id").toString());
		}else{
			UserBank bank=null;
			try{
				if(userBank.getId()!=null){
					bank=this.userBankService.getById(userBank.getId());
					userBank.setUserId(user.getId());
					userBank.setBankAddip(this.getIpAddr(request));
					flag=userBankService.saveOrUpdate(userBank);
				}else{
					paramsMap.put("userId", user.getId().toString());
					bank=this.userBankService.getByUserId(paramsMap); 
					if(bank==null){
						userBank.setUserId(user.getId());
						userBank.setBankAddip(this.getIpAddr(request));
						flag=userBankService.saveOrUpdate(userBank);
					}else{
						logger.error("该用户已添加银行卡");
						SpringUtils.renderDwzResult(response, false, "该用户已添加银行卡！",DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id").toString());
					}
				}
			}catch (Exception e) {
				logger.error("银行账号添加/修改失败！",e);
				flag = false;
			}
			if(flag)
			SpringUtils.renderDwzResult(response, flag, flag?"银行账号操作成功":"银行账号操作失败",DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id").toString());
		}
		
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response
			) {
		Map<String,String> paramsMap = getParameters(request);
		boolean flag=false;
		try{
			Integer userBankId=Integer.parseInt(paramsMap.get("id"));
			flag=userBankService.deleteById(userBankId);
		}catch (Exception e) {
			
			logger.error("银行账号添加/修改失败！",e);
			flag = false;
		}
		
		SpringUtils.renderDwzResult(response, flag, flag?"银行账号操作成功":"银行账号操作失败","",paramsMap.get("right_id").toString(),"userBank/v_getUserBankList?right_id="+paramsMap.get("right_id").toString());

	}
	
	
	/**
	 * 导出Excel
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"toExcel")
	public void logToExcel(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		params.put("numPerPage", "10000");
		
		try {
			PageModel pm = userBankService.getUserBankList(params);
			List<UserBank> ars = pm.getList();

			String[] titles = { "序号", "用户名","真实姓名", "银行卡号", "开户银行", "银行分行", "创建时间",
					"创建IP地址"};

			List<String[]> contents = new ArrayList<String[]>();
			for (UserBank ub : ars) {
				String[] conList = new String[titles.length];
				conList[0] = StringUtil.toString(ub.getId());
				conList[1] = StringUtil.toString(ub.getUserAccount());
				conList[2] = StringUtil.toString(ub.getUserRealname());
				conList[3] = StringUtil.toString(ub.getBankAccount());
				conList[4] = StringUtil.toString(ub.getBankName());
				conList[5] = StringUtil.toString(ub.getBankBranch());
				conList[6] = StringUtil.toString(DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", ub.getBankAddtime()));
				conList[7] = StringUtil.toString(ub.getBankAddip());

				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("userBank-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "帐户银行统计数据", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getRelaseBankList")
	public String getRelaseBankList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);		
		PageModel pm = relaseBankService.getRelaseBankList(paramsMap);
		model.addAttribute("pm",pm);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		
		paramsMap.put("userId", String.valueOf(user.getId()));
		
		model.addAttribute("right_id", paramsMap.get("right_id"));//刷新
    	// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		
		return "fundAccount/releaseBankList";
	}
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateRelaseBank")
	public String updateRelaseBank(HttpServletRequest request, HttpServletResponse response,
			Model model) {
			Map<String,String> paramsMap = getParameters(request);
			model.addAttribute("right_id",paramsMap.get("right_id"));
			
			Integer relaseCardId = Integer.parseInt(paramsMap.get("id"));
			RelaseCard relaseCard= relaseBankService.getById(relaseCardId);
			model.addAttribute("relaseCard",relaseCard);
			return "fundAccount/addUpdateRelaseBank";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveOrUpdateRelaseBank")
	public void saveOrUpdateRelaseBank(HttpServletRequest request, HttpServletResponse response,
			Model model) {
			Map<String,String> paramsMap = getParameters(request);
			model.addAttribute("right_id",paramsMap.get("right_id"));
			String result = paramsMap.get("result");
			Integer relaseCardId = Integer.parseInt(paramsMap.get("id"));
			RelaseCard relaseCard = relaseBankService.getById(relaseCardId);
			boolean flag = false;
			if(result != null && !"".equals(result)){
				relaseCard.setResult(result);
				flag = relaseBankService.saveOrUpdate(relaseCard);
				if(flag && "2".equals(result)){
					flag = userBankService.deleteById(relaseCard.getUserBankId());
				}
			}
			if(flag)
			SpringUtils.renderDwzResult(response, flag, flag?"银行账号解绑申诉操作成功":"银行账号解绑申诉操作失败",DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id").toString());
	}
}
