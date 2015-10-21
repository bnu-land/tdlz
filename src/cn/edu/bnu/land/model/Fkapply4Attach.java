package cn.edu.bnu.land.model;

import java.util.Date;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Fkapply4Attach{
	private String applyId;
	private String projectname;
	private String conUnit;
	private String projectId;
	private String applyDate;
	private String note;
	private CommonsMultipartFile file;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public CommonsMultipartFile  getFile(){
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getConUnit() {
		return conUnit;
	}
	public void setConUnit(String conUnit) {
		this.conUnit = conUnit;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}


}
