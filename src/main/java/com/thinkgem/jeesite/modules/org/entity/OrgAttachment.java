package com.thinkgem.jeesite.modules.org.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class OrgAttachment extends DataEntity<OrgAttachment> {

	@Length(min=1, max=200, message="源文件名称长度必须介于 1 和 200 之间")
	private String srcFileName;
	
	@Length(min=1, max=200, message="保存后文件名称长度必须介于 1 和 200 之间")
	private String saveFileName;
	
	@Length(min=1, max=10, message="后缀名称长度必须介于 1 和 10 之间")
	private String suffix;
	
	@Length(min=1, max=200, message="文件储存相对路径长度必须介于 1 和 200 之间")
	private String filePath;
	
	private Date credt;
	
	private Date moddt;
	
	@Length(min=1, max=30, message="机构代码长度必须介于 1 和 30 之间")
	private String orgCode;
	
	@Length(min=1, max=1, message="项目代码长度必须为1")
	private String itemCode;

	public String getSrcFileName() {
		return srcFileName;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	
	
}
