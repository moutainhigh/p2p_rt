package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Recommend;
import com.rbao.east.entity.RecommendReward;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.RecommendService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.GetDatabaseConn;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;
/**
 * 推广关系
 * */
@Controller
@RequestMapping("recommend/")
public class RecommendController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(RecommendController.class);

	@Autowired
	private RecommendService recommendService;
	@Autowired
	private SysModuleService moduleService;
	
	@Autowired
	private UserService userService;


	// 列表
	@RequestMapping(Constants.PRE_PATH_VIEW + "getRecommendsList")
	public String getRecommendsList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = recommendService.getRecommends(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", paramsMap);
		model.addAttribute("rel", request.getParameter("rel"));
		return "recommend/recommendlist";
	}
	
	
	//获取推荐人下面的被推荐人信息
	@RequestMapping(Constants.PRE_PATH_VIEW + "getSubRecommendsLists")
	public String getSubRecommendsLists(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		//PageModel pageModel = recommendService.getRecommends(paramsMap);
		
		String recommendUserId=paramsMap.get("recommendUserId");
		
		User user = userService.getById(Integer.parseInt(recommendUserId));
		
		//List<Recommend> rbRecommendLevl1 = this.getRecommendOrSetSession(request, recommendUserId, 1);
		//List<Recommend> rbRecommendLevl2 = this.getRecommendOrSetSession(request, recommendUserId, 2);
		
		
		
		//List<Recommend> rbRecommendLevl3 =this.getRecommendOrSetSession(request, recommendUserId, 3);
		
		
		List<User> rbRecommendLevl1 = this.getRecommendLevl(recommendUserId, 2);
		List<User> rbRecommendLevl2 = this.getRecommendLevl(recommendUserId, 3);
		List<User> rbRecommendLevl3 = this.getRecommendLevl(recommendUserId, 4);
		
		
		model.addAttribute("list1", rbRecommendLevl1);
		model.addAttribute("list2", rbRecommendLevl2);
		model.addAttribute("list3", rbRecommendLevl3);
		
		
		model.addAttribute("listsize1", rbRecommendLevl1.size());
		model.addAttribute("listsize2", rbRecommendLevl2.size());
		model.addAttribute("listsize3", rbRecommendLevl3.size());
		model.addAttribute("recommendUserId", recommendUserId);
		model.addAttribute("user", user);
		
		
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("searchParams", paramsMap);
		model.addAttribute("rel", request.getParameter("rel"));
		return "recommend/subrecommendlistlevl";
	}
	
	
	
	private List<User> getRecommendLevl(String recommendUserId,Integer levl){
		
		String[] recommendSubLevl = GetDatabaseConn.getRecommendSubLevl(recommendUserId, levl+"");
		
		Map<String,Object> Levl=new HashMap<String, Object>();
		Levl.put("recommendUserId", recommendUserId);
		List<User> levllist=new ArrayList<User>();
		for (String userId : recommendSubLevl) {
			User ruser = userService.getById(Integer.parseInt(userId));
			if(ruser == null){
				
			}else{
				ruser.setLevl(levl-1);
				levllist.add(ruser);
			}
		}
		return levllist;
	}
	
	
	/**
	 * 存session方法,现在废用
	 * */
	@SuppressWarnings("unchecked")
	private List<Recommend> getRecommendOrSetSession(HttpServletRequest request,String recommendUserId,Integer levl){
		List<Recommend> rbRecommendLevl =new ArrayList<Recommend>();
		rbRecommendLevl = (List<Recommend>) request.getSession().getAttribute("rbRecommendLevl-"+levl+"-"+recommendUserId);
		if(null==rbRecommendLevl){
			Map<String,Object> Levl=new HashMap<String, Object>();
			Levl.put("recommendUserId", recommendUserId);
			Levl.put("level", levl);
			
			rbRecommendLevl =recommendService.getRbRecommendLevl(Levl);
			
			Iterator<Recommend> iterator1 = rbRecommendLevl.iterator();
			while(iterator1.hasNext()){  
				  Recommend a=iterator1.next(); 
				  a.setLevl(levl);//标识几级用户
				 
			}
		}
		request.getSession().setAttribute("rbRecommendLevl-"+levl+"-"+recommendUserId, rbRecommendLevl);
		
		return rbRecommendLevl;
	}
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getSubRecommendLevl1sLists")
	public String getSubRecommendLevl1sLists(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		//PageModel pageModel = recommendService.getRecommends(paramsMap);
		
		String recommendUserId=paramsMap.get("recommendUserId");
		
		User user = userService.getById(Integer.parseInt(recommendUserId));
		
		List<User> levl1list = this.getRecommendLevl(recommendUserId, 2);
		
		
		//List<Recommend> rbRecommendLevl1 = this.getRecommendOrSetSession(request, recommendUserId, 1);
		model.addAttribute("list1", levl1list);
		
		
		model.addAttribute("listsize1", levl1list.size());
		model.addAttribute("recommendUserId", recommendUserId);
		model.addAttribute("user", user);
		
		
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("searchParams", paramsMap);
		model.addAttribute("rel", request.getParameter("rel"));
		
		
		
		return "recommend/subrecommendlistlevl1";
	}
	
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getSubRecommendLevl2sLists")
	public String getSubRecommendLevl2sLists(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		
		String recommendUserId=paramsMap.get("recommendUserId");
		
		User user = userService.getById(Integer.parseInt(recommendUserId));
		
		//List<Recommend> rbRecommendLevl2= this.getRecommendOrSetSession(request, recommendUserId, 2);
		
		List<User> levl2list = this.getRecommendLevl(recommendUserId, 3);
		
		
		model.addAttribute("list1", levl2list);
		
		
		model.addAttribute("listsize1", levl2list.size());
		model.addAttribute("recommendUserId", recommendUserId);
		model.addAttribute("user", user);
		
		
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("searchParams", paramsMap);
		model.addAttribute("rel", request.getParameter("rel"));
		
		return "recommend/subrecommendlistlevl2";
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getSubRecommendLevl3sLists")
	public String getSubRecommendLevl3sLists(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> paramsMap = getParameters(request);
		paramsMap
				.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		//PageModel pageModel = recommendService.getRecommends(paramsMap);
		
		String recommendUserId=paramsMap.get("recommendUserId");
		
		User user = userService.getById(Integer.parseInt(recommendUserId));
		
		//List<Recommend> rbRecommendLevl3= this.getRecommendOrSetSession(request, recommendUserId, 3);
		List<User> levl3list = this.getRecommendLevl(recommendUserId, 4);
		
		
		
		
		model.addAttribute("list1", levl3list);
		
		
		model.addAttribute("listsize1", levl3list.size());
		model.addAttribute("recommendUserId", recommendUserId);
		model.addAttribute("user", user);
		
		
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("searchParams", paramsMap);
		model.addAttribute("rel", request.getParameter("rel"));
		return "recommend/subrecommendlistlevl3";
	}
	
		// 推广级数查询列表
		@RequestMapping(Constants.PRE_PATH_VIEW + "getRecommendsLists")
		public String getRecommendsLists(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			Map<String, String> paramsMap = getParameters(request);
			PageModel pageModel = recommendService.selectCountRecommend4Admin(paramsMap);
			model.addAttribute("pm", pageModel);
			model.addAttribute("searchParams", paramsMap);
			model.addAttribute("rel", request.getParameter("rel"));
			model.addAttribute("right_id", paramsMap.get("right_id"));
			paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
			// 获得当前登录用户的rightId下的子权限
			List<SysModule> righSubtList = moduleService
					.getRightGroupList(paramsMap);
			model.addAttribute("righSubtList", righSubtList);
			request.getSession(true).setAttribute("queryParams", paramsMap);
			return "recommend/recommendlevellist";
		}

	// 根据Id查询推广邀请
	@RequestMapping(Constants.PRE_PATH_VIEW + "getRecommendById")
	public String getRecommendById(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		Recommend recommend = recommendService.getRecommendById(Integer
				.parseInt(param.get("supportID").toString()));
		model.addAttribute("recommend", recommend);
		model.addAttribute("right_id", param.get("right_id"));
		return "recommend/checkrecommend";

	}

	@RequestMapping(Constants.PRE_PATH_EDIT + "checkRecommend")
	public void checkRecommend(HttpServletRequest request,
			HttpServletResponse response, Model model, Recommend recommend) {
		boolean succ = false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id").toString());
		recommend.setVerifyUserid(this.loginAdminUser(request).getId());
		recommend.setVerifyAddtime(new Date());

		String ip = this.getIpAddr(request);
		recommend.setVerifyAddip(ip);

		try {
			succ = recommendService.saveRecommend(recommend,recommend.getRecommendMoney());
			//推荐奖励固定金额
			this.recommendService.saveOrUpdateData(recommend, recommend.getRecommendMoney(), new BigDecimal(0),new BigDecimal(0), RecommendReward.initLevel,RecommendReward.StatusStaied,null,null);
		} catch (Exception e) {
			logger.info("邀请推广审核出错！" + e.toString());
			
		}
		SpringUtils.renderDwzResult(response, succ, succ ? "操作成功" : "操作失败",
				DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id")
						.toString());
	}

	
	//导出某人下面的被推荐人
	@RequestMapping(Constants.PRE_PATH_VIEW+"exportSubRecommendsList")
	public void exportSubRecommendsList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		params.put("numPerPage", "10000");
		try {
			//PageModel pm = recommendService.getRecommends(params);
			String recommendUserId=params.get("recommendUserId");
			
			User user = userService.getById(Integer.parseInt(recommendUserId));
			
			
			//List<Recommend> subRecommendLists= recommendService.getSubRecommendLists(recommendUserId);
			/*List<Recommend> rbRecommendLevl1 = this.getRecommendOrSetSession(request, recommendUserId, 1);
			List<Recommend> rbRecommendLevl2 = this.getRecommendOrSetSession(request, recommendUserId, 2);
			
			List<Recommend> rbRecommendLevl3 =this.getRecommendOrSetSession(request, recommendUserId, 3);
			rbRecommendLevl1.addAll(rbRecommendLevl2);
			rbRecommendLevl1.addAll(rbRecommendLevl3);*/
			
			
			List<User> rbRecommendLevl1 = this.getRecommendLevl(recommendUserId, 2);
			List<User> rbRecommendLevl2 = this.getRecommendLevl(recommendUserId, 3);
			List<User> rbRecommendLevl3 = this.getRecommendLevl(recommendUserId, 4);
			rbRecommendLevl1.addAll(rbRecommendLevl2);
			rbRecommendLevl1.addAll(rbRecommendLevl3);
			
			String[] titles = { "被推荐用户id", "被推荐用户名称","被推荐用户员工内部号","被推荐用户真实姓名", "建立时间", "分销级数"};

			List<String[]> contents = new ArrayList<String[]>();
			for (User usr : rbRecommendLevl1) {
				String[] conList = new String[6];
				conList[0] = StringUtil.toString(usr.getId());
				conList[1] = StringUtil.toString(usr.getUserAccount());
				conList[2] = StringUtil.toString(usr.getRtUserFlag());
				conList[3] = StringUtil.toString(usr.getUserRealname());
				conList[4] = StringUtil.toString(dateFormat.format(usr.getUserAddtime()));
				conList[5] = StringUtil.toString(usr.getLevl());
				
				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String((user.getUserAccount() + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, user.getUserAccount()+"推广邀请记录统计数据", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}
	}
	
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW+"exportSubRecommendsListLevlOne")
	public void exportSubRecommendsListLevlOne(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		params.put("numPerPage", "10000");
		try {
			//PageModel pm = recommendService.getRecommends(params);
			String recommendUserId=params.get("recommendUserId");
			
			User user = userService.getById(Integer.parseInt(recommendUserId));
			
			
			//List<Recommend> rbRecommendLevl1= this.getRecommendOrSetSession(request, recommendUserId, 1);
			
			List<User> rbRecommendLevl1 = this.getRecommendLevl(recommendUserId, 2);
			String[] titles = { "被推荐用户id", "被推荐用户名称","被推荐用户员工内部号","被推荐用户真实姓名", "建立时间", "分销级数"};

			List<String[]> contents = new ArrayList<String[]>();
			for (User usr : rbRecommendLevl1) {
				String[] conList = new String[6];
				conList[0] = StringUtil.toString(usr.getId());
				conList[1] = StringUtil.toString(usr.getUserAccount());
				conList[2] = StringUtil.toString(usr.getRtUserFlag());
				conList[3] = StringUtil.toString(usr.getUserRealname());
				conList[4] = StringUtil.toString(dateFormat.format(usr.getUserAddtime()));
				conList[5] = StringUtil.toString(1);
				
				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String((user.getUserAccount() + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, user.getUserAccount()+"推广邀请记录统计数据(一级)", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}
	}
	
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW+"exportSubRecommendsListLevlTwo")
	public void exportSubRecommendsListLevlTwo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		params.put("numPerPage", "10000");
		try {
			//PageModel pm = recommendService.getRecommends(params);
			String recommendUserId=params.get("recommendUserId");
			
			User user = userService.getById(Integer.parseInt(recommendUserId));
			
			
			
			//List<Recommend> rbRecommendLevl2= this.getRecommendOrSetSession(request, recommendUserId, 2);
			

			List<User> rbRecommendLevl1 = this.getRecommendLevl(recommendUserId, 3);
			String[] titles = { "被推荐用户id", "被推荐用户名称","被推荐用户员工内部号","被推荐用户真实姓名", "建立时间", "分销级数"};

			List<String[]> contents = new ArrayList<String[]>();
			for (User usr : rbRecommendLevl1) {
				String[] conList = new String[6];
				conList[0] = StringUtil.toString(usr.getId());
				conList[1] = StringUtil.toString(usr.getUserAccount());
				conList[2] = StringUtil.toString(usr.getRtUserFlag());
				conList[3] = StringUtil.toString(usr.getUserRealname());
				conList[4] = StringUtil.toString(dateFormat.format(usr.getUserAddtime()));
				conList[5] = StringUtil.toString(2);
				
				contents.add(conList);
			}


			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String((user.getUserAccount() + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, user.getUserAccount()+"推广邀请记录统计数据(二级)", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}
	}
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW+"exportSubRecommendsListLevlThree")
	public void exportSubRecommendsListLevlThree(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		params.put("numPerPage", "10000");
		try {
			//PageModel pm = recommendService.getRecommends(params);
			String recommendUserId=params.get("recommendUserId");
			
			User user = userService.getById(Integer.parseInt(recommendUserId));
			
			//List<Recommend> rbRecommendLevl3= this.getRecommendOrSetSession(request, recommendUserId, 3);
			

			List<User> rbRecommendLevl1 = this.getRecommendLevl(recommendUserId, 4);
			String[] titles = { "被推荐用户id", "被推荐用户名称","被推荐用户员工内部号","被推荐用户真实姓名", "建立时间", "分销级数"};

			List<String[]> contents = new ArrayList<String[]>();
			for (User usr : rbRecommendLevl1) {
				String[] conList = new String[6];
				conList[0] = StringUtil.toString(usr.getId());
				conList[1] = StringUtil.toString(usr.getUserAccount());
				conList[2] = StringUtil.toString(usr.getRtUserFlag());
				conList[3] = StringUtil.toString(usr.getUserRealname());
				conList[4] = StringUtil.toString(dateFormat.format(usr.getUserAddtime()));
				conList[5] = StringUtil.toString(3);
				
				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String((user.getUserAccount() + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, user.getUserAccount()+"推广邀请记录统计数据(三级)", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}
	}
	
	/**
	 * 导出Excel
	 * 
	 * @author adc
	 */
	@RequestMapping("toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		params.put("numPerPage", "10000");
		try {
			PageModel pm = recommendService.getRecommends(params);
			List<Map<String, String>> ars = pm.getList();


			//String[] titles = { "序号", "用户名称", "状态", "奖励金额", "添加时间", "推荐人", "审核人", "审核时间", "审核IP", "审核备注", "排序" };
			String[] titles = { "序号", "用户名称", "用户真实姓名","被推荐人手机号码", "状态", "添加时间", "推荐人","推荐人真实姓名","推荐人手机号码", "审核人", "审核时间", "审核IP", "审核备注", "排序" };
			List<String[]> contents = new ArrayList<String[]>();
			for (Map<String, String> map : ars) {
				String[] conList = new String[titles.length];
				conList[0] = StringUtil.toString(map.get("id"));
				conList[1] = StringUtil.toString(map.get("userAccount"));
				conList[2] = StringUtil.toString(map.get("userRealname"));
				conList[3] = StringUtil.toString(map.get("userPhone"));
				conList[4] = StringUtil.toString(Recommend.ALL_RECOMMEND_STATUS.get(map.get("recommendStatus")));
				//conList[3] = StringUtil.toString(map.get("recommendMoney"));
				conList[5] = StringUtil.toString(map.get("recommendAddtime"));
				conList[6] = StringUtil.toString(map.get("recommendUser"));
				conList[7] = StringUtil.toString(map.get("recommendUserRealname"));
				conList[8] = StringUtil.toString(map.get("recommendUserPhone"));
				
				conList[9] = StringUtil.toString(map.get("verifyUser"));
				conList[10] = StringUtil.toString(map.get("verifyAddtime"));
				conList[11] = StringUtil.toString(map.get("verifyAddip"));
				conList[12] = StringUtil.toString(map.get("verifyRemark"));
				conList[13] = StringUtil.toString(map.get("recommendSequence"));

				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("accountRecharge-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "推广邀请记录统计数据", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}

	}
	
	/**
	 * 导出推广级数查询
	 * @param request
	 * @param response
	 */
	@RequestMapping("toExcelLevel")
	public void toExcelLevel(HttpServletRequest request, HttpServletResponse response) {
		/*Map<String, String> params = this.getParameters(request);
		params.put("numPerPage", "10000");*/
		try {
			Map<String, String> params = (Map) request.getSession().getAttribute("queryParams");
			params.put("pageNum", "1");
			params.put("numPerPage", "500000");
			params.remove("userId");
			
			PageModel pm = recommendService.selectCountRecommend4Admin(params);
			List<Map<String, String>> ars = pm.getList();

			System.out.println("---------export-- Recommends-number--->"
					+ ars.size());

			/*String[] titles = { "推荐人Id", "推荐人用户名", "推荐人姓名", "推荐人邮箱", "推荐人手机", "一级会员(个)",
					"二级会员(个)", "推荐总奖励"};*/
			
			String[] titles = { "推荐人Id", "推荐人用户名", "融腾内部员工号","推荐人姓名", "推荐人手机", "推荐人邮箱","推荐总奖励"};


			List<String[]> contents = new ArrayList<String[]>();
			for (Map<String, String> map : ars) {
				String[] conList = new String[11];
				conList[0] = StringUtil.toString(map.get("id"));
				conList[1] = StringUtil.toString(map.get("userAccount"));
				conList[2] = StringUtil.toString(map.get("rtUserFlag"));
				
				conList[3] = StringUtil.toString(map.get("userRealname"));
				conList[4] = StringUtil.toString(map.get("userPhone"));
				conList[5] = StringUtil.toString(map.get("userEmail"));
				/*conList[5] = StringUtil.toString(map.get("level1"));
				conList[6] = StringUtil.toString(map.get("level2"));*/
				conList[6] = StringUtil.toString(map.get("rewards"));
				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("recommendLevel" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "推广级数查询统计数据", titles, contents);
		} catch (Exception e) {
			
			logger.error("导出excel失败", e);
		}

	}
	
	
	
}
