package cn.edu.bnu.land.model;

import java.util.Date;

public class WarmRules {
	private Integer id;
	private Date createTime;
	private String warmTarget;
	private String warmContent;
	private Date endTime;
	private String ruleDescription;
	private Integer cycleTime;
	private String warmCategory;
	private int warmTargetID;
	private int warmContentID;
	private int warmCategoryID;
	private String projectID;
	
	
	
	public int getWarmTargetID() {
		return warmTargetID;
	}
	public void setWarmTargetID(int warmTargetID) {
		this.warmTargetID = warmTargetID;
	}
	public int getWarmContentID() {
		return warmContentID;
	}
	public void setWarmContentID(int warmContentID) {
		this.warmContentID = warmContentID;
	}
	public int getWarmCategoryID() {
		return warmCategoryID;
	}
	public void setWarmCategoryID(int warmCategoryID) {
		this.warmCategoryID = warmCategoryID;
	}
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getWarmCategory() {
		return warmCategory;
	}
	public void setWarmCategory(String warmCategory) {
		this.warmCategory = warmCategory;
	}
	public Integer getCycleTime() {
		return cycleTime;
	}
	public void setCycleTime(Integer cycleTime) {
		this.cycleTime = cycleTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getWarmTarget() {
		return warmTarget;
	}
	public void setWarmTarget(String warmTarget) {
		this.warmTarget = warmTarget;
	}
	public String getWarmContent() {
		return warmContent;
	}
	public void setWarmContent(String warmContent) {
		this.warmContent = warmContent;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	
	
}
