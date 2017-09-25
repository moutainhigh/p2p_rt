<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>
<title>回答-${question.title }</title>
<link rel="stylesheet" type="text/css" href="${frontPath }/css/wd.css" />

<!-- ckeditor -->
<script type="text/javascript"src="${frontPath }/plugins/ckeditor/ckeditor.js"></script>
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
								<form action="../search.do" method="post" id="searchForm">
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
								<div class="tw-anniou" style="cursor: pointer;" onclick="location.href='../ask.do'">
									<a style="color: #fff;" href="../ask.do">我要提问</a>
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
					<div class="list-left">
						<div class="list-lefttop">
							<span>
								<img src="${frontPath }/images/wdzq/wdzq_14.jpg" width="21" height="13" />
							</span> 
							<a href="../index.do"><span style="cursor: pointer;">问答</span></a>
						</div>
						<div class="list-nrb">
							<div class="list-nrk">
								<div class="wt-k">
									<div class="wt-left">
										<dl>
											<dd>
												<c:if test="${empty user.avatarImg }">
													<img src="${frontPath }/images/default-avatar.jpg" width="66" height="66" />
												</c:if>
												<c:if test="${not empty user.avatarImg }">
													<img src="${path }${user.avatarImg }" width="66" height="66" />
												</c:if>
											</dd>
											<dt style="color: #ff4922;">
												<a href="../user/${question.userId }.do">${question.userName }</a>
											</dt>
											<dt>
												<fmt:formatDate value="${question.questionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
											</dt>
										</dl>
									</div>
									<div class="wt-rgiht">
										<div class="wt-zhongbj">
											<div class="wt-wenzik">
												<span style="font-size: 14px;font-weight:700; color: #666; line-height: 30px;">
													${question.title }
												</span> 
												<span style="width: 490px;">
													${question.content }
												</span>
												<span style="width: 45px; float: right;">
													<div id="tw">
														<div class="tw-k" id="same">${question.sameNum }</div>
														<div class="tw-wz" style="cursor: pointer;" onclick="same(${question.id})">同问</div>
													</div>
												</span>
											</div>
											<c:if test="${not empty tags }">
											<div class="wt-ank">
													<c:forEach var="t" items="${tags }">
														<div class="wt-an-sk" style="background-color: #e7ecef;cursor: pointer;" onclick="location.href='../tag/${t.tagId}.do'">
															<img src="${frontPath }/images/ico-bq.jpg" width="10" height="10" />
															${t.name }
														</div>
													</c:forEach>
											</div>
											</c:if>
										</div>
									</div>
								</div>
								
								<div class="twxq-k">
									<div class="twxq-top">
										<b style="font-size: 16px; width: 325px; text-align: left;">
											&nbsp;&nbsp;&nbsp;&nbsp;共${fn:length(answers)}条回答
										</b>
										<!-- <b class="xh-k" style="margin-right: 10px; background-color: #e7ecef; border-radius: 5px 5px 0px 0px;">最早</b>
										<b class="xh-k" style="margin-right: 10px;">最新</b>
										<b class="xh-k">得票最多</b> -->
									</div>
									
									<c:if test="${empty answers }">
										<div style="text-align: center;margin-bottom: 20px;margin-top: 30px;color: #666">
											暂时还没有人回答
										</div>
									</c:if>
									<c:if test="${not empty answers }">
									<c:forEach items="${answers }" var="a">
										<div class="syhd-bao">
											<div class="sy-hd-top">
												<dl>
													<dd>
														<c:if test="${empty a.userImg }">
															<img src="${frontPath }/images/default-avatar.jpg" width="66" height="66" />
														</c:if>
														<c:if test="${not empty a.userImg }">
															<img src="${path }${a.userImg }" width="66" height="66" />
														</c:if>
													</dd>
													<dt style="color: #ff4922;"><a href="../user/${a.userId }.do">${a.userName }</a></a></dt>
													<dt>
														<fmt:formatDate value="${a.answerDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
													</dt>
												</dl>
											</div>
											<div class="sy-neir">
												<span style="width: 550px; float: left;">
													${a.content }
												</span>
												<span>
													<div id="tw">
														<div class="tw-k" id="vote-${a.id }">${a.voteNum }</div>
														<div class="tw-wz" style="cursor: pointer;" onclick="vote(${a.id})" >投票</div>
													</div>
												</span>
											</div>
										</div>
									</c:forEach>
									</c:if>
									
								</div>
								
								<div class="sy-tjhd">
									<form action="../answerHandler.do" method="post" id="answerForm">
									<input type="hidden" name="questionId" value="${question.id }"/>
									<div class="tjhd-top">你可以在下面添加你的回答</div>
									<div class="bjk-tu">
										<textarea id="content" name="content" style="width: 615px;height: 200px;"></textarea>	
									</div>
									<c:if test="${not empty FRONT_USER_SESSION }">
										<div style="margin-top: 20px;cursor: pointer;" class="tw-anniou" id="subAnswer">提交你的回答</div>
									</c:if>
									<c:if test="${empty FRONT_USER_SESSION }">
										<div style="margin-top: 20px;cursor: pointer;" class="tw-anniou">登录后可回答此问题</div>
									</c:if>
									</form>
								</div>

							</div>
						</div>
					</div>
					
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
		 getTags();
		 getTopUser('../getTopUser.do');
		 CKEDITOR.replace('content',{ height: '175px', width: '615px' });
	});
	
	$("#subAnswer").bind('click',function(){
		$("#answerForm").submit();
	});
	
	$("#monthRank").click(function(){
		$("#allRank").removeClass("ph-k-2");
		$("#monthRank").addClass("ph-k-2");
		
		getTopUser('../getTopUserMonth.do');
	});
	
	$("#allRank").click(function(){
		$("#monthRank").removeClass("ph-k-2");
		$("#allRank").addClass("ph-k-2");
		
		getTopUser('../getTopUser.do');
	});

	function same(id) {
		$.ajax({
			url : '../questionSame.do',
			data : {
				id : id
			},
			type : 'post',
			success : function(data) {
				if (data != 'nologin') {
					$("#same").html(data);
				}else{
					alertc("请登录:)");
				}
			}
		});
	};

	function vote(aid) {
		$.ajax({
			url : '../answerVote.do',
			data : {
				aid : aid
			},
			type : 'post',
			success : function(data) {
				if (data != 'nologin') {
					$("#vote-" + aid + "").html(data);
				}else{
					alertc("请登录:)");
				}
			}
		});
	};

	function getTags(){
		$.get('../getTags.do',null,function(data){
			content = "";
			if(data != null && data.length >0){
				$.each(data,function(i,tag){
					content += "<a href=\"../tag/"+tag.id+".do\"><span>"+tag.tagName+"</span></a>";
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
							+ "<a href=\"../user/"+user.userId+".do\"><span>"+user.userName+"</span></a>"
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