<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="pageContent">
			<form method="post" action="area/saveOrUpdate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden"  name="id" value="${area.id }">
				
				<div class="pageFormContent" layoutH="56">
				
					<p><label>上级栏目：</label>
					    <input name="district.id"  type="hidden" value=${pid }>
						<input class="required textInput readonly" name="district.districtName" type="text" readonly=""  value="${pname }" >
						<a class="btnLook" href="area/rootTree" lookupgroup="district" width="250">栏目</a>
					 </p>
					<div class="divider"></div>
					
					<p><label>地区名称：</label>
						<input name="areaName" class="required"  type="text" alt="请输入地区名称" size="20"  value="${area.areaName }"/></p>
					<div class="divider"></div>
					
					<p><label>地区编码：</label>
						<input name="areaCode" class="required"  type="text" alt="请输入地区编码" size="20"  value="${area.areaCode }"/></p>
					<div class="divider"></div>
					
					<p><label>排&nbsp;&nbsp;&nbsp;&nbsp;序：</label>
							<input name="areaSequence" class="digits required"  type="text" alt="请输入排序" size="10" value="${area.areaSequence }"/></p>
					<div class="divider"></div>
					
					<p><label>域&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
					  <input name="areaDomain"  type="text" alt="请输入域名" size="20" value="${area.areaDomain }"/></p>
					</p>
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
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
