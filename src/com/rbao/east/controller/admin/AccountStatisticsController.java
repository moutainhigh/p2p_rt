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
import com.rbao.east.entity.AccountStatistics;
import com.rbao.east.entity.SysModule;
import com.rbao.east.service.AccountStatisticsService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.StringUtil;

/**
 * 每日统计
 */

@Controller
@RequestMapping("accountStatistics/")
public class AccountStatisticsController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(AccountStatisticsController.class);

	@Autowired
	private AccountStatisticsService accountStatisticsService;

	@Autowired
	private SysModuleService moduleService;

	/**
	 * 每日统计列表
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAccountStatisticsList")
	public String getAccountStatisticsList(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Map<String, String> paramsMap = getParameters(request);
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> rightSubList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("rightSubList", rightSubList);
		// PageModel pageModel =
		// accountStatisticsService.getAccountStatistics(paramsMap);
		PageModel pageModel = accountStatisticsService
				.getPageAccountStatistics(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		return "accountStatistics/accountstatisticslist";
	}

	/**
	 * 导出Excel
	 */
	@RequestMapping("toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);

		try {
			List<AccountStatistics> ass = accountStatisticsService
					.getAllAccountStatistics(params);
			if (ass.size() > 500000) {
				ass = ass.subList(0, 500000);
			}
			System.out.println("---------export-- accountStatistics-number--->"
					+ ass.size());

			String[] titles = { "序号", "统计日期", "用户名", "真实姓名", "总资金", "可用资金",
					"冻结资金", "待收资金", "待还资金", "待收利息", "待还利息", "总支出奖励", "总支出利息",
					"总获得奖励", "总获得利息", "净总额", "投标总额" };

			List<String[]> contents = new ArrayList<String[]>();
			for (AccountStatistics as : ass) {
				String[] conList = new String[17];
				conList[0] = as.getId().toString();
				conList[1] = DateUtils.formatDate("yyyy-MM-dd",
						as.getStatisticsAddtime());
				conList[2] = StringUtil.toString(as.getUserName());
				conList[3] = StringUtil.toString(as.getUserRealname());
				conList[4] = toString(as.getAllMoney());
				conList[5] = toString(as.getAvailableMoney());
				conList[6] = toString(as.getUnavailableMoney());
				conList[7] = toString(as.getWaitRepossessedCapital());
				conList[8] = toString(as.getWaitRepayCapital());
				conList[9] = toString(as.getWaitRepossessedInterest());
				conList[10] = toString(as.getWaitRepayInterest());
				conList[11] = toString(as.getPayReward());
				conList[12] = toString(as.getPayInterest());
				conList[13] = toString(as.getGetReward());
				conList[14] = toString(as.getGetInterest());
				conList[15] = toString(as.getWorthMoney());
				conList[16] = toString(as.getTenderAmount());

				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("everyday-report-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "每日统计数据", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}

	}
	
	private  String toString(Object obj) {
		if (obj == null) {
			return "0.00";
		} else {
			return obj.toString();
		}
	}

}
