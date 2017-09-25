package com.rbao.east.controller.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.User;
import com.rbao.east.service.BorrowRelatedService;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.BorrowTypeHandleService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.SpringUtils;
/**
 * 前台还款
 * */
@Controller
@RequestMapping("/borrowRepayment")
public class BorrowRepaymentController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(BorrowRepaymentController.class);
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
	@Autowired
	private BorrowTransferService transferService;

	/**
	 * 还款
	 * @param request
	 * @param response
	 * @param signId
	 */
	@RequestMapping("/repay/{repayId}.do")
	public void repay(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("repayId") Integer repayId) {
		User user = this.loginFrontUser(request);
		if (user == null) {
			SpringUtils.renderJsonResult(response, "115", "获取用户信息失败，请重新登录");
			return;
		}
		DesEncrypt enc = new DesEncrypt();
		String signStr = request.getParameter("signStr");
		BorrowRepayment repay =  null;
		try {
	//		if(!enc.decrypt(signStr).equals(String.valueOf(repayId))){ //加密校验
//				SpringUtils.renderJsonResult(response, "154","数据有误，请刷新页面后重试");
//				return ;
	//		}
			repay = this.borrowRepaymentService.getBorrowRepaymentById(repayId); 
			if(repay == null){  
				SpringUtils.renderJsonResult(response, "156","未能找到记录，请刷新页面后重试");
				return ;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			SpringUtils.renderJsonResult(response, "154","数据有误，请刷新页面后重试");
			return ;
		}
		Borrow borrow = this.borrowQueryService.getBorrowById(repay.getBorrowId());
		BorrowType borrowType = borrowTypeService.getBorrowTypeById(borrow.getBidKind());
		//获取标种对应的service实现类
		BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class, borrowType.getDealService());
		ServiceResult rest = null;
		try {
			repay.setRepayedUserId(user.getId());
			rest = borrowService.repay(repay);
		} catch (Exception e) {
			
			logger.error("repay borrow error:"+repayId,e);
			String msg = e.getLocalizedMessage();
			if(StringUtils.isEmpty(msg)){
				msg = "还款失败";
			}
			rest = new ServiceResult("324",msg);
		}
		//添加日志		
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("还款");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
		operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
		operatorLog.setOperatorParams("signStr="+signStr+",repayId="+repayId);
		operatorLog.setOperatorReturn(rest.getMsg());
		operatorLog.setOperatorStatus(Integer.parseInt(rest.getCode()));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addFrontLog(operatorLog);
				
		SpringUtils.renderJsonResult(response, rest.getCode(),rest.getMsg());
	}
	
}
