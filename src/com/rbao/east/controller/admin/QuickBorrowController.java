package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.rbao.east.dao.QuickBorrowDao;
import com.rbao.east.entity.QuickBorrow;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.QuickBorrowService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;

/**
 * 快速贷款
 * 
 */
@Controller
@RequestMapping("quickBorrow/")
public class QuickBorrowController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(QuickBorrowController.class);

	@Autowired
	private QuickBorrowService quickBorrowService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private UserService userService;
	@Autowired
	private QuickBorrowDao quickBorrowDao;

	@RequestMapping(Constants.PRE_PATH_VIEW + "getAllList")
	public String getAllList(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Map<String, String> paramsMap = getParameters(request);
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		/*PageModel pageModel = quickBorrowService.getPagedList(paramsMap);*/
		PageModel pageModel = quickBorrowDao.getPage("selectPages", "selectCount", paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		request.getSession().setAttribute("queryParams", paramsMap);
		return "quickBorrow/allList";
	}
	
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcel")
	public void toExcel(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, String> maps=(Map<String, String>)request.getSession().getAttribute("queryParams");
			maps.put("numPerPage", "500000");	
			
			PageModel pageModel = quickBorrowService.getPagedList(maps);
			List list=pageModel.getList();
			
			//String[] titles={"序号","贷款人","手机号","地区","借款金额(万元)","借款期限","用途","利率","申请时间","当前状态"};
			
			String[] titles={"序号","贷款人状态","真实姓名","用户名","手机号","地区","借款金额(万元)","借款期限","用途","申请时间","当前状态"};
			
			List<String[]> contents = new ArrayList<String[]>();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < list.size(); i++) {		
				QuickBorrow row=(QuickBorrow)list.get(i);
				String[] str=new String[titles.length];
				str[0]=StringUtil.toString(row.getId());
				
				if(row.getMainUse()!=null){
					str[1]=StringUtil.toString("企业");
				}else{
					str[1]=StringUtil.toString("个人");
				}
				
				str[2]=StringUtil.toString(row.getUserRealname());
				str[3]=StringUtil.toString(row.getUserName());
				str[4]=StringUtil.toString(row.getUserTel());
				str[5]=StringUtil.toString(row.getUserArea());
				str[6]=StringUtil.toString((int)(row.getBorrowAmount().divide(new BigDecimal(10000)).doubleValue()));
				str[7]=StringUtil.toString(row.getBorrowPeriod());
				str[8]=StringUtil.toString(row.getBorrowUse());
				//str[7]=StringUtil.toString(row.getInterestrateMin()+" ~ "+row.getInterestrateMax());
				
				if(row.getCreateTime()!=null){
					str[9]=StringUtil.toString(sdf.format(row.getCreateTime()));
				}else{
					str[9]=StringUtil.toString("");
				}
				
				str[10]=StringUtil.toString(QuickBorrow.ALL_STATUS.get(row.getStatus()));		
				
				contents.add(str);
			}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("quickBorrow-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, " 快速借款查看全部", titles, contents);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel失败");
		}
		
		
	}

	@RequestMapping(Constants.PRE_PATH_VIEW + "getList")
	public String getList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		Integer audit = Integer.parseInt(paramsMap.get("audit"));
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = quickBorrowService.getPagedList(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		if (audit != null && audit == 1) {
			return "quickBorrow/firstList";
		} else {
			return "quickBorrow/secList";
		}
	}

	/**
	 * 审核
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "quickBorrow_audit")
	public String quickAudit(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		int audit = 0;
		Map<String, String> requests = this.getParameters(request);

		if (requests.get("audit") == null) {
			audit = 1;
		} else {
			audit = Integer.parseInt(requests.get("audit"));
		}
		Integer id = Integer.parseInt(requests.get("supportID"));
		QuickBorrow quickBorrow = quickBorrowService.getById(id);

		model.addAttribute("qb", quickBorrow);
		model.addAttribute("right_id", requests.get("right_id"));
		if (audit == 1) {
			return "quickBorrow/quickBorrowAudit";
		} else {
			Integer userId = quickBorrow.getFistAuditUserid();
			User user = userService.getById(userId);
			model.addAttribute("user", user);

			Integer secUserId = quickBorrow.getSecondAuditUserid();
			if (secUserId != null) {
				User secUser = userService.getById(secUserId);
				model.addAttribute("secUser", secUser);
			}

			return "quickBorrow/quickBorrowSecAudit";
		}
	}

	/**
	 * 处理审核
	 */
	@RequestMapping("checkAudit")
	public void checkAudit(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		Map<String, String> requests = this.getParameters(request);
		Integer type = Integer.parseInt(requests.get("type"));

		boolean flag = true;
		String result = "";
		Integer id = Integer.parseInt(requests.get("id"));
		QuickBorrow quickBorrow = quickBorrowService.getById(id);

		if (type != null && type == 1) {// 初审
			try {
				Integer firStatus = Integer.parseInt(requests
						.get("firstStatus"));
				if (firStatus != null && firStatus == 2) {
					result = "初审通过";
				} else {
					result = "初审未通过";
				}
				quickBorrow.setStatus(firStatus);
				quickBorrow.setFirstAuditRemark(requests
						.get("firstAuditRemark"));
				quickBorrow.setFirstAuditIp(this.getIpAddr(request));
				quickBorrow.setFistAuditTime(new Date());
				quickBorrow.setFistAuditUserid(loginUser(request,
						Constants.ADMIN_USER_SESSION).getId());
				quickBorrowService.saveOrUpdate(quickBorrow);
				flag = true;
			} catch (Exception e) {
				
				logger.error("初审异常");
				flag = false;
			}

			SpringUtils.renderDwzResult(response, flag, flag ? "操作成功--"
					+ result : "操作异常通过--" + result,
					DwzResult.CALLBACK_CLOSECURRENT, requests.get("right_id")
							.toString());

		} else if (type != null && type == 2) {// 复审
			try {
				Integer secSatus = Integer.parseInt(requests
						.get("secondStatus"));
				if (secSatus != null && secSatus == 4) {
					result = "复审通过";
				} else {
					result = "复审未通过";
				}
				quickBorrow.setStatus(secSatus);
				quickBorrow.setSecondAuditRemark(requests
						.get("secondAuditRemark"));
				quickBorrow.setSecondAuditIp(this.getIpAddr(request));
				quickBorrow.setSecondAuditTime(new Date());
				quickBorrow.setSecondAuditUserid(loginUser(request,
						Constants.ADMIN_USER_SESSION).getId());
				quickBorrowService.saveOrUpdate(quickBorrow);

				flag = true;
			} catch (Exception e) {
				
				logger.error("复审异常");
				flag = false;
			}
			SpringUtils.renderDwzResult(response, flag, flag ? "操作成功--"
					+ result : "操作异常通过--" + result,
					DwzResult.CALLBACK_CLOSECURRENT, requests.get("right_id")
							.toString());
		} else {
			logger.error("异常");
			flag = false;
			SpringUtils.renderDwzResult(response, flag, "审核过程异常",
					DwzResult.CALLBACK_CLOSECURRENT, requests.get("right_id")
							.toString());
		}

	}

}
