<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>问个问题 - ${showTitle }</title>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/wd.css" />

<!-- chosen -->
<link rel="stylesheet" type="text/css" href="${frontPath }/plugins/chosen/chosen.css" />
<script charset="utf-8" src="${frontPath }/plugins/chosen/chosen.jquery.js"></script>

<!-- ckeditor -->
<script type="text/javascript" src="${frontPath }/plugins/ckeditor/ckeditor.js"></script>
</head>

<body>
	<jsp:include page="/top.do"></jsp:include>
<%@include file="tit.jsp"%>
	<div class="gywm-bao">
		<div class="wdzh-bao">
			<!-- <div class="gywm-right-bt" style="width: 1020px;">
				<span
					style="border-right: 5px solid #ff4922; display: block; width: 5px; height: 24px; float: left; margin-right: 10px;"></span><span
					style="line-height: 24px; font-size: 18px; color: #ff4922;">问答专区</span>
			</div> -->
			<div class="wdzh-dabao">
				<div class="ssuo-bao">
					<div class="ss-bao">
						<div class="ss-k">
							<div class="ss-input">
								<b class="fdj-ico"></b> 
								<form action="search.do" method="post" id="searchForm">
								<input style="color: #ccc;" name="keyword" type="text" class="formIpt" id="keyword"
									title="请输入关键字" value="${keyword }"
									maxlength="100" autocomplete="off">
								</form>
							</div>
							<div class="ss-anniou" id="searchBtn" style="cursor: pointer;">
								<a style="color: #fff;" id="searchBtn">搜索</a>
							</div>
						</div>
					</div>
					<div class="ss-right">
						<div class="right-tw">
							<c:if test="${not empty FRONT_USER_SESSION }">
							<div class="tw-anniou" style="cursor: pointer;" onclick="location.href='ask.do'">
								<a style="color: #fff;" href="ask.do">我要提问</a>
							</div>
							</c:if>
							<c:if test="${empty FRONT_USER_SESSION }">
							<div class="tw-anniou" style="cursor: pointer;" id="needLogin">
								<a style="color: #fff;">登录后可以提问</a>
							</div>
							</c:if>
							<div class="tw-rs">
								<span><i>${totalQuestionsCount }</i>个问题</span><br/> 
								<span><i>${totalUsersCount }</i>人参与</span>
							</div>
						</div>
					</div>
				</div>
				
				
							
				<div class="wdnr-bao">
					<form action="askHandler.do" method="post" id="askForm">
					<div class="list-left">
						<div style="width: 700px;" class="tjhd-top">问个问题</div>
						<div class="tj-top">
							<input style="color: #666;" name="title" id="title" type="text" class="tj-formIpt" placeholder="&nbsp;&nbsp;你有什么想问的？" maxlength="100">
						</div>
						<div class="bjk-tu">
							<textarea id="content" name="content" style="width: 650px;height:250px;"></textarea>	
						</div>

						<div class="tjbq-k">
							<div class="tjbq-topbj">
								<b class="tjbq-xh-k">指定标签</b>
							</div>
							<div class="bq-wenzi">
								<span style="width: 670px;float: left;font-size: 14px;color: #000;line-height: 35px;">您可以最多选择5个标签 </span> 
									<select class="tag-select" name="tags" multiple="true" data-placeholder="请选择标签." style="width:660px;" >
										<c:forEach var="tag" items="${tagList }">
											<option value="${tag.id }">${tag.tagName }</option>
										</c:forEach>
									</select>
							</div>
							
							<div class="zdbq-anniou">
								<div class="bq-xanniou" style="cursor: pointer;" id="subBut">提交</div>
								<div style="background-color: #999999;cursor: pointer;" class="bq-xanniou" id="canBut">取消</div>
							</div>
							
						</div>
					</div>
					</form>

					<div class="list-right">
						<div class="list-right-top">
							<div class="wdph-top">
								<b style="font-size: 16px; width: 130px;">问答排行</b> 
								<b class="ph-k ph-k-2" id="allRank">总排行</b>
								<b class="ph-k" id="monthRank">上月排行</b>
							</div>
							
							<ul id="topUser"></ul>
							
						</div>
						<div class="rmbq-k">
							<div class="bq-sk">
								<h3>热门标签</h3>
								
								<ul id="tagList"></ul>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
$("#needLogin").click(function(){
	confirmc("需要登录后才可以提问!是否立即登录",function (){window.location.href = '${path }/login'});
});
	$(function(){
		CKEDITOR.replace('content');
		
		 getTags();
		 getTopUser('getTopUser.do');
	});
	
	$("#monthRank").click(function(){
		$("#allRank").removeClass("ph-k-2");
		$("#monthRank").addClass("ph-k-2");
		
		getTopUser('getTopUserMonth.do');
	});
	
	$("#allRank").click(function(){
		$("#monthRank").removeClass("ph-k-2");
		$("#allRank").addClass("ph-k-2");
		
		getTopUser('getTopUser.do');
	});

	$(".tag-select").chosen({
		no_results_text : "没有相关结果",
		max_selected_options : 5
	});
	$(".tag-select").chosen().change();
	
	$("#subBut").bind('click', function() {
		var title = $("#title").val();
		var content = $("#content").val();
		if ($.trim(title) == '') {
			alertc("请输入标题!");
		} else if ($.trim(content) == '') {
			//alert("请说点什么吧!");
			$("#askForm").submit();
		} else {
			$("#askForm").submit();
		}
	});
	
	function getTags(){
		$.get('getTags.do',null,function(data){
			content = "";
			if(data != null && data.length >0){
				$.each(data,function(i,tag){
					content += "<a href=\"tag/"+tag.id+".do\"><span>"+tag.tagName+"</span></a>";
				});
			}else{
				content = "<div style=\"margin-left:100px;margin-top:10px;color:#666\">暂无标签</div>";
			}
			$("#tagList").html(content);
		});
	};
	
	function getTopUser(url){
		$.get(url,null,function(data){
			content = "";
			if(data != null && data.length >0 ){
				$.each(data,function(i,user){
					
					var img = "${frontPath }/images/wdzq/index_11.png";
					if(user.userImg != null && user.userImg != ''){
						img = '${path}'+user.userImg;
					}
					
					content += "<div class=\"wd-list\">"
							+ "<span style=\"width: 34px;\">"
							+ "<img src="+img+" width=\"38\" height=\"45\" />"
							+ "</span>"
							+ "<a href=\"user/"+user.userId+".do\"><span>"+user.userName+"</span></a>"
							+ "<span>得票<b style=\"color: #ff4922;\">("+user.voteNum+")</b></span>"
							+ "<span>回答<b style=\"color: #ff4922;\">("+user.userCount+")</b></span>"
							+"</div>";
				});
			}else{
				content = "<div class=\"wd-list\" style=\"margin-top: 30px;margin-left: 110px;color: #666\">暂无排行</div>";
			}
			$("#topUser").html(content);
		});
	};
	
	$("#searchBtn").click(function(){
		var keyword = $("#keyword").val();
		if($.trim(keyword) == ''){
			alertc("请输入想问的关键字!");
		}else{
			$("#searchForm").submit();
		}
	});
</script>
</html>