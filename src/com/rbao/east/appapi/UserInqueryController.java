package com.rbao.east.appapi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
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

import com.rbao.east.appapi.common.AppUtils;
import com.rbao.east.appapi.common.ResponseDto;
import com.rbao.east.appapi.common.Status;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.APPAutologinLog;
import com.rbao.east.entity.Area;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.Content;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysConfigParams;
import com.rbao.east.entity.SysLetterTemplates;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.APPAutoLoginLogService;
import com.rbao.east.service.AreaService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.ContentService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.CreditTypeService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.RecommendService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.Base64Utils;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.DesUtil;
import com.rbao.east.utils.JsonUtils;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SmsUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.SysCacheUtils;
import com.rbao.east.utils.TokenUtils;

/**
 * 
* @ClassName: UserCenterOperteController
* @Description: 用户基本类
* @date 2016年1月4日
 */
@Controller
@RequestMapping("/UserInquery")
public class UserInqueryController  extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserInqueryController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private APPAutoLoginLogService appLogService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private BorrowService borrowQueryService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private SysConfigParamService sysConfigParamService;
	@Autowired
	private CreditTypeService creditTypeService;
	
	/**
	 * 
	* @Title: login
	* @Description: 用户登录
	* @param  userAccount 用户名
	* @param  userPassword    密码
	* @return   返回类型
	* @throws
	 */
	@RequestMapping("/appLogin.do")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = this.getParametersO(request);
		ResponseDto dto = new ResponseDto();
		try {
			if (!param.containsKey("userAccount")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("用户名或手机号或邮箱不能为空");
				return;
			}
			if (!param.containsKey("userPassword")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("密码不能为空");
				return;
			}
			String userAccount = param.get("userAccount").toString();
			System.out.println(param.get("userPassword").toString());
			DesEncrypt aesEncrypt = new DesEncrypt(
					Constants.DES_PRIVATE_ENCRYPT_KEY);
			
			String pwd = DesUtil.decryptDES(StringUtils.trimToEmpty(param.get(
					"userPassword").toString()),
					Constants.DES_PUBLIC_KEY_IOS_ANDROID);
			String checkpassword = MD5Utils
					.stringToMD5(aesEncrypt.encrypt(pwd));
			User user = userService.selectByUserUid(userAccount);
			if (null != user) {
				if (user.getUserPassword().equals(checkpassword)) {
					if (user.getUserIslock() == 0) {
						HttpSession session = request.getSession();
						session.setMaxInactiveInterval(TokenUtils.effSecond);
						session.setAttribute(Constants.APP_USER_SESSION, user);
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						try {
							user.setUserLogintime(sdf.parse(sdf
									.format(new Date())));
							user.setUserLoginip(this.getIpAddr(request));
							userService.saveOrUpdate(user);
						} catch (Exception e) {
							dto.setCode(Status.FAILD.getName());
							dto.setMessage("用户登录失败，请重新登录");
							logger.error(e.getMessage());
							return;
						}
						//三种状态
						dto.addKeyValue("realNameStatus", user.getRealnameStatus().toString());
						dto.addKeyValue("emailStatus", user.getEmailStatus().toString());
						dto.addKeyValue("phoneStatus", user.getPhoneStatus().toString());
						//邮箱
						if(null == user.getUserEmail()){
							dto.addKeyValue("userEmail", "");
						}else{
							dto.addKeyValue("userEmail", user.getUserEmail().toString());
						}
						//头像地址
						dto.addKeyValue("avatarImg", user.getAvatarImg()+"");
						dto.addKeyValue("userPhone", user.getUserPhone().toString());
						//用户名
						dto.addKeyValue("userAccount", user.getUserAccount());
						
						UserAccount userAccount2 = userAccountService.getByUserId(user.getId());
						dto.addKeyValue("allMoney", userAccount2.getAllMoney().toString());
						dto.addKeyValue("availableMoney", userAccount2.getAvailableMoney().toString());
						// 添加日志
						try {
							OperatorLog log = new OperatorLog();
							log.setLogUserid(user.getId().toString());
							log.setOperatorIp(this.getIpAddr(request));
							log.setOperatorTitle("app端用户"
									+ user.getUserAccount() + "登录");
							log.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
							log.setOperatorType(OperatorLog.TYPE_FRONT);
							log.setOperatorReturn("登录成功");
							log.setOperatorStatus(200);
							log.setOperatorParams(user.getUserAccount());
							operatorLogService.addFrontLog(log);
							dto.setCode(Status.SUCCESS.getName());
							dto.setMessage("登录成功");
							// 生成约定好的token
							String generateToken = TokenUtils
									.generateToken(user);
							dto.addKeyValue("token", generateToken);
							Map<String, String> params = new HashMap<String, String>();
							params.put("userId", user.getId().toString());
							
							APPAutologinLog loginLog = this.appLogService
								.getByParam(params);
						
							if (loginLog == null) {								
								loginLog = new APPAutologinLog();
								loginLog.setUserId(user.getId());
								loginLog.setUserAccount(user.getUserAccount());
								loginLog.setUserPassword(user.getUserPassword());
								loginLog.setLoginTime(user.getUserLogintime());
								loginLog.setEffTime(TokenUtils.getEffTime(user
										.getUserLogintime()));
								loginLog.setToken(generateToken);
								this.appLogService.add(loginLog);
							}else{
								loginLog.setLoginTime(user.getUserLogintime());
								loginLog.setToken(generateToken);
								loginLog.setEffTime(TokenUtils.getEffTime(user
										.getUserLogintime()));
								
								this.appLogService.updateByUser(loginLog);
							}
							
							System.out.println("登陆后响应给客户端的jsessionId:--->"
									+ session.getId());
							dto.addKeyValue("JSESSIONID", session.getId());
							return;
						} catch (Exception e) {
							dto.setCode(Status.ERROR.getName());
							dto.setMessage("登录失败");
							logger.error(e.getMessage());
							return;
						}
					} else {
						dto.setCode(Status.FAILD.getName());
						dto.setMessage("账户锁定，请联系系统管理员");
					}
				} else {
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("密码输入有误，请重新输入");
				}
			} else {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("用户名不存在，请重新输入");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.ERROR.getName());
			dto.setMessage("登录失败");
			logger.error("用户" + param.get("userAccount") + "登录操作失败！！！ip："
					+ this.getIpAddr(request), e);
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	
	/**
	 * 
	* @Title: appIndex
	* @Description: app 首页
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appIndex.do")
	public void appIndex(HttpServletRequest request, HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		try{	
			Map<String, Object> ret = this.getParametersO(request);
			
			Map<String, Object> result = new HashMap<String, Object>();
			List<Integer> list = new ArrayList<Integer>();
			list.add(Borrow.STATUS_FIRSTAUDIT_YES);
			list.add(Borrow.STATUS_REPLYING);
			list.add(Borrow.STATUS_FULL);
			list.add(Borrow.STATUS_REVIEW_YES);
			list.add(Borrow.STATUS_REPLY_SUCCESS);
			
			result.put("list", list);
			result.put(Constants.PAGED_CURPAGE, "1");// 当前页
			result.put(Constants.PAGED_NUM_PERPAGE, "1");// 页条数
			result.put("orderType", 4+"");
			result.putAll(ret);
			
//			if (ret.containsKey("borrowType")) {
//				String borrowType = ret.get("borrowType").toString();
//				if(borrowType.equals("8")){
//					result.put("bidList", new Integer [] {BorrowType.TYPE_XINSHOU});
//				}else{
//					result.put("bidList", new Integer [] {BorrowType.TYPE_ZHENGFEN});
//				}
//			}else{
//				result.put("bidList", new Integer [] {BorrowType.TYPE_XINSHOU});
//			}
			result.put("bidList", new Integer [] {BorrowType.TYPE_XINSHOU});
			PageModel LYBList = borrowQueryService
					.showBorrowStatusInfoPageByParam(result);	
			result.put("bidList", new Integer [] {BorrowType.TYPE_LI});
			PageModel JYBList = borrowQueryService
					.showBorrowStatusInfoPageByParam(result);	
			result.put("bidList", new Integer [] {BorrowType.TYPE_ZHENGFEN});
			PageModel SEYBList = borrowQueryService
					.showBorrowStatusInfoPageByParam(result);		
			// 数据集
			List retList = LYBList.getList();
			retList.addAll(JYBList.getList());
			retList.addAll(SEYBList.getList());
			
			List<Map> dataList = new LinkedList<Map>();
			for (Iterator<Map> i = retList.iterator(); i.hasNext();) {
				Map<String, Object> curMap = i.next();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("borrowId", curMap.get("eid").toString());// 标id
				data.put("borrowTitle", curMap.get("borrow_title").toString());// 标标题
				data.put("annualInterestRate", curMap.get("annual_interest_rate").toString());// 年利率
				//判断月天
				String isDay = curMap.get("is_day").toString();
				if(isDay.equals(Borrow.IS_DAY_Y.toString())){
					data.put("borrowTimeLimit", curMap.get("borrow_time_limit").toString()+"天");// 期限
				}else{
					data.put("borrowTimeLimit", curMap.get("borrow_time_limit").toString()+"个月");// 期限
				}
				
				BigDecimal tenderSum = (BigDecimal)curMap.get("tender_sum");
				BigDecimal borrowSum = (BigDecimal)curMap.get("borrow_sum");
				MathContext mc = new MathContext(5);
				float percent = tenderSum.divide(borrowSum,mc).setScale(5, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP).floatValue();
				data.put("percent", percent+"");//百分比
				int borrowStatus = Integer.parseInt(curMap.get("borrow_status").toString());
				if(borrowStatus == 2){
					data.put("borrowStatus", "立即抢购");//标状态
				}else{
					data.put("borrowStatus", Borrow.ALL_STATUS.get(borrowStatus));//标状态
				}
				data.put("buttonLink", "/mobile/UserBorrow/appBorrowDetail.do");// 按钮链接
				//抵扣金金额
				//判断抵扣金开关设置
				Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
				String deductionSwitchValue = sysConfigParamMap.get("deductionMoney_switch");
				String deductionMoneyValue = sysConfigParamMap.get("deductionMoney_amount");
				if(deductionSwitchValue.equals("1")){
					data.put("deductionMoney", deductionMoneyValue);
				}else{
					data.put("deductionMoney", "0");
				}
				dataList.add(data);
			}
			
			dto.addKeyValue("dataList", dataList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("首页查询成功");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("query borrow save error:" +  e);
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("首页查询失败");
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	/**
	 * 
	* @Title: appIndexBanner
	* @Description: app Banner 信息
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appIndexBanner.do")
	public void appIndexBanner(HttpServletRequest request, HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		try{	
			//首页的地址：返回一个list数组
			String channelCode = "appIndex";
			List<Content> contentListPic = contentService.getContentPicFri(channelCode);
			List<Map<String,String>> picList = new LinkedList<Map<String,String>>();
			for(int i = 0;i<contentListPic.size();i++){
				Map<String, String> pic = new HashMap<String, String>();
				Map map = (Map)contentListPic.get(i);
				pic.put("externalLink", map.get("external_link_title")+"");
				pic.put("attachPath",map.get("attach_path")+"");
				pic.put("externalTitle", map.get("content_title")+"");
				picList.add(pic);				
			}
			dto.addKeyValue("picList",picList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("首页查询成功");
			
		}catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("app Banner 查询失败");
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	/**
	 * 
	* @Title: appBorrowList
	* @Description: 理财——标列表
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appBorrowList.do")
	public void appBorrowList(HttpServletRequest request, HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		try{	
			Map<String, Object> ret = this.getParametersO(request);
			Map<String, Object> result = new HashMap<String, Object>();
			List<Integer> list = new ArrayList<Integer>();
			list.add(Borrow.STATUS_FIRSTAUDIT_YES);
			list.add(Borrow.STATUS_REPLYING);
			list.add(Borrow.STATUS_FULL);
			list.add(Borrow.STATUS_REVIEW_YES);
			list.add(Borrow.STATUS_REPLY_SUCCESS);
			result.put("list", list);
			result.put("orderType", 4);
			if(ret != null){
				//页数：默认为1
				if(ret.containsKey(Constants.PAGED_CURPAGE)){
					result.put(Constants.PAGED_CURPAGE, ret.get(Constants.PAGED_CURPAGE).toString());
				}else{
					result.put(Constants.PAGED_CURPAGE, "1");
				}
				// 每页条数
				if (ret.containsKey(Constants.PAGED_NUM_PERPAGE)) {
					result.put(Constants.PAGED_NUM_PERPAGE, ret.get(Constants.PAGED_NUM_PERPAGE).toString());
				} else {
					result.put(Constants.PAGED_NUM_PERPAGE, "10");
				}
				
				//标的种类
				if (ret.containsKey("borrowtypeArray")) {
					String borrowtypeArray = ret.get("borrowtypeArray").toString();
					String[] borrowtypes = null;
					if (!borrowtypeArray.equals("all") && !borrowtypeArray.equals("other")) {
//						borrowtypes = new String[]{borrowtypeArray};
						borrowtypes = borrowtypeArray.split(",");
						result.put("borrowtypeArrays", borrowtypes);
					} else if (borrowtypeArray.equals("other")) {
						borrowtypes = new String[]{"9","10","11","12","13"};
						result.put("borrowtypeArrays", borrowtypes);
					}else {
						result.remove("borrowtypeArrays");
					}
				}
			}
			PageModel page = borrowQueryService
					.showBorrowStatusInfoPageByParam(result);
			// 数据集
			List retList = page.getList();
			List<Map> dataList = new LinkedList<Map>();
			for (Iterator<Map> i = retList.iterator(); i.hasNext();) {
				Map<String, Object> curMap = i.next();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("borrowId", curMap.get("eid").toString());// 标id
				data.put("borrowTitle", curMap.get("borrow_title").toString());			
				data.put("annualInterestRate", curMap.get("annual_interest_rate").toString());// 年利率
				data.put("borrowSum", curMap.get("borrow_sum").toString());// 总金额
				// 剩余可投金额
				data.put("leftMoney", new BigDecimal(curMap.get("borrow_sum").toString()).subtract(new BigDecimal(curMap.get("tender_sum").toString())).toString());
				String timeLimit = curMap.get("borrow_time_limit").toString();
				String timeType = curMap.get("is_day").toString();
				if(timeType.equals(Borrow.IS_DAY_Y.toString())){
					data.put("borrowLimit", timeLimit+"天");
				}else{
					data.put("borrowLimit", timeLimit+"个月");
				}
				data.put("minMount", curMap.get("min_amount").toString());//最小投标金额
				data.put("percent", AppUtils.setPercentStard(curMap.get("tenderProgressRate").toString()));//百分比
				//小图标
				String bidKind = curMap.get("bid_kind").toString();
				data.put("bidKind",bidKind);
				dataList.add(data);
			}
			dto.addKeyValue(Constants.TOTAL_PAGE,page.getTotalPage()+"");
			dto.addKeyValue(Constants.PAGED_CURPAGE,result.get(Constants.PAGED_CURPAGE).toString());
			dto.addKeyValue(Constants.PAGED_NUM_PERPAGE,result.get(Constants.PAGED_NUM_PERPAGE).toString());
			dto.addKeyValue("dataList",dataList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("理财列表查询成功");
		}catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("理财列表查询失败");
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	/**
	 * 用户注册
	 * 
	 * @param	userAccount 用户名
	 * @param	userPassword 密码
	 * @param	userPhone 手机号
	 * @param	userConfirmPassword 确认密码
	 * @param   smsCode 手机验证码
	 * @param   recommendId:可选
	 */
	@RequestMapping("/appRegister.do")
	public void appRegister(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> param = this.getParametersO(request);
		ResponseDto dto = new ResponseDto();
		try {
			/*if (!param.containsKey("userAccount")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("用户名不能为空");
				return;
			}*/
			if (!param.containsKey("userPhone")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("手机号不能为空");
				return;
			}
			if (!isPhone(param.get("userPhone").toString())) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("手机号格式不正确");
				return;
			}

			if (!param.containsKey("userPassword")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("密码不能为空");
				return;
			}
			/*if (!param.containsKey("userConfirmPassword")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("确认密码不能为空");
				return;
			}*/
			if (!param.containsKey("smsCode")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("验证码不能为空");
				return;
			}
            String userPhone=param.get("userPhone").toString();
			/*String userAccount = param.get("userAccount").toString();
			// 校验用户名是否存在
			User existUser = userService.selectByUserUid(userAccount);
			if (null != existUser) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("用户名已存在");
				return;
			}
			// 验证是否有中文
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(userAccount);

			boolean matches = userAccount
					.matches("^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,25}$");
			if (matches == false) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("用户名格式不正确,请输入6-25位字符.英文,数字");
				return;
			}
			boolean result = m.find();
			if (result) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("用户名不能为中文,请输入6-15位字符.英文,数字");
				return;
			}*/
			User user1 = userService.selectByUserUid(param.get(
					"userPhone").toString());
			if (null != user1  ) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("手机号已存在");
				return;
			}
			/*if (!param.get("userPassword").equals(
					param.get("userConfirmPassword"))) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("两次密码不一致");
				return;
			}*/
			boolean flags=checkPhoneCode(request, response);
			if (flags == false) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("验证码错误");
				return;
			}
			DesEncrypt aesEncrypt = new DesEncrypt(
					Constants.DES_PRIVATE_ENCRYPT_KEY);
			String userPassword = DesUtil.decryptDES(StringUtils
					.trimToEmpty(param.get("userPassword").toString()),
					Constants.DES_PUBLIC_KEY_IOS_ANDROID);
			String checkpassword = MD5Utils.stringToMD5(aesEncrypt
					.encrypt(userPassword));

			String ip = this.getIpAddr(request);
			User user = new User();
			user.setTypeId(1);
			user.setUserAccount(userPhone);
			user.setUserPhone(userPhone);
			user.setUserPassword(checkpassword);
			user.setUserAddip(ip);
			user.setUserAddtime(new Date());
			user.setPhoneStatus(2);
			Map<String,String> map= SysCacheUtils.getConfigParams();
			//User recommendUser = userService.selectByUserUid(param.get("userPhone").toString());
			String recommendId=null;
			if(param.containsKey("recommendId"))
				recommendId =  param.get("recommendId").toString();
			if(map.get("recommend_status").equals(SysConfigParams.recommend_open)){
				// 邀请注册时设置邀请人Id，邀请奖励
				if (StringUtils.isNotBlank(recommendId)) {
					/*String inviteId = Base64Utils.decodeStr(inviteUserId);*/
					user.setInviteUserid(Integer.parseInt(recommendId));
					String inviteReward =map.get("recommend_InviteReward");
					if (CompareUtils.greaterThanZero(new BigDecimal(inviteReward))) {
						user.setInviteMoney(inviteReward);
					}
				}
			}
			boolean flag = userService.saveOrUpdate(user);
			if (flag == true) {
				/*if (param.containsKey("recommendId") && !"".equals((String)param.get("recommendId").toString().trim())) {
					Recommend recommend = new Recommend();
					String recommendId = (String) param.get("recommendId");
					recommendService.saveRecommend(recommend,BigDecimal.valueOf(Double.parseDouble(recommendUser.getInviteMoney())));
				}*/

				// 添加日志
				OperatorLog log = new OperatorLog();
				log.setCreateTime(new Date());
				log.setOperatorIp(ip);
				log.setOperatorTitle("前台用户" + userPhone + "注册");
				log.setOperatorCategory(OperatorLog.CATEGORY_LOGIN);
				log.setOperatorType(OperatorLog.TYPE_FRONT);
				log.setOperatorReturn("注册成功");
				log.setOperatorStatus(200);
				log.setOperatorParams(userPhone);
				log.setOperatorType(OperatorLog.TYPE_FRONT);
				operatorLogService.addFrontLog(log);

				userCreditService.addUserCredit(CreditType.PHONE, user.getId());
				creditLogService.addCreditLog(CreditType.PHONE, user.getId());
				dto.setCode(Status.SUCCESS.getName());
				dto.setMessage("注册成功");
			} else {
				dto.setCode(Status.ERROR.getName());
				dto.setMessage("注册失败");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.ERROR.getName());
			dto.setMessage("注册失败");

		} finally {
			JsonUtils.toObjectJson(response, dto);
		}

	}
	
	
    /**
     * 发送验证码
     * @param request
     * @param response
     */
	@RequestMapping("/appGetSms.do")
	public void sendAppSms(HttpServletRequest request,
			HttpServletResponse response){
		ResponseDto dto = new ResponseDto();
		String userPhone =request.getParameter("userPhone");
		String type=request.getParameter("type");
		try {
			//判断手机号是否为空
			if (null == userPhone || userPhone == "") {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("手机号码为空");
				return;
			}
			//判断手机号格式是否正确
			if(!isPhone(userPhone)){
				dto.setCode(Status.FAILD.getName());
				 dto.setMessage("手机号格式不正确");
				 return ;
				    }
			
			//发送短信
			int rand = new Random().nextInt(1000000);
			DesEncrypt desEnc = new DesEncrypt();
			String randEnc = desEnc.encrypt(rand + "");
			Cookie cookie = new Cookie("smsRand", randEnc);
			cookie.setMaxAge(5 * 60);
			response.addCookie(cookie);
			request.getSession().setAttribute("phonevalcode", rand + "");
			String content="您的验证码是："+rand+"。请不要把验证码泄露给其他人。";
			Date sendTime = new Date();
			MessageCenter center = new MessageCenter();
			center.setMessageAddress(userPhone);
			center.setMessageSendDatetime(sendTime);
			center.setMessageContent(content);
			center.setMessageSendIp(this.getIpAddr(request));
			//center.setReceiveUserId(userId);
			//判断发送验证码类型
			   if(type.equals("register")){
				   center.setMessageTitle(MessageCenter.APP_REGISTER);
				}
			   if(type.equals("findPassword")){
				   center.setMessageTitle(MessageCenter.APP_FINDPASSWORD);
			   }
			   if(type.equals("findPayPassword")){
				   center.setMessageTitle(MessageCenter.APP_FINDPAYPASSWORD);
			   }
			   if(type.equals("cash")){
				   center.setMessageTitle(MessageCenter.APP_CASH);
			   }
			center.setNoticeTypeId(MessageCenter.SMS);
			center.setSendUserId(Constants.ADMIN_USER_ID);
		    int result = SmsUtil.sendSms(userPhone,content);
		    OperatorLog log = new OperatorLog();
			 //log.setLogUserid(userId.toString());
			 log.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			 log.setOperatorIp(this.getIpAddr(request));
			 log.setOperatorType(OperatorLog.TYPE_FRONT);
			 log.setOperatorTitle("app短信");
		    if(result==0){
		    	dto.setCode(Status.SUCCESS.getName());
				dto.setMessage("发送验证码成功");
		    }else{
		    	log.setOperatorReturn("手机验证码短信发送失败，错误码为："+result);
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("发送验证码失败");
		    }
			operatorLogService.add(log);
			messageCenterService.addMsg(center);
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
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
	 * 
	 * @Title: appFindPassword
	 * @Description: 找回密码
	 * @param userPhone ：手机号 
	 * @param smsCode ： 手机验证码
	 * @return void  
	 * @throws
	 */
	@RequestMapping("/appFindPassword.do")
	public void appFindPassword(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> param = this.getParametersO(request);
		ResponseDto dto = new ResponseDto();
		try {
			if (!param.containsKey("userPhone")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("手机号不能为空");
				return;
			}
			if (!param.containsKey("smsCode")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("手机验证码不能为空");
				return;
			}
			String userPhone = param.get("userPhone").toString();
			String smsCode = param.get("smsCode").toString();
			// 判断手机验证码是否正确
			boolean flag = checkPhoneCode(request, response);
			if (flag == false) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("手机验证码错误");
				return;
			}
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userPhone", userPhone);
			// 判断当前用户是否存在
			User user = userService.selectByUserUid(userPhone);
			if (null == user) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("此用户不存在");
				return;
			}
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("用户查找成功");
			dto.addKeyValue("userPhone", userPhone);
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("用户查找失败");
			logger.error(e.getMessage());
			return;
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}

	/**
	 * 
	 * @Title: appResetPassword
	 * @Description: 找回密码
	 * @param userPhone：手机号 
	 * @param userPassword：新密码
	 * @param passwordr：确认密码
	 * @return void  
	 * @throws
	 */
	@RequestMapping("/appResetPassword.do")
	public void appResetPassword(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> param = this.getParametersO(request);
		ResponseDto dto = new ResponseDto();
		try {
			if (!param.containsKey("userPhone")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("手机号不能为空");
				return;
			}
			if (!param.containsKey("userPassword")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("新密码不能为空");
				return;
			}
			if (!param.containsKey("passwordr")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("确认新密码不能为空");
				return;
			}

			// 根据手机号，得到用户信息
			String userPhone = param.get("userPhone").toString();
			User user = userService.findUserByPhone(userPhone);
			// 存过来的密码都是加密过的，通过公钥得到原来的string
			DesEncrypt aesEncrypt = new DesEncrypt(
					Constants.DES_PRIVATE_ENCRYPT_KEY);
			String userPassword = DesUtil.decryptDES(StringUtils
					.trimToEmpty(param.get("userPassword").toString()),
					Constants.DES_PUBLIC_KEY_IOS_ANDROID);
			String userPasswordr = DesUtil.decryptDES(
					StringUtils.trimToEmpty(param.get("passwordr").toString()),
					Constants.DES_PUBLIC_KEY_IOS_ANDROID);
			// 判断两次输入的密码是否正确
			if (!userPassword.equals(userPasswordr)) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("两次密码不一致");
				return;
			}

		if (null != user) {
				// 再次加密后的秘密
				String checkpassword = MD5Utils.stringToMD5(aesEncrypt
						.encrypt(userPassword));
				user.setUserPassword(checkpassword);
				boolean flag = userService.saveOrUpdate(user);
				if(flag){
					dto.setCode(Status.SUCCESS.getName());
					dto.setMessage("找回密码成功");
					return ;
				}
			} else {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("不存在当前用户");
				return;
			}
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("修改用户密码失败");
			logger.error(e.getMessage());
			return;
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	/**
	 * 发送找回密码邮件
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/appSendEmail.do")
	public void reSendEmail(HttpServletRequest request, HttpServletResponse response,Model model){
		ResponseDto dto=new ResponseDto();
		dto.setCode(Status.FAILD.getName());
		Map<String, String> param = this.getParameters(request);
		try {
			if (!param.containsKey("userEmail")) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("邮箱不能为空");
				return;
			}
			String email = param.get("userEmail");
			Map<String, String> map = new HashMap<String, String>();
			map.put("userEmail", email);
			User user1=userService.checkUserByEmail(map);
			
			if (!isEmail(email)) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("邮箱格式不正确");
				return;
			}
			
		if (user1 != null && email.equals(user1.getUserEmail())) {
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("操作成功,请登录您的邮箱查看找回密码邮件");
			//发送邮件
			MessageCenter center = new MessageCenter();
			center.setSendUserId(Constants.ADMIN_USER_ID);
			center.setReceiveUserId(user1.getId());
			center.setMessageTitle("【"+SysCacheUtils.getSysConfig().getSysWebsitename()+"】用户"
					+ user1.getUserAccount() + "找回登录密码邮件");
			String id = MD5Utils.stringToMD5(user1.getId().toString());
			String sendEmailTime = System.currentTimeMillis()/ 1000 + "";
			String path = request.getContextPath()+"/web";
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
			SysLetterTemplates sysLetterTemplates = SysCacheUtils.getSysLetterTemplates();
			String mailContent = sysLetterTemplates
					.getSysPasswordMailinfo()
					.replace("#userAccount#", user1.getUserAccount())
					.replace("#url#",basePath
							+ "/foundPassword.do?send="
							+ sendEmailTime
							+ ","
							+ user1.getId()
							+ ","
							+ MD5Utils.stringToMD5(user1
							.getUserAccount()));
			center.setMessageContent("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>"+mailContent+"</body></html>");
			center.setMessageAddress(user1.getUserEmail());
			center.setMessageSendIp(this.getIpAddr(request));
			messageCenterService.sendEmail(center, null);
		} else {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("此邮箱不存在");
		  }
		}catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("系统异常");
			e.printStackTrace();
		}finally{
			JsonUtils.toObjectJson(response, dto);
		} 
	}
	

	/**
	 * 邮箱激活
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("appActivation.do")
	public String appActivation(HttpServletRequest request,HttpServletResponse response ,Model model)
			throws Exception {
		try {
			String[] values = request.getParameter("validateId").split(",");
			String validateId = values[0];
			String uid = values[1] ;
			String send = Base64Utils.decodeStr(values[2]);
			Long sendTime = Long.valueOf(send);
			User user = userService.getById(Integer.parseInt(uid));
			if (System.currentTimeMillis() - sendTime * 1000 > 1 * 24 * 60 * 60 * 1000) {
				request.setAttribute("code", "-1");
				request.setAttribute(Constants.MESSAGE, "激活码已过期。");
			}else if(user.getEmailStatus().equals(User.EMAIL_PASS)){
				request.setAttribute("code", "-1");
				request.setAttribute(Constants.MESSAGE, "邮箱已经激活，请勿重复操作。");
			}else if (!validateId.equals(MD5Utils.stringToMD5(uid))) {
				request.setAttribute("code", "-1");
				request.setAttribute(Constants.MESSAGE, "邮箱激活失败。");
				 
			}else{
				userCreditService.addUserCredit(CreditType.EMAIL, user.getId());
				creditLogService.addCreditLog(CreditType.EMAIL, user.getId());
				user.setEmailStatus(User.EMAIL_PASS);
				boolean flag=userService.saveOrUpdate(user);
				//资料完整度
				if(flag==true){
					User integralUser = this.userService.getById(user.getId());
					CreditType credit=this.creditTypeService.getCreditType(null, CreditType.EMAIL);
					String integral = integralUser.getUserIntegral();
					if(integral != null){
						Integer integral1 = Integer.parseInt(integral);
						Integer userIntegral = integral1+credit.getCreditScore();
						integralUser.setUserIntegral(userIntegral.toString());
					}else{
						integralUser.setUserIntegral(credit.getCreditScore().toString());
					}
					userService.updateByPrimaryKeySelective(integralUser);
					
					request.setAttribute("code", "0");
					request.setAttribute(Constants.MESSAGE, "恭喜您，邮箱激活成功！");
					/**
					 * 发送站内信
					 */
					MessageCenter center=new MessageCenter();
					center.setMessageSendDatetime(new Date());
					center.setMessageSendIp(this.getIpAddr(request));
					center.setMessageStatus(MessageCenter.STATUS_UNREAD);
					center.setReceiveUserId(user.getId());
					center.setSendUserId(Constants.ADMIN_USER_ID);
					center.setMessageContent(flag&&user.getEmailStatus()==User.EMAIL_PASS?"用户"+user.getUserAccount()+"，你的邮箱激活成功，添加积分"+credit.getCreditScore().toString()+"分。":"用户"+user.getUserAccount()+"，你的邮箱激活失败，请重新操作。");
					center.setMessageTitle("邮箱激活");
					center.setNoticeTypeId(MessageCenter.MESSAGE);
					this.messageCenterService.send(center, flag&&user.getEmailStatus()==User.EMAIL_PASS?Notice.EMAIL_YES:Notice.EMAIL_NO);
					/**
					 * 记录日志
					 */
					OperatorLog operatorLog = new OperatorLog();
					//operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
					operatorLog.setOperatorTitle("邮箱激活");
					operatorLog.setOperatorType(OperatorLog.TYPE_FRONT);
					operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
					operatorLog.setOperatorParams(user.toString());
					operatorLog.setOperatorReturn(flag&&user.getEmailStatus()==User.EMAIL_PASS?"用户"+user.getUserAccount()+",你的邮箱激活成功，添加积分"+credit.getCreditScore().toString()+"分。":"用户"+user.getUserAccount()+"，你的邮箱激活失败，请重新操作。");
					operatorLog.setOperatorStatus(flag&&user.getEmailStatus()==User.EMAIL_PASS?200:300);
					operatorLog.setLinkUrl(RequestUtils.getIpAddr());
					operatorLog.setOperatorIp(this.getIpAddr(request));
					operatorLogService.addAdminLog(operatorLog);
				}else{
					request.setAttribute("code", "-1");
					request.setAttribute(Constants.MESSAGE, "邮箱激活失败。");
				}
				model.addAttribute("userName", user.getUserAccount());
				request.getSession().setAttribute(Constants.APP_USER_SESSION,user);
			}
		} catch (Exception e) {
			request.setAttribute("code", "-1");
			request.setAttribute(Constants.MESSAGE, "邮箱激活失败。");
			e.printStackTrace();	
		}finally{
			return "registersuc";
		}
	}
	
	/**
	 * 
	* @Title: appGetVesion
	* @Description: 获取版本号的内容
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/appGetVesion.do")
	public void appGetVesion(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseDto dto = new ResponseDto();
		try {
			Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
			String versionNumber = sysConfigParamMap.get("appversion_number");
			String versionUpdate = sysConfigParamMap.get("appversion_update");
			String versionLink = sysConfigParamMap.get("appversion_link");
			String versionContent = sysConfigParamMap.get("appversion_content");
			String versionCode = sysConfigParamMap.get("appversion_code");
			dto.addKeyValue("versionNumber", versionNumber);
			dto.addKeyValue("versionUpdate", versionUpdate);
			dto.addKeyValue("versionLink", versionLink);
			dto.addKeyValue("versionContent", versionContent);
			dto.addKeyValue("versionCode", versionCode);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("获取版本号成功");
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("获取版本号失败");
			logger.error(e.getMessage());
			return;
		} finally {
			JsonUtils.toObjectJson(response, dto);
		}
	}

	
	
	/**
	 * 如果str为null，返回“”,否则返回str
	 * 
	 * @param str
	 * @return
	 */
	public String isNull(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}
	
	/**
	 * 检查phone是否是手机格式，返回true表示是，反之为否
	 * 
	 * @param phone
	 * @return
	 */
	public boolean isPhone(String phone) {
		phone = isNull(phone);
		Pattern regex = Pattern.compile("1[3|5|7|8|][0-9]{9}");

		Matcher matcher = regex.matcher(phone);
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	
	
	/**
	 * 检查email是否是邮箱格式，返回true表示是，反之为否
	 * 
	 * @param email
	 * @return
	 */
	public boolean isEmail(String email) {
		email = isNull(email);
		Pattern regex = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	
	public static void main(String[] args) {
		String content = "您的验证码是：1124。请不要把验证码泄露给其他人。";
		int begin = content.indexOf("：");
		int end = content.indexOf("。");
		System.out.println(content.substring(begin+1, end));
		
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
	
}
