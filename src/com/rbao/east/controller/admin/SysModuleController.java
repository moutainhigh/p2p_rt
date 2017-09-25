package com.rbao.east.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.google.gson.JsonObject;
import com.rbao.east.common.Constants;
import com.rbao.east.common.SequenceUtils;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.OperatorLog;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.TreeModel;
import com.rbao.east.entity.User;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.thread.weixin.AccessToken;
import com.rbao.east.thread.weixin.Button;
import com.rbao.east.thread.weixin.CommonButton;
import com.rbao.east.thread.weixin.ComplexButton;
import com.rbao.east.thread.weixin.Menu;
import com.rbao.east.thread.weixin.WeChatErrorCode;
import com.rbao.east.thread.weixin.WeChatUtil;
import com.rbao.east.utils.JsonUtils;
import com.rbao.east.utils.PropertiesUtil;
import com.rbao.east.utils.RequestUtils;
import com.rbao.east.utils.SpringUtils;
import com.rbao.east.utils.TreeUtils;
/**
 * 模块管理
 * */
@Controller
@RequestMapping("module/")
public class SysModuleController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(SysModuleController.class);
	
	
	
	@Autowired
	private SysModuleService moduleService;
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getModuleList")
	public String getModuleList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, String> param=this.getParameters(request);
		String target="";
		try {
			//得到UserId查询对应的权限
			User user=this.loginAdminUser(request);
			List<TreeModel> treeModels=moduleService.getTreeModelByUserId(user.getId());
			String url="module/v_getModuleListInfo?right_id="+param.get("right_id")+"&moduleId=";
			String ModuleList =TreeUtils.getTreeModelStrings(url,"moduleBox",0,0, treeModels);
			result.put("ModuleList",ModuleList.replaceAll("<ul ></ul>",""));
			if(!param.containsKey("right_id")){
				String right_id=ModuleList.substring(ModuleList.indexOf("right_id=")+9,ModuleList.indexOf("&"));
				param.put("right_id", right_id);
			}
			if(!param.containsKey("moduleId")){
				String moduleId=ModuleList.substring(ModuleList.indexOf("moduleId=")+9,ModuleList.indexOf("target")-1);
				param.put("moduleId", moduleId);
			}
			getModuleListInfo(param, request);
			result.put("code", 1);
			target="sysmodule/listModuleSearch";
			logger.info("显示用户权限树成功！");
		} catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("展示用户权限树失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return target;
	}
	
	
	public void getModuleListInfo(Map<String, String> param,HttpServletRequest request){
		Map<String, Object> result=new HashMap<String,Object>();
		User user=this.loginAdminUser(request);
		try {
			if(param.containsKey("right_id")&&param.containsKey("moduleId")){
				param.put("userId", user.getId().toString());
				// 获得当前登录用户的rightId下的子权限
				List<SysModule> righSubtList=moduleService.getRightGroupList(param);
				result.put("righSubtList", righSubtList);
				Integer currentPage=param.containsKey(Constants.PAGED_CURPAGE)?
						Integer.valueOf(param.get(Constants.PAGED_CURPAGE).toString()):1;
				if(param.containsKey("module_name")){
					result.put("module_name", param.get("module_name"));
					param.put("module_name", "%"+param.get("module_name")+"%");
				}
				PageModel pageModel = moduleService.getTreeModelListByRightId(param, currentPage);
				result.put("pm", pageModel);
				result.put("code", 1);
				result.put("right_id", param.get("right_id"));
				result.put("moduleId", param.get("moduleId"));
				logger.info("显示权限列表成功！");
			}else{
				result.put("code", 0);
				result.put("message", "数据操作出现异常，请稍后再试！");
			}
		} catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("展示权限列表失败，异常信息:" + e);
		}
		this.setParameters(result, request);
	}
	
	
	@RequestMapping(Constants.PRE_PATH_VIEW + "getModuleListInfo")
	public String getModuleListInfo(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		getModuleListInfo(param,request);
		return "sysmodule/listModuleSearchInfo";
	}
	
	/**
	 * 微信管理页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"getWeiXin")
	public String getWeiXin(HttpServletRequest request, HttpServletResponse response,
			Model model){
		//读取属性文件
		PropertiesUtil util=new PropertiesUtil();
		String appid=util.get("weixin.appID");
		String appsecret=util.get("weixin.appsecret");
		
		request.setAttribute("APP", appid);
		request.setAttribute("secret", appsecret);
		System.out.println("APPID:"+appid);
		System.out.println("appsecret:"+appsecret);
		return "sysmodule/showweixin";
	}
	
	/**
	 * 微信创建菜单
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"getWeiXins")
	public void weiXins(HttpServletRequest request, HttpServletResponse response,
			Model model){
		//第一个
		String yiji1=request.getParameter("yiji1");
		
		String erji1=request.getParameter("erji1");
		String leixing1=request.getParameter("leixing1");
		String url1=request.getParameter("url1");
/*		String key1=request.getParameter("key1");*/
		
		String erji2=request.getParameter("erji2");
		String leixing2=request.getParameter("leixing2");
		String url2=request.getParameter("url2");
/*		String key2=request.getParameter("key2");*/
		
		String erji3=request.getParameter("erji3");
		String leixing3=request.getParameter("leixing3");
		String url3=request.getParameter("url3");
/*		String key3=request.getParameter("key3");*/
		
		String erji4=request.getParameter("erji4");
		String leixing4=request.getParameter("leixing4");
		String url4=request.getParameter("url4");
/*		String key4=request.getParameter("key4");*/
		
		String erji5=request.getParameter("erji5");
		String leixing5=request.getParameter("leixing5");
		String url5=request.getParameter("url5");
/*		String key5=request.getParameter("key5");*/
		
		
		//第二个
		String yiji2=request.getParameter("yiji2");
		
		String erji6=request.getParameter("erji6");
		String leixing6=request.getParameter("leixing6");
		String url6=request.getParameter("url6");
/*		String key6=request.getParameter("key6");*/
		
		String erji7=request.getParameter("erji7");
		String leixing7=request.getParameter("leixing7");
		String url7=request.getParameter("url7");
/*		String key7=request.getParameter("key7");*/
		
		String erji8=request.getParameter("erji8");
		String leixing8=request.getParameter("leixing8");
		String url8=request.getParameter("url8");
/*		String key8=request.getParameter("key8");*/
		
		String erji9=request.getParameter("erji9");
		String leixing9=request.getParameter("leixing9");
		String url9=request.getParameter("url9");
/*		String key9=request.getParameter("key9");*/
		
		String erji10=request.getParameter("erji10");
		String leixing10=request.getParameter("leixing10");
		String url10=request.getParameter("url10");
/*		String key10=request.getParameter("key10");*/
		
		//第三个
		String yiji3=request.getParameter("yiji3");
		
		String erji11=request.getParameter("erji11");
		String leixing11=request.getParameter("leixing11");
		String url11=request.getParameter("url11");
/*		String key11=request.getParameter("key11");*/
		
		String erji12=request.getParameter("erji12");
		String leixing12=request.getParameter("leixing12");
		String url12=request.getParameter("url12");
/*		String key12=request.getParameter("key12");*/
		
		String erji13=request.getParameter("erji13");
		String leixing13=request.getParameter("leixing13");
		String url13=request.getParameter("url13");
/*		String key13=request.getParameter("key13");*/
		
		String erji14=request.getParameter("erji14");
		String leixing14=request.getParameter("leixing14");
		String url14=request.getParameter("url14");
/*		String key14=request.getParameter("key14");*/
		
		String erji15=request.getParameter("erji15");
		String leixing15=request.getParameter("leixing15");
		String url15=request.getParameter("url15");
/*		String key15=request.getParameter("key15");*/
		
		
		
		
		//二级菜单按钮
		CommonButton button1=new CommonButton();
		button1.setName(erji1);
		button1.setType(leixing1);
		button1.setUrl(url1);
		CommonButton button2=new CommonButton();
		button2.setName(erji2);
		button2.setType(leixing2);
		button2.setUrl(url2);
		CommonButton button3=new CommonButton();
		button3.setName(erji3);
		button3.setType(leixing3);
		button3.setUrl(url3);
		CommonButton button4=new CommonButton();
		button4.setName(erji4);
		button4.setType(leixing4);
		button4.setUrl(url4);
		CommonButton button5=new CommonButton();
		button5.setName(erji5);
		button5.setType(leixing5);
		button5.setUrl(url5);
		CommonButton button6=new CommonButton();
		button6.setName(erji6);
		button6.setType(leixing6);
		button6.setUrl(url6);
		CommonButton button7=new CommonButton();
		button7.setName(erji7);
		button7.setType(leixing7);
		button7.setUrl(url7);
		CommonButton button8=new CommonButton();
		button8.setName(erji8);
		button8.setType(leixing8);
		button8.setUrl(url8);
		CommonButton button9=new CommonButton();
		button9.setName(erji9);
		button9.setType(leixing9);
		button9.setUrl(url9);
		CommonButton button10=new CommonButton();
		button10.setName(erji10);
		button10.setType(leixing10);
		button10.setUrl(url10);
		CommonButton button11=new CommonButton();
		button11.setName(erji11);
		button11.setType(leixing11);
		button11.setUrl(url11);
		CommonButton button12=new CommonButton();
		button12.setName(erji12);
		button12.setType(leixing12);
		button12.setUrl(url12);
		CommonButton button13=new CommonButton();
		button13.setName(erji13);
		button13.setType(leixing13);
		button13.setUrl(url13);
		CommonButton button14=new CommonButton();
		button14.setName(erji14);
		button14.setType(leixing14);
		button14.setUrl(url14);
		CommonButton button15=new CommonButton();
		button15.setName(erji15);
		button15.setType(leixing15);
		button15.setUrl(url15);
		
		
		ComplexButton cb1=null;
		ComplexButton cb2=null;
		ComplexButton cb3=null;
		
		if (erji2.equals("") && erji7.equals("") && erji12.equals("")) {
			//一级按钮(5个子菜单)
			cb1=new ComplexButton();
			cb1.setName(yiji1);
			cb1.setSub_button(new CommonButton[]{				
					button1
					});
			cb2=new ComplexButton();
			cb2.setName(yiji2);
			cb2.setSub_button(new CommonButton[]{				
					button6
					});
			cb3=new ComplexButton();
			cb3.setName(yiji3);
			cb3.setSub_button(new CommonButton[]{				
					button11
			});
		}else if (erji3.equals("")&& erji8.equals("") && erji13.equals("")) {
			cb1=new ComplexButton();
			cb1.setName(yiji1);
			cb1.setSub_button(new CommonButton[]{				
					button1,button2
					});
			cb2=new ComplexButton();
			cb2.setName(yiji2);
			cb2.setSub_button(new CommonButton[]{				
					button6,button7
					});
			cb3=new ComplexButton();
			cb3.setName(yiji3);
			cb3.setSub_button(new CommonButton[]{				
					button11,button12
			});			
		}else if(erji4.equals("") && erji9.equals("") && erji14.equals("")){
			cb1=new ComplexButton();
			cb1.setName(yiji1);
			cb1.setSub_button(new CommonButton[]{				
					button1,button2,button3
					});
			cb2=new ComplexButton();
			cb2.setName(yiji2);
			cb2.setSub_button(new CommonButton[]{				
					button6,button7,button8
					});
			cb3=new ComplexButton();
			cb3.setName(yiji3);
			cb3.setSub_button(new CommonButton[]{				
					button11,button12,button13
			});	
		}else if (erji5.equals("") && erji10.equals("") && erji15.equals("")) {
			cb1=new ComplexButton();
			cb1.setName(yiji1);
			cb1.setSub_button(new CommonButton[]{				
					button1,button2,button3,button4
					});
			cb2=new ComplexButton();
			cb2.setName(yiji2);
			cb2.setSub_button(new CommonButton[]{				
					button6,button7,button8,button9
					});
			cb3=new ComplexButton();
			cb3.setName(yiji3);
			cb3.setSub_button(new CommonButton[]{				
					button11,button12,button13,button14
			});	
			
		}else {
			//一级按钮(5个子菜单)
			cb1=new ComplexButton();
			cb1.setName(yiji1);
			cb1.setSub_button(new CommonButton[]{				
					button1,button2,button3,button4,button5,
					});
			cb2=new ComplexButton();
			cb2.setName(yiji2);
			cb2.setSub_button(new CommonButton[]{				
					button6,button7,button8,button9,button10,
					});
			cb3=new ComplexButton();
			cb3.setName(yiji3);
			cb3.setSub_button(new CommonButton[]{				
					button11,button12,button13,button14,button15,
					});
		}
		//创建按钮基类
		Menu menu1=new Menu();
		menu1.setButton(new Button[]{
		cb1,cb2,cb3
		});
		
/*	    // 将菜单对象转换成json字符串  
	    String jsonMenu = JSONObject.fromObject(menu1).toString();  */
		String appid=request.getParameter("appid");
		String appsecret=request.getParameter("appser");
		//获取Token （获取到的Token2小时有效，可重复使用）
		AccessToken at = WeChatUtil.getAccessToken(appid, appsecret);
		
		if (at!=null&&at.getToken()!=null) {
			System.out.println("获取到的TOKEN：\n"+at.getToken());
			// 调用接口创建菜单
			int result = WeChatUtil.createMenu(menu1,at.getToken());
			String ss=Integer.toString(result);
			String msg=(String)WeChatErrorCode.ERRORCODE.get(result);
			JsonResult a=new JsonResult();
			// 判断菜单创建结果
			if (0==result) {
				a.setCode("200");
				a.setMsg("菜单创建成功！");
			}else {
				a.setCode(ss);
				a.setMsg(msg);
			}
		JsonUtils.toJson(response, a);
		}
	}
	/**
	 * 微信   图文消息 跳转页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"songTuWen")
	public String songTuWen(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		//读取属性文件
		PropertiesUtil util=new PropertiesUtil();
		String appid=util.get("weixin.appID");
		String appsecret=util.get("weixin.appsecret");
		
		request.setAttribute("APP", appid);
		request.setAttribute("secret", appsecret);
		return "sysmodule/songTuWen";
	}

	
	/**
	 * 微信 发送 图文消息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"songTuWens")
	public void songTuWens(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		 String filePath=null;
		filePath=request.getParameter("borrowPicture");//图片
		String filePaths = request.getSession().getServletContext().getRealPath("") + filePath; 
		System.out.println("filePath****************");
        String name=request.getParameter("name");//标题
        String auhtor=request.getParameter("author");//作者
        filePath=request.getParameter("borrowPicture");//图片
        String miaosu=request.getParameter("miaosu");//描述
        String url=request.getParameter("url");//链接
        String zhengwen=request.getParameter("moduleDescribe");//正文
		JsonResult b=new JsonResult();
		//读取属性文件
		PropertiesUtil util=new PropertiesUtil();
		String appid=util.get("weixin.appID");
		String appsecret=util.get("weixin.appsecret");
		
		if (filePath==null) {
			b.setCode("400");
			b.setMsg("请添加图片！");
			JsonUtils.toJson(response, b);
		}
		
		//获取token
        WeChatUtil w=new WeChatUtil();
        AccessToken t=w.getAccessToken(appid, appsecret);
        if (t==null) {
		b.setCode("300");
		b.setMsg("获取Token失败！");
		JsonUtils.toJson(response, b);
		}
        //微信图片的 mid
        String j= w.send("image", filePaths, t.getToken());
		int a=0;//发送图文消息。
        if (j!=null) {
			Map map=new LinkedHashMap();
			map.put("thumb_media_id",j);
			map.put("author",auhtor);
			map.put("title", name);
			map.put("content_source_url", url);
			map.put("content", zhengwen);
			map.put("digest", miaosu);
			map.put("show_cover_pic", "1");
			
			Map map2=new LinkedHashMap();
			List<Map> list=new ArrayList<Map>();
			list.add(map);
			map2.put("articles", list);
			String ssss=JsonUtils.toParseJson(map2);
			//微信图文素材mid
			String sucai=w.tuPianSuCai(t.getToken(), ssss);
			if (sucai.equals("")) {
				b.setCode("45009");
				b.setMsg("接口调用超过限制！");
				JsonUtils.toJson(response, b);
			}
			//如果上传的图文素材id不为空
			if (sucai!=null) {
				//获取用户ID
				String yonghu=w.yonghu(t.getToken());
				if (yonghu!=null) {//用户ID不为空
					String[] ids=yonghu.split(",");//用户组ID数组
					for (String od : ids) {
						//组装群发数据 
						JsonObject json=new JsonObject();
						JsonObject f=new JsonObject();
						f.addProperty("is_to_all", "false");
						f.addProperty("group_id", od);
						json.add("filter", f);
						
						JsonObject json2=new JsonObject();
						json2.addProperty("media_id", sucai);
						json.add("mpnews", json2);
						json.addProperty("msgtype", "mpnews"); 
						
						a=w.sendMsg(t.getToken(), json.toString());
						System.out.println("****************发送完返回的数据：**************"+a);	
						
					}
				}
			}	
		}
		String ss=Integer.toString(a);
		String msg=(String)WeChatErrorCode.ERRORCODE.get(a);

		// 判断菜单创建结果
		if (0==a) {
			b.setCode("200");
			b.setMsg("群发成功！");
		}else {
			b.setCode(ss);
			b.setMsg(msg);
		}
	JsonUtils.toJson(response, b);
	}
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "saveSysModule")
	public String saveSysModule(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		Map<String, Object> result=new HashMap<String,Object>();
		User user=this.loginAdminUser(request);
		String target="";
		try {
			if("toJsp".equals(param.get("type").toString())){
				request.setAttribute("right_id", param.get("right_id"));
				target="sysmodule/saveSysModule";
			}else if("add".equals(param.get("type").toString())){
				SysModule module=new SysModule();
				module.setModuleName(param.get("moduleName"));
				module.setModuleUrl(param.get("moduleUrl"));
				module.setModuleView(Integer.parseInt(param.get("moduleView").toString()));
				module.setModuleDescribe(param.get("moduleDescribe"));
				module.setModuleUnicode(SequenceUtils.getMD5Str(param.get("moduleName")));
				if(param.containsKey("district.id")){
					module.setModuleParentId(Integer.parseInt(param.get("district.id").toString()));
				}else{
					module.setModuleParentId(0);
				}
				if(param.containsKey("moduleSequence")){
					module.setModuleSequence(Integer.parseInt(param.get("moduleSequence").toString()));
				}else{
					module.setModuleSequence(0);
				}
				result.put("module", module);
				result.put("userId", user.getId());
				boolean bool=moduleService.insert(result);
				SpringUtils.renderDwzResult(response, bool, bool?"添加权限成功":"添加权限失败"
						,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
				target=null;
				logger.info("添加权限成功");
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("添加权限");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_AUTHORITY);
				operatorLog.setOperatorParams(result.toString());
				operatorLog.setOperatorReturn(bool?"添加权限成功":"添加权限失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
			}
		} catch (Exception e) {
			
			logger.error("添加权限信息失败，异常信息:" + e);
		}
		return target;
	}
	
	
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "updateSysModule")
	public String updateSysModule(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		Map<String, Object> result=new HashMap<String,Object>();
		String target="";
		User user=this.loginAdminUser(request);
		try {
			if("toJsp".equals(param.get("type").toString())){
				SysModule module=moduleService.selectByPrimaryKey(Integer.parseInt(param.get("supportID").toString()));
				request.setAttribute("module", module);
				request.setAttribute("right_id", param.get("right_id"));
				target="sysmodule/updateSysModule";
			}else if("update".equals(param.get("type").toString())){
				SysModule module=new SysModule();
				module.setId(Integer.parseInt(param.get("id").toString()));
				module.setModuleName(param.get("moduleName"));
				module.setModuleUrl(param.get("moduleUrl"));
				module.setModuleView(Integer.parseInt(param.get("moduleView").toString()));
				module.setModuleDescribe(param.get("moduleDescribe"));
				module.setModuleUnicode(SequenceUtils.getMD5Str(param.get("moduleName")));
				module.setModuleParentId(Integer.parseInt(param.get("district.id").toString()));
				if(param.containsKey("moduleSequence")){
					module.setModuleSequence(Integer.parseInt(param.get("moduleSequence").toString()));
				}else{
					module.setModuleSequence(0);
				}
				boolean bool=moduleService.update(module);
				SpringUtils.renderDwzResult(response, bool, bool?"修改权限成功":"修改权限失败"
						,DwzResult.CALLBACK_CLOSECURRENT,param.get("right_id").toString()); 
				target=null;
				logger.info("修改权限成功");
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(user.getUserAccount());
				operatorLog.setOperatorTitle("修改权限");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_AUTHORITY);
				operatorLog.setOperatorParams(result.toString());
				operatorLog.setOperatorReturn(bool?"修改权限成功":"修改权限失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
			}
		} catch (Exception e) {
			
			logger.error("修改权限信息失败，异常信息:" + e);
		}
		return target;
	}
	
	
	@RequestMapping(Constants.PRE_PATH_EDIT + "deleteSysModule")
	public void deleteSysModule(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> param=this.getParameters(request);
		Map<String, Object> result=new HashMap<String,Object>();
		try {
				Integer id=Integer.parseInt(param.get("supportID").toString());
				boolean bool=moduleService.deleteByPrimaryKey(id);
				SpringUtils.renderDwzResult(response, bool, bool?"删除权限成功":"删除权限失败","",param.get("right_id").toString(),"module/v_getModuleList?right_id="+param.get("right_id").toString()); 
				logger.info("删除权限成功");
				OperatorLog operatorLog = new OperatorLog();
				operatorLog.setLogUserid(id.toString());
				operatorLog.setOperatorTitle("删除权限");
				operatorLog.setOperatorType(OperatorLog.TYPE_ADMIN);
				operatorLog.setOperatorCategory(OperatorLog.CATEGORY_AUTHORITY);
				operatorLog.setOperatorParams(result.toString());
				operatorLog.setOperatorReturn(bool?"删除权限成功":"删除权限失败");
				operatorLog.setOperatorStatus(bool?200:300);
				operatorLog.setLinkUrl(RequestUtils.getIpAddr());
				operatorLogService.addAdminLog(operatorLog);
		} catch (Exception e) {
			
			logger.error("删除权限信息失败，异常信息:" + e);
		}
	}
	
	/**
	 * 显示权限树
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW + "shwoRightList")
	public String shwoRightList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> result=new HashMap<String,Object>();
		User user=this.loginAdminUser(request);
		try {
			//得到UserId查询对应的权限
			List<TreeModel> treeModels=moduleService.getTreeModelByUserId(user.getId());
			String outGroupHtml=TreeUtils.getCallBackTreeString(0,0, treeModels);
			result.put("outGroupHtml",outGroupHtml);
			result.put("code", 1);
			logger.info("显示用户权限树成功！");
		} catch (Exception e) {
			
			result.put("code", 0);
			result.put("message", "数据操作出现异常，请稍后再试！");
			logger.error("展示权限树失败，异常信息:" + e);
		}
		this.setParameters(result, request);
		return "sysmodule/shwoRight";
	}
	
	
}
