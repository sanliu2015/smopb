/*
*	YUI JS
*	cm.yu
*	2017.5.5
*/
$(function(){
	/*	YUI-checkbox	*/
	$('.yui-checkbox').each(function(){
		var c = $(this).find('input[type="checkbox"]');
		var skin = $(this).data('skin'),skins;
		var icon = $(this).find('.iconfont');
		if(skin == undefined){
			skins = '';
		}else{
			skins = '-'+skin;
		}
		if(c.is(':checked')){
			$(this).addClass('yui-checked'+skins);
			icon.html('&#xe607;');
		}
		$(this).click(function(){
			if(c.is(':checked')){
				$(this).removeClass('yui-checked'+skins);
				icon.html('&#xe606;');
				c.prop('checked',false);
			}else{
				$(this).addClass('yui-checked'+skins);
				icon.html('&#xe607;');
				c.prop('checked',true);
			}			
		})
	})
	
	/*	YUI-radio	*/
	$('.yui-radio').each(function(){
		var c = $(this).find('input[type="radio"]');
		var skin = $(this).data('skin'),skins;
		var icon = $(this).find('.iconfont');
		if(skin == undefined){
			skins = '';
		}else{
			skins = '-'+skin;
		}
		if(c.is(':checked')){
			$(this).addClass('yui-radioed'+skins);
			icon.html('&#xe607;');
		}		
		/*$(this).click(function(){
			if(c.is(':checked')){
				$(this).removeClass('yui-radioed'+skins);
				icon.html('&#xe6c9;');
				c.prop('checked',false);
			}else{
				$(this).addClass('yui-radioed'+skins);
				icon.html('&#xe607;');
				c.prop('checked',true);
			}
		})	*/
        $(this).click(function(){
			var n = c.attr('name');
			if(c.is(':checked') == false){
				$('input[name='+n+']').parent().removeClass('yui-radioed');
				$('input[name='+n+']').prop('checked',false);
				$('input[name='+n+']').siblings('label').find('.iconfont').html('&#xe6c9;');
				$(this).addClass('yui-radioed');
				icon.html('&#xe607;');
				c.prop('checked',true);
			}
		})
	})
	
	/*	YUI-switch	*/
	$('.yui-switch').click(function(){
		var s = $(this).hasClass('yui-switch-true');
		var val = $(this).find('em').html();
		var vals=[];
		switch (val){
			case 'ON':case 'OFF':
				vals = ['ON','OFF']
				break;
			case '开':case '关':
				vals = ['开','关']
				break;
			case '是':case '否':
				vals = ['是','否']
		}
		$(this).toggleClass('yui-switch-true');
		if(s){
			$(this).find('em').html(vals[1])
		}else{
			$(this).find('em').html(vals[0])
		}
	})
	
	/*	YUI-input	*/
	$('.yui-input-readonly').attr('readonly','readonly');
	
	/*	YUI-upfile	*/
	var uploader = $(".yui-uploader input[type=file]");
	uploader.change(function(){
		if($(this).val()!==''){
			$(this).siblings(".filename").val($(this).val());
		}		
	});

	/*	YUI-table	*/
	$('.yui-table').each(function(){
		var zebra = $(this).data('zebra');
		switch (zebra){
			case 'even':
				$(this).find('tbody tr:nth-child(even)').css({background:'#f8f8f8'});
				break;
			case 'odd':
				$(this).find('tbody tr:nth-child(odd)').css({background:'#f8f8f8'});
				break;		
		}
	})
	$('.yui-table tbody tr').hover(function(){
		$(this).addClass('trcolor')
	},function(){
		$(this).removeClass('trcolor')
	})	
	
	/*	YUI-Tab	*/
	$(".yui-tab-title li").click(function(){
		var index = $(this).index();
		$(this).addClass('current').siblings().removeClass();
		$(this).parent().siblings('.yui-tab-con').find('.con').hide().eq(index).show();
	})

})
