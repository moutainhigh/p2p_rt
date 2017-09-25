package com.rbao.east.controller.front;

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

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.User;
import com.rbao.east.service.BorrowRelatedService;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTypeHandleService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.SpringUtils;

/**
 * 标的相关操作
 * 当前类会被session过滤
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/iborrow")
public class BorrowHandleController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(BorrowHandleController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private BorrowTypeService borrowTypeService;
	@Autowired
	private BorrowService borrowQueryService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private BorrowRelatedService borrowRelatedService;
	@Autowired
	private BorrowRepaymentService borrowRepaymentService;

	/**
	 * 进入发标页面
	 * @param request
	 * @param response
	 * @param model
	 * @param code
	 * @return
	 */
	///borrowInvitation/{code}.do"
	@RequestMapping("/borrowInvitation/{code}.do")
	public String borrowInvitation(HttpServletRequest request,
			HttpServletResponse response, Model model,@PathVariable("code") String code) {
		//获得标种code
		BorrowType borrowType = borrowTypeService.getBorrowTypeByCode(code.toUpperCase());
		if(borrowType==null){//空标
			return null;
		}
		model.addAttribute("borrowType",borrowType);
		String result="borrow/borrowInvitation";
		try {
			User user=this.userService.getById(this.loginFrontUser(request).getId());
			if(user.getRealnameStatus()==User.REALNAME_PASS&&user.getEmailStatus()==User.EMAIL_PASS&&user.getPhoneStatus()==User.PHONE_PASS){
				result="borrow/borrowInvitation";
			}else{
				if(user.getRealnameStatus()!=User.REALNAME_PASS){
					result="redirect:/basics/personalData.do";
				}
				if(user.getEmailStatus()!=User.EMAIL_PASS){
					result="redirect:/basics/personalData.do";
				}
				if(user.getPhoneStatus()!=User.PHONE_PASS){
					result="redirect:/basics/personalData.do"; 
				}
			}
		} catch (Exception e) {
			
		}
		return result;
	}

	/**
	 * 前台保存发标
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param borrow
	 */
	@RequestMapping("saveBorrow.do")
	public void saveBorrow(HttpServletRequest request,
			HttpServletResponse response, Borrow borrow) {
		JsonResult results=new JsonResult();
		User user = this.loginFrontUser(request);
		ServiceResult result = new ServiceResult();
		//获得标code
		String borrowTypeName=request.getParameter("borrowType");
		try {
			if (user != null) {
				if (validateCaptcha(request)) {
					BorrowType borrowType = borrowTypeService.getBorrowTypeByName(borrowTypeName);
					// 获取标种对应的service实现类
					BorrowTypeHandleService borrowService = SpringUtils.getBean(
							BorrowTypeHandleService.class, borrowType.getDealService());

					borrow.setUserId(user.getId());
					borrow.setBorrowAddip(getIpAddr(request));
					//判断是否有未完成的标
					Integer[] unFullStatus = {Borrow.STATUS_NEW ,Borrow.STATUS_FIRSTAUDIT_YES};
					if(borrowQueryService.getBorrowCount(borrow.getUserId(), unFullStatus)>0){ //查询是否有未完成的标
						result = new ServiceResult("101","还有未完成的标，不能继续发");
					}else{
						result = borrowService.allowToPublishBorrow(borrow);
					}
					if (result.isSuccessed()) {
						result = borrowService.saveBorrow(borrow);
						results.setCode("200");
						results.setMsg(result.getMsg());
						// 添加日志
						OperatorLog operatorLog = new OperatorLog();
						operatorLog.setLogUserid(user.getUserAccount());
						operatorLog.setOperatorTitle("发布借款");
						operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
						operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
						operatorLog.setOperatorParams(borrow.toString());
						operatorLog.setOperatorReturn((String)results.getMsg());
						operatorLog.setLinkUrl(getURI(request));
						operatorLogService.addFrontLog(operatorLog);
					}else{
						results=new JsonResult(result);
						// 添加日志
						OperatorLog operatorLog = new OperatorLog();
						operatorLog.setLogUserid(user.getUserAccount());
						operatorLog.setOperatorTitle("发布借款");
						operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
						operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
						operatorLog.setOperatorParams(borrow.toString());
						operatorLog.setOperatorReturn((String)results.getMsg());
						operatorLog.setLinkUrl(getURI(request));
						operatorLogService.addFrontLog(operatorLog);
					}
				} else {
					results.setCode("300");
					results.setMsg("验证码输入错误");
				}
			} else {
				results.setCode("300");
				results.setMsg("获取用户信息失败，请重新登录");
			}
		} catch (Exception e) {
			
			results.setCode("300");
			results.setMsg("保存发标失败");
			logger.error("save borrow error:" + borrow, e);
		}
		SpringUtils.renderJson(response, results);
	}
		
	/**
	 * 查询正在招标借款标
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("borrowStatus.do")
	public String borrowStatus(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/evaluateApply/borrowStatus";
	}

	@RequestMapping("borrowStatusPage.do")
	public void borrowStatusPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> paramsMap = getParametersO(request);
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		paramsMap.put("borrowStatus", new String []{String.valueOf(Borrow.STATUS_FIRSTAUDIT_YES),Borrow.STATUS_FULL.toString()});
		PageModel pageModel = borrowQueryService.selectBorrowsByStatusAndUserId(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);

	}
	/**
	 * 查询尚未发布的借款标
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("borrowStatus1.do")
	public String borrowStatus1(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/evaluateApply/borrowStatus1";
	}

	@RequestMapping("borrowStatusPage1.do")
	public void borrowStatusPage1(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> paramsMap = getParametersO(request);
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		paramsMap.put("borrowStatus", new String [] {Borrow.STATUS_NEW.toString()});
		PageModel pageModel = borrowQueryService.selectBorrowsByStatusAndUserId(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);

	}
	
	/**
	 * 查询正在还款的借款
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("borrowStatus2.do")
	public String borrowStatus2(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/evaluateApply/borrowStatus2";
	}

	@RequestMapping("borrowStatusPage2.do")
	public void borrowStatusPage2(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> paramsMap = getParametersO(request);
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		paramsMap.put("borrowStatus", new String []{String.valueOf(Borrow.STATUS_REPLYING)});
		PageModel pageModel = borrowQueryService.selectBorrowsByStatusAndUserId(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);

	}
	/**
	 * 已还完的借款
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("borrowStatus3.do")
	public String borrowStatus3(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/evaluateApply/borrowStatus3";
	}

	@RequestMapping("borrowStatusPage3.do")
	public void borrowStatusPage3(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> paramsMap = getParametersO(request);
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		paramsMap.put("borrowStatus",new String []{ String.valueOf(Borrow.STATUS_REPLY_SUCCESS)});
		PageModel pageModel = borrowQueryService.selectBorrowsByStatusAndUserId(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);

	}
	
	/**
	 * 逾期中的借款
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 2015-3-20
	 * 
	 */
	@RequestMapping("borrowStatus4.do")
	public String borrowStatus4(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/evaluateApply/borrowStatus4";
	}
	
	/**
	 * 取消借款标
	 */
	@RequestMapping("cancelBorrow.do")
	public String cancelBorrow(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		Borrow borrow = new Borrow();
		borrow.setId(Integer.parseInt(paramsMap.get("id")));
		borrow.setBorrowStatus(Borrow.STATUS_CANCEL_BY_SELF);
		borrowQueryService.saveBorrow(borrow);
		return "userinfo/evaluateApply/borrowStatus1";
	}
	/**
	 * 还款明细
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("borrowRepayment.do")
	public String borrowRepayment(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "userinfo/evaluateApply/borrowRepayment";
	}

	@RequestMapping("borrowRepaymentPage.do")
	public void borrowRepaymentPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		Integer userId = this.loginFrontUser(request).getId();
		paramsMap.put("userId", String.valueOf(userId));
		PageModel pageModel = borrowRepaymentService.selectBorrowRepayment(paramsMap);
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		SpringUtils.renderJson(response, pageModel);

	}
	
}
