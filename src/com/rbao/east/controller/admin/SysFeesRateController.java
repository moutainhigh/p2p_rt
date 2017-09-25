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
import com.rbao.east.entity.SysFeesRate;
import com.rbao.east.listener.Initializer;
import com.rbao.east.service.SysFeesRateService;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
/**
 * 资金费率
 * */
@Controller
@RequestMapping("sys/")
public class SysFeesRateController  extends BaseController{
	private static Logger logger=LoggerFactory.getLogger(SystemConfigController.class);
	
	@Autowired
	private SysFeesRateService sysFeesRateService;
	
	/**
	 * 查询所有的SysFeesRate,转进sysfeesrate.jsp
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"getAllSFR")
	public String getAllSysFeesRate(HttpServletRequest request, HttpServletResponse response,Model model) {
		List<SysFeesRate> list=this.sysFeesRateService.getAll();
		if(list.size()>0){
			SysFeesRate entity=list.get(0);
			model.addAttribute("entity", entity);
		}
		return "sys/sysfeesrate";
		
	}
	/***
	 * 对SysFeesRate进行添加和修改操作
	 * @param request
	 * @param response
	 * @param entity
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveSysFeesRate")
	public void saveSFR(HttpServletRequest request,HttpServletResponse response,SysFeesRate entity){
		boolean flag=false;
		try{
			OperatorLog operatorLog=new OperatorLog();
			List<SysFeesRate> list=this.sysFeesRateService.getAll();
			if(list.size()<=0||list.size()==1){
				flag=this.sysFeesRateService.saveOrUpdate(entity);
				operatorLog.setOperatorTitle("添加资金费率消息");
				operatorLog.setOperatorReturn(flag?"添加资金费率消息成功":"添加资金费率消息失败");
			}else if(list.size()>1){
				Integer id=list.get(0).getId();
				entity.setId(id);
				flag=this.sysFeesRateService.saveOrUpdate(entity);
				operatorLog.setOperatorTitle("修改资金费率消息");
				operatorLog.setOperatorReturn(flag?"修改资金费率消息成功":"修改资金费率消息失败");
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
			
			logger.error("添加或者修改信息出错");
		}
		new Initializer().updateCache();//刷新缓存
		SpringUtils.renderDwzResult(response, flag, flag?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENTDIALOG);
	}

}
