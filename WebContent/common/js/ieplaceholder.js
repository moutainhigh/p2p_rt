(function($) {
	$.fn.placeholder = function(options) {
		var defaults = {
			pColor : "#ccc",
			pActive : "#999",
			pFont : "12px",
			activeBorder : "#080",
			posL : 8,
			zIndex : "99"
		}, opts = $.extend(defaults, options);
		//
		return this.each(function() {
			if ("placeholder" in document.createElement("input"))
				return;
			//2015-11-19fyc添加，个别input的父容器指定了label的align样式或者input中最左侧有其他绝对定位的空间，会导致下边生成的用户显示placeholder的label显示错位，扩展该input的cgLef属性
			//在原本计算的基础上添加padding-left多少个像素
			var cgLeft = $(this).attr("cgLeft");
			if(cgLeft == undefined || cgLeft == "" || cgLeft == null){
				cgLeft = 0;
			}
			cgLeft = parseFloat(cgLeft);
			$(this).parent().css("position", "relative");
			var isIE = $.browser.msie, version = $.browser.version;
			// 不支持placeholder的浏览器,获得placeholder创建label,使用绝对定位使其显示在文本框内部
			var $this = $(this), msg = $this.attr("placeholder"), iH = $this
					.outerHeight(), iW = $this.outerWidth(), iX = $this
					.position().left, iY = $this.position().top, oInput = $(
					"<label>", {
						"class" : "test",
						"text" : msg,
						"css" : {
							"position" : "absolute",
							"left" : iX + "px",
							"top" : iY + "px",
							"width" : iW - opts.posL + "px",
							"padding-left" : (opts.posL+2+cgLeft) + "px",
							"height" : iH + "px",
							"line-height" : iH + "px",
							"color" : opts.pColor,
							"font-size" : opts.pFont,
							"z-index" : opts.zIndex,
							"cursor" : "text"
						}
					}).insertBefore($this);
			// 2015-05-26 fyc添加，创建完lable之后移除之前的placeholder，避免重复显示
			$this.attr("placeholder", '');
			// 初始状态就有内容
			var value = $this.val();
			if (value.length > 0) {
				oInput.hide();
			}
			//
			$this.on("focus", function() {
				var value = $(this).val();
				if (value.length > 0) {
					oInput.hide();
				}
				oInput.css("color", opts.pActive);
				//

				if (isIE && version < 9) {
					var myEvent = "propertychange";
				} else {
					var myEvent = "input";
				}

				$(this).on(myEvent, function() {
					var value = $(this).val();
					if (value.length == 0) {
						oInput.show();
					} else {
						oInput.hide();
					}
				});

			}).on("blur", function() {
				var value = $(this).val();
				if (value.length == 0) {
					oInput.css("color", opts.pColor).show();
				}
			});
			//
			oInput.on("click", function() {
				$this.trigger("focus");
				$(this).css("color", opts.pActive)
			});
			//
			$this.filter(":focus").trigger("focus");
		});
	}
})(jQuery);
$(function() {
	if ($.browser.msie && $.browser.version <= 9) {
		$(":input[placeholder]").each(function() {
			$(this).placeholder();
		});
	}
});