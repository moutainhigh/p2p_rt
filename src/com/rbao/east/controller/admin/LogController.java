package com.rbao.east.controller.admin;

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

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.OperatorLogService;
import com.rbao.east.service.SysModuleService;
/**
 * 日志管理
 * */
@Controller
@RequestMapping("log/")
public class LogController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(LogController.class);
	@Autowired
	private OperatorLogService operatorLogService;
	@Autowired
	private SysModuleService moduleService;
	@RequestMapping(Constants.PRE_PATH_VIEW + "getOperatorLogList")
	public String getOperatorLogList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> param = this.getParameters(request);
		String target = "";
		User user = this.loginAdminUser(request);
		try {
			param.put("userId", user.getId().toString());
			List<SysModule> righSubtList = moduleService
					.getRightGroupList(param);
			result.put("righSubtList", righSubtList);
			param.put("operatorType",OperatorLog.TYPE_ADMIN+"");
			result.putAll(param);
			PageModel pageModel = operatorLogService.getPagedList(param);
			result.put("pm", pageModel);
			target = "log/showOperatorLogList";
			result.put("code", 1);
		} catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("显示操作日志失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return target;
	}

	@RequestMapping(Constants.PRE_PATH_VIEW + "getFrontLogList")
	public String getFrontLogList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> param = this.getParameters(request);
		String target = "";
		User user = this.loginAdminUser(request);
		try {
			param.put("userId", user.getId().toString());
			List<SysModule> righSubtList = moduleService
					.getRightGroupList(param);
			result.put("righSubtList", righSubtList);
			param.put("operatorType",OperatorLog.TYPE_FRONT+"");
			result.putAll(param);
			PageModel pageModel = operatorLogService.getPagedList(param);
			result.put("pm", pageModel);
			target = "log/showFrontOperatorLogList";
			result.put("code", 1);
		} catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("显示前台日志失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return target;
	}
}
