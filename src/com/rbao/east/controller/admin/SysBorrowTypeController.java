package com.rbao.east.controller.admin;



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
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.SpringUtils;
/**
 *标种管理
 * @author Sandy
 *
 */
@Controller
@RequestMapping("sys/")
public class SysBorrowTypeController  extends BaseController{
	private static Logger logger = LoggerFactory
			.getLogger(SysBorrowTypeController.class);
	@Autowired
    private BorrowTypeService borrowTypeService;
	@Autowired
	private SysModuleService moduleService;
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAllBorrowTypeList")
	public String getAllBorrowTypeList(HttpServletRequest request, HttpServletResponse response,
			Model model ) {
		User user = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);		
		PageModel  pageModel=borrowTypeService.getAllBorrowType(paramsMap);
		model.addAttribute("pm",pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		
		paramsMap.put("userId", String.valueOf(user.getId()));
		
		model.addAttribute("right_id", paramsMap.get("right_id"));//刷新
    	// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		
		return "sys/borrowTypeList";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "addBorrowType")
	public String addBorrowType(HttpServletRequest request, HttpServletResponse response,
			Model model ) {
		Map<String,String> paramsMap = getParameters(request);
		model.addAttribute("right_id",paramsMap.get("right_id"));
		
		return "sys/addUpdateBorrowType";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateBorrowType")
	public String updateBankType(HttpServletRequest request, HttpServletResponse response,
			Model model ) {
		Map<String,String> paramsMap = getParameters(request);
		model.addAttribute("right_id",paramsMap.get("right_id"));
		
		BorrowType borrowType=borrowTypeService.getBorrowTypeById(Integer.parseInt(paramsMap.get("id")));
		model.addAttribute("borrowType",borrowType);
		return "sys/addUpdateBorrowType";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveOrUpdateBorrowType")
	public void saveUpdateBorrowType(HttpServletRequest request, HttpServletResponse response,
			Model model,BorrowType borrowType,String logPath[]) {
		Map<String,String> paramsMap = this.getParameters(request);
		boolean flag=false;
		try{
			if(logPath !=null && logPath.length>1){
				throw new RuntimeException("上传图片限制一张");
			}else if(logPath==null){
					borrowType.setLogPath(null);
					flag=borrowTypeService.saveBorrowType(borrowType);
				}else if(logPath!=null && logPath.length==1){
					flag=borrowTypeService.saveBorrowType(borrowType);
				}
			SpringUtils.renderDwzResult(response, flag, flag?"标种设置操作成功":"标种设置操作失败",DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id").toString());
			
		}catch (Exception e) {
			
			SpringUtils.renderDwzResult(response, false, e.getLocalizedMessage(),"",paramsMap.get("right_id").toString());
			logger.error("标种设置添加/修改失败！",e);
			flag = false;
		}
		
		
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "deleteBorrowType")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> paramsMap = getParameters(request);
		boolean flag=false;
		try{
			Integer borrowTypeId=Integer.parseInt(paramsMap.get("id"));
			flag=borrowTypeService.deleteById(borrowTypeId);
		}catch (Exception e) {
			
			logger.error("标种删除失败！",e);
			flag = false;
		}
		
		SpringUtils.renderDwzResult(response, flag, flag?"标种删除成功":"标种删除失败","",paramsMap.get("right_id").toString(),"sys/v_getAllBorrowTypeList?right_id="+paramsMap.get("right_id").toString());

	}
	@RequestMapping("checkBorrowTypeCode")
	public void checkChannelCode(HttpServletRequest request, HttpServletResponse response,Model model,BorrowType borrowType){
	       String id=request.getParameter("id");
	       String code= request.getParameter("code");
	       Integer  totalCount=borrowTypeService.getByBorrowTypeCode(id,code);
	      if(totalCount==0){
	    	  SpringUtils.renderText(response, "success");
	      }
	      else{
	    	  SpringUtils.renderText(response, "fail");
	      }
	       
		
	}
	
	
}
