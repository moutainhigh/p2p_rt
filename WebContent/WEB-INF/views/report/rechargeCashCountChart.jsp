<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="resources/js/highcharts.js"></script>
<script type="text/javascript" src="resources/js/exporting.js"></script>

<form  id="pagerForm" onsubmit="return navTabSearch(this);"
	action="summary/${PRE_PATH_VIEW }getWithGoToWhat?type=${type }" method="post"><!-- summary/goto platformChart -->
	
<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>年份: 
							<input type="text" name="dateYear" id="dateYear" value="${dateYear }" class="date textInput readonly" datefmt="yyyy"  readonly="readonly"  maxDate="{%y}-%M-{%d}"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button>查询</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
	
	<div  class="pageContent">
	
	<!-- 图表显示区 -->
	<div id="rechargeCashCountContainer" style="min-width: 900px; height: 350px;"></div>
   	</div>	 
	<script type="text/javascript">
		var type = "${type}";
		var year = $("#dateYear").val();
		$(function() {
			$.ajax({
				type : "post",
				url : "summary/getRechargeCashCountData",
				data : {dateYear: year},
				dataType : "json",
				success : function(data) {
					$("#rechargeCashCountContainer").highcharts( {
						chart : {
							type : 'line'
						},
						title : {
							text : year + '年充值提现金额数据折线图'
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
								text : '交易(笔)'
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