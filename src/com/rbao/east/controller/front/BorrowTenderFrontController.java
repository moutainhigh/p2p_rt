package com.rbao.east.controller.front;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTypeHandleService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.SpringUtils;

/**
 * 投标
 * */
@Controller
@RequestMapping("borrowTender/")
public class BorrowTenderFrontController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(BorrowTenderFrontController.class);
	
	@Autowired
	private BorrowTenderService borrowTenderService;
	@Autowired
	private BorrowService borrowQueryService;
	@Autowired
	private BorrowTypeService borrowTypeService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private SysConfigParamService sysConfigParamService;

	
	@RequestMapping("/tender.do")
	public void tender(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = this.loginFrontUser(request);
		if (user == null) {
			SpringUtils.renderJsonResult(response, "115", "获取用户信息失败，请重新登录");
			return;
		}
		JsonResult result = validateTenderRequest(request);// 验证请求是否合法，防止客户端修改数据
		if (!result.isSuccessed()) {
			SpringUtils.renderJson(response, result);
			return;
		}
		Map<String, String> params = this.getParameters(request);
		//限制投标人代收款
		boolean flag = this.minWaitRepossessLimit(response, user, params);
		if(flag==false){
			return;
		}
		// 填充tender对象
		BorrowTender tender = new BorrowTender();
		tender.setBorrowId(Integer.parseInt(params.get("borrowId")));
		tender.setUserId(user.getId());
		tender.setTenderAddip(getIpAddr(request));
		tender.setCalInterestType(BorrowTender.CAL_INTEREST_TYPE_BACK);
		if(params.containsKey("tenderAmount")){
			//判断是否用了抵扣金
			if( params.containsKey("deductionStatus") && "1".equals(params.get("deductionStatus")) ){
				tender.setDeductionMoney(new BigDecimal(params.get("deductionMoney")));
			}else{
				tender.setDeductionMoney(new BigDecimal(0));
			}
			tender.setTenderAmount(new BigDecimal(params.get("tenderAmount")));
		}			
		else{
			//判断是否用了抵扣金
			if(params.containsKey("deductionStatus") && "1".equals(params.get("deductionStatus"))){
				tender.setDeductionMoney(new BigDecimal(params.get("deductionMoney")));
			}else{
				tender.setDeductionMoney(new BigDecimal(0));
			}
			tender.setTenderCopies(Integer.valueOf(params.get("tenderCopies")));
		}

		BorrowType borrowType = borrowTypeService.getBorrowTypeById(Integer
				.parseInt(params.get("bidKind")));
		// 获取标种对应的service实现类
		BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class,
				borrowType.getDealService());
		ServiceResult sResult = null;
		try {
			sResult = borrowService.saveTender(tender,params); // 保存投标
		} catch (Exception e) {
			logger.error("save tender error:" + params, e);
			sResult = new ServiceResult("301", "保存投标失败");
		}
		// 添加日志
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("投标保存");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
		operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
		operatorLog.setOperatorParams(params.toString());
		operatorLog.setOperatorReturn(sResult.getMsg());
		operatorLog.setOperatorStatus(Integer.parseInt(sResult.getCode()));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addFrontLog(operatorLog);

		SpringUtils.renderJsonResult(response, sResult.getCode(),
				sResult.getMsg());
	}
	//限制待收
		public boolean minWaitRepossessLimit(HttpServletResponse response,User user,Map params){
			Borrow borrow = borrowQueryService.getBorrowById(Integer.parseInt((String) params.get("borrowId")));
			BigDecimal minWaitRepossess = borrow.getMinWaitRepossess();//查看此标的限制投标人代收款
			
			UserAccount useraccount = userAccountService.getByUserId(user.getId());
			BigDecimal totalWaitRepossessed = useraccount.getWaitRepossessedInterest().add(useraccount.getWaitRepossessedCapital());
			
			
			
			if(minWaitRepossess.longValue()>0){
				if(totalWaitRepossessed.subtract(minWaitRepossess).longValue()<0){
					SpringUtils.renderJsonResult(response, "222", "您的待收总额不足,不能投此标");
					return false;
				}
			}
			return true;
		}
	/**
	 * 投标时验证请求是否合法
	 * 
	 * @param request
	 * @return
	 */
	private JsonResult validateTenderRequest(HttpServletRequest request) {
		JsonResult err = new JsonResult("116", "传入数据有误，请刷新后重试");
		try {
			
			Borrow borrow = borrowQueryService.getBorrowById(Integer
					.parseInt(request.getParameter("borrowId")));
			if(!new DesEncrypt().encrypt(borrow.getId()+"")
					.equals(request.getParameter("SignBorrowId"))){
				return err;
			}
			if (!CompareUtils.equals(borrow.getAnnualInterestRate(),
					new BigDecimal(request.getParameter("annualInterestRate")))) {
				return err;
			}
			if (!CompareUtils.equals(borrow.getBorrowSum(), new BigDecimal(
					request.getParameter("borrowSum")))) {
				return err;
			}
			if (borrow.getBorrowTimeLimit() != Integer.parseInt(request
					.getParameter("borrowTimeLimit"))) {
				return err;
			}
			if (!validateCaptcha(request)) {
				return new JsonResult("115", "验证码输入错误");
			}
			//判断是否有投标密码
			if(borrow.getTenderPassword() != null  && !borrow.getTenderPassword().equals("")){
				String tenderPassword = request.getParameter("tenderPassword").toString();
				if(!borrow.getTenderPassword().equals(tenderPassword)){
					return new JsonResult("116", "投标密码输入错误");
				}
			}
			Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
			//判断是否用抵扣金
			if( null != request.getParameter("userdeductionStatus") && !"0".equals(request.getParameter("userdeductionStatus"))){
				String deductionMoneyPercent = sysConfigParamMap.get("deductionMoney_percent");
				if(null != request.getParameter("deductionStatus") && "1".equals(request.getParameter("deductionStatus"))){
					MathContext mc = new MathContext(6,RoundingMode.HALF_DOWN);
					BigDecimal tenderAmount = new BigDecimal(request.getParameter("tenderAmount"));
					BigDecimal decutionMoney =  new BigDecimal(request.getParameter("deductionMoney"));
					
					if(-1 == tenderAmount.divide(new BigDecimal(deductionMoneyPercent), mc).compareTo(decutionMoney)){
						return new JsonResult("117", "抵扣金最多只能为投资金额的"+deductionMoneyPercent+"%，请重新输入！");
					}
				}
			}
			
		} catch (NumberFormatException e) {
			return err;
		}
		return new JsonResult(JsonResult.SUCCESS);
	}
	
	
	@RequestMapping("/getTotalNoSuccess.do")
	public String getTotalNoSuccess(HttpServletRequest request, HttpServletResponse response,Model model,Integer borrowId) throws Exception{
		String msg="";
		
		try {
			if(null != borrowId){
				//查询当前发的标
		    	   Borrow borrow = this.borrowQueryService.getBorrowById(borrowId);
		    	   BigDecimal su=new BigDecimal(0);
		    	   if(null != borrow.getTenderSum()){
		    		   su=borrow.getTenderSum();
		    	   }
		    	   borrow.setBorrowSum(borrow.getBorrowSum());
		    	   borrow.setTenderSum(su);
		    	   BigDecimal v=borrow.getBorrowSum().subtract(borrow.getTenderSum());
		    	   User user=(User)request.getSession().getAttribute(Constants.FRONT_USER_SESSION);
		    	   UserAccount account = this.userAccountService.getByUserId(user.getId());
		    	   if(CompareUtils.greaterEquals(v, account.getAvailableMoney())){
		    		   //改成保留0位小数,向下
		    		   SpringUtils.renderJson(response, account.getAvailableMoney().setScale(0, BigDecimal.ROUND_DOWN).toString());
		    	   }else{
		    		   SpringUtils.renderJson(response, v.setScale(0, BigDecimal.ROUND_DOWN).toString());
		    	   }
		    	   return null;
			}else{
				msg="m";
			}
		} catch (Exception e) {
			System.out.println("判断还有多少没有借成功====="+e.toString());
			logger.info("判断还有多少没有借成功====="+e.toString());
			msg="m";
		}
		SpringUtils.renderJson(response, msg);
		return null;
	}

	
	
}
