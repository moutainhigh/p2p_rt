package com.rbao.east.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rbao.east.common.Constants;
import com.rbao.east.entity.AccountDeduct;
import com.rbao.east.entity.AccountLog;
import com.rbao.east.entity.AccountRecharge;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.BorrowCollection;
import com.rbao.east.entity.BorrowRedeem;
import com.rbao.east.entity.BorrowRepayment;
import com.rbao.east.entity.BorrowRepossessed;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowTransfer;
import com.rbao.east.entity.FinancialProducts;
import com.rbao.east.entity.LotteryPrize;
import com.rbao.east.entity.LotteryRecord;
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.QuickBorrow;
import com.rbao.east.entity.QuickinvestmentApplications;
import com.rbao.east.entity.Recommend;
import com.rbao.east.entity.RedenvelopesRecord;
import com.rbao.east.entity.ShopGoods;
import com.rbao.east.entity.ShopOrder;
import com.rbao.east.entity.SysConfig;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserEvaluateApply;
import com.rbao.east.entity.VipUser;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.SysConfigService;
import com.rbao.east.service.SysFeesRateService;
import com.rbao.east.service.SysLetterTemplatesService;
import com.rbao.east.service.SysSmsConfigService;
import com.rbao.east.service.SysSmtpConfigService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.utils.PropertiesUtil;
import com.rbao.east.utils.StringUtil;
import com.rbao.east.utils.SysCacheUtils;

/**
 * 系统配置
 * @author cjx
 *SystemConfigStarter.java
 * 2014-1-8
 */
public class SystemConfigStarter implements Starter  {

	private static Logger logger = LoggerFactory
			.getLogger(SystemConfigStarter.class);
	
	private SysFeesRateService sysFeesRateService;
	private SysSmsConfigService sysSmsConfigService;
	private SysSmtpConfigService sysSmtpConfigService;
	private SysLetterTemplatesService sysLetterTemplatesService;
	private SysConfigService sysConfigService;
	private UserAccountService userAccountService;
	private SysConfigParamService cfgService;
	/**
	 * 获取spring注入的bean对象  
	 * @param ctx
	 */
	private void initBeans(ServletContext ctx){
		
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx); 
		sysFeesRateService = (SysFeesRateService)springContext.getBean("sysFeesRateServiceImpl"); 
		sysSmsConfigService   = (SysSmsConfigService)springContext.getBean("sysSmsConfigServiceImpl");
		sysSmtpConfigService=(SysSmtpConfigService)springContext.getBean("sysSmtpConfigServiceImpl");
		sysLetterTemplatesService = (SysLetterTemplatesService) springContext.getBean("sysLetterTemplatesServiceImpl");
		sysConfigService = (SysConfigService) springContext.getBean("sysConfigServiceImpl");
		userAccountService = (UserAccountService) springContext.getBean("userAccountServiceImpl");
		cfgService = (SysConfigParamService) springContext.getBean("sysConfigParamServiceImpl");
	}
	
	@Override
	public void init(ServletContext ctx) {
		initBeans(ctx); //初始化spring bean
		  
		ctx.setAttribute("PRE_PATH_VIEW" , Constants.PRE_PATH_VIEW);//查询路径前缀
		ctx.setAttribute("PRE_PATH_EDIT", Constants.PRE_PATH_EDIT);//编辑路径前缀
		
		ctx.setAttribute(Constants.ADMIN_URL, StringUtil.removeLastStr(
						PropertiesUtil.get("SYSTEM.ADMIN.REQUEST.URL"), "/"));//后台请求路径 
		//加载静态状态值
		ctx.setAttribute("BORROW_ALL_STATUS", Borrow.ALL_STATUS); //borrow中的所有状态
		ctx.setAttribute("BORROW_BC_STATUS", BorrowCollection.BC_STATUS); //borrowCollection中的所有状态
		ctx.setAttribute("BORROW_ALL_BORROW_USE", Borrow.ALL_BORROW_USE); //贷款用途
		ctx.setAttribute("BORROW_ALL_REPAYMENT_STYLE", Borrow.ALL_REPAYMENT_STYLE);//borrow中还款方式
		ctx.setAttribute("BORROW_ALL_REPOSSESSED_STATUS", BorrowRepossessed.ALL_STATUS);//borrow中还款方式
		ctx.setAttribute("APPLY_ALL_APPLY_STATUS",UserEvaluateApply.ALL_APPLY_STATUS);//额度申请状态
		ctx.setAttribute("USER_ALL_CARD", User.ALL_CARD); //user表中的所有证件类型
		
		ctx.setAttribute("MSG_ALL_TYPE", MessageCenter.NOTICE_TYPE);//MessageCenter中所有消息类型
		ctx.setAttribute("BORROWREPLAYMENT_ALL_REPAYMENT_STATUS", BorrowRepayment.ALL_REPAYMENT_STATUS);
		ctx.setAttribute("LOG_ALL_TRADE_CODE",AccountLog.ALL_TRADE_CODE);//资金记录类型
		ctx.setAttribute("RECOMMEND_ALL_RECOMMEND_STATUS", Recommend.ALL_RECOMMEND_STATUS);//推广邀请记录状态
		ctx.setAttribute("ACCOUNTRECHARGE_ALL_RECHAREGE_STATUS", AccountRecharge.ALL_RECHAREGE_STATUS);//充值记录状态
		ctx.setAttribute("ACCOUNTDEDUCT_ALL_TYPE", AccountDeduct.ALL_TYPE);//扣费类型
		ctx.setAttribute("TENDER_ALL_STATUS", BorrowTender.ALL_STATUS);//投标表类型
		ctx.setAttribute("TENDER_ALL_TYPE", BorrowTender.ALL_TENDER_TYPE);//投标方式
		ctx.setAttribute("BORROW_ALL_BID_RANK", Borrow.ALL_BID_RANK);//标星等级
		ctx.setAttribute("BORROW_ALL_CREDIT_TYPE", Borrow.ALL_CREDIT_TYPE);//信用类型

		ctx.setAttribute("ACCOUNTRECHARGE_ALL_RECHAREGE_TYPE", AccountRecharge.ALL_RECHAREGE_TYPE);//充值记录类型
		ctx.setAttribute("USER_REALNAME_STATUS", User.REALNAME);//user表中所有实名的状态
		ctx.setAttribute("USER_PHONE_STATUS", User.PHONE);//user表中所有手机认证的状态
		ctx.setAttribute("USER_EMAIL_STATUS", User.EMAIL);//user表中所有邮箱认证的状态
		ctx.setAttribute("USER_VIDEO_STATUS", User.VIDEO);//user表中所有视频认证的状态
		ctx.setAttribute("USER_SCENE_STATUS", User.SCENE);//user表中所有下次认证的状态
		ctx.setAttribute("VIP_STATUS", VipUser.VIP_STATUS);//vipUser的所有vip认证状态
		ctx.setAttribute("BorrowRedeem_ALL_STATUS", BorrowRedeem.ALL_REDDEM);//借款赎回状态
		ctx.setAttribute("BORROW_TRANSFER_STATUS", BorrowTransfer.ALL_STATUS);//债券转让状态
		
		//标种
		ctx.setAttribute("BORROW_TYPE_LI",Borrow.BORROW_TYPE_LI);//
		ctx.setAttribute("BORROW_TYPE_DING",Borrow.BORROW_TYPE_DING);//
		ctx.setAttribute("BORROW_TYPE_JING",Borrow.BORROW_TYPE_JING);//
		ctx.setAttribute("BORROW_TYPE_MIAO",Borrow.BORROW_TYPE_MIAO);//
		ctx.setAttribute("BORROW_TYPE_XING",Borrow.BORROW_TYPE_XING);//
		ctx.setAttribute("BORROW_TYPE_ZHUAN",Borrow.BORROW_TYPE_ZHUAN);//
		ctx.setAttribute("BORROW_TYPE_ZHUAN_DF", Borrow.BORROW_TYPE_ZHUAN_DF);
		
		ctx.setAttribute("QUICKBORROW_ALL_STATUS", QuickBorrow.ALL_STATUS);
		
		ctx.setAttribute("PRODUCTS_ALL_PRODUCTS_STATUS", FinancialProducts.ALL_PRODUCTS_STATUS);//理财产品中理财产品状态
		ctx.setAttribute("QUICKINVESTMENT_ALL_QUICKINVESTMENT_STATUS", QuickinvestmentApplications.ALL_QUICKINVESTMENT_STATUS);//预约申请状态
		
		//加载系统设置
		ctx.setAttribute(Constants.CACHE_SYS_FEES_RATE, sysFeesRateService.getSysFeesRate());//加载费率
		ctx.setAttribute(Constants.CACHE_SYS_SMTP_CONFIG, sysSmtpConfigService.getSysSmtpConfig());//加载smtp配置
		ctx.setAttribute(Constants.CACHE_SYS_SMS_CONFIG, sysSmsConfigService.getSysSmsConfig());//加载短信配置
		ctx.setAttribute(Constants.CACHE_SYS_LETTER_TEMPLATES, sysLetterTemplatesService.getSysLetterTemplate());
		List<SysConfig> cfg = sysConfigService.getAll();
		if(cfg!=null&&cfg.size()>0){
			ctx.setAttribute(Constants.CACHE_SYS_CONFIG, cfg.get(0));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleName", "客服");
		List<Map<String, Object>> QQList=userAccountService.getQQ(map);
		ctx.setAttribute(Constants.CUSTOMER_SERVICE, QQList);
		ctx.setAttribute("SysConfigParams", cfgService.getAllValueToMap());
		ctx.setAttribute("RED_ALL_STATUS", RedenvelopesRecord.ALL_STATUS);
		ctx.setAttribute("RED_ALL_TYPE", RedenvelopesRecord.ALL_Type);
		
		ctx.setAttribute("ALL_LOTTERY_RECORD_STATUS", LotteryRecord.ALL_LotteryRecord_STATUS);//兑奖状态
		ctx.setAttribute("ALL_SHOP_ORDER_STATUS", ShopOrder.ALL_ShopOrder_STATUS);//订单状态
		ctx.setAttribute("SHOP_GOODS_ALL_isEnable", ShopGoods.ALL_isEnable);
		ctx.setAttribute("LOTTERY_PRZ_ALL_STATS", LotteryPrize.ALL_stats);
		ctx.setAttribute("LOTTERY_PRZ_ALL_type", LotteryPrize.ALL_type);
		
		ctx.setAttribute("websitetel", SysCacheUtils.getSysConfig()
				.getSysWebsitetel());
		ctx.setAttribute("websiteName", SysCacheUtils.getSysConfig()
				.getSysWebsitename());
		ctx.setAttribute("address", SysCacheUtils.getSysConfig()
				.getSysWebsiteaddress());
		ctx.setAttribute("serviceTime", SysCacheUtils.getSysConfig()
				.getSysWebsitefax());
		ctx.setAttribute("websiteicp", SysCacheUtils.getSysConfig()
				.getSysWebsiteicp());
		logger.debug("------------load SystemConfig end-------------");
	}

}
