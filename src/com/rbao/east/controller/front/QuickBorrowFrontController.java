package com.rbao.east.controller.front;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.rbao.east.common.result.JsonResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.dao.QuickBorrowDao;
import com.rbao.east.entity.BorrowType;
import com.rbao.east.entity.QuickBorrow;
import com.rbao.east.entity.User;
import com.rbao.east.service.BorrowTypeService;
import com.rbao.east.service.QuickBorrowService;
import com.rbao.east.utils.SpringUtils;

/**
 * 快速贷款
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("quickBorrow/")
public class QuickBorrowFrontController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(QuickBorrowFrontController.class);
	@Autowired
	private QuickBorrowService quickBorrowService;
	@Autowired
	private BorrowTypeService borrowTypeService;
	@Autowired
	private QuickBorrowDao quickBorrowDao;
	@RequestMapping("input.do")
	public String addEmail(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User u = loginFrontUser(request);
		if(u==null){
			try {
				response.sendRedirect(request.getContextPath()+"/toLogin.do");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		PageModel pageModel = quickBorrowService.getPagedList(new HashMap());
		model.addAttribute("borrowList", pageModel.getList());
		return "quickBorrow/input";
	}
    //查询借款人
	@RequestMapping("getList.do")
	public void getBorrowUserList(HttpServletRequest request,
			HttpServletResponse response, Model model){
		PageModel pageModel = quickBorrowService.getPagedList(new HashMap());
		SpringUtils.renderJson(response, pageModel);
	}
	@RequestMapping("save.do")
	public void requestAddEmail(HttpServletRequest request,
			HttpServletResponse response, QuickBorrow quickBorrow) {
		// 万元转换为元
		User user = super.loginFrontUser(request);
		JsonResult rest =null;
		if(validateCaptcha(request)){
			//判断借款人三天之内是否重复提交申请
			Map<String,String> params = new HashMap<String,String>();
			String userName=null;
			if(quickBorrow.getMainUse()!=null){
				userName=quickBorrow.getUserName();
			}else{
				userName = user.getUserAccount();
			}
			params.put("userName", userName);
			Calendar   cal   =   Calendar.getInstance();
			cal.add(Calendar.DATE,   -3);
			String day = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(cal.getTime());
			params.put("beginTime", day);
			Integer count = quickBorrowDao.getTotalCount("selectCount", params);
			if(count>0){
				rest = new JsonResult(JsonResult.ERROR, "您的借款需求已成功提交，我们将在3个工作日内与您联系，无需重复提交");
				SpringUtils.renderJson(response, rest);
			}else{
				rest = new JsonResult(JsonResult.SUCCESS, "您的借款需求已成功提交，我们将在3个工作日内与您联系");
			if (user != null) {
				quickBorrow.setUserId(user.getId());
			}
			quickBorrow.setBorrowAmount(quickBorrow.getBorrowAmount().multiply(
					new BigDecimal(10000)));
			quickBorrow.setCreateTime(new Date());
			quickBorrow.setCreateIp(super.getIpAddr(request));
			quickBorrow.setStatus(QuickBorrow.STATUS_NEW);
			try {
				quickBorrowService.saveOrUpdate(quickBorrow);
			} catch (Exception e) {
				
				logger.error("quick borrow save error:" + quickBorrow, e);
				rest = new JsonResult("104", "快速借款申请失败");
			}
			SpringUtils.renderJson(response, rest);
		}
	}else{
		SpringUtils.renderJson(response, new JsonResult("100","验证码错误"));
	}
	}
}
