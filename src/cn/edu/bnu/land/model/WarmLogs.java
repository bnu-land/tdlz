package cn.edu.bnu.land.model;

import java.util.Date;

public class WarmLogs {
	private Integer id;
	private String warmTarget;
	private String warmContent;
	private Date warmTime;
	private String logsFlag;
	private String  solveWay;
	private Date solveTime;
	private String warmCategory;
	private String projectID;
	//对应规则
	private Integer rule_id;
	
	
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getWarmTime() {
		return warmTime;
	}
	public void setWarmTime(Date warmTime) {
		this.warmTime = warmTime;
	}
	public String getLogsFlag() {
		return logsFlag;
	}
	public void setLogsFlag(String logsFlag) {
		this.logsFlag = logsFlag;
	}
	public String getSolveWay() {
		return solveWay;
	}
	public void setSolveWay(String solveWay) {
		this.solveWay = solveWay;
	}
	public Date getSolveTime() {
		return solveTime;
	}
	public void setSolveTime(Date solveTime) {
		this.solveTime = solveTime;
	}
	public Integer getRule_id() {
		return rule_id;
	}
	public void setRule_id(Integer rule_id) {
		this.rule_id = rule_id;
	}
	
	
	
}
