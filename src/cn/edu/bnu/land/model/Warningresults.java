package cn.edu.bnu.land.model;

// Generated 2013-9-29 11:13:47 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Warningresults generated by hbm2java
 */
public class Warningresults implements java.io.Serializable {

	private Integer warningresultsDkid;
	private Date warningresultsTime;
	private Boolean warningresultsResult;

	public Warningresults() {
	}

	public Warningresults(Date warningresultsTime, Boolean warningresultsResult) {
		this.warningresultsTime = warningresultsTime;
		this.warningresultsResult = warningresultsResult;
	}

	public Integer getWarningresultsDkid() {
		return this.warningresultsDkid;
	}

	public void setWarningresultsDkid(Integer warningresultsDkid) {
		this.warningresultsDkid = warningresultsDkid;
	}

	public Date getWarningresultsTime() {
		return this.warningresultsTime;
	}

	public void setWarningresultsTime(Date warningresultsTime) {
		this.warningresultsTime = warningresultsTime;
	}

	public Boolean getWarningresultsResult() {
		return this.warningresultsResult;
	}

	public void setWarningresultsResult(Boolean warningresultsResult) {
		this.warningresultsResult = warningresultsResult;
	}

}
