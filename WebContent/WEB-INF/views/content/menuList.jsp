 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="pageContent" >
	<div class="tabs">	
		<div class="tabsContent">
			<div>
				<div layoutH="14" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
		            <ul class='tree treeFolder collapse'>
					    <li>
					    	<a href="content/${PRE_PATH_VIEW }getSonList?right_id=${right_id }" id="rootChannel" target="ajax" rel="jbsxBox2" >根目录</a>
					    	 ${requestScope.treeInfo }
					     </li>
				   </ul>
				</div>
				
				<div id="jbsxBox2" class="unitBox" style="margin-left:246px;">
					<!--第一次加载根目录list   S -->
					<%-- <form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox2');"	action="content/${PRE_PATH_VIEW }getSonList" method="post">
							<div class="pageHeader">
								<input type="hidden" name="init" value=0>
								<div class="searchBar">
									<table class="searchContent">
										<tr>
											<td>
												内容标题:
												<input type="text" name="contentTitle" value="${searchParams.contentTitle }"/>
											</td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td>
												<div class="buttonActive">
													<div class="buttonContent">
														<button type="submit">
															查  询
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
											<li>
											<a class="add" href="content/${PRE_PATH_VIEW }get/0" rel="AddContent" target="dialog" width="800" height="580"><span>添加栏目</span></a>
												</li>
												<li class="line">
													line
												</li>
												<li>
													<a class="edit" href="content/${PRE_PATH_VIEW }get/{contentId}?rel=${rel }" rel="EditContent"
														target="dialog"  width="800" height="580"><span>修改栏目</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
													<li>
													<a class="delete"
														href="content/${PRE_PATH_EDIT }delete/{contentlId}"
														target="ajaxTodo" title="确认要删除该栏目吗？"><span>删除栏目</span>
													</a>
												</li>
									</ul>
								</div>
								<table class="list" width="100%" layoutH="95" fetchSize="200">
									<thead>
										<tr>
											<th width="10%"  align="center">序&nbsp;&nbsp;号</th>
											<th width="10%" align="center">内容标题</th>
											<th width="10%" align="center">所属栏目</th>
											<th width="10%" align="center">排&nbsp;&nbsp;序</th>
											<!-- <th width="15%" align="center">创建时间</th> -->
											<!-- <th width="15%" align="center">修改时间</th> -->
											<th width="15%" align="center">发布时间</th>
										</tr>
									</thead>
									<tbody>
										 <c:forEach var="content" items="${pm.list }">
											<tr target="contentId" rel="${content.id }">
												<th  align="center">
												    ${content.id }
												</th>
												<th  align="center">
												    ${content.contentTitle }
												</th>
												<th  align="center">
												    ${content.channelIds }
												</th>
												<th  align="center">
												    <input type="text" name="channelSequence" style="width:20px;" value="${content.channelSequence }" onblur="blurSequence(${content.id},this)" /> 
												</th>
												<th  align="center">
												     <fmt:formatDate value="${content.contentPublishTime  }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</th>
											</tr>
											</c:forEach> 
									</tbody>
								</table>
								<!-- 分页 -->
								<c:set var="page" value="${pm }"></c:set>
								<c:set var="pagedRel" value="jbsxBox2"></c:set>
								<%@ include file="../page.jsp" %>
							</div>
					</form> --%>
					<!-- 根目录  E -->
				</div>
	
			</div>
			
			
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	
</div>
<script type="text/javascript">
$(document).ready(function(){
	setTimeout(function(){$("#rootChannel").click();},10);
	 
});  
</script>