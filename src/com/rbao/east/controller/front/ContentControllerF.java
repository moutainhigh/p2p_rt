package com.rbao.east.controller.front;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Channel;
import com.rbao.east.entity.Content;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.ChannelService;
import com.rbao.east.service.ContentService;
import com.rbao.east.service.ReportService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.TreeUtils;


/**
 * 信息
 * @author Sandy
 *
 */
@Controller
@RequestMapping("/content")
public class ContentControllerF extends BaseController {
	@Autowired 
	private ChannelService channelService;
	@Autowired
	private ContentService contentService;	
	@Autowired
	private UserService userService;
	@Autowired
	private BorrowService borrowService;
	/**
	 * 信息列表  页面右边
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/contentList/{channelIdParent}/{channelIdSon}.do")
	public String contentList(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("channelIdParent") Integer channelIdParent,@PathVariable("channelIdSon") Integer channelIdSon){
		String target = "content/contentList";
		//List
		Map<String, String> param=new HashMap<String, String>();
		param.put("channelDisplay", "1");
		param.put("channelParentId", "0");
		if(channelIdParent == 77){//专题中心
			Channel parentChannel = channelService.getById(77);
			model.addAttribute("pChannel",parentChannel);
			param.put("channelParentId", "77");
			Map<String, String> params = new HashMap<String, String>();
			params.put("contentStatus", "1");
			List<Content> contentList = contentService.getContentTitle(params);
			List<Channel> relationChannels = channelService.getHot(param);
			int userCount = userService.getAllUsers();
			BigDecimal tender_sum = borrowService.gettenderSum(params);
			model.addAttribute("tenderSum",tender_sum);
			model.addAttribute("userCount",userCount);
			model.addAttribute("contentsList",contentList);
			model.addAttribute("relationChannels",relationChannels);
			target = "content/ztzxContentList";
		}
		List<Channel> channelLists=channelService.getByParentId(param);
		model.addAttribute("channelList",channelLists);
		//为获得标题
		Channel channel =channelService.getById(channelIdSon);
		param.clear();
		param.put("channelId", channel.getId()+"");
		
		if(channelIdSon==76){ //咨询中心
			target = "content/newContentList";
		}
		if(channelIdParent == 77 || channelIdSon==76){//专题中心
			param.put("channelDisplay", "(0,1)");
			param.put("orderBy", "content_sequence");
		}
		List<Map<String,Object>> contents = channelService.getContentByChannelId(param);
		model.addAttribute("channel",channel);
		model.addAttribute("contents",contents);
		
		return target;
	}
	
	@RequestMapping("/getContentListJson.do")
	public void getContentListJson(HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, String> param=this.getParameters(request);
		PageModel page = channelService.getContentPageByChannelId(param);
		SpringUtils.renderJson(response, page);
	}
	
	@RequestMapping("/getContentListMapJson.do")
	public void getContentListMapJson(HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, String> param=this.getParameters(request);
		List<Map<String,Object>> contents = channelService.getContentByChannelId(param);
		SpringUtils.renderJson(response, contents);
	}
	/**
	 * 内容分页 页面左边
	 * @param request
	 * @param response
	 * @param model
	 * @param channelIdParent
	 * @param channelIdSon
	 */
	
	@RequestMapping("/contentPageList/{channelIdParent}/{channelIdSon}.do")
	public void contentPageList(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("channelIdParent") Integer channelIdParent,@PathVariable("channelIdSon") Integer channelIdSon){
		Map<String, String> param=this.getParameters(request);
		//栏目列表
		Channel channel=new Channel();
		channel.setChannelDisplay(1);
		List treeList=channelService.getListByDisplay(channel);
		String tree =TreeUtils.getMenuParent(0, 0, treeList,channelIdParent);
		model.addAttribute("treeInfo",tree.replace("<dd></dd>", ""));//除掉最后一个dd
		
		Integer channelId=channelIdSon;//默认子节点
		if(channelIdParent==0){//无父节点
			 channelId=channelIdSon;
		}else if(channelIdSon==0){//无子节点
			 channelId=channelIdParent;
		}
		//为获得内容List
		param.put("channelId", String.valueOf(channelId));
		param.put("contentStatus", "1");//内容状态  1显示 
		
		PageModel page=contentService.showContentByPage(param);
		if(page.getList().size()>0){//如果有内容显示内容
			SpringUtils.renderJson(response, page);
		}else{//否则显示本身内容
			param.put("id", String.valueOf(channelId));
			PageModel  channelContentPage=channelService.getByIdPage(param);
			SpringUtils.renderJson(response, channelContentPage);
		}
		
		
	}
	/**
	 * 内容详情
	 * @param request
	 * @param response
	 * @param model
	 * @param channelId
	 * @param contentId
	 * @return
	 */
	@RequestMapping("/contentDetails/{channelId}/{contentId}.do")
	public String contentDetails(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("channelId") Integer channelId,@PathVariable("contentId") Integer contentId){
		String target = "content/contentDetails";
		if(channelId==76){
			target = "content/newContentDetails";
		}
		//栏目名称
		Channel channel=channelService.getById(channelId);
		model.addAttribute("channel",channel);
		//栏目列表
		Map<String, String> param=new HashMap<String, String>();
		param.put("channelDisplay", "1");
		param.put("channelParentId", "0");
		List<Channel> channelLists=channelService.getByParentId(param);
		model.addAttribute("channelList",channelLists);
		if(contentId!=0){//内容详情
			Content content = contentService.getById(contentId,null);
			model.addAttribute("contentDetails",content);
			try {
				contentService.updateByPrimaryKeyForNumber(contentId);
			} catch (Exception e) {
				System.out.println("更改文章点击量出错===="+e.toString());
			}
			
		}
		
		return target;
	}
	
	
	/**
	 * 内容详情
	 * @param request
	 * @param response
	 * @param model
	 * @param channelId
	 * @param contentId
	 * @return
	 */
	@RequestMapping("/contentDetails/{pChannelId}/{channelId}/{contentId}.do")
	public String contentDetails(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable("pChannelId") Integer pChannelId,
			@PathVariable("channelId") Integer channelId,@PathVariable("contentId") Integer contentId){
		String target = "content/newContentDetails";
		//栏目名称
		Channel pChannel = channelService.getById(pChannelId);
		model.addAttribute("pChannel",pChannel);
		//栏目名称
		Channel channel = channelService.getById(channelId);
		model.addAttribute("channel",channel);
		//栏目列表
		Map<String, String> param = new HashMap<String, String>();
		param.put("channelDisplay", "1");
		param.put("channelParentId", String.valueOf(pChannelId));
		List<Channel> channelLists = channelService.getByParentId(param);
		model.addAttribute("channelList",channelLists);
		List<Channel> relationChannels = channelService.getHot(param);
		model.addAttribute("relationChannels",relationChannels);
		
		param.put("contentStatus", "1");
		List<Content> contentList = contentService.getContentTitle(param);
		model.addAttribute("contentList",contentList);
		
		if(contentId!=0){//内容详情
			Content content = contentService.getById(contentId,null);
			model.addAttribute("contentDetails",content);
			try {
				contentService.updateByPrimaryKeyForNumber(contentId);
			} catch (Exception e) {
				System.out.println("更改文章点击量出错===="+e.toString());
			}
			
		}
		
		return target;
	}
	
	
	/**
	 * 内容详情
	 * @param request
	 * @param response
	 * @param model
	 * @param channelId
	 * @param contentId
	 * @return
	 */
	@RequestMapping("/channelDetail/{channelId}.do")
	public String channelDetail(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("channelId") Integer channelId){
		//List
		Map<String, String> param=new HashMap<String, String>();
		param.put("channelDisplay", "1");
		param.put("channelParentId", "0");
		List<Channel> channelLists=channelService.getByParentId(param);
		model.addAttribute("channelList",channelLists);
		//栏目名称
		Channel channel=channelService.getById(channelId);
		model.addAttribute("channelDetails",channel);
		
		return "content/channelDetails";
	}
	
		// 帮助中心
		@RequestMapping("/help.do")
		public String aboutLeftNav(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			return "content/leftNav";
		}
		// 产品介绍
		@RequestMapping("/cpjs.do")
		public String aboutLeftNav2(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			return "content/cpjsHelp";
		}
		// 名词解释
		@RequestMapping("/mcjs.do")
		public String aboutLeftNav3(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			return "content/mcjsHelp";
		}
		// 网站资费
		@RequestMapping("/wzzf.do")
		public String aboutLeftNav4(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			return "content/wzzfHelp";
		}
		// 政策法规
		@RequestMapping("/zcfg.do")
		public String aboutLeftNav5(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			return "content/zcfgHelp";
		}
		// 隐私保护
		@RequestMapping("/ysbh.do")
		public String aboutLeftNav6(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			return "content/ysbhHelp";
		}
		/**
		 * 首页bannel活动公告跳转页
		 * @param request
		 * @param response
		 * @param model
		 * @param channelId
		 * @param contentId
		 * @return
		 */
		@RequestMapping("/activity.do")
		public String channelDetail(HttpServletRequest request, HttpServletResponse response, Model model){
			return "content/activityDetails";
		}		
		
}