<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><sitemesh:title/></title>
	<%@ include file="/WEB-INF/views/layouts/org/header.jsp"%>
	<sitemesh:head/>
	<style type="">
	dl dt{font-weight:normal;}
	a:hover{text-decoration:none;/*指鼠标在链接*/}
	a:active{text-decoration:none;/* 指正在点的链接*/}
	</style>
</head>
<body>
	<!--topNav begin-->
    <div class="head">
        <div class="clear w1240">
           <a href="javascript:void(0)" class="adv ml25 fl">好买机构审核管理平台</a>
           <div class="fr headBoxR f20">
                <a href="${ctx}/logout" class="backSystem"><i class="iconfont">&#xe610;</i><span>退出系统</span></a>
                <a href="javascript:void(0)" class="manager"><small></small>${fns:getUser().userType == 'FADM' ? '管理员-':''}${fns:getUser().name}</a>
           </div>
        </div>
    </div>
    <div class="wrapper">
    	<dl class="w220 navSlide">
           <dt ${module == 1 ? 'class="active"' : ''}><a href="${ctx}/orgAuditLog/auditList?module=1"><i class="iconfont f24">&#xe609;</i>机构审核</a></dt>
           <dt ${module == 2 ? 'class="active"' : ''}><a href="${ctx}/user/managerList?module=2"><i class="iconfont f24">&#xe608;</i>管理员列表</a></dt>
        </dl>
        <div class="contentR">
        	<sitemesh:body/>
        </div>
    </div>
	
	<%@ include file="/WEB-INF/views/layouts/org/footer.jsp"%>
</body>
</html>