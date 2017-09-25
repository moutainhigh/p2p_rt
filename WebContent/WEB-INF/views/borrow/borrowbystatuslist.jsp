<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="borrow/${PRE_PATH_VIEW }getBorrowByStatusList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<input type="hidden" name="borrowStatuss" value="${searchParams.borrowStatus}">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名：
							<input type="text" name="userAccount" id="userAccount" value="${searchParams.userAccount }"/>
						</td>
						<td>
							标种：
							<select name="bidKind">
								<option value="">全部</option>
								<c:forEach var="type" items="${borrowTypes}">   
								<option value="${type.id}">${type.name}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							状态：
							<!-- 所有借款查询 -->
							<input type="hidden" value="${ borrowStatusAll}" name="borrowStatusAll"/>
							<c:if test="${'0' eq borrowStatusAll }">
								<select name="borrowStatus">
									<option value="0">全部</option>
									<c:forEach var="item" items="${BORROW_ALL_STATUS}">   
									<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
							</c:if>
                            <!-- 未通过借款查询 -->
                            <c:if test="${'0' ne borrowStatusAll and '1' eq unpass}">
                                <select name="borrowStatus">
                                    <option value="3">初审未通过</option>
                                    <option value="8">自己取消</option>
                                    <option value="9">平台取消</option>
                                    <option value="11">复审未通过</option>
                                    <option value="12">已流标</option>
                                </select>
                            </c:if>
							<!-- 非所有借款查询 -->
							<c:if test="${'0' ne borrowStatusAll and '1' ne unpass}">
								<select name="borrowStatus" disabled="disabled">
									<option value="0">全部</option>
									<c:forEach var="item" items="${BORROW_ALL_STATUS}">   
									<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
							</c:if>
						</td>
						<td>
							发布时间：
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
				var queryStatus = "${searchParams.borrowStatus}";
				if(queryStatus){
					$("select[name='borrowStatus']").val(queryStatus);
					//$("select[name='borrowStatus']").attr("disabled",true);
				}
				var type="${searchParams.bidKind}";
				if(type){
					$("select[name='bidKind']").val(type);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_getBorrowById')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"  target="dialog" width="600" height="450"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/o_firstCheckBorrow')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&type=toJsp"
									target="navTab" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/o_secondCheckBorrow')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&type=toJsp"
									target="navTab" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'cancel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
								  target="ajaxTodo" title="确认撤销此标？"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_toExcel')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?borrowStatus=${searchParams.borrowStatus}&right_id=${right_id}&cashStatus=${cashStatus}" ><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/o_updateBorrowStatusForStatusFull')}">
							<li>
								<a class="icon" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&borrowStatus=${searchParams.borrowStatus}" 
								target="ajaxTodo" title="确认要将该标改为满标吗？"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_getBorrowTenderPage')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="navTab" width="750" height="550" ><span>${rightsub.moduleName}</span>
								</a>
							</li> 
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/o_editLimit')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&type=toJsp" target="navTab" ><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'extConfig/v_getBaseDataInfo/rb_borrow')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?borrowId={sid_support}&right_id=${right_id}" target="navTab" width="750" height="550"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_toUpdateBorrow')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"  target="dialog" width="1210" height="670">
									<span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="5%"  align="center">序号</th>
						<th width="11%" align="center">用户名称</th>
						<th width="9%" align="center">标种</th>
						<th width="12%" align="center">借款标题</th>
						<th width="9%" align="center">借款金额</th>
						<th width="9%" align="center">已投金额</th>
						<th width="6%" align="center">利率</th>
						<c:if test="${searchParams.borrowStatus eq '2' }">
							<th width="6%" align="center">投标进度</th>
						</c:if>
						<th width="9%" align="center">借款期限</th>
						<th width="11%" align="center">发布时间</th>
						<c:if test="${searchParams.borrowStatus eq '10' ||  searchParams.borrowStatus eq '11'}">
							<th width="11%" align="center">初审时间</th>
						</c:if>
						<th width="9%" align="center">状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="borrow" items="${pm.list }">
						<tr  target="sid_support" rel="${borrow.id }">
							<th  align="center">
							    ${borrow.id}
							</th>
							<th  align="center">
							    ${borrow.userAccount}
							</th>
							<th  align="center">
							    ${borrow.borrowTypeName}
							</th>
							<th  align="center">
								<a href="borrow/v_getBorrowDetailById?supportID=${borrow.id }&right_id=${right_id}"  target="dialog"  width="810" height="550" >${borrow.borrowTitle}</a>
							</th>
							<th  align="center">
								${borrow.borrowSum}
							</th>
							<th  align="center">
								${borrow.tenderSum}
							</th>
							<th  align="center">
							    <fmt:formatNumber value="${borrow.annualInterestRate}" pattern="#.##"/>%
							</th>
							<c:if test="${searchParams.borrowStatus eq '2' }">
							<th  align="center">
							    <fmt:formatNumber value="${borrow.tenderProgressRate}" pattern="#.##"/>%
							</th>
							</c:if>
								<th  align="center">
									${borrow.borrowTimeLimit}
									<c:if test="${borrow.isDay eq 1 }">天</c:if>
									<c:if test="${borrow.isDay eq 2 }">月</c:if>
								</th>
							<th  align="center">
								<fmt:formatDate value="${borrow.publishDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<c:if test="${searchParams.borrowStatus eq '10' ||  searchParams.borrowStatus eq '11'}">
								<th  align="center">
									<fmt:formatDate value="${borrow.verifyTrialTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</th>
							</c:if>
							<th  align="center">
							    ${BORROW_ALL_STATUS[borrow.borrowStatus] }
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