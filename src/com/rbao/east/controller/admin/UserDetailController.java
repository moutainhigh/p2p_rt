package com.rbao.east.controller.admin;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.rbao.east.entity.EducationMessage;
import com.rbao.east.entity.FinanceMessage;
import com.rbao.east.entity.HouseMessage;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.OtherMessage;
import com.rbao.east.entity.PersonalMessage;
import com.rbao.east.entity.PrivateBusinessMessage;
import com.rbao.east.entity.RelationMessage;
import com.rbao.east.entity.SpouseMessage;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.UnitMessage;
import com.rbao.east.entity.User;
import com.rbao.east.service.EducationMessageService;
import com.rbao.east.service.FinanceMessageService;
import com.rbao.east.service.HouseMessageService;
import com.rbao.east.service.OtherMessageService;
import com.rbao.east.service.PersonalMessageService;
import com.rbao.east.service.PrivateBusinessMessageService;
import com.rbao.east.service.RelationMessageService;
import com.rbao.east.service.SpouseMessageService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UnitMessageService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.ExcelUtil;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.StringUtil;
/**
 * 用户详细信息
 * */
@Controller
@RequestMapping("userDetail/")
public class UserDetailController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(UserManagerController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private UnitMessageService unitMessageService;
	@Autowired
	private HouseMessageService houseMessageService;
	@Autowired
	private PersonalMessageService personalMessageService;
	@Autowired
	private PrivateBusinessMessageService privateBusinessMessageService;
	@Autowired
	private OtherMessageService otherMessageService;
	@Autowired
	private EducationMessageService educationMessageService;
	@Autowired
	private SpouseMessageService spouseMessageService;
	@Autowired
	private RelationMessageService relationMessageService;
	@Autowired
	private FinanceMessageService financeMessageService;
	
	/**
	 * 用户详情信息
	 * @param request
	 * @param response
	 * @param model
	 * @param userType
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getListUserMessage")
	public String getListUserMessage(HttpServletRequest request, HttpServletResponse response,
			Model model,User user) {
		
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		paramsMap.put("isSystem", Integer.toString(User.SYSTEMN));// 不是管理员
		Map<String, Object> result=new HashMap<String, Object>();
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		result.put("righSubtList", righSubtList);
		result.put("right_id", paramsMap.get("right_id"));
		PageModel pageModel = userService.listUserMessage(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",user);//用于搜索框保留值
		this.setParameters(result, request);
		return "usermanage/userDetails/userMessageDetails";
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "toExcel")
	public void toExcel(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> maps=getParameters(request);
		maps.put("isSystem", Integer.toString(User.SYSTEMN));// 不是管理员 
		maps.put("numPerPage", "500000");
		
		try {
			PageModel pageModel = userService.listUserMessage(maps);
			List list=pageModel.getList();
			
			String[] titles={"序号","用户名称","真实姓名","房产资料","单位资料","私营业主资料","财务状况","联系方式","配偶资料","教育背景","其他"};
			List<String[]> contents = new ArrayList<String[]>();
			
			for (int i = 0; i < list.size(); i++) {
				Map map=(Map)list.get(i);
				String[] str=new String[titles.length];
				str[0]=StringUtil.toString(map.get("id"));
				str[1]=StringUtil.toString(map.get("userAccount"));
				str[2]=StringUtil.toString(map.get("userRealName"));
				str[3]=StringUtil.toString(map.get("hMsg"));
				str[4]=StringUtil.toString(map.get("unMsg"));
				str[5]=StringUtil.toString(map.get("pbMsg"));
				str[6]=StringUtil.toString(map.get("fMsg"));
				str[7]=StringUtil.toString(map.get("rMsg"));
				str[8]=StringUtil.toString(map.get("sMsg"));
				str[9]=StringUtil.toString(map.get("eMsg"));
				str[10]=StringUtil.toString(map.get("otherMsg"));
				
				contents.add(str);
			}
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(("userDetail-data" + ".xls")
									.getBytes("UTF-8"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			ExcelUtil.buildExcel(os, "用户详细管理", titles, contents);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel失败");
		}
		
		
	}
	
	
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getUserMessage")
	public String getUserMessage(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		//request.setAttribute("right_id", request.getAttribute("right_id"));
		User user = userService.getById(Integer.parseInt(paramsMap.get("supportID")));
		model.addAttribute("user", user);
		this.setParameters(paramsMap, request);
		return "usermanage/userDetails/saveUserDetail";
	}
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW+"userinfoDetail")
	public String userinfo(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		model.addAttribute("right_id", paramsMap.get("right_id"));
		return "usermanage/saveUserDetail";
	}
	
	
	/**
	 * 个人资料详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"personalMessage")
	public String personalMessage(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		PersonalMessage personalMessage = personalMessageService.getByUserId(Integer.parseInt(paramsMap.get("supportID")));
		model.addAttribute("userId", Integer.parseInt(paramsMap.get("supportID")));
		if(personalMessage!=null){
			model.addAttribute("personalMessage", personalMessage);
		}
		this.setParameters(paramsMap, request);
		return "usermanage/userDetails/personalMessage";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT+"savePersonalMessage")
	public void savePersonalMessage(HttpServletRequest request,
			HttpServletResponse response, Model model,PersonalMessage personalMessage){
		boolean bool=false;
		OperatorLog operatorLog = new OperatorLog();
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		PersonalMessage personal = personalMessageService.getByUserId(Integer.parseInt(paramsMap.get("userId")));
		if(personal!=null){
			personalMessage.setId(personal.getId());
			bool=personalMessageService.save(personalMessage);
			operatorLog.setOperatorTitle("添加个人详细资料");
			operatorLog.setOperatorReturn(bool?"添加个人详细资料成功":"添加个人详细资料失败");
		}else{
			bool=personalMessageService.add(personalMessage);
			operatorLog.setOperatorTitle("修改个人详细资料");
			operatorLog.setOperatorReturn(bool?"修改个人详细资料成功":"修改个人详细资料失败");
		}
		/**
		 * 记录日志
		 */
		operatorLog.setLogUserid("操作人："+this.loginAdminUser(request).getUserAccount()+"用户Id："+Integer.parseInt(paramsMap.get("userId")));
		operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
		operatorLog.setOperatorParams(personalMessage.toString());
		operatorLog.setOperatorStatus(bool?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, bool, bool ? "成功" : "失败",
				DwzResult.CALLBACK_CLOSECURRENTDIALOG, paramsMap.get("right_id").toString());
		
	}
	/**
	 * 房产信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"houseMessage")
	public String houseMessage(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		HouseMessage houseMessage = houseMessageService.getByUserId(Integer.parseInt(paramsMap.get("supportID")));
		model.addAttribute("userId", Integer.parseInt(paramsMap.get("supportID")));
		if(houseMessage!=null){
			model.addAttribute("houseMessage", houseMessage);
		}
		this.setParameters(paramsMap, request);
		return "usermanage/userDetails/houseMessage";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveHouseMessage")
	public void saveHouseMessage(HttpServletRequest request,
			HttpServletResponse response, Model model,HouseMessage houseMessage){
		boolean bool = false;
		OperatorLog operatorLog = new OperatorLog();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		HouseMessage house = houseMessageService.getByUserId(Integer.parseInt(paramsMap.get("userId")));
		try {
			houseMessage.setYear(sdf.parse(paramsMap.get("year1")));
		} catch (ParseException e) {
			
		}
		if(house!=null){
			houseMessage.setId(house.getId());
			bool = houseMessageService.save(houseMessage);
			operatorLog.setOperatorTitle("添加 房产信息");
			operatorLog.setOperatorReturn(bool?"添加 房产信息成功":"添加 房产信息失败");
		}else{
			bool = houseMessageService.add(houseMessage);
			operatorLog.setOperatorTitle("修改 房产信息");
			operatorLog.setOperatorReturn(bool?"修改 房产信息成功":"修改 房产信息失败");
		}
		/**
		 * 记录日志
		 */
		operatorLog.setLogUserid("操作人"+this.loginAdminUser(request).getUserAccount()+ "用户Id："+houseMessage.getUserId());
		operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
		operatorLog.setOperatorParams(houseMessage.toString());
		operatorLog.setOperatorStatus(bool?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, bool, bool ? "成功" : "失败",
				DwzResult.CALLBACK_CLOSECURRENTDIALOG, paramsMap.get("right_id").toString());
	}
	
	/**
	 * 单位资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"unitMessage")
	public String unitMessage(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		UnitMessage unitMessage = unitMessageService.getByUserId(Integer.parseInt(paramsMap.get("supportID")));
		model.addAttribute("userId", Integer.parseInt(paramsMap.get("supportID")));
		if(unitMessage!=null){
			model.addAttribute("unitMessage", unitMessage);
		}
		this.setParameters(paramsMap, request);
		return "usermanage/userDetails/unitMessage";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveUnitMessage")
	public void saveUnitMessage(HttpServletRequest request,
			HttpServletResponse response, Model model,UnitMessage unitMessage){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean bool = false;
		OperatorLog operatorLog=new OperatorLog();
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		UnitMessage unit = unitMessageService.getByUserId(Integer.parseInt(paramsMap.get("userId")));
		try {
			unitMessage.setServeTime(sdf.parse(paramsMap.get("serveTime1")));
			unitMessage.setToServeTime(sdf.parse(paramsMap.get("toServeTime1")));
		} catch (ParseException e) {
			
		}
		if(unit!=null){
			unitMessage.setId(unit.getId());
			bool = unitMessageService.save(unitMessage);
			operatorLog.setOperatorTitle("添加单位资料");
			operatorLog.setOperatorReturn(bool?"添加单位资料成功":"添加单位资料失败");
		}else{
			bool = unitMessageService.add(unitMessage);
			operatorLog.setOperatorTitle("修改单位资料");
			operatorLog.setOperatorReturn(bool?"修改单位资料成功":"修改单位资料失败");
		}
		/**
		 * 记录日志
		 */
		operatorLog.setLogUserid("操作人"+this.loginAdminUser(request).getUserAccount()+"用户Id："+unitMessage.getUserId());
		operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
		operatorLog.setOperatorParams(unitMessage.toString());
		operatorLog.setOperatorStatus(bool?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, bool, bool ? "成功" : "失败",
				DwzResult.CALLBACK_CLOSECURRENTDIALOG, paramsMap.get("right_id").toString());
	}
	/**
	 * 私营业主资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"privateBusinessMessage")
	public String privatBusinessMessage(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		PrivateBusinessMessage privateBusinessMessage = privateBusinessMessageService.getByUserId(Integer.parseInt(paramsMap.get("supportID")));
		model.addAttribute("userId", Integer.parseInt(paramsMap.get("supportID")));
		if(privateBusinessMessage!=null){
			model.addAttribute("privateBusinessMessage", privateBusinessMessage);
		}
		this.setParameters(paramsMap, request);
		return "usermanage/userDetails/privateBusinessMessage";
	}

	@RequestMapping(Constants.PRE_PATH_EDIT+"savePrivateBusinessMessage")
	public void savePrivateBusinessMessage(HttpServletRequest request,
			HttpServletResponse response, Model model,PrivateBusinessMessage privateBusinessMessage){
		boolean bool = false;
		OperatorLog operatorLog=new OperatorLog();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		PrivateBusinessMessage privateBusiness = privateBusinessMessageService.getByUserId(Integer.parseInt(paramsMap.get("userId")));
		try {
			privateBusinessMessage.setEstablishTime(sdf.parse(paramsMap.get("establishTime1")));
		} catch (ParseException e) {
			
		}
		if(privateBusiness!=null){
			privateBusinessMessage.setId(privateBusiness.getId());
			bool = privateBusinessMessageService.save(privateBusinessMessage);
			operatorLog.setOperatorTitle("添加私营业主资料");
			operatorLog.setOperatorReturn(bool?"添加私营业主资料成功":"添加私营业主资料失败");
		}else{
			bool = privateBusinessMessageService.add(privateBusinessMessage);
			operatorLog.setOperatorTitle("修改私营业主资料");
			operatorLog.setOperatorReturn(bool?"修改私营业主资料成功":"修改私营业主资料失败");
		}
		/**
		 * 记录日志
		 */
		operatorLog.setLogUserid("操作人"+this.loginAdminUser(request).getUserAccount()+"用户Id："+privateBusinessMessage.getUserId());
		operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
		operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
		operatorLog.setOperatorParams(privateBusinessMessage.toString());
		operatorLog.setOperatorStatus(bool?200:300);
		operatorLog.setLinkUrl(RequestUtils.getIpAddr());
		operatorLogService.addAdminLog(operatorLog);
		SpringUtils.renderDwzResult(response, bool, bool ? "成功" : "失败",
				DwzResult.CALLBACK_CLOSECURRENTDIALOG, paramsMap.get("right_id").toString());
	}
	/**
	 * 用戶其他信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"otherMessage")
	public String userOtherMessage(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map<String, String> paramsMap = getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		OtherMessage other = otherMessageService.getByUserId(Integer.parseInt(paramsMap.get("supportID")));
		model.addAttribute("userId", Integer.parseInt(paramsMap.get("supportID")));
		if(other!=null){
			model.addAttribute("other", other);
		}
		this.setParameters(paramsMap, request);
		return "usermanage/userDetails/otherMessage";
	}
	
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveOtherMessage")
	public void saveOtherMessage(HttpServletRequest request,HttpServletResponse response, Model model,OtherMessage other){
		boolean flag=false;
		OperatorLog operatorLog=new OperatorLog();
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		try{
			OtherMessage o=this.otherMessageService.getByUserId(Integer.parseInt(param.get("userId")));
			if(o==null){
				flag=this.otherMessageService.add(other);
				operatorLog.setOperatorTitle("添加用戶其他信息");
				operatorLog.setOperatorReturn(flag?"添加用戶其他信息成功":"添加用戶其他信息失败");
			}else{
				other.setId(o.getId());
				flag=this.otherMessageService.save(other);
				operatorLog.setOperatorTitle("修改用戶其他信息");
				operatorLog.setOperatorReturn(flag?"修改用戶其他信息成功":"修改用戶其他信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("操作人"+this.loginAdminUser(request).getUserAccount()+"用户Id："+other.getUserId());
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			operatorLog.setOperatorParams(other.toString());
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addAdminLog(operatorLog);
		}catch(Exception e){
			
			flag=false;
			logger.error("添加或者修改用戶其他信息出错");
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENTDIALOG, param.get("right_id").toString());
	}
	
	/**
	 * 学历信息xiangxiaoyan
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"educationMessage")
	public String educationMessage(HttpServletRequest request,HttpServletResponse response, Model model){
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		EducationMessage edu=this.educationMessageService.getByUserId(Integer.parseInt(param.get("supportID")));
		model.addAttribute("userId", Integer.parseInt(param.get("supportID")));
		if(edu!=null){
			model.addAttribute("edu", edu);
		}
		this.setParameters(param, request);
		return "usermanage/userDetails/educationMessage";
	}
	
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveEducationMessage")
	public void saveEducationMessage(HttpServletRequest request,HttpServletResponse response, Model model,EducationMessage edu){
		boolean flag=false;
		OperatorLog operatorLog=new OperatorLog();
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			EducationMessage e=this.educationMessageService.getByUserId(Integer.parseInt(param.get("userId")));
			if(e==null){
				edu.setDateFrom(sdf.parse(param.get("datebegin")));
				edu.setDateTo(sdf.parse(param.get("dateend")));
				flag=this.educationMessageService.add(edu);
				operatorLog.setOperatorTitle("添加学历信息");
				operatorLog.setOperatorReturn(flag?"添加学历信息成功":"添加学历信息失败");
			}else{
				edu.setId(e.getId());
				edu.setDateFrom(sdf.parse(param.get("datebegin")));
				edu.setDateTo(sdf.parse(param.get("dateend")));
				flag=this.educationMessageService.save(edu);
				operatorLog.setOperatorTitle("修改学历信息");
				operatorLog.setOperatorReturn(flag?"修改学历信息成功":"修改学历信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("操作人"+this.loginAdminUser(request).getUserAccount()+"用户Id："+edu.getUserId());
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			operatorLog.setOperatorParams(edu.toString());
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addAdminLog(operatorLog);
		}catch(Exception e){
			
			flag=false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENTDIALOG, param.get("right_id").toString());
	}
	
	/**
	 * 配偶信息xiangxiaoyan
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"spouseMessage")
	public String spouseMessage(HttpServletRequest request,HttpServletResponse response, Model model){
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		Integer id=Integer.parseInt(param.get("supportID"));
		SpouseMessage spouse=this.spouseMessageService.getByUserId(id);
		model.addAttribute("userId", id);
		if(spouse!=null){
			model.addAttribute("spouse", spouse);
		}
		this.setParameters(param, request);
		return "usermanage/userDetails/spouseMessage";
	}
	
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveSpouseMessage")
	public void saveSpouseMessage(HttpServletRequest request,HttpServletResponse response, Model model,SpouseMessage spouse){
		boolean flag=false;
		OperatorLog operatorLog=new OperatorLog();
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		try{
			SpouseMessage s=this.spouseMessageService.getByUserId(Integer.parseInt(param.get("userId")));
			if(s==null){
				flag=this.spouseMessageService.add(spouse);
				operatorLog.setOperatorTitle("添加配偶信息");
				operatorLog.setOperatorReturn(flag?"添加配偶信息成功":"添加配偶信息失败");
			}else{
				spouse.setId(s.getId());
				flag=this.spouseMessageService.save(spouse);
				operatorLog.setOperatorTitle("修改配偶信息");
				operatorLog.setOperatorReturn(flag?"修改配偶信息成功":"修改配偶信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("操作人"+this.loginAdminUser(request).getUserAccount()+"用户Id："+spouse.getUserId());
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			operatorLog.setOperatorParams(spouse.toString());
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addAdminLog(operatorLog);
		}catch(Exception e){
			
			flag=false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENTDIALOG, param.get("right_id").toString());
	}
	
	
	/**
	 * 联系人信息xiangxiaoyan
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"relationMessage")
	public String relationMessage(HttpServletRequest request,HttpServletResponse response, Model model){
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		Integer id=Integer.parseInt(param.get("supportID"));
		RelationMessage rg=this.relationMessageService.getByUserId(id);
		model.addAttribute("userId", id);
		if(rg!=null){
			model.addAttribute("rg", rg);
		}
		this.setParameters(param, request);
		return "usermanage/userDetails/relationMessage";
	}
	
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveRelationMessage")
	public void saveRelationMessage(HttpServletRequest request,HttpServletResponse response, Model model,RelationMessage rg){
		boolean flag=false;
		OperatorLog operatorLog=new OperatorLog();
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		try{
			RelationMessage r=this.relationMessageService.getByUserId(Integer.parseInt(param.get("userId")));
			if(r==null){
				flag=this.relationMessageService.add(rg);
				operatorLog.setOperatorTitle("添加联系人信息");
				operatorLog.setOperatorReturn(flag?"添加联系人信息成功":"添加联系人信息失败");
			}else{
				rg.setId(r.getId());
				flag=this.relationMessageService.save(rg);
				operatorLog.setOperatorTitle("修改联系人信息");
				operatorLog.setOperatorReturn(flag?"修改联系人信息成功":"修改联系人信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("操作人"+this.loginAdminUser(request).getUserAccount()+"用户Id："+rg.getUserId());
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			operatorLog.setOperatorParams(rg.toString());
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addAdminLog(operatorLog);
		}catch(Exception e){
			
			flag=false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENTDIALOG, param.get("right_id").toString());
	}

	/**
	 * 个人财务信息xiangxiaoyan
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"financeMessage")
	public String financeMessage(HttpServletRequest request,HttpServletResponse response, Model model){
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		Integer id=Integer.parseInt(param.get("supportID"));
		FinanceMessage finance=this.financeMessageService.getByUserId(id);
		model.addAttribute("userId", id);
		if(finance!=null){
			model.addAttribute("finance", finance);
		}
		this.setParameters(param, request);
		return "usermanage/userDetails/financeMessage";
	}
	
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveFinanceMessage")
	public void saveFinanceMessage(HttpServletRequest request,HttpServletResponse response, Model model,FinanceMessage finance){
		boolean flag=false;
		OperatorLog operatorLog=new OperatorLog();
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		try{
			FinanceMessage f=this.financeMessageService.getByUserId(Integer.parseInt(param.get("userId")));
			if(f==null){
				flag=this.financeMessageService.add(finance);
				operatorLog.setOperatorTitle("添加个人财务信息");
				operatorLog.setOperatorReturn(flag?"添加个人财务信息成功":"添加个人财务信息失败");
			}else{
				finance.setId(f.getId());
				flag=this.financeMessageService.save(finance);
				operatorLog.setOperatorTitle("修改个人财务信息");
				operatorLog.setOperatorReturn(flag?"修改个人财务信息成功":"修改个人财务信息失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("操作人"+this.loginAdminUser(request).getUserAccount()+"用户Id："+finance.getUserId());
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			operatorLog.setOperatorParams(finance.toString());
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addAdminLog(operatorLog);
		}catch(Exception e){
			
			flag=false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENTDIALOG, param.get("right_id").toString());
	}

}
