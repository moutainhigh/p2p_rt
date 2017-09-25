// JavaScript Document

$(document).ready(function() {
	
	//设置默认属性
	$.validator.setDefaults({       
		submitHandler: function(form) {    
			form.submit();    
	   }
	}),
	
	//开始验证
	$("#signupForm").validate({						  
		rules: {
			firstname: {
				required: true,
				stringCheck:true,   
				byteRangeLength:[4,15]
			},
			realname: {
				minlength:5
			},
			password: {
				required: true,
				minlength: 5
			},
			confirm_password: {
				required: true,
				minlength: 5,
				equalTo: "#password"
			},
			email: {
				required: true,
				email: true
			},
			tel: {
				isMobile: true
			},
			address: {
				stringCheck:true,   
				byteRangeLength:[4,100]
			},
			read: {
				required:true
			},
			degree: {
				required:true,
				min:0
			},
			idcard: {
				required:true,
				isIdCardNo:true
			}	
		},
		
		//设置错误信息存放标签
		errorElement: "em",
		
		//设置提示信息
		messages:{
			address:{
				stringCheck: "请正确输入您的联系地址",   
                byteRangeLength: "请详实您的联系地址以便于我们联系您"
			},
			read:{
				required:"请先阅读注册条约"
			},
			degree:{
				min:"请选择学历"
			}
		},
		
		//指定错误信息位置
		errorPlacement: function (error, element) { 
      		if (element.is(':radio') || element.is(':checkbox')) {
          		var eid = element.attr('name');
          		error.appendTo(element.parent());
      		} else {
          		error.insertAfter(element);
     		}
		},

		
		//设置验证触发事件
		focusInvalid: true,   

		//设置验证成功提示格式
		success:function(e)
		{
    		e.html("&nbsp;").addClass("valid").text('ok');
		}
	})
});