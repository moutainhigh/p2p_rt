<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="credit/${PRE_PATH_VIEW }getListCreditType?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							积分名称:
							<input type="text" name="creditName" value="${searchParams.creditName }"/>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'credit/v_addCreditType')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="dialog"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'credit/v_getCreditType')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'credit/o_deleteCreditType')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该积分类型吗？"><span>${rightsub.moduleName}</span>
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
						<a class="add" href="credit/${PRE_PATH_VIEW }get1CreditType?rel=${rel }" rel="addCredit" target="dialog"><span>添加</span></a>
							</li>
							<li class="line">
								line
							</li>
							<li>
								<a class="edit" href="credit/${PRE_PATH_VIEW }getCreditType/{usrId}?rel=${rel }" rel="editCredit"
									target="dialog"><span>修改</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
								<li>
								<a class="delete"
									href="credit/${PRE_PATH_EDIT }deleteCreditType/{usrId}"
									target="ajaxTodo" title="确认要删除该用户吗？"><span>删除</span>
								</a>
							</li>
				</ul>
				-->
			</div>
			<table class="list" width="100%" layoutH="87">
				<thead>
					<tr>
						<th width="3%"  align="center">序号</th>
						<th width="7%" align="center">积分名称</th>
						<th width="7%" align="center">积分编码</th>
						<th width="7%" align="center">积分数值</th>
						<th width="7%" align="center">积分周期</th>
						<th width="7%" align="center">奖励次数</th>
						<th width="7%" align="center">时间间隔</th>
						<th width="7%" align="center">操作者</th>
						<th width="7%" align="center">添加时间</th>
						<th width="7%" align="center">添加IP</th>
						<th width="7%" align="center">最后更新时间</th>
						<th width="7%" align="center">最后更新IP</th>
						<th width="7%" align="center">备注</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="credit" items="${pm.list }">
						<tr target="sid_support" rel="${credit.id }">
							<th  align="center">
							    ${credit.id }
							</th>
							<th  align="center">
							    ${credit.creditName }
							</th>
							<th  align="center">
							   ${credit.creditCode }
							</th>
							<th  align="center">
							    ${credit.creditScore }
							</th>
							<th  align="center">
								<c:if test="${credit.creditCycle==1 }">
								一次
								</c:if>
								<c:if test="${credit.creditCycle==2 }">
								每天
								</c:if>
								<c:if test="${credit.creditCycle==3 }">
								间隔分钟
								</c:if>
								<c:if test="${credit.creditCycle==4 }">
								不限
								</c:if>
							</th>
							<th  align="center">
								<c:if test="${credit.creditAwardTimes==0 }">
								不限
								</c:if>
							</th>
							<th  align="center">
							    ${credit.creditInterval }分钟
							</th>
							<th  align="center">
							    ${credit.creditOptUserid }
							</th>
							<th  align="center">
								<fmt:formatDate value="${credit.creditOptDatetime }" pattern="yyyy-MM-dd  HH:mm:ss" />
							</th>
							<th  align="center">
							    ${credit.creditOptIp }
							</th>
							<th  align="center">
								<fmt:formatDate value="${credit.creditOptUpdatetime }" pattern="yyyy-MM-dd  HH:mm:ss" />
							</th>
							<th  align="center">
							    ${credit.creditOptUpdateip }
							</th>
							<th  align="center">
							    ${credit.creditRemark }
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