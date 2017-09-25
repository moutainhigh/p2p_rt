<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-个人资料</title>
<link type="text/css" rel="stylesheet" href="${frontPath }/css/account.css" />
<script src="${frontPath }/js/city.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="/top.do"></jsp:include>
	<div class="center">
	<div class="top-k">
		<div class="wydkxq-nr">
		<input type="hidden" value="tit_4_left22" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
		<div class="setInfo fr">
					<c:set var="curNav" value="title3"></c:set>
           			 <%@include file="titleNav.jsp"%>
					<!-- 个人资料 -->
					<div class="tab_info" id="tab0">

						<form action="" name="form1"  id="unit" method="post">
                		<input type="hidden" name="id" id="id" value="${unit.id }">

							<div class="alerts">请填写你的单位相关信息</div>
							<table class="tableLs siteInfoWidth1">
								<tbody>
									<tr>
										<td class="ls">单位名称：</td>
										<td class="bl"><span class="leftspace"></span>
											<input type="text" value="${unit.companyName}" name="companyName" id="companyName" class="input">
		                   				    <span class="tip error" style="display:none;" id="realNameTip">
										</td>
									</tr>
									<tr>
										<td class="ls">单位性质：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="companyIndustry" id="companyIndustry">
		                                   		<option value="0" <c:if test="${not empty unit.companyIndustry and 0 eq unit.companyIndustry}">selected="selected"</c:if>>政府机关</option>
						              			<option value="1" <c:if test="${not empty unit.companyIndustry and 1 eq unit.companyIndustry}">selected="selected"</c:if>>国有企业</option>
						              			<option value="2" <c:if test="${not empty unit.companyIndustry and 2 eq unit.companyIndustry}">selected="selected"</c:if>>台（港，澳）资企业</option>
						              			<option value="3" <c:if test="${not empty unit.companyIndustry and 3 eq unit.companyIndustry}">selected="selected"</c:if>>外资企业</option>
						              			<option value="4" <c:if test="${not empty unit.companyIndustry and 4 eq unit.companyIndustry}">selected="selected"</c:if>>合资企业</option>
						              			<option value="5" <c:if test="${not empty unit.companyIndustry and 5 eq unit.companyIndustry}">selected="selected"</c:if>>个体户</option>
						              			<option value="6" <c:if test="${not empty unit.companyIndustry and 6 eq unit.companyIndustry}">selected="selected"</c:if>>事业性单位</option>
						              			<option value="7" <c:if test="${not empty unit.companyIndustry and 7 eq unit.companyIndustry}">selected="selected"</c:if>>私营企业</option>
	                         				</select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">单位行业：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="work" id="work">
		                                	<option value="0" <c:if test="${not empty unit.work and 0 eq unit.work}">selected="selected"</c:if>>农，林，牧，渔业</option>
						              			<option value="1" <c:if test="${not empty unit.work and 1 eq unit.work}">selected="selected"</c:if>>制造业</option>
						              			<option value="2" <c:if test="${not empty unit.work and 2 eq unit.work}">selected="selected"</c:if>>电子，燃气及水的生产和供应业</option>
						              			<option value="3" <c:if test="${not empty unit.work and 3 eq unit.work}">selected="selected"</c:if>>建筑业</option>
						              			<option value="4" <c:if test="${not empty unit.work and 4 eq unit.work}">selected="selected"</c:if>>交通运输，仓储和邮政业</option>
						              			<option value="5" <c:if test="${not empty unit.work and 5 eq unit.work}">selected="selected"</c:if>>信息传输，计算机服务和软件业</option>
						              			<option value="6" <c:if test="${not empty unit.work and 6 eq unit.work}">selected="selected"</c:if>>批发和零售业</option>
						              			<option value="7" <c:if test="${not empty unit.work and 7 eq unit.work}">selected="selected"</c:if>>金融业</option>
						              			<option value="8" <c:if test="${not empty unit.work and 8 eq unit.work}">selected="selected"</c:if>>地产业</option>
						              			<option value="9" <c:if test="${not empty unit.work and 9 eq unit.work}">selected="selected"</c:if>>采矿业</option>
						              			<option value="10" <c:if test="${not empty unit.work and 10 eq unit.work}">selected="selected"</c:if>>租凭和商务服务业</option>
						              			<option value="11" <c:if test="${not empty unit.work and 11 eq unit.work}">selected="selected"</c:if>>科学研究，技术服务和地质勘查业</option>
						              			<option value="12" <c:if test="${not empty unit.work and 12 eq unit.work}">selected="selected"</c:if>>水利，环境和公共设备管理业</option>
						              			<option value="13" <c:if test="${not empty unit.work and 13 eq unit.work}">selected="selected"</c:if>>居民服务和其他服务业</option>
						              			<option value="14" <c:if test="${not empty unit.work and 14 eq unit.work}">selected="selected"</c:if>>科学研究，技术服务和地质勘查业</option>
						              			<option value="15" <c:if test="${not empty unit.work and 15 eq unit.work}">selected="selected"</c:if>>教育</option>
						              			<option value="16" <c:if test="${not empty unit.work and 16 eq unit.work}">selected="selected"</c:if>>卫生，社会保障和社会福利业</option>
						              			<option value="17" <c:if test="${not empty unit.work and 17 eq unit.work}">selected="selected"</c:if>>文化，体育和娱乐业</option>
						              			<option value="18" <c:if test="${not empty unit.work and 18 eq unit.work}">selected="selected"</c:if>>公共管理和社会组织</option>
						              			<option value="19" <c:if test="${not empty unit.work and 19 eq unit.work}">selected="selected"</c:if>>国际组织</option>
	                         	 			</select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">工作级别：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="workGrade" id="workGrade" >
	                                   			<option value="0" <c:if test="${not empty unit.workGrade and 0 eq unit.workGrade}">selected="selected"</c:if>>普通员工</option>
						              			<option value="1" <c:if test="${not empty unit.workGrade and 1 eq unit.workGrade}">selected="selected"</c:if>>管理人员</option>
						              			<option value="2" <c:if test="${not empty unit.workGrade and 2 eq unit.workGrade}">selected="selected"</c:if>>股东</option>
						              			<option value="3" <c:if test="${not empty unit.workGrade and 3 eq unit.workGrade}">selected="selected"</c:if>>私营业主</option>
	                        				</select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">职 位：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="zhiwei" id="zhiwei" class="input08">
			                               		<option value="77" <c:if test="${not empty unit.zhiwei and 77 eq unit.zhiwei}">selected="selected"</c:if>>财务总监</option>
							              		<option value="78" <c:if test="${not empty unit.zhiwei and 78 eq unit.zhiwei}">selected="selected"</c:if>>财务主管</option>
							              		<option value="79" <c:if test="${not empty unit.zhiwei and 79 eq unit.zhiwei}">selected="selected"</c:if>>审计/税务</option>
							              		<option value="80" <c:if test="${not empty unit.zhiwei and 80 eq unit.zhiwei}">selected="selected"</c:if>>总账/成本</option>
							              		<option value="81" <c:if test="${not empty unit.zhiwei and 81 eq unit.zhiwei}">selected="selected"</c:if>>会计人员</option>
							              		<option value="82" <c:if test="${not empty unit.zhiwei and 82 eq unit.zhiwei}">selected="selected"</c:if>>人力资源总监</option>
							              		<option value="83" <c:if test="${not empty unit.zhiwei and 83 eq unit.zhiwei}">selected="selected"</c:if>>人力资源经理/主管</option>
							              		<option value="84" <c:if test="${not empty unit.zhiwei and 84 eq unit.zhiwei}">selected="selected"</c:if>>招聘经理/主管</option>
							              		<option value="85" <c:if test="${not empty unit.zhiwei and 85 eq unit.zhiwei}">selected="selected"</c:if>>薪资福利经理/主管</option>
							              		<option value="86" <c:if test="${not empty unit.zhiwei and 86 eq unit.zhiwei}">selected="selected"</c:if>>财务经理</option>
							              		<option value="87" <c:if test="${not empty unit.zhiwei and 87 eq unit.zhiwei}">selected="selected"</c:if>>培训经理/主管</option>
							               		<option value="88" <c:if test="${not empty unit.zhiwei and 88 eq unit.zhiwei}">selected="selected"</c:if>>人事专员/助理</option>
							               		<option value="89" <c:if test="${not empty unit.zhiwei and 89 eq unit.zhiwei}">selected="selected"</c:if>>行长/副行长</option>
							               		<option value="90" <c:if test="${not empty unit.zhiwei and 90 eq unit.zhiwei}">selected="selected"</c:if>>信贷/信用管理</option>
							               		<option value="91" <c:if test="${not empty unit.zhiwei and 91 eq unit.zhiwei}">selected="selected"</c:if>>资产管理/评估</option>
							               		<option value="92" <c:if test="${not empty unit.zhiwei and 92 eq unit.zhiwei}">selected="selected"</c:if>>投融资项目/基金</option>
							               		<option value="93" <c:if test="${not empty unit.zhiwei and 93 eq unit.zhiwei}">selected="selected"</c:if>>外汇管理/国际结算</option>
							               		<option value="94" <c:if test="${not empty unit.zhiwei and 94 eq unit.zhiwei}">selected="selected"</c:if>>风险管理</option>
							               		<option value="95" <c:if test="${not empty unit.zhiwei and 95 eq unit.zhiwei}">selected="selected"</c:if>>证券/期货</option>
							               		<option value="96" <c:if test="${not empty unit.zhiwei and 96 eq unit.zhiwei}">selected="selected"</c:if>>客户经理/主管</option>
							               		<option value="97" <c:if test="${not empty unit.zhiwei and 97 eq unit.zhiwei}">selected="selected"</c:if>>核保/理赔</option>
							               		<option value="98" <c:if test="${not empty unit.zhiwei and 98 eq unit.zhiwei}">selected="selected"</c:if>>保险代理人/内勤</option>
							               		<option value="99" <c:if test="${not empty unit.zhiwei and 99 eq unit.zhiwei}">selected="selected"</c:if>>银行专员/柜台</option>
							               		<option value="100" <c:if test="${not empty unit.zhiwei and 100 eq unit.zhiwei}">selected="selected"</c:if>>精算师</option>
							               		<option value="101" <c:if test="${not empty unit.zhiwei and 101 eq unit.zhiwei}">selected="selected"</c:if>>编辑/记者/文案</option>
							               		<option value="102" <c:if test="${not empty unit.zhiwei and 102 eq unit.zhiwei}">selected="selected"</c:if>>版面设计</option>
							               		<option value="103" <c:if test="${not empty unit.zhiwei and 103 eq unit.zhiwei}">selected="selected"</c:if>>平面设计</option>
							               		<option value="104" <c:if test="${not empty unit.zhiwei and 104 eq unit.zhiwei}">selected="selected"</c:if>>装潢/陈列设计</option>
							               		<option value="105" <c:if test="${not empty unit.zhiwei and 105 eq unit.zhiwei}">selected="selected"</c:if>>纺织/服装设计</option>
							               		<option value="106" <c:if test="${not empty unit.zhiwei and 106 eq unit.zhiwei}">selected="selected"</c:if>>工艺品/珠宝设计</option>
							               		<option value="107" <c:if test="${not empty unit.zhiwei and 107 eq unit.zhiwei}">selected="selected"</c:if>>经理</option>
							               		<option value="108" <c:if test="${not empty unit.zhiwei and 108 eq unit.zhiwei}">selected="selected"</c:if>>主任</option>
							               		<option value="109" <c:if test="${not empty unit.zhiwei and 109 eq unit.zhiwei}">selected="selected"</c:if>>生物工程</option>
							               		<option value="110" <c:if test="${not empty unit.zhiwei and 110 eq unit.zhiwei}">selected="selected"</c:if>>药物注册</option>
							               		<option value="111" <c:if test="${not empty unit.zhiwei and 111 eq unit.zhiwei}">selected="selected"</c:if>>临床研究/协调</option>
							               		<option value="112" <c:if test="${not empty unit.zhiwei and 112 eq unit.zhiwei}">selected="selected"</c:if>>药物研发/分析工程师</option>
							               		<option value="113" <c:if test="${not empty unit.zhiwei and 113 eq unit.zhiwei}">selected="selected"</c:if>>化学工程师</option>
							               		<option value="114" <c:if test="${not empty unit.zhiwei and 114 eq unit.zhiwei}">selected="selected"</c:if>>环保工程师</option>
							               		<option value="115" <c:if test="${not empty unit.zhiwei and 115 eq unit.zhiwei}">selected="selected"</c:if>>化工人员</option>
							               		<option value="116" <c:if test="${not empty unit.zhiwei and 116 eq unit.zhiwei}">selected="selected"</c:if>>结构/建筑工程师</option>
							               		<option value="117" <c:if test="${not empty unit.zhiwei and 117 eq unit.zhiwei}">selected="selected"</c:if>>土建工程师/建造师</option>
							               		<option value="118" <c:if test="${not empty unit.zhiwei and 118 eq unit.zhiwei}">selected="selected"</c:if>>机电/给排水/暖通工程师</option>
							               		<option value="119" <c:if test="${not empty unit.zhiwei and 119 eq unit.zhiwei}">selected="selected"</c:if>>工程造价师/预决算</option>
							               		<option value="120" <c:if test="${not empty unit.zhiwei and 120 eq unit.zhiwei}">selected="selected"</c:if>>监理/安全工程师</option>
							               		<option value="121" <c:if test="${not empty unit.zhiwei and 121 eq unit.zhiwei}">selected="selected"</c:if>>房地产开发/策划</option>
							               		<option value="122" <c:if test="${not empty unit.zhiwei and 122 eq unit.zhiwei}">selected="selected"</c:if>>园林景观/城市规划师</option>
							               		<option value="123" <c:if test="${not empty unit.zhiwei and 123 eq unit.zhiwei}">selected="selected"</c:if>>物业管理</option>
							               		<option value="124" <c:if test="${not empty unit.zhiwei and 124 eq unit.zhiwei}">selected="selected"</c:if>>房地产评估/中介/交易</option>
							               		<option value="125" <c:if test="${not empty unit.zhiwei and 125 eq unit.zhiwei}">selected="selected"</c:if>>电子/电路工程师</option>
							               		<option value="126" <c:if test="${not empty unit.zhiwei and 126 eq unit.zhiwei}">selected="selected"</c:if>>电气工程师</option>
							               		<option value="127" <c:if test="${not empty unit.zhiwei and 127 eq unit.zhiwei}">selected="selected"</c:if>>半导体工程师</option>
							               		<option value="128" <c:if test="${not empty unit.zhiwei and 128 eq unit.zhiwei}">selected="selected"</c:if>>测试工程师</option>
							               		<option value="129" <c:if test="${not empty unit.zhiwei and 129 eq unit.zhiwei}">selected="selected"</c:if>>自动化工程师</option>
							               		<option value="130" <c:if test="${not empty unit.zhiwei and 130 eq unit.zhiwei}">selected="selected"</c:if>>光学工程师</option>
							               		<option value="131" <c:if test="${not empty unit.zhiwei and 131 eq unit.zhiwei}">selected="selected"</c:if> >电力工程师</option>
							               		<option value="132" <c:if test="${not empty unit.zhiwei and 132 eq unit.zhiwei}">selected="selected"</c:if>>水利/水电工程师</option>
							               		<option value="133" <c:if test="${not empty unit.zhiwei and 133 eq unit.zhiwei}">selected="selected"</c:if>>单片机/DLC/DSP/底层软件开发</option>
							               		<option value="134" <c:if test="${not empty unit.zhiwei and 134 eq unit.zhiwei}">selected="selected"</c:if>>其他工程师</option>
							               		<option value="135" <c:if test="${not empty unit.zhiwei and 135 eq unit.zhiwei}">selected="selected"</c:if>>技术人员</option>
							               		<option value="136" <c:if test="${not empty unit.zhiwei and 136 eq unit.zhiwei}">selected="selected"</c:if>>助理</option>
							               		<option value="137" <c:if test="${not empty unit.zhiwei and 137 eq unit.zhiwei}">selected="selected"</c:if>>厂长/副厂长</option>
							               		<option value="138" <c:if test="${not empty unit.zhiwei and 138 eq unit.zhiwei}">selected="selected"</c:if>>生产经理/车间主任</option>
							               		<option value="139" <c:if test="${not empty unit.zhiwei and 139 eq unit.zhiwei}">selected="selected"</c:if>>产品开发</option>
							               		<option value="140" <c:if test="${not empty unit.zhiwei and 140 eq unit.zhiwei}">selected="selected"</c:if>>工业/产品设计</option>
							               		<option value="141" <c:if test="${not empty unit.zhiwei and 141 eq unit.zhiwei}">selected="selected"</c:if>>生产/计划/调度</option>
							               		<option value="142" <c:if test="${not empty unit.zhiwei and 142 eq unit.zhiwei}">selected="selected"</c:if>>质量管理</option>
							               		<option value="143" <c:if test="${not empty unit.zhiwei and 143 eq unit.zhiwei}">selected="selected"</c:if>>项目管理</option>
							               		<option value="144" <c:if test="${not empty unit.zhiwei and 144 eq unit.zhiwei}">selected="selected"</c:if>>工程设备管理</option>
							               		<option value="145" <c:if test="${not empty unit.zhiwei and 145 eq unit.zhiwei}">selected="selected"</c:if>>安全/健康/环境管理</option>
							               		<option value="146" <c:if test="${not empty unit.zhiwei and 146 eq unit.zhiwei}">selected="selected"</c:if>>生产工艺/技术</option>
							               		<option value="147" <c:if test="${not empty unit.zhiwei and 147 eq unit.zhiwei}">selected="selected"</c:if>>技工</option>
							               		<option value="148" <c:if test="${not empty unit.zhiwei and 148 eq unit.zhiwei}">selected="selected"</c:if>>机械工程师</option>
							               		<option value="149" <c:if test="${not empty unit.zhiwei and 149 eq unit.zhiwei}">selected="selected"</c:if>>维修工程师</option>
							               		<option value="150" <c:if test="${not empty unit.zhiwei and 150 eq unit.zhiwei}">selected="selected"</c:if>>机械设计/制图</option>
							               		<option value="151" <c:if test="${not empty unit.zhiwei and 151 eq unit.zhiwei}">selected="selected"</c:if>>机电工程师</option>
							               		<option value="152" <c:if test="${not empty unit.zhiwei and 152 eq unit.zhiwei}">selected="selected"</c:if>>模具工程师</option>
							               		<option value="153" <c:if test="${not empty unit.zhiwei and 153 eq unit.zhiwei}">selected="selected"</c:if>>精密机械/仪器仪表</option>
							               		<option value="154" <c:if test="${not empty unit.zhiwei and 154 eq unit.zhiwei}">selected="selected"</c:if>>船舶工程师</option>
							               		<option value="155" <c:if test="${not empty unit.zhiwei and 155 eq unit.zhiwei}">selected="selected"</c:if>>冲压/注塑/焊接</option>
							               		<option value="156" <c:if test="${not empty unit.zhiwei and 156 eq unit.zhiwei}">selected="selected"</c:if>>餐饮/娱乐管理</option>
							               		<option value="157" <c:if test="${not empty unit.zhiwei and 157 eq unit.zhiwei}">selected="selected"</c:if>>酒店/宾馆管理</option>
							               		<option value="158" <c:if test="${not empty unit.zhiwei and 158 eq unit.zhiwei}">selected="selected"</c:if>>服务人员/领班</option>
							               		<option value="159" <c:if test="${not empty unit.zhiwei and 159 eq unit.zhiwei}">selected="selected"</c:if>>导游/旅行顾问/票务</option>
							               		<option value="160" <c:if test="${not empty unit.zhiwei and 160 eq unit.zhiwei}">selected="selected"</c:if>>营业员/收银员</option>
							               		<option value="161" <c:if test="${not empty unit.zhiwei and 161 eq unit.zhiwei}">selected="selected"</c:if>>技术总监/首席技术执行官</option>
							               		<option value="162" <c:if test="${not empty unit.zhiwei and 162 eq unit.zhiwei}">selected="selected"</c:if>>技术经理</option>
							               		<option value="163" <c:if test="${not empty unit.zhiwei and 163 eq unit.zhiwei}">selected="selected"</c:if>>项目经理</option>
							               		<option value="164" <c:if test="${not empty unit.zhiwei and 164 eq unit.zhiwei}">selected="selected"</c:if>>系统分析工程师</option>
							               		<option value="165" <c:if test="${not empty unit.zhiwei and 165 eq unit.zhiwei}">selected="selected"</c:if>>ERP应用顾问</option>
							               		<option value="166" <c:if test="${not empty unit.zhiwei and 166 eq unit.zhiwei}">selected="selected"</c:if>>数据库工程师/管理员</option>
							               		<option value="167" <c:if test="${not empty unit.zhiwei and 167 eq unit.zhiwei}">selected="selected"</c:if>>软件开发工程师</option>
							               		<option value="168" <c:if test="${not empty unit.zhiwei and 168 eq unit.zhiwei}">selected="selected"</c:if>>硬件开发工程师</option>
							               		<option value="169" <c:if test="${not empty unit.zhiwei and 169 eq unit.zhiwei}">selected="selected"</c:if>>信息支持/安全维护</option>
							               		<option value="170" <c:if test="${not empty unit.zhiwei and 170 eq unit.zhiwei}">selected="selected"</c:if>>网页设计/编辑</option>
							               		<option value="171" <c:if test="${not empty unit.zhiwei and 171 eq unit.zhiwei}">selected="selected"</c:if>>通讯工程师</option>
							               		<option value="172" <c:if test="${not empty unit.zhiwei and 172 eq unit.zhiwei}">selected="selected"</c:if>>多媒体/游戏开发</option>
							               		<option value="173" <c:if test="${not empty unit.zhiwei and 173 eq unit.zhiwei}">selected="selected"</c:if>>采购经理/主管</option>
							               		<option value="174" <c:if test="${not empty unit.zhiwei and 174 eq unit.zhiwei}">selected="selected"</c:if>>采购专员/助理</option>
							               		<option value="175" <c:if test="${not empty unit.zhiwei and 175 eq unit.zhiwei}">selected="selected"</c:if>>贸易经理/主管</option>
							               		<option value="176" <c:if test="${not empty unit.zhiwei and 176 eq unit.zhiwei}">selected="selected"</c:if>>市场咨询/调研</option>
							               		<option value="177" <c:if test="${not empty unit.zhiwei and 177 eq unit.zhiwei}">selected="selected"</c:if>>创意与策划</option>
							               		<option value="178" <c:if test="${not empty unit.zhiwei and 178 eq unit.zhiwei}">selected="selected"</c:if>>客服经理/主管</option>
							               		<option value="179" <c:if test="${not empty unit.zhiwei and 179 eq unit.zhiwei}">selected="selected"</c:if>>客服专员/助理</option>
							               		<option value="180" <c:if test="${not empty unit.zhiwei and 180 eq unit.zhiwei}">selected="selected"</c:if>>客户关系管理</option>
							               		<option value="181" <c:if test="${not empty unit.zhiwei and 181 eq unit.zhiwei}">selected="selected"</c:if>>客户咨询/热线支持</option>
							               		<option value="182" <c:if test="${not empty unit.zhiwei and 182 eq unit.zhiwei}">selected="selected"</c:if>>销售总监</option>
							               		<option value="183" <c:if test="${not empty unit.zhiwei and 183 eq unit.zhiwei}">selected="selected"</c:if>>销售经理</option>
							               		<option value="184" <c:if test="${not empty unit.zhiwei and 184 eq unit.zhiwei}">selected="selected"</c:if>>销售经理</option>
							               		<option value="185" <c:if test="${not empty unit.zhiwei and 185 eq unit.zhiwei}">selected="selected"</c:if>>业务拓展经理</option>
							               		<option value="186" <c:if test="${not empty unit.zhiwei and 186 eq unit.zhiwei}">selected="selected"</c:if>>销售主管/主任</option>
							               		<option value="187" <c:if test="${not empty unit.zhiwei and 187 eq unit.zhiwei}">selected="selected"</c:if>>售前/售后支持</option>
							               		<option value="188" <c:if test="${not empty unit.zhiwei and 188 eq unit.zhiwei}">selected="selected"</c:if>>销售工程师</option>
							               		<option value="189" <c:if test="${not empty unit.zhiwei and 189 eq unit.zhiwei}">selected="selected"</c:if>>销售代表</option>
							               		<option value="190" <c:if test="${not empty unit.zhiwei and 190 eq unit.zhiwei}">selected="selected"</c:if>>医药代表</option>
							               		<option value="191" <c:if test="${not empty unit.zhiwei and 191 eq unit.zhiwei}">selected="selected"</c:if>>销售助理</option>
							               		<option value="192" <c:if test="${not empty unit.zhiwei and 192 eq unit.zhiwei}">selected="selected"</c:if>>专业顾问</option>
							               		<option value="193" <c:if test="${not empty unit.zhiwei and 193 eq unit.zhiwei}">selected="selected"</c:if>>咨询师</option>
							               		<option value="194" <c:if test="${not empty unit.zhiwei and 194 eq unit.zhiwei}">selected="selected"</c:if>>调研员</option>
							               		<option value="195" <c:if test="${not empty unit.zhiwei and 195 eq unit.zhiwei}">selected="selected"</c:if>>英语翻译</option>
							               		<option value="196" <c:if test="${not empty unit.zhiwei and 196 eq unit.zhiwei}">selected="selected"</c:if>>日语翻译</option>
							               		<option value="197" <c:if test="${not empty unit.zhiwei and 197 eq unit.zhiwei}">selected="selected"</c:if>>德语翻译</option>
							               		<option value="198" <c:if test="${not empty unit.zhiwei and 198 eq unit.zhiwei}">selected="selected"</c:if>>法语翻译</option>
							               		<option value="199" <c:if test="${not empty unit.zhiwei and 199 eq unit.zhiwei}">selected="selected"</c:if>>俄语翻译</option>
							               		<option value="200" <c:if test="${not empty unit.zhiwei and 200 eq unit.zhiwei}">selected="selected"</c:if>>西班牙语翻译</option>
							               		<option value="201" <c:if test="${not empty unit.zhiwei and 201 eq unit.zhiwei}">selected="selected"</c:if>>其他翻译</option>
							               		<option value="202" <c:if test="${not empty unit.zhiwei and 202 eq unit.zhiwei}">selected="selected"</c:if>>董事/总经理/总裁</option>
							               		<option value="203" <c:if test="${not empty unit.zhiwei and 203 eq unit.zhiwei}">selected="selected"</c:if>>首席执行官/运营官</option>
							               		<option value="204" <c:if test="${not empty unit.zhiwei and 204 eq unit.zhiwei}">selected="selected"</c:if>>营运总监/经理</option>
							               		<option value="205" <c:if test="${not empty unit.zhiwei and 205 eq unit.zhiwei}">selected="selected"</c:if>>合伙人</option>
							               		<option value="206" <c:if test="${not empty unit.zhiwei and 206 eq unit.zhiwei}">selected="selected"</c:if>>行政总监</option>
							               		<option value="207" <c:if test="${not empty unit.zhiwei and 207 eq unit.zhiwei}">selected="selected"</c:if> >行政经理</option>
							               		<option value="208" <c:if test="${not empty unit.zhiwei and 208 eq unit.zhiwei}">selected="selected"</c:if>>主管/主任</option>
							               		<option value="209" <c:if test="${not empty unit.zhiwei and 209 eq unit.zhiwei}">selected="selected"</c:if>>助理/秘书</option>
							               		<option value="210" <c:if test="${not empty unit.zhiwei and 210 eq unit.zhiwei}">selected="selected"</c:if>>前台/文员</option>
							               		<option value="211" <c:if test="${not empty unit.zhiwei and 211 eq unit.zhiwei}">selected="selected"</c:if>>物流经理/主管</option>
							               		<option value="212" <c:if test="${not empty unit.zhiwei and 212 eq unit.zhiwei}">selected="selected"</c:if>>物流专员/助理</option>
							               		<option value="213" <c:if test="${not empty unit.zhiwei and 213 eq unit.zhiwei}">selected="selected"</c:if>>物料/仓库管理</option>
							               		<option value="214" <c:if test="${not empty unit.zhiwei and 214 eq unit.zhiwei}">selected="selected"</c:if>>供应链</option>
							               		<option value="215" <c:if test="${not empty unit.zhiwei and 215 eq unit.zhiwei}">selected="selected"</c:if>>供应链</option>
							               		<option value="216" <c:if test="${not empty unit.zhiwei and 216 eq unit.zhiwei}">selected="selected"</c:if>>单证/报关员</option>
							               		<option value="217" <c:if test="${not empty unit.zhiwei and 217 eq unit.zhiwei}">selected="selected"</c:if>>律师/法务</option>
							               		<option value="218" <c:if test="${not empty unit.zhiwei and 218 eq unit.zhiwei}">selected="selected"</c:if>>能源/动力</option>
							               		<option value="219" <c:if test="${not empty unit.zhiwei and 219 eq unit.zhiwei}">selected="selected"</c:if>>医疗/护理</option>
							               		<option value="220" <c:if test="${not empty unit.zhiwei and 220 eq unit.zhiwei}">selected="selected"</c:if>>科研人员</option>
							               		<option value="221" <c:if test="${not empty unit.zhiwei and 221 eq unit.zhiwei}">selected="selected"</c:if>>培训/教育</option>
							               		<option value="222" <c:if test="${not empty unit.zhiwei and 222 eq unit.zhiwei}">selected="selected"</c:if>>储备干部/培训生</option>
							               		<option value="223" <c:if test="${not empty unit.zhiwei and 223 eq unit.zhiwei}">selected="selected"</c:if>>在校学生</option>
							               		<option value="224" <c:if test="${not empty unit.zhiwei and 224 eq unit.zhiwei}">selected="selected"</c:if>>其他职位</option>
	                                		</select> 
		                    				<span class="tip error" style="display:none;" id="realNameTip">
										</td>
									</tr>
									<tr>
										<td class="ls">服务时间：</td>
										<td><span class="leftspace"></span>
											<input  type="text" class="input" name="yearString" id="uninBeginTime" onClick="WdatePicker({isShowClear:false,readOnly:true})" readonly="readonly" value="<fmt:formatDate value="${unit.serveTime}" pattern="yyyy-MM-dd"/>"> 
				                			到
			                               	<input class="input" type="text"  name="yearString1" id="uninEndTime" onclick="WdatePicker({isShowClear:false,readOnly:true});" readonly="readonly" value="<fmt:formatDate value="${unit.toServeTime}" pattern="yyyy-MM-dd"/>">
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">工作年限：</td>
										<td class="bl"><span class="leftspace"></span>
											<select name="workYear" id="workYear" >
			                                    <option value="0" <c:if test="${not empty unit.workYear and 0 eq unit.workYear}">selected="selected"</c:if>>一年以内</option>
						              			<option value="1" <c:if test="${not empty unit.workYear and 1 eq unit.workYear}">selected="selected"</c:if>>一年以上</option>
						              			<option value="2" <c:if test="${not empty unit.workYear and 2 eq unit.workYear}">selected="selected"</c:if>>两年以上</option>
						              			<option value="3" <c:if test="${not empty unit.workYear and 3 eq unit.workYear}">selected="selected"</c:if>>三年以上</option>
						              			<option value="4" <c:if test="${not empty unit.workYear and 4 eq unit.workYear}">selected="selected"</c:if>>四年年以上</option>
						              			<option value="5" <c:if test="${not empty unit.workYear and 5 eq unit.workYear}">selected="selected"</c:if>>五年以上</option>
						              			<option value="6" <c:if test="${not empty unit.workYear and 6 eq unit.workYear}">selected="selected"</c:if>>六年以上</option>
			                                </select>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">工作电话：</td>
										<td class="bl"><span class="leftspace"></span>
											<input class="input" type="text" value="${unit.workTel}" onkeyup="value=value.replace(/[^0-9]/g,'')" name="workTel" id="workTel" />
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">公司地址：</td>
										<td class="bl"><span class="leftspace"></span>
											<input class="input" type="text" value="${unit.companyAdd}" name="companyAdd" id="companyAdd" />
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">公司网站：</td>
										<td class="bl"><span class="leftspace"></span>
											<input class="input" type="text" value="${unit.companyWebsite}" name="companyWebsite" id="companyWebsite"/>
		                    				<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td class="ls">备注说明：</td>
										<td class="bl"><span class="leftspace"></span>
											<textarea style="width:500px; height:100px; margin-top:10px;" name="remark" id="remark">${unit.remark}</textarea>
		                   					<span class="tip error" style="display:none;" id="realNameTip"></span>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span class="wdzh-buttu" onclick="subForm('unit');" style="cursor: pointer; margin-left: 150px; margin-top: 20px; margin-bottom: 20px;">确认</span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="alerts">* 温馨提示：我们将对所有的信息进行保密</div>
						</form>
					</div>
					
				</div>
</div>
    
    </div>
  </div>	
 <!--/bottom-->
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
function subForm(obj) {
	var url = "";
		if ($("#companyName").val() == "") {
			alert("请输入单位名称");
			$("#companyName").focus();
			return;
		}

		if ($("#uninBeginTime").val() == "") {
			alert("请输入服务时间");
			$("#uninBeginTime").focus();
			return;
		}

		if ($("#uninEndTime").val() == "") {
			alert("请输入服务时间");
			$("#uninEndTime").focus();
			return;
		}

		//var workTelPattern = /(^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$)|(^(([0\+]\d{2,3})?(0\d{2,3}))(\d{7,8})((\d{3,}))?$)|(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		if ($("#workTel").val() == "") {
			alert("请输入工作电话");
			$("#workTel").focus();
			return;
		} 
		/* else if (!workTelPattern.test($("#workTel").val())) {
			alert("请输入合法电话");
			$("#workTel").focus();
			return;
		} */

		if ($("#companyAdd").val() == "") {
			alert("请输入公司地址");
			$("#companyAdd").focus();
			return;
		}
		url = "${path}/basics/savaUnit.do";
		 $.ajax({
	          dataType: 'json',
	            url:url,
	            data:$("#"+obj).serialize(),
	            type: 'POST',
	            success: function(data) {
	            	if(data.code == '100'){
	            		alertc('成功');
	            	}
	            	if(data.code == '101'){
	            		alertc('失败');
	            	}
	            },
	            error: function() {
	            		 alertc("失败！！！");
	            }
	        });   
	}

</script>
</html>
