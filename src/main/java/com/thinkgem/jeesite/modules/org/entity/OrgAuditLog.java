package com.thinkgem.jeesite.modules.org.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

public class OrgAuditLog extends DataEntity<OrgAuditLog> {

	private static final long serialVersionUID = -1698863684285654006L;

	private String orgCode;
	
	private String itemCode;
	
	private String status;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date credt;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date moddt;
	
	private User operator;
	
	@Length(min=0, max=200, message="审核意见")
	private String msg;
	
	private String orgName;
	
	private String submitSdate;
	
	private String submitEdate;
	

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSubmitSdate() {
		return submitSdate;
	}

	public void setSubmitSdate(String submitSdate) {
		this.submitSdate = submitSdate;
	}

	public String getSubmitEdate() {
		return submitEdate;
	}

	public void setSubmitEdate(String submitEdate) {
		this.submitEdate = submitEdate;
	}

	
}
