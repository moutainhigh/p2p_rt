<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%-- <c:if test="${code==1}"> --%>
	<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox2');"
		action="content/${PRE_PATH_VIEW }getSonList?right_id=${right_id }&contentIds=${contentIds}"
		method="post">
		<div class="pageHeader">
							<input type="hidden" name="init" value=0>
								<input type="hidden" name="right_ids" value="${right_id}"/>
				                <input type="hidden" name="channelIds" value="${channelIds}">
				                <input type="hidden" name="channelParentId" value="${channelParentId }"/>
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
									       <c:forEach items="${righSubtList}" var="rightsub">
											<c:if test="${fn:contains(rightsub.moduleUrl,'add')}">
												<li>
													<a class="add" href="${rightsub.moduleUrl}&right_id=${right_id}"  target="navTab" rel="jbsxBox2" width="800" height="580"><span>${rightsub.moduleName}</span></a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											<c:if test="${fn:contains(rightsub.moduleUrl,'update')}">
												<li>
													<a class="edit" href="${rightsub.moduleUrl}&contentIdChannelId={contentIdChannelId}&right_id=${right_id}"
														target="navTab" rel="jbsxBox2" width="800" height="580"><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
											
											<c:if test="${fn:contains(rightsub.moduleUrl,'content/o_delete')}">
												<li>
													<a class="delete"
														href="${rightsub.moduleUrl}?contentIdChannelId={contentIdChannelId}&right_id=${right_id}"
														target="ajaxTodo" rel="jbsxBox2"  title="确认要删除信息？"><span>${rightsub.moduleName}</span>
													</a>
												</li>
												<li class="line">
													line
												</li>
											</c:if>
										</c:forEach> 
											<%-- <li>
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
												</li> --%>
									</ul>
								</div>
								<table class="list" width="100%" layoutH="105" fetchSize="200">
									<thead>
										<tr>
											<th width="10%"  align="center">序&nbsp;&nbsp;号</th>
											<th width="10%" align="center">内容标题</th>
											<th width="10%" align="center">所属栏目</th>
											<th width="10%" align="center">排&nbsp;&nbsp;序</th>
											<th width="15%" align="center">发布时间</th>
										</tr>
									</thead>
									<tbody>
										 <c:forEach var="content" items="${pm.list }">
											<tr target="contentIdChannelId" rel="${content.contentId },${content.channelId}">
												<th  align="center">
												    ${content.contentId }
												</th>
												<th  align="center">
												    ${content.contentTitle }
												</th>
												<th  align="center">
												    ${content.channelTitle }
												</th>
												<th  align="center">
												     <input type="text" name="contentSequence" style="width:20px;" value="${content.contentSequence}" onblur="blurSeq(${content.contentId},this,${content.channelId })" />  
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
					</form>
					<!-- 根目录  E -->
		
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
<script type="text/javascript">
      function blurSeq(contentId,curText,channelId){
    	var contentVal= $(curText).val();
    	  $.ajax({
    		  type: "post",
    		  data: {"contentId" : contentId,"contentVal" : contentVal,"channelId":channelId},
    		  url: "content/editSeq",
    		  async:false,
    		  success:function (ret){
    			  if(ret == 'success'){
    				 $("#pagerForm").submit();
    			  }else{
    				  alert("操作失败！");
    			  }
    		  }
    		});
     }
     </script>