<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }账务交易验证</title>
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
            <h2>账务交易验证</h2>
            <b class="line"></b></div>

        <div class="verify-sms">
            <div class="p">账户资金提现、赎回时，为确保安全，需进行验证</div>
            <dl>
                <dt class="light-green">
                    <a href="javascript:;" class="icons proper-green ToolTips ToolTipCol" data-text="已启用">帮助</a> 支付密码 
                    <span class="s">已启用</span>
                </dt>
                <dd>账户资金提现、赎回时，需验证支付密码，此项不可撤销。 
                </dd>
            </dl>
            <dl>
                
                <dt>
                    <a href="javascript:;" class="icons yinfo-org ToolTips ToolTipCol" data-text="尚未启用">帮助</a> 短信验证 
                    <span class="s">未启用</span>
                </dt>
                <dd>启用此功能后，账户资金提现、赎回时，验证支付密码后还需验证手机短信动态码。双重验证可有效提升账户资金安全。 
                    <a href="javascript:verifyDia();" class="btn-text">启用</a></dd>
                
                
                
            </dl>
        </div>
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
	$("#accountTradeVerify").addClass("current");
}
</script>
</html>