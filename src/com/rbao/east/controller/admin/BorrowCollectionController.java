package com.rbao.east.controller.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
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
import com.rbao.east.entity.BorrowCollection;
import com.rbao.east.entity.SysModule;
import com.rbao.east.service.BorrowCollectionService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;
/**
 * 催收操作
 * */
@Controller
@RequestMapping("borrowCollection/")
public class BorrowCollectionController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(BorrowCollectionController.class);

	@Autowired
	private BorrowCollectionService borrowCollectionService;
	@Autowired
	private SysModuleService moduleService;
	
	
	//催收管理
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowCollectionList")
	public String getBorrowsCollectionList(HttpServletRequest request, HttpServletResponse response,Model model){
		Map<String,String> params = getParameters(request);
		params.put("userId", this.loginAdminUser(request).getId().toString());
		List<SysModule> righSubtList=moduleService.getRightGroupList(params);
		PageModel pageModel = borrowCollectionService.getBorrowCollectionList(params);
		model.addAttribute("righSubtList", righSubtList);
		model.addAttribute("right_id", params.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",params);//用于搜索框保留值
		request.getSession(true).setAttribute("queryParams", params);
		
		return "borrow/borrowcollectionlist";
	}
	
	//催收操作
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveBorrowCollection")
	public void saveBorrowCollection(HttpServletRequest request, HttpServletResponse response,Model model){
		Map<String, String> param = this.getParameters(request);
		BorrowCollection borrowCollection=new BorrowCollection();
		borrowCollection.setAddUserid(this.loginAdminUser(request).getId());
		borrowCollection.setCollectionType(Integer.parseInt(param.get("collectionType")));
		borrowCollection.setCollectionUser(param.get("collectionUser"));
		try {
			borrowCollection.setAddTime(new Date());
			borrowCollection.setCollectionTime(DateUtils.convertStringToDate(param.get("collectionTime")));
			borrowCollection.setBorrowId(Integer.parseInt(param.get("borrowId")));
			borrowCollection.setAddIp(this.getIpAddr(request));
			borrowCollection.setCollectionRemark(param.get("collectionRemark"));
			borrowCollection.setCollectionResult(param.get("collectionResult"));
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(borrowCollection);
		System.out.println(borrowCollection.getAddTime());
		System.out.println(borrowCollection.getBorrowId());
		System.out.println(borrowCollection.getCollectionResult());
		System.out.println(borrowCollection.getAddUserid());
		System.out.println(borrowCollection.getCollectionTime());
		System.out.println(borrowCollection.getAddIp());
		System.out.println(borrowCollection.getCollectionUser());
		borrowCollectionService.addBorrowCollection(borrowCollection);
		SpringUtils.renderDwzResult(response, true, "操作成功！",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());
	}
	//催收界面
	@RequestMapping(Constants.PRE_PATH_VIEW + "toUpdateBorrowCollection")
	public String toUpdateBorrowCollection(HttpServletRequest request, HttpServletResponse response,Model model){
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		Map<String, String> params = this.getParameters(request);
		params.put("borrowId", param.get("ids"));
		request.getSession(true).setAttribute("queryParams", params);
		List<Map<String, Object>> borrowList =borrowCollectionService.findBorrowCollectionById(params);
		model.addAttribute("pm", borrowList.get(0));
		return "borrow/BorrowCollectionDetail";
	}
	
	//导出
	@SuppressWarnings("unchecked")
	@RequestMapping(Constants.PRE_PATH_VIEW + "toRepayExcel")
	public void toRepayExcel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
		try {
			List<Map<String, Object>> repayList = borrowCollectionService.toRepayExcel(params);
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String[] titles={"序号","借款标题","借款人","催收方式","催收人","催收时间","催收概要","备注"};
			List<String[]> contents=new ArrayList<String[]>();
			int i=1;
			for (Map<String, Object> repay:repayList) {
				String [] strList=new String[8];
				strList[0]=String.valueOf(i);
				if(repay.containsKey("borrowTitle")){
					strList[1]=StringUtil.toString(repay.get("borrowTitle"));
				}else{
					strList[1]="";
				}
				if(repay.containsKey("userAccount")){
					strList[2]=StringUtil.toString(repay.get("userAccount"));
				}else{
					strList[2]="";
				}
				if(repay.containsKey("collectionType")){
					if(repay.get("collectionType")!=null){
						strList[3]=BorrowCollection.BC_STATUS.get(Integer.parseInt(repay.get("collectionType").toString()));
					}else{
						strList[3]="";
					}
				}else{
					strList[3]="";
				}
				if(repay.containsKey("collectionUser")){
					strList[4]=StringUtil.toString(repay.get("collectionUser"));
				}else{
					strList[4]="";
				}
				if(repay.containsKey("collectionTime")){
					if(repay.get("collectionTime")!=null){
						strList[5]=dateFormat.format(repay.get("collectionTime")).toString();
					}else{
						strList[5]="";
					}
					
				}else{
					strList[5]="";
				}
				if(repay.containsKey("collectionResult")){
					strList[6]=StringUtil.toString(repay.get("collectionResult"));
				}else{
					strList[6]="";
				}
				if(repay.containsKey("collectionRemark")){
					strList[7]=StringUtil.toString(repay.get("collectionRemark"));
				}else{
					strList[7]="";
				}
				i++;
				contents.add(strList);
			}
				OutputStream os = response.getOutputStream();
				response.reset();// 清空输出流     
				response.setHeader("Content-disposition", "attachment; filename="+ 
								new String("BorrowCollectionList.xls".getBytes("UTF-8"),"ISO8859-1"));
				response.setContentType("application/msexcel");// 定义输出类型   
				ExcelUtil.buildExcel(os,"催收信息",titles,contents);
		} catch (IOException e) {
			logger.error("导出excel失败",e);
			
		}
	}
	

}
