package com.rbao.east.controller.front;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.QuickinvestmentApplications;
import com.rbao.east.entity.User;
import com.rbao.east.service.QuickinvestmentApplicationsService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
/**
 * 理财预约
 * */
@Controller
@RequestMapping("quickInvestment/")
public class QuickinvestmentApplicationsController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(QuickinvestmentApplicationsController.class);
	@Autowired
	private QuickinvestmentApplicationsService quickinvestmentApplicationsService;

	/**
	 * 进入预约表单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("toQuickInvestment.do")
	public void toApplayQuickInvestment(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String productId = request.getParameter("productId");
		if (productId != null) {
			SpringUtils.renderJsonResult(response, JsonResult.SUCCESS,
					productId);
		} else {
			SpringUtils.renderJsonResult(response, JsonResult.SUCCESS, "无产品");
		}

	}

	/**
	 * 保存预约信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param applications
	 */
	@RequestMapping("saveQuickInvestment.do")
	public void saveQuickInvestment(HttpServletRequest request,
			HttpServletResponse response, Model model,
			QuickinvestmentApplications applications) {
		User user = this.loginFrontUser(request);
		if (user != null) {
			String province = request.getParameter("province");
			String city = request.getParameter("city");
			String area = request.getParameter("area");
			String productId = request.getParameter("productId");
			
				//applications.setInvestUserArea(province + city + area);
				if (productId != null && !productId.equals("")) {
					applications.setInvestProductsId(Integer
							.parseInt(productId));
				}
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				try {
					Date date = sdf.parse(sdf.format(new Date()));
					applications.setInvestAddDatetime(date);
				} catch (ParseException e) {
					logger.error("获取时间出错！");
					
				}
				applications.setInvestAddIp(this.getIpAddr(request));
				applications
						.setInvestVerifyStatus(QuickinvestmentApplications.QUICKINVESTMENT_STATUS_WAIT);
				boolean flag = false;
				flag = quickinvestmentApplicationsService
						.saveQuickinvestmentApplications(applications);
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid("用户Id：" + user.getId() + ",用户名："
						+ user.getUserAccount());
				operatorLog.setOperatorTitle("提交预约申请");
				operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PRODUCTS);
				operatorLog.setOperatorParams(applications.toString());
				operatorLog.setOperatorReturn(flag ? "预约成功" : "预约失败");
				operatorLog.setOperatorStatus(flag ? 200 : 300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLog.setOperatorIp(this.getIpAddr(request));
				operatorLogService.addFrontLog(operatorLog);
				if (flag == true) {
					SpringUtils.renderJsonResult(response, JsonResult.SUCCESS,
							"预约成功");
				} else {
					SpringUtils.renderJsonResult(response, "301", "预约失败");
				}
			
		} else {
			SpringUtils.renderJsonResult(response, "201", "没有登录");
		}
	}
}
