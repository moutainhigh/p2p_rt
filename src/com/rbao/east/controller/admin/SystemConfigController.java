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
import com.rbao.east.entity.SysConfig;
import com.rbao.east.listener.Initializer;
import com.rbao.east.service.SysConfigService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
/**
 * 网站参数配置
 * */
@Controller
@RequestMapping("sys")
public class SystemConfigController extends BaseController{
	
	private static Logger logger=LoggerFactory.getLogger(SystemConfigController.class);
	
	
	@Autowired
	private SysConfigService sysConfigService;
	
	
	/**
	 * 查询所有的SysConfig,转进sysConfig.jsp中
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"getAllSysConfig")
	public String getAllSysConfig(HttpServletRequest request,HttpServletResponse response,Model model){
		List<SysConfig> list=this.sysConfigService.getAll();
		if(list.size()>0){
			SysConfig entity=list.get(0);
			model.addAttribute("SysConfig", entity);
		}
		return "sys/sysConfig";
	}
	/**
	 * 更新系统缓存
	 * @param request
	 * @param response
	 * @param model
	 * @param sysConfig
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"updateCache")
	public void updateCache(HttpServletRequest request, HttpServletResponse response,Model model,SysConfig sysConfig){
	    boolean succ = false;
		try {
			new Initializer().updateCache();
			succ = true;
		} catch (Exception e) {
			
			succ = false;
		}
		SpringUtils.renderDwzResult(response, succ, succ?"刷新缓存成功":"刷新缓存失败"); 
		
	}
	
	/***
	 * 对SysConfig进行添加和修改操作
	 * @param request
	 * @param response
	 * @param entity
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveSysConfig")
	public void saveSysConfig(HttpServletRequest request,HttpServletResponse response,SysConfig entity){
		boolean flag=false;
		try{
			OperatorLog operatorLog=new OperatorLog();
			List<SysConfig> list=this.sysConfigService.getAll();
			if(list.size()<=0||list.size()==1){
				flag=this.sysConfigService.saveOrUpdate(entity);
				operatorLog.setOperatorTitle("添加网站参数配置");
				operatorLog.setOperatorReturn(flag?"添加网站参数配置成功":"添加网站参数配置失败");
			}else if(list.size()>1){
				Integer id=list.get(0).getId();
				entity.setId(id);
				flag=this.sysConfigService.saveOrUpdate(entity);
				operatorLog.setOperatorTitle("修改网站参数配置");
				operatorLog.setOperatorReturn(flag?"修改网站参数配置成功":"修改网站参数配置失败");
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
			
			logger.error("添加或修改SysConfig信息出错！");
		}
		new Initializer().updateCache();//刷新缓存
		SpringUtils.renderDwzResult(response, flag, flag?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENTDIALOG);
	}

	
}
