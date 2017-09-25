<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="attestation/${PRE_PATH_VIEW }getListAttestationType?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							类型名称:
							<input type="text" name="attestationName" value="${searchParams.attestationName }"/>
						</td>
						 <!-- <td>
							开始时间:
							<input type="text" name="startTime" class="date textInput readonly"  readonly="readonly"/>
						</td>
						<td>
							结束时间: 
							<input type="text" name="endTime" class="date textInput readonly"  readonly="readonly" />
						</td>  -->
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										查询
									</button>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'attestation/v_get1AttestationType')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="dialog"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'attestation/v_getAttestationType')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'attestation/o_deleteAttestationType')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该证件类型吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<table class="list" width="100%" layoutH="87">
				<thead>
					<tr>
						<th width="3%"  align="center">序号</th>
						<th width="30%" align="center">类型名称</th>
						<th width="3%" align="center">积分</th>
						<th width="3%" align="center">排序</th>
						<th width="3%" align="center">状态</th>
						<th width="7%" align="center">添加时间</th>
						<th width="7%" align="center">添加IP</th>
						<th width="7%" align="center">简要</th>
						<th width="7%" align="center">备注</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="attType" items="${pm.list }">
						<tr target="sid_support" rel="${attType.id }">
							<th  align="center">
							    ${attType.id }
							</th>
							<th  align="center">
							    ${attType.attestationName }
							</th>
							<th  align="center">
							   ${attType.attestationScore }
							</th>
							<th  align="center">
							    ${attType.attestationSequence }
							</th>
							<th  align="center">
								<c:if test="${attType.attestationStatus==0 }">不可用</c:if>
								<c:if test="${attType.attestationStatus==1 }">可用</c:if>
							</th>
							<th  align="center">
								<fmt:formatDate value="${attType.attestationDatetime }" pattern="yyyy-MM-dd"/>
								
							</th>
							<th  align="center">
							    ${attType.attestationIp }
							</th>
							<th  align="center">
							    ${attType.attestationSummary }
							</th>
							<th  align="center">
							    ${attType.attestationRemark }
							</th>
							
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<%@ include file="../../page.jsp" %>
		</div>
</form>