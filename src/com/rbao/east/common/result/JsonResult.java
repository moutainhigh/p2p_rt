package com.rbao.east.common.result;
/**
 *封装json参数 
 * */
public class JsonResult {

	public static String SUCCESS = "200";
	public static String ERROR ="500";
	/**
	 * 返回代码
	 */
	private String code ;
	
	/**
	 * 提示信息
	 */
	private String msg ; 
	
	public JsonResult(ServiceResult serviceResult){
		this.code = serviceResult.getCode();
		this.msg = serviceResult.getMsg();
	}
	
	public boolean isSuccessed(){
		return getCode().equals(SUCCESS);
	}
	
	public JsonResult(String code){
		this.code = code;
	}
	public JsonResult(){
	}
	
	public JsonResult(String code,String msg){
		this.code = code;
		this.msg  = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
