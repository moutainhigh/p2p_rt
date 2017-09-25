<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="pageContent">
			<form method="post" action="dis/config/saveOrUpdate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id"  value="${config.id}" />
				
				<div class="pageFormContent" layoutH="56">
				
					<p><label>key：</label>
						<input name="disKey" readonly="readonly"  type="text"  size="20" value="${config.disKey }" /></p>
					<div class="divider"></div>
					
					<p style="height: 100px;"><label>设置描述：</label>
						<%-- <input name="description"  type="text"  size="20" value="${config.description }" /> --%>
						<textarea rows="5" cols="20" name="description">${config.description }</textarea>
					</p>
					<div class="divider"></div>
					
					<p><label>是否需要审核:</label>
						<c:if test="${config.disValue eq 'YES' }">
							<input name="disValue"  type="radio" value="YES" checked="checked"/>需要&nbsp;&nbsp;&nbsp;&nbsp;
							<input name="disValue" type="radio"   value="NO" />不需要</p>
						</c:if>
						<c:if test="${config.disValue eq 'NO' }">
							<input name="disValue"  type="radio" value="YES"/>需要&nbsp;&nbsp;&nbsp;&nbsp;
							<input name="disValue" value="NO"  type="radio"  checked="checked" />不需要</p>
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
