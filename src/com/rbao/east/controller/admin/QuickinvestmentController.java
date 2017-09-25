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

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.QuickinvestmentApplications;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.QuickinvestmentApplicationsService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;

/**
 * 理财预约
 */

@Controller
@RequestMapping("quickInvestmentApplications/")
public class QuickinvestmentController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(QuickinvestmentController.class);
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private QuickinvestmentApplicationsService quickinvestmentApplicationsService;
	
	/**
	 * 预约列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getQuickInvestmentList")
	public String getQuickInvestmentList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		// 获得当前登录用户的rightId下的子权限
		User user=this.loginAdminUser(request);
		paramsMap.put("userId", user.getId().toString());
		String productId = paramsMap.get("supportID");
		if(productId != null){
			paramsMap.put("investProductsId", productId);
		}
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = quickinvestmentApplicationsService.getQuickinvestmentApplicationsList(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		return "quickInvestment/quickinvestmentlist";
	}
	
	/**
	 * 根据Id查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"getQuickInvestmentById")
	public String getQuickInvestmentById(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, String> param = this.getParameters(request);
		QuickinvestmentApplications applications = quickinvestmentApplicationsService.getById(Integer.parseInt(param.get("supportID").toString()));
		model.addAttribute("application", applications);
		model.addAttribute("right_id", param.get("right_id"));
		model.addAttribute("rel",request.getParameter("rel"));
		return "quickInvestment/checkquickinvestment";
	}
	
	/**
	 * 理财预约审核
	 * @param request
	 * @param response
	 * @param model
	 * @param applications
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "checkQuickInvestment")
	public void checkQuickInvestment(HttpServletRequest request,
			HttpServletResponse response, Model model,QuickinvestmentApplications applications) {
		boolean succ = false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id").toString());
		User user = this.loginAdminUser(request);
		applications.setInvestVerifyUser(user.getId());
		applications.setInvestVerifyDatetime(new Date());
		try {
			succ = quickinvestmentApplicationsService.updateQuickinvestmentApplications(applications);
		} catch (Exception e) {
			logger.info("理财预约审核失败！" + e.toString());
			
		}
		//添加日志
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid("用户Id：" + user.getId() + ",用户名："
				+ user.getUserAccount());
		operatorLog.setOperatorTitle("审核理财预约");
		operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PRODUCTS);
		operatorLog.setOperatorReturn(succ ? "审核成功" : "审核失败");
		operatorLog.setOperatorStatus(succ ? 200 : 300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLog.setOperatorIp(this.getIpAddr(request));
		operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, succ, succ ? "操作成功" : "操作失败",
				DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id")
						.toString());
	}
}
