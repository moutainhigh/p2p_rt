package com.rbao.east.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.dao.BorrowDao;
import com.rbao.east.dao.BorrowRelatedDao;
import com.rbao.east.dao.UserAccountDao;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.Attach;
import com.rbao.east.entity.AutotenderRules;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowArrays;
import com.rbao.east.entity.BorrowRelated;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.CreditType;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysConfig;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.AccountLogService;
import com.rbao.east.service.AttachService;
import com.rbao.east.service.AutotenderRulesService;
import com.rbao.east.service.BorrowRelatedService;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.BorrowTypeHandleService;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.CreditLogService;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.OperatorLogService;
import com.rbao.east.service.SysConfigService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserCreditService;
import com.rbao.east.thread.CreateAgreeMentThread;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;



@Service
@Transactional
public class BorrowServiceImpl implements BorrowService {

	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	BorrowDao borrowDao;
	@Autowired
	private BorrowRelatedDao borrowRelatedDao;
	@Autowired
	private UserAccountDao accountDao;
	@Autowired
	private BorrowTenderService borrowTenderService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private AccountLogService accountLogService;
	
	@Autowired
	private OperatorLogService operatorLogService;
	@Autowired
	private BorrowTypeService borrowTypeService;
	
	@Autowired
	private BorrowRelatedService borrowRelatedService;
	
	@Autowired
	private MessageCenterService messageCenterService;
	
	@Autowired
	private UserCreditService userCreditService;
	
	@Autowired
	private CreditLogService creditLogService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private AutotenderRulesService autotenderRulesService;
	@Autowired
	private AttachService attachService;

	
	
	@Override
	public int getBorrowCount(Integer userId, Integer[] borrowStatus) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("inStatus", borrowStatus);
		return borrowDao.getTotalCount("selectCountByStatus", paramsMap);
	}

	@Override
	public PageModel getBorrowsByStatus(Map<String, String> paramsMap) {
		return borrowDao.getPage("selectBorrowsByStatus", "selectCountBorrowsByStatus", paramsMap);
	}
	
	@Override
	public Borrow getBorrowById(Integer id) {
		return borrowDao.selectByPrimaryKey(id);
	}

	@Override
	public PageModel getPaymentBorrows(Map<String, String> paramsMap) {
		return borrowDao.getPage("selectRepaymentBorrows", "selectCountRepaymentBorrows", paramsMap);
	}
	
	
	@Override
	public List<Map<String, Object>> toRepayExcel(Map<String, String> paramsMap){
		return borrowDao.selects("selectRepaymentBorrows", paramsMap);
	}
	

	@Override
	public boolean saveBorrow(Borrow borrow) {
		return borrowDao.saveOrUpdate(borrow);
	}

	@Override
	public boolean updateBorrow(Borrow borrow) {
		return borrowDao.updateByPrimaryKeySelective(borrow);
	}

	public boolean updateBorrowForAttach(Borrow borrow, String[] attachTitle,
			String[] attachPath, String[] attachRealTitle,
			String[] attachFileType, Integer[] attachSequence) {
		boolean flag = false;
		try {
			flag = borrowDao.updateByPrimaryKeySelective(borrow);
			if (flag) {
				// 保存附件
				List<Attach> attachs = new ArrayList<Attach>();
				if (attachRealTitle != null) {
					for (int i = 0; i < attachRealTitle.length; i++) {
						Attach newAttach = new Attach();
						newAttach.setAttachSequence(attachSequence[i]);
						newAttach.setAttachTitle(attachTitle[i]);
						newAttach.setAttachRealTitle(attachRealTitle[i]);
						newAttach.setAttachPath(attachPath[i]);
						newAttach.setAttachFileType(attachFileType[i]);
						attachs.add(newAttach);
					}
					attachService.save(attachs, borrow.getId(),
							Attach.TABLE_NAME_BORROW);
				} else {
					/*attachService.save(attachs, borrow.getId(),
							Attach.TABLE_NAME_BORROW);*/
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("运行时出错！");
		}
		return flag;
	}

	@Override
	public boolean updateSecondCheckBorrow(Map<String, Object> param) {
		Borrow borrow = borrowDao.selectEntity("selectByPrimaryKeyForUpdate",
				Integer.parseInt(param.get("borrowId").toString()));

		boolean bool = false;
		try {
			if (!Borrow.STATUS_FULL.equals(borrow.getBorrowStatus())) {
				throw new RuntimeException("当前标不允许复审操作。");
			}
			User user = (User) param.get("user");
			if (null != user) {

				borrow.setId(Integer.parseInt(param.get("borrowId").toString()));
				if (param.containsKey("verifyReviewRemark")) {
					borrow.setVerifyReviewRemark(param
							.get("verifyReviewRemark").toString());
				}
				borrow.setVerifyReviewUser(user.getId());
				borrow.setVerifyReviewTime(new Date());
				borrow.setBorrowStatus(Borrow.STATUS_REVIEW_NO);
				bool = borrowDao.updateByPrimaryKeySelective(borrow);
				if (bool) {
					Borrow borrows = (Borrow) param.get("borrow");
					bool = this.returnedMoneyByBorrowTenderList(borrows);
				} else {
					throw new RuntimeException("复审修改失败！！！");
				}
			} else {
				throw new RuntimeException("您当前尚未登录！请登录后再操作！！！");
			}
		} catch (Exception e) {
			bool = false;
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return bool;
	}
	
	
	//复审失败，回款
	public boolean returnedMoneyByBorrowTenderList(Borrow borrow){
		boolean bool=false;
		try {
			List<BorrowTender> BorrowTenderList= borrowTenderService.selectByBorrowId(borrow.getId());
			if(BorrowTenderList.size() == 0){
				return true;
			}
			for (BorrowTender borrowTender : BorrowTenderList) {
				boolean isbool=false;
				UserAccount userAccount=accountDao.selectByUserIdForUpdate(borrowTender.getUserId());
				if(null!=userAccount){
					BigDecimal availableMoney=userAccount.getAvailableMoney().add(borrowTender.getEffectiveAmount()).subtract(borrowTender.getDeductionMoney()); 
					BigDecimal unavailableMoney=userAccount.getUnavailableMoney().subtract(borrowTender.getEffectiveAmount()).add(borrowTender.getDeductionMoney());
					userAccount.setAvailableMoney(availableMoney);
					userAccount.setUnavailableMoney(unavailableMoney);
					userAccount.setDeductionMoney(userAccount.getDeductionMoney().add(borrowTender.getDeductionMoney()));
					isbool=userAccountService.updateByPrimaryKeySelective(userAccount);
					if(isbool){
						borrowTender.setTenderStatus(BorrowTender.STATUS_TENDER_FAILD);
						isbool=borrowTenderService.updateByBorrowTender(borrowTender);
						if(isbool){
							//添加日志数据
							bool=accountLogService.add(userAccount, AccountLog.TRADE_CODE_RETURN_MONEY, borrowTender.getTenderAmount()
									,new BigDecimal(0), borrow.getUserId(), 
									"标["+borrow.getBorrowTitle()+"]投标失败，解除冻结款["+borrowTender.getTenderAmount()+"]元,其中包括抵扣金["+borrowTender.getDeductionMoney()+"]元。",
									RequestUtils.getIpAddr());
							if(bool){
								//推送消息
								MessageCenter center = new MessageCenter();
								center.setMessageContent("标["+borrow.getBorrowTitle()+"]投标失败，解除冻结款["+borrowTender.getTenderAmount()+"]元,其中包括抵扣金["+borrowTender.getDeductionMoney()+"]元。");
								center.setMessageSendIp(RequestUtils.getIpAddr());
								center.setReceiveUserId(borrow.getUserId());
								center.setMessageTitle("投标失败,解除冻结款");
								center.setSendUserId(Constants.ADMIN_USER_ID);
								messageCenterService.send(center, Notice.BORROW_END);
								
								
								if(!borrow.getBorrowStatus().equals(Borrow.STATUS_FLOW_STANDARD)){
									//添加积分
									userCreditService.addUserCredit(CreditType.BORROW_END,borrow.getUserId(), Constants.ADMIN_USER_ID);
									creditLogService.addCreditLog(CreditType.BORROW_END,borrow.getUserId(), Constants.ADMIN_USER_ID);
								}
							}else{
								throw new RuntimeException("标添加日志数据失败");
							}
						}else{
							throw new RuntimeException("修改投标详细状态失败");
						}
					}else{
						throw new RuntimeException("修改用户回款金额失败");
					}
				}else{
					throw new RuntimeException("用户资金信息为空");
				}
				}
			
		} catch (Exception e) {
			bool=false;
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return bool;
	}

	
	

	@Override
	public List<Borrow> getAllBorrowsByStatus(Map<String, String> params) {
		return borrowDao.select("selectBorrowsByStatus", params);
	}

	@Override
	public List<Borrow> getBorrowListByEntity(Borrow borrow) {
		return borrowDao.select("selectBorrowsByEntity", borrow);
	}

	@Override
	public void returnedMoneyForFlowStandardByBorrowTenderList(Borrow borrow) {
		try {			
					borrow.setBorrowStatus(Borrow.STATUS_FLOW_STANDARD);
					boolean bool=this.returnedMoneyByBorrowTenderList(borrow);
					if(bool){	
						bool=this.updateBorrow(borrow);
						if(!bool){
							throw new RuntimeException("修改标状态失败！");
						}
					}else{
						throw new RuntimeException("流标回款失败！！！");
					}
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	@Override
	public PageModel selectAgreementPath(Map<String, String> params) {
		return borrowDao.getPage("selectAgreementPath", "selectAgreementPathCount", params);
	}

	@Override
	public PageModel showBorrowStatusInfoPageByParam(Map<String, Object> param) {
		PageModel pageModel=new  PageModel(Integer.parseInt(param.get(Constants.PAGED_CURPAGE).toString()));  //设置当前页
		if(param.containsKey(Constants.PAGED_NUM_PERPAGE)){
			pageModel.setPageSize(Integer.parseInt(param.get(Constants.PAGED_NUM_PERPAGE).toString()));
		}
		param.put("tenderStatus", Borrow.STATUS_FIRSTAUDIT_YES);
		PageModel model= borrowDao.getPage("showBorrowStatusInfoPageByParam","showBorrowStatusInfoPageCountByParam", param,pageModel);
		new DesEncrypt().encryptInList(model.getList(),new String[]{"id"}); //id加密
		return model;
	}


	@Override
	public boolean savefixedCurrentLinkAccount(Map<String, Object> param) {
		boolean bool=false;
		try {
			User user=(User) param.get("user");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			BorrowArrays borrowArrays=(BorrowArrays) param.get("borrowArrays");
			if(borrowArrays==null){
				throw new RuntimeException("数据传入错误");
			}
			Date validTime=format.parse(param.get("relatedPublishTime").toString());
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(validTime);
			rightNow.add(Calendar.DAY_OF_YEAR, Integer.parseInt(param.get("validTime").toString()));
			BorrowType borrowType = borrowTypeService.getBorrowTypeById(Integer.parseInt(param.get("bidKind").toString()));
			//获取标种对应的service实现类
			BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class, borrowType.getDealService());
			ServiceResult result = new ServiceResult();
			Borrow borrows[]=borrowArrays.getBorrows();
			Integer id[]=new Integer[3];
			for (int i=0;i<borrows.length;i++) {
				Borrow borrow=borrows[i];
				borrow.setUserId(Constants.PLATFORM_VOUCH_USER_ID);
				borrow.setRepaymentStyle(Borrow.REPAYMENT_STYLE_MONTHLY_INTEREST_DUE_ALL);
				borrow.setBidKind(borrowType.getId());
				borrow.setIsDay(Integer.valueOf((String)param.get("isDay")));
				borrow.setAllowTenderTime(rightNow.getTime());
				borrow.setValidTime(Integer.parseInt(param.get("validTime").toString()));
				borrow.setBorrowAddip(RequestUtils.getIpAddr());
				borrow.setPublishDatetime(validTime);
				result = borrowService.allowToPublishBorrow(borrow);
				if(result.isSuccessed()){
						result = borrowService.saveBorrow(borrow);
						messageCenterService.send(borrow.getUserId(),"标创建成功","标["+borrow.getBorrowTitle()+"]创建成功，等待初审",Notice.BORROW_CREATED);
						bool=true;
				}else{
					throw new RuntimeException(result.getMsg());
				}
				
				id[i] = borrow.getId();
				DwzResult dwzResult = new DwzResult(result);
				//添加日志
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("发布借款");
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_BORROW);
				operatorLog.setOperatorParams(borrow.toString());
				operatorLog.setOperatorReturn(dwzResult.getMessage());
				operatorLog.setOperatorStatus(Integer.parseInt(dwzResult.getStatusCode()));
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
			}
			if(!bool){
				throw new RuntimeException("发标失败！！！");
			}
			BorrowRelated borrowRelated=new BorrowRelated();
			borrowRelated.setRelatedPeriodNum(Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()).toString()));
			borrowRelated.setAddTime(new Date());
			
			borrowRelated.setRelatedAllowTenderTime(rightNow.getTime());
			borrowRelated.setRelatedPublishTime(validTime);
			borrowRelated.setBorrowoneId(id[0]);
			borrowRelated.setBorrowtwoId(id[1]);
			borrowRelated.setBorrowthreeId(id[2]);
			borrowRelated.setStatus(BorrowRelated.STATUS_FIRSTAUDIT_YES);
			borrowRelatedService.saveOrUpdate(borrowRelated);
			
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return bool;
	}

	@Override
	public ServiceResult cancelBorrow(Integer id) {
		Borrow borrow = this.borrowDao.selectByPrimaryKey(id);
		if(!borrow.getBorrowStatus().equals(Borrow.STATUS_FIRSTAUDIT_YES)){
			return new ServiceResult("104","当前标不允许撤销");
		}
		try {
			boolean boo = returnedMoneyByBorrowTenderList(borrow);
			if(boo){
				borrow.setBorrowStatus(Borrow.STATUS_CANCEL_BY_ADMIN);
				borrowDao.updateByPrimaryKeySelective(borrow);
			}
		} catch (DataAccessException e) {
			
			this.log.error("cancel borrow error:"+id,e);
			throw new RuntimeException();
		}
		
		return new ServiceResult(ServiceResult.SUCCESS,"标["+borrow.getBorrowTitle()+"]撤销成功");
	}

	@Override
	public PageModel selectBorrowsByStatusAndUserId(Map<String, Object> params) {
				
		
				PageModel model = borrowDao.getPage("selectBorrowsByStatusAndUserId", "countBorrowsByStatusAndUserId", params);
				new DesEncrypt().encryptInList(model.getList(),new String[]{"id"}); //repayId加密
				//new DesEncrypt().encryptInList(model.getList(),new String[]{"userId"}); //repayId加密
				return model;
	}

	@Override
	public Borrow findBorrowByTransferId(Integer transferId) {
		// TODO Auto-generated method stub
		return borrowDao.selectEntity("findBorrowByTransferId", transferId);
	}

	@Override
	public List<Borrow> findAutoBorrowAuditForImmediate(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return borrowDao.select("findAutoBorrowAuditForImmediate", param);
	}

	@Override
	public boolean AutoBorrowAuditForImmediate(Borrow borrow,BorrowType borrowType) {
		boolean bool=false;
		try {
			//获取标种对应的service实现类
			BorrowTypeHandleService borrowService = SpringUtils.getBean(BorrowTypeHandleService.class, borrowType.getDealService());
			borrow.setVerifyReviewUser(Constants.ADMIN_USER_ID);
			borrow.setVerifyReviewTime(new Date());
			ServiceResult rest = borrowService.reviewSuccess(borrow);
			if(rest.isSuccessed()){
				//生成pdf
				Borrow borrowPdf = this.getBorrowById(borrow.getId());
				Thread createThread=new Thread(new CreateAgreeMentThread(borrowService, borrowPdf));
				createThread.start();
			}
			if(rest.isSuccessed()){
				bool=true;
			}else{
				throw new RuntimeException(rest.getMsg());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}		
		return bool;
	}

	@Override
	public PageModel getBorrowDing(Map<String, String> paramsMap) {
		// TODO Auto-generated method stub
		return borrowRelatedDao.getPage("selectBorrowDing", "selectCountBorrowDing", paramsMap);
	}

	@Override
	public List<Borrow> getBorrowListByUserIdANDBorrowTypeCode(
			Map<String, String> param) {
		// TODO Auto-generated method stub
		return borrowDao.select("getBorrowListByUserIdANDBorrowTypeCode", param);
	}

	@Override
	public List getborrowInfo(Integer userId) {
		// TODO Auto-generated method stub
		return borrowDao.select("getborrowInfo", userId);
	}

	@Override
	public List getborrowAccount(Integer userId) {
		// TODO Auto-generated method stub
		return borrowDao.select("getborrowAccount", userId);
	}
	@Override
	public List<Borrow> getByBorrowName(String name) {
		return borrowDao.select("getByTitle", name);
	}

	@Override
	public PageModel getBorrowInBidPage(Map<String, String> paramsMap) {
		
		PageModel model =borrowDao.getPage("selectInBid", "countInBid", paramsMap);

//		new DesEncrypt().encryptInList(model.getList(),new String[]{"bId"}); //repayId加密
		for (Map m : (List<Map>)model.getList()) {
			m.put("ebId", m.get("bId"));
		}
		
		return model;
	}

	@Override
	public Integer getBorrowUsers() {
		return borrowDao.getTotalCount("selectBorrowUsers", null);
	}

	@Override
	public BigDecimal getFullMoney(Map<String, String> paramsMap) {
		return (BigDecimal) borrowDao.selects("selectFullMoney", paramsMap).get(0);
	}

	@Override
	public void autoTenderRules() {
		List<SysConfig> cfg = sysConfigService.getAll();
		if(cfg.size()>0){
			if(cfg.get(0).getAutoTenderStatus().equals(SysConfig.STATUS_NO)){
				return;
			}
		}
		List<Borrow> borrowList = borrowDao.select(
				"findAllowToBorrowListByBorrowStatus", null);
		if (borrowList.size() > 0) {
			List<AutotenderRules> autotenderRulesList = autotenderRulesService.getAutoQueue();
			for (AutotenderRules autotenderRules : autotenderRulesList) {
					autotenderRulesService.autoTenderRules(autotenderRules);
			}
		}
	}

	@Override
	public Borrow borrowFirst(Map<String, String> paramsMap) {
		List<Borrow> borrow=borrowDao.select("borrowFirst", paramsMap);
		if(borrow.size()>0){
			return borrow.get(0);
		}else{
			return null;
		}
	}

	@Override
	public PageModel getBorrowDeal(Map<String, String> paramsMap) {
		
		return borrowDao.getPage("findBorrowDeal", paramsMap);
	}

	
	/**
	 * 借款待办事项
	 * @param status
	 * 				状态：1.待审核
	 * 					2.待发布
	 * 					3.待放款
	 * @param flag
	 * @return
	 */
	@Override
	public Integer summaryBacklogCount(Integer status, Boolean flag) {
		return borrowDao.summaryBacklogCount(status, flag);
	}
	
	/**
	 * 借款数据统计-按产品类型
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByType(String date) {
		return borrowDao.borrowSummaryByType(date);
	}
	
	/**
	 * 借款数据统计-按期限
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByDate(String date) {
		return borrowDao.borrowSummaryByDate(date);
	}
	
	/**
	 * 借款数据统计-按省份
	 * @param date
	 * 			年份
	 * @return
	 */
	public List<Map<String, Object>> borrowSummaryByProvince(String date) {
		return borrowDao.borrowSummaryByProvince(date);
	}

	@Override
	public PageModel getProjectSummary(Map<String, String> paramsMap) {
		return borrowDao.getPage("getProjectSummary", "getProjectSummaryCount", paramsMap);
	}

	@Override
	public List<Map<String, Object>> getAllProjectSummary(Map<String, String> paramsMap) {
		return borrowDao.selects("getProjectSummary", paramsMap);
	}

	@Override
	public BigDecimal gettenderSum(Map<String, String> paramsMap) {
		// TODO Auto-generated method stub
		return (BigDecimal)borrowDao.selects("gettenderSum", paramsMap).get(0);
	}
	
}
