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
		<title>添加提醒</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" action="sys/${PRE_PATH_EDIT }saveNotice"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="noticeId" value="${notice.id}">
				
				<div class="pageFormContent" layoutH="60">
					<p><label>提醒类型:</label>
						<c:if test="${notice.id != null}">
							<select name="noticeTypeId" disabled="disabled">
								<c:forEach var="noticeType" items="${noticeTypes}">
									<option value="${noticeType.id}">${noticeType.noticeName}</option>
								</c:forEach>
							</select>
						</c:if>
						<c:if test="${notice.id == null}">
							<select name="noticeTypeId">
									<c:forEach var="noticeType" items="${noticeTypes}">
										<option value="${noticeType.id}">${noticeType.noticeName}</option>
									</c:forEach>
							</select>
						</c:if>
					</p>
					<div class="divider"></div>
					<p><label>名称:</label><input name="noticeTitle" class="required" type="text" value="${notice.noticeTitle}" /></p>
					<div class="divider"></div>
					<c:if test="${notice.id != null}">
					<p><label>编码:</label><input name="noticeCode" readonly="readonly" class="required" type="text"  value="${notice.noticeCode}" /></p>
					</c:if>
					<c:if test="${notice.id == null}">
					<p><label>编码:</label><input name="noticeCode" class="required" type="text"  value="${notice.noticeCode}" /></p>
					</c:if>
					<div class="divider"></div>
					<p><label>状态:</label>
						<input name="noticeStatus" type="radio"  value="1" <c:if test="${notice.noticeStatus==null || notice.noticeStatus==1}"> checked="checked" </c:if>/>启用
						<input name="noticeStatus" type="radio"  value="0" <c:if test="${notice.noticeStatus==0}"> checked="checked" </c:if> />停用
					</p>
					<div class="divider"></div>
					<p><label>短消息:</label>
						<select name="message">
							<option value="1">必选已选</option>
							<option value="2">必选未选</option>
							<option value="3">可选已选</option>
							<option value="4">可选未选</option>
						</select>
					<script type="text/javascript">
					var queryType = "${notice.message}";
					if(queryType){
						$("select[name='message']").val(queryType);
					}
					</script>
					</p>
					<div class="divider"></div>
					<p><label>邮箱:</label>
						<select name="email">
							<option value="1">必选已选</option>
							<option value="2">必选未选</option>
							<option value="3">可选已选</option>
							<option value="4">可选未选</option>
						</select>
					<script type="text/javascript">
					var queryType = "${notice.email}";
					if(queryType){
						$("select[name='email']").val(queryType);
					}
					</script>
					</p>
					<div class="divider"></div>
					<p><label>手机短信:</label>
						<select name="phone">
							<option value="1">必选已选</option>
							<option value="2">必选未选</option>
							<option value="3">可选已选</option>
							<option value="4">可选未选</option>
						</select>
					<script type="text/javascript">
					var queryType = "${notice.phone}";
					if(queryType){
						$("select[name='phone']").val(queryType);
					}
					</script>
					</p>
					<div class="divider"></div>
					<p><label>排序:</label><input name="noticeSequence" class="digits" type="text"  value="${notice.noticeSequence}" /></p>
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
		</div>

	</body>
</html>
