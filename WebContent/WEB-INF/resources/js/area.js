$(function() {
	getProvince();
	getNation();
});

/* 从数据库获取民族数据 */
function getNation(){
	$.ajax({
		type : 'post',
		url : 'area/getNationData',
		dataType : 'json',
		success : function(data) {
			$.each(data, function(i, item) {
				var options = "<option value='" + item.id + "'>"
						+ item.nationName + "</option>";
				$("#nation").append(options);
			});
		},
	});
};

function getProvince() {
	$.ajax({
		type : 'post',
		data : {
			pid : 0
		},
		url : 'area/getAreaData',
		dataType : 'json',
		success : function(data) {
			$.each(data, function(i, item) {
				var options = "<option value='" + item.id + "'>"
						+ item.areaName + "</option>";
				$("#province").append(options);
			});
		},
	});
};

function getCity(id) {
	$("#city option[value!=0]").remove();
	$("#area option[value!=0]").remove();
	if (id != 0) {
		$.ajax({
			type : 'post',
			data : {
				pid : id
			},
			url : 'area/getAreaData',
			dataType : 'json',
			success : function(data) {
				$.each(data, function(i, item) {
					var options = "<option value='" + item.id + "'>"
							+ item.areaName + "</option>";
					$("#city").append(options);
				});
			},
		});
	}
};

function getArea(id) {
	$("#area option[value!=0]").remove();
	if (id != 0) {
		$.ajax({
			type : 'post',
			data : {
				pid : id
			},
			url : 'area/getAreaData',
			dataType : 'json',
			success : function(data) {
				$.each(data, function(i, item) {
					var options = "<option value='" + item.id + "'>"
							+ item.areaName + "</option>";
					$("#area").append(options);
				});
			},
		});
	}
};