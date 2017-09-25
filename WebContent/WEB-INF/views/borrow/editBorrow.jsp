<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = path+"/common/views";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>发标</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="issueForm" name="issueForm" method="post" action="borrow/${PRE_PATH_EDIT }updateBorrow"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" value="${borrow.id}" />
				<input type="hidden" name="borrowTypeCode" value="${borrow.borrowType.code}"/>
				<input type="hidden" name="userId" value="${borrow.userId }"/>
				<div class="pageFormContent" layoutH="55">

				<fieldset>
						<legend>借款人</legend>
						<dl style="width:100%;">
							<dt  style="width:90px;">请选择借款人：</dt>
							<dd class="unit" style="width:444px;">
								<span id="showCheckUserName" style="color: red; float: left;"></span>
					      		<a class="btnLook" width="700" lookupgroup="user" href="user/v_getAllowBorrowUserList">会员列表</a>
					      		<span id="showCheckUserName" style="color: red; float: left;">（当没有选择借款人，默认是平台发布）</span>
							</dd>
						</dl>
				</fieldset>
				<fieldset>
					<legend>贷款信息</legend>
                    <dl>
                        <dt>信用类型:</dt>
                        <dd class="unit" style="width:400px;">
                            <select name="creditType" class="required" id="creditType">
	                            <c:forEach items="${BORROW_ALL_CREDIT_TYPE }" var="item">
	                                <option value="${item.key }" <c:if test="${item.key == borrow.creditType}">selected="selected"</c:if>>${item.value }</option>
	                            </c:forEach>
                            </select>
                        </dd>
                    </dl>
                    <dl></dl>
					<dl>
						<dt>贷款总额:</dt>
						<dd class="unit">
							<input name="borrowSum" id="borrowSum" class="required digits" type="text" value='<fmt:parseNumber integerOnly="true" type="number" value="${borrow.borrowSum }"/>'/>
						</dd>
					</dl>
					<dl>
						<dt>年利率:</dt>
						<dd class="unit">
							<input name="annualInterestRate" size="20" id="annualInterestRate" class="required number" type="text" value="${borrow.annualInterestRate }"/>%
						</dd>
					</dl>
					<c:if test="${borrowType.code eq BORROW_TYPE_ZHUAN }">
					<input name="isDay" type="hidden" value="2"/>
						<dl>
							<dt>每份金额:</dt>
							<dd class="unit">
								<input name="amountPerCopies" id="amountPerCopies" class="required digits" min="100" type="text" value="${borrow.amountPerCopies }"/>
							</dd>
						</dl>
					</c:if>
					<c:if test="${borrowType.code ne BORROW_TYPE_ZHUAN }">
						<dl>
							<dt>是否天标:</dt>
							<dd class="unit">
								<input name="isDay" type="radio" value="1" onclick="changeIsDay();" <c:if test="${borrow.isDay ==1 }">checked="checked"</c:if>/>是 
								<input name="isDay" type="radio" value="2" onclick="changeIsDay();" <c:if test="${borrow.isDay ==2 }">checked="checked"</c:if>/>否
							</dd>
						</dl>
					</c:if>
					<dl  style="width:550px;">
						<dt>贷款期限:</dt>
						<dd class="unit" style="width:400px;">
							<select name="borrowTimeLimit" class="required" id="monthSelect" style="display: block;">
								<option value="1" <c:if test="${borrow.borrowTimeLimit ==1 }">selected="selected"</c:if>>1个月</option>
								<option value="2" <c:if test="${borrow.borrowTimeLimit ==2 }">selected="selected"</c:if>>2个月</option>
								<option value="3" <c:if test="${borrow.borrowTimeLimit ==3 }">selected="selected"</c:if>>3个月</option>
								<option value="4" <c:if test="${borrow.borrowTimeLimit ==4 }">selected="selected"</c:if>>4个月</option>
								<option value="5" <c:if test="${borrow.borrowTimeLimit ==5 }">selected="selected"</c:if>>5个月</option>
								<option value="6" <c:if test="${borrow.borrowTimeLimit ==6 }">selected="selected"</c:if>>6个月</option>
								<option value="7" <c:if test="${borrow.borrowTimeLimit ==7 }">selected="selected"</c:if>>7个月</option>
								<option value="8" <c:if test="${borrow.borrowTimeLimit ==8 }">selected="selected"</c:if>>8个月</option>
								<option value="9" <c:if test="${borrow.borrowTimeLimit ==9 }">selected="selected"</c:if>>9个月</option>
								<option value="10" <c:if test="${borrow.borrowTimeLimit ==10 }">selected="selected"</c:if>>10个月</option>
								<option value="11" <c:if test="${borrow.borrowTimeLimit ==11 }">selected="selected"</c:if>>11个月</option>
								<option value="12" <c:if test="${borrow.borrowTimeLimit ==12 }">selected="selected"</c:if>>12个月</option>
								<option value="24" <c:if test="${borrow.borrowTimeLimit ==13 }">selected="selected"</c:if>>24个月</option>
								<option value="36" <c:if test="${borrow.borrowTimeLimit ==14 }">selected="selected"</c:if>>36个月</option>
							</select><span id="showCheckUserName" style="color: red; float: left;">*</span> 
							<label id="dayAlert" style="color: red; width: 200px; display: none">系统约定按每月30天计算利率</label>
						</dd>
					</dl>
					<dl>
						<dt>贷款用途:</dt>
						<dd class="unit">
							<select name="borrowUse" class="required">
								<c:forEach var="item" items="${BORROW_ALL_BORROW_USE}"> 
									<option value="${item.key}" <c:if test="${item.key == borrow.borrowUse }">selected="selected"</c:if>>${item.value}</option>
								</c:forEach>
							</select><span  id="showCheckUserName" style="color: red; float: left;">*</span> 
						</dd>
					</dl>
					<dl>
						<dt>投标密码:</dt>
						<dd class="unit">
							<input name="tenderPassword" class="alphanumeric" id="tenderPassword" type="password" value="${borrow.tenderPassword }"/>
						</dd>
					</dl>
					<dl>
						<dt>最低投标金额:</dt>
						<dd class="unit">
							<select name="minAmount">
								<option  value="1" <c:if test="${borrow.minAmount ==1 }">selected="selected"</c:if>>1元</option>	
								<option value="50" <c:if test="${borrow.minAmount ==50 }">selected="selected"</c:if>>50元</option>
								<option value="100" <c:if test="${borrow.minAmount ==100 }">selected="selected"</c:if>>100元</option>
								<option value="150" <c:if test="${borrow.minAmount ==150 }">selected="selected"</c:if>>150元</option>
								<option value="200" <c:if test="${borrow.minAmount ==200 }">selected="selected"</c:if>>200元</option>
								<option value="300" <c:if test="${borrow.minAmount ==300 }">selected="selected"</c:if>>300元</option>
								<option value="500" <c:if test="${borrow.minAmount ==500 }">selected="selected"</c:if>>500元</option>
								<option value="1000" <c:if test="${borrow.minAmount ==1000 }">selected="selected"</c:if>>1000元</option>
								<option value="1500" <c:if test="${borrow.minAmount ==1500 }">selected="selected"</c:if>>1500元</option>
								<option value="3000" <c:if test="${borrow.minAmount ==3000 }">selected="selected"</c:if>>3000元</option>
								<option value="5000" <c:if test="${borrow.minAmount ==5000 }">selected="selected"</c:if>>5000元</option>
								<option value="10000" <c:if test="${borrow.minAmount ==10000 }">selected="selected"</c:if>>10000元</option>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>最多投标金额:</dt>
						<dd class="unit">
							<select name="maxAmount">
								<option value="0" <c:if test="${borrow.maxAmount ==0 }">selected="selected"</c:if>>没有限制</option>
								<option value="2000" <c:if test="${borrow.maxAmount ==2000 }">selected="selected"</c:if>>2000元</option>
								<option value="5000" <c:if test="${borrow.maxAmount ==5000 }">selected="selected"</c:if>>5000元</option>
								<option value="10000" <c:if test="${borrow.maxAmount ==10000 }">selected="selected"</c:if>>10000元</option>
								<option value="15000" <c:if test="${borrow.maxAmount ==15000 }">selected="selected"</c:if>>15000元</option>
								<option value="20000" <c:if test="${borrow.maxAmount ==20000 }">selected="selected"</c:if>>20000元</option>
								<option value="50000" <c:if test="${borrow.maxAmount ==50000 }">selected="selected"</c:if>>50000元</option>
								<option value="100000" <c:if test="${borrow.maxAmount ==100000 }">selected="selected"</c:if>>100000元</option>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>有效时间:</dt>
						<dd class="unit">
							<select name="validTime" class="required">
								<option value="1" <c:if test="${borrow.validTime ==1 }">selected="selected"</c:if>>1天</option>
								<option value="2" <c:if test="${borrow.validTime ==2 }">selected="selected"</c:if>>2天</option>
								<option value="3" <c:if test="${borrow.validTime ==3 }">selected="selected"</c:if>>3天</option>
								<option value="4" <c:if test="${borrow.validTime ==4 }">selected="selected"</c:if>>4天</option>
								<option value="5" <c:if test="${borrow.validTime ==5 }">selected="selected"</c:if>>5天</option>
								<option value="6" <c:if test="${borrow.validTime ==6 }">selected="selected"</c:if>>6天</option>
								<option value="7" <c:if test="${borrow.validTime ==7 }">selected="selected"</c:if>>7天</option>
							</select><span  id="showCheckUserName" style="color: red; float: left;">*</span>
						</dd>
					</dl>
					<dl>
						<dt>还款方式:</dt>
						<dd class="unit">
							<select name="repaymentStyle"  class="required">
							<c:forEach var="item" items="${BORROW_ALL_REPAYMENT_STYLE}"> 
									<option value="${item.key}" <c:if test="${item.key == borrow.repaymentStyle }">selected="selected"</c:if>>${item.value}</option>
								</c:forEach>
							</select><span  id="showCheckUserName" style="color: red; float: left;">*</span>
						</dd>
					</dl>
				</fieldset>
				<fieldset>
					<legend>投标奖励</legend>
					<p style="width: 25%">
						<input type="radio" name="tenderAward" value="1" onclick="changeTenderAward(1);" <c:if test="${borrow.tenderAward ==1 }">checked="checked"</c:if>/>不设置奖励
					</p>
					<p style="width: 30%">
						<input type="radio" name="tenderAward" onclick="changeTenderAward(2);" value="2" <c:if test="${borrow.tenderAward ==2 }">checked="checked"</c:if>/>按投标金额比例奖励 
						<span id="rateDiv" <c:if test="${borrow.tenderAward ==1 || borrow.tenderAward ==3 }">style="display: none;"</c:if>> 
							<input name="proportionRate" size="10" id="proportionRate" value="${borrow.proportionRate }"/>%
						</span>
					</p>
					<p style="width: 33%">
						<input type="radio" name="tenderAward" onclick="changeTenderAward(3);" value="3" <c:if test="${borrow.tenderAward ==3 }">checked="checked"</c:if>/>按固定金额分摊奖励 
						<span id="partDiv" <c:if test="${borrow.tenderAward ==1 || borrow.tenderAward ==2 }">style="display: none;"</c:if>> 
							<input name="partAmount" size="10" id="partAmount" value="${borrow.partAmount }"/>元
						</span>
					</p>
				</fieldset>
				<fieldset>
					<legend>账户信息</legend>
					<p style="width: 22%;">
						<input type="checkbox" name="openAccount" id="openAccount" value="1" <c:if test="${borrow.openAccount ==1 }">checked="checked"</c:if>/>公开我的帐户资金情况
					</p>
					<p style="width: 22%;">
						<input type="checkbox" name="openBorrow" id="openBorrow" value="1" <c:if test="${borrow.openBorrow ==1 }">checked="checked"</c:if>/>公开我的借款资金情况
					</p>
					<p style="width: 22%;">
						<input type="checkbox" name="openTender" id="openTender"  value="1" <c:if test="${borrow.openTender ==1 }">checked="checked"</c:if>/>公开我的投标资金情况
					</p>
					<p style="width: 22%;">
						<input type="checkbox" name="openCredit" id="openCredit" value="1" <c:if test="${borrow.openCredit ==1 }">checked="checked"</c:if>/>公开我的信用额度情况
					</p>
				</fieldset>
				<fieldset>
					<legend>详细信息</legend>
					<dl class="nowrap">
						<dt>贷款标题:</dt>
						<dd>
							<input name="borrowTitle" id="borrowTitle" type="text" size="50" class="required" value="${borrow.borrowTitle }"/>
						</dd>
					</dl>

					<dl class="nowrap">
						<dt>详细信息:</dt>
						<dd>
							<textarea rows="16" cols="100" class="editor" id="borrowContent" name="borrowContent" upImgUrl="upload/editorImg">${borrow.borrowContent }</textarea>
						</dd>
					</dl>
					<dl class="nowrap">
						<p>
							<input type="file" name="uploadifys" id="uploadifys" />
							<span id="result" style="font-size: 13px; color: red"></span></p>
							<div class="divider" style="border-style:none;"></div>
							<div id="fileQueues"
								style="width: 740px auto; <c:if test="${not empty  attachList}"> border: 1px solid #99BBE8;</c:if>">
								<ul id="fileULs" style="">
									<c:forEach var="attach" items="${attachList }">
										<li id="${attach.id }">
											<input type="hidden" name="attachPath" value="${attach.attachPath}" style="width: 300px;"></input> 
											<input type="hidden" name='attachFileType' value="${attach.attachFileType}"></input>
											<%-- <input name="attachId" value="${attach.id }"/> --%>
											&nbsp;附件名：<input name="attachRealTitle" value="${attach.attachRealTitle}" style="width: 200px; border: none;"></input> 
											&nbsp;重命名： <input name="attachTitle" value="${attach.attachTitle}" style="width: 200px;"></input> 
											&nbsp; 排序： <input name="attachSequence" value="${attach.attachSequence}" style="width: 20px;"></input> 
											<a href="javaScript:delet(${attach.id })">&nbsp;删除</a>
											<div style="height: 10px;"></div>
										</li>
									</c:forEach>
								</ul>
							</div>
						<p>
					</dl>
					<dl class="nowrap">
						<dt>验证码:</dt>
						<dd>
							<input maxlength="4" style="width: 60px; margin-bottom: 10px;"
								name="captcha" type="text" id="captcha" class="required">
							<img id="captchaImg" style="margin-top: -5px;"
								src="<%=path%>/captcha.svl"
								onclick="this.src='<%=request.getContextPath()%>/captcha.svl?d='+new Date()*1"
								valign="bottom" alt="点击更新" title="点击更新" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt></dt>
						<dd>
							<input type="file" name="uploadify" id="uploadify" /> 
						    <div id="fileQueue" style="width: 740px auto;">
						      <ul id="fileUL" style="list-style: none;">
							        <%-- <li id='img_1'> 
							          <img src='<%=path %>${.borrowPicture}' style="width: 200px;height: 200px;"></img>
							          <input type="hidden" name="borrowPicture" value="${borrowType.borrowPicture}"/>&nbsp;&nbsp;&nbsp;
							          &nbsp;&nbsp;<a href="javaScript:delet('img_1')" >删除</a>
							        </li> --%>
						       </ul>
						    </div>
						</dd>
					</dl>
				</fieldset>
			</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="formSubmit();">重新发标</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
		var indx = 0;
		$(document).ready(function() {
			$("#uploadifys").uploadify({  
		        'uploader'       : '<%=basePath %>/uploadify/scripts/uploadify.swf',  
		        'script'         : '<%=path %>${ADMIN_URL }/upload/uploadFiles;jsessionid=<%=session.getId()%>',  
		        'cancelImg'      : '<%=basePath %>/uploadify/cancel.png',  
		        'buttonImg'      : '<%=basePath %>/uploadify/buttonImg.png',  
		        'folder'         : '/jxdBlog/photos',  
		        'queueID'        : 'fileQueues',  
		        'auto'           : true,  
		        'multi'          : true,  
		        'wmode'          : 'transparent',  
		        'simUploadLimit' : 20,  
		        'fileExt'        : '*.jpg;*.jpeg;*.gif;*.png;*.bmp;*.xls;*.doc;*.ppt;*.xlsx;*.docx;*.pptx;*.wps;*.pdf;*.rar;*.zip',  
		        'fileDesc'       : '请上传合法文件(*.jpg;*.jpeg;*.gif;*.png;*.xls;*.doc;*.ppt;*.xlsx;*.docx;*.pptx;*.rar;*.zip)',  
		        'sizeLimit'		 : 20000000,    
		        'onComplete'  :function(event,queueId,fileObj,response,data){  
		        	var retJson = eval(response)[0];
		            /* $('#result').html('还剩'+data.fileCount +'个附件没上传'); */
		            
		            $('#fileULs').append("<li id='"+queueId+"'>&nbsp;附件名：<input name='attachRealTitle' value='"+retJson.filename+"' ></input>"
		            +"&nbsp;&nbsp;重命名：<input name='attachTitle' value='"+retJson.filename+"'></input>"
		            +"<input name='attachPath' type='hidden' value='"+retJson.filepath+"' ></input>"
		            
		            +"<input name='attachFileType'  type='hidden' value='"+retJson.suffix+"'></input>"
		            
		            + "&nbsp;&nbsp;&nbsp;&nbsp;排序：<input name='attachSequence' value='0'></input>"
		             +"&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('"+queueId+"')\" >删除</a></li>"
		             ); 
		            
		            $('input[name="attachRealTitle"]').css("width","200px");
		            $('input[name="attachRealTitle"]').css("border","none");
		            $('input[name="attachTitle"]').css("width","200px");
		            $('input[name="attachSequence"]').css("width","20px");
		           
		            
		        }
		    });
			
		    $("#uploadify").uploadify({  
		        'uploader'       : '<%=basePath %>/uploadify/scripts/uploadify.swf',  
		        'script'         : '<%=path %>${ADMIN_URL }/upload/uploadFiles;jsessionid=<%=session.getId()%>',  
		        'cancelImg'      : '<%=basePath %>/uploadify/cancel.png',  
		        'buttonImg'      : '<%=basePath %>/uploadify/buttonImglogo.png',  
		        'folder'         : '/jxdBlog/photos',  
		        'queueID'        : 'fileQueue',  
		        'auto'           : true,  
		        'multi'          : true,  
		        'wmode'          : 'transparent',  
		        'simUploadLimit' : 999,  
		        'fileExt'        : '*.png;*.gif;*.jpg;*.jpeg',  
		        'fileDesc'       : '*.png,*.gif,*.jpg,*.jpeg',  
		        'onComplete'  :function(event,queueId,fileObj,response,data){
		        	indx++;
		        	var retJson = eval(response)[0];
		        	$("#fileUL").append("<li  id='li_"+indx+"'> <img src='<%=path %>"+retJson.filepath+"' style='width: 200px;height: 200px;' ></img>"
		        	+"<input type='hidden' name='borrowPicture' type='text' value='"+retJson.filepath+"' ></input>"
		        	+"&nbsp;&nbsp;&nbsp;<a href=\"javaScript:delet('li_"+indx+"')\" >删除</a>"
		        	+"</li>");
		        	
		        }  
		    }); 
		});
		function delet(attachId){
			$("#"+attachId).remove();
		}
		//是否为天标，借款期限改变
		function changeIsDay() {
			var val = $("input[name='isDay']:checked").val();
			if (val == 1) { //天标
				initBorrowTimeLimit(30, "天");
				$("#dayAlert").show();
			} else {
				initBorrowTimeLimit(12, "个月");
				$("#dayAlert").hide();
			}
		}
		function initBorrowTimeLimit(len, show) {
			var timeLimitSelect = $("select[name='borrowTimeLimit']");
			timeLimitSelect.html(getOption("", "--贷款期限--"));
			for (i = 1; i <= len; i++) {
				timeLimitSelect.html(timeLimitSelect.html()
						+ getOption(i, i + show));
			}
			timeLimitSelect.html(timeLimitSelect.html()
					+ getOption(45, 45 + show));
		}
		function getOption(value, text) {
			return "<option value=\""+value+"\">" + text + "</option>";
		}
	
		//设置投资奖励
		var lastAwardType = "";
		function changeTenderAward(val) {
			if(lastAwardType == val){
				return;
			}
			lastAwardType = val;
			$("#rateDiv").find("input").val("");
			$("#partDiv").find("input").val("");
			$("#rateDiv").find("input").removeClass("required").removeClass("number");
			$("#partDiv").find("input").removeClass("required").removeClass("number");
			if (val == 1) {
				$("#rateDiv").hide();
				$("#partDiv").hide();
			}
			if (val == 2) {
				$("#rateDiv").show(); 
				$("#partDiv").hide();
				$("#rateDiv").find("input").addClass("required").addClass("number");
				$("#partDiv").find("input").removeClass("required").removeClass("number");
			}
			if (val == 3) {
				$("#rateDiv").hide();
				$("#partDiv").show();
				$("#rateDiv").find("input").removeClass("required").removeClass("number");
				$("#partDiv").find("input").addClass("required").addClass("number");
			}
		}
		function formSubmit() {
			$("#issueForm").submit();
		}
	</script>
	<style type="text/css">
		.pageFormContent p{width:50%;}
	</style>
</html>