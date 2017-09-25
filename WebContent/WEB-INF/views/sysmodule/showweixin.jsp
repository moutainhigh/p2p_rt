<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信管理</title>
</head>
<body>
		<form id="tenderFrm" name="tenderFrm" method="post" action="" class="pageForm required-validate">
				<div class="pageFormContent" layoutH="55">
  					 <p><label>APPID:</label><input id="appid" name="appid" type="text" size="32" name="sysLoanRate"  value="${APP}" readonly="readonly"/></p>
					<div class="divider"></div>
					
					<p><label>AppSecret:</label><input id="appser" name="appser" type="text" size="32" name="sysLoanPoundage"   value="${secret}" readonly="readonly"/></p> 
					<div class="divider"></div>
					<!-- 第一个按钮 -->
					<p><label>(1)一级菜单:</label><input type="text" id="yiji1" name="yiji1" ></p>
					<div class="divider"></div>
					<label>二级菜单</label><input type="text" id="erji1" name="erji1">
					<!-- <input type="text" id="leixing" name="leixing"> -->
					<label>类型</label>
					<select id="leixing1" name="leixing1">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url1" name="url1">
					<!-- <label>KEY</label><input type="text" id="key1" name="key1"> --><p>
 					<label>二级菜单</label><input type="text" id="erji2" name="erji2">
 					<label>类型</label>
					<select id="leixing2" name="leixing2">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url2" name="url2">
					<!-- <label>KEY</label><input type="text" id="key2" name="key2"> --><p>
					<label>二级菜单</label><input type="text" id="erji3" name="erji3">
					<label>类型</label>
					<select id="leixing3" name="leixing3">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url3" name="url3">
					<!-- <label>KEY</label><input type="text" id="key3" name="key3"> --><p>
					<label>二级菜单</label><input type="text" id="erji4" name="erji4">
					<label>类型</label>
					<select id="leixing4" name="leixing4">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url4" name="url4">
					<!-- <label>KEY</label><input type="text" id="key4" name="key4"> --><p>
					<label>二级菜单</label><input type="text" id="erji5" name="erji5">
					<label>类型</label>
					<select id="leixing5" name="leixing5">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url5" name="url5">
					<!-- <label>KEY</label><input type="text" id="key5" name="key5"> --><p>
					
					<div class="divider"></div>
					
					<!-- 第二个按钮 -->
					<p><label>(2)一级菜单:</label><input type="text" id="yiji2" name="yiji2" ></p>
					<div class="divider"></div>
					<label>二级菜单</label><input type="text" id="erji6" name="erji6">
					<!-- <input type="text" id="leixing" name="leixing"> -->
					<label>类型</label>
					<select id="leixing6" name="leixing6">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url6" name="url6">
					<!-- <label>KEY</label><input type="text" id="key6" name="key6"> --><p>
 					<label>二级菜单</label><input type="text" id="erji7" name="erji7">
 					<label>类型</label>
					<select id="leixing7" name="leixing7">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url7" name="url7">
					<!-- <label>KEY</label><input type="text" id="key7" name="key7"> --><p>
					<label>二级菜单</label><input type="text" id="erji8" name="erji8">
					<label>类型</label>
					<select id="leixing8" name="leixing8">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url8" name="url8">
					<!-- <label>KEY</label><input type="text" id="key8" name="key8"> --><p>
					<label>二级菜单</label><input type="text" id="erji9" name="erji9">
					<label>类型</label>
					<select id="leixing9" name="leixing9">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url9" name="url9">
					<!-- <label>KEY</label><input type="text" id="key9" name="key9"> --><p>
					<label>二级菜单</label><input type="text"id="erji10" name="erji10" >
					<label>类型</label>
					<select id="leixing10" name="leixing10">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url10" name="url10">
					<!-- <label>KEY</label><input type="text" id="key10" name="key10"> --><p>
					
					<div class="divider"></div>
					<!-- 第三个按钮 -->
					<p><label>(3)一级菜单:</label><input type="text" id="yiji3" name="yiji3" ></p>
					<div class="divider"></div>
					<label>二级菜单</label><input type="text" id="erji11" name="erji11">
					<!-- <input type="text" id="leixing" name="leixing"> -->
					<label>类型</label>
					<select id="leixing11" name="leixing11">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url11" name="url11">
				<!-- 	<label>KEY</label><input type="text" id="key11" name="key11"> --><p>
 					<label>二级菜单</label><input type="text" id="erji12" name="erji12">
 					<label>类型</label>
					<select id="leixing12" name="leixing12">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url12" name="url12">
					<!-- <label>KEY</label><input type="text" id="key12" name="key12"> --><p>
					<label>二级菜单</label><input type="text" id="erji13" name="erji13">
					<label>类型</label>
					<select id="leixing13" name="leixing13">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url13" name="url13">
					<!-- <label>KEY</label><input type="text" id="key13" name="key13"> --><p>
					<label>二级菜单</label><input type="text" id="erji14" name="erji14">
					<label>类型</label>
					<select id="leixing14" name="leixing14">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url14" name="url14">
					<!-- <label>KEY</label><input type="text" id="key14" name="key14"> --><p>
					<label>二级菜单</label><input type="text" id="erji15" name="erji15">
					<label>类型</label>
					<select id="leixing15" name="leixing15">
					<option id=""></option>
					<option id="view">view</option>
					</select>
					<label>URL</label><input type="text" id="url15" name="url15">
					<!-- <label>KEY</label><input type="text" id="key15" name="key15"> --><p>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="bn">
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
</body>
		
		<script type="text/javascript">
		$("#bn").click(function(){
			var yiji1=$("#yiji1").val();
			var erji1=$("#erji1").val();
			var leixing1=$("#leixing1").val();
			var url1=$("#url1").val();
			
			var yiji2=$("#yiji2").val();
			var erji2=$("#erji6").val();
			var leixing2=$("#leixing6").val();
			var url2=$("#url6").val();
			
			
			var yiji3=$("#yiji3").val();
			var erji3=$("#erji11").val();
			var leixing3=$("#leixing11").val();
			var url3=$("#url11").val();

			
			if (yiji1=='') {
				alert('一级菜单不能为空!');
				$(":text[id=yiji1]").focus();  
				return;
			}
			
			if (erji1=='') {
				alert('二级菜单不能为空!');
				$(":text[id=erji1]").focus();  
				return;
			}
			
			if (leixing1=='') {
				alert('类型不能为空!');
				$(":text[id=leixing1]").focus();  
				return;
			}
			if (url1=='') {
				alert('url不能为空!');
				$(":text[id=url1]").focus();  
				return;
			}
			if(!url1.match(/^((https?|http):\/\/)([a-z]([a-z0-9\-]*[\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\/[a-z0-9_\-\.~]+)*(\/([a-z0-9_\-\.]*)(\?[a-z0-9+_\-\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$/)){
				alert('网址不正确！！！');
				$(":text[id=url1]").focus();  
			return;
			}	
			
			if (yiji2=='') {
				alert('(2)一级菜单不能为空!');
				$(":text[id=yiji2]").focus();  
				return;
			}
			
			if (erji2=='') {
				alert('(2)二级菜单不能为空!');
				$(":text[id=erji6]").focus();  
				return;
			}
			
			if (leixing2=='') {
				alert('(2)类型不能为空!');
				$(":text[id=leixing6]").focus();  
				return;
			}
			if (url2=='') {
				alert('(2)url不能为空!');
				$(":text[id=url6]").focus();  
				return;
			}
			if(!url2.match(/^((https?|http):\/\/)([a-z]([a-z0-9\-]*[\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\/[a-z0-9_\-\.~]+)*(\/([a-z0-9_\-\.]*)(\?[a-z0-9+_\-\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$/)){
				alert('网址不正确！！！');
				$(":text[id=url6]").focus(); 
			return;
			}
			
			
			if (yiji3=='') {
				alert('(3)一级菜单不能为空!');
				$(":text[id=yiji3]").focus();  
				return;
			}
			
			if (erji3=='') {
				alert('(3)二级菜单不能为空!');
				$(":text[id=erji11]").focus();  
				return;
			}
			
			if (leixing3=='') {
				alert('(3)类型不能为空!');
				$(":text[id=leixing11]").focus();  
				return;
			}
			if (url3=='') {
				alert('(3)url不能为空!');
				$(":text[id=url11]").focus();  
				return;
			}
			if(!url3.match(/^((https?|http):\/\/)([a-z]([a-z0-9\-]*[\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\/[a-z0-9_\-\.~]+)*(\/([a-z0-9_\-\.]*)(\?[a-z0-9+_\-\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$/)){
				alert('网址不正确！！！');
				$(":text[id=url11]").focus();  
			return;
			}
			
			$.ajax({
				type : "POST",
				dataType : 'json',
				url : "module/o_getWeiXins",
				data : $('#tenderFrm').serialize(),
				error : function(request) {
					alert(data.msg);
				},
				success : function(data) {
					if (data.code == '200') {
						alert(data.msg);
						window.location.reload();
					} else {
						alert("错误状态码："+data.code+"\n错误消息："+data.msg);
					}
				}
			});

		});
		</script>
		
		


		<script type="text/javascript">
		function saveTransfer() {
			
			var ssssss=$('#tenderFrm').serialize();
			alert(ssssss);
			$.ajax({
				type : "POST",
				dataType : 'json',
				url : "module/o_getWeiXins",
				data : $('#tenderFrm').serialize(),
				error : function(request) {
					alert(data.msg);
				},
				success : function(data) {
					if (data.code == '200') {
						alert(data.msg);
						window.location.reload();
					} else {
						alert("错误状态码："+data.code+"\n错误消息："+data.msg);
					}
				}
			});
		}
		</script>
</html>