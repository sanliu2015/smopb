package com.thinkgem.jeesite.modules.org.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.org.entity.OrganizationInfo;
import com.thinkgem.jeesite.modules.org.entity.Register;
import com.thinkgem.jeesite.modules.sys.entity.User;

@MyBatisDao
public interface OrganizationInfoDao extends CrudDao<OrganizationInfo> {

	List<Map<String, Object>> queryOrganInfo(String orgCode);

	List<Map<String, Object>> queryUserByMobile(String mobile);

	void updateOrgInfo(Register register);

	void updateAdminUser(Register register);

	void updateAdminUserWithMobile(Register register);

	List<User> findUserList(String orgCode);

	int updateUserStatus(Map<String, Object> paraMap);

	OrganizationInfo findObject(String orgCode);

	void updateOrganAuditStatus(OrganizationInfo organInfo);
}
