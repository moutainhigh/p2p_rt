<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-用户认证</title>
<link rel="stylesheet" href="${frontPath }/css/account.css" />
<link rel="stylesheet" href="${frontPath }/css/vip.css" />
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left20" id="curTitle">
			 <jsp:include page="/account/userLeft.do"></jsp:include>
    <div id="main" class="setInfo fr">
	        <c:set var="curNav" value="title2"></c:set>
            <%@include file="titleNav.jsp"%>
	        <div id="email" class="model-box">
					<div class="form-opt">
			        	<form action="requestAddEmail.do" method="post" id="formEmail">
				            <div class="alerts">
				            <c:if test="${user.emailStatus==-2 }">绑定邮箱有利于我们及时通知您在${CACHE_SYS_CONFIG.sysWebsitename}的交易情况以及最新动态。</c:if>
				            <c:if test="${user.emailStatus==-1 }">您的邮箱正在认证，请等待结果。</c:if>
				            <c:if test="${user.emailStatus==1 }">您的邮箱认证未通过，请重新认真填写。</c:if>
				            <c:if test="${user.emailStatus==2 }">恭喜您，您的邮箱认证已经通过。</c:if>
				            </div>
				           <%--  <ul class="items">
				                <li class="txt"><em>*</em>邮箱：</li>
				                <li>
				                    <input type="text" id="userEmail" name="userEmail" class="input" value="${user.userEmail }" <c:if test="${user.userEmail!=null && '' ne user.userEmail }">readonly="readonly"</c:if>/>
				                    <span class="tip error" id="mailTip" style="display:none;"><i class="icons reg-error"></i></span>
				                </li>
				            </ul>
				            	<c:if test="${user.emailStatus==-2||user.emailStatus==1 }">
					           	 	<div class="btn-submit"> <a style="margin-left: 150px;" href="javascript:subForm('formEmail');" class="btn btnSize_1 btn_orange" id="submit">提交</a> </div>
				            	</c:if> --%>
				            	
				            <table class="userPtb">
			    		<tbody>
			    			<tr>
			       	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的邮箱：
			       	 				<input type="text" id="userEmail" name="userEmail" class="input" value="${user.userEmail }" <c:if test="${user.userEmail!=null && '' ne user.userEmail }">readonly="readonly"</c:if>/>
			       	 			</td>
			       	 			<td></td>
			   		 		</tr>
			   		 		<tr><td><br/></td></tr>
			   		 		<tr>
			   		 			<td>
			   		 				<c:if test="${user.emailStatus==-2||user.emailStatus==1 }">
			   		 				<span class="wdzh-buttu" id="subbtn" onclick="javascript:subForm('formEmail');" style="cursor: pointer;margin-left: 70px;margin-top: 20px;">确认提交</span>
			   		 				
			   		 				</c:if>
			   		 			</td>
			   		 		</tr>
						</tbody>
						</table>
			       	 	</form>
        			</div>
			</div>
	   
    </div>
    </div>
    </div>
    </div>
    <!--/main--> 
 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
	function subForm(obj){
		url="${path}/verify/requestAddEmail.do";
		 $.ajax({
	          dataType: 'json',
	            url:url,
	            data:$("#"+obj).serialize(),
	            type: 'POST',
	            success: function(data) {
	            	
            		//视频
            		if(data.code=='102'){
            			$("#"+obj).find(".wdzh-buttu").hide();
            			$("#"+obj).find(".alerts").html("您的邮箱认证正在处理，请等待结果。");
            		}
	            },
	            error: function() {
	            		 alertc("认证申请失败！！！");
	            }
	        });     
	}
</script>
</html>