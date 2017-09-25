package com.rbao.east.controller.front;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowTransfer;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.HtmlUtil;
import com.rbao.east.utils.SpringUtils;
/**
 * 前台债权
 * */
@Controller
@RequestMapping("borrowTransfer/")
public class BorrowTransferFrontController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(BorrowTransferFrontController.class);

	@Autowired
	private BorrowTransferService borrowTransferService;

	@Autowired
	private BorrowService borrowQueryService;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private BorrowTenderService borrowTenderService;
	
	@Autowired
	private BorrowRepossessedService borrowRepossessedService;
	
	@Autowired
	private UserService userService;
	

	@RequestMapping("/showBorrowTransInfo/{transferId}.do")
	public String showBorrowTransInfo(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable("transferId") Integer transferId) {
		//解密  加密后的transferEId
//		DesEncrypt enc=new DesEncrypt();
//		Integer transferId=Integer.parseInt(enc.decrypt(transferEId));
		
		Integer isDay=2;
		
		User user = this.loginFrontUser(request);
		BorrowTransfer borrowTransfer = borrowTransferService
				.showBorrowTransferById(transferId);
		
		Borrow borrow = borrowQueryService.findBorrowByTransferId(transferId);
		
		//天标
		if(borrow.getIsDay().equals(Borrow.IS_DAY_Y)||borrow.getRepaymentStyle().equals(Borrow.REPAYMENT_STYLE_ONETIME)){
			BorrowRepossessed borrowRepossessed= borrowRepossessedService.getborrowRepossessedByTransferId(transferId);
			Integer daysBetween=DateUtils.daysBetween(new Date(), borrowRepossessed.getPrepareDatetime());
			model.addAttribute("daysBetween", daysBetween);
			isDay=1;
			
			
		}
		BorrowTender borrowTender = borrowTenderService
				.selectBorrowTenderByBorrowTenderId(borrowTransfer
						.getTenderId());
		int leftDays = DateUtils.daysBetween(new Date(),
				borrowTransfer.getEndTime());

		if (leftDays <= 0) {
			model.addAttribute("leftDays", "0");
		} else {
			model.addAttribute("leftDays", leftDays);
		}
		
		User borrowUser=userService.getById(borrowTender.getUserId());
		
		String borrowUserAccount =borrowUser.getUserAccount();
		borrowUserAccount = borrowUserAccount.substring(0, 2)+"****"+borrowUserAccount.substring(borrowUserAccount.length()-3, borrowUserAccount.length());
		
		long endTime = borrowTransfer.getEndTime().getTime();
		long nowTime = new Date().getTime();
		if (user != null) {
			if(StringUtils.isEmpty(user.getUserPaypassword())){
				model.addAttribute("userPaypassword",1);
			}else{
				model.addAttribute("userPaypassword",0);
			}
			UserAccount userAccount = userAccountService.getByUserId(user
					.getId());
			model.addAttribute("userAccount", userAccount);
		}
		model.addAttribute("borrowUser", borrowUser);
	
		model.addAttribute("borrowTender", borrowTender);
		model.addAttribute("borrowTransfer", borrowTransfer);
		model.addAttribute("borrow", borrow);
		model.addAttribute("borrowUserAccount", borrowUserAccount);
		String borrowConent = HtmlUtil.getTextFromHtml(borrow.getBorrowContent());
		model.addAttribute("borrowConent", borrowConent);
		/*model.addAttribute("isDay", isDay);*/
		/*model.addAttribute("hasLogin", user == null ? false : true);*/
		
		model.addAttribute("endTime", endTime);
		model.addAttribute("nowTime", nowTime);
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		return "borrow/borrowTransferInfo";
	}

	/**
	 * 申请债权转让
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("requestTransfer.do")
	public void requestTransfer(HttpServletRequest request,
			HttpServletResponse response,BorrowTransfer transfer) {
		User user = this.loginFrontUser(request);
		if (user == null) {
			SpringUtils.renderJsonResult(response, "115", "获取用户信息失败，请重新登录");
			return;
		}
		transfer.setCreateIp(super.getIpAddr(request));
		ServiceResult sResult = borrowTransferService.allowToTransfer(transfer);
		try {
			if (sResult.isSuccessed()) {
				borrowTransferService.save(transfer);
			}
		} catch (Exception e) {
			
			logger.error("save transfer error:" + transfer, e);
			sResult = new ServiceResult("165", "转让失败，请稍后重试");
		}
		// 添加日志
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("提交债权转让");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
		operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
		operatorLog.setOperatorParams(transfer.toString());
		operatorLog.setOperatorReturn(sResult.getMsg());
		operatorLog.setOperatorStatus(Integer.parseInt(sResult.getCode()));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addFrontLog(operatorLog);

		SpringUtils.renderJsonResult(response, sResult.getCode(),sResult.getMsg());
	}
	@RequestMapping("/getBorrowRepossessedByTransferId.do")
	public void getBorrowRepossessedByTransferId(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		PageModel page = borrowTransferService
				.selectBorrowRepossessedByTransferId(param);
		SpringUtils.renderJson(response, page);
	}

	@RequestMapping("/getBorrowTransferAuction.do")
	public void getBorrowTransferAuction(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		
		PageModel page = borrowTransferService
				.selectBorrowTransferAuction(param);
		System.out.println(page.getList().size());
		SpringUtils.renderJson(response, page);
	}

	/**
	 * 竞拍
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/saveTransfer.do")
	public void saveTransfer(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		Map<String, Object> result = new HashMap<String, Object>();
		JsonResult jsonResult = new JsonResult();
	/*	System.out.println(request.getParameter("captcha").toLowerCase());*/
		User user = this.loginFrontUser(request);
		try {
			if (null != user) {
				/*if (this.validateCaptcha(request)) {*/
					if (param.containsKey("payPassword")) {
						result.putAll(param);
						result.put("user", user);
						boolean bool = borrowTransferService
								.saveTransfer(result);
						if (bool) {
							jsonResult.setCode("200");
							jsonResult.setMsg("竞拍成功！！！");
						} else {
							jsonResult.setCode("300");
							jsonResult.setMsg("竞拍失败！！！请稍后再试！！！");
						}
					} else {
						jsonResult.setCode("300");
						jsonResult.setMsg("请输入支付密码！！！");
					}
				/*} else {
					jsonResult.setCode("300");
					jsonResult.setMsg("请输入验证码！！！");
				}*/
			} else {
				jsonResult.setCode("300");
				jsonResult.setMsg("请先登录！！！");
			}
		} catch (Exception e) {
			jsonResult.setCode("300");
			jsonResult.setMsg(e.getLocalizedMessage());
		}
		SpringUtils.renderJson(response, jsonResult);
	}

}
