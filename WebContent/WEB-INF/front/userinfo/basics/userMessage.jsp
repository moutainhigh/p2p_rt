<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp" %>
<title>${showTitle }-消息中心</title>
</head>

<body>
<jsp:include page="/top.do"></jsp:include>
		

<div class="J-xzg-myaccount">
 <div class="J-xzg-ma-con nav-con clearfix">
  <div class="J-ma-nav jboxsize">
<input type="hidden" value="left11" id="curTitle">
		<jsp:include page="/account/userLeft.do"></jsp:include>
  </div>

  <div class="J-ma-conR xx_con" style="padding:30px 0;">
    <div class="title">
      <a  onclick="tabChange(1);" class="tap">全部</a>
      <a  onclick="tabChange(2);">未读</a>
      <a  onclick="tabChange(3);">己读</a>
    </div>
    <div class="xx_txt">
      <ul id="userReceiveMessageTable">
      </ul>

      <ul style="display:none;" id="userReceiveMessageTable2" style="text-align: center;">
      </ul>

      <ul style="display:none;" id="userReceiveMessageTable3" style="text-align: center;" >
      </ul>
    </div>
    
  </div><!-- J-ma-conR -->
 </div>
</div>
 <!--/bottom-->
 <jsp:include page="/foot.do"></jsp:include>
 <%@ include file="../../onlineSupport.jsp"%>
<script type="text/javascript">
/* 全部信息 */
$(function(){
	 var data={"tojsp":1};
	 getPageTable(data,"${path }/basics/userReceiveMessagePage.do",generateuserReceiveMessageTable,"userReceiveMessageTable",5);
});


//拼成表格"<li style=\"text-align: center; width: 80px;\"><a href=\"${path}/basics/deleteMessage.do?messageId="+data.id+"\">删除</a></li>";
function generateuserReceiveMessageTable(data){
	var htmlStr='';
	var status=data.messageStatus;
	var statusMsg='';
	if(status==0||status==1){
		
		htmlStr = "<a onclick=getMsgById('A"+data.id+"')><li id='A"+data.id+"' ><div class=time><i>"+data.messageTitle+"</i><span>"+toDate(data.messageSendDateTime,'yyyy-MM-dd hh:mm:ss')+"</span></div><p>"+data.messageContent+"</p> </li></a>";
	
	}
	
    return htmlStr;
};
/*未读信息  */
function generateuserReceiveMessageTable3(data){
	var htmlStr='';
	var status=data.messageStatus;
	var statusMsg='';
	
		
		htmlStr = "<a onclick=getMsgById('w"+data.id+"')><li id='w"+data.id+"' ><div class=time><i>"+data.messageTitle+"</i><span>"+toDate(data.messageSendDateTime,'yyyy-MM-dd hh:mm:ss')+"</span></div><p>"+data.messageContent+"</p> </li></a>";
	

	
    return htmlStr;
}; 
/* 已读信息 */
function generateuserReceiveMessageTable2(data){
	var htmlStr='';
	var status=data.messageStatus;
	var statusMsg='';

		
		htmlStr = "<a onclick=getMsgById('y"+data.id+"')><li id='y"+data.id+"'><div class=time><i>"+data.messageTitle+"</i><span>"+toDate(data.messageSendDateTime,'yyyy-MM-dd hh:mm:ss')+"</span></div><p>"+data.messageContent+"</p> </li></a>";
	
	
	
    return htmlStr;
};
function tabChange(obj){
	if(obj==1){
	 	
		/* 全部信息 */
		$(function(){
			 var data={"tojsp":1};
			 getPageTable(data,"${path }/basics/userReceiveMessagePage.do",generateuserReceiveMessageTable,"userReceiveMessageTable",5);
		});
		
	}else if(obj==2){
		$(function(){
			 var data={"tojsp":1};
			 getPageTable(data,"${path }/basics/userReceiveingMessagePage.do",generateuserReceiveMessageTable2,"userReceiveMessageTable2",5);
		});
	}else if(obj==3){
		 $(function(){
			 var data={"tojsp":1};
			 getPageTable(data,"${path }/basics/userReceiveedMessagePage.do",generateuserReceiveMessageTable3,"userReceiveMessageTable3",5);
		});
	}
}
function getMsgById(id){
	 /* $("#"+id).click(function(){ 
		if($(this).attr("class")==null||$(this).attr("class")==''){
		$(this).addClass("read");
		
		}else if($(this).attr("class")!=null){
			$(this).removeClass();
		}
		
		
 	});  */
 	
	if($("#"+id).attr("class")==null||$("#"+id).attr("class")==''){
		$("#"+id).addClass("read");
		
		}else if($("#"+id).attr("class")!=null){
			$("#"+id).removeClass();
		}
	var ids = id.substr(1);
	$.ajax({
		type : "post",
		url	:	"${path }/basics/getMessageById.do"	,
		data: "messageId="+ids,
		success: function(data){
		     window.location.reload(true); 
		  }

	})
}	
</script>
</body>

</html>