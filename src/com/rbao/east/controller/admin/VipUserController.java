package com.rbao.east.controller.admin;

import java.util.Date;
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
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.User;
import com.rbao.east.entity.VipUser;
import com.rbao.east.service.VipUserService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
/**
 * vip管理
 * */
@Controller
@RequestMapping("/vip")
public class VipUserController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(VipUserController.class);
	@Autowired
	private VipUserService vipUserService;
	
	/**
	 * 跳转到修改页面
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"updateVipUser")
	public String updateVipUser(HttpServletResponse response,
			HttpServletRequest request, Model model){
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		VipUser user = this.vipUserService.selectVipUserById(Integer.parseInt(param.get("supportID")));
		model.addAttribute("vipUser", user);
		return "usermanage/vipUser/updateVipUser";
	}
	/**
	 * 修改vip用户
	 * @param request
	 * @param response
	 * @param model
	 * @param vipUser
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"doUpdateVipUser")
	public void doUpdateVipUser(HttpServletRequest request,HttpServletResponse response, Model model,VipUser vipUser){
		boolean flag = false;
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		User loginUser=this.loginAdminUser(request);
		try{
			vipUser.setVipOperatorId(loginUser.getId());
			vipUser.setVipUpdateIp(this.getIpAddr(request));
			vipUser.setVipUpdateDatetime(new Date());
			flag=this.vipUserService.saveOrUpdate(vipUser);
			/**
			 * 记录日志
			 */
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setLogUserid("用户Id："+loginUser.getId()+",用户名："+loginUser.getUserAccount());
			operatorLog.setOperatorTitle("修改vip用户信息");
			operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorCategory(OperatorLog.CATEGORY_ATTESTATION);
			operatorLog.setOperatorParams(vipUser.toString());
			operatorLog.setOperatorReturn(flag?"修改成功":"修改失败");
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addAdminLog(operatorLog);
		}catch(Exception e){
			logger.error("修改vip信息出错");
			flag=false;
			
		}
		if(flag){
			SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作失败",DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id").toString());
		}
	}

}
