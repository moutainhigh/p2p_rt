package com.rbao.east.appapi;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baofoo.sdk.api.BaoFooApi;
import com.rbao.east.appapi.common.ResponseDto;
import com.rbao.east.appapi.common.Status;
import com.rbao.east.common.CalculateProcess;
import com.rbao.east.common.Constants;
import com.rbao.east.common.GenerateNo;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.dao.AccountCashDao;
import com.rbao.east.dao.BorrowRepossessedDao;
import com.rbao.east.entity.AccountCash;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.AllBank;
import com.rbao.east.entity.Area;
import com.rbao.east.entity.AutotenderRules;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.ExperienceGold;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.PaymentConfig;
import com.rbao.east.entity.RedenvelopesRecord;
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.entity.SysLetterTemplates;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.entity.UserBank;
import com.rbao.east.service.APPAutoLoginLogService;
import com.rbao.east.service.AccountCashService;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.AccountRechargeService;
import com.rbao.east.service.AllBankService;
import com.rbao.east.service.AreaService;
import com.rbao.east.service.AutotenderRulesService;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.CreditTypeService;
import com.rbao.east.service.ExperienceGoldService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.PaymentConfigService;
import com.rbao.east.service.RecommendRewardService;
import com.rbao.east.service.RedenvelopesService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.SysConfigService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserBankService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.AppAttestationUtil;
import com.rbao.east.utils.Base64Utils;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.DesUtil;
import com.rbao.east.utils.JsonUtils;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;
import com.rbao.east.utils.SysCacheUtils;
import com.rbao.east.utils.TokenUtils;
import com.rbao.east.web.bf.vo.PaymentInfoBF;
import com.rbao.east.web.llpay.config.PartnerConfig;
import com.rbao.east.web.llpay.vo.PaymentInfo;

/**
 * 用户中心相关操作
 */
@Controller
@RequestMapping("/userCenterOperte")
public class UserCenterOperteController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserCenterOperteController.class);
	@Autowired
	private AreaService areaService;
	@Autowired
	private BorrowService borrowQueryService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserBankService userBankService;
	@Autowired
	private AllBankService allBankService;

	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	private SysConfigParamService sysConfigParamService;
	@Autowired
	private ExperienceGoldService experienceGoldService;
	@Autowired
	private CreditTypeService creditTypeService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private UserAccountService accountService;
	@Autowired
	private RecommendRewardService recommendRewardService;
	@Autowired
	private BorrowRepossessedDao borrowRepossessedDao;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private BorrowRepossessedService borrowRepossessedService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private AccountCashDao accountCashDao;
	@Autowired
	private AccountCashService accountCashService;
	@Autowired
	private APPAutoLoginLogService appLogService;
	@Autowired
	private AccountRechargeService accountRechargeService;
	@Autowired
	private PaymentConfigService paymentConfigService;
	
	@Autowired
	private AutotenderRulesService autotenderRulesService;
	@Autowired
	private BorrowTypeService borrowTypeService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private RedenvelopesService redService;
	
	@RequestMapping("appLayout.do")
	public void appLayout(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ResponseDto dto = new ResponseDto();
		try {
		/*	User loginAPPUser = this.loginAPPUser(request);*/
			String token = request.getHeader("token");
			this.appLogService.deleteByTokenId(token);
			for (Enumeration attribute = request.getAttributeNames(); attribute
					.hasMoreElements();) {
				request.getSession().removeAttribute(
						(String) attribute.nextElement());
			}
			request.getSession().invalidate();

			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("您已成功退出！");
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("退出出错！");
			logger.error(e.getMessage());
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}

	}
	
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "appToUserCenter.do")
	public void appToUserCenter(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = this.loginAPPUser(request);
		//刷新用户，重新设置session
		user = userService.getById(user.getId());
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(TokenUtils.effSecond);
		session.setAttribute(Constants.APP_USER_SESSION, user);		
		ResponseDto dto = new ResponseDto();
		Map<String, String> paramsMap = new HashMap<String, String>();
		
		try {
			if (user != null) {
				paramsMap.put("userId", user.getId().toString());
				UserBank userBank = this.userBankService.getByUserId(paramsMap);
				paramsMap.remove("userId");
				dto.addKeyValue("imgUrl", user.getAvatarImg());
				BigDecimal totalIncome = new BigDecimal(0);
				UserAccount userAccount = accountService.getByUserId(user
						.getId());
				// 用户名
				dto.addKeyValue("userAccount", user.getUserAccount());
				dto.addKeyValue("userPhone", user.getUserPhone());
				dto.addKeyValue("userEmail", user.getUserEmail());
				dto.addKeyValue("phoneStatus", user.getPhoneStatus().toString());
				dto.addKeyValue("emailStatus", user.getEmailStatus().toString());
				dto.addKeyValue("cardNumber", user.getCardNumber());
				dto.addKeyValue("realNameStatus", user.getRealnameStatus().toString());
				dto.addKeyValue("userRealName", user.getUserRealname());
				if(userBank!=null&&userBank.getAllBank()!=null){
					dto.addKeyValue("bankName", userBank.getAllBank().getBankName());
					dto.addKeyValue("bankAccount", userBank.getBankAccount());
					dto.addKeyValue("bankStatus", "1");
				}else{
					dto.addKeyValue("bankStatus", "0");
				}
				if(user.getUserPaypassword()==null ||user.getUserPaypassword().equals("") ){
					dto.addKeyValue("payPasswordStatus", "0");
				}else{
					dto.addKeyValue("payPasswordStatus", "1");
				}
				// 总资产
				dto.addKeyValue("allMoney", userAccount.getAllMoney()==null?"0.00":userAccount.getAllMoney().toString());
				// 可用余额
				dto.addKeyValue("avaMoney", userAccount.getAvailableMoney()==null?"0.00":userAccount.getAvailableMoney().toString());
				paramsMap.put("userId", user.getId().toString());
				// 代收总额
				List<BigDecimal> repMoney = borrowRepossessedDao.selects(
						"selectTotalMoneys", paramsMap);
				dto.addKeyValue("allRepMoney",repMoney.get(0)==null?"0.00":repMoney.get(0).toString());
				paramsMap.remove("userId");
				totalIncome = totalIncome.add(userAccount.getGetInterest());
				// 体验金
				paramsMap.put("userId", user.getId().toString());
				paramsMap.put("experienceStatus", "1");
				ExperienceGold experienceGold = experienceGoldService
						.getExperienceGoldByParam(paramsMap);
				if (experienceGold != null) {
					totalIncome = totalIncome.add(experienceGold
							.getExperienceGoldInterest());
				}
				// 分销收益
				paramsMap.clear();
				paramsMap.put("recommendUserid", user.getId().toString());
				// 为空情况
				PageModel pageModel = recommendRewardService
						.getRewardPage(paramsMap);
				if (pageModel != null) {
					List<Map> dataList = new LinkedList<Map>();
					for (Iterator<Map> i = pageModel.getList().iterator(); i
							.hasNext();) {
						Map<String, Object> curMap = i.next();
						String recommendMoney = curMap.get("recommendMoney")
								.toString();
						totalIncome = totalIncome.add(new BigDecimal(
								recommendMoney));
					}
				}
				// 总收益
				dto.addKeyValue("totalIncome",
						totalIncome.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				//服务电话
				dto.addKeyValue("serverTel",SysCacheUtils.getSysConfig().getSysWebsitetel());
				
				dto.setCode(Status.SUCCESS.getName());
				dto.setMessage("查询成功");
			} else {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("用户不存在");
			}
		} catch (Exception e) {

		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}

	/**
	 * 获得用户正在投标项目信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("appToMytender.do")
	public void appToMytender(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		User user = this.loginAPPUser(request);

		try {
			Map<String, String> param = this.getParameters(request);
			param.put("userId", String.valueOf(user.getId()));
			PageModel pageModel = borrowService.getBorrowInBidPage(param);
			List<Map> list = pageModel.getList();
			if (list == null) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("查询失败");
				return;
			}
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			for (Map maps : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("borrowTitle", maps.get("borrowTitle"));
				map.put("tenderAddTime", maps.get("tenderAddTime").toString());
				map.put("tenderMoney", maps.get("tenderAmount").toString());
				map.put("tenderSum", maps.get("tenderSum").toString());
				map.put("borrowSum", maps.get("borrowSum").toString());
				dataList.add(map);

			}
			dto.addKeyValue("totalPage", pageModel.getTotalPage());
			dto.addKeyValue("dataList", dataList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("查询成功");
			logger.info("用户：" + user.getUserAccount() + "ip:"
					+ this.getIpAddr(request) + "获得用户正在投标项目信息成功。");
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("查询失败");
			logger.error(
					"用户：" + user.getUserAccount() + "ip:"
							+ this.getIpAddr(request) + "获得用户正在投标信息失败。", e);
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}

	/**
	 * 
	 * @Title: getRepByStatusPage
	 * @Description: 得到正在投标的信息
	 * @return void    返回类型
	 * @throws
	 */
	@RequestMapping("appGetRepByStatusPage.do")
	public void getRepByStatusPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ResponseDto dto = new ResponseDto();
		Map<String, String> paramsMap = getParameters(request);
		User user = this.loginAPPUser(request);
		try {
			paramsMap.put("userId", user.getId().toString());
			PageModel pageModel = borrowRepossessedService
					.getRepByStatusPage(paramsMap);
			if (paramsMap.containsKey("repossessedStatus")
					&& paramsMap.get("repossessedStatus").equals("1")) {
				// 代收总额
				List<BigDecimal> repMoney = borrowRepossessedDao.selects(
						"selectTotalMoneys", paramsMap);
				dto.addKeyValue("allRepMoney",repMoney.get(0)==null?"0.00":repMoney.get(0).toString());
			}
			List<Map> list = pageModel.getList();
			if (list == null) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("查询失败");
				return;
			}
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if(paramsMap.containsKey("repossessedStatus")
					&& paramsMap.get("repossessedStatus").equals("1")){
				for (Map map : list) {
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("borrowTitle", map.get("borrowTitle"));
					maps.put("prepareDatetime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("prepareDatetime")));
					maps.put("prepareAmount", map.get("prepareAmount").toString());
					dataList.add(maps);
				}
			}else{
				for (Map map : list) {
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("borrowTitle", map.get("borrowTitle"));
					maps.put("prepareDatetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("paidDatetime")));
					maps.put("prepareAmount", map.get("prepareAmount").toString());
					dataList.add(maps);
				}
			}
			
			dto.addKeyValue("dataList", dataList);
			dto.addKeyValue("totalPage", String.valueOf(pageModel.getTotalPage()));
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("查询成功");
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("查询失败");
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}
	/**
     * 验证手机验证码
     * @return
     */
	private boolean checkPhoneCode(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		Map<String, String> paramMap=this.getParameters(request);
		String inputSmsCode = request.getParameter("smsCode");
		String telPhone = request.getParameter("userPhone");
		String type=request.getParameter("type");
		String title="";
		if(type.equals("register")){
			title = MessageCenter.APP_REGISTER;
		}
		if(type.equals("findPassword")){
			title=MessageCenter.APP_FINDPASSWORD;
		}
		if(type.equals("findPayPassword")){
			title=MessageCenter.APP_FINDPAYPASSWORD;
		}
		if(type.equals("cash")){
			title=MessageCenter.APP_CASH;
		}
		paramMap.put("telPhone", telPhone);
		paramMap.put("title", title);
		paramMap.put("smsCode",inputSmsCode);
		
		
		List<MessageCenter> centers=messageCenterService.getMessage(paramMap);
		if(centers.size() == 0){
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("短信验证码错误");
			return false;
		}else{
			//获取发送短信内容
			String content = centers.get(0).getMessageContent();
			//截取短信验证码
			int begin = content.indexOf("：");
			int end = content.indexOf("。");
			String code=content.substring(begin+1,end);
			if(!code.equals(inputSmsCode)) {
				dto.setMessage("短信验证码错误");
				return false;
			}		
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("短信验证码正确");
			return true;
		}
		

	}
	/**
	 * 根据parentId获取所有地区json数据
	 */
	@RequestMapping("getAreaData")
	public String getAreaData(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer pid) {
		List<Area> areas = areaService.selectByParentId(pid);
		SpringUtils.renderJson(response, areas);
		return null;
	}
	/**
	 * App交易明细记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */

	@RequestMapping("appTranscationRecordPage.do")
	public void appTranscationRecordPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ResponseDto dto = new ResponseDto();
		User user = this.loginAPPUser(request);
		try {
			Map<String, String> param = this.getParameters(request);
			if(param.containsKey("tradeCode")){
				String tradeCode = param.get("tradeCode");
				if(tradeCode.equals("0")){
					param.put("rechargeTradeCode", "0");
					param.remove("tradeCode");
				}else if(tradeCode.equals("1")){
					param.put("cashTradeCode", "1");
					param.remove("tradeCode");
				}else if(tradeCode.equals("2")){
					param.put("tenderTradeCode", "2");
					param.remove("tradeCode");
				}else if(tradeCode.equals("3")){
					param.put("getTradeCode", "3");
					param.remove("tradeCode");
				}else if(tradeCode.equals("4")){
					param.put("awardTradeCode", "4");
					param.remove("tradeCode");
				}else if(tradeCode.equals("5")){
					param.put("others", "5");
					param.remove("tradeCode");
				}
			}
			param.put("userId", String.valueOf(user.getId()));
			PageModel pageModel = accountLogService.getAccountLog(param);

			List<Map> list = pageModel.getList();
			if (list == null) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("查询失败");
				return;
			}
			AccountLog logs = new AccountLog();
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			for (Map maps : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tradeType", logs.ALL_TRADE_CODE.get(maps.get("tradeCode")).toString());
				map.put("alLogAddTime", maps.get("alLogAddTime").toString());
				map.put("dealMoney", maps.get("dealMoney").toString());
				dataList.add(map);
			}
			dto.addKeyValue("dataList", dataList);
			dto.addKeyValue("totalPage", String.valueOf(pageModel.getTotalPage()));
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("查询成功");
			logger.info("用户：" + user.getUserAccount() + "ip:"
					+ this.getIpAddr(request) + "获得用户资金记录信息成功。");
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("查询失败");
			logger.error(
					"用户：" + user.getUserAccount() + "ip:"
							+ this.getIpAddr(request) + "获得用户资金记录信息失败。", e);
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}

	}

	/**
	 * 充值
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("appToRecharge.do")
	public void recharge(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ResponseDto dto = new ResponseDto();
		User user = userService.selectByPrimaryKey(this.loginAPPUser(request)
				.getId());
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", user.getId().toString());
		try {
			UserBank userBank = this.userBankService.getByUserId(params);
			if (user.getRealnameStatus() != User.REALNAME_PASS) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("未实名认证");
				return;
			} else if (user.getPhoneStatus() != User.PHONE_PASS) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("未手机认证");
				return;
			} else if (userBank == null) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("未绑定银行卡");
				return;
			}
			dto.addKeyValue("bankName", userBank.getAllBank().getBankName());
			dto.addKeyValue("bankCode", userBank.getAllBank().getBankCode().toString());
			dto.addKeyValue("bankAccount", userBank.getBankAccount());
			dto.addKeyValue("userRealName", user.getUserRealname());
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("查询成功");

		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("查询失败");
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}

	}
	//APP连连支付接口
		@RequestMapping(value = "appLLonlineRecharge.do")
		public void appOnlineRecharge(HttpServletRequest request,
				HttpServletResponse response, Model model,
				AccountRecharge accountRecharge,PaymentInfo paymentInfo) throws UnsupportedEncodingException {
			ResponseDto dto = new ResponseDto();
			Map<String, String> param = this.getParameters(request);
			User user =userService.getById(this.loginAPPUser(request).getId());
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", user.getId().toString());
			UserBank userBank = this.userBankService.getByUserId(params);
			String basePath=SysCacheUtils.getSysConfig().getSysWebsitedomain();
			BigDecimal moneyOrder = new BigDecimal(paymentInfo.getMoney_order());
			BigDecimal maxMoney = new BigDecimal("10000000.00");
			int Statuss =moneyOrder.compareTo(maxMoney);
			if(Statuss==1){
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("最大充值金额为一千万");
			}else{
				String clientIP =this.getIpAddr(request);
				if (clientIP.equals("0:0:0:0:0:0:0:1")) { //localhost取到的IP需转换一下
					clientIP = "127.0.0.1";
				}
				//String payment = request.getParameter("rechargeId");
				String payment = "13";
				String serialNumber=null;
				if(!payment.equals("5")){
					 serialNumber= GenerateNo.payRecordNo(user.getId());
					}else if(payment.equals("5")){
					  serialNumber = GenerateNo.payRecordNo();
					}
				
				Date curDate = new Date();
				accountRecharge.setRechargeTradeNo(serialNumber);
				accountRecharge.setUserId(user.getId());
				accountRecharge.setRechargeAddtime(curDate);
				accountRecharge.setRechargeAddip(clientIP);
				param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
				param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
				String remark = "";
				PaymentConfig paymentConfig = this.paymentConfigService.getById(Integer.parseInt(payment));
				if (payment.equals("13")) { // 连连
					remark = remark +userBank.getAllBank().getBankName()+"（APP）";
				} 
				accountRecharge.setRechargeRemark(remark);
				accountRecharge.setRechargePayment(remark);
				accountRecharge.setRechargeType(AccountRecharge.RECHARGE_TYPE_ON);
				accountRecharge.setRechargeMoney(new BigDecimal(paymentInfo.getMoney_order()));
				boolean flag = accountRechargeService.saveAccountRecharge(accountRecharge,true);
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("添加线上充值");
				operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_RECHARGE);
				operatorLog.setOperatorParams(user.toString());
				operatorLog.setOperatorReturn(flag?"添加线上充值成功":"添加线上充值失败");
				operatorLog.setOperatorStatus(flag?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addFrontLog(operatorLog);
				if (flag) {
					dto.addKeyValue("oid_partner", PartnerConfig.OID_PARTNER);
					dto.addKeyValue("no_order",serialNumber);
					dto.addKeyValue("dt_register", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getUserAddtime()).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
					dto.addKeyValue("dt_order", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accountRecharge.getRechargeAddtime()).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
					dto.addKeyValue("userId", user.getId().toString());
					dto.addKeyValue("id_no", user.getCardNumber());
					dto.addKeyValue("acct_name", user.getUserRealname());
					dto.addKeyValue("card_no", userBank.getBankAccount());
					dto.addKeyValue("notify_url", basePath+PartnerConfig.NOTIFY_URL);
					dto.addKeyValue("bankName", userBank.getAllBank().getBankName());
					dto.addKeyValue("MD5Key", PartnerConfig.MD5_KEY);
					dto.setCode(Status.SUCCESS.getName());
					dto.setMessage("添加充值记录成功");
				}else{
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("添加充值记录失败");
				}
			}
				
			
			JsonUtils.toObjectJson(response, dto);
		}
		
	//APP宝付支付接口
	@RequestMapping(value = "appBFonlineRecharge.do")
	public void appBFOnlineRecharge(HttpServletRequest request,
			HttpServletResponse response, Model model,
			AccountRecharge accountRecharge,PaymentInfoBF paymentInfo) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException
				, InvocationTargetException, InstantiationException, IOException {
		ResponseDto dto = new ResponseDto();
		Map<String, String> param = this.getParameters(request);
		User user =userService.getById(this.loginAPPUser(request).getId());
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", user.getId().toString());
		UserBank userBank = this.userBankService.getByUserId(params);
		String basePath=SysCacheUtils.getSysConfig().getSysWebsitedomain();
		BigDecimal moneyOrder = new BigDecimal(paymentInfo.getMoney_order());
		BigDecimal maxMoney = new BigDecimal("10000000.00");
		int Statuss =moneyOrder.compareTo(maxMoney);
		if(Statuss==1){
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("最大充值金额为一千万");

			JsonUtils.toObjectJson(response, dto);
			return;
		}
		
		String clientIP =this.getIpAddr(request);
		if (clientIP.equals("0:0:0:0:0:0:0:1")) { //localhost取到的IP需转换一下
			clientIP = "127.0.0.1";
		}
		//String payment = request.getParameter("rechargeId");
		String payment = "2";
		String serialNumber=null;
		if(!payment.equals("5")){
			 serialNumber= GenerateNo.payRecordNo(user.getId());
		}else if(payment.equals("5")){
			 serialNumber = GenerateNo.payRecordNo();
		}
		
		//调用宝会SDK支付接口
		Map<String, String> result = BaoFooApi.SdkFrom(request, response, serialNumber, userBank, user, paymentInfo, basePath);
		
		//添加支付订单
		if ("0000".equals(result.get("retCode"))) {
			String tradeNo = result.get("tradeNo");
			Date curDate = new Date();
			accountRecharge.setRechargeTradeNo(serialNumber);
			accountRecharge.setUserId(user.getId());
			accountRecharge.setRechargeAddtime(curDate);
			accountRecharge.setRechargeAddip(clientIP);
			param.put("paymentStatus", PaymentConfig.STATUS_START.toString());
			param.put("paymentType", PaymentConfig.PAYMENT_TYPE_ON.toString());
			String remark = userBank.getAllBank().getBankName()+"（APP）";
			accountRecharge.setRechargeRemark(remark);
			accountRecharge.setRechargePayment(remark);
			accountRecharge.setRechargeType(AccountRecharge.RECHARGE_TYPE_ON);
			accountRecharge.setRechargeMoney(new BigDecimal(paymentInfo.getMoney_order()));
			boolean flag = accountRechargeService.saveAccountRecharge(accountRecharge,true);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid(user.getUserAccount());
			operatorLog.setOperatorTitle("添加线上充值");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_RECHARGE);
			operatorLog.setOperatorParams(user.toString());
			operatorLog.setOperatorReturn(flag?"添加线上充值成功":"添加线上充值失败");
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addFrontLog(operatorLog);
			if (flag) {
				dto.addKeyValue("no_order",serialNumber);
				dto.addKeyValue("tradeNo", tradeNo);
				dto.setCode(Status.SUCCESS.getName());
				dto.setMessage("添加充值记录成功");
			}else{
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("添加充值记录失败");
			}
		} else {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("调用支付接口失败." + result.get("retMsg"));
		}
		
		JsonUtils.toObjectJson(response, dto);
	}
		
	/**app当日回款统计
	 * 
	 * @param request
	 * @param response
	 */
@RequestMapping("appGetCurrentTrlog.do")
public void appGetCurrentTrlog(HttpServletRequest request,HttpServletResponse response){
	Map<String,Object> params = this.getParametersO(request);
	ResponseDto dto = new ResponseDto();
	User user = this.loginAPPUser(request);
	try{
		params.put("userId", user.getId());
		params.put("dateTime", new Date());
		params.put("repossessedStatus", 2);
		 List<Map> list = borrowRepossessedDao.selects("selectCurrentDayLog", params);
		if(list==null){
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("查询失败");
			return;
		}
		dto.addKeyValue("totalCount", list.get(0).get("totalCount"));
		dto.addKeyValue("allRepMoney", list.get(0).get("allRepMoney")==null? "0.00":list.get(0).get("allRepMoney").toString());
		dto.addKeyValue("allCapital", list.get(0).get("allCapital")==null? "0.00":list.get(0).get("allCapital").toString());
		dto.addKeyValue("allInterest", list.get(0).get("allCapital")==null? "0.00":list.get(0).get("allCapital").toString());
		dto.setCode(Status.SUCCESS.getName());
		dto.setMessage("查询成功");
	}catch(Exception e){
		System.out.println(e.getMessage());
	}finally{
		JsonUtils.toObjectJson(response, dto);
	}
	
}
/**
 * app资产明细
 * @param request
 * @param response
 */
@RequestMapping("appMoneyForDetails.do")
 public void appMoneyForDetails(HttpServletRequest request,HttpServletResponse response){
	 ResponseDto dto = new ResponseDto();
	 User user = this.loginAPPUser(request);
	 Map<String,String> params = this.getParameters(request);
	 try{
		 UserAccount userAccount = userAccountService.getByUserId(user.getId());
		 //账户余额
		 BigDecimal accountBalance = userAccount.getAvailableMoney().add(userAccount.getUnavailableMoney());
		 dto.addKeyValue("allMoney", userAccount.getAllMoney().toString());
		 dto.addKeyValue("accountBalance", accountBalance.toString());
		 dto.addKeyValue("availableBalance", userAccount.getAvailableMoney().toString());
		 dto.addKeyValue("unAvailableBalance", userAccount.getUnavailableMoney().toString());
		 //待收金额
		 params.put("userId", user.getId().toString());
		 params.put("repossessedStatus","1");
		   List<Map> list = borrowRepossessedDao.selects("selectCurrentDayLog", params);
			dto.addKeyValue("allRepMoneying", list.get(0).get("allRepMoney")==null? "0.00":list.get(0).get("allRepMoney").toString());
			dto.addKeyValue("allCapitaling", list.get(0).get("allCapital")==null? "0.00":list.get(0).get("allCapital").toString());
			dto.addKeyValue("allInteresting", list.get(0).get("allInterest")==null? "0.00":list.get(0).get("allInterest").toString());
		//已收金额	
		   params.put("repossessedStatus", "2");
		   List<Map> lists = borrowRepossessedDao.selects("selectCurrentDayLog", params);
		   dto.addKeyValue("allRepMoneyed", list.get(0).get("allRepMoney")==null? "0.00":list.get(0).get("allRepMoney").toString());
		   dto.addKeyValue("allCapitaled", list.get(0).get("allCapital")==null? "0.00":list.get(0).get("allCapital").toString());
		   dto.addKeyValue("allInterested", list.get(0).get("allInterest")==null? "0.00":list.get(0).get("allInterest").toString());
		   params.remove("repossessedStatus");
		//优惠劵
		   dto.addKeyValue("privilege", "0.00");
		   dto.addKeyValue("privilegeed", "0.00");
		   //收益
		   dto.addKeyValue("allInterest", userAccount.getGetInterest());
		   Object obj1 = accountCashDao.getObject("selectSumCash", params);
		   Object obj2 = accountCashDao.getObject("selectSumRecharge", params);
		   dto.addKeyValue("cashMoney",obj1 ==null? "0.00":new BigDecimal(obj1.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		   dto.addKeyValue("rechargeMoney", obj2 ==null? "0.00":new BigDecimal(obj2.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		   dto.setCode(Status.SUCCESS.getName());
		   dto.setMessage("查询成功");
		   
	 }catch(Exception e){
		 System.out.println(e.getMessage());
	 }finally{
		 JsonUtils.toObjectJson(response, dto);
	 }
	 
 }
/**
 * App提现
* @Title: getWebWithdraw
* @Description: 用户提现界面
* @return json
* @throws
 */
@RequestMapping(value="appGetWebWithdraw.do")//提现
public void appGetWebWithdraw(HttpServletRequest request,HttpServletResponse response, Model model){
	ResponseDto dto = new ResponseDto();
	Map<String, String> param = new HashMap<String, String>();
	User user=this.userService.getById(this.loginAPPUser(request).getId());
	param.put("userId", user.getId().toString());
	//判断用户是否实名认证
	try{
		UserBank userBank = this.userBankService.getByUserId(param);
		if(user.getRealnameStatus()!=User.REALNAME_PASS){
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("用户未实名认证");
			return;
		}else if(userBank==null){
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("用户未绑定银行卡");
			return;
		}else{
			UserAccount userAccount = this.userAccountService.getByUserId(user.getId());
			// 费率
			SysFeesRate sFRate = SysCacheUtils.getSysFeesRate();
			dto.addKeyValue("bankName", userBank.getAllBank().getBankName());
			dto.addKeyValue("userPhone", user.getUserPhone());
			/*dto.addKeyValue("bankCode", userBank.getAllBank().getBankCode());*/
			dto.addKeyValue("bankAccount", userBank.getBankAccount());
			/*dto.addKeyValue("userRealName", user.getUserRealname());*/
			dto.addKeyValue("available", userAccount.getAvailableMoney().toString());
			dto.addKeyValue("cashFee", sFRate.getSysWithdrawalOne().toString());
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("查询成功");
			
		}
		
	}catch(Exception e){
		
	}finally{
		JsonUtils.toObjectJson(response, dto);
	}
	
}/**
 * App提交提现申请
 * 
 * @param request
 * @param response
 * @param model
 * @return
 */
@RequestMapping("appApplyWithDraw.do")
public void appApplyWithDraw(HttpServletRequest request,HttpServletResponse response, Model model, AccountCash accountCash) {
	ResponseDto dto = new ResponseDto();
	User user = this.loginAPPUser(request);
	Map<String, String> params = new HashMap<String, String>();
	params.put("userId", user.getId().toString());
	String noOrder = GenerateNo.payRecordNo(user.getId());
	UserBank userBank = this.userBankService.getByUserId(params);
	 String basePath=SysCacheUtils.getSysConfig().getSysWebsitedomain();
	String [] strs = userBank.getBankCity().split("\\|");
	try{
		if(checkPhoneCode(request, response)){
			Map<String, String> param = this.getParameters(request);
			param.put("userId", user.getId().toString());
			param.put("noOrder", noOrder);
			accountCash.setCashAddip(this.getIpAddr(request));
			Map<String ,String> map=new HashMap<String, String>();
			map.put("userId", user.getId().toString());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			map.put("thisTime", sdf.format(new Date()));
			Map m=new HashMap();
			m.putAll(param);
			m.put("cashStatus",  new Integer[]{AccountCash.cashApply,AccountCash.cashSuccess});
			
			//今日已经提现或申请的
			BigDecimal todayTotal=this.accountCashService.selectWithDrawTotal(m);
			if(todayTotal==null){
				todayTotal=new BigDecimal(0);
			}
			DesEncrypt aesEncrypt = new DesEncrypt(
					Constants.DES_PRIVATE_ENCRYPT_KEY);
			String payPassword = DesUtil.decryptDES(StringUtils
					.trimToEmpty(param.get("payPassword").toString()),
					Constants.DES_PUBLIC_KEY_IOS_ANDROID);
			if ("".equals(user.getUserPaypassword())
					|| user.getUserPaypassword() == null) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("你的交易密码为初始密码，请先修改交易密码！");
				logger.error("你的交易密码为初始密码，请先修改交易密码！");
			}else{
				if(param!=null){
					if(user.getUserPaypassword().equals(MD5Utils.stringToMD5(aesEncrypt.encrypt(payPassword)))){
						//账户
						UserAccount userAccount = this.userAccountService.selectByUserIdForUpdate(user.getId());
						// 费率
						SysFeesRate sFRate = SysCacheUtils.getSysFeesRate();
						//大于0
						if(CompareUtils.greaterThanZero(accountCash.getCashTotal())){
							//最小提现金额
							if(CompareUtils.greaterEquals(accountCash.getCashTotal(), sFRate.getSysMinWithdrawal())){
								//今日提现总额
								BigDecimal total=todayTotal.add(accountCash.getCashTotal());
								//最大提现金额
								if(CompareUtils.greaterEquals(sFRate.getSysMaxWithdrawal(), accountCash.getCashTotal())){
									if(CompareUtils.greaterEquals(sFRate.getSysMaxWithdrawal(), total)){
										//用户可用余额
										if(CompareUtils.greaterEquals( userAccount.getAvailableMoney(),accountCash.getCashTotal())){
											BigDecimal delaySumMoney=new BigDecimal(0);
											param.put("code", Borrow.BORROW_TYPE_JING);
											//只查初审通过
											List<Borrow> borrowList=borrowQueryService.getBorrowListByUserIdANDBorrowTypeCode(param);
											
											for (Borrow borrow : borrowList) {
												BigDecimal	delayMoney= borrow.getBorrowSum().subtract(borrow.getPaidAmount()).subtract(borrow.getPaidInterest());
												delaySumMoney=delaySumMoney.add(delayMoney);
											}
											delaySumMoney = delaySumMoney.multiply(new BigDecimal(1.1)); //10%
											
											if(CompareUtils.greaterThanAndEqualsZero(userAccount.getAvailableMoney().subtract(delaySumMoney))){
												accountCash.setUserId(user.getId());
												accountCash.setCashType(2);
												boolean flag = this.accountCashService.saveAccountCash(accountCash,param);
												
												/**
												 * 记录日志
												 */
												User loginUser=this.loginAPPUser(request);
												OperatorLog operatorLog = new OperatorLog();
												operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
												operatorLog.setOperatorTitle("提现申请");
												operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
												operatorLog.setOperatorCategory(OperatorLog.CATEGORY_DRAW);
												operatorLog.setOperatorParams(accountCash.toString());
												operatorLog.setOperatorReturn(flag?"添加提现申请成功":"添加提现申请失败");
												operatorLog.setOperatorStatus(flag?200:300);
												operatorLog.setLinkUrl(RequestUtils.getIpAddr());
												operatorLog.setOperatorIp(this.getIpAddr(request));
												operatorLogService.addFrontLog(operatorLog);
												if(flag){
													dto.setCode(Status.SUCCESS.getName());
													dto.setMessage("提现申请正在处理，请耐心等待");
													request.getSession().setAttribute("applayStatus", "提现申请正在处理，请耐心等待");
													logger.info("提现申请成功！");
												}else{
													dto.setCode(Status.FAILD.getName());
													dto.setMessage("提现申请失败！");
													logger.error("提现申请失败！");
												}
											}else{
												dto.setCode(Status.FAILD.getName());
												dto.setMessage("账户可用净资产不足！");
												/*json.setMsg("账户可用净资产不足！您当前可提现"+delaySumMoney.setScale(2, BigDecimal.ROUND_DOWN)+"元。");*/
												logger.error("账户可用净资产不足！");
											}
										}else{
											dto.setCode(Status.FAILD.getName());
											dto.setMessage("账户可用余额不足！");
											logger.error("账户可用余额不足！");
										}
									}else{
										dto.setCode(Status.FAILD.getName());
										dto.setMessage("每日最大提现金额为"+sFRate.getSysMaxWithdrawal()+"元");
										logger.error("每日最大提现金额为"+sFRate.getSysMaxWithdrawal()+"元");
									}
								}else{
									dto.setCode(Status.FAILD.getName());
									dto.setMessage("最大提现金额为"+sFRate.getSysMaxWithdrawal()+"元");
									logger.error("最大提现金额为"+sFRate.getSysMaxWithdrawal()+"元");
								}
								
							}else{
								dto.setCode(Status.FAILD.getName());
								dto.setMessage("最小提现金额为"+sFRate.getSysMinWithdrawal()+"元");
								logger.error("最小提现金额为"+sFRate.getSysMinWithdrawal()+"元");
							}
							
						}else{
							dto.setCode(Status.FAILD.getName());
							dto.setMessage("提现金额必须大于"+sFRate.getSysMinWithdrawal()+"元");
							logger.error("提现金额必须大于"+sFRate.getSysMinWithdrawal()+"元");
						}
					}else{
						dto.setCode(Status.FAILD.getName());
						dto.setMessage("交易密码错误");
						logger.error("交易密码错误");
					}
				}
			}
		}else{
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("验证码错误");
		}
	}catch(Exception e){
		dto.setCode(Status.FAILD.getName());
		dto.setMessage("提现失败");
	}
	JsonUtils.toObjectJson(response, dto);
}
	/**
	 * @Title: appToBankCard
	 * @Description: 跳转到用户绑卡
	 * @return   返回类型
	 * @throws 做成H5页面
	 */
	@RequestMapping("appToBankCard.do")
	public String appToBankCard(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = new HashMap<String, String>();
		User user = this.loginAPPUser(request);
		String result = "";
		// 判断用户是否实名认证
			param.put("userId", this.loginAPPUser(request).getId().toString());
			UserBank userBank = this.userBankService.getByUserId(param);
			// 判断该用户是否已添加银行卡
			if (userBank != null) {
				String[] strs = userBank.getBankCity().split("\\|");
				userBank.setBankCity(strs[0] + strs[1].toString());
				model.addAttribute("userBank", userBank);

			}
			List<AllBank> list = this.allBankService.getAllList();
			model.addAttribute("user", user);
			model.addAttribute("bank", list);
			
			String token = request.getHeader("token");
			if(token == null || "".equals(token)){
				token = request.getParameter("token");
			}
			model.addAttribute("token", token);
			result = "usercenter/bank";
		return result;

	}

	/**
	 * @Title: appRealNameAttestation
	 * @Description: 实名认证
	 * @return   返回类型
	 * @throws
	 * 
	 * 
	 */
	@RequestMapping("appRealNameAttestation.do")
	public void appRealNameAttestation(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ResponseDto dto = new ResponseDto();
		Map<String, String> param = getParameters(request);
		User user = this.loginAPPUser(request);
		user.setId(this.loginAPPUser(request).getId());
		user.setCardNumber(param.get("cardNumber").toUpperCase());

		// 加验证
		String ur = param.get("userRealname").trim();
		String cd = param.get("cardNumber").trim().toUpperCase();
		boolean checkNameCheseFlag = AppAttestationUtil.checkNameChese(ur);
		boolean idCard = AppAttestationUtil.isIDCard(cd);

		if (checkNameCheseFlag == true && idCard == true) {
			if (user.getCardNumber().length() == 18) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("cardNumber", user.getCardNumber());
				User u = this.userService.selectUserByParam(map);
				if (u == null) {
					// 判断可用余额
					UserAccount userAccount = userAccountService
							.getByUserId(user.getId());
					BigDecimal availableMoney = userAccount.getAvailableMoney();
					BigDecimal sysAuthRate = SysCacheUtils.getSysFeesRate()
							.getSysAuthRate();
					boolean b = false;
					if (CompareUtils.greaterThanAndEqualsZero(availableMoney
							.subtract(sysAuthRate))) {
						user.setCardType(User.IDCARD);
						// 实名认证
						/*
						 * boolean flag =
						 * CheckRealName.checkRealName(param.get("userRealname"
						 * ),param.get("cardNumber"));
						 */
						boolean flag = true;
						if (flag) {

							userCreditService.addUserCredit(
									CreditType.REALNAME, user.getId());
							creditLogService.addCreditLog(CreditType.REALNAME,
									user.getId());

							user.setUserRealname(param.get("userRealname"));
							user.setRealnameStatus(2);
							dto.setCode(Status.SUCCESS.getName());
							dto.setMessage("实名认证成功");
							b = userService.saveOrUpdate(user);
							User user1 = userService.selectByPrimaryKey(user
									.getId());
							CreditType credit = this.creditTypeService
									.getCreditType(null, CreditType.REALNAME);
							String integral = user1.getUserIntegral();
							if (integral != null) {
								Integer integral1 = Integer.parseInt(integral);
								Integer userIntegral = integral1
										+ credit.getCreditScore();
								user1.setUserIntegral(userIntegral.toString());
							} else {
								user1.setUserIntegral(credit.getCreditScore()
										.toString());
							}
							userService.updateByPrimaryKeySelective(user1);
							MessageCenter center = new MessageCenter();
							center.setMessageSendDatetime(new Date());
							center.setMessageSendIp(this.getIpAddr(request));
							center.setMessageStatus(MessageCenter.STATUS_UNREAD);
							center.setReceiveUserId(user1.getId());
							center.setSendUserId(Constants.ADMIN_USER_ID);
							center.setMessageContent(flag
									&& user1.getRealnameStatus() == User.REALNAME_PASS ? "用户"
									+ user1.getUserAccount()
									+ "，你的实名认证成功，认证积分"
									+ credit.getCreditScore().toString() + "分。"
									: "用户" + user1.getUserAccount()
											+ "，你的实名认证失败，请重新操作。");
							center.setMessageTitle("实名认证");
							center.setNoticeTypeId(MessageCenter.MESSAGE);
							this.messageCenterService
									.send(center,
											flag
													&& user.getEmailStatus() == User.REALNAME_PASS ? Notice.REALNAME_YES
													: Notice.REALNAME_NO);
							// 实名且邮箱认证都通过后，插入抵扣金，修改抵扣金状态
							user = userService.selectByPrimaryKey(user.getId());
							if (true == b) {
								Map<String, String> sysConfigParamMap = sysConfigParamService
										.getAllValueToMap();
								String deductionMoneySwitch = sysConfigParamMap
										.get("deductionMoney_switch");

								if (deductionMoneySwitch.equals("1")) {
									String deductionMoneyAmount = sysConfigParamMap
											.get("deductionMoney_amount");
									userAccount
											.setDeductionMoney(new BigDecimal(
													deductionMoneyAmount));
									userAccount.setDeductionStatus(1);
									boolean addFlag = userAccountService
											.updateByPrimaryKeySelective(userAccount);
									if (true == addFlag) {
										MessageCenter centerDeduction = new MessageCenter();
										centerDeduction
												.setMessageSendDatetime(new Date());
										centerDeduction.setMessageSendIp(this
												.getIpAddr(request));
										centerDeduction
												.setMessageStatus(MessageCenter.STATUS_UNREAD);
										centerDeduction.setReceiveUserId(user
												.getId());
										centerDeduction
												.setSendUserId(Constants.ADMIN_USER_ID);
										centerDeduction
												.setMessageContent(addFlag ? "用户"
														+ user.getUserAccount()
														+ "得到抵扣金"
														+ deductionMoneyAmount
														+ "元，可以正常使用抵扣金。"
														: "得到抵扣金失败！");
										centerDeduction
												.setMessageTitle("发送抵扣金");
										centerDeduction
												.setNoticeTypeId(MessageCenter.MESSAGE);
										/*
										 * this.messageCenterService.send(
										 * centerDeduction,
										 * addFlag&&user.getEmailStatus
										 * ()==User.EMAIL_PASS
										 * ?Notice.EMAIL_YES:Notice.EMAIL_NO);
										 */
										// 发送站内信
										this.messageCenterService.send(
												user.getId(), "抵扣金", "已经发放抵扣金"
														+ deductionMoneyAmount
														+ "元,可以正常使用抵扣金",
												Notice.deductionMoney);

									}
								}
								String experienceGoldSwitchValue = sysConfigParamMap
										.get("experienceGold_switch");
								String experienceGoldAprValue = sysConfigParamMap
										.get("experienceGold_apr");
								String experienceGoldPeriodValue = sysConfigParamMap
										.get("experienceGold_period");
								String experienceGoldMoneyValue = sysConfigParamMap
										.get("experienceGold_money");

								// 判断是否开启体验金开关
								if (experienceGoldSwitchValue.equals("1")) {
									// 插入体验金
									ExperienceGold experienceGold = experienceGoldService
											.getExperienceGoldByUserId(user
													.getId());

									if (experienceGold == null) {
										// 获取l利率和金额,期限,
										BigDecimal apr = new BigDecimal(
												experienceGoldAprValue);
										BigDecimal totalExperienceGold = new BigDecimal(
												experienceGoldMoneyValue);
										int loanPeriodCount = Integer
												.parseInt(experienceGoldPeriodValue);

										// 计算出体验金利息(按一次性还款)
										BigDecimal interestOfDay365 = CalculateProcess
												.interestOfDay365(
														totalExperienceGold,
														apr, loanPeriodCount);

										Date date = new Date();// 添加时间

										Date repayDay = DateUtils.addDay(date,
												loanPeriodCount);// 还款时间

										ExperienceGold experienceGold2 = new ExperienceGold();
										experienceGold2.setUserid(user.getId());
										experienceGold2
												.setExperienceGoldStatus(0);
										experienceGold2
												.setExperienceGoldInterest(interestOfDay365);
										experienceGold2
												.setExperienceGoldAddtime(date);
										experienceGold2
												.setExperienceGoldRepaytime(repayDay);

										boolean payFlag = experienceGoldService
												.saveOrUpdate(experienceGold2);

										if (payFlag == true) {
											// 发送站内信
											messageCenterService
													.send(user.getId(),
															"体验金",
															"已经发放体验金"
																	+ totalExperienceGold
																	+ ",利率为"
																	+ apr
																	+ "%,时长为"
																	+ loanPeriodCount
																	+ "天,到期收益为"
																	+ interestOfDay365
																	+ "元(注：3天后"
																	+ totalExperienceGold
																	+ "元体验金的利息"
																	+ interestOfDay365
																	+ "元显示在“可用余额”中，您需在首投后，且首投的产品进入到“计息中”的状态中，"
																	+ interestOfDay365
																	+ "元方可提现。)",
															Notice.ExperienceGoid);

											// 记录操作日志
											User loginUser = this
													.loginAPPUser(request);
											OperatorLog operatorLog = new OperatorLog();
											operatorLog.setLogUserid("用户Id："
													+ loginUser.getId()
													+ ",用户名："
													+ loginUser
															.getUserAccount());
											operatorLog
													.setOperatorTitle("体验金发放");
											operatorLog
													.setOperatorType(OperatorLog.TYPE_FRONT);
											operatorLog
													.setOperatorCategory(OperatorLog.CATEGORY_EXPERIENCE_GOLD);
											operatorLog.setOperatorParams(user
													.toString());
											operatorLog
													.setOperatorReturn(payFlag ? "体验金发放成功"
															: "体验金发放失败");
											operatorLog
													.setOperatorStatus(payFlag ? 200
															: 300);
											operatorLog.setLinkUrl(RequestUtils
													.getIpAddr());
											operatorLog.setOperatorIp(this
													.getIpAddr(request));
											operatorLogService
													.addFrontLog(operatorLog);
										}

									}

								}

							}
						} else {
							dto.setCode(Status.ERROR.getName());
							dto.setMessage("实名认证失败");
						}

						/**
						 * 记录日志
						 */
						User loginUser = this.loginAPPUser(request);
						OperatorLog operatorLog = new OperatorLog();
						operatorLog.setLogUserid("用户Id：" + loginUser.getId()
								+ ",用户名：" + loginUser.getUserAccount());
						operatorLog.setOperatorTitle("申请实名认证");
						operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
						operatorLog
								.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION_APPLY);
						operatorLog.setOperatorParams(user.toString());
						operatorLog.setOperatorReturn(b ? "申请实名认证成功"
								: "申请实名认证失败");
						operatorLog.setOperatorStatus(b ? 200 : 300);
						operatorLog.setLinkUrl(RequestUtils.getIpAddr());
						operatorLog.setOperatorIp(this.getIpAddr(request));
						operatorLogService.addFrontLog(operatorLog);
					} else {

						dto.setCode(Status.FAILD.getName());
						dto.setMessage("可用余额不足");
					}
				} else {
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("此身份证号已存在");
				}
			} else {
				logger.error("实名认证信息不完整！");
			}
		} else {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("填入信息格式不正确");
		}

		JsonUtils.toObjectJson(response, dto);

	}

	/**
	 * @Title: appUserMessageCenter
	 * @Description: 用户信息中心
	 * @return   返回类型
	 * @throws
	 */
	@RequestMapping(value = "appUserMessageCenter.do")
	public void appUserMessageCenter(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ResponseDto dto = new ResponseDto();
		Map<String, String> paramsMap = getParameters(request);
		Integer userId = this.loginAPPUser(request).getId();
		if (userId != null) {
			paramsMap.put("receiveUserId", String.valueOf(userId));
			PageModel pageModel = messageCenterService
					.getUserMessagePage(paramsMap);
			List<Map> list = pageModel.getList();

			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			for (Map map : list) {
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("messageSendTime", map.get("messageSendDateTime"));
				map1.put("messageId", map.get("id").toString());
				map1.put("messageTitle", map.get("messageTitle").toString());
				dataList.add(map1);
			}

			dto.addKeyValue("totalPage",
					StringUtil.toString(pageModel.getTotalPage()));
			dto.addKeyValue("dataList", dataList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("查询成功");

		} else {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("查询失败");
		}
		JsonUtils.toObjectJson(response, dto);
	}

	/**
	 * @Title: appUserMessageCenterById
	 * @Description: 用户信息中心展示详情
	 */
	@RequestMapping("appUserMessageCenterById.do")
	public void getMessageById(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String messageId = request.getParameter("messageId");
		ResponseDto dto = new ResponseDto();
		try {
			if (!messageId.equals("")) {
				MessageCenter messageCenter = messageCenterService
						.getMsgById(Integer.parseInt(messageId));
				if (null != messageCenter) {
					if (MessageCenter.STATUS_UNREAD.equals(messageCenter
							.getMessageStatus())) {

						messageCenter
								.setMessageStatus(MessageCenter.STATUS_READED);
						boolean flag = messageCenterService
								.updateMsgCenter(messageCenter);

					}
					dto.setCode(Status.SUCCESS.getName());
					dto.setMessage("查询成功");
					dto.addKeyValue("messageTitle",
							messageCenter.getMessageTitle());
					dto.addKeyValue("messageContent",
							messageCenter.getMessageContent());
					dto.addKeyValue("messageSendTime", DateUtils
							.getDate(messageCenter.getMessageSendDatetime()));
				} else {
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("信息查询失败");
				}

			} else {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("展示信息失败");

			}
		} catch (Exception e) {

		} finally {
			JsonUtils.toObjectJson(response, dto);
		}

	}

	//好友奖励汇总
	@RequestMapping("friendSumReward.do")
	public void friendSumReward(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ResponseDto dto=new ResponseDto();
		try {
			Map<String, String> paramsMap = getParameters(request);
			if (paramsMap == null) {
				paramsMap = new HashMap<String, String>();
			}
			Integer userId = this.loginAPPUser(request).getId();
			if (userId != null) {
				paramsMap.put("recommendUserid", String.valueOf(userId));
				PageModel pageModel = recommendRewardService.getRewardPage(paramsMap);
				List<Map> list=pageModel.getList();
				List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
				for (Map map : list) {
					Map<String, Object> newMap=new HashMap<String, Object>();
					newMap.put("userAccount", map.get("userAccount"));
					newMap.put("time", map.get("time"));
					//newMap.put("typename", map.get("typename"));
					//newMap.put("r", StringUtil.toString(map.get("r")));
					newMap.put("recommendMoney", StringUtil.toString(map.get("recommendMoney")));
					//newMap.put("sta", "1".equals(StringUtil.toString(map.get("sta"))) ? "已生效" : "未生效");
					dataList.add(newMap);
				}
				dto.addKeyValue("recommendUserid", String.valueOf(userId));
				dto.addKeyValue("dataList", dataList);
				dto.addKeyValue("totalPage", StringUtil.toString(pageModel.getTotalPage()));
				
				String signUserId = Base64Utils.encodeStr(this.loginAPPUser(request).getId().toString());
				signUserId = signUserId.replaceAll("\r\n", "");//signUserId自带换行
				String basePath =SysCacheUtils.getSysConfig().getSysWebsitedomain()+"/web/toRegister.do?u=";
				dto.addKeyValue("basePath", basePath+signUserId);
				dto.addKeyValue("signUserId", signUserId);
				
				dto.setCode(Status.SUCCESS.getName());
				dto.setMessage("查询成功");
			}
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("查询失败");
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}	
	}

	/**
	 * 修改支付密码
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("requestSaveUserPayPass.do")
	public void requestSaveUserPayPass(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		ResponseDto dto=new ResponseDto();
		try {
			User user = userService.getById(this.loginAPPUser(request).getId());
			Map<String, String> param = getParameters(request);
			
			//DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
			DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
			
			String newUserPaypassword = DesUtil.decryptDES(StringUtils.trimToEmpty(param.get("newUserPaypassword").toString()), Constants.DES_PUBLIC_KEY_IOS_ANDROID);
						
			if (param.get("newUserPaypassword").equals(param.get("newUserPaypassword2"))) {
				User u = new User();
				u.setId(this.loginAPPUser(request).getId());
				u.setUserPaypassword(MD5Utils.stringToMD5(aesEncrypt.encrypt(newUserPaypassword)));
				boolean b = userService.saveOrUpdate(u);
				user.setUserPaypassword(u.getUserPaypassword());
				User updateUser = userService.getById(this.loginAPPUser(request).getId());
				request.getSession().setAttribute(Constants.APP_USER_SESSION,updateUser);
				
				/**
				 * 记录日志
				 */
				User loginUser = this.loginAPPUser(request);
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
						+ loginUser.getUserAccount());
				operatorLog.setOperatorTitle("修改支付密码");
				operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
				operatorLog.setOperatorParams(user.toString());
				operatorLog.setOperatorReturn(b ? "修改支付密码成功" : "修改支付密码失败");
				operatorLog.setOperatorStatus(b ? 200 : 300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLog.setOperatorIp(this.getIpAddr(request));
				operatorLogService.addFrontLog(operatorLog);
			
				if(b){
					dto.setCode(Status.SUCCESS.getName());
					dto.setMessage("修改支付密码成功");
				}else{
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("修改支付密码失败");
				}	
			} else {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("两次输入密码不一致请重新输入");
			}
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("修改支付密码失败");
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	/**
	 * 修改登录密码
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("requestSaveUserLoginPass.do")
	public void requestSaveUserLoginPass(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		ResponseDto dto=new ResponseDto();
		try {
			User user = userService.getById(this.loginAPPUser(request).getId());
			Map<String, String> param = getParameters(request);
			
			//DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
			DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
			
			//String userPassword = desEncrpt.decrypt(StringUtils.trimToEmpty(request.getParameter("userPassword")));
			//String newUserPassword = desEncrpt.decrypt(StringUtils.trimToEmpty(request.getParameter("newUserPassword")));

			String userPassword = DesUtil.decryptDES(StringUtils.trimToEmpty(param.get("userPassword").toString()),Constants.DES_PUBLIC_KEY_IOS_ANDROID);
			String newUserPassword = DesUtil.decryptDES(StringUtils.trimToEmpty(param.get("newUserPassword").toString()), Constants.DES_PUBLIC_KEY_IOS_ANDROID); 
			
			String pass = MD5Utils.stringToMD5(aesEncrypt.encrypt(userPassword));
			if (pass.equals(user.getUserPassword())) {
//				if (param.get("newUserPassword").equals(param.get("newUserPassword2"))) {
					User u = new User();
					u.setId(this.loginAPPUser(request).getId());
					u.setUserPassword(MD5Utils.stringToMD5(aesEncrypt.encrypt(newUserPassword)));
					boolean b = userService.saveOrUpdate(u);
					
					user.setUserPassword(u.getUserPassword());
					
					User updateUser = userService.getById(this.loginAPPUser(request).getId());
					request.getSession().setAttribute(Constants.APP_USER_SESSION, updateUser);
					
					/**
					 * 记录日志
					 */
					User loginUser = this.loginAPPUser(request);
					OperatorLog operatorLog = new OperatorLog();
					operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
							+ loginUser.getUserAccount());
					operatorLog.setOperatorTitle("修改登陆密码");
					operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
					operatorLog.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
					operatorLog.setOperatorParams(user.toString());
					operatorLog.setOperatorReturn(b ? "修改登陆成功" : "修改登陆失败");
					operatorLog.setOperatorStatus(b ? 200 : 300);
					operatorLog.setLinkUrl(RequestUtils.getIpAddr());
					operatorLog.setOperatorIp(this.getIpAddr(request));
					operatorLogService.addFrontLog(operatorLog);
					
					if(b){
						dto.setCode(Status.SUCCESS.getName());
						dto.setMessage("修改登陆密码成功");
					}else{
						dto.setCode(Status.FAILD.getName());
						dto.setMessage("修改登陆密码失败");
					}				
//				} else {
//					dto.setCode(Status.FAILD.getName());
//					dto.setMessage("两次输入密码不一致请重新输入");
//				}
			} else {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("登录密码不正确请重新输入");
			}
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("修改登陆密码失败");
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	/**
	 * 实名认证
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 */
	@RequestMapping("requestRealnameAttestation.do")
	public void requestRealnameAttestation(HttpServletRequest request,
			HttpServletResponse response, Model model, User user) {
		ResponseDto dto=new ResponseDto();
		Map<String, String> param = getParameters(request);
		
		user.setId(this.loginAPPUser(request).getId());
		user.setCardNumber(param.get("cardNumber").toUpperCase());

		if (user.getCardNumber().length() == 18) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNumber", user.getCardNumber());
			User u = this.userService.selectUserByParam(map);
			if (u == null) {
				//判断可用余额
				UserAccount userAccount=userAccountService.getByUserId(user.getId());
				BigDecimal availableMoney=userAccount.getAvailableMoney();
				BigDecimal sysAuthRate=SysCacheUtils.getSysFeesRate().getSysAuthRate();
				boolean b=false;
				if(CompareUtils.greaterThanAndEqualsZero(availableMoney.subtract(sysAuthRate))){
					user.setCardType(User.IDCARD);
					boolean flag=true;
					if(flag){
						
						userCreditService.addUserCredit(CreditType.REALNAME, user.getId());
						creditLogService.addCreditLog(CreditType.REALNAME, user.getId());
						
						user.setUserRealname(param.get("userRealname"));
						user.setRealnameStatus(2);
						dto.setCode(Status.SUCCESS.getName());
						dto.setMessage("实名认证成功");
						b = userService.saveOrUpdate(user);
						User user1 = userService.selectByPrimaryKey(user.getId());
						CreditType credit=this.creditTypeService.getCreditType(null, CreditType.REALNAME);
						String integral = user1.getUserIntegral();
						if(integral != null){
							Integer integral1 = Integer.parseInt(integral);
							Integer userIntegral = integral1+credit.getCreditScore();
							user1.setUserIntegral(userIntegral.toString());
						}else{
							user1.setUserIntegral(credit.getCreditScore().toString());
						}
						userService.updateByPrimaryKeySelective(user1);
						MessageCenter center=new MessageCenter();
						center.setMessageSendDatetime(new Date());
						center.setMessageSendIp(this.getIpAddr(request));
						center.setMessageStatus(MessageCenter.STATUS_UNREAD);
						center.setReceiveUserId(user1.getId());
						center.setSendUserId(Constants.ADMIN_USER_ID);
						center.setMessageContent(flag&&user1.getRealnameStatus()==User.REALNAME_PASS?"用户"+user1.getUserAccount()+"，你的实名认证成功，认证积分"+credit.getCreditScore().toString()+"分。":"用户"+user1.getUserAccount()+"，你的实名认证失败，请重新操作。");
						center.setMessageTitle("实名认证");
						center.setNoticeTypeId(MessageCenter.MESSAGE);
						this.messageCenterService.send(center, flag&&user.getEmailStatus()==User.REALNAME_PASS?Notice.REALNAME_YES:Notice.REALNAME_NO);
						//实名且邮箱认证都通过后，插入抵扣金，修改抵扣金状态
						user = userService.selectByPrimaryKey(user.getId());
						if(true == b){
							Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
							String deductionMoneySwitch = sysConfigParamMap.get("deductionMoney_switch");
							
							if(deductionMoneySwitch.equals("1")){
								String deductionMoneyAmount = sysConfigParamMap.get("deductionMoney_amount");
								userAccount.setDeductionMoney(new BigDecimal(deductionMoneyAmount));
								userAccount.setDeductionStatus(1);
								boolean addFlag = userAccountService.updateByPrimaryKeySelective(userAccount);
								if(true == addFlag){
									MessageCenter centerDeduction=new MessageCenter();
									centerDeduction.setMessageSendDatetime(new Date());
									centerDeduction.setMessageSendIp(this.getIpAddr(request));
									centerDeduction.setMessageStatus(MessageCenter.STATUS_UNREAD);
									centerDeduction.setReceiveUserId(user.getId());
									centerDeduction.setSendUserId(Constants.ADMIN_USER_ID);
									centerDeduction.setMessageContent(addFlag?"用户"+user.getUserAccount()+"得到抵扣金"+deductionMoneyAmount+"元，可以正常使用抵扣金。":"得到抵扣金失败！");
									centerDeduction.setMessageTitle("发送抵扣金");
									centerDeduction.setNoticeTypeId(MessageCenter.MESSAGE);
									/*this.messageCenterService.send(centerDeduction, addFlag&&user.getEmailStatus()==User.EMAIL_PASS?Notice.EMAIL_YES:Notice.EMAIL_NO);*/
									//发送站内信
									this.messageCenterService.send(user.getId(), "抵扣金", "已经发放抵扣金"+deductionMoneyAmount+"元,可以正常使用抵扣金", Notice.deductionMoney);
									
							}
						}
							String experienceGoldSwitchValue = sysConfigParamMap.get("experienceGold_switch");
							String experienceGoldAprValue = sysConfigParamMap.get("experienceGold_apr");
							String experienceGoldPeriodValue = sysConfigParamMap.get("experienceGold_period");
							String experienceGoldMoneyValue = sysConfigParamMap.get("experienceGold_money");
							
							//判断是否开启体验金开关
							if(experienceGoldSwitchValue.equals("1")){
								//插入体验金
									ExperienceGold experienceGold=experienceGoldService.getExperienceGoldByUserId(user.getId());
									
									if(experienceGold ==null){
										//获取l利率和金额,期限,
										BigDecimal apr=new BigDecimal(experienceGoldAprValue);
										BigDecimal totalExperienceGold=new BigDecimal(experienceGoldMoneyValue);
										int loanPeriodCount=Integer.parseInt(experienceGoldPeriodValue);
										
										//计算出体验金利息(按一次性还款)
										BigDecimal interestOfDay365 = CalculateProcess.interestOfDay365(totalExperienceGold, apr, loanPeriodCount);
										
										Date date = new Date();//添加时间
										
										Date repayDay = DateUtils.addDay(date, loanPeriodCount);//还款时间
										
										
										ExperienceGold experienceGold2 = new ExperienceGold();
										experienceGold2.setUserid(user.getId());
										experienceGold2.setExperienceGoldStatus(0);
										experienceGold2.setExperienceGoldInterest(interestOfDay365);
										experienceGold2.setExperienceGoldAddtime(date);
										experienceGold2.setExperienceGoldRepaytime(repayDay);
										
										boolean payFlag = experienceGoldService.saveOrUpdate(experienceGold2);
										
										
										if(payFlag==true){
											//发送站内信
											messageCenterService.send(user.getId(), "体验金", "已经发放体验金"+totalExperienceGold+",利率为"+apr+"%,时长为"+loanPeriodCount+"天,到期收益为"+interestOfDay365+"元(注：3天后"+totalExperienceGold+"元体验金的利息"+interestOfDay365+"元显示在“可用余额”中，您需在首投后，且首投的产品进入到“计息中”的状态中，"+interestOfDay365+"元方可提现。)", Notice.ExperienceGoid);
											
											//记录操作日志
											User loginUser = this.loginAPPUser(request);
											OperatorLog operatorLog = new OperatorLog();
											operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
													+ loginUser.getUserAccount());
											operatorLog.setOperatorTitle("体验金发放");
											operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
											operatorLog
													.setOperatorCategory(OperatorLog.CATEGORY_EXPERIENCE_GOLD);
											operatorLog.setOperatorParams(user.toString());
											operatorLog.setOperatorReturn(payFlag ? "体验金发放成功" : "体验金发放失败");
											operatorLog.setOperatorStatus(payFlag ? 200 : 300);
											operatorLog.setLinkUrl(RequestUtils.getIpAddr());
											operatorLog.setOperatorIp(this.getIpAddr(request));
											operatorLogService.addFrontLog(operatorLog);
										}																				
									}
							}								
						}
					}else {
						dto.setCode(Status.FAILD.getName());
						dto.setMessage("实名认证失败");
					}
					
					/**
					 * 记录日志
					 */
					User loginUser = this.loginAPPUser(request);
					OperatorLog operatorLog = new OperatorLog();
					operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："
							+ loginUser.getUserAccount());
					operatorLog.setOperatorTitle("申请实名认证");
					operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
					operatorLog
							.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION_APPLY);
					operatorLog.setOperatorParams(user.toString());
					operatorLog.setOperatorReturn(b ? "申请实名认证成功" : "申请实名认证失败");
					operatorLog.setOperatorStatus(b ? 200 : 300);
					operatorLog.setLinkUrl(RequestUtils.getIpAddr());
					operatorLog.setOperatorIp(this.getIpAddr(request));
					operatorLogService.addFrontLog(operatorLog);
	
				}else{
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("可用余额不足！");
				}
			}else{
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("此身份证号已存在");
			}
		} else {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("实名认证信息不完整！");
		}
     
		JsonUtils.toObjectJson(response, dto);
	}
	
	/**
	 * 邮箱认证
	 */
	@RequestMapping(value = "reSendEmai.do")
	public void reSendEmail(HttpServletRequest request, HttpServletResponse response){
		ResponseDto dto=new ResponseDto();
		Map<String, String> param = this.getParameters(request);
		User user1 = this.userService.checkUserByEmail(param);
		if (user1==null||user1.getEmailStatus()!=2) {
			try {
				User user =  this.userService.selectByPrimaryKey(this.loginAPPUser(request).getId());
				if (user == null) {
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("请登录后操作!");
					JsonUtils.toObjectJson(response, dto);
					return;
				}
				String email = request.getParameter("userEmail").trim();
				if (!isEmail(email)) {
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("邮箱格式不正确!");
					JsonUtils.toObjectJson(response, dto);
					return;
				}
			//	if (!email.equals(user.getUserEmail())) {//不是原来邮箱则更新
					user.setUserEmail(email);
					user.setEmailStatus(User.EMAIL_APPLY);//申请中
					userService.updateByPrimaryKeySelective(user);
				//}
			
				dto.setCode(Status.SUCCESS.getName());
				dto.setMessage("操作成功,请登录您的邮箱查看激活邮件!");
				
				//发送邮件
				MessageCenter center = new MessageCenter();
				center.setSendUserId(Constants.ADMIN_USER_ID);
				center.setReceiveUserId(user.getId());
				center.setMessageTitle("【"+SysCacheUtils.getSysConfig().getSysWebsitename()+"】会员" + email + "激活邮件");
				String id = MD5Utils.stringToMD5(user.getId().toString());
				String sendEmailTime = System.currentTimeMillis()/ 1000 + "";
				String path = request.getContextPath();
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
				SysLetterTemplates sysLetterTemplates = SysCacheUtils.getSysLetterTemplates();
				String mailContent = sysLetterTemplates
						.getSysRegisteredMailinfo()
						.replace("#userAccount#", user.getUserAccount())
						.replace("#url#",basePath
								+ "/mobile/UserInquery/appActivation.do?validateId="
								+ id
								+ ","
								+ user.getId()
								+","
								+Base64Utils.encodeStr(sendEmailTime));
				center.setMessageContent("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>"+mailContent+"</body></html>");
				center.setMessageAddress(user.getUserEmail());
				
				center.setMessageSendIp(this.getIpAddr(request));
				messageCenterService.sendEmail(center, null);
				
			} catch (Exception e) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("系统异常!");
				e.printStackTrace();
			}finally{
				JsonUtils.toObjectJson(response, dto);
			}
		} else {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("邮箱已存在");
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	
	/**
	 * 添加银行卡
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userBank
	 */
	@RequestMapping("addBankCard.do")
	public void addBankCard(HttpServletRequest request,
			HttpServletResponse response, Model model, UserBank userBank) {
		@SuppressWarnings("unused")
		Map<String, String> param = this.getParameters(request);
		/*userBank.setBankCity(param.get("userProvince")+param.get("userCity")+param.get("userArea"));*/
		param.put("userId", this.loginAPPUser(request).getId().toString());
		boolean flag = false;
		String ip;
		JsonResult json=new JsonResult();
		try {
			ip = this.getIpAddr(request);
			userBank.setBankAddip(ip);
			userBank.setBankAddtime(new Date());
			UserBank bank = this.userBankService.getByUserId(param);
			if(bank==null){
				if(userBank.getBankId()!=null && userBank.getBankAccount()!=null){
					flag = this.userBankService.saveOrUpdate(userBank);
					if(flag){
						json.setCode("100");
						json.setMsg("银行卡添加成功");
					}else{
						json.setCode("101");
						json.setMsg("银行卡添加失败");
					}
				}else{
					flag=false;
					json.setCode("102");
					json.setMsg("银行卡信息填写不完整");
				}
			}else{
				flag=false;
				json.setCode("103");
				json.setMsg("该用户已添加银行卡");
			}
			/**
			 * 记录日志
			 */
			User loginUser=this.loginAPPUser(request);
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
			operatorLog.setOperatorTitle("添加银行卡");
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BANKCARD);
			operatorLog.setOperatorParams(userBank.toString());
			operatorLog.setOperatorReturn(flag?"添加银行卡成功":"添加银行卡失败");
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
		} catch (Exception e) {
			flag = false;
		}
		SpringUtils.renderJson(response, json);
	}
	
	
	/**
	 * 
	* @Title: toAutoInvest
	* @Description: 自动投标的界面
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping("toAutoInvest.do")
	public void toAutoInvest(HttpServletRequest request,HttpServletResponse response,Model model){
		ResponseDto dto=new ResponseDto();
		try {
			User user = this.loginAPPUser(request);
			AutotenderRules autoTenderRules = autotenderRulesService.getByUserId(user.getId());
			String flag="1";//autoTenderRules对象不为空
			if(autoTenderRules==null){
				autoTenderRules = new AutotenderRules();
				flag="0";//autoTenderRules对象为空
			} 
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("查询成功");
			
			dto.addKeyValue("autoTenderRules", flag);//投标标种			
			dto.addKeyValue("borrowType", StringUtil.toString(autoTenderRules.getBorrowType()));//投标标种
			dto.addKeyValue("aprMin", StringUtil.toString(autoTenderRules.getAprMin()));//利率最小范围
			dto.addKeyValue("aprMax", StringUtil.toString(autoTenderRules.getAprMax()));//利率最大范围
			dto.addKeyValue("periodBegin", StringUtil.toString(autoTenderRules.getPeriodBegin()));//项目期限最小范围
			dto.addKeyValue("periodEnd", StringUtil.toString(autoTenderRules.getPeriodEnd()));//项目期限最大范围
			dto.addKeyValue("repayType", StringUtil.toString(autoTenderRules.getRepayType()));//还款方式
			dto.addKeyValue("rewardType", StringUtil.toString(autoTenderRules.getRewardType()));//投标奖励类型
			dto.addKeyValue("reward", StringUtil.toString(autoTenderRules.getReward()));//按投标金额比例奖励/按固定金额分摊奖励
			dto.addKeyValue("rulesStatus", StringUtil.toString(autoTenderRules.getRulesStatus()));//状态
			dto.addKeyValue("tenderMoney",StringUtil.toString( autoTenderRules.getTenderMoney()));//投标金额
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("查询失败");
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	/**
	 * 添加自动投资
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param auto
	 */
	@RequestMapping("saveAuto.do")
	public void getAuto(HttpServletRequest request,
			HttpServletResponse response, Model model, AutotenderRules auto) {
		ResponseDto dto = new ResponseDto();
		try {		
			User user = this.loginAPPUser(request);
			auto.setUserId(user.getId());
			auto.setUpdateTime(new Date());
			AutotenderRules pms = autotenderRulesService.getByUserId(user.getId());
	        String status = request.getParameter("Status");
	        boolean  flag = false;		
			OperatorLog operatorLog = new OperatorLog();
			if (pms == null) {// 第一次添加
				auto.setAprStatus(1);
				boolean retmsg = autotenderRulesService.addAuto(auto);
				operatorLog.setOperatorTitle("添加自动投资");
				operatorLog.setOperatorReturn(retmsg ? "添加自动投资成功" : "添加自动投资失败");
				operatorLog.setOperatorStatus(retmsg ? 200 : 300);
				if (retmsg) {
					dto.setCode(Status.SUCCESS.getName());
					dto.setMessage("添加成功");
				}else{
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("添加失败");
				}
			} else {// 修改
				auto.setId(pms.getId());
				if(status!=null){
		        	pms.setRulesStatus(Integer.parseInt(status));
		        	pms.setUpdateTime(new Date());
		        	flag = autotenderRulesService.updateAuto(pms);
		        }else{
		        	flag = autotenderRulesService.updateAuto(auto);
		        }
				operatorLog.setOperatorTitle("修改自动投资");
				operatorLog.setOperatorReturn(flag ? "修改自动投资成功" : "修改自动投资失败");
				operatorLog.setOperatorStatus(flag ? 200 : 300);
				if(flag){
					dto.setCode(Status.SUCCESS.getName());
					dto.setMessage("修改成功");
				}else{
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("修改失败");;
				}
			}
			/**
			 * 记录日志
			 */
			User loginUser = this.loginAPPUser(request);
			operatorLog.setLogUserid("用户Id：" + loginUser.getId() + ",用户名："+ loginUser.getUserAccount());
			operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
			operatorLog.setOperatorParams(user.toString());
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLog.setOperatorIp(this.getIpAddr(request));
			operatorLogService.addFrontLog(operatorLog);
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("失败");;
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	/**
	 * 
	* @Title: appEnvelopeList
	* @Description: 红包中心
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appEnvelopeList.do")
	public void appEnvelopeList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = this.getParametersO(request);
		ResponseDto dto = new ResponseDto();
		try {
			Map<String,Object> m = new HashMap<String,Object>();
			User user = this.loginAPPUser(request);
			//红包类型
			if (param.containsKey("redType")) {
				if(param.get("redType").equals("1")){
					m.put("redTypes", "1");
				}else{
					m.put("redTypes", "2");
				}
			}else{
				
			}
			// 页数：默认为1
			if (param.containsKey(Constants.PAGED_CURPAGE)) {
				m.put(Constants.PAGED_CURPAGE, param.get(Constants.PAGED_CURPAGE).toString());
			} else {
				m.put(Constants.PAGED_CURPAGE, "1");
			}
			// 每页条数
			if (param.containsKey(Constants.PAGED_NUM_PERPAGE)) {
				m.put(Constants.PAGED_NUM_PERPAGE, param.get(Constants.PAGED_NUM_PERPAGE).toString());
			} else {
				m.put(Constants.PAGED_NUM_PERPAGE, "10");
			}
			
			m.put("userId", user.getId());
			PageModel pm = redService.getFrontListPaged(m);
			dto.addKeyValue("totalPage", pm.getTotalPage()+"");
			// 数据集
			List<RedenvelopesRecord> retList = pm.getList();
			List<Map<String,String>> dataList = new LinkedList<Map<String,String>>();
			for (int i =0;i<retList.size();i++) {
				RedenvelopesRecord record = retList.get(i);
				Map<String, String> data = new HashMap<String, String>();
				data.put("redId", record.getId().toString());// 
				data.put("amount", record.getAmount().toString());
				data.put("name",record.getName().toString());
				data.put("remark", record.getRemark().toString());
				data.put("periodBeginTime", DateUtils.getDate(record.getPeriodBeginTime()));
				data.put("periodEndTime", DateUtils.getDate(record.getPeriodEndTime()));
				data.put("status", record.getStatus().toString());
				
				dataList.add(data);
			}
			
			
			dto.addKeyValue("dataList", dataList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("红包记录查询成功");
		
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.ERROR.getName());
			dto.setMessage("红包记录查询失败");
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	
	/**
	 * 邮箱格式验证
	 * @param email
	 * @return
	 */
	private boolean isEmail(String email) {
		email = isNull(email);
		Pattern regex = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	private String isNull(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

}
