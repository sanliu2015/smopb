package com.thinkgem.jeesite.modules.org.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.modules.org.dao.OrgAuditLogDao;
import com.thinkgem.jeesite.modules.org.entity.OrgAttachment;
import com.thinkgem.jeesite.modules.org.entity.OrgAuditLog;
import com.thinkgem.jeesite.modules.org.entity.OrganizationInfo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class OrgAuditLogService extends CrudService<OrgAuditLogDao, OrgAuditLog> {

	@Autowired
	private OrgAttachmentService orgAttachmentService;
	
	public List<Map<String, Object>> queryList(String orgCode) {
		return dao.queryList(orgCode);
	}

	public void saveFile(MultipartFile multipartFile, HttpServletRequest request) throws IllegalStateException, IOException {
		String itemCode = request.getParameter("itemCode");
		String orgCode = UserUtils.getUser().getOrgCode();
		String srcFileName = multipartFile.getOriginalFilename();
		String suffix  = srcFileName.substring(srcFileName.lastIndexOf("."));
		String saveFileName = itemCode + suffix;
		String filePath = "/static/licences/" + orgCode + "/";
		String descFileName = request.getSession().getServletContext().getRealPath("/") + filePath + saveFileName;
		boolean result = FileUtils.createFile(descFileName, true);
		if (!result) {
			throw new IOException("上传文件时出现异常！");
		} else {
			multipartFile.transferTo(new File(descFileName));
			OrgAttachment attachment = new OrgAttachment();
			attachment.setOrgCode(orgCode);
			attachment.setItemCode(itemCode);
			List<OrgAttachment> dtlList = orgAttachmentService.findList(attachment);
			if (dtlList != null && dtlList.size() > 0) {
				attachment.setId(dtlList.get(0).getId());
			}
			attachment.setSuffix(suffix);
			attachment.setSrcFileName(srcFileName);
			attachment.setSaveFileName(saveFileName);
			attachment.setFilePath(filePath);
			attachment.setCredt(new Date());
			attachment.setModdt(attachment.getCredt());
			orgAttachmentService.save(attachment);
			dao.updateFileStatus(attachment);
		}
		
	}

	public void submitAudit(OrganizationInfo organizationInfo) {
		Date currentDate = new Date();
		if ("1".equals(organizationInfo.getLicenceStatus())) {		// 已上传
			OrgAuditLog log = new OrgAuditLog();
			log.setStatus("0");
			log.setItemCode("1");	// 营业执照
			log.setCredt(currentDate);
			log.setModdt(currentDate);
			log.setOrgCode(organizationInfo.getCode());
			super.save(log);
		}
		
		if ("1".equals(organizationInfo.getLogStatus())) {		
			OrgAuditLog log = new OrgAuditLog();
			log.setStatus("0");
			log.setItemCode("2");	// 产品净值保证函
			log.setCredt(currentDate);
			log.setModdt(currentDate);
			log.setOrgCode(organizationInfo.getCode());
			super.save(log);
		}
		
		if ("1".equals(organizationInfo.getLoaStatus())) {		
			OrgAuditLog log = new OrgAuditLog();
			log.setStatus("0");
			log.setItemCode("3");	// 授权函
			log.setCredt(currentDate);
			log.setModdt(currentDate);
			log.setOrgCode(organizationInfo.getCode());
			super.save(log);
		}
		
		OrgAuditLog log = new OrgAuditLog();
		log.setStatus("0");
		log.setItemCode("0");
		log.setCredt(currentDate);
		log.setModdt(currentDate);
		log.setOrgCode(organizationInfo.getCode());
		super.save(log);
		
		dao.updateStatus(organizationInfo);
		
		
	}

	public List<Map<String, Object>> queryAuditLog(OrgAuditLog auditLog) {
		return dao.queryAuditLog(auditLog);
	}

	public OrgAuditLog getOrganAuditLog(OrgAuditLog orgAuditLog) {
		return dao.getOrganAuditLog(orgAuditLog);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void auditLog(OrgAuditLog orgAuditLog) {
		dao.auditLog(orgAuditLog);
	}

	public List<Map<String, Object>> queryAttachs(OrgAuditLog auditLog) {
		return dao.queryAttachs(auditLog);
	}


}
