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
		var type = "${type}";
		var year = $("#dateYear").val();
		$(function() {
			$.ajax({
				type : "post",
				url : "summary/getPlatformChartData",
				data : {dateYear: year},
				dataType : "json",
				success : function(data) {
					$("#platformContainer").highcharts( {
						chart : {
							type : 'line'
						},
						title : {
							text : year + '年季度平台资金统计折线图'
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
								text : '金额(元)'
							},
							plotLines : [ {
								value : 0+'k',
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
	action="summary/${PRE_PATH_VIEW }getPlatformSummary?right_id=${params.right_id}" method="post">
	
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>年份: 
							<input type="text" name="dateYear" id="dateYear" value="${params.dateYear }" class="date textInput readonly" datefmt="yyyy"  readonly="readonly"  maxDate="{%y}-%M-{%d}"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'summary/o_toPlatformExcel')}">
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
					<th width="16%" align="center">类别</th>
					<th width="16%" align="center">一季度</th>
					<th width="16%" align="center">二季度</th>
					<th width="16%" align="center">三季度</th>
					<th width="16%" align="center">四季度</th>
					<th width="20%" align="center">合计</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="re">
					<tr>
						<th align="center">${re.money_type }</th>
						<th align="center">${re.spring }</th>
						<th align="center">${re.summer }</th>
						<th align="center">${re.autumn }</th>
						<th align="center">${re.winter }</th>
						<th align="center">${re.total }</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- 图表显示区 -->
		<div id="platformContainer" style="min-width: 900px; height: 350px;"></div>
	</div>
</form>