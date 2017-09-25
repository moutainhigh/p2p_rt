<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }</title>
</head>
<body>
	 <jsp:include page="/top.do"></jsp:include> 
	<div class="J-register jborder jboxsize">
          <div class="J-register-top"><img src="${frontPath}/images/J-zhuce03.png"/></div>
          <div class="J-reg-con clearfix">
            <ol class="J-reg-success">
             <li><img src="${frontPath}/images/J-zhuce-success.png"/>恭喜您注册成功！</li>
             <li><p>现在您可以认证您的身份，开启安全投资之旅</p></li>
            </ol>
         <%--    <form action="${path}/verify/requestRealnameAttestation.do" method="post" id="formRealname"> --%>
           <div class=" J-regcon-list J-reg-suclist  clearfix">
               <form id="formRealname">
            <ul>
               <li>
                <div class="J-regconl-input">
                 <p>真实姓名</p>
                 <input type="text" placeholder="请填写真实姓名" class="cyyj_in_hover" id="userRealname" name="userRealname"/>
                </div>
               </li>
                <li>
                <div class="J-regconl-input">
                 <p>身份证号码</p>
                 <input type="text" placeholder="请填写身份证号码" class="cyyj_in_hover" id="cardNumber" name="cardNumber"/>
                </div>
               </li>
               <li>
              <div class="J-regconl-checkbox clearfix" style="width:100%;">
               <div class="clearfix jmt40">
                 <p>&nbsp;</p>
                 <div class="J-btn jfl"><a onclick="formRealName();" target="_blank">提交</a></div>
               </div>
               <h4><a href="${path }/account/accountIndex.html">跳过此步</a></h4>
              </div>
             </li>
             </ul>
              </form>
           </div>
           <!-- </form> -->
          </div>
        </div>
	<jsp:include page="/foot.do"></jsp:include>
	
</body>
<!-- 浏览器兼容性问题 -->
<script type="text/javascript" src="${path }/common/js/ieplaceholder.js"></script>
<script type="text/javascript">
function formRealName(){
	var regCardNumber=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}[0-9xX]$/;
	 if($("#userRealname").val()==''||$("#cardNumber").val()==''){
		alertc("姓名或身份证号不能为空");
		
	}else if(!regCardNumber.test($("#cardNumber").val())){
		alertc("请填写正确的身份证号");
	}else{
		$.ajax({
			type : "POST",
			data :{
				"userRealname":$("#userRealname").val(),
				"cardNumber":$("#cardNumber").val()
			},
			url : "${path}/verify/requestRealnameAttestation.do",
			async : false,
			dataType : "json",
			success : function(ret) {
				if(ret.code=='10001'){
					alertc("实名认证成功");
					window.location.href = "${path}/index.html";
        		}
				if(ret.code=='10002'){
					alertc("实名认证失败");
				}
				if(ret.code=='10003'){
					alertc(ret.msg);
					
				}
			/* 	if(ret.code=='1001'){
					alertc(data.msg);
				}
				
				if(ret.code=='104'){
					alertc(data.msg);
            	} */
				if(ret.code=='102'){
					alertc(ret.msg);
            	}
			}
		});
	}
	}
function reloadPage(){
   	window.location.reload(true);
   }	
</script>
</html>