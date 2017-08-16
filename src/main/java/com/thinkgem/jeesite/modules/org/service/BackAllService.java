package com.thinkgem.jeesite.modules.org.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.org.dao.OrgAuditLogDao;
import com.thinkgem.jeesite.modules.org.entity.OrgAuditLog;
import com.thinkgem.jeesite.modules.org.entity.OrganizationInfo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class BackAllService extends CrudService<OrgAuditLogDao, OrgAuditLog> {

	@Autowired
	private OrgAuditLogService orgAuditLogService;
	@Autowired
	private OrganizationInfoService organizationInfoService;
	
	public void orgAuditSubmit(List<OrgAuditLog> dtlList, Map<String, Object> respMap, OrgAuditLog organAuditLog) {
		boolean passFlag = true;
		StringBuilder noPassMsg = new StringBuilder(100);
		Date currentDate = new Date();
		User currentUser = UserUtils.getUser();
		OrganizationInfo organInfo = organizationInfoService.findObject(dtlList.get(0).getOrgCode());
		
		for (OrgAuditLog orgAuditLog : dtlList) {
			// 设置子项审核状态
			if ("1".equals(orgAuditLog.getItemCode())) {
				organInfo.setLicenceStatus(orgAuditLog.getStatus());
			} else if ("2".equals(orgAuditLog.getItemCode())) {
				organInfo.setLogStatus(orgAuditLog.getStatus());
			} else {
				organInfo.setLoaStatus(orgAuditLog.getStatus());
			}
			
			
			if ("2".equals(orgAuditLog.getStatus())) {
				passFlag = false;
				noPassMsg.append(DictUtils.getDictLabel(orgAuditLog.getItemCode(), "item_code", "")).append("审核不通过");
				if (StringUtils.isNotBlank(orgAuditLog.getMsg())) {
					noPassMsg.append(",原因:").append(orgAuditLog.getMsg());
				}
				noPassMsg.append("|");
			}
			orgAuditLog.setUpdateDate(currentDate);
			orgAuditLog.setOperator(currentUser);
			orgAuditLogService.auditLog(orgAuditLog);
		}
		
		if (passFlag) {
			organAuditLog.setStatus("1");
			organInfo.setStatus("1");
		} else {
			organAuditLog.setStatus("2");
			organInfo.setStatus("2");
		}
		organAuditLog.setMsg(noPassMsg.toString());
		organAuditLog.setUpdateDate(currentDate);
		organAuditLog.setOperator(currentUser);
		orgAuditLogService.auditLog(organAuditLog);
		
		organInfo.setUpdateDate(currentDate);
		organizationInfoService.updateOrganAuditStatus(organInfo);
		respMap.put("sucFlag", "1");
	}

	
}
