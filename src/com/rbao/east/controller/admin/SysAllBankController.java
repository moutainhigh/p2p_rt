package com.rbao.east.controller.admin;



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
import com.rbao.east.entity.AllBank;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.AllBankService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.SpringUtils;
/**
 * 银行设置
 * @author Sandy
 *
 */
@Controller
@RequestMapping("sys/")
public class SysAllBankController  extends BaseController{
	private static Logger logger = LoggerFactory
			.getLogger(SysAllBankController.class);
	@Autowired
	private AllBankService allBankService;
	@Autowired
	private SysModuleService moduleService;
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAllBankList")
	public String getAllBankList(HttpServletRequest request, HttpServletResponse response,
			Model model ) {
		User user = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);		
		PageModel  pageModel=allBankService.getAllBankList(paramsMap);
		model.addAttribute("pm",pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		
		paramsMap.put("userId", String.valueOf(user.getId()));
		
		model.addAttribute("right_id", paramsMap.get("right_id"));//刷新
    	// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		
		return "sys/allBankList";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "addBankType")
	public String addBankType(HttpServletRequest request, HttpServletResponse response,
			Model model ) {
		Map<String,String> paramsMap = getParameters(request);
		model.addAttribute("right_id",paramsMap.get("right_id"));
		
		return "sys/addUpdateBankType";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateBankType")
	public String updateBankType(HttpServletRequest request, HttpServletResponse response,
			Model model ) {
		Map<String,String> paramsMap = getParameters(request);
		model.addAttribute("right_id",paramsMap.get("right_id"));
		
		AllBank allBank=allBankService.getById(Integer.parseInt(paramsMap.get("id")));
		model.addAttribute("allBank",allBank);
		return "sys/addUpdateBankType";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveOrUpdate")
	public void setBankType(HttpServletRequest request, HttpServletResponse response,
			Model model,AllBank allBank ) {
		Map<String,String> paramsMap = getParameters(request);
		boolean flag=false;
		try{
			allBank.setBankUpdatetime(new Date());
			flag=allBankService.saveOrUpdate(allBank);
		}catch (Exception e) {
			
			logger.error("银行设置添加/修改失败！",e);
			flag = false;
		}
		SpringUtils.renderDwzResult(response, flag, flag?"银行设置操作成功":"银行设置操作失败",DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id").toString());
		
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> paramsMap = getParameters(request);
		boolean flag=false;
		try{
			Integer AllBankId=Integer.parseInt(paramsMap.get("id"));
			flag=allBankService.deleteById(AllBankId);
		}catch (Exception e) {
			
			logger.error("银行删除失败！",e);
			flag = false;
		}
		
		SpringUtils.renderDwzResult(response, flag, flag?"银行删除成功":"银行删除失败","",paramsMap.get("right_id").toString(),"sys/v_getAllBankList?right_id="+paramsMap.get("right_id").toString());

	}
	@RequestMapping("checkChannelCode")
	public void checkChannelCode(HttpServletRequest request, HttpServletResponse response,Model model,AllBank allBank){
	       String allBankId=request.getParameter("allBankId");
	       String bankCode= request.getParameter("bankCode");
	       Integer  totalCount=allBankService.getByBankCode(allBankId,bankCode);
	      if(totalCount==0){
	    	  SpringUtils.renderText(response, "success");
	      }
	      else{
	    	  SpringUtils.renderText(response, "fail");
	      }
	       
		
	}
	/*@RequestMapping("changSeq")
	public void changSeq(HttpServletRequest request, HttpServletResponse response,
			Model model,AllBank allBank ) {
		allBank.setId(Integer.parseInt(request.getParameter("allBankId")) );
		allBank.setBankSequence(Integer.parseInt(request.getParameter("allBankVal")));
		try{
			allBankService.saveOrUpdate(allBank);
			SpringUtils.renderText(response, "success");
		}catch (Exception e) {
			
			logger.error("银行排序更改失败！",e);
		}
	}*/
	
}
