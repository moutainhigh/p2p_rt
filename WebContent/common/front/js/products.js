function getProductsByStatus(status){
		var current=$("#menu").find(".leftBg");
		$(current).removeClass("leftBg");
		 if(status==1){
			 $("#left1").addClass("leftBg");
		 }else if(status==2){
			 $("#left2").addClass("leftBg");
		 }else if(status==3){
			 $("#left3").addClass("leftBg");
		 }
		$("#productsDiv").show();
		$("#detailDiv").hide();
		$("#inputDiv").hide();
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
	
/*<tr>
<td height="32"><div class="xxyy-cpmc">金元惠理保障房15专专专专专专专专专专专专专专专</div></td>
<td>资管类产品</td>
<td>10%-10.3%</td>
<td>100万</td>
<td>自然季度</td>
<td>产品详情</td>
</tr>*/

//理财产品列表
function generateProductsTable(data){
	 var htmlStr = '<tr>';
	 htmlStr+="<td height=\"32\">"+
	 		  "<div class=\"xxyy-cpmc\"><span style=\"margin-left:5px;\" title="+data.productsTitle+"><a style=\"cursor: pointer;\" onclick=\"getProductDetail("+data.id+");\">"+data.productsTitle+"</a></span></div>"+
	 		  "<td>"+data.dicName+"</td>"+
	 		  "<td>"+data.productsMinProfit+"%-"+data.productsMaxProfit+"%</td>"+
	 		  "<td>"+data.productsInvestBegin+"元</td>"+
	 		  "<td>"+data.productsIncomeDistribution+"</td>"+
	 		  "<td style=\"border-right:1px solid #EEEEEE\"><a style=\"cursor: pointer;\" onclick=\"getProductDetail("+data.id+");\">产品详情</a></td>";
     htmlStr+="</tr>";
    return htmlStr;
};
	
	//理财产品详情
	function getProductDetail(id){
		$("#productsDiv").hide();
		$("#detailDiv").show();
		$("#inputDiv").hide();
		var url=$("#url").val()+"/products/getProductById.do?productId="+id;
		var sta = '';
		$.ajax({
			  type: "post",
			  data: {},
			  url: url,
			  async:false,
			  success:function (data){
				  if(data.productsStatus == 1){
					  sta = "[立即预约此产品]";
				  }
				  
				  var htmlStr = '<h3 style=\"padding-left:25px; font-size:18px; border-bottom:3px solid #EB5811; width:760px\">'+data.productsTitle+"</h3>"+
				  				"<table style=\"padding:8px 0px 8px 30px; margin:20px 0 20px 40px; border:none\">"+
				  				"<tbody>"+
				  				"<tr>"+
				  				"<th width=\"89\" >产品状态：</th>"+
				  				"<td width=\"679\" ><span style=\"color:#F00\">"+data.viewProductsStatus+
				  				"</span><span style=\"color:#6C9\"><a style=\"cursor: pointer;\" onclick=\"toApplay("+data.id+");\" style=\"color:#94B133\">"+sta+"</a>"+
				  				"</span></td>"+"</tr>"+
				  				"<tr>"+"<th>产品类别：</th>"+"<td>"+data.dictionaryProductsType.dicName+"</td>"+"</tr>"+
				  				"<tr>"+"<th>年化收益：</th>"+"<td>"+data.productsMinProfit+"%-"+data.productsMaxProfit+"%</td>"+"</tr>"+
				  				"<tr>"+"<th>产品期限：</th>"+"<td>"+data.productsTimeLimit+"个月</td>"+"</tr>"+
				  				"<tr>"+"<th>发行日期：</th>"+"<td>"+toDate(data.productsPublishDate,'yyyy-MM-dd hh:mm:ss')+"</td>"+"</tr>"+
				  				"<tr>"+"<th>募集规模：</th>"+"<td>"+data.productsScale+"元</td>"+"</tr>"+
				  				"<tr>"+"<th>投资起点：</th>"+"<td>"+data.productsInvestBegin+"元</td>"+"</tr>"+
				  				"<tr>"+"<th>收益分配：</th>"+"<td>"+data.productsIncomeDistribution+"</td>"+"</tr>"+
				  				"<tr>"+"<th>产品描述：</th>"+"<td>"+data.productsContent+"</td>"+"</tr>"+
				  				"</tbody>"+
				  				"</table>";
				  htmlStr+="</div>";
				  $("#detailDiv").html(htmlStr);
			  }} );
	}
	
	//进入预约
	var pId;
	function toApplay(id){
		var current=$("#menu").find(".leftBg");
		$(current).removeClass("leftBg");
		$("#left4").addClass("leftBg");
		var url = '';
		if(id != null){
			url=$("#url").val()+"/quickInvestment/toQuickInvestment.do?productId="+id;
		}else{
			url=$("#url").val()+"/quickInvestment/toQuickInvestment.do?productId="+"";
		}
		
		var sta = '';
		$.ajax({
			  type: "post",
			  data: {},
			  url: url,
			  async:false,
			  success:function (data){
				  if(data.code == "200"){
					  pId = data.msg;
						$("#inputDiv").show();
						$("#detailDiv").hide();
						$("#productsDiv").hide();
				  }
			  }} );
	}
	
	//保存预约
	function saveApplay(){
		$("#applyForm").validate({						  
			rules: {
				investUserName: {
					required: true
				},
				province:{
					required:true
				},
				city:{
					required:true
				},
				area:{
					required:true
				},
				investUserTel:{
					required:true,
					isMobile:true
				},
				investUserCycle:{
					required:true,
					digits:true
				},
				investMoney:{
					required:true,
					number:true
				}
			}
		});
		if($("#applyForm").valid()){
		    var url=$("#url").val()+"/quickInvestment/saveQuickInvestment.do?productId="+pId;
			if(checkArea()){
				$.ajax({
					  type: "post",
					  data:$("#applyForm").serialize(),
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
	}
	
	function reloadPage(){
		window.location.reload(true);
	}
	
	//地区
	function checkArea(){
		if($("#province").val() != 0 && $("#city").val() != 0 && $("#area").val() != 0){
			return true;
		}else{
			alertc("请选择地区");
			return false;
		}
	}