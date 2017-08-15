/*
 * 模拟下拉select v1.2
 * Author : cm.yu
 * Date : 2017-5-8
 */
;(function($){
  $.fn.selectUI = function(options){
    var defaults = {
      zindex:500,
	  addCurrent:'current'
      //defaultsItem: 0//默认选中的select项
    }
	$('head').append('<link rel="stylesheet" type="text/css" href="../css/modules/yui-select.css" />')
	
	return  this.each(function(){ 
		var options = $.extend({},defaults, options),_this = $(this),selectInput = _this.find("p"),_d = $(document),selectCon = _this.find("ul");
		selectInput.click(function(){
		  _this.css({zIndex:options.zindex});
		  if(selectCon.is(":visible")==false){
			closeSelect();
			selectCon.fadeIn("fast");
			selectInput.addClass(options.addCurrent);
			a = _d.scrollTop();  
			b = _this.offset().top;
			c = _this.height();
			d = selectCon.height();
			e = $(window).height();
			f = e-b+a-d-c;
			if(f < 0){
			  if (!$.support.leadingWhitespace == false) {//ie6-8
				selectCon.css({bottom:c});
			  }
			}else{
			  selectCon.css({bottom:"inherit"});
			}
			//标注当前已选择的选项
		  	var inputVal = _this.find('p > span').text();
			selectCon.find('li').each(function(){
				if($(this).text() == inputVal){
					$(this).addClass('on').siblings('li').removeClass('on')
				}
			})
		  }
		});
		selectCon.find("li").click(function(){//传值
		  var _this = $(this),_thp = _this.parent(),_saveValue;
		  _thp.siblings("p").find("span").text(_this.text());
		  _thp.next('input').val(_this.attr("date-value"));
		  closeSelect();
		});
		_d.bind("click",function(e){//页面中点击关闭select
		  var target  = $(e.target);
		  if(target.closest(_this).length == 0){
			closeSelect();
		  }
		}); 
		var closeSelect = function(){//关闭所有select下拉，恢复默认
		  if(selectCon.is(":visible")){
		  selectCon.hide();
		  _this.css({zIndex:0});
		  selectInput.removeClass(options.addCurrent);
		  }
		}
	});
  };
})(jQuery);