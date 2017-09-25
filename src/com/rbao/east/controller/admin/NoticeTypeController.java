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
import com.rbao.east.entity.NoticeType;
import com.rbao.east.entity.SysModule;
import com.rbao.east.service.NoticeTypeService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.SpringUtils;

/**
 * 提醒类型
 * */
@Controller
@RequestMapping("sys/")
public class NoticeTypeController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(NoticeTypeController.class);

	@Autowired
	private NoticeTypeService noticeTypeService;
	@Autowired
	private SysModuleService moduleService;
	
	//提醒类型列表
	@RequestMapping(Constants.PRE_PATH_VIEW + "getNoticeTypeList")
	public String getNoticeTypeList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String,String> paramsMap = getParameters(request);
		paramsMap.put("userId", this.loginAdminUser(request).getId().toString());
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		PageModel pageModel = noticeTypeService.getpagedList(paramsMap);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams",paramsMap);//用于搜索框保留值
		model.addAttribute("rel",request.getParameter("rel"));
		return "sys/noticetypelist";
	}
	
	//根据Id查询提醒类型
	@RequestMapping(Constants.PRE_PATH_VIEW + "getNoticeTypeById")
	public String getNoticeTypeById(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		model.addAttribute("noticeType", noticeTypeService.getById(Integer.parseInt(param.get("supportID").toString())));
		model.addAttribute("right_id",param.get("right_id"));
		return "sys/savenoticetype";
	}
	
	//跳转到添加提醒类型页面
	@RequestMapping(Constants.PRE_PATH_VIEW + "forAddNoticeType")
	public String forAddNoticeType(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		model.addAttribute("right_id",param.get("right_id"));
		return "sys/savenoticetype";
	}

	//保存提醒类型
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveNoticeType")
	public void saveNoticeType(HttpServletRequest request, HttpServletResponse response,
			Model model,NoticeType noticeType){
		boolean succ=false;
		Map<String, String> param=this.getParameters(request);
		model.addAttribute("right_id",param.get("right_id"));
		try {
			Map map = new HashMap();
			map.put("noticeCode",noticeType.getNoticeCode());
			if(noticeType.getId()==null &&  noticeTypeService.selectTotalCount(map)>0){
				SpringUtils.renderDwzResult(response, false,"编码 " + noticeType.getNoticeCode()+" 已经存在，不能重复",DwzResult.CALLBACK_CLOSECURRENTDIALOG);
				return;
			}
			if(noticeType.getId() == null){
				String ip = this.getIpAddr(request);
				noticeType.setNoticeAddip(ip);
			}
			succ = noticeTypeService.save(noticeType);
		} catch (Exception e) {
			
		}
		SpringUtils.renderDwzResult(response, succ, succ?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
	}
	
	
	//删除提醒类型
	@RequestMapping(Constants.PRE_PATH_EDIT + "deleteNoticeType")
	public void deleteNoticeType(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param=this.getParameters(request);
		boolean succ = false;
		try {
			succ = noticeTypeService.deleteById(Integer.parseInt(param.get("supportID").toString()));
		} catch (Exception e) {
			
			logger.error("删除提醒类型出错："+Integer.parseInt(param.get("supportID").toString()),e);
		}
		SpringUtils.renderDwzResult(response, succ, succ?"删除提醒类型成功":"删除提醒类型失败","",param.get("right_id").toString(),"sys/v_getNoticeList?right_id="+param.get("right_id").toString());
		logger.info("删除提醒类型成功");
	}
}
