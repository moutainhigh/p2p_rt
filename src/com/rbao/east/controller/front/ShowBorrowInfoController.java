package com.rbao.east.controller.front;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.UserAccount;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.BorrowTenderService;
import com.rbao.east.service.UserAccountService;
import com.rbao.east.utils.SpringUtils;

/**
 * 我的账户
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("showBorrowInfo/")
public class ShowBorrowInfoController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private BorrowTenderService borrowTenderService;
	@Autowired
	private BorrowService borrowQueryService;
	/**
	 * 帐户资金情况
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/account.do")
	public String getAccount(HttpServletRequest request, HttpServletResponse response,Model model,Integer userId) throws Exception{
		try {
			UserAccount userAccount=userAccountService.getByUserId(userId) ;
	    	SpringUtils.renderJson(response, userAccount);
		} catch (Exception e) {
			System.out.println("获取帐户信息====="+e.toString());
			logger.info("获取帐户信息====="+e.toString());
		}   
		return null;
	}
	/**
	 * 信用额度情况
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/borrowInfo.do")
	public String getBorrowInfo(HttpServletRequest request, HttpServletResponse response,Model model,Integer userId) throws Exception{
		try {
			List borrow=new  ArrayList();
			borrow=borrowQueryService.getborrowInfo(userId);
	    	SpringUtils.renderJson(response, borrow.get(0));
		} catch (Exception e) {
			System.out.println("获取帐户信息====="+e.toString());
			logger.info("获取帐户信息====="+e.toString());
		}   
		return null;
	}
	/**
	 * 投标资金情况
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tender.do")
	public String getTender(HttpServletRequest request, HttpServletResponse response,Model model,Integer userId) throws Exception{
		try {
			List tender=new  ArrayList();
			tender=borrowTenderService.getbyUserId(userId) ;
	    	SpringUtils.renderJson(response, tender.get(0));
		} catch (Exception e) {
			System.out.println("获取帐户信息====="+e.toString());
			logger.info("获取帐户信息====="+e.toString());
		}   
		return null;
	}
	/**
	 * 借款资金情况
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/borrowAccount.do")
	public String getBorrowAccount(HttpServletRequest request, HttpServletResponse response,Model model,Integer userId) throws Exception{
		try {
			List borrow=new  ArrayList();
			borrow=borrowQueryService.getborrowAccount(userId);
	    	SpringUtils.renderJson(response, borrow.get(0));
		} catch (Exception e) {
			System.out.println("获取帐户信息====="+e.toString());
			logger.info("获取帐户信息====="+e.toString());
		}   
		return null;
	}
	
}
