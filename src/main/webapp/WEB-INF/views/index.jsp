<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <title>登录页</title>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/org/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/org/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/org/css/yui.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/org/font/iconfont.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/org/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" />
    <script type="text/javascript" src="${ctxStatic}/jquery/jquery-2.2.4.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/layer/3.0.3/layer.js"></script>
	<style type="text/css">
		.hide{display: none;}
		#messageBox label{display:inline-block}
		label.error{background:none;width:180px;padding-left:50px;text-align:center;font-weight:normal;color:#C00;margin:0;}
	</style>
    <script type="text/javascript">
	    $(document).ready(function() {
			$("#loginForm").validate({
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
		function login() {
			if ($("#username").val() == "") {
				layer.alert("用户名不能为空！", {icon: 5, end: function(){
					parent.$("#username").focus();
				}});  
				return false;
			}
			if ($("#password").val() == "") {
				layer.alert("密码不能为空！", {icon: 5, end: function(){
					parent.$("#password").focus();
				}});  
				return false;
			}
			$("#loginForm").submit();
		}
	</script>
</head>

<body>
    <!--topNav begin-->
    <div class="head">
        <div class="clear mgCenter w1190">
           <a href="javascript:void(0)" class="adv fl">好买机构审核管理平台</a>
        </div>
    </div>
    
    <div class="adver adverSh">
        <div class="w1190 mgCenter clear" >
            <div class="fl adverL mt50">
                <img src="${ctxStatic}/org/images/lpic.jpg" alt="">
            </div>
            <form class="fr adverLogin mt50" id="loginForm" style="height:360px;" action="${ctx}/login" method="post">
                <h4>审核人员登录</h4>
                <div class="yui-form-cell mt30 mb20 clear">
                    <div class="cell-left f14 w50 tar pr10">用户名</div>
                    <div class="cell-right">
                        <input type="text" id="username" name="username" class="yui-input w230" placeholder="">
                    </div>
			    </div>
               <div class="yui-form-cell mb20 clear">
                    <div class="cell-left f14 w50 tar pr10">密码</div>
                    <div class="cell-right">
                        <input type="password" id="password" name="password" class="yui-input w230" placeholder="">
                        <div class="bcInfo w240 clear">
                            <div class="yui-checkbox mt6 fl">
                                <label><i class="iconfont"></i></label><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}><span>记住密码</span>
                            </div>
                            <!-- <div class="yui-checkbox mt6 fr bcInfo">
                                <a href="">忘记密码/修改密码？</a>
                            </div> -->
                        </div>
                    </div>
			    </div>
			    <div id="messageBox" class="errorMsg tac red ${empty message ? 'hide' : ''}" >
                	<label id="loginError" class="error">${message}</label>
                </div>
               <a href="javascript:void(0)" onclick="login();" class="btn-style-a mt20 db">登 录</a>
            </form>
        </div>
    </div>
    <div class="footer" style="background:#fff;">
        <div class="gray9 tac f12">版权所有 好买财富管理股份有限公司 Copyright©ehowbuy.com, inc 2017. All rights reserved. [ICP备08003295号]</div>
    </div>
    <script type="text/javascript" src="${ctxStatic}/org/js/yui.js"></script>
</body>
</html>