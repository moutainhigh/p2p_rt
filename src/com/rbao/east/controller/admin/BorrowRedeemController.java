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
import com.rbao.east.entity.BorrowRedeem;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.BorrowRedeemService;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;

/**
 * 投资赎回操作
 * */
@Controller
@RequestMapping("borrowRedeem/")
public class BorrowRedeemController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(BorrowRedeemController.class);

	@Autowired
	private SysModuleService moduleService;
	
	@Autowired
	private BorrowRedeemService borrowRedeemService;
	
	@Autowired
	private BorrowRepossessedService borrowRepossessedService; 
	
	
	/**
	 * 获取列表
	 * */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowRedeemList")
	public String getBorrowByStatusList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> param = getParameters(request);
		param.put("userId", this.loginAdminUser(request).getId().toString());
		try {
			if(param.containsKey("redeemStatus")){
				if(Integer.parseInt(param.get("redeemStatus").toString())==0){
					param.remove("redeemStatus");
				}
			}
			List<SysModule> righSubtList=moduleService.getRightGroupList(param);
			PageModel page=borrowRedeemService.getBorrowByStatusList(param);
			model.addAttribute("pm",page);
			model.addAttribute("righSubtList", righSubtList);
			model.addAttribute("right_id", param.get("right_id"));
			model.addAttribute("params",param);
		} catch (Exception e) {
			
		}
		return "borrowRedeem/getBorrowRedeemList";
	}
	
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "checkBorrowRedeem")
	public String checkBorrowRedeem(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		Map<String, Object> result=new HashMap<String, Object>();
		User user=this.loginAdminUser(request);
		String target=null;
		try {
			if("toJsp".equals(param.get("type").toString())){
				BorrowRedeem borrowRedeem=borrowRedeemService.getBorrowRedeemById(Integer.parseInt(param.get("supportID").toString()));
				List<Integer> pks=borrowRedeemService.getRepossessedIdListByPk(Integer.parseInt(param.get("supportID").toString()));
				result.put("list", pks);
				List list=borrowRepossessedService.getBorrowRepossessedListByPkList(result);
				model.addAttribute("pm",list);
				model.addAttribute("borrowRedeem",borrowRedeem);
				model.addAttribute("right_id",param.get("right_id"));
				target="borrowRedeem/checkBorrowRedeemInfo";
			}else if("update".equals(param.get("type").toString())){
				String status = "通过";
				if((param.get("redeemStatus")).equals(BorrowRedeem.STATUS_FAIL)){
					status = "不通过";
				}
				result.put("userId", user.getId());
				result.put("ipAddress", this.getIpAddr(request));
				result.putAll(param);
				boolean bool=borrowRedeemService.updateBorrowRedeem(result);
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("投资赎回审核"+status);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW_REDEEM);
				operatorLog.setOperatorParams(result.toString());
				operatorLog.setOperatorReturn(bool?"借款赎回审核成功":"借款赎回审核失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
				SpringUtils.renderDwzResult(response, bool, bool?"借款赎回审核成功":"借款赎回审核失败，请稍后再试！！！",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());
			}
		} catch (Exception e) {
			SpringUtils.renderDwzResult(response, false,e.getLocalizedMessage()
					,"",param.get("right_id").toString()); 
				
				logger.error("赎回审核失败，异常信息:" + e);
		}
		
		return target;
	}
	
}
