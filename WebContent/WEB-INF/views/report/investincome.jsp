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
	<div id="investcontainer" style="min-width: 1000px; height: 350px "></div>
   	</div>	 
		<script type="text/javascript">
		
		var days = '${days}';
		var type = '${type}';
		
			$(function() {
				$.ajax({
					type : "post",
					url : 'report/getInvestIncomeData' ,
					dataType : "json",
					success : function(data) {

						$('#investcontainer').highcharts(
								{
									chart : {
										type : 'line',
									},

									title : {
										text : '  ',
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
											text : '金额(元)'
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
										name : '利息',
										data : eval(data.interests),

									}, {
										name : '投标奖励',
										data : eval(data.tenderRewards)
									}, {
										name : '线下充值奖励',
										data : eval(data.rechargeOfflineRewards)
									},{
										name : '续投奖励',
										data : eval(data.continueRewards)
									}, 
									{
										name : '推荐奖励',
										data : eval(data.recommendRewards)
									},{
										name : '注册奖励',
										data : eval(data.registerRewards)
									}, 
									]

								});

					},
				});
			});
		</script>
	</div>