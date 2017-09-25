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
import com.rbao.east.entity.SysLetterTemplates;
import com.rbao.east.listener.Initializer;
import com.rbao.east.service.SysLetterTemplatesService;
import com.rbao.east.utils.SpringUtils;
/**
 * 站内信模板
 * */
@Controller
@RequestMapping("sys/")
public class SysLetterTemplatesController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(SysLetterTemplatesController.class);

	@Autowired
	private SysLetterTemplatesService sysLetterTemplatesService;

	/**
	 * 获取模板信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "getSysLetterTemplates")
	public String getSysLetterTemplates(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<SysLetterTemplates> list = sysLetterTemplatesService
				.getSysLetterTemplates();
		if (list.size() > 0) {
			SysLetterTemplates sysLetterTemplates = list.get(0);
			model.addAttribute("sysLetterTemplates", sysLetterTemplates);
		}
		return "sys/sysLetterTemplates";
	}

	/**
	 * 保存模板信息
	 * 
	 * @param request
	 * @param response
	 * @param letterTemplates
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveSysLetterTemplates")
	public void saveSysLetterTemplates(HttpServletRequest request,
			HttpServletResponse response, SysLetterTemplates letterTemplates) {
		List<SysLetterTemplates> list = sysLetterTemplatesService
				.getSysLetterTemplates();
		if (list.size() > 0) {
			letterTemplates.setId(list.get(0).getId());
		}
		boolean succ = false;
		try {
			succ = sysLetterTemplatesService
					.saveSysLetterTemplates(letterTemplates);
		} catch (Exception e) {
			logger.info("模板设置失败。");
			
		}
		new Initializer().updateCache();//刷新缓存
		SpringUtils.renderDwzResult(response, succ, succ ? "操作成功" : "操作失败",
				DwzResult.CALLBACK_CLOSECURRENTDIALOG);
	}
}
