package cn.edu.bnu.land.model;

// Generated 2013-9-8 16:59:33 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * FkTasktable generated by hbm2java
 */
public class FkTasktable implements java.io.Serializable {

	private Integer taskId;
	private String projectId;
	private String projectname;
	private String pianKuaiId;
	private String collectionItems;
	private String dataFormat;
	private String submission;
	private String dataRequirements;
	private String gatherers;
	private Date acquisitiontime;

	public FkTasktable() {
	}

	public FkTasktable(String projectId, String projectname, String pianKuaiId,
			String collectionItems, String dataFormat, String submission,
			String dataRequirements, String gatherers, Date acquisitiontime) {
		this.projectId = projectId;
		this.projectname = projectname;
		this.pianKuaiId = pianKuaiId;
		this.collectionItems = collectionItems;
		this.dataFormat = dataFormat;
		this.submission = submission;
		this.dataRequirements = dataRequirements;
		this.gatherers = gatherers;
		this.acquisitiontime = acquisitiontime;
	}

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getPianKuaiId() {
		return this.pianKuaiId;
	}

	public void setPianKuaiId(String pianKuaiId) {
		this.pianKuaiId = pianKuaiId;
	}

	public String getCollectionItems() {
		return this.collectionItems;
	}

	public void setCollectionItems(String collectionItems) {
		this.collectionItems = collectionItems;
	}

	public String getDataFormat() {
		return this.dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getSubmission() {
		return this.submission;
	}

	public void setSubmission(String submission) {
		this.submission = submission;
	}

	public String getDataRequirements() {
		return this.dataRequirements;
	}

	public void setDataRequirements(String dataRequirements) {
		this.dataRequirements = dataRequirements;
	}

	public String getGatherers() {
		return this.gatherers;
	}

	public void setGatherers(String gatherers) {
		this.gatherers = gatherers;
	}

	public Date getAcquisitiontime() {
		return this.acquisitiontime;
	}

	public void setAcquisitiontime(Date acquisitiontime) {
		this.acquisitiontime = acquisitiontime;
	}

}
