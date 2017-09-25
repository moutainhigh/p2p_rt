<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../taglibs.jsp"%>



<title>${showTitle }-私募基金</title>

 <script type="text/javascript" >
		function jsMothed(obj){
		
	
		
		$('#myModal'+obj).show();
		$('.reveal-modal-bg').show();
		
	}
	
		function closemu(obj){
			
			$('#myModal'+obj).hide();
		$('.reveal-modal-bg').hide();
		}
	
	</script>

</head>
<body>
<jsp:include page="/top.do"></jsp:include>
<!-- 路径 --><input type="hidden" value="${baseUrl}" id="url"/>
	<div class="J-smjj ">
         <div class="J-smjj-banner"></div>
         <div class="J-smjj-con nav-con jpt30">
          <ol class="clearfix J-smjjc-title">
           <li class="J-active" onclick="changeProduct(1)" id="pd1">可支配产品<span></span></li>
           <li id="pd2" onclick="changeProduct(2)">己成立产品</li>
           <li id="pd3" onclick="changeProduct(3)">己兑付产品</li>
          </ol>
          <div class="J-smjj-changecon">
           <ul>
            <li class="J-act-con">
             <table cellpadding="0" cellspacing="0" width="100%" style="border-collapse:collapse">
              <tr>
               <th>产品名称</th>
               <th>类型</th>
               <th>预期年化收益</th>
               <th>投资起点</th>
               <th>收益分配方式</th>
               <th>&nbsp;</th>
              </tr>
              
             <tbody class="" id="productsTable"></tbody>
              
              <%-- <tr>
               <td ><div class="J-smjj-tf">雪球财富信融汇...</div></td>
               <td>资管类产品</td>
               <td class="J-t-color">10%-13.5%</td>
               <td>100万元</td>
               <td>自然季度</td>
               <td class="J-smjj-btn">
                 
                 <a href="javascript:" onClick="jsMothed(1)" style="color: #fff">预约</a>
                 
                 
                 <div>
                 <div id="myModal1" class="reveal-modal J-smjj-pop jborder" >
                   <div class="J-smjj-pop-title">预约申请</div>
                   <div class="J-smjj-pop-con">
                    <ol class="clearfix jboxsize">
                     <li>
                      <b class="text-overflow">产品名称:</b>
                      <p class="text-overflow">富国低碳环保混合型证券投资基金</p>
                      <div class="jclear"></div>
                     </li>
                      <li>
                      <b class="text-overflow">产品类型:</b>
                      <p class="text-overflow">资管类产品</p>
                      <div class="jclear"></div>
                     </li>
                     <li>
                      <b class="text-overflow">逾期年化:</b>
                      <p class="text-overflow">10%~13.5%</p>
                      <div class="jclear"></div>
                     </li>
                     <li>
                      <b class="text-overflow">投资气点:</b>
                      <p class="text-overflow">100万元</p>
                      <div class="jclear"></div>
                     </li>
                     <li>
                      <b class="text-overflow">收益方式:</b>
                      <p class="text-overflow">自在季度</p>
                      <div class="jclear"></div>
                     </li>
                    </ol>
                    <form action="" method="post" id="f1">
                    <input type="hidden" name="productId" value="1">
                     <label><p><span>*</span>姓名</p></label>
                     <input type="text" name="investUserName"/><br>
                     <label><p><span>*</span>手机号码</p></label>
                     <input type="text" name="investUserTel"/><br>
                     <label><p><span>*</span><em>投资金额<em></p></label>
                     <input type="text" name="investMoney"/>
                    </form>
                    <ul>
                     <li>
                      <a href="javascript:;" onclick="suv(1)" class="jfl">确认</a>
                      <a href="javascript:;" style="float:right; background:#ccc;" onclick="closemu(1)">取消</a>
                     </li>
                    </ul>
                   </div>
                   <a class="close-reveal-modal J-smjj-p-a" onclick="closemu(1)"><img src="${frontPath }/images/J-smjj-pop.png"/></a>
                  </div>  
                  
                  </div>
                  <div class="reveal-modal-bg"></div>
               </td>
              </tr> --%>
			  
			  
			  
			  
             </table>
            </li>
           
             
           </ul>
          </div>
         </div>
        </div>
		
	
	
	
<jsp:include page="/foot.do"></jsp:include>
    </body>
    <script type="text/javascript">
    $(document).ready(function(){
    	changeTopHover(5);

    	getProductsByStatus(1); 


    	});
    
    function getProductsByStatus(status){
		
		var url=$("#url").val()+"/products/products.do";
		$.ajax({
			  type: "post",
			  data: {"productsStatus":status},
			  url: url,
			  async:false,
			  success:function (data){
				  getPageTable(data,url+"?productsStatus="+status,generateProductsTable,"productsTable");
			  }} );
	}
	
  //理财产品列表
    function generateProductsTable(data){
    	 var htmlStr = '';
    	 
         htmlStr='<tr><td ><div class="J-smjj-tf">'
         		+data.productsTitle+'</div></td><td>'
         		+data.dicName+'</td><td class="J-t-color">'
         		+data.productsMinProfit+'%-'
         		+data.productsMaxProfit+'%</td><td>'
         		+(data.productsInvestBegin/10000)+'万元</td><td>'
         		+data.productsIncomeDistribution+'</td><td class="J-smjj-btn"><a href="javascript:" onClick="jsMothed('
         		+data.id+')" style="color: #fff">预约</a><div><div id="myModal'
         		+data.id+'" class="reveal-modal J-smjj-pop jborder" ><div class="J-smjj-pop-title">预约申请</div><div class="J-smjj-pop-con"><ol class="clearfix jboxsize"><li><b class="text-overflow">产品名称:</b><p class="text-overflow">'
         		+data.productsTitle+'</p><div class="jclear"></div></li><li><b class="text-overflow">产品类型:</b><p class="text-overflow">'
         		+data.dicName+'</p><div class="jclear"></div></li><li><b class="text-overflow">预期年化:</b><p class="text-overflow">'
         		+data.productsMinProfit+'%~'
         		+data.productsMaxProfit+'%</p><div class="jclear"></div></li><li><b class="text-overflow">投资起点:</b><p class="text-overflow">'
         		+(data.productsInvestBegin/10000)+'万元</p><div class="jclear"></div></li><li><b class="text-overflow">收益方式:</b><p class="text-overflow">'
         		+data.productsIncomeDistribution+'</p><div class="jclear"></div></li></ol><form action="" method="post" id="f'
         		+data.id+'"><input type="hidden" name="productId" value="'
         		+data.id+'"><label><p><span>*</span>姓名</p></label><input type="text" name="investUserName"/><br><label><p><span>*</span>手机号码</p></label><input type="text" name="investUserTel"/><br><label><p><span>*</span><em>投资金额<em></p></label><input type="text" name="investMoney" /></form><ul><li><a href="javascript:;" onclick="suv('
         				+data.id+')" class="jfl" style="color:#fff">确认</a><a href="javascript:;" style="float:right; background:#ccc;" onclick="closemu('
         						+data.id+')">取消</a></li></ul></div><a class="close-reveal-modal J-smjj-p-a" onclick="closemu('
         								+data.id+')"><img src="${frontPath }/images/J-smjj-pop.png"/></a></div></div><div class="reveal-modal-bg"></div></td></tr>';
        return htmlStr;
    };
    
    
    function suv(pId){
    	$("#f"+pId).validate({						  
			rules: {
				investUserName: {
					required: true
				},
				
				investUserTel:{
					required:true,
					isMobile:true
				},
				
				investMoney:{
					required:true,
					number:true
				}
			}
		});
    	if($("#f"+pId).valid()){
    	 var url=$("#url").val()+"/quickInvestment/saveQuickInvestment.do?productId="+pId;
			
				$.ajax({
					  type: "post",
					  data:$("#f1").serialize(),
					  url: url,
					  async:false,
					  success:function (data){
						  if(data.code == "200"){
							  alertc("预约成功。",reloadPage);
						  }else if(data.code == "201"){
							  window.location.href=$("#url").val()+"/login";
						  }else if(data.code == "301"){
							  alertc("预约失败。");
						}else if(data.code == "101"){
							alertc("请选择地区");
						}
				}} );
				
    	}
    }
    
    function reloadPage(){
		window.location.reload(true);
	}
    
    
    function changeProduct(obj){
    	if(obj==1){
    		$("#pd1").attr("class","J-active");
    		$("#pd2").attr("class","");
    		$("#pd3").attr("class","");
    		getProductsByStatus(1); 
    	}else if(obj==2){
    		$("#pd1").attr("class","");
    		$("#pd2").attr("class","J-active");
    		$("#pd3").attr("class","");
    		getProductsByStatus(2); 
    	}else{
    		$("#pd1").attr("class","");
    		$("#pd2").attr("class","");
    		$("#pd3").attr("class","J-active");
    		getProductsByStatus(3); 
    	}
    	
    }
    </script>
</html>