package com.thinkgem.jeesite.modules.org.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.org.entity.OrgAuditLog;
import com.thinkgem.jeesite.modules.org.entity.OrganizationInfo;
import com.thinkgem.jeesite.modules.org.service.BackAllService;
import com.thinkgem.jeesite.modules.org.service.OrgAuditLogService;
import com.thinkgem.jeesite.modules.org.service.OrganizationInfoService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping("${adminPath}")
public class BackAllContoller extends BaseController {

	@Autowired
	private OrgAuditLogService orgAuditLogService;
	@Autowired
	private OrganizationInfoService organizationInfoService;
	@Autowired
	private BackAllService backAllService;
	@Autowired
	private SystemService systemService;
	
	
	@RequiresPermissions("org:audit:view")
	@RequestMapping(value = "/orgAuditLog/auditList")
	public String OrgAuditLogAuditList(OrgAuditLog orgAuditLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrgAuditLog> page = orgAuditLogService.findPage(new Page<OrgAuditLog>(request, response), orgAuditLog); 
		model.addAttribute("page", page);
		model.addAttribute("module", request.getParameter("module"));
		return "modules/org/auditList";
	}
	
	@RequiresPermissions("org:audit:view")
	@RequestMapping(value = "/orgAuditLog/view")
	public String orgAuditView(OrgAuditLog orgAuditLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		OrgAuditLog auditLog = orgAuditLogService.get(request.getParameter("id"));
		List<Map<String, Object>> organMap = organizationInfoService.queryOrganInfo(auditLog.getOrgCode());
		request.setAttribute("organMap", organMap.get(0));
		List<Map<String, Object>> auditLogList = orgAuditLogService.queryAuditLog(auditLog);
		model.addAttribute("auditLogList", auditLogList);
		model.addAttribute("module", request.getParameter("module"));
		return "modules/org/auditView";
	}
	
	
	@RequiresPermissions("org:audit:edit")
	@RequestMapping(value = "/orgAuditLog/audit")
	public String orgAuditAudit(OrgAuditLog orgAuditLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		OrgAuditLog auditLog = orgAuditLogService.get(request.getParameter("id"));
		List<Map<String, Object>> organMap = organizationInfoService.queryOrganInfo(auditLog.getOrgCode());
		request.setAttribute("organMap", organMap.get(0));
		List<Map<String, Object>> auditLogList = orgAuditLogService.queryAuditLog(auditLog);
		model.addAttribute("auditLogList", auditLogList);
		model.addAttribute("module", request.getParameter("module"));
		return "modules/org/auditSubmit";
	}
	
	@RequiresPermissions("org:audit:edit")
	@RequestMapping(value = "/orgAuditLog/auditSubmit")
	@ResponseBody
	public <T> Map<String, Object> orgAuditSubmit(@RequestBody  List<OrgAuditLog> dtlList, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		if (dtlList != null && dtlList.size() > 0) {
			OrgAuditLog organAuditLog = orgAuditLogService.getOrganAuditLog(dtlList.get(0));
			if (!"0".equals(organAuditLog.getStatus())) {
				respMap.put("sucFlag", "0");
				respMap.put("msg", "已经审核过了，不能重复审核！");
				return respMap;
			} else {
				backAllService.orgAuditSubmit(dtlList, respMap, organAuditLog);
			}
		}
		return respMap;
	}
	
	
	@RequestMapping(value = "/user/managerList")
	public String managerList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<User> userList = systemService.findUserList(user);
		model.addAttribute("userList", userList);
		model.addAttribute("module", request.getParameter("module"));
		return "modules/org/managerList";
	}
	
	
	@RequestMapping(value = "/manager/updateUserByDelete")
	@ResponseBody
	public Map<String, Object> updateUserByDelete(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId, @RequestParam String delFlag) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		User user = systemService.getUser(userId);
		if ("0".equals(delFlag)) {
			List<Map<String, Object>> rsList = systemService.queryOtherUserByLoginName(user);
			if (rsList != null && rsList.size() > 0) {
				respMap.put("sucFlag", "0");
				respMap.put("message", "操作失败，已经存在此用户!");
				return respMap;
			}
		} 
		
		paraMap.put("userId", userId);
		paraMap.put("delFlag", delFlag);
		systemService.updateUserByDelete(paraMap);
		UserUtils.clearCache(user);
//		UserUtils.kickOut(user);	// 剔除某用户待完善
		respMap.put("sucFlag", "1");
		return respMap;
	}
	
	
	/**
	 * 机构添加用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/manager/saveManager")
	@ResponseBody
	public Map<String, Object> saveManager(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		User user = new User();
		user.setLoginName(request.getParameter("email"));
		List<Map<String, Object>> rsList = systemService.queryOtherUserByLoginName(user);
		if (rsList != null && rsList.size() > 0) {
			respMap.put("sucFlag", "0");
			respMap.put("message", "操作失败，已经存在此用户!");
		} else {
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("name", request.getParameter("name"));
			paraMap.put("email", request.getParameter("email"));
			paraMap.put("password", request.getParameter("password"));
			systemService.saveManager(paraMap);
			respMap.put("sucFlag", "1");
		}
		return respMap;
	}
	
	
	
	
}
