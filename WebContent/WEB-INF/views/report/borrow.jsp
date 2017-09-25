<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="resources/js/highcharts.js"></script>

<form  id="pagerForm" onsubmit="return navTabSearch(this);"
	action="report/${PRE_PATH_VIEW }goWhichChart?type=${type }" method="post">
	
<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>起始时间: 
							<input type="text" name="beginTime" id="beginTime" value="${params.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}"/>
					</td>
					<td>截止时间: 
						<input type="text" name="endTime" id="endTime" value="${params.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"  maxDate="{%y}-%M-{%d}"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
	<div id="borrowcontainer" style="min-width: 1000px; height: 350px "></div>
   	</div>	 
		<script type="text/javascript">
		
		var days = '${days}';
		var type = '${type}';
		
			$(function() {
				$.ajax({
					type : "post",
					url : 'report/getBorrowData' ,
					dataType : "json",
					success : function(data) {

						$('#borrowcontainer').highcharts(
								{
									chart : {
										type : 'line',
									},

									title : {
										text : '借款统计 ',
									},

									credits : {
										enabled : false,
									},

									tooltip : {
										formatter : function() {
											return '<b>' + this.series.name
													+ '</b><br/>' + this.x
													+ ': ' + this.y;
										}
									},

									xAxis : {
										categories : data.dates
									},

									yAxis : {
										title : {
											text : '数量(个)/金额(元)'
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
									        },
									        //enableMouseTracking: false
									    }
									},

									series : [ {
										name : '发布数量',
										data : eval(data.publishCounts),

									}, {
										name : '发布总额',
										data : eval(data.publishAmounts)
									}, {
										name : '还款数',
										data : eval(data.repayedCounts)
									},{
										name : '还款总额',
										data : eval(data.repayedAmounts)
									}, 
									{
										name : '投标数',
										data : eval(data.tenderCounts),

									}, {
										name : '投标总额',
										data : eval(data.tenderAmounts)
									}, {
										name : '有效投标数',
										data : eval(data.effeTenderCounts)
									},{
										name : '有效投标总额',
										data : eval(data.effeTenderAmounts)
									}, 
									]

								});

					},
				});
			});
		</script>
	</div>