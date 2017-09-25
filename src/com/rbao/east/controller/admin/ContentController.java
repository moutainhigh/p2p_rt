package com.rbao.east.controller.admin;

import java.util.ArrayList;
import java.util.Date;
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
import com.rbao.east.entity.Content;
import com.rbao.east.entity.ContentChannel;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.ChannelService;
import com.rbao.east.service.ContentChannelService;
import com.rbao.east.service.ContentService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.TreeUtils;

/**
 * 内容管理
 * 
 * @author Sandy
 * @param <E>
 * 
 */
@Controller
@RequestMapping("content/")
public class ContentController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(ContentController.class);

	@Autowired
	private ContentService contentService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private SysModuleService moduleService;
	@Autowired
	private ContentChannelService contentChannelService;

	@RequestMapping(Constants.PRE_PATH_EDIT + "issueContent")
	public String issueContent(HttpServletRequest request,
			HttpServletResponse response, Model model, Content content) {
		model.addAttribute("nowDate", new Date());
		Map<String, String> param = this.getParameters(request);
		model.addAttribute("right_id",param.get("right_id"));
		return "content/input";
	}

	@RequestMapping(Constants.PRE_PATH_EDIT + "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = this.getParameters(request);
		boolean succ = false;
		try {
			String ccId[] = StringUtils.split(param.get("contentIdChannelId"), ",");

		/*	if (param.containsKey("contentIdChannelId")) {
				Integer contentId = Integer.parseInt(ccId[0]);
				Integer channeId = Integer.parseInt(ccId[1]);
				}*/
			Integer contentId = Integer.parseInt(ccId[0]);
			succ = contentService.deleteById(contentId);
		} catch (Exception e) {
			
			logger.error(
					"删除信息出错："
							+ Integer.parseInt(param.get("contentIdChannelId")
									.toString()), e);
		}
		SpringUtils.renderDwzResult(response, succ, succ ? "删除信息成功" : "删除信息失败",
				"", param.get("right_id").toString(),
				"content/v_getList?right_id="
						+ param.get("right_id").toString());
	}

	@RequestMapping(Constants.PRE_PATH_EDIT + "saveOrUpdate")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Content content, String[] attachTitle, String[] attachPath,
			String[] attachRealTitle, String[] attachFileType,
			Integer[] attachSequence) {
		Map<String, String> param=this.getParameters(request);
		boolean succ = false;
		User user = loginAdminUser(request);
		try {
			String channelIds = request.getParameter("district.id");
			if (channelIds.equals("")) {
				channelIds = request.getParameter("ids");
			}

			if (!StringUtils.isEmpty(channelIds)) {
				content.setChannelIds(channelIds);
			}
			content.setContentCreateUser(user.getUserAccount());
			content.setContentUpdateUser(user.getUserAccount());
			content.setContentUpdateDatetime(new Date());
			String contentSource = content.getContentSource();
			String contentSourceLink = content.getContentSourceLink();
			if (StringUtils.isEmpty(contentSource)
					&& StringUtils.isEmpty(contentSourceLink)) {
				content.setContentSource("原创");
			}
			succ = contentService.saveOrUpdate(content, attachTitle,attachPath,
					attachRealTitle,attachFileType,
					attachSequence);
			request.setAttribute("channelCode", 1);

		} catch (Exception e) {
			logger.info("发布或者修改信息内容出错=====" + e.toString());
			succ = false;
		}
		SpringUtils.renderDwzResult(response, succ, succ ? "信息内容操作成功"
				: "信息内容操作失败", DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString());
	}

	@RequestMapping(Constants.PRE_PATH_VIEW + "get")
	public String content(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> param = this.getParameters(request);
		String ccId[] = StringUtils.split(param.get("contentIdChannelId"), ",");

		if (param.containsKey("contentIdChannelId")) {
			Integer contentId = Integer.parseInt(ccId[0]);
			Integer channeId = Integer.parseInt(ccId[1]);

			
			List<Channel> channelList = new ArrayList<Channel>();

			// int id=Integer.parseInt(param.get("contentId").toString());

			Content content = contentService.getById(contentId, channeId);
			// 获得栏目List
			String channelId[] = StringUtils
					.split(content.getChannelIds(), ",");
			for (int i = 0; i < channelId.length; i++) {
				channelList.add(channelService.getById(Integer
						.parseInt(channelId[i])));
			}
			model.addAttribute("content", content);
			model.addAttribute("channelList", channelList);
			model.addAttribute("channeIdNow", channeId);// 确认当前channelId
			
		}
		model.addAttribute("right_id", param.get("right_id"));
		model.addAttribute("nowDate", new Date());
		return "content/input";
	}

	// 获得根栏目列表
	@RequestMapping(Constants.PRE_PATH_VIEW + "getList")
	public String getList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Channel channel = new Channel();
		Map<String, String> paramsMap = getParameters(request);
		PageModel pageModel = contentService.getListById(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		// tree列表
		List treeList = channelService.getTreeList(channel);
		String url = "content/" + Constants.PRE_PATH_VIEW
				+ "getSonList?right_id=" + paramsMap.get("right_id")
				+ "&channelIds=";
		String tree = TreeUtils.getTreeModelStrings(url, "jbsxBox2", 0, 0,
				treeList);
		model.addAttribute("treeInfo", tree.replace("<ul ></ul>", ""));// 除掉最后一个ul
		return "content/menuList";
	}

	@RequestMapping(Constants.PRE_PATH_VIEW + "getSonList")
	public String sonList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User user = loginAdminUser(request);
		Map<String, String> paramsMap = getParameters(request);
		request.setAttribute("channelIds", paramsMap.get("channelIds"));
		paramsMap.put("userId", String.valueOf(user.getId()));
		// 获得列表
		PageModel pageModel = contentService.getListById(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("right_id", paramsMap.get("right_id"));// 刷新
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList = moduleService
				.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);

		Content content = new Content();
		content.setContentTitle(paramsMap.get("contentTitle"));
		model.addAttribute("searchParams", content);// 用于搜索框保留值
		return "content/sonList";
	}

	// 获得根栏目tree
	@RequestMapping("treeInfo")
	public String tree(HttpServletRequest request,
			HttpServletResponse response, Model model, Channel channel) {
		// tree列表
		List treeList = channelService.getTreeList(channel);
		// 获得已选中的栏目checkBox
		String tree = TreeUtils.getCallBackTreeStrings(0, 0, treeList);
		model.addAttribute("treeInfo", tree.replace("<ul ></ul>", ""));// 除掉最后一个ul
		return "content/treeInfo";
	}

	// 排序
	@RequestMapping("editSeq")
	public void editSeq(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String contentId = request.getParameter("contentId");
		String contentVal = request.getParameter("contentVal");
		String channelId = request.getParameter("channelId");
		ContentChannel contentChannel = new ContentChannel();
		contentChannel.setChannelId(Integer.parseInt(channelId));
		contentChannel.setContentId(Integer.parseInt(contentId));
		contentChannel.setContentSequence(Integer.parseInt(contentVal));

		try {
			if (null != contentId || !contentVal.equals("")) {
				contentChannelService.saveOrUpdate(contentChannel);
				SpringUtils.renderText(response, "success");
			} else {
				SpringUtils.renderText(response, "fail");
			}

		} catch (Exception e) {
			logger.info("修改信息内容排序出错=====" + e.toString());
		}
	}

}
