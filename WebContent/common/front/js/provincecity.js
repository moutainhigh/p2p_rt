$(function() {
	getProvince();//页面初始
});

var url = $_path + '/area/getAreaData.do';

function getProvince() {
	$.ajax({
		type : 'post',
		data : {
			pid : 999999999
		},
		url : url,
		dataType : 'json',
		success : function(data) {
			$.each(data, function(i, item) {
				var options = "<option value='" + item.id + "' curcode='"
						+ item.areaCode + "'>" + item.areaName + "</option>";
				$("#bankProvince").append(options);
			});
			
			var initVal = $("#bankProvince").attr("init");
			if(initVal)
			$("#bankProvince").val(initVal);
			getCity();
		}
	});
};

function getCity() {
	var id = $("#bankProvince option:selected").val();
	
	$("#bankCt option[value!=0]").remove();
	if (id != 0) {
		$.ajax({
			type : 'post',
			data : {
				pid : id
			},
			url : url,
			dataType : 'json',
			success : function(data) {
				$.each(data, function(i, item) {
					var options = "<option value='" + item.id
							+ "' curcode='" + item.areaCode + "'>" + item.areaName
							+ "</option>";
					$("#bankCt").append(options);
				});
				
				var initVal = $("#bankCt").attr("init");
				if(initVal)
				$("#bankCt").val(initVal);
			}
		});
	}
};