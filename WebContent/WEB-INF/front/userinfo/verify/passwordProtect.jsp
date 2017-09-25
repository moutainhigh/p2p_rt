<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }启用密保问题</title>
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/invest.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/account.css" />
<link type="text/css" rel="stylesheet" href="${cssJsPath }/css/base.css" />
<link type="text/css" rel="stylesheet" href="${cssJsPath }/css/user.css" />
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
<div id="wrapper"> 
    <!--sidebar-->
    <jsp:include page="/account/attestationLeft.do"></jsp:include>
    <!--/sidebar--> 
    <!--main-->
    <div id="main">
    	       <div class="title-items backtohis"><a href="javascript:history.back();" class="toback r3"><i class="icons arrow-gray-dotl"></i>返回</a>
            <h2>启用密保问题</h2>
            <b class="line"></b></div>
        <form action="/secure/setSecurityQuestion2.session.action" method="POST" id="setSQForm">
        <div class="form-opt">
            <ul class="items select-os">
                <li class="txt"><em>*</em>选择密保问题：</li>
                <li>
                    <select class="select" id="secretQuestion" name="securityQuestion">
                        <option>爷爷的名字</option>
                        <option>母亲的姓氏</option>
                        <option>出生的城市</option>
                        <option>你的小学</option>
                        <option>你的中学</option>
                        <option>最熟悉的学校宿舍室友的名字</option>
                        <option>小学班主任的名字</option>
                        <option>对你影响最大的人的名字</option>
                    </select>
                    
                </li>
            </ul>
            <ul class="items">
                <li class="txt"><em>*</em>回答：</li>
                <li>
                    <input type="text" id="answer" name="answer" class="input">
                    <span class="tip error" id="answerTip" style="display:none;"><i class="icons reg-error"></i></span> </li>
            </ul>
            <ul class="items">
                <li class="txt"><em>*</em>支付密码：</li>
                <li>
                    <input type="password" id="payPassword" name="payPassword" class="input">
                    <span class="tip error" id="payPasswordTip" style="display:none;"><i class="icons reg-error"></i></span> </li>
            </ul>
            <div class="btn-submit"> <a href="javascript:;" class="gbtn" id="submit">确认</a> </div>
        </div>
        </form>
    </div>
    <!--/main--> 
</div>
<!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>

</body>

<script type="text/javascript">
$(function(){
	changeCurrent();
})

function changeCurrent(){
	var current=$("#menu").find(".current");
	$(current).removeClass("current");
	$("#passwordProtect").addClass("current");
}
</script>
</html>