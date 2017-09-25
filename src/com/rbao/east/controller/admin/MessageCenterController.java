package com.rbao.east.controller.admin;

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
import com.rbao.east.entity.MessageCenter;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.TreeModel;
import com.rbao.east.service.MessageCenterService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.TreeUtils;
/**
 * 消息管理
 * */
@Controller
@RequestMapping("msg/")
public class MessageCenterController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(UserManagerController.class);
	
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private UserService userService;
	/**
	 * 获取消息列表
	 * @param request
	 * @param response
	 * @param model
	 * @param messageCenter
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"getMsgPage")
	public String getMsgPage(HttpServletRequest request,HttpServletResponse response,Model model,MessageCenter messageCenter){
		Map<String, String> paramsMap = getParameters(request);
		paramsMap.put("userId", Integer.toString(this.loginAdminUser(request).getId()));
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel page=this.messageCenterService.getPagedList(paramsMap);
		model.addAttribute("page", page);
		model.addAttribute("searchParams", messageCenter);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("rel", request.getParameter("rel"));
		return "msgcenter/msgCenterList";
	}
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"toInsertMsgCenter")
	public String toInsertMsgCenter(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		return "msgcenter/saveMsgcenter";
	}
	
	/**
	 * 弹出层，所有前台用户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"getAllFrontUser")
	public String getAllFrontUser(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, Object> result=new HashMap<String,Object>();
		Map<String, String> param=this.getParameters(request);
		try {
				List<TreeModel> allFrontUser=userService.allFrontUserList();
				String outGroupHtml=TreeUtils.getTreeStringWidthALlFrontUser(allFrontUser,  "userId");
				result.put("right_id", param.get("right_id"));
				result.put("userId", param.get("supportID"));
				result.put("outGroupHtml", outGroupHtml);
				result.put("code", 1);
		}catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
		}
		this.setParameters(result, request);
		return "msgcenter/allFrontUser";
		
	}
	
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW+"getAllFrontUserNew")
	public String getAllFrontUserNew(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String, String> param=this.getParameters(request);
		
		PageModel list = this.userService.selectForSelectedList(param);
		model.addAttribute("pm", list);
		model.addAttribute("param",param); 
		
		return "msgcenter/showSelectedUserList";
		
	}

	
	
	/**
	 *添加或修改消息
	 * @param request
	 * @param response
	 * @param center
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"toSaveMsgCenter")
	public void toSaveMsgCenter(HttpServletRequest request,HttpServletResponse response,MessageCenter center,Model model){
		boolean flag=false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		try{
			OperatorLog operatorLog=new OperatorLog();
			if(center.getId()==null){
				String ip = this.getIpAddr(request); 
				center.setMessageSendIp(ip);
				center.setSendUserId(this.loginAdminUser(request).getId());
				operatorLog.setOperatorTitle("添加Message消息");
			}else{
				operatorLog.setOperatorTitle("修改Message消息");
			}
			String receiveUserIds=param.get("receiveUserIds");
			String noticeTypeIds=param.get("noticeTypeId");
			flag=this.messageCenterService.toSaveMsgCenter(center, receiveUserIds, noticeTypeIds);
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("用户Id："+center.getReceiveUserId());
			operatorLog.setOperatorReturn(flag?"操作成功":"操作失败");
			operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorParams(center.toString());
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addAdminLog(operatorLog);
		}catch(Exception e){
			flag = false;
			
			logger.error("添加或者修改信息出错");
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
	}
	
	/**
	 * 删除消息
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"toDeleteMsgCenter")
	public void toDeleteMsgCenter(HttpServletRequest request,HttpServletResponse response,Model model){
		boolean flag=false;
		Map<String, String> param = this.getParameters(request);
		try{
			flag=this.messageCenterService.deleteMsgCenter(Integer.parseInt(param.get("supportID").toString()));

			/**
			 * 记录日志
			 */
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id："+param.get("supportID").toString());
			operatorLog.setOperatorTitle("删除消息");
			operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorParams(param.get("supportID").toString());
			operatorLog.setOperatorReturn(flag?"删除消息成功":"删除消息失败");
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			logger.info("删除消息成功");
		}catch(Exception e){
			
			flag=false;
			logger.error("删除消息出错："+ Integer.parseInt(param.get("supportID").toString()), e);
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "删除消息成功": "删除消息失败", "", param.get("right_id").toString(),"msg/v_getMsgPage?right_id="+ param.get("right_id").toString());
	}
}
