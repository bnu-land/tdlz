package cn.edu.bnu.land.model;

// Generated 2014-5-13 17:06:55 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * AbnmWholesupervision generated by hbm2java
 */
public class AbnmWholesupervision implements java.io.Serializable {

	private Integer abwsId;
	private String abwsTitle;
	private String abwsClass;
	private Date abwsDate;
	private String abwsContent;
	private String abwsIsSolved;
	private String abwsOpinion;
	private String abwsPhotopath;
	private String abwsCollector;
	private Date abwsCollecttime;
	private String abwsIsTaskAssigned;
	private String rwId;
	private String projectName;
	private String projectId;
	private String sourceRecordid;
	private String abwsXunchacontent;

	public AbnmWholesupervision() {
	}

	public AbnmWholesupervision(String abwsTitle) {
		this.abwsTitle = abwsTitle;
	}

	public AbnmWholesupervision(String abwsTitle, String abwsClass,
			Date abwsDate, String abwsContent, String abwsIsSolved,
			String abwsOpinion, String abwsPhotopath, String abwsCollector,
			Date abwsCollecttime, String abwsIsTaskAssigned, String rwId,
			String projectName, String projectId, String sourceRecordid,
			String abwsXunchacontent) {
		this.abwsTitle = abwsTitle;
		this.abwsClass = abwsClass;
		this.abwsDate = abwsDate;
		this.abwsContent = abwsContent;
		this.abwsIsSolved = abwsIsSolved;
		this.abwsOpinion = abwsOpinion;
		this.abwsPhotopath = abwsPhotopath;
		this.abwsCollector = abwsCollector;
		this.abwsCollecttime = abwsCollecttime;
		this.abwsIsTaskAssigned = abwsIsTaskAssigned;
		this.rwId = rwId;
		this.projectName = projectName;
		this.projectId = projectId;
		this.sourceRecordid = sourceRecordid;
		this.abwsXunchacontent = abwsXunchacontent;
	}

	public Integer getAbwsId() {
		return this.abwsId;
	}

	public void setAbwsId(Integer abwsId) {
		this.abwsId = abwsId;
	}

	public String getAbwsTitle() {
		return this.abwsTitle;
	}

	public void setAbwsTitle(String abwsTitle) {
		this.abwsTitle = abwsTitle;
	}

	public String getAbwsClass() {
		return this.abwsClass;
	}

	public void setAbwsClass(String abwsClass) {
		this.abwsClass = abwsClass;
	}

	public Date getAbwsDate() {
		return this.abwsDate;
	}

	public void setAbwsDate(Date abwsDate) {
		this.abwsDate = abwsDate;
	}

	public String getAbwsContent() {
		return this.abwsContent;
	}

	public void setAbwsContent(String abwsContent) {
		this.abwsContent = abwsContent;
	}

	public String getAbwsIsSolved() {
		return this.abwsIsSolved;
	}

	public void setAbwsIsSolved(String abwsIsSolved) {
		this.abwsIsSolved = abwsIsSolved;
	}

	public String getAbwsOpinion() {
		return this.abwsOpinion;
	}

	public void setAbwsOpinion(String abwsOpinion) {
		this.abwsOpinion = abwsOpinion;
	}

	public String getAbwsPhotopath() {
		return this.abwsPhotopath;
	}

	public void setAbwsPhotopath(String abwsPhotopath) {
		this.abwsPhotopath = abwsPhotopath;
	}

	public String getAbwsCollector() {
		return this.abwsCollector;
	}

	public void setAbwsCollector(String abwsCollector) {
		this.abwsCollector = abwsCollector;
	}

	public Date getAbwsCollecttime() {
		return this.abwsCollecttime;
	}

	public void setAbwsCollecttime(Date abwsCollecttime) {
		this.abwsCollecttime = abwsCollecttime;
	}

	public String getAbwsIsTaskAssigned() {
		return this.abwsIsTaskAssigned;
	}

	public void setAbwsIsTaskAssigned(String abwsIsTaskAssigned) {
		this.abwsIsTaskAssigned = abwsIsTaskAssigned;
	}

	public String getRwId() {
		return this.rwId;
	}

	public void setRwId(String rwId) {
		this.rwId = rwId;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSourceRecordid() {
		return this.sourceRecordid;
	}

	public void setSourceRecordid(String sourceRecordid) {
		this.sourceRecordid = sourceRecordid;
	}

	public String getAbwsXunchacontent() {
		return this.abwsXunchacontent;
	}

	public void setAbwsXunchacontent(String abwsXunchacontent) {
		this.abwsXunchacontent = abwsXunchacontent;
	}

}
