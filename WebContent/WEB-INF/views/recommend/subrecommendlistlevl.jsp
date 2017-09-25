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
			<font style="color: red;font-size: 18x;">${user.userAccount }</font>的推广记录(一级推荐${listsize1 }个,二级推荐${listsize2 }个,三级推荐${listsize3 }个)
		</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
					 <li>
						<a class="add" href="recommend/${PRE_PATH_VIEW }exportSubRecommendsList?recommendUserId=${recommendUserId}"><span>导&nbsp;出</span>
						</a>
					</li>
					<%-- <c:forEach items="${righSubtList}" var="rightsub">
						<c:if
						test="${fn:contains(rightsub.moduleUrl,'recommend/v_exportSubRecommendsList')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?recommendUserId=${recommendUserId}"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
						
					</c:forEach> --%>
					
					<li>
						<a class="icon" onclick="closeform()"><span>退&nbsp;出</span>
						</a>
					</li>
			</ul>
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="5%"  align="center">被推荐用户id
					</th>
						<th width="9%" align="center">被推荐用户名称</th>
						<th width="9%" align="center">被推荐用户员工内部号</th>
						<th width="9%" align="center">被推荐用户真实姓名</th>
						<th width="9%" align="center">建立时间</th>
						<th width="9%" align="center">分销级数</th>
						
					</tr>
				</thead>
				<tbody>
					<tr>
							<th  align="center">
							    一级  （${listsize1 }个）
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							</tr>
					<c:forEach var="recommend" items="${list1 }">
						<tr  target="sid_support" rel="${recommend.id }">
							<tr  target="sid_support" rel="${recommend.id }">
							<th  align="center">
							    ${recommend.id}
							</th>
							<th  align="center">
							    ${recommend.userAccount}
							</th>
							<th  align="center">
							    ${recommend.rtUserFlag}
							</th>
							<th  align="center">
							    ${recommend.userRealname}
							</th>
							<th  align="center">
							   
							    <fmt:formatDate value="${recommend.userAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							 <th  align="center">
							    ${recommend.levl}
							</th> 
							
						</tr>
						</c:forEach>
						
						<tr>
							<th  align="center">
							    二级  （${listsize2 }个）
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							</tr>
						
						<c:forEach var="recommend" items="${list2 }">
						<tr  target="sid_support" rel="${recommend.id }">
							<tr  target="sid_support" rel="${recommend.id }">
							<th  align="center">
							    ${recommend.id}
							</th>
							<th  align="center">
							    ${recommend.userAccount}
							</th>
							<th  align="center">
							    ${recommend.rtUserFlag}
							</th>
							<th  align="center">
							    ${recommend.userRealname}
							</th>
							<th  align="center">
							   
							    <fmt:formatDate value="${recommend.userAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
							    ${recommend.levl}
							</th>
							
						</tr>
						</c:forEach>
						
						
						<tr>
							<th  align="center">
							    三级  （${listsize3 }个）
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							<th  align="center">
							</th>
							</tr>
						
						<c:forEach var="recommend" items="${list3 }">
						<tr  target="sid_support" rel="${recommend.id }">
							<tr  target="sid_support" rel="${recommend.id }">
							<th  align="center">
							    ${recommend.id}
							</th>
							<th  align="center">
							    ${recommend.userAccount}
							</th>
							<th  align="center">
							    ${recommend.rtUserFlag}
							</th>
							<th  align="center">
							    ${recommend.userRealname}
							</th>
							<th  align="center">
							   
							    <fmt:formatDate value="${recommend.userAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
							    ${recommend.levl}
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

<script type="text/javascript">
	  
	  function closeform(){
		navTab.closeCurrentTab();
	  }
	</script>