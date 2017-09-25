package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.RedenvelopesProbability;
import com.rbao.east.entity.RedenvelopesRecord;
import com.rbao.east.entity.SysModule;
import com.rbao.east.service.RedenvelopesProbabilityService;
import com.rbao.east.service.RedenvelopesService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;

/**
 * 红包管理
 * */
@Controller
@RequestMapping("redenvelopes/")
public class RedenvelopesManagerController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(RedenvelopesManagerController.class);

	@Autowired
	private RedenvelopesProbabilityService redenvelopesProbabilityService;
	@Autowired
	RedenvelopesService redService ;
	@Autowired
	private SysModuleService moduleService;
	
	
	//红包概率倍率
	@RequestMapping(Constants.PRE_PATH_VIEW + "getRedenvelopProbabilityList")
	public String getRedenvelopProbabilityList(HttpServletRequest request, HttpServletResponse response,Model model){
		List<RedenvelopesProbability> list=redenvelopesProbabilityService.getAll();
				
		if(list.size() > 0)
			request.setAttribute("multiList", list);
		return "redenvelopes/editRedenvelopesProbability";
		
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "save")
	public void save(HttpServletRequest request, HttpServletResponse response){
		String[] probability =  request.getParameterValues("probability");
		String[] multi =  request.getParameterValues("multi");
		String[] min =  request.getParameterValues("min");
		String[] max =  request.getParameterValues("max");

		
		if(probability == null || multi == null){
			SpringUtils.renderDwzResult(response, false, "至少保存一条记录",DwzResult.CALLBACK_CLOSECURRENTDIALOG);
			return ;
		}
		List<RedenvelopesProbability> list = new ArrayList<RedenvelopesProbability>();
		for(int i=0;i<probability.length;i++){
			if(StringUtils.isBlank(probability[i]) || StringUtils.isBlank(multi[i])){
				continue;
			}
			RedenvelopesProbability rp = new RedenvelopesProbability();
			rp.setProbability(new BigDecimal(probability[i]));
			rp.setMulti(new BigDecimal(multi[i])); 
			if(!StringUtils.isBlank(min[i])){
				rp.setMin(new BigDecimal(min[i]));
			}
			if(!StringUtils.isBlank(max[i])){
				rp.setMax(new BigDecimal(max[i]));
			}

			list.add(rp);
		}
		boolean succ=false;
		try {
			succ = redenvelopesProbabilityService.save(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringUtils.renderDwzResult(response, succ, succ?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENTDIALOG); 
			
	}
	/**
	 * 进入发放红包页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "sendRedEnv")
	public String sendRedEnv(HttpServletRequest request, HttpServletResponse response,Model model){
		String maxMoney = StringUtils.defaultIfBlank(request.getParameter("max"),"0");
		model.addAttribute("maxMoney",maxMoney);
		return "redenvelopes/sendRedEnv";
		
	}
	/**
	 * 进入发放红包页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "requestSendRedEnv")
	public void requestSendRedEnv(HttpServletRequest request, HttpServletResponse response,Model model){
		Date now = new Date();
		Map<String, String> map = this.getParameters(request);
		Integer sendUsrId = this.loginAdminUser(request).getId();
		List<Integer> recvUsrIds = StringUtil.toIntegerList(StringUtil.splitToArray(request.getParameter("receiveUserIds"),","));
		if(recvUsrIds.size() == 0){ //
			SpringUtils.renderDwzResult(response, false, "请选择用户"); 
			return;
		}
		RedenvelopesRecord env = new RedenvelopesRecord();
		env.setName(map.get("redName"));
		env.setSendUserId(sendUsrId);
		env.setSendTime(now);
		/**有效期计算***/
		env.setPeriodBeginTime(now);
		Date endPeriod = null;
		try {
			switch(Integer.parseInt(map.get("periodType"))){
				case 1: //月
					endPeriod = DateUtils.addMonth(env.getPeriodBeginTime(), Integer.parseInt(map.get("periodType_month")));
					break;
				case 2://天
					String days = map.get("periodType_day");
					if(StringUtils.isBlank(days)){
						SpringUtils.renderDwzResult(response, false, "请输入有效期"); 
						return;
					}
					endPeriod = DateUtils.addDay(env.getPeriodBeginTime(), Integer.parseInt(days));
					break;
			}
			env.setPeriodEndTime(endPeriod);
			/**红包类型***/
			switch(Integer.parseInt(map.get("redType"))){
				case 1://现金红包
					
					String money = map.get("redType_1").equals("1")?map.get("selectMoney"):map.get("inputMoney");
					if(StringUtils.isBlank(money)){
						SpringUtils.renderDwzResult(response, false, "请输入发放金额"); 
						return;
					}
					env.setStatus(RedenvelopesRecord.STATUS_NOT_OPEN);
					env.setAmount(new BigDecimal(money));
					env.setRemark("无条件");
					env.setRedType(RedenvelopesRecord.TYPE_CASH);
					break;
					
				case 2://满额红包
					String[] params = getTenderRed(map.get("redType_2"),request);//{0:limit;1:money }
					
					env.setOpenLimitType(RedenvelopesRecord.OPEN_LIMIT_TYPE_TENDER);
					env.setOpenLimitParam(new BigDecimal(params[0]));
					env.setAmount(new BigDecimal(params[1]));
					env.setRemark("投资满"+env.getOpenLimitParam()+"元");
					env.setRedType(RedenvelopesRecord.TYPE_TENDER);
					break;
				case 3://登录红包
					params = getLoginRed(map.get("redType_3"),request);//{0:limit type;1:limit param;2:money }
					
					env.setOpenLimitType(Integer.valueOf(params[0])); 
					env.setOpenLimitParam(new BigDecimal(params[1]));
					env.setAmount(new BigDecimal(params[2]));
					env.setRemark((env.getOpenLimitType().equals(RedenvelopesRecord.OPEN_LIMIT_TYPE_LOGIN_CONTINUE)?"连续":"")
						+"登录"+env.getOpenLimitParam()+"天");
					env.setRedType(RedenvelopesRecord.TYPE_LOGIN);
					break;
				case 4://续投红包
//			params = getLoginRed(map.get("redType_3"),request);//{0:limit type;1:limit param;2:money }
					
					env.setOpenLimitType(RedenvelopesRecord.OPEN_LIMIT_TYPE_CONTINUE_TENDER); 
					env.setOpenLimitParam(new BigDecimal(1)); 
					env.setAmount(new BigDecimal(request.getParameter("continueDayRed"))); 
					env.setRemark("有效期内再次投资");
					env.setRedType(RedenvelopesRecord.TYPE_CONTINUE_TENDER);
					env.setPeriodEndTime(DateUtils.addDay(env.getPeriodBeginTime(), env.getOpenLimitParam().intValue()));
					break;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			SpringUtils.renderDwzResult(response, false, "数据校验失败,请确认输入数据是否正确"); 
			return;
		}
		boolean succ = false;
		try {
			succ = redService.sendRedRecord(env, recvUsrIds);
		} catch (Exception e) {
			e.printStackTrace();
			SpringUtils.renderDwzResult(response, false, "操作失败,请确认输入数据是否正确"); 
			return;
		}
		SpringUtils.renderDwzResult(response, succ, succ?"操作成功":"操作失败"); 
		
	}
	private String[] getTenderRed(String type,HttpServletRequest request){
		String[] str = null;
		switch(Integer.parseInt(type)){
			case 1:
				str = new String[]{"500","5"};
				break;
			case 2:
				str = new String[]{"1000","10"};
				break;
			case 3:
				str = new String[]{"10000","100"};
				break;
			case 4:
				str = new String[]{request.getParameter("tenderMoney"),request.getParameter("tenderMoneyRed")};
				break;
		}
		return str;
	}
	private String[] getLoginRed(String type,HttpServletRequest request){
		String[] str = null;
		/*switch(Integer.parseInt(type)){
			case 1:
				str = new String[]{RedenvelopesRecord.OPEN_LIMIT_TYPE_LOGIN_DAYS.toString(),"30","5"};
				break;
			case 2:
				str = new String[]{RedenvelopesRecord.OPEN_LIMIT_TYPE_LOGIN_CONTINUE.toString(),"20","10"};
				break;
			
		}*/
		
		str = new String[]{RedenvelopesRecord.OPEN_LIMIT_TYPE_LOGIN_DAYS.toString(),
				request.getParameter("loginDays"),request.getParameter("loginDayRed")};
		
		
		
		
		
		
		
		return str;
	}
	//红包发放
	@RequestMapping(Constants.PRE_PATH_VIEW + "redRecordList")
	public String redRecordList(HttpServletRequest request, HttpServletResponse response,Model model){
		
		Map<String, String> maps = this.getParameters(request);
		PageModel pm = redService.getAdminListPaged(maps);
		
		maps.put("userId", this.loginAdminUser(request).getId().toString());
		List<SysModule> righSubtList = moduleService.getRightGroupList(maps);

		model.addAttribute("pm", pm);
		model.addAttribute("righSubtList", righSubtList);
		model.addAttribute("searchParams", maps);
		model.addAttribute("right_id", maps.get("right_id"));
		model.addAttribute("hidden", maps.get("hidden"));
		return "redenvelopes/redRecordList";
		
	}
	
	/**
	 * 导出Excel
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcel")
	public void toExcel(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> maps = this.getParameters(request);
		maps.put("numPerPage", "500000");
		try {
			PageModel pm = redService.getAdminListPaged(maps);
			List<Map<String, Object>> ars = pm.getList();

			String[] titles = { "用户账户", "金额", "类型", "发放时间", "发放人", "红包状态",
					"打开时间" };

			List<String[]> contents = new ArrayList<String[]>();
			for (Map<String, Object> map : ars) {
				String[] conList = new String[7];
				conList[0] = StringUtil.toString(map.get("userAccount"));
				conList[1] = StringUtil.toString(map.get("amount"));
				conList[2] = StringUtil.toString(RedenvelopesRecord.ALL_Type
						.get(map.get("redType")));
				conList[3] = StringUtil.toString(map.get("sendTime"));
				conList[4] = StringUtil.toString(map.get("sendUserAccount"));
				conList[5] = StringUtil.toString(RedenvelopesRecord.ALL_STATUS   
						.get(map.get("status")));
				
				if(map.get("status").equals(RedenvelopesRecord.STATUS_HAS_OPEN)){
					conList[6] = StringUtil.toString(map.get("updateTime"));
				}else{
					conList[6] = "";
				}
				contents.add(conList);

			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("accountlog-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "红包记录", titles, contents);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出excel失败", e);
		}

	}
	
	
	
	
	
	
}
