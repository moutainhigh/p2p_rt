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
import com.rbao.east.entity.Notice;
import com.rbao.east.entity.SysModule;
import com.rbao.east.service.NoticeService;
import com.rbao.east.service.NoticeTypeService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.SpringUtils;

/**
 * 提醒类型
 * */
@Controller
@RequestMapping("sys/")
public class NoticeController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeTypeService noticeTypeService;
	@Autowired
	private SysModuleService moduleService;
	
	//提醒列表
	@RequestMapping(Constants.PRE_PATH_VIEW + "getNoticeList")
	public String getNoticeList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = noticeService.getpagedList(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("noticeTypes", noticeTypeService.getAll());
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		return "sys/noticelist";
	}
	
	//根据Id查询提醒
	@RequestMapping(Constants.PRE_PATH_VIEW + "getNoticeById")
	public String getNoticeById(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		model.addAttribute("noticeTypes", noticeTypeService.getAll());
		model.addAttribute("notice", noticeService.getById(Integer.parseInt(param.get("supportID").toString())));
		model.addAttribute("right_id",param.get("right_id"));
		return "sys/savenotice";
	}
	
	//跳转到添加提醒页面
	@RequestMapping(Constants.PRE_PATH_VIEW + "forAddNotice")
	public String forAddNotice(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		model.addAttribute("right_id",param.get("right_id"));
		model.addAttribute("noticeTypes", noticeTypeService.getAll());
		return "sys/savenotice";
	}

	//保存提醒
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveNotice")
	public void saveNotice(HttpServletRequest request, HttpServletResponse response,
			Model model,Notice notice){
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		model.addAttribute("right_id",param.get("right_id"));
		try {
			Map map = new HashMap();
			map.put("noticeCode",notice.getNoticeCode());
			if(notice.getId()==null &&  noticeService.selectTotalCount(map)>0){
				SpringUtils.renderDwzResult(response, false,"编码 " + notice.getNoticeCode()+" 已经存在，不能重复",DwzResult.CALLBACK_CLOSECURRENTDIALOG);
				return;
			}
			if(notice.getId() == null){
				String ip = this.getIpAddr(request);
				notice.setNoticeAddip(ip);
			}
			succ = noticeService.save(notice);
		} catch (Exception e) {
			
		}
		SpringUtils.renderDwzResult(response, succ, succ?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
	}
	
	
	//删除提醒
	@RequestMapping(Constants.PRE_PATH_EDIT + "deleteNotice")
	public void deleteNotice(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param=this.getParameters(request);
		boolean succ = false;
		try {
			succ = noticeService.deleteById(Integer.parseInt(param.get("supportID").toString()));
		} catch (Exception e) {
			
			logger.error("删除提醒出错："+Integer.parseInt(param.get("supportID").toString()),e);
		}
		SpringUtils.renderDwzResult(response, succ, succ?"删除提醒成功":"删除提醒失败","",param.get("right_id").toString(),"sys/v_getNoticeList?right_id="+param.get("right_id").toString());
		logger.info("删除提醒成功");
	}
}
