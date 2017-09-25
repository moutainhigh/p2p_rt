package com.rbao.east.controller.admin;

import java.util.Date;
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
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.UserEvaluateApply;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserEvaluateApplyService;
import com.rbao.east.utils.SpringUtils;
/**
 * 额度申请
 * */
@Controller
@RequestMapping("userEvaluateApply/")
public class UserEvaluateApplyController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(UserEvaluateApplyController.class);
	
	@Autowired
	private UserEvaluateApplyService userEvaluateApplyService;
	@Autowired
	private SysModuleService moduleService;
	
	//所有额度申请列表
	@RequestMapping(Constants.PRE_PATH_VIEW + "getUserEvaluateApplyList")
	public String getUserEvaluateApplyList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = userEvaluateApplyService.getUserEvaluateApplyList(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id").toString());
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		return "borrow/userevaluateapplylist";
	}
	
	//根据Id查询额度申请信息
	@RequestMapping(Constants.PRE_PATH_VIEW + "getUserEvaluateApplyById")
	public String getUserEvaluateApplyById(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("userId",this.loginAdminUser(request).getId().toString());
		model.addAttribute("right_id",paramsMap.get("right_id").toString());
		UserEvaluateApply apply=userEvaluateApplyService.getUserEvaluateApplyById(Integer.parseInt(paramsMap.get("supportID").toString()));
		model.addAttribute("apply",apply);
		return "borrow/checkuserevaluateapply";
		
	}
	
	//审核额度申请
	@RequestMapping(Constants.PRE_PATH_EDIT + "checkUserEvaluateApply")
	public void checkUserEvaluateApply(HttpServletRequest request,HttpServletResponse response,Model model,UserEvaluateApply apply){
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		model.addAttribute("right_id",param.get("right_id").toString());
		/*if(apply.getAmountApply().compareTo(apply.getAmount())==1 ||apply.getAmountApply().compareTo(apply.getAmount())==0){//申请额度大于或等于通过额度
*/			apply.setVerifyUser(this.loginAdminUser(request).getId());
			apply.setVerifyDatetime(new Date());
			try {
				succ = userEvaluateApplyService.updateUserEvaluateApply(apply);
			} catch (Exception e) {
				
			}
		/*}*/
		SpringUtils.renderDwzResult(response, succ, succ?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
	}
}
