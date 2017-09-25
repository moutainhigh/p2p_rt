<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="sys/${PRE_PATH_VIEW }getNoticeList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							提醒类型：
							<select name="noticeTypeId" id="noticeTypeId">
								<option value="">全部</option>
								<c:forEach var="noticeType" items="${noticeTypes}">
									<option value="${noticeType.id}">${noticeType.noticeName}</option>
								</c:forEach>
							</select>
						</td>
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
				<script type="text/javascript">
				var queryType = "${searchParams.noticeTypeId}";
				if(queryType){
					$("select[name='noticeTypeId']").val(queryType);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'sys/v_forAddNotice')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="dialog" width="600" height="450"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'sys/v_getNoticeById')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog" width="600" height="450" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'sys/o_deleteNotice')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该提醒吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
				<!--  
				<ul class="toolBar">
						<li>
						<a class="add" href="sys/${PRE_PATH_VIEW }forAddNotice?rel=${rel }" rel="addNotice" target="dialog"  width="600" height="560"><span>添加</span></a>
							</li>
							<li class="line">
								line
							</li>
							<li>
								<a  class="edit" href="sys/${PRE_PATH_VIEW }getNoticeById/{noticeId}?rel=${rel }" rel="editNotice"
									target="dialog"  width="600" height="560"><span>修改</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
								<li>
								<a class="delete"
									href="sys/${PRE_PATH_EDIT }deleteNotice/{noticeId}"
									target="ajaxTodo" title="确认要删除该提醒吗？"><span>删除</span>
								</a>
							</li>
				</ul>-->
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="5%"  align="center">序号</th>
						<th width="11%" align="center">名称</th>
						<th width="11%" align="center">编码</th>
						<th width="7%" align="center">状态</th>
						<th width="11%" align="center">类型</th>
						<th width="7%" align="center">短消息</th>
						<th width="7%" align="center">邮箱</th>
						<th width="7%" align="center">手机短信</th>
						<th width="12%" align="center">添加时间</th>
						<th width="9%" align="center">添加IP</th>
						<th width="5%" align="center">排序</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="notice" items="${pm.list }">
						<tr  target="sid_support" rel="${notice.id }">
							<th  align="center">
							    ${notice.id}
							</th>
							<th  align="center">
							    ${notice.noticeTitle}
							</th>
							<th  align="center">
							   ${notice.noticeCode}
							</th>
							<th  align="center">
								${notice.viewNoticeStatus}
							</th>
							<th  align="center">
								${notice.noticeType.noticeName}
							</th>
							<th  align="center">
							    ${notice.viewMessage}
							</th>
							<th  align="center">
							    ${notice.viewEmail}
							</th>
							<th  align="center">
							    ${notice.viewPhone}
							</th>
							<th  align="center">
							    <fmt:formatDate value="${notice.noticeAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
							    ${notice.noticeAddip}
							</th>
							<th  align="center">
							    ${notice.noticeSequence}
							</th>
							
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<%@ include file="../page.jsp" %>
		</div>
</form>