<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../../taglibs.jsp"%>
	<title>${showTitle }-银行卡</title>
	<style>
		.cardfileDiv{
			width: 272px;
		    margin-left: 94px;
		    height: 167px;
		    margin-top: 25px;
	        border: 1px solid #D7DADE;
		}
		.cardfileDiv div{
			width: 260px;
		    margin: 6px;
		    height: 155px;
			background: url(${frontPath}/images/tm_bg.jpg);
		}
		a.fileA {
			display: inline-block;
		    width: 205px;
		    height: 59px;
		    left: 30px;
    		top: 52px;
		    position: relative;
		    overflow: hidden;
		    border: 1px solid #D7DADE;
		    border-radius: 11px;
		    background: url(${frontPath}/images/choice.png) no-repeat;
		}
		
		a.fileA:hover {
			background: url(${frontPath}/images/choice.png) no-repeat;
		}
		
		input.cardPic {
			position: absolute;
			right: 0;
			top: 0;
			font-size: 100px;
			opacity: 0;
			filter: alpha(opacity = 0);
		}
	</style>
</head>
<body>
	<script type="text/javascript" src="<%=path%>/common/front/js/provincecity.js"></script>
	<jsp:include page="/top.do"></jsp:include>
	<div class="J-xzg-myaccount">
		<div class="J-xzg-ma-con nav-con clearfix">
			<div class="J-ma-nav jboxsize">
				<input type="hidden" value="left8" id="curTitle">
				<jsp:include page="/account/userLeft.do"></jsp:include>
			</div>
			<div class="J-ma-conR yh_con" style="padding: 30px 0;">
				<div class="title">
					<a href="javascript:void(0);" class="tap">解绑申诉</a>
				</div>
				<div class="yh_txt">
					<form method="post" id="relaseCardForm" enctype="multipart/form-data">
						<div class="input">
							<i>申诉理由:</i>
							<input type="text" name="relaseReason" id="relaseReason" />
						</div>
						<div class="input">
							<i>上传身份证:</i>
							<div>
								<img id="imgPre" style="display: block;width: 360px;height: 270px;" src=""/>
							</div>
							<div class="cardfileDiv">
								<div>
									<a href="#" class="fileA">
										<input type="file" name="cardPic" id="cardPic" class="cardPic" onchange="preImg(this.id,'imgPre');"/>
									</a>
								</div>
							</div>
						</div>
						<a href="javascript:relaseCard();" class="btn">提交</a>
					</form>
					<div class="tip">
						<h5>温馨提示：</h5>
						<ul>
							<li>- 解绑银行卡，需提交本人身份证正面头部照一张。<br/>&nbsp;&nbsp;持身份证正面照示例如下:</li>
							<img src="${frontPath}/images/jbhelp.jpg" style="width:360px;height:270px"/>
						</ul>
					</div>
					<!-- tip -->
				</div>
				<!-- yh_txt -->
			</div>
			<!-- J-ma-conR -->
		</div>
	</div>
	<!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	function relaseCard(){
		$("#relaseCardForm").attr("action",'${path}/account/relaseCard.html');
		$("#relaseCardForm").submit();
	}
	function preImg(sourceId, targetId) {
		var url = getFileUrl(sourceId);
		var imgPre = document.getElementById(targetId);
		imgPre.src = url;
	} 
	
	function getFileUrl(sourceId) {
		var url;
		if (navigator.userAgent.indexOf("MSIE") >= 1) { // IE
			url = document.getElementById(sourceId).value;
		} else if (navigator.userAgent.indexOf("Firefox") > 0) { // Firefox
			url = window.URL
					.createObjectURL(document.getElementById(sourceId).files
							.item(0));
		} else if (navigator.userAgent.indexOf("Chrome") > 0) { // Chrome
			url = window.URL
					.createObjectURL(document.getElementById(sourceId).files
							.item(0));
		}
		return url;
	}
</script>
</html>