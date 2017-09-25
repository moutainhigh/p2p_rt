<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>添加理财产品</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" action="products/${PRE_PATH_EDIT}saveProduct"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="productId" value="${product.id}">
				
				<div class="pageFormContent" layoutH="60">
					<p><label>产品类型:</label>
						<select name="productsType" id="productsType">
							<c:forEach var="productsType" items="${productsTypes}">
								<option value="${productsType.id}" 
								<c:if test="${productsType.id == product.productsType}">selected="selected"</c:if>
								>${productsType.dicName}</option>
							</c:forEach>
						</select>
					</p>
					<div class="divider"></div>
					<p><label>产品标题:</label>
					<input name="productsTitle" class="required" type="text" value="${product.productsTitle}" /></p>
					<div class="divider"></div>
					<p><label>年化最低收益:</label><input name="productsMinProfit" class="required number" type="text"  value="${product.productsMinProfit}" /><span>&nbsp;%</span></p>
					<div class="divider"></div>
					<p><label>年化最高收益:</label><input name="productsMaxProfit" class="required number" type="text"  value="${product.productsMaxProfit}" /><span>&nbsp;%</span></p>
					<div class="divider"></div>
					<p><label>产品期限:</label><input name="productsTimeLimit" class="required digits" type="text"  value="${product.productsTimeLimit}" /><span>&nbsp;个月</span></p>
					<div class="divider"></div>
					<p><label>募集规模:</label><input name="productsScale" class="required number" type="text"  value="${product.productsScale}" /><span>&nbsp;元</span></p>
					<div class="divider"></div>
					<p><label>投资起点:</label><input name="productsInvestBegin" class="required number" type="text"  value="${product.productsInvestBegin}" /><span>&nbsp;元</span></p>
					<div class="divider"></div>
					<p><label>收益分配:</label><input name="productsIncomeDistribution" class="required" type="text"  value="${product.productsIncomeDistribution}" /></p>
					<div class="divider"></div>
					<p><label>发布时间:</label>
						<input
						class="date textInput readonly required" name="productsPublishDate"
						<c:if test="${empty product.productsPublishDate  }">value="<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"</c:if>
						<c:if test="${not empty product.productsPublishDate  }"> value="<fmt:formatDate value="${product.productsPublishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" </c:if>
						type="text" readonly="true" datefmt="yyyy-MM-dd HH:mm:ss"
						name="date10"> <a class="inputDateButton"
						href="javascript:;">选择</a> <span class="info">yyyy-MM-ddHH:mm:ss</span>
					</p>
					<div class="divider"></div>
					<p><label>产品状态:</label>
						<input type="radio" name="productsStatus" checked="checked" value="1"
						<c:if test="${product.productsStatus ==1 }">checked=checked;</c:if>
						/>可配置
						<input type="radio" name="productsStatus" value="2"
						<c:if test="${product.productsStatus ==2 }">checked=checked;</c:if>
						/>已成立
						<input type="radio" name="productsStatus" value="3"
						<c:if test="${product.productsStatus ==3 }">checked=checked;</c:if>
						/>已兑付
				   </p>
				   <div class="divider"></div>
					<p><label>产品描述:</label>
						<textarea cols="65" rows="15" class="editor" upImgUrl="upload/editorImg" name="productsContent" >${product.productsContent}</textarea>
				</p>
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

	</body>
</html>
