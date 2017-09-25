<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="${showKeywords}" />
<meta name="description" content="${description}" />
<title>理财计算器-${showTitle }</title>
<script type="text/javascript" src="${frontPath}/js/calculator.js"></script>
</head>
<body>
	<jsp:include flush="false" page="/top.do"></jsp:include>
<!---计算器 star---->

<div class="QLL_earnings QLL_cooper">
  <div class="QLL_E_con QLL_coo_content">
    <div class="QLLE_title">收益计算器</div>
    <div class="QLLE_nr">
      <ul>
        <li>
          <dl>
            <dd>投资资金：</dd>
            <dd><input id="principal" type="text" value="" name="principal" onkeyup="value=value.replace(/[^0-9]/g,'')"></dd>
            <dd class="QLLE_nr_zouju">元</dd>
             <div class="clear"></div>
          </dl>
          <div class="clear"></div>
          <dl>
            <dd>年化收益：</dd>
            <dd><input type="text" id="rate" value="" name="rate" onkeyup="value=value.replace(/[^0-9]/g,'')"></dd>
            <dd class="QLLE_nr_zouju">%</dd>
             <div class="clear"></div>
          </dl>
        </li>
        <li style=" margin:0 80px;">
          <dl>
            <dd>还款方式：</dd>
            <dd>
              <form>
                <select id="hkfs">
                  <option  value="3">按月分期还款(按月)</option>
				  <option selected="selected" value="2">一次性还款(按月)</option>
				  <option value="4">每月还息到期还本(按月)</option>
                </select>
              </form></dd>
            
             <div class="clear"></div>
          </dl>
          <div class="clear"></div>
          <dl>
            <dd>投资期限：</dd>
            <dd class="QLL_Timelimit "><input type="text" id="month" value="" name="month" style="width: 173px;" onkeyup="value=value.replace(/[^0-9]/g,'')"><span></span></dd>
            <dd class="QLLE_nr_zouju">个月</dd>
             <div class="clear"></div>
          </dl>
          <!-- 默认为一个当前时间  -->
          
          <%-- <input type="hidden" id= "date" name="date" value="<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd"/>"> --%>
        </li>
        <li class="QLLE_nr_button">
          <dl>
            <dd onclick="clearAll();">重置</dd>
            <dd class="button_sepcial" style="border-color:#f78182;" onclick="calculate();">开始计算</dd>
          </dl>
        </li>
        <div class="clear"></div>
      </ul>
    </div><!---QLLE_nr--->
    <div class="clear"></div>
    <div class="QLLE_bottom">
      <div class="QLLE_title" style="margin:0 0 10px 0; border:none;">计算结果</div>
      <div class="QLLE_B_table">
        <table cellpadding="0" cellspacing="0">
          <thead>
		  <tr>
            <td>还款日期</td>
            <td>应收本息（元）</td>
            <td>应收利息（元）</td>
            <td>应收本金（元）</td>
            <td>剩余本金（元）</td>
          </tr>
        </thead>
		<tbody id="investRecordsTable">
			
		</tbody>
		</table>
      </div>
    </div>
  </div>
</div>

<!---计算器 end---->
<jsp:include page="/foot.do"></jsp:include>

</body>
</html>