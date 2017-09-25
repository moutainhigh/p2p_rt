<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EducationMessage</title>
	<script type="text/javascript" src="../../js/user/city.js"></script>
</head>
<body>
	<div class="pageContent">
			<form  method="post"  action="userDetail/o_saveFinanceMessage" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="userId"  value="${userId }"> 
				<input type="hidden" name="id"  value="${finance.id }"> 
				<div class="pageFormContent" style="height: 376px;">
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">每月无抵押贷款还款额:</label><input class="number required" onkeyup="value=value.replace(/[^0-9|.]/g,'')" maxlength="20"  type="text" name="refundMoney"  value="${finance.refundMoney }" /><label style="float:left; width:10px; padding:0 5px; line-height:21px;">元</label></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">自有房产:</label>
										 <select name="houseFreedom" id="houseFreedom" onchange="doit('houseFreedom','hourse');"  style="width:auto">
		                                  	<option value="0" <c:if test="${not empty finance.houseFreedom and 0 eq finance.houseFreedom}">selected="selected"</c:if>>购买完毕</option>
		             						<option value="1" <c:if test="${not empty finance.houseFreedom and 1 eq finance.houseFreedom}">selected="selected"</c:if>>仍在按揭</option>
		                                </select> 
					</p>
					<div class="divider"></div>
					
					<div id="hourse" style="display: none;">
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">每月房屋按揭金额:</label><input onkeyup="value=value.replace(/[^0-9|.]/g,'')" class="number required" type="text" maxlength="20" name="jiejinMoney"  value="${finance.jiejinMoney }" /><label style="float:left; width:10px; padding:0 5px; line-height:21px;">元</label></p>
					<div class="divider"></div>
					</div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">自有汽车:</label>
										<select name="carFreedom" id="carFreedom" onchange="doit('carFreedom','car');" class="input08" style="width:auto">
		                                    <option value="0" <c:if test="${not empty finance.carFreedom and 0 eq finance.carFreedom}">selected="selected"</c:if>>购买完毕</option>
		             						<option value="1" <c:if test="${not empty finance.carFreedom and 1 eq finance.carFreedom}">selected="selected"</c:if>>仍在按揭</option>
		                                </select>
					 </p>
					<div class="divider"></div>
					
					<div id="car" style="display: none;">
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">每月汽车按揭金额:</label><input onkeyup="value=value.replace(/[^0-9|.]/g,'')" class="number required" maxlength="20" type="text" name="carJiejin" value="${finance.carJiejin }" /><label style="float:left; width:10px; padding:0 5px; line-height:21px;">月</label></p>
					<div class="divider"></div>
					</div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">每月信用卡还款金额:</label><input onkeyup="value=value.replace(/[^0-9|.]/g,'')" class="number required" maxlength="20"  type="text" name="refundXinyong" value="${finance.refundXinyong }" /><label style="float:left; width:10px; padding:0 5px; line-height:21px;">元</label></p>
					<div class="divider"></div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										保存
									</button>
								</div>
							</div>
						</li>
						<!-- <li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li> -->
					</ul>
				</div>
			</form>
		</div>
</body>
<script type="text/javascript">
function doit(selectid,divid){
	var id=$("#"+selectid).val();
	if(id==0){
		$("#"+divid).hide();
	}else if(id==1){
		$("#"+divid).show();
	}
}
/* $(document).ready(function(){
	if($("#houseFreedom").val()==""||$("#houseFreedom").val()==null){
		$("#hourse").hide();
	}else{
		$("#hourse").show();
	}
	if($("#carFreedom").val()==""||$("#carFreedom").val()==null){
		$("#car").hide();
	}else{
		$("#car").show();
	}
});
	 */
</script>
</html>

