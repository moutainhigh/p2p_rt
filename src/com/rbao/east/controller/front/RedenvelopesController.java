package com.rbao.east.controller.front;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.BorrowTender;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.RedenvelopesRecord;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.RedenvelopesProbabilityService;
import com.rbao.east.service.RedenvelopesService;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.SpringUtils;

/**
 * 红包管理
 * */
@Controller
@RequestMapping("redenvelopes/")
public class RedenvelopesController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(RedenvelopesController.class);

	@Autowired
	private RedenvelopesProbabilityService redenvelopesProbabilityService;
	@Autowired
	RedenvelopesService redService ;
	@Autowired
	private UserService userService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private SysConfigParamService sysConfigParamService;
	@Autowired
	private BorrowTenderService borrowTenderService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/showList.do")
	public String showList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = this.loginFrontUser(request);
		Map m = new HashMap();
		if(user!=null)
			m.put("userId", user.getId());
		User dbUser = this.userService.getById(user.getId());
		if(dbUser.getAutoRedFlag().intValue() == 2){
			model.addAttribute("openAutoRedFlag","true"); 
		}
		m.put("inStatus", new Integer[]{RedenvelopesRecord.STATUS_NOT_OPEN});
		model.addAttribute("pm",redService.getFrontListPaged(m)); 
		//查看抵扣金
		UserAccount userAccount = userAccountService.getByUserId(user.getId());
		BigDecimal deductionMoney = userAccount.getDeductionMoney();
		int r=deductionMoney.compareTo(BigDecimal.ZERO);
		if(r==0){
			model.addAttribute("deductionMoney",null); 
		}else{
			model.addAttribute("deductionMoney",deductionMoney); 
		}
		//查看活动开关
		Map<String, String> sysConfigParamMap = sysConfigParamService.getAllValueToMap();
		String activitySwitch = sysConfigParamMap.get("activity_switch");
		model.addAttribute("activitySwitch",activitySwitch);
		String activityBeginStr = sysConfigParamMap.get("activity_begin");
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//已使用优惠红包金额
		Map<String,Object> map=new HashMap<String, Object>();
		Date activityBegin;
		try {
			activityBegin = sdf.parse(activityBeginStr);
			map.put("userId", dbUser.getId());
			map.put("tenderAddtime", activityBegin);
			//查询一定时间内投资成功的总金额
			map.put("tenderStatus", new String [] {BorrowTender.STATUS_REPAYING.toString(),BorrowTender.STATUS_REPAYED.toString(),BorrowTender.STATUS_OVERDUE.toString(),BorrowTender.STATUS_TRANSFER.toString()});
			map.put("borrowKinds", new String [] {BorrowType.TYPE_MINGXING.toString(),BorrowType.TYPE_LIUYUE.toString(),BorrowType.TYPE_JIUYUE.toString(),BorrowType.TYPE_SHEERYUE.toString(),});
			//已投资金额
			BigDecimal tenderMoney=this.borrowTenderService.queryTenderMoneyByKind(map);
			tenderMoney = tenderMoney.divide(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP);
			BigDecimal unUseMoney = new BigDecimal(1000).subtract(tenderMoney).setScale(2,RoundingMode.HALF_UP);
			if(unUseMoney.compareTo(new BigDecimal(0)) == -1){
				model.addAttribute("unUseMoney","0.00");
			}else{
				model.addAttribute("unUseMoney",unUseMoney);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "userinfo/red/redList";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPagedList.do")
	public void getPagedList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = this.loginFrontUser(request);
		Map m = this.getParameters(request);
		if(user!=null)
			m.put("userId", user.getId());
	//	m.put("inStatus", new Integer[]{RedenvelopesRecord.STATUS_NOT_OPEN});
		SpringUtils.renderJson(response,redService.getFrontListPaged(m)); 
		
	}
	@RequestMapping("/open.do")
	public void openRed(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Integer id = Integer.valueOf(StringUtils.defaultIfBlank(request.getParameter("id"),"0")); 
		JsonResult rest = redService.openRedenvelopes(id,false);  
		
		SpringUtils.renderJsonResult(response, rest.getCode(), rest.getMsg()); 
		
	}
	@RequestMapping("/setAutoFlag.do")
	public void setAuto(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = this.loginFrontUser(request);
		
		boolean opened = StringUtils.defaultIfBlank(request.getParameter("flag"),"1")
					.equals("2");  
		boolean  boo = redService.setAutoFlag(user.getId(), opened);
		
		String succMsg = "";
		if(opened){
			if(boo){
				//打开所有红包
				int openCount = redService.openAllRed(user.getId());
				succMsg = "设置成功"+(openCount>0?",已打开"+openCount+"个红包":"");
			}
			
		}else{
			succMsg = "自动打开红包已关闭";
		}
		SpringUtils.renderJsonResult(response, boo?"200":"300", boo?succMsg:"操作失败");
		
		
	}
	
}
