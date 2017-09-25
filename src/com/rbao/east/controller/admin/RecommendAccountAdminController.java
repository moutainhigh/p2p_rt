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
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.SysModule;
import com.rbao.east.service.RecommendAccountService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.StringUtil;

/**
 * 推广账号管理
 * */
@Controller
@RequestMapping("recommendAccountAd/")
public class RecommendAccountAdminController extends BaseController{
	
	private static Logger logger = LoggerFactory
			.getLogger(RecommendAccountAdminController.class);


	@Autowired
	private RecommendAccountService recommendAccountService ;
	@Autowired
	private SysModuleService moduleService;
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getRecommendRAPage")
	public String getRecommendRAPage(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> param=this.getParameters(request);
		PageModel pageModel = recommendAccountService.getPage(param);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", param);// 用于搜索框保留值
		model.addAttribute("right_id", param.get("right_id"));
		param.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(param);
		model.addAttribute("righSubtList", righSubtList);
		
		request.getSession(true).setAttribute("queryParams", param);
		
		return "recommend/recommendAccountlist";
	}
	
	/**
	 * 推荐总奖励查询导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("toExcelLevel")
	public void toExcelLevel(HttpServletRequest request, HttpServletResponse response) {
		/*Map<String, String> params = this.getParameters(request);
		params.put("numPerPage", "10000");*/
		try {
			Map<String, String> params = (Map) request.getSession().getAttribute("queryParams");
			params.put("pageNum", "1");
			params.put("numPerPage", "500000");
			params.remove("userId");
			
			PageModel pm = recommendAccountService.getPage(params);
			List<Map<String, String>> ars = pm.getList();

			System.out.println("---------export-- Recommends-number--->"
					+ ars.size());

			String[] titles = {"序号", "推荐人Id", "推荐人用户名", "推荐人姓名", "推荐人邮箱", "推荐人手机", "级别", "推荐总奖励"};

			List<String[]> contents = new ArrayList<String[]>();
			for (Map<String, String> map : ars) {
				String[] conList = new String[8];
				conList[0] = StringUtil.toString(map.get("id"));
				conList[1] = StringUtil.toString(map.get("rUserId"));
				conList[2] = StringUtil.toString(map.get("rUserAccount"));
				conList[3] = StringUtil.toString(map.get("rUserRealname"));
				conList[4] = StringUtil.toString(map.get("rUserEmail"));
				conList[5] = StringUtil.toString(map.get("rUserPhone"));
				conList[6] = StringUtil.toString(map.get("levels"))+"级";
				conList[7] = StringUtil.toString(map.get("rewards"));
				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("queryrecommendLevel" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "推广奖励查询统计数据", titles, contents);
		} catch (Exception e) {
			logger.error("导出excel失败", e);
		}

	}
}
