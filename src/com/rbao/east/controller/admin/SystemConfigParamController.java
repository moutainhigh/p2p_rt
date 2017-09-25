package com.rbao.east.controller.admin;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.SysConfigParams;
import com.rbao.east.listener.Initializer;
import com.rbao.east.service.SysConfigParamService;
import com.rbao.east.utils.SpringUtils;
/**
 * 动态配置参数
 * */
@Controller
@RequestMapping("cfgParam")
public class SystemConfigParamController extends BaseController{
	
	private static Logger logger=LoggerFactory.getLogger(SystemConfigParamController.class);
	
	@Autowired
	SysConfigParamService cfgService;
	/**
	 * 进入系统配置编辑页面，
	 * 传入不同的type可以配置多个类型的页面
	 * @param request
	 * @param response
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"enterCfgEdit/{cfgType}")
	public String enterCfgEdit(HttpServletRequest request,HttpServletResponse response,Model model
			,@PathVariable("cfgType") String type){
		List<SysConfigParams> list=this.cfgService.getByType(type);

		model.addAttribute("cfgList", list);
	
		return "sys/sysConfigParam";
	}
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveCfgParam")
	public void saveCfgParam(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String,String> paramsMap = getParameters(request);

		Iterator<Entry<String, String>> it = paramsMap.entrySet().iterator();
		String namePre = "id_";
		int succ = 0;
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			String name = entry.getKey();
			String value = entry.getValue();
			
			if(name.startsWith(namePre)){
				String id = name.replaceFirst(namePre, "");
				try {
					if(this.cfgService.updateValue(Integer.valueOf(id), value)){
						succ ++;
					}
				} catch (Exception e) {
					
				}
			}
		}
		new Initializer().updateCache();//刷新缓存
		SpringUtils.renderDwzResult(response, succ > 0, succ > 0?"操作成功":"操作失败");
	}
}
