<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构审核列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/orgAuditLog/auditList">机构审核列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="orgAuditLog" action="${ctx}/orgAuditLog/auditList" method="post" class="breadcrumb form-search">
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
				<input id="submitSdate" name="submitSdate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${orgAuditLog.submitSdate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			-
				<input id="submitEdate" name="submitEdate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
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
				<shiro:hasPermission name="org:audit:view"><th>操作</th></shiro:hasPermission>
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
					<c:if test="${obj.status == '0'}">
    					<a href="${ctx}/orgAuditLog/audit?id=${obj.id}">审核</a>	
    				</c:if>
    				<a href="${ctx}/orgAuditLog/view?id=${obj.id}">详情</a>
    				<shiro:hasPermission name="org:audit:edit">
    				</shiro:hasPermission>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>