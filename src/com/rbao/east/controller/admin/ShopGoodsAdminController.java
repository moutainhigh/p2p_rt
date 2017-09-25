package com.rbao.east.controller.admin;

import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.ShopGoods;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.ShopCategoryService;
import com.rbao.east.service.ShopGoodsService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;
/**
 * 商品维护
 * @author Liutq
 *
 */
@Controller
@RequestMapping("shopGoods/")
public class ShopGoodsAdminController  extends BaseController{
	private static Logger logger = LoggerFactory
			.getLogger(ShopGoodsAdminController.class);
	
	final static String goods_list_page = "shop/goods/goodsList";
	final static String goods_edit_page = "shop/goods/goodsEdit";
	@Autowired
	private ShopGoodsService goodsService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	ShopCategoryService shopCtgService;
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getList")
	public String getList(HttpServletRequest request, HttpServletResponse response,
			Model model ) {
		User user = loginAdminUser(request);
		Map<String,String> paramsMap = getParameters(request);		
		PageModel  pageModel=goodsService.getAdminPage(paramsMap);
		model.addAttribute("pm",pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		
		paramsMap.put("userId", String.valueOf(user.getId()));
		
		model.addAttribute("right_id", paramsMap.get("right_id"));//刷新
		model.addAttribute("shopCtg",shopCtgService.selectEnableList());//商品分类
    	// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		
		return goods_list_page;
	}
	@RequestMapping(Constants.PRE_PATH_VIEW + "enterEditPage")
	public String addBankType(HttpServletRequest request, HttpServletResponse response,
			Model model ) {
		Map<String,String> paramsMap = getParameters(request);
		model.addAttribute("right_id",paramsMap.get("right_id"));
		String goodsId = paramsMap.get("id");
		if(StringUtils.isNotEmpty(goodsId)){ //添加的时候goodsId为空
			model.addAttribute("record",goodsService.getById(Integer.valueOf(goodsId)));
		}
		model.addAttribute("shopCtg",shopCtgService.selectEnableList());//商品分类
		model.addAttribute("nowDate",new Date());
		return goods_edit_page;
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model,ShopGoods goods ) {
		Map<String,String> paramsMap = getParameters(request);
		User user = loginAdminUser(request);
		JsonResult jr = null;
		try{
			jr = this.goodsService.save(goods);
		}catch (Exception e) {
			logger.error("save error:"+goods,e);
			jr = new JsonResult("909","保存失败："+e.toString());
		}
		//添加日志
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("保存商品");
		operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_SHOP_GOODS);
		operatorLog.setOperatorParams(goods.toString());
		operatorLog.setOperatorReturn(jr.getMsg());
		operatorLog.setOperatorStatus(Integer.parseInt(jr.getCode())); 
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, jr.isSuccessed(), jr.getMsg(),DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id").toString());
		
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "delete/{id}")
	public void delete(HttpServletRequest request, HttpServletResponse response
			,@PathVariable("id") Integer id) {
		User user = loginAdminUser(request);
		JsonResult jr = null;
		try{
			
			jr = goodsService.deleteById(id);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("delete error:"+id,e);
			jr = new JsonResult("909","删除失败:"+e.toString());
		}
		//添加日志
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("删除商品");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_SHOP_GOODS);
				operatorLog.setOperatorParams(StringUtil.toString(id));
				operatorLog.setOperatorReturn(jr.getMsg());
				operatorLog.setOperatorStatus(Integer.valueOf(jr.getCode()));
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, jr.isSuccessed(), jr.getMsg());

	}
	/**
	 * 检查code是否存在
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkCode")
	public void checkCode(HttpServletRequest request, HttpServletResponse response){
	    String code=request.getParameter("code");
	    String idStr= request.getParameter("id");
	    Integer id = null;
	    if(StringUtils.isNotEmpty(idStr)){
	    	id = Integer.valueOf(idStr);
	    }
	    boolean hasSame = goodsService.checkHasSameCode(code, id);
	    SpringUtils.renderText(response, hasSame?"fail":"success");
	}
	
}
