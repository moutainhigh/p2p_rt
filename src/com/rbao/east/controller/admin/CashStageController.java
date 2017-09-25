package com.rbao.east.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.CashStage;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.CashStageService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
/**
 * 提现费率
 * */
@Controller
@RequestMapping("cashStage/")
public class CashStageController extends BaseController {
	
	@Autowired
	private CashStageService cashStageService;
	
	@Autowired
	private SysModuleService moduleService;
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getCashPage")
	public String getCashPage(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> param=this.getParameters(request);
		User user = loginAdminUser(request);
		param.put("userId", String.valueOf(user.getId()));
		List<SysModule> righSubtList=moduleService.getRightGroupList(param);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = cashStageService.getPage(param);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", param);// 用于搜索框保留值
		model.addAttribute("right_id", param.get("right_id"));
		return "cash/listCash";
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toAddCashStage")
	public String toAddCashStage(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		return "cash/addCash";
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toUpdateCashStage")
	public String toUpdateCashStage(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		Map<String, String> params = this.getParameters(request);
		Integer id=Integer.parseInt(params.get("supportID"));
		CashStage cash=this.cashStageService.selectById(id);
		model.addAttribute("cash", cash);
		return "cash/addCash";
	}


	@SuppressWarnings("null")
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveOrUpdate")
	public void saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, Model model,CashStage cashStage){
		User user = loginAdminUser(request);
		boolean flag = false;
		CashStage cash=null;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		String ip = this.getIpAddr(request); 
		OperatorLog operatorLog = new OperatorLog();
		try{
			if(cashStage.getId()==null){
				cashStage.setAddIp(ip);
				cashStage.setAddTime(new Date());
				cashStage.setAddUser(user.getId());
				operatorLog.setOperatorTitle("添加提现费率");
			}else{
				cash=this.cashStageService.selectById(cashStage.getId());
				cashStage.setId(cash.getId());
				cashStage.setUpdateIp(ip);
				cashStage.setUpdateTime(new Date());
				cashStage.setUpdateUser(loginAdminUser(request).getId());
				operatorLog.setOperatorTitle("修改提现费率");
			}
			flag=this.cashStageService.saveOrUpdate(cashStage);
		}catch(Exception e){
			flag=false;
			
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
		operatorLog.setLogUserid(user.getUserAccount());
		
		operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorParams(user.toString());
		operatorLog.setOperatorReturn(flag?"添加或者修改提现费率成功":"添加或者修改提现费率失败");
		operatorLog.setOperatorStatus(flag?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
	}
}
