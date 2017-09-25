package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.TreeModel;
import com.rbao.east.entity.User;
import com.rbao.east.entity.UserType;
import com.rbao.east.service.AttestationTypeService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserService;
import com.rbao.east.service.UserTypeService;
import com.rbao.east.utils.DesEncrypt;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.MD5Utils;
import com.rbao.east.utils.PropertiesUtil;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;
import com.rbao.east.utils.TreeUtils;
/**
 * 用户管理
 * */
@Controller
@RequestMapping("user/")
public class UserManagerController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(UserManagerController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private AttestationTypeService attestationTypeService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private UserTypeService userTypeService;
	

	/**
	 * xiangxiaoyan 用户信息，管理前台 分页查询所有前台用户信息
	 * @throws ParseException 
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getUserPage")
	public String getUserPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		paramsMap.put("isSystem", Integer.toString(User.SYSTEMN));// 不是管理员
		paramsMap.put("userBeginId",PropertiesUtil.get("SYSTEM.USER.BEGIN.ID"));
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		paramsMap.put("isBlackList", User.IS_BLACK_LIST_NO.toString());
		
		PageModel page = userService.getPagedList(paramsMap);
		
		
		model.addAttribute("pm", page);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		
		request.getSession(true).setAttribute("pageModel", page);
		request.getSession(true).setAttribute("queryParams", paramsMap);
		return "usermanage/userList";
	}
	
	/**
	 * 用户信息管理 导出Excel
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> maps = (Map<String, String>) request.getSession().getAttribute("queryParams");
			//maps.put("numPerPage", "500000");

			//PageModel pm = userService.getPagedList(maps);
			List<User> list = userService.selectUserListToExcel(maps);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String[] titles = { "序号", "用户类型", "用户名称", "真实姓名", "性别", "邮箱", "电话",
					"手机", "民族", "证件类型", "证件号","银行卡", "注册时间","融腾内部员工号" };

			List<String[]> contents = new ArrayList<String[]>();
			for (int i = 0; i < list.size(); i++) {

				User user = list.get(i);
				String[] conList = new String[titles.length];
				conList[0] = StringUtil.toString(user.getId());

				if (user.getType() != null) {
					conList[1] = StringUtil.toString(user.getType().getName());
				} else {
					conList[1] = StringUtil.toString("");
				}
				conList[2] = StringUtil.toString(user.getUserAccount());
				conList[3] = StringUtil.toString(user.getUserRealname());
				conList[4] = StringUtil.toString(user.getUserSex());
				conList[5] = StringUtil.toString(user.getUserEmail());
				conList[6] = StringUtil.toString(user.getUserTel());

				conList[7] = StringUtil.toString(user.getUserPhone());
				conList[8] = StringUtil.toString(user.getUserNation());
				conList[9] = StringUtil.toString(user.getViewCard());
				
				conList[10] = StringUtil.toString(user.getCardNumber());
				
				conList[11] = StringUtil.toString(user.getBankAccount());
				
				if (null != user.getUserAddtime()) {
					conList[12] = StringUtil.toString(dateFormat.format(user
							.getUserAddtime()));
				} else {
					conList[12] = "";
				}

				conList[13] = StringUtil.toString(user.getRtUserFlag());
				
				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(("user-data" + ".xls").getBytes("UTF-8"),
							"ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "用户信息管理", titles, contents);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出excel失败", e);
		}

	}
	

	/**
	 * 弹出层，所有前台用户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"showSelectedUserList")
	public String showSelectedUserList(HttpServletRequest request,HttpServletResponse response,Model model){

		Map<String, String> param=this.getParameters(request);
	
		PageModel list = this.userService.selectForSelectedList(param);
		model.addAttribute("pm", list);
		model.addAttribute("param",param); 
		
		return "usermanage/showSelectedUserList";
		
	}
	/**
	 * xiangxiaoyan 跳转到添加页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "toInsertUser")
	public String getSaveUser(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		List<UserType> type=this.userTypeService.selectByType(UserType.frontType);
		model.addAttribute("type", type);
		model.addAttribute("right_id", param.get("right_id"));
		return "usermanage/saveUser";
	}
	/**
	 * 弹出层，所有前台用户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"getAllowBorrowUserList")
	public String getAllowBorrowUserList(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, String> param=this.getParameters(request);
		
		PageModel list = this.userService.selectForSelectedList(param);
		model.addAttribute("pm", list);
		model.addAttribute("param",param); 
		
		/*Map<String, Object> result=new HashMap<String,Object>();
		Map<String, String> param=this.getParameters(request);
		try {
				List<TreeModel> allFrontUser=userService.allFrontUserList();
				String outGroupHtml=TreeUtils.getBorrowedUser(allFrontUser);
				result.put("right_id", param.get("right_id"));
				result.put("userId", param.get("supportID"));
				result.put("outGroupHtml", outGroupHtml);
				result.put("code", 1);
		}catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
		}
		this.setParameters(result, request);*/
		
		return "usermanage/allAllowBorrowUserList";
		
	}
	/**
	 * xiangxiaoyan 添加，修改用户信息
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveUser")
	public void saveUser(HttpServletRequest request,
			HttpServletResponse response, User user, Model model) {
		// @RequestParam(value = "file", required = false) MultipartFile file2,
		boolean flag = false;
		User users=null;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		try {
			String ip = this.getIpAddr(request); 
			user.setUserAddip(ip);
			users= userService.selectByUserUid(user.getUserAccount());
			if(user.getId()==null){
				if(users==null){
					//加密密码
					DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
					user.setUserPassword(MD5Utils.md5(aesEncrypt.encrypt(User.DEFAULT_PWD)));
					flag = this.userService.saveOrUpdate(user);
				}else{
					flag=false;
					SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败，该用户名已存在",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
				}
			}else{
				user.setId(users.getId());
				flag=this.userService.saveOrUpdate(user);
			}
			
		} catch (Exception e) {
			flag = false;
			
			logger.error("添加或者修改信息出错");
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("添加或者修改用户");
		operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorParams(user.toString());
		operatorLog.setOperatorReturn(flag?"添加或者修改用户成功":"添加或者修改用户失败");
		operatorLog.setOperatorStatus(flag?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
		
	}

	/**
	 * xiangxiaoyan 获取单个用户信息，跳转到修改页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "toUpdateUser")
	public String getSaveUserById(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		Map<String, String> params = this.getParameters(request);
		params.put("id", param.get("supportID"));
		params.put("isSystem", Integer.toString(User.SYSTEMN));
		User user = this.userService.getByIdParam(params);
		List<UserType> type=this.userTypeService.selectByType(UserType.frontType);
		model.addAttribute("type", type);
		model.addAttribute("user", user);
		System.out.println(params.get("supportID")+"======================");
		return "usermanage/saveUser";
	}
	
	/**
	 * 黑名单列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getUserBlackPage")
	public String getUserBlackPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		paramsMap.put("isSystem", Integer.toString(User.SYSTEMN));// 不是管理员
		paramsMap.put("userBeginId",PropertiesUtil.get("SYSTEM.USER.BEGIN.ID"));
		paramsMap.put("isBlackList", User.IS_BLACK_LIST_YES.toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel page = userService.getPagedList(paramsMap);
		model.addAttribute("pm", page);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		
		request.getSession(true).setAttribute("queryParams", paramsMap);
		return "usermanage/userBlackList";
	}
	
	
	@RequestMapping("toExcelLevel")
	public void toExcelLevel(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			Map<String, String> paramsMap = (Map<String, String>) request.getSession().getAttribute("queryParams");
			paramsMap.put("pageNum", "1");
			paramsMap.put("numPerPage", "500000");
			
			paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
			paramsMap.put("isSystem", Integer.toString(User.SYSTEMN));// 不是管理员
			paramsMap.put("userBeginId",PropertiesUtil.get("SYSTEM.USER.BEGIN.ID"));
			paramsMap.put("isBlackList", User.IS_BLACK_LIST_YES.toString());
			PageModel page = userService.getPagedList(paramsMap);
			List<User> ars = page.getList();

			System.out.println("---------export-- Recommends-number--->"
					+ ars.size());

			String[] titles = {"序号", "用户类型", "用户名", "真实姓名", "性别", "邮箱", "电话", "手机","证件类型","证件号","注册时间","拉黑时间"};
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			List<String[]> contents = new ArrayList<String[]>();
			for (User user: ars) {
				String[] conList = new String[12];
				conList[0] = user.getId().toString();
				conList[1] =user.getType().getName(); 
				conList[2] =user.getUserAccount(); 
				conList[3] =user.getUserRealname(); 
				conList[4] =user.getUserSex(); 
				conList[5] =user.getUserEmail(); 
				conList[6] =user.getUserTel(); 
				conList[7] =user.getUserPhone(); 
				conList[8] =user.getViewCard(); 
				conList[9] =user.getCardNumber(); 
				conList[10]="";
				if( null != user.getUserAddtime()){
					conList[10]=dateFormat.format(user.getUserAddtime());
				}
				conList[11]="";
				if( null != user.getAddBlackTime() ){
					conList[11]=dateFormat.format(user.getAddBlackTime());
				}
				contents.add(conList);
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("blacklist" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "黑名单导出", titles, contents);
		} catch (Exception e) {
			logger.error("导出excel失败", e);
		}

	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "addBlackList")
	public void addBlackList(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> param = this.getParameters(request);
		boolean flag = false;
		User user=new User();
		try{
			user.setIsBlackList(User.IS_BLACK_LIST_YES);
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			user.setAddBlackTime(new Date());
			user.setId(Integer.parseInt(param.get("supportID").toString()));
			flag=this.userService.saveOrUpdate(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(this.loginAdminUser(request).getUserAccount());
		operatorLog.setOperatorTitle("加入黑名单");
		operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorParams(user.toString());
		operatorLog.setOperatorReturn(flag?"加入黑名单成功":"加入黑名单失败");
		operatorLog.setOperatorStatus(flag?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);

		SpringUtils.renderDwzResult(response, flag, flag ? "加入黑名单成功": "加入黑名单失败", "", param.get("right_id").toString(),"user/v_getUserPage?right_id="+ param.get("right_id").toString());
		logger.info("加入黑名单成功");
	}
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateBlackList")
	public void updateBlackList(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> param = this.getParameters(request);
		boolean flag = false;
		User user=new User();
		try{
			user.setIsBlackList(User.IS_BLACK_LIST_NO);
			user.setId(Integer.parseInt(param.get("supportID").toString()));
			flag=this.userService.saveOrUpdate(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(this.loginAdminUser(request).getUserAccount());
		operatorLog.setOperatorTitle("移除黑名单");
		operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorParams(user.toString());
		operatorLog.setOperatorReturn(flag?"移除黑名单成功":"移除黑名单失败");
		operatorLog.setOperatorStatus(flag?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);

		SpringUtils.renderDwzResult(response, flag, flag ? "移除黑名单成功": "移除黑名单失败", "", param.get("right_id").toString(),"user/v_getUserBlackPage?right_id="+ param.get("right_id").toString());
		logger.info("移除黑名单成功");
	}
	
	/**
	 * xiangxiaoyan 删除用户
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "toDeleteUser")
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		boolean flag = false;
		User userAdmin=this.loginAdminUser(request);
		User user = new User();
		try {
			// param.get("supportID").toString())=1为超级管理员，不能删除
				user.setId(Integer.parseInt(param.get("supportID").toString()));
				user.setIsSystem(User.SYSTEMN);
				flag = this.userService.deleteFrontUserById(user);
		} catch (Exception e) {
			
			logger.error("删除用户出错："+ Integer.parseInt(param.get("supportID").toString()), e);
		}
		
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(userAdmin.getUserAccount());
		operatorLog.setOperatorTitle("删除用户");
		operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorParams(user.toString());
		operatorLog.setOperatorReturn(flag?"删除用户成功":"删除用户失败");
		operatorLog.setOperatorStatus(flag?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);

		SpringUtils.renderDwzResult(response, flag, flag ? "删除用户成功": "删除用户失败", "", param.get("right_id").toString(),"user/v_getUserPage?right_id="+ param.get("right_id").toString());
		logger.info("删除用户成功");
		
	}

	/**
	 * 查看用户信息详细
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getUserDetail")
	public String getDetailUserInfo(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		Map<String, String> params = this.getParameters(request);
		params.put("id", param.get("supportID"));
		params.put("isSystem", Integer.toString(User.SYSTEMN));
		User user = this.userService.getByIdParam(params);
		model.addAttribute("user", user);
		return "usermanage/userDetail";
	}

	/**
	 * 用户信息，后台管理 分页查询所有用户信息
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAdminUserPage")
	public String getAdminUserPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = getParameters(request);
		Map<String, Object> result=new HashMap<String,Object>();
		param.put("userId", Integer.toString(this.loginAdminUser(request).getId()));
		result.put("isSystem", Integer.toString(User.SYSTEMY));// 是管理员

		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(param);
		model.addAttribute("righSubtList", righSubtList);
		Integer currentPage=param.containsKey(Constants.PAGED_CURPAGE)?
				Integer.valueOf(param.get(Constants.PAGED_CURPAGE).toString()):1;
		if(param.containsKey("userAccount")){
			result.put("userAccount", param.get("userAccount"));
		}
		if(param.containsKey("userRealname")){
			result.put("userRealname", param.get("userRealname"));
		}
		if(param.containsKey("userSex")){
			result.put("userSex", param.get("userSex"));
		}
		if(param.containsKey("cardNumber")){
			result.put("cardNumber", param.get("cardNumber"));
		}
		if(param.containsKey("userPhone")){
			result.put("userPhone", param.get("userPhone"));
		}
		if(param.containsKey("numPerPage")){
			result.put("numPerPage", param.get("numPerPage"));
		}
		//id》=100才显示
		if(!PropertiesUtil.get("SYSTEM.USER.BEGIN.ID").equals(null)){
			result.put("userBeginID", PropertiesUtil.get("SYSTEM.USER.BEGIN.ID"));
		}
		PageModel page = userService.getPageModel(result,currentPage);
		model.addAttribute("pm", page);
		model.addAttribute("right_id", param.get("right_id"));
		model.addAttribute("searchParams",result);
		return "usermanage/adminUserList";
	}

	/**
	 * xiangxiaoyan 跳转到添加页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "toAdminInsertUser")
	public String getAdminSaveUser(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		return "usermanage/adminSaveUser";
	}

	/**
	 * 添加或修改用户信息
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "toAdminSaveUser")
	public void adminSaveUser(HttpServletRequest request,
			HttpServletResponse response, User user, Model model) {
		// @RequestParam(value = "file", required = false) MultipartFile file2,
		boolean flag = false;
		User users=null;
		User userAdmin=this.loginAdminUser(request);
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		try {
			if (user.getId() == null) {
				user.setIsSystem(User.SYSTEMY);
				//加密密码
				DesEncrypt aesEncrypt = new DesEncrypt(Constants.DES_PRIVATE_ENCRYPT_KEY);
				user.setUserPassword(MD5Utils.md5(aesEncrypt.encrypt(User.DEFAULT_PWD)));
			}
			String ip = this.getIpAddr(request); 
			user.setUserAddip(ip);
			users= userService.selectByUserUid(user.getUserAccount());
			if(user.getId()==null){
				if(users==null){
					flag = this.userService.saveOrUpdate(user);
				}else{
					flag=false;
					SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败，该用户名已存在",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
				}
			}else{
				if(users != null && !user.getId().equals(users.getId())){
					SpringUtils.renderDwzResult(response, false, "用户名已经存在，请重新输入");
					return ;
				}
				flag=this.userService.saveOrUpdate(user);
			}
			
		} catch (Exception e) {
			flag = false;
			
			logger.error("添加或者修改信息出错");
		}
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(userAdmin.getUserAccount());
		operatorLog.setOperatorTitle("添加或修改用户信息");
		operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorParams(user.toString());
		operatorLog.setOperatorReturn(flag?"添加或修改用户信息成功":"添加或修改用户信息失败");
		operatorLog.setOperatorStatus(flag?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
		
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
		
	}

	/**
	 * xiangxiaoyan 获取单个用户信息，跳转到修改页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "toAdminUpdateUser")
	public String getAdminSaveUserById(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		Map<String, String> params = this.getParameters(request);
		params.put("id", param.get("supportID"));
		params.put("isSystem", Integer.toString(User.SYSTEMY));
		User user = this.userService.getByIdParam(params);
		model.addAttribute("user", user);
		return "usermanage/adminSaveUser";
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "toAdminDeleteUser")
	public void AdminDeleteUser(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		User user = new User();
		User userAdmin=this.loginAdminUser(request);
		boolean flag = false;
		try {
			// param.get("supportID").toString())=1为超级管理员，不能删除
			
				user.setId(Integer.parseInt(param.get("supportID").toString()));
				user.setIsSystem(User.SYSTEMY);
				flag = this.userService.deleteById(user);
		} catch (Exception e) {
			
			SpringUtils.renderDwzResult(response, false,e.getLocalizedMessage(),"", param.get("right_id").toString());
			logger.error(
					"删除用户出错："
							+ Integer.parseInt(param.get("supportID").toString()), e);
		}
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(userAdmin.getUserAccount());
		operatorLog.setOperatorTitle("删除用户信息");
		operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorParams(user.toString());
		operatorLog.setOperatorReturn(flag?"删除用户成功":"删除用户失败");
		operatorLog.setOperatorStatus(flag?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);

		SpringUtils.renderDwzResult(response, flag, flag ? "删除用户成功": "删除用户失败", "", param.get("right_id").toString(),"user/v_getUserPage?right_id="+ param.get("right_id").toString());
		logger.info("删除用户成功");
		
	}

	/**
	 * xiangxiaoyan 查看详情
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAdminUserDetail")
	public String getAdminDetailUserInfo(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		Map<String, String> params = this.getParameters(request);
		params.put("id", param.get("supportID"));
		params.put("isSystem", Integer.toString(User.SYSTEMY));
		User user = this.userService.getByIdParam(params);
		model.addAttribute("user", user);
		return "usermanage/adminUserDetail";
	}

	/**
	 * 证件类型管理
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getAttestationTypePage")
	public String getAttestationTypePage(HttpServletRequest request,
			HttpServletResponse response, Model model, User user) {
		Map<String, String> paramsMap = getParameters(request);
		PageModel page = attestationTypeService.getList(paramsMap);
		model.addAttribute("page", page);
		model.addAttribute("searchParams", user);// 用于搜索框保留值
		model.addAttribute("rel", request.getParameter("rel"));
		return "usermanage/userList";
	}
	
	/**
	 * 用戶授權
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveUserRole")
	public String saveUserRole(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> result=new HashMap<String,Object>();
		Map<String, String> param=this.getParameters(request);
		User user=this.loginAdminUser(request);
		String target="";
		boolean bool=false;
		try {
			if("toJsp".equals(param.get("type").toString())){
				List<TreeModel> adminOutRole=userService.getUserRoleListByUserId(user.getId());
				List<TreeModel> adminHaveRole=userService.getUserRoleListByUserId(Integer.parseInt(param.get("supportID").toString()));
				String outGroupHtml=TreeUtils.getTreeStringWidthCheckBoxOne(adminOutRole, adminHaveRole, "roleIds");
				result.put("right_id", param.get("right_id"));
				result.put("userId", param.get("supportID"));
				result.put("outGroupHtml", outGroupHtml);
				result.put("code", 1);
				target="usermanage/shwoUsersRole";
			}else if("update".equals(param.get("type").toString())){
				String[] roleIds=request.getParameterValues("roleIds");
				result.put("roles", roleIds);
				result.put("right_id", param.get("right_id"));
				result.put("userId", param.get("userId"));
				bool=userService.addUserRole(result);
				target=null;
				SpringUtils.renderDwzResult(response, bool, bool ? "用户授权成功" : "用户授权失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
				logger.info("用户授权成功");
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("用户授权");
				operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorParams(result.toString());
				operatorLog.setOperatorReturn(bool?"用户授权成功":"用户授权失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
			}
		}catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("用户授权失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return target;
	}
	
	/**
	 * 用户类型管理
	 * @param request
	 * @param response
	 * @param model
	 * @param userType
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getListUserType")
	public String getListUserType(HttpServletRequest request, HttpServletResponse response,
			Model model,UserType userType) {
		
		Map<String,String> paramsMap = getParameters(request);
		Map<String, Object> result=new HashMap<String, Object>();
		paramsMap.put("userId", "1");
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		result.put("righSubtList", righSubtList);
		result.put("right_id", paramsMap.get("right_id"));
		PageModel pageModel = userTypeService.getList(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",userType);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		
		this.setParameters(result, request);
		return "usermanage/usertypelist";
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "get1UserType")
	public String get1UserType(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		return "usermanage/updateusertype";
	}
	@RequestMapping(Constants.PRE_PATH_VIEW + "getUserType")
	public String getUserType(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		//request.setAttribute("right_id", request.getAttribute("right_id"));
		UserType userType = userTypeService.getById(Integer.parseInt(paramsMap.get("supportID")));
		
		model.addAttribute("userType", userType);
		this.setParameters(paramsMap, request);
		return "usermanage/updateusertype";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveUserType")
	public void saveUserType(HttpServletRequest request, HttpServletResponse response,
			UserType userType) {
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		User user=this.loginAdminUser(request);
		try {
			if(userType.getId()==null){
				succ = userTypeService.add(userType);
			}else{
				succ = userTypeService.save(userType);
			}
		} catch (Exception e) {
			
			/*logger.error("添加用户出错："+creditType.getName(),e);*/
		}
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("添加或修改用户类型");
		operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorParams(userType.toString());
		operatorLog.setOperatorReturn(succ?"添加用户类型成功":"添加用户类型失败");
		operatorLog.setOperatorStatus(succ?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, succ, succ?"成功":"失败"
			,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "deleteUserType")
	public void deleteUserType(HttpServletRequest request, HttpServletResponse response
			) {
		Map<String,String> paramsMap = getParameters(request);
		User user=this.loginAdminUser(request);
		boolean succ = false;
		try {
			succ = userTypeService.deleteById(Integer.parseInt(paramsMap.get("supportID")));
		} catch (Exception e) {
			
			logger.error("删除出错："+paramsMap.get("supportID"),e);
		}
		OperatorLog operatorLog = new OperatorLog();
		operatorLog.setLogUserid(user.getUserAccount());
		operatorLog.setOperatorTitle("删除用户类型");
		operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorParams(paramsMap.get("supportID").toString());
		operatorLog.setOperatorReturn(succ?"删除用户类型成功":"删除用户类型失败");
		operatorLog.setOperatorStatus(succ?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, succ, succ?"删除成功":"删除失败","",paramsMap.get("right_id").toString(),"user/v_getListUserType?right_id="+paramsMap.get("right_id").toString());
	}
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateUserPassWord")
	public String updateUserPassWord(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> result=new HashMap<String,Object>();
		Map<String, String> param=this.getParameters(request);
		User user=this.loginAdminUser(request);
		String target="";
		boolean bool=false;
		try {
				if("toJsp".equals(param.get("type").toString())){
					result.put("code", 1);
					target="usermanage/updateUser";
				}else if("update".equals(param.get("type").toString())){
						User oldUser=userService.getById(user.getId());
						DesEncrypt desEncrypt = new DesEncrypt();
						String oldPassword=MD5Utils.md5(desEncrypt.encrypt(param.get("oldPassword").toString()));
						String newPassword=MD5Utils.md5(desEncrypt.encrypt(param.get("newPassword").toString()));
						if(oldPassword.toLowerCase().equals(oldUser.getUserPassword().toLowerCase())){
							oldUser.setId(user.getId());
							oldUser.setUserPassword(newPassword);
					 		bool = this.userService.saveOrUpdate(oldUser);
					 		SpringUtils.renderDwzResult(response, bool, bool ? "修改密码成功" : "修改密码失败",
					 			DwzResult.CALLBACK_CLOSECURRENT);
						}else{
							SpringUtils.renderDwzResult(response, bool, bool ? "" : "您的旧密码和数据库不一致，请稍后再操作！",
					 			DwzResult.CALLBACK_CLOSECURRENT);
						}
						target=null;
						OperatorLog operatorLog = new OperatorLog();
						operatorLog.setLogUserid(user.getUserAccount());
						operatorLog.setOperatorTitle("修改密码");
						operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
						operatorLog.setOperatorParams(oldUser.toString());
						operatorLog.setOperatorReturn(bool?"修改密码成功":"修改密码失败");
						operatorLog.setOperatorStatus(bool?200:300);
						operatorLog.setLinkUrl(RequestUtils.getIpAddr());
						operatorLogService.addAdminLog(operatorLog);
				}
		}catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("修改密码失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return target;
	}
	
}
