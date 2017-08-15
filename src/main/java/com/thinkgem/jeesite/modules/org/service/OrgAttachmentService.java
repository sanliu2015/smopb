package com.thinkgem.jeesite.modules.org.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.org.dao.OrgAttachmentDao;
import com.thinkgem.jeesite.modules.org.entity.OrgAttachment;

@Service
@Transactional(readOnly = true)
public class OrgAttachmentService extends CrudService<OrgAttachmentDao, OrgAttachment> {


}
