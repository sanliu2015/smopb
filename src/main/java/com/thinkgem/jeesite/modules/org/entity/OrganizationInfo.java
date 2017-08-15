package com.thinkgem.jeesite.modules.org.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class OrganizationInfo extends DataEntity<OrganizationInfo> {

	@Length(min=1, max=30, message="机构代码长度必须介于 1 和 30 之间")
	private String code;
	
	@Length(min=1, max=100, message="机构名称长度必须介于 1 和 100 之间")
	private String name;
	
	private String userId;
	
	private String licenceStatus;
	
	private String logStatus;
	
	private String loaStatus;
	
	private String status;
	
	private Date credt;
	
	private Date moddt;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLicenceStatus() {
		return licenceStatus;
	}

	public void setLicenceStatus(String licenceStatus) {
		this.licenceStatus = licenceStatus;
	}

	public String getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}

	public String getLoaStatus() {
		return loaStatus;
	}

	public void setLoaStatus(String loaStatus) {
		this.loaStatus = loaStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCredt() {
		return credt;
	}

	public void setCredt(Date credt) {
		this.credt = credt;
	}

	public Date getModdt() {
		return moddt;
	}

	public void setModdt(Date moddt) {
		this.moddt = moddt;
	}
	
	
}
