package com.rbao.east.controller.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.ExcelUtil;


@Controller
@RequestMapping("borrowThirtyDays/")
public class BorrowThirtyDaysController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(BorrowThirtyDaysController.class);

	@Autowired
	private BorrowService borrowQueryService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private BorrowTypeService borrowTypeService;


	//还款管理
	@RequestMapping(Constants.PRE_PATH_VIEW + "getPaymentBorrowsList")
	public String getPaymentBorrowsList(HttpServletRequest request, HttpServletResponse response,Model model){
		Map<String,String> paramsMap = getParameters(request);
		if(!paramsMap.containsKey("repaymentStatus")){
			/*paramsMap.putAll((Map<String, String>) request.getSession(true).getAttribute("REPAY_LIST_PARAMS"));
			if(paramsMap.get("repaymentStatus").equals(BorrowRepayment.REPAYMENT_STATUS_SUCCESS)){
				
			}*/
		}else{
			request.getSession(true).setAttribute("REPAY_LIST_PARAMS",paramsMap);
			model.addAttribute("repaymentStatus",paramsMap.get("repaymentStatus"));
		}
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = borrowQueryService.getPaymentBorrows(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		if(StringUtils.isBlank(paramsMap.get("repaymentStatus"))){
			model.addAttribute("repaymentStatusAll","0");//用于搜索状态全部
		}else if(StringUtils.isNotBlank(paramsMap.get("repaymentStatusAll"))){
			if(paramsMap.get("repaymentStatusAll").equals("0"))
			  {
				model.addAttribute("repaymentStatusAll","0");
			  
			  }
		}
		System.out.println("repaymentStatus" + paramsMap.get("repaymentStatus"));
		request.getSession(true).setAttribute("queryParams", paramsMap);  
		model.addAttribute("rel",request.getParameter("rel"));
		model.addAttribute("borrowTypes", borrowTypeService.getAllBorrowTypes());
		/*	model.addAttribute("borStatus", paramsMap.get("borStatus"));*/
		return "borrow/paymentborrowsthirtydayslist";
	}
	


	@RequestMapping(Constants.PRE_PATH_VIEW + "toNearlyRepayExcel")
	public void toNearlyRepayExcel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
		try {
			List<Map<String, Object>> repayList = borrowQueryService.toRepayExcel(params);
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String[] titles={"序号","用户名","借款标题","借款金额（元）","剩余期数","本期应还金额（元）"
							,"剩余应还本金（元）","距离下次还款（天）","下一还款日"};
			List<String[]> contents=new ArrayList<String[]>();
			int i=1;
			for (Map<String, Object> repay:repayList) {
				String [] strList=new String[9];
				strList[0]=String.valueOf(i);
				if(repay.containsKey("userAccount")){
					strList[1]=repay.get("userAccount").toString();
				}else{
					strList[1]="";
				}
				if(repay.containsKey("borrowTitle")){
					strList[2]=repay.get("borrowTitle").toString();
				}else{
					strList[2]="";
				}
				if(repay.containsKey("borrowSum")){
					strList[3]=repay.get("borrowSum").toString();
				}else{
					strList[3]="0.00";
				}
				if(repay.containsKey("surplusPeriod")){
					strList[4]=dateFormat.format(repay.get("surplusPeriod")).toString();
				}else{
					strList[4]="0";
				}
				if(repay.containsKey("repaymentAmount")){
					strList[5]=repay.get("repaymentAmount").toString();
				}else{
					strList[5]="0.00";
				}
				if(repay.containsKey("surplusSum")){
					strList[6]=repay.get("surplusSum").toString();
				}else{
					strList[6]="0.00";
				}
				if(repay.containsKey("surplusTime")){
					strList[7]=repay.get("surplusTime").toString();
				}else{
					strList[7]="0";
				}				
				if(repay.containsKey("repaymentTiime")){
					strList[7]=dateFormat.format(repay.get("repaymentTiime")).toString();
				}else{
					strList[7]="";
				}
				i++;
				contents.add(strList);
			}
				OutputStream os = response.getOutputStream();
				response.reset();// 清空输出流     
				response.setHeader("Content-disposition", "attachment; filename="+ 
								new String("NearlyRepayList.xls".getBytes("UTF-8"),"ISO8859-1"));
				response.setContentType("application/msexcel");// 定义输出类型   
				ExcelUtil.buildExcel(os,"近30天待还款",titles,contents);
		} catch (IOException e) {
			logger.error("导出excel失败",e);
			
		}
	}

}
