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
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.ShopCategory;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.ShopCategoryService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
/**
 * 积分商城
 * @author Sandy
 *
 */
@Controller
@RequestMapping("shop/")
public class ShopCategoryController  extends BaseController{
	private static Logger logger = LoggerFactory
			.getLogger(ShopCategoryController.class);
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private SysModuleService moduleService;
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getShopCategoryList")
	public String getShopCategoryList(HttpServletRequest request, HttpServletResponse response,
			Model model ) {
		User user = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);		
		PageModel  pageModel=shopCategoryService.getShopCategoryList(paramsMap);
		model.addAttribute("pm",pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		
		paramsMap.put("userId", String.valueOf(user.getId()));
		
		model.addAttribute("right_id", paramsMap.get("right_id"));//刷新
    	// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		
		return "shop/category/shopCategoryList";
	}
	
	//增加
	@RequestMapping(Constants.PRE_PATH_EDIT+"addShopCategoryType")
	public String addShopCategoryType(HttpServletRequest request,HttpServletResponse response,
			Model model){
		Map<String, String > paramsMap=getParameters(request);
		model.addAttribute("right_id",paramsMap.get("right_id") );
		
		
		return "shop/category/addUpdateShopCategory";
	}
	//进入页面
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateShopCategoryType")
	public String updateBankType(HttpServletRequest request, HttpServletResponse response,
			Model model ) {
		Map<String,String> paramsMap = getParameters(request);
		model.addAttribute("right_id",paramsMap.get("right_id"));
		
		ShopCategory shopCategory=shopCategoryService.getById(Integer.parseInt(paramsMap.get("id")));
		model.addAttribute("shopCategory",shopCategory);
		return "shop/category/addUpdateShopCategory";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveOrUpdate")
	public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response,
			Model model,ShopCategory shopCategory ) {
		Map<String,String> paramsMap = getParameters(request);
		User user = loginAdminUser(request);
		JsonResult flag=null;
		try{
			shopCategory.setUpdateTime(new Date());
			flag=shopCategoryService.saveOrUpdate(shopCategory);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("添加/修改失败！",e);
			flag = new JsonResult("909","保存栏目失败："+e.toString());
		}
		//添加日志
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("保存商品类别");
		operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_SHOP_CATEGORY);
		operatorLog.setOperatorParams(paramsMap.toString());
		operatorLog.setOperatorReturn(flag.getMsg());
		operatorLog.setOperatorStatus(Integer.parseInt(flag.getCode())); 
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, flag.isSuccessed(), flag.getMsg(),DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id").toString());
		
	}
	
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> paramsMap = getParameters(request);
		User user = loginAdminUser(request);
		JsonResult flag=null;
		try{
			Integer shopCategoryId=Integer.parseInt(paramsMap.get("id"));
			
			flag=shopCategoryService.deleteById(shopCategoryId);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("删除失败！",e);
			flag = new JsonResult("909","删除栏目失败："+e.toString());
		}
		//添加日志
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("删除商品类别");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_SHOP_CATEGORY);
				operatorLog.setOperatorParams(paramsMap.toString());
				operatorLog.setOperatorReturn(flag.getMsg());
				operatorLog.setOperatorStatus(Integer.parseInt(flag.getCode())); 
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, flag.isSuccessed(), flag.getMsg(),"",paramsMap.get("right_id").toString(),"shop/v_getShopCategoryList?right_id="+paramsMap.get("right_id").toString());
	}
	
	
	@RequestMapping("checkChannelCode")
	public void checkChannelCode(HttpServletRequest requeset,HttpServletResponse response,Model model){
		
		String shopCategoryId=requeset.getParameter("shopCategoryId");
		String categoryCode =requeset.getParameter("categoryCode");
		Integer totalCount=shopCategoryService.checkSameCode(shopCategoryId, categoryCode);
		
		if (totalCount==0) {
			SpringUtils.renderText(response,"success");
		}else{
			
			SpringUtils.renderText(response,"fail");
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
