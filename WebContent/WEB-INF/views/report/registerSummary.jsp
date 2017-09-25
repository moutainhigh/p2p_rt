<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="resources/js/highcharts.js"></script>
<script type="text/javascript" src="resources/js/exporting.js"></script>

	<script type="text/javascript">
		$(function() {
			$.ajax({
				type : "post",
				url : "summary/getRegisterChartData",
				dataType : "json",
				success : function(data) {
					$("#registerContainer").highcharts( {
						chart : {
							type : 'line'
						},
						title : {
							text : '用户注册统计折线图'
						},
						credits : {
							enabled : false
						},
						tooltip : {
							formatter : function() {
								return '<b>' + this.series.name + '</b><br/>' + this.x + ': ' + this.y;
							}
						},
						xAxis : {
							categories : data.xaxis
						},
						yAxis : {
							title : {
								text : '数量(人)'
							},
							plotLines : [ {
								value : 0,
								width : 1,
								color : '#808080'
							} ]
						},
						plotOptions: {//显示数值
							line: {
								dataLabels: {
									enabled: true
								}
							}
						},
						series : data.series

					});
				},
			});
		});
	</script>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="summary/${PRE_PATH_VIEW }getRegisterSummary?right_id=${params.right_id}" method="post">
	
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
		</div>
	</div>
	
	<div class="pageContent">
		<div class="panelBar">
				<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'summary/o_toRegisterExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${params.right_id}&&dateYear=${params.dateYear}" ><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			
		<!-- 数据表 -->
		<table class="list" width="100%" layoutH="500">
			<thead>
				<tr>
					<th width="34%" align="center">省份</th>
					<th width="33%" align="center">新增（人）</th>
					<th width="33%" align="center">累计（人）</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="re">
					<tr>
						<th align="center">${re.province }</th>
						<th align="center">${re.day_add }</th>
						<th align="center">${re.total_add }</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- 图表显示区 -->
		<div id="registerContainer" style="min-width: 900px; height: 350px;"></div>
	</div>
</form>