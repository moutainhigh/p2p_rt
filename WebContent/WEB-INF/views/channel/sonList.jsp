<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

	<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox1');"	action="channel/${PRE_PATH_VIEW }getSonList?right_id=${right_id}" method="post">
							<div class="pageHeader">
								<input type="hidden" name="init" value=0>
								<input type="hidden" name="right_ids" value="${right_id}"/>
				                <input type="hidden" name="channelIds" value="${channelIds }">
				                <input type="hidden" name="channelParentId" value="${channelParentId }"/>
								<div class="searchBar">
									<table class="searchContent">
										<tr>
											<td>
												栏目名称:
												<input type="text" name="channelTitle" value="${searchParams.channelTitle }"/>
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
										 <c:forEach items="${righSubtList}" var="rightsub">
											<c:if test="${fn:contains(rightsub.moduleUrl,'add')}">
												<li>
													<a class="add" href="${rightsub.moduleUrl}&right_id=${right_id}"  target="dialog" rel="jbsxBox1" width="800" height="580"><span>${rightsub.moduleName}</span></a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											<c:if test="${fn:contains(rightsub.moduleUrl,'update')}">
												<li>
													<a class="edit" href="${rightsub.moduleUrl}&channelId={channelId}&right_id=${right_id}"
														target="dialog" rel="jbsxBox1" width="800" height="580"><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											
											<c:if test="${fn:contains(rightsub.moduleUrl,'channel/o_delete')}">
												<li>
													<a class="delete"
														href="${rightsub.moduleUrl}?channelId={channelId}&right_id=${right_id}"
														target="ajaxTodo" rel="jbsxBox1"  title="确认要删除栏目？"><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
										</c:forEach> 
											<%--  <li>
											<a class="add" href="channel/${PRE_PATH_VIEW }get/0" rel="AddChannel" target="dialog" width="800" height="580"><span>添加栏目</span></a>
												</li>
												<li class="line">
													line
												</li>
												<li>
													<a class="edit" href="channel/${PRE_PATH_VIEW }get/{channelId}?rel=${rel }" rel="EditChannel"
														target="dialog"  width="800" height="580"><span>修改栏目</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
													<li>
													<a class="delete"
														href="channel/${PRE_PATH_EDIT }delete/{channelId}"
														target="ajaxTodo" title="确认要删除该栏目吗？"><span>删除栏目</span>
													</a>
												</li>  --%>
									</ul>
								</div>
								<table class="list" width="100%" layoutH="105" fetchSize="200">
									<thead>
										<tr>
											<th width="10%"  align="center">序&nbsp;&nbsp;号</th>
											<th width="10%" align="center">栏目名称</th>
											<th width="10%" align="center">状&nbsp;&nbsp;态</th>
											<th width="10%" align="center">排&nbsp;&nbsp;序</th>
											<!-- <th width="15%" align="center">创建时间</th> -->
											<th width="15%" align="center">修改时间</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="channel" items="${pm.list }">
											<tr target="channelId" rel="${channel.id }">
												<th  align="center">
												    ${channel.id }
												</th>
												<th  align="center">
												    ${channel.channelTitle }
												</th>
												<th  align="center">
												   <c:if test="${'0' eq channel.channelDisplay }">隐藏</c:if>
												   <c:if test="${'1' eq channel.channelDisplay }">显示</c:if>
												</th>
												<th  align="center">
												    <input type="text"  style="width:20px;" value="${channel.channelSequence }" onblur="blurSequence(${channel.id},this)" /> 
												</th>
												<%-- <th  align="center">
												  ${ channel.channelCreateTime }
												</th> --%>
												<th  align="center">
												     <fmt:formatDate value="${channel.channelUpdateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</th>
											</tr>
											</c:forEach>
									</tbody>
								</table>
								<!-- 分页 -->
								<c:set var="page" value="${pm }"></c:set>
								<c:set var="pagedRel" value="jbsxBox1"></c:set>
								<%@ include file="../page.jsp" %>
							</div>
					</form>

<script type="text/javascript">
      function blurSequence(channelId,curText){
    	var channeVal= $(curText).val();
    	  $.ajax({
    		  type: "post",
    		  data: {"channelId" : channelId,"channeVal" : channeVal},
    		  url: "channel/editSeq",
    		  async:false,
    		  success:function (ret){
    			  if(ret == 'success'){
    				 $("#pagerForm").submit();
    			  }else{
    				  alert("排序不能为空");
    			  }
    		  }
    		});
     }
     </script>