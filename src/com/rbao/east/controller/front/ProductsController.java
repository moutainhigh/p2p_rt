package com.rbao.east.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.FinancialProducts;
import com.rbao.east.service.FinancialProductsService;
import com.rbao.east.utils.SpringUtils;
/**
 * 理财预约
 * */
@Controller
@RequestMapping("products/")
public class ProductsController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(ProductsController.class);
	@Autowired
	private FinancialProductsService financialProductsService;
	
	@RequestMapping("toProducts.do")
	public String toProducts(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String baseUrl = request.getScheme()+
						 "://"+request.getServerName()+
						 ":"+request.getServerPort()+
						 request.getContextPath();
		model.addAttribute("baseUrl",baseUrl);
		return "products/products";
	}
	
	@RequestMapping("productsLeft.do")
	public String producstLeft(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "products/productsLeft";
	}
	
	@RequestMapping("products.do")
	public void addEmail(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		if (paramsMap == null) {
			paramsMap = new HashMap<String, String>();
		}
		PageModel pageModel = financialProductsService.getFinancialProductsList(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);
	}
	
	@RequestMapping("getProductById.do")
	public void getProductById(HttpServletRequest request,HttpServletResponse response,Model model){
		String id = request.getParameter("productId");
		FinancialProducts financialProducts = financialProductsService.getById(Integer.parseInt(id));
		model.addAttribute("product", financialProducts);
		SpringUtils.renderJson(response, financialProducts);
	}
}
