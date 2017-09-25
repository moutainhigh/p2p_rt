$(function() {
	getProvince();//页面初始
});

var url = 'area/getAreaData';

function getProvince() {
	$.ajax({
		type : 'post',
		data : {
			pid : 0
		},
		url : url,
		dataType : 'json',
		success : function(data) {
			$.each(data, function(i, item) {
				var options = "<option value='" + item.areaName + "' curid='"
						+ item.id + "'>" + item.areaName + "</option>";
				$("#province").append(options);
			});
		},
	});
};

function getCity() {
	var id = $("#province option:selected").attr("curid");
	$("#city option[value!=0]").remove();
	$("#area option[value!=0]").remove();
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
					var options = "<option value='" + item.areaName
							+ "' curid='" + item.id + "'>" + item.areaName
							+ "</option>";
					$("#city").append(options);
				});
			},
		});
	}
};

function getArea(id) {
	var id = $("#city option:selected").attr("curid");
	$("#area option[value!=0]").remove();
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
					var options = "<option value='" + item.areaName
							+ "' curid='" + item.id + "'>" + item.areaName
							+ "</option>";
					$("#area").append(options);
				});
			},
		});
	}
};