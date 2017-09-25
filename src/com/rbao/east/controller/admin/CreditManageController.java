package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.AccountDeduct;
import com.rbao.east.entity.CreditLog;
import com.rbao.east.entity.CreditRank;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserCredit;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.CreditRankService;
import com.rbao.east.service.CreditTypeService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;

/**
 * 积分管理
 * @author Liutq
 *
 */
@Controller
@RequestMapping("credit/")
public class CreditManageController extends BaseController{
	
	private static Logger logger = LoggerFactory
			.getLogger(CreditManageController.class);
	
	@Autowired
	private CreditTypeService creditTypeService;
	@Autowired
	private CreditRankService creditRankService;
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	private SysModuleService moduleService;

	
	/**
	 * 积分类型管理
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "deleteCreditType")
	public void deleteCreditType(HttpServletRequest request, HttpServletResponse response
			) {
		Map<String,String> paramsMap = getParameters(request);
		boolean succ = false;
		try {
			succ = creditTypeService.deleteById(Integer.parseInt(paramsMap.get("supportID")));
		} catch (Exception e) {
			
			logger.error("删除出错："+paramsMap.get("supportID"),e);
		}
		
		SpringUtils.renderDwzResult(response, succ, succ?"删除成功":"删除失败");
	}
	@RequestMapping(Constants.PRE_PATH_VIEW + "addCreditType")
	public String addCreditType(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		request.setAttribute("right_id",paramsMap.get("right_id"));
		return "creditmanage/updatecredittype";
	}
	@RequestMapping(Constants.PRE_PATH_VIEW + "getCreditType")
	public String getCreditType(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		//request.setAttribute("right_id", request.getAttribute("right_id"));
		CreditType creditType = creditTypeService.getById(Integer.parseInt(paramsMap.get("supportID")));
		
		model.addAttribute("creditType", creditType);
		this.setParameters(paramsMap, request);
		return "creditmanage/updatecredittype";
	}
	@RequestMapping(Constants.PRE_PATH_VIEW + "getListCreditType")
	public String listCreditType(HttpServletRequest request, HttpServletResponse response,
			Model model,CreditType creditType) {
		User loginUser = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);
		Map<String, Object> result=new HashMap<String, Object>();
		paramsMap.put("userId", loginUser.getId().toString());
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		result.put("righSubtList", righSubtList);
		result.put("right_id", paramsMap.get("right_id"));
		PageModel pageModel = creditTypeService.getList(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",creditType);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		
		this.setParameters(result, request);
		return "creditmanage/credittypelist";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveCreditType")
	public void saveCreditType(HttpServletRequest request, HttpServletResponse response,
			CreditType creditType) {
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		User loginUser = loginAdminUser(request);
		try {
			if(creditType.getId()==null){
				String ip = this.getIpAddr(request); 
				creditType.setCreditOptIp(ip);
				/*creditType.setCreditOptUserid(loginUser.getId());*/
				succ = creditTypeService.add(creditType);
			}else{
				String ip = this.getIpAddr(request); 
				creditType.setCreditOptUpdatetime(new Date());
				creditType.setCreditOptUpdateip(ip);
				/*creditType.setCreditOptUserid(loginUser.getId());*/
				succ = creditTypeService.save(creditType);
			}
		} catch (Exception e) {
			
			/*logger.error("添加用户出错："+creditType.getName(),e);*/
		}
		SpringUtils.renderDwzResult(response, succ, succ?"成功":"失败"
			,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
	}
	
	/**
	 * 积分等级管理
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "deleteCreditRank")
	public void deleteCreditRank(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> paramsMap = getParameters(request);
		boolean succ = false;
		try {
			succ = creditRankService.deleteById(Integer.parseInt(paramsMap.get("supportID")));
		} catch (Exception e) {
			
			logger.error("删除出错："+paramsMap.get("supportID"),e);
		}
		
		SpringUtils.renderDwzResult(response, succ, succ?"删除成功":"删除失败");
	}
	@RequestMapping(Constants.PRE_PATH_VIEW + "addCreditRank")
	public String addCreditRank(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		return "creditmanage/updatecreditrank";
	}
	@RequestMapping(Constants.PRE_PATH_VIEW + "getCreditRank")
	public String getCreditRank(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		CreditRank creditRank = creditRankService.getById(Integer.parseInt(paramsMap.get("supportID")));
		
		model.addAttribute("creditRank", creditRank);
		this.setParameters(paramsMap, request);
		return "creditmanage/updatecreditrank";
	}
	@RequestMapping(Constants.PRE_PATH_VIEW + "getListCreditRank")
	public String listCreditRank(HttpServletRequest request, HttpServletResponse response,
			Model model,CreditRank creditRank) {
		User loginUser = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);
		Map<String, Object> result=new HashMap<String, Object>();
		paramsMap.put("userId", loginUser.getId().toString());
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		result.put("righSubtList", righSubtList);
		result.put("right_id", paramsMap.get("right_id"));
		PageModel pageModel = creditRankService.getList(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",creditRank);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		this.setParameters(result, request);
		return "creditmanage/creditranklist";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveCreditRank")
	public void saveCreditRank(HttpServletRequest request, HttpServletResponse response,
			CreditRank creditRank) {
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		try {
			if(creditRank.getId()==null){
				creditRank.setRankAddtime(new Date());
				String ip = this.getIpAddr(request); 
				creditRank.setRankAddip(ip);
				succ = creditRankService.add(creditRank);
			}else{
				succ = creditRankService.save(creditRank);
			}
		} catch (Exception e) {
			
			/*logger.error("添加用户出错："+creditRank.getName(),e);*/
		}

		
		SpringUtils.renderDwzResult(response, succ, succ?"成功":"失败"
			,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
	}
	

	/**
	 * 用户积分管理
	 * @param request
	 * @param response
	 * @param model
	 * @param creditRank
	 * @return
	 */
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getListUserCredit")
	public String listUserCredit(HttpServletRequest request, HttpServletResponse response,
			Model model,UserCredit userCredit) {
		User loginUser = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);
		Map<String, Object> result=new HashMap<String, Object>();
		paramsMap.put("userId", loginUser.getId().toString());
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		result.put("righSubtList", righSubtList);
		result.put("right_id", paramsMap.get("right_id"));
		PageModel pageModel = userCreditService.getList(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",userCredit);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		this.setParameters(result, request);
		return "creditmanage/usercreditlist";
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcel")
	public void toExcel(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> maps=getParameters(request);
		maps.put("numPerPage", "500000");
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			PageModel pageModel = userCreditService.getList(maps);
			List list=pageModel.getList();
			
			String[] titles={"序号","用户名称","信用积分","可用积分","修改人","添加IP","添加时间","修改IP","修改时间"};
			List<String[]> contents = new ArrayList<String[]>();
			
			for (int i = 0; i < list.size(); i++) {		
				Map map=(Map)list.get(i);
				String[] str=new String[titles.length];
				str[0]=StringUtil.toString(map.get("id"));
				str[1]=StringUtil.toString(map.get("uUserAccount"));
				//str[2]=StringUtil.toString(map.get("rankIcon"));
				str[2]=StringUtil.toString(map.get("creditValue"));
				str[3]=StringUtil.toString(map.get("creditValueUsable"));
				str[4]=StringUtil.toString(map.get("operUserAccount"));
				str[5]=StringUtil.toString(map.get("creditAddIp"));
				
				if(map.get("creditAddTime")!=null){
					str[6]=StringUtil.toString(sdf.format((Date)map.get("creditAddTime")));
				}else{
					str[6]=StringUtil.toString("");
				}
				
				str[7]=StringUtil.toString(map.get("creditUpdateIp"));
				
				if(map.get("creditUpdateTime")!=null){
					str[8]=StringUtil.toString(sdf.format((Date)map.get("creditUpdateTime")));
				}else{
					str[8]=StringUtil.toString("");
				}
				
				contents.add(str);
			}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("credit-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "用户积分管理", titles, contents);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel失败");
		}
		
		
	}
	
	
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "deleteUserCredit/{id}")
	public void deleteUserCredit(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") int id) {
		
		boolean succ = false;
		try {
			succ = userCreditService.deleteById(id);
		} catch (Exception e) {
			
			logger.error("删除出错："+id,e);
		}
		
		SpringUtils.renderDwzResult(response, succ, succ?"删除成功":"删除失败");
	}
	@RequestMapping(Constants.PRE_PATH_VIEW + "getUserCredit")
	public String getUserCredit(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		UserCredit userCredit = userCreditService.getById(Integer.parseInt(paramsMap.get("supportID")));
		
		model.addAttribute("userCredit", userCredit);
		this.setParameters(paramsMap, request);
		return "creditmanage/updateusercredit";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveUserCredit")
	public void saveUserCredit(HttpServletRequest request, HttpServletResponse response,
			UserCredit userCredit) {
		User loginUser = loginAdminUser(request);
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		try {
			userCredit.setCreditUpdateTime(new Date());
			String ip = this.getIpAddr(request); 
			userCredit.setCreditUpdateIp(ip);
			userCredit.setCreditOperator(loginUser.getId());
			UserCredit uc = userCreditService.getById(userCredit.getId());
			succ = userCreditService.save(userCredit);
			if(succ){
				//添加积分记录
				CreditLog creditLog = new CreditLog();
				param.get("creditValue");
				creditLog.setUserId(uc.getUserId());
				creditLog.setCreditTypeId(25);
				if(uc.getCreditValue()>Integer.parseInt(param.get("creditValue"))){
					creditLog.setCreditOperateType(2);
					creditLog.setCreditOperateValue(uc.getCreditValue()-Integer.parseInt(param.get("creditValue")));
				}else{
					creditLog.setCreditOperateType(1);
					creditLog.setCreditOperateValue(Integer.parseInt(param.get("creditValue"))-uc.getCreditValue());
				}
				creditLog.setCreditOperateRemark(param.get("remark"));
				creditLog.setCreditOperateDatetime(new Date());
				creditLog.setCreditOperateIp(ip);
				creditLog.setCreditOperater(loginUser.getId());
				succ = creditLogService.add(creditLog);
			}
			
			
		} catch (Exception e) {
			
			/*logger.error("添加用户出错："+creditRank.getName(),e);*/
		}
		SpringUtils.renderDwzResult(response, succ, succ?"成功":"失败"
			,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
	}
	
	/**
	 * 积分记录
	 * @param request
	 * @param response
	 * @param model
	 * @param userCredit
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getListCreditLog")
	public String listCreditLog(HttpServletRequest request, HttpServletResponse response,
			Model model,CreditLog creditLog) {
		User loginUser = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);
		Map<String, Object> result=new HashMap<String, Object>();
		paramsMap.put("userId", loginUser.getId().toString());
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		result.put("righSubtList", righSubtList);
		result.put("right_id", paramsMap.get("right_id"));
		PageModel pageModel = creditLogService.getPagedList(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		this.setParameters(result, request);
		return "creditmanage/creditloglist";
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcelCreditLog")
	public void toExcelCreditLog(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> maps=getParameters(request);
		User loginUser = loginAdminUser(request);
		maps.put("userId", loginUser.getId().toString());
		maps.put("numPerPage", "500000");
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			PageModel pageModel = creditLogService.getPagedList(maps);
			List list=pageModel.getList();
			
			String[] titles={"序号","用户名称","积分类型","变动类型","变动积分","时间","IP","操作人","备注"};
			List<String[]> contents = new ArrayList<String[]>();
			
			for (int i = 0; i < list.size(); i++) {		
				CreditLog clog=(CreditLog)list.get(i);
				String[] str=new String[titles.length];
				str[0]=StringUtil.toString(clog.getId());
				
				if(clog.getUser()!=null){
					str[1]=StringUtil.toString(clog.getUser().getUserAccount());
				}else{
					str[1]=StringUtil.toString("");
				}
				
				str[2]=StringUtil.toString(clog.getCreditType().getCreditName());
				
				if(clog.getCreditOperateType()==1){
					str[3]=StringUtil.toString("增加");
				}else if(clog.getCreditOperateType()==2){
					str[3]=StringUtil.toString("减少");
				}else{
					str[3]=StringUtil.toString("");
				}
			
				str[4]=StringUtil.toString(clog.getCreditOperateValue());
				
				if(clog.getCreditOperateDatetime()!=null){
					str[5]=StringUtil.toString(sdf.format((Date)clog.getCreditOperateDatetime()));
				}else{
					str[5]=StringUtil.toString("");
				}
			
				str[6]=StringUtil.toString(clog.getCreditOperateIp());
				
				if(clog.getOperUser()!=null){
					str[7]=StringUtil.toString(clog.getOperUser().getUserAccount());
				}else{
					str[7]=StringUtil.toString("");
				}
				
				
				str[8]=StringUtil.toString(clog.getCreditOperateRemark());
			
				contents.add(str);
			}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("creditLog-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "积分记录", titles, contents);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel失败");
		}
		
		
	}
	
	
	/**
	 * 唯一编码
	 * @param request
	 * @param response
	 * @param model
	 * @param channel
	 */
	@RequestMapping("checkCreditCode")
	public void checkChannelCode(HttpServletRequest request,
			HttpServletResponse response, Model model, CreditType creditType) {
		String creditId = request.getParameter("creditId");
		String creditCode = request.getParameter("creditCode");
		if(!creditId.equals("")&&!creditId.equals(null)){
			creditType.setId(Integer.parseInt(creditId));
			}
		creditType.setCreditCode(creditCode);
		Integer totalCount = creditTypeService.getbyCode(creditType);
		if (totalCount == 0) {
			SpringUtils.renderText(response, "success");
		} else {
			SpringUtils.renderText(response, "fail");
		}

	}
}
