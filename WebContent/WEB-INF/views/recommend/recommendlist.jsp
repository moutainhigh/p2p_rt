<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="recommend/${PRE_PATH_VIEW }getRecommendsList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名：
							<input type="text" name="userAccount" id="userAccount" value="${searchParams.userAccount}"/>
						</td>
						<td>
							推荐人：
							<input type="text" name="recommendUser" id="recommendUser" value="${searchParams.recommendUser}"/>
						</td>
						<td>
							状态：
							<select name="recommendStatus">
								<option value="">全部</option>
								<c:forEach var="item" items="${RECOMMEND_ALL_RECOMMEND_STATUS}">   
								<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							添加时间：
							<input type="text" name="beginTime" id="beginTime" value="${searchParams.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" name="endTime" id="endTime" value="${searchParams.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
				var queryStatus = "${searchParams.recommendStatus}";
				if(queryStatus){
					$("select[name='recommendStatus']").val(queryStatus);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'recommend/v_getRecommendById')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"  target="dialog"  width="750" height="500"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if
						test="${fn:contains(rightsub.moduleUrl,'recommend/toExcel')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}&&beginTime=${searchParams.beginTime}&&endTime=${searchParams.endTime}&&userAccount=${searchParams.userAccount}&&recommendUser=${searchParams.recommendUser}&&recommendStatus=${searchParams.recommendStatus}"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					</c:forEach>
			
			</ul>
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="5%"  align="center">序号</th>
						<th width="6%" align="center">用户名称</th>
						<th width="6%" align="center">用户真实姓名</th>
						<th width="6%" align="center">被推荐人手机号码</th>
						<th width="6%" align="center">状态</th>
						<th width="10%" align="center">添加时间</th>
						<th width="6%" align="center">推荐人</th>
						<th width="6%" align="center">推荐人真实姓名</th>
						<th width="6%" align="center">推荐人手机号码</th>
						
						<th width="6%" align="center">审核人</th>
						<th width="10%" align="center">审核时间</th>
						<th width="6%" align="center">审核IP</th>
						<th width="10%" align="center">审核备注</th>
						<th width="6%" align="center">排序</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="recommend" items="${pm.list }">
						<tr  target="sid_support" rel="${recommend.id }">
							<th  align="center">
							    ${recommend.id}
							</th>
							<th  align="center">
							    ${recommend.userAccount}
							</th>
							<th  align="center">
							    ${recommend.userRealname}
							</th>
							<th  align="center">
							   ${recommend.userPhone}
							</th>
							<th  align="center">
								${RECOMMEND_ALL_RECOMMEND_STATUS[recommend.recommendStatus]}
							</th>
							<th  align="center">
							    <fmt:formatDate value="${recommend.recommendAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								${recommend.recommendUser}
							</th>
							<th  align="center">
							   ${recommend.recommendUserRealname}
							</th>
							<th  align="center">
							   ${recommend.recommendUserPhone}
							</th>
							
							<th  align="center">
							    ${recommend.verifyUser}
							</th>
							<th  align="center">
							    <fmt:formatDate value="${recommend.verifyAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
							    ${recommend.verifyAddip}
							</th>
							<th  align="center">
							    ${recommend.verifyRemark}
							</th>
							<th  align="center">
								${recommend.recommendSequence}
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