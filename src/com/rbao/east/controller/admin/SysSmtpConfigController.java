package com.rbao.east.controller.admin;

import java.util.List;

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
import com.rbao.east.entity.SysSmtpConfig;
import com.rbao.east.listener.Initializer;
import com.rbao.east.service.SysSmtpConfigService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
/**
 * 邮箱
 * */
@Controller
@RequestMapping("sys/")
public class SysSmtpConfigController extends BaseController{
	
	private static Logger logger=LoggerFactory.getLogger(SystemConfigController.class);
	
	@Autowired
	private SysSmtpConfigService sysSmtpConfigService;
	
	/**
	 * 查询所有的SysConfig,转进sysSmtpConfig.jsp中
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"getAllSysSmtpConfig")
	public String getAllSysSmtpConfig(HttpServletRequest request,HttpServletResponse response,Model model){
		List<SysSmtpConfig> list=this.sysSmtpConfigService.getAll();
		if(list.size()>0){
			SysSmtpConfig entity=list.get(0);
			model.addAttribute("sysSmtpConfig", entity);
		}
		return "sys/sysSmtpConfig";
	}
	
	/***
	 * 对SysSmtpConfig进行添加和修改操作
	 * @param request
	 * @param response
	 * @param entity
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveSysSmtpConfig")
	public void saveSysSmtpConfig(HttpServletRequest request,HttpServletResponse response,SysSmtpConfig entity){
		boolean flag=false;
		try{
			OperatorLog operatorLog=new OperatorLog();
			List<SysSmtpConfig> list=this.sysSmtpConfigService.getAll();
			if(list.size()<=0||list.size()==1){
				flag=this.sysSmtpConfigService.saveOrUpdate(entity);
				operatorLog.setOperatorTitle("添加邮件配置");
				operatorLog.setOperatorReturn(flag?"添加邮件配置成功":"添加邮件配置失败");
			}else if(list.size()>1){
				Integer id=list.get(0).getId();
				entity.setId(id);
				flag=this.sysSmtpConfigService.saveOrUpdate(entity);
				operatorLog.setOperatorTitle("修改添加邮件配置");
				operatorLog.setOperatorReturn(flag?"修改添加邮件配置成功":"修改添加邮件配置失败");
			}
			/**
			 * 记录日志
			 */
			operatorLog.setLogUserid("操作人："+this.loginAdminUser(request).getUserAccount());
			operatorLog.setOperatorCategory(OperatorLog.TYPE_ADMIN);
			operatorLog.setOperatorParams(entity.toString());
			operatorLog.setOperatorStatus(flag?200:300);
			operatorLog.setLinkUrl(RequestUtils.getIpAddr());
			operatorLogService.addAdminLog(operatorLog);
		}catch(Exception e){
			
			logger.error("添加或修改SysSmtpConfig信息出错！");
		}
		new Initializer().updateCache();//刷新缓存
		SpringUtils.renderDwzResult(response, flag, flag?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENTDIALOG);
	}

}
