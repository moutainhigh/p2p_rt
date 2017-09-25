package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.rbao.east.entity.ShopOrder;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.ShopGoodsService;
import com.rbao.east.service.ShopOrderService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;

//订单管理
@Controller
@RequestMapping("shop/")
public class ShopOrderAdminController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(ShopOrderAdminController.class);
	@Autowired
	private ShopOrderService shopOrderService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	UserService usrSvc;
	@Autowired
	ShopGoodsService goodsSvc;

	// 订单列表
	@RequestMapping(Constants.PRE_PATH_VIEW + "getShopOrderList")
	public String getShopOrderList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = loginAdminUser(request);
		Map<String, String> paramsMap = getParameters(request);
		PageModel pageMode = shopOrderService.getShopOrderList(paramsMap);
		model.addAttribute("pm", pageMode);
		model.addAttribute("searchParams", paramsMap);
		
		request.getSession(true).setAttribute("queryParams", paramsMap);
		
		paramsMap.put("userId", String.valueOf(user.getId()));
		model.addAttribute("right_id", paramsMap.get("right_id"));
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);

		return "shop/order/shopOrderList";
	}

	// 进入页面
	@RequestMapping(Constants.PRE_PATH_VIEW + "updateShopOrder")
	public String updateShopOrder(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));

		ShopOrder shopOrder = shopOrderService.getById(Integer
				.parseInt(paramsMap.get("id")));
		model.addAttribute("shopOrder", shopOrder);
		model.addAttribute("usr",usrSvc.getById(shopOrder.getUserId())); 
		model.addAttribute("goods",goodsSvc.getById(shopOrder.getGoodsId())); 
		model.addAttribute("type", paramsMap.get("type"));
		return "shop/order/updateShopOrder";
	}

	// 发货
	@RequestMapping(Constants.PRE_PATH_VIEW + "update")
	public void updateShopOrder(HttpServletResponse response,
			HttpServletRequest request, Model model, ShopOrder shopOrder) {
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		JsonResult flag = null;
		String fl = paramsMap.get("type");

		try {

			shopOrder.setId(Integer.parseInt(paramsMap.get("id")));
			if (fl.equals("4")) { // 发货
				shopOrder.setStatus(shopOrder.status_yes_send);
				flag = shopOrderService.updateShopOrder(shopOrder);
			} else if (fl.equals("2")) { // 平台取消订单
				flag = shopOrderService.cancelUserOrder(
						Integer.parseInt(paramsMap.get("id")),
						shopOrder.status_cancle_by_plat);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发货失败！", e);
			flag = new JsonResult("102", "操作失败");
		}
		// 添加日志
		User user = loginAdminUser(request);
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("修改订单");
		operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_SHOP_ORDER);
		operatorLog.setOperatorParams(paramsMap.toString());
		operatorLog.setOperatorReturn(flag.getMsg());
		operatorLog.setOperatorStatus(Integer.parseInt(flag.getCode()));
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);

		SpringUtils.renderDwzResult(response, flag.isSuccessed(),
				flag.getMsg(), DwzResult.CALLBACK_CLOSECURRENT,
				paramsMap.get("right_id").toString());

	}

	// 导出
	@RequestMapping("toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response) {

		try {
			Map<String, String> maps = (Map) request.getSession().getAttribute("queryParams");
			maps.put("pageNum", "1");
			maps.put("numPerPage", "500000");
			maps.remove("userId");
			
			PageModel pm = shopOrderService.getShopOrderList(maps);
			List list = pm.getList();
			// List<Map<String, String>> ars = pm.getList();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
			String[] titles = { "用户名", "订单号", "商品名称", "订单商品数量", "下单时间",
					"收件人地址", "收件人名称", "收件人电话", "状态" };

			List<String[]> contents = new ArrayList<String[]>();
			for (int i = 0; i < list.size(); i++) {
				Map m = (Map) list.get(i);

				// User user = (User) list.get(i);

				// System.out.println("user:---"+user.toString());
				String[] conList = new String[9];
				conList[0] = StringUtil.toString(m.get("userName"));
				// conList[1] = StringUtil.toString(map.get("type.name"));

				// System.out.println(user.getType().getName());
				// conList[1] = StringUtil.toString(user.getType().getName());

				conList[1] = StringUtil.toString(m.get("orderNo"));
				conList[2] = StringUtil.toString(m.get("goodsName"));
				conList[3] = StringUtil.toString(m.get("goodsCount"));
				conList[4] = StringUtil.toString(m.get("createTime"));
				conList[5] = StringUtil.toString(m.get("recvAddress"));
				conList[6] = StringUtil.toString(m.get("recvUser"));
				conList[7] = StringUtil.toString(m.get("recvTel"));
				/*
				 * conList[7] = LotteryRecord.ALL_LotteryRecord_STATUS.
				 * get(Integer.valueOf(m.get("stats").toString()));
				 */
				conList[8] = ShopOrder.ALL_ShopOrder_STATUS.get(Integer
						.valueOf(m.get("status").toString()));

				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(("user-data" + ".xls").getBytes("UTF-8"),
							"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "订单列表", titles, contents);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出excel失败", e);
		}

	}

}
