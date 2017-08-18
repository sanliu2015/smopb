<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>机构信息</title>
<meta name="decorator" content="org" />
<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet" />
<!--[if lte IE 7]><link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome-ie7.min.css" type="text/css" rel="stylesheet" /><![endif]-->
<!--[if lte IE 6]><link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
<link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/common/jeesite.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>
<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>
</head>
<body>
	<div class="mainTitle mt30">机构审核</div>
    <!-- <div class="w1000"> -->
        <form:form id="searchForm" modelAttribute="orgAuditLog" action="${ctx}/orgAuditLog/auditList?module=${module}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>机构名称：</label>
				<form:input path="orgName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>机构代码：</label>
				<form:input path="orgCode" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>审核状态：</label>
				<form:select path="status" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('audit_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li><label>提交日期：</label>
				<input id="submitSdate" name="submitSdate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="${orgAuditLog.submitSdate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			-
				<input id="submitEdate" name="submitEdate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="${orgAuditLog.submitEdate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>机构名称</th>
				<th>机构代码</th>
				<th>审核状态</th>
				<th>提交日期</th>
				<th>审核人</th>
				<shiro:hasPermission name="org:audit:view"></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="obj">
			<tr>
				<td>
					${obj.orgName}
				</td>
				<td>
					${obj.orgCode}
				</td>
				<td>
					${fns:getDictLabel(obj.status, 'audit_status','')}
				</td>
				<td>
					<fmt:formatDate value="${obj.credt}" type="both" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${obj.operator.name}
				</td>
				<shiro:hasPermission name="org:audit:view"><td>
					<c:if test="${not empty obj.newestFlag}">
    				<a href="${ctx}/orgAuditLog/view?module=${module}&id=${obj.id}">详情</a>
    				</c:if>
    				<shiro:hasPermission name="org:audit:edit">
    					<c:if test="${obj.status == '0'}">
    						<a href="${ctx}/orgAuditLog/audit?module=${module}&id=${obj.id}">审核</a>	
    					</c:if>
    				</shiro:hasPermission>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
    <!-- </div> -->
<script type="text/javascript">
</script>
</body>
</html>