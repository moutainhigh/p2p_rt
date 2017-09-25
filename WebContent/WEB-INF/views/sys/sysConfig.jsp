<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<title>Insert title here</title>
<script type="text/javascript">
function isTel(object)
{
//国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"

 var s =document.getElementById(object).value; 
 var pattern =/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,4}))?$/;
     if(s!="")
     {
         if(!pattern.exec(s))
         {
          alert('请输入正确的传真格式:传真格式号码格式为国家代码(2到3位)-区号(2到3位)-传真格式号码(7到8位)-分机号(3到4位)"');
          object.value="";
          object.focus();
         }
     }
}
</script>
</head>
<body>
	<form method="post" action="sys/${PRE_PATH_EDIT }saveSysConfig"  onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="id" value="${SysConfig.id}"/>
				<div class="pageFormContent" layoutH="55">
					<p><label>网站名称</label><input type="text" alt="请输入网站名称"  size="30" maxlength="100" name="sysWebsitename"  value="${SysConfig.sysWebsitename }" /></p>
					<div class="divider"></div>
					
					<p ><label>网站域名</label><input type="text" alt="请输入网站域名" size="30" maxlength="120" name="sysWebsitedomain" value="${SysConfig.sysWebsitedomain }" /></p>
					<div class="divider"></div>
					
					<p><label>网站备案号</label><input type="text"  alt="请输入网站备案号" size="30" maxlength="200" name="sysWebsiteicp" value="${SysConfig.sysWebsiteicp }"/></p>
					<div class="divider"></div>
					
					<p><label>网站关键词</label><input type="text" alt="请输入网站关键词" size="30"  maxlength="1000" name="sysWebsitekeyword" value="${SysConfig.sysWebsitekeyword }"/></p>
					<div class="divider"></div>
					
					<p ><label>网站描述</label><input type="text" alt="请输入网站描述" size="30"  maxlength="2000" name="sysWebsitedescribe" value="${SysConfig.sysWebsitedescribe }" /></p>
					<div class="divider"></div>
					 
					<p><label>网站传真</label><input type="text" alt="请输入网站传真" size="30" id="fax" name="sysWebsitefax" value="${SysConfig.sysWebsitefax }" maxlength="50" onblur="isTel('fax')" /></p>
					<div class="divider"></div>
					
					<p><label>联系电话</label><input type="text" alt="请输入联系电话" size="30"  maxlength="150" id="fax" name="sysWebsitetel" value="${SysConfig.sysWebsitetel }" onblur="isTel('fax')" /></p>
					<div class="divider"></div>
					
					<p><label>联系地址</label><input type="text" alt="请输入联系地址" size="30" maxlength="100" name="sysWebsiteaddress" value="${SysConfig.sysWebsiteaddress }" /></p>
					<div class="divider"></div>
				
					<p><label>网站署名</label><input type="text" alt="请输入网站署名" size="30" maxlength="2000" name="sysWebsitesignature" value="${SysConfig.sysWebsitesignature }" /></p>
					<div class="divider"></div>
					
					<p><label>自动投标状态</label><select name="autoTenderStatus">
						<option value="1">开启</option>
						<option value="2" <c:if test="${SysConfig.autoTenderStatus eq '2' }"> selected="true" </c:if> >关闭</option>
					</select> &nbsp;&nbsp;&nbsp;&nbsp;当前状态：<font color="#F7772D;">
					<c:if test="${SysConfig.autoTenderStatus eq '1' }"> 已开启 </c:if>
					<c:if test="${SysConfig.autoTenderStatus eq '2' }"> 已关闭 </c:if>
					</font> 
					
					
					</p>
					<div class="divider"></div>
					
					<p style="height: 100%;width: 100%;"><label>注册协议</label>
						<textarea cols="100" rows="17" class="editor" upImgUrl="upload/editorImg" name="sysRegisterProtocol" >${SysConfig.sysRegisterProtocol }</textarea></p>
					<div class="divider"></div>
					
					
					
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
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
</body>
</html>