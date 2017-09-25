package com.rbao.east.appapi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.appapi.common.AppUtils;
import com.rbao.east.appapi.common.ResponseDto;
import com.rbao.east.appapi.common.Status;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Content;
import com.rbao.east.service.ChannelService;
import com.rbao.east.service.ContentService;
import com.rbao.east.utils.JsonUtils;

@Controller
@RequestMapping("content/")
public class AppContentController extends BaseController {

	@Autowired
	private ChannelService channelService;
	@Autowired
	private ContentService contentService;

	/**
	 * 获取最新公告
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("content.do")
	public void content(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> page = this.getParametersO(request);
		ResponseDto dto=new ResponseDto();
		Map<String, String> params=new HashMap<String, String>();
		params.put("channelId", "71");
		try {
			
			if(page!=null){
				//页数：默认为1
				if(page.containsKey(Constants.PAGED_CURPAGE)){
					params.put(Constants.PAGED_CURPAGE, page.get(Constants.PAGED_CURPAGE).toString());
				}else{
					params.put(Constants.PAGED_CURPAGE, "1");
				}
				// 每页条数
				if (page.containsKey(Constants.PAGED_NUM_PERPAGE)) {
					params.put(Constants.PAGED_NUM_PERPAGE, page.get(Constants.PAGED_NUM_PERPAGE).toString());
				} else {
					params.put(Constants.PAGED_NUM_PERPAGE, "10");
				}
			}
			PageModel pageModel=channelService.getContentPageByChannelId(params);
			List retList = pageModel.getList();
			List<Map> picList = new LinkedList<Map>();
			for (Iterator<Map> i = retList.iterator(); i.hasNext();) {
				Map<String, Object> map = i.next();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("contentId",map.get("id").toString());//ID
				data.put("contentTitle", map.get("content_title").toString());//标题
				data.put("addTime", map.get("content_add_datetime").toString());//添加时间
				data.put("externalLink", map.get("external_link_title").toString());//标题外部链接
				data.put("attachPath", map.get("attach_path")+"");
				picList.add(data);
			}
			dto.addKeyValue(Constants.TOTAL_PAGE, pageModel.getTotalPage()+"");
			dto.addKeyValue(Constants.PAGED_CURPAGE,params.get(Constants.PAGED_CURPAGE).toString());
			dto.addKeyValue(Constants.PAGED_NUM_PERPAGE,params.get(Constants.PAGED_NUM_PERPAGE).toString());
			dto.addKeyValue("picList",picList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("信息查询成功");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("获取信息失败");
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}
	}
	
	
	/**
	 * 查看公告详情
	 * @param request
	 * @param response
	 */
	@RequestMapping("contentDetail.do")
	public void contentDetail(HttpServletRequest request, HttpServletResponse response)
	{
		ResponseDto dto=new ResponseDto();
		dto.setCode(Status.FAILD.getName());
		Map<String, Object> param=this.getParametersO(request);
		if(!param.containsKey("contentId")){
			dto.setMessage("参数错误");
			return ;
		}
		try {
			Map<String, String> contentMap=new HashMap<String, String>();
			Integer contentId=Integer.parseInt(param.get("contentId").toString());
			if(contentId!=0){//内容详情
				Content content = contentService.getById(contentId,null);
				contentMap=AppUtils.toObjectMap(new String[]{"contentTitle","contentTxt","contentAddDatetime"},content, null);
			}else{
				contentMap.put("contentTitle", "");
				contentMap.put("contentTxt", "");
				contentMap.put("content_add_datetime", "");
			}
			dto.addKeyValue("contentDetail", contentMap);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("获取信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(Status.ERROR.getName());
			dto.setMessage("获取信息失败");
		}finally{
			JsonUtils.toObjectJson(response,dto);
		}
	}
	
	/**
	 * 
	* @Title: appGetH5Content
	* @Description: 根据id，得到h5的界面，统一放在app下
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping("appGetH5Content.do")
	public String appGetH5Content(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> param=this.getParametersO(request);
		ResponseDto dto=new ResponseDto();
		if(!param.containsKey("contentId")){
			dto.setCode(Status.FAILD.toString());
			dto.setMessage("输入id错误");
			JsonUtils.toObjectJson(response,dto);
			return "";
		}else{
			String result = param.get("contentId").toString();
			return result;
		}
	}

	
}
