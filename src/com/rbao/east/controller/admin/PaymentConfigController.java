package com.rbao.east.controller.admin;

import java.util.HashMap;
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
import com.rbao.east.entity.PaymentConfig;
import com.rbao.east.entity.SysModule;
import com.rbao.east.service.PaymentConfigService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.SpringUtils;

/**
 * 支付配置
 * */
@Controller
@RequestMapping("sys/")
public class PaymentConfigController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(PaymentConfigController.class);

	@Autowired
	private PaymentConfigService paymentConfigService;
	@Autowired
	private SysModuleService moduleService;
	
	//支付方式列表
	@RequestMapping(Constants.PRE_PATH_VIEW + "getList")
	public String list(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = paymentConfigService.getpagedList(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		return "sys/paymentconfiglist";
	}
	
	//根据ID查询支付方式信息
	@RequestMapping(Constants.PRE_PATH_VIEW + "getById")
	public String getById(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		PaymentConfig paymentConfig = paymentConfigService.getById(Integer.parseInt(param.get("supportID").toString()));
		model.addAttribute("paymentConfig", paymentConfig);
		model.addAttribute("right_id",param.get("right_id"));
		return "sys/savepaymentconfig";
	}
	
	//跳转到添加支付方式页面
	@RequestMapping(Constants.PRE_PATH_VIEW + "getPaymentConfig")
	public String getPaymentConfig(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		model.addAttribute("right_id",param.get("right_id"));
		return "sys/savepaymentconfig";
	}
	
	//保存支付方式信息
	@RequestMapping(Constants.PRE_PATH_EDIT + "savePaymentConfig")
	public void savePaymentConfig(HttpServletRequest request, HttpServletResponse response,PaymentConfig paymentConfig){
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		try {
			paymentConfig.setPaymentIco(param.get("url"));
			Map map = new HashMap();
			map.put("paymentCode",paymentConfig.getPaymentCode() );
			map.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON);
			if(paymentConfig.getId()==null &&  paymentConfigService.selectTotalCount(map)>0){
				SpringUtils.renderDwzResult(response, false, paymentConfig.getPaymentName()+"已经存在，不能重复添加",DwzResult.CALLBACK_CLOSECURRENTDIALOG);
				return;
			}
			succ = paymentConfigService.save(paymentConfig);
		} catch (Exception e) {
			
		}
		SpringUtils.renderDwzResult(response, succ, succ?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id")); 
	}
	
	//删除支付方式
	@RequestMapping(Constants.PRE_PATH_EDIT + "deletePaymentConfig")
	public void deletePaymentConfig(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param=this.getParameters(request);
		boolean succ = false;
		try {
			succ = paymentConfigService.deleteById(Integer.parseInt(param.get("supportID").toString()));
		} catch (Exception e) {
			
			logger.error("删除支付方式出错："+Integer.parseInt(param.get("supportID").toString()),e);
		}
		SpringUtils.renderDwzResult(response, succ, succ?"删除支付方式成功":"删除支付方式失败","",param.get("right_id").toString(),"sys/v_getList?right_id="+param.get("right_id").toString());
		logger.info("删除支付方式成功");
	}
}
