<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-用户认证</title>
<link rel="stylesheet" href="${frontPath }/css/account.css" />
<link rel="stylesheet" href="${frontPath }/css/vip.css" />
</head>
 <script type="text/javascript">
 KindEditor.ready(function(K) {
	 var editor = K.editor({
			allowFileManager : true,
			uploadJson:'${path}/upload/editorImgForKindEditor.do',
			formatUploadUrl:false
		});
	 //资料上传
	 K('#attestationImgId').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					showRemote : false,
					imageUrl :K('#attestationImg').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#attestationImg').val(url);
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
			<c:set var="curNav" value="title6"></c:set>
            <%@include file="titleNav.jsp"%>
	        
	        <div id="uploading" class="model-box">
	        	<div class="form-opt">
			        	<form action="" id="formUploading" method="post">
							<div class="alerts">
								<font color="#FF0000">*</font> 必须是本人的真实、有效资料
							</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">资料上传：</td>
										<td id="imgUpload"><span class="leftspace"></span>
											<input value="上传" type="button" id="attestationImgId" style="width:60px; height: 30px;" class="w80 l-button"/> 
				                			<input style="border: 1px solid #8A949C; line-height: 30px; width:190px; height:30px;" type="text" value="" name="attestationImg" id="attestationImg"/>
										</td>
									</tr>
									<tr>
										<td class="ls">上传类型：</td>
										<td><span class="leftspace"></span>
											<select id="attestationTypeid" name="attestationTypeid" style="width: 435px;">
						                	<c:forEach var="type" items="${attType }">
						                		<c:if test="${not empty type }">
							                		<option value="${type.id }" style="width: 405px;">${type.attestationName }</option>
						                		</c:if>
						                		<c:if test="${empty type }">
						                			<option value="" style="width: 405px;">-没有要上传的证件-</option>
						                		</c:if>
						                	</c:forEach>
						                	</select>
										</td>
									</tr>
									<tr>
										<td class="ls">备注说明：</td>
										<td><span class="leftspace"></span>
											<textarea rows="5" cols="50"  id="attestationRemarks" name="attestationRemarks"></textarea>
				                    		<span class="tip error" id="mailTip" style="display:none;"><i class="icons reg-error"></i></span>
										</td>
									</tr>
									<tr id="yzm">
										<td class="ls">验&nbsp;证&nbsp;&nbsp;码：</td>
										<td><span class="leftspace"></span>
											<input maxlength="4" style="width:100px; margin-bottom: 10px;margin-top: 10px;float: left;margin-left: 10px;" name="captcha" type="text" id="captcha" class="input" />&nbsp;
											<span class="txt"><img style="margin-top: 15px;" id="captchaImg" src="<%=path %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" /></span>
										</td>
									</tr>

									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="formUpload();" style="cursor: pointer;margin-top: 20px;margin-left: 80px;" id="submit">确认提交</span>
										</td>
									</tr>
								</tbody>
							</table>
						</form>
						<div class="alerts2">* 温馨提示：我们将严格对用户的所有资料进行保密</div>
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
	//资料上传
	function formUpload(){
		
		
		$("#formUploading").validate({						  
			rules: {
				attestationImg: {
					required: true
				},
				captcha:{
					required:true
				},
				attestationTypeid:{
					required:true
				}
			}
		});
		
		if($("#captcha").val()==""||$("#captcha").val()==null){
			
			alertc("请填写验证码");
			$("#captchaImg").click();
			
		}else if($("#captcha").val()==""||$("#captcha").val()==null){
			$("#captchaImg").click();
		}
		
		
		if($("#formUploading").valid()){
			$.ajax({
	          	dataType: 'json',
	            url:"${path}/verify/toAttestationApply.do",
	            data:$("#formUploading").serialize(),
	            type: 'POST',
	            
	            success: function(data) {
	           		//资料上传
	           		if(data.code=='200'){
	           			document.getElementById("imgUpload").style.height="200px";
	           			$("#imgUpload").html('<img alt="" src="'+"${path}"+$("#attestationImg").val()+'" width="200" height="200">');
	           			$("#formUploading").find(".wdzh-buttu").hide();
	           			$("#yzm").hide();
	           			$("#formUploading").find(".alerts").html("您的资料上传正在处理，请等待结果。");
	           		}
	           		if(data.code=='201'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='202'){
	           			alertc(data.msg);
	           		}
	           		if(data.code=='203'){
	           			alertc(data.msg);
	           		}
	            },
	            error: function() {
	            		 alertc("认证申请失败！！！");
	            }
	        }); 
		}
	}
</script>
</html>