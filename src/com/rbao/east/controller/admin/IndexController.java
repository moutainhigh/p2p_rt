package com.rbao.east.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.SysModuleService;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);
	@Autowired
	private SysModuleService moduleService;

	@RequestMapping("index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		/***** 没有登录界面这样替代session，有登录界面后删除 ************************/
		User user = (User) request.getSession().getAttribute(
				Constants.ADMIN_USER_SESSION);
		List<SysModule> menuModuleList = moduleService.getMenuModuleList(user
				.getId());
		if (menuModuleList.size() > 0) {
			Map<String, String> param = new HashMap<String, String>();
			List<Object> list = new ArrayList<Object>();
			int moduleId = menuModuleList.get(0).getId();
			param.put("userId", user.getId().toString());
			param.put("right_id", String.valueOf(moduleId));
			List<SysModule> rightGroupModuleList = moduleService
					.getRightGroupList(param);
			for (int i = 0; i < rightGroupModuleList.size(); i++) {
				param.clear();
				SysModule sysModule = rightGroupModuleList.get(i);
				param.put("userId", user.getId().toString());
				param.put("right_id", sysModule.getId().toString());
				List<SysModule> rightModuleList = moduleService
						.getRightGroupList(param);
				sysModule.setSysModule(rightModuleList);
				list.add(sysModule);
			}
			request.setAttribute("menuModuleList", menuModuleList);
			request.setAttribute("rightModuleList", list);
		}
		Properties props = System.getProperties();
		request.setAttribute("props", props);
		return "index";
	}

	@RequestMapping("subMenu")
	public String subMenu(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		Map<String, Object> result = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		User user=this.loginAdminUser(request);
		try {
			// 得到UserId查询对应的权限
			param.put("userId", user.getId().toString());
			List<SysModule> rightGroupModuleList = moduleService
					.getRightGroupList(param);
			for (int i = 0; i < rightGroupModuleList.size(); i++) {
				SysModule sysModule = rightGroupModuleList.get(i);
				param.put("right_id", sysModule.getId().toString());
				List<SysModule> rightModuleList = moduleService
						.getRightGroupList(param);
				sysModule.setSysModule(rightModuleList);
				list.add(sysModule);
			}
			result.put("rightModuleList", list);
			result.put("code", "1");
			logger.info("显示用户权限树成功！");
		} catch (Exception e) {
			
			result.put("code", "0");
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("展示权限树失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return "subMenu";
	}

}
