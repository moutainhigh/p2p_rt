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
import com.rbao.east.entity.SysSmsConfig;
import com.rbao.east.listener.Initializer;
import com.rbao.east.service.SysSmsConfigService;
import com.rbao.east.utils.SpringUtils;

/**
 * 短信
 * */
@Controller
@RequestMapping("sys/")
public class SysSmsController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(SysSmsController.class);

	@Autowired
	private SysSmsConfigService sysSmsConfigService;
	
	//短信配置
	@RequestMapping(Constants.PRE_PATH_VIEW + "get")
	public String get(HttpServletRequest request, HttpServletResponse response,Model model){
		List<SysSmsConfig> list=sysSmsConfigService.getAll();
		if(list.size()>0){
			SysSmsConfig sysSmsConfig=list.get(0);
			model.addAttribute("sysSmsConfig", sysSmsConfig);
			return "sys/syssmsconfig";
		}else{
			return "sys/syssmsconfig";
		}
	}
	@RequestMapping(Constants.PRE_PATH_EDIT + "save")
	public void save(HttpServletRequest request, HttpServletResponse response,SysSmsConfig sysSmsConfig){
		List<SysSmsConfig> list=sysSmsConfigService.getAll();
		if(list.size()>0){
			sysSmsConfig.setId(list.get(0).getId());
		}
		boolean succ=false;
		try {
			succ = sysSmsConfigService.save(sysSmsConfig);
		} catch (Exception e) {
			
		}
		new Initializer().updateCache();//刷新缓存
		SpringUtils.renderDwzResult(response, succ, succ?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENTDIALOG); 
	}
}
