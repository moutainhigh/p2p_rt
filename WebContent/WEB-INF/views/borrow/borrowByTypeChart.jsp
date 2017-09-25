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
	<div id="borrowByType" style="min-width: 900px; height: 350px;"></div>
   	</div>	 
	<script type="text/javascript">
		var type = "${type}";
		var year = $("#dateYear").val();
		$(function() {
			$.ajax({
				type : "post",
				url : "summary/getBorrowByWhatData",
				data : {dateYear: year, type: type},
				dataType : "json",
				success : function(data) {
					$("#borrowByType").highcharts( {
						chart : {
							type : 'pie'
						},
						title : {
							text : year + '年成交金额同比增长柱状图'
						},
						credits : {
							enabled : false
						},
						plotOptions: {//显示数值
							pie: {
				                innerSize: 100
				            }
						},
						series : [data]
					});
				},
			});
		});
	</script>