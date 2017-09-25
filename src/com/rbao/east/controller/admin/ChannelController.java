package com.rbao.east.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.rbao.east.entity.Channel;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.ChannelService;
import com.rbao.east.service.ContentService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.TreeUtils;

/**
 * 栏目管理
 * 
 * @author Sandy
 * 
 */
@Controller
@RequestMapping("channel/")
public class ChannelController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(ChannelController.class);

	@Autowired
	private ChannelService channelService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private ContentService contentService;

	
	/**
	 * 删除栏目
	 * */
	@RequestMapping(Constants.PRE_PATH_EDIT + "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		User user = this.loginAdminUser(request);
		if (user == null) {
			SpringUtils.renderDwzResult(response, false, "获取用户信息失败，请重新登录");
			return;
		}
		Map<String, String> param = this.getParameters(request);
		Map<String, String> paramId = new HashMap<String, String>();
		paramId.put("channelParentId", param.get("channelId"));
		PageModel pageModel = channelService.getListByParentId(paramId);
		List contentList = contentService.getListBychannelIds(param);
		boolean succ = false;
		if (pageModel.getList().size() > 0) {
			SpringUtils.renderDwzResult(response, false,
					"删除失败，此栏目下有子栏目，请先删除子栏目", "", param.get("right_id")
							.toString(),
					"channel/v_getList?channelParentId=0&right_id="
							+ param.get("right_id").toString());
		} else if (contentList.size() > 0) {
			SpringUtils.renderDwzResult(response, false,
					"删除失败，此栏目下有消息，请先删除本栏目下消息", "", param.get("right_id")
							.toString(),
					"channel/v_getList?channelParentId=0&right_id="
							+ param.get("right_id").toString());
		} else {

			try {
				Integer id = Integer
						.parseInt(param.get("channelId").toString());
				succ = channelService.deleteById(id);
			} catch (Exception e) {
				
				logger.error(
						"删除栏目出错："
								+ Integer.parseInt(param.get("channelId")
										.toString()), e);
			}
			SpringUtils.renderDwzResult(response, succ, succ ? "删除栏目成功"
					: "删除栏目失败", "", param.get("right_id").toString(),
					"channel/v_getList?channelParentId=0&right_id="
							+ param.get("right_id").toString());
			logger.info("删除栏目成功！");
		}
	}

	
	/**
	 * 获取栏目
	 * */
	@RequestMapping(Constants.PRE_PATH_VIEW + "get")
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id", param.get("right_id"));
		if (param.containsKey("channelId")) {
			int id = Integer.parseInt(param.get("channelId").toString());
			// int
			// id=Integer.parseInt(request.getAttribute("channelId").toString());
			Channel channel = channelService.getById(id);
			model.addAttribute("channel", channel);

			Integer channelPId = channel.getChannelParentId();
			if (channelPId == 0) {
				model.addAttribute("channelP", 0);
				model.addAttribute("pId","0");
			} else {
				Channel channelP = channelService.getById(channelPId);
				model.addAttribute("channelP", channelP);
				model.addAttribute("pId","other");
			}
		}
		return "channel/input";
	}

	// 获得根栏目列表
	@RequestMapping(Constants.PRE_PATH_VIEW + "getList")
	public String getList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Channel channel = new Channel();
		Map<String, String> paramsMap = getParameters(request);
		channel.setChannelParentId(Integer.parseInt(paramsMap.get(
				"channelParentId").toString()));
		// 列表
		PageModel pageModel = channelService.getListByParentId(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", channel);// 用于搜索框保留值
		model.addAttribute("right_id", paramsMap.get("right_id"));
		// tree列表
		List treeList = channelService.getTreeList(channel);
		String url = "channel/" + Constants.PRE_PATH_VIEW
				+ "getSonList?right_id=" + paramsMap.get("right_id")
				+ "&channelParentId=";
		String tree = TreeUtils.getTreeModelStrings(url, "jbsxBox1", 0, 0,
				treeList);
		model.addAttribute("treeInfo", tree.replace("<ul ></ul>", ""));// 除掉最后一个ul
		return "channel/menuList";
	}

	// 获得子栏目列表
	@RequestMapping(Constants.PRE_PATH_VIEW + "getSonList")
	public String getChildrenList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = loginAdminUser(request);
		Map<String, String> paramsMap = getParameters(request);
		if (paramsMap.containsKey("right_ids")) {
			paramsMap.put("right_id", paramsMap.get("right_ids"));
		}
		if (StringUtils.isNotBlank(paramsMap.get("channelTitle")))
			paramsMap.put("channelParentId", null);
		paramsMap.put("userId", String.valueOf(user.getId()));
		// 列表
		PageModel pageModel = channelService.getListByParentId(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("right_id", paramsMap.get("right_id"));// 刷新
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);

		model.addAttribute("searchParams", paramsMap);// 用于搜索框保留值
		model.addAttribute("channelParentId", paramsMap.get("channelParentId"));
		return "channel/sonList";
	}

	// 获得根栏目tree
	@RequestMapping("treeInfo")
	public String tree(HttpServletRequest request,
			HttpServletResponse response, Model model, Channel channel) {
		// tree列表
		List treeList = channelService.getTreeList(channel);
		String tree = TreeUtils.getCallBackTreeStrings(0, 0, treeList);
		model.addAttribute("treeInfo", tree.replace("<ul ></ul>", ""));// 除掉最后一个ul
		return "channel/treeInfo";
	}

	@RequestMapping(Constants.PRE_PATH_EDIT + "saveOrUpdate")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Channel channel) {
		User user = this.loginAdminUser(request);
		if (user == null) {
			SpringUtils.renderDwzResult(response, false, "获取用户信息失败，请重新登录");
			return;
		}
		Map<String, String> param = this.getParameters(request);
		String parentId = request.getParameter("district.id");
		boolean succ = false;
		try {
			if (!StringUtils.isEmpty(parentId)) {
				channel.setChannelParentId(Integer.parseInt(parentId));
			}
			channel.setChannelUpdateTime(new Date());
			succ = channelService.saveOrUpdate(channel);

		} catch (Exception e) {
			logger.error("栏目添加、修改出错");
			succ = false;
		}
		SpringUtils.renderDwzResult(response, succ, succ ? "栏目操作成功" : "栏目操作失败",
				DwzResult.CALLBACK_CLOSECURRENT, param.get("right_id")
						.toString());
	}

	@RequestMapping("editSeq")
	public void editSeq(HttpServletRequest request,
			HttpServletResponse response, Model model, Channel channel) {

		String channelId = request.getParameter("channelId");
		String channeVal = request.getParameter("channeVal");
		channel.setId(Integer.parseInt(channelId));
		channel.setChannelSequence(Integer.parseInt(channeVal));
		if (null != channelId || !channeVal.equals("")) {
			this.channelService.saveOrUpdate(channel);
			SpringUtils.renderText(response, "success");
		}

	}

	@RequestMapping("checkChannelCode")
	public void checkChannelCode(HttpServletRequest request,
			HttpServletResponse response, Model model, Channel channel) {
		String channelId = request.getParameter("channelId");
		String channelCode = request.getParameter("channelCode");
		Integer totalCount = channelService.getByChannelCode(channelId,
				channelCode);
		if (totalCount == 0) {
			SpringUtils.renderText(response, "success");
		} else {
			SpringUtils.renderText(response, "fail");
		}

	}

}
