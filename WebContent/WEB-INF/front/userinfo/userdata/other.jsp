<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-个人资料</title>
<link type="text/css" rel="stylesheet" href="${frontPath }/css/account.css" />
<script src="${frontPath }/js/city.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left22" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
		<div class="setInfo fr">
					<c:set var="curNav" value="title9"></c:set>
           			 <%@include file="titleNav.jsp"%>
					<!-- 个人资料 -->
					<div class="tab_info" id="tab0">

						<form action="" name="form1"  id="other" method="post">
                		<input type="hidden" name="id" id="id" value="${other.id }">

							<div class="alerts">请填写你的其他信息</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">个人能力：</td>
										<td class="bl"><span class="leftspace"></span>
											<textarea style="width:300px; height:80px; margin-top:10px;" id="personalAbility" name="personalAbility" onblur="notNull('personalAbility');">${other.personalAbility }</textarea>
		                  	 				<br><span style="font-size: 12px;margin-left: 20px;">（如电脑能力、组织协调能力或其他）</span>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">个人爱好：</td>
										<td class="bl"><span class="leftspace"></span>
											<textarea style="width:300px; height:80px; margin-top:10px;" id="personalHobbies" name="personalHobbies" onblur="notNull('personalHobbies');">${other.personalHobbies }</textarea>
		                   					<br><span style="font-size: 12px;margin-left: 20px;">（突出自己的个性，工作态度或他人对自己的评价等）</span>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">其他说明：</td>
										<td class="bl"><span class="leftspace"></span>
										<textarea style="width:300px; height:80px; margin-top:10px;" id="otherState" name="otherState" onblur="notNull('otherState');">${other.otherState}</textarea>
		                    			<br><span style="font-size: 12px;margin-left: 20px;">个人能力、个人爱好、其他说明必须填一项！</span>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="subForm('other');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
				</div>
</div>
    
    </div>
  </div>	
 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
function subForm(obj) {
	var url = "";
		//不允许为空
		function notNull(ids) {
			var names = document.getElementById(ids).value;
			if (names == "") {

				return false;
			} else {

				return true;
			}
		}
		function dd() {
			if (notNull('personalAbility') == false
					&& notNull('personalHobbies') == false
					&& notNull('otherState') == false) {
				alert("必填一项!");
				return false;
			} else {
				return true;
			}
		}
		if (dd() == false) {
			return;
		}
		url = "${path}/basics/savaOther.do";
		 $.ajax({
	          dataType: 'json',
	            url:url,
	            data:$("#"+obj).serialize(),
	            type: 'POST',
	            success: function(data) {
	            	if(data.code == '100'){
	            		alertc('成功');
	            	}
	            	if(data.code == '101'){
	            		alertc('失败');
	            	}
	            },
	            error: function() {
	            		 alertc("失败！！！");
	            }
	        });     
	}
</script>
</html>
