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
			<c:set var="curNav" value="title4"></c:set>
            <%@include file="titleNav.jsp"%>
	        <div id="voide" class="model-box">
				<div class="form-opt">
			        	<form action="${path }/verify/videoAttestationApply.do" method="post" id="formVideo">
				            <div class="alerts">
				            <c:if test="${user.videoStatus==-2 }">您的视频认证还没有申请</c:if>
				            <c:if test="${user.videoStatus==-1 }">您的视频认证正在处理，请等待结果。</c:if>
				            <c:if test="${user.videoStatus==1 }">您的视频认证未通过，请重新认真填写。</c:if>
				            <c:if test="${user.videoStatus==2 }">恭喜您，您的视频认证已经通过。</c:if>
				            </div>
			                <c:if test="${user.videoStatus==1 || user.videoStatus == -2}">
				           		 <span class="wdzh-buttu" onclick="subForm('formVideo');" style="cursor: pointer;margin-top: 3px;" id="submit">申请</span>
				           		 <div class="alerts2">如果您需要视频认证，请您点击申请，等待公司安排，客服将与您联系。</div>
           					</c:if>
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
				url="${path }/verify/videoAttestationApply.do";
		 $.ajax({
	          dataType: 'json',
	            url:url,
	            data:$("#"+obj).serialize(),
	            type: 'POST',
	            success: function(data) {
	            	//视频
            		if(data.code=='200'){
            			$("#"+obj).find(".wdzh-buttu").hide();
            			$("#"+obj).find(".alerts").html("您的视频认证正在处理，请等待结果。");
            		}else if(data.code=='101'){
            			alertc("邮箱认证、手机认证、实名认证通过后才能申请视频认证。");
            		}
	            },
	            error: function() {
	            		 alertc("认证申请失败！！！");
	            }
	        });     
	}
</script>
</html>