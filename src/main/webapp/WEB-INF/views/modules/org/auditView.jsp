<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构信息</title>
	<meta name="decorator" content="org" />
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.media.js"></script> --%>
	<script type="text/javascript">
	$(function(){
		$('.nopass').unbind("click");
		$(".clear img").not(".pdf").bind("click", function(){
			layer.open({
				  type: 1,
				  title: false,
				  closeBtn: 0,
				  area: '1000px',
				  skin: 'layui-layer-nobg', //没有背景色
				  shadeClose: true,
				  content: '<img src="' + $(this).attr("src") + '" />'
				});
		});
	});
	</script>
</head>
<body>
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
                       	<c:forEach items="${auditLogList}" var="obj">
                       		<li>
                       		<c:choose> 
                       		<c:when test="${obj.suffix == '.pdf'}">
                       			<a target="_blank" href="${fns:getConfig('web.fileServer')}${obj.filePath}${obj.saveFileName}"><img alt="" class="pdf" src="${ctxStatic}/org/images/pdf.jpg" style="width:182px;height:130px;"></a>
                       		</c:when>
                       		<c:otherwise>
                       			<img alt="" src="${fns:getConfig('web.fileServer')}${obj.filePath}${obj.saveFileName}" style="width:182px;height:130px;">
                       		</c:otherwise>
                       		</c:choose>
                       			<p class="clear mt10"><b class="fl">${fns:getDictLabel(obj.itemCode, 'item_code','')}</b> <a href="${ctx}/download/downloadRemote?attchId=${obj.attachId}" class="fr downLoad">下载</a></p>
                                <div class="yui-checkbox mt10 fl">
                                    <span class="gray9 nopass">${fns:getDictLabel(obj.status, 'audit_status','')}</span>
                                </div>	
                                <div class="${empty obj.msg ? 'reason hide' : 'reason'}">
                                   <i></i>
                                    <textarea autofocus rows="4" readonly="readonly">${obj.msg}</textarea>
                                </div>
                       		</li>	
                       	</c:forEach>
                   </ul>
                   <a href="javascript:void(0)" class="btn-style-a mt50 mgCenter db w350" onclick='history.go(-1);'>返  回</a>
                   </div>
                </div>
            </div>
        </div>	
</body>
</html>