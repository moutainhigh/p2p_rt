package com.rbao.east.controller.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.dao.AttachDao;
import com.rbao.east.entity.Attach;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowArrays;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserType;
import com.rbao.east.service.AttachService;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTypeHandleService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.service.UserTypeService;
import com.rbao.east.thread.AutoTenderThread;
import com.rbao.east.thread.CreateAgreeMentThread;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;

/**
 * 标相关操作
 * */
@Controller
@RequestMapping("borrow/")
public class BorrowController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(BorrowController.class);

	@Autowired
	private BorrowService borrowQueryService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private BorrowTypeService borrowTypeService;
	@Autowired
	private UserTypeService userTypeService; 
	@Autowired
	private BorrowRepaymentService repayService;
	@Autowired
	private BorrowTenderService borrowTenderService;
	@Autowired
	private BorrowRepossessedService borrowRepossessedService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	private AttachDao attachDao;
	
	//修改限额
		@RequestMapping(Constants.PRE_PATH_EDIT + "editLimit")
		public String editLimit(HttpServletRequest request, HttpServletResponse response,
				Model model) {
			Map<String, String> param=this.getParameters(request);
			String target="";
			User user=this.loginAdminUser(request);
			boolean bool=false;
			int borrowStatus=0;
			try {
				if("toJsp".equals(param.get("type").toString())){
					int id=Integer.parseInt(param.get("supportID").toString());
					Borrow borrow=borrowQueryService.getBorrowById(id);
					if(borrow.getBorrowStatus()==Borrow.STATUS_FIRSTAUDIT_YES){
						List<UserType> userTypeList=userTypeService.getUserTypeList();
						model.addAttribute("borrow", borrow);
						model.addAttribute("right_id", param.get("right_id"));
						model.addAttribute("userTypeList", userTypeList);
						target="borrow/editLimit";
					}else{
						target=null;
						throw new RuntimeException("编辑错误");
					}
				}else if("update".equals(param.get("type").toString())){
					Borrow borrow=new Borrow();
					borrowStatus=Integer.parseInt(param.get("borrowStatus").toString());
					if(borrowStatus==Borrow.STATUS_FIRSTAUDIT_YES){
						borrow.setId(Integer.parseInt(param.get("borrowId").toString()));
						
						BigDecimal minAmount = new BigDecimal(param.get("minAmount").toString());
						BigDecimal maxAmount = new BigDecimal(param.get("maxAmount").toString());
						if(maxAmount.equals(BigDecimal.ZERO)){
							borrow.setMinAmount(minAmount);
							borrow.setMaxAmount(maxAmount);
							
							bool=borrowQueryService.updateBorrow(borrow);
							SpringUtils.renderDwzResult(response, bool, bool?"修改限额成功":"修改限额失败，请稍后再试"
								,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());	
						}else{
							if(minAmount.subtract(maxAmount).longValue()>0){
								SpringUtils.renderDwzResult(response, bool, "最低投标金额大于最多投标金额,请修改!"
									,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());
							}else{
								borrow.setMinAmount(minAmount);
								borrow.setMaxAmount(maxAmount);
								
								bool=borrowQueryService.updateBorrow(borrow);
								SpringUtils.renderDwzResult(response, bool, bool?"修改限额成功":"修改限额失败，请稍后再试"
									,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());	
							}
						}
					}
					
					
					target=null;
				}
			} catch (Exception e) {
				SpringUtils.renderDwzResult(response, false,e.getLocalizedMessage()
					,"",param.get("right_id").toString()); 
				
			}
			if(borrowStatus==Borrow.STATUS_FIRSTAUDIT_YES){
				borrowQueryService.autoTenderRules();
			}
			return target;
		}	
		
	//借款列表
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowByStatusList")
	public String getBorrowByStatusList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		if(!paramsMap.containsKey("borrowStatus")){
			if(paramsMap.containsKey("borrowStatuss")){
				paramsMap.put("borrowStatus", paramsMap.get("borrowStatuss"));
			}
		}else{
			if(Integer.parseInt(paramsMap.get("borrowStatus").toString())==0){
				paramsMap.put("borrowStatus","");
			}
		}
		
		if (paramsMap.containsKey("borrowStatus")) {
			int borrowStatus = Integer.parseInt(paramsMap.get("borrowStatus").toString());
			if (Borrow.STATUS_FIRSTAUDIT_NO == borrowStatus
					|| Borrow.STATUS_CANCEL_BY_SELF == borrowStatus
					|| Borrow.STATUS_CANCEL_BY_ADMIN == borrowStatus
					|| Borrow.STATUS_REVIEW_NO == borrowStatus
					|| Borrow.STATUS_FLOW_STANDARD == borrowStatus) {
				model.addAttribute("unpass", "1");
			}
		}
		
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		String dingCode= Borrow.BORROW_TYPE_DING;
		paramsMap.put("dingCode", dingCode);
		PageModel	 pageModel = borrowQueryService.getBorrowsByStatus(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		if(StringUtils.isBlank(paramsMap.get("borrowStatus"))){
			model.addAttribute("borrowStatusAll","0");//用于搜索状态全部
		}else if(StringUtils.isNotBlank(paramsMap.get("borrowStatusAll"))){
			if(paramsMap.get("borrowStatusAll").equals("0"))
			  {
				model.addAttribute("borrowStatusAll","0");
			  
			  }
		}
		
		model.addAttribute("rel",request.getParameter("rel"));
		/*model.addAttribute("borrowTypes", borrowTypeService.getAllBorrowTypes());*/
		model.addAttribute("borrowTypes", borrowTypeService.getBorrowTypesNotDing(dingCode));
		request.getSession(true).setAttribute("queryParams", paramsMap); 
		return "borrow/borrowbystatuslist";
	}
	
	//根据Id查看标详细信息
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowDetailById")
	public String getBorrowDetailById(HttpServletRequest request, HttpServletResponse response,Model model) {
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		model.addAttribute("borrow", borrowQueryService.getBorrowById(Integer.parseInt(paramsMap.get("supportID").toString())));
		model.addAttribute("right_id",paramsMap.get("right_id"));
		return "borrow/borrowdetail";
	}
	//定活通
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowDingList")
	public String getBorrowDingList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		String dingCode= Borrow.BORROW_TYPE_DING;
		paramsMap.put("dingCode", dingCode);
		PageModel	 pageModel = borrowQueryService.getBorrowDing(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		return "borrow/borrowDingList";
	}
	//根据Id查看定活通标详细信息
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowDingDetail")
	public String getBorrowDingDetail(HttpServletRequest request, HttpServletResponse response,Model model) {
		Map<String,String> paramsMap = getParameters(request);
		model.addAttribute("right_id",paramsMap.get("right_id"));
		String ids []=StringUtils.split(paramsMap.get("ids"),",");
		
		List<Borrow> list = new ArrayList<Borrow>();
		for(int i=0;i<ids.length;i++){
			Borrow borrow=new Borrow();
			borrow= borrowQueryService.getBorrowById(Integer.parseInt(ids[i]));
			list.add(borrow);
		}
		model.addAttribute("borrow",list);
		return "borrow/borrowDingDetail";
	}
	//还款管理
	@RequestMapping(Constants.PRE_PATH_VIEW + "getPaymentBorrowsList")
	public String getPaymentBorrowsList(HttpServletRequest request, HttpServletResponse response,Model model){
		Map<String,String> paramsMap = getParameters(request);
		if(!paramsMap.containsKey("repaymentStatus")){
			/*paramsMap.putAll((Map<String, String>) request.getSession(true).getAttribute("REPAY_LIST_PARAMS"));
			if(paramsMap.get("repaymentStatus").equals(BorrowRepayment.REPAYMENT_STATUS_SUCCESS)){
				
			}*/
		}else{
			request.getSession(true).setAttribute("REPAY_LIST_PARAMS",paramsMap);
			model.addAttribute("repaymentStatus",paramsMap.get("repaymentStatus"));
		}
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = borrowQueryService.getPaymentBorrows(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		if(StringUtils.isBlank(paramsMap.get("repaymentStatus"))){
			model.addAttribute("repaymentStatusAll","0");//用于搜索状态全部
		}else if(StringUtils.isNotBlank(paramsMap.get("repaymentStatusAll"))){
			if(paramsMap.get("repaymentStatusAll").equals("0"))
			  {
				model.addAttribute("repaymentStatusAll","0");
			  
			  }
		}
		if(paramsMap.get("days")!=null){
			if(paramsMap.get("days").equals("30")){
				model.addAttribute("day","30");
			}else {
				model.addAttribute("day","90");
			}
		}
		request.getSession(true).setAttribute("queryParams", paramsMap);  
		model.addAttribute("rel",request.getParameter("rel"));
		model.addAttribute("borrowTypes", borrowTypeService.getAllBorrowTypes());
		/*	model.addAttribute("borStatus", paramsMap.get("borStatus"));*/
		return "borrow/paymentborrowslist";
	}
	

	
	//后台发标时进入发标页面
	@RequestMapping(Constants.PRE_PATH_VIEW + "adminIssueBorrow")
	public String adminIssueBorrow(HttpServletRequest request, HttpServletResponse response,Model model){
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		BorrowType borrowType = borrowTypeService.getBorrowTypeByCode(paramsMap.get("code").toUpperCase());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("borrowType",borrowType);
		return "borrow/issueBorrow";
	}
	/**
	 * 获得还款信息
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getRepay")
	public String getRepay(HttpServletRequest request, HttpServletResponse response,Model model){
		Map<String, String> param=this.getParameters(request);
		model.addAttribute("right_id",param.get("right_id"));
		String[] ids = request.getParameter("ids").split(",");
		Integer repayId = 0;
		Integer borrowId = 0;
		try {
			repayId = Integer.parseInt(ids[1]);
			borrowId = Integer.parseInt(ids[0]);
		} catch (NumberFormatException e) {
			
			logger.debug("repay request params error:"+request.getParameter("ids"),e);
			SpringUtils.renderDwzResult(response,false,"数据有误，请刷新页面重试");
			return null;
		}
		//获得待收人信息
		param.put("repayId", String.valueOf(repayId));
		PageModel  pageModel =borrowRepossessedService.selectBorrowRepossessedByRepaymentId(param);
		model.addAttribute("pm", pageModel);
		
	    model.addAttribute("ids",request.getParameter("ids"));
	    model.addAttribute("repaymentStatus",param.get("repaymentStatus"));
		return "borrow/paymentborrowsMsg";
		
	}
	/**
	 * 后台还款
	 * @param request
	 * @param response
	 * @param model
	 * @param borrow
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "repay")
	public void repay(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> param=this.getParameters(request);
		User user = this.loginAdminUser(request);
		if(user == null){
			SpringUtils.renderDwzResult(response,false,"获取用户信息失败，请重新登录");
			return;
		}
		String[] ids = request.getParameter("ids").split(",");//[0]=borrowId;[1]=repayid
		Integer repayId = 0;
		Integer borrowId = 0;
		try {
			repayId = Integer.parseInt(ids[1]);
			borrowId = Integer.parseInt(ids[0]);
		} catch (NumberFormatException e) {
			
			logger.debug("repay request params error:"+request.getParameter("ids"),e);
			SpringUtils.renderDwzResult(response,false,"数据有误，请刷新页面重试");
			return ;
		}
		
		BorrowRepayment repay = this.repayService.getBorrowRepaymentById(repayId); 
		if(repay == null || !(repay.getBorrowId().toString().equals(borrowId.toString()))){  //校验，防止页面修改数据
			SpringUtils.renderDwzResult(response,false,"数据有误，请刷新页面重试");
			return ;
		}
		Borrow borrow = this.borrowQueryService.getBorrowById(borrowId);
		BorrowType borrowType = borrowTypeService.getBorrowTypeById(borrow.getBidKind());
		//获取标种对应的service实现类
		BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class, borrowType.getDealService());
		ServiceResult rest = null;
		try {
			repay.setRepayedUserId(Constants.PLATFORM_VOUCH_USER_ID);
			rest = borrowService.repay(repay);
		} catch (Exception e) {
			
			logger.error("repay borrow error:"+repayId,e);
			String msg = e.getLocalizedMessage();
			if(StringUtils.isEmpty(msg)){
				msg = "还款失败";
			}
			rest = new ServiceResult("324",msg);
		}
		DwzResult dwzResult = new DwzResult(rest);
		//添加日志		
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("还款");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
		operatorLog.setOperatorParams("borrowId:"+borrowId+";repayId:"+repayId);
		operatorLog.setOperatorReturn(dwzResult.getMessage());
		operatorLog.setOperatorStatus(Integer.parseInt(rest.getCode()));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addAdminLog(operatorLog);
				
		dwzResult.setCallbackType(DwzResult.CALLBACK_CLOSECURRENT);
		dwzResult.setNavTabId(param.get("right_id"));
		SpringUtils.renderJson(response,dwzResult);
		
		/*SpringUtils.renderDwzResult(response, succ, succ?"还款成功":"还款失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());*/
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "cancel")
	public void cancel(HttpServletRequest request, HttpServletResponse response){
		User user = this.loginAdminUser(request);
		if(user == null){
			SpringUtils.renderDwzResult(response,false,"获取用户信息失败，请重新登录");
			return;
		}
		Integer id = Integer.parseInt(request.getParameter("supportID"));
		ServiceResult rest = new ServiceResult();
		try {
			
			rest  = this.borrowQueryService.cancelBorrow(id);
		} catch (Exception e) {
			
			logger.error("cancel borrow error:"+id,e);
			String msg = e.getLocalizedMessage();
	//		if(StringUtils.isEmpty(msg)){
				msg = "撤标失败";
	//		}
			rest = new ServiceResult("324",msg);
		}
		DwzResult dwzResult = new DwzResult(rest);
		//添加日志		
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("撤销标");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
		operatorLog.setOperatorParams("id="+id);
		operatorLog.setOperatorReturn(dwzResult.getMessage());
		operatorLog.setOperatorStatus(Integer.parseInt(dwzResult.getStatusCode()));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addAdminLog(operatorLog);
				
		SpringUtils.renderJson(response,dwzResult);
	}
	/**
	 * 后台保存发标
	 * @param request
	 * @param response
	 * @param model
	 * @param borrow
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveBorrow")
	public void saveBorrow(HttpServletRequest request, HttpServletResponse response,
			Borrow borrow,String borrowPicture [],
			String[] attachTitle, String[] attachPath,
			String[] attachRealTitle, String[] attachFileType,
			Integer[] attachSequence){
		
		User user = this.loginAdminUser(request);
		if(user == null){
			SpringUtils.renderDwzResult(response,false,"获取用户信息失败，请重新登录");
			return;
		}
		if(!validateCaptcha(request)){
			SpringUtils.renderDwzResult(response,false,"验证码输入错误");
			return;
		}
		BorrowType borrowType = borrowTypeService.getBorrowTypeByCode(borrow.getBorrowTypeCode());
		//获取标种对应的service实现类
		BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class, borrowType.getDealService());
		ServiceResult result = new ServiceResult();
		try {
			if(borrow.getUserId().intValue() <= 0){ //如果没有选择借款人，就是平台借款
				borrow.setUserId(Constants.PLATFORM_VOUCH_USER_ID);
			}
			borrow.setBidKind(borrowType.getId());
			borrow.setBorrowAddip(getIpAddr(request));
			if(borrowPicture !=null && borrowPicture.length>1){
				throw new RuntimeException("上传图片限制一张");
			}else if(borrowPicture==null){
					borrow.setBorrowPicture("");
			}
			result = borrowService.allowToPublishBorrow(borrow);
			if(result.isSuccessed()){
				result = borrowService.saveBorrow(borrow);	
				
				boolean bl =borrowQueryService.updateBorrowForAttach(borrow,attachTitle,attachPath,
						attachRealTitle,attachFileType,
						attachSequence);
				if(result.isSuccessed() && bl){
					messageCenterService.send(borrow.getUserId(),"标创建成功","标["+borrow.getBorrowTitle()+"]创建成功，等待初审",Notice.BORROW_CREATED);
				}else{
					result.setCode("121");
					result.setMsg("保存发标失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save borrow error:"+borrow,e);
			result.setCode("121");
			result.setMsg("保存发标失败");
		}
		borrow.setBorrowContent("");//清空content，以免保存到log，参数很多
		DwzResult dwzResult = new DwzResult(result);
		//添加日志
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("发布借款");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
		operatorLog.setOperatorParams(borrow.toString());
		operatorLog.setOperatorReturn(dwzResult.getMessage());
		operatorLog.setOperatorStatus(Integer.parseInt(dwzResult.getStatusCode()));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addAdminLog(operatorLog);
		
		SpringUtils.renderJson(response, dwzResult);
	}
	
	/**
	 * 重新发标
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "toUpdateBorrow")
	public String toUpdateBorrow(HttpServletRequest request,Model model) {
		Map<String, String> param=this.getParameters(request);
		String borrowId = param.get("supportID");
		Borrow borrow = borrowQueryService.getBorrowById(Integer.parseInt(borrowId));
		Attach attach = new Attach();
		attach.setAttachTablename(Attach.TABLE_NAME_BORROW);
		attach.setAttachRelateId(Integer.parseInt(param.get("supportID")));
		List<Attach> attachList=attachDao.select("selectByAttach", attach);
		model.addAttribute("attachList", attachList);
		model.addAttribute("borrow", borrow);
		model.addAttribute("right_id", param.get("right_id"));
		return "borrow/editBorrow";
	}
	
	/**
	 * 后台再次发标
	 * @param request
	 * @param response
	 * @param model
	 * @param borrow
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateBorrow")
	public void updateBorrow(HttpServletRequest request, HttpServletResponse response,Model model,
			Borrow borrow,String borrowPicture [],
			String[] attachTitle, String[] attachPath,
			String[] attachRealTitle, String[] attachFileType,
			Integer[] attachSequence){
		boolean bool = false;
		Map<String,String> paramsMap = getParameters(request);
		model.addAttribute("right_id",paramsMap.get("right_id"));
		User user = this.loginAdminUser(request);
		if(user == null){
			SpringUtils.renderDwzResult(response,false,"获取用户信息失败，请重新登录");
			return;
		}
		if(!validateCaptcha(request)){
			SpringUtils.renderDwzResult(response,false,"验证码输入错误");
			return;
		}
		BorrowType borrowType = borrowTypeService.getBorrowTypeByCode(borrow.getBorrowTypeCode());
		//获取标种对应的service实现类
		BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class, borrowType.getDealService());
		ServiceResult result = new ServiceResult();
		try {
			if(borrow.getUserId().intValue() <= 0){ //如果没有选择借款人，就是平台借款
				borrow.setUserId(Constants.PLATFORM_VOUCH_USER_ID);
			}
			borrow.setBidKind(borrowType.getId());
			borrow.setBorrowAddip(getIpAddr(request));
			if(borrowPicture !=null && borrowPicture.length>1){
				throw new RuntimeException("上传图片限制一张");
			}else if(borrowPicture==null){
					borrow.setBorrowPicture("");
			}
			result = borrowService.allowToPublishBorrow(borrow);
			if(result.isSuccessed()){
				borrow.setBorrowStatus(Borrow.STATUS_NEW);
				result = borrowService.updateBorrow(borrow);	
				
				boolean bl = borrowQueryService.updateBorrowForAttach(borrow,attachTitle,attachPath,
						attachRealTitle,attachFileType,
						attachSequence);
				if(result.isSuccessed() && bl){
					bool = true;
					messageCenterService.send(borrow.getUserId(),"重新发标成功","标["+borrow.getBorrowTitle()+"]重新发标成功，等待初审",Notice.BORROW_CREATED);
				}else{
					result.setCode("121");
					result.setMsg("重新发标失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save borrow error:"+borrow,e);
			result.setCode("121");
			result.setMsg("重新发标失败");
		}
		borrow.setBorrowContent("");//清空content，以免保存到log，参数很多
		DwzResult dwzResult = new DwzResult(result);
		//添加日志
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("重新发布借款");
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
		operatorLog.setOperatorParams(borrow.toString());
		operatorLog.setOperatorReturn(dwzResult.getMessage());
		operatorLog.setOperatorStatus(Integer.parseInt(dwzResult.getStatusCode()));
		operatorLog.setLinkUrl(getURI(request));
		operatorLogService.addAdminLog(operatorLog);
		
		SpringUtils.renderDwzResult(response, bool, bool?"重新发标成功":"重新发标失败，请稍后再试"
			,DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id"));	
	}
	
	/**
	 * 初审
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "firstCheckBorrow")
	public String firstCheckBorrow(HttpServletRequest request, HttpServletResponse response,
			Model model,String[] attachTitle, String[] attachPath,
			String[] attachRealTitle, String[] attachFileType,
			Integer[] attachSequence) {
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, String> param=this.getParameters(request);
		String target="";
		User user=this.loginAdminUser(request);
		boolean bool=false;
		int borrowStatus=0;
		try {
			if("toJsp".equals(param.get("type").toString())){
				int id=Integer.parseInt(param.get("supportID").toString());
				Borrow borrow=borrowQueryService.getBorrowById(id);
				if(borrow.getBorrowStatus()==Borrow.STATUS_NEW){
					List<UserType> userTypeList=userTypeService.getUserTypeList();
					model.addAttribute("borrow", borrow);
					model.addAttribute("right_id", param.get("right_id"));
					model.addAttribute("userTypeList", userTypeList);
					target="borrow/firstCheckBorrow";
				}else{
					target=null;
					throw new RuntimeException("该标不是新标，不能被初审！！！");
				}
			}else if("update".equals(param.get("type").toString())){
				//Borrow borrow=new Borrow();
				Borrow borrow=borrowQueryService.getBorrowById(Integer.parseInt(param.get("borrowId").toString()));
				MessageCenter center = new MessageCenter();
				int OperatorCategory=0;
				String OperatorTitle="";
				borrowStatus=Integer.parseInt(param.get("borrowStatus").toString());
				borrow.setId(Integer.parseInt(param.get("borrowId").toString()));
				borrow.setMinAmount(new BigDecimal(param.get("minAmount").toString()));
				borrow.setMaxAmount(new BigDecimal(param.get("maxAmount").toString()));
				if(param.containsKey("verifyTrialRemark")){
					borrow.setVerifyTrialRemark(param.get("verifyTrialRemark").toString());
				}
				borrow.setVerifyTrialUser(user.getId());
				borrow.setVerifyTrialTime(new Date());
				if(borrowStatus==Borrow.STATUS_FIRSTAUDIT_NO){
					//初审未通过
					borrow.setBorrowStatus(Borrow.STATUS_FIRSTAUDIT_NO);
					OperatorCategory=Borrow.STATUS_FIRSTAUDIT_NO;
					OperatorTitle="初审未通过";
					center.setMessageContent("标【"+borrow.getBorrowTitle()+"】初审未通过，");
					center.setMessageSendIp(this.getIpAddr(request));
					center.setReceiveUserId(borrow.getUserId());
					center.setMessageTitle("初审未通过");
					center.setSendUserId(user.getId());
					messageCenterService.send(center, Notice.BORROW_NO);
				}else if(borrowStatus==Borrow.STATUS_FIRSTAUDIT_YES){
					//初审通过
					OperatorCategory=Borrow.STATUS_FIRSTAUDIT_YES;
					OperatorTitle="初审通过";
					borrow.setBorrowStatus(Borrow.STATUS_FIRSTAUDIT_YES);
					if(param.containsKey("publishDatetime")){
						SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date validTime=dateFormat.parse(param.get("publishDatetime").toString());
						Calendar rightNow = Calendar.getInstance();
						rightNow.setTime(validTime);
						rightNow.add(Calendar.DAY_OF_YEAR, Integer.parseInt(param.get("validTimes").toString()));
						borrow.setPublishDatetime(validTime);
						borrow.setAllowTenderTime(rightNow.getTime());
					}
					String repayStyle = param.get("repaymentStyle").toString();
					if(StringUtils.isNotBlank(repayStyle)){
						borrow.setRepaymentStyle(Integer.parseInt(repayStyle));
					}
					if(param.containsKey("bidRank")){
						borrow.setBidRank(Integer.parseInt(param.get("bidRank").toString()));
					}
					if(param.containsKey("minWaitRepossess")){
						borrow.setMinWaitRepossess(new BigDecimal(param.get("minWaitRepossess")));
					}
					
					Borrow borrows=borrowQueryService.getBorrowById(borrow.getId());
					
					center.setMessageContent("标【"+borrows.getBorrowTitle()+"】初审通过，");
					center.setMessageSendIp(this.getIpAddr(request));
					center.setReceiveUserId(borrows.getUserId());
					center.setMessageTitle("初审通过");
					center.setSendUserId(user.getId());
					messageCenterService.send(center, Notice.BORROW_YES);
					
				}
				bool=borrowQueryService.updateBorrowForAttach(borrow,attachTitle,attachPath,
						attachRealTitle,attachFileType,
						attachSequence);
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle(OperatorTitle);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
				operatorLog.setOperatorParams(param.toString());
				operatorLog.setOperatorReturn(bool?"初审操作成功":"初审操作失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(getURI(request));
				operatorLogService.addAdminLog(operatorLog);
				SpringUtils.renderDwzResult(response, bool, bool?"初审成功":"初审失败，请稍后再试"
					,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());	
				logger.info(bool?"初审成功":"初审失败，请稍后再试");
				target=null;
			}
		} catch (Exception e) {
			SpringUtils.renderDwzResult(response, false,e.getLocalizedMessage()
				,"",param.get("right_id").toString()); 
			e.printStackTrace();
			logger.error("初审失败，异常信息:" + e);
		}
		if(borrowStatus==Borrow.STATUS_FIRSTAUDIT_YES.intValue()){
			 try {
				   /**
				    * 开始自动投标
				    */
				   Thread autoTenderThread = new Thread(new AutoTenderThread(borrowQueryService));
				   autoTenderThread.start();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("dealAutoTender error",e);
			}
		}
		return target;
	}
	
	/**
	 * 复审
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "secondCheckBorrow")
	public String secondCheckBorrow(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, String> param=this.getParameters(request);
		String target=null;
		User user=this.loginAdminUser(request);
		boolean bool=false;
		try {
			if("toJsp".equals(param.get("type").toString())){
				int id=Integer.parseInt(param.get("supportID").toString());
				Borrow borrow=borrowQueryService.getBorrowById(id);
				if(borrow.getBorrowStatus()==Borrow.STATUS_FULL){
					List<UserType> userTypeList=userTypeService.getUserTypeList();
					model.addAttribute("borrow", borrow);
					model.addAttribute("right_id", param.get("right_id"));
					model.addAttribute("userTypeList", userTypeList);
					//搜索attach表中有无附加文件
					Attach attach = new Attach();
					attach.setAttachTablename(Attach.TABLE_NAME_BORROW);
					attach.setAttachRelateId(Integer.parseInt(param.get("supportID")));
					List<Attach> attachList=attachDao.select("selectByAttach", attach);
					model.addAttribute("attachList", attachList);
					target="borrow/secondCheckBorrow";
				}else{
					target=null;
					throw new RuntimeException("该标还未满，不能复审！！！");
				}
			}else if("update".equals(param.get("type").toString())){
				result.putAll(param);
				result.put("user", user);
				DwzResult dwzRest = null;
				Borrow borrow=borrowQueryService.getBorrowById(Integer.parseInt(param.get("borrowId").toString()));
				borrow.setVerifyReviewUser(user.getId());
				borrow.setVerifyReviewRemark(param.get("verifyReviewRemark"));
				borrow.setVerifyReviewTime(new Date());
				if(Borrow.STATUS_FULL==borrow.getBorrowStatus()){ 
					result.put("borrow", borrow);
					if(Integer.parseInt(param.get("borrowStatus").toString())==Borrow.STATUS_REPLYING.intValue()){
						ServiceResult rest = null;
						try {
							//复审通过 状态为 收益中
							BorrowType borrowType = borrowTypeService.getBorrowTypeById(borrow.getBidKind());
							//获取标种对应的service实现类
							BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class, borrowType.getDealService());
							rest = borrowService.reviewSuccess(borrow);
							if(rest.isSuccessed()){
								//生成pdf
								Borrow borrowPdf = borrowQueryService.getBorrowById(borrow.getId());
								Thread createThread=new Thread(new CreateAgreeMentThread(borrowService, borrowPdf));
								createThread.start();
							}
						} catch (Exception e) {
							
							logger.error("secondCheckBorrow yes error:"+param,e);
							rest = new ServiceResult("301","复审操作失败");
						}
						dwzRest = new DwzResult(rest);
						bool = rest.isSuccessed();
						
					}else if(Integer.parseInt(param.get("borrowStatus").toString())==Borrow.STATUS_REVIEW_NO){
						//复审未通过
						bool=borrowQueryService.updateSecondCheckBorrow(result);
						
						MessageCenter center=new MessageCenter();
						center.setMessageContent("标【"+borrow.getBorrowTitle()+"】复审通过，");
						center.setMessageSendIp(this.getIpAddr(request));
						center.setReceiveUserId(borrow.getUserId());
						center.setMessageTitle("满标复审不通过");
						center.setSendUserId(user.getId());
						messageCenterService.send(center, Notice.BORROW_REVIEW_YES);
						
					}
					if(dwzRest == null){
						OperatorLog operatorLog = new OperatorLog();
						operatorLog.setLogUserid(user.getUserAccount());
						operatorLog.setOperatorTitle("满标复审");
						operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
						operatorLog.setOperatorParams(result.toString());
						operatorLog.setOperatorReturn(bool?"复审未通过操作成功":"复审未通过操作失败");
						operatorLog.setOperatorStatus(bool?200:300);
						operatorLog.setLinkUrl(getURI(request));
						operatorLogService.addAdminLog(operatorLog);
						SpringUtils.renderDwzResult(response, bool, bool?"复审成功":"复审失败，请稍后再试！！！"
							,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());	
					}else{
						//添加日志
						OperatorLog operatorLog = new OperatorLog();
						operatorLog.setLogUserid(user.getUserAccount());
						operatorLog.setOperatorTitle("满标复审");
						operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
						operatorLog.setOperatorParams(param.toString());
						operatorLog.setOperatorReturn(dwzRest.getMessage());
						operatorLog.setOperatorStatus(Integer.parseInt(dwzRest.getStatusCode()));
						operatorLog.setLinkUrl(getURI(request));
						operatorLogService.addAdminLog(operatorLog);
						
						dwzRest.setCallbackType(DwzResult.CALLBACK_CLOSECURRENT);
						dwzRest.setNavTabId(param.get("right_id").toString());
						
						SpringUtils.renderJson(response, dwzRest);
					}	
					logger.info(bool?"复审成功":"复审失败");
				}else{
					throw new RuntimeException("该标已被审核过,或已通过审核！！！");
				}
				target=null;
			}
		} catch (Exception e) {
			SpringUtils.renderDwzResult(response, false,e.getLocalizedMessage()
					,"",param.get("right_id").toString()); 
				logger.error("复审失败，异常信息:" + e,e);
		}
		return target;
	}

	
	/**
	 * 导出
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
		try {
			String dingCode= Borrow.BORROW_TYPE_DING;
		params.put("dingCode", dingCode);
		List<Borrow> borrows = borrowQueryService.getAllBorrowsByStatus(params);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String[] titles={"序号","用户名","标种","借款标题","借款金额","已投金额","利率","借款期限","发布时间","状态"};
		
		String borrowStatus=params.get("borrowStatus");
		if("2".equals(borrowStatus)){
			titles=new String[]{"序号","用户名","标种","借款标题","借款金额","已投金额","利率",   "投标进度"   ,"借款期限","发布时间","状态"};
		}else if("10".equals(borrowStatus) || "11".equals(borrowStatus)){
			titles=new String[]{"序号","用户名","标种","借款标题","借款金额","已投金额","利率","借款期限","发布时间",   "初审时间"  ,"状态"};
		}
		
		List<String[]> contents=new ArrayList<String[]>();
		for (Borrow borrow : borrows) {
			int index=0;
			String [] strList=new String[titles.length];

			strList[index++]=StringUtil.toString(borrow.getId());//序号
			strList[index++]=StringUtil.toString(borrow.getUserAccount());//用户名
			strList[index++]=StringUtil.toString(borrow.getBorrowTypeName());//标种
			strList[index++]=StringUtil.toString(borrow.getBorrowTitle());//借款标题
			strList[index++]=StringUtil.toString(borrow.getBorrowSum());//借款金额
			strList[index++]=StringUtil.toString(borrow.getTenderSum());//已投金额
			strList[index++]=StringUtil.toString(borrow.getAnnualInterestRate())+"%";//利率
			
			if("2".equals(borrowStatus)){//投标进度
				strList[index++]=StringUtil.toString(borrow.getTenderProgressRate().doubleValue())+"%";
			}
			
			if(borrow.getIsDay().equals(Borrow.IS_DAY_Y)){//借款期限
				strList[index++]=StringUtil.toString(borrow.getBorrowTimeLimit())+"天";
			}else{
				strList[index++]=StringUtil.toString(borrow.getBorrowTimeLimit())+"个月";
			}			
			if(borrow.getPublishDatetime()!=null){//发布时间
				strList[index++]=StringUtil.toString(dateFormat.format(borrow.getPublishDatetime()));
			}else{
				strList[index++]="";
			}
			
			if("10".equals(borrowStatus) || "11".equals(borrowStatus)){//初审时间
				if(borrow.getVerifyTrialTime()!=null){
					strList[index++]=StringUtil.toString(dateFormat.format(borrow.getVerifyTrialTime()));
				}else{
					strList[index++]="";
				}
				
			}
			
			
			strList[index++]=StringUtil.toString(Borrow.ALL_STATUS.get(borrow.getBorrowStatus()));//状态
			
		
			contents.add(strList);
		}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流     
			response.setHeader("Content-disposition", "attachment; filename="+ 
							new String("borrowsList.xls".getBytes("UTF-8"),"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型   
			ExcelUtil.buildExcel(os,"标信息",titles,contents);
		} catch (IOException e) {
			
			logger.error("导出excel失败",e);
			
		}
	}
	
	
	/**
	 * 导出还款
	 * */
	@RequestMapping(Constants.PRE_PATH_VIEW + "toRepayExcel")
	public void toRepayExcel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
		try {
			List<Map<String, Object>> repayList = borrowQueryService.toRepayExcel(params);
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			List<String> tits = Lists.newArrayList();
			tits.add("序号");
			tits.add("借款人");
			tits.add("标种");
			tits.add("借款标题");
			tits.add("应还时间");
			tits.add("应还金额");
			tits.add("应还利息");
			tits.add("本金");
			tits.add("还款时间");
			tits.add("逾期天数");
			tits.add("逾期利息");
			
			/*String[] titles={"序号","借款人","标种","借款标题","应还时间","应还金额","应还利息"
							,"本金","还款时间","逾期天数","逾期利息","状态"};
				"借款金额","剩余期数","本期应还金额（元）"
							,"剩余应还本金（元）","逾期费用（元","最近催收时间"
			*/
			if(params.get("repaymentStatus")!=null&&params.get("repaymentStatus").equals("3")){
				tits.add("借款金额");
				tits.add("剩余期数");
				tits.add("本期应还金额（元）");
				tits.add("剩余应还本金（元）");
				tits.add("逾期费用（元）");
				tits.add("最近催收时间");
			}
			tits.add("状态");
			String[] titles = new String[tits.size()];
			tits.toArray(titles);
			List<String[]> contents=new ArrayList<String[]>();
			int i=1;
			for (Map<String, Object> repay:repayList) {
				String [] strList=new String[repay.size()];
				strList[0]=String.valueOf(i);
				int count=0;
				count++;
				if(repay.containsKey("userAccount")){
					strList[count]=repay.get("userAccount").toString();
				}else{
					strList[count]="";
				}
				count++;
				if(repay.containsKey("TypeName")){
					strList[count]=repay.get("TypeName").toString();
				}else{
					strList[count]="";
				}
				count++;
				if(repay.containsKey("borrowTitle")){
					strList[count]=repay.get("borrowTitle").toString();
				}else{
					strList[count]="";
				}
				count++;
				if(repay.containsKey("repaymentTiime")){
					strList[count]=dateFormat.format(repay.get("repaymentTiime")).toString();
				}else{
					strList[count]="";
				}
				count++;
				if(repay.containsKey("repaymentAmount")){
					strList[count]=repay.get("repaymentAmount").toString();
				}else{
					strList[count]="0.00";
				}
				count++;
				if(repay.containsKey("repaymentInterest")){
					strList[count]=repay.get("repaymentInterest").toString();
				}else{
					strList[count]="0.00";
				}
				count++;
				if(repay.containsKey("repaymentPrincipal")){
					strList[count]=repay.get("repaymentPrincipal").toString();
				}else{
					strList[count]="0.00";
				}
				count++;
				if(repay.containsKey("repaymentPaidtime")){
					if(repay.get("repaymentPaidtime")!=null){
						strList[count]=dateFormat.format(repay.get("repaymentPaidtime")).toString();
					}else{
						strList[count]="";
					}
					
				}else{
					strList[count]="";
				}
				count++;
				if(repay.containsKey("lateDays")){
					strList[count]=repay.get("lateDays").toString();
				}else{
					strList[count]="";
				}
				count++;
				if(repay.containsKey("lateInterest")){
					strList[count]=repay.get("lateInterest").toString();
				}else{
					strList[count]="0.00";
				}
				count++;
			if(params.get("repaymentStatus")!=null&&params.get("repaymentStatus").equals("3")){
				if(repay.containsKey("borrowSum")){
					strList[count]=repay.get("borrowSum").toString();
				}else{
					strList[count]="0.00";
				}
				count++;
				if(repay.containsKey("surplusPeriod")){
					strList[count]=repay.get("surplusPeriod").toString();
				}else{
					strList[count]="0";
				}
				count++;
				if(repay.containsKey("repaymentAmount")){
					strList[count]=repay.get("repaymentAmount").toString();
				}else{
					strList[count]="0.00";
				}
				count++;
				if(repay.containsKey("surplusSum")){
					strList[count]=repay.get("surplusSum").toString();
				}else{
					strList[count]="0.00";
				}
				count++;
				if(repay.containsKey("lateInterest")){
					strList[count]=repay.get("lateInterest").toString();
				}else{
					strList[count]="0.00";
				}
				count++;
				if(repay.containsKey("repaymentTiime")){
					strList[count]=dateFormat.format(repay.get("repaymentTiime")).toString();
				}else{
					strList[count]="";
				}
				count++;	
			}
				
				if(repay.containsKey("repaymentStatus")){
					strList[count]=BorrowRepayment.ALL_REPAYMENT_STATUS.get(Integer.parseInt(repay.get("repaymentStatus").toString()));
				}else{
					strList[count]="";
				}
				i++;
				contents.add(strList);
			}
				OutputStream os = response.getOutputStream();
				response.reset();// 清空输出流     
				response.setHeader("Content-disposition", "attachment; filename="+ 
								new String("repayList.xls".getBytes("UTF-8"),"ISO8859-1"));
				response.setContentType("application/msexcel");// 定义输出类型   
				ExcelUtil.buildExcel(os,"还款信息",titles,contents);
		} catch (IOException e) {
			logger.error("导出excel失败",e);
			
		}
	}
	
	
	/**
	 * 获取最近30天待还
	 * */
	@RequestMapping(Constants.PRE_PATH_VIEW + "toNearlyRepayExcel")
	public void toNearlyRepayExcel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
		try {
			List<Map<String, Object>> repayList = borrowQueryService.toRepayExcel(params);
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String[] titles={"序号","用户名","标种","借款标题","借款金额（元）","剩余期数","本期应还金额（元）"
							,"剩余应还本金（元）","距离下次还款（天）","下一还款日"};
			List<String[]> contents=new ArrayList<String[]>();
			int i=1;
			for (Map<String, Object> repay:repayList) {
				String [] strList=new String[18];
				strList[0]=String.valueOf(i);
				if(repay.containsKey("userAccount")){
					strList[1]=repay.get("userAccount").toString();
				}else{
					strList[1]="";
				}
				if(repay.containsKey("borrowTypeName")){
					strList[2]=repay.get("borrowTypeName").toString();
				}else{
					strList[2]="";
				}
				if(repay.containsKey("borrowTitle")){
					strList[3]=repay.get("borrowTitle").toString();
				}else{
					strList[3]="";
				}
				if(repay.containsKey("borrowSum")){
					strList[4]=repay.get("borrowSum").toString();
				}else{
					strList[4]="0.00";
				}
				if(repay.containsKey("surplusPeriod")){
					strList[5]=repay.get("surplusPeriod").toString();
				}else{
					strList[5]="0";
				}
				if(repay.containsKey("repaymentAmount")){
					strList[6]=repay.get("repaymentAmount").toString();
				}else{
					strList[6]="0.00";
				}
				if(repay.containsKey("surplusSum")){
					strList[7]=repay.get("surplusSum").toString();
				}else{
					strList[7]="0.00";
				}
				if(repay.containsKey("surplusTime")){
					strList[8]=repay.get("surplusTime").toString();
				}else{
					strList[8]="0";
				}				
				if(repay.containsKey("repaymentTiime")){
					strList[9]=dateFormat.format(repay.get("repaymentTiime")).toString();
				}else{
					strList[9]="";
				}
				i++;
				contents.add(strList);
			}
				OutputStream os = response.getOutputStream();
				response.reset();// 清空输出流     
				response.setHeader("Content-disposition", "attachment; filename="+ 
								new String("NearlyRepayList.xls".getBytes("UTF-8"),"ISO8859-1"));
				response.setContentType("application/msexcel");// 定义输出类型   
				ExcelUtil.buildExcel(os,"近30天待还款",titles,contents);
		} catch (IOException e) {
			logger.error("导出excel失败",e);
			
		}
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toBeyondRepayExcel")
	public void toBeyondRepayExcel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
		try {
			List<Map<String, Object>> repayList = borrowQueryService.toRepayExcel(params);
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String[] titles={"序号","借款人","标种","借款标题","应还时间","应还金额","应还利息","本金","还款时间","逾期天数","逾期利息","借款金额","剩余期数","本期应还金额（元）"
							,"剩余应还本金（元）","逾期费用（元","最近催收时间","状态"};
			List<String[]> contents=new ArrayList<String[]>();
			int i=1;
			for (Map<String, Object> repay:repayList) {
				String [] strList=new String[18];
				strList[0]=String.valueOf(i);
				if(repay.containsKey("userAccount")){
					strList[1]=StringUtil.toString(repay.get("userAccount"));
				}else{
					strList[1]="";
				}
				if(repay.containsKey("borrowTypeName")){
					strList[2]=StringUtil.toString(repay.get("borrowTypeName"));
				}else{
					strList[2]="";
				}
				if(repay.containsKey("borrowTitle")){
					strList[3]=StringUtil.toString(repay.get("borrowTitle"));
				}else{
					strList[3]="";
				}
				if(repay.containsKey("repaymentTiime")){
					if(repay.get("repaymentTiime")!=null){
						strList[4]=StringUtil.toString(dateFormat.format(repay.get("repaymentTiime")));
					}else{
						strList[4]="";
					}
				}else{
					strList[4]="";
				}
				if(repay.containsKey("repaymentAmount")){
					strList[5]=StringUtil.toString(repay.get("repaymentAmount"));
				}else{
					strList[5]="0.00";
				}
				if(repay.containsKey("repaymentInterest")){
					strList[6]=StringUtil.toString(repay.get("repaymentInterest"));
				}else{
					strList[6]="0.00";
				}
				if(repay.containsKey("repaymentPrincipal")){
					strList[7]=StringUtil.toString(repay.get("repaymentPrincipal"));
				}else{
					strList[7]="0.00";
				}
				if(repay.containsKey("repaymentPaidtime")){
					if(repay.get("repaymentPaidtime")!=null){
						strList[8]=StringUtil.toString(dateFormat.format(repay.get("repaymentPaidtime")));
					}else{
						strList[8]="";
					}
				}else{
					strList[8]="";
				}
				if(repay.containsKey("lateDays")){
					strList[9]=StringUtil.toString(repay.get("lateDays"));
				}else{
					strList[9]="0";
				}
				if(repay.containsKey("lateInterest")){
					strList[10]=StringUtil.toString(repay.get("lateInterest"));
				}else{
					strList[10]="0.00";
				}
				if(repay.containsKey("borrowSum")){
					strList[11]=StringUtil.toString(repay.get("borrowSum"));
				}else{
					strList[11]="0.00";
				}
				if(repay.containsKey("surplusPeriod")){
					strList[12]=StringUtil.toString(repay.get("surplusPeriod"));
				}else{
					strList[12]="0";
				}
				if(repay.containsKey("repaymentAmount")){
					strList[13]=StringUtil.toString(repay.get("repaymentAmount"));
				}else{
					strList[13]="0.00";
				}
				if(repay.containsKey("surplusSum")){
					strList[14]=StringUtil.toString(repay.get("surplusSum"));
				}else{
					strList[14]="0.00";
				}
				if(repay.containsKey("lateInterest")){
					strList[15]=StringUtil.toString(repay.get("lateInterest"));
				}else{
					strList[15]="0.00";
				}
				if(repay.containsKey("repaymentTiime")){
					if(repay.get("repaymentTiime")!=null){
						strList[16]=StringUtil.toString(dateFormat.format(repay.get("repaymentTiime")));
					}else{
						strList[16]="";
					}
				}else{
					strList[16]="";
				}
				if(repay.containsKey("repaymentStatus")){
					if(repay.get("repaymentStatus")!=null){
						strList[17]=BorrowRepayment.ALL_REPAYMENT_STATUS.get(Integer.parseInt(repay.get("repaymentStatus").toString()));
					}else{
						strList[17]="";
					}
				}else{
					strList[17]="";
				}
				i++;
				contents.add(strList);
			}
				OutputStream os = response.getOutputStream();
				response.reset();// 清空输出流     
				response.setHeader("Content-disposition", "attachment; filename="+ 
								new String("BeyondRepayList.xls".getBytes("UTF-8"),"ISO8859-1"));
				response.setContentType("application/msexcel");// 定义输出类型   
				ExcelUtil.buildExcel(os,"逾期待还款",titles,contents);
		} catch (IOException e) {
			logger.error("导出excel失败",e);
			
		}
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toBeyondsRepayExcel")
	public void toBeyondsRepayExcel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> params=(Map<String, String>) request.getSession().getAttribute("queryParams");
		try {
			List<Map<String, Object>> repayList = borrowQueryService.toRepayExcel(params);
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String[] titles={"序号","借款人","标种","借款标题","应还时间","应还金额","应还利息","本金","还款时间","逾期天数","逾期利息","借款金额","剩余期数","本期应还金额（元）"
					,"剩余应还本金（元）","逾期费用（元）","最近催收时间","状态"};
			List<String[]> contents=new ArrayList<String[]>();
			int i=1;     
			for (Map<String, Object> repay:repayList) {
				String [] strList=new String[18];
				strList[0]=String.valueOf(i);
				if(repay.containsKey("userAccount")){
					strList[1]=StringUtil.toString(repay.get("userAccount"));
				}else{
					strList[1]="";
				}
				if(repay.containsKey("borrowTypeName")){
					strList[2]=StringUtil.toString(repay.get("borrowTypeName"));
				}else{
					strList[2]="";
				}
				if(repay.containsKey("borrowTitle")){
					strList[3]=StringUtil.toString(repay.get("borrowTitle"));
				}else{
					strList[3]="";
				}
				if(repay.containsKey("repaymentTiime")){
					if(repay.get("repaymentTiime")!=null){
						strList[4]=dateFormat.format(repay.get("repaymentTiime")).toString();
					}else{
						strList[4]="";
					}	
				}else{
					strList[4]="";
				}
				if(repay.containsKey("repaymentAmount")){
					strList[5]=StringUtil.toString(repay.get("repaymentAmount"));
				}else{
					strList[5]="0.00";
				}
				if(repay.containsKey("repaymentInterest")){
					strList[6]=StringUtil.toString(repay.get("repaymentInterest"));
				}else{
					strList[6]="0.00";
				}
				if(repay.containsKey("repaymentPrincipal")){
					strList[7]=StringUtil.toString(repay.get("repaymentPrincipal"));
				}else{
					strList[7]="0.00";
				}
				if(repay.containsKey("repaymentPaidtime")){
					if(repay.get("repaymentPaidtime")!=null){
						strList[8]=dateFormat.format(repay.get("repaymentPaidtime")).toString();
					}else{
						strList[8]="";
					}	
				}else{
					strList[8]="";
				}
				if(repay.containsKey("lateDays")){
					strList[9]=StringUtil.toString(repay.get("lateDays"));
				}else{
					strList[9]="0";
				}
				if(repay.containsKey("lateInterest")){
					strList[10]=StringUtil.toString(repay.get("lateInterest"));
				}else{
					strList[10]="0.00";
				}
				if(repay.containsKey("borrowSum")){
					strList[11]=StringUtil.toString(repay.get("borrowSum"));
				}else{
					strList[11]="0.00";
				}
				if(repay.containsKey("surplusPeriod")){
					strList[12]=StringUtil.toString(repay.get("surplusPeriod"));
				}else{
					strList[12]="0";
				}
				if(repay.containsKey("repaymentAmount")){
					strList[13]=StringUtil.toString(repay.get("repaymentAmount"));
				}else{
					strList[13]="0.00";
				}
				if(repay.containsKey("surplusSum")){
					strList[14]=StringUtil.toString(repay.get("surplusSum"));
				}else{
					strList[14]="0.00";
				}
				if(repay.containsKey("lateInterest")){
					strList[15]=StringUtil.toString(repay.get("lateInterest"));
				}else{
					strList[15]="0.00";
				}
				if(repay.containsKey("repaymentTiime")){
					if(repay.get("repaymentTiime")!=null){
						strList[16]=dateFormat.format(repay.get("repaymentTiime")).toString();
					}else{
						strList[16]="";
					}		
				}else{
					strList[16]="";
				}
				if(repay.containsKey("repaymentStatus")){
					if(repay.get("repaymentStatus")!=null){
						strList[17]=BorrowRepayment.ALL_REPAYMENT_STATUS.get(Integer.parseInt(repay.get("repaymentStatus").toString()));
					}else{
						strList[17]="";
					}	
				}else{
					strList[17]="";
				}
				i++;
				contents.add(strList);
			}
				OutputStream os = response.getOutputStream();
				response.reset();// 清空输出流     
				response.setHeader("Content-disposition", "attachment; filename="+ 
								new String("BeyondRepayList.xls".getBytes("UTF-8"),"ISO8859-1"));
				response.setContentType("application/msexcel");// 定义输出类型   
				ExcelUtil.buildExcel(os,"逾期待还款",titles,contents);
		} catch (IOException e) {
			logger.error("导出excel失败",e);
			
		}
	}
	
	
	/**
	 * 满标修改
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateBorrowStatusForStatusFull")
	public void updateBorrowStatusForStatusFull(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		User user=this.loginAdminUser(request);
		boolean bool=false;
		try {
				int id=Integer.parseInt(param.get("supportID").toString());
				Borrow borrow=borrowQueryService.getBorrowById(id);
				if(borrow.getBorrowStatus()==Borrow.STATUS_FIRSTAUDIT_YES&&CompareUtils.greaterThanZero(borrow.getTenderSum())){
					borrow.setBorrowStatus(Borrow.STATUS_FULL);
					borrow.setBorrowSum(borrow.getTenderSum());
					bool=borrowQueryService.updateBorrow(borrow);
				}else{
					throw new RuntimeException("该标不能直接满标");
				}
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("满标修改");
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
				operatorLog.setOperatorParams(param.toString());
				operatorLog.setOperatorReturn(bool?"满标修改成功":"满标修改失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(getURI(request));
				operatorLogService.addAdminLog(operatorLog);
				SpringUtils.renderDwzResult(response, bool, bool?"满标修改成功":"满标修改失败","",param.get("right_id").toString(),"borrow/v_getBorrowByStatusList?borrowStatus="+param.get("borrowStatus").toString()+"&right_id="+param.get("right_id").toString()); 
				logger.info("满标修改成功");
		} catch (Exception e) {
			SpringUtils.renderDwzResult(response, false,e.getLocalizedMessage(),"",param.get("right_id").toString());
			logger.error("满标修改成功失败，异常信息:" + e);
		}
	}
	
	
	/**
	 * 投标记录
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getBorrowTenderPage")
	public String getBorrowTenderPage(HttpServletRequest request, HttpServletResponse response,Model model){
			Map<String,String> paramsMap = getParameters(request);
			paramsMap.put("borrowId", paramsMap.get("supportID"));
			PageModel page=borrowTenderService.shwoBorrowTenderInfoByPage(paramsMap);
			model.addAttribute("right_id", paramsMap.get("right_id"));
			model.addAttribute("pm", page);
			model.addAttribute("supportID", paramsMap.get("supportID"));
			return "borrow/showBorrowTenderPage";
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW +"borrowTenderPageToExcel")
	public void borrowTenderPageToExcel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> params=this.getParameters(request);
		try {
		params.put("borrowId", params.get("supportID"));
		List<Map<String, Object>> tenderList =borrowTenderService.toTenderRecordExcel(params);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String[] titles={"序号","投资人","投资金额","投资时间","投资标题","状态"};
		List<String[]> contents=new ArrayList<String[]>();
		int i=1;
		for (Map<String, Object> tender:tenderList) {
			String [] strList=new String[12];
			strList[0]=String.valueOf(i);
			
			strList[1]="";
			if(tender.containsKey("investorAccount")){
				strList[1]=tender.get("investorAccount").toString();
			}
			strList[2]="0.00";
			if(tender.containsKey("effective_amount")){
				strList[2]=tender.get("effective_amount").toString();
			}
			strList[3]="";
			if(tender.containsKey("tender_addtime")){
				strList[3]=dateFormat.format(tender.get("tender_addtime")).toString();
			}
			strList[4]="";
			if(tender.containsKey("borrowTitle")){
				strList[4]=tender.get("borrowTitle").toString();
			}
			strList[5]="";
			if(tender.containsKey("tender_status")){
				strList[5]=BorrowTender.ALL_STATUS.get(Integer.parseInt(tender.get("tender_status").toString()));
			}
			i++;
			contents.add(strList);
		}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流     
			response.setHeader("Content-disposition", "attachment; filename="+ 
							new String("tenderRecord.xls".getBytes("UTF-8"),"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型   
			ExcelUtil.buildExcel(os,"投标记录",titles,contents);
		} catch (IOException e) {
			logger.error("导出excel失败",e);
			
		}
	}
	
	/**
	 * 跳转定活通发标页面   
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "fixedCurrentLinkAccount")
	public String fixedCurrentLinkAccount(HttpServletRequest request, HttpServletResponse response,Model model){
			Map<String,String> paramsMap = getParameters(request);
			BorrowType borrowType = borrowTypeService.getBorrowTypeByCode(paramsMap.get("code").toUpperCase());
			model.addAttribute("right_id", paramsMap.get("right_id"));
			model.addAttribute("borrowType", borrowType);
			return "borrow/fixedCurrentLinkAccount";
	}
	
	/**
	 * 定活通发标   
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "savefixedCurrentLinkAccount")
	public void savefixedCurrentLinkAccount(HttpServletRequest request, HttpServletResponse response,Model model,BorrowArrays borrowArrays
			){
			Map<String,String> param = getParameters(request);
			Map<String, Object> result=new HashMap<String,Object>();
			User user = this.loginAdminUser(request);
			boolean bool=false;
			try {
				if(user == null){
					throw new RuntimeException("获取用户信息失败，请重新登录");
				}
				if(!validateCaptcha(request)){
					throw new RuntimeException("验证码输入错误");
				}
				BorrowType borrowType = borrowTypeService.getBorrowTypeByCode(param.get("borrowTypeCode"));
				result.put("validTime", param.get("validTime"));
				result.put("relatedPublishTime", param.get("relatedPublishTime"));
				result.put("borrowArrays", borrowArrays);
				result.put("right_id", param.get("right_id"));
				result.put("bidKind", borrowType.getId());
				result.put("isDay", param.get("isDay"));
				result.put("user", user);
				bool=borrowQueryService.savefixedCurrentLinkAccount(result);
				SpringUtils.renderDwzResult(response, bool, bool?"定活通发标成功":"定活通发标失败","",param.get("right_id").toString());
			} catch (Exception e) {
				SpringUtils.renderDwzResult(response, false,e.getLocalizedMessage(),"",param.get("right_id").toString());
				logger.error("定活通发标失败，异常信息:" + e,e);
			}		
	}
	
	/**
	 * 借款协议
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "borrowDeal")
	public String borrowDeal(HttpServletRequest request, HttpServletResponse response,Model model){
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		PageModel pageModel=borrowQueryService.getBorrowDeal(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		request.getSession(true).setAttribute("queryParams", paramsMap);
		return "borrow/borrowDeallist";
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcelBorrowDeal")
	public void toExcelBorrowDeal(HttpServletRequest request, HttpServletResponse response,Model model){
		try {
			Map<String, String> paramsMap = (Map<String, String>) request.getSession().getAttribute("queryParams");
			paramsMap.put("numPerPage", "500000");

			PageModel pageModel=borrowQueryService.getBorrowDeal(paramsMap);
			List list = pageModel.getList();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String[] titles={"序号","借款标题","借款账号","姓名","身份证","投标人","签订日期"};
			
			List<String[]> contents=new ArrayList<String[]>();
			for (int i = 0; i < list.size(); i++) {
				Map map=(Map)list.get(i);
				String[] str=new String[titles.length];
				str[0]=StringUtil.toString((i+1));
				str[1]=StringUtil.toString(map.get("borrowTitle"));
				str[2]=StringUtil.toString(map.get("userAccount"));
				str[3]=StringUtil.toString(map.get("userRealName"));
				str[4]=StringUtil.toString(map.get("cardNumber"));
				str[5]=StringUtil.toString(map.get("tenderUserAccount"));
				
				if(map.get("agreementTime")!=null){
					str[6]=StringUtil.toString(dateFormat.format(map.get("agreementTime")));
				}else{
					str[6]=StringUtil.toString("");
				}
				contents.add(str);
			}
			
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(("user-data" + ".xls").getBytes("UTF-8"),
							"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "借款协议", titles, contents);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出excel失败", e);
		}
		
	}
	
}
