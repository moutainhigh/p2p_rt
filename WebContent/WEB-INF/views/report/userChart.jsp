<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="resources/js/highcharts.js"></script>

<form  id="pagerForm" onsubmit="return navTabSearch(this);"
	action="report/${PRE_PATH_VIEW }goWhichChart?type=${type }" method="post"><!-- report/gotoUserChart -->
	
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
	<div id="usercontainer" style="min-width: 1000px; height: 350px "></div>
   	</div>	 
	<script type="text/javascript">
		var days = '${days}';
		var type = '${type}';
		
		var beginTime=$("#beginTime").val();
		var endTime = $("#endTime").val();
			$(function() {
				
				$.ajax({
					type : "post",
					url : 'report/getUserData' ,
					data :{beginTime:beginTime,endTime:endTime},
					dataType : "json",
					success : function(data) {

						$('#usercontainer').highcharts(
								{
									chart : {
										type : 'line',
										//type : 'column',
										//marginRight : 130,
										//marginBottom : 25
									},

									title : {
										text : days+'天内新增用户详情',
									},

									/* legend : {
										layout : 'vertical',
										align : 'right',
										verticalAlign : 'top',
										x : -10,
										y : 100,
										borderWidth : 0
									},  */

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
											text : '人员数量(个)'
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
										name : 'VIP认证数',
										data : eval(data.countVip),

									/* dataLabels: {//在柱子上显示值
									    enabled: true,
									    rotation: -90,
									    color: '#FFFFFF',
									    align: 'right',
									    x: 4,
									    y: 10,
									    style: {
									        fontSize: '13px',
									        fontFamily: 'Verdana, sans-serif',
									        textShadow: '0 0 3px black'
									    }
									} */

									}, {
										name : '实名认证数',
										data : eval(data.countName)
									}, {
										name : '手机认证数',
										data : eval(data.countTel)
									},{
										name : '注册数',
										data : eval(data.countReg)
									}, 
									]

								});
					},
				});
			});
		</script>
	</div>