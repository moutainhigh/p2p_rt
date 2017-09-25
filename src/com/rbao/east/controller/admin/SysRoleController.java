package com.rbao.east.controller.admin;

import java.util.Collections;
import java.util.Comparator;
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
import com.rbao.east.entity.SysRole;
import com.rbao.east.entity.TreeModel;
import com.rbao.east.entity.User;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.SysRoleService;
import com.rbao.east.utils.PropertiesUtil;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.TreeUtils;
/**
 * 角色管理
 * */
@Controller
@RequestMapping("role/")
public class SysRoleController extends BaseController{

	private static Logger logger = LoggerFactory
			.getLogger(SysRoleController.class);
	
	
	
	@Autowired
	private SysRoleService roleService;
	
	@Autowired
	private SysModuleService moduleService;


	@RequestMapping(Constants.PRE_PATH_VIEW + "getRoleList")
	public String getRoleList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, String> param=this.getParameters(request);
		String target="";
		User user=this.loginAdminUser(request);
		try {
			//得到UserId查询对应的权限
			List<TreeModel> treeModels=roleService.getSysRoleTreeByUserId(user.getId());
			softList(treeModels);
			String url="role/v_getRoleListInfo?right_id="+param.get("right_id")+"&roleId=";
			String roleList = "";
			if(treeModels.size()>0){
				roleList =TreeUtils.getTreeModelStrings(url,"roleBox",treeModels.get(0).getPid(),0, treeModels);
			}			
			result.put("roleList",roleList.replaceAll("<ul ></ul>",""));
			if(!param.containsKey("right_id")){
				String right_id=roleList.substring(roleList.indexOf("right_id=")+9,roleList.indexOf("&"));
				param.put("right_id", right_id);
			}
			if(!param.containsKey("roleId")){
				String roleId=roleList.substring(roleList.indexOf("roleId=")+7,roleList.indexOf("target")-1);
				param.put("roleId", roleId);
			}
			getRoleListInfo(param, request);
			result.put("code", 1);
			target="role/listRoleSearch";
			logger.info("显示角色权限树成功！");
		} catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("展示角色权限树失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return target;
	}
	
	
	public void getRoleListInfo(Map<String, String> param,HttpServletRequest request){
		Map<String, Object> result=new HashMap<String,Object>();
		User user=this.loginAdminUser(request);
		try {
			if(param.containsKey("right_id")&&param.containsKey("roleId")){
				int beginUserId = 0;
				beginUserId = Integer.parseInt(PropertiesUtil.get("SYSTEM.USER.BEGIN.ID"));
				param.put("userId", user.getId().toString());
				// 获得当前登录用户的rightId下的子权限
				List<SysModule> righSubtList=moduleService.getRightGroupList(param);
				result.put("righSubtList", righSubtList);
				Integer currentPage=param.containsKey(Constants.PAGED_CURPAGE)?
						Integer.valueOf(param.get(Constants.PAGED_CURPAGE).toString()):1;
				if(param.containsKey("role_name")){
					result.put("role_name", param.get("role_name"));
					param.put("role_name", "%"+param.get("role_name")+"%");
				}
				if(user.getId().intValue() < beginUserId){
					param.put("userId", null);
				}
				PageModel pageModel = roleService.getSysRolePage(param, currentPage);
				result.put("pm", pageModel);
				result.put("code", 1);
				result.put("right_id", param.get("right_id"));
				result.put("roleId", param.get("roleId"));
				logger.info("显示角色列表成功！");
			}else{
				result.put("code", 0);
				result.put("message", "数据操作出现异常，请稍后再试！");
			}
		} catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("展示角色列表失败，异常信息:" + e);
		}
		this.setParameters(result, request);
	}
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getRoleListInfo")
	public String getModuleListInfo(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		getRoleListInfo(param,request);
		return "role/listRoleSearchInfo";
	}
	
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveRole")
	public String saveRole(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		Map<String, Object> result=new HashMap<String,Object>();
		User user=this.loginAdminUser(request);
		String target="";
		boolean bool=false;
		try {
			if("toJsp".equals(param.get("type").toString())){
				request.setAttribute("right_id", param.get("right_id"));
				target="role/saveSysRole";
			}else if("add".equals(param.get("type").toString())){
				SysRole role=new SysRole();
				role.setRoleAddip(this.getIpAddr(request));
				role.setRoleName(param.get("role_name"));
				role.setRoleRemark(param.get("role_remark"));
				role.setRoleStatus(Integer.parseInt(param.get("roleStatus").toString()));
				role.setRoleSummary(param.get("role_summary"));
				if(param.containsKey("district.id")){
					role.setRoleSuper(Integer.parseInt(param.get("district.id").toString()));
				}else{
					role.setRoleSuper(0);
				}
				result.put("role", role);
				result.put("userId", user.getId());
				bool=roleService.saveSysRole(result);
				SpringUtils.renderDwzResult(response, bool, bool?"添加角色成功":"添加角色失败"
					,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());	
				target=null;
				logger.info("添加角色成功");
				//添加日志
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("添加角色");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_AUTHORITY);
				operatorLog.setOperatorParams(result.toString());
				operatorLog.setOperatorReturn(bool?"添加角色成功":"添加角色失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
			}
		} catch (Exception e) {
			
			logger.error("添加角色失败，异常信息:" + e);
		}
		return target;
	}
	
	
	

	@RequestMapping(Constants.PRE_PATH_EDIT + "updateRole")
	public String updateRole(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		Map<String, Object> result=new HashMap<String,Object>();
		User user=this.loginAdminUser(request);
		String target="";
		try {
			if("toJsp".equals(param.get("type").toString())){
				SysRole role=roleService.selectSysRoleById(Integer.parseInt(param.get("supportID").toString()));
				request.setAttribute("right_id", param.get("right_id"));
				request.setAttribute("role",role);
				target="role/updateSysRole";
			}else if("update".equals(param.get("type").toString())){
				SysRole role=new SysRole();
				role.setId(Integer.parseInt(param.get("id").toString()));
				role.setRoleAddip(this.getIpAddr(request));
				role.setRoleName(param.get("role_name"));
				role.setRoleRemark(param.get("role_remark"));
				role.setRoleStatus(Integer.parseInt(param.get("roleStatus").toString()));
				role.setRoleSummary(param.get("role_summary"));
				if(param.containsKey("district.id")){
					role.setRoleSuper(Integer.parseInt(param.get("district.id").toString()));
				}else{
					role.setRoleSuper(0);
				}
				boolean bool=roleService.updateSysRole(role);
				SpringUtils.renderDwzResult(response, bool, bool?"修改角色成功":"修改角色失败"
					,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());	
				target=null;
				logger.info("修改角色成功");
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("修改角色");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_AUTHORITY);
				operatorLog.setOperatorParams(result.toString());
				operatorLog.setOperatorReturn(bool?"修改角色成功":"修改角色失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
			}
		} catch (Exception e) {
			
			logger.error("修改角色失败，异常信息:" + e);
		}
		return target;
	}
	
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "deleteRole")
	public void deleteRole(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		Map<String, Object> result=new HashMap<String,Object>();
		User user=this.loginAdminUser(request);
		try {
				Integer id=Integer.parseInt(param.get("supportID").toString());
				boolean bool=roleService.deleteSysRoleById(id);
				SpringUtils.renderDwzResult(response, bool, bool?"删除角色成功":"删除角色失败","",param.get("right_id").toString(),"role/v_getRoleList?right_id="+param.get("right_id").toString()); 
				logger.info("删除角色成功");
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("删除角色");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_AUTHORITY);
				operatorLog.setOperatorParams(id.toString());
				operatorLog.setOperatorReturn(bool?"删除角色成功":"删除角色失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
		} catch (Exception e) {
			
			logger.error("删除角色信息失败，异常信息:" + e);
		}
	}
	
	/**
	 * 显示角色树
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "showRoleList")
	public String shwoRightList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> result=new HashMap<String,Object>();
		User user=this.loginAdminUser(request);
		try {
			//得到UserId查询对应的权限
			List<TreeModel> treeModels=roleService.getSysRoleTreeByUserId(user.getId());
			String roleTreeModelHtml=TreeUtils.getCallBackTreeString(0,0, treeModels);
			result.put("roleTreeModelHtml",roleTreeModelHtml);
			result.put("code", 1);
			logger.info("显示角色树成功！");
		} catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("展示角色树失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return "role/showRoleTree";
	}
	
	/**
	 * 得到角色权限树
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "showRoleModuleList")
	public String showRoleModuleList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> result=new HashMap<String,Object>();
		Map<String, String> param=this.getParameters(request);
		User user=this.loginAdminUser(request);
		String target="";
		try {
			if("toJsp".equals(param.get("type").toString())){
				List<TreeModel> adminOutRight=moduleService.getTreeModelByUserId(user.getId());
				List<TreeModel> adminHaveRight=moduleService.getTreeModelByRoleId(Integer.parseInt(param.get("supportID").toString()));
				String outGroupHtml=TreeUtils.getTreeStringWidthCheckBox(0, 0, adminOutRight, adminHaveRight, "rightIds");
				result.put("right_id", param.get("right_id"));
				result.put("role_id", param.get("supportID"));
				result.put("outGroupHtml", outGroupHtml);
				result.put("code", 1);
				target="role/shwoRolesRight";
			}else if("update".equals(param.get("type").toString())){
				String[] rightIds=request.getParameterValues("rightIds");
				result.put("rightIds", rightIds);
				result.put("right_id", param.get("right_id"));
				result.put("role_id", param.get("role_id"));
				boolean bool=roleService.addRoleModule(result);
				target=null;
				SpringUtils.renderDwzResult(response, bool, bool?"角色授权成功":"角色授权失败"
					,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());	
				logger.info("角色授权成功");
			}
		}catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("角色授权失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return target;
	}
		
	
	/**
	 * 根据roleId显示用户列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "showUserListByRoleId")
	public String showUserListByRoleId(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> result=new HashMap<String,Object>();
		Map<String, String> param=this.getParameters(request);
		try {
			result.put("roleId", param.get("supportID"));
			List<TreeModel> userTreeModelList=roleService.showUserListByRoleId(result);
			//String outGroupHtml=TreeUtils.getTreeStringWidthTreeModelList(userTreeModelList,"userId");
			//result.put("outGroupHtml", outGroupHtml);
			result.put("userTreeModelList", userTreeModelList);
			result.put("code", 1);
		}catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("(SysRoleController)展示用户列表失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return "role/shwoUsersRole";
	}
	
	public void softList(List<TreeModel> list){
		Comparator<TreeModel> comparator=new Comparator<TreeModel>() {
			@Override
			public int compare(TreeModel o1, TreeModel o2) {
				     return o1.pid-o2.pid;
			}
		};
		Collections.sort(list,comparator);
	}

}
