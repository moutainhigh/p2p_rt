package com.rbao.east.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.Constants;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Borrow;
import com.rbao.east.entity.ExtValue;
import com.rbao.east.service.BorrowService;
import com.rbao.east.service.ExtFieldService;
import com.rbao.east.utils.SpringUtils;

/**
 * 数据修改配置(例如标的基本配置)
 */
@Controller
@RequestMapping("extConfig")
public class ExtFieldController extends BaseController {
	
	private static Logger logger=LoggerFactory.getLogger(ExtFieldController.class);
	@Autowired
	private ExtFieldService extFieldService;
	@Autowired
	private BorrowService borrowService;
	
	/**
	 * 查询当前标的基本信息(基本信息value)信息    
	 * @param request  
	 * @param response  
	 * @param model  
	 * @return   
	 */
	@RequestMapping(Constants.PRE_PATH_VIEW+"getBaseDataInfo/{fldInTable}")
	public String getExtInfo(HttpServletRequest request,HttpServletResponse response,Model model,
			@PathVariable("fldInTable") String fldInTable){
		String borrowId = request.getParameter("borrowId");
		String right_id = request.getParameter("right_id");
		if(StringUtils.isNotBlank(borrowId)) {
			// 根据表borrow表的主键Id关联查询
			List list = this.extFieldService.getAllExtInfo(fldInTable,Integer.parseInt(borrowId));  
			// 查询当前操作的标基本信息
			Borrow borrowInfo = this.borrowService.getBorrowById(Integer.parseInt(borrowId));
			if(list.size()>0){ 
				model.addAttribute("extFieldList", list);  
				model.addAttribute("firstBorrowId", borrowId);  
				model.addAttribute("right_id",right_id);  
				model.addAttribute("borrowInfo", borrowInfo);
			}
		}
		return "borrow/extConfig";
	}
	
	/***
	 * 对ExtValue进行添加和修改操作 
	 * @param request 
	 * @param response 
	 * @param entity 
	 */
	@RequestMapping(Constants.PRE_PATH_EDIT+"saveExtConfig")
	public void saveExtConfig(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> paramsMap = getParameters(request);

		Iterator<Entry<String, String>> it = paramsMap.entrySet().iterator();
		String namePre = "id_";    // 查询以id_开头的
		
		Map<String, String> params = new HashMap<String, String>();
		String borrowId = paramsMap.get("borrowId");    // 对应rb_borrow表中的id 
		String fFldInTable = paramsMap.get("fFldInTable");
		params.put("borrowId", borrowId);
		params.put("fFldInTable", fFldInTable);
		
		int succ = 0;
		List<ExtValue> list = new ArrayList<ExtValue>();
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			String name = entry.getKey();
			String value = entry.getValue();
			
			if(name.startsWith(namePre)){
				String[] subName = name.split("_");
				String id = subName[1];  // 根据rb_ext_field表id 
				String valueField = subName[2]; //值保存的字段
				
				ExtValue val = new ExtValue();
				val.setFldId(Integer.valueOf(id));
				val.setTbRelatedId(Integer.valueOf(borrowId));
				val.setUptTime(new Date()); 
				
				try {
					BeanUtils.setProperty(val, valueField, value);
				} catch (Exception e) {
					e.printStackTrace();
					val.setfValue(value);
				}
				list.add(val);
			}
		}
		try{
			if(this.extFieldService.updateValue(list, params)){
				succ ++;
			}
		}
		 catch (Exception e) {
		}
		SpringUtils.renderDwzResult(response, succ > 0, succ > 0?"操作成功":"操作失败", 
				DwzResult.CALLBACK_CLOSECURRENT, paramsMap.get("right_id").toString());
	}
	
}
