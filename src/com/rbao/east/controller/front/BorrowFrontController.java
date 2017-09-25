package com.rbao.east.controller.front;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.Attach;
import com.rbao.east.entity.AttestationApply;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowRelated;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.PersonalMessage;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.entity.UserEvaluate;
import com.rbao.east.service.AttachService;
import com.rbao.east.service.AttestationApplyService;
import com.rbao.east.service.BorrowRelatedService;
import com.rbao.east.service.BorrowRepaymentService;
import com.rbao.east.service.BorrowRepossessedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTransferService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.PersonalMessageService;
import com.rbao.east.service.ReportService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserEvaluateService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.CompareUtils;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.SpringUtils;

/**
 * 前台查询标相关的信息 这个类不被session过滤掉
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/borrow")
public class BorrowFrontController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(BorrowFrontController.class);
	@Autowired
	private BorrowTenderService borrowTenderService;
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
	private BorrowTransferService borrowTransferService;
	@Autowired
	private BorrowRepossessedService borrowRepossessedService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private UserEvaluateService evaluateService;
	@Autowired
	private AttestationApplyService applyService;
	@Autowired
	private PersonalMessageService sersonalMessageService;
	@Autowired
	private SysConfigParamService sysConfigParamService;
	@Autowired
	private AttachService attachService;

	@RequestMapping("/showBorrows/{borrowEId}.do")
	public String getBorrowByStatusLists(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable("borrowEId") String borrowEId) {
		User user = this.loginFrontUser(request);
		if (user != null) {
			User tmpUser = userService.getById(user.getId());
			model.addAttribute("noSetPayPassword",
					StringUtils.isEmpty(tmpUser.getUserPaypassword()));
		}
		model.addAttribute("borrowEId", borrowEId);
		return "borrow/shwoBorrowInfoss";
	}

	@RequestMapping("/showBorrow/{borrowId}.do")
	public String getBorrowByStatusList(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable("borrowId") Integer borrowId) {
		User user = this.loginFrontUser(request);
		if (user != null) {
			User tmpUser = userService.getById(user.getId());
			model.addAttribute("noSetPayPassword",
					StringUtils.isEmpty(tmpUser.getUserPaypassword()));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		String agreementPath = "";
		Integer agreementStatus = 0;
		// 解密 加密后的borrowEId
//		DesEncrypt enc = new DesEncrypt();
//		Integer borrowId = 0;
//		try {
//			borrowId = Integer.parseInt(enc.decrypt(borrowEId));
//		} catch (NumberFormatException e1) {
//			e1.printStackTrace();
//			SpringUtils.renderText(response, "解析路径出错");
//			return null;
//		}
		
		try {
			Borrow borrow = borrowQueryService.getBorrowById(borrowId);
			User borrowUser = userService.getById(borrow.getUserId());
			Integer[] borrowStatus = { Borrow.STATUS_REPLY_SUCCESS };
			Integer borrowSuccessSum = borrowQueryService.getBorrowCount(
					borrow.getUserId(), borrowStatus);
			PersonalMessage personalMessage=sersonalMessageService.getByUserId(borrow.getUserId());
			
			
			model.addAttribute("personalMessage", personalMessage);
			model.addAttribute("borrowSuccessSum", borrowSuccessSum);
			borrowStatus = new Integer[] { Borrow.STATUS_FLOW_STANDARD };
			Integer borrowFlowStandard = borrowQueryService.getBorrowCount(
					borrow.getUserId(), borrowStatus);
			model.addAttribute("borrowFlowStandard", borrowFlowStandard);
			if (null != user) {
				result.put("userId", user.getId());
				result.put("borrowId", borrowId);
				agreementPath=borrowTenderService.getAgreementPathByBorrowIdAndUserId(result);
				if(null!=agreementPath){
					agreementStatus = 1;
				}
				
			}
			
			result.clear();
			
			Integer tenderUsersCount=borrowTenderService.getTenderUsers(borrowId);
			
			
			result.put("userId", borrow.getUserId());
			result.put("status", BorrowRepayment.REPAYMENT_STATUS_REPAYING);
			Integer repayingCount = borrowRepaymentService
					.getRepayingCount(result);
			model.addAttribute("repayingCount", repayingCount);

			result.put("status", BorrowRepayment.REPAYMENT_STATUS_SUCCESS);
			Integer repaySuccessCount = borrowRepaymentService
					.getRepayingCount(result);
			model.addAttribute("repaySuccessCount", repaySuccessCount);

			result.put("type", "carry");
			Integer repayCarrySuccessCount = borrowRepaymentService
					.getRepayingCount(result);
			model.addAttribute("repayCarrySuccessCount", repayCarrySuccessCount);

			result.put("type", "forward");
			Integer repayForwardSuccessCount = borrowRepaymentService
					.getRepayingCount(result);
			model.addAttribute("repayForwardSuccessCount",
					repayForwardSuccessCount);

			result.put("type", "forwardOverdue");
			Integer repayForwardOverdueCount = borrowRepaymentService
					.getRepayingCount(result);
			model.addAttribute("repayForwardOverdueCount",
					repayForwardOverdueCount);

			result.put("type", "carryOverdue");
			Integer repayCarryOverdueCount = borrowRepaymentService
					.getRepayingCount(result);
			model.addAttribute("repayCarryOverdueCount", repayCarryOverdueCount);

			result.remove("type");
			result.put("status", BorrowRepayment.REPAYMENT_STATUS_OVERDUE);
			Integer repayOverdueCount = borrowRepaymentService
					.getRepayingCount(result);
			model.addAttribute("repayOverdueCount", repayOverdueCount);

			result.clear();
			result.put("userId", borrow.getUserId());
			result.put("repossessedStatus",
					BorrowRepossessed.STATUS_REPOSSESSING);
			Map<String, Object> repossessedMap = (Map<String, Object>) borrowRepossessedService
					.getPrepareAmountOrRepossessedInterestByUserId(borrow
							.getUserId());
			if (null != repossessedMap) {
				if (repossessedMap.containsKey("repossessedInterest")) {
					model.addAttribute("repossessedInterest", new BigDecimal(
							repossessedMap.get("repossessedInterest")
									.toString()));
				} else {
					model.addAttribute("repossessedInterest", new BigDecimal(0));
				}
				if (repossessedMap.containsKey("prepareAmount")) {
					model.addAttribute("prepareAmount", new BigDecimal(
							repossessedMap.get("prepareAmount").toString()));
				} else {
					model.addAttribute("prepareAmount", new BigDecimal(0));
				}
			} else {
				model.addAttribute("prepareAmount", new BigDecimal(0));
				model.addAttribute("repossessedInterest", new BigDecimal(0));
			}

			UserEvaluate userEvaluate = evaluateService
					.getUserEvaluateByUserId(borrow.getUserId());

			model.addAttribute("userEvaluate", userEvaluate);

			result.put("tradeCode", AccountLog.TRADE_CODE_TENDER_SUCC);
			String tendSum = reportService.getSumByUserId(result);
			model.addAttribute("tendSum", tendSum);

			result.put("tradeCode", AccountLog.TRADE_CODE_REPAY);
			String repaySum = reportService.getSumByUserId(result);
			model.addAttribute("repaySum", repaySum);

			String repossessedSum = reportService
					.getRepossessedSumByUserId(result);
			model.addAttribute("repossessedSum", repossessedSum);

			AttestationApply attestationApply = new AttestationApply();
			attestationApply.setUserId(borrow.getUserId());
			attestationApply.setAttestationStatus(1);
			List<AttestationApply> attestationApplyList = applyService
					.listAttestationApply(attestationApply);
			model.addAttribute("attestationApplyList", attestationApplyList);
			model.addAttribute("attestationApplyListSize",
					attestationApplyList.size());

			
			Attach attach=new Attach();
			attach.setAttachTablename(Attach.TABLE_NAME_BORROW);
			attach.setAttachRelateId(borrow.getId());
			List<Attach> att=this.attachService.selectByAttach(attach);
			if(att.size()>0){
				model.addAttribute("att", att);
			}
			
			List list = borrowQueryService.getborrowAccount(borrow.getUserId());

			Map<String, String> borrowSum = (Map<String, String>) list.get(0);
			if (borrowSum.containsKey("borrowSum")) {
				model.addAttribute("borrowSum", borrowSum.get("borrowSum"));
			} else {
				model.addAttribute("borrowSum", "0.00");
			}

			List<BorrowRepayment> repaymentList = borrowRepaymentService
					.getRepaymentByBorrowId(borrowId);
			UserAccount borrowUserAccount = userAccountService
					.getByUserId(borrow.getUserId());
			BorrowType bType = borrowTypeService.getBorrowTypeById(borrow
					.getBidKind());
			BigDecimal percentage = borrow.getTenderSum()
					.divide(borrow.getBorrowSum(), 4, BigDecimal.ROUND_DOWN)
					.multiply(new BigDecimal(100));
			String[] annualInterestRateStr = borrow.getAnnualInterestRate()
					.toString().split("\\.");
			System.out.println(annualInterestRateStr[0]);
			if (user != null) {
				UserAccount userAccount = userAccountService.getByUserId(user
						.getId());
				model.addAttribute("userAccount", userAccount);
			}
			//borrowContent不需要去掉html标签，理财计划简介对应这个字段的值，项目详情布局更改
			//String borrowContent = HtmlUtil.getTextFromHtml(borrow.getBorrowContent());
			model.addAttribute("borrowContent", borrow.getBorrowContent());
			model.addAttribute("borrow", borrow);
			model.addAttribute("borrowType", bType);
			model.addAttribute("borrowTenderCount", tenderUsersCount);
			model.addAttribute("agreementStatus", agreementStatus);
			model.addAttribute("agreementPath", agreementPath);
			model.addAttribute("borrowUser", borrowUser);
			model.addAttribute("repaymentList", repaymentList);
			model.addAttribute("repaymentListSize", repaymentList.size());
			model.addAttribute("borrowUserAccount", borrowUserAccount);
			model.addAttribute("SignBorrowId",
					new DesEncrypt().encrypt(borrow.getId().toString()));// 加密串
			model.addAttribute("nowDate", new Date().getTime() + "");
			model.addAttribute("percentage",
					percentage.setScale(2, BigDecimal.ROUND_DOWN));
			model.addAttribute("annualInterestRateStr", annualInterestRateStr);
			model.addAttribute("allowTenderTime", borrow.getAllowTenderTime()
					.getTime() + "");
			model.addAttribute("publishTime", borrow.getPublishDatetime()
					.getTime() + "");
			model.addAttribute(
					"Published",
					CompareUtils.greaterThan(new Date(),
							borrow.getPublishDatetime()));
			model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
			//判断抵扣金开关设置
			Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
			String deductionSwitchValue = sysConfigParamMap.get("deductionMoney_switch");
			String deductionMoneyPercent = sysConfigParamMap.get("deductionMoney_percent");
			model.addAttribute("deductionSwitchValue", deductionSwitchValue);
			model.addAttribute("deductionMoneyPercent", deductionMoneyPercent);
			// 查询是否是第一次购买
			result.put("userId", user.getId());
			model.addAttribute("isFirst",
						borrowTenderService.selectFirstBuy(result));
			//活动开关
			String activitySwitch = sysConfigParamMap.get("activity_switch");
			model.addAttribute("activitySwitch", activitySwitch);
			
			
		} catch (Exception e) {
			
		}
		return "borrow/shwoBorrowInfo";
	}

	@RequestMapping("/showBorrowStatusInfoPage.do")
	public String showBorrowTypeInfoPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String[] codes = { "LI", "ZHUAN", "MIAO", "JING", "XING" };
		List<BorrowType> borrowTypes = borrowTypeService.getByTypeCodes(codes);
		model.addAttribute("borrowTypes", borrowTypes);
		return "borrow/showBorrowStatusInfo";
	}

	@RequestMapping("/showBorrowTenderInfoPage.do")
	public String showBorrowTenderInfoPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String[] codes = { "LI", "ZHUAN", "MIAO", "JING", "XING" };
		List<BorrowType> borrowTypes = borrowTypeService.getByTypeCodes(codes);
		model.addAttribute("borrowTypes", borrowTypes);
		return "borrow/showBorrowTenderInfo";
	}

	/**
	 * 投标页面下面的投资记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/shwoBorrowTenderInfoByPage.do")
	public void getBorrowByStatusList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		PageModel page = borrowTenderService.shwoBorrowTenderInfoByPage(param);
		SpringUtils.renderJson(response, page);
	}

	@RequestMapping("/showBorrowStatusInfoPageByParam.do")
	public void showBorrowStatusInfoPageByParam(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		Map<String, Object> result = new HashMap<String, Object>();
		List<Integer> list = new ArrayList<Integer>();
		list.add(Borrow.STATUS_FIRSTAUDIT_YES);
		list.add(Borrow.STATUS_REPLYING);
		list.add(Borrow.STATUS_FULL);
		list.add(Borrow.STATUS_REVIEW_YES);
		list.add(Borrow.STATUS_REPLY_SUCCESS);
		result.put("list", list);
		result.put("orderType", 4);
		result.putAll(param);
		if (!result.containsKey(Constants.PAGED_CURPAGE)) {
			result.put(Constants.PAGED_CURPAGE, 1);
		}
		//标的种类
		if (result.containsKey("borrowtypeArrays")) {
			String[] borrowtypeArrays = result.get("borrowtypeArrays").toString()
					.split(",");
			if (!borrowtypeArrays[0].equals("all")) {
				result.put("borrowtypeArrays", borrowtypeArrays);
			} else {
				result.remove("borrowtypeArrays");
			}
		}
		//状态
		if (result.containsKey("statuArrays")) {
			String[] statuArrays = result.get("statuArrays")
					.toString().split(",");
			if (!statuArrays[0].equals("all") && !statuArrays[0].equals("1")) {
				result.remove("list");
				result.put("statuArrays", statuArrays);
				System.out.println(statuArrays[0]);
			} else if(statuArrays[0].equals("1")){
				result.remove("list");
				statuArrays[0] = "2";
				result.put("isPrepare", "isPrepare");
				result.put("statuArrays", statuArrays);
			}else {
				result.remove("statuArrays");
			}
		}
		//利率
		if (result.containsKey("rateArrays")) {
			String[] rateArrays = result.get("rateArrays").toString()
					.split(",");
			if (!rateArrays[0].equals("all")) {
				result.put("rateArrays", rateArrays);
			} else {
				result.remove("rateArrays");
			}
		}
		//期限
		if(result.containsKey("dateArrays")){
			String[] dateArrays=result.get("dateArrays").toString().split(",");
			if(!dateArrays[0].equals("all")){
				result.put("dateArrays", dateArrays);
			}else{
				result.remove("dateArrays");
			}
		}
		
		//还款方式  
		if(result.containsKey("securitymodeArrays")){
			String[] securitymodeArrays=result.get("securitymodeArrays").toString().split(",");
			if(!securitymodeArrays[0].equals("all")){
				result.put("securitymodeArrays", securitymodeArrays);
			}else{
				result.remove("securitymodeArrays");
			}
		}
		
		//result.put("isIndex", "1");
		PageModel page = borrowQueryService
				.showBorrowStatusInfoPageByParam(result);
		System.out.println(page.getList().size());
		SpringUtils.renderJson(response, page);
	}

	@RequestMapping("/toInvest.do")
	public String toInvest(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		model.addAttribute("nowDate", new Date().getTime());
		return "borrow/invest";
	}

	@RequestMapping("/toBorrowTransList.do")
	public String toBorrowTransList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		model.addAttribute("nowDate", new Date().getTime());
		return "borrow/showBorrowTransList";
	}

	@RequestMapping("/toBorrowInfos.do")
	public String toBorrowInfos(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String[] codes = { "LI", "ZHUAN", "MIAO", "JING", "XING" };
		List<BorrowType> borrowTypes = borrowTypeService.getByTypeCodes(codes);
		model.addAttribute("borrowTypes", borrowTypes);
		model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
		model.addAttribute("nowDate", new Date().getTime());
		return "borrow/shwoBorrowInfos";
	}

	@RequestMapping("/getDingHuoTongJson.do")
	public void getDingHuoTongJson(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		PageModel pageModel = borrowRelatedService.getDHTPage(param);
		SpringUtils.renderJson(response, pageModel);
	}

	@RequestMapping("/dingHuoTong.do")
	public String toDingCunBao(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		BorrowRelated borrowRelated = borrowRelatedService.findRecentlyEntity();
		if (null != borrowRelated) {
			// 加密
			DesEncrypt enc = new DesEncrypt();
			String borrowRelatedEId = enc.encrypt(String.valueOf(borrowRelated
					.getId()));
			String borrowoneEId = enc.encrypt(String.valueOf(borrowRelated
					.getBorrowoneId()));
			String borrowtwoEId = enc.encrypt(String.valueOf(borrowRelated
					.getBorrowtwoId()));
			String borrowthreeEId = enc.encrypt(String.valueOf(borrowRelated
					.getBorrowthreeId()));

			if (!CompareUtils.greaterThan(new Date(), borrowRelated
					.getBorrowone().getAllowTenderTime())
					&& CompareUtils.greaterEquals(new Date(), borrowRelated
							.getBorrowone().getPublishDatetime())) {
				model.addAttribute("oneType", 1);
			} else {
				if (CompareUtils.greaterThan(new Date(), borrowRelated
						.getBorrowone().getAllowTenderTime())) {
					model.addAttribute("oneType", 2);
				} else {
					model.addAttribute("oneType", 0);
				}
			}

			if (!CompareUtils.greaterThan(new Date(), borrowRelated
					.getBorrowtwo().getAllowTenderTime())
					&& CompareUtils.greaterEquals(new Date(), borrowRelated
							.getBorrowtwo().getPublishDatetime())) {
				model.addAttribute("twoType", 1);
			} else {
				if (CompareUtils.greaterThan(new Date(), borrowRelated
						.getBorrowtwo().getAllowTenderTime())) {
					model.addAttribute("twoType", 2);
				} else {
					model.addAttribute("twoType", 0);
				}
			}
			if (!CompareUtils.greaterThan(new Date(), borrowRelated
					.getBorrowthree().getAllowTenderTime())
					&& CompareUtils.greaterEquals(new Date(), borrowRelated
							.getBorrowthree().getPublishDatetime())) {
				model.addAttribute("threeType", 1);
			} else {
				if (CompareUtils.greaterThan(new Date(), borrowRelated
						.getBorrowthree().getAllowTenderTime())) {
					model.addAttribute("threeType", 2);
				} else {
					model.addAttribute("threeType", 0);
				}
			}

			model.addAttribute("borrowRelated", borrowRelated);

			model.addAttribute("allowTenderTime", borrowRelated
					.getRelatedPublishTime().getTime());
			model.addAttribute("nowDate", new Date().getTime());
			model.addAttribute("borrowRelatedEId", borrowRelatedEId);
			model.addAttribute("borrowoneEId", borrowoneEId);
			model.addAttribute("borrowtwoEId", borrowtwoEId);
			model.addAttribute("borrowthreeEId", borrowthreeEId);

		}
		return "borrow/dingHuoTong";
	}

	@RequestMapping("/toCalculator.do")
	public String toCalculator(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "borrow/calculator";
	}

	@RequestMapping("/toDingHuoTongInfo/{borrowEId}/{borrowRelatedEId}.do")
	public String toDingHuoTongInfo(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable("borrowEId") String borrowEId,
			@PathVariable("borrowRelatedEId") String borrowRelatedEId) {
		User user = this.loginFrontUser(request);
		String agreementPath = "";
		Integer agreementStatus = 0;
		// Map<String, String> param = getParameters(request);
		// 解密
		DesEncrypt enc = new DesEncrypt();
		Integer borrowId = Integer.parseInt(enc.decrypt(borrowEId));
		Integer borrowRelatedId = Integer.parseInt(enc
				.decrypt(borrowRelatedEId));
		try {
			// if(param.containsKey("borrowId")&&param.containsKey("borrowRelatedId")){
			List<BorrowTender> borrowTender = borrowTenderService
					.selectByBorrowId(borrowId);
			Borrow borrow = borrowQueryService.getBorrowById(borrowId);
			BorrowRelated borrowRelated = borrowRelatedService
					.selectByPrimaryKey(borrowRelatedId);
			BigDecimal percentage = borrow.getTenderSum()
					.divide(borrow.getBorrowSum(), 2, BigDecimal.ROUND_DOWN)
					.multiply(new BigDecimal(100));
			String[] annualInterestRateStr = borrow.getAnnualInterestRate()
					.toString().split("\\.");
			System.out.println(annualInterestRateStr[0]);
			if (user != null) {
				if (StringUtils.isEmpty(user.getUserPaypassword())) {
					model.addAttribute("userPaypassword", 1);
				} else {
					model.addAttribute("userPaypassword", 0);
				}
				UserAccount userAccount = userAccountService.getByUserId(user
						.getId());
				model.addAttribute("userAccount", userAccount);
				for (BorrowTender tender : borrowTender) {
					if (tender.getUserId().equals(user.getId())) {
						if (tender.getTenderStatus().equals(
								BorrowTender.STATUS_REPAYING)
								|| tender.getTenderStatus().equals(
										BorrowTender.STATUS_REPAYED)
								|| tender.getTenderStatus().equals(
										BorrowTender.STATUS_OVERDUE))
							agreementStatus = 1;
						agreementPath = tender.getAgreementPath();
						break;
					}
				}
			}
			model.addAttribute("agreementStatus", agreementStatus);
			model.addAttribute("agreementPath", agreementPath);
			model.addAttribute("borrow", borrow);
			model.addAttribute("borrowRelated", borrowRelated);
			model.addAttribute("nowDate", new Date().getTime() + "");
			model.addAttribute("SignBorrowId",
					new DesEncrypt().encrypt(borrow.getId().toString()));// 加密串
			model.addAttribute("percentage",
					percentage.setScale(2, BigDecimal.ROUND_DOWN));
			model.addAttribute("annualInterestRateStr", annualInterestRateStr);
			model.addAttribute("allowTenderTime", borrowRelated
					.getRelatedAllowTenderTime().getTime() + "");
			model.addAttribute("publishTime", borrowRelated
					.getRelatedPublishTime().getTime() + "");
			model.addAttribute(
					"Published",
					CompareUtils.greaterThan(new Date(),
							borrowRelated.getRelatedPublishTime()));

			model.addAttribute("publickey", Constants.DES_PUBLIC_ENCRYPT_KEY);
			// }
		} catch (Exception e) {
			
		}
		return "borrow/shwoDingHuoTongInfo";
	}

	//得到债券转让的详细内容
	@RequestMapping("/getBorrowTransList.do")
	public void getBorrowTransList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		Map<String, Object> result = new HashMap<String, Object>();
		//标的种类
		result.put("orderType", 6);
		result.put("checkNum", 1);
		result.putAll(param);
		if (!result.containsKey(Constants.PAGED_CURPAGE)) {
			result.put(Constants.PAGED_CURPAGE, 1);
		}
		if (result.containsKey("borrowtypeArrays")) {
			String[] borrowtypeArrays = result.get("borrowtypeArrays").toString()
					.split(",");
			if (!borrowtypeArrays[0].equals("all")) {
				result.put("borrowtypeArrays", borrowtypeArrays);
			} else {
				result.remove("borrowtypeArrays");
			}
		}
		//利率
		if (result.containsKey("rateArrays")) {
			String[] rateArrays = result.get("rateArrays").toString()
					.split(",");
			if (!rateArrays[0].equals("all")) {
				result.put("rateArrays", rateArrays);
			} else {
				result.remove("rateArrays");
			}
		}
		//期限
		if(result.containsKey("dateArrays")){
			String[] dateArrays=result.get("dateArrays").toString().split(",");
			if(!dateArrays[0].equals("all")){
				result.put("dateArrays", dateArrays);
			}else{
				result.remove("dateArrays");
			}
		}
		
		//还款方式  
		if(result.containsKey("securitymodeArrays")){
			String[] securitymodeArrays=result.get("securitymodeArrays").toString().split(",");
			System.out.println(securitymodeArrays[0]);
			if(!securitymodeArrays[0].equals("all")){
				result.put("securitymodeArrays", securitymodeArrays);
			}else{
				result.remove("securitymodeArrays");
			}
		}
				
		PageModel page = borrowTransferService.selectBorrowTransListData(result);
		System.out.println(page.getTotalRecord());
		SpringUtils.renderJson(response, page);
	}

	/**
	 * 前台发标类别
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("borrowType.do")
	public String borrow(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			List<BorrowType> borrowTypeList = borrowTypeService
					.getBorrowsTypeByParam(1, null, BorrowType.STATUS_ABLE);
			model.addAttribute("borrowTypeList", borrowTypeList);
		} catch (Exception e) {
			logger.info("前台发标结束出错=====" + e.toString());
		}
		return "borrow/borrowTypeList";
	}
}
