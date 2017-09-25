<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../taglibs.jsp"%>
<title>${showTitle }</title>
</head>
<body>
	<jsp:include page="/top.do"></jsp:include>
	
	<!--banner-->
<div class="wyjk-banner sl-wyjk-banner"></div>

<!--内页内容区域-->
<div class="sl_content">
    <div class="sl_f_l_p">
        <div class="sl_f_l_p_t">
            <p >
            	<img width="271px;" height="283px;" src="${path }${goods.logPath }" style="margin-left: 76px;margin-top: 34px;">
            </p>
            <div class="sl_mnyx">
                <h3>${goods.gName }</h3>
                <ul>
                    <li>
                       所属类别：${goods.categoryName }
                    </li>
                    <li>
                        所需积分：<strong>${goods.reqCredit }</strong>积分
                    </li>
                    <li>
                        剩余数量：${goods.totalCount - goods.sellCount }/个
                    </li>
                    <li>
                        上架日期：<fmt:formatDate value="${goods.publishTime }" pattern="yyyy-MM-dd HH:mm:ss" />
                    </li>
                    <li>
                        我要兑换：<input class="sl_wydh" type="text" name="num" id="num" onkeyup="value=value.replace(/[^0-9|.]/g,'')" /> 件
                    </li>
                    
                    
                     <c:choose>
	                    	<c:when test="${goods.reqCredit >  creditValueUsable}">
	                    		
                     <li class="sl_n_d_j_f">
                       您的积分不够
                    </li>
	                    	</c:when>
	                    	<c:otherwise>
	                    		
	                    		 <li class="sl_n_d_j_f" style="background: #ff6600 none repeat scroll 0 0;cursor: pointer;" onclick="submit()">
                      兑换
                    </li>
	                    	</c:otherwise>
	                    </c:choose>
                    
                    
                    
                    
                </ul>
            </div>
        </div>
        <div class="sl_f_l_p_b">
            <div class="box_g">
        <ul class="tab_menu_g">
            <li style="border-right:1px solid #ddd;" class="current">商品详情</li>
            <li style="border-right:1px solid #ddd;">积分规则</li>
           
        </ul>
        <div class="tab_box_g">
            <div>
               ${goods.gDetail }
            </div>
            <div class="hide">
               <div>
                    <p>
                    积分商城规则<br/>
                  
                   ${rule }
               </div>
            </div>              
        </div>
    </div>             

    
    <script src="${cssJsPath }/js/jquery.tabs2.js"></script>
    <script type="text/javascript">
    $(function(){
        $('.box_g').Tabs({
            event:'click'
        });

    }); 
    </script>

    </div>
         
</div>
</div>
	
	<jsp:include page="/foot.do"></jsp:include>
</body>
<script type="text/javascript">
	$(function () {
		onHoverLi(5);
		
		
	});
	
	function submit(){
		var url="${path }/shop/cashGoods.do";
		var gid="${goods.id}";
		var num=$("#num").val();
		var sumCredit="${goods.reqCredit}"*num;
		var creditValue="${creditValueUsable}";
		if(num != 0 && num !=null){
			if(sumCredit>creditValue){
				alertc("积分不足！");
			}else{
				$.post(url,{goodsId:gid,number:num},function(data){
					if(data.code=="101"){
						alertc(data.msg);
					}
					if(data.code=="102"){
						alertc(data.msg);
					}
					if(data.code=="103"){
						alertc(data.msg);
					}
					if(data.code=="104"){
						alertc(data.msg);
					}
					if(data.code=="105"){
						alertc(data.msg);
					}
					if(data.code=="106"){
						alertc(data.msg);
					}
					if(data.code=="107"){
						alertc(data.msg);
					}
					if(data.code=="108"){
						alertc(data.msg);
					}
					if(data.code=="200"){
						alertc(data.msg,function () {
							window.location.reload();
						});
					}
				});
			}
		}else{
			alert("请选择正确的兑换数量！");
		}
	}
</script>
</html>