<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>发放红包</title>

</head>
<body>
	<form method="post" action="redenvelopes/${PRE_PATH_EDIT }requestSendRedEnv"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" id="receiveUserIds" name="receiveUserIds"/> 
				<div class="pageFormContent" layoutH="55">
					<p><label>选择用户<a class="btnLook" href="user/v_showSelectedUserList"
									target="dialog"  width="800" height="396" rel="jbsxBox"><span>选择用户</span>
								</a>:</label>
						<span  id="showCheckUserName" style="color: red; float: left;"></span>
			      	</p>
					<div class="divider"></div>
					
					<p><label>红包名称</label><input type="text" size="30" name="redName" class="required"  maxlength="20" /></p>
					<div class="divider"></div>
					<!-- 有效期 -->
					
					<fieldset>
						<legend>有效期</legend>
					<p><input type="radio" name="periodType" value="1" enableItem="period_month" disableItem="period_day"/> 
					<select name="periodType_month" id="period_month">
								<option value="12">&nbsp;12&nbsp;</option>
								<option value="6">&nbsp;&nbsp;6&nbsp;</option>
								<option value="3">&nbsp;&nbsp;3&nbsp;</option>
								<option value="1">&nbsp;&nbsp;1&nbsp;</option>
							</select>个月
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="periodType" value="2"  enableItem="period_day" disableItem="period_month"/> 
							<input type="text" size="6" name="periodType_day" id="period_day" value="7" style="width: 70px;" max="365"/>天
							</p>
					</fieldset>
					<fieldset>
						<legend>红包类型</legend>
					<!-- 现金红包 -->
					<p><input type="radio" name="redType" value="1" checkedId="red1" disableItem="inputMoney,tenderMoney,tenderMoneyRed,loginDays,loginDayRed,continueDays,continueDayRed" > 现金红包（直接发放）</p>
					<div class="divider"></div>
					
					<p>&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" enableItem="selectMoney" disableItem="inputMoney" id="red1" name="redType_1" value="1"/> 
					<select name="selectMoney" id="selectMoney">
								<option value="5">&nbsp;5&nbsp;</option>
								<option value="10">&nbsp;10&nbsp;</option>
							</select>元
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="redType_1" value="2" enableItem="inputMoney" disableItem="selectMoney"/> 
							<input type="text" size="6" id="inputMoney" name="inputMoney" class="required number" style="width: 70px;" maxlength="6" />元
							</p>
					
					<div class="divider"></div>
					<!-- 满额红包 -->
					<p><input type="radio" name="redType" value="2" checkedId="redme1" disableItem="tenderMoney,tenderMoneyRed,loginDays,loginDayRed">满额红包</p>
					<div class="divider"></div>
					
					<p>&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="redme1" name="redType_2" value="1"  disableItem="tenderMoney" /> 
					满500送5元
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="redType_2" value="2" disableItem="tenderMoney,tenderMoneyRed,loginDays,loginDayRed"/> 
							满1000送10元
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							
							<c:if test="${maxMoney != 50 && maxMoney != 10 }">
								<input type="radio" name="redType_2" value="3" disableItem="tenderMoney,tenderMoneyRed,loginDays,loginDayRed"/> 
								满10000送100元
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</c:if>
							<input type="radio" name="redType_2" value="4" enableItem="tenderMoney,tenderMoneyRed"/> 
							满<input type="text" size="6" id="tenderMoney" name="tenderMoney" style="width: 40px;" maxlength="6" value="500"/>
							送
							<input type="text" size="6" id="tenderMoneyRed" name="tenderMoneyRed"  style="width: 40px;" maxlength="6" value="5"/>
							元

							</p>
							<div class="divider"></div>
							<!-- 登录红包 -->
					<p><input type="radio" name="redType" value="3" checkedId="redInput" enableItem="loginDays,loginDayRed">登录红包</p>
					<div class="divider"></div>
					
					<!-- <p>&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="redFive" name="redType_3" value="1" /> 
					登录30天送5元
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="redType_3" value="2" /> 
							连续登录20天送5元
					</p> -->
					<p>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" id="redInput" name="redType_3" value="1" enableItem="loginDays,loginDayRed"/> 
							累计登录<input type="text" size="6" id="loginDays" name="loginDays"  style="width: 40px;" maxlength="6" value="30"/>
							天,送
							<input type="text" size="6" id="loginDayRed" name="loginDayRed" style="width: 40px;" maxlength="6" value="5"/>
							元
					</p>
					<div class="divider"></div>
							<!-- 续投红包 -->
					<p><input type="radio" name="redType" value="4" checkedId="continueRed"  disableItem="inputMoney,tenderMoney,tenderMoneyRed,loginDays,loginDayRed" enableItem="continueDays,continueDayRed">续投红包</p>
					<div class="divider"></div>
					
					<!-- <p>&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="redFive" name="redType_3" value="1" /> 
					登录30天送5元
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="redType_3" value="2" /> 
							连续登录20天送5元
					</p> -->
					<p>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" id="continueRed" name="redType_4" value="1" enableItem="continueDays,continueDayRed"/> 
							<input type="text" size="6" id="continueDays" name="continueDays"  style="width: 40px;" maxlength="6" value="30"/>
							天内再投资，送
							<input type="text" size="6" id="continueDayRed" name="continueDayRed" style="width: 40px;" maxlength="6" value="5"/>
							元
					</p>
					</fieldset>
					

				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" name="submitBtn">
										确定
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
</body>
</html>
<script type="text/javascript">
<!--
$("select").css("float","none");
$("input").css("float","none");
$("input").css("float","none");
$(".btnLook").css("float","right");

var maxMoney = ${maxMoney };

$(function(){
	$("[name=submitBtn]").bind("click",function(){
		
		if($("[name='redName']").val() == ""){
			alertMsg.error("请输入红包名称");
			return;
		}
		var money = getMoney();
		if(money == "err"){
			return;
		}
		if(!money){
			alertMsg.error("请输入发放金额");
			return;
		}
		var recvUserIds = $("#receiveUserIds").val();
		if(recvUserIds == ""){
			alertMsg.error("请选择用户");
			return;
		}
		var userArr = recvUserIds.split(",").length;//已选择用户数
		
		var totalMoney = money * userArr ;
		alertMsg.confirm("已选择["+userArr+"]个用户，总发放金额["+totalMoney+"]元<br /><br />确定发放？",{
			 okCall: function(){
				  $("form").submit();
				 },
				 cancelCall : function() {}
				});
	});
	$("input[name^='redType']").bind("click",function(){
		var val = $(this).val();
		var curName = $(this).attr("name");
		var checkedId = $(this).attr("checkedId");
		
		$("input[name^='redType_']").attr("checked",false);
		$(this).attr("checked",true);
		if(checkedId){
			$("#"+checkedId).attr("checked",true);
		}
		if(curName.indexOf("redType_") == -1){ //第一级
			$("input[name^='redType_']").attr("disabled",true);
			$("input[name=redType_"+val+"]").removeAttr("disabled");
		}else{
			
		}
		dealCheckedItem(this);
	});
	$("input[name='periodType']").bind("click",function(){
		
		dealCheckedItem(this);
	});
	
	$("input[name=redType][value=1]").click();
	$("input[name=periodType][value=1]").click();
});
function dealCheckedItem(obj){
	var disableItem = $(obj).attr("disableItem");
	if(disableItem){
		var itemArr = disableItem.split(",");
		for(i=0;i<itemArr.length;i++){
			$("[id='"+itemArr[i]+"']").attr("disabled",true);
		}
	}
		
	
	var enableItem = $(obj).attr("enableItem");
	if(enableItem){
		var itemArr = enableItem.split(",");
		for(i=0;i<itemArr.length;i++){
			$("[id='"+itemArr[i]+"']").removeAttr("disabled");
		}
	}
		
}
function getSelectedUser(userId,userAccount){
	 $("input[name='receiveUserIds']").val(userId);
	$("#showCheckUserName").text(userAccount); 
}
/**
 * 获取单个用户发放金额
 */
function getMoney(){
	var checkedItem = $("input[name^='redType_']:checked");
	var checkName = checkedItem.attr("name");
	var checkVal = checkedItem.val();
	
	var checkTypeId = checkName.replace("redType_","");
	var needCheckInputMoney = false; //需要校验最大金额
	if(checkTypeId == "1"){ //
		
		if(checkVal == "1"){
			money = $("[name='selectMoney']").val();
		}else{  // 手动输入金额
			money = $("[name='inputMoney']").val();
		}
		if(money == ""){
			alertMsg.error("请输入发放金额");
			return "err";
		}
		needCheckInputMoney = true;
		
	}else if(checkTypeId == "2"){
		
		if(checkVal == "1"){
			money = "5";
		}else if(checkVal == "2"){
			money = "10";
		}else if(checkVal == "3"){
			money = "100";
		}else if(checkVal == "4"){
			money = $("[name='tenderMoneyRed']").val();
			needCheckInputMoney = true;
		}
	}else if(checkTypeId == "3"){
		money = $("[name='loginDayRed']").val();
		needCheckInputMoney = true;
	}else if(checkTypeId == "4"){
		money = $("[name='continueDayRed']").val();
		needCheckInputMoney = true;
	}
	if(isNaN(money)){
		alertMsg.error("金额输入非法，请重新输入");
		return "err";
	}
	if(needCheckInputMoney && maxMoney > 0 && parseInt(money) > maxMoney){
		alertMsg.error("您当前发放红包金额上线为"+maxMoney+"元。");
		return "err";
	}
	return parseFloat(money);
}
//-->
</script>
<style type="text/css">
/* select {
	float: none;
}	 */	
</style>