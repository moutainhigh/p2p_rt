<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="pageContent">
			<form method="post" action="dis/question/saveOrUpdate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id"  value="${question.id}" />
				
				<div class="pageFormContent" layoutH="56">
				
					<p><label>提问人：</label>
						<input name="userName" readonly="readonly"  type="text" size="35" value="${question.userName }"/></p>
					<div class="divider"></div>
					
					<p style="height: 60px;"><label>标题：</label>
						<textarea rows="2" cols="35" name="title" readonly="readonly">${question.title }</textarea>
					<div class="divider"></div>
					
					<p style="height: 120px;"><label>内容：</label>
						<textarea rows="6" cols="35" name="content" readonly="readonly">${question.content }</textarea>
					</p>
					<div class="divider"></div>
					
					<p><label>提问时间：</label>
						<input name="questionDate" readonly="readonly" type="text" size="35" value="<fmt:formatDate value="${question.questionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>" /></p>
					<div class="divider"></div>
					
					<p><label>是否前台显示:</label>
						<c:if test="${question.hidden eq 0 }">
							<input name="hidden" type="radio" value="0" checked="checked"/>显示&nbsp;&nbsp;&nbsp;&nbsp;
							<input name="hidden" type="radio" value="1" />不显示</p>
						</c:if>
						<c:if test="${question.hidden eq 1 }">
							<input name="hidden" type="radio" value="0"/>显示&nbsp;&nbsp;&nbsp;&nbsp;
							<input name="hidden" value="1" type="radio" checked="checked" />不显示</p>
						</c:if>
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
