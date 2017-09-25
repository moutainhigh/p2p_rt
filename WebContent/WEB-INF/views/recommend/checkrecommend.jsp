<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();

%>   

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>审核</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="recommend/${PRE_PATH_EDIT}checkRecommend"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="recommendId" value="${recommend.id}">
				
				<div class="pageFormContent" layoutH="60">
					<p><label>用户名称:</label>
					<input type="text" readonly="readonly" value="${recommend.user.userAccount}" /></p>
					<input type="hidden" name="userId" value="${recommend.userId }" />
					<div class="divider"></div>
					<p><label>奖励金额:</label><input type="text" name="recommendMoney" readonly="readonly" value="${recommend.recommendMoney}"/></p>
					<div class="divider"></div>
					<p><label>添加时间:</label><input type="text"  name="recommendAddtime" readonly="readonly" value="<fmt:formatDate value="${recommend.recommendAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></p>
					<div class="divider"></div>
					<p><label>推荐人:</label>
						<input type="text" readonly="readonly" value="${recommend.recommendUser.userAccount}" />
						<input type="hidden" name="recommendUserid" value="${recommend.recommendUserid}" />
					</p>
					<div class="divider" ></div>
					
					<!-- 状态不为1即待审核时显示 -->
					<div id="checkDiv1" style="display: none;">
					<p><label>状态:</label>
						<input type="text" readonly="readonly" value="${RECOMMEND_ALL_RECOMMEND_STATUS[recommend.recommendStatus]}"/>
					</p>
					<div class="divider"></div>
					<p><label>排序:</label><input type="text" class="digits" readonly="readonly" value="${recommend.recommendSequence}"/></p>
					<div class="divider"></div>
					<dl class="nowrap">
					    <dt>
					          审核备注：
					    </dt>
					    <dd>
					        <textarea class="textInput valid" readonly="readonly" rows="5" cols="45">${recommend.verifyRemark}</textarea>
					    </dd>
					<dl>
					<div class="divider"></div>
					</div>
					
					<!-- 状态为1即待审核时显示 -->
					<div id="checkDiv2" style="display: none;">
					<p><label>状态:</label>
						<input type="radio" name="recommendStatus" value="2" />通过
						<input type="radio" name="recommendStatus" value="3" checked="checked" />不通过
					</p>
					<div class="divider"></div>
					<p><label>排序:</label><input type="text" class="digits" name="recommendSequence" value="${recommend.recommendSequence}"/></p>
					<div class="divider"></div>
					<dl class="nowrap">
					    <dt>
					          审核备注：
					    </dt>
					    <dd>
					        <textarea class="textInput valid" name="verifyRemark" rows="5" cols="45"></textarea>
					    </dd>
					<dl>
					<div class="divider"></div>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive" id="buttonActive">
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
		</div>
	</body>
	<script type="text/javascript">
	$(document).ready(function(){
		  var recommendStatus="${recommend.recommendStatus}";
		  if(recommendStatus==1){
			  $("#checkDiv2").show();
		  }else{
			  $("#checkDiv2").hide();
			  $("#checkDiv1").show();
			  $("#buttonActive").hide();
		  }
	});
	</script>
</html>
