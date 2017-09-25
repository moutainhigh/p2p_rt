package com.rbao.east.controller.admin;

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
import com.rbao.east.entity.LotteryPrize;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.User;
import com.rbao.east.service.LotteryPrizeService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.utils.SpringUtils;

//奖品设置
@Controller
@RequestMapping("lotteryPrize")

public class LotteryPrizeController  extends  BaseController{
	private static Logger logger=LoggerFactory.getLogger(LotteryPrizeController.class);
	@Autowired
	private LotteryPrizeService lotteryPrizeService;
	@Autowired
	private SysModuleService moduleService;
	
	//奖品管理(显示所有)
	@RequestMapping(Constants.PRE_PATH_VIEW+"getLotteryPrizeList")
	public String getLotteryPrizeList(HttpServletRequest request,HttpServletResponse response,Model model){
		User user=loginAdminUser(request);
		
		Map<String ,String> paramsMap=getParameters(request);
		PageModel pageModel=lotteryPrizeService.getLotteryPrizeList(paramsMap);
		model.addAttribute("pm", pageModel);
		model.addAttribute("searchParams", paramsMap);
		paramsMap.put("userId", String.valueOf(user.getId()) );
		model.addAttribute("right_id", paramsMap.get("right_id"));//刷新
		// 获得当前登录用户的rightId下的子权限
		List<SysModule> righSubtList=moduleService.getRightGroupList(paramsMap);
		model.addAttribute("righSubtList", righSubtList);
		
		return "lottery/prize/lotteryPrizeList";
	}
	
	//增加
	@RequestMapping(Constants.PRE_PATH_VIEW+"addLotteryPrizeType")
	public String addLotteryPrizeType(HttpServletRequest request,HttpServletResponse response,Model model){
		
		Map<String ,String> paramsMap=getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		
		
		return "lottery/prize/addUpdateLotteryPrize";
	}
	
	//进入页面
	@RequestMapping(Constants.PRE_PATH_VIEW+"updateLotteryPrizeType")
	public String updateLotteryPrizeType(HttpServletRequest request,HttpServletResponse response,Model model ){
		Map<String,String> paramsMap=getParameters(request);
		model.addAttribute("right_id", paramsMap.get("right_id"));
		LotteryPrize lotteryPrize=lotteryPrizeService.getById(Integer.parseInt(paramsMap.get("id")));
		
		model.addAttribute("lotteryPrize", lotteryPrize);
		
		return "lottery/prize/addUpdateLotteryPrize";
	}
	//增加、修改方法
	@RequestMapping(Constants.PRE_PATH_VIEW+"saveOrUpdate")
	public void setLotteryPrizeType(HttpServletRequest request, HttpServletResponse response,
			Model model ,LotteryPrize lotteryPrize){
		Map<String,String> paramsMap = getParameters(request);
		boolean flag=false;
		
		try {
			flag=lotteryPrizeService.saveOrUpdate(lotteryPrize);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加/修改失败！",e);
			flag=false;
		}
		
		
		SpringUtils.renderDwzResult(response, flag,flag?"操作成功":"操作失败",DwzResult.CALLBACK_CLOSECURRENT,paramsMap.get("right_id"));
		
	}
	
	//删除
	@RequestMapping(Constants.PRE_PATH_VIEW+"delete")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> paramsMap=getParameters(request);
		boolean flag=false;
		try {
			Integer lotteryPrizeId=Integer.parseInt(paramsMap.get("id")) ;
			flag=lotteryPrizeService.deleteById(lotteryPrizeId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error("删除失败！", e);
			flag=false;
		}
		SpringUtils.renderDwzResult(response, flag, flag?"删除成功":"删除失败","",paramsMap.get("right_id").toString(),"lotteryPrize/v_getLotteryPrizeList?right_id="+paramsMap.get("right_id").toString() );
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
