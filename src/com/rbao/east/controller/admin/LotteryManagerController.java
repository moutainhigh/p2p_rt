package com.rbao.east.controller.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.LotteryRecord;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.service.ILotteryService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;
/**
 * 抽奖管理
 * */
@Controller
@RequestMapping("lotteryManager/")
public class LotteryManagerController extends BaseController{
	
	private static Logger logger = LoggerFactory
			.getLogger(LotteryManagerController.class);

	@Autowired
	ILotteryService lotteryService;
	@Autowired
	private SysModuleService moduleService; 
	
	@RequestMapping("getRecordList")
	public String enterLottery(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map m2 = getParameters(request);
		
		/*model.addAttribute("pm",lotteryService.getLotteryRecordPaged(m));
		model.addAttribute("Prizes",lotteryService.getAllPrizes());
		model.addAttribute("params",m);
		return "user/lottery/winRecordList";*/
		/*Integer pages = 1;
		String pageStr = request.getParameter("currentPage");
		if (StringUtils.isNotBlank(pageStr)) {
			pages = Integer.valueOf(pageStr);
		}*/
		if("EXCEL".equals(request.getParameter("downLoadType"))){ //下载excel
			m2.put(Constants.PAGED_NUM_PERPAGE, "500000");
		}
	
		
		PageModel list = lotteryService.getLotteryRecordPaged(m2);
		request.getSession(true).setAttribute("queryParams", m2);
		m2.put("userId", this.loginAdminUser(request).getId().toString());
		List<SysModule> righSubtList=moduleService.getRightGroupList(m2);
		
		if("EXCEL".equals(request.getParameter("downLoadType"))){  //下载excel
			String mainTitle = "查看所有抽奖记录";
			String fileName = "lotteryExcel.xls";
			String[] title = {"ID","用户名","真实姓名","奖品",
								"中奖时间","电话","备注","状态"};
			List<String[]> contents = new ArrayList<String[]>();
			for(int i=0;i<list.getList().size();i++){ //一行数据
				Map m = (Map) list.getList().get(i);
				String status=String.valueOf(m.get("stats"));
				String s="";
				if(status.equals("1")){
					s="未兑奖";
				}else if(status.equals("2")){
					s="已兑奖";
				}
				contents.add(new String[]{
						String.valueOf( m.get("id")),
						String.valueOf( m.get("usrname")),
						String.valueOf( m.get("realname")),
						String.valueOf( m.get("disc")),
						String.valueOf( m.get("createTime")),
						String.valueOf( m.get("tel")),
						String.valueOf( m.get("remark")),
						s
						
				});
			}
			try {
				OutputStream os = response.getOutputStream();
				response.reset();// 清空输出流     
				response.setHeader("Content-disposition", "attachment; filename="+ 
								new String(fileName.getBytes("UTF-8"),"ISO8859-1"));
				
				response.setContentType("application/msexcel");// 定义输出类型   
				ExcelUtil.buildExcel(os,mainTitle, title,contents);
				
			} catch (IOException e) {
				logger.error("导出excel失败",e);
				e.printStackTrace();
			}
			return null;
		}else{
			model.addAttribute("pm",list);
			model.addAttribute("Prizes",lotteryService.getAllPrizes());
			model.addAttribute("searchParams",m2);
			model.addAttribute("right_id",m2.get("right_id"));
			model.addAttribute("righSubtList", righSubtList);
			
			return "lottery/winRecordList";
		}
		
	}
	
	//导出
	@RequestMapping("toExcel")
	public void toExcel(HttpServletRequest request,HttpServletResponse response) {
		
		//Map<String, String> maps = this.getParameters(request);
		//maps.put("numPerPage", "500000");
		try {
			Map<String, String> maps = (Map) request.getSession().getAttribute("queryParams");
			maps.put("pageNum", "1");
			maps.put("numPerPage", "500000");
			maps.remove("userId");
			PageModel pm = lotteryService.getLotteryRecordPaged(maps);
			List list = pm.getList();
			//List<Map<String, String>> ars = pm.getList();
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd ");
			String[] titles = {"ID","用户名","真实姓名","奖品","中奖时间","电话","备注","状态"};

			List<String[]> contents = new ArrayList<String[]>();
			for (int i=0;i<list.size();i++) {
				Map m = (Map) list.get(i);
				
				//User user = (User) list.get(i);
			
				//System.out.println("user:---"+user.toString());
				String[] conList = new String[8];
				conList[0] = StringUtil.toString(m.get("id"));
				//conList[1] = StringUtil.toString(map.get("type.name"));
				
				//System.out.println(user.getType().getName());
				//conList[1] = StringUtil.toString(user.getType().getName());
				
				conList[1] = StringUtil.toString(m.get("usrname"));
				conList[2] = StringUtil.toString( m.get("realname"));
				conList[3] = StringUtil.toString(m.get("disc"));
				conList[4] = StringUtil.toString(m.get("createTime"));
				conList[5] = StringUtil.toString( m.get("tel"));
				conList[6] = StringUtil.toString( m.get("remark"));
				conList[7] = LotteryRecord.ALL_LotteryRecord_STATUS.
											get(Integer.valueOf(m.get("stats").toString()));
				contents.add(conList);
			}
			
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("user-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "查看所有", titles, contents);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出excel失败", e);
		}

	}
	
	
	
	/**
	 * 标记为已发放兑奖
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("updateToPrized")
	public void updateToPrized(HttpServletRequest request,
			HttpServletResponse response) {
			Map<String,String> paramsMap=getParameters(request);
		
			JsonResult rest=null;
		try {
			Integer vId=Integer.parseInt(paramsMap.get("ids"));
			LotteryRecord recd = this.lotteryService.getRecordById(vId);
			if(recd == null){
				SpringUtils.renderDwzResult(response, false, "没有找到记录，请刷新后重试");
				return ;
			}
			LotteryRecord lrecd=new LotteryRecord();
			lrecd.setId(vId);
			lrecd.setStatus(LotteryRecord.status_hasPrized);
			lotteryService.updateRecord(lrecd);
			rest = new JsonResult(JsonResult.SUCCESS,"操作成功");
		} catch (Exception e) {
			logger.error("updateToPrized error:"+paramsMap,e);
			rest = new JsonResult("999","系统异常："+e.toString());
		}
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(this.loginAdminUser(request).getUserAccount());
		operatorLog.setOperatorTitle("标记为已兑奖");
		operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorParams(paramsMap.toString());
		operatorLog.setOperatorReturn(rest.getMsg());
		operatorLog.setOperatorStatus(Integer.parseInt(rest.getCode()));
		operatorLog.setLinkUrl(this.getIpAddr(request)); 
		operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, rest.isSuccessed(), rest.getMsg());
	}
	
}
