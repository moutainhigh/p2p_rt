<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
%>

			
	<c:if test="${code==1}">		
			<div class="accordion" fillSpace="sideBar">
					<c:forEach items="${requestScope.rightModuleList}" var="rightModule" varStatus="rightModule1">
					<div class="accordionHeader">
						<c:if test="${ rightModule1.index==0}">
						<h2 class="collapsable"><span>icon</span>${rightModule.moduleName }</h2>
						</c:if>
						<c:if test="${ rightModule1.index>0}">
							<h2 class=""><span>icon</span>${rightModule.moduleName }</h2>
						</c:if>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<c:forEach items="${rightModule.sysModule}" var="righSubtList">
								<c:if test="${fn:contains(righSubtList.moduleUrl,'?')}">
									<c:if test="${fn:contains(righSubtList.moduleUrl,'right_id')}">
										<c:set var="startIndx" value="${fn:indexOf(righSubtList.moduleUrl,'right_id')}"></c:set>
										<c:set var="urlLength" value="${fn:length(righSubtList.moduleUrl)}"></c:set>
										<li><a href="${righSubtList.moduleUrl}" target="navTab" rel="${fn:substring(righSubtList.moduleUrl,startIndx+9,urlLength)}">${righSubtList.moduleName}</a></li>
									</c:if>
									<c:if test="${!fn:contains(righSubtList.moduleUrl,'right_id')}">
									<li><a href="${righSubtList.moduleUrl}&right_id=${righSubtList.id}" target="navTab" rel="${righSubtList.id}">${righSubtList.moduleName}</a></li>
									</c:if>
								</c:if>
								<c:if test="${!fn:contains(righSubtList.moduleUrl,'?')}">
									<li><a href="${righSubtList.moduleUrl}?right_id=${righSubtList.id}" target="navTab" rel="${righSubtList.id}">${righSubtList.moduleName}</a></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					</c:forEach>
				</div>
				
				</c:if>
<c:if test="${code==0}">
	${message}
</c:if>

