<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }</title>
<script type="text/javascript" src="${frontPath }/js/index.js"></script>
    <link type="text/css" rel="stylesheet" href="${frontPath }/css/style.css"/>
    <link type="text/css" rel="stylesheet" href="${frontPath }/css/Jane-css.css"/>
    
    <script type="text/javascript" src="${frontPath }/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${frontPath }/js/common.js"></script>
    <script type="text/javascript" src="${frontPath }/js/Jane-js.js"></script>
<style type="text/css">
.on{
background-color:#ee2121;
color:white;
}
body, div{padding:0;margin:0;} 

.bg{margin:20px 0px 20px 0px;margin:0px auto;}
#activity img{width:100%}
#activity a{display:block;width:450px;height:60px; cursor: pointer;}
</style>
</head>
<body>
<!--头部-->
<jsp:include page="/top.do"></jsp:include>
<!-- 活动页  -->
<!-- ie padding:340% 0% 200% 0%;display:block;   background:url(../common/front/images/newYearActivity.jpg) center no-repeat -->
<div id="activity" class="bg" style="">
<img src="../common/front/images/newYearActivity.png"/>
<div id="1"><a href="${path }/invest"></a></div>
<div id="2"><a href="${path }/invest"></a></div>
<div id="3" onclick="javascript:window.open('http://wpa.b.qq.com/cgi/wpa.php?ln=2&uin=4006114589&ref='+document.location, '_blank', 'height=544, width=644,toolbar=no,scrollbars=no,menubar=no,status=no');"><a></a></div>
<div id="4"><a href="${path}/basics/userFriend.html"></a></div>

<div id="5"><a href="http://www.xzgjf.com" target="_blank"></a></div>
<div id="6"><a href="http://www.rotongroup.com" target="_blank"></a></div>
</div>
<!--尾部-->
<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
$(document).ready(function() {
	var clientWidth = document.body.clientWidth;
	$('#activity').css('width',clientWidth)
	              .css('position','relative');
	$('#activity').children("div").css('position','absolute');
	$('#1').css({'left':'38%','top':'21%'});
	$('#2').css({'left':'38%','top':'36.5%'});
	$('#3').css({'left':'38%','top':'50.5%'});
	$('#4').css({'left':'38%','top':'94%'});
	
	$('#5').children("a").css({'width':'135px','height':'20px'});
	$('#6').children("a").css({'width':'200px','height':'20px'});
	$('#5').css({'left':'62%','top':'43%'});
	$('#6').css({'left':'57%','top':'43.5%'});
});
</script>
</html>
