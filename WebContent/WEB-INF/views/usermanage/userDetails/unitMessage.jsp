<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type="text/javascript" src="../../js/user/city.js"></script>
</head>
<body>
	<div class="pageContent">
			<form id="frm" method="post" action="userDetail/${PRE_PATH_EDIT}saveUnitMessage" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="userId" id="userId" value="${userId}"> 
				<div class="tabsContent" style="height: 390px;">
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">公司名称:</label><input class="required"  type="text" name="companyName"  value="${unitMessage.companyName }" /></p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">公司性质:</label>
					<select name="companyIndustry" id="companyIndustry" style="width:auto">
                        <option value="0" <c:if test="${not empty unitMessage.companyIndustry and 0 eq unitMessage.companyIndustry}">selected="selected"</c:if>>政府机关</option>
              			<option value="1" <c:if test="${not empty unitMessage.companyIndustry and 1 eq unitMessage.companyIndustry}">selected="selected"</c:if>>国有企业</option>
              			<option value="2" <c:if test="${not empty unitMessage.companyIndustry and 2 eq unitMessage.companyIndustry}">selected="selected"</c:if>>台（港，澳）资企业</option>
              			<option value="3" <c:if test="${not empty unitMessage.companyIndustry and 3 eq unitMessage.companyIndustry}">selected="selected"</c:if>>外资企业</option>
              			<option value="4" <c:if test="${not empty unitMessage.companyIndustry and 4 eq unitMessage.companyIndustry}">selected="selected"</c:if>>合资企业</option>
              			<option value="5" <c:if test="${not empty unitMessage.companyIndustry and 5 eq unitMessage.companyIndustry}">selected="selected"</c:if>>个体户</option>
              			<option value="6" <c:if test="${not empty unitMessage.companyIndustry and 6 eq unitMessage.companyIndustry}">selected="selected"</c:if>>事业性单位</option>
              			<option value="7" <c:if test="${not empty unitMessage.companyIndustry and 7 eq unitMessage.companyIndustry}">selected="selected"</c:if>>私营企业</option>
	                </select>
	                 
				</p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">公司行业:</label>
					 <select name="work" id="work" style="width:auto">
                        <option value="0" <c:if test="${not empty unitMessage.work and 0 eq unitMessage.work}">selected="selected"</c:if>>农，林，牧，渔业</option>
              			<option value="1" <c:if test="${not empty unitMessage.work and 1 eq unitMessage.work}">selected="selected"</c:if>>制造业</option>
              			<option value="2" <c:if test="${not empty unitMessage.work and 2 eq unitMessage.work}">selected="selected"</c:if>>电子，燃气及水的生产和供应业</option>
              			<option value="3" <c:if test="${not empty unitMessage.work and 3 eq unitMessage.work}">selected="selected"</c:if>>建筑业</option>
              			<option value="4" <c:if test="${not empty unitMessage.work and 4 eq unitMessage.work}">selected="selected"</c:if>>交通运输，仓储和邮政业</option>
              			<option value="5" <c:if test="${not empty unitMessage.work and 5 eq unitMessage.work}">selected="selected"</c:if>>信息传输，计算机服务和软件业</option>
              			<option value="6" <c:if test="${not empty unitMessage.work and 6 eq unitMessage.work}">selected="selected"</c:if>>批发和零售业</option>
              			<option value="7" <c:if test="${not empty unitMessage.work and 7 eq unitMessage.work}">selected="selected"</c:if>>金融业</option>
              			<option value="8" <c:if test="${not empty unitMessage.work and 8 eq unitMessage.work}">selected="selected"</c:if>>地产业</option>
              			<option value="9" <c:if test="${not empty unitMessage.work and 9 eq unitMessage.work}">selected="selected"</c:if>>采矿业</option>
              			<option value="10" <c:if test="${not empty unitMessage.work and 10 eq unitMessage.work}">selected="selected"</c:if>>租凭和商务服务业</option>
              			<option value="11" <c:if test="${not empty unitMessage.work and 11 eq unitMessage.work}">selected="selected"</c:if>>科学研究，技术服务和地质勘查业</option>
              			<option value="12" <c:if test="${not empty unitMessage.work and 12 eq unitMessage.work}">selected="selected"</c:if>>水利，环境和公共设备管理业</option>
              			<option value="13" <c:if test="${not empty unitMessage.work and 13 eq unitMessage.work}">selected="selected"</c:if>>居民服务和其他服务业</option>
              			<option value="14" <c:if test="${not empty unitMessage.work and 14 eq unitMessage.work}">selected="selected"</c:if>>科学研究，技术服务和地质勘查业</option>
              			<option value="15" <c:if test="${not empty unitMessage.work and 15 eq unitMessage.work}">selected="selected"</c:if>>教育</option>
              			<option value="16" <c:if test="${not empty unitMessage.work and 16 eq unitMessage.work}">selected="selected"</c:if>>卫生，社会保障和社会福利业</option>
              			<option value="17" <c:if test="${not empty unitMessage.work and 17 eq unitMessage.work}">selected="selected"</c:if>>文化，体育和娱乐业</option>
              			<option value="18" <c:if test="${not empty unitMessage.work and 18 eq unitMessage.work}">selected="selected"</c:if>>公共管理和社会组织</option>
              			<option value="19" <c:if test="${not empty unitMessage.work and 19 eq unitMessage.work}">selected="selected"</c:if>>国际组织</option>
                     </select>
				</p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">工作级别</label>
					<select name="workGrade" id="workGrade" style="width:auto">
                        <option value="0" <c:if test="${not empty unitMessage.workGrade and 0 eq unitMessage.workGrade}">selected="selected"</c:if>>普通员工</option>
              			<option value="1" <c:if test="${not empty unitMessage.workGrade and 1 eq unitMessage.workGrade}">selected="selected"</c:if>>管理人员</option>
              			<option value="2" <c:if test="${not empty unitMessage.workGrade and 2 eq unitMessage.workGrade}">selected="selected"</c:if>>股东</option>
              			<option value="3" <c:if test="${not empty unitMessage.workGrade and 3 eq unitMessage.workGrade}">selected="selected"</c:if>>私营业主</option>
	                </select>
				</p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">职位：</label>
					<select name="zhiwei" id="zhiwei" class="input08" style="width:auto">
                            		<option value="77" <c:if test="${not empty unitMessage.zhiwei and 77 eq unitMessage.zhiwei}">selected="selected"</c:if>>财务总监</option>
	              		<option value="78" <c:if test="${not empty unitMessage.zhiwei and 78 eq unitMessage.zhiwei}">selected="selected"</c:if>>财务主管</option>
	              		<option value="79" <c:if test="${not empty unitMessage.zhiwei and 79 eq unitMessage.zhiwei}">selected="selected"</c:if>>审计/税务</option>
	              		<option value="80" <c:if test="${not empty unitMessage.zhiwei and 80 eq unitMessage.zhiwei}">selected="selected"</c:if>>总账/成本</option>
	              		<option value="81" <c:if test="${not empty unitMessage.zhiwei and 81 eq unitMessage.zhiwei}">selected="selected"</c:if>>会计人员</option>
	              		<option value="82" <c:if test="${not empty unitMessage.zhiwei and 82 eq unitMessage.zhiwei}">selected="selected"</c:if>>人力资源总监</option>
	              		<option value="83" <c:if test="${not empty unitMessage.zhiwei and 83 eq unitMessage.zhiwei}">selected="selected"</c:if>>人力资源经理/主管</option>
	              		<option value="84" <c:if test="${not empty unitMessage.zhiwei and 84 eq unitMessage.zhiwei}">selected="selected"</c:if>>招聘经理/主管</option>
	              		<option value="85" <c:if test="${not empty unitMessage.zhiwei and 85 eq unitMessage.zhiwei}">selected="selected"</c:if>>薪资福利经理/主管</option>
	              		<option value="86" <c:if test="${not empty unitMessage.zhiwei and 86 eq unitMessage.zhiwei}">selected="selected"</c:if>>财务经理</option>
	              		<option value="87" <c:if test="${not empty unitMessage.zhiwei and 87 eq unitMessage.zhiwei}">selected="selected"</c:if>>培训经理/主管</option>
	               		<option value="88" <c:if test="${not empty unitMessage.zhiwei and 88 eq unitMessage.zhiwei}">selected="selected"</c:if>>人事专员/助理</option>
	               		<option value="89" <c:if test="${not empty unitMessage.zhiwei and 89 eq unitMessage.zhiwei}">selected="selected"</c:if>>行长/副行长</option>
	               		<option value="90" <c:if test="${not empty unitMessage.zhiwei and 90 eq unitMessage.zhiwei}">selected="selected"</c:if>>信贷/信用管理</option>
	               		<option value="91" <c:if test="${not empty unitMessage.zhiwei and 91 eq unitMessage.zhiwei}">selected="selected"</c:if>>资产管理/评估</option>
	               		<option value="92" <c:if test="${not empty unitMessage.zhiwei and 92 eq unitMessage.zhiwei}">selected="selected"</c:if>>投融资项目/基金</option>
	               		<option value="93" <c:if test="${not empty unitMessage.zhiwei and 93 eq unitMessage.zhiwei}">selected="selected"</c:if>>外汇管理/国际结算</option>
	               		<option value="94" <c:if test="${not empty unitMessage.zhiwei and 94 eq unitMessage.zhiwei}">selected="selected"</c:if>>风险管理</option>
	               		<option value="95" <c:if test="${not empty unitMessage.zhiwei and 95 eq unitMessage.zhiwei}">selected="selected"</c:if>>证券/期货</option>
	               		<option value="96" <c:if test="${not empty unitMessage.zhiwei and 96 eq unitMessage.zhiwei}">selected="selected"</c:if>>客户经理/主管</option>
	               		<option value="97" <c:if test="${not empty unitMessage.zhiwei and 97 eq unitMessage.zhiwei}">selected="selected"</c:if>>核保/理赔</option>
	               		<option value="98" <c:if test="${not empty unitMessage.zhiwei and 98 eq unitMessage.zhiwei}">selected="selected"</c:if>>保险代理人/内勤</option>
	               		<option value="99" <c:if test="${not empty unitMessage.zhiwei and 99 eq unitMessage.zhiwei}">selected="selected"</c:if>>银行专员/柜台</option>
	               		<option value="100" <c:if test="${not empty unitMessage.zhiwei and 100 eq unitMessage.zhiwei}">selected="selected"</c:if>>精算师</option>
	               		<option value="101" <c:if test="${not empty unitMessage.zhiwei and 101 eq unitMessage.zhiwei}">selected="selected"</c:if>>编辑/记者/文案</option>
	               		<option value="102" <c:if test="${not empty unitMessage.zhiwei and 102 eq unitMessage.zhiwei}">selected="selected"</c:if>>版面设计</option>
	               		<option value="103" <c:if test="${not empty unitMessage.zhiwei and 103 eq unitMessage.zhiwei}">selected="selected"</c:if>>平面设计</option>
	               		<option value="104" <c:if test="${not empty unitMessage.zhiwei and 104 eq unitMessage.zhiwei}">selected="selected"</c:if>>装潢/陈列设计</option>
	               		<option value="105" <c:if test="${not empty unitMessage.zhiwei and 105 eq unitMessage.zhiwei}">selected="selected"</c:if>>纺织/服装设计</option>
	               		<option value="106" <c:if test="${not empty unitMessage.zhiwei and 106 eq unitMessage.zhiwei}">selected="selected"</c:if>>工艺品/珠宝设计</option>
	               		<option value="107" <c:if test="${not empty unitMessage.zhiwei and 107 eq unitMessage.zhiwei}">selected="selected"</c:if>>经理</option>
	               		<option value="108" <c:if test="${not empty unitMessage.zhiwei and 108 eq unitMessage.zhiwei}">selected="selected"</c:if>>主任</option>
	               		<option value="109" <c:if test="${not empty unitMessage.zhiwei and 109 eq unitMessage.zhiwei}">selected="selected"</c:if>>生物工程</option>
	               		<option value="110" <c:if test="${not empty unitMessage.zhiwei and 110 eq unitMessage.zhiwei}">selected="selected"</c:if>>药物注册</option>
	               		<option value="111" <c:if test="${not empty unitMessage.zhiwei and 111 eq unitMessage.zhiwei}">selected="selected"</c:if>>临床研究/协调</option>
	               		<option value="112" <c:if test="${not empty unitMessage.zhiwei and 112 eq unitMessage.zhiwei}">selected="selected"</c:if>>药物研发/分析工程师</option>
	               		<option value="113" <c:if test="${not empty unitMessage.zhiwei and 113 eq unitMessage.zhiwei}">selected="selected"</c:if>>化学工程师</option>
	               		<option value="114" <c:if test="${not empty unitMessage.zhiwei and 114 eq unitMessage.zhiwei}">selected="selected"</c:if>>环保工程师</option>
	               		<option value="115" <c:if test="${not empty unitMessage.zhiwei and 115 eq unitMessage.zhiwei}">selected="selected"</c:if>>化工人员</option>
	               		<option value="116" <c:if test="${not empty unitMessage.zhiwei and 116 eq unitMessage.zhiwei}">selected="selected"</c:if>>结构/建筑工程师</option>
	               		<option value="117" <c:if test="${not empty unitMessage.zhiwei and 117 eq unitMessage.zhiwei}">selected="selected"</c:if>>土建工程师/建造师</option>
	               		<option value="118" <c:if test="${not empty unitMessage.zhiwei and 118 eq unitMessage.zhiwei}">selected="selected"</c:if>>机电/给排水/暖通工程师</option>
	               		<option value="119" <c:if test="${not empty unitMessage.zhiwei and 119 eq unitMessage.zhiwei}">selected="selected"</c:if>>工程造价师/预决算</option>
	               		<option value="120" <c:if test="${not empty unitMessage.zhiwei and 120 eq unitMessage.zhiwei}">selected="selected"</c:if>>监理/安全工程师</option>
	               		<option value="121" <c:if test="${not empty unitMessage.zhiwei and 121 eq unitMessage.zhiwei}">selected="selected"</c:if>>房地产开发/策划</option>
	               		<option value="122" <c:if test="${not empty unitMessage.zhiwei and 122 eq unitMessage.zhiwei}">selected="selected"</c:if>>园林景观/城市规划师</option>
	               		<option value="123" <c:if test="${not empty unitMessage.zhiwei and 123 eq unitMessage.zhiwei}">selected="selected"</c:if>>物业管理</option>
	               		<option value="124" <c:if test="${not empty unitMessage.zhiwei and 124 eq unitMessage.zhiwei}">selected="selected"</c:if>>房地产评估/中介/交易</option>
	               		<option value="125" <c:if test="${not empty unitMessage.zhiwei and 125 eq unitMessage.zhiwei}">selected="selected"</c:if>>电子/电路工程师</option>
	               		<option value="126" <c:if test="${not empty unitMessage.zhiwei and 126 eq unitMessage.zhiwei}">selected="selected"</c:if>>电气工程师</option>
	               		<option value="127" <c:if test="${not empty unitMessage.zhiwei and 127 eq unitMessage.zhiwei}">selected="selected"</c:if>>半导体工程师</option>
	               		<option value="128" <c:if test="${not empty unitMessage.zhiwei and 128 eq unitMessage.zhiwei}">selected="selected"</c:if>>测试工程师</option>
	               		<option value="129" <c:if test="${not empty unitMessage.zhiwei and 129 eq unitMessage.zhiwei}">selected="selected"</c:if>>自动化工程师</option>
	               		<option value="130" <c:if test="${not empty unitMessage.zhiwei and 130 eq unitMessage.zhiwei}">selected="selected"</c:if>>光学工程师</option>
	               		<option value="131" <c:if test="${not empty unitMessage.zhiwei and 131 eq unitMessage.zhiwei}">selected="selected"</c:if> >电力工程师</option>
	               		<option value="132" <c:if test="${not empty unitMessage.zhiwei and 132 eq unitMessage.zhiwei}">selected="selected"</c:if>>水利/水电工程师</option>
	               		<option value="133" <c:if test="${not empty unitMessage.zhiwei and 133 eq unitMessage.zhiwei}">selected="selected"</c:if>>单片机/DLC/DSP/底层软件开发</option>
	               		<option value="134" <c:if test="${not empty unitMessage.zhiwei and 134 eq unitMessage.zhiwei}">selected="selected"</c:if>>其他工程师</option>
	               		<option value="135" <c:if test="${not empty unitMessage.zhiwei and 135 eq unitMessage.zhiwei}">selected="selected"</c:if>>技术人员</option>
	               		<option value="136" <c:if test="${not empty unitMessage.zhiwei and 136 eq unitMessage.zhiwei}">selected="selected"</c:if>>助理</option>
	               		<option value="137" <c:if test="${not empty unitMessage.zhiwei and 137 eq unitMessage.zhiwei}">selected="selected"</c:if>>厂长/副厂长</option>
	               		<option value="138" <c:if test="${not empty unitMessage.zhiwei and 138 eq unitMessage.zhiwei}">selected="selected"</c:if>>生产经理/车间主任</option>
	               		<option value="139" <c:if test="${not empty unitMessage.zhiwei and 139 eq unitMessage.zhiwei}">selected="selected"</c:if>>产品开发</option>
	               		<option value="140" <c:if test="${not empty unitMessage.zhiwei and 140 eq unitMessage.zhiwei}">selected="selected"</c:if>>工业/产品设计</option>
	               		<option value="141" <c:if test="${not empty unitMessage.zhiwei and 141 eq unitMessage.zhiwei}">selected="selected"</c:if>>生产/计划/调度</option>
	               		<option value="142" <c:if test="${not empty unitMessage.zhiwei and 142 eq unitMessage.zhiwei}">selected="selected"</c:if>>质量管理</option>
	               		<option value="143" <c:if test="${not empty unitMessage.zhiwei and 143 eq unitMessage.zhiwei}">selected="selected"</c:if>>项目管理</option>
	               		<option value="144" <c:if test="${not empty unitMessage.zhiwei and 144 eq unitMessage.zhiwei}">selected="selected"</c:if>>工程设备管理</option>
	               		<option value="145" <c:if test="${not empty unitMessage.zhiwei and 145 eq unitMessage.zhiwei}">selected="selected"</c:if>>安全/健康/环境管理</option>
	               		<option value="146" <c:if test="${not empty unitMessage.zhiwei and 146 eq unitMessage.zhiwei}">selected="selected"</c:if>>生产工艺/技术</option>
	               		<option value="147" <c:if test="${not empty unitMessage.zhiwei and 147 eq unitMessage.zhiwei}">selected="selected"</c:if>>技工</option>
	               		<option value="148" <c:if test="${not empty unitMessage.zhiwei and 148 eq unitMessage.zhiwei}">selected="selected"</c:if>>机械工程师</option>
	               		<option value="149" <c:if test="${not empty unitMessage.zhiwei and 149 eq unitMessage.zhiwei}">selected="selected"</c:if>>维修工程师</option>
	               		<option value="150" <c:if test="${not empty unitMessage.zhiwei and 150 eq unitMessage.zhiwei}">selected="selected"</c:if>>机械设计/制图</option>
	               		<option value="151" <c:if test="${not empty unitMessage.zhiwei and 151 eq unitMessage.zhiwei}">selected="selected"</c:if>>机电工程师</option>
	               		<option value="152" <c:if test="${not empty unitMessage.zhiwei and 152 eq unitMessage.zhiwei}">selected="selected"</c:if>>模具工程师</option>
	               		<option value="153" <c:if test="${not empty unitMessage.zhiwei and 153 eq unitMessage.zhiwei}">selected="selected"</c:if>>精密机械/仪器仪表</option>
	               		<option value="154" <c:if test="${not empty unitMessage.zhiwei and 154 eq unitMessage.zhiwei}">selected="selected"</c:if>>船舶工程师</option>
	               		<option value="155" <c:if test="${not empty unitMessage.zhiwei and 155 eq unitMessage.zhiwei}">selected="selected"</c:if>>冲压/注塑/焊接</option>
	               		<option value="156" <c:if test="${not empty unitMessage.zhiwei and 156 eq unitMessage.zhiwei}">selected="selected"</c:if>>餐饮/娱乐管理</option>
	               		<option value="157" <c:if test="${not empty unitMessage.zhiwei and 157 eq unitMessage.zhiwei}">selected="selected"</c:if>>酒店/宾馆管理</option>
	               		<option value="158" <c:if test="${not empty unitMessage.zhiwei and 158 eq unitMessage.zhiwei}">selected="selected"</c:if>>服务人员/领班</option>
	               		<option value="159" <c:if test="${not empty unitMessage.zhiwei and 159 eq unitMessage.zhiwei}">selected="selected"</c:if>>导游/旅行顾问/票务</option>
	               		<option value="160" <c:if test="${not empty unitMessage.zhiwei and 160 eq unitMessage.zhiwei}">selected="selected"</c:if>>营业员/收银员</option>
	               		<option value="161" <c:if test="${not empty unitMessage.zhiwei and 161 eq unitMessage.zhiwei}">selected="selected"</c:if>>技术总监/首席技术执行官</option>
	               		<option value="162" <c:if test="${not empty unitMessage.zhiwei and 162 eq unitMessage.zhiwei}">selected="selected"</c:if>>技术经理</option>
	               		<option value="163" <c:if test="${not empty unitMessage.zhiwei and 163 eq unitMessage.zhiwei}">selected="selected"</c:if>>项目经理</option>
	               		<option value="164" <c:if test="${not empty unitMessage.zhiwei and 164 eq unitMessage.zhiwei}">selected="selected"</c:if>>系统分析工程师</option>
	               		<option value="165" <c:if test="${not empty unitMessage.zhiwei and 165 eq unitMessage.zhiwei}">selected="selected"</c:if>>ERP应用顾问</option>
	               		<option value="166" <c:if test="${not empty unitMessage.zhiwei and 166 eq unitMessage.zhiwei}">selected="selected"</c:if>>数据库工程师/管理员</option>
	               		<option value="167" <c:if test="${not empty unitMessage.zhiwei and 167 eq unitMessage.zhiwei}">selected="selected"</c:if>>软件开发工程师</option>
	               		<option value="168" <c:if test="${not empty unitMessage.zhiwei and 168 eq unitMessage.zhiwei}">selected="selected"</c:if>>硬件开发工程师</option>
	               		<option value="169" <c:if test="${not empty unitMessage.zhiwei and 169 eq unitMessage.zhiwei}">selected="selected"</c:if>>信息支持/安全维护</option>
	               		<option value="170" <c:if test="${not empty unitMessage.zhiwei and 170 eq unitMessage.zhiwei}">selected="selected"</c:if>>网页设计/编辑</option>
	               		<option value="171" <c:if test="${not empty unitMessage.zhiwei and 171 eq unitMessage.zhiwei}">selected="selected"</c:if>>通讯工程师</option>
	               		<option value="172" <c:if test="${not empty unitMessage.zhiwei and 172 eq unitMessage.zhiwei}">selected="selected"</c:if>>多媒体/游戏开发</option>
	               		<option value="173" <c:if test="${not empty unitMessage.zhiwei and 173 eq unitMessage.zhiwei}">selected="selected"</c:if>>采购经理/主管</option>
	               		<option value="174" <c:if test="${not empty unitMessage.zhiwei and 174 eq unitMessage.zhiwei}">selected="selected"</c:if>>采购专员/助理</option>
	               		<option value="175" <c:if test="${not empty unitMessage.zhiwei and 175 eq unitMessage.zhiwei}">selected="selected"</c:if>>贸易经理/主管</option>
	               		<option value="176" <c:if test="${not empty unitMessage.zhiwei and 176 eq unitMessage.zhiwei}">selected="selected"</c:if>>市场咨询/调研</option>
	               		<option value="177" <c:if test="${not empty unitMessage.zhiwei and 177 eq unitMessage.zhiwei}">selected="selected"</c:if>>创意与策划</option>
	               		<option value="178" <c:if test="${not empty unitMessage.zhiwei and 178 eq unitMessage.zhiwei}">selected="selected"</c:if>>客服经理/主管</option>
	               		<option value="179" <c:if test="${not empty unitMessage.zhiwei and 179 eq unitMessage.zhiwei}">selected="selected"</c:if>>客服专员/助理</option>
	               		<option value="180" <c:if test="${not empty unitMessage.zhiwei and 180 eq unitMessage.zhiwei}">selected="selected"</c:if>>客户关系管理</option>
	               		<option value="181" <c:if test="${not empty unitMessage.zhiwei and 181 eq unitMessage.zhiwei}">selected="selected"</c:if>>客户咨询/热线支持</option>
	               		<option value="182" <c:if test="${not empty unitMessage.zhiwei and 182 eq unitMessage.zhiwei}">selected="selected"</c:if>>销售总监</option>
	               		<option value="183" <c:if test="${not empty unitMessage.zhiwei and 183 eq unitMessage.zhiwei}">selected="selected"</c:if>>销售经理</option>
	               		<option value="184" <c:if test="${not empty unitMessage.zhiwei and 184 eq unitMessage.zhiwei}">selected="selected"</c:if>>销售经理</option>
	               		<option value="185" <c:if test="${not empty unitMessage.zhiwei and 185 eq unitMessage.zhiwei}">selected="selected"</c:if>>业务拓展经理</option>
	               		<option value="186" <c:if test="${not empty unitMessage.zhiwei and 186 eq unitMessage.zhiwei}">selected="selected"</c:if>>销售主管/主任</option>
	               		<option value="187" <c:if test="${not empty unitMessage.zhiwei and 187 eq unitMessage.zhiwei}">selected="selected"</c:if>>售前/售后支持</option>
	               		<option value="188" <c:if test="${not empty unitMessage.zhiwei and 188 eq unitMessage.zhiwei}">selected="selected"</c:if>>销售工程师</option>
	               		<option value="189" <c:if test="${not empty unitMessage.zhiwei and 189 eq unitMessage.zhiwei}">selected="selected"</c:if>>销售代表</option>
	               		<option value="190" <c:if test="${not empty unitMessage.zhiwei and 190 eq unitMessage.zhiwei}">selected="selected"</c:if>>医药代表</option>
	               		<option value="191" <c:if test="${not empty unitMessage.zhiwei and 191 eq unitMessage.zhiwei}">selected="selected"</c:if>>销售助理</option>
	               		<option value="192" <c:if test="${not empty unitMessage.zhiwei and 192 eq unitMessage.zhiwei}">selected="selected"</c:if>>专业顾问</option>
	               		<option value="193" <c:if test="${not empty unitMessage.zhiwei and 193 eq unitMessage.zhiwei}">selected="selected"</c:if>>咨询师</option>
	               		<option value="194" <c:if test="${not empty unitMessage.zhiwei and 194 eq unitMessage.zhiwei}">selected="selected"</c:if>>调研员</option>
	               		<option value="195" <c:if test="${not empty unitMessage.zhiwei and 195 eq unitMessage.zhiwei}">selected="selected"</c:if>>英语翻译</option>
	               		<option value="196" <c:if test="${not empty unitMessage.zhiwei and 196 eq unitMessage.zhiwei}">selected="selected"</c:if>>日语翻译</option>
	               		<option value="197" <c:if test="${not empty unitMessage.zhiwei and 197 eq unitMessage.zhiwei}">selected="selected"</c:if>>德语翻译</option>
	               		<option value="198" <c:if test="${not empty unitMessage.zhiwei and 198 eq unitMessage.zhiwei}">selected="selected"</c:if>>法语翻译</option>
	               		<option value="199" <c:if test="${not empty unitMessage.zhiwei and 199 eq unitMessage.zhiwei}">selected="selected"</c:if>>俄语翻译</option>
	               		<option value="200" <c:if test="${not empty unitMessage.zhiwei and 200 eq unitMessage.zhiwei}">selected="selected"</c:if>>西班牙语翻译</option>
	               		<option value="201" <c:if test="${not empty unitMessage.zhiwei and 201 eq unitMessage.zhiwei}">selected="selected"</c:if>>其他翻译</option>
	               		<option value="202" <c:if test="${not empty unitMessage.zhiwei and 202 eq unitMessage.zhiwei}">selected="selected"</c:if>>董事/总经理/总裁</option>
	               		<option value="203" <c:if test="${not empty unitMessage.zhiwei and 203 eq unitMessage.zhiwei}">selected="selected"</c:if>>首席执行官/运营官</option>
	               		<option value="204" <c:if test="${not empty unitMessage.zhiwei and 204 eq unitMessage.zhiwei}">selected="selected"</c:if>>营运总监/经理</option>
	               		<option value="205" <c:if test="${not empty unitMessage.zhiwei and 205 eq unitMessage.zhiwei}">selected="selected"</c:if>>合伙人</option>
	               		<option value="206" <c:if test="${not empty unitMessage.zhiwei and 206 eq unitMessage.zhiwei}">selected="selected"</c:if>>行政总监</option>
	               		<option value="207" <c:if test="${not empty unitMessage.zhiwei and 207 eq unitMessage.zhiwei}">selected="selected"</c:if> >行政经理</option>
	               		<option value="208" <c:if test="${not empty unitMessage.zhiwei and 208 eq unitMessage.zhiwei}">selected="selected"</c:if>>主管/主任</option>
	               		<option value="209" <c:if test="${not empty unitMessage.zhiwei and 209 eq unitMessage.zhiwei}">selected="selected"</c:if>>助理/秘书</option>
	               		<option value="210" <c:if test="${not empty unitMessage.zhiwei and 210 eq unitMessage.zhiwei}">selected="selected"</c:if>>前台/文员</option>
	               		<option value="211" <c:if test="${not empty unitMessage.zhiwei and 211 eq unitMessage.zhiwei}">selected="selected"</c:if>>物流经理/主管</option>
	               		<option value="212" <c:if test="${not empty unitMessage.zhiwei and 212 eq unitMessage.zhiwei}">selected="selected"</c:if>>物流专员/助理</option>
	               		<option value="213" <c:if test="${not empty unitMessage.zhiwei and 213 eq unitMessage.zhiwei}">selected="selected"</c:if>>物料/仓库管理</option>
	               		<option value="214" <c:if test="${not empty unitMessage.zhiwei and 214 eq unitMessage.zhiwei}">selected="selected"</c:if>>供应链</option>
	               		<option value="215" <c:if test="${not empty unitMessage.zhiwei and 215 eq unitMessage.zhiwei}">selected="selected"</c:if>>供应链</option>
	               		<option value="216" <c:if test="${not empty unitMessage.zhiwei and 216 eq unitMessage.zhiwei}">selected="selected"</c:if>>单证/报关员</option>
	               		<option value="217" <c:if test="${not empty unitMessage.zhiwei and 217 eq unitMessage.zhiwei}">selected="selected"</c:if>>律师/法务</option>
	               		<option value="218" <c:if test="${not empty unitMessage.zhiwei and 218 eq unitMessage.zhiwei}">selected="selected"</c:if>>能源/动力</option>
	               		<option value="219" <c:if test="${not empty unitMessage.zhiwei and 219 eq unitMessage.zhiwei}">selected="selected"</c:if>>医疗/护理</option>
	               		<option value="220" <c:if test="${not empty unitMessage.zhiwei and 220 eq unitMessage.zhiwei}">selected="selected"</c:if>>科研人员</option>
	               		<option value="221" <c:if test="${not empty unitMessage.zhiwei and 221 eq unitMessage.zhiwei}">selected="selected"</c:if>>培训/教育</option>
	               		<option value="222" <c:if test="${not empty unitMessage.zhiwei and 222 eq unitMessage.zhiwei}">selected="selected"</c:if>>储备干部/培训生</option>
	               		<option value="223" <c:if test="${not empty unitMessage.zhiwei and 223 eq unitMessage.zhiwei}">selected="selected"</c:if>>在校学生</option>
	               		<option value="224" <c:if test="${not empty unitMessage.zhiwei and 224 eq unitMessage.zhiwei}">selected="selected"</c:if>>其他职位</option>
	              </select>
				</p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">服务时间:</label><input class="date textInput readonly required" type="text" name="serveTime1" value='<fmt:formatDate value="${unitMessage.serveTime }" pattern="yyyy-MM-dd"/>' />到<input class="date textInput readonly required" type="text" name="toServeTime1" value="<fmt:formatDate value="${unitMessage.toServeTime }"  pattern="yyyy-MM-dd"/>" /></p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">工作年限：</label>
					<select name="workYear" id="workYear" style="width:auto">
                        <option value="0" <c:if test="${not empty unitMessage.workYear and 0 eq unitMessage.workYear}">selected="selected"</c:if>>一年以内</option>
             			<option value="1" <c:if test="${not empty unitMessage.workYear and 1 eq unitMessage.workYear}">selected="selected"</c:if>>一年以上</option>
             			<option value="2" <c:if test="${not empty unitMessage.workYear and 2 eq unitMessage.workYear}">selected="selected"</c:if>>两年以上</option>
             			<option value="3" <c:if test="${not empty unitMessage.workYear and 3 eq unitMessage.workYear}">selected="selected"</c:if>>三年以上</option>
             			<option value="4" <c:if test="${not empty unitMessage.workYear and 4 eq unitMessage.workYear}">selected="selected"</c:if>>四年年以上</option>
             			<option value="5" <c:if test="${not empty unitMessage.workYear and 5 eq unitMessage.workYear}">selected="selected"</c:if>>五年以上</option>
             			<option value="6" <c:if test="${not empty unitMessage.workYear and 6 eq unitMessage.workYear}">selected="selected"</c:if>>六年以上</option>
                     </select>
				</p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">工作电话:</label><input class="required" onkeyup="value=value.replace(/[^0-9]/g,'')" type="text" name="workTel"  value="${unitMessage.workTel }" /></p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">公司地址:</label><input class="required" type="text" name="companyAdd"  value="${unitMessage.companyAdd }" /></p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">公司网址:</label><input type="text" name="companyWebsite"  value="${unitMessage.companyWebsite }" /></p>
				<div class="divider"></div>
				
				<p><label style="float:left; width:120px; padding:0 5px; line-height:21px;">备注说明:</label><input type="text" name="remark"  value="${unitMessage.remark }" /></p>
				<div class="divider"></div>
			</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										保存
									</button>
								</div>
							</div>
						</li>
						<!-- <li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li> -->
					</ul>
				</div>
			</form>
		</div>
</body>
<script type="text/javascript">

			
			
</script>
</html>