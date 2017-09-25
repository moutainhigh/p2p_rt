package com.rbao.east.appapi;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.appapi.common.ResponseDto;
import com.rbao.east.appapi.common.Status;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.dao.QuickBorrowDao;
import com.rbao.east.entity.QuickBorrow;
import com.rbao.east.entity.User;
import com.rbao.east.service.QuickBorrowService;
import com.rbao.east.utils.JsonUtils;
import com.rbao.east.utils.StringUtil;

@Controller
@RequestMapping("quickBorrowApp/")
public class QuickBorrowAppController extends BaseController{

	@Autowired
	private QuickBorrowService quickBorrowService;
	
	@Autowired
	private QuickBorrowDao quickBorrowDao;
	
	/**
	 * 借款
	 * @param request
	 * @param response
	 * @param quickBorrow
	 */
	@RequestMapping("save.do")
	public void requestAddEmail(HttpServletRequest request,
			HttpServletResponse response, QuickBorrow quickBorrow) {
		
		User user =super.loginAPPUser(request);
		ResponseDto dto = new ResponseDto();
		//if (validateCaptcha(request)) {
			// 判断借款人三天之内是否重复提交申请
			Map<String, String> params = new HashMap<String, String>();
			String userName = null;
			if (quickBorrow.getMainUse() != null) {
				userName = quickBorrow.getUserName();
			} else {
				userName = user.getUserAccount();
			}
			params.put("userName", userName);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -3);
			String day = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
			params.put("beginTime", day);
			Integer count = quickBorrowDao.getTotalCount("selectCount", params);
			if (count > 0) {
				dto.setCode(Status.FAILD.getName());
				dto.setMessage("您的借款需求已成功提交，我们将在3个工作日内与您联系，无需重复提交");
			} else {
				dto.setCode(Status.SUCCESS.getName());
				dto.setMessage("您的借款需求已成功提交，我们将在3个工作日内与您联系");
				
				if (user != null) {
					quickBorrow.setUserId(user.getId());
				}
				quickBorrow.setBorrowAmount(quickBorrow.getBorrowAmount()
						.multiply(new BigDecimal(10000)));
				quickBorrow.setCreateTime(new Date());
				quickBorrow.setCreateIp(super.getIpAddr(request));
				quickBorrow.setStatus(QuickBorrow.STATUS_NEW);
				try {
					quickBorrowService.saveOrUpdate(quickBorrow);
				} catch (Exception e) {
					dto.setCode(Status.FAILD.getName());
					dto.setMessage("快速借款申请失败");
				}
			}
			JsonUtils.toObjectJson(response, dto);
		/*} else {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("验证码错误");
			JsonUtils.toObjectJson(response, dto);
		}*/
	}
	
	
	
	 /*
	  * 查询借款人
	  */
	@RequestMapping("getBorrowUserList.do")
	public void getBorrowUserList(HttpServletRequest request,
				HttpServletResponse response, Model model){
		ResponseDto dto = new ResponseDto();
		try {
			Map params = this.getParametersO(request);
			PageModel pageModel = quickBorrowService.getPagedList(params);
			List<QuickBorrow> borrowList=pageModel.getList();
			List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
			
			for (QuickBorrow quickBorrow : borrowList) {
				Map<String, Object> map=new HashMap<String, Object>();
				if(StringUtils.isBlank(quickBorrow.getUserRealname())){
					map.put("userRealname", quickBorrow.getUserName());
				}else{
					map.put("userRealname", quickBorrow.getUserRealname());
				}
				map.put("borrowAmount", StringUtil.toString(quickBorrow.getBorrowAmount()));
				map.put("status",QuickBorrow.ALL_STATUS.get(quickBorrow.getStatus()));
				dataList.add(map);
			}
			
			dto.addKeyValue("totalPage", StringUtil.toString(pageModel.getTotalPage()));
			dto.addKeyValue("dataList", dataList);
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("查询成功");
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("查询失败");
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}
	}
	 /*
	  * 得到借款人数
	  */
	@RequestMapping("getBorrowUserNumber.do")
	public void getBorrowUserNumber(HttpServletRequest request,
				HttpServletResponse response, Model model){
		ResponseDto dto = new ResponseDto();
		try {
			PageModel pageModel = quickBorrowService.getPagedList(new HashMap());
			dto.addKeyValue("userNumber", StringUtil.toString(pageModel.getTotalRecord()));
			dto.setCode(Status.SUCCESS.getName());
			dto.setMessage("查询成功");
		} catch (Exception e) {
			dto.setCode(Status.FAILD.getName());
			dto.setMessage("查询失败");
		}finally{
			JsonUtils.toObjectJson(response, dto);
		}
	}
}
