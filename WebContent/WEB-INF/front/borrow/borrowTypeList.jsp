<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<%@page import="com.rbao.east.common.Constants" %>
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/invest.css" />
<link rel="stylesheet" type="text/css" href="${cssJsPath }/css/global-min.css" />
<title>${showTitle }</title>
</head>
<body>
<c:if test="${not empty borrowTypeList }">
   <c:forEach items="${ borrowTypeList}" var="borrowType">
     <form action="">
        <table width="800px" height="200px" border="1px solid bule">
           <tr>
              <td rowspan="3"><img src="${path }${borrowType.logPath }" style="width: 130px;height: 130px;"/></td>
              <td>${borrowType.name }</td>
           </tr>
           <tr>
              <td>${borrowType.remark }</td>
           </tr>
            <tr>
              <td> 
              	<a href="${path }/iborrow/borrowInvitation/${borrowType.code }.do">发&nbsp;&nbsp;&nbsp;布</a>
              </td>
           </tr>
        </table>
     </form>
   </c:forEach>
</c:if>
<c:if test="${empty borrowTypeList }">
暂时无标
</c:if>
</body>
</html>