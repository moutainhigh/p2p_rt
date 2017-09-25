$(function() {
	getNation();
});

/* 从数据库获取民族数据 */
function getNation() {
	$.ajax({
		type : 'post',
		url : 'dic/getNationData',
		dataType : 'json',
		success : function(data) {
			$.each(data, function(i, item) {
				var options = "<option value='" + item.nationName + "' curid='"
						+ item.id + "'>" + item.nationName + "</option>";
				$("#nation").append(options);
			});
		},
	});
};