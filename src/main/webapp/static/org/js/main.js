$(function () {
    /*
        页面设置最小高度
    */
    window.onload=window.onresize=function(){
        setMinHeight();
    }

    function setMinHeight(){
        var _w=$(window).width();
        var _h=$(window).height();
        var _setH=_h-$('.head').height()-$('.footer').height()-172;/*120为各主题结构的padding值*/
        $('.contentR').css('min-height',_setH);
    }
    /*侧边栏收起/展开*/
    if($('.wrapper').find('.navSlide')){
        $('.wrapper').append('<div class="displayArrow"><a class="pngfix" href="javascript:void(0);"></a></div>');
        $('.pngfix').on('click',function(){
            $(this).toggleClass('open');
            $('body').toggleClass('bigPage');
           // $('.navSlide').toggleClass('hide');
            
        })
    }else{
        return false;
    }
    
    
    //判断浏览器是否支持placeholder属性
  supportPlaceholder='placeholder'in document.createElement('input'),
 
  placeholder=function(input){
    var text = input.attr('placeholder'),
    defaultValue = input.defaultValue;
 
    if(!defaultValue){
      input.val(text).addClass("phcolor");
    }
 
    input.focus(function(){
      if(input.val() == text){
        $(this).val("");
      }
    });
 
  
    input.blur(function(){
      if(input.val() == ""){
        $(this).val(text).addClass("phcolor");
          $('.loginPwd').attr('type','text');
      }
    });
 
    //输入的字符不为灰色
    input.keydown(function(){
      $(this).removeClass("phcolor");
      $('.loginPwd').attr('type','password');
    });
  };
 
  //当浏览器不支持placeholder属性时，调用placeholder函数
  if(!supportPlaceholder){
    $('input').each(function(){
      text = $(this).attr("placeholder");
      if($(this).attr("type") == "text"){
        placeholder($(this));
      }
     if($(this).attr("type") == "password"){
        placeholder($(this));
      }
    })
  }
    

  
    // 验证码倒计时
        var times = 60,
            timer = null;

        $(document).on('click', '#getCode', function () {
        	
        	var pattern = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
        	if (!pattern.test($("#contactMobile").val())) {
        		layer.alert("请填写正确的手机号码!", {icon: 5,end : function(){$("#contactMobile").focus();}}); 
        		return false;
        	}
            var $this = $(this);
            if (times < 60) {
            	layer.msg('请' + times + '秒后再试！');
            	return false;
            } else {
            	$.ajax({
//            		url: ctx + "/register/getMobileCheckCode",
					type: "post",
					cache: false,
					dataType: "json",
					data:{"mobile":$("#contactMobile").val()},
			        success:function(resp){  
			            if(resp.sucFlag == "1"){  
			            	layer.msg('验证码已发送，请注意查收');
			            	// 计时开始
			                timer = setInterval(function () {
			                    times--;
			                    if (times <= 0) {
			                        $this.text('发送验证码');
			                        clearInterval(timer);
//			                        $this.attr('disabled', false);
			                        times = 60;
			                    } else {
			                        $this.text(times + '秒');
//			                        $this.attr('disabled', true);
			                    }
			                }, 1000);
			            }else{  
			                layer.alert(resp.message, {icon: 0});  
			            }  
			        },  
			        error:function(data) {
						layer.alert(data.responseText, {icon: 5, area: '500px'});  
					}
            	});
            	
                
            }
            
        });

    /*表格隔行变色*/
    $('table tr:nth-child(odd) td').css('background','#f7f7f7');
    
    /*审核不通过原因*/
    $('.nopass').on('click',function(){
    	$(this).parent().next('.reason').find("textarea").val("");
        if($(this).parent('.yui-checkbox').hasClass('yui-checked')){
            $(this).parent().next('.reason').addClass('hide'); 
        }else{
            $(this).parent().next('.reason').removeClass('hide'); 
        }
    });
    
    $('.iconfont').on('click',function(){
    	$(this).parent().parent().next('.reason').find("textarea").val("");
        if($(this).parent().parent('.yui-checkbox').hasClass('yui-checked')){
            $(this).parent().parent().next('.reason').addClass('hide'); 
        }else{
            $(this).parent().parent().next('.reason').removeClass('hide'); 
        }
    });
//    /*点击下载*/
//    $('.downLoad').on('click',function(){
//        var dataUrl = $(this).parent().prev('img').attr('src');
//        var newImg = document.createElement("img");
//	    newImg.src =  dataUrl;
//        download(newImg.src);
//    })
//    
//    function download(src) {
//        var $a = $("<a></a>").attr("href", src).attr("download", "img.png");
//        $a[0].click();
//    }
    
    /*导航*/
/*    $('.navSlide').on('click','dt',function(){
        $(this).addClass('active').next('dd').toggleClass('hide');
    })*/
    
});
