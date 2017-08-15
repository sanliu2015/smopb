package com.thinkgem.jeesite.modules.org.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.org.dao.OrganizationInfoDao;
import com.thinkgem.jeesite.modules.org.entity.OrganizationInfo;
import com.thinkgem.jeesite.modules.org.entity.Register;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class OrganizationInfoService extends CrudService<OrganizationInfoDao, OrganizationInfo> {
	
	@Autowired
	private SystemService systemService;

	@Transactional(readOnly = true)
	public List<Map<String, Object>> queryOrganInfo(String orgCode) {
		return dao.queryOrganInfo(orgCode);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> queryUserByMobile(String mobile) {
		return dao.queryUserByMobile(mobile);
	}

	@Transactional(readOnly = false)
	public void updateOrgInfo(Register register) {
		dao.updateOrgInfo(register);
		dao.updateAdminUser(register);
	}

	@Transactional(readOnly = false)
	public void updateWithNewMobile(Register register) {
		if (StringUtils.isNotBlank(register.getPassword())) {
			register.setPassword(SystemService.entryptPassword(register.getPassword()));
		}
		dao.updateOrgInfo(register);
		dao.updateAdminUser(register);
		dao.updateAdminUserWithMobile(register);
	}

	public List<User> findUserList(String orgCode) {
		return dao.findUserList(orgCode);
	}

	@Transactional(readOnly = false)
	public int updateUserStatus(Map<String, Object> paraMap) {
		return dao.updateUserStatus(paraMap);
	}

	public void saveUser(Map<String, String> paraMap) {
		User currentUser = UserUtils.getUser();
		User user = new User();
		user.setUserType("FOPR");
		user.setName(paraMap.get("name"));
		user.setEmail(paraMap.get("email"));
		user.setMobile(paraMap.get("mobile"));
		user.setOrgCode(currentUser.getOrgCode());
		user.setLoginName(paraMap.get("mobile"));
		user.setPassword(SystemService.entryptPassword(user.getMobile().substring(user.getMobile().length()-6)));
		// 其他相关参数初始化
		Office company = new Office();
		company.setId("1");
		Office office = new Office();
		office.setId("2");
		user.setCompany(company);
		user.setOffice(office);
		user.setCreateBy(currentUser);
		user.setUpdateBy(currentUser);
		Date currentDate = new Date();
		user.setCreateDate(currentDate);
		user.setUpdateDate(currentDate);
		Role role = systemService.getRoleByEnname("orgFOPR");
		List<Role> roleList = new ArrayList<Role>(1);
		roleList.add(role);
		user.setRoleList(roleList);
		systemService.saveUser(user);
		
		
	}

	public OrganizationInfo findObject(String orgCode) {
		return dao.findObject(orgCode);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateOrganAuditStatus(OrganizationInfo organInfo) {
		dao.updateOrganAuditStatus(organInfo);
	}
	
	

}
