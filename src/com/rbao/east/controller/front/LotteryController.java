package com.rbao.east.controller.front;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.result.ServiceResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.LotteryPrize;
import com.rbao.east.entity.User;
import com.rbao.east.service.ILotteryService;
import com.rbao.east.utils.SpringUtils;
/**
 * 抽奖管理
 * */
@Controller
@RequestMapping("/lottery/")
public class LotteryController extends BaseController{
	
	private static Logger logger = LoggerFactory
			.getLogger(LotteryController.class);

	@Autowired
	ILotteryService lotteryService;
	 

	@RequestMapping("lottery.do")
	public String enterLottery(HttpServletRequest request, HttpServletResponse response,
			Model model) {

		return "/lottery/startLottery";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("startLottery.do")
	public void startLottery(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User usr = this.loginFrontUser(request);
		Integer usrId = usr==null?null:usr.getId();
		//奖区类别；大中小奖区
		Integer ltType = Integer.valueOf(request.getParameter("ltType"));
		ServiceResult sRest = lotteryService.startLottery(usrId,ltType);
		
		if(!sRest.isSuccessed()){ //失败
			SpringUtils.renderJson(response, sRest);
			return;
		}
		LotteryPrize prz = (LotteryPrize) sRest.getExt();
		//旋转角度
		int angle = new Random().nextInt(prz.getPointEnd()-prz.getPointStart())+prz.getPointStart();
		
		Map m = new HashMap();
		m.put("angle", angle);
		m.put("prz", prz);		
		SpringUtils.renderJson(response, m);
	}
	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("winPrize.do")
	public void winPrize(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		User usr = this.loginFrontUser(request);
		Integer usrId = usr==null?null:usr.getId();
		
		String przId = request.getParameter("przId");
		LotteryPrize prz = lotteryService.getPrizeById(Integer.valueOf(przId));
		lotteryService.rcordWinPrize(usrId, prz);
		
	}*/
	@SuppressWarnings("rawtypes")
	@RequestMapping("getWinRecordList.do")
	public void getWinRecordList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		Map m = this.getParameters(request);
		
		SpringUtils.renderJson(response,lotteryService.getLotteryRecordPaged(m));
	}
	/**
	 * 个人抽奖记录
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("getPersonLotteryList.do")
	public String getPersonLotteryList(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = this.loginFrontUser(request);
		Map<String, String> m=new HashMap<String, String>();
		m.put("userId", String.valueOf(user.getId()));
		
		model.addAttribute("pm",lotteryService.getLotteryRecordPaged(m));
		return "member/lotteryDraw";
	}
	
}
