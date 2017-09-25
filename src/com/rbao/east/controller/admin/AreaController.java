package com.rbao.east.controller.admin;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Area;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.AreaService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.TreeUtils;

/**
 * 地址
 * @author daicheng
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("area")
public class AreaController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(AreaController.class);

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private SysModuleService moduleService;
	
	@RequestMapping("test")
	public String test2(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("num", 1);
		return "dictionary/area/test";
	}
	
	/**
	 * 获取页面的选择省市
	 */
	@RequestMapping("getAreas")
	public void getAreas(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		
		Map<String, String> maps = this.getParameters(request);
		Set<String> keys = maps.keySet();
		for (String key : keys) {
			System.out.println("key--" + key + ", value--" + maps.get(key));
		}
	}

	/**
	 * 根据parentId获取所有地区json数据
	 */
	@RequestMapping("getAreaData")
	public String getAreaData(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer pid) {
		List<Area> areas = areaService.selectByParentId(pid);
		SpringUtils.renderJson(response, areas);
		return null;
	}

	@RequestMapping("getTreeList")
	public String getTreeList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> requests = this.getParameters(request);
		User user = this.loginAdminUser(request);
		model.addAttribute("userId", user.getId());
		PageModel pm = areaService.getPageAreas(requests);
		model.addAttribute("pm", pm);
		model.addAttribute("searchParams", requests);
		model.addAttribute("right_id", requests.get("right_id"));

		List treeList = areaService.getTreeList();
		String url = "area/getSonList?right_id=" + requests.get("right_id")
				+ "&parentId=";

		String tree = TreeUtils.getTreeModelStrings(url, "jbsxBox-area", 0, 0,
				treeList);
		model.addAttribute("treeInfo", tree.replace("<ul ></ul>", ""));// 除掉最后一个ul
		return "dictionary/area/areaMenuList";
	}

	@RequestMapping("getSonList")
	public String getSonList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> requests = this.getParameters(request);
		requests.put("userId", this.loginAdminUser(request).getId().toString());

		PageModel pm = areaService.getPageAreas(requests);
		model.addAttribute("pm", pm);
		model.addAttribute("searchParams", requests);
		model.addAttribute("right_id", requests.get("right_id"));
		model.addAttribute("parentId", requests.get("parentId"));

		List<SysModule> righSubtList = moduleService
				.getRightGroupList(requests);
		model.addAttribute("righSubtList", righSubtList);

		return "dictionary/area/areaSonList";
	}

	@RequestMapping("rootTree")
	public String rootTree(Model model) {
		List treeList = areaService.getTreeList();
		String tree = TreeUtils.getCallBackTreeStrings(0, 0, treeList);
		model.addAttribute("rootTree", tree.replace("<ul ></ul>", ""));// 除掉最后一个ul
		return "dictionary/area/areaRootTree";
	}

	@RequestMapping("add")
	public String add(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Area area = new Area();
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		model.addAttribute("area", area);
		return "dictionary/area/areaAdd";
	}

	@RequestMapping("edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> maps = this.getParameters(request);
		String pname = "";
		if (Integer.parseInt(maps.get("pid")) == 0) {
			pname = "根目录";
		} else {
			Integer parentId = Integer.parseInt(maps.get("pid"));
			pname = areaService.selectByPrimaryKey(parentId).getAreaName();
		}

		Integer areaId = Integer.parseInt(maps.get("areaId"));
		Area area = areaService.selectByPrimaryKey(areaId);
		model.addAttribute("area", area);
		model.addAttribute("pname", pname);
		model.addAttribute("pid", Integer.parseInt(maps.get("pid")));
		model.addAttribute("right_id", maps.get("right_id"));
		return "dictionary/area/areaEdit";
	}

	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, Model model, Area area) {
		boolean flag = false;
		Map<String, String> maps = this.getParameters(request);
		Integer parentId = Integer.parseInt(maps.get("district.id"));
		area.setParentId(parentId);
		try {
			areaService.saveOrUpdate(area);
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.error("添加地区异常!");
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常",
				DwzResult.CALLBACK_CLOSECURRENT, maps.get("right_id")
						.toString());
	}

	@RequestMapping("delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		boolean flag = false;
		Map<String, String> maps = this.getParameters(request);
		Integer areaId = Integer.parseInt(maps.get("areaId"));
		
		List<Area> areas = areaService.selectByParentId(areaId);
		if (areas != null && areas.size()>0) {
			SpringUtils.renderDwzResult(response, false, "暂时无法删除,此地区还有下属地区",
					"", maps.get("right_id").toString(),
					"area/getTreeList?parentId=0&right_id="
							+ maps.get("right_id").toString());

		}else {
			flag = areaService.deleteByPrimaryKey(areaId);
			SpringUtils.renderDwzResult(response, flag, flag ? "删除栏目成功" : "删除栏目失败",
					"", maps.get("right_id").toString(),
					"area/getTreeList?parentId=0&right_id="
							+ maps.get("right_id").toString());

		}
	}
	
	@RequestMapping("checkAreaExist")
	public void checkAreaExist(HttpServletRequest request,
			HttpServletResponse response, String areaCode) {
		String msg = "false";
		Map<String, String> param = new HashMap<String, String>();
		param.put("areaCode", areaCode);
		int num = areaService.checkAreaExist(param);
		if (num < 1) {
			msg = "true";
		}
		SpringUtils.renderText(response, msg);
	}
}
