<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-用户认证</title>
<link rel="stylesheet" href="${frontPath }/css/account.css" />
</head>
<style>
#attul li{
	width: 85px; 
	padding: 10px 0px 0px 0px;
}
#attul li span{
	margin: 15px 0px 0px 10px;
}

</style>
 <script type="text/javascript">
 KindEditor.ready(function(K) {
	 var editor = K.editor({
			allowFileManager : true,
			uploadJson:'${path}/upload/editorImgForKindEditor.do',
			formatUploadUrl:false
		});
	 K('#productURL').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					showRemote : false,
					imageUrl :K('#productURLs').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#productURLs').val(url);
						$("#div").css("display","block");
						$("#img").attr("src",url);
						editor.hideDialog();
					}
				});
			});
		});
	 
	 K('#productURL1').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					showRemote : false,
					imageUrl :K('#productURLs1').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#productURLs1').val(url);
						$("#div").css("display","block");
						$("#img").attr("src",url);
						editor.hideDialog();
					}
				});
			});
		});
 	});
 </script>
<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left20" id="curTitle">
			 <jsp:include page="/account/userLeft.do"></jsp:include>
    <div id="main" class="setInfo fr">
	        <c:set var="curNav" value="title1"></c:set>
            <%@include file="titleNav.jsp"%>
	        <div id="realname" class="model-box">
	        	<div class="form-opt" style="padding-top: 0px;">
	        		<div class="alerts" >
	        	   <span>*申请实名认证将扣除费用:${sysAuthRate }元,您当前可用余额${availableMoney }元 。</span><br/>
		            </div>
	        	    <div class="alerts"> 
			            <c:if test="${user.realnameStatus==-2 }">请填写正确的真实姓名和证件号码，以便后台进行审核。</c:if>
			            <c:if test="${user.realnameStatus==-1 }">您的实名正在认证，请等待结果...</c:if>
			            <c:if test="${user.realnameStatus==1 }">您的实名认证未通过，请重新认真填写。</c:if>
			            <c:if test="${user.realnameStatus==2 }">恭喜您，您的实名认证已经通过。</c:if>
			            </div> 
		        	<form action="requestRealnameAttestation.do" method="post" id="formRealname">
		            <table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls" style="text-align: center;">真实姓名：</td>
										<td class="bl"><span class="leftspace"></span>
										<input size="38" class="input" id="userRealname" name="userRealname" value="${user.userRealname }" <c:if test="${user.realnameStatus==2 || user.realnameStatus==-1}">readonly="readonly"</c:if> />
										</td>
									</tr>
									<tr>
										<td class="ls" style="text-align: center;">身份证号码：</td>
										<td class="bl">
											<span class="leftspace"></span>
											<input size="38" class="input"id="cardNumber" name="cardNumber" type="text" value="${user.cardNumber }" <c:if test="${user.realnameStatus==2 || user.realnameStatus==-1}">readonly="readonly"</c:if> />
											<em style="font-style: normal;"></em></td>
									</tr>
									<tr>
										<td class="ls" style="text-align: center;">身份证[正面]：</td>
										<td class="bl">
											<c:if test="${user.realnameStatus==1 || user.realnameStatus==-2}">
											<span class="leftspace"></span>
											<input value="上传" type="button" id="productURL" style="width:60px; height: 30px;" /> 
											<input readonly="readonly" style="border: 1px solid #8A949C; line-height: 30px; width:145px; height:30px;" type="text" value="" name="productURLs" id="productURLs"/>
											</c:if>
											<c:if test="${user.realnameStatus==2 || user.realnameStatus==-1}">
				 								<a href="${path }${user.cardFrontImg }" target="_blank" style="margin-left: 9px;" id="aForntImg"><img src="${path }${user.cardFrontImg }" width="150" height="150" id="img" /></a>
				 							</c:if>
										</td>
									</tr>
									<tr>
										<td class="ls" style="text-align: center;">身份证[背面]：</td>
										<td class="bl">
											<c:if test="${user.realnameStatus==1 || user.realnameStatus==-2}">
											<span class="leftspace"></span>
											<input value="上传" type="button" id="productURL1" style="width:60px; height: 30px;" /> 
											<input readonly="readonly" style="border: 1px solid #8A949C; line-height: 30px; width:145px; height:30px;" type="text" value="" name="productURLs1" id="productURLs1"/>
											</c:if>
											<c:if test="${user.realnameStatus==2 || user.realnameStatus==-1}">
								 				<a href="${path }${user.cardBackImg }" target="_blank" style="margin-left: 9px;"><img src="${path }${user.cardBackImg }" width="150" height="150" id="img" /></a>
								 			</c:if>
										</td>
									</tr>
									<c:if test="${ user.realnameStatus == 1 || user.realnameStatus == -2}">
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" id="subbtn" onclick="javascript:formRealName();" style="cursor: pointer;margin-left: 70px;margin-top: 20px;">确认提交</span>
										</td>
									</tr>
									</c:if>
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
	//实名认证
	function formRealName(){
		$("#formRealname").validate({						  
			rules: {
				userRealname: {
					required: true,
					chcharacter:true
				},
				cardNumber:{
					required:true,
					isIdCardNo:"#cardNumber"
				},
				productURLs:{
					required:true
				},
				productURLs1:{
					required:true
				}
			}
		});
		if($("#formRealname").valid()){
			$.ajax({
				type : "POST",
				url : "${path}/verify/requestRealnameAttestation.do",
				data : $('#formRealname').serialize(),
				async : false,
				success : function(data) {
					if(data.code=='100'){
            			window.location.href="${path}/verify/userAttestation.do";
            		}
					if(data.code=='104'){
	            		alertc(data.msg);
	            	}
					if(data.code=='102'){
	            		alertc(data.msg);
	            	}
				}
			});
		}
	}
</script>
</html>