<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="sys/${PRE_PATH_VIEW }getList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							支付类型：
							<select name="paymentType">
								<option value="">全部</option>
								<option value="2">线下支付</option>
								<option value="1">线上支付</option>
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
				var queryType = "${searchParams.paymentType }";
				if(queryType){
					$("select[name='paymentType']").val(queryType);
				}
				</script>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'sys/v_getPaymentConfig')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="dialog"  width="750" height="550"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'sys/v_getById')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog"  width="750" height="550" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'sys/o_deletePaymentConfig')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该支付方式吗？"><span>${rightsub.moduleName}</span>
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
						<a class="add" href="sys/${PRE_PATH_VIEW }getPaymentConfig?rel=${rel }" rel="addPaymentConfig" target="dialog"  width="800" height="580"><span>添加</span></a>
							</li>
							<li class="line">
								line
							</li>
							<li>
								<a  class="edit" href="sys/${PRE_PATH_VIEW }getById/{paymentConfigId}?rel=${rel }" rel="editPaymentConfig"
									target="dialog"  width="800" height="580"><span>修改</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
								<li>
								<a class="delete"
									href="sys/${PRE_PATH_EDIT }deletePaymentConfig/{paymentConfigId}"
									target="ajaxTodo" title="确认要删除该支付方式吗？"><span>删除</span>
								</a>
							</li>
				</ul>
				 -->
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="5%"  align="center">序号</th>
						<th width="7%" align="center">名称</th>
						<th width="7%" align="center">编码</th>
						<th width="9%" align="center">状态</th>
						<th width="7%" align="center">类型</th>
						<th width="7%" align="center">费用类型</th>
						<th width="7%" align="center">最大金额</th>
						<th width="7%" align="center">最大费率</th>
						<th width="22%" align="center">描述</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="paymentConfig" items="${pm.list }">
						<tr  target="sid_support" rel="${paymentConfig.id }">
							<th  align="center">
							    ${paymentConfig.id}
							</th>
							<th  align="center">
							    ${paymentConfig.paymentName }
							</th>
							<th  align="center">
							   ${paymentConfig.paymentCode }
							</th>
							<th  align="center">
								${paymentConfig.viewPaymentStatus }
							</th>
							<th  align="center">
								${paymentConfig.viewPaymentType }
							</th>
							<th  align="center">
							    ${paymentConfig.viewFeeType }
							</th>
							<th  align="center">
							    ${paymentConfig.paymentMaxMoney }
							</th>
							<th  align="center">
							    ${paymentConfig.paymentMaxFee }
							</th>
							<th  align="center">
							    ${paymentConfig.paymentDescription }
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