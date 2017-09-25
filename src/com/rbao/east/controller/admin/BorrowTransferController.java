package com.rbao.east.controller.admin;

import java.io.OutputStream;
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
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.BorrowTransfer;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.StringUtil;

/**
 * 债权转让
 * */

@Controller
@RequestMapping("borrowTransfer/")
public class BorrowTransferController extends BaseController{

	
	private static Logger logger = LoggerFactory
			.getLogger(BorrowTransferController.class);
	
	
	@Autowired
	private SysModuleService moduleService;
	
	@Autowired
	private BorrowTransferService borrowTransferService;
	
	
	/**
	 * 获取债权列表
	 * */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowTransferList")
	public String getBorrowTransferList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);		
		paramsMap.put("userId", String.valueOf(user.getId()));
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel  pageModel=borrowTransferService.selectBorrowTransList(paramsMap);
		model.addAttribute("pm", pageModel);
		this.setParameters(paramsMap, request);
		
		request.getSession(true).setAttribute("queryParams", paramsMap);  
		logger.info("显示债券转让竞拍记录成功");
		return  "borrowTransfer/showBorrowTransferList";
		
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String, String> maps=(Map<String, String>)request.getSession().getAttribute("queryParams");
			maps.put("numPerPage", "500000");
			
			PageModel  pageModel=borrowTransferService.selectBorrowTransList(maps);
			List list=pageModel.getList();
			String[] titles=new String[]{"序号","借款标题","转让人","年收益率","待收本金","转让本金","最新竞拍价格","手续费","创建时间","结束投标时间","创建IP","状态"};
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<String[]> contents=new ArrayList<String[]>();
			for (int i = 0; i <list.size(); i++) {
				Map<String, String> map=(Map<String, String>)list.get(i);
				String[] str=new String[titles.length];
				
				int serialNumber=pageModel.getPageSize() * (pageModel.getCurrentPage()-1) + i + 1;
				str[0]=StringUtil.toString(serialNumber);
				str[1]=StringUtil.toString(map.get("borrow_title"));
				str[2]=StringUtil.toString(map.get("user_account"));
				str[3]=StringUtil.toString(map.get("transfer_interest_rate"))+"%";
				str[4]=StringUtil.toString(map.get("repossessed_capital"));
				str[5]=StringUtil.toString(map.get("transfer_money"));
				str[6]=StringUtil.toString(map.get("last_auction_money"));
				str[7]=StringUtil.toString(map.get("transfer_fee"));
				
				if(map.get("create_time")!=null){
					str[8]=StringUtil.toString(sdf.format(map.get("create_time")));
				}else{
					str[8]=StringUtil.toString("");
				}
				
				if(map.get("end_time")!=null){
					str[9]=StringUtil.toString(sdf.format(map.get("end_time")));
				}else{
					str[9]=StringUtil.toString("");
				}
				
				str[10]=StringUtil.toString(map.get("create_ip"));
				str[11]=StringUtil.toString(BorrowTransfer.ALL_STATUS.get(map.get("transfer_status")));
				
				contents.add(str);
				
			}
			
			String title="债权转让";
			String fileName="borrowTransfer";
			if("1".equals(maps.get("transferStatus"))){
				title="债权正在转让";
				fileName="borrowTransfer_underway";
			}else if("2".equals(maps.get("transferStatus"))){
				title="债权转让成功";
				fileName="borrowTransfer_success";
			}else if("3".equals(maps.get("transferStatus"))){
				title="债权转让失败";
				fileName="borrowTransfer_fail";
			}
			
			
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String((fileName + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, title, titles, contents);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel失败");
		}
		
	}
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowTransferAuctionList")
	public String getBorrowTransferAuctionList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);		
		paramsMap.put("userId", String.valueOf(user.getId()));
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel  pageModel=borrowTransferService.selectBorrowTransferAuction(paramsMap);
		model.addAttribute("pm", pageModel);
		this.setParameters(paramsMap, request);
		logger.info("显示债券转让成功");
		return  "borrowTransfer/showBorrowTransferAuctionList";
		
	}
	
	//债权转让协议
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowTransferDealList")
	public String getBorrowTransferDealList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);		
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel  pageModel=borrowTransferService.findBorrowTransferDeal(paramsMap);
		model.addAttribute("searchParams",paramsMap);
		model.addAttribute("pm", pageModel);
		return  "borrowTransfer/borrowTransferDeallist";
		
	}
	
	//预览PDF
		@RequestMapping(Constants.PRE_PATH_VIEW + "showPDF")
		public String showPDF(HttpServletRequest request, HttpServletResponse response,
				Model model) {
			Map<String,String> paramsMap = getParameters(request);		
			model.addAttribute("agreePath", paramsMap.get("agreePath"));
			return  "borrowTransfer/showPDF";
			
		}
	
}
