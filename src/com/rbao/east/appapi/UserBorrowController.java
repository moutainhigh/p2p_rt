package com.rbao.east.appapi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.appapi.common.AppUtils;
import com.rbao.east.appapi.common.ResponseDto;
import com.rbao.east.appapi.common.Status;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Attach;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AttachService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTypeHandleService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.DesUtil;
import com.rbao.east.utils.JsonUtils;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.SpringUtils;

/**
 * 
 * @ClassName: UserBorrowController
 * @Description: app 操作标的信息
 * @date 2016年1月11日
 */
@Controller
@RequestMapping("/UserBorrow")
public class UserBorrowController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(UserBorrowController.class);
	@Autowired
	private BorrowService borrowQueryService;
	@Autowired
	private AttachService attachService;
	@Autowired
	private BorrowTenderService borrowTenderService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private BorrowTenderService tenderService;
	@Autowired
	private SysConfigParamService sysConfigParamService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private BorrowTypeService borrowTypeService;
	/**
	 * 
	* @Title: appBorrowDate
	* @Description: 投资详情页
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appBorrowDate.do")
	public void appBorrowDate(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		Map<String, Object> param = this.getParametersO(request);
		try {
			if (!param.containsKey("borrowEId")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("标id不能为空");
				return;
			}
			String borrowEId = param.get("borrowEId").toString();
			dto.addKeyValue("borrowEId", borrowEId.toString());
			// 解密 加密后的borrowEId
			DesEncrypt enc = new DesEncrypt();
			Integer borrowId = 0;
			try {
				borrowId = Integer.parseInt(enc.decrypt(borrowEId));
			} catch (NumberFormatException e1) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("解析路径错误");
				return;
			}
			Borrow borrow = borrowQueryService.getBorrowById(borrowId);
			dto.addKeyValue("borrowTitle", borrow.getBorrowTitle());
			String timeLimit = borrow.getBorrowTimeLimit().toString();
			String timeType = borrow.getIsDay().toString();
			if (timeType.equals(Borrow.IS_DAY_Y.toString())) {
				dto.addKeyValue("borrowLimit", timeLimit + "天");
			} else {
				dto.addKeyValue("borrowLimit", timeLimit + "个月");
			}
			dto.addKeyValue("minAmount", borrow.getMinAmount().toString());
			dto.addKeyValue("borrowSum", borrow.getBorrowSum().toString());
			MathContext mc = new MathContext(5);
			float percent = borrow.getTenderSum().divide(borrow.getBorrowSum(),mc)
					.setScale(5, RoundingMode.HALF_UP)
					.multiply(new BigDecimal(100))
					.setScale(2, RoundingMode.HALF_UP).floatValue();
			dto.addKeyValue("percent", AppUtils.setPercentStard(percent + ""));// 百分比
			dto.addKeyValue("maxAmount", borrow.getMaxAmount().toString());
			dto.addKeyValue("borrowRepaymentStatus",
					Borrow.ALL_REPAYMENT_STYLE.get(borrow.getRepaymentStyle()));// 标状态
			dto.addKeyValue("allowTenderTime",
					DateUtils.getDate(borrow.getAllowTenderTime()));
			dto.addKeyValue("buttonLink", "/mobile/UserBorrow/appBorrowDetail.do");// 按钮链接
			dto.addKeyValue("timeLimit", borrow.getBorrowTimeLimit().toString()); //期限数
			dto.addKeyValue("timeStatus", borrow.getIsDay().toString());  //类别1：天标
			//年利率
			dto.addKeyValue("annualInterestRate", borrow.getAnnualInterestRate().toString());
			//剩余可投金额
			dto.addKeyValue("leftMoney", borrow.getBorrowSum().subtract(borrow.getTenderSum()).toString());
			
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("投资详情页查询成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("投资详情页查询失败");
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}

	}

	/**
	 * 
	* @Title: appBorrowDetail
	* @Description: 产品详情页
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appBorrowDetail.do")
	public void appBorrowDetail(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		Map<String, Object> param = this.getParametersO(request);
		try {
			if (!param.containsKey("borrowEId")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("标id不能为空");
				return;
			}
			String borrowEId = param.get("borrowEId").toString();
			dto.addKeyValue("borrowEId", borrowEId.toString());
			// 解密 加密后的borrowEId
			DesEncrypt enc = new DesEncrypt();
			Integer borrowId = 0;
			try {
				borrowId = Integer.parseInt(enc.decrypt(borrowEId));
			} catch (NumberFormatException e1) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("解析路径错误");
				return;
			}
			Borrow borrow = borrowQueryService.getBorrowById(borrowId);
			dto.addKeyValue("borrowContent", borrow.getBorrowContent());
			dto.addKeyValue("borrowUse",
					Borrow.ALL_BORROW_USE.get(borrow.getBorrowUse()));

			// 资料图片
			Attach attach = new Attach();
			attach.setAttachTablename(Attach.TABLE_NAME_BORROW);
			attach.setAttachRelateId(borrow.getId());
			List<Attach> att = this.attachService.selectByAttach(attach);
			List<Map<String, String>> attrList = new LinkedList<Map<String, String>>();
			if (att.size() > 0) {
				for (int i = 0; i < att.size(); i++) {
					Map<String, String> oneMap = new HashMap<String, String>();
					oneMap.put("linkPic", att.get(i).getAttachPath());
					attrList.add(oneMap);
				}
			}
			dto.addKeyValue("attrList", attrList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("产品详情查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("产品详情查询失败");
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}

	/**
	 * 
	* @Title: appTenderRecord
	* @Description: 投资记录查询
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appTenderRecord.do")
	public void appTenderRecord(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		Map<String, Object> params = this.getParametersO(request);
		try {
			if (!params.containsKey("borrowEId")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("标id不能为空");
				return;
			}
			Map<String, String> param = new HashMap<String, String>();
			// 页数：默认为1
			if (params.containsKey(Constants.PAGED_CURPAGE)) {
				param.put(Constants.PAGED_CURPAGE, params.get(Constants.PAGED_CURPAGE).toString());
			} else {
				param.put(Constants.PAGED_CURPAGE, "1");
			}
			// 每页条数
			if (params.containsKey(Constants.PAGED_NUM_PERPAGE)) {
				param.put(Constants.PAGED_NUM_PERPAGE, params.get(Constants.PAGED_NUM_PERPAGE).toString());
			} else {
				param.put(Constants.PAGED_NUM_PERPAGE, "10");
			}
			
			// 解密 加密后的borrowEId
			String borrowEId = params.get("borrowEId").toString();
			DesEncrypt enc = new DesEncrypt();
			Integer borrowId = 0;
			try {
				borrowId = Integer.parseInt(enc.decrypt(borrowEId));
			} catch (NumberFormatException e1) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("解析路径错误");
				return;
			}

			param.put("borrowId", borrowId + "");
			PageModel page = borrowTenderService
					.shwoBorrowTenderInfoByPage(param);
			// 数据集
			List retList = page.getList();
			List<Map> dataList = new LinkedList<Map>();
			for (Iterator<Map> i = retList.iterator(); i.hasNext();) {
				Map<String, Object> curMap = i.next();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("investorAccount", curMap.get("investorAccount")
						.toString());// 标id
				data.put("tenderAmount", curMap.get("tender_amount").toString());
				// 投资时间
				Date tenderTime = (Date) curMap.get("tender_addtime");
				data.put("tenderTime", DateUtils.getDateSplitSecond(tenderTime));
				dataList.add(data);
			}
			dto.addKeyValue(Constants.TOTAL_PAGE, page.getTotalPage()+"");
			dto.addKeyValue(Constants.PAGED_CURPAGE,param.get(Constants.PAGED_CURPAGE).toString());
			dto.addKeyValue(Constants.PAGED_NUM_PERPAGE,param.get(Constants.PAGED_NUM_PERPAGE).toString());
			dto.addKeyValue("dataList", dataList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("投资记录查询成功");

		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("投资记录查询失败");
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}

	/**
	 * 
	* @Title: appUserCenter
	* @Description: 用户消息中心
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appUserCenter.do")
	public void appUserCenter(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		try {
			User user = this.loginAPPUser(request);
			Map<String, Object> param = this.getParametersO(request);
			Map<String, String> params = new HashMap<String, String>();
			params.put("receiveUserId", user.getId().toString());
			// 页数：默认为1
			if (param.containsKey(Constants.PAGED_CURPAGE)) {
				params.put(Constants.PAGED_CURPAGE, param.get(Constants.PAGED_CURPAGE).toString());
			} else {
				params.put(Constants.PAGED_CURPAGE, "1");
			}
			// 每页条数
			if (param.containsKey(Constants.PAGED_NUM_PERPAGE)) {
				params.put(Constants.PAGED_NUM_PERPAGE, param.get(Constants.PAGED_NUM_PERPAGE).toString());
			} else {
				params.put(Constants.PAGED_NUM_PERPAGE, "10");
			}
			
			PageModel page=  messageCenterService.getUserMessagePage(params);
			dto.addKeyValue(Constants.TOTAL_PAGE, page.getTotalPage()+"");
			dto.addKeyValue(Constants.PAGED_CURPAGE, params.get(Constants.PAGED_CURPAGE).toString());
			dto.addKeyValue(Constants.PAGED_NUM_PERPAGE, params.get(Constants.PAGED_NUM_PERPAGE).toString());
			// 数据集
			List retList = page.getList();
			List<Map> dataList = new LinkedList<Map>();
			for (Iterator<Map> i = retList.iterator(); i.hasNext();) {
				Map<String, Object> curMap = i.next();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("messageId", curMap.get("id").toString());// 信息id
				data.put("messageTitle", curMap.get("messageTitle").toString());
				data.put("messageContent", curMap.get("messageContent").toString());
				// 发送时间
				Date tenderTime = (Date) curMap.get("messageSendDateTime");
				data.put("messageSendDateTime", DateUtils.getDateSplitSecond(tenderTime));
				dataList.add(data);
			}
			dto.addKeyValue("dataList", dataList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("消息记录查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("消息记录查询失败");
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}

	/**
	 * 
	* @Title: appTenderSuccess
	* @Description: 投标成功后的界面
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appTenderSuccess.do")
	public void appTenderSuccess(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> param = this.getParametersO(request);
		ResponseDto dto = new ResponseDto();
		try {
			User user = this.loginAPPUser(request);
			if (!param.containsKey("borrowEId")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("标id不能为空");
				return;
			}
			if (!param.containsKey("tenderMoney")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("金额不能为空");
				return;
			}
			String borrowEId = param.get("borrowEId").toString();
			dto.addKeyValue("borrowEId", borrowEId.toString());
			// 解密 加密后的borrowEId
			DesEncrypt enc = new DesEncrypt();
			Integer borrowId = 0;
			try {
				borrowId = Integer.parseInt(enc.decrypt(borrowEId));
			} catch (NumberFormatException e1) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("解析路径错误");
				return;
			}
			Borrow borrow = borrowQueryService.getBorrowById(borrowId);
			dto.addKeyValue("borrowTitle", borrow.getBorrowTitle());
			String timeLimit = borrow.getBorrowTimeLimit().toString();
			String timeType = borrow.getIsDay().toString();
			if (timeType.equals(Borrow.IS_DAY_Y.toString())) {
				dto.addKeyValue("borrowLimit", timeLimit + "天");
			} else {
				dto.addKeyValue("borrowLimit", timeLimit + "个月");
			}
			dto.addKeyValue("annualInterestRate", borrow.getAnnualInterestRate().toString());//利率
			//预期收益
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", user.getId());
			params.put("borrowId", borrowId);
			BigDecimal tenderMoney = new BigDecimal(param.get("tenderMoney").toString());
			List<BorrowTender> tenderList = tenderService.queryTenderByBorrowidAndUserId(params);
			if(tenderList == null){
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("个人投标记录查询失败");
				return ;
			}
			for(int i = 0 ;i<tenderList.size();i++){
				if(tenderMoney.compareTo(tenderList.get(i).getEffectiveAmount()) ==0){
					dto.addKeyValue("interestAmount", tenderList.get(i).getInterestAmount().toString());
				}
			}
			dto.addKeyValue("buttonLink", "/mobile/UserBorrow/appBorrowDate.do");// 按钮链接
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("个人投标记录查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("个人投标记录查询失败");
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}

	/**
	 * 
	* @Title: appTenderBeforeData
	* @Description: 投标前的详情页
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appTenderBeforeData.do")
	public void appTenderBeforeData(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> param = this.getParametersO(request);
		ResponseDto dto = new ResponseDto();
		User user = this.loginAPPUser(request);
		try {
			if (!param.containsKey("borrowEId")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("标id不能为空");
				return;
			}
			String borrowEId = param.get("borrowEId").toString();
			dto.addKeyValue("borrowEId", borrowEId.toString());
			// 解密 加密后的borrowEId
			DesEncrypt enc = new DesEncrypt();
			Integer borrowId = 0;
			try {
				borrowId = Integer.parseInt(enc.decrypt(borrowEId));
			} catch (NumberFormatException e1) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("解析路径错误");
				return;
			}
			Borrow borrow = borrowQueryService.getBorrowById(borrowId);
			dto.addKeyValue("borrowTitle", borrow.getBorrowTitle());
			String timeLimit = borrow.getBorrowTimeLimit().toString();
			String timeType = borrow.getIsDay().toString();
			if (timeType.equals(Borrow.IS_DAY_Y.toString())) {
				dto.addKeyValue("borrowLimit", timeLimit + "天");
			} else {
				dto.addKeyValue("borrowLimit", timeLimit + "个月");
			}
			dto.addKeyValue("annualInterestRate", borrow.getAnnualInterestRate().toString());//利率
			//剩余可投金额
			dto.addKeyValue("leftMoney", borrow.getBorrowSum().subtract(borrow.getTenderSum()).toString());
			//按钮文字
			int borrowStatus = borrow.getBorrowStatus();
			if(borrowStatus == 2){
				dto.addKeyValue("borrowStatus", "确认投标");//标状态
			}else{
				dto.addKeyValue("borrowStatus", Borrow.ALL_STATUS.get(borrowStatus));//标状态
			}
			//是否有交易密码
			String tenderPassword = borrow.getTenderPassword();
			if(tenderPassword.equals("") || tenderPassword == null){
				dto.addKeyValue("tenderPassword", "0"); //没有交易密码
			}else{
				dto.addKeyValue("tenderPassword", "1"); //拥有交易密码
			}
			dto.addKeyValue("borrowSum", borrow.getBorrowSum().toString());
			dto.addKeyValue("timeLimit", borrow.getBorrowTimeLimit().toString()); //期限数
			dto.addKeyValue("timeStatus", borrow.getIsDay().toString());  //类别1：天标
			UserAccount userAccount = userAccountService.getByUserId(user.getId());
			dto.addKeyValue("availableMoney", userAccount.getAvailableMoney().toString());
			
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("投标查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("投标查询失败");
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	/**
	 * 
	* @Title: appTenderSubmit
	* @Description: 投标按钮
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appTenderSubmit.do")
	public void appTenderSubmit(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> param = this.getParametersO(request);
		ResponseDto dto = new ResponseDto();
		try {
			User user = this.loginAPPUser(request);
			Map<String, String> params = new HashMap<String, String>();
			if (!param.containsKey("borrowEId")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("标id不能为空");
				return;
			}
			if (!param.containsKey("annualInterestRate")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("利率不能为空");
				return;
			}
			if (!param.containsKey("borrowSum")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("借款金额不能为空");
				return;
			}
			if (!param.containsKey("payPassword")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("交易密码不能为空");
				return;
			}
			if (!param.containsKey("tenderAmount")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("投标金额不能为空");
				return;
			}
			String borrowEId = param.get("borrowEId").toString();
			
			// 解密 加密后的borrowEId
			DesEncrypt enc = new DesEncrypt();
			Integer borrowId = 0;
			try {
				borrowId = Integer.parseInt(enc.decrypt(borrowEId));
			} catch (NumberFormatException e1) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("解析路径错误");
				return;
			}
			JsonResult result = validateTenderRequest(request,borrowId);
			if (!result.isSuccessed()) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage(result.getMsg());
				return;
			}
			Borrow borrow = borrowQueryService.getBorrowById(borrowId);
			
			params.put("tenderAmount", param.get("tenderAmount").toString());
			params.put("borrowId", borrowId+"");
			params.put("deductionMoney", "0");
			//处理交易密码
			//原始交易密码
			String pwd = DesUtil.decryptDES(StringUtils.trimToEmpty(param.get("payPassword").toString()),Constants.DES_PUBLIC_KEY_IOS_ANDROID);
			//转换为jsp加密密码
			DesEncrypt des = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
			// DES加密
			String str2 = des.encrypt(pwd);
			params.put("payPassword", str2);
			//如果有交易密码，传过去明文就行
			if (param.containsKey("tenderPassword") && !"".equals(param.get("tenderPassword").toString())) {
				String tenderPassword = DesUtil.decryptDES(StringUtils.trimToEmpty(param.get(
						"tenderPassword").toString()),Constants.DES_PUBLIC_KEY_IOS_ANDROID);
				params.put("tenderPassword", tenderPassword);
			}
			
			//限制投标人代收款
			boolean flag = this.minWaitRepossessLimit(response, user, params);
			if(flag==false){
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("您的待收总额不足,不能投此标");
				return;
			}
			
			// 填充tender对象
			BorrowTender tender = new BorrowTender();
			tender.setBorrowId(borrowId);
			tender.setUserId(user.getId());
			tender.setTenderAddip(getIpAddr(request));
			tender.setCalInterestType(BorrowTender.CAL_INTEREST_TYPE_BACK);
			if(param.containsKey("tenderAmount")){
				tender.setDeductionMoney(new BigDecimal(0));
				tender.setTenderAmount(new BigDecimal(param.get("tenderAmount").toString()));
			}			
			else{
				tender.setDeductionMoney(new BigDecimal(0));
				tender.setTenderCopies(Integer.valueOf(param.get("tenderCopies").toString()));
			}

			BorrowType borrowType = borrowTypeService.getBorrowTypeById(borrow.getBidKind());
			// 获取标种对应的service实现类
			BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class,
					borrowType.getDealService());
			ServiceResult sResult = null;
			sResult = borrowService.saveTender(tender,params); // 保存投标
			if(!sResult.isSuccessed()){
				dto.setCode(Status.FAILD.getName());
				dto.setMessage(sResult.getMsg());
				return;
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
			
			dto.addKeyValue("borrowEId", borrowEId.toString());
			dto.addKeyValue("tenderMoney", tender.getTenderAmount().toString());
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("投标成功");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("投标失败");
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	
	/**
	 * 投标时验证请求是否合法
	 * 
	 * @param request
	 * @return
	 */
	private JsonResult validateTenderRequest(HttpServletRequest request,int borrowId) {
		JsonResult err = new JsonResult("116", "传入数据有误，请刷新后重试");
		try {
			
			Borrow borrow = borrowQueryService.getBorrowById(borrowId);
			if (!CompareUtils.equals(borrow.getAnnualInterestRate(),
					new BigDecimal(request.getParameter("annualInterestRate")))) {
				return err;
			}
			if (!CompareUtils.equals(borrow.getBorrowSum(), new BigDecimal(
					request.getParameter("borrowSum")))) {
				return err;
			}
			//判断是否有投标密码
			if(borrow.getTenderPassword() != null  && !borrow.getTenderPassword().equals("")){
				String tenderPassword = request.getParameter("tenderPassword").toString();
				 try {
					tenderPassword = DesUtil.decryptDES(StringUtils.trimToEmpty(tenderPassword),Constants.DES_PUBLIC_KEY_IOS_ANDROID);
					tenderPassword = MD5Utils.stringToMD5(tenderPassword);
				 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	//限制待收
	public boolean minWaitRepossessLimit(HttpServletResponse response,User user,Map<String, String> params){
		Borrow borrow = borrowQueryService.getBorrowById(Integer.parseInt(params.get("borrowId")));
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
	
	public static void main(String[] args) {
		
		String appMi = "Yz6Pziy/Kw0=";
		DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
		//处理交易密码
		DesEncrypt aesEncrypt = new DesEncrypt(
				Constants.DES_PRIVATE_ENCRYPT_KEY);
		//原始交易密码
		String pwd;
		try {
			//原始交易密码
			pwd = DesUtil.decryptDES(StringUtils.trimToEmpty(appMi),Constants.DES_PUBLIC_KEY_IOS_ANDROID);
			System.out.println("原始交易密码："+pwd);
			//转换为jsp加密密码
			DesEncrypt des = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
			//jsp 加密数据
			String str2 = des.encrypt(pwd);
			System.out.println("jsp加密数据："+str2);
			String jspYuan = desEncrpt.decrypt(str2).toString();
			System.out.println("jsp原始数据:"+jspYuan);
			System.out.println("加密后的数据："+MD5Utils.stringToMD5(aesEncrypt.encrypt(jspYuan)));
			
			String tenderPassword = DesUtil.decryptDES(StringUtils.trimToEmpty(appMi),Constants.DES_PUBLIC_KEY_IOS_ANDROID);
			System.out.println(tenderPassword);
			//判断定向密码
			System.out.println("投标密码："+MD5Utils.stringToMD5("123456"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
