package com.rbao.east.controller.front;

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

import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Channel;
import com.rbao.east.entity.Content;
import com.rbao.east.service.ChannelService;
import com.rbao.east.service.ContentService;

@Controller
@RequestMapping("/channel")
public class ChannelControllerF extends BaseController {
	@Autowired 
	private ChannelService channelService;
	@Autowired
	private ContentService contentService;

	@RequestMapping("/channelList/{channelIdParent}/{channelIdSon}.do")
	public String contentList(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("channelIdParent") Integer channelIdParent,@PathVariable("channelIdSon") Integer channelIdSon){
		String target = "channel/channelList";
		Map<String, String> param=new HashMap<String, String>();
		
		param.put("channelDisplay", "1");
		param.put("channelParentId", String.valueOf(channelIdSon));
		List<Channel> channelLists = channelService.getByParentId(param);
		List<Channel> relationChannels = channelService.getHot(param);
		
		param.put("contentStatus", "1");
		List<Content> contentList = contentService.getContentTitle(param);
		model.addAttribute("contentList",contentList);
		//为获得标题
		Channel channel =channelService.getById(channelIdSon);
		model.addAttribute("channelList",channelLists);
		model.addAttribute("relationChannels",relationChannels);
		model.addAttribute("channel",channel);
		
		return target;
	}
}
