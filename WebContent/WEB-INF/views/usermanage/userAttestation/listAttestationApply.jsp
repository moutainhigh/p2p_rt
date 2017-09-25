<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="attestation/${PRE_PATH_VIEW }getListAttestationApply?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text" name="userAccount" value="${searchParams.userAccount }"/>
						</td>
						<td>
							证件类型:
							<select name="attestationTypeid">
								<option value="">---------------全部-----------------</option>
								<c:forEach items="${attList }" var="att">
								<option value="${att.id }" <c:if test="${att.id==searchParams.attestationTypeid}">selected="selected"</c:if>>${att.attestationName }</option>
								</c:forEach>
							</select>
							<%-- <input type="text" name="attestationName" value="${searchParams.attestationName }"/> --%>
						</td>
						<td>
							状态:
							<select name="attestationStatus" >
								<option value="" <c:if test="${searchParams.attestationStatus==null}">selected="selected"</c:if>>全部</option>
								<option value="0" <c:if test="${searchParams.attestationStatus==0}">selected="selected"</c:if>>申请中</option>
								<option value="1"<c:if test="${searchParams.attestationStatus==1}">selected="selected"</c:if>>申请通过</option>
								<option value="-1"<c:if test="${searchParams.attestationStatus==-1}">selected="selected"</c:if>>申请未通过</option>
							</select>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'attestation/v_getAttestationApply')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog" width="500" height="550"><span>${rightsub.moduleName}</span>
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
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该权限吗？"><span>${rightsub.moduleName}</span>
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
						<th width="3%" align="center">用户名称</th>
						<th width="3%" align="center">真是姓名</th>
						<th width="7%" align="center">认证类型</th>
						<th width="7%" align="center">认证图片</th>
						<th width="3%" align="center">认证积分</th>
						<th width="3%" align="center">认证状态</th>
						<th width="7%" align="center">认证简介</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="attApply" items="${pm.list }">
						<tr target="sid_support" rel="${attApply.id }">
							<th  align="center">
							    ${attApply.id }
							</th>
							<th  align="center">
							    ${attApply.userAccount }
							</th>
							<th  align="center">
							   ${attApply.userRealName }
							</th>
							<th  align="center">
							    ${attApply.attestationName }
							</th>
							<th  align="center">
								<c:if test="${not empty attApply.attestationImg }">
									<a href="<%=path %>${attApply.attestationImg }" target="_Blank">查看</a>
								</c:if>
								<c:if test="${empty attApply.attestationImg }">
									查看
								</c:if>
							</th>
							<th  align="center">
								 ${attApply.attestationScore }
							</th>
							<th  align="center">
								<c:if test="${attApply.attestationStatus==0 }"><span style="color:blue; ">申请中</span></c:if>
								<c:if test="${attApply.attestationStatus==1 }"><span style="color: green;">申请通过</span></c:if>
								<c:if test="${attApply.attestationStatus==-1 }"><span style="color:red; ">申请未通过</span></c:if>
							</th>
							<th  align="center">
							    ${attApply.attestationRemarks }
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