package com.thinkgem.jeesite.modules.org.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.org.entity.OrgAttachment;
import com.thinkgem.jeesite.modules.org.entity.OrgAuditLog;
import com.thinkgem.jeesite.modules.org.entity.OrganizationInfo;

@MyBatisDao
public interface OrgAuditLogDao extends CrudDao<OrgAuditLog> {

	List<Map<String, Object>> queryList(String orgCode);

	/**
	 * 更新上传文件状态
	 * @param attachment
	 */
	void updateFileStatus(OrgAttachment attachment);

	/**
	 * 提交审核更新机构审核状态
	 * @param organizationInfo
	 */
	void updateStatus(OrganizationInfo organizationInfo);

	List<Map<String, Object>> queryAuditLog(OrgAuditLog auditLog);

	OrgAuditLog getOrganAuditLog(OrgAuditLog orgAuditLog);

	void auditLog(OrgAuditLog orgAuditLog);

	List<Map<String, Object>> queryAttachs(OrgAuditLog auditLog);

}
