package com.rbao.east.appapi.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;


/**
 * 定义返回json数据实体
 * */
public class ResponseDto {
	
	/**
	 * 集合key
	 */
	private final String LIST_KEY = "list";
	
	/**
	 * 分页key
	 */
	private final String PAGE_KEY = "page";
	
	/**
	 * 返回类型
	 */
	private Map<String, Object> params = new HashMap<String, Object>();

	/**
	 * 返回CODE
	 */
	@Expose
	private String code;
	
	/**
	 * 返回提示信息
	 */
	@Expose
	private String message = "成功";
	
	/**
	 * 返回结果信息
	 */
	@Expose
	private Object result;
	
	/**
	 * 添加list集合
	 * @param list
	 */
	public void addLists(List<Object> list) {
		params.put(LIST_KEY, list);
		
		this.setResult(params);
	}
	
	/**
	 * 添加page信息
	 * @param currentPage
	 * @param pageSize
	 * @param totalPage
	 */
	public void addPage(String currentPage, String pageSize, String totalPage) {
		Map<String, String> page = new HashMap<String, String>();
		page.put("currentPage", currentPage);
		page.put("pageSize", pageSize);
		page.put("totalPage", totalPage);
		
		params.put(PAGE_KEY, page);
		
		this.setResult(params);
	}
	
	/**
	 * 添加其他信息
	 */
	public void addKeyValue(String key, Object value) {
		params.put(key, value);
		
		this.setResult(params);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	public static void main(String[] args) {
//		DesEncrypt desEncrpt = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
//		System.out.println(desEncrpt.encrypt("123456"));
//		ResponseDto dto = new ResponseDto();
//		dto.addLists(new ArrayList<Object>());
//		dto.addPage("0", "0", "0");
//		Timestamp timestamp = null;
//		dto.addKeyValue("line", timestamp);
//		System.out.println(JsonUtils.beanWithGsonToJson(dto));
	}
}
