<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>升级浏览器 - ${showTitle }</title>

<style type="text/css">
.browser {
	width: 1000px;
	margin: 50px auto 0;
	min-height: 500px;
	height: auto !important;
	height: 500px;
	_overflow: visible
}

.browser .lf {
	width: 300px;
	height: 500px;
	background: url(${frontPath }/images/browser/cry.jpg) no-repeat right
		top;
	margin-right: 56px;
	float: left
}

.browser .rt {
	width: auto;
	padding-top: 40px;
	float: left
}

.browser p {
	padding-bottom: 30px;
	font-size: 14px;
	line-height: 26px
}

.browser p.bot {
	padding: 0
}

.browser p.ft30 {
	font-size: 30px;
	color: #fc8026
}

.browser img {
	border: none
}
</style>

</head>

<body>
	<jsp:include page="/top.do"></jsp:include>

	<div class="browser">
		<div class="lf"></div>
		<div class="rt">
			<p class="ft30">请升级您的浏览器~</p>
			<p class="line">您的浏览器版本过低，为了更好的体验，请升级您的浏览器~</p>
			<p class="bot">
				<a href="https://www.google.com/intl/zh-CN/chrome/browser/"
					target="_blank"> 
					<img src="${frontPath }/images/browser/chrome.jpg" width="60" height="60">
				</a> 
				<a href="http://www.firefox.com.cn/" target="_blank"> 
					<img src="${frontPath }/images/browser/firefox.jpg" width="60" height="60">
				</a> 
				<a href="http://www.opera.com/" target="_blank"> 
					<img src="${frontPath }/images/browser/opera.jpg" width="60" height="60">
				</a> 
				<a href="http://support.apple.com/kb/DL1531?viewlocale=zh_CN&locale=zh_CN"
					target="_blank"> 
					<img src="${frontPath }/images/browser/safari.jpg" width="60" height="60">
				</a> 
				<a href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-10-worldwide-languages"
					target="_blank"> 
					<img src="${frontPath }/images/browser/ie10.jpg" width="60" height="60">
				</a>
			</p>
		</div>
	</div>


	<jsp:include page="/foot.do"></jsp:include>
</body>
</html>