<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../taglibs.jsp"%>
	<title>${channel.channelTitle }-${showTitle }</title>
	<script type="text/javascript" src="${frontPath }/js/index.js"></script>
	<style type="text/css">
		.on{
			background-color:#ee2121;
			color:white;
		}
	 	.j-ab-add{
			 padding:40px 30px;
			 color:#666; 
			 line-height:50px; 
			 font-size:15px;
		 }
		 .j-ab-add dl{
			 width:80%;
			 margin:0 auto 20px;;
		 }
		 .j-ab-add dl:after{
			 content:'';
			 height:0;
			 display:block;
			 overflow:hidden;
			 clear:both;
			 zoom:1;
		 }
		 .j-ab-add dl dd{
			 float:left;
			 line-height:10px;
			 margin-right:30px;
			 font-size:14px;
		 }
		.j-ab-add h2{
		    width: 100%;
		    height: 70px;
		    line-height: 76px;
		    font-size: 20px;
		    font-weight: bold;
		    text-indent: 35px;
		    color: #f78182;
		    text-align: center;
		}
		.j-ab-add h1{
		    text-align: center;
		}
	   .j-ab-add p{
	       padding-top:20px;
	       letter-spacing:3px;
	    }
	</style>
</head>
<body>
	<!--头部-->
	<jsp:include page="/top.do"></jsp:include>
	<div class="about-banner"></div>
	<div class="J-helpcenter jmt30 jmb30">
		<div class="J-hc-con nav-con clearfix">
			<div>当前位置：
				<a href="${path }/">首页</a>
				>
				<c:forEach items="${channelList }" var="channels">
					<c:if test="${channel.id==channels.id }">
						<c:if test="${channels.channelIsUrl==0 }">
							<a href="<c:url value='/content/contentList/0/${channels.id }.do'/>">${channels.channelTitle }</a>>
						</c:if>
			
						<c:if test="${channels.channelIsUrl!=0 }">
							<a href="<c:url value='${channels.channelUrl }'/>">${channels.channelTitle }</a>>
						</c:if>
					</c:if>
				</c:forEach>
				${contentDetails.contentTitle }
			</div>
			<div class="J-hcc-nav jfl J-aboutus-nav">
				<ul>
					<c:forEach items="${channelList }" var="channels">
						<li <c:if test="${channel.id==channels.id }"> class="choose"</c:if>>
							<c:if test="${channels.channelIsUrl==0 }">
								<a <c:if test="${channel.id==channels.id }"></c:if> <c:if test="${channel.id==channels.id }"> </c:if>  href="<c:url value='/content/contentList/0/${channels.id }.do'/>">${channels.channelTitle }</a>
							</c:if>
				
							<c:if test="${channels.channelIsUrl!=0 }">
								<a <c:if test="${channel.id==channels.id }"></c:if>  href="<c:url value='${channels.channelUrl }'/>">${channels.channelTitle }</a>
							</c:if>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="J-hcc-con jfl jboxsize">
				<div class="j-ab-add">
					<h1>${contentDetails.contentTitle }</h1> 
			        <dl>
				        <dd>作者：${contentDetails.contentAuthor }</dd>
				        <dd>文章来源：${contentDetails.contentSource }</dd>
				        <dd>点击次数：${contentDetails.contentClick +1}次</dd>
				        <dd>更新时间：${contentDetails.updateTimeStr }</dd>
			        </dl>
			        <p>${contentDetails.contentTxt }</p>
				</div>
				<div>
					<div style="float:left"><span id="pre"></span></div>
					<div style="float:right"><span id="next"></span></div>
				</div>
			</div>
		</div>
	</div> 
	<!--尾部-->
	<jsp:include page="/foot.do"></jsp:include>
	<%@ include file="../onlineSupport.jsp"%>
</body>
<script type="text/javascript">
	var currentId = '${contentDetails.id }';
	$(function(){	
		changeTopHover(6);
		getStatistics();
	});
	 var paramsObject={};
     var channelId='${channel.id }';
 
     paramsObject.channelId=channelId;
     //paramsObject.pageNum = 1;
     $(function(){
         $.ajax({
             dataType: 'json',
             url:"${path }/content/getContentListMapJson.do",
             data:paramsObject,
             type: 'POST',
             beforeSend: function(){
                 
             },
             success: function(data) {
            	  $.each(data,function(i,item){
            		  if(item.id==currentId){
            			  if(i==0){
            				  var nextHtml = '<a href="${path}/content/contentDetails/'+channelId+'_'+data[i+1].id+'.html" target="_blank" class="clearfix"><p>'+data[i+1].content_title+'</p></a>';
            				  $("#next").html(nextHtml);
            			  }else if(i>0 && i <data.length-1){
            				  var nextHtml = '<a href="${path}/content/contentDetails/'+channelId+'_'+data[i+1].id+'.html" target="_blank" class="clearfix"><p>'+data[i+1].content_title+'</p></a>';
            				  var preHtml = '<a href="${path}/content/contentDetails/'+channelId+'_'+data[i-1].id+'.html" target="_blank" class="clearfix"><p>'+data[i-1].content_title+'</p></a>';
            				  $("#next").html(nextHtml);
            				  $("#pre").html(preHtml);
            			  }else{
            				  var preHtml = '<a href="${path}/content/contentDetails/'+channelId+'_'+data[i-1].id+'.html" target="_blank" class="clearfix"><p>'+data[i-1].content_title+'</p></a>';
            				  $("#pre").html(preHtml);
            			  }
            		  }
            	  });
             },
             error: function() {
                 
             }
         }); 
     });
</script>
</html>
