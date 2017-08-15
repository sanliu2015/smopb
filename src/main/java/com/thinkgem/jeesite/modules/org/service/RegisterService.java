package com.thinkgem.jeesite.modules.org.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.org.entity.OrganizationInfo;
import com.thinkgem.jeesite.modules.org.entity.Register;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

@Service
@Transactional(readOnly = true)
public class RegisterService {

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private OrganizationInfoService organizationInfoService;
	
	public List<Map<String, Object>> queryOrganInfo(String orgCode) {
		return organizationInfoService.queryOrganInfo(orgCode);
	}

	public void save(Register register) {
		User user = new User();
		user.setOrgCode(register.getOrgCode());
		user.setUserType("FADM");
		user.setName(register.getContactName());
		user.setMobile(register.getContactMobile());
		user.setEmail(register.getContactEmail());
		user.setLoginName(register.getContactMobile());
		user.setPassword(SystemService.entryptPassword(register.getPassword()));
		// 其他相关参数初始化
		Office company = new Office();
		company.setId("1");
		Office office = new Office();
		office.setId("2");
		user.setCompany(company);
		user.setOffice(office);
		User adminUser = systemService.getUser("1");
		user.setCreateBy(adminUser);
		user.setUpdateBy(adminUser);
		Date currentDate = new Date();
		user.setCreateDate(currentDate);
		user.setUpdateDate(currentDate);
		Role role = systemService.getRoleByEnname("orgAdmin");
		List<Role> roleList = new ArrayList<Role>(1);
		roleList.add(role);
		user.setRoleList(roleList);
		systemService.saveUser(user);
		
		OrganizationInfo organizationInfo = new OrganizationInfo();
		organizationInfo.setCode(register.getOrgCode());
		organizationInfo.setName(register.getOrgName());
		organizationInfo.setUserId(user.getId());
		organizationInfo.setCredt(new Date());
		organizationInfo.setModdt(organizationInfo.getCredt());
		organizationInfoService.save(organizationInfo);
		
	}

}
