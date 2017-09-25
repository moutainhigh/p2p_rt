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

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Dictionary;
import com.rbao.east.entity.Nation;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.DictionaryService;
import com.rbao.east.service.NationService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.TreeUtils;

/**
 * 字典管理
 * 
 * @author daicheng
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RequestMapping("dic")
@Controller
public class DictionaryController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(DictionaryController.class);

	@Autowired
	private NationService nationService;

	@Autowired
	private SysModuleService moduleService;

	@Autowired
	private DictionaryService dictionaryService;
	
	/**
	 * 获取所有民族json
	 */
	@RequestMapping("nation/getNationData")
	public String getNationData(HttpServletResponse response){
		Map<String, String> map = new HashMap<String, String>();
		List<Nation> nations = nationService.getNationList(map);
		SpringUtils.renderJson(response, nations);
		return null;
	}

	//---民族字典
	@RequestMapping("nation/getNationList")
	public String getNationList(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Map<String, String> maps = this.getParameters(request);
		maps.put("userId", this.loginAdminUser(request).getId().toString());
		PageModel pm = nationService.getNationPage(maps);

		model.addAttribute("pm", pm);
		model.addAttribute("searchParams", maps);
		model.addAttribute("right_id", maps.get("right_id"));

		List<SysModule> righSubtList = moduleService.getRightGroupList(maps);
		model.addAttribute("righSubtList", righSubtList);
		return "dictionary/nation/nationList";
	}

	@RequestMapping("nation/add")
	public String nationAdd(HttpServletRequest request, Model model) {
		Nation nation = new Nation();
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		model.addAttribute("nation", nation);
		return "dictionary/nation/nationAdd";
	}

	@RequestMapping("nation/edit")
	public String nationeEdit(HttpServletRequest request, Model model) {
		Map<String, String> param = this.getParameters(request);
		Integer nid = Integer.parseInt(param.get("nationId"));
		Nation nation = nationService.selectByPrimaryKey(nid);
		model.addAttribute("right_id", param.get("right_id"));
		model.addAttribute("nation", nation);
		return "dictionary/nation/nationEdit";
	}

	@RequestMapping("nation/saveOrUpdate")
	public void nationSaveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, Model model, Nation nation) {
		boolean flag = false;
		Map<String, String> maps = this.getParameters(request);
		try {
			nationService.saveOrUpdate(nation);
			flag = true;
		} catch (Exception e) {
			logger.error("操作民族异常");
			flag = false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常",
				DwzResult.CALLBACK_CLOSECURRENT, maps.get("right_id")
						.toString());
	}

	@RequestMapping("nation/delete")
	public void nationDelete(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> param = this.getParameters(request);
		Integer nid = Integer.parseInt(param.get("nationId"));
		boolean flag = false;
		try {
			nationService.deleteByPrimaryKey(nid);
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.error("删除异常");
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常", "",
				param.get("right_id").toString(),
				"dic/nation/getNationList?right_id="
						+ param.get("right_id").toString());
	}

	@RequestMapping("nation/checkExist")
	public void checkExist(HttpServletRequest request,
			HttpServletResponse response, String nationCode) {
		String msg = "false";
		Map<String, String> param = new HashMap<String, String>();
		param.put("nationCode", nationCode);
		int num = nationService.checkExist(param);
		if (num < 1) {
			msg = "true";
		}
		SpringUtils.renderText(response, msg);
	}

	// ---网站字典------------------------------

	@RequestMapping("checkCodeExist")
	public void checkCodeExist(HttpServletResponse response, String dicCode) {
		String msg = "false";
		Map<String, String> params = new HashMap<String, String>();
		params.put("dicCode", dicCode);
		if (dictionaryService.checkCodeExist(params)) {
			msg = "true";
		}
		SpringUtils.renderText(response, msg);
	}

	@RequestMapping("getDicTree")
	public String getDicTree(HttpServletRequest request, Model model) {
		Map<String, String> requests = this.getParameters(request);
		User user = this.loginAdminUser(request);
		model.addAttribute("userId", user.getId());
		requests.put("dicIshidden", "1");
		PageModel pm = dictionaryService.getPageDic(requests);

		model.addAttribute("pm", pm);
		model.addAttribute("searchParams", requests);
		model.addAttribute("right_id", requests.get("right_id"));

		List treeList = dictionaryService.getTreeList();
		String url = "dic/getSonList?right_id=" + requests.get("right_id")
				+ "&parentId=";

		String tree = TreeUtils.getTreeModelStrings(url, "jbsxBox-dic", 0, 0,
				treeList);
		model.addAttribute("treeInfo", tree.replace("<ul ></ul>", ""));
		return "dictionary/dic/dicMenuList";

	}

	@RequestMapping("getSonList")
	public String getSonList(HttpServletRequest request, Model model) {
		Map<String, String> requests = this.getParameters(request);
		requests.put("userId", this.loginAdminUser(request).getId().toString());
		// requests.put("dicIshidden", "1");
		PageModel pm = dictionaryService.getPageDic(requests);
		model.addAttribute("pm", pm);
		model.addAttribute("searchParams", requests);
		model.addAttribute("right_id", requests.get("right_id"));
		model.addAttribute("parentId", requests.get("parentId"));

		List<SysModule> righSubtList = moduleService
				.getRightGroupList(requests);
		model.addAttribute("righSubtList", righSubtList);

		return "dictionary/dic/dicSonList";
	}

	@RequestMapping("rootTree")
	public String rootTree(Model model) {
		List treeList = dictionaryService.getTreeList();
		String tree = TreeUtils.getCallBackTreeStrings(0, 0, treeList);
		model.addAttribute("rootTree", tree.replace("<ul ></ul>", ""));// 除掉最后一个ul
		return "dictionary/dic/dicRootTree";
	}

	@RequestMapping("add")
	public String dicAdd(HttpServletRequest request, Model model) {
		Dictionary dictionary = new Dictionary();
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		model.addAttribute("dictionary", dictionary);
		return "dictionary/dic/dicAdd";
	}

	@RequestMapping("edit")
	public String dicEdit(HttpServletRequest request, Model model) {
		Map<String, String> maps = this.getParameters(request);
		String pname = "";
		if (Integer.parseInt(maps.get("pid")) == 0) {
			pname = "根目录";
		} else {
			Integer parentId = Integer.parseInt(maps.get("pid"));
			pname = dictionaryService.selectByPrimaryKey(parentId).getDicName();
		}

		Integer dicId = Integer.parseInt(maps.get("dicId"));
		Dictionary dictionary = dictionaryService.selectByPrimaryKey(dicId);

		model.addAttribute("dictionary", dictionary);
		model.addAttribute("pid", Integer.parseInt(maps.get("pid")));
		model.addAttribute("right_id", maps.get("right_id"));
		model.addAttribute("pname", pname);
		return "dictionary/dic/dicEdit";
	}

	@RequestMapping("dicSave")
	public void dicSave(HttpServletRequest request,
			HttpServletResponse response, Model model, Dictionary dictionary) {
		boolean flag = false;
		Map<String, String> params = this.getParameters(request);
		Integer parentId = Integer.parseInt(params.get("district.id"));
		String uploadUrl = params.get("url");

		dictionary.setDicAttach(uploadUrl);
		dictionary.setParentId(parentId);
		try {
			Map<String, String> code = new HashMap<String, String>();
			code.put("dicCode", dictionary.getDicCode());
			if (dictionaryService.checkCodeExist(code)) {
				flag = false;
			} else {
				dictionaryService.saveOrUpdate(dictionary);
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			
			logger.error("添加字典出错!");
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常",
				DwzResult.CALLBACK_CLOSECURRENT, params.get("right_id")
						.toString());
	}

	@RequestMapping("dicUpdate")
	public void dicSaveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, Model model, Dictionary dictionary) {
		boolean flag = false;
		Map<String, String> params = this.getParameters(request);
		Integer parentId = Integer.parseInt(params.get("district.id"));
		Integer id = Integer.parseInt(params.get("id"));
		String uploadUrl = params.get("url");

		dictionary.setDicAttach(uploadUrl);
		dictionary.setParentId(parentId);

		Dictionary oldDictionary = this.dictionaryService
				.selectByPrimaryKey(id);
		try {
			Map<String, String> code = new HashMap<String, String>();
			code.put("dicCode", dictionary.getDicCode());
			if (dictionaryService.checkCodeExist(code)
					&& !dictionary.getDicCode().equals(
							oldDictionary.getDicCode())) {
				flag = false;
			} else {
				dictionaryService.saveOrUpdate(dictionary);
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			
			logger.error("添加字典出错!");
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常",
				DwzResult.CALLBACK_CLOSECURRENT, params.get("right_id")
						.toString());
	}

	@RequestMapping("delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		boolean flag = false;
		Map<String, String> params = this.getParameters(request);
		Integer dicId = Integer.parseInt(params.get("dicId"));

		params.put("parentId", dicId + "");
		List<Dictionary> dictionaries = dictionaryService
				.selectByParentId(params);

		if (dictionaries.size() > 0) {
			SpringUtils.renderDwzResult(
					response,
					false,
					"暂时无法删除,此栏目还有下属栏目",
					"",
					params.get("right_id").toString(),
					"dic/getTreeList?parentId=0&right_id="
							+ params.get("right_id").toString());
		} else {
			flag = dictionaryService.deleteByPrimaryKey(dicId);
			SpringUtils.renderDwzResult(
					response,
					flag,
					flag ? "删除栏目成功" : "删除栏目失败",
					"",
					params.get("right_id").toString(),
					"dic/getTreeList?parentId=0&right_id="
							+ params.get("right_id").toString());

		}
	}

}
