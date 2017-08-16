<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构信息</title>
	<meta name="decorator" content="org" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" />
	<!-- js -->
	<script type="text/javascript" src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/jquery-form/jquery.form.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		
		/*添加管理员*/
	    $('.addManager').on('click',function(){
	    	$("#inputForm")[0].reset();
	    	$("#inputForm").validate().resetForm();
	        layer.open({
	          title:'添加管理员信息',
	          type: 1,
	          skin: 'layui-layer-rim', //加上边框
	          area: ['800px', '330px'], //宽高
	          content: $(".addManagePop"),
	          btn: ['保存', '取消'],
	          yes:function(index){
	        	  $("#inputForm").submit();
	          }
	        });
	    });
		
		$("#inputForm").validate({
			onfocusout: function(element) {$(element).valid()},// 开启实时验证
			rules:{
				name:{
                    required:true
                },
                email:{
                	required:true,
                    email:true
                },
                password:{
                    required:true,
                    rangelength:[6,32]
                },
                confirmPassword:{
                	required:true,
                    equalTo:"#password"
                }                    
            },
            messages:{
            	email:{
                    email:"邮箱格式有误，请重新输入"
                },
                password:{
                    rangelength: $.format("密码长度必须介于{0}至{1}字符，请重新输入")
                },
                confirmPassword:{
                    equalTo:"两次输入密码不一致，请重新输入"
                }                                    
            },
			submitHandler: function(form){
				$('#inputForm').ajaxSubmit({
					url : "${ctx}/manager/saveManager",  
					type : "post",  
					dataType : "json", 
					beforeSend : function(XMLHttpRequest) {
						layer.load();	// 遮罩
					},
					success:function(responseText, statusText, xhr, $form){
						layer.closeAll('loading');
						if(responseText.sucFlag == 1){  
							layer.msg('添加成功!', {icon: 1,time: 1000,end : function(){
	                			window.location.reload();
	                		}});  
			            }else{
			            	layer.alert(responseText.message, {icon: 0});  
			            }  
					},
					error:function(data) {
						layer.closeAll('loading');
						layer.alert(data.responseText, {icon: 5, area: ['600px','500px']});  
					}
				});
			},
			errorPlacement: function(error, element) {
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else if (element.attr("id") == "validateCode" || element.attr("id") == "mobileCheckCode") {
					error.insertAfter(element.parent());
				} else {
					error.insertAfter(element.parent());
				}
			}
		});
	});

	
	</script>
</head>
<body>
	<div class="mainTitle mt30">管理员列表</div>
    <div class="w1000">
        <div class="contentSub pt30">
            <table class="yui-table managerInfo tac" data-zebra="odd">
                <tr>
                    <th>管理员姓名</th>
                    <th>管理员邮箱</th>
                    <th>管理员邮箱状态</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${userList}" var="user">
				<tr>
					<td>${user.name}</td>
                    <td>${user.email}</td>
                    <td><span class="${user.delFlag == 0 ? 'green' : 'red'}">${fns:getDictLabel(user.delFlag, 'delete_status', '')}</span></td>
                    <td>
                    	<c:choose>
                    	<c:when test="${user.delFlag == '1'}">
							<a href="javascript:void(0)" class="fwb operateBtn" onclick="update('${user.id}',0)">重新添加</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:void(0)" class="fwb del" onclick="update('${user.id}',1)">删除</a>
						</c:otherwise>
						</c:choose>
                    </td>
				</tr>
				</c:forEach>
            </table>
            <a href="javascript:void(0)" class="btn-style-a w340 mgCenter mt40 addManager db">新增管理员</a>
        </div>
    </div>
    <div class="pop addManagePop pt20 hide">
    <form id="inputForm" method="post">
        <div class="yui-form-cell mb5 clear">
            <div class="cell-left w170"><span class="ml15 red">*</span>管理员姓名：</div>
            <div class="cell-right">
                <input type="text" id="name" name="name" class="yui-input w350" placeholder="请输入管理员姓名">
            </div>
        </div>
        <div class="yui-form-cell mb5 clear">
            <div class="cell-left w170"><span class="ml15 red">*</span>管理员邮箱：</div>
            <div class="cell-right">
                <input type="text" id="email" name="email" class="yui-input w350" placeholder="请输入管理员邮箱">
            </div>
        </div>
        <div class="yui-form-cell mb5 clear">
            <div class="cell-left w170"><span class="ml15 red">*</span>登录密码：</div>
            <div class="cell-right">
                <input type="password" name="password" id="password" class="yui-input w350" placeholder="密码需为6-32位字符" maxlength="32" minlength="6">
            </div>
        </div>
                
        <div class="yui-form-cell mb5 clear">
	        <div class="cell-left w170"><span class="ml15 red">*</span>确认登录密码：</div>
	        <div class="cell-right">
	            <input type="password" name="confirmPassword" id="confirmPassword" class="yui-input w350" placeholder="密码需为6-32位字符">
	        </div>
        </div>
    </form> 
    </div>
    
<script type="text/javascript">
	function update(userId,delFlag) {
		$.ajax({
    		url: "${ctx}/manager/updateUserByDelete",
			type: "post",
			cache: false,
			dataType: "json",
			data:{"userId":userId,"delFlag":delFlag},
	        success:function(resp){  
	            if(resp.sucFlag == "1"){  
	            	layer.msg('操作成功!', {icon: 1,time: 1000,end: function(){
	                		window.location.reload();
	                	}
	                });  
	            }else{  
	                layer.alert(resp.message, {icon: 0});  
	            }  
	        },  
	        error:function(data) {
				layer.alert(data.responseText, {icon: 5, area: '500px'});  
			}
    	});
	}
</script>
</body>
</html>