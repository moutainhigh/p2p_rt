<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加、修改设置</title>
</head>
<body>
  <div class="pageContent">
		<form method="post" action="lotteryPrize/${PRE_PATH_VIEW }saveOrUpdate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${lotteryPrize.id}" />
				<div class="pageFormContent" layoutH="55">
				
				<p>
					<label>分类：</label> 
					<select name="prizeType"  class="required">
					<option value="">请选择分类</option>
					<c:forEach var="c" items="${LOTTERY_PRZ_ALL_type }">
					<option value="${c.key }">${c.value }</option>
					</c:forEach>
					</select>
					<script type="text/javascript">
					$("[name='prizeType']").val("${lotteryPrize.prizeType }");
					</script>
				</p>
				<div class="divider"></div>
				
					<p><label>名称：</label><input type="text" size="22" name="disc" value="${lotteryPrize.disc }" class="required"/></p>
					<div class="divider"></div>
					
					<p><label>开始角度：</label><input type="text" size="22" name="pointStart" id="pointStart" value="${lotteryPrize.pointStart }" class="required digits" min="0" max="360" /></p>
					<div class="divider"></div>
					
					<p><label>结束角度：</label><input type="text" size="22" name="pointEnd" id="pointEnd" value="${lotteryPrize.pointEnd }" class="required digits" min="0" max="360" /></p>
					<div class="divider"></div>
					
					<p><label>中奖概率：</label><input type="text" size="22" name="probability" id="probability" value="${lotteryPrize.probability }" class="required digits" min="1" max="10000" /></p>
					<div class="divider"></div> 
					
					<p><label>奖品总数：</label><input type="text" size="22" name="totalAmount" id="totalAmount" value="${lotteryPrize.totalAmount }" class="required digits" min="1" max="10000000"  /></p>
					<div class="divider"></div>
					 
					 <p><label>状态：</label>
					   <input type="radio" name="status"  value="1" <c:if test="${'0'ne lotteryPrize.status }">checked="checked" </c:if>/>可用
					   <input type="radio" name="status"  value="0" <c:if test="${'0'eq lotteryPrize.status }">checked="checked" </c:if>/>不可用
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
</body>

</html>