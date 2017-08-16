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
	function ajaxSub() {
		var dtlList = new Array();
		var exitFlag = false;
		$("ul[class='clear'] li").each(function(){
			var status = "3";
			if ($(this).find(".yui-checkbox").hasClass("yui-checked")) {
				status = "2";
				if ($(this).find("textarea").val() == "") {
					exitFlag = true;
					return false;
				} 
			}
			dtlList.push({id: $(this).find("input[name='id']").val(),itemCode: $(this).find("input[name='itemCode']").val(),orgCode: $(this).find("input[name='orgCode']").val(),credt: $(this).find("input[name='credt']").val(),moddt: $(this).find("input[name='moddt']").val() , status: status, msg: $(this).find("textarea").val()});
		});
		
		if (exitFlag) {
			layer.alert("审核不通过时原因不能为空", {icon: 0});  
			return false;
		}
		$.ajax({
			url : "${ctx}/orgAuditLog/auditSubmit",  
			type: "post",
			dataType: "json",
			data: JSON.stringify(dtlList),
			contentType:"application/json;charset=utf-8",  
			beforeSend:function(XMLHttpRequest) {
			},
	        success:function(resp){  
	        	layer.closeAll('loading');
				if(resp.sucFlag == 1){  
	                layer.msg('审核成功!', {icon: 1,time: 1000,end : function(){
	                		//window.history.go(-1);
	                		window.location.href = "${ctx}/orgAuditLog/auditList?module=1";
	                	}
	                });  
	            }else{
	            	layer.alert(resp.message, {icon: 0});  
	            }  
	        },  
	        error:function(data) {
	        	layer.closeAll('loading');
				layer.alert(data.responseText, {icon: 5, area: ['600px','500px']});  
			}
		});
		
		return false;
	}
	</script>
	
</head>
<body>
	<form id="inputForm">
		<div class="contentR" style="padding-left:10px;">
            <div class="mainTitle mt30">机构审核详情</div>
            <div class="w1000">  
                <ul class="topInfo clear">
                    <li class="fstCon">
                        <p><span>公司指定联系人名称:</span><b>${organMap.adminName}</b></p>
                        <p><span>公司指定联系人邮箱:</span><b>${organMap.adminEmail}</b></p>
                        <p><span>公司指定联系人手机号:</span><b>${organMap.adminMobile}</b></p>
                    </li>
                    <li>
                        <p><span>机构法定名称:</span><b>${organMap.orgName}</b></p>
                        <p><span>组织机构代码:</span><b>${organMap.orgCode}</b></p>
                    </li>
                </ul>
                <div class="contentSub mt30">
                    <div class="verifyInfo">
                    <ul class="clear">
                       	<c:forEach items="${auditLogList}" var="obj" varStatus="status">
                       		<li>
                       		<input type="hidden" name="id" value="${obj.logId}"/>
                       		<input type="hidden" name="orgCode" value="${obj.orgCode}"/>
                       		<input type="hidden" name="itemCode" value="${obj.itemCode}"/>
                       		<input type="hidden" name="credt" value="${obj.credt}"/>
                       		<input type="hidden" name="moddt" value="${obj.moddt}"/>
                       		<c:choose> 
                       		<c:when test="${obj.suffix == '.pdf'}">
                       			<img alt="" src="${ctxStatic}/org/images/pdf.jpg" style="width:182px;height:130px;">
                       		</c:when>
                       		<c:otherwise>
                       			<img alt="" src="${fns:getConfig('web.fileServer')}${obj.filePath}${obj.saveFileName}" style="width:182px;height:130px;">
                       		</c:otherwise>
                       		</c:choose>
                       			<p class="clear mt10"><b class="fl">${fns:getDictLabel(obj.itemCode, 'item_code','')}</b> <a href="${ctx}/downloadNative?fileName=${obj.saveFileName}&filePath=${obj.filePath}${obj.saveFileName}" class="fr downLoad">下载</a></p>
                                <div class="yui-checkbox mt10 fl">
                                    <label><i class="iconfont"></i></label><input type="checkbox" name="" hidden=""><span class="gray9 nopass">不通过</span>
                                </div>	
                                <div class="reason hide">
                                   <i></i>
                                    <textarea autofocus rows="4" name="msg"></textarea>
                                </div>
                       		</li>	
                       	</c:forEach>
                   </ul>
                    <a href="javascript:void(0)" class="btn-style-a mt50 mgCenter db w350" onclick='ajaxSub();'>审 核</a>
                   </div>
                </div>
            </div>
        </div>	
    </form>    
</body>
</html>