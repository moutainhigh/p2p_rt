<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>${showTitle }-问答</title>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/wd.css" />
</head>

<body>
	<jsp:include page="/top.do"></jsp:include>
<%@include file="tit.jsp"%>
	<div class="gywm-bao">
		<div class="wdzh-bao">
			<!-- <div class="gywm-right-bt" style="width: 1020px;">
				<span
					style="border-right: 5px solid #ff4922; display: block; width: 5px; height: 24px; float: left; margin-right: 10px;"></span>
				<span style="line-height: 24px; font-size: 18px; color: #ff4922;">问答专区</span>
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
								<span><i>${totalQuestionsCount }</i>个问题</span><br> 
								<span><i>${totalUsersCount }</i>人参与</span>
							</div>
						</div>
					</div>
				</div>

				<div class="wdnr-bao">
					<div class="list-left">
						<div class="list-lefttop">
							<span> 
								<img src="${frontPath }/images/wdzq/wdzq_14.jpg" width="21" height="13" />
							</span> 
							<a href="index.do"><span>问答</span></a>
						</div>
						
						 <div class="ssjg-k">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${record } 条结果 （关键词"<b style=" color:#ff4922;">${keyword }</b>"）</div>

						<!-- 问题 -->
						<div class="list-nrb">
							<div class="list-nrk">

								<ul id="questionList"></ul>

							</div>
						</div>
					</div>

					<!-- 问答排行 -->
					<div class="list-right">
						<div class="list-right-top">
							<div class="wdph-top">
								<b style="font-size: 16px; width: 130px;">问答排行</b> 
								<b class="ph-k ph-k-2" id="allRank">总排行</b>
								<b class="ph-k" id="monthRank">上月排行</b>
							</div>
							
							<ul id="topUser"></ul>

						</div>

						<!-- 热门标签 -->
						<div class="rmbq-k">
							<div class="bq-sk">
								<h3>热门标签</h3>
								<%-- <c:forEach items="${tags }" var="t">
									<a href="tag/${t.tagCode }"><span>${t.tagName }</span></a>
								</c:forEach> --%>
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
	var keyword = '${keyword}';
	$(function() {
		getTopUser('getTopUser.do');
		getTags();
		var data = {
			"tojsp" : 1,
			"content" : keyword
		};
		getPageFrom(data, "getQuestionData.do", getAllQuestions, "questionList");
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

	function getAllQuestions(data) {
		
		var img = "${frontPath }/images/wdzq/index_11.png";
		if(data.userImg != null && data.userImg != ''){
			img = '${path}'+data.userImg;
		}

		var content = "";
		content = content
				+ "<div class=\"wt-k\">"
				
				//left
				+ "<div class=\"wt-left\">"
				+ "<dl>"
				+ "<dd>"
				+ "<img src="+img+" width=\"66\" height=\"64\" />"
				+ "</dd>"
				+ "<dt style=\"color: #ff4922;\"><a href=\"user/"+data.userId+".do\">" + data.userName + "</a></dt>" 
				+ "<dt>" + toDate(data.questionDate) + "</dt>"
				+ "</dl>" 
				+ "</div>"
				
				+ "<div class=\"wt-rgiht\">"
				+ "<div class=\"wt-zhongbj\">"
				+ "<div class=\"wt-wenzik\">"
				+ "<span style=\"font-size: 15px;font-weight:700; color: #666; line-height: 40px;\"><a href=\"question/"+data.id+".do\">"+data.title+"</a></span>"
				+ "<span>" + data.content + "</span>"
				+ "</div>"
				
				+ "<div class=\"wt-ank\">";
				
				$.each(data.tags,function(i,tag){
					content = content + "<div class=\"wt-an-sk\" onclick=\"location.href=\'tag/"+tag.id+".do\';\" style=\"background-color: #e7ecef;cursor:pointer;\">"
					+ "<img src=\"${frontPath }/images/ico-bq.jpg\" style=\"width:10px; height:10px;\" />  "
					+ tag.tagName
					+ "</div>";
				});
				
content =content + "</div>"
				
				+ "<div class=\"wt-ank\">" 
				+ "<div class=\"wt-an-sk\">"
				+ "回答<span style=\"color: #ff4922;\"> " + data.replyNum + "</span>" 
				+ "</div>" 
				+ "<div class=\"wt-an-sk\">"
				+ "浏览<span style=\"color: #ff4922;\"> " + data.broswerNum + "</span>" 
				+ "</div>" 
				+ "<div class=\"wt-an-sk\">"
				+ "同问<span style=\"color: #ff4922;\"> " + data.sameNum + "</span>" 
				+ "</div>" 
				+ "</div>" 
				+ "</div>" 
				+ "</div>" 
				+ "</div>";
		
		return content;

	}
	
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
	}
	
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
	}
	
	$("#searchBtn").click(function(){
		var keyword = $("#keyword").val();
		if($.trim(keyword) == ''){
			alert("请输入想问的关键字!");
		}else{
			$("#searchForm").submit();
		}
	});
</script>
</html>