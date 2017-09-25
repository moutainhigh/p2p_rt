<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
String basePath = path+"/common/views";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>添加支付方式</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" action="sys/${PRE_PATH_EDIT }savePaymentConfig"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="paymentConfigId" value="${paymentConfig.id}">
				
				<div class="pageFormContent" layoutH="60">
					<p><label>类型:</label>
						<c:if test="${paymentConfig.id != null}">
						<input type="radio" value="1" name="paymentType" id="paymentType2" disabled="disabled"
							<c:if test="${paymentConfig.paymentType==null || paymentConfig.paymentType==1}">
							 checked="checked"
							</c:if> 
							onclick="changePaymentType(1);"
						/>线上支付
						<input type="radio" value="2" name="paymentType" disabled="disabled"
							<c:if test="${paymentConfig.paymentType==2}">
							 checked="checked"
							</c:if>
							 onclick="changePaymentType(2);" 
						 />线下支付
						 </c:if>
						 <c:if test="${paymentConfig.id == null}">
						<input type="radio" value="1" name="paymentType" id="paymentType1"
							<c:if test="${paymentConfig.paymentType==null || paymentConfig.paymentType==1}">
							 checked="checked"
							</c:if> 
							onclick="changePaymentType(1);"
						/>线上支付
						<input type="radio" value="2" name="paymentType"
							<c:if test="${paymentConfig.paymentType==2}">
							 checked="checked"
							</c:if>
							 onclick="changePaymentType(2);" 
						 />线下支付
						 </c:if>
					</p>
					<div class="divider"></div>
					<!--<input type="hidden" name="paymentName" value="">
					--><p><label>名称:</label>
					
						<input name="paymentName" class="required"  id="paymentName" 
					 type="text" value="${paymentConfig.paymentName }"/>
						
					</p>
					<%--<p id="onlineName"><label>名称:</label>
					   <input name="paymentNameSelect" id="paymentNameSelect" type="text" value="${paymentConfig.paymentName}" /> 
					 <select name="paymentNameSelect" id="paymentNameSelect" onchange="forChangeName();" 
					<c:if test="${paymentConfig.paymentType==1}"> disabled="disabled" </c:if>>
							<option value="国付宝" <c:if test="${'国付宝' eq paymentConfig.paymentName}"> selected="selected" </c:if> >国付宝</option>
							<option value="网银在线支付"<c:if test="${'网银在线支付' eq paymentConfig.paymentName}"> selected="selected" </c:if> >网银在线支付</option>
							<option value="双乾支付" <c:if test="${'双乾支付' eq paymentConfig.paymentName }"> selected="selected" </c:if> >双乾支付</option>
							<option value="宝付" <c:if test="${'宝付' eq paymentConfig.paymentName}"> selected="selected" </c:if> >宝付</option>
						</select> 
					</p> --%>
					<div class="divider"></div>
					
					<p><label>编码:</label>
						<!-- <input name="paymentCode" id="paymentCode" type="text"  value=""/> -->
						 <input name="paymentCode" id="paymentCode" type="text" class="required" value=""
							<%-- <c:if test="${paymentConfig.paymentType==1}"> 
							 readonly="readonly" 
							</c:if> --%>
						 /> 
					</p>
					<div class="divider"></div>
					
					<p><label>支付地址:</label>
						
						<input name="gatewaUrl" id="gatewaUrl" type="text"  value="${paymentConfig.gatewaUrl}" />
					</p>
					<div class="divider"></div>
					
					<div id="online" style="<c:if test="${paymentConfig.paymentType==0}">display: none;</c:if>
					<c:if test="${paymentConfig.paymentType==null || paymentConfig.paymentType==1}">display: block;</c:if>">
						<p><label>客户号:</label><input name="clientId" id="clientId" type="text" value="${paymentConfig.clientId}"/></p>
						<div class="divider"></div>
						<p><label>秘钥:</label><input name="encryptKey" id="encryptKey" type="text"  value="${paymentConfig.encryptKey}"/></p>
						<div class="divider"></div>
						<div id="accountDiv" style="<c:if test="${'国付宝' eq paymentConfig.paymentName || '宝付' eq paymentConfig.paymentName}">display: block;</c:if>
												<c:if test="${'网银在线支付' eq paymentConfig.paymentName || '双乾支付' eq paymentConfig.paymentName}">display: none;</c:if>">
						<p><label>账号:</label><input name="account" id="account" type="text"  value="${paymentConfig.account}"/></p>
						<div class="divider"></div>
						</div>
					</div>
					<p><label>状态:</label>
						<c:if test="${paymentConfig.paymentStatus==null}">
							<input type="radio" value="1" name="paymentStatus"  checked="checked"/>启用
							<input type="radio" value="0" name="paymentStatus" />停用
						</c:if>
						<c:if test="${paymentConfig.paymentStatus==0}">
							<input type="radio" value="1" name="paymentStatus" />启用
							<input type="radio" value="0" name="paymentStatus" checked="checked" />${paymentConfig.viewPaymentStatus }
						</c:if>
						<c:if test="${paymentConfig.paymentStatus==1}">
							<input type="radio" value="1" name="paymentStatus"  checked="checked"/>${paymentConfig.viewPaymentStatus }
							<input type="radio" value="0" name="paymentStatus" />停用
						</c:if>
					</p>
					<div class="divider"></div>
					
					<p><label>费用类型:</label>
						<c:if test="${paymentConfig.paymentFeeType==null}">
							<input type="radio" value="1" name="paymentFeeType"  checked="checked"/>比例收费
							<input type="radio" value="2" name="paymentFeeType" />固定费用
							<input type="radio" value="0" name="paymentFeeType" />不收费
						</c:if>
						<c:if test="${paymentConfig.paymentFeeType==0}">
							<input type="radio" value="1" name="paymentFeeType" />比例收费
							<input type="radio" value="2" name="paymentFeeType" />固定费用
							<input type="radio" value="0" name="paymentFeeType" checked="checked" />${paymentConfig.viewFeeType}
						</c:if>
						<c:if test="${paymentConfig.paymentFeeType==1}">
							<input type="radio" value="1" name="paymentFeeType"  checked="checked"/>${paymentConfig.viewFeeType}
							<input type="radio" value="2" name="paymentFeeType" />固定费用
							<input type="radio" value="0" name="paymentFeeType" />不收费
						</c:if>
						<c:if test="${paymentConfig.paymentFeeType==2}">
							<input type="radio" value="1" name="paymentFeeType"/>比例收费
							<input type="radio" value="2" name="paymentFeeType" checked="checked" />${paymentConfig.viewFeeType}
							<input type="radio" value="0" name="paymentFeeType" />不收费
						</c:if>
					</p>
					<div class="divider"></div>
					<p><label>最大金额:</label><input name="paymentMaxMoney" class="number"  type="text" size="30"  value="${paymentConfig.paymentMaxMoney }" /></p>
					<div class="divider"></div>
					<p><label>最大费率:</label><input name="paymentMaxFee" class="number" type="text"  size="30"  value="${paymentConfig.paymentMaxFee }" /></p>
					<div class="divider"></div>
					<p><label>排序:</label><input name="paymentSequence" class="digits" type="text" size="30" value="${paymentConfig.paymentSequence }" /></p>
					<div class="divider"></div>
					<p><label>上传图片:</label>
						<input type="text" readonly="readonly" id="url" name="url" value="${paymentConfig.paymentIco }" />
						<input type="file" name="uploadify" id="uploadify" />
					</p>
					<div class="divider"></div>
					<dl class="nowrap">
				    <dt>
				          描述：
				    </dt>
				    <dd>
				        <textarea class="textInput valid" name="paymentDescription" rows="5" cols="45">${paymentConfig.paymentDescription }</textarea>
				    </dd>
					<dl>
					<div class="divider"></div>
					
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="submitFrm()">
										保存
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>

	</body>
<script type="text/javascript">
var indx = 0;
var imgSuffix = ".jpg,";
$(document).ready(function() { 
    $("#uploadify").uploadify({  
        'uploader'       : '<%=basePath %>/uploadify/scripts/uploadify.swf',  
        'script'         : '<%=path %>${ADMIN_URL }/upload/uploadFiles;jsessionid=<%=session.getId()%>',  
        'cancelImg'      : '<%=basePath %>/uploadify/cancel.png',  
       	'buttonImg'      : '<%=basePath %>/uploadify/buttonImg.png',
        'folder'         : '/jxdBlog/photos',  
        'queueID'        : 'fileQueue',  
        'auto'           : true,  
        'multi'          : true,  
        'wmode'          : 'transparent',  
        'simUploadLimit' : 999,  
        'fileExt'        : '*',  
        'fileDesc'       : '*',  
        'onComplete'  :function(event,queueId,fileObj,response,data){  
        	indx ++;
        	var retJson = eval(response)[0];
            /* $('#result').html('还剩'+data.fileCount +'个附件没上传'); */
            
            var img = "";
            if(imgSuffix.indexOf(retJson.suffix) != -1){
            	img = "<img style='width:100px;height:100px;' src=\"<%=path %>"+retJson.filepath+"\" />";
            }
            
            $("#url").val(retJson.filepath);
           /*  $('#fileQueue').append("<li id='li_"+indx+"'>&nbsp;附件名：<input name='attachRealTitle' value='"+retJson.filename+"' ></input>"
            +"&nbsp;&nbsp;重命名：<input name='attachTitle' value='"+retJson.filename+"'></input>"
            +"<input name='attachPath' type='hidden' value='"+retJson.filepath+"' ></input>"
            
            +"<input name='attachFileType'  type='hidden' value='"+retJson.suffix+"'></input>"
            
            + "&nbsp;&nbsp;&nbsp;&nbsp;排序：<input name='attachSequence' value='"+indx+"'></input>"
             +"&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('li_"+indx+"')\" >删除</a></li>"
            
            +"<div name='sty'>&nbsp;</div>"); 
            
            $('input[name="attachRealTitle"]').css("width","200px");
            $('input[name="attachRealTitle"]').css("border","none");
            $('input[name="attachTitle"]').css("width","200px");
            $('input[name="attachSequence"]').css("width","20px"); */
            /* $('div[name="sty"]').css("height","10px");  */
            
        }  
    }); 
    
});  




var inputName = "${paymentConfig.paymentName }";
$(document).ready(function(){
	
		changePaymentType();
		forChangeName();
		if("${paymentConfig.paymentCode }"){
			$("#paymentCode").val("${paymentConfig.paymentCode }");
		}
		
});
function submitFrm(){
	$("#frm").submit();
}

	function changePaymentType(val){
		val = $("input[name='paymentType']:checked").val();
		if(val==1){ //线上
			inputName = $("#paymentNameInput").val();
			$("#online").show();
			if($("#paymentNameSelect").val()=="国付宝" || $("#paymentName").val()=="宝付"){
				$("#accountDiv").show();
			}
			if($("#paymentNameSelect").val()=="网银在线支付" || $("#paymentName").val()=="双乾支付"){
				$("#accountDiv").hide();
			}
			//$("input[name='paymentCode']").attr("readonly","readonly");
			forChangeName();
		}else{
			$("#online").hide();
			//$("input[name='paymentCode']").removeAttr("readonly");
			$("#paymentCode").val("");
	//		$("#paymentNameInput").val("");
		}
	}
	
	function forChangeName(){
		if($("input[name='paymentType']:checked").val()=="1"){
			
			var selected = $("#paymentNameSelect").val();
	//		alert(selected);
			if(selected=="国付宝"){
				$("#paymentCode").val("guofubao");
				$("#accountDiv").show();
			}
			if(selected=="网银在线支付"){
				$("#paymentCode").val("chinabank");
				$("#accountDiv").hide();
			}
			if(selected=="双乾支付"){
				$("#paymentCode").val("shuangqian");

				$("#accountDiv").hide();
			}
			if(selected=="宝付"){
				$("#paymentCode").val("baofoo");
				$("#accountDiv").show();
			}
		}		
	}
</script>
</html>
