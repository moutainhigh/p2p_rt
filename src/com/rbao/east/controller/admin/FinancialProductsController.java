package com.rbao.east.controller.admin;

import java.text.SimpleDateFormat;
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
import com.rbao.east.entity.Dictionary;
import com.rbao.east.entity.FinancialProducts;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.DictionaryService;
import com.rbao.east.service.FinancialProductsService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
/**
 * 私募基金
 * */
@Controller
@RequestMapping("products/")
public class FinancialProductsController extends BaseController{
	private static Logger logger = LoggerFactory
	.getLogger(FinancialProductsController.class);
	
	@Autowired
	private FinancialProductsService financialProductsService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private DictionaryService dictionaryService;
	
	/**
	 * 理财产品列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getProductsList")
	public String getProductsList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		paramsMap.put("dicIshidden",String.valueOf(1));
		paramsMap.put("parentId",String.valueOf(2));
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = financialProductsService.getFinancialProductsList(paramsMap);
		List<Dictionary> productsTypes = dictionaryService.selectByParentId(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("productsTypes", productsTypes);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		return "products/productslist";
	}
	
	/**
	 * 跳转到添加理财产品页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "toAddProduct")
	public String forAddNotice(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		paramsMap.put("dicIshidden",String.valueOf(1));
		paramsMap.put("parentId",String.valueOf(2));
		model.addAttribute("right_id",paramsMap.get("right_id"));
		model.addAttribute("productsTypes", dictionaryService.selectByParentId(paramsMap));
		model.addAttribute("nowDate", new Date());
		return "products/saveproduct";
	}
	
	/**
	 * 保存理财产品
	 * @param request
	 * @param response
	 * @param model
	 * @param notice
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveProduct")
	public void saveProduct(HttpServletRequest request, HttpServletResponse response,
			Model model,FinancialProducts financialProducts){
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		model.addAttribute("right_id",param.get("right_id"));
		User user = this.loginAdminUser(request);
		Integer userId = user.getId();
		if(financialProducts.getId() == null){
			financialProducts.setProductsAdduser(userId);
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse(sdf.format(new Date()));
				financialProducts.setProductsAddtime(date);
				financialProducts.setProductsStatus(FinancialProducts.PRODUCTS_STATUS_CONFIGURABLE);
				succ = financialProductsService.saveFinancialProducts(financialProducts);
			} catch (Exception e) {
				logger.info("添加理财产品失败。");
				
			}
			//添加日志
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id：" + user.getId() + ",用户名："
					+ user.getUserAccount());
			operatorLog.setOperatorTitle("添加理财产品");
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PRODUCTS);
			operatorLog.setOperatorParams("理财产品名称："+financialProducts.getProductsTitle());
			operatorLog.setOperatorReturn(succ ? "添加成功" : "添加失败");
			operatorLog.setOperatorStatus(succ ? 200 : 300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addAdminLog(operatorLog);
		}else{
			financialProducts.setProductsUpdateuser(userId);
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse(sdf.format(new Date()));
				financialProducts.setProductsUpdatetime(date);
				succ = financialProductsService.updateFinancialProducts(financialProducts);
			} catch (Exception e) {
				logger.info("修改理财产品失败。");
				
			}
			
			//添加日志
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id：" + user.getId() + ",用户名："
					+ user.getUserAccount());
			operatorLog.setOperatorTitle("修改理财产品");
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_PRODUCTS);
			operatorLog.setOperatorParams("理财产品名称："+financialProducts.getProductsTitle());
			operatorLog.setOperatorReturn(succ ? "修改成功" : "修改失败");
			operatorLog.setOperatorStatus(succ ? 200 : 300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addAdminLog(operatorLog);
		}
		SpringUtils.renderDwzResult(response, succ, succ?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
	}
	
	/**
	 * 根据Id查看
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getProductById")
	public String getProductById(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		paramsMap.put("dicIshidden",String.valueOf(1));
		paramsMap.put("parentId",String.valueOf(2));
		model.addAttribute("productsTypes", dictionaryService.selectByParentId(paramsMap));
		model.addAttribute("product", financialProductsService.getById(Integer.parseInt(paramsMap.get("supportID").toString())));
		model.addAttribute("right_id",paramsMap.get("right_id"));
		return "products/saveproduct";
	}
}
