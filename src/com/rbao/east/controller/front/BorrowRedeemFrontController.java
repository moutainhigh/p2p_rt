package com.rbao.east.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.User;
import com.rbao.east.service.BorrowRedeemService;
import com.rbao.east.utils.SpringUtils;
/**
 *赎回
 * */
@Controller
@RequestMapping("borrowRedeem/")
public class BorrowRedeemFrontController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(BorrowRedeemFrontController.class);

	@Autowired
	private BorrowRedeemService borrowRedeemService;

	@RequestMapping("/borrowRedeemRedeemAuditing/{tenderId}.do")
	public void borrowRedeemRedeemAuditing(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable("tenderId") Integer tenderId) {
		JsonResult jsonResult = new JsonResult();
		Map<String, Object> param=new HashMap<String, Object>();
		User user = this.loginFrontUser(request);
		try {
			if (user == null) {
				throw new RuntimeException("您当前尚无登录");
			}
			param.put("IpAddr", this.getIpAddr(request));
			param.put("tenderId", tenderId);
			boolean bool = borrowRedeemService.saveBorrowRedeem(param);
			if (bool) {
				jsonResult.setCode("200");
				jsonResult.setMsg("赎回申请成功！！！");
			} else {
				jsonResult.setCode("300");
				jsonResult.setMsg("赎回申请失败！！！");
			}
		} catch (Exception e) {
			jsonResult.setCode("300");
			jsonResult.setMsg(e.getLocalizedMessage());
			logger.error("赎回申请失败" + e);
		}
		
		//添加日志		
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("投资赎回申请");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW_REDEEM);
		operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
		operatorLog.setOperatorParams(param.toString());
		operatorLog.setOperatorReturn(jsonResult.getMsg());
		operatorLog.setOperatorStatus(Integer.parseInt(jsonResult.getCode()));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addFrontLog(operatorLog);
		
		SpringUtils.renderJson(response, jsonResult);
	}

}
